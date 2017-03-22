package com.maxrumo.shiro.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.maxrumo.shiro.filter.CustomAuthorizationFilter;
import com.maxrumo.shiro.realm.MyShiroRealm;

/**
 * Shiro 配置
 */
@Configuration
public class ShiroConfiguration {

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public EhCacheManager getEhCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return em;
    }
    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        //credentialsMatcher.setHashIterations(2);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    @Bean(name = "myShiroRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm realm = new MyShiroRealm();
        realm.setCacheManager(getEhCacheManager());
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager() {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        dwsm.setRealm(myShiroRealm());
        dwsm.setCacheManager(getEhCacheManager());
        return dwsm;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(getDefaultWebSecurityManager());
        return aasa;
    }

//    @Bean
//    public CustomAuthorizationFilter getCustomAuthorizationFilter() {
//        return new CustomAuthorizationFilter();
//    }
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager  
        shiroFilterFactoryBean.setSecurityManager(getDefaultWebSecurityManager());
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        // 登录成功后要跳转的连接
        //shiroFilterFactoryBean.setSuccessUrl("/");
        shiroFilterFactoryBean.setUnauthorizedUrl("/error/403");

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        //自定义过滤器
        HashMap<String, Filter> filters = new LinkedHashMap<String, Filter>();
        filters.put("customperms", new CustomAuthorizationFilter());
        shiroFilterFactoryBean.setFilters(filters);
        filterChainDefinitionMap.put("/favicon.ico", "anon"); 		//网站图标
        filterChainDefinitionMap.put("/vcode", "anon");				//验证码
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/toLogin", "anon");  			//登录页面
        filterChainDefinitionMap.put("/logout", "authc");
        filterChainDefinitionMap.put("/toReg", "anon");  			//注册页面
        filterChainDefinitionMap.put("/register", "anon");    		//注册请求地址
        filterChainDefinitionMap.put("/validate*", "anon");    		//注册ajax请求验证
        filterChainDefinitionMap.put("/", "authc");					//登录才能查看首页
        filterChainDefinitionMap.put("/index", "authc");
        filterChainDefinitionMap.put("/error/**", "anon");			//错误页面
        filterChainDefinitionMap.put("/druid/**", "anon");			//德鲁伊连接池监控地址
        filterChainDefinitionMap.put("/static/**", "anon"); 		//静态资源
        filterChainDefinitionMap.put("/**", "authc,customperms");	//其他地址需要登录，且验证权限
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
    /**
     * 注册shiro的DelegatingFilterProxy，不然会报错
     * @author max
     */
    @Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
		DelegatingFilterProxy proxy = new DelegatingFilterProxy("shiroFilter");
		proxy.setTargetFilterLifecycle(true);
        filterRegistration.setFilter(proxy); 
        filterRegistration.setEnabled(true);
//        filterRegistration.addUrlPatterns("/*"); 
//        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
        return filterRegistration;
	}

}