package com.nortal.spring.cw.core.util;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Various utility methods dealing with classes through reflection
 * 
 * @author Alrik Peets
 * @since 20.01.2014
 */
public class ReflectionUtil {

   // ===================
   // Following code taken from: http://www.artima.com/weblogs/viewpost.jsp?thread=208860
   // ===================

   public static <S> List<Class<?>> getTypeArguments(Class<S> baseClass, Class<? extends S> childClass) {
      Map<Type, Type> resolvedTypes = new HashMap<Type, Type>();
      Type type = childClass;
      // start walking up the inheritance hierarchy until we hit baseClass
      while (!getClass(type).equals(baseClass)) {
         if (type instanceof Class) {
            // there is no useful information for us in raw types, so just keep going.
            type = ((Class<?>) type).getGenericSuperclass();
         } else {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Class<?> rawType = (Class<?>) parameterizedType.getRawType();

            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
            for (int i = 0; i < actualTypeArguments.length; i++) {
               resolvedTypes.put(typeParameters[i], actualTypeArguments[i]);
            }

            if (!rawType.equals(baseClass)) {
               type = rawType.getGenericSuperclass();
            }
         }
      }

      // finally, for each actual type argument provided to baseClass, determine (if possible)
      // the raw class for that type argument.
      Type[] actualTypeArguments;
      if (type instanceof Class) {
         actualTypeArguments = ((Class<?>) type).getTypeParameters();
      } else {
         actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
      }
      List<Class<?>> typeArgumentsAsClasses = new ArrayList<Class<?>>();
      // resolve types by chasing down type variables.
      for (Type baseType : actualTypeArguments) {
         while (resolvedTypes.containsKey(baseType)) {
            baseType = resolvedTypes.get(baseType);
         }
         typeArgumentsAsClasses.add(getClass(baseType));
      }
      return typeArgumentsAsClasses;
   }

   public static boolean hasInterface(Class<?> cl, Class<?> targetInterface) {
      if (cl == null || cl.getInterfaces() == null)
         return false;
      for (Class<?> cls : cl.getInterfaces()) {
         if (cls.equals(targetInterface))
            return true;
      }
      return false;
   }

   public static Class<?> getClass(Type type) {
      if (type instanceof Class) {
         return (Class<?>) type;
      } else if (type instanceof ParameterizedType) {
         return getClass(((ParameterizedType) type).getRawType());
      } else if (type instanceof GenericArrayType) {
         Type componentType = ((GenericArrayType) type).getGenericComponentType();
         Class<?> componentClass = getClass(componentType);
         if (componentClass != null) {
            return Array.newInstance(componentClass, 0).getClass();
         }
      }
      return null;
   }

}
