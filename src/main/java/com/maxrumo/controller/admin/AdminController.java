package com.maxrumo.controller.admin;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.maxrumo.controller.BaseController;
import com.maxrumo.entity.Role;
import com.maxrumo.service.RoleService;

@Controller
@RequestMapping("admin")
public class AdminController extends BaseController{

	private static Logger logger = Logger.getLogger(AdminController.class);

	@Autowired
	RoleService roleService;
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
	public String role(Model model){
		List<Role> list = roleService.findAll();
		model.addAttribute("list", list);
		return "admin/role";
	}
	@RequestMapping("/perm")
	public String perm(){
		return "admin/perm";
	}
}
