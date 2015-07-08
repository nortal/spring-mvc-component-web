package com.nortal.spring.cw.core.cache;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

/**
 * Imported and refactored from EMPIS project
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 15.02.2013
 */
@Slf4j
@Component("cacheTrigger")
public class CacheTriggerImpl implements CacheTrigger {

   private CacheTriggerListener listener;

   @Override
   public void setListener(CacheTriggerListener listener) {
      this.listener = listener;
   }

   @Override
   public void reloadAll() {
      listener.reloadAll();
   }

   @Override
   public void invoke() {
      log.debug("Checking for cache updates..." + listener);
      if (listener != null) {
         listener.reload();
      }
   }
}
