package com.goldsign.csfrm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间工具类型
 * 
 * @author lenovo
 */
public class DateHelper {

    /**
     * 取得天
     * 
     * @param d
     * @return 
     */
    public static String getDay(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("dd");
        return f.format(d);
    }

    /**
     * 取得月
     * 
     * @param d
     * @return 
     */
    public static String getMonth(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("MM");
        return f.format(d);
    }

    /**
     * 取得年
     * 
     * @param d
     * @return 
     */
    public static String getYear(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy");
        return f.format(d);
    }

    /**
     * 取得时
     * 
     * @param d
     * @return 
     */
    public static String getHour(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("HH");
        return f.format(d);
    }

    /**
     * 取得分
     * 
     * @param d
     * @return 
     */
    public static String getMinute(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("mm");
        return f.format(d);
    }

    /**
     * 取得秒
     * 
     * @param d
     * @return 
     */
    public static String getSecond(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("ss");
        return f.format(d);
    }

    /**
     * 时间字符串转日期时间（yyyyMMddHHmmss）
     * 
     * @param dateTime
     * @return 
     */
    public static Date str14yyyyMMddHHmmssToDate(String dateTime) {
        Date resultDate = null;
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            resultDate = f.parse(dateTime);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return resultDate;
    }
    
    /**
     * 时间字符串转日期时间（yyyy-MM-dd HH:mm:ss）
     * 
     * @param dateTime
     * @return 
     */
    public static Date str19yyyy_MM_dd_HH_mm_ssToDate(String dateTime) {
        Date resultDate = null;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            resultDate = f.parse(dateTime);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return resultDate;
    }
    
