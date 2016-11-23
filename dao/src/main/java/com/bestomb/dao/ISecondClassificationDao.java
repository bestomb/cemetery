package com.bestomb.dao;

import com.bestomb.common.Pager;
import com.bestomb.entity.SecondClassification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISecondClassificationDao {
    int deleteByPrimaryKey(@Param("id") String... id);

    int insert(SecondClassification record);

    int insertSelective(SecondClassification record);

    SecondClassification selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SecondClassification record);

    int updateByPrimaryKey(SecondClassification record);

    /**
     * 获取数据总条数
     *
     * @return
     */
    int countByPagination(@Param("first_classify") Integer firstClassify);

    /**
     * 根据对象及分页条件获取分页数据集合
     *
     * @return
     */
    List<SecondClassification> selectByPagination(@Param("page") Pager page, @Param("first_classify") Integer firstClassify);

    /**
     * 根据商品一级分类查询二级分类集合
     *
     * @param firstClassify
     * @return
     */
    List<SecondClassification> selectByFirstClassify(@Param("first_classify") Integer firstClassify);
}