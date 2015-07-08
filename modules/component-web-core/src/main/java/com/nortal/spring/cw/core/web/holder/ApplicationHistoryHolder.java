package com.nortal.spring.cw.core.web.holder;

import java.io.Serializable;
import java.util.LinkedList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Alrik Peets
 */
@Component
public class ApplicationHistoryHolder implements Serializable {
   private static final long serialVersionUID = 1L;

   private static final Long MAX_PATH_HISTORY = 5L;

   private String activeApplicationPath;
   private LinkedList<String> prevApplicationPaths = new LinkedList<>();

   public void addPath(String applicationPath) {
      if (StringUtils.equals(activeApplicationPath, applicationPath)) {
         return;
      }

      if (StringUtils.isNotBlank(activeApplicationPath)) {
         prevApplicationPaths.addFirst(activeApplicationPath);
      }

      activeApplicationPath = applicationPath;

      if (prevApplicationPaths.size() > MAX_PATH_HISTORY) {
         prevApplicationPaths.removeLast();
      }
   }

   /**
    * Tagastab aktiivse pathi või NULL kui seda pole
    * 
    * @return
    */
   public String getActivePath() {
      return activeApplicationPath;
   }

   /**
    * Tagastab eelmise pathi või NULL kui seda pole
    * 
    * @return
    */
   public String peekPreviousPath() {
      return prevApplicationPaths.peekFirst();
   }

   /**
    * Tagastab eelmise pathi, eemaldades selle nimekirjast. Eelmine path sätitakse ka aktiivseks pathiks
    * 
    * @return
    */
   public String getPreviousPath() {
      String prevPath = prevApplicationPaths.pollFirst();
      activeApplicationPath = prevPath;
      return prevPath;
   }
}
