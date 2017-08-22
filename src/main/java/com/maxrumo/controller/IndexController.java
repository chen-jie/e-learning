package com.maxrumo.controller;

import com.maxrumo.entity.Course;
import com.maxrumo.service.CourseService;
import com.maxrumo.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController extends BaseController {

	private static Logger logger = Logger.getLogger(IndexController.class);
	@Autowired
	UserService userService;
	@Autowired
	CourseService courseService;

	@RequestMapping(path = { "/", "index" })
	public String index(Model model) {
		List<Course> list = courseService.findAll();
		model.addAttribute("list",list);
		return "index";
	}

}