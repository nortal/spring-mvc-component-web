package com.nortal.spring.cw.jsp.servlet.view;

import lombok.AccessLevel;
import lombok.Getter;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.ObjectError;

import com.nortal.spring.cw.core.web.component.composite.Component;
import com.nortal.spring.cw.core.web.component.composite.complex.AbstractComplexComponent;
import com.nortal.spring.cw.core.web.component.composite.view.ResolvableView;
import com.nortal.spring.cw.core.web.servlet.view.CustomView;
import com.nortal.spring.cw.core.web.util.BeanUtil;

/**
 * Tegemist on rakenduse komponendiga, mida peavad laiendama kõik komponendid, mis sisaldavad vaateid ehk komponendiga käib kaasas üks või
 * rohkem JSP faili.
 * 
 * @author Margus Hanni
 * @since 13.03.2013
 */
public class ResolvableViewComponent extends AbstractComplexComponent implements ResolvableView {
   private static final long serialVersionUID = 1L;

   private CwJspInternalResourceViewResolver viewResolver;
   @Getter(AccessLevel.PROTECTED)
   private LabelNamingStrategy defaultLabelNamingStrategy;

   {
      defaultLabelNamingStrategy = new LabelNamingStrategy() {
         @Override
         public String getLabel(Component target) {
            String label = StringUtils.removeStart(
                  StringUtils.removeEnd(StringUtils.removeStart(target.getClass().getName(), viewResolver.getViewPackage()),
                        viewResolver.getComponentClassSuffix()).toLowerCase(), ".");

            label = StringUtils.substringAfter(label, ".");
            return label;
         }
      };
   }

   public ResolvableViewComponent(String componentName) {
      super(componentName);
      viewResolver = BeanUtil.getBean(CwJspInternalResourceViewResolver.class);
   }

   /**
    * Tagastab JSP vaate nime lähtuvalt komponendi nimest ja packagest. ee.epm.portal.web.portal.component.ClientViewComponent puhul
    * otsitakse järgmist JSP-d: WEB-INF/jsp/component/clientview.jsp. Lisaks kontrollitakse kas komponent implementeerib {@link CustomView},
    * sellisel juhul kasutatakse vastavalt määratud viewd
    */
   @Override
   public String getViewPath() {
      return viewResolver.getViewPath(this);
   }

   /**
    * Tagastab JSP vaate nime lähtuvalt komponendi nimest ja packagest. ee.epm.portal.web.portal.component.ClientViewComponent puhul
    * otsitakse järgmist JSP-d: WEB-INF/jsp/component/clientview.jsp
    */
   public String getDefaultView() {
      return viewResolver.getDefaultView(this);
   }

   /**
    * Komponendi põhise üksikveateate koos argumentidega lisamine
    * 
    * @param code
    *           Veateate kood
    * @param args
    *           Veateate argumendid
    */
   public void addComponentError(String code, Object... args) {
      String[] errorCodes = (String[]) ArrayUtils.add(new String[1], getLabel() + "." + code);

      ObjectError error = new ObjectError(getId(), errorCodes, args, code);
      getControllerComponent().getBindingResult().addError(error);
   }

   protected void addReturnMessage(String code, Object... args) {
      getControllerComponent().addMessageWithPrefix(code, args);
   }
}
