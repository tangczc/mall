package com.rootchen.mall.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @description: ftp文件服务器对接
 * @author: LiChen
 * @create: 2019-02-08 16:18
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FTPUtil {

    private static Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    private static String ftpIp = PropertiesUtil.getProperty("ftp.server.ip");
    private static String ftpUser = PropertiesUtil.getProperty("ftp.user");
    private static String ftpPwd = PropertiesUtil.getProperty("ftp.pass");

    public FTPUtil(String ip, int port, String user, String pwd) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }

    public static boolean uploadFile(List<File> fileList) throws IOException {
        FTPUtil ftpUtil = new FTPUtil(ftpIp, 21, ftpUser, ftpPwd);
        logger.info("开始连接ftp");

        boolean result = ftpUtil.uploadFile("img", fileList);

        logger.info("结束上传,上传结果:{}", result);

        return result;

    }

    private boolean uploadFile(String remotePath, List<File> fileList) throws IOException {
        boolean uploaded = true;
        FileInputStream fileInputStream = null;
        //连接ftp
        if (connectServer(this.ip, this.port, this.user, this.pwd)) {
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                for (File fileItem : fileList) {
                    fileInputStream = new FileInputStream(fileItem);
                    ftpClient.storeFile(fileItem.getName(), fileInputStream);
                }
            } catch (IOException e) {
                logger.error("文件上传异常");
                uploaded = false;
                e.printStackTrace();
            } finally {
                fileInputStream.close();
                ftpClient.disconnect();
            }
        }
        return uploaded;
    }


    /**
     * 连接ftp方法
     *
     * @param ip
     * @param port
     * @param user
     * @param pwd
     * @return
     */
    private boolean connectServer(String ip, int port, String user, String pwd) {
        boolean succcess = false;
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(ip);
            succcess = ftpClient.login(user, pwd);
        } catch (IOException e) {
            logger.error("连接错误", e);
            e.printStackTrace();
        }
        return succcess;
    }

    private String ip;
    private int port;
    private String user;
    private String pwd;
    private FTPClient ftpClient;


}