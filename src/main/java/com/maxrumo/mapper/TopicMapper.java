package com.maxrumo.mapper;

import com.maxrumo.entity.Topic;
import com.maxrumo.entity.TopicExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TopicMapper {
    int countByExample(TopicExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Topic record);

    int insertSelective(Topic record);

    List<Topic> selectByExampleWithBLOBs(TopicExample example);

    List<Topic> selectByExample(TopicExample example);

    Topic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Topic record);

    int updateByPrimaryKeyWithBLOBs(Topic record);

    int updateByPrimaryKey(Topic record);
}