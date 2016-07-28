package com.bestomb.common.response;

/**
 * 附件响应输出对象
 * Created by jason on 2016-07-25.
 */
public class FileResponse {
    //附件名称
    private String fileName;
    //附件地址
    private String filePath;
    //附件大小
    private String fileSize;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
}
