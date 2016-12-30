package com.maxrumo;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@SpringBootApplication
@EnableAutoConfiguration
@ServletComponentScan("com.maxrumo")
public class Application implements EmbeddedServletContainerCustomizer {

    private static Logger logger = Logger.getLogger(Application.class);
    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @ResponseBody
    @RequestMapping(value = "/upload")
    public String uploadVideoStageBanner(@RequestParam("file") MultipartFile file, String name, String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileName = file.getOriginalFilename();
//      String fileName = new Date().getTime()+".jpg";  
        String path = "D:/upload";
        System.out.println(path);
        File targetFile = new File(path, fileName);
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }
//    @Bean
//    public ServletRegistrationBean servletRegistrationBean(){
//        ServletRegistrationBean bean = new ServletRegistrationBean(new CaptchaServlet(), "/captcha");
//        return bean;
//    }
    /*@Bean
    public FilterRegistrationBean filtertRegistrationBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean(new DruidStatFilter());
        bean.addUrlPatterns("*//*");
        bean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid*//*");
        return bean;
    }*/
}

