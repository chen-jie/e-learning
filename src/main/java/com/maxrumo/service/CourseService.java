package com.maxrumo.service;

import com.maxrumo.entity.*;
import com.maxrumo.mapper.CourseMapper;
import com.maxrumo.mapper.SelectedCourseMapper;
import com.maxrumo.util.CommonUtil;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CourseService {

	@Autowired
	CourseMapper courseMapper;
	@Autowired
	SelectedCourseMapper selectedCourseMapper;

	public List<Course> findAll() {
		CourseExample example = new CourseExample();
		example.setOrderByClause("student_count desc");
		return courseMapper.selectByExample(example);
	}

	public Course findById(int id) {
		CourseExample example = new CourseExample();
		example.createCriteria().andIdEqualTo(id);
		List<Course> list = courseMapper.selectByExample(example);
		return CommonUtil.getFirst(list);
	}

	public boolean isSelected(int id) {
		User user = CommonUtil.getCurrentUser();
		if(user!=null){
			SelectedCourseExample example = new SelectedCourseExample();
			SelectedCourseExample.Criteria criteria = example.createCriteria();
			criteria.andStudentIdEqualTo(user.getId());
			criteria.andCourseIdEqualTo(id);
			List<SelectedCourse> list = selectedCourseMapper.selectByExample(example);
			//集合为空，代表该学生没有选这门课程
			return !CollectionUtils.isEmpty(list);
		}
		return false;
	}

	/**
	 * 参加课程
	 * @param id  课程ID
     */
	@Transactional
	public void applyCourse(int id) {
		boolean isSelected = isSelected(id);
		//没选课才执行选课操作
		if(!isSelected){
			Integer studentId = CommonUtil.getCurrentUserId();
			SelectedCourse selectedCourse = new SelectedCourse();
			selectedCourse.setCourseId(id);
			selectedCourse.setStudentId(studentId);
			selectedCourse.setCreateTime(new Date());
			selectedCourseMapper.insert(selectedCourse);

			//更新在学人数
			Course course = courseMapper.selectByPrimaryKey(id);
			course.setStudentCount(course.getStudentCount()+1);
			courseMapper.updateByPrimaryKey(course);
		}
	}

	public void addCourse(String courseName, String description, String filePath) {
		Course course = new Course();
		course.setStudentCount(0);
		course.setCourseName(courseName);
		course.setDescription(description);
		if(StringUtils.isEmpty(filePath)){
			course.setPoster("/static/images/course/default-course-img.jpg");
		}else{
			course.setPoster(filePath);
		}
		User user = CommonUtil.getCurrentUser();
		course.setTeacherId(user.getId());
		course.setTeacherName(user.getNickname());
		course.setCreateDate(new Date());
		courseMapper.insert(course);
	}

	/**
	 * 查询指定教师的所有课程
	 * @param teacherId
	 * @return
     */
	public List<Course> findTeacherCourses(Integer teacherId) {
		CourseExample example = new CourseExample();
		example.createCriteria().andTeacherIdEqualTo(teacherId);
		List<Course> list = courseMapper.selectByExample(example);
		return list;
	}

	public void deleteCourseById(int id) {
		courseMapper.deleteByPrimaryKey(id);
	}

	public void update(Course course) {
		courseMapper.updateByPrimaryKeySelective(course);
	}

	public List<Course> getMySelectedCourse(Integer studentId) {
		List<Course> result = new ArrayList<Course>();
		SelectedCourseExample example = new SelectedCourseExample();
		example.createCriteria().andStudentIdEqualTo(studentId);
		List<SelectedCourse> selectedCourses = selectedCourseMapper.selectByExample(example);
		if(org.apache.commons.collections.CollectionUtils.isNotEmpty(selectedCourses)){
			for(SelectedCourse sc : selectedCourses){
				Course course = courseMapper.selectByPrimaryKey(sc.getCourseId());
				result.add(course);
			}
		}
		return result;
	}
}
