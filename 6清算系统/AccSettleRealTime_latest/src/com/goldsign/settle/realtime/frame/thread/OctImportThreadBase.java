/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.thread;

import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameOctFileImportConstant;
import com.goldsign.settle.realtime.frame.dao.FlowDao;
import com.goldsign.settle.realtime.frame.dao.OctFileImportDao;
import com.goldsign.settle.realtime.frame.filter.FileFilterOctImportSettleZip;
import com.goldsign.settle.realtime.frame.filter.FileFilterOctImportTrxZip;
import com.goldsign.settle.realtime.frame.util.DateHelper;
import com.goldsign.settle.realtime.frame.util.FileUtil;
import com.goldsign.settle.realtime.frame.util.LoggerUtil;
import com.goldsign.settle.realtime.frame.util.ZipUtil;
import com.goldsign.settle.realtime.frame.vo.FileFindResult;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class OctImportThreadBase extends Thread {

    public static int INTERVAL = 10000;
    public static int INTERVAL_WAIT_FILE_FINISH = 10000;
    public static int INTERVAL_WAIT_FILE_WRITE_FINISH = 10000;
    private static Logger logger = Logger.getLogger(OctImportThreadBase.class.
            getName());

    public boolean isUnFinishedStep(String balanceWaterNo, String step) {
        boolean isUnFinished = true;
        try {
            isUnFinished = FlowDao.isUnFinishedStep(balanceWaterNo, step);
        } catch (Exception ex) {
            logger.error(ex);
        }
        return isUnFinished;
    }

    public boolean isHasSetted(String balanceWaterNo, String fileType) {
        OctFileImportDao dao = new OctFileImportDao();
        boolean isSet = false;
        try {
            isSet = dao.isHasSetted(balanceWaterNo, fileType);
        } catch (Exception ex) {
            logger.error(ex);
        }
        return isSet;

    }

    public int insertSet(String balanceWaterNo, String fileType, String setType) {
        OctFileImportDao dao = new OctFileImportDao();
        int n = 0;
        try {
            n = dao.insertOrUpdate(balanceWaterNo, fileType, setType);
        } catch (Exception ex) {
            logger.error(ex);
        }
        return n;

    }

    public boolean isOverImportTime(String balanceWaterNo, String limitHHMM) {
        //不做时间限制，清算一直等待
        if (limitHHMM.equals(FrameOctFileImportConstant.SYS_SETTLE_OCT_IMPORT_SETTLE_TIME_UNLIMIT)) {
            return false;
        }

        String HHMMCur = DateHelper.curentDateToHHMM();
        String YmdCur = DateHelper.dateOnlyToString(new Date());
        String YmdBal = balanceWaterNo.substring(0, 8);
        long diff = 0;
        try {
            diff = DateHelper.getDifferInDaysForYYYYMMDD(YmdBal, YmdCur);
            if (diff == 1 && HHMMCur.compareTo(limitHHMM) >= 0)//当前时间是清算流水的下一天且当前是分超过设定的时分限制
            {
                return true;
            }
        } catch (ParseException ex) {
            logger.error(ex);
        }
        return false;


    }

    protected FileFindResult existOctImportSettle(String path) {
        FileFindResult ret = new FileFindResult();
        File f = new File(path);
        if (!f.isDirectory()) {
            return ret;
        }
        FileFilterOctImportSettleZip filter = new FileFilterOctImportSettleZip();
        File[] files = f.listFiles(filter);
        if (files == null || files.length == 0) {
            return ret;
        }
        //判断是否存在控制文件
        if (!this.isExistControlFile(files)) {
            return ret;
        }
        //删除control.txt文件
        files = this.removeControlFile(files);

        if (files == null || files.length == 0) {
            return ret;
        }

        ret.setExisted(true);
        ret.setFiles(files);
        return ret;
    }

    protected FileFindResult existOctImportTrx(String path) {
        FileFindResult ret = new FileFindResult();
        File f = new File(path);
        if (!f.isDirectory()) {
            return ret;
        }
        FileFilterOctImportTrxZip filter = new FileFilterOctImportTrxZip();
        File[] files = f.listFiles(filter);
        if (files == null || files.length == 0) {
            return ret;
        }
        //判断是否存在控制文件
        if (!this.isExistControlFile(files)) {
            logger.info("不存在控制文件control.txt");
            return ret;
        }
        //删除control.txt文件
        files = this.removeControlFile(files);
        if (files == null || files.length == 0) {
            return ret;
        }

        ret.setExisted(true);
        ret.setFiles(files);
        return ret;
    }

    protected boolean isExistControlFile(File[] files) {

        for (File f : files) {
            if (f.getName().equals(FrameCodeConstant.CONTROL_FILE)) {

                return true;
            }
        }
        return false;

    }

    protected File[] removeControlFile(File[] files) {
        Vector<File> al = new Vector();

        for (File f : files) {
            if (f.getName().equals(FrameCodeConstant.CONTROL_FILE)) {
                continue;
            }
            al.add(f);
        }
        File[] filesNew = new File[al.size()];
        for (int i = 0; i < al.size(); i++) {
            filesNew[i] = al.get(i);
        }
        return filesNew;

    }

    protected void unzipFilesForTrx(String balanceWaterNo, String zipPath, String unzipPath, File[] files, String promptMsg, String zipPathHis, String zipPathErr) {
        this.unzipFilesForSettle(balanceWaterNo, zipPath, unzipPath, files, promptMsg, zipPathHis, zipPathErr);
    }

    protected void unzipFiles(String balanceWaterNo, String zipPath, String unzipPath, File[] files, String promptMsg, String zipPathHis, String zipPathErr) {
        ZipUtil eb = new ZipUtil();
        FileUtil fu = new FileUtil();
        // String zipPathErr = zipPath + "/" + "error";
        //String zipPathHis = zipPath + "/" + "his";
        File f1 = null;
        try {
            for (File f : files) {
                f1 = f;
                eb.unzipFile(zipPath, f.getName(), unzipPath);
                LoggerUtil.loggerLineForSectAll(logger, "完成" + promptMsg + "压缩文件：" + f.getName() + "，解压至" + unzipPath);
                fu.moveFile(f1.getName(), zipPath, f1.getName(), zipPathHis, balanceWaterNo);


            }
        } catch (Exception e) {
            logger.error(e);
            fu.moveFile(f1.getName(), zipPath, f1.getName(), zipPathErr, balanceWaterNo);
        }


    }

    protected void unzipFilesForSettle(String balanceWaterNo, String zipPath, String unzipPath, File[] files, String promptMsg, String zipPathHis, String zipPathErr) {
        ZipUtil eb = new ZipUtil();
        FileUtil fu = new FileUtil();
        // String zipPathErr = zipPath + "/" + "error";
        //String zipPathHis = zipPath + "/" + "his";
        File f1 = null;
        try {
            for (File f : files) {
                f1 = f;
                eb.unzipFile(zipPath, f.getName(), unzipPath);
                LoggerUtil.loggerLineForSectAll(logger, "完成" + promptMsg + "压缩文件：" + f.getName() + "，解压至" + unzipPath);
                logger.info("延时" + (INTERVAL_WAIT_FILE_WRITE_FINISH / 1000) + "秒，预防压缩文件没有完整解压而移动文件。");
                this.sleep(INTERVAL_WAIT_FILE_WRITE_FINISH / 1000);
                fu.moveFileForSettle(f1.getName(), zipPath, f1.getName(), zipPathHis, balanceWaterNo);


            }
        } catch (Exception e) {
            fu.moveFileForSettle(f1.getName(), zipPath, f1.getName(), zipPathErr, balanceWaterNo);
        }


    }
    protected void unzipFileForSettle(String balanceWaterNo, String zipPath, String unzipPath, File file, String promptMsg, String zipPathHis, String zipPathErr) {
        ZipUtil eb = new ZipUtil();
        FileUtil fu = new FileUtil();
        String fileName = file.getName();

        try {
           
                eb.unzipFile(zipPath, fileName, unzipPath);
                LoggerUtil.loggerLineForSectAll(logger, "完成" + promptMsg + "压缩文件：" + fileName + "，解压至" + unzipPath);
                logger.info("延时" + (INTERVAL_WAIT_FILE_WRITE_FINISH / 1000) + "秒，预防压缩文件没有完整解压而移动文件。");
                this.sleep(INTERVAL_WAIT_FILE_WRITE_FINISH / 1000);
                fu.moveFileForSettle(fileName, zipPath, fileName, zipPathHis, balanceWaterNo);

        } catch (Exception e) {
            fu.moveFileForSettle(fileName, zipPath, fileName, zipPathErr, balanceWaterNo);
        }


    }

    protected void threadSleep() {

        try {
            this.sleep(INTERVAL);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    protected void threadSleep(int sleeptime) {

        try {
            this.sleep(sleeptime);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
     protected String getBalanceWaterNoFromFileName(String fileName) throws Exception{
         if(fileName.length() <32)
             throw new Exception("文件名称长度不符合规定长度32:"+fileName);
         String balanceWaterNo = fileName.substring(18,26)+"01";
         return balanceWaterNo;
         
     }

    protected String getBalanceWaterNo() {
        while (true) {
            if (FrameCodeConstant.BALANCE_WATER_NO != null && FrameCodeConstant.BALANCE_WATER_NO.length() != 0) {
                return FrameCodeConstant.BALANCE_WATER_NO;
            }

            LoggerUtil.loggerLineForSectAll(logger, "全局清算流水号为空或NULL值，等候清算流水号生成。。。");
            this.threadSleep();

        }


    }
}
