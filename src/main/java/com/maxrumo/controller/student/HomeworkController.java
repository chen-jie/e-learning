package com.maxrumo.controller.student;

import com.maxrumo.controller.BaseController;
import com.maxrumo.service.CourseChapterService;
import com.maxrumo.service.CourseService;
import com.maxrumo.service.HomeworkService;
import com.maxrumo.service.UserService;
import com.maxrumo.util.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller("studentHomeworkController")
public class HomeworkController extends BaseController {

    private static Logger logger = Logger.getLogger(HomeworkController.class);
    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;
    @Autowired
    CourseChapterService courseChapterService;
    @Autowired
    HomeworkService homeworkService;

    @RequestMapping(path = {"/student/homework/unSubmitList"})
    public String list(Model model) {
        int userId = CommonUtil.getCurrentUserId();
        List<Map> list = homeworkService.getUnSubmitedReport(userId);
        model.addAttribute("list",list);
        return "/background/student/homework/list";
    }
}