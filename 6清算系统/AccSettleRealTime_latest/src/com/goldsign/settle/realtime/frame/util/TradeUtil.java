/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.util;

import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import java.io.File;
import java.math.BigDecimal;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class TradeUtil {

    private static Logger logger = Logger.getLogger(TradeUtil.class.getName());
    private static int INTERVAL_GET_BALANCE_WATER_NO = 10000;//查找清算流水号时间间隔

    public static int convertYuanToFen(String yuan) {
        BigDecimal bYuan = new BigDecimal(yuan);
        BigDecimal mul = new BigDecimal("100.00");
        BigDecimal bFen = bYuan.multiply(mul);
        return bFen.intValue();

    }

    public static BigDecimal convertFenToYuan(int fen) {
        BigDecimal bYuan = new BigDecimal(fen);
        BigDecimal div = new BigDecimal("100.00");
        BigDecimal b = bYuan.divide(div);
        return b;

    }

    public static int getIntValue(String i) {
        i = i.trim();
        return Integer.parseInt(i);
    }

    public static boolean isCardForMetro(String cardMainId) {
        for (String tmp : FrameCodeConstant.CARD_MAIN_TYPE_METRO) {
            if (tmp.equals(cardMainId)) {
                return true;
            }
        }
        return false;
    }

    public static String getBalanceWaterNo() {
        while (true) {
            /*
             if (FrameCodeConstant.BALANCE_WATER_NO != null && FrameCodeConstant.BALANCE_WATER_NO.length() != 0) {
             return FrameCodeConstant.BALANCE_WATER_NO;
             }
             */
            if (TradeUtil.isValidBalanceWaterNo()) {
                return FrameCodeConstant.BALANCE_WATER_NO;
            }

            LoggerUtil.loggerLineForSectAll(logger, "没有有效的清算流水号，全局清算流水号为空或NULL或-1值，等候"+
                    (INTERVAL_GET_BALANCE_WATER_NO/1000)+
                    "秒清算流水号生成。。。");
            threadSleep();

        }


    }

    public static boolean isValidBalanceWaterNo() {
        if (FrameCodeConstant.BALANCE_WATER_NO != null && FrameCodeConstant.BALANCE_WATER_NO.length() != 0
                && !FrameCodeConstant.BALANCE_WATER_NO.equals("-1")) {
            return true;
        }
        return false;
    }

    public static void waitForValidBalanceWaterNo() {
        while (true) {
            /*
             if (FrameCodeConstant.BALANCE_WATER_NO != null && FrameCodeConstant.BALANCE_WATER_NO.length() != 0
             && !FrameCodeConstant.BALANCE_WATER_NO.equals("-1")) {
             return;
             }
             */
            if (TradeUtil.isValidBalanceWaterNo()) {
                return;
            }

            LoggerUtil.loggerLineForSectAll(logger, "没有有效的清算流水号，全局清算流水号为空或NULL或-1值，等候"+
                    (INTERVAL_GET_BALANCE_WATER_NO/1000)+
                    "秒清算流水号生成。。。");
            threadSleep();

        }


    }

    protected static void threadSleep() {

        try {
            Thread.sleep(INTERVAL_GET_BALANCE_WATER_NO);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static String getDirForBalanceWaterNo(String path, String balanceWaterNo, boolean isCreate) {
        String pathNameNew = path + "/" + balanceWaterNo;
        File pathNew;
        if (isCreate) {
            pathNew = new File(pathNameNew);
            if (!pathNew.exists()) {
                pathNew.mkdirs();
            }
        }
        return pathNameNew;

    }


    public static String getDirForBalanceWaterNo(String path, boolean isCreate) {
        String balanceWaterNo = TradeUtil.getBalanceWaterNo();
        String pathNameNew = path + "/" + balanceWaterNo;
        File pathNew;
        if (isCreate) {
            pathNew = new File(pathNameNew);
            if (!pathNew.exists()) {
                pathNew.mkdirs();
            }
        }
        return pathNameNew;

    }

    public static boolean isLegalTradeType(String tradeType) {
        if (tradeType == null || tradeType.length() == 0) {
            return false;
        }
        String tmp;
        for (int i = 0; i < FrameCodeConstant.TRX_TYPE_CODES.length; i++) {
            tmp = FrameCodeConstant.TRX_TYPE_CODES[i];
            if (tradeType.equals(tmp)) {
                return true;
            }
        }
        return false;


    }

    public static String[] getVectorToStringArray(Vector<String> v) {
        if (v == null) {
            return null;
        }
        String[] arr = new String[v.size()];
        for (int i = 0; i < v.size(); i++) {
            arr[i] = v.get(i);
        }
        return arr;
    }

    public static boolean isLegalTradeLenForGreaterAndEqual(String tradeType, String line) {
        int tradeLen = getTradeLen(tradeType);
        int lineLen = line.length();
        if (lineLen >= tradeLen) {
            return true;
        }
        return false;
    }

    public static int getTradeLen(String tradeType) {
        if (tradeType == null || tradeType.length() == 0) {
            return 0;
        }
        String tmp;
        for (int i = 0; i < FrameCodeConstant.TRX_TYPE_CODES.length; i++) {
            tmp = FrameCodeConstant.TRX_TYPE_CODES[i];
            if (tradeType.equals(tmp)) {
                return FrameCodeConstant.LEN_TRX_TYPES[i];
            }
        }
        return 0;


    }

    private static String getHexStringUnit(int i) {
        String tmp = Integer.toHexString(i);
        if (tmp.length() < 2) {
            tmp = "0" + tmp;
        }
        return tmp;
    }

    public static String getHexStringLimitLen(String line, int len) {
        if (line == null || line.length() == 0) {
            return "";
        }
        char[] cs = line.toCharArray();
        int i;
        String hexLine = "";
        for (char c : cs) {
            i = (int) c;
            hexLine += getHexStringUnit(i);

        }
        if (hexLine.length() > len) {
            hexLine = hexLine.substring(0, len);
        }
        return hexLine;

    }

    public static String getHexString(String line) {
        if (line == null || line.length() == 0) {
            return "";
        }
        char[] cs = line.toCharArray();
        int i;
        String hexLine = "";
        for (char c : cs) {
            i = (int) c;
            hexLine += getHexStringUnit(i);
        }
        return hexLine;

    }
     public static String formatFieldForRight(String field, int length, String addChar) {
        //String space = " ";
        if(field ==null)
            field ="";
        field = field.trim();
        int i = length - field.length();
        if (i <= 0) {
            field = field.substring(0, length);
        } else {
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < i; j++) {
                sb.append(addChar);
            }
            sb.append(field);

            field = sb.toString();
        }
        return field;

    }
}
