package com.maxrumo.service;

import com.maxrumo.entity.Homework;
import com.maxrumo.entity.Judgement;
import com.maxrumo.entity.JudgementExample;
import com.maxrumo.entity.User;
import com.maxrumo.mapper.*;
import com.maxrumo.util.CommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class JudgementService {

	@Autowired
	HomeworkMapper homeworkMapper;
	@Autowired
	SelectedCourseMapper selectedCourseMapper;
	@Autowired
	JudgementMapper judgementMapper;
	@Autowired
	CourseChapterMapper courseChapterMapper;
	@Autowired
	UserMapper userMapper;


	public List<Map> findByHomeworks(List<Homework> list) {
		Date now = new Date();
		List<Map> result = new ArrayList<Map>();
		if(CollectionUtils.isNotEmpty(list)){
			Integer userId = CommonUtil.getCurrentUserId();
			for(Homework homework : list){
				Map<Object,Object> map = new HashMap<Object, Object>();
				if(userId != null){
					Integer homeworkId = homework.getId();
					JudgementExample example = new JudgementExample();
					example.createCriteria()
							.andStudentIdEqualTo(userId)
							.andHomeworkIdEqualTo(homeworkId);
					List<Judgement> judgements = judgementMapper.selectByExample(example);
					Judgement judgement = CommonUtil.getFirst(judgements);
					map.put("judgement",judgement);
				}else{
					map.put("judgement",null);
				}
				map.put("homework",homework);
				Date endTime = homework.getEndTime();
				if(now.compareTo(endTime) < 0){
					map.put("showBtn",true);
				}
				result.add(map);
			}
		}
		return result;
	}

	public Map createReport(int homeworkId) {
		Map result = new HashMap();
		JudgementExample example = new JudgementExample();
		example.createCriteria().andHomeworkIdEqualTo(homeworkId);
		List<Judgement> judgements = judgementMapper.selectByExample(example);
		List<Judgement> submitedList = new ArrayList<Judgement>();
		List<Judgement> unSubmitedList = new ArrayList<Judgement>();
		List<Judgement> unReviewList = new ArrayList<Judgement>();
		List<Map> submitedMap = new ArrayList<Map>();
		List<Map> unSubmitedMap = new ArrayList<Map>();
		List<Map> unReviewMap = new ArrayList<Map>();
		if(CollectionUtils.isNotEmpty(judgements)){
			for(Judgement judgement : judgements){
				if(judgement.getStatus() == 0){
					unSubmitedList.add(judgement);
				}else if(judgement.getStatus() == 1){
					submitedList.add(judgement);
					unReviewList.add(judgement);
				}else{
					submitedList.add(judgement);
				}
			}
		}
		addMap(submitedList, submitedMap);
		addMap(unSubmitedList, unSubmitedMap);
		addMap(unReviewList, unReviewMap);
		result.put("submitedMapList",submitedMap);
		result.put("unSubmitedMapList",unSubmitedMap);
		result.put("unReviewMapList",unReviewMap);
		return result;

	}

	private void addMap(List<Judgement> list, List<Map> mapList) {
		for(Judgement judgement : list){
			Map<Object,Object> map = new HashMap<Object, Object>();
			User user = userMapper.selectByPrimaryKey(judgement.getStudentId());
			map.put("user",user);
			map.put("judgement",judgement);
			mapList.add(map);
		}
	}

	public void judge(int judgementId, int score) {
		Judgement judgement = judgementMapper.selectByPrimaryKey(judgementId);
		judgement.setScore(score);
		judgement.setStatus(2);
		judgementMapper.updateByPrimaryKey(judgement);
	}

	public Judgement findById(int id) {
		return judgementMapper.selectByPrimaryKey(id);
	}

	public void uploadHomework(int judgementId, String filePath) {
		Judgement judgement = judgementMapper.selectByPrimaryKey(judgementId);
		judgement.setHomeworkUrl(filePath);
		judgement.setCreateTime(new Date());
		judgement.setStatus(1);
		judgementMapper.updateByPrimaryKey(judgement);
	}
}
