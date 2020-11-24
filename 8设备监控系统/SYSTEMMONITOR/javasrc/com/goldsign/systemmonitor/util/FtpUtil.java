package com.goldsign.systemmonitor.util;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FrameCharUtil;
import java.net.SocketException;
import java.util.Date;
import java.util.Vector;
import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;


public class FtpUtil {

    private String CURRENT;
    private long startTime;
    private long endTime;
    /**
     * 日志记录使用
     */
    private long hdlStartTime; //处理的起始时间
    private long hdlEndTime;//处理的结束时间
    private static Logger logger = Logger.getLogger(FtpUtil.class.getName());

    private void setFtpClientSocketOption(FTPClient ftpClient, String server) throws SocketException {

        ftpClient.setDefaultTimeout(FrameDBConstant.FtpTimeout);
        logger.info("设置连接超时时间：" + FrameDBConstant.FtpTimeout + " 准备连接服务器：" + server);

    }

    private void setFtpClientSocketOptionAfter(FTPClient ftpClient, String server) throws SocketException {
        ftpClient.setDataTimeout(FrameDBConstant.FtpTimeout);
        ftpClient.setSoTimeout(FrameDBConstant.FtpTimeout);
        logger.info("控制socket超时时间：" + FrameDBConstant.FtpTimeout + "数据socket超时时间：" + FrameDBConstant.FtpTimeout);
    }

