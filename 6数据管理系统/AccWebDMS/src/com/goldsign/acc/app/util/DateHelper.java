package com.goldsign.acc.app.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hejj
 */
public class DateHelper {

    // private static int N = 0;
    /**
     * 时间格式:yyyyMMdd
     */
    private static final SimpleDateFormat YYYYMMDD = new SimpleDateFormat(
            "yyyyMMdd");
    /**
     * 时间格式:HHmm
     */
    private static final SimpleDateFormat HHMM = new SimpleDateFormat("HHmm");
    /**
     * 时间格式:yyyyMMddHHmmss
     */
    private static final SimpleDateFormat YYYYMMDDHHMMSS = new SimpleDateFormat(
            "yyyyMMddHHmmss");
    /**
     * 时间格式:yyyy-MM-dd
     */
    private static final SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat(
            "yyyy-MM-dd");
    /**
     * 时间格式:yyyy-MM-dd HH:mm:ss
     */
    private static final SimpleDateFormat YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public static synchronized String datetimeToStringOnlyDateF(java.util.Date d) {
        return YYYYMMDD.format(d);
    }

    public static synchronized String curentDateToHHMM() {
        return HHMM.format(new Date());
    }

    public static String getDateBefore(String curDate, long beforeTime) {
        Date d;
        String dateBefore = "";
        try {
            synchronized (YYYYMMDD) {
                d = YYYYMMDD.parse(curDate);
                Date datePre = new Date(d.getTime() - beforeTime);
                dateBefore = YYYYMMDD.format(datePre);
            }
        } catch (ParseException e) {
            Logger.getLogger(DateHelper.class.getName()).log(Level.SEVERE,
                    null, e);
        }
        return dateBefore;

    }

    public static String getDateAfter(String curDate, long afterTime) {

        Date d;
        String dateAfter = "";
        try {
            synchronized (YYYYMMDD) {
                d = YYYYMMDD.parse(curDate);
                Date datePre = new Date(d.getTime() + afterTime);
                dateAfter = YYYYMMDD.format(datePre);
            }
        } catch (ParseException e) {
            Logger.getLogger(DateHelper.class.getName()).log(Level.SEVERE,
                    null, e);
        }
        return dateAfter;

        // df.
    }

    public static String getDateTimeAfter(String curDate, long afterTime) {

        Date d;
        String dateAfter = "";
        try {
            synchronized (YYYYMMDDHHMMSS) {
                d = YYYYMMDDHHMMSS.parse(curDate);
                Date datePre = new Date(d.getTime() + afterTime);
                dateAfter = YYYYMMDDHHMMSS.format(datePre);
            }
        } catch (ParseException e) {
            Logger.getLogger(DateHelper.class.getName()).log(Level.SEVERE,
                    null, e);
        }
        return dateAfter;

    }

    public static synchronized String dateToString(java.util.Date d) {
        return YYYYMMDDHHMMSS.format(d);
    }

    public static synchronized String dateOnlyToString(java.util.Date d) {
        return YYYYMMDD.format(d);
    }

    public static synchronized String dateToStringName(java.util.Date d) {
        return YYYY_MM_DD_HH_MM_SS.format(d);
    }

    public static  synchronized  String datetimeToString(java.util.Date d) {
        return YYYY_MM_DD_HH_MM_SS.format(d);
    }

    public static synchronized String datetimeToStringByDate(java.util.Date d) {
        return YYYY_MM_DD.format(d);
    }


    public static String currentTodToString() {
        Date d = new Date(System.currentTimeMillis());
        return dateToString(d);
    }

    public static java.sql.Timestamp utilDateToSqlTimestamp(
            java.util.Date utilDate) {
        return new java.sql.Timestamp(utilDate.getTime());
    }

    public static java.sql.Timestamp getCurrentSqlTimestamp() {
        Date utilDate = new Date(System.currentTimeMillis());
        return new java.sql.Timestamp(utilDate.getTime());
    }

    public static synchronized long getDiffer(String date, Date date1) throws ParseException {
        Date d = YYYY_MM_DD_HH_MM_SS.parse(date);
        long n1 = date1.getTime();
        long n = d.getTime();
        return Math.abs(n1 - n);

    }

    public static synchronized long getDiffer(String date, String date1)
            throws ParseException {

        Date d = YYYY_MM_DD_HH_MM_SS.parse(date);
        Date d1 = YYYY_MM_DD_HH_MM_SS.parse(date1);
        long n1 = d1.getTime();
        long n = d.getTime();
        return Math.abs(n1 - n);

    }

    public static synchronized long getDifferInDays(String date, String date1)
            throws ParseException {

        Date d = YYYY_MM_DD.parse(date);
        Date d1 = YYYY_MM_DD.parse(date1);
        long n1 = d1.getTime();
        long n = d.getTime();
        long days = (n1 - n) / (24 * 3600 * 1000);
        return days;

    }

    public static String convertStandFormat(String ymd) {
        return ymd.substring(0, 4) + "-" + ymd.substring(4, 6) + "-"
                + ymd.substring(6, 8);
    }

    public static synchronized boolean isGEIndicateDays(String date, Date date1, int difDays)
            throws ParseException {

        Date d = YYYY_MM_DD.parse(date);
        long n1 = date1.getTime();
        long n = d.getTime();
        long dif = difDays * 24 * 3600 * 1000;
        if (n1 - n >= dif) {
            return true;
        }
        return false;

    }

    public static synchronized boolean isLEIndicateDays(String date, Date date1, int difDays)
            throws ParseException {

        Date d = YYYY_MM_DD.parse(date);
        long n1 = date1.getTime();
        long n = d.getTime();
        long dif = difDays * 24 * 3600 * 1000;
        if (n1 - n <= dif) {
            return true;
        }
        return false;

    }

    private static synchronized Date dateStrToUtilDate(String dateStr) {
        ParsePosition pos = new ParsePosition(0);
        return YYYYMMDDHHMMSS.parse(dateStr, pos);
    }

    public static Timestamp dateStrToSqlTimestamp(String dateStr) {
        java.util.Date utilDate = dateStrToUtilDate(dateStr);
        return new Timestamp(utilDate.getTime());
    }

    public static Date strToDate(String dateStr, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(dateStr);
    }
}
