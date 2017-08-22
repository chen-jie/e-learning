package com.maxrumo.controller.background.teacher;

import com.maxrumo.controller.BaseController;
import com.maxrumo.entity.Course;
import com.maxrumo.entity.CourseChapter;
import com.maxrumo.service.CourseChapterService;
import com.maxrumo.service.CourseService;
import com.maxrumo.service.UserService;
import com.maxrumo.util.CommonUtil;
import com.maxrumo.util.FileUploadUtil;
import com.maxrumo.vo.TreeNode;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class TeacherController extends BaseController {

    private static Logger logger = Logger.getLogger(TeacherController.class);
    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;
    @Autowired
    CourseChapterService courseChapterService;

    /**
     * 进入课程添加页面
     *
     * @return
     */
    @RequestMapping(path = {"/teacher/course/toAdd"})
    public String toAdd() {
        return "background/teacher/course/add";
    }

    /**
     * 课程添加操作
     * @param model
     * @param courseName
     * @param description
     * @param file
     * @return
     */
    @RequestMapping(path = {"/teacher/course/add"})
    public String add(Model model, String courseName, String description, @RequestParam("file") MultipartFile file) {
        try {
            String filePath = FileUploadUtil.upload(file);
            courseService.addCourse(courseName, description, filePath);
            model.addAttribute("msg", "添加成功");
        } catch (Exception e) {
            logger.error("添加课程时发生错误", e);
            model.addAttribute("msg", "添加失败");
        }
        return "background/teacher/course/add";
    }

    /**
     * 进入课程编辑页面
     *
     * @return
     */
    @RequestMapping(path = {"/teacher/course/toEdit/{id}"})
    public String toEdit(Model model,@PathVariable int id) {
        Integer teacherId = CommonUtil.getCurrentUserId();
        Course course = courseService.findById(id);
        if(course != null){
            if(teacherId != course.getTeacherId()){
                return "background/teacher/course/list";
            }
        }
        model.addAttribute("course",course);
        return "background/teacher/course/edit";
    }

    /**
     * 课程编辑操作
     * @param model
     * @param course
     * @param file
     * @return
     */
    @RequestMapping(path = {"/teacher/course/edit"})
    public String edit(Model model, Course course, @RequestParam("file") MultipartFile file) {
        try {
            String filePath = FileUploadUtil.upload(file);
            course.setPoster(filePath);
            courseService.update(course);
            model.addAttribute("msg", "编辑成功");
        } catch (Exception e) {
            logger.error("编辑课程时发生错误", e);
            model.addAttribute("msg", "编辑失败");
        }
        return "background/teacher/course/edit";
    }
    /***
     * 进入课程列表页面
     *
     * @return
     */
    @RequestMapping(path = {"/teacher/course/list"})
    public String list(Model model) {
        Integer teacherId = CommonUtil.getCurrentUserId();
        List<Course> list = courseService.findTeacherCourses(teacherId);
        model.addAttribute("list", list);
        return "background/teacher/course/list";
    }

    /**
     * 删除课程
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(path = {"/teacher/course/delete/{id}"})
    public String delete(@PathVariable int id) {
        Integer teacherId = CommonUtil.getCurrentUserId();
        Course course = courseService.findById(id);
        if(course != null){
            if (course.getTeacherId() != teacherId){
                return fail("删除失败！");
            }
        }else{
            return fail("删除失败！记录不存在.");
        }
        courseService.deleteCourseById(id);
        return success("删除成功");
    }

    /**
     * 课程内容页面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(path = {"/teacher/course/content/{id}"})
    public String courseContent(Model model,@PathVariable int id) {
        Course course = courseService.findById(id);
        //章节
        List<CourseChapter> chapters = courseChapterService.findChapterByCourseId(id);
        //初始化为前端便于遍历的node
        List<TreeNode> treeNodes = CommonUtil.convetChaptersToTree(chapters);
        model.addAttribute("course",course);
        model.addAttribute("chapters",treeNodes);
        SecurityUtils.getSubject().getSession().setAttribute("course",course);
        return "/teacher/course/content";
    }
}