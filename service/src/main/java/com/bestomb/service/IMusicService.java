package com.bestomb.service;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.entity.Music;
import org.springframework.web.multipart.MultipartFile;

/**
 * 陵园背景音乐业务接口
 * Created by jason on 2016-07-15.
 */
public interface IMusicService {

    /***
     * 删除音乐
     *
     * @param id
     * @param memberId
     * @return
     * @throws EqianyuanException
     */
    public boolean deleteById(String id, Integer memberId) throws EqianyuanException;

    /***
     * 根据条件查询音乐分页集合
     *
     * @param music
     * @param page
     * @return
     * @throws EqianyuanException
     */
    PageResponse getListByCondition(Music music, Pager page) throws EqianyuanException;

    /**
     * 对陵园上传音乐
     *
     * @param musicFile
     * @param name
     * @param cemeteryId
     * @param memberId
     * @throws EqianyuanException
     */
    void uploadMusic(MultipartFile musicFile, String name, String cemeteryId, Integer memberId) throws EqianyuanException;


}
