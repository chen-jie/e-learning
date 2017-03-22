package com.maxrumo.aspect;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


/** 
 * AOP日志类，针对权限管理时日志打印
 * @author:max
 */
@Aspect
@Component
public class LogAspect {

	private static final Logger logger = Logger.getLogger(LogAspect.class);

    @Before("execution(* com.maxrumo.controller.admin.PermissionController.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
    	String username = (String)SecurityUtils.getSubject().getPrincipal();
        String methodName = joinPoint.getSignature().getName();
        //不针对查询权限
        if(!methodName.equals("list")){
        	 StringBuilder sb = new StringBuilder();
             for (Object arg : joinPoint.getArgs()) {
                 sb.append("arg:" + arg.toString() + "|");
             }
        	logger.warn(String.format("【%s】->【%s】  with param[%s]", username,methodName,sb.toString()));
        }
    }

}
