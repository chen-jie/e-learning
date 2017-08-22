package com.maxrumo.mapper;

import com.maxrumo.entity.Judgement;
import com.maxrumo.entity.JudgementExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface JudgementMapper {
    int countByExample(JudgementExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Judgement record);

    int insertSelective(Judgement record);

    List<Judgement> selectByExample(JudgementExample example);

    Judgement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Judgement record);

    int updateByPrimaryKey(Judgement record);
}