package com.nortal.spring.cw.jsp.web.portal.content;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.nortal.spring.cw.core.exception.AppBaseRuntimeException;
import com.nortal.spring.cw.core.exception.UserAccessRestrictedException;
import com.nortal.spring.cw.core.i18n.model.MessageType;
import com.nortal.spring.cw.core.security.UserSecurityUtil;
import com.nortal.spring.cw.core.security.annotation.Restricted;
import com.nortal.spring.cw.core.support.user.CwUserRequestInfo;
import com.nortal.spring.cw.core.support.user.CwUserSessionInfo;
import com.nortal.spring.cw.core.web.annotation.component.EventHandler;
import com.nortal.spring.cw.core.web.component.composite.ControllerComponent;
import com.nortal.spring.cw.core.web.component.element.EventElement;
import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.helper.UrlHelper;
import com.nortal.spring.cw.core.web.holder.Message;
import com.nortal.spring.cw.core.web.json.JsonResponse;
import com.nortal.spring.cw.core.web.util.BeanUtil;
import com.nortal.spring.cw.core.web.util.ElementUtil;
import com.nortal.spring.cw.core.web.util.RequestUtil;
import com.nortal.spring.cw.jsp.web.portal.menu.MenuComponent;

/**
 * @author Margus Hanni
 */
@Slf4j
public abstract class AbstractModelPageController extends BasePageController implements Serializable {
   private static final long serialVersionUID = 1L;

   public static final String MODEL_COMP = ControllerComponent.MODEL_COMP;

   public static final String SUBMIT_CLIENT_CHANGE = "submitClientChange";
   public static final String SUBMIT_CLIENT_CHANGE_ID = "submitClientChangeId";

   @Autowired
   private CwUserRequestInfo userRequestInfo;
   @Autowired
   private CwUserSessionInfo userSessionInfo;

   @RequestMapping(value = "/**")
   public ModelAndView doView(Model model, @ModelAttribute(MODEL_COMP) ControllerComponent cc, HttpServletRequest request) {

      // Parse controller parameters
      initControllerParametersAndStoreController(cc, request);
      model.addAttribute("model", this);

      String redirect = initView(cc);

      if (StringUtils.isNotEmpty(redirect)) {
         return new ModelAndView(redirect);
      }
      return getControllerView();
   }

   protected String initView(ControllerComponent cc) {
      MenuComponent menuCode = this.getClass().getAnnotation(MenuComponent.class);
      if (menuCode != null) {
         userRequestInfo.setActiveMainMenu(menuCode.menuItem().name());
      }

      if (cc.needToInitComponent()) {
         // TODO Margus - annotatsiooni põhine kontroll tuleb tõsta voterisse
         if (UserSecurityUtil.checkClassPrivileges(this.getClass())) {
            initControllerComponent(cc);
            cc.initComponent();
         } else {
            throw new UserAccessRestrictedException();
         }
      }

      cc.initComponentsIfNeeded();

      return cc.getRedirectUrl();
   }

   /**
    * Päringu parameetrite kontrolleri külge lisamine ning kontrolleri sessiooni objekti salvestamine. Täiendavalt kontrollitakse kas
    * kasutajal on õigus pöörduda avatavale lehele, kontrollides privileege mis on defineeritud annotatsioonis {@link Restricted}
    * 
    * @param cc
    *           {@link ControllerComponent}
    * @param request
    *           {@link HttpServletRequest}
    */
   private void initControllerParametersAndStoreController(ControllerComponent cc, HttpServletRequest request) {
      if (UserSecurityUtil.checkClassPrivileges(this.getClass())) {
         userSessionInfo.setActiveControllerComponent(userRequestInfo.getApplicationRequestKey(this.getClass()), cc);
         for (Entry<String, String> entry : RequestUtil.collectControllerParameters(this.getClass(), request).entrySet()) {
            cc.addParameter(entry.getKey(), entry.getValue());
         }
      }
   }

   /**
    * Komponendi initsialiseerimine
    * 
    * @return
    */
   @ModelAttribute(MODEL_COMP)
   public ControllerComponent getControllerComponent(Model model) {

      ControllerComponent cc;
      if (userSessionInfo.existsActiveControllerComponent(userRequestInfo.getApplicationRequestKey(this.getClass()))) {
         cc = userSessionInfo.getActiveControllerComponent(userRequestInfo.getApplicationRequestKey(this.getClass()));
      } else {
         cc = new ControllerComponent();
         cc.init(this, MODEL_COMP);
      }

      cc.setModel(model);

      userRequestInfo.setControllerComponent(cc);

      return cc;
   }

