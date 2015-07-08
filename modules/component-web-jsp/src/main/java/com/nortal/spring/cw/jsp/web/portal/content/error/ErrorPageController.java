package com.nortal.spring.cw.jsp.web.portal.content.error;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nortal.spring.cw.core.exception.AppBaseRuntimeException;
import com.nortal.spring.cw.core.i18n.SupportedLanguages;
import com.nortal.spring.cw.core.i18n.model.Lang;
import com.nortal.spring.cw.core.support.user.CwPropertyKey;
import com.nortal.spring.cw.core.web.holder.Message;
import com.nortal.spring.cw.core.web.json.JsonResponse;
import com.nortal.spring.cw.core.web.util.RequestUtil;
import com.nortal.spring.cw.jsp.web.portal.content.BasePageController;

/**
 * @author Margus Hanni
 * 
 */
@Slf4j
@Controller
@RequestMapping("/error/{code}")
public class ErrorPageController extends BasePageController {

   @Autowired
   private SupportedLanguages supportedLanguages;

   private static final long serialVersionUID = 1L;
   private static final Map<String, String> servletErrors = new LinkedHashMap<>();
   private static final String lineSeparator = System.getProperty("line.separator");

   @Value("#{new Boolean('${" + CwPropertyKey.ERROR_PAGE_SHOW_ERROR_STACK + "}')}")
   private Boolean showErrorStack;

   static {
      servletErrors.put("Request URI", "javax.servlet.error.request_uri");
      servletErrors.put("Status code", "javax.servlet.error.status_code");
      servletErrors.put("Message", "javax.servlet.error.message");
      servletErrors.put("Exception type", "javax.servlet.error.exception_type");
      servletErrors.put("Servlet name", "javax.servlet.error.servlet_name");
   }

   @RequestMapping(headers = RequestUtil.HEADER_ACCEPT_JSON, produces = RequestUtil.HEADER_JSON_UTF8)
   @ResponseBody
   @ExceptionHandler(value = Exception.class)
   public JsonResponse getJsonResponseByHeader(HttpServletRequest request, @PathVariable int code, Exception ex) {
      return getJsonResponse(request, code, ex);
   }

   @RequestMapping(params = { RequestUtil.PARAM_EPM_EVT_AJAX }, produces = RequestUtil.HEADER_JSON_UTF8)
   @ResponseBody
   @ExceptionHandler(value = Exception.class)
   public String getJsonResponseByParam(HttpServletRequest request, @PathVariable int code, ModelAndView modelAndView, Exception ex)
         throws JsonProcessingException {
      return new ObjectMapper().writeValueAsString(getJsonResponse(request, code, ex));
   }

   @RequestMapping
   @ExceptionHandler(value = Exception.class)
   public ModelAndView getView(HttpServletRequest request, @PathVariable int code, Exception ex) {
      logError(request, ex);
      ModelMap map = new ModelMap();

      Throwable rootCause = getThrowable(request);
      map.put("messages", collectMessagesFromCauseOrCode(code, rootCause));
      map.put("showErrorStack", showErrorStack);

      if (showErrorStack) {
         map.put("exceptionString", buildDetailedException(request, ex, rootCause));
      }
      map.put("model", this);

      return new ModelAndView("/error/" + code, map);
   }

   private String buildDetailedException(HttpServletRequest request, Exception ex, Throwable rootCause) {
      StringBuilder exception = new StringBuilder(getErrorHeader(request));
      exception.append(lineSeparator).append(ExceptionUtils.getStackTrace(rootCause));

      if (ex != null) {
         exception.append(lineSeparator).append("Original:").append(lineSeparator);
         exception.append(ExceptionUtils.getStackTrace(ex));
      }

      exception.append(lineSeparator).append("Parameter types:");
      Map<?, ?> parms = request.getParameterMap();
      for (Iterator<?> iterator = parms.entrySet().iterator(); iterator.hasNext();) {
         Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iterator.next();
         exception.append(lineSeparator).append(entry.getKey()).append("=").append(entry.getValue().getClass());
      }
      return exception.toString();
   }

   private Map<String, Set<Message>> collectMessagesFromCauseOrCode(int code, Throwable rootCause) {
      Map<String, Set<Message>> messages;
      if (rootCause instanceof AppBaseRuntimeException) {
         AppBaseRuntimeException appEx = (AppBaseRuntimeException) rootCause;
         messages = collectMessages(appEx.getMessageCode(), appEx.getMessageArgs());
      } else {
         messages = collectMessages(code);
      }
      return messages;
   }

   private void logError(HttpServletRequest request, Exception ex) {
      log.error("Error on loading page: " + getErrorHeader(request), getThrowable(request));
      if (ex != null) {
         log.error("Additional:", ex);
      }
   }

   private Throwable getThrowable(HttpServletRequest request) {
      return (Throwable) request.getAttribute("javax.servlet.error.exception");
   }

   private String getErrorHeader(HttpServletRequest request) {
      StringBuilder collectErrors = new StringBuilder(lineSeparator);

      for (Entry<String, String> error : servletErrors.entrySet()) {
         collectErrors.append(error.getKey()).append(" : ");
         collectErrors.append(request.getAttribute(error.getValue())).append(lineSeparator);
      }

      return collectErrors.toString();
   }

   private Map<String, Set<Message>> collectMessages(int code) {
      return collectMessages("global.page.error." + code);
   }

   private Map<String, Set<Message>> collectMessages(String msgeCode, Object... arguments) {
      Map<String, Set<Message>> messageMap = new HashMap<String, Set<Message>>();

      for (Lang lang : supportedLanguages.getSupportedLanguages()) {
         Message msg = Message.createMessage(msgeCode, lang, arguments);
         Set<Message> messages = messageMap.get(msg.getMessageType());
         if (messages == null) {
            messages = new HashSet<>();
            messages.add(msg);
         }

         messageMap.put(msg.getMessageType(), messages);
      }
      return messageMap;
   }

   private JsonResponse getJsonResponse(HttpServletRequest request, int code, Exception ex) {
      logError(request, ex);
      JsonResponse jsonResponse = new JsonResponse();

      jsonResponse.setMessages(collectMessagesFromCauseOrCode(code, getThrowable(request)));
      jsonResponse.setSuccess(false);

      String targetComponent = request.getParameter(RequestUtil.PARAM_EPM_EVT_TARGET);

      /* Lisame vajadusel JSONisse komponendi põhised teated */
      if (StringUtils.isNotEmpty(targetComponent)) {
         List<String> errorMsg = new ArrayList<>();
         for (Set<Message> messages : jsonResponse.getMessages().values()) {
            for (Message message : messages) {
               if (message.isActiveLanguageMessage()) {
                  errorMsg.add(message.getMessage());
               }
            }
         }
         Map<String, String> errors = new HashMap<>();
         errors.put(targetComponent, StringUtils.join(errorMsg, "; "));
         jsonResponse.setFieldErrors(errors);
      }

      return jsonResponse;
   }
}
