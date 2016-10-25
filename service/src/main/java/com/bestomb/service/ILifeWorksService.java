package com.bestomb.service;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.lifeWorksCollection.lifeWorks.LifeWorksEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.master.lifeWorksCollection.lifeWorks.LifeWorksBo;

/**
 * 陵园纪念人作品集作品接口
 * Created by jason on 2016-10-18.
 */
public interface ILifeWorksService {

    /**
     * 为作品集添加作品
     *
     * @param lifeWorksEditRequest
     * @throws EqianyuanException
     */
    void add(LifeWorksEditRequest lifeWorksEditRequest) throws EqianyuanException;

    /**
     * 根据作品编号删除作品数据
     *
     * @param lifeWorksId
     * @param cemeteryId
     * @param memberId
     * @throws EqianyuanException
     */
    void del(String lifeWorksId, String cemeteryId, Integer memberId) throws EqianyuanException;

    /***
     * 根据作品及编号编号查询作品分页集合
     *
     * @param lifeWorksCollectionId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    PageResponse getList(String lifeWorksCollectionId, Pager page) throws EqianyuanException;

    /**
     * 根据作品编号查询作品详情
     *
     * @param lifeWorksId
     * @return
     * @throws EqianyuanException
     */
    LifeWorksBo getInfo(String lifeWorksId) throws EqianyuanException;
}
