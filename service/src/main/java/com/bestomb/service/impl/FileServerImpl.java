package com.bestomb.service.impl;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.FileResponse;
import com.bestomb.common.util.FileUtilHandle;
import com.bestomb.service.IFileService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
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
    public List<FileResponse> upload(HttpServletRequest request, String fileType) throws EqianyuanException {
        //todo 判断文件类型是否为允许上传的类型 fileType

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;// 转换请求
        MultiValueMap<String, MultipartFile> multiFiles = multipartRequest.getMultiFileMap();
        List<FileResponse> fileResponses = FileUtilHandle.upload(multiFiles);
        return fileResponses;
    }
}
