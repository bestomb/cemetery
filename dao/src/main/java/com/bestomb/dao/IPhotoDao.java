package com.bestomb.dao;

import com.bestomb.common.Pager;
import com.bestomb.entity.Photo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IPhotoDao {
    int deleteByPrimaryKey(String id);

    int insert(Photo record);

    int insertSelective(Photo record);

    Photo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Photo record);

    int updateByPrimaryKey(Photo record);

    int countByCondition(@Param("albumId") String albumId);

    List<Photo> selectByCondition(@Param("albumId") String albumId, @Param("page") Pager page);
}