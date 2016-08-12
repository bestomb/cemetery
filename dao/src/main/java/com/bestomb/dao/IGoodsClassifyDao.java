package com.bestomb.dao;

import com.bestomb.entity.GoodsClassify;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IGoodsClassifyDao {

    /**
     * 根据父分类编号查找下一级子分类集合
     *
     * @param parentId
     * @return
     */
    List<GoodsClassify> selectLevelOneListByParentId(@Param("parent_id") String parentId);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(@Param("id") String... id);

    int insert(GoodsClassify record);

    /**
     * 商品分类添加
     * @param record
     * @return
     */
    int insertSelective(GoodsClassify record);

    GoodsClassify selectByPrimaryKey(String id);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(GoodsClassify record);

}