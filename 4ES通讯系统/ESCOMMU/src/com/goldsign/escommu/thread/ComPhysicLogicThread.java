/*
 * 文件名：ComPhysicLogicThread
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.escommu.thread;

import com.goldsign.escommu.dao.PhyLogicCompDao;
import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.env.FileConstant;
import com.goldsign.escommu.util.CharUtil;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.vo.PhyLogicVo;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;


/*
 * 物理卡号，逻辑卡号对照线程
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-9-16
 */

public class ComPhysicLogicThread extends Thread{
    
    private static Logger logger = Logger.getLogger(ComPhysicLogicThread.class.getName());

    private String nextMakeTime;
    
    private String fileName;//文件名
    
    private String path;//生成保存路径
    
    /**
     * 创建审核文件线程
     * 
     */
    public ComPhysicLogicThread() {
        String curDate = DateHelper.dateToYYYYMMDD(new Date());
        nextMakeTime = curDate + AppConstant.PhysicsLogicNextMakeTime;
    }

    /**
     * 运行
     * 
     */
    public void run() {

        while (true) {
            try {
                if (isTimeToMake() && !isExistFile()) {
                    logger.info(DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date())+"-开始生成物理卡号、逻辑卡号对照文件");
                    this.makePhyLogicFiles();
                    logger.info(DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date())+"-完成生成物理卡号、逻辑卡号对照文件，下次时间："+nextMakeTime);
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
            nextMakeTime = nextDate + AppConstant.PhysicsLogicNextMakeTime;
            return true;
        }
        return false;
    }
    
    /**
     * 是否存在当天的对照表文件
     * @param ftpFileVo
     * @return 
     */
    private boolean isExistFile() {
        path = getPath();
        fileName = getFileName(FileConstant.PHY_LOGIC_FILE_PRE);
        File file = new File(path,fileName);
        return file.exists();
    }

    /**
     * 生成物理卡号，逻辑卡号对照文件
     * 
     * @throws Exception 
     */
    private void makePhyLogicFiles() throws Exception {
        System.out.print("当前虚拟机最大可用内存为1:");   
        System.out.println(Runtime.getRuntime().maxMemory()/1024/1024+"M");   
        System.out.print("当前，虚拟机已占用内存1:");   
        System.out.println(Runtime.getRuntime().totalMemory()/1024/1024+"M"); 
        List<PhyLogicVo> files = getPhyLogicFiles();
        logger.info("-取得审计文件，长度："+files.size());
        makeFiles(files);

    }

    /**
     * 生成文件
     * 
     * @param fileNamePre
     * @throws IOException 
     */
    private void makeFiles(List<PhyLogicVo> files) 
            throws IOException {

        System.out.print("当前虚拟机最大可用内存为2:");   
        System.out.println(Runtime.getRuntime().maxMemory()/1024/1024+"M");   
        System.out.print("当前，虚拟机已占用内存2:");   
        System.out.println(Runtime.getRuntime().totalMemory()/1024/1024+"M");  
        
        BufferedWriter bufferedWriter = null;
        logger.info("文件长度："+files.size());
        logger.info("文件生成路径："+path);
        logger.info("文件名："+fileName);
        bufferedWriter = getWriter();
        for (PhyLogicVo fileVo : files) {
            writeFile(bufferedWriter, fileVo);
        }
        writeCRC(bufferedWriter);
        closeWriter(bufferedWriter);
        
        System.out.print("当前虚拟机最大可用内存为3:");   
        System.out.println(Runtime.getRuntime().maxMemory()/1024/1024+"M");   
        System.out.print("当前，虚拟机已占用内存3:");   
        System.out.println(Runtime.getRuntime().totalMemory()/1024/1024+"M");  
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
    private BufferedWriter getWriter() {

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
    private String getFileName(String fileNamePre) {

        String date = DateHelper.datetimeToYYYYMMDD(new Date());
        String fileName = fileNamePre + "." + date;

        return fileName;
    }

    /**
     * 取得文件路径
     * 
     * @return 
     */
    private String getPath() {
        String path = AppConstant.PhyLogicFileMakeDir;
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
    private void writeFile(BufferedWriter bufferedWriter, PhyLogicVo fileVo) 
            throws IOException {
        
        bufferedWriter.write(fileVo.getPhysicNo());
        bufferedWriter.write(FileConstant.N_SIGN);
        bufferedWriter.write(fileVo.getLogicNo());
        bufferedWriter.write(FileConstant.CRLF_1);
        bufferedWriter.flush();
    }

    /**
     * 写文件CRC码
     * 
     * @param bufferedWriter
     * @param fileName
     * @throws IOException 
     */
    private void writeCRC(BufferedWriter bufferedWriter) 
            throws IOException {
        StringBuffer sb = new StringBuffer(fileName);

        if (fileName != null) {
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
     * 取得物理卡号与逻辑卡号对照文件
     * 
     * @return 
     */
    private List<PhyLogicVo> getPhyLogicFiles() {

        List<PhyLogicVo> phyLogicFiles = null;

        PhyLogicCompDao phyLogicCompDao = new PhyLogicCompDao();
        try {
            phyLogicFiles = phyLogicCompDao.getPhyLogicList();
        } catch (Exception ex) {
            logger.error(ex);
        }

        return phyLogicFiles;
    }

    /**
     * 休息
     */
    private void threadSleep() {

        try {
            this.sleep(AppConstant.PhyLogicFileFindInterval * 60 * 60 * 1000);
        } catch (NumberFormatException e) {
            logger.error(e);
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }

}
