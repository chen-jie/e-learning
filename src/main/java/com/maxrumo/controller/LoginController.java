package com.maxrumo.controller;

import com.maxrumo.entity.User;
import com.maxrumo.service.UserService;
import com.maxrumo.util.Constant;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController extends BaseController{

	private static Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	private UserService userService;
	
    @ResponseBody
    @RequestMapping("login")
    public String login(HttpSession session,String username, String password,String vcode){
        Object sessionCode = session.getAttribute(Constant.SESSION_CODE);
        if(StringUtils.isBlank(vcode) || !vcode.equalsIgnoreCase(sessionCode.toString())){
            return fail("验证码错误");
        }
        User user = userService.login(username, password);
    	if(user != null){
    		return success("登录成功");
    	}
        return fail("用户名或密码错误");
    }

    @ResponseBody
    @RequestMapping("register")
    public String register(HttpSession session,String vcode,User user){
        Object sessionCode = session.getAttribute(Constant.SESSION_CODE);
        if(StringUtils.isBlank(vcode) || !vcode.equalsIgnoreCase(sessionCode.toString())){
            return fail("验证码错误");
        }
        Map<String, Object> map = null;
    	try {
            map = userService.register(user);
    		if(map.isEmpty()){
    			
    			logger.info("用户【"+user.getNickname()+"】注册成功!");
    			
    			return success("注册成功");
    		}
		} catch (Exception e) {
			logger.error("注册时发生错误",e);
            return fail("注册失败",user);
		}
    	//将用户所填信息返回，避免再次填写
        return fail(map.get("msg").toString(),user);
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
    @RequestMapping("toReg")
    public String toReg(){
        return "reg";
    }
}