    /**
     * 时间字符串转日期（yyyy-MM-dd）
     * 
     * @param dateTime
     * @return 
     */
    public static Date str10yyyy_MM_ddToDate(String dateTime) {
        Date resultDate = null;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        try {
            resultDate = f.parse(dateTime);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return resultDate;
    }
    
    /**
     * 时间字符串转日期（yyyyMMdd）
     * 
     * @param dateTime
     * @return 
     */
    public static Date str8yyyyMMddToDate(String date) {
        Date resultDate = null;
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        try {
            resultDate = f.parse(date);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return resultDate;
    }

    /**
     * 日期转日期时间字符串（yyyyMMddHHmmss）
     * 
     * @param dateTime
     * @return 
     */
    public static String dateToStr14yyyyMMddHHmmss(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
        return f.format(d);
    }
    
    /**
     * 日期转日期时间字符串（yyyyMMdd HHmmss）
     *
     * @param dateTime
     * @return
     */
    public static String dateToStr15yyyyMMdd_HHmmss(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd HHmmss");
        return f.format(d);
    }
    
    /**
     * 日期转日期字符串（yyyyMMdd）
     * 
     * @param dateTime
     * @return 
     */
    public static String dateToStr8yyyyMMdd(java.util.Date d) {

        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        return f.format(d);
    }

    /**
     * 日期转日期字符串（yyMMdd）
     * 
     * @param dateTime
     * @return 
     */
    public static String dateToStr6yyMMdd(java.util.Date d) {

        SimpleDateFormat f = new SimpleDateFormat("yyMMdd");
        return f.format(d);
    }

    /**
     * 日期转日期字符串（yyyy-MM-dd）
     * 
     * @param dateTime
     * @return 
     */
    public static String dateToStr8yyyy_MM_dd(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        return f.format(d);
    }

    /**
     * 日期转日期时间字符串（yyyy-MM-dd HH:mm:ss）
     * 
     * @param dateTime
     * @return 
     */
    public static String dateToStr19yyyy_MM_dd_HH_mm_ss(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.format(d);
    }

    /**
     * 控制台输出带时间的内容
     * 
     * @param str 
     */
    public static void screenPrintForEx(String str) {
        System.out.println(dateToStr19yyyy_MM_dd_HH_mm_ss(new Date()) + "  " + str);
    }

    /**
     * 控制台输出内容
     *
     * @param str
     */
    public static void screenPrintForEx(Object obj) {
        System.out.println(obj);
    }
    
    /**
     * 控制台输出内容
     *
     * @param str
     */
    public static void screenPrintFor(String str) {
        System.out.println(str);
    }
    
    /**
     * 控制台输出带时间的内容
     *
     * @param v
     */
    public static void screenPrintForEx(List v) {
        if (v == null) {
            return;
        }
        for (int i = 0; i < v.size(); i++) {
            screenPrintFor(v.get(i).toString());
        }
    }

    /**
     * 控制台输出带时间的内容
     *
     * @param ht
     */
    public static void screenPrintForEx(Map ht) {
        if (ht == null) {
            return;
        }
        Set set = ht.keySet();
        Iterator it = set.iterator();
        String key;
        while (it.hasNext()) {
            key = (String) it.next();
            if (key.toLowerCase().indexOf("password") != -1) {
                screenPrintFor(" 键值= " + key + " 配置值=" + "******");
            } else {
                screenPrintFor(" 键值= " + key + " 配置值=" + ht.get(key));
            }
        }
    }
    
    /**
     * 日期字符串（yyyy-MM-dd）转日期字符串（yyyyMMdd）
     * 
     * @param str
     * @return 
     */
    public static String str10yyyy_MM_ddToStr8yyyyMMdd(String str) {
        return dateToStr8yyyyMMdd(str10yyyy_MM_ddToDate(str));
    }

    /**
     * 取当前日期字符串(yyyyMMdd)
     * 
     * @return 
     */
    public static String curDateToStr8yyyyMMdd() {
        return DateHelper.dateToStr8yyyyMMdd(new java.util.Date());
    }
    
    /**
     * 取当前日期时间字符串(yyyyMMddHHmmss)
     * 
     * @return 
     */
    public static String curDateToStr14yyyyMMddHHmmss() {
        Date d = new Date(System.currentTimeMillis());
        return dateToStr14yyyyMMddHHmmss(d);
    }
    
    /**
     * 取当前日期时间字符串(yyyyMMdd HHmmss)
     *
     * @return
     */
    public static String curDateToStr15yyyyMMdd_HHmmss() {
        Date d = new Date(System.currentTimeMillis());
        return dateToStr15yyyyMMdd_HHmmss(d);
    }

    /**
     * 取当前日期时间字符串(yyyy-MM-dd HH:mm:ss)
     * 
     * @return 
     */
    public static String curDateToStr19yyyy_MM_dd_HH_mm_ss() {
        Date d = new Date(System.currentTimeMillis());
        return dateToStr19yyyy_MM_dd_HH_mm_ss(d);          
    }

    /**
     * java.util.Date转java.sql.Timestamp格式
     * 
     * @param utilDate
     * @return 
     */
    public static java.sql.Timestamp utilDateToSqlTimestamp(java.util.Date utilDate) {
        return new java.sql.Timestamp(utilDate.getTime());
    }

    /**
     * 取当前日期时间
     * 
     * @return 
     */
    public static java.sql.Timestamp getCurSqlTimestamp() {
        Date utilDate = new Date(System.currentTimeMillis());
        return new java.sql.Timestamp(utilDate.getTime());
    }

    /**
     * 添加N天,负数表示减
     * 
     * @param baseDate
     * @param days
     * @return 
     */
    public static Date addDaysToDate(java.util.Date baseDate, int days) {

        Calendar calendar = Calendar.getInstance();
        if (baseDate == null) {
            calendar.setTime(new java.util.Date());
        } else {
            calendar.setTime(baseDate);
        }

        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }
    
    /*
     * String to String 字符串yyyyMMdd转yyyy-MM-dd
     * 
     * @param dateStr
     * @return toDate
     */
    public static String str8yyyyMMddTo10yyyy_MM_dd(String dateStr){
        String toDate = dateStr.substring(0,4);
        toDate += "-";
        toDate += dateStr.substring(4,6);
        toDate += "-";
        toDate += dateStr.substring(6,8);
        return toDate;
    }
}
