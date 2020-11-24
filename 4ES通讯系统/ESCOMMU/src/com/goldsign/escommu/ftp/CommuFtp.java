package com.goldsign.escommu.ftp;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.env.LogConstant;
import com.goldsign.escommu.exception.CommuException;
import com.goldsign.escommu.exception.FileFtpException;
import com.goldsign.escommu.util.CharUtil;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.util.LogDbUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Date;
import java.util.Vector;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class CommuFtp {

    private long startTime;
    private long endTime;
    /**
     * 日志记录使用
     */
    private long hdlStartTime; //处理的起始时间
    private long hdlEndTime;//处理的结束时间
    private static Logger logger = Logger.getLogger(CommuFtp.class.getName());

    private void setFtpClientSocketOption(FTPClient ftpClient, String server) throws SocketException {
        // ftpClient.setDataTimeout(ApplicationConstant.FtpTimeout);
        ftpClient.setDefaultTimeout(AppConstant.FtpTimeout);
        //ftpClient.setSoTimeout(ApplicationConstant.FtpTimeout);

        ftpClient.setConnectTimeout(AppConstant.FtpTimeout);
        DateHelper.screenPrint("设置连接超时时间：" + AppConstant.FtpTimeout
                + " 准备连接服务器：" + server);

    }

    private void setFtpClientSocketOptionAfter(FTPClient ftpClient, String server) throws SocketException {
        ftpClient.setDataTimeout(AppConstant.FtpTimeout);

        ftpClient.setSoTimeout(AppConstant.FtpTimeout);

        ftpClient.setConnectTimeout(AppConstant.FtpTimeout);
        DateHelper.screenPrint("控制socket超时时间：" + AppConstant.FtpTimeout
                + "数据socket超时时间：" + AppConstant.FtpTimeout);

    }

    private void setFtpClientFileOption(FTPClient ftpClient) throws Exception {
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        int retCode;
        retCode = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(retCode)) {
            throw new CommuException("设置FTP被动模式或文件类型错误! ");
        }

    }

    /**
     * 关闭FTP客户端
     * 
     * @param ftpClient 
     */
    private void disconnection(FTPClient ftpClient) {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

    }

    /**
     * 失败每次重连时间
     * 
     */
    private void threadSleep() {
        try {
            Thread.sleep(AppConstant.FtpRetryWaiting);
        } catch (InterruptedException ex) {
        }

    }

    /**
     * 取客户端FTP对象(连接，登录)
     * 
     * @param server
     * @param userName
     * @param password
     * @return
     * @throws FileFtpException
     * @throws Exception 
     */
    private FTPClient getFtpClient(String server, String userName, String password)
            throws FileFtpException, Exception {
        int ftpTime = AppConstant.FtpRetryTime; // first time +
        // retry time
        FTPClient ftpClient = null;
        int retCode;
        while (ftpTime > 0) {
            try {

                ftpClient = new FTPClient();
                //设置FTP的控制socket读超时时间、数据读socket超时时间、连接超时时间、缺省超时时间
                this.setFtpClientSocketOption(ftpClient, server);
                ftpClient.connect(server);
                this.setFtpClientSocketOptionAfter(ftpClient, server);

                if (!ftpClient.login(userName, password)) {
                    throw new CommuException("Ftp 登陆" + server + "错误! ");
                }

                DateHelper.screenPrint("CommuFtp 登陆服务器 : " + server + "!");
                //设置文件类型为bin、被动方式
                this.setFtpClientFileOption(ftpClient);

                ftpTime = -1; // if success anytime
            } catch (Exception e) {
                ftpTime = ftpTime - 1;
                logger.error("Ftp 建立连接或登陆" + server + "错误! 将重试" + (ftpTime)
                        + " 次! " + e);
                DateHelper.screenPrint("Ftp 建立连接或登陆" + server + "错误! 将重试" + (ftpTime)
                        + " 次! " + e);
                this.disconnection(ftpClient);//释放登陆失败连接
                this.threadSleep();//重试等待时间间隔


            }
        }
        if (ftpTime == 0) {
            throw new FileFtpException(
                    "重试" + (AppConstant.FtpRetryTime) + "次后，Ftp 仍然不能连接或登陆" + server + "! ");
        }

        return ftpClient;

    }

    /**
     * 获取多个文件,FTP入口
     * 
     * @param server
     * @param userName
     * @param password
     * @param fileNames 
     */
    public void ftpGetFiles(String server, String userName, String password,
            Vector fileNames) {
        FTPClient ftpClient = null;
        String fileName;
        try {
            this.hdlStartTime = System.currentTimeMillis();
            //如不存在，创建本地工作目录
            setLocalDirectory(AppConstant.FtpLocalDir);
            //每个文件使用独立的连接
            for (int i = 0; i < fileNames.size(); i++) {
                //建立连接、及设置连接属性
                ftpClient = this.getFtpClient(server, userName, password);
                fileName = (String) fileNames.get(i);
                //取文件
                this.ftpGetFile(fileName, server, AppConstant.FtpLocalDir, ftpClient);
                
            }

        } catch (Exception e) {
            DateHelper.screenPrintForEx(e.getMessage());
            logger.error(e.getMessage());

            this.hdlEndTime = System.currentTimeMillis();
            //记录日志
            LogDbUtil.logForDbDetail(LogConstant.MESSAGE_ID_FTP,
                    CharUtil.getLocalIp(),
                    this.hdlStartTime, this.hdlEndTime,
                    LogConstant.RESULT_HDL_FAIL,
                    Thread.currentThread().getName(),
                    AppConstant.LOG_LEVEL_ERROR, e.getMessage());

        }finally {
            this.disconnection(ftpClient);

        }

    }

    /**
     * 获取一个文件,FTP入口
     * 
     * @param server
     * @param userName
     * @param password
     * @param fileName
     * @throws FileFtpException 
     */
    public void ftpGetFileSingle(String server, String userName, String password,
            String fileName) throws FileFtpException {
        FTPClient ftpClient = null;
        //  String fileName;
        try {
            this.hdlStartTime = System.currentTimeMillis();
            //如不存在，创建本地工作目录
            setLocalDirectory(AppConstant.FtpLocalDir);
            //每个文件使用独立的连接

            //建立连接、及设置连接属性
            ftpClient = this.getFtpClient(server, userName, password);

            //取文件
            this.ftpGetFile(fileName, server, AppConstant.FtpLocalDir, ftpClient);

            // }

        } catch (Exception e) {
            DateHelper.screenPrintForEx(e.getMessage());
            logger.error(e.getMessage());

            this.hdlEndTime = System.currentTimeMillis();
            //记录日志
            LogDbUtil.logForDbDetail(LogConstant.MESSAGE_ID_FTP,
                    CharUtil.getLocalIp(),
                    this.hdlStartTime, this.hdlEndTime,
                    LogConstant.RESULT_HDL_FAIL,
                    Thread.currentThread().getName(),
                    AppConstant.LOG_LEVEL_ERROR, e.getMessage());

            throw new FileFtpException("获取文件" + fileName + "失败");

        } finally {
            this.disconnection(ftpClient);

        }

    }

    /**
     * FTP取文件
     * 
     * @param fileName
     * @param server
     * @param localDir
     * @param ftpClient
     * @throws Exception 
     */
    private void ftpGetFile(String fileName, String server, String localDir, FTPClient ftpClient) throws Exception {
        boolean dirChanged = false;
        OutputStream output = null;
        //File f;
        int spendTime = 0;

        try {

            startTime = System.currentTimeMillis();
            //设置服务器工作目录

            String remoteDir = (String) (AppConstant.FTP_PATHS).get(fileName.substring(0, 2));
            if (remoteDir != null && remoteDir.length() != 0) {
                if (!ftpClient.changeWorkingDirectory(remoteDir)) {
                    throw new Exception("设置" + server + "工作目录" + remoteDir + "错误");
                }
                dirChanged = true;
            }
            System.out.println("远程目录："+remoteDir+",服务器："+server+",本地目录："+localDir+"，文件名："+fileName);

            //取文件,如失败，删除临时文件，成功，临时文件命名为正式文件
            /*
             * File destination = new File(localDir,
             * ApplicationConstant.FtpTmpFilePrefix + fileName);
             */
            File destination = new File(localDir,
                    fileName);
            output = new FileOutputStream(destination);
            try {
                if (!ftpClient.retrieveFile(fileName, output)) {
                    throw new IOException("获取文件" + fileName + "失败。返回消息：" + ftpClient.getReplyString());
                }
                //暂时屏蔽
                //   f = new File(localDir+"/bak", fileName);
                //  this.renameFile(destination, f);//临时文件命名为正式文件

            } catch (IOException e) {
                destination.delete();//删除临时文件
                throw new IOException(e.getMessage());

            }

            DateHelper.screenPrint("FTP 收取文件 : " + fileName + " 成功!");
            endTime = System.currentTimeMillis();
            spendTime = (int) (endTime - startTime);
            LogDbUtil.writeFtpLog(new Date(System.currentTimeMillis()), server,
                    fileName, new java.util.Date(startTime),
                    spendTime, LogConstant.RESULT_HDL_SUCESS);

        } catch (Exception e) {
            DateHelper.screenPrintForEx(e.getMessage());
            logger.error(e.getMessage());

            endTime = System.currentTimeMillis();
            spendTime = (int) (endTime - startTime);
            LogDbUtil.writeFtpLog(new Date(System.currentTimeMillis()), server,
                    fileName, new java.util.Date(startTime),
                    spendTime, LogConstant.RESULT_HDL_FAIL);

            throw new Exception(e.getMessage());

        } finally {
            //关闭文件输出流、服务器返回工作目录的上一级
            this.finalHandle(output, dirChanged, ftpClient);
            this.disconnection(ftpClient);
        }
    }

    /**
     * 关闭相应资源
     * 
     * @param output
     * @param dirChanged
     * @param ftpClient 
     */
    private void finalHandle(OutputStream output, boolean dirChanged, FTPClient ftpClient) {
        try {
            if (output != null) {
                output.close();
            }
            if (dirChanged) {
                try {
                    ftpClient.changeToParentDirectory();
                } catch (Exception e) {
                    logger.error(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void renameFile(File destination, File f) throws Exception {
        // 交易文件大小为0，不作处理
        boolean result = true;
        if (destination.length() == 0) {
            destination.delete();
            return;
        }
        // 同名交易文件存在且交易文件大小为0，删除同名交易文件，将新的交易文件重命名
        if (f.exists() && f.length() == 0) {
            f.delete();
            result = destination.renameTo(f);
            return;
        }
        // 同名交易文件存在且交易文件大小不为0，不作处理
        if (f.exists() && f.length() != 0) {
            destination.delete();
            return;
        }
        // 同名交易文件不存且交易文件大小不为0，将新的交易文件重命名
        result = destination.renameTo(f);
        if (!result) {
            throw new Exception("文件" + destination.getAbsolutePath() + "重命名为" + f.getAbsolutePath() + "失败");
        }

    }

    /**
     * 创建本地目录
     * 
     * @param path 
     */
    private void setLocalDirectory(String path) {

        File filePath = new File(path);
        if (!filePath.exists()) {
            // logger.info("FTP path "+localDir+" not exists, created!");
            filePath.mkdirs();
        }
    }
}
