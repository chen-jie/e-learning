package com.maxrumo.mapper;

import com.maxrumo.entity.CourseChapter;
import com.maxrumo.entity.CourseChapterExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CourseChapterMapper {
    int countByExample(CourseChapterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CourseChapter record);

    int insertSelective(CourseChapter record);

    List<CourseChapter> selectByExample(CourseChapterExample example);

    CourseChapter selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CourseChapter record);

    int updateByPrimaryKey(CourseChapter record);

    void deleteChildrenByParentId(int chapterId);
}