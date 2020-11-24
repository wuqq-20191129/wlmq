package com.goldsign.acc.frame.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateHelper {

    public static long daysBetweenTwo(Date date1, Date date2) {
        long time0 = date1.getTime();
        long time1 = date2.getTime();
        long days = (time1 - time0) / (1000 * 60 * 60 * 24);
        return days;
    }

    public static String dateToStringByFormat(Date date, String format) {
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(date);
    }

    public static String dateToString(Date date) {
        if(date==null){
            return "";
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.format(date);
    }

    public static Date stringToDate(String dateStr) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.parse(dateStr);
    }

    public static Date stringToDateByFormat(String dateStr, String format) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.parse(dateStr);
    }

    public static String getDbDateStr(String dateStr) {
        if (dateStr != null && !dateStr.isEmpty()) {
            return dateStr.substring(0, 19);
        } else {
            return dateStr;
        }
    }
    
    public static java.sql.Timestamp dateStrToSqlTimestamp(String dateStr) throws ParseException {
        java.util.Date utilDate = stringToDate(dateStr);
        return new java.sql.Timestamp(utilDate.getTime());
    }
}