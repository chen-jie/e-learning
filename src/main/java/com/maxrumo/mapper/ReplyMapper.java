package com.maxrumo.mapper;

import com.maxrumo.entity.Reply;
import com.maxrumo.entity.ReplyExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {
    int countByExample(ReplyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Reply record);

    int insertSelective(Reply record);

    List<Reply> selectByExampleWithBLOBs(ReplyExample example);

    List<Reply> selectByExample(ReplyExample example);

    Reply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Reply record);

    int updateByPrimaryKeyWithBLOBs(Reply record);

    int updateByPrimaryKey(Reply record);
}