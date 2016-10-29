package com.bestomb.dao;

import com.bestomb.common.Pager;
import com.bestomb.entity.PhotoAlbum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IPhotoAlbumDao {
    int deleteByPrimaryKey(String id);

    int insert(PhotoAlbum record);

    int insertSelective(PhotoAlbum record);

    PhotoAlbum selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PhotoAlbum record);

    int updateByPrimaryKey(PhotoAlbum record);

    int countByCondition(@Param("masterId") String masterId);

    List<PhotoAlbum> selectByCondition(@Param("masterId") String masterId, @Param("page") Pager page);
}