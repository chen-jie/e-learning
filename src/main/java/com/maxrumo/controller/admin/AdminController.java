package com.maxrumo.controller.admin;

import com.maxrumo.controller.BaseController;
import com.maxrumo.entity.Course;
import com.maxrumo.entity.Role;
import com.maxrumo.entity.User;
import com.maxrumo.service.CourseService;
import com.maxrumo.service.RoleService;
import com.maxrumo.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController extends BaseController{

	private static Logger logger = Logger.getLogger(AdminController.class);

	@Autowired
	RoleService roleService;
	@Autowired
	CourseService courseService;
	@Autowired
	UserService userService;
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

	@RequestMapping(path = {"/course/list"})
	public String list(Model model) {
		List<Course> list = courseService.findAll();
		model.addAttribute("list", list);
		return "background/admin/course/list";
	}

	@RequestMapping(path = {"/user/list"})
	public String userList(Model model) {
		List<User> list = userService.findAll();
		model.addAttribute("list", list);
		return "background/admin/user/list";
	}
	@ResponseBody
	@RequestMapping(path = {"/user/delete"})
	public String delete(int id) {
		try {
			userService.deleteUser(id);
		}catch (Exception e){
			logger.error("删除用户时发生错误",e);
			return fail("删除失败");
		}
		return success("删除成功");
	}

	@RequestMapping(path = {"/teacher/toAdd"})
	public String toTeacherAdd() {
		return "background/admin/teacher/add";
	}

	@ResponseBody
	@RequestMapping(path = {"/teacher/add"})
	public String teacherAdd(User user) {
		try {
			userService.registerTeacher(user);
		}catch (Exception e){
			logger.error("添加教师失败",e);
			return fail("添加教师失败");
		}
		return success("添加教师成功");
	}
}
