package com.maxrumo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/12/20.
 */
@Controller
public class TestController {

    @ResponseBody
    @RequestMapping("test")
    public String test(){
        return "test";
    }
}
