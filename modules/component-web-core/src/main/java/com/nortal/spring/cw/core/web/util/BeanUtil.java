package com.nortal.spring.cw.core.web.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.nortal.spring.cw.core.context.SpringApplicationContext;
import com.nortal.spring.cw.core.exception.AppBaseRuntimeException;
import com.nortal.spring.cw.core.i18n.model.Lang;
import com.nortal.spring.cw.core.support.user.CwUserRequestInfo;
import com.nortal.spring.cw.core.web.annotation.component.EventHandler;

/**
 * Antud klass tegeleb erinevate beani väärtuste mõjutamisega.<br>
 * Erinevate väärtuste leidmisel oskab klass arvestada ka aktiivse keelega {@link Lang}, leides leeke kasutaja päringu hoidlast
 * {@link CwUserRequestInfo}. Kui getter meetodil on argumendina nõutud ka keel siis antud meetodi välja kutsumisel lisatakse aktiivne keel
 * automaatselt argumendiks
 * 
 * @author Margus Hanni
 */
public final class BeanUtil {

   public static final String CONTROLLER_SUFFIX = "Controller";

   public static final String SET = "set";

   public static final String GET = "get";

   /**
    * The delimiter that separates the components of a nested reference.
    */
   public static final String NESTED_DELIM = ".";

   private BeanUtil() {
      super();
   }

   /**
    * Meetodi invokemine. Kui sisendiks on meetodi argumendid, lisatakse need vastavalt meetodi parameetriteks. Parameetrite järjekord ei
    * ole oluline, oluline on tüüp.
    * 
    * @param method
    *           invoketav meetod
    * @param object
    *           Objekt mille küljes antud meetod eksisteerib
    * @param args
    *           Meetodi parameetrid
    * @return
    * @throws IllegalAccessException
    * @throws IllegalArgumentException
    * @throws InvocationTargetException
    */
   public static Object invokeMethod(Method method, Object object, Object... args) throws IllegalAccessException, IllegalArgumentException,
         InvocationTargetException {

      if (args == null || args.length == 0) {
         return method.invoke(object);
      }

      List<Object> invokeArgs = new ArrayList<>();

      for (Class<?> typeClass : method.getParameterTypes()) {
         for (Object arg : args) {
            if (arg == null) {
               invokeArgs.add(null);
               break;
            } else if (typeClass.isAssignableFrom(arg.getClass())) {
               invokeArgs.add(arg);
               break;
            }
         }
      }

      return invokeArgs.isEmpty() ? method.invoke(object) : method.invoke(object, invokeArgs.toArray());
   }

   /**
    * Andmeolemi klassi sisese GET meetodi leidmine. Kui <b>elementFieldPath</b> on sisaldab objekti eraldajat (.), eeldatakse et tegemist
    * on klassi sees oleva teise andmeolemi klassiga, mis sisaldab endast otsitavat GET meetodit<br>
    * <br>
    * Väljakutsumine: #getMethodByFieldPath(Andmeolemi klass, "rada.rada.rada")
    * 
    * @param objectClass
    * @param elementFieldPath
    * @return {@link Method}
    */
   public static Method getMethodByFieldPath(Class<?> objectClass, String elementFieldPath) {

      Class<?> checkClass = objectClass;
      Method method = null;
      try {
         String[] pathSplit = StringUtils.split(elementFieldPath, NESTED_DELIM);
         int size = pathSplit.length;

         for (int i = 0; i < size; i++) {
            // Check for parameter
            String methodName = GET + StringUtils.capitalize(pathSplit[i]);
            if (i != size) {
               method = null;
               for (Method chekMethod : checkClass.getMethods()) {
                  if (StringUtils.equals(chekMethod.getName(), methodName)) {
                     method = chekMethod;
                     break;
                  }
               }
               if (method == null) {
                  throw new AppBaseRuntimeException("Class {0} has no method {1}", checkClass.getName(), methodName);
               }
               checkClass = method.getReturnType();
            }
         }

      } catch (IllegalArgumentException | SecurityException e) {
         throw new AppBaseRuntimeException(e);
      }
      return method;
   }

