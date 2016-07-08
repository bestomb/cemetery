package com.bestomb.dao;

import com.bestomb.entity.SystemMenu;

import java.util.List;

public interface ISystemMenuDao {
    /**
     * 查询所有数据集合
     *
     * @return
     */
    List<SystemMenu> selectByList();
}