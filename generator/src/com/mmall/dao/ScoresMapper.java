package com.mmall.dao;

import com.mmall.model.Scores;

public interface ScoresMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Scores record);

    int insertSelective(Scores record);

    Scores selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Scores record);

    int updateByPrimaryKeyWithBLOBs(Scores record);

    int updateByPrimaryKey(Scores record);
}