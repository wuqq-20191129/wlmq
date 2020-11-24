/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.util;

import com.goldsign.settle.realtime.frame.constant.FrameCheckConstant;
import com.goldsign.settle.realtime.frame.constant.FrameCollectionConstant;
import com.goldsign.settle.realtime.frame.constant.FrameFileHandledConstant;
import com.goldsign.settle.realtime.frame.dao.FileLogDao;
import com.goldsign.settle.realtime.frame.exception.FileNameException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hejj
 */
public class FileNameParseUtil {

    /**
     * 文件名称校验相关
     */
    //长度
    public static int FILE_LEN_TRX = 20;//交易文件名称长度
    public static int FILE_LEN_PRO = 16;//收益文件名称长度
    public static int FILE_LEN_AUD = 16;//审计文件名称长度
    public static int FILE_LEN_REG = 16;//寄存器文件名称长度
    public static int FILE_LEN_STL = 14;//lcc对账文件文件名称长度
    public static int FILE_LEN_ORD = 18;//订单文件文件名称长度
    public static int FILE_LEN_ORI = 18;//订单执行文件文件名称长度
    public static int FILE_LEN_OCT_IMPORT = 32;//公交IC卡对账文件文件名称长度
    //开头
    public static String FILE_START_TRX = "TRX";//交易文件名称的开头
    public static String FILE_START_PRO = "PRO";//收益文件名称的开头
    public static String FILE_START_AUD = "AUD";//审计文件名称的开头
    public static String FILE_START_REG = "REG";//寄存器文件名称的开头
    public static String FILE_START_STL = "STL";//lcc对账文件名称的开头
    public static String FILE_START_ORD = "ORD";//订单文件名称的开头
    public static String FILE_START_ORI = "ORI";//订单执行文件名称的开头
    public static String FILE_START_OCT_ERR = "CW";//公交IC卡错误交易文件名称的开头
    public static String FILE_START_OCT_AUB = "QS";//公交IC卡结算审计文件名称的开头
    public static String FILE_START_OCT_AUF = "DS";//公交IC卡审计文件名称的开头
    public static String FILE_START_OCT_BLL = "MD";//公交IC卡黑名单文件名称的开头
    public static String FILE_START_OCT_AUS = "CJ";//公交IC卡接收ACC文件状态的开头
    public static String FILE_START_OCT_TRX = "TRX";//公交上传的交易文件开头
    public static String FILE_START_OCT_BLA = "BLA";//公交上传的名单申请文件开头

    public static String FILE_START_TRX_MOBILE = "TRX80";//手机支付交易文件名称的开头
    public static String FILE_START_TRX_MOBILE_1 = "TRX81";//手机支付交易文件名称的开头
    public static String FILE_START_TRX_NETPAID = "TRX82";//互联网支付交易文件名称的开头
    public static String FILE_START_TRX_QRCODE = "TRX70";//二维码平台交易文件名称的开头
    public static String FILE_START_TRX_QRCODE_1 = "TRX71";//地铁二维码平台交易文件名称的开头
    
    public static String[] FILE_START_TRX_MOBILES={FILE_START_TRX_MOBILE,FILE_START_TRX_MOBILE_1};
    public static String[] FILE_START_TRX_QRCODES={FILE_START_TRX_QRCODE,FILE_START_TRX_QRCODE_1};
    //分隔符
    public static String FILE_DELIMIT = ".";//文件名称的分隔

    public void parseLen(String fileName, int len) throws FileNameException {
        if (fileName.length() != len) {
            throw new FileNameException(FrameFileHandledConstant.FILE_ERR_FILE_NAME_LEN[1],
                    FrameFileHandledConstant.FILE_ERR_FILE_NAME_LEN[0]);
        }

    }

    public void parseFmtForTwo(String fileName, String start) throws FileNameException {

        if (!fileName.startsWith(start)) {
            throw new FileNameException(FrameFileHandledConstant.FILE_ERR_FILE_NAME_FMT[1],
                    FrameFileHandledConstant.FILE_ERR_FILE_NAME_FMT[0]);
        }
        String delim = (String) fileName.substring(7, 8);
        if (!delim.equals(FileNameParseUtil.FILE_DELIMIT)) {
            throw new FileNameException(FrameFileHandledConstant.FILE_ERR_FILE_NAME_FMT[1],
                    FrameFileHandledConstant.FILE_ERR_FILE_NAME_FMT[0]);
        }
        delim = (String) fileName.substring(16, 17);
        if (!delim.equals(FileNameParseUtil.FILE_DELIMIT)) {
            throw new FileNameException(FrameFileHandledConstant.FILE_ERR_FILE_NAME_FMT[1],
                    FrameFileHandledConstant.FILE_ERR_FILE_NAME_FMT[0]);
        }
    }

