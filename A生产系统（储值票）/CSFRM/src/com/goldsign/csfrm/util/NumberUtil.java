package com.goldsign.csfrm.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数字处理工具类
 * 
 * @author lenvo
 */
public class NumberUtil {

    /**
     * Creates a new instance of NumberFormatUtil
     */
    public NumberUtil() {
    }

    /**
     * 格式化数字转为字符串
     * 
     * @param num 需要格式化的数字
     * @param length 小数点后保留的位数
     */
    public static String keepAfterDot(float num, int length) {
        BigDecimal bigDecimal = new BigDecimal(num);
        float flo = bigDecimal.setScale(length, BigDecimal.ROUND_HALF_UP).floatValue();

        String pattern = "#0";
        if (length > 0) {
            pattern += ".";
        }
        for (int i = 0; i < length; i++) {
            pattern += "0";
        }
        DecimalFormat fnum = new DecimalFormat(pattern);
        String str = fnum.format(flo);
        //System.out.println(str);
        return str;
    }

    /**
     * 格式化数字转为字符串
     * 
     * @param num 需要格式化的数字
     * @param length 小数点后保留的位数
     */
    public static String keepAfterDot(double num, int length) {
        BigDecimal bigDecimal = new BigDecimal(num);
        double flo = bigDecimal.setScale(length, BigDecimal.ROUND_HALF_UP).doubleValue();

        String pattern = "#0";
        if (length > 0) {
            pattern += ".";
        }
        for (int i = 0; i < length; i++) {
            pattern += "0";
        }
        DecimalFormat fnum = new DecimalFormat(pattern);
        String str = fnum.format(flo);
        // System.out.println(str);
        return str;
    }

    /**
     *前面补0
     * 
     * @param waterNo 需要在前面补零的数字
     * @param len 补零后的长度
     * @return 长度为len的字符串
     */
    public static String addBeforeZero(int waterNo, int len) {
        if (waterNo < 0 || len < 1) {
            return "";
        }
        String ret = "" + waterNo;
        if (ret.length() >= len) {
            return ret.substring(ret.length() - len);
        }
        long addNum = (long) Math.pow(10.00, (double) len);
        addNum += waterNo;
        ret = "" + addNum;
        return ret.substring(1);
    }

    /**
     * 前面补0
     * 
     * @param waterNo
     * @param len
     * @return 
     */
    public static String addBeforeZero(long waterNo, int len) {
        if (waterNo < 0 || len < 1) {
            return "";
        }
        String ret = "" + waterNo;
        if (ret.length() >= len) {
            return ret.substring(ret.length() - len);
        }
        long addNum = (long) Math.pow(10.00, (double) len);
        addNum += waterNo;
        ret = "" + addNum;
        return ret.substring(1);
    }

    /**
     * 如果待转换的字符串是空，则返回0 如果输入的字符串不是数字，也返回0 否则返回对应的浮点数
     *
     * @param strNum 转换的字符串
     * @return A number type of float.
     */
    public static float parseFloat(String strNum) {
        if (strNum == null || strNum.trim().length() == 0) {
            return 0;
        }
        float ret = 0;
        try {
            ret = Float.parseFloat(strNum.trim());
        } catch (Exception e) {
            return 0;
        }
        return ret;
    }

    /**
     * 如果待转换的字符串是空，则返回0 如果输入的字符串不是数字，也返回0 否则返回对应的整型数
     *
     * @param strNum 转换的字符串
     * @return A number type of int.
     */
    public static int parseInt(String strNum) {
        if (strNum == null || strNum.trim().length() == 0) {
            return 0;
        }
        int ret = 0;
        try {
            ret = Integer.parseInt(strNum.trim());
        } catch (Exception e) {
            return 0;
        }
        return ret;
    }

    /**
     * 如果待转换的字符串是空，则返回0 如果输入的字符串不是数字，也返回0 否则返回对应的双精度数
     *
     * @param strNum 转换的字符串
     * @return A number type of double.
     */
    public static double parseDouble(String strNum) {

        if (strNum == null || strNum.trim().length() == 0) {
            return 0;
        }
        double ret = 0;
        try {
            ret = Double.parseDouble(strNum.trim());
        } catch (Exception e) {
            return 0;
        }
        return ret;
    }

    /**
     * 格化化数字
     * 
     * @param pattern
     * @param n
     * @return 
     */
    public static String formatNumber(String pattern, int n) {
        DecimalFormat f = new DecimalFormat(pattern);
        return f.format(n);
    }

    /**
     * 增加
     * 
     * @param base
     * @param add
     * @return 
     */
    public static double doubleAdd(double base, double add) {
        BigDecimal b1 = new BigDecimal(Double.toString(base));
        BigDecimal b2 = new BigDecimal(Double.toString(add));
        return b1.add(b2).doubleValue();
    }
    
    /**
     * 把数字转为字符串，如为整形则为无小数点字符串
     * 
     * @param price
     * @return 
     */
    public static String getDecimalText(double price) {
        if (price % 1.0 == 0) {
            return String.valueOf((int) price);
        } else {
            return String.valueOf(price);
        }
    }
}
