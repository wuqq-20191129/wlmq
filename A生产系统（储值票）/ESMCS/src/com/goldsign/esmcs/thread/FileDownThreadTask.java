/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.thread;

import com.goldsign.esmcs.ftp.CommuFtp;
import com.goldsign.esmcs.vo.FtpFileParamVo;
import org.apache.log4j.Logger;

/**
 * 文件下载线程
 *
 * @author lenovo
 */
public class FileDownThreadTask extends Thread{

    private static final Logger logger = Logger.getLogger(FileDownThreadTask.class.getName());

    //FTP锁
    public static final Object FTP_LOCK = new Object();
    
    //FTP
    private CommuFtp ftp = new CommuFtp();
    
    //下载文件
    private FtpFileParamVo ftpFileVo;
    
    public FileDownThreadTask(FtpFileParamVo ftpFileVo){
        this.ftpFileVo = ftpFileVo;
    }

    /**
     * 运行
     * 
     */
    @Override
    public void run() {

        try {
            ftp.ftpGetFileSingle(ftpFileVo.getIp(), ftpFileVo.getUserCode(), ftpFileVo.getPwd(),
                    ftpFileVo.getFile(), ftpFileVo.getLocalPath(), ftpFileVo.getRemotePath());
        } catch (Exception e) {
            logger.error(ftpFileVo);
            logger.error("文件下载失败："+e.getMessage());
        }
    }
}
