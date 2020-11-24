/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.thread;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.env.FileConstant;
import com.goldsign.escommu.dao.EsFileDao;
import com.goldsign.escommu.util.CharUtil;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.util.StringUtil;
import com.goldsign.escommu.vo.FileAudErrVo;
import java.io.*;
import java.util.*;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class ComAuditFileThread extends Thread {

    private static Logger logger = Logger.getLogger(ComAuditFileThread.class.getName());

    private String nextMakeTime;
    
    /**
     * 创建审核文件线程
     * 
     */
    public ComAuditFileThread() {
        String curDate = DateHelper.dateToYYYYMMDD(new Date());
        nextMakeTime = curDate + AppConstant.AuditFileNextMakeTime;
    }

    /**
     * 运行
     * 
     */
    public void run() {

        while (true) {
            try {
                if (isTimeToMake()) {
                    logger.info(DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date())+"-开始生成审计和错误文件。。。。。。");
                    this.makeAuditFiles();
                    logger.info(DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date())+"-审计文件完成，错误文件开始!");
                    this.makeErrorFiles();
                    logger.info(DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date())+"-完成生成审计和错误文件。。。。。。下次时间："+nextMakeTime);
                }
                this.threadSleep();
            } catch (Exception e) {
                logger.error(e);
            }
        }
    }
    
    /**
     * 是否是生成文件时间
     * 
     * @return 
     */
    private boolean isTimeToMake() {
        String curTime = DateHelper.dateToYYYYMMDDHH(new Date());
        if (curTime.compareTo(nextMakeTime) > 0) {
            String nextDate = DateHelper.dateToYYYYMMDD(DateHelper.getDateAfterDate(new Date(), 1));
            nextMakeTime = nextDate + AppConstant.AuditFileNextMakeTime;
            return true;
        }
        return false;
    }

    /**
     * 生成审计文件
     * 
     * @throws Exception 
     */
    private void makeAuditFiles() throws Exception {

        List<FileAudErrVo> files = getAuditFiles();
        logger.info("-取得审计文件，长度："+files.size());
        Map<String, List<FileAudErrVo>> deviceFiles = getDeviceFiles(files);
        makeFiles(FileConstant.AUDIT_FILE_PRE, deviceFiles);

    }

    /**
     * 生成错误文件
     * 
     * @throws Exception 
     */
    private void makeErrorFiles() throws Exception {

        List<FileAudErrVo> files = getErrorFiles();
        logger.info("-取得错误文件，长度："+files.size());
        Map<String, List<FileAudErrVo>> deviceFiles = getDeviceFiles(files);
        makeFiles(FileConstant.AUDIT_ERR_FILE_PRE, deviceFiles);

    }

    /**
     * 取设备文件
     * 
     * @param files
     * @return 
     */
    private Map<String, List<FileAudErrVo>> getDeviceFiles(List<FileAudErrVo> files) {

        Map<String, List<FileAudErrVo>> deviceFiles = new HashMap<String, List<FileAudErrVo>>();
        for (FileAudErrVo fileAudErrVo : files) {
            String deviceId = fileAudErrVo.getDeviceId();
            List<FileAudErrVo> tempFiles = null;
            if (deviceFiles.containsKey(deviceId)) {
                tempFiles = deviceFiles.get(deviceId);
            } else {
                tempFiles = new ArrayList<FileAudErrVo>();
            }
            tempFiles.add(fileAudErrVo);
            deviceFiles.put(deviceId, tempFiles);
        }

        return deviceFiles;
    }

    /**
     * 生成文件
     * 
     * @param fileNamePre
     * @param deviceFiles
     * @throws IOException 
     */
    private void makeFiles(String fileNamePre, Map<String, List<FileAudErrVo>> deviceFiles) 
            throws Exception {

        if (deviceFiles.isEmpty()) {
            return;
        }

        BufferedWriter bufferedWriter = null;
        String path = null;
        String fileName = null;
        Set<String> deviceIds = deviceFiles.keySet();
        for (String deviceId : deviceIds) {
            logger.info("设备编号："+deviceId);
            List<FileAudErrVo> files = deviceFiles.get(deviceId);
            logger.info("文件长度："+files.size());
            path = getPath();
            logger.info("文件生成路径："+path);
            fileName = getFileName(fileNamePre, deviceId);
            logger.info("文件名："+fileName);
            bufferedWriter = getWriter(path, fileName);
            for (FileAudErrVo fileVo : files) {
                //writeFile(bufferedWriter, fileVo);//hwj 20150925 modify 因为接口规范
                if(FileConstant.AUDIT_FILE_PRE.equals(fileNamePre)){
                    writeAuditFile(bufferedWriter, fileVo);
                }else if(FileConstant.AUDIT_ERR_FILE_PRE.equals(fileNamePre)){
                    writeErrorFile(bufferedWriter, fileVo);
                }else{
                    writeFile(bufferedWriter, fileVo);
                }
            }
            writeCRC(bufferedWriter, files);
            closeWriter(bufferedWriter);
            writeDB(deviceId, fileName);
        }
        closeWriter(bufferedWriter);
    }

    /**
     * 关闭文件写流
     * 
     * @param bufferedWriter
     * @throws IOException 
     */
    private void closeWriter(BufferedWriter bufferedWriter) throws IOException {
        if (null != bufferedWriter) {
            bufferedWriter.close();
            bufferedWriter = null;
        }
    }

    /**
     * 取得文件写流
     * 
     * @param path
     * @param fileName
     * @return 
     */
    private BufferedWriter getWriter(String path, String fileName) {

        OutputStream outputStream = null;
        BufferedWriter bufferedWriter = null;
        File file = null;
        try {
            file = new File(path, fileName);
            outputStream = new FileOutputStream(file);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        } catch (IOException e) {
            logger.error(e);
        }

        return bufferedWriter;
    }

    /**
     * 取得文件名
     * 
     * @param fileNamePre
     * @param deviceId
     * @return 
     */
    private String getFileName(String fileNamePre, String deviceId) {

        String date = DateHelper.datetimeToYYYYMMDD(new Date());
        String fileName = fileNamePre + deviceId + "." + date;

        return fileName;
    }

    /**
     * 取得文件路径
     * 
     * @return 
     */
    private String getPath() {
        String path = AppConstant.AuditFileMakeDir;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getPath();
    }

    /**
     * 写文件
     * 
     * @param bufferedWriter
     * @param fileVo
     * @throws IOException 
     */
    private void writeFile(BufferedWriter bufferedWriter, FileAudErrVo fileVo) 
            throws IOException {

        bufferedWriter.write(fileVo.getFileName());
        bufferedWriter.write(FileConstant.CRLF_1);
        bufferedWriter.flush();
    }
    
    //hwj 20150925 modify 因为接口规范
    private void writeAuditFile(BufferedWriter bufferedWriter, FileAudErrVo fileVo) throws IOException{
        
        String fileName = StringUtil.addCharsAfter2(fileVo.getFileName(),20," ");
        File file = new File(AppConstant.FtpLocalDir+"/"+fileName);
        String fileLen = "";
        if(file.exists() && file.isFile()){
            fileLen = file.length()+"";
        }
        String cnt = fileName + StringUtil.addCharsBefore(fileLen,10,"0");
        bufferedWriter.write(cnt);
        bufferedWriter.write(FileConstant.CRLF_1);
        bufferedWriter.flush();
    }
    
    private void writeErrorFile(BufferedWriter bufferedWriter, FileAudErrVo fileVo) throws IOException{
        
        String fileName = StringUtil.addCharsAfter2(fileVo.getFileName(),20," ");
        String errorCode = fileVo.getErrorCode();
        String cnt = fileName + StringUtil.addCharsBefore(errorCode,2,"0");
        bufferedWriter.write(cnt);
        bufferedWriter.write(FileConstant.CRLF_1);
        bufferedWriter.flush();
    }
    
    //--------

    /**
     * 写文件CRC码
     * 
     * @param bufferedWriter
     * @param files
     * @throws IOException 
     */
    private void writeCRC(BufferedWriter bufferedWriter, List<FileAudErrVo> files) 
            throws IOException {
        StringBuffer sb = new StringBuffer("");
        for (FileAudErrVo fileVo : files) {
            sb.append(fileVo.getFileName());
            sb.append(FileConstant.CRLF_1);
        }

        if (files != null && files.size() > 0) {
            String s = sb.toString();
            byte[] b = null;//new byte[2800000];
            b = s.getBytes();
            long crc32 = CharUtil.getCRC32Value(b);
            String crc = Long.toHexString(crc32);
            for (int i = crc.length(); i < 8; i++) {
                crc = "0" + crc;
            }
            bufferedWriter.write(FileConstant.SVER + crc);
        } else {
            bufferedWriter.write(FileConstant.SVER + "00000000");
        }
        bufferedWriter.flush();
    }

    /**
     * 写数据库
     * 
     * @param deviceId
     * @param fileName 
     */
    private void writeDB(String deviceId, String fileName) throws Exception {

        EsFileDao esFileDao = new EsFileDao();
        esFileDao.insertAuditFile(deviceId, fileName);
    }

    /**
     * 取得审核文件
     * 
     * @return 
     */
    private List<FileAudErrVo> getAuditFiles() {

        List<FileAudErrVo> auditFiles = null;

        EsFileDao esFileDao = new EsFileDao();

        auditFiles = esFileDao.getAuditFiles();

        return auditFiles;
    }

    /**
     * 取得错误文件
     * 
     * @return 
     */
    private List<FileAudErrVo> getErrorFiles() {

        List<FileAudErrVo> errorFiles = null;

        EsFileDao esFileDao = new EsFileDao();

        errorFiles = esFileDao.getErrorFiles();

        return errorFiles;
    }

    /**
     * 休息
     */
    private void threadSleep() {

        try {
            this.sleep(AppConstant.AuditFileFindInterval * 60 * 60 * 1000);
        } catch (NumberFormatException e) {
            logger.error(e);
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }
}
