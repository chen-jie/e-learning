package com.maxrumo.controller.admin;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maxrumo.controller.BaseController;
import com.maxrumo.entity.Permission;
import com.maxrumo.service.PermissionService;
import com.maxrumo.util.JsonTool;
import com.maxrumo.vo.TreeNode;

@Controller
@RequestMapping("admin/perm")
public class PermissionController extends BaseController{

	private static Logger logger = Logger.getLogger(PermissionController.class);

	@Autowired
	private PermissionService permissionService;
	
	@ResponseBody
	@RequestMapping("/list")
	public String list(Integer id){
		if(id == null){
			List<TreeNode> treeList = permissionService.getPermissionTree();
			return JsonTool.objToJson(treeList);
		}
		Permission permission = permissionService.findById(id);
		return JsonTool.objToJson(permission);
	}
	@ResponseBody
	@RequestMapping("/add")
	public String add(Permission permission){
		try {
			permissionService.insert(permission);
			return success("添加成功");
		} catch (Exception e) {
			logger.error("添加权限失败",e);
		}
		return fail("添加失败");
	}
	@ResponseBody
	@RequestMapping("/edit")
	public String edit(Permission permission){
		try {
			permissionService.update(permission);
			return success("编辑成功");
		} catch (Exception e) {
			logger.error("编辑资源失败",e);
		}
		return fail("编辑失败");
	}
	@ResponseBody
	@RequestMapping("/delete")
	public String delete(int id){
		try {
			permissionService.deletePemAndChildren(id);
			return success("删除成功");
		} catch (Exception e) {
			logger.error("删除资源失败",e);
		}
		return fail("删除失败");
	}
}
