package com.maxrumo.service;

import com.maxrumo.entity.Reply;
import com.maxrumo.entity.ReplyExample;
import com.maxrumo.entity.Topic;
import com.maxrumo.entity.User;
import com.maxrumo.mapper.ReplyMapper;
import com.maxrumo.mapper.TopicMapper;
import com.maxrumo.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class ReplyService {

	@Autowired
	ReplyMapper replyMapper;

	@Autowired
	TopicMapper topicMapper;

	public List<Reply> findByTopicId(int topicId){
		ReplyExample example = new ReplyExample();
		example.createCriteria().andTopicIdEqualTo(topicId);
		List<Reply> replies = replyMapper.selectByExampleWithBLOBs(example);
		return replies;
	}

	public Reply findById(int id){
		return replyMapper.selectByPrimaryKey(id);
	}

	@Transactional
	public void addReply(Reply reply){
		//插入回复
		Date date = new Date();
		reply.setCreateTime(date);
		User currentUser = CommonUtil.getCurrentUser();
		reply.setUserId(currentUser.getId());
		reply.setRoleId(currentUser.getRoleId());
		reply.setUsername(currentUser.getNickname());
		replyMapper.insert(reply);

		//修改主题的信息
		Topic topic = topicMapper.selectByPrimaryKey(reply.getTopicId());
		topic.setReplyCount(topic.getReplyCount()+1);
		topic.setLastReplyTime(date);
		topicMapper.updateByPrimaryKey(topic);
	}

	@Transactional
	public void deleteById(int id) {
		Reply reply = replyMapper.selectByPrimaryKey(id);
		if(reply != null){
			//修改主题信息，回复数减一
			int topicId = reply.getTopicId();
			Topic topic = topicMapper.selectByPrimaryKey(topicId);
			topic.setReplyCount(topic.getReplyCount()-1);
			topicMapper.updateByPrimaryKey(topic);
			//删除回复
			replyMapper.deleteByPrimaryKey(id);
		}
	}
}
