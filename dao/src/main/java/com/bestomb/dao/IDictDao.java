package com.bestomb.dao;

import java.util.List;

import com.bestomb.entity.Dict;
import com.bestomb.entity.DictKey;

public interface IDictDao {
    int deleteByPrimaryKey(DictKey key);

    int insert(Dict record);

    int insertSelective(Dict record);

    Dict selectByPrimaryKey(DictKey key);

    int updateByPrimaryKeySelective(Dict record);

    int updateByPrimaryKey(Dict record);
    
    /***
     * 获取所有字典信息
     * @return
     */
    List<Dict> getAllDicts();
    
    /***
     * 根据条件查询字典信息
     * @param record
     * @return
     */
    List<Dict> getDictsByDictKey(DictKey record);
    
}