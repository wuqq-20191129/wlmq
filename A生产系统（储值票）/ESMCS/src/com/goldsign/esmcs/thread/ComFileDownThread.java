/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.thread;

import com.goldsign.esmcs.env.AppConstant;
import com.goldsign.esmcs.exception.FileFtpException;
import com.goldsign.esmcs.ftp.CommuFtp;
import com.goldsign.esmcs.vo.FtpFileParamVo;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * 文件下载线程
 *
 * @author lenovo
 */
public class ComFileDownThread extends Thread{

    private static final Logger logger = Logger.getLogger(ComFileDownThread.class.getName());
    
    //文件队列
    private static List<FtpFileParamVo> FILES_QUEUE = new ArrayList<FtpFileParamVo>();
    
    //文件锁
    public static final Object FILE_LOCK = new Object();
    
    //FTP锁
    public static final Object FTP_LOCK = new Object();
    
    //FTP
    private CommuFtp ftp = new CommuFtp();
    
    /**
     * 放入待下载队列
     * 
     * @param files 
     */
    public static void push(List<FtpFileParamVo> files){
        synchronized(FILE_LOCK){
            FILES_QUEUE.addAll(files);
        }
    }
    
    /**
     * 放入待下载队列
     *
     * @param file
     */
    public static void push(FtpFileParamVo file){
        synchronized(FILE_LOCK){
            FILES_QUEUE.add(file);
        }
    }
    
    /**
     * 从下载队列中取出
     * 
     * @return 
     */
    private static List<FtpFileParamVo> pop(){
        
        List<FtpFileParamVo> files = new ArrayList<FtpFileParamVo>();
        synchronized(FILE_LOCK){
            if(FILES_QUEUE.isEmpty()){
                return files;
            }
            files.addAll(FILES_QUEUE);
            FILES_QUEUE.clear();
        }
        return files;
    }
    
    /**
     * 从下载队列中取出
     * 
     * @return 
     */
    private static FtpFileParamVo popOne(){
        
        FtpFileParamVo ftpFileParamVo = null;
        synchronized(FILE_LOCK){
            if(FILES_QUEUE.isEmpty()){
                return null;
            }
            ftpFileParamVo = FILES_QUEUE.get(0);
            FILES_QUEUE.remove(0);
        }
        
        return ftpFileParamVo;
    }
    
    /**
     * 运行
     * 
     */
    @Override
    public void run() {
        
        while(true){
            try {
                //文件获取
                List<FtpFileParamVo> files = pop();
                if(null != files && files.size() != 0){
                    for(final FtpFileParamVo ftpFileVo: files){
                        synchronized(FTP_LOCK){
                            //下载文件
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        logger.info("FTP下载："+ftpFileVo);
                                        ftp.ftpGetFileSingle(ftpFileVo.getIp(), ftpFileVo.getUserCode(), ftpFileVo.getPwd(), 
                                            ftpFileVo.getFile(), ftpFileVo.getLocalPath(), ftpFileVo.getRemotePath());
                                    } catch (FileFtpException ex) {
                                        logger.error(ftpFileVo);
                                        logger.error("文件下载失败："+ex.getMessage());
                                    }
                                }
                            }.start();
                        }
                    }
                }
                this.threadSleep();
            } catch (Exception e) {
                logger.error(e);
            }
//           if(AppConstant.IS_CHECK==true&&FILES_QUEUE.isEmpty()){
//                
//            }
        }
        
    }
    
    /**
     * 休息
     * 
     */
    private void threadSleep() {

        try {
            sleep(AppConstant.FileDownThreadSleepTime);
        } catch (NumberFormatException e) {
            logger.error(e);
        } catch (InterruptedException e) {
            logger.error(e);
        }

    }

}
