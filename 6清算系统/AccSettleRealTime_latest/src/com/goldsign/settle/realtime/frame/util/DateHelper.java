/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hejj
 */
public class DateHelper {

    private static int N = 0;

    public static String datetimeToStringOnlyDateF(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        return f.format(d);
    }

    public static String curentDateToHHMM() {
        SimpleDateFormat f = new SimpleDateFormat("HHmm");
        return f.format(new Date());
    }

    public static String getDateBefore(String curDate, long beforeTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date d;
        String dateBefore = "";
        try {
            d = sdf.parse(curDate);
            Date datePre = new Date(d.getTime() - beforeTime);
            dateBefore = sdf.format(datePre);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateBefore;

        //df.
    }

    public static String getDateAfter(String curDate, long afterTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date d;
        String dateAfter = "";
        try {
            d = sdf.parse(curDate);
            Date datePre = new Date(d.getTime() + afterTime);
            dateAfter = sdf.format(datePre);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateAfter;

        //df.
    }

    public static String getDateTimeAfter(String curDate, long afterTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date d;
        String dateAfter = "";
        try {
            d = sdf.parse(curDate);
            Date datePre = new Date(d.getTime() + afterTime);
            dateAfter = sdf.format(datePre);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateAfter;

        //df.
    }

    public static String dateToString(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
        return f.format(d);
    }

    public static String dateOnlyToString(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        return f.format(d);
    }

    public static String dateToStringName(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
        return f.format(d);
    }

    public static String datetimeToString(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.format(d);
    }

    public static String datetimeToStringByDate(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        return f.format(d);
    }

    public static String currentTodToString() {
        Date d = new Date(System.currentTimeMillis());
        return dateToString(d);
    }

    public static java.sql.Timestamp utilDateToSqlTimestamp(java.util.Date utilDate) {
        return new java.sql.Timestamp(utilDate.getTime());
    }

    public static java.sql.Timestamp getCurrentSqlTimestamp() {
        Date utilDate = new Date(System.currentTimeMillis());
        return new java.sql.Timestamp(utilDate.getTime());
    }

    public static long getDiffer(String date, Date date1) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = f.parse(date);
        long n1 = date1.getTime();
        long n = d.getTime();
        return Math.abs(n1 - n);

    }

    public static long getDiffer(String date, String date1) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = f.parse(date);
        Date d1 = f.parse(date1);
        long n1 = d1.getTime();
        long n = d.getTime();
        long diff = Math.abs(n1 - n) + 1;//时间间隔
        return diff;

    }

    public static long getDifferInDays(String date, String date1) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Date d = f.parse(date);
        Date d1 = f.parse(date1);
        long n1 = d1.getTime();
        long n = d.getTime();
        long days = (n1 - n) / (24 * 3600 * 1000);
        return days;

    }

    public static long getDifferInDaysForYYYYMMDD(String date, String date1) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        Date d = f.parse(date);
        Date d1 = f.parse(date1);
        long n1 = d1.getTime();
        long n = d.getTime();
        long days = (n1 - n) / (24 * 3600 * 1000);
        return days;

    }

    public static String convertStandFormat(String ymd) {
        return ymd.substring(0, 4) + "-" + ymd.substring(4, 6) + "-" + ymd.substring(6, 8);
    }

    public static String convertStandFormatDatetime(String ymdhms) {
        if (ymdhms == null) {
            return "";
        }
        if (ymdhms.length() < 14) {
            return ymdhms;
        }
        return ymdhms.substring(0, 4) + "-" + ymdhms.substring(4, 6) + "-" + ymdhms.substring(6, 8) + " "
                + ymdhms.substring(8, 10) + ":" + ymdhms.substring(10, 12) + ":" + ymdhms.substring(12, 14);
    }
     public static String convertStandFormatOnlyDate(String ymd) {
        if (ymd == null) {
            return "";
        }
        if (ymd.length() < 8) {
            return ymd;
        }
        return ymd.substring(0, 4) + "-" + ymd.substring(4, 6) + "-" + ymd.substring(6, 8) + " "
               ;
    }

    public static boolean isGEIndicateDays(String date, Date date1, int difDays) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Date d = f.parse(date);
        long n1 = date1.getTime();
        long n = d.getTime();
        long dif = difDays * 24 * 3600 * 1000;
        if (n1 - n >= dif) {
            return true;
        }
        return false;

    }

    public static boolean isLEIndicateDays(String date, Date date1, int difDays) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Date d = f.parse(date);
        long n1 = date1.getTime();
        long n = d.getTime();
        long dif = difDays * 24 * 3600 * 1000;
        if (n1 - n <= dif) {
            return true;
        }
        return false;

    }

    public static void main(String[] args) {
        String date = "20120410";
        String cur = "20120409";
        //Date cur = new Date();
        int difDays = 2;
        try {
            // FrameDateHelper.isLEIndicateDays(date, cur, difDays);
            // System.out.println(date+"与"+cur+"相差小于等于"+difDays);
            //String datePre = FrameDateHelper.getDateBefore("20100704", 3600000 * 24);
            //System.out.println(datePre);
            long n = getDifferInDaysForYYYYMMDD(date, cur);
            System.out.println("相差天数:" + n);
        } catch (ParseException ex) {
            Logger.getLogger(DateHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
