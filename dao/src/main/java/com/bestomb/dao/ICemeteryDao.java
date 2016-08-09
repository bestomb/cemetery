package com.bestomb.dao;

import com.bestomb.common.Page;
import com.bestomb.entity.Cemetery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICemeteryDao {
    int deleteByPrimaryKey(String id);

    int insertSelective(Cemetery record);

    Cemetery selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Cemetery record);

    /**
     * 根据会员编号获取陵园总数
     *
     * @return
     */
    Long countByMemberId(@Param("member_id") String memberId);

    /**
     * 获取数据总条数
     *
     * @param communityId 地区（社）编号
     * @return
     */
    Long countByPagination(@Param("community_id") String communityId);

    /**
     * 根据社区编号及分页条件获取分页后查询结果总数
     *
     * @param page
     * @param communityId
     * @return
     */
    Long countyByLimit(@Param("page") Page page, @Param("community_id") String communityId);

    /**
     * 根据对象及分页条件获取分页数据集合
     *
     * @param page        分页对象
     * @param communityId 地区（社）编号
     * @return
     */
    List<Cemetery> selectByPagination(@Param("page") Page page, @Param("community_id") String communityId);

    /**
     * 根据社区编号及陵园编号查找数据所处同社区位置
     *
     * @param communityId
     * @param cemeteryId
     * @return
     */
    Long selectByPositioning(@Param("community_id") String communityId, @Param("id") String cemeteryId);

    /**
     * 根据会员编号查询陵园集合
     *
     * @param memberId
     * @return
     */
    List<Cemetery> selectByMemberId(@Param("member_id") Integer memberId);

    /**
     * 根据陵园编号查询陵园园主编号
     * @param cemeteryIds
     * @return
     */
    List<Cemetery> selectMemberIdByCemeteryIds(@Param("ids") List<Integer> cemeteryIds);
}