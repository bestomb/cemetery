package com.bestomb.dao;


import com.bestomb.common.Pager;
import com.bestomb.entity.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IVideoDao {
    int deleteByPrimaryKey(String id);

    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);

    int countByCondition(@Param("albumId") String albumId);

    List<Video> selectByCondition(@Param("albumId") String albumId, @Param("page") Pager page);
}