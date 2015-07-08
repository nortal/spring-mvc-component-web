package com.nortal.spring.cw.core.xml.jaxb;

import java.util.UUID;

import javax.activation.DataHandler;
import javax.xml.bind.attachment.AttachmentMarshaller;

import org.springframework.oxm.mime.MimeContainer;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 22.10.2013
 */
public class EpmAttachmentMarshaller extends AttachmentMarshaller {

   private final MimeContainer mimeContainer;

   public EpmAttachmentMarshaller(MimeContainer mimeContainer) {
      this.mimeContainer = mimeContainer;
   }

   @Override
   public String addMtomAttachment(byte[] data, int offset, int length, String mimeType, String elementNamespace, String elementLocalName) {
      throw new UnsupportedOperationException("EpmAttachmentMarshaller.addMtomAttachment: is unsupported");
   }

   @Override
   public String addMtomAttachment(DataHandler dataHandler, String elementNamespace, String elementLocalName) {
      throw new UnsupportedOperationException("EpmAttachmentMarshaller.addMtomAttachment: is unsupported");
   }

   @Override
   public String addSwaRefAttachment(DataHandler dataHandler) {
      String contentId = UUID.randomUUID().toString();
      this.mimeContainer.addAttachment("<" + contentId + ">", dataHandler);
      return EpmJaxb2Marshaller.CID + contentId;
   }

   @Override
   public boolean isXOPPackage() {
      return false;
   }
}