    private void setFtpClientFileOption(FTPClient ftpClient) throws Exception {
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        int retCode;
        retCode = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(retCode)) {
            throw new Exception("设置FTP被动模式或文件类型错误! ");
        }
    }

    private void disconnection(FTPClient ftpClient) {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
            } catch (IOException ioe) {
            }
        }

    }

    private void threadSleep() {
        try {
            Thread.sleep(FrameDBConstant.FtpRetryWaiting);
        } catch (InterruptedException ex) {
        }

    }

    private FTPClient getFtpClient(String server, String userName, String password) throws Exception {
        int ftpTime = FrameDBConstant.FtpRetryTime + 1; // first time +
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
                    throw new Exception("Ftp 登陆" + server + "错误! ");
                }

                logger.info("CommuFtp 登陆服务器 : " + server + "!");
                //设置文件类型为bin、被动方式
                this.setFtpClientFileOption(ftpClient);

                ftpTime = -1; // if success anytime
            } catch (Exception e) {
                ftpTime = ftpTime - 1;
                logger.error("Ftp 建立连接或登陆" + server + "错误! 将重试" + (ftpTime + 1) + " 次! " + e);
                this.disconnection(ftpClient);//释放登陆失败连接
                this.threadSleep();//重试等待时间间隔
            }
        }
        if (ftpTime == 0) {
            throw new Exception("重试" + (FrameDBConstant.FtpRetryTime + 1) + "次后，Ftp 仍然不能连接或登陆" + server + "! ");
        }

        return ftpClient;

    }

    private String getRemotePath() {
        String path = FrameCharUtil.IsoToGbk(FrameDBConstant.FtpRemoteDir);
        String current = DateHelper.datetimeToStringOnlyDateF(new Date());
        CURRENT = current;
        path = path + "/" + current;
        return path;
    }

    private Vector getRemoteFileNames(String server, String userName, String password) {
        FTPClient ftpclient = null;
        FTPFile[] files;
        FTPFile file;
        Vector fileNames = new Vector();
        try {
            ftpclient = this.getFtpClient(server, userName, password);
            ftpclient.changeWorkingDirectory(this.getRemotePath());
            files = ftpclient.listFiles();
            if (files != null && files.length != 0) {
                for (int i = 0; i < files.length; i++) {
                    file = files[i];
                    if (file.isDirectory()) {
                        continue;
                    }
                    fileNames.add(file.getName());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            this.disconnection(ftpclient);
        }
        return fileNames;

    }

    public String getLocalDir() {
        String localDir = FrameDBConstant.FtpLocalDir;
       // String localDir = FrameDBConstant.FtpRemoteDir;
        if (CURRENT == null || CURRENT.length() == 0) {
            CURRENT = DateHelper.datetimeToStringOnlyDateF(new Date());
        }
        localDir = localDir + "/" + CURRENT;
        /*
        File f = new File(localDir);
        if (!f.exists()) {
            logger.info("创建目录：" + localDir);
            f.mkdir();
        }
        */
        return localDir;
    }

    public String getLocalDirWithoutMakeDir() {
        String localDir = FrameDBConstant.FtpLocalDir;
        //String localDir = FrameDBConstant.FtpRemoteDir;
        if (CURRENT == null || CURRENT.length() == 0) {
            CURRENT = DateHelper.datetimeToStringOnlyDateF(new Date());
        }
        localDir = localDir + "/" + CURRENT;
        return localDir;


    }

    public void ftpGetFiles(String server, String userName, String password) {
        FTPClient ftpClient = null;
        String fileName;
        Vector fileNames = this.getRemoteFileNames(server, userName, password);
        if (fileNames.isEmpty()) {
            logger.info("服务器:" + server + "的目录" + FrameDBConstant.FtpRemoteDir + "没有文件");
            return;
        }
        try {
            this.hdlStartTime = System.currentTimeMillis();
            //如不存在，创建本地工作目录
            setLocalDirectory(this.getLocalDir());
            //每个文件使用独立的连接
            logger.info("总共需获取文件数量:" + fileNames.size());
            for (int i = 0; i < fileNames.size(); i++) {
                //建立连接、及设置连接属性
                ftpClient = this.getFtpClient(server, userName, password);
                fileName = (String) fileNames.get(i);
                //this.bakupFile(fileName);
                logger.info("准备获取文件:" + fileName);
                //取文件
              //  this.ftpGetFile(fileName, server, this.getLocalDir(), ftpClient);

            }

        } catch (Exception e) {

            logger.error(e.getMessage());

            this.hdlEndTime = System.currentTimeMillis();
            //记录日志



        } finally {
            this.disconnection(ftpClient);

        }


    }

    private String getBakFileName(String fileName) {
        return fileName + "_" + DateHelper.dateToString(new Date());
    }

    private void bakupFile(String fileName) {
        String bak = FrameDBConstant.FtpLocalDir + "/" + "bak" + "/" + this.getBakFileName(fileName);
        String old = FrameDBConstant.FtpLocalDir + "/" + fileName;
        File f = new File(old);
        if (f.exists()) {
            logger.info("备份文件:" + fileName);
            f.renameTo(new File(bak));
        }
    }
/*
    public void ftpGetFile(String fileName, String server, String localDir, FTPClient ftpClient) throws Exception {
        boolean dirChanged = false;
        OutputStream output = null;
        File f;
        int spendTime = 0;

        try {

            startTime = System.currentTimeMillis();
            //设置服务器工作目录
            String remoteDir = this.getRemotePath();// CharUtil.IsoToGbk(ApplicationConstant.FtpRemoteDir);
            logger.info("设置远程工作目录 : " + remoteDir);
            logger.info("设置本地工作目录 : " + localDir);
            if (remoteDir != null && remoteDir.length() != 0) {
                if (!ftpClient.changeWorkingDirectory(remoteDir)) {
                    throw new Exception("设置" + server + "工作目录" + remoteDir + "错误");
                }
                dirChanged = true;
            }

            //取文件,如失败，删除临时文件，成功，临时文件命名为正式文件
            File destination = new File(localDir,
                    FrameDBConstant.FtpTmpFilePrefix + fileName);
            output = new FileOutputStream(destination);
            try {
                if (!ftpClient.retrieveFile(fileName, output)) {
                    throw new IOException("获取文件" + fileName + "失败。返回消息：" + ftpClient.getReplyString());
                }
                f = new File(localDir, fileName);
                this.renameFile(destination, f);//临时文件命名为正式文件

            } catch (IOException e) {
                destination.delete();//删除临时文件
                throw new IOException(e.getMessage());

            }

            logger.info("FTP 收取文件 : " + fileName + " 成功!");
            endTime = System.currentTimeMillis();
            spendTime = (int) (endTime - startTime);
        } catch (Exception e) {
            logger.error(e.getMessage());
            endTime = System.currentTimeMillis();
            spendTime = (int) (endTime - startTime);
            throw new Exception(e.getMessage());
        } finally {
            //关闭文件输出流、服务器返回工作目录的上一级
            this.finalHandle(output, dirChanged, ftpClient);
            this.disconnection(ftpClient);
        }
    }
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
        }
    }

    private void renameFile(File destination, File f) {
        // 交易文件大小为0，不作处理
        if (destination.length() == 0) {
            destination.delete();
            return;
        }
        // 同名交易文件存在且交易文件大小为0，删除同名交易文件，将新的交易文件重命名
        if (f.exists()) {
            f.delete();
            destination.renameTo(f);
            return;
        }
        // 同名交易文件存在且交易文件大小不为0，不作处理
		/*
         if (f.exists() && f.length() != 0) {
         destination.delete();
         return;
         }
         */
        // 同名交易文件不存且交易文件大小不为0，将新的交易文件重命名
        destination.renameTo(f);

    }

    private void setLocalDirectory(String path) {

        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
    }

    public FtpUtil() {
        super();
    }
}
