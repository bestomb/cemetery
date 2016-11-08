package com.bestomb.service.impl;

import com.bestomb.common.Pager;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.dao.IBackpackDao;
import com.bestomb.dao.IGoodsPersonageDao;
import com.bestomb.dao.IParkDao;
import com.bestomb.dao.IPlantsAndAnimalsDao;
import com.bestomb.entity.*;
import com.bestomb.service.IBackpackService;
import com.bestomb.service.IGoodsService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/***
 * 背包接口实现类
 *
 * @author qfzhang
 */
@Service
public class BackpackImpl implements IBackpackService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IBackpackDao backpackDao;    //背包dao接口
    @Autowired
    private IGoodsService goodsService;        //商品dao接口
    @Autowired
    private IGoodsPersonageDao goodsPersonageDao; // 商城（个人）Dao接口
    @Autowired
    private IPlantsAndAnimalsDao plantsAndAnimalsDao; // 动植物表Dao接口
    @Autowired
    private IParkDao parkDao; // 公园Dao接口

    /***
     * 根据查询条件查询会员背包商品分页列表
     *
     * @param backpack
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getGoodsPageList(Backpack backpack, Pager page) throws EqianyuanException {

        // 会员编号是否为空
        if (ObjectUtils.isEmpty(backpack.getMemberId())) {
            logger.warn("query fail , because memberId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
        // 背包来源（商城类型）为空
        if (ObjectUtils.isEmpty(backpack.getSource())) {
            logger.warn("query fail , because source is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MALLTYPE_IS_EMPTY);
        }
        // 先查询总数
        int dataCount = backpackDao.getPageListCount(backpack);
        if (dataCount == 0) {
            logger.info("根据条件查询背包分页列表无数据l");
            return new PageResponse(page, null);
        }
        page.setTotalRow(dataCount);
        // 再查询分页数据
        List<Backpack> entityList = backpackDao.getPageList(backpack, page);
        return new PageResponse(page, entityList);

    }

    /***
     * 查看背包商品详情
     *
     * @param backpack
     * @return
     */
    public Object getGoodsDetail(Backpack backpack) throws EqianyuanException {

        Mall mall = new Mall(backpack.getSource(), backpack.getGoodsId());
        return goodsService.getGoodsById(mall);
    }

    public boolean use(UseGoods useGoods) throws EqianyuanException {
        // 会员编号是否为空
        if (ObjectUtils.isEmpty(useGoods.getMemberId())) {
            logger.warn("sell fail , because memberId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
        // 商品编号是否为空
        if (StringUtils.isEmpty(useGoods.getGoodsId())) {
            logger.warn("sell fail , because goodsId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.GOODSID_IS_EMPTY);
        }
        // 陵园编号是否为空
        if (StringUtils.isEmpty(useGoods.getCemeteryId())) {
            logger.warn("sell fail , because cemeteryId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_ID_IS_EMPTY);
        }
        // 查询该商品背包信息
        Backpack entity = backpackDao.selectByCondition(useGoods.getMemberId(), useGoods.getGoodsId());
        boolean flag = false;
        if (entity.getCount() == 0) { // 背包商品数量为0，该商品无效，直接返回false
            logger.error("会员（" + useGoods.getMemberId() + "）使用商品（" + useGoods.getGoodsId() + "）时，该商品在背包中数量为0 .");
            return flag;
        }
        // 如果来源是商城（个人）或动植物表，则表示该商品是动植物，将该动植物放到公园表里后，扣减该商品
        if (entity.getSource() == 2 || entity.getSource() == 3) {
            flag = pushParkAndModifyBackpack(useGoods, entity);
            // 如果来源是商城（官方）
        } else if (entity.getSource() == 1) {
            PlantsAndAnimals plantsAndAnimals = plantsAndAnimalsDao.selectByPrimaryKey(useGoods.getGoodsId());
            // 如果是动植物商品
            if (!ObjectUtils.isEmpty(plantsAndAnimals)) {
                flag = pushParkAndModifyBackpack(useGoods, entity);
            } else { // 直接修改该商品背包数量？
                flag = modifyBackpackCount(useGoods, entity);
            }
        }

        return flag;
    }

    /***
     * 动植物商品，将该商品放入公园中，并修改背包中该商品数量（删除或减1）
     *
     * @param useGoods
     * @param entity
     * @return
     */
    private boolean pushParkAndModifyBackpack(UseGoods useGoods, Backpack entity) {
        // 将该商品放到公园表里
        boolean flag = parkDao.insert(new Park(useGoods.getCemeteryId(), useGoods.getGoodsId())) > 0;
        if (flag) {
            flag = modifyBackpackCount(useGoods, entity);
        } else {
            logger.error("会员（" + useGoods.getMemberId() + "）使用商品（" + useGoods.getGoodsId() + "）时，插入公园表失败.");
        }
        return flag;
    }

    /***
     * 修改背包中该商品数量（删除或减1）
     *
     * @param useGoods
     * @param entity
     * @return
     */
    private boolean modifyBackpackCount(UseGoods useGoods, Backpack entity) {
        boolean flag = false;
        if (entity.getCount() == 1) { // 该商品只有一条，则直接在背包中删除该商品
            flag = backpackDao.deleteByPrimaryKey(entity.getId()) > 0;
            if (!flag) {
                logger.error("会员（" + useGoods.getMemberId() + "）使用商品（" + useGoods.getGoodsId() + "）时，插入公园表后删除该商品失败.");
            }
        } else if (entity.getCount() > 1) { // 该商品只有一条，则在背包中将该商品数量减1
            entity.setCount(entity.getCount() - 1);
            flag = backpackDao.updateByPrimaryKey(entity) > 0;
            if (!flag) {
                logger.error("会员（" + useGoods.getMemberId() + "）使用商品（" + useGoods.getGoodsId() + "）时，插入公园表后将该商品从背包中数量减1失败.");
            }
        }
        return flag;
    }

    public boolean sell(GoodsPersonage goodsPersonage) throws EqianyuanException {
        // 会员编号是否为空
        if (ObjectUtils.isEmpty(goodsPersonage.getMemberId())) {
            logger.warn("sell fail , because memberId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
        // 商品编号是否为空
        if (StringUtils.isEmpty(goodsPersonage.getPlantsAndAnimalsId())) {
            logger.warn("sell fail , because goodsId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.GOODSID_IS_EMPTY);
        }
        // 查询该商品背包信息
        Backpack entity = backpackDao.selectByCondition(goodsPersonage.getMemberId(), goodsPersonage.getPlantsAndAnimalsId());
        // 如果背包数量小于发售的数量，则抛出错误
        boolean flag;
        if (entity.getCount() < goodsPersonage.getRepertory()) {
            logger.warn("sell fail , because backpack goods is not enough count.");
            throw new EqianyuanException(ExceptionMsgConstant.GOODSID_IS_EMPTY);
        } else if (entity.getCount() == goodsPersonage.getRepertory()) { // 如果背包数量等于发售的数量，则发售成功后删除背包商品
            flag = goodsPersonageDao.insert(goodsPersonage) > 0;
            if (flag) {
                flag = backpackDao.deleteByPrimaryKey(entity.getId()) > 0;
                if (!flag) {
                    logger.error("会员（" + goodsPersonage.getMemberId() + "）发售商品（" + goodsPersonage.getPlantsAndAnimalsId() + "）时，删除背包该商品失败.");
                }
            } else {
                logger.error("会员（" + goodsPersonage.getMemberId() + "）发售商品（" + goodsPersonage.getPlantsAndAnimalsId() + "）时，插入个人商城表失败.");
            }
        } else { // 如果背包数量大于发售的数量，则发售成功后将背包商品数量减1
            flag = goodsPersonageDao.insert(goodsPersonage) > 0;
            if (flag) {
                entity.setCount(entity.getCount() - 1);
                flag = backpackDao.updateByPrimaryKey(entity) > 0;
                if (!flag) {
                    logger.error("会员（" + goodsPersonage.getMemberId() + "）发售商品（" + goodsPersonage.getPlantsAndAnimalsId() + "）时，将背包该商品数量减1失败.");
                }
            } else {
                logger.error("会员（" + goodsPersonage.getMemberId() + "）发售商品（" + goodsPersonage.getPlantsAndAnimalsId() + "）时，插入个人商城表失败.");
            }
        }

        return flag;
    }


}
