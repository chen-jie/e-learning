package com.maxrumo.controller.admin;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.maxrumo.controller.BaseController;

@Controller
@RequestMapping("admin")
public class AdminController extends BaseController{

	private static Logger logger = Logger.getLogger(AdminController.class);

	/** 
	 * @description 三个简单的页面跳转
	 * @date 2017年1月4日 下午4:40:08
	 * @return
	 */
	@RequestMapping("/user")
	public String user(){
		return "admin/user";
	}
	@RequestMapping("/role")
	public String role(){
		return "admin/role";
	}
	@RequestMapping("/perm")
	public String perm(){
		return "admin/perm";
	}
}