   /**
    * Meetod otsib andmeolemi klassis olevat meetodit millel on annotatsioon {@link EventHandler} ning mille väärtuseks on argumendina
    * määratud <b>eventName</b>
    * 
    * @param targetClass
    * @param eventName
    * @return
    */
   public static Method getEventHandlerMethod(Class<?> targetClass, String eventName) {
      Method method = null;

      for (Method m : targetClass.getMethods()) {
         if (m.isAnnotationPresent(EventHandler.class)) {
            if (StringUtils.equals(eventName, m.getAnnotation(EventHandler.class).eventName())) {
               method = m;
               break;
            }
         }
      }

      return method;
   }

   private static Object getValueByPathSegment(Class<?> targetClass, Object targetObject, String elementName) {
      try {
         String methodName = null;
         String parameter = null;
         // Check for parameter
         // TODO: Alrik - vaja veel pikemalt läbi mõelda
         // Pattern p = Pattern.compile("(.+)\\['?([^'|.]+)'?\\]");
         Pattern p = Pattern.compile("(.+)\\['?([^']+)'?\\]");
         Matcher m = p.matcher(elementName);
         // If matches elements comes from a map
         if (m.find()) {
            methodName = GET + StringUtils.capitalize(m.group(1));
            parameter = m.group(2);
         } else {
            methodName = GET + StringUtils.capitalize(elementName);
         }

         Method method = ReflectionUtils.findMethod(targetClass, methodName);
         if (method == null) {
            method = ReflectionUtils.findMethod(targetClass, methodName, Lang.class);
            if (method == null) {
               throw new AppBaseRuntimeException("Class {0} has no method {1}", targetClass.getName(), methodName);
            }
         }
         if (parameter == null) {
            return invokeGetMethod(method, targetObject);
         } else {
            Object componentMap = method.invoke(targetObject);

            Class<?> type = method.getReturnType();
            boolean isCollection = Collection.class.isAssignableFrom(type);

            // kui on tegemist Collectioniga siis märame tüübiks alati List,
            // Liidesel Collection puudub meetod get,
            // samas eeldavasti on antud juhul alati tegemist List tüüpi
            // objektiga
            if (isCollection) {
               type = List.class;
            }

            Class<?> paramTypes = isCollection ? int.class : Object.class;

            Method mapGet = ReflectionUtils.findMethod(type, GET, paramTypes);
            return mapGet.invoke(componentMap, isCollection ? Integer.valueOf(parameter) : parameter);
         }
      } catch (IllegalArgumentException | SecurityException | IllegalAccessException | InvocationTargetException e) {
         throw new AppBaseRuntimeException(e);
      }
   }

   /**
    * Meetod leiab andmeolemi sees oleva välja väärtuse. Kui <b>path</b> on sisaldab objekti eraldajat (.), eeldatakse et tegemist on
    * andmeolemi sees oleva teise andmeolemiga, mis sisaldab endast otsitavat GET meetodit<br>
    * <br>
    * Väljakutsumine: #getValueByPath(Andmeolem, "rada.rada.rada")
    * 
    * @param entity
    * @param path
    * @return
    */
   @SuppressWarnings("unchecked")
   public static <T, E> E getValueByPath(T entity, String path) {

      E value = null;
      try {
         for (String element : splitByElements(path)) {
            if (value == null) {
               value = (E) getValueByPathSegment(entity.getClass(), entity, element);
            } else {
               value = (E) getValueByPathSegment(value.getClass(), value, element);
            }

            if (value == null) {
               return null;
            }
         }
      } catch (IllegalArgumentException | SecurityException e) {
         throw new AppBaseRuntimeException(e);
      }

      return value;
   }

