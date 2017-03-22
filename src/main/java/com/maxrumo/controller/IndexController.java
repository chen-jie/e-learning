package com.maxrumo.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.maxrumo.service.UserService;

@Controller
public class IndexController extends BaseController {

	private static Logger logger = Logger.getLogger(IndexController.class);
	@Autowired
	private UserService userService;

	@RequestMapping(path = { "/", "index" })
	public String index(String username, String password, String vcode) {
		return null;
	}

}