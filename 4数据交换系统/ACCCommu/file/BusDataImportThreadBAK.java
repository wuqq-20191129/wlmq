package com.goldsign.commu.frame.thread;

import com.goldsign.commu.app.dao.BusDataDao;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.goldsign.commu.frame.constant.FrameCodeConstant;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取一卡通中心的结算结果文件 上传一卡通地铁交易文件到一卡通中心
 *
 * @author lindq
 *
 *
 */
public class BusDataImportThreadBAK implements Runnable {

    private static String FILE_TRX = "trx";// 交易文件标识
    private static String FILE_SETTLE = "settle";// 结算结果文件标识
    private static String FILE_OCT = "XF";// 一卡通通讯包标识
    private static String DIRECTORY_TRX = "";
    private static String DIRECTORY_SETTLE = "";
    private static Logger logger = Logger.getLogger(BusDataImportThreadBAK.class);
    private static String CONTROL_FILE_NAME = "control.txt";

    @Override
    public void run() {
        while (true) {
            logger.info("------------------------与一卡通中心文件交互执行begin-------------------------");
            // 获取清算流水号
            try {
                BusDataDao bdd = new BusDataDao();
                String banlanceWaterNo = bdd.query();
                // 没有获取到清算流水号
                if ("".equals(banlanceWaterNo.trim())) {
                    logger.warn("--没有获取到清算流水号.");
                    continue;
                }
                logger.debug("--查询到的清算流水号:" + banlanceWaterNo);
                // 上传交易文件
                upload(banlanceWaterNo, FILE_TRX, DIRECTORY_TRX);
                // 取一卡通结算结果文件
                handle(banlanceWaterNo, FILE_SETTLE, DIRECTORY_SETTLE);
            } catch (Exception e1) {
                logger.error("出错", e1);
            }
            logger.info("------------------------与一卡通中心文件交互执行end-------------------------");
            try {
                Thread.sleep(FrameCodeConstant.busFtpInterval);// 休眠5分钟
            } catch (InterruptedException e) {
                logger.error("睡眠被打断", e);
            }
        }

    }
    

    /**
     *
     * @param fileType 文件类型
     * @param fileSubDirectory 文件存放的子目录
     */
    private void handle(String banlanceWaterNo, String fileType,
            String fileSubDirectory) {
        try {

            // 判断清算流水号对应的文件是否获取
            BusDataDao bdd = new BusDataDao();
            boolean isExited = bdd.judgeWaterNoExited(banlanceWaterNo, fileType);

            // 流水号对应的文件已经获取过，退出程序
            if (isExited) {
                logger.info("--清算流水号对应的一卡通结算文件已经获取过");
                return;
            }
            logger.warn("--准备FTP登录一卡通中心取一卡通结算文件.");
            // 根据清算流水号获取文件
            boolean result = getFile(banlanceWaterNo, fileType,
                    fileSubDirectory);

            // 如果获取文件成功，记录结果到表中
            if (result) {
                bdd.writeDB(banlanceWaterNo, fileType);
            }

        } catch (Exception e) {
            logger.error("--处理" + fileType + "类型目录一卡通结算文件过程中出错：", e);
        }

    }

