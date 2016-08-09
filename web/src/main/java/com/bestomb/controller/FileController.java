package com.bestomb.controller;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.FileResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 附件控制器
 * Created by jason on 2016-07-24.
 */
@Controller
public class FileController extends BaseController {

    @Autowired
    private IFileService fileService;

    @RequestMapping("/file/upload")
    @ResponseBody
    public ServerResponse upload(HttpServletRequest request,
                                 @RequestParam(value = "file_type", required = false) String fileType) throws EqianyuanException, IOException {
        List<FileResponse> fileResponses = fileService.upload(request, fileType);
        return new ServerResponse.ResponseBuilder().data(fileResponses).build();
    }

}