   @RequestMapping(value = "/**", method = RequestMethod.POST, params = { RequestUtil.PARAM_EPM_EVT }, headers = "Accept=application/json", produces = RequestUtil.HEADER_JSON_UTF8)
   @ResponseBody
   public JsonResponse submitEpmEventJson(@ModelAttribute(MODEL_COMP) ControllerComponent cc, @RequestParam Map<String, String> params,
         Model model, HttpServletRequest request, HttpServletResponse response) {

      JsonResponse jsonResponse;

      BindingResult bindingResult = cc.getBindingResult();

      Object data = submitEpmEvent(cc, params, model, request, response);

      if (data instanceof JsonResponse) {
         jsonResponse = (JsonResponse) data;
      } else {
         jsonResponse = new JsonResponse();
      }

      jsonResponse.setSuccess(!bindingResult.hasErrors());

      if (bindingResult.hasErrors()) {
         Map<String, String> errors = new LinkedHashMap<String, String>();
         for (FieldError error : bindingResult.getFieldErrors()) {
            String trans = HtmlUtils.htmlEscape(translate(error.getCode(), error.getArguments()));
            errors.put(((FormElement) error.getRejectedValue()).getDisplayId(), trans);
         }
         jsonResponse.setFieldErrors(errors);
      }

      jsonResponse.setMessages(cc.getMessagesAndClear());

      return jsonResponse;
   }

   @RequestMapping(value = "/**", method = RequestMethod.POST, params = { RequestUtil.PARAM_EPM_EVT })
   public Object submitEpmEvent(@ModelAttribute(MODEL_COMP) ControllerComponent cc, @RequestParam Map<String, String> params, Model model,
         HttpServletRequest request, HttpServletResponse response) {

      // lisame juurde ka päringu parameetrid, neid võib meil kuskil kõhjus vaja
      // minna
      userRequestInfo.setRequestParameters(params);

      // Parse controller parameters
      initControllerParametersAndStoreController(cc, request);

      BindingResult bindingResult = cc.getBindingResult();

      if (!cc.isInitialized()) {
         // kui kontroller ei ole initsialiseeritud kuid tegu on POST päringuga
         // siis võib meil olla käsil olukord kus kasutaja vajutas
         // lehitseja tagasi nuppu ja siis F5. Teeme uuesti suunamise, kuna
         // meile ei pruugi meeldida eelmise päringu POST parameetrid.
         return RequestUtil.redirect(userRequestInfo.getApplicationPath());
      }

      try {
         // First we find target Component
         String targetComponentDisplayId = params.get(RequestUtil.PARAM_EPM_EVT_TARGET);
         String targetComponent = ElementUtil.convertDisplayIdToPath(targetComponentDisplayId);
         Object target = null;
         if (StringUtils.isEmpty(targetComponent)) {
            target = cc;
         } else {
            target = BeanUtil.getValueByPath(cc, targetComponent);
         }

         String epmEvent = params.get(RequestUtil.PARAM_EPM_EVT);
         Class<?> targetClass = target.getClass();

         // Find the target event
         Method m = BeanUtil.getEventHandlerMethod(targetClass, epmEvent);

         // kui meetod puudub, siis vaatame kas meil on olemas fallback
         if (m == null) {
            // kui on sündmuse element, siis kontrollime ka vanema üle, kuna rada ei tõlgenda alati elemendi omanikku
            if (EventElement.class.isAssignableFrom(targetClass)) {
               target = ((EventElement) target).getParent();
               targetClass = target.getClass();
               m = BeanUtil.getEventHandlerMethod(targetClass, epmEvent);
            }

            // kui jäi leidmata siis otsime otse kontrollerist
            if (m == null) {
               targetClass = this.getClass();
               target = this;
               m = BeanUtil.getEventHandlerMethod(targetClass, epmEvent);
            }
         }

         Assert.isTrue(m != null, String.format("Event '%s' does not exist", epmEvent));

         boolean isDownloadStream = m.getAnnotation(EventHandler.class).downloadStream();

         if (m.getReturnType().equals(String.class)) {
            // kui meetod tagastab stringi siis on tegu suunamisega
            String toPage = (String) BeanUtil.invokeMethod(m, target, cc, params, bindingResult, model, request, response);
            // kui toPage ei ole tühi, siis ainult suuname ja ei initsialiseeri viewd uuesti, seda ei tohiks olla vaja, kuna me liigume
            // praegusest vaatest ära
            if (StringUtils.isEmpty(toPage)) {
               toPage = initView(cc);
            }
            if (cc.getBindingResult().hasErrors()) {
               // kui esines vigu, siis jääme alati aktiivsele lehele
               return toActivePageWithoutParams(cc);
            }

            String goTo = StringUtils.isEmpty(toPage) ? userRequestInfo.getApplicationPath() : toPage;
            // Teeme redirecti et pääseda POST submit temaatikast
            return RequestUtil.redirect(goTo);
         } else if (m.getReturnType().equals(JsonResponse.class)) {
            Object jsonResponse = BeanUtil.invokeMethod(m, target, cc, params, bindingResult, model, request, response);
            if (jsonResponse != null) {
               return jsonResponse;
            }
         } else {
            BeanUtil.invokeMethod(m, target, cc, params, bindingResult, model, request, response);
            if (isDownloadStream) {
               return null;
            }
         }
      } catch (Exception exception) {
         log.error("ERROR:", exception);
         AppBaseRuntimeException re = findRuntimeBaseException(exception);
         if (re != null) {
            cc.addMessage(re.getMessageCode(), re.getMessageArgs());
         } else {
            cc.addMessage(MessageType.ERROR, ExceptionUtils.getRootCauseMessage(exception), null);
         }
      }

      String redirect = initView(cc);

      if (StringUtils.isEmpty(redirect) && !bindingResult.hasErrors()) {
         return RequestUtil.redirect(userRequestInfo.getApplicationPath());
      }

      return StringUtils.isEmpty(redirect) ? toActivePageWithoutParams(cc) : redirect;
   }

