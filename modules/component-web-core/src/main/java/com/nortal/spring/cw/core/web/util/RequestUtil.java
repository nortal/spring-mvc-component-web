package com.nortal.spring.cw.core.web.util;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;

import com.nortal.spring.cw.core.i18n.SupportedLanguages;
import com.nortal.spring.cw.core.i18n.model.Lang;
import com.nortal.spring.cw.core.support.user.CwUserRequestInfo;
import com.nortal.spring.cw.core.web.annotation.controller.ControllerVariable;
import com.nortal.spring.cw.core.web.component.composite.ControllerComponent;
import com.nortal.spring.cw.core.web.helper.UrlHelper;

/**
 * Utiliit, millesse on koondatud erinevate päringute teostamise abivahendid
 * 
 * @author Margus Hanni
 * @since 18.04.2013
 */
public final class RequestUtil {

   public static final String PARAM_EPM_EVT = "epm_evt";
   public static final String PARAM_EPM_EVT_AJAX = "epm_evt_ajax";
   public static final String PARAM_EPM_EVT_TARGET = "epm_evt_target";
   public static final String PARAM_EPM_EVT_PARAM = "epm_evt_param";
   public static final String REDIRECT_PREFIX = "redirect:";

   public final static String MODEL_COMP = "comp";

   public static final String HEADER_JSON = "application/json";
   public static final String HEADER_JSON_UTF8 = HEADER_JSON + ";charset=UTF-8";
   public static final String HEADER_ACCEPT_JSON = "Accept=" + HEADER_JSON;

   /**
    * Kontrollerkomponendi nimi. Antud meetodit kasutatakse JSPdes
    * 
    * @return {@link String}
    */
   public static String getModalCompName() {
      return MODEL_COMP;
   }

   /**
    * Meetod tagastab kasutaja päringuobjekti {@link CwUserRequestInfo}
    * 
    * @return {@link CwUserRequestInfo}
    */
   public static CwUserRequestInfo getUserRequestInfo() {
      return BeanUtil.getBean(CwUserRequestInfo.class);
   }

   /**
    * Meetod tagastab aktiivse portaali keele {@link Lang}, mis leitakse {@link CwUserRequestInfo#getActiveLanguage()} vahendusel
    * 
    * @return {@link Lang} Aktiivne keel
    */
   public static Lang getActiveLang() {
      return getUserRequestInfo().getActiveLanguage();
   }

   /**
    * Meetod tagastab rakenduse vaikekeele {@link Lang}, mis leitakse {@link SupportedLanguages#getDefaultLanguage()} vahendusel
    * 
    * @return {@link Lang} Vaikekeel
    */
   public static Lang getDefaultLanguage() {
      return BeanUtil.getBean(SupportedLanguages.class).getDefaultLanguage();
   }

   /**
    * Päringu suunamine teise kontrollerisse
    * 
    * @param controller
    *           Kontrolleri klass kuhu päring suunatakse
    * @param initParameters
    *           nimekiri kontrolleri init-parameetritest. Sisalduvad lõplikus URLis. Kõik parameetrid viiakse tekstilisele kujule, mis
    *           tähendab seda et kutsutakse välja {@link Object#toString()}
    * @return redirect direktiiv
    */
   public static String redirect(Class<?> controller, Object... initParameters) {
      return redirect(controllerPath(controller, initParameters));
   }

