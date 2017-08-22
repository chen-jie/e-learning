package com.maxrumo.controller.student;

import com.maxrumo.controller.BaseController;
import com.maxrumo.entity.Course;
import com.maxrumo.service.CourseChapterService;
import com.maxrumo.service.CourseService;
import com.maxrumo.service.UserService;
import com.maxrumo.util.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("studentCourseController")
public class CourseController extends BaseController {

    private static Logger logger = Logger.getLogger(CourseController.class);
    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;
    @Autowired
    CourseChapterService courseChapterService;

    @RequestMapping(path = {"/student/course/myCourseList"})
    public String show(Model model) {
        Integer userId = CommonUtil.getCurrentUserId();
        List<Course> list = courseService.getMySelectedCourse(userId);
        model.addAttribute("list",list);
        return "/background/student/course/list";
    }
}