   private AppBaseRuntimeException findRuntimeBaseException(Exception e) {
      if (e == null || e instanceof AppBaseRuntimeException) {
         return (AppBaseRuntimeException) e;
      }
      Throwable current = e;
      do {
         current = current.getCause();
         if (current instanceof AppBaseRuntimeException) {
            return (AppBaseRuntimeException) current;
         }
      } while (current != null && current.getCause() != null);
      return null;
   }

   public String getModelCompName() {
      return MODEL_COMP;
   }

   /**
    * Meetod tagastab aktiivse kontrolleri {@link ControllerComponent}
    * 
    * @return
    */
   protected ControllerComponent getControllerComponent() {
      return userSessionInfo.getActiveControllerComponent(userRequestInfo.getApplicationRequestKey(this.getClass()));
   }

   protected String toActivePage(ControllerComponent cc) {
      String path = toActivePageWithoutParams(cc);
      Map<String, String> parameters = cc.getParameters();
      String param = StringUtils.join(parameters.values(), UrlHelper.URL_SEPARATOR);
      if (StringUtils.isNotEmpty(param)) {
         path += UrlHelper.URL_SEPARATOR + param;
      }

      return path;
   }

   protected String toActivePageWithoutParams(ControllerComponent cc) {
      Model model = cc.getModel();
      model.addAttribute("model", this);
      return getControllerView().getViewName();
   }

   protected void addComponentErrorMessage(String compId, String code, Object... args) {
      BindingResult result = getBindingResult();
      if (result == null) {
         throw new IllegalStateException("Cannot add error message for component: " + compId);
      }
      result.addError(new ObjectError(compId, new String[] { code }, args, null));
   }

   protected BindingResult getBindingResult() {
      return userRequestInfo.getBindingResult(ControllerComponent.class);
   }

   protected ModelAndView getControllerView() {
      return new ModelAndView(RequestUtil.controllerPath(this.getClass()));
   }

   protected void addControllerMessage(String code, Object... args) {
      getControllerComponent().addMessage(code, args);
   }

   public Map<String, Set<Message>> getReturnMessages() {
      return getControllerComponent().getMessagesAndClear();
   }

   /**
    * Elemendi järgmise tabindexi tagastamine. Kasutatakse sisendväljade kui ka nuppude järjestamiseks
    * 
    * @return {@link Long}
    */
   public long getNextElementTabindex() {
      return userRequestInfo.getNextElementTabindex();
   }

   // ================ Abstract methods ==============================

   /**
    * Ühiskomponendi initsialiseerimine
    * 
    * @param compositeComp
    */
   protected abstract void initControllerComponent(ControllerComponent cc);
}
