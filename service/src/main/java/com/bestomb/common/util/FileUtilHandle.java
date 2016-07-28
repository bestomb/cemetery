package com.bestomb.common.util;

import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;

public class FileUtilHandle {

    private static Logger logger = Logger.getLogger(FileUtilHandle.class);

    /**
     * 附件移动，从临时目录移动到指定目录
     *
     * @param fileName  附件名
     * @param original  源目录
     * @param directory 指定放置目录
     * @return
     */
    public static void moveFile(String fileName, String original, String directory) throws EqianyuanException {
        if (StringUtils.isEmpty(fileName)) {
            logger.info("moveFile fail , because fileName is empty");
            throw new EqianyuanException(ExceptionMsgConstant.FILE_NAME_IS_EMPTY);
        }

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
                return ;
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
}
