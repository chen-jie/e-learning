package com.maxrumo.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

/** 
 * 自定义权限过滤器
 * @author max
 */
public class CustomAuthorizationFilter extends AuthorizationFilter {
    
	public static final Logger logger = Logger.getLogger(CustomAuthorizationFilter.class);
	public static final String MSG_SUCCESS = "【成功】";
	public static final String MSG_FAIL = "【失败】";
	@Override
	public boolean isAccessAllowed(
	        ServletRequest request,
			ServletResponse response, 
			Object mappedValue) throws IOException {

		HttpServletRequest req = (HttpServletRequest) request;
		
		String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        //获取访问的uri
        int i = uri.indexOf(contextPath);
        if (i > -1) {
            uri = uri.substring(i + contextPath.length());
        }
        //路径为空则说明是访问网站根节点
        if (StringUtils.isBlank(uri)) {
            uri = "/";
        }
        boolean permitted = false;
        if ("/".equals(uri)) {
            permitted = true;
        } else {
            //使用shiro 鉴权
            if (uri.startsWith("/"))
            {
                uri = uri.substring(1);
            }
            permitted = subject.isPermitted(uri);
            String msg = MSG_FAIL;
            if(permitted){
            	msg = MSG_SUCCESS;
            }
            logger.info(String.format("%s:用户【%s】访问权限资源:%s",msg,username,uri));
        }
        return permitted;
	}
}
