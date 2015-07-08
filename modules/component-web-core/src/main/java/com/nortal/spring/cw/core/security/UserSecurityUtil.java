package com.nortal.spring.cw.core.security;

import com.nortal.spring.cw.core.security.annotation.Restricted;

/**
 * 
 * @author Margus Hanni
 * @since 05.06.2013
 */
// TODO implement this
public class UserSecurityUtil {

   public static boolean hasAnyPrivilege(CwPrivilege... privileges) {
      for (CwPrivilege item : privileges) {
         if (hasPrivilege(item)) {
            return true;
         }
      }
      return false;
   }

   public static boolean setHasAnyPrivilege(CwPrivilege... privileges) {
      return hasAnyPrivilege(privileges);
   }

   public static boolean hasPrivileges(CwPrivilege... privileges) {
      for (CwPrivilege item : privileges) {
         if (!hasPrivilege(item)) {
            return false;
         }
      }
      return true;
   }

   public static boolean hasPrivilege(CwPrivilege privilege) {
      // TODO implement this
      return false;
   }

   public static boolean hasAnyPrivilege(String... privileges) {
      // TODO implement this
      return false;
   }

   /**
    * Meetod kontrollib kas aktiivsel kasutajal on õigusi toimetada etteantud objektiga. Objektiks võib olla kontroller või mõni komponent.
    * Kui objekti juures on määratud annotatsioon {@link Restricted}, siis kontrollitakse kas kasutajal on küljes vastavalt määratud
    * privileegid. Kui kasutajale ei ole määratud vastavat privileegi siis tagastatakase <code>false</code>. Kui aga objektile ei ole
    * määratud vastavat annotatsiooni, tagastatakse alati <code>true</code>
    * 
    * @param checkClass
    *           {@link Class}
    * @return <code>true</true> ei ole piiratud, <code>false</true> juurdepääs on piiratud
    */
   public static boolean checkClassPrivileges(Class<?> checkClass) {
      Restricted privileges = checkClass.getAnnotation(Restricted.class);
      return privileges == null || UserSecurityUtil.hasAnyPrivilege(privileges.privileges());
   }

}
