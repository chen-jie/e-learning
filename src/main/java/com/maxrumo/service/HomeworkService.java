package com.maxrumo.service;

import com.maxrumo.entity.*;
import com.maxrumo.mapper.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class HomeworkService {

	@Autowired
	HomeworkMapper homeworkMapper;
	@Autowired
	CourseMapper courseMapper;
	@Autowired
	SelectedCourseMapper selectedCourseMapper;
	@Autowired
	JudgementMapper judgementMapper;
	@Autowired
	CourseChapterMapper courseChapterMapper;

	@Transactional
	public void addHomework(Homework homework,Integer courseId) {
		Date endTime = homework.getEndTime();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endTime);
		calendar.set(Calendar.HOUR_OF_DAY,23);
		calendar.set(Calendar.MINUTE,59);
		homework.setEndTime(calendar.getTime());

		CourseChapter chapter = courseChapterMapper.selectByPrimaryKey(homework.getChapterId());
		homework.setChapterName(chapter.getTitle());
		homework.setCreateTime(new Date());
		homeworkMapper.insert(homework);

		SelectedCourseExample example = new SelectedCourseExample();
		example.createCriteria().andCourseIdEqualTo(courseId);
		List<SelectedCourse> selectedCourses = selectedCourseMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(selectedCourses)){
			for(SelectedCourse sc : selectedCourses){
				Integer studentId = sc.getStudentId();
				Judgement judgement = new Judgement();
				judgement.setHomeworkId(homework.getId());
				judgement.setStatus(0);
				judgement.setStudentId(studentId);
				judgementMapper.insert(judgement);
			}
		}
	}

	public List<Homework> findByCourseId(int courseId) {
		HomeworkExample example = new HomeworkExample();
		example.createCriteria().andCourseIdEqualTo(courseId);
		return homeworkMapper.selectByExampleWithBLOBs(example);
	}

	public Homework findById(int id) {
		return homeworkMapper.selectByPrimaryKey(id);
	}

	public List<Map> getUnSubmitedReport(int userId) {

		List<Map> result = new ArrayList<Map>();
		Date now = new Date();

		JudgementExample example = new JudgementExample();
		example.createCriteria().andStudentIdEqualTo(userId)
				.andStatusEqualTo(0);
		List<Judgement> judgements = judgementMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(judgements)){

			for(Judgement jg : judgements){
				Homework homework = homeworkMapper.selectByPrimaryKey(jg.getHomeworkId());
				Course course = courseMapper.selectByPrimaryKey(homework.getCourseId());
				Map<Object,Object> map = new HashMap<Object, Object>();

				map.put("judgement",jg);
				map.put("homework",homework);
				map.put("course",course);

				Date endTime = homework.getEndTime();
				if(now.compareTo(endTime) < 0){
					map.put("showBtn",true);
				}
				result.add(map);
			}
		}
		return result;
	}
}
