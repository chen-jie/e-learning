package com.maxrumo.mapper;

import com.maxrumo.entity.SelectedCourse;
import com.maxrumo.entity.SelectedCourseExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SelectedCourseMapper {
    int countByExample(SelectedCourseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SelectedCourse record);

    int insertSelective(SelectedCourse record);

    List<SelectedCourse> selectByExample(SelectedCourseExample example);

    SelectedCourse selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SelectedCourse record);

    int updateByPrimaryKey(SelectedCourse record);
}