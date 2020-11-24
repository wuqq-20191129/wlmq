/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.fm.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    public static String getDay(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("dd");
        return f.format(d);
    }

    public static String getMonth(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("MM");
        return f.format(d);
    }

    public static String getYear(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy");
        return f.format(d);
    }

    public static String getHour(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("HH");
        return f.format(d);
    }

    public static String getMinute(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("mm");
        return f.format(d);
    }

    public static String getSecond(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("ss");
        return f.format(d);
    }

    public static Date getDateTimeFromString(String dateTime) {
        Date resultDate = null;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            resultDate = f.parse(dateTime);
        } catch (ParseException ex) {
            ex.printStackTrace();
            //  AppConstant.publicPanel.setopResultError(ex.getMessage());
        }
        return resultDate;
    }

    public static Date getDateTimeFromString(String date, String defaultTime) {
        Date resultDate = null;
        date += " " + defaultTime;
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        try {
            resultDate = f.parse(date);
        } catch (ParseException ex) {
            ex.printStackTrace();
            //  AppConstant.publicPanel.setopResultError(ex.getMessage());
        }
        return resultDate;
    }

    public static Date getDateFromString(String dateTime) {
        if (dateTime == null) {
            return null;
        }
        Date resultDate = null;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        try {
            resultDate = f.parse(dateTime);
        } catch (ParseException ex) {
            ex.printStackTrace();
            // AppConstant.publicPanel.setopResultError(ex.getMessage());
        }
        return resultDate;
    }

    /**
     *
     *  yyyyMM -> yyyy-MM
     *  yyyyMMdd -> yyyy-MM-dd
     *  yyyyMMddhhmmss->yyyy-MM-dd hh:mm:ss
     *  @param bcdDate 待转换的BCD格式日期
     */
    public static String bcdDateToString(String bcdDate) {
        if (bcdDate == null) {
            return "";
        }
        bcdDate = bcdDate.trim();
        int len = bcdDate.length();
        String date = "";
        if (len == 6) {
            date = bcdDate.substring(0, 4) + "-" + bcdDate.substring(4, 6);
        } else if (len == 8) {
            date = bcdDate.substring(0, 4) + "-" + bcdDate.substring(4, 6) + "-" + bcdDate.substring(6, 8);
        } else if (len == 14) {
            date = bcdDate.substring(0, 4) + "-" + bcdDate.substring(4, 6) + "-" + bcdDate.substring(6, 8) + " "
                    + bcdDate.substring(8, 10) + ":" + bcdDate.substring(10, 12) + ":" + bcdDate.substring(12, 14);
        } else {
            date = "";
        }

        return date;
    }

    public static String bcdMonthToString(String bcdDate) {
        if (bcdDate == null) {
            return null;
        }
        int len = bcdDate.length();
        String date = "";
        if (len == 6) {
            date = bcdDate.substring(0, 4) + "-" + bcdDate.substring(4, 6);
        } else if (len == 8) {
            date = bcdDate.substring(0, 2) + "-" + bcdDate.substring(2, 4) + " " + bcdDate.substring(4, 6) + ":" + bcdDate.substring(6, 8);
        } else if (len == 14) {
            date = bcdDate.substring(0, 4) + "-" + bcdDate.substring(4, 6) + "-" + bcdDate.substring(6, 8) + " "
                    + bcdDate.substring(8, 10) + ":" + bcdDate.substring(10, 12) + ":" + bcdDate.substring(12, 14);
        } else {
            date = null;
        }

        return date;
    }

    /**
     * @param  the date been change.
     * @return a string type yyyyMMdd
     */
    public static String dateToBcdString(java.util.Date d) {

        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        return f.format(d);
    }

    public static String dateToShortBcdString(java.util.Date d) {

        SimpleDateFormat f = new SimpleDateFormat("yyMMdd");
        return f.format(d);
    }

    public static String dateToBcdString(String d) {
        if (d == null || d.trim().length() == 0) {
            return "";
        }
        return dateToBcdString(getDateFromString(d));
    }

    public static String dateToString(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
        return f.format(d);
    }

    public static String dateToString(String d) {
        return dateToString(getDateFromString(d));
    }

    public static String dateToShortString(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        return f.format(d);
    }

    public static String dateToStringName(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
        return f.format(d);
    }

    public static String dateToStringShort(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        return f.format(d);
    }

    public static String datetimeToString(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.format(d);
    }

    public static String datetimeToStringForWindows(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return f.format(d);
    }

    public static void screenPrintForEx(String str) {
        System.out.println(datetimeToString(new Date()) + "  " + str);
    }

    public static String currentTodToString() {
        Date d = new Date(System.currentTimeMillis());
        return dateToString(d);
    }

    public static String currentTodToFormatString() {
        Date d = new Date(System.currentTimeMillis());

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.format(d);          //return dateToString(d);
    }

    public static java.sql.Timestamp utilDateToSqlTimestamp(java.util.Date utilDate) {
        return new java.sql.Timestamp(utilDate.getTime());
    }

    public static java.sql.Timestamp getCurrentSqlTimestamp() {
        Date utilDate = new Date(System.currentTimeMillis());
        return new java.sql.Timestamp(utilDate.getTime());
    }

    /**
     * 返回yyyy-MM-dd hh:mm:ss 的当前时间
     */
    public static String getCurrentStringDatetime() {
        return DateHelper.datetimeToString(new java.util.Date());
    }

    /**
     * 返回yyyyMMdd格式的当前时间
     */
    public static String getCurrentBcdStringDate() {
        return DateHelper.dateToBcdString(new java.util.Date());
    }

    public static String datetimeToStringOnlyDateF(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        return f.format(d);
    }

    public static String curentDateToHHMM() {
        SimpleDateFormat f = new SimpleDateFormat("HHmm");
        return f.format(new Date());
    }

    /**
     *
     * 输入原始时间,返回在此基础上增加days天后的时间
     * @param baseDate  基准时间
     * @param days 需要加的天数
     * @return <code>java.util.Date</code>格式的时间日期
     */
    public static Date addDaysRetDate(java.util.Date baseDate, int days) {


        Calendar calendar = Calendar.getInstance();
        if (baseDate == null) {
            //baseDate为空,则当前时间
            calendar.setTime(new java.util.Date());
        } else {
            calendar.setTime(baseDate);
        }

        calendar.add(Calendar.DATE, days);
        // System.out.println("ret:"+calendar.getTime());
        //GregorianCalendar gc =new GregorianCalendar();
        return calendar.getTime();
    }

    /**
     *
     * 输入原始时间,返回在此基础上增加days天后的时间
     * @param baseDate  基准时间
     * @param days 需要加的天数
     * @return <code>String</code>格式的时间日期yyyy-MM-dd
     */
    public static String addDaysRetShortString(java.util.Date baseDate, int days) {
        return DateHelper.dateToShortString(DateHelper.addDaysRetDate(baseDate, days));
    }

    /**
     *
     * 输入原始时间,返回在此基础上增加days天后的时间
     * @param baseDate  基准时间
     * @param days 需要加的天数
     * @return <code>String</code>格式的时间日期yyMMdd
     */
    public static String addDaysRetShortBcdString(java.util.Date baseDate, int days) {

        return DateHelper.dateToShortBcdString(DateHelper.addDaysRetDate(baseDate, days));
    }

    /**
     *
     * 输入原始时间,返回在此基础上增加days天后的时间
     * @param baseDate  基准时间
     * @param days 需要加的天数
     * @return <code>String</code>格式的时间日期yyyyMMdd
     */
    public static String addDaysRetBcdString(java.util.Date baseDate, int days) {

        return DateHelper.dateToBcdString(DateHelper.addDaysRetDate(baseDate, days));
    }

    /**
     *
     * 输入原始时间,返回在此基础上增加days天后的时间
     * @param baseDate  基准时间
     * @param days 需要加的天数
     * @return <code>String</code>格式的时间日期yyyy-MM-dd hh:mm:ss
     */
    public static String addDaysRetString(java.util.Date baseDate, int days) {
        return DateHelper.datetimeToString(DateHelper.addDaysRetDate(baseDate, days));
    }
}