    public void parseFmtForTwo(String fileName, String[] starts) throws FileNameException {
        boolean isStart =false;
        for (String start : starts) {
            if (fileName.startsWith(start)) {
                isStart=true;

            }

        }
        if (!isStart) {
                throw new FileNameException(FrameFileHandledConstant.FILE_ERR_FILE_NAME_FMT[1],
                        FrameFileHandledConstant.FILE_ERR_FILE_NAME_FMT[0]);
            }

        String delim = (String) fileName.substring(7, 8);
        if (!delim.equals(FileNameParseUtil.FILE_DELIMIT)) {
            throw new FileNameException(FrameFileHandledConstant.FILE_ERR_FILE_NAME_FMT[1],
                    FrameFileHandledConstant.FILE_ERR_FILE_NAME_FMT[0]);
        }
        delim = (String) fileName.substring(16, 17);
        if (!delim.equals(FileNameParseUtil.FILE_DELIMIT)) {
            throw new FileNameException(FrameFileHandledConstant.FILE_ERR_FILE_NAME_FMT[1],
                    FrameFileHandledConstant.FILE_ERR_FILE_NAME_FMT[0]);
        }
    }

    public void parseFmtForOneOnlyLine(String fileName, String start) throws FileNameException {

        if (!fileName.startsWith(start)) {
            throw new FileNameException(FrameFileHandledConstant.FILE_ERR_FILE_NAME_FMT[1],
                    FrameFileHandledConstant.FILE_ERR_FILE_NAME_FMT[0]);
        }
        String delim = (String) fileName.substring(5, 6);
        if (!delim.equals(FileNameParseUtil.FILE_DELIMIT)) {
            throw new FileNameException(FrameFileHandledConstant.FILE_ERR_FILE_NAME_FMT[1],
                    FrameFileHandledConstant.FILE_ERR_FILE_NAME_FMT[0]);
        }

    }

    public void parseFmtForOneLineStation(String fileName, String start) throws FileNameException {

        if (!fileName.startsWith(start)) {
            throw new FileNameException(FrameFileHandledConstant.FILE_ERR_FILE_NAME_FMT[1],
                    FrameFileHandledConstant.FILE_ERR_FILE_NAME_FMT[0]);
        }
        String delim = (String) fileName.substring(7, 8);
        if (!delim.equals(FileNameParseUtil.FILE_DELIMIT)) {
            throw new FileNameException(FrameFileHandledConstant.FILE_ERR_FILE_NAME_FMT[1],
                    FrameFileHandledConstant.FILE_ERR_FILE_NAME_FMT[0]);
        }

    }

    public void parseFmtForOctImport(String fileName, String start) throws FileNameException {

        if (!fileName.startsWith(start)) {
            throw new FileNameException(FrameFileHandledConstant.FILE_ERR_FILE_NAME_FMT[1],
                    FrameFileHandledConstant.FILE_ERR_FILE_NAME_FMT[0]);
        }

    }

    public void parseStation(String fileName) throws FileNameException {
        String station = fileName.substring(3, 7);
        String lineAll = fileName.substring(5, 7);
        if (FrameCollectionConstant.BUF_STATION_CODE == null) {
            return;
        }
        if (lineAll.equals("00")) {
            return;
        }

        if (!FrameCollectionConstant.BUF_STATION_CODE.contains(station)) {
            throw new FileNameException(FrameFileHandledConstant.FILE_ERR_FILE_NAME_STATION[1],
                    FrameFileHandledConstant.FILE_ERR_FILE_NAME_STATION[0]);
        }

    }

    public void parseDate(String fileName) throws FileNameException {
        //String date = fileName.substring(8, 8);
    }

    public void parseDateIndexTwo(String fileName, String balanceWaterNo) throws FileNameException {
        //String date = fileName.substring(8, 8);
        String[] fileNameParts = fileName.split("\\.");
        String fileNameDate = fileNameParts[1];
        String balanceWaterNoDate = balanceWaterNo.substring(0, 8);
        if (fileNameDate.compareTo(balanceWaterNoDate) > 0) {
            throw new FileNameException(FrameFileHandledConstant.FILE_ERR_FILE_NAME_TIME[1],
                    FrameFileHandledConstant.FILE_ERR_FILE_NAME_TIME[0]);
        }
        //正式运营日期之前数据20181017
        if (fileNameDate.compareTo(FrameCheckConstant.CHK_TIME_FORMAL_ONLINE) < 0) {
            throw new FileNameException(FrameFileHandledConstant.FILE_ERR_FILE_NAME_TIME[1],
                    FrameFileHandledConstant.FILE_ERR_FILE_NAME_TIME[0]);
        }
        

    }

    public void parseRepeat(String fileName) throws FileNameException {
        FileLogDao dao = new FileLogDao();
        boolean isRepeat = dao.isRepeat(fileName);
        if (isRepeat) {
            throw new FileNameException(FrameFileHandledConstant.FILE_ERR_FILE_NAME_REPEAT[1],
                    FrameFileHandledConstant.FILE_ERR_FILE_NAME_REPEAT[0]);
        }
    }

    public static void main(String[] args) {
        FileNameParseUtil util = new FileNameParseUtil();
        String fileName, balanceWaterNo;
        fileName = "TRX0101.20180608.001";
        balanceWaterNo = "2018060801";
        try {
            util.parseDateIndexTwo(fileName, balanceWaterNo);
        } catch (FileNameException ex) {
            Logger.getLogger(FileNameParseUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
