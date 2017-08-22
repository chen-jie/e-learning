package com.maxrumo.mapper;

import com.maxrumo.entity.Course;
import com.maxrumo.entity.CourseExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CourseMapper {
    int countByExample(CourseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Course record);

    int insertSelective(Course record);

    List<Course> selectByExample(CourseExample example);

    Course selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);
}