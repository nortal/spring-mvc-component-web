package com.nortal.spring.cw.core.xml.jaxb;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Source;

import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.oxm.mime.MimeContainer;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 22.10.2013
 */
public class EpmJaxb2Marshaller extends Jaxb2Marshaller {
   public static final String CID = "cid:";

   @Override
   public void marshal(Object graph, Result result, MimeContainer mimeContainer) throws XmlMappingException {
      try {
         Marshaller marshaller = createMarshaller();

         if (mimeContainer != null) {
            marshaller.setAttachmentMarshaller(new EpmAttachmentMarshaller(mimeContainer));
         }
         marshaller.marshal(graph, result);
      } catch (JAXBException ex) {
         throw convertJaxbException(ex);
      }
   }

   @Override
   public Object unmarshal(Source source, MimeContainer mimeContainer) throws XmlMappingException {
      try {
         Unmarshaller unmarshaller = super.createUnmarshaller();

         if (mimeContainer != null) {
            unmarshaller.setAttachmentUnmarshaller(new EpmAttachmentUnmarshaller(mimeContainer));
         }
         return unmarshaller.unmarshal(source);
      } catch (JAXBException e) {
         throw convertJaxbException(e);
      }
   }

}
