/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.csfrm.util;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * 格式化为固定长度(左边0)
     *
     * @param str
     * @param totalLen
     * @return
     */
    public static String fmtStrLeftZeroLen(String str, int totalLen){
        
        return fmtStrLeftLen(str, totalLen, "0");
    }

    /**
     * 格式化为固定长度(右边0)
     *
     * @param str
     * @param totalLen
     * @param ch
     * @return
     */
    public static String fmtStrRightZeroLen(String str, int totalLen){
       
        return fmtStrRightLen(str, totalLen, "0");
    }
    
    /**
     * 格式化为固定长度(左边空格)
     *
     * @param str
     * @param totalLen
     * @return
     */
    public static String fmtStrLeftEmptyLen(String str, int totalLen){
        
        return fmtStrLeftLen(str, totalLen, " ");
    }

    /**
     * 格式化为固定长度(右边空格)
     *
     * @param str
     * @param totalLen
     * @param ch
     * @return
     */
    public static String fmtStrRightEmptyLen(String str, int totalLen){
       
        return fmtStrRightLen(str, totalLen, " ");
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
     * 后面加字符 -gbk
     * 
     * @param str
     * @param totalLen
     * @param chars
     * @return 
     */
    public static String addCharsAfterGBK(String str, int totalLen, String chars){
        if(null == str){
                str = "";
            }
        String strRet = str;
        try {
            int strLen = str.getBytes("GBK").length;
            int len = totalLen - strLen;
            for (int i = 0; i < len; i++) {
                strRet = strRet + chars;
            }
            if(len < 0){
                strRet = strRet.substring(0, strLen);
            }
            
            return strRet;
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return strRet;
    }
    
    /**
     * 后面加字符 -utf-8
     * 
     * @param str
     * @param totalLen
     * @param chars
     * @return 
     */
    public static String addCharsAfterUTF(String str, int totalLen, String chars){
        if(null == str){
                str = "";
            }
        String strRet = str;
        try {
            int strLen = str.getBytes("UTF-8").length;
            int len = totalLen - strLen;
            for (int i = 0; i < len; i++) {
                strRet = strRet + chars;
            }
            if(len < 0){
                strRet = strRet.substring(0, strLen);
            }
            
            return strRet;
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
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

        return addCharsAfter(str, totalLen, " ");
        
    }
    
    /**
     * 字符串后面加空格 -中文
     * 
     * @param str
     * @param totalLen
     * @return 
     */
    public static String addEmptyAfterGBK(String str, int totalLen) {

        return addCharsAfterGBK(str, totalLen, " ");
        
    }

    /**
     * 字符串后面加空格 -中文
     * 
     * @param str
     * @param totalLen
     * @return 
     */
    public static String addEmptyAfterUTF(String str, int totalLen) {

        return addCharsAfterUTF(str, totalLen, " ");
        
    }
    
    /**
     * 字符串前面加空格
     * 
     * @param str
     * @param totalLen
     * @return 
     */
    public static String addEmptyBefore(String str, int totalLen) {

        return addCharsBefore(str, totalLen, " ");
    }
    
    /**
     *字符串前面加0
     * 
     * @param waterNo 需要在前面补零的字符串
     * @param len 补零后的长度
     * @return 长度为len的字符串
     */
    public static String addZeroBefore(String waterNo, int len) {
        
        return addCharsBefore(waterNo, len, "0");

    }
    
    /**
     *字符串后面加0
     * 
     * @param waterNo 需要在前面补零的字符串
     * @param len 补零后的长度
     * @return 长度为len的字符串
     */
    public static String addZeroAfter(String waterNo, int len) {

        return addCharsAfter(waterNo, len, "0");

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
     * 是否长整型
     *
     * @param text
     * @return
     */
    public static boolean isLong(String text) {
        try {
            Long.parseLong(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
     /**
     * 字符串转长整型
     * 
     * @param text
     * @return 
     */
    public static long getLong(String text) {

        long ret = 0;
        try {
            ret = Long.parseLong(text);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return ret;
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
     * 是否浮点型
     *
     * @param text
     * @return
     */
    public static boolean isDouble(String text) {
        try {
            Double.parseDouble(text);
            return true;
        } catch (Exception e) {
            return false;
        }
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
            //ret = Byte.parseByte(text);
            ret = (byte)Short.parseShort(text);
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
    
    /**
     * 字符串为空时返回""
     * @param str
     * @return 
     */
    public static String nullToString(String str){
        if(str==null){
            str = "";
        }
        return str;
    }
    
    //byte转String
    public static String getString(byte x){
        return String.format("%08d", Integer.valueOf(Integer.toBinaryString(Integer.valueOf(x&0xFF))));
    }
    
    public static void main(String[] args){
        System.out.println(fmtStrLeftLen(null, 10, "0"));
        System.out.println("12345678901234567890");
    }
}
