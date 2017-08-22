package com.maxrumo.controller.student;

import com.maxrumo.controller.BaseController;
import com.maxrumo.entity.CourseChapter;
import com.maxrumo.service.CourseChapterService;
import com.maxrumo.service.CourseService;
import com.maxrumo.service.UserService;
import com.maxrumo.util.FileUploadUtil;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

@Controller("studentChapterController")
public class ChapterController extends BaseController {

    private static Logger logger = Logger.getLogger(ChapterController.class);
    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;
    @Autowired
    CourseChapterService courseChapterService;

    /**
     * 章节播放页面
     * @param model
     * @param chapterId
     * @return
     */
    @RequestMapping(path = {"/student/chapter/show/{chapterId}"})
    public String show(Model model, @PathVariable int chapterId) {
        CourseChapter chapter = courseChapterService.findChapterById(chapterId);
//        int fileType = FileUploadUtil.getFileType(chapter.getAttachment());
        boolean isVideo = FileUploadUtil.isVideo(chapter.getAttachment());
        if(isVideo)
            model.addAttribute("isVideo",true);
        model.addAttribute("chapter",chapter);
        return "/chapter/show";
    }
    @RequestMapping(path = {"/download"})
    public void download(HttpServletRequest request,HttpServletResponse response,int chapterId) {
        CourseChapter chapter = courseChapterService.findChapterById(chapterId);
        OutputStream out = null;
        try {
            String localUrl = FileUploadUtil.getLocalLocation(chapter.getAttachment());
            File file = new File(localUrl);
            //文件格式，带 .
            String fileType = file.getName().substring(file.getName().lastIndexOf("."));
            out = response.getOutputStream();
            response.setContentType("application/octet-stream; charset=utf-8");
            String fileName = chapter.getTitle()+fileType;
            fileName = FileUploadUtil.filenameEncoding(fileName,request);
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            out.write(FileUtils.readFileToByteArray(file));
            out.flush();
            out.close();
        }catch (Exception e){
            logger.error("下载文件时错误!",e);
        }finally {
            if(out != null)
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

}