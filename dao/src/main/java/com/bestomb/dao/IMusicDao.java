package com.bestomb.dao;

import com.bestomb.common.Page;
import com.bestomb.common.Pager;
import com.bestomb.entity.Music;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IMusicDao {
    int deleteByPrimaryKey(String id);

    int insertSelective(Music record);

    Music selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Music record);

    /**
     * 获取数据总条数
     *
     * @param cemeteryId 陵园编号
     * @return
     */
    Long countByPagination(@Param("cemetery_id") String cemeteryId);

    /**
     * 根据对象及分页条件获取分页数据集合
     *
     * @param page       分页对象
     * @param cemeteryId 陵园编号
     * @return
     */
    List<Music> selectByPagination(@Param("page") Page page, @Param("cemetery_id") String cemeteryId);
    
    
    int countByCondition(@Param("music") Music music, @Param("page") Pager page);
    
    List<Music> selectByCondition(@Param("music") Music music, @Param("page") Pager page);
    
}