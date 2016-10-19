package com.bestomb.service.impl;

import com.bestomb.common.Pager;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.FileResponse;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.music.MusicBo;
import com.bestomb.common.util.FileUtilHandle;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.dao.ICemeteryDao;
import com.bestomb.dao.IMusicDao;
import com.bestomb.entity.Cemetery;
import com.bestomb.entity.Music;
import com.bestomb.service.IMusicService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 陵园背景音乐业务逻辑接口实现类
 * Created by jason on 2016-08-08.
 */
@Service
public class MusicServiceImpl implements IMusicService {
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IMusicDao musicDao;

    @Autowired
    private ICemeteryDao cemeteryDao;

    @Autowired
    private CommonService commonService;

    /***
     * 音乐删除
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(String id, Integer memberId) throws EqianyuanException {
        if (StringUtils.isEmpty(id)) {
            logger.warn("deleteById fail , because id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_LACK_OF_REQUEST_PARAMETER);
        }

        //根据音乐编号查询音乐数据
        Music music = musicDao.selectByPrimaryKey(id);

        if (!ObjectUtils.isEmpty(music)
                && !ObjectUtils.isEmpty(music.getId())) {
            //检查当前登录会员是否拥有对该陵园的管理权限
            Cemetery cemetery = commonService.hasPermissionsByCemetery(music.getCemeteryId(), memberId);

            //删除陵园音乐数据
            musicDao.deleteByPrimaryKey(id);

            //获取被删除音乐附件容量(bit)
            int fileSize = music.getFileSize();

            //删除附件
            FileUtilHandle.deleteFile(SessionUtil.getSession().getServletContext().getRealPath("/") + music.getFileAddress());
            //回收附件空间容量
            cemetery.setRemainingStorageSize(cemetery.getRemainingStorageSize() + fileSize);
            //持久化陵园数据
            cemeteryDao.updateByPrimaryKeySelective(cemetery);
        }
        return true;
    }

    /***
     * 封装查询音乐列表方法
     *
     * @param music
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getListByCondition(Music music, Pager page) throws EqianyuanException {
        //检查查询条件中，陵园编号是否存在值，如果有值，则查询陵园自定义上传音乐，如果没有值，则查询系统音乐
        if(!StringUtils.isEmpty(music.getCemeteryId())){
            //获取陵园编号
            String cemeteryId = music.getCemeteryId();
            // 验证陵园编号
            commonService.validCemeteryId(cemeteryId);
        }

        // 根据条件查询音乐列表
        int dataCount = musicDao.countByCondition(music, page);
        page.setTotalRow(dataCount);
        if (dataCount <= 0) {
            logger.info("根据条件查询音乐列表无数据l");
            return new PageResponse(page, null);
        }
        List<Music> musics = musicDao.selectByCondition(music, page);
        if (CollectionUtils.isEmpty(musics)) {
            logger.info("pageNo [" + page.getPageNo() + "], pageSize [" + page.getPageSize() + "], 根据条件查询音乐列表无数据l");
            return new PageResponse(page, null);
        }
        List<MusicBo> musicBos = new ArrayList<MusicBo>();
        for (Music m : musics) {
            MusicBo musicBo = new MusicBo();
            BeanUtils.copyProperties(m, musicBo);
            musicBos.add(musicBo);
        }
        return new PageResponse(page, musicBos);
    }

    /**
     * 对陵园上传音乐
     * 需要先检查陵园剩余容量空间是否可以容纳当前文件大小
     *
     * @param musicFile
     * @param name
     * @param cemeteryId
     * @throws EqianyuanException
     */
    @Transactional(rollbackFor = Exception.class)
    public void uploadMusic(MultipartFile musicFile, String name, String cemeteryId, Integer memberId) throws EqianyuanException {
        //检查当前登录会员是否拥有对该陵园的管理权限
        commonService.hasPermissionsByCemetery(cemeteryId, memberId);

        //根据陵园编号查询陵园当前剩余容量
        Cemetery cemetery = cemeteryDao.selectByPrimaryKey(cemeteryId);
        if (ObjectUtils.isEmpty(cemetery) || ObjectUtils.isEmpty(cemetery.getId())) {
            logger.info("uploadMusic fail , because cemeteryId [" + cemeteryId + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_DATA_NOT_EXISTS);
        }
        //获得当前陵园剩余容量(bit)
        int remainingStorageSize = cemetery.getRemainingStorageSize();
        if (remainingStorageSize <= 0 || remainingStorageSize < musicFile.getSize()) {
            logger.info("uploadMusic fail , because cemeteryId [" + cemeteryId + "] storage size insufficient");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_STORAGE_SIZE_INSUFFICIENT);
        }

        //音乐上传
        FileResponse fileResponse = FileUtilHandle.upload(musicFile);

        //扣减陵园剩余容量
        cemetery.setRemainingStorageSize((int) (remainingStorageSize - musicFile.getSize()));
        cemeteryDao.updateByPrimaryKeySelective(cemetery);

        /**
         * 构建音乐文件持久化目录地址
         * 目录结构：music/陵园编号/文件
         */
        String musicPath = SystemConf.MUSIC_FILE_UPLOAD_FIXED_DIRECTORY.toString() + File.separator + cemeteryId;

        //插入音乐数据
        Music music = new Music();
        music.setName(name);
        music.setCemeteryId(cemeteryId);
        music.setFileSize((int) musicFile.getSize());
        music.setFileAddress(musicPath + File.separator + fileResponse.getFileName());
        music.setType(2);
        musicDao.insertSelective(music);

        //将音乐文件从临时上传目录移动到持久目录
        FileUtilHandle.moveFile(fileResponse.getFilePath(), musicPath);
    }
}
