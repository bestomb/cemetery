package com.bestomb.dao;

import com.bestomb.common.Page;
import com.bestomb.entity.SpecialHoliday;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISpecialHolidayDao {

    /**
     * 删除特殊节日
     * @param id
     * @return
     */
    int deleteByPrimaryKey(@Param("id") String... id);

    int insert(SpecialHoliday record);

    /**
     * 添加特殊节日
     * @param record
     * @return
     */
    int insertSelective(SpecialHoliday record);

    SpecialHoliday selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SpecialHoliday record);

    int updateByPrimaryKey(SpecialHoliday record);

    /**
     * 分页查询
     */
    List<SpecialHoliday> selectByPagination(@Param("page") Page page);

    /**
     * 根据数据对象获取总条数
     */
    Long countByPagination();
}