   private static List<String> splitByElements(String input) {
      List<String> result = new ArrayList<String>();
      if (StringUtils.isBlank(input)) {
         return result;
      }
      boolean quotedString = false;
      StringBuilder elementPart = new StringBuilder();
      for (int i = 0; i < input.length(); i++) {
         if (input.charAt(i) == '\'') {
            quotedString = !quotedString;
         }
         if (input.charAt(i) == '.' && !quotedString) {
            result.add(elementPart.toString());
            elementPart = new StringBuilder();
         } else {
            elementPart.append(input.charAt(i));
         }
      }
      if (elementPart.length() > 0) {
         result.add(elementPart.toString());
      }

      return result;
   }

   /**
    * Meetod väärtustab andmeolemi sees oleva välja etteantud väärtusega. Kui <b>path</b> on sisaldab objekti eraldajat (.), eeldatakse et
    * tegemist on andmeolemi sees oleva teise andmeolemiga, mis sisaldab endast otsitavat SET meetodit<br>
    * <br>
    * Väljakutsumine: #setValueByPath(Andmeolem, "rada.rada.rada", väärtus)
    * 
    * @param entity
    * @param path
    * @param value
    */
   public static <T> void setValueByPath(T entity, String path, Object value) {
      Object object = entity;
      try {
         String[] pathSplit = StringUtils.split(path, NESTED_DELIM);
         int size = pathSplit.length;

         for (int i = 0; i < size; i++) {
            String element = pathSplit[i];
            if (i != size - 1) {
               Method method = ReflectionUtils.findMethod(object.getClass(), GET + StringUtils.capitalize(element));
               if (method == null) {
                  method = ReflectionUtils.findMethod(object.getClass(), GET + StringUtils.capitalize(element), Lang.class);
               }

               Object main = object;
               object = invokeGetMethod(method, object);

               if (object == null) {
                  object = BeanUtils.instantiateClass(method.getReturnType());
                  setValueByPath(main, element, object);
               }
            }
         }

         for (Method method : object.getClass().getMethods()) {
            if (method.getName().equals(SET + StringUtils.capitalize(pathSplit[size - 1]))) {
               Class<?> argType = method.getParameterTypes()[0];
               /*
                * Spring MVC hoiab vormi valikuid endas objektis LinkedHashSet. Leiame meetodi argumendi tüübi ja muudame objekti vastavalt
                */
               if (value instanceof LinkedHashSet && List.class.equals(argType)) {
                  value = new ArrayList<>((Collection<?>) value);
               }
               if (value instanceof LinkedHashSet && Set.class.equals(argType)) {
                  value = new HashSet<>((Collection<?>) value);
               }
               invokeMethod(method, object, prepareMethodArguments(method, value));
            }
         }

      } catch (Exception e) {
         throw new AppBaseRuntimeException(e);
      }
   }

   /**
    * Andmeolemi kloonimine.<br>
    * Antud meetod toetab lisaks ka {@link Map}, {@link Collection} ja {@link Enum}
    * 
    * @param entity
    * @return
    */
   @SuppressWarnings("unchecked")
   public static <T> T clone(T entity) {
      Class<? extends Object> clazz = entity.getClass();

      T cloned = (T) BeanUtils.instantiate(clazz);
      BeanUtils.copyProperties(entity, cloned);

      for (Method method : clazz.getMethods()) {

         if (method.getName().startsWith(GET)
               && (method.getReturnType().equals(Map.class) || method.getReturnType().equals(Collection.class) || method.getReturnType()
                     .isEnum())) {

            String fieldName = method.getName().substring(3, 4).toLowerCase().concat(method.getName().substring(4));
            Object value = getValueByPath(entity, fieldName);
            if (value != null) {
               setValueByPath(cloned, fieldName, value);
            }
         }
      }

      return cloned;
   }

