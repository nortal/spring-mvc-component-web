package com.nortal.spring.cw.core.xml.jaxb;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.activation.DataHandler;
import javax.xml.bind.attachment.AttachmentUnmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.oxm.UnmarshallingFailureException;
import org.springframework.oxm.mime.MimeContainer;
import org.springframework.util.FileCopyUtils;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 22.10.2013
 */
public class EpmAttachmentUnmarshaller extends AttachmentUnmarshaller {
   private MimeContainer mimeContainer;

   public EpmAttachmentUnmarshaller(MimeContainer mimeContainer) {
      this.mimeContainer = mimeContainer;
   }

   @Override
   public DataHandler getAttachmentAsDataHandler(String cid) {
      if (StringUtils.startsWithIgnoreCase(cid, EpmJaxb2Marshaller.CID)) {
         cid = cid.substring(EpmJaxb2Marshaller.CID.length());
         try {
            cid = URLDecoder.decode(cid, "UTF-8");
         } catch (UnsupportedEncodingException e) {
            // ignore
         }
      }
      cid = "<" + cid + ">";
      return mimeContainer.getAttachment(cid);
   }

   @Override
   public byte[] getAttachmentAsByteArray(String cid) {
      try {
         DataHandler dataHandler = getAttachmentAsDataHandler(cid);
         return FileCopyUtils.copyToByteArray(dataHandler.getInputStream());
      } catch (IOException e) {
         throw new UnmarshallingFailureException("EpmAttachmentUnmarshaller.getAttachmentAsByteArray: Couldn't read attachment", e);
      }
   }
}
