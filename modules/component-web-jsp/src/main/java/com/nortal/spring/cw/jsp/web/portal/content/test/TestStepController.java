package com.nortal.spring.cw.jsp.web.portal.content.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nortal.spring.cw.jsp.web.portal.menu.MainMenuItemEnum;
import com.nortal.spring.cw.jsp.web.portal.menu.MenuComponent;

/**
 * @author Margus Hanni
 * 
 */
@Controller
@RequestMapping("/test/test-step")
@MenuComponent(menuItem = MainMenuItemEnum.TEST)
public class TestStepController {

}
