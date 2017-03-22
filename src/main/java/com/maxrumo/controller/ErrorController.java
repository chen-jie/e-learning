package com.maxrumo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ErrorController {
   @ResponseBody
   @RequestMapping("/error/{code}")
   public String errorPage(@PathVariable("code")String code){
	   return code;
   }
}
