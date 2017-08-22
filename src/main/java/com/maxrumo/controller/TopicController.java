package com.maxrumo.controller;

import com.maxrumo.entity.Reply;
import com.maxrumo.entity.Topic;
import com.maxrumo.service.ReplyService;
import com.maxrumo.service.TopicService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("commonTopicController")
@RequestMapping("/topic/")
public class TopicController extends BaseController {

    private static Logger logger = Logger.getLogger(TopicController.class);

    @Autowired
    TopicService topicService;
    @Autowired
    ReplyService replyService;

    @RequestMapping(path = {"/add"})
    public String list(Model model,Topic topic) {
        try {
            topicService.addTopic(topic);
            model.addAttribute("msg","发布成功！");
        }catch (Exception e){
            model.addAttribute("msg","发布失败!");
            logger.error("发布话题时失败:",e);
        }
        List<Topic> list = topicService.findAllByCourseId(topic.getCourseId());
        model.addAttribute("list",list);
        return "redirect:/teacher/topic/"+topic.getCourseId()+"/0";
    }

    @RequestMapping(path = {"/addReply"})
    public String addReply(Model model,Reply reply) {
        try {
            replyService.addReply(reply);
            model.addAttribute("msg","发布成功！");
        }catch (Exception e){
            model.addAttribute("msg","发布失败!");
            logger.error("发布回复时失败:",e);
        }
        return "redirect:/topic/show/"+reply.getTopicId();
    }

    @RequestMapping(path = {"/show/{id}"})
    public String content(Model model, @PathVariable int id) {
        try {
            Topic topic = topicService.findById(id);
            List<Reply> replies = replyService.findByTopicId(id);
            topicService.addViewCount(id);
            model.addAttribute("topic",topic);
            model.addAttribute("replies",replies);
            return "/teacher/topic/content";
        }catch (Exception e){
            logger.error("查看话题时失败:",e);
        }
        return null;
    }
}