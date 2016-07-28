package com.bestomb.service.impl;

import com.bestomb.common.Page;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.model.ModelByEditRequest;
import com.bestomb.common.response.FileResponse;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.model.ModelBo;
import com.bestomb.common.util.FileUtilHandle;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.dao.IModelClassifyDao;
import com.bestomb.dao.IModelDao;
import com.bestomb.entity.Model;
import com.bestomb.entity.ModelClassify;
import com.bestomb.service.IFileService;
import com.bestomb.service.IModelService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 模型业务逻辑接口实现类
 * Created by jason on 2016-07-24.
 */
@Service
public class ModelServiceImpl implements IModelService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IFileService fileService;

    @Autowired
    private IModelDao modelDao;

    @Autowired
    private IModelClassifyDao modelClassifyDao;

    //模型名称DB许可字节长度
    private static final int MODEL_NAME_MAX_BYTES_BY_DB = 60;

    /**
     * 根据主键编号删除数据
     *
     * @param id
     * @throws EqianyuanException
     */
    public void removeByIds(String... id) throws EqianyuanException {
        if (ObjectUtils.isEmpty(id)) {
            logger.info("removeByIds fail , because id is null, a full table delete is prohibited");
            throw new EqianyuanException(ExceptionMsgConstant.MODEL_ID_IS_EMPTY);
        }

        //根据主键查询数据
        List<Model> modelList = modelDao.selectById(id);
        if (CollectionUtils.isEmpty(modelList)) {
            return;
        }

        //删除数据
        modelDao.deleteByPrimaryKey(id);

        for (Model model : modelList) {
            //删除对应实体模型
            FileUtilHandle.deleteFile(SessionUtil.getSession().getServletContext().getRealPath("/") + model.getFileAddress());
        }
    }

    /**
     * 添加数据
     *
     * @param modelByEditRequest
     * @throws EqianyuanException
     */
    public void add(HttpServletRequest request, ModelByEditRequest modelByEditRequest) throws EqianyuanException {

        //模型名是否为空
        if (StringUtils.isEmpty(modelByEditRequest.getName())) {
            logger.info("add fail , because name is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MODEL_NAME_IS_EMPTY);
        }

        //模型归属分类是否为空
        if (StringUtils.isEmpty(modelByEditRequest.getClassifyId())) {
            logger.info("add fail , because classify id is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MODEL_CLASSIFY_ID_IS_EMPTY);
        }

        //模型分类名称内容长度是否超出DB许可长度
        try {
            if (modelByEditRequest.getName().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > MODEL_NAME_MAX_BYTES_BY_DB) {
                logger.info("add fail , because name [" + modelByEditRequest.getName()
                        + "] bytes greater than" + MODEL_NAME_MAX_BYTES_BY_DB);
                throw new EqianyuanException(ExceptionMsgConstant.MODEL_NAME_TO_LONG);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("add fail , because name [" + modelByEditRequest.getName()
                    + "] getBytes(" + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
        }

        //模型文件上传
        List<FileResponse> fileResponses = fileService.upload(request);

        if (CollectionUtils.isEmpty(fileResponses)) {
            logger.info("add fail , because file upload error");
            throw new EqianyuanException(ExceptionMsgConstant.FILE_UPDATE_ERROR);
        }

        FileResponse fileResponse = fileResponses.get(0);

        //模型实体文件地址是否为空
        if (StringUtils.isEmpty(fileResponse.getFilePath())) {
            logger.info("add fail , because file address is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MODEL_FILE_ADDRESS_IS_EMPTY);
        }

        //根据分类编号查询分类对象
        ModelClassify modelClassify = modelClassifyDao.selectByPrimaryKey(modelByEditRequest.getClassifyId());
        if (ObjectUtils.isEmpty(modelClassify)
                || StringUtils.isEmpty(modelClassify.getId())) {
            logger.info("add fail , because model classify id [" + modelByEditRequest.getClassifyId() + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MODEL_CLASSIFY_DATA_NOT_EXISTS);
        }

        //移动临时附件到持久目录
        FileUtilHandle.moveFile(fileResponse.getFileName(), fileResponse.getFilePath(), SystemConf.MODEL_FILE_UPLOAD_FIXED_DIRECTORY.toString());

        //持久化模型数据
        Model model = new Model();
        BeanUtils.copyProperties(modelByEditRequest, model);
        model.setFileAddress(SystemConf.MODEL_FILE_UPLOAD_FIXED_DIRECTORY.toString() + File.separator + fileResponse.getFileName());
        modelDao.insertSelective(model);
    }

    /**
     * 修改模型数据
     *
     * @param modelByEditRequest
     * @throws EqianyuanException
     */
    public void modify(HttpServletRequest request, ModelByEditRequest modelByEditRequest) throws EqianyuanException {
        //主键是否为空
        if (StringUtils.isEmpty(modelByEditRequest.getId())) {
            logger.info("modify fail , because id is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MODEL_ID_IS_EMPTY);
        }

        //模型名是否为空
        if (StringUtils.isEmpty(modelByEditRequest.getName())) {
            logger.info("modify fail , because name is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MODEL_NAME_IS_EMPTY);
        }

        //根据主键查询对象数据
        Model modelById = modelDao.selectByPrimaryKey(modelByEditRequest.getId());
        if (ObjectUtils.isEmpty(modelById)
                || StringUtils.isEmpty(modelById.getId())) {
            logger.info("modify fail , because model id [" + modelByEditRequest.getId() + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MODEL_DATA_NOT_EXIXTS);
        }

        //持久化模型数据
        Model model = new Model();
        BeanUtils.copyProperties(modelByEditRequest, model);

        //模型文件上传
        List<FileResponse> fileResponses = fileService.upload(request);
        if (!CollectionUtils.isEmpty(fileResponses)) {
            FileResponse fileResponse = fileResponses.get(0);

            //模型实体文件地址是否为空
            if (StringUtils.isEmpty(fileResponse.getFilePath())) {
                logger.info("modify fail , because file address is empty");
                throw new EqianyuanException(ExceptionMsgConstant.MODEL_FILE_ADDRESS_IS_EMPTY);
            }

            //移动临时附件到持久目录
            FileUtilHandle.moveFile(fileResponse.getFileName(), fileResponse.getFilePath(), SystemConf.MODEL_FILE_UPLOAD_FIXED_DIRECTORY.toString());
            model.setFileAddress(SystemConf.MODEL_FILE_UPLOAD_FIXED_DIRECTORY.toString() + File.separator + fileResponse.getFileName());

            //删除原数据绑定的模型文件
            FileUtilHandle.deleteFile(SessionUtil.getSession().getServletContext().getRealPath("/") + modelById.getFileAddress());
        }

        modelDao.updateByPrimaryKeySelective(model);
    }

    /**
     * 根据模型主键查询详情
     *
     * @param id
     * @return
     * @throws EqianyuanException
     */
    public ModelBo queryById(String id) throws EqianyuanException {
        //主键是否为空
        if (StringUtils.isEmpty(id)) {
            logger.info("queryById fail , because id is empty");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_USER_ID_IS_EMPTY);
        }

        //根据主键查询对象数据
        Model modelById = modelDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(modelById)
                || StringUtils.isEmpty(modelById.getId())) {
            logger.info("queryById fail , because model id [" + id + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MODEL_DATA_NOT_EXIXTS);
        }

        //查询模型分类
        ModelClassify modelClassify = modelClassifyDao.selectByPrimaryKey(modelById.getClassifyId());
        if (ObjectUtils.isEmpty(modelClassify)
                || StringUtils.isEmpty(modelClassify.getId())) {
            logger.info("queryById fail , because model classify id [" + modelClassify.getId() + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MODEL_CLASSIFY_DATA_NOT_EXISTS);
        }

        ModelBo modelBo = new ModelBo();
        BeanUtils.copyProperties(modelById, modelBo);
        modelBo.setClassifyName(modelClassify.getName());
        return modelBo;
    }

    /**
     * 分页查询模型集合
     *
     * @param pageNo
     * @param pageSize
     * @param classifyId
     * @return
     */
    public PageResponse queryByPagination(int pageNo, int pageSize, String classifyId) {
        Long dataCount = modelDao.countByPagination(classifyId);
        if (ObjectUtils.isEmpty(dataCount)) {
            logger.info("classifyId [" + classifyId + "] get total count is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        Page page = new Page(pageNo, pageSize);
        List<Model> models = modelDao.selectByPagination(page, classifyId);
        if (CollectionUtils.isEmpty(models)) {
            logger.info("pageNo [" + pageNo + "], pageSize [" + pageSize + "], classifyId [" + classifyId + "] get List is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        List<ModelBo> modelBos = new ArrayList<ModelBo>();
        for (Model model : models) {
            ModelBo modelBo = new ModelBo();
            BeanUtils.copyProperties(model, modelBo);
            modelBos.add(modelBo);
        }
        return new PageResponse(pageNo, pageSize, dataCount, modelBos);
    }
}
