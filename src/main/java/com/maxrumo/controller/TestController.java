package com.maxrumo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @ResponseBody
    @RequestMapping("test")
    public String test(){
        return "test";
    }

    @ResponseBody
    @RequestMapping("test1")
    public String test1(){
        return "test1 requrie permisong test1";
    }

    @ResponseBody
    @RequestMapping("test2")
    public String test2(){
        return "test1 requrie permisong test/test2";
    }
}