    private boolean getFile(String banlanceWaterNo, String fileType,
            String fileSubDirectory) {
        String url = FrameCodeConstant.busFtpURL;
        int port = FrameCodeConstant.busFtpPort;
        String username = FrameCodeConstant.busFtpUserName;
        String password = FrameCodeConstant.busFtpPassWord;
        String remotePath = fileSubDirectory;
        String localPath = FrameCodeConstant.busFtpLocalDir + "/import/" + fileType
                + "/" + banlanceWaterNo;

        // 初始表示下载失败
        boolean result = false;
        // 创建FTPClient对象
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            // 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftp.connect(url, port);
            // 登录ftp
            ftp.login(username, password);
            logger.info("--下载FTP成功登录一卡通中心."+ url + ":" + port);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            // 转到指定下载目录
            if (!remotePath.isEmpty() && remotePath.length() > 0) {
                logger.info("--切换到一卡通中心目录：" + remotePath);
                boolean isRemotePathExited = ftp.changeWorkingDirectory(remotePath);
                if (!isRemotePathExited) {
                    logger.info("--远程目录:" + remotePath + "不存在");
                    ftp.disconnect();
                    return result;
                }
            }
            // 设置文件类型（二进制）
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            // 设置传文件过程超时
            ftp.setDataTimeout(FrameCodeConstant.ftpTimeout);
            ftp.enterLocalPassiveMode();
//            ftp.setBufferSize(1024);
//            ftp.setControlEncoding("GBK");

            /*
            间隔时间内遍历两次文件，对比文件数量、大小，相同时判断为文件写完成状态，可以下载
             */
            Map<String, FTPFile> fileMap = new HashMap<String, FTPFile>();
            // 列出该目录下所有文件1,第一次查询文件大小
            FTPFile[] fs1 = ftp.listFiles();
//            logger.info("--列出该目录下所有文件1:" + fs1.length);
            if (null == fs1 || fs1.length == 0) {
                return result;
            }
            // 遍历文件夹存在当前流水号文件名
            for (FTPFile ff : fs1) {
                logger.info("--枚举一卡通结算文件:" + ff.getName());
                if (FILE_OCT.equals(ff.getName().substring(0, 2)) 
                        && ff.getName().contains(banlanceWaterNo.substring(0, 8))) {
                    fileMap.put(ff.getName(), ff);
                }
            }

            //并对比第一次遍历结果
            int i = 0;
            if (0 < fileMap.size()) {
                try {
                    Thread.sleep(FrameCodeConstant.busFtpDelayInterval);
                } catch (InterruptedException ex) {
                    logger.error(ex.getMessage());
                }

                // 列出该目录下所有文件2,第二次查询文件大小
                FTPFile[] fs2 = ftp.listFiles();
                if (null == fs2 || fs2.length == 0) {
                    return result;
                }
                // 遍历文件夹存在当前流水号文件名,并对比第一次遍历结果
                for (FTPFile ff : fs2) {
                    if (FILE_OCT.equals(ff.getName().substring(0, 2)) 
                            && ff.getName().contains(banlanceWaterNo.substring(0, 8))) {
                        logger.info("--枚举一卡通结算文件:" + ff.getName());
                        if (fileMap.containsKey(ff.getName())) {
                            i++;
                            if (fileMap.get(ff.getName()).getSize() != ff.getSize()) {
                                return result;
                            }
                        }
                    }
                }
            }

            if (i == fileMap.size()) {
                logger.info("--下载文件到本地目录：" + localPath);
                File localDir = new File(localPath);
                if (!localDir.exists()) {
                    localDir.mkdirs();
                }
                for (Map.Entry<String, FTPFile> entry : fileMap.entrySet()) {
                    FTPFile value = entry.getValue();
                    downFileUnit(localPath, ftp, value);
                }
                result = true;
            }

//            if (!result) {
//                logger.info("--------在一卡通中心目录" + remotePath + "中没有发现清算流水号" + banlanceWaterNo + "文件");
//            }
        } catch (IOException e) {
            logger.error("--FTP取一卡通结算文件出错:", e);
        } finally {
            if (ftp.isConnected()) {
                try {
                    // 退出ftp
                    ftp.logout();
                    ftp.disconnect();
                } catch (IOException ioe) {
                    logger.error("--FTP取一卡通结算文件出错:", ioe);
                }
            }
        }
        return result;

    }

    private void downFileUnit(String localPath, FTPClient ftp, FTPFile ff)
            throws FileNotFoundException, IOException {
        OutputStream out = null;
        // 根据绝对路径初始化文件
        File localFile = new File(localPath + "/" + ff.getName());
        // 输出流
        try {
            out = new FileOutputStream(localFile);
            // 下载文件
            ftp.retrieveFile(ff.getName(), out);
            logger.info("成功下载：" + ff.getName());
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            this.closeOut(out);
        }
    }

    private void closeOut(OutputStream out) {
        try {
            if (out != null) {
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
    上传地铁交易、对账结果文件到一卡通ftp服务器
     */
    private void upload(String banlanceWaterNo, String fileType, String fileSubDirectory) {
        try {

            // 判断清算流水号对应的文件是否上传
            BusDataDao bdd = new BusDataDao();
            boolean isExited = bdd.judgeWaterNoExited(banlanceWaterNo, fileType);

            // 流水号对应的文件已经上传过，退出程序
            if (isExited) {
                logger.info("--清算流水号对应的一卡通地铁消费文件已经上传过");
                return;
            }
            logger.warn("--准备FTP登录一卡通中心上传文件.");
            // 根据清算流水号上传文件
            boolean result = uploadFile(banlanceWaterNo, fileType, fileSubDirectory);

            // 如果获取文件成功，记录结果到表中
            if (result) {
                bdd.writeDB(banlanceWaterNo, fileType);
            }
        } catch (Exception e) {
            logger.error("--处理" + fileType + "类型目录文件过程中出错：", e);
        }
    }
    
    private boolean uploadFile(String banlanceWaterNo, String fileType,
            String fileSubDirectory) {
        String url = FrameCodeConstant.busFtpURL;
        int port = FrameCodeConstant.busFtpPort;
        String username = FrameCodeConstant.busFtpUploadUser;
        String password = FrameCodeConstant.busFtpUploadPW;
        String remotePath = fileSubDirectory;
        String localPath = FrameCodeConstant.busFtpLocalDir + "/export/" + fileType
                + "/" + banlanceWaterNo;

        // 初始表示上传失败
        boolean result = false;
        // 判断清算文件
        File csDir = new File(localPath);
        if(!csDir.exists()){
            logger.info("--------地铁公交交易、清算文件目录未生成------------");
            return result;
        }
        File[] fs = csDir.listFiles();
        if (null == fs || fs.length == 0) {
            logger.info("--------地铁公交交易、清算文件未生成------------");
            return result;
        }
        //判断control文件，存在时可取文件
        for(File f: fs){
            if(CONTROL_FILE_NAME.equals(f.getName())){
                result = true;
                break;
            }
        }
        
        //上传文件
        if(result){
            // 创建FTPClient对象
            FTPClient ftp = new FTPClient();
            try {
                int reply;
                // 连接FTP服务器
                ftp.connect(url, port);
                // 登录ftp
                ftp.login(username, password);
                logger.info("--上传FTP成功登录一卡通中心."+ url + ":" + port);
                reply = ftp.getReplyCode();
                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftp.disconnect();
                    return result;
                }
                // 转到指定上传目录
                if (!remotePath.isEmpty() && remotePath.length() > 0) {
                    logger.info("--切换到一卡通中心目录：" + remotePath);
                    boolean isRemotePathExited = ftp.changeWorkingDirectory(remotePath);
                    if (!isRemotePathExited) {
                        logger.info("--远程目录:" + remotePath + "不存在");
                        ftp.disconnect();
                        return result;
                    }
                }
                // 设置文件类型（二进制）
                ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
                // 设置传文件过程超时
                ftp.setDataTimeout(FrameCodeConstant.ftpTimeout);
                ftp.enterLocalPassiveMode();
//                ftp.setBufferSize(1024);
//                ftp.setControlEncoding("GBK");

                for(File f: fs){
                    if(FILE_OCT.equals(f.getName().substring(0, 2))){
                        uploadFileUnit(ftp, f);
                    }
                }

            } catch (IOException e) {
                logger.error("--FTP上传地铁公交交易、清算文件出错:", e);
            } finally {
                if (ftp.isConnected()) {
                    try {
                        // 退出ftp
                        ftp.logout();
                        ftp.disconnect();
                    } catch (IOException ioe) {
                        logger.error("--FTP上传地铁公交交易、清算文件出错:", ioe);
                    }
                }
            }
        }
        
        return result;
    }
    
    private void uploadFileUnit(FTPClient ftp, File f)
            throws FileNotFoundException, IOException {
        // 输入流
        InputStream in = null;
        
        try {
            in = new FileInputStream(f);
            boolean result = true;
            // 上传文件
            result = ftp.storeFile(f.getName(), in);
            logger.info("--上传：" + f.getName() +"," +result);
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            this.closeIn(in);
        }
    }

    private void closeIn(InputStream in) {
        try {
            if (in != null) {
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
