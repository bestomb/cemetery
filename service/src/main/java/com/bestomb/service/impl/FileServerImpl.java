package com.bestomb.service.impl;

import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.FileResponse;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.service.IFileService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 附件业务逻辑接口实现类
 * Created by jason on 2016-07-25.
 */
@Service
public class FileServerImpl implements IFileService {

    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 附件上传
     *
     * @param request
     * @return
     */
    public List<FileResponse> upload(HttpServletRequest request) throws EqianyuanException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;// 转换请求
        MultiValueMap<String, MultipartFile> multiFiles = multipartRequest.getMultiFileMap();

        if (CollectionUtils.isEmpty(multiFiles)) {
            logger.info("upload fail , because file no exists");
            throw new EqianyuanException(ExceptionMsgConstant.FILE_NO_EXISTS);
        }

        //获取图片上传位置
        String absoluteDirectory = SessionUtil.getSession().getServletContext().getRealPath("/");

        File f1 = new File(absoluteDirectory + SystemConf.MODEL_FILE_UPLOAD_TEMP_DIRECTORY.toString());

        if (!f1.exists()) {
            f1.mkdirs();
        }

        List<FileResponse> fileResponses = new ArrayList<FileResponse>();
        for (String key : multiFiles.keySet()) {
            MultipartFile file = multiFiles.getFirst(key);
            if (ObjectUtils.isEmpty(file)) {
                continue;
            }

            if (StringUtils.isEmpty(file.getOriginalFilename())) {
                continue;
            }

            // 取得上传文件后缀名
//            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
//            String newUploadFileName = System.currentTimeMillis() + "." + suffix;
            String fullName = SystemConf.MODEL_FILE_UPLOAD_TEMP_DIRECTORY.toString() + File.separator + file.getOriginalFilename();
            String fullPath = absoluteDirectory + fullName;
            // 图片处理上传
            try {
                file.transferTo(new File(fullPath));
            } catch (IOException e) {
                logger.error("upload fail ", e);
                throw new EqianyuanException(ExceptionMsgConstant.FILE_UPDATE_ERROR);
            }

            FileResponse fileResponse = new FileResponse();
            fileResponse.setFileName(file.getOriginalFilename());
            fileResponse.setFilePath(fullPath);
            fileResponse.setFileSize(String.valueOf(file.getSize()));
            fileResponses.add(fileResponse);
        }

        return fileResponses;
    }
}
