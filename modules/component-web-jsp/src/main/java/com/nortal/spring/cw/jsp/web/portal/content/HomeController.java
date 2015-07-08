package com.nortal.spring.cw.jsp.web.portal.content;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nortal.spring.cw.core.web.component.composite.ControllerComponent;

/**
 * @author Margus Hanni
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping("/home")
public class HomeController extends AbstractModelPageController {

   @Override
   protected void initControllerComponent(ControllerComponent compositeComp) {
      // TODO Auto-generated method stub

   }

}
