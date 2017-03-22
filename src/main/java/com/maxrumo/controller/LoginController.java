package com.maxrumo.controller;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maxrumo.entity.User;
import com.maxrumo.service.UserService;
import com.maxrumo.shiro.realm.MyShiroRealm;
import com.maxrumo.util.Constant;

@Controller
public class LoginController extends BaseController{

	private static Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	private UserService userService;
	
    @ResponseBody
    @RequestMapping("login")
    public String login(String username, String password,String vcode){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        Object sessionCode = session.getAttribute(Constant.SESSION_CODE);
        if(StringUtils.isBlank(vcode) || !vcode.equalsIgnoreCase(sessionCode.toString())){
            return fail("验证码错误");
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try{
        	if(subject.isAuthenticated()){
        		return success("无须重复登录");
        	}
        	subject.login(token);
            return success("登录成功");
        }catch (Exception e){
            return fail("用户名或密码错误");
        }
    }

    @ResponseBody
    @RequestMapping("register")
    public String register(String vcode,User user){
        Session session = getSession();
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

    @RequestMapping("logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        if(subject != null){
            subject.logout();
        }
        return "redirect:/toLogin";
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

    @ResponseBody
    @RequestMapping("removeCache")
    public String removeCache() {
        try {
            logger.info("手动清理权限缓存");
            DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
            CacheManager cacheManager = securityManager.getCacheManager();
            Cache<Object, Object> cache = cacheManager.getCache(MyShiroRealm.REALM_NAME);
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
            return success();
        } catch (Exception e) {
            logger.error("清理缓存发生错误", e);
            return fail();
        }
    }
}
