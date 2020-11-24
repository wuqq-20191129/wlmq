/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.util;

/**
 * 字符串处理工具类
 * 
 * @author lenovo
 */
public class StringUtil {

    /**
     * 格式化为固定长度(左边)
     *
     * @param str
     * @param totalLen
     * @param ch
     * @return
     */
    public static String fmtStrLeftLen(String str, int totalLen, String ch){
        if(null == str){
            str = "";
        }
        int len = str.length();
        if(len > totalLen){
            return str.substring(len - totalLen);
        }
        return addCharsBefore(str, totalLen, ch);
    }

    /**
     * 格式化为固定长度(右边)
     *
     * @param str
     * @param totalLen
     * @param ch
     * @return
     */
    public static String fmtStrRightLen(String str, int totalLen, String ch){
        if(null == str){
            str = "";
        }
        int len = str.length();
        if(len > totalLen){
            return str.substring(0, totalLen);
        }
        return addCharsAfter(str, totalLen, ch);
    }
    
    /**
     * 后面加字符
     *
     * @param str
     * @param totalLen
     * @param chars
     * @return
     */
    public static String addCharsAfter(String str, int totalLen, String chars){
        if(null == str){
            str = "";
        }
        String strRet = str;
        int strLen = str.getBytes().length;
        int len = totalLen - strLen;
        for (int i = 0; i < len; i++) {
            strRet = strRet + chars;
        }
        if(len < 0){
            strRet = strRet.substring(0, strLen);
        }
        
        return strRet;
    }
    
    /**
     * 后面加字符，如果字符串过长，将截取指定长度
     *
     * @param str
     * @param totalLen
     * @param chars
     * @return
     */
    public static String addCharsAfter2(String str, int totalLen, String chars){
        if(null == str){
            str = "";
        }
        String strRet = str;
        int strLen = str.getBytes().length;
        int len = totalLen - strLen;
        for (int i = 0; i < len; i++) {
            strRet = strRet + chars;
        }
        if(len < 0){
            strRet = strRet.substring(0, totalLen);
        }
        
        return strRet;
    }
    
    /**
     * 前面加字符
     * 
     * @param str
     * @param totalLen
     * @param chars
     * @return 
     */   
    public static String addCharsBefore(String str, int totalLen, String chars) {

        if (null == str) {
            str = "";
        }
        String strRet = str;
        int strLen = str.getBytes().length;
        int len = totalLen - strLen;
        for (int i = 0; i < len; i++) {
            strRet = chars + strRet;
        }

        if(len < 0){
            strRet = strRet.substring(-len, strLen);
        }
        return strRet;
    }
    
    /**
     * 字符串后面加空格
     * 
     * @param str
     * @param totalLen
     * @return 
     */
    public static String addEmptyAfter(String str, int totalLen) {

        String strRet = str;
        int strLen = str.getBytes().length;
        int len = totalLen - strLen;
        for (int i = 0; i < len; i++) {
            strRet = strRet + " ";
        }

        return strRet;
    }

    /**
     * 字符串前面加空格
     * 
     * @param str
     * @param totalLen
     * @return 
     */
    public static String addEmptyBefore(String str, int totalLen) {

        String strRet = str;
        int strLen = str.getBytes().length;
        int len = totalLen - strLen;
        for (int i = 0; i < len; i++) {
            strRet = " " + strRet;
        }

        return strRet;
    }
    
    /**
     *字符串前面加0
     * 
     * @param waterNo 需要在前面补零的字符串
     * @param len 补零后的长度
     * @return 长度为len的字符串
     */
    public static String addBeforeZero(String waterNo, int len) {
        if (len < 1) {
            return "";
        }
        if (waterNo == null) {
            waterNo = "";
        }
        String ret = "" + waterNo;
        String addStr = "";
        for (int i = 0; i < len; i++) {
            addStr += "0";
        }

        ret = addStr + ret;
        return ret.substring(ret.length() - len);

    }
     
    /**
     * 流水号自动+1
     *
     * @param waterNo 流水号 返回流水号+1
     */
    public static String waterNoAddOne(String waterNo) {
        if (waterNo == null || waterNo.trim().length() == 0) {
            return "";
        }
        long baseNum = 0;
        try {
            baseNum = Long.parseLong(waterNo.trim());
        } catch (Exception e) {
            return "";
        }
        int len = waterNo.trim().length();
        long addNum = (long) Math.pow(10.00, (double) len) + 1;
        String ret = "" + (addNum + baseNum);
        return ret.substring(1);

    }

    /**
     * 是否整型
     * 
     * @param text
     * @return 
     */
    public static boolean isInt(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 字符串转整型
     * 
     * @param text
     * @return 
     */
    public static int getInt(String text) {

        int ret = 0;
        try {
            ret = Integer.parseInt(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 字符串转又精浮点型
     * 
     * @param text
     * @return 
     */
    public static double getDouble(String text) {
        double ret = 0;
        try {
            ret = Double.parseDouble(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    
    /**
     * 字符串转短整型
     * 
     * @param text
     * @return 
     */
    public static short getShort(String text){
        short ret = 0;
        try {
            ret = Short.parseShort(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    
    /**
     * 取字符串的字节值
     * 
     */
    public static byte getByte(String text) {
        byte ret = 0;
        try {
            ret = Byte.parseByte(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    
    /**
     * 反转字符串
     * 
     * @param str
     * @return 
     */
    public static String reverse(String str){
    
        StringBuffer sb = new StringBuffer(str);
        
        return sb.reverse().toString();
    }
    
    /**
     * 字符串是否为空，包括NULL和空串
     * 
     * @param str
     * @return 
     */
    public static boolean isEmpty(String str){
        
        if(null == str){
            return true;
        }
        
        return str.trim().equals("");
    }
}
