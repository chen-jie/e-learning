package com.maxrumo.controller.background.teacher;

import com.maxrumo.controller.BaseController;
import com.maxrumo.entity.Course;
import com.maxrumo.entity.Topic;
import com.maxrumo.service.CourseService;
import com.maxrumo.service.ReplyService;
import com.maxrumo.service.TopicService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/teacher/topic/")
public class TopicController extends BaseController {

    private static Logger logger = Logger.getLogger(TopicController.class);

    @Autowired
    TopicService topicService;
    @Autowired
    CourseService courseService;
    @Autowired
    ReplyService replyService;
    @RequestMapping(path = {"/{courseId}/{type}"})
    public String list(Model model,@PathVariable int courseId, @PathVariable int type) {
        Course course = courseService.findById(courseId);
        model.addAttribute("type",type);
        model.addAttribute("course",course);
        List<Topic> list = topicService.findAllByCourseId(courseId);
        model.addAttribute("list",list);
        return "/teacher/topic/topic";
    }

    @ResponseBody
    @RequestMapping(path = {"/delete/{id}"})
    public String delete(@PathVariable int id) {
        try {
            topicService.deleteById(id);
            return success("删除成功。");
        }catch (Exception e){
            logger.error("删除帖子时发生错误!",e);
        }
        return fail("删除失败");
    }

    @ResponseBody
    @RequestMapping(path = {"/deleteReply/{id}"})
    public String deleteReply(@PathVariable int id) {
        try {
            replyService.deleteById(id);
            return success("删除成功。");
        }catch (Exception e){
            logger.error("删除回复时发生错误!",e);
        }
        return fail("删除失败");
    }

    @ResponseBody
    @RequestMapping(path = {"/toTop/{id}"})
    public String toTop(@PathVariable int id) {
        try {
            topicService.toTop(id);
            return success("置顶成功。");
        }catch (Exception e){
            logger.error("删除回复时发生错误!",e);
        }
        return fail("操作失败。");
    }
}