   /**
    * Meetod tagastab aadressi, mis sisaldab kontrollisse pöördumise punkti, mis on defineeritud annotatsioonis {@link RequestMapping} ning
    * kui on määratud mõni GET parameeter (initParameters) lisatakse parameetrid aadressi lõppu. Kui kontrolleris on määratud mitu
    * pöördepunkti (mappingut) siis leitakse nende seast kõige esimene ning kasutatakse seda.
    * 
    * @param controller
    *           Kontrolleri klass kuhu päring suunatakse
    * @param initParameters
    *           nimekiri kontrolleri init-parameetritest. Sisalduvad lõplikus URLis. Kõik parameetrid viiakse tekstilisele kujule, mis
    *           tähendab seda et kutsutakse välja {@link Object#toString()}
    * @return redirect direktiiv
    */
   public static String controllerPath(Class<?> controller, Object... initParameters) {
      String path = controller.getAnnotation(RequestMapping.class).value()[0];
      if (ArrayUtils.isNotEmpty(initParameters) && !StringUtils.endsWith(path, UrlHelper.URL_SEPARATOR)) {
         path += UrlHelper.URL_SEPARATOR;
      }
      path = StringUtils.remove(path, "*") + StringUtils.join(initParameters, UrlHelper.URL_SEPARATOR);
      return path;
   }

   /**
    * Meetod tagastab täispika aadressi koos serveri nimega, mis lisaks sisaldab kontrollisse pöördumise punkti, mis on defineeritud
    * annotatsioonis {@link RequestMapping} ning kui on määratud mõni GET parameeter (initParameters) lisatakse parameetrid aadressi lõppu.
    * Kui kontrolleris on määratud mitu pöördepunkti (mappingut) siis leitakse nende seast kõige esimene ning kasutatakse seda.
    * 
    * @param controller
    *           Kontrolleri klass kuhu päring suunatakse
    * @param initParameters
    *           nimekiri kontrolleri init-parameetritest. Sisalduvad lõplikus URLis. Kõik parameetrid viiakse tekstilisele kujule, mis
    *           tähendab seda et kutsutakse välja {@link Object#toString()}
    * @return redirect direktiiv
    */
   public static String controllerFullPath(Class<?> controller, Object... initParameters) {
      String path = controllerPath(controller, initParameters);
      CwUserRequestInfo requestInfo = getUserRequestInfo();
      return requestInfo.getServerUrl() + requestInfo.getContextPath()
            + UrlHelper.URL_SEPARATOR.concat(getActiveLang().getCode().toLowerCase()) + path;
   }

   /**
    * Meetod koostab päringu edasi suunamise aadressi, lisades kõige ette <code>redirect:</code>. Kui <code>path</code> on tühi või null,
    * tagastatakse null. Täiendavalt lisatakse juurde päringu keel
    * 
    * @param path
    *           {@link String} päringu aadress
    * @return {@link String} redirect:/lang/path
    */
   public static String redirect(String path) {

      if (StringUtils.isEmpty(path)) {
         return null;
      }

      if (StringUtils.startsWith(path, REDIRECT_PREFIX)) {
         return path;
      }

      String fullPath = path;

      if (!fullPath.startsWith(UrlHelper.URL_SEPARATOR)) {
         fullPath = UrlHelper.URL_SEPARATOR + fullPath;
      }

      return REDIRECT_PREFIX + UrlHelper.URL_SEPARATOR.concat(getActiveLang().getCode().toLowerCase()) + fullPath;
   }

   /**
    * Kas on tegu AJAX päringuga. Kontrollitakse kas päringu sisaltab AJAX päist või eksisteerib päringuparameeter "epm_evt_ajax".
    * Kasutatakse JSP failides
    * 
    * @param request
    * @return
    */
   public static boolean getIsAjaxRequest(HttpServletRequest request) {
      Enumeration<String> headerNames = request.getHeaders("accept");
      while (headerNames.hasMoreElements()) {
         String header = headerNames.nextElement();
         if (StringUtils.endsWithIgnoreCase(header, "application/json")) {
            return true;
         }
      }

      return isAjaxRequest(request.getParameterMap());
   }

   /**
    * Kas on tegu AJAX päringuga. Kontrollitakse eksisteerib parameeter "epm_evt_ajax".
    * 
    * @param params
    * @return
    */
   public static boolean isAjaxRequest(Map<String, String[]> params) {
      String[] value = params.get(PARAM_EPM_EVT_AJAX);
      return BooleanUtils.toBoolean(value[0]);
   }

