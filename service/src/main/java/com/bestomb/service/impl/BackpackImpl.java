package com.bestomb.service.impl;

import com.bestomb.common.Pager;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.dao.*;
import com.bestomb.entity.*;
import com.bestomb.service.IBackpackService;
import com.bestomb.service.IGoodsService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private IGoodsOfficialDao goodsOfficialDao; // 商城（官方）Dao接口
    @Autowired
    private IGoodsPersonageDao goodsPersonageDao; // 商城（个人）Dao接口
    @Autowired
    private IPlantsAndAnimalsDao plantsAndAnimalsDao; // 动植物表Dao接口
    @Autowired
    private IParkDao parkDao; // 公园Dao接口
    @Autowired
    private ICemeteryDao cemeteryDao; // 陵园Dao接口
    @Autowired
    private IMemberAccountDao memberAccountDao; // 会员信息Dao接口
    @Autowired
    private IGoodsUseRelatDao goodsUseRelatDao; // 商品使用关联表

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

    @Transactional
    public boolean use(UseGoods useGoods) throws EqianyuanException {
        // 会员编号是否为空
        if (ObjectUtils.isEmpty(useGoods.getMemberId())) {
            logger.warn("use fail , because memberId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
        // 商品编号是否为空
        if (StringUtils.isEmpty(useGoods.getGoodsId())) {
            logger.warn("use fail , because goodsId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.GOODSID_IS_EMPTY);
        }
        // 陵园编号是否为空
        if (StringUtils.isEmpty(useGoods.getCemeteryId())) {
            logger.warn("use fail , because cemeteryId is null.");
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
            } else { // 根据不同的商品类型做不同处理。
                // 1：大门、2：墓碑、3：祭品（香）、4：祭品（蜡烛）、5：祭品（花）、6：普通祭品、7：扩展陵园存储容量、8：增加可建陵园数、9：动物饲料、10：植物肥料、11：祭品（广场）、12：祭品（湖泊）、13：祭品（盆栽植物）、14：祭品（食品）、15：祭品（用品）、16：祭品（金钱）、17：祭品（特色）、18：祭品（守护）、19:：祭品（休闲娱乐）、20：祭品（儿童用品）
                GoodsOfficialWithBLOBs goodsOfficial = goodsOfficialDao.selectByPrimaryKey(useGoods.getGoodsId());
                // 使用对象是墓碑，墓碑编号是否为空
                if (goodsOfficial.getType() == 2 && StringUtils.isEmpty(useGoods.getTombstoneId())) {
                    logger.warn("use fail , because tombstoneId is null.");
                    throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_MASTER_DATA_NOT_EXISTS);
                } else if ((goodsOfficial.getType() == 3 || goodsOfficial.getType() == 4 || goodsOfficial.getType() == 5
                        || goodsOfficial.getType() == 6 || goodsOfficial.getType() == 11 || goodsOfficial.getType() == 12
                        || goodsOfficial.getType() == 13 || goodsOfficial.getType() == 14 || goodsOfficial.getType() == 15
                        || goodsOfficial.getType() == 16 || goodsOfficial.getType() == 17 || goodsOfficial.getType() == 18
                        || goodsOfficial.getType() == 19 || goodsOfficial.getType() == 20) && StringUtils.isEmpty(useGoods.getMasterId())) {
                    // 使用对象是纪念人，纪念人编号是否为空
                    logger.warn("use fail , because marstId is null.");
                    throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_MASTER_DATA_NOT_EXISTS);
                }
                switch (goodsOfficial.getType()) {
                    case 1:
                        // 类型为1、2的商品使用后需要插入商品使用信息关联表（无时间限制），该商品在背包表中数量不变
                        String cemeteryId = useGoods.getCemeteryId();
                        //先去商品使用关联表中根据陵园编号查询数据
                        int dataCntByCemetery = goodsUseRelatDao.countByObjectId(cemeteryId);

                        //如果商品使用关联数据数量为0，则新增数据，如果不为空，则删除数据后再新增
                        if(dataCntByCemetery > 0){
                            goodsUseRelatDao.deleteByObjectId(cemeteryId);
                        }

                        GoodsUseRelat goodsUseRelatByCemetery = new GoodsUseRelat(goodsOfficial.getType(), useGoods.getMemberId(), useGoods.getGoodsId(), cemeteryId, CalendarUtil.getSystemSeconds(), goodsOfficial.getLifecycle());
                        flag = goodsUseRelatDao.insertSelective(goodsUseRelatByCemetery) > 0;
                        if (!flag) {
                            logger.error("会员（" + useGoods.getMemberId() + "）使用商品（" + useGoods.getGoodsId() + "）时，插入商品使用信息关联表失败。");
                        }
                        break;
                    case 2: {
                        // 类型为1、2的商品使用后需要插入商品使用信息关联表（无时间限制），该商品在背包表中数量不变
                        // 如果墓碑编号不为空，则使用墓碑编号
                        String tombstoneId = useGoods.getTombstoneId();
                        //先去商品使用关联表中根据陵园编号查询数据
                        int dataCntByTombstone = goodsUseRelatDao.countByObjectId(tombstoneId);

                        //如果商品使用关联数据数量为0，则新增数据，如果不为空，则删除数据后再新增
                        if(dataCntByTombstone > 0){
                            goodsUseRelatDao.deleteByObjectId(tombstoneId);
                        }

                        GoodsUseRelat goodsUseRelatByTombstone = new GoodsUseRelat(goodsOfficial.getType(), useGoods.getMemberId(), useGoods.getGoodsId(), tombstoneId, CalendarUtil.getSystemSeconds(), goodsOfficial.getLifecycle());
                        flag = goodsUseRelatDao.insertSelective(goodsUseRelatByTombstone) > 0;
                        if (!flag) {
                            logger.error("会员（" + useGoods.getMemberId() + "）使用商品（" + useGoods.getGoodsId() + "）时，插入商品使用信息关联表失败。");
                        }
                        break;
                    }
                    case 3:
                    case 4:
                    case 5:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                    case 20:
                    case 6: {
                        // 类型为3、4、5、6、11、12/13/14/15/16/17/18/19/20的商品使用后需要插入商品使用信息关联表
                        String objectId = useGoods.getMasterId();
//                        if(goodsOfficial.getType() != 6){
//                            // 如果纪念人编号不为空，则使用纪念人编号
//                            if (!StringUtils.isEmpty(useGoods.getMasterId())) {
//                                objectId = useGoods.getMasterId();
//                            }
//                        }

                        GoodsUseRelat goodsUseRelat = new GoodsUseRelat(goodsOfficial.getType(), useGoods.getMemberId(), useGoods.getGoodsId(), objectId, CalendarUtil.getSystemSeconds(), goodsOfficial.getLifecycle());
                        flag = goodsUseRelatDao.insertSelective(goodsUseRelat) > 0;
                        if (flag) {
                            // 成功后需要修改该商品在背包表中的数量
                            flag = modifyBackpackCount(useGoods, entity);
                            if (!flag) {
                                logger.error("会员（" + useGoods.getMemberId() + "）使用祭品（" + useGoods.getGoodsId() + "）时，修改该商品在背包表中的数量失败。");
                            }
                        } else {
                            logger.error("会员（" + useGoods.getMemberId() + "）使用祭品（" + useGoods.getGoodsId() + "）时，插入商品使用信息关联表失败。");
                        }
                        break;
                    }
                    case 7: {
                        // 增加陵园信息表cemetery中的剩余存储容量和总存储容量，并修改该商品在背包表中的数量
                        int extraStorage = Integer.valueOf(goodsOfficial.getExtendAttribute());
                        Cemetery oldCemetery = cemeteryDao.selectByPrimaryKey(useGoods.getCemeteryId());
                        Cemetery newCemetery = new Cemetery();
                        newCemetery.setId(Integer.valueOf(useGoods.getCemeteryId()));
                        newCemetery.setRemainingStorageSize(oldCemetery.getRemainingStorageSize() + extraStorage); // 增加剩余存储容量
                        newCemetery.setStorageSize(oldCemetery.getStorageSize() + extraStorage); // 增加总存储容量
                        // 修改陵园信息表
                        flag = cemeteryDao.updateByPrimaryKeySelective(newCemetery) > 0;
                        if (flag) {
                            // 成功后修改该商品在背包表中的数量
                            flag = modifyBackpackCount(useGoods, entity);
                            if (!flag) {
                                logger.error("会员（" + useGoods.getMemberId() + "）使用商品（" + useGoods.getGoodsId() + "）扩展陵园（" + useGoods.getCemeteryId() + "）存储容量时，修改该商品在背包表中的数量失败。");
                            }
                        } else {
                            logger.error("会员（" + useGoods.getMemberId() + "）使用商品（" + useGoods.getGoodsId() + "）扩展陵园（" + useGoods.getCemeteryId() + "）存储容量时，修改陵园信息表失败，扩展容量为：" + extraStorage + " 字节");
                        }
                        break;
                    }
                    case 8: {
                        MemberAccount oldMemberAccount = memberAccountDao.selectByPrimaryKey(useGoods.getMemberId());
                        MemberAccount newMemberAccount = new MemberAccount();
                        newMemberAccount.setMemberId(useGoods.getMemberId());
                        newMemberAccount.setConstructionCount(oldMemberAccount.getConstructionCount() + 1);
                        // 将会员帐号信息表member_account中的可建设陵园总数+1
                        flag = memberAccountDao.updateByPrimaryKeySelective(newMemberAccount) > 0;
                        if (flag) {
                            // 并修改该商品在背包表中的数量
                            flag = modifyBackpackCount(useGoods, entity);
                            if (!flag) {
                                logger.error("会员（" + useGoods.getMemberId() + "）使用商品（" + useGoods.getGoodsId() + "）扩展可建设陵园总数时，修改该商品在背包表中的数量失败");
                            }
                        } else {
                            logger.error("会员（" + useGoods.getMemberId() + "）使用商品（" + useGoods.getGoodsId() + "）扩展可建设陵园总数时，修改会员帐号信息表失败");
                        }
                        break;
                    }
                    case 9:
                    case 10: {
                        Park park = parkDao.selectByPrimaryKey(useGoods.getParkId());
                        if (ObjectUtils.isEmpty(park)) {
                            logger.warn("动植物喂养失败，因为不存在喂养对象");
                            throw new EqianyuanException(ExceptionMsgConstant.PARK_OBJECT_IS_EMPTY);
                        }
                        //生命值加1
                        park.setLifeValue(park.getLifeValue() + 1);
                        flag = parkDao.updateByPrimaryKeySelective(park) > 0;
                        if (flag) {
                            // 成功后修改该商品在背包表中的数量
                            flag = modifyBackpackCount(useGoods, entity);
                            if (!flag) {
                                logger.error("会员（" + useGoods.getMemberId() + "）使用商品（" + useGoods.getGoodsId() + "）喂养/饲养时，修改该商品在背包表中的数量失败。");
                            }
                        } else {
                            logger.error("会员（" + useGoods.getMemberId() + "）使用商品（" + useGoods.getGoodsId() + "）喂养/饲养时，修改动植物生命周期失败");
                        }
                        break;
                    }
                    default:
                        break;
                }
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
        // 发布的商品价格是否为空
        if (ObjectUtils.isEmpty(goodsPersonage.getPrice())) {
            logger.warn("sell fail , because price is null.");
            throw new EqianyuanException(ExceptionMsgConstant.GOODS_PRICE_IS_EMPTY);
        }
        // 发布的商品数量是否为空
        if (ObjectUtils.isEmpty(goodsPersonage.getRepertory())) {
            logger.warn("sell fail , because count is null.");
            throw new EqianyuanException(ExceptionMsgConstant.GOODS_COUNT_IS_EMPTY);
        }
        // 查询该商品背包信息
        Backpack entity = backpackDao.selectByCondition(goodsPersonage.getMemberId(), goodsPersonage.getPlantsAndAnimalsId());
        // 如果背包数量小于发售的数量，则抛出错误
        boolean flag;
        if (entity.getCount() < goodsPersonage.getRepertory()) {
            logger.warn("sell fail , because backpack goods is not enough count.");
            throw new EqianyuanException(ExceptionMsgConstant.GOODS_COUNT_IS_NOT_ENOUGH);
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
