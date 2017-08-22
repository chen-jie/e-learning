package com.maxrumo.controller.background.teacher;

import com.maxrumo.controller.BaseController;
import com.maxrumo.entity.Course;
import com.maxrumo.entity.Homework;
import com.maxrumo.entity.Judgement;
import com.maxrumo.entity.User;
import com.maxrumo.service.CourseService;
import com.maxrumo.service.HomeworkService;
import com.maxrumo.service.JudgementService;
import com.maxrumo.service.UserService;
import com.maxrumo.util.FileUploadUtil;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/teacher/homework")
public class HomeworkController extends BaseController {

    private static Logger logger = Logger.getLogger(HomeworkController.class);
    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;
    @Autowired
    HomeworkService homeworkService;
    @Autowired
    JudgementService judgementService;
    /**
     * 进入章节添加页面
     *
     * @return
     */
    @RequestMapping(path = {"/toAdd"})
    public String toAdd(Model model, Integer chapterId,Integer courseId) {
        model.addAttribute("chapterId",chapterId);
        model.addAttribute("courseId",courseId);
        return "background/teacher/homework/add";
    }
    @RequestMapping(path = {"/add"})
    public String add(Homework homework,Integer courseId,Model model, @RequestParam("file") MultipartFile file) {
        try {
            String filePath = FileUploadUtil.upload(file,homework.getName());
            homework.setAttachment(filePath);
            homeworkService.addHomework(homework,courseId);
            model.addAttribute("msg", "添加成功");
        } catch (Exception e) {
            logger.error("布置作业时发生错误", e);
            model.addAttribute("msg", "添加失败");
        }
        return "background/teacher/homework/add";
    }

    @RequestMapping(path = {"/{id}"})
    public String list(Model model, @PathVariable int id) {
        try {
            List<Homework> list = homeworkService.findByCourseId(id);
            List<Map> mapList = judgementService.findByHomeworks(list);
            Course course = courseService.findById(id);
            model.addAttribute("course",course);
            model.addAttribute("list",mapList);
        } catch (Exception e) {
            logger.error("查看作业时发生错误", e);
        }
        return "/teacher/homework/list";
    }

    @RequestMapping(path = {"/report"})
    public String report(Model model,  int homeworkId) {
        try {
            Homework homework = homeworkService.findById(homeworkId);
            Course course = courseService.findById(homework.getCourseId());
            Map map = judgementService.createReport(homeworkId);
            model.addAttribute("course",course);
            model.addAttribute("map",map);
        } catch (Exception e) {
            logger.error("查看作业报告时发生错误", e);
        }
        return "/teacher/homework/report";
    }

    @RequestMapping(path = {"/download"})
    public void download(HttpServletRequest request, HttpServletResponse response, int id) {
        Homework homework = homeworkService.findById(id);
        OutputStream out = null;
        try {
            String localUrl = FileUploadUtil.getLocalLocation(homework.getAttachment());
            File file = new File(localUrl);
            //文件格式，带 .
            String fileType = file.getName().substring(file.getName().lastIndexOf("."));
            out = response.getOutputStream();
            response.setContentType("application/octet-stream; charset=utf-8");
            String fileName = homework.getChapterName()+fileType;
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

    @RequestMapping(path = {"/downloadJudgement"})
    public void downloadJudgement(HttpServletRequest request, HttpServletResponse response, int id) {
        Judgement judgement = judgementService.findById(id);
        User student = userService.findById(judgement.getStudentId());
        OutputStream out = null;
        try {
            String localUrl = FileUploadUtil.getLocalLocation(judgement.getHomeworkUrl());
            File file = new File(localUrl);
            //文件格式，带 .
            String fileType = file.getName().substring(file.getName().lastIndexOf("."));
            out = response.getOutputStream();
            response.setContentType("application/octet-stream; charset=utf-8");
            String fileName = student.getNickname()+fileType;
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

    @ResponseBody
    @RequestMapping(path = {"/judge"})
    public String judge(int judgementId,int score) {
        try {
            judgementService.judge(judgementId,score);
        }catch (Exception e){
            logger.error("评分作业时发生错误",e);
            return fail("评分失败");
        }
        return success("评分成功");
    }

    @ResponseBody
    @RequestMapping(path = {"/uploadHomework"})
    public String uploadHomework(int judgementId, @RequestParam("file") MultipartFile file) {
        try {
            String filePath = FileUploadUtil.upload(file);
            judgementService.uploadHomework(judgementId,filePath);
        } catch (Exception e) {
            logger.error("上传作业时发生错误", e);
            return fail("上传失败");
        }
        return success("上传成功");
    }
}