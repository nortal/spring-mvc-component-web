package com.nortal.spring.cw.core.web.util;

import java.beans.Introspector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.nortal.spring.cw.core.web.component.ElementPath;
import com.nortal.spring.cw.core.web.component.ElementPathList;
import com.nortal.spring.cw.core.web.component.ElementPathMap;
import com.nortal.spring.cw.core.web.component.GenericElement;
import com.nortal.spring.cw.core.web.component.Hierarchical;
import com.nortal.spring.cw.core.web.component.composite.ControllerComponent;
import com.nortal.spring.cw.core.web.component.composite.complex.ComplexComponent;
import com.nortal.spring.cw.core.web.component.element.ElementLabel;
import com.nortal.spring.cw.core.web.component.element.FormDataElement;
import com.nortal.spring.cw.core.web.component.event.confirmation.ConfirmationDialog;
import com.nortal.spring.cw.core.web.component.single.values.MultiValueSupport;

/**
 * Util class for manipulating with element path
 * 
 * @author Margus Hanni
 * @author Alrik Peets
 * @since 19.03.2013
 */
public final class ElementUtil {

   private ElementUtil() {
      super();
   }

   public static String convertPathToDisplayId(String path) {
      if (path == null) {
         return null;
      }
      return path.replaceAll("(\\[\\')|(\\'\\])", "---").replaceAll("[\\[\\]]", "--").replaceAll("[\\.]", "_");
   }

   public static String convertDisplayIdToPath(String displayId) {
      if (displayId == null) {
         return null;
      }
      return displayId.replaceAll("(---([^.]+?)---)", "['$2']").replaceAll("(--([^.]+?)--)", "[$2]").replaceAll("[_]", ".");
   }

   /**
    * Leiame tabel elemendi rea numbri, kui elementi ei leita, tagastatakse -1
    * 
    * @param modalName
    * @param elementId
    * @return
    */
   public static int getListRowId(String elementId) {
      Pattern p = Pattern.compile(".?listRow\\[(.+?)\\].?");
      Matcher m = p.matcher(elementId);
      String rowNum = null;
      if (m.find()) {
         rowNum = m.group(1);
         rowNum = rowNum.replace("'", "");
      }
      return rowNum != null ? Integer.parseInt(rowNum) : -1;
   }

   /**
    * Leiame tabel elemendi veeru numbri, Kui elementi ei leita, tagastatakse -1
    * 
    * @param modalName
    * @param elementId
    * @return
    */
   public static int getListCellId(String listComponentPath, String elementId) {
      Pattern p = Pattern.compile(".?elements\\[(.+?)\\].?");
      Matcher m = p.matcher(elementId);
      String cellNr = null;
      if (m.find()) {
         cellNr = m.group(1);
      }
      return cellNr != null ? Integer.parseInt(cellNr) : -1;
   }

   /**
    * Leiame komponenti hidva põhi komponendi
    * 
    * @param comp
    * @param modalName
    * @param elementId
    * @return
    */
   public static ComplexComponent findEnclosingComplexComponent(ControllerComponent comp, String targetComponentName,
         String elementDisplayId) {
      ComplexComponent component = comp;

      for (String split : StringUtils.splitByWholeSeparator(elementDisplayId, ComplexComponent.COMPOSITE_MAP_METHOD_NAME)) {
         String parentName = StringUtils.remove(StringUtils.remove(split, "--"), "-");
         if (!StringUtils.equals(parentName, targetComponentName)) {
            component = component.getComponentByKey(parentName);
         }
      }

      return component;
   }

   /**
    * Elemendi põhikomponendi leidmine. Meetod tagastab elemendi kõige viimase parent objekti
    * 
    * @param element
    * @return
    */
   public static ComplexComponent getMainComponent(GenericElement element) {

      ComplexComponent component = null;

      GenericElement check = element;

      while (check != null) {
         if (check instanceof ComplexComponent) {
            component = (ComplexComponent) check;
         }
         if (check instanceof Hierarchical) {
            check = ((Hierarchical) check).getParent();
         }
      }

      return component;
   }

   /**
    * Meetod implementeerib {@link ElementLabel} tekitades uue objekti mille {@link ElementLabel#getLabel()} tagastab meetodi argumendiks
    * oleva väärtuse
    * 
    * @param label
    *           {@link String}
    * @return {@link ElementLabel}
    */
   public static ElementLabel createSimpleLabel(final String label) {
      return new ElementLabel() {

         private static final long serialVersionUID = 1L;

         @Override
         public String getLabel() {
            return label;
         }
      };
   }

   /**
    * Meetod kontrollib elemendi väärtuse olemasolu. Kui element ei sisalda väärtusi tagastatakse <code>true</code> vastasel juhul
    * tagastatakse <code>false</code>. Kui tegemist on {@link MultiValueSupport} kontrollitakse lisaks kas {@link Collection} ei ole tühi
    * 
    * @param dataElement
    *           {@link FormDataElement}
    * @return {@link Boolean}
    */
   public static boolean isEmpty(FormDataElement dataElement) {
      Object value = dataElement.getRawValue();

      if (value == null) {
         return true;
      }

      if (value instanceof String && StringUtils.isBlank((String) value)) {
         return true;
      }

      if (value instanceof Collection<?> && CollectionUtils.isEmpty((Collection<?>) value)) {
         return true;
      }

      return false;
   }

   public static String getNameForFullPath(Object element) {
      return Introspector.decapitalize(String.format(element.getClass().getSimpleName()));
   }

   public static String getFullPath(GenericElement element) {
      List<String> paths = new ArrayList<>();

      if (element instanceof ElementPath && !(element instanceof ControllerComponent)) {
         ElementPath elementPath = (ElementPath) element;
         paths.add(getElementPath(elementPath));
         collectFullComponents(elementPath.getParentElementPath(), paths);
      }

      if (paths.size() == 1) {
         // kui elemendil ei ole ühtegi ülemat siis on tegu elemendiga, mis elab otse kontrolleri küljes, käitume temaga vastavalt
         paths.clear();
         ElementPath elementPath = (ElementPath) element;
         paths.add(String.format("components['%s']", ((GenericElement) elementPath).getId()));
      } else if (element instanceof ConfirmationDialog && ((ConfirmationDialog) element).getParent() instanceof ComplexComponent) {
         // kui on tegemist dialoog aknaga ning tema ülemaks on ComplexComponent siis otsime dialoog akend komponendi enda seest mitte raja
         // põhiselt
         paths.remove(0);
         ElementPath elementPath = (ElementPath) element;
         paths.add(String.format("components['%s']", ((GenericElement) elementPath).getId()));
      } else {
         // keerame nimekirja ümber, alustame komponentidest
         Collections.reverse(paths);
      }

      return StringUtils.join(paths, GenericElement.ID_DELIMITER);
   }

   private static void collectFullComponents(ElementPath parent, List<String> paths) {
      if (parent != null && !(parent instanceof ControllerComponent)) {
         ElementPath elementPath = (ElementPath) parent;
         paths.add(getElementPath(elementPath));
         collectFullComponents(elementPath.getParentElementPath(), paths);
      }
   }

   private static String getElementPath(ElementPath elementPath) {

      if (elementPath instanceof ConfirmationDialog) {
         return elementPath.getPath();
      }

      if (elementPath instanceof ElementPathList) {
         return String.format(elementPath.getPath() + "['%d']", ((ElementPathList) elementPath).getPathIndex());
      }

      if (elementPath instanceof ElementPathMap) {
         return String.format(elementPath.getPath() + "['%s']", ((ElementPathMap) elementPath).getPathKey());
      }

      return elementPath.getPath();
   }
}
