package com.nortal.spring.cw.core.xml.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.xml.transform.StringSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.nortal.spring.cw.core.exception.AppBaseRuntimeException;
import com.nortal.spring.cw.core.exception.JaxbValidatingException;
import com.nortal.spring.cw.core.file.holder.DirectoryHolder;
import com.nortal.spring.cw.core.file.holder.FileHolder;
import com.nortal.spring.cw.core.model.FileHolderModel;
import com.nortal.spring.cw.core.util.ClassScanner;
import com.nortal.spring.cw.core.web.factory.FileStreamFactory;
import com.nortal.spring.cw.core.xml.EpmXMLNameTransformer;
import com.nortal.spring.cw.core.xml.XmlBinding;
import com.nortal.spring.cw.core.xml.XmlModel;
import com.nortal.spring.cw.core.xml.jaxb.FormSchemaOutputResolver;
import com.nortal.spring.cw.core.xml.jaxb.XmlValidationEventHandler;

/**
 * @author Margus Hanni
 * 
 */
@Slf4j
public final class JAXBUtil {

   private static final Map<Class<?>, DirectoryHolder> SCHEMA_HOLDER = new HashMap<>();

   private JAXBUtil() {
      super();
   }

   public static <T> StringWriter toXML(T object) throws JAXBException {

      JAXBContext context = createContext(object.getClass());
      Marshaller m = context.createMarshaller();
      m.setEventHandler(new XmlValidationEventHandler());
      m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

      StringWriter w = null;
      try {
         w = new StringWriter();
         m.marshal(object, w);
      } finally {
         try {
            w.close();
         } catch (Exception e) {
         }
      }

      return w;
   }

   public static <T> T toObject(Source source, Class<?>... objectType) throws JAXBException {
      JAXBContext context = createContext(objectType);

      Unmarshaller m = context.createUnmarshaller();
      m.setEventHandler(new XmlValidationEventHandler());

      @SuppressWarnings("unchecked")
      T object = (T) m.unmarshal(source);

      return object;
   }

   public static <T> T toObject(Reader reader, Class<T> objectType) throws JAXBException {
      JAXBContext context = createContext(objectType);

      Unmarshaller m = context.createUnmarshaller();
      m.setEventHandler(new XmlValidationEventHandler());

      @SuppressWarnings("unchecked")
      T object = (T) m.unmarshal(reader);

      return object;
   }

   public static <T> T toObject(InputStream inputStream, Class<T> objectType) throws JAXBException, IOException, SAXException,
         TransformerFactoryConfigurationError, TransformerException {
      JAXBContext context = createContext(objectType);

      Unmarshaller m = context.createUnmarshaller();
      m.setEventHandler(new XmlValidationEventHandler());

      @SuppressWarnings("unchecked")
      T object = (T) m.unmarshal(inputStream);

      return object;
   }

   public static XmlModel toObject(Reader inputReader) throws JAXBException {
      Element documentElement = null;
      try {
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         dbf.setValidating(false);
         dbf.setNamespaceAware(true);
         DocumentBuilder db = dbf.newDocumentBuilder();
         db.setEntityResolver(new EntityResolver() {
            @Override
            public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
               return new InputSource(new StringReader(""));
            }
         });
         Document xmlDoc = db.parse(new InputSource(inputReader));

         documentElement = xmlDoc.getDocumentElement();
      } catch (Exception e) {
         log.error("Exception creating XML document", e);
      }

      Class<?> xmlStreamClass = determineObjectType(documentElement);
      JAXBContext context = createContext(xmlStreamClass);

      Unmarshaller m = context.createUnmarshaller();
      m.setEventHandler(new XmlValidationEventHandler());

      Object xmlModel = m.unmarshal(documentElement);

