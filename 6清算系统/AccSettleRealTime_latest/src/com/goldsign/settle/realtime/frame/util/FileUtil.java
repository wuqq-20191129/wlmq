/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.util;

import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameFileHandledConstant;
import com.goldsign.settle.realtime.frame.filter.FileFilterOctReturn;
import com.goldsign.settle.realtime.frame.filter.FileFilterOctUpload;
import com.goldsign.settle.realtime.frame.filter.FileFilterOther;
import com.goldsign.settle.realtime.frame.filter.FileFilterOther70;
import com.goldsign.settle.realtime.frame.filter.FileFilterOther80;
import com.goldsign.settle.realtime.frame.filter.FileFilterOther82;
import com.goldsign.settle.realtime.frame.filter.FileFilterTrx;
import com.goldsign.settle.realtime.frame.filter.FileFilterTrx7x;
import com.goldsign.settle.realtime.frame.filter.FileFilterTrx80;
import com.goldsign.settle.realtime.frame.filter.FileFilterTrx82;
import com.goldsign.settle.realtime.frame.handler.HandlerREG;

import com.goldsign.settle.realtime.frame.vo.FileNameSection;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class FileUtil {

    private static Logger logger = Logger.getLogger(FileUtil.class.getName());
    public static String DATA_OCT = "Oct";//数据来源标识，公交IC卡数据
    //public static int LEN_LIMIT = 10;//一次处理文件数量限制

    public File[] getFilesLimit(File[] files) {
        if (!this.isLimit()) {
            return files;
        }
        if (files.length <= FrameCodeConstant.SYS_SETTLE_FILE_ONCE_LIMIT_NUMBER) {
            return files;
        }
        File[] filesLimit = new File[FrameCodeConstant.SYS_SETTLE_FILE_ONCE_LIMIT_NUMBER];
        for (int i = 0; i < FrameCodeConstant.SYS_SETTLE_FILE_ONCE_LIMIT_NUMBER; i++) {
            filesLimit[i] = files[i];
        }
        return filesLimit;

    }

    public boolean isLimit() {
        if (FrameCodeConstant.SYS_SETTLE_FILE_ONCE_LIMIT_FLAG.equals(FrameCodeConstant.SYS_SETTLE_FILE_ONCE_LIMIT_YES)) {
            return true;
        }
        return false;
    }

    public File[] getFilesForTrx(String path) {
        File f = new File(path);
        if (!f.isDirectory()) {
            return null;
        }
        FileFilterTrx filter = new FileFilterTrx();
        File[] files = f.listFiles(filter);

        File[] filesLimit = this.getFilesLimit(files);
        return filesLimit;

    }

    //空发交易文件，add by hejj 20160109
    public File[] getFilesForTrx80(String path) {
        File f = new File(path);
        if (!f.isDirectory()) {
            return null;
        }
        FileFilterTrx80 filter = new FileFilterTrx80();
        File[] files = f.listFiles(filter);
        File[] filesLimit = this.getFilesLimit(files);
        return filesLimit;

    }

    //二维码平台交易文件，add by hejj 20190612
    public File[] getFilesForTrx7x(String path) {
        File f = new File(path);
        if (!f.isDirectory()) {
            return null;
        }
        FileFilterTrx7x filter = new FileFilterTrx7x();
        File[] files = f.listFiles(filter);
        File[] filesLimit = this.getFilesLimit(files);
        return filesLimit;

    }

    //互联网交易文件，add by hejj 20161125
    public File[] getFilesForTrx82(String path) {
        File f = new File(path);
        if (!f.isDirectory()) {
            return null;
        }
        FileFilterTrx82 filter = new FileFilterTrx82();
        File[] files = f.listFiles(filter);
        File[] filesLimit = this.getFilesLimit(files);
        return filesLimit;

    }

    public File[] getFilesForOther(String path) {
        File f = new File(path);
        if (!f.isDirectory()) {
            return null;
        }
        FileFilterOther filter = new FileFilterOther();
        File[] files = f.listFiles(filter);

        File[] filesLimit = this.getFilesLimit(files);
        return filesLimit;

    }

    public File[] getFilesForOther70(String path) {
        File f = new File(path);
        if (!f.isDirectory()) {
            return null;
        }
        FileFilterOther70 filter = new FileFilterOther70();
        File[] files = f.listFiles(filter);
        File[] filesLimit = this.getFilesLimit(files);
        return filesLimit;

    }

    public File[] getFilesForOther80(String path) {
        File f = new File(path);
        if (!f.isDirectory()) {
            return null;
        }
        FileFilterOther80 filter = new FileFilterOther80();
        File[] files = f.listFiles(filter);
        File[] filesLimit = this.getFilesLimit(files);
        return filesLimit;

    }

    public File[] getFilesForOther82(String path) {
        File f = new File(path);
        if (!f.isDirectory()) {
            return null;
        }
        FileFilterOther82 filter = new FileFilterOther82();
        File[] files = f.listFiles(filter);
        return files;

    }

    public File[] getFilesForOctReturn(String path) {
        File f = new File(path);
        if (!f.isDirectory()) {
            return null;
        }
        FileFilterOctReturn filter = new FileFilterOctReturn();
        File[] files = f.listFiles(filter);
        return files;

    }

    public File[] getFilesForOctImport(String path) {
        File f = new File(path);
        if (!f.isDirectory()) {
            return null;
        }
        FileFilterOctUpload filter = new FileFilterOctUpload();
        File[] files = f.listFiles(filter);
        return files;

    }

    public static void mkDir(String pathName) {
        File path = new File(pathName);
        //if(!path.isDirectory())
        //  return;
        if (!path.exists()) {
            path.mkdirs();
        }
    }

    public static void moveFile(String fileNameSrc, String pathSrc, String fileNameDes, String pathDes, String balanceWaterNo) {
        String fileNameFullSrc = pathSrc + "/" + fileNameSrc;
        String fileNameFullDes = pathDes + "/" + balanceWaterNo + "/" + fileNameDes;
        String pathSubDes = pathDes + "/" + balanceWaterNo;
        String fileNameFullDesRep;
        File path = new File(pathSubDes);
        if (!path.exists()) {
            path.mkdirs();
        }
        File src = new File(fileNameFullSrc);
        File des = new File(fileNameFullDes);
        if (des.exists()) {
            fileNameFullDesRep = fileNameFullDes + "." + DateHelper.dateToString(new Date());
            File rep = new File(fileNameFullDesRep);
            des.renameTo(rep);

            //des.delete();
        }

        boolean result = src.renameTo(des);
        logger.info("文件 " + fileNameSrc + " 移至" + pathDes);
        /*
        if (!result) {
                logger.info("文件 "+fileNameSrc+" 移至"+pathDes+"时存在重复或不能移动");
            }
         */

    }

    public static void moveFileForSettle(String fileNameSrc, String pathSrc, String fileNameDes, String pathDes, String balanceWaterNo) {
        FileUtil.moveFile(fileNameSrc, pathSrc, fileNameDes, pathDes, balanceWaterNo);

        FileUtil.moveFile(FrameCodeConstant.CONTROL_FILE, pathSrc, FrameCodeConstant.CONTROL_FILE, pathDes, balanceWaterNo);

    }

    public static void moveFileForOct(String fileNameSrc, String pathSrc, String fileNameDes, String pathDes, String balanceWaterNo) {
        String fileNameFullSrc = pathSrc + "/" + fileNameSrc;
        String fileNameFullDes = pathDes + "/" + balanceWaterNo + "/" + fileNameDes;
        String pathSubDes = pathDes + "/" + balanceWaterNo;
        String fileNameFullDesRep;
        File path = new File(pathSubDes);
        if (!path.exists()) {
            path.mkdirs();
        }
        File src = new File(fileNameFullSrc);
        File des = new File(fileNameFullDes);
        if (des.exists()) {
            fileNameFullDesRep = fileNameFullDes + "." + DateHelper.dateToString(new Date());
            File rep = new File(fileNameFullDesRep);
            des.renameTo(rep);
            //des.delete();
        }
        src.renameTo(des);
        //创建控制文件
        String pathControl = pathDes + "/" + balanceWaterNo;
        createControlFile(pathControl, FrameCodeConstant.CONTROL_FILE);

    }

    public static void moveFileForMobile(String fileNameSrc, String pathSrc, String fileNameDes, String pathDes, String balanceWaterNo) {
        String fileNameFullSrc = pathSrc + "/" + fileNameSrc;
        String fileNameFullDes = pathDes + "/" + balanceWaterNo + "/" + fileNameDes;
        String pathSubDes = pathDes + "/" + balanceWaterNo;
        String fileNameFullDesRep;
        File path = new File(pathSubDes);
        if (!path.exists()) {
            path.mkdirs();
        }
        File src = new File(fileNameFullSrc);
        File des = new File(fileNameFullDes);
        if (des.exists()) {
            fileNameFullDesRep = fileNameFullDes + "." + DateHelper.dateToString(new Date());
            File rep = new File(fileNameFullDesRep);
            des.renameTo(rep);
            //des.delete();
        }
        src.renameTo(des);
        //创建控制文件
        String pathControl = pathDes + "/" + balanceWaterNo;
        createControlFile(pathControl, FrameCodeConstant.CONTROL_FILE);

    }

    public static void createControlFile(String path, String fileName) {
        String fileNameFull = path + "/" + fileName;
        File f = new File(fileNameFull);
        if (f.exists()) {
            return;
        }
        writeDataToFileForEmpty(path, fileName);

    }

    private static boolean writeDataToFileForEmpty(String path, String fileName) {
        boolean result = false;
        FileWriter out = null;
        File file = null;
        char[] CRLF = {0x0d, 0x0a};

        String crc;
        try {
            file = new File(path, fileName);
            out = new FileWriter(file);
            String line = "file finished";

            // for (String line : data) {
            out.write(line, 0, line.length());
            out.write(CRLF);
            out.flush();
            //  }

            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(out);
        }
        return result;
    }

    private static void close(FileWriter out) {
        if (out != null) {
            try {
                out.close();
                //encode(paraFile);
            } catch (Exception e) {
            }
        }

    }

    public static void moveFile(String fileNameSrc, String pathSrc, String fileNameDes, String pathDes) {
        String cur = DateHelper.dateOnlyToString(new Date());
        String fileNameFullSrc = pathSrc + "/" + fileNameSrc;
        String fileNameFullDes = pathDes + "/" + cur + "/" + fileNameDes;
        String pathSubDes = pathDes + "/" + cur;
        String fileNameFullDesRep;
        File path = new File(pathSubDes);
        if (!path.exists()) {
            path.mkdirs();
        }
        File src = new File(fileNameFullSrc);
        File des = new File(fileNameFullDes);
        if (des.exists()) {
            fileNameFullDesRep = fileNameFullDes + "." + DateHelper.dateToString(new Date());
            File rep = new File(fileNameFullDesRep);
            des.renameTo(rep);
            //des.delete();
        }
        src.renameTo(des);

    }

    public static String getFieDataType(String fileName) {
        if (fileName == null || fileName.length() < 3) {
            return "";
        }
        String fileNameId = fileName.substring(0, 3);
        String s;
        for (int i = 0; i < FrameFileHandledConstant.FTP_FILE_DATA_TYPE_NAME.length; i++) {
            s = FrameFileHandledConstant.FTP_FILE_DATA_TYPE_NAME[i];
            if (fileNameId.equals(s)) {
                return FrameFileHandledConstant.FTP_FILE_DATA_TYPE[i];
            }

        }
        return "";
    }

    public static String getFileType(String fileName) {
        if (fileName == null || fileName.length() == 0) {
            return "";
        }
        return fileName.substring(0, 3);
    }

    public static String getFileTypeForOctImport(String fileName) {
        if (fileName == null || fileName.length() == 0) {
            return "";
        }
        return DATA_OCT + fileName.substring(0, 2);
    }

    public static FileNameSection getFileSectForTwo(String fileName) {
        if (fileName == null || fileName.length() == 0) {
            return null;
        };
        String tradType = fileName.substring(0, 3);
        String lineStationId = fileName.substring(3, 7);
        String tradDate = fileName.substring(8, 16);
        String strSeq = fileName.substring(17);
        FileNameSection sec = new FileNameSection();
        sec.setTradType(tradType);
        sec.setLineStationId(lineStationId);
        sec.setTradDate(tradDate);
        sec.setSeq(Integer.parseInt(strSeq));
        return sec;

    }

    public static FileNameSection getFileSectForOneOnlyLine(String fileName) {
        if (fileName == null || fileName.length() == 0) {
            return null;
        };
        String tradType = fileName.substring(0, 3);
        String lineStationId = fileName.substring(3, 5);
        String tradDate = fileName.substring(6, 14);
        // String strSeq = fileName.substring(17);
        FileNameSection sec = new FileNameSection();
        sec.setTradType(tradType);
        sec.setLineStationId(lineStationId);
        sec.setTradDate(tradDate);
        // sec.setSeq(Integer.parseInt(strSeq));
        return sec;

    }

    public static FileNameSection getFileSectForOneLineStation(String fileName) {
        if (fileName == null || fileName.length() == 0) {
            return null;
        };
        String tradType = fileName.substring(0, 3);
        String lineStationId = fileName.substring(3, 7);
        String tradDate = fileName.substring(6, 14);
        // String strSeq = fileName.substring(17);
        FileNameSection sec = new FileNameSection();
        sec.setTradType(tradType);
        sec.setLineStationId(lineStationId);
        sec.setTradDate(tradDate);
        // sec.setSeq(Integer.parseInt(strSeq));
        return sec;

    }

    /*
     public static FileNameSection getFileSectForOnePro(String fileName) {
     if (fileName == null || fileName.length() == 0) {
     return null;
     };
     String tradType = fileName.substring(0, 3);
     String lineStationId = fileName.substring(3, 7);
     String tradDate = fileName.substring(8, 16);
     // String strSeq = fileName.substring(17);
     FileNameSection sec = new FileNameSection();
     sec.setTradType(tradType);
     sec.setLineStationId(lineStationId);
     sec.setTradDate(tradDate);
     // sec.setSeq(Integer.parseInt(strSeq));
     return sec;

     }
     */
    public static String getTradeTypeForOct(String orgTradeType) {
        return DATA_OCT + orgTradeType;
    }

    public static FileNameSection getFileSectForOct(String fileName) {
        if (fileName == null || fileName.length() == 0) {
            return null;
        };
        String tradType = fileName.substring(0, 2);
        String lineStationId = "";//fileName.substring(8, 12);
        String tradDate = fileName.substring(18, 26);
        // String strSeq = fileName.substring(17);
        FileNameSection sec = new FileNameSection();
        sec.setTradType(tradType);
        sec.setLineStationId(lineStationId);
        sec.setTradDate(tradDate);
        // sec.setSeq(Integer.parseInt(strSeq));
        return sec;

    }
}
