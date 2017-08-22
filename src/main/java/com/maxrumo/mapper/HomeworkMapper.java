package com.maxrumo.mapper;

import com.maxrumo.entity.Homework;
import com.maxrumo.entity.HomeworkExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HomeworkMapper {
    int countByExample(HomeworkExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Homework record);

    int insertSelective(Homework record);

    List<Homework> selectByExampleWithBLOBs(HomeworkExample example);

    List<Homework> selectByExample(HomeworkExample example);

    Homework selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Homework record);

    int updateByPrimaryKeyWithBLOBs(Homework record);

    int updateByPrimaryKey(Homework record);
}