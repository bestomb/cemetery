package com.bestomb.service;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.cemetery.BiontBo;
import com.bestomb.entity.Biont;

/***
 * 动植物园接口
 *
 * @author qfzhang
 */
public interface IParkService {

    /***
     * 根据条件查询动植物分页列表
     *
     * @param biont
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getPageList(Biont biont, Pager page) throws EqianyuanException;

    /***
     * 根据商品ID查询动植物详情
     *
     * @param biont
     * @return
     * @throws EqianyuanException
     */
    public BiontBo getDetail(String goodsId) throws EqianyuanException;

    /***
     * 动植物升级（动物喂养，植物施肥）
     *
     * @param biont（cemeteryId、goodsId不能为空）
     * @return
     * @throws EqianyuanException
     */
    public boolean upgrade(Biont biont) throws EqianyuanException;

    /***
     * 动植物拾取（动物收取，植物收割）
     *
     * @param biont（cemeteryId、goodsId不能为空）
     * @param memberId
     * @return
     * @throws EqianyuanException
     */
    public boolean pickUp(Biont biont, Integer memberId) throws EqianyuanException;

}
