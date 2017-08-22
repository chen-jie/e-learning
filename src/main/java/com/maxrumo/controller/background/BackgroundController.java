package com.maxrumo.controller.background;

import com.maxrumo.controller.BaseController;
import com.maxrumo.service.CourseService;
import com.maxrumo.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BackgroundController extends BaseController {

	private static Logger logger = Logger.getLogger(BackgroundController.class);
	@Autowired
	UserService userService;
	@Autowired
	CourseService courseService;

	@RequestMapping(path = { "/background"})
	public String index(Model model) {

		return "background/index";
	}
	@RequestMapping(path = { "/background/toEdit"})
	public String toEdit(Model model) {
		return "background/editPsw";
	}

	@ResponseBody
	@RequestMapping(path = { "/background/edit"})
	public String edit(String opassword,String password) {
		try {
			userService.editPassword(opassword,password);
		}catch (Exception e){
			logger.error("修改密码失败",e);
			return fail(e.getMessage());
		}
		return success("修改成功");
	}

}