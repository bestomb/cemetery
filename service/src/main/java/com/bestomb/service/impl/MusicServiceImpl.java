package com.bestomb.service.impl;

import com.bestomb.common.Pager;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.FileResponse;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.music.MusicBo;
import com.bestomb.common.util.FileUtilHandle;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.dao.ICemeteryDao;
import com.bestomb.dao.IMusicDao;
import com.bestomb.entity.Cemetery;
import com.bestomb.entity.Music;
import com.bestomb.service.IFileService;
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
    private IFileService fileService;

    /***
     * 音乐删除
     */
    public boolean deleteById(String id) throws EqianyuanException {
        if (StringUtils.isEmpty(id)) {
            logger.warn("deleteById fail , because id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_LACK_OF_REQUEST_PARAMETER);
        }
        return musicDao.deleteByPrimaryKey(id) > 0;
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
        String cemeteryId = music.getCemeteryId();
        // 验证陵园编号
        validCemeteryId(cemeteryId);
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


    /***
     * 根据陵园编号查询陵园背景音乐分页集合
     *
     * @param cemeteryId
     * @param pageNo
     * @param pageSize
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getListByCemeteryId(String cemeteryId, int pageNo, int pageSize) throws EqianyuanException {
        Music music = new Music();
        music.setCemeteryId(cemeteryId);
        Pager page = new Pager(pageNo, pageSize);
        return getListByCondition(music, page);
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
    public void uploadMusic(MultipartFile musicFile, String name, String cemeteryId) throws EqianyuanException {
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
        music.setFileSize(musicFile.getSize());
        music.setFileAddress(musicPath + File.separator + fileResponse.getFileName());
        music.setType(2);
        musicDao.insertSelective(music);

        //将音乐文件从临时上传目录移动到持久目录
        FileUtilHandle.moveFile(fileResponse.getFilePath(), musicPath);
    }

    /***
     * 验证陵园编号是否有值，并且在数据库中存在。否则直接抛出异常
     *
     * @param cemeteryId
     * @throws EqianyuanException
     */
    private void validCemeteryId(String cemeteryId) throws EqianyuanException {
        if (StringUtils.isEmpty(cemeteryId)) {
            logger.warn("getListByCemeteryId fail , because cemeteryId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_ID_IS_EMPTY);
        }
        //根据陵园编号获取陵园
        Cemetery cemetery = cemeteryDao.selectByPrimaryKey(cemeteryId);
        if (ObjectUtils.isEmpty(cemetery)
                || ObjectUtils.isEmpty(cemetery.getId())) {
            logger.info("getListByCemeteryId fail , because cemeteryId [" + cemeteryId + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_DATA_NOT_EXISTS);
        }
    }


}
