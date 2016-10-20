package com.bestomb.service;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.MasterEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.master.MasterBo;

import java.util.List;

public interface IMasterService {

    /**
     * 根据陵园编号和分页信息查询陵园纪念人分页信息
     */
    PageResponse getList(int pageNo, int pageSize, Integer cemeteryId) throws EqianyuanException;

    /**
     * 查询墓碑纪念人详细信息
     *
     * @param id
     * @return
     * @throws EqianyuanException
     */
    MasterBo queryById(String id) throws EqianyuanException;

    /**
     * 根据陵园墓碑编号查询墓中纪念人
     *
     * @param tombstoneId
     * @return
     * @throws EqianyuanException
     */
    List<MasterBo> queryByTombstone(String tombstoneId) throws EqianyuanException;

    /**
     * 添加墓碑纪念人
     *
     * @param masterEditRequest
     * @throws EqianyuanException
     */
    void addMaster(MasterEditRequest masterEditRequest) throws EqianyuanException;

    /**
     * 修改墓碑纪念人
     *
     * @param masterEditRequest
     * @throws EqianyuanException
     */
    void modifyMaster(MasterEditRequest masterEditRequest) throws EqianyuanException;

    /**
     * 删除墓碑纪念人
     *
     * @param masterId
     * @param cemeteryId
     * @param memberId
     * @throws EqianyuanException
     */
    void delMaster(String masterId, String cemeteryId, Integer memberId) throws EqianyuanException;

}
