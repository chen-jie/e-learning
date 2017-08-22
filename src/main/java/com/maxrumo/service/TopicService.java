package com.maxrumo.service;

import com.maxrumo.entity.Topic;
import com.maxrumo.entity.TopicExample;
import com.maxrumo.mapper.TopicMapper;
import com.maxrumo.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TopicService {

	@Autowired
	TopicMapper topicMapper;

	public void addTopic(Topic topic){
		Date now = new Date();
		topic.setCreateTime(now);
		topic.setLastReplyTime(now);
		topic.setAuthor(CommonUtil.getCurrentUser().getNickname());
		topic.setAuthorId(CommonUtil.getCurrentUserId());
		topic.setViewCount(0);
		topic.setReplyCount(0);
		topic.setIsTop(0);
		topicMapper.insert(topic);
	}

	public List<Topic> findAllByCourseId(Integer courseId) {
		TopicExample example = new TopicExample();
		example.createCriteria().andCourseIdEqualTo(courseId);
		example.setOrderByClause("is_top desc,last_reply_time desc");
		List<Topic> topics = topicMapper.selectByExample(example);
		return topics;
	}

	public Topic findById(int id) {
		return topicMapper.selectByPrimaryKey(id);
	}

	public void addViewCount(int id) {
		Topic topic = topicMapper.selectByPrimaryKey(id);
		topic.setViewCount(topic.getViewCount()+1);
		topicMapper.updateByPrimaryKey(topic);
	}

	public void deleteById(int id) {
		topicMapper.deleteByPrimaryKey(id);
	}

	public void toTop(int id) {
		Topic topic = topicMapper.selectByPrimaryKey(id);
		int isTop = topic.getIsTop();
		int result = Math.abs(isTop - 1);
		topic.setIsTop(result);
		topicMapper.updateByPrimaryKey(topic);
	}
}