   /**
    * Meetod tagastab pöördumise GET parameetrid. GET parameetrid peavad olema defineeritud kontrolleri klassis annotatsiooni
    * {@link ControllerVariable} vahendusel
    * 
    * @param controllerClass
    *           {@link Class}
    * @param request
    *           {@link HttpServletRequest}
    * @return
    */
   public static final Map<String, String> collectControllerParameters(Class<?> controllerClass, HttpServletRequest request) {
      String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
      Map<String, String> parameters = new LinkedHashMap<>();
      // 1. check if we have corresponding interface
      ControllerVariable cv = controllerClass.getAnnotation(ControllerVariable.class);
      if (cv == null || cv.names().length == 0) {
         return parameters;
      }
      // 2. Split the remaining path, iterate over ControllerVariable values and
      // assign corresponding initParameters
      // If the path contains more elements than configured in
      // ControllerVariable, ignore them
      RequestMapping rm = controllerClass.getAnnotation(RequestMapping.class);
      path = path.replaceFirst(rm.value()[0], "");
      while (path.startsWith(UrlHelper.URL_SEPARATOR)) {
         path = path.substring(1);
      }
      String[] splitPath = path.split("/");
      for (int i = 0; i < Math.min(cv.names().length, splitPath.length); i++) {
         parameters.put(cv.names()[i], splitPath[i]);
      }

      return parameters;
   }

   /**
    * Meetod tagastab aktiivse lehe täispika aadressi kujul http(s)://server[:port]/....
    * 
    * @return {@link String} Aktiivse lehe aadress
    */
   public static String getRequestPath() {
      CwUserRequestInfo requestInfo = getUserRequestInfo();
      return requestInfo.getServerUrl() + requestInfo.getContextPath() + UrlHelper.URL_SEPARATOR
            + requestInfo.getActiveLanguage().getCode().toLowerCase() + requestInfo.getApplicationPath();
   }

   /**
    * Meetod eemaldab sisendist ohtlikud sümbolid, mis võimaldavad teostada XSS rünnakuid.
    * 
    * @param value
    *           {@link String} Töödeldav tekstiline väärtus
    * @return {@link String} Töödeldud tekstiline väärtus
    */
   public static String cleanXSS(String value) {

      if (StringUtils.isEmpty(value)) {
         return value;
      }

      String rValue = value;

      rValue = rValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
      rValue = rValue.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
      // rValue = rValue.replaceAll("'", "& #39;");

      rValue = rValue.replaceAll("eval\\((.*)\\)", "");
      rValue = rValue.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");

      rValue = rValue.replaceAll("(?i)<script.*?>.*?<script.*?>", "");
      rValue = rValue.replaceAll("(?i)<script.*?>.*?</script.*?>", "");
      rValue = rValue.replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "");
      rValue = rValue.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "");

      return rValue;
   }

   /**
    * Meetod tagastav päringuobjektis {@link CwUserRequestInfo} asuva {@link ControllerComponent}
    * 
    * @return {@link ControllerComponent}
    */
   public static ControllerComponent getControllerComponent() {
      return getUserRequestInfo().getControllerComponent();
   }

   public static boolean isFirefox(HttpServletRequest request) {
      if (isIE(request)) {
         return false;
      }
      String useragent = getUseragent(request);
      return useragent.contains("Firefox") || useragent.contains("Mozilla");
   }

   public static boolean isIE(HttpServletRequest request) {
      return getUseragent(request).contains("MSIE");
   }

   public static boolean isIE6(HttpServletRequest request) {
      return getUseragent(request).contains("MSIE 6");
   }

   public static boolean isIE7(HttpServletRequest request) {
      String useragent = getUseragent(request);
      return useragent.contains("MSIE 7") && !useragent.contains("Trident/4");
   }

   public static boolean isIE8(HttpServletRequest request) {
      String useragent = getUseragent(request);
      return useragent.contains("MSIE 8") || (useragent.contains("MSIE 7") && useragent.contains("Trident/4"));
   }

   private static String getUseragent(HttpServletRequest request) {
      return request.getHeader("User-Agent");
   }
}