   /**
    * Getting ApplicationContext. SpringApplicationContext is fallback for unit tests that don't have WebApplicationContext
    * 
    * @return
    */
   public static ApplicationContext getApplicationContext() {
      if (ContextLoaderListener.getCurrentWebApplicationContext() != null) {
         return ContextLoaderListener.getCurrentWebApplicationContext();
      }
      return SpringApplicationContext.getApplicationContext();
   }

   /**
    * Klassi tüübi järgi rakendusserveri kontekstist {@link WebApplicationContext} defineeritud beanide leidmine
    */
   public static <T> Map<String, T> getBeansOfType(Class<T> requiredType) {
      return getApplicationContext().getBeansOfType(requiredType);
   }

   /**
    * Klassi tüübi järgi rakendusserveri kontekstist {@link WebApplicationContext} defineeritud beani leidmine
    * 
    * @param requiredType
    * @return
    */
   public static <T> T getBean(Class<T> requiredType) {
      return getApplicationContext().getBean(requiredType);
   }

   @SuppressWarnings("unchecked")
   public static <T> T getBean(String name, Object... args) {
      return (T) getApplicationContext().getBean(name, args);
   }

   /**
    * Valmistame ette meetodi argumendid. Kui argumente on kaks ning üheks on keel siis väärtustame selle aktiivse keelega. Kui meetodi
    * argumente on üks siis tagastame selle
    * 
    * @param method
    * @param value
    * @return
    * @throws IllegalAccessException
    * @throws IllegalArgumentException
    * @throws InvocationTargetException
    */
   private static Object[] prepareMethodArguments(Method method, Object value) throws IllegalAccessException, IllegalArgumentException,
         InvocationTargetException {

      if (method.getParameterTypes().length == 1) {
         return new Object[] { value };
      }

      Object[] args = new Object[method.getParameterTypes().length];
      int count = 0;
      for (Class<?> argClass : method.getParameterTypes()) {
         if (argClass == Lang.class) {
            args[count++] = RequestUtil.getActiveLang();
         } else {
            args[count++] = value;
         }
      }

      return args;
   }

   /**
    * Kutsume välja get meetodi. Vajadusel kutsutakse välja koos sisendargumendiga. Juhul kui meetoodi argumendi tüübiks on {@link Lang},
    * leitakse aktiivse päringu keel ning vastavalt lisatakse argumendiks
    * 
    * @param method
    * @param value
    * @return
    * @throws IllegalAccessException
    * @throws IllegalArgumentException
    * @throws InvocationTargetException
    */
   private static Object invokeGetMethod(Method method, Object object) throws IllegalAccessException, IllegalArgumentException,
         InvocationTargetException {
      Object rValue = object;
      boolean hasLangArgument = hasMethodLangArg(method);

      /* tegemist keele argumendiga, väärdustame */
      if (hasLangArgument) {
         rValue = method.invoke(object, RequestUtil.getActiveLang());
      } else {
         rValue = method.invoke(object);
      }

      return rValue;
   }

   private static boolean hasMethodLangArg(Method method) {

      for (Class<?> argClass : method.getParameterTypes()) {
         if (argClass == Lang.class) {
            return true;
         }
      }

      return false;
   }

   public static Resource getResource(String path) {
      return getApplicationContext().getResource(path);
   }

   /**
    * Tagastab atribuudi kirjelduse etteantud tüübist vastavalt atribuudi rajale
    * 
    * @param type
    * @param path
    * @return
    */
   /*
    * public static Property<?, ?> getPropertyByFieldPath(Class<?> type, String path) { String[] pathItems = StringUtils.split(path,
    * NESTED_DELIM);
    * 
    * Class<?> targetType = type; for (int i = 0; i < pathItems.length - 1; i++) { targetType = BeanUtils.getPropertyDescriptor(targetType,
    * pathItems[i]).getPropertyType(); } return BeanMappings.get(targetType).props().get(pathItems[pathItems.length - 1]); }
    */
}
