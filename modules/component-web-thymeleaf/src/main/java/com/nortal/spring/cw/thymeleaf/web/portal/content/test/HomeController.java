package com.nortal.spring.cw.thymeleaf.web.portal.content.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/home")
public class HomeController  {

   @RequestMapping()
   public String contacts(Model model) {
       model.addAttribute("contacts","test");
       return "home";
   }

}