package com.maxrumo.service;

import com.maxrumo.entity.CourseChapter;
import com.maxrumo.entity.CourseChapterExample;
import com.maxrumo.mapper.CourseChapterMapper;
import com.maxrumo.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class CourseChapterService {

	@Autowired
	CourseChapterMapper courseChapterMapper;

	/**
	 * 根据课程ID获取该课程所有的章节
	 * @return
     */
	public List<CourseChapter> findChapterByCourseId(int courseId){
		CourseChapterExample example = new CourseChapterExample();
		example.createCriteria().andCourseIdEqualTo(courseId);
		List<CourseChapter> list = courseChapterMapper.selectByExample(example);
		return list;
	}

	public void addChapter(CourseChapter chapter) {
		chapter.setCreateTime(new Date());
		courseChapterMapper.insert(chapter);
	}

	@Transactional
	public void deleteByChapterId(int chapterId) {
		//先删除子课时
		courseChapterMapper.deleteChildrenByParentId(chapterId);
		//删除父目录
		courseChapterMapper.deleteByPrimaryKey(chapterId);
	}

	public CourseChapter findChapterById(int chapterId) {
		CourseChapterExample example = new CourseChapterExample();
		example.createCriteria().andIdEqualTo(chapterId);
		List<CourseChapter> list = courseChapterMapper.selectByExample(example);
		return CommonUtil.getFirst(list);
	}
}
