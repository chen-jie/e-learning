package com.maxrumo.controller;

import com.maxrumo.entity.Course;
import com.maxrumo.service.CourseService;
import com.maxrumo.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("course")
public class CourseController extends BaseController {

	private static Logger logger = Logger.getLogger(CourseController.class);
	@Autowired
	UserService userService;
	@Autowired
	CourseService courseService;

	@RequestMapping(path = { "/detail/{id}"})
	public String detail(Model model,@PathVariable int id) {
		List<Course> list = courseService.findAll();
		model.addAttribute("list",list);
		Course course = courseService.findById(id);
		//判断用户是否已经选了该课程
		boolean isSelected = courseService.isSelected(id);
		model.addAttribute("course",course);
		model.addAttribute("isSelected",isSelected);
		return "course/detail";
	}

	@RequestMapping(path = { "/apply/{id}"})
	public String apply(@PathVariable int id) {
		courseService.applyCourse(id);
		return "redirect:/course/detail/"+id;
	}
}