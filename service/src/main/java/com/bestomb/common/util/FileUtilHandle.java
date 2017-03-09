package com.bestomb.common.util;

import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.FileResponse;
import com.bestomb.common.util.yamlMapper.SystemConf;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtilHandle {

    private static Logger logger = Logger.getLogger(FileUtilHandle.class);

    /**
     * 多个附件上传
     *
     * @param multiFiles
     * @return
     * @throws EqianyuanException
     */
    public static List<FileResponse> upload(MultiValueMap<String, MultipartFile> multiFiles) throws EqianyuanException {
        if (CollectionUtils.isEmpty(multiFiles)) {
            logger.info("upload fail , because file no exists");
            throw new EqianyuanException(ExceptionMsgConstant.FILE_NO_EXISTS);
        }

        List<FileResponse> fileResponses = new ArrayList<FileResponse>();
        for (String key : multiFiles.keySet()) {
            MultipartFile file = multiFiles.getFirst(key);
            FileResponse fileResponse = upload(file);
            fileResponses.add(fileResponse);
        }

        return fileResponses;
    }

    /**
     * 单个附件上传
     *
     * @param multipartFile
     * @return
     * @throws EqianyuanException
     */
    public static FileResponse upload(MultipartFile multipartFile) throws EqianyuanException {
        if (ObjectUtils.isEmpty(multipartFile)) {
            logger.info("upload fail , because file no exists");
            throw new EqianyuanException(ExceptionMsgConstant.FILE_NO_EXISTS);
        }

        if (StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
            logger.info("upload fail , because file no exists");
            throw new EqianyuanException(ExceptionMsgConstant.FILE_NO_EXISTS);
        }

        //获取附件上传位置
        String absoluteDirectory = SessionUtil.getSession().getServletContext().getRealPath("/");
        File f1 = new File(absoluteDirectory + SystemConf.FILE_UPLOAD_TEMP_DIRECTORY.toString());

        if (!f1.exists()) {
            f1.mkdirs();
        }

//        String fullName = SystemConf.FILE_UPLOAD_TEMP_DIRECTORY.toString() + File.separator + multipartFile.getOriginalFilename();
        String orginalFileName = multipartFile.getOriginalFilename();
        String fileSuffix = orginalFileName.substring(orginalFileName.lastIndexOf("."));
        String fileName = System.currentTimeMillis() + fileSuffix;
        String fullName = SystemConf.FILE_UPLOAD_TEMP_DIRECTORY.toString() + File.separator + fileName;
        String fullPath = absoluteDirectory + fullName;
        // 附件处理上传
        try {
            multipartFile.transferTo(new File(fullPath));
        } catch (IOException e) {
            logger.error("upload fail ", e);
            throw new EqianyuanException(ExceptionMsgConstant.FILE_UPDATE_ERROR);
        }

        FileResponse fileResponse = new FileResponse();
        fileResponse.setFileName(fileName);
        fileResponse.setFilePath(fullPath);
        fileResponse.setFileSize(String.valueOf(multipartFile.getSize()));
        return fileResponse;
    }

    /**
     * 附件移动，从临时目录移动到指定目录
     *
     * @param original  源目录
     * @param directory 指定放置目录
     * @return
     */
    public static void moveFile(String original, String directory) throws EqianyuanException {
        if (StringUtils.isEmpty(original)) {
            logger.info("moveFile fail , because original is empty");
            throw new EqianyuanException(ExceptionMsgConstant.FILE_MOVE_ERROR);
        }

        if (StringUtils.isEmpty(directory)) {
            logger.info("moveFile fail , because directory is empty");
            throw new EqianyuanException(ExceptionMsgConstant.FILE_MOVE_ERROR);
        }

        File file = new File(original);
        if (!file.exists()) {
            logger.info("moveFile fail , because file no exists");
            throw new EqianyuanException(ExceptionMsgConstant.FILE_NO_EXISTS);
        }
        String absoluteDirectory = SessionUtil.getSession().getServletContext().getRealPath("/");
        //获取指定存放目录位置路劲
        File savefile = new File(absoluteDirectory + directory);

        if (!savefile.exists()) {
            savefile.mkdirs();
        }

        if (!file.renameTo(new File(absoluteDirectory + directory + File.separator + file.getName()))) {
            logger.info("moveFile fail , because file renameTo error");
//            throw new EqianyuanException(ExceptionMsgConstant.FILE_MOVE_ERROR);
        }
    }

    /**
     * 附件删除
     *
     * @return
     */
    public static void deleteFile(String filePath) throws EqianyuanException {
        if (StringUtils.isEmpty(filePath)) {
            logger.info("deleteFile fail , because filePath is empty");
            throw new EqianyuanException(ExceptionMsgConstant.FILE_NO_EXISTS);
        }

        File file = new File(filePath);
        if (file.isDirectory()) {
            //递归删除目录内容后再删除目录自身
            directoryDel(file);
        } else {
            if (!file.exists()) {
                logger.info("deleteFile fail , because file no exists");
                //throw new EqianyuanException(ExceptionMsgConstant.FILE_NO_EXISTS);
                return;
            }

            if (!file.delete()) {
                logger.info("deleteFile fail , because file delete error");
                throw new EqianyuanException(ExceptionMsgConstant.FILE_DELETE_ERROR);
            }
        }
    }

    /**
     * 目录递归删除
     */
    private static void directoryDel(File dir) throws EqianyuanException {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (String child : children) {
                directoryDel(new File(dir, child));
            }
        }
        if (!dir.delete()) {
            logger.info("directoryDel fail , because file delete error");
            throw new EqianyuanException(ExceptionMsgConstant.FILE_DELETE_ERROR);
        }
    }

    /**
     * 获取文件体积
     *
     * @param filePath
     * @return
     * @throws EqianyuanException
     */
    public static long getFileSize(String filePath) throws EqianyuanException {
        if (StringUtils.isEmpty(filePath)) {
            logger.info("deleteFile fail , because filePath is empty");
            return 0;
        }

        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            logger.info("deleteFile fail , because file no exists");
            return 0;
        }

        return file.length();
    }
}