      return (XmlModel) xmlModel;
   }

   private static Class<?> determineObjectType(Element documentElement) {
      try {
         return findMatchingClass(documentElement.getLocalName());
      } catch (Exception e) {
         log.error("Exception determining object type", e);
      }
      return null;
   }

   private static Class<?> findMatchingClass(String rootElementName) {
      Collection<Class<?>> matches = new ClassScanner("ee.epm").withAnnotationFilter(XmlRootElement.class)
            .withAssignableFilter(XmlModel.class).findClasses();
      EpmXMLNameTransformer nameTransformer = new EpmXMLNameTransformer();
      for (Class<?> cl : matches) {
         XmlRootElement annotation = cl.getAnnotation(XmlRootElement.class);
         String classRootElementName = null;
         if (annotation != null && !"##default".equals(annotation.name())) {
            classRootElementName = nameTransformer.transformRootElementName(annotation.name());
         } else {
            classRootElementName = nameTransformer.transformRootElementName(cl.getName());
         }

         if (classRootElementName != null && classRootElementName.equalsIgnoreCase(rootElementName)) {
            return cl;
         }
      }
      return null;
   }

   public static <T> List<FileHolder> getOrCreateSchemasAsFile(Class<T> objectType) throws IOException, JAXBException,
         TransformerFactoryConfigurationError, TransformerException {
      DirectoryHolder directoryHolder = SCHEMA_HOLDER.get(objectType);

      if (directoryHolder != null) {
         if (directoryHolder.exists()) {
            return directoryHolder.getFileHolders();
         }
      }

      XmlRootElement xmlRootElement = objectType.getAnnotation(XmlRootElement.class);
      String name;
      if (xmlRootElement == null) {
         name = objectType.getAnnotation(XmlType.class).name();
      } else {
         name = xmlRootElement.name();
      }

      directoryHolder = new DirectoryHolder(name);

      int count = 0;
      for (FileHolder holder : createSchemasAsFile(objectType)) {
         directoryHolder.getNewHolder(String.format("schema%s", ++count), "xsd").setData(new String(holder.getData()));
         holder.delete();
      }

      SCHEMA_HOLDER.put(objectType, directoryHolder);

      return directoryHolder.getFileHolders();
   }

   public static List<FileHolder> createSchemasAsFile(Class<?>... objectTypes) throws IOException, JAXBException,
         TransformerFactoryConfigurationError, TransformerException {

      List<FileHolder> schemas = new ArrayList<>();

      for (Source schema : createSchemasAsSource(objectTypes)) {
         StringWriter writer = new StringWriter();
         Transformer transformer = TransformerFactory.newInstance().newTransformer();
         transformer.transform(schema, new StreamResult(writer));
         schemas.add(new FileHolder(writer.toString()));
      }

      return schemas;
   }

   public static List<Source> createSchemasAsSource(Class<?>... objectTypes) throws IOException, JAXBException,
         TransformerFactoryConfigurationError, TransformerException {

      List<Source> schemas = new ArrayList<>();

      for (DOMResult schema : createSchemasAsDOM(objectTypes)) {
         schemas.add(new DOMSource(schema.getNode()));
      }

      return schemas;
   }

   public static List<DOMResult> createSchemasAsDOM(Class<?>... objectTypes) throws IOException, JAXBException,
         TransformerFactoryConfigurationError, TransformerException {

      JAXBContext jaxbContext = createContext(objectTypes);

      FormSchemaOutputResolver sor = new FormSchemaOutputResolver();
      jaxbContext.generateSchema(sor);

      return sor.getSchemas();
   }

   public static JAXBContext createContext(Class<?>... objectTypes) throws JAXBException {
      return createContext(objectTypes, null);
   }

   public static JAXBContext createContext(Class<?>[] objectTypes, Map<String, Object> properties) throws JAXBException {
      List<String> bindings = new ArrayList<String>();
      for (Class<?> typeItem : objectTypes) {
         XmlBinding binding = AnnotationUtils.findAnnotation(typeItem, XmlBinding.class);
         if (binding != null) {
            for (String bindingItem : binding.value()) {
               if (!bindings.contains(bindingItem)) {
                  bindings.add(bindingItem);
               }
            }
         }
      }

      if (properties == null || properties instanceof AbstractMap) {
         properties = new HashMap<String, Object>();
      }
      if (!bindings.isEmpty()) {
         properties.put(JAXBContextProperties.OXM_METADATA_SOURCE, bindings);
      }
      return JAXBContextFactory.createContext(objectTypes, properties);
   }

   /**
    * XMLi andmebaasist välja lugemine, määrartud tüübi instantsieerimine ning XMLi andmetega täitmine. Toimingu ebaõnnestumisel kutsutakse
    * välja erind {@link AppBaseRuntimeException}
    * 
    * @param fileModel
    *           {@link FileHolderModel}
    * @param documentType
    *           {@link Class}
    * @return T
    * @throws AppBaseRuntimeException
    */
   @SuppressWarnings("unchecked")
   public static <T> T unmarshaller(FileHolderModel fileModel, Class<T> documentType) {

      try {
         StringSource source = new StringSource(IOUtils.toString(FileStreamFactory.instance(FileStreamFactory.DATABASE).getFileStream(
               fileModel)));

         return (T) toObject(source, documentType);
      } catch (Exception e) {
         throw new AppBaseRuntimeException(e, "global.jaxb.error.xml-to-object", ExceptionUtils.getRootCauseMessage(e));
      }
   }

   /**
    * XMLi vastu dokumendi tüüpi valideerimine. Valideerimise ebaõnnestumisel kutsutakse välja erind {@link JaxbValidatingException}
    * 
    * @param xml
    *           {@link String}
    * @param documentType
    *           {@link Class}
    * @throws JaxbValidatingException
    */
   public static <T> void validateXml(String xml, Class<T> documentType) {
      try {
         validate(new StringSource(xml), documentType);
      } catch (Exception e) {
         log.error("Error while validating XML against XSD", e);
         throw new JaxbValidatingException(e);
      }
   }

   private static void validate(Source xmlSource, Class<?> objectType) throws SAXException, IOException, JAXBException,
         TransformerFactoryConfigurationError, TransformerException {

      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      // TODO Margus - XSD peaksid olema eelnevalt genereeritud. Hetkel on
      // lahendatud nii, et esimesel korral genereeritakse ning hiljem
      // kasutatakse juba olemasolevat
      Schema schema = sf.newSchema(getOrCreateSchemasAsFile(objectType).iterator().next().getFile());
      schema.newValidator().validate(xmlSource);
   }
}
