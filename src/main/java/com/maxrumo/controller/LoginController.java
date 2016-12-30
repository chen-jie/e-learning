package com.maxrumo.controller;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maxrumo.entity.User;
import com.maxrumo.service.UserService;

@Controller
public class LoginController extends BaseConroller{

	private static Logger logger = Logger.getLogger(BaseConroller.class);
	@Autowired
	private UserService userService;
	
    @ResponseBody
    @RequestMapping("login")
    public String login(String username,String password){
    	User user = userService.login(username, password);
    	if(user != null){
    		return success("登录成功");
    	}
        return fail("用户名或密码错误");
    }

    @ResponseBody
    @RequestMapping("register")
    public String register(User user){
    	try {
    		Map<String, Object> map = userService.register(user);
    		if(map.isEmpty()){
    			
    			logger.info("用户【"+user.getNickname()+"】注册成功!");
    			
    			return success("注册成功");
    		}
		} catch (Exception e) {
			logger.error("注册时发生错误",e);
		}
    	//将用户所填信息返回，避免再次填写
        return fail("注册失败",user);
    }
    
    
    /** 
     * @description ajax验证用户名是否存在
     * @author cj
     * @date 2016年12月29日 下午4:11:09
     * @param username
     * @return
     */
    @ResponseBody
    @RequestMapping("validateUsername")
    public String validateUsername(String username){
    	if(userService.isUsernameExist(username)){
    		return fail("用户名已经存在");
    	}
        return success();
    }
    @ResponseBody
    @RequestMapping("validateEmail")
    public String validateEmail(String email){
    	if(userService.isEmailExist(email)){
    		return fail("邮箱已经被使用");
    	}
        return success();
    }
    @ResponseBody
    @RequestMapping("validateNickname")
    public String validateNickname(String nickname){
    	if(userService.isNicknameExist(nickname)){
    		return fail("昵称已经被使用");
    	}
        return success();
    }
    
    @RequestMapping("toLogin")
    public String toLogin(){
        return "login";
    }
}
