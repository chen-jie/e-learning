package com.maxrumo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.maxrumo.util.Constant;
import com.maxrumo.util.VerifyCodeUtils;

@Controller
public class VCodeController {


	public static final int WIDTH = 200;
	public static final int HEIGHT = 80;
	public static final int CODE_NUM = 4;
	

	@RequestMapping("vcode")
	public void test(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setContentType("image/jpeg");  
		String code = VerifyCodeUtils.generateVerifyCode(CODE_NUM);
		HttpSession session = request.getSession();
		session.setAttribute(Constant.SESSION_CODE, code);
        VerifyCodeUtils.outputImage(WIDTH, HEIGHT, response.getOutputStream(), code);  
	}
}
