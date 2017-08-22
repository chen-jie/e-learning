package com.maxrumo.controller.background.teacher;

import com.maxrumo.controller.BaseController;
import com.maxrumo.entity.CourseChapter;
import com.maxrumo.service.CourseChapterService;
import com.maxrumo.service.CourseService;
import com.maxrumo.service.UserService;
import com.maxrumo.util.FileUploadUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ChapterController extends BaseController {

    private static Logger logger = Logger.getLogger(ChapterController.class);
    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;
    @Autowired
    CourseChapterService courseChapterService;

    /**
     * 进入章节添加页面
     *
     * @return
     */
    @RequestMapping(path = {"/teacher/chapter/toAdd"})
    public String toAdd(Model model, Integer parentId,Integer courseId) {
        model.addAttribute("parentId",parentId);
        model.addAttribute("courseId",courseId);
        return "background/teacher/chapter/add";
    }
    @RequestMapping(path = {"/teacher/chapter/add"})
    public String add(CourseChapter chapter,Model model, @RequestParam("file") MultipartFile file) {
        try {
            String filePath = FileUploadUtil.upload(file);
            chapter.setAttachment(filePath);
            courseChapterService.addChapter(chapter);
            model.addAttribute("msg", "添加成功");
        } catch (Exception e) {
            logger.error("添加课程时发生错误", e);
            model.addAttribute("msg", "添加失败");
        }
        return "background/teacher/chapter/add";
    }

    @ResponseBody
    @RequestMapping(path = {"/teacher/chapter/delete/{chapterId}"})
    public String delete(@PathVariable int chapterId) {
        try {
            courseChapterService.deleteByChapterId(chapterId);
        }catch (Exception e){
            logger.error("删除课时时发生错误",e);
            return fail("删除失败");
        }
        return success("删除成功");
    }

}