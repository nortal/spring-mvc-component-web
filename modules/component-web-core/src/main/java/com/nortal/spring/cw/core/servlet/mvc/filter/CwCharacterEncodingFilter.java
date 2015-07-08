package com.nortal.spring.cw.core.servlet.mvc.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.util.UrlPathHelper;

/**
 * Filter laiendab Springi veebifiltrit {@link CharacterEncodingFilter} lisades
 * juurde välistamise võimaluse, mis tähendab seda et teatud pöördumiste juures
 * määratud kodeeringu nõuet ei rakendata. Sellised pöördumised võivad olla
 * näiteks välisteenuste päärdumised, mis saadavad päringuid mõnes teises
 * kodeeringus kui on defineeritud
 * {@link CharacterEncodingFilter#setEncoding(String)}.
 * 
 * @author Margus Hanni
 * @since 08.10.2013
 */
public class CwCharacterEncodingFilter extends CharacterEncodingFilter {

   private final UrlPathHelper urlPathHelper = new UrlPathHelper();
   private static final List<String> EXCLUDE = new ArrayList<>();

   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
         throws ServletException, IOException {

      final String path = URLDecoder.decode(urlPathHelper.getPathWithinApplication(request), "UTF-8");

      if (CollectionUtils.exists(EXCLUDE, new Predicate() {
         @Override
         public boolean evaluate(Object object) {
            return path.matches((String) object);
         }
      })) {
         // käivitame tavalise filtri
         filterChain.doFilter(request, response);
         return;
      }

      super.doFilterInternal(request, response, filterChain);
   }

   /**
    * Välistavate aadressi maskide (regex) lisamine
    * 
    * @param exclude
    *           Välistatavad aadressid
    */
   public void setExclude(List<String> exclude) {
      EXCLUDE.addAll(exclude);
   }
}
