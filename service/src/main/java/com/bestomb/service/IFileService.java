package com.bestomb.service;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.FileResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 附件业务逻辑接口
 * Created by jason on 2016-07-25.
 */
public interface IFileService {

    /**
     * 附件上传
     *
     * @param request
     * @return
     */
    List<FileResponse> upload(HttpServletRequest request) throws EqianyuanException;
}
