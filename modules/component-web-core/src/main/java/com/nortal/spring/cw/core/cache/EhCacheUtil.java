package com.nortal.spring.cw.core.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.lang3.Validate;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.nortal.spring.cw.core.cache.model.Cacheable;

/**
 * Imported and refactored from EMPIS project
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 15.02.2013
 */
// TODO: check methods
public abstract class EhCacheUtil {

   @SuppressWarnings("unchecked")
   public static <T> T get(Object key, Ehcache cache) {
      Validate.notNull(key);
      Element element = cache.get(key);
      return element == null ? null : (T) element.getObjectValue();
   }

   public static <K, V> List<V> getAll(Collection<K> keys, Ehcache cache) {
      return getAllMatching(keys, cache, null);
   }

   @SuppressWarnings("unchecked")
   public static <K, V> List<V> getAllMatching(Collection<K> keys, Ehcache cache, Predicate<V> predicate) {
      Validate.notEmpty(keys);
      List<V> out = new ArrayList<V>();
      for (Element element : cache.getAll(keys).values()) {
         V value = (V) element.getObjectValue();
         if (predicate == null || predicate.apply(value)) {
            out.add(value);
         }
      }
      return out;
   }

   @SuppressWarnings("unchecked")
   public static <T extends Cacheable> T find(Ehcache cache, Predicate<T> predicate) {
      for (Object key : cache.getKeys()) {
         T value = (T) cache.get(key).getObjectValue();

         if (predicate.apply(value)) {
            return value;
         }
      }

      return null;
   }

   public static <T extends Cacheable> List<T> findAll(Ehcache cache) {
      return findAllMatching(cache, null);
   }

   @SuppressWarnings("unchecked")
   public static <T extends Cacheable> List<T> findAllMatching(Ehcache cache, Predicate<T> predicate) {
      List<T> out = new ArrayList<T>();
      for (Object key : cache.getKeys()) {
         T value = (T) cache.get(key).getObjectValue();

         if (predicate == null || predicate.apply(value)) {
            out.add(value);
         }
      }
      return out;
   }

   @SuppressWarnings("unchecked")
   public static <T extends Cacheable, F> List<F> findAllMatchingAndTransform(Ehcache cache, Predicate<T> predicate,
         Function<T, F> transformer) {
      List<F> out = new ArrayList<F>();
      for (Object key : cache.getKeys()) {
         T value = (T) cache.get(key).getObjectValue();

         if (predicate.apply(value)) {
            out.add(transformer.apply(value));
         }
      }
      return out;
   }

   public static void put(Cacheable object, Ehcache cache) {
      cache.put(new Element(object.getCacheKey(), object));
   }

   public static <T extends Cacheable> void putAll(Collection<T> objects, Ehcache cache) {
      for (T cachedObject : objects) {
         put(cachedObject, cache);
      }
   }

   public static void remove(Collection<Object> keys, Ehcache cache) {
      for (Object key : keys) {
         cache.remove(key);
      }
   }
}
