package com.nortal.spring.cw.jsp.web.portal.resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.nortal.spring.cw.core.web.holder.Message;
import com.nortal.spring.cw.core.web.json.JsonResponse;
import com.nortal.spring.cw.jsp.web.portal.content.BasePageController;

@Controller
@RequestMapping("/resource")
public class ResourceController extends BasePageController {
   private static final long serialVersionUID = 1L;

   @RequestMapping("/sysparam/query")
   @ResponseBody
   public JsonResponse getSystemParameter(HttpServletRequest request) {
      JsonResponse response = new JsonResponse();

      // TODO: to we need that?
      response.addData("sys_param", 0);

      response.setSuccess(true);

      return response;
   }

   @RequestMapping("/message/translate")
   @ResponseBody
   public JsonResponse getMessage(HttpServletRequest request) {

      JsonResponse response = new JsonResponse();

      String code = StringUtils.trimToEmpty(request.getParameter("code"));
      if (StringUtils.isNotEmpty(code)) {
         Message message = Message.createMessage(code, getActiveLanguage());
         response.addMessage(message);
         response.setSuccess(true);
      } else {
         response.setSuccess(false);
      }

      return response;
   }

   @RequestMapping("/message/global-messages")
   @ResponseBody
   public JsonResponse getGlobalMessages(HttpServletRequest request) {

      JsonResponse response = new JsonResponse();

      List<String> keys = new ArrayList<String>();

      Set<String> collectedKeys = new HashSet<>();
      collectedKeys.addAll(collectMessageKeys(keys, "calendar"));
      collectedKeys.addAll(collectMessageKeys(keys, "multiselect"));
      collectedKeys.addAll(collectMessageKeys(keys, "modal"));
      collectedKeys.addAll(collectMessageKeys(keys, "global"));

      response.setSuccess(true);

      return response;
   }

   private List<String> collectMessageKeys(List<String> keys, final String findKeyPrefix) {
      return FluentIterable.from(keys).filter(new Predicate<String>() {
         public boolean apply(String input) {
            return StringUtils.startsWith(input, findKeyPrefix);
         }
      }).toList();
   }
}
