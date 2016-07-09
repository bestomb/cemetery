package com.bestomb.dao;

import com.bestomb.entity.SystemMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISystemMenuDao {
    /**
     * 查询所有数据集合
     *
     * @return
     */
    List<SystemMenu> selectByList();

}