package com.nortal.spring.cw.core.xml.jaxb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.dom.DOMResult;

/**
 * @author Margus Hanni
 * 
 */
public class FormSchemaOutputResolver extends SchemaOutputResolver {

   private final List<DOMResult> schemas = new ArrayList<>();

   public DOMResult createOutput(String namespaceURI, String suggestedFileName) throws IOException {
      DOMResult domResult = new DOMResult();
      schemas.add(domResult);
      domResult.setSystemId(suggestedFileName);
      return domResult;
   }

   public List<DOMResult> getSchemas() {
      return schemas;
   }
}
