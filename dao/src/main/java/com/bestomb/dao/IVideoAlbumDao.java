package com.bestomb.dao;

import com.bestomb.common.Pager;
import com.bestomb.entity.VideoAlbum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IVideoAlbumDao {
    int deleteByPrimaryKey(String id);

    int insert(VideoAlbum record);

    int insertSelective(VideoAlbum record);

    VideoAlbum selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(VideoAlbum record);

    int updateByPrimaryKey(VideoAlbum record);

    int countByCondition(@Param("masterId") String masterId);

    List<VideoAlbum> selectByCondition(@Param("masterId") String masterId, @Param("page") Pager page);
}