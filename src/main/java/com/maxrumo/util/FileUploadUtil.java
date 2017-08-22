package com.maxrumo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * Created by Administrator on 2017/4/20.
 */
@Component
public class FileUploadUtil {

    public static final String LOCATION = "upload";


    private static String rootPath;

    @Value("${web.upload-path}")
    public void setRootPath(String path){
        rootPath = path;
    }
    public static String upload(MultipartFile file) {
        return upload(file,null);
    }

    public static String upload(MultipartFile file, String parentDir) {
        String localDir = null;
        String webDir = null;
        if(parentDir == null){
            localDir = rootPath+LOCATION;
            webDir = "/"+LOCATION+"/";
        }else{
            localDir = rootPath+LOCATION+"/"+parentDir;
            webDir = "/"+LOCATION+"/"+parentDir+"/";
        }
        if(file != null){
            String originalFilename = file.getOriginalFilename();
            if(org.apache.commons.lang.StringUtils.isNotBlank(originalFilename)){
                //后缀名
                String fix = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFileName = UUID.randomUUID() + fix;
                File uploadFile = new File(localDir, newFileName);
                try {
                    if(uploadFile.getParentFile().exists() == false){
                        uploadFile.getParentFile().mkdirs();
                    }
                    file.transferTo(uploadFile);
                    return webDir+newFileName;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static boolean isVideo(String url) {
        if(StringUtils.isEmpty(url))
            return false;
        return url.endsWith("mp4");
    }

    public static String getLocalLocation(String url){
        return rootPath+url;
    }
//    public static int getFileType(String fileName) {
//
//    }

 /*   public static String getUploadLocation() {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            return WINDOWS_LOCATION;
        }
        return LINUX_LOCATION;
    }*/

    public static String filenameEncoding(String filename, HttpServletRequest request) throws IOException {
        String agent = request.getHeader("User-Agent"); //获取浏览器
        if (agent.contains("Firefox")) {
            BASE64Encoder base64Encoder = new BASE64Encoder();
            filename = "=?utf-8?B?"
                    + base64Encoder.encode(filename.getBytes("utf-8"))
                    + "?=";
        } else if(agent.contains("MSIE")) {
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }
}
