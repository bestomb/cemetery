package com.bestomb.dao;

import com.bestomb.entity.ModelClassify;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IModelClassifyDao {
    /**
     * 根据序列编号删除数据
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(@Param("id") String... id);

    int insertSelective(ModelClassify record);

    ModelClassify selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ModelClassify record);

    /**
     * 根据父分类编号查找下一级子分类集合
     *
     * @param parentId
     * @return
     */
    List<ModelClassify> selectLevelOneListByParentId(@Param("parent_id") String parentId);
}