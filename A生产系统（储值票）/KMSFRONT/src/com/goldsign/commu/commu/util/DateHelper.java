package com.goldsign.commu.commu.util;

import com.goldsign.commu.commu.env.BaseConstant;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class DateHelper {

    private static Logger logger = Logger.getLogger(DateHelper.class.getName());
    
    public static Date getDatetimeFromYYYYMMDDHHMMSS(String d) {
        Date date = null;
        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddhhmmss");
            date = sd.parse(d);
        } catch (ParseException ex) {
            logger.error(ex.getMessage());
        }
        return date;
    }
    
    public static Date getDatetimeFromYYYY_MM_DDHHMMSS(String d) {
        Date date = null;
        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sd.parse(d);
        } catch (ParseException ex) {
            logger.error(ex.getMessage());
        }
        return date;
    }
    
    public static java.sql.Date getDateFromYYYY_MM_DDHHMMSS_SQL(String d) {
        Date date = null;
        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sd.parse(d);
        } catch (ParseException ex) {
            logger.error(ex.getMessage());
        }
        return new java.sql.Date(date.getTime());
    }
    
    public static java.sql.Timestamp getDatetimeFromYYYY_MM_DDHHMMSS_SQL(String d) {
        Date date = null;
        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sd.parse(d);
        } catch (ParseException ex) {
            logger.error(ex.getMessage());
        }
        return new java.sql.Timestamp(date.getTime());
    }
        
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
            logger.error(e.getMessage());
        }
        return dateBefore;
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
            logger.error(e.getMessage());
        }
        return dateAfter;
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
            logger.error(e.getMessage());
        }
        return dateAfter;

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
    
    public static String timeToString(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
        return f.format(d);
    }
    
     public static String timeToStringSSS(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss:sss");
        return f.format(d);
    }

    public static void screenPrint(Object obj) {
        if (BaseConstant.ConsolePrint) {
            System.out.println(datetimeToString(new Date()) + "  " + obj);
        }
    }

    public static void screenPrintForEx(String str) {
        screenPrint(str);
    }
    
    public static void screenPrintForEx(Object obj) {
        screenPrint(obj);
    }

    public static void screenPrintForEx(Vector v) {
        if (v == null) {
            return;
        }
        int len = v.size();
        for (int i = 0; i < len; i++) {
            screenPrint(v.get(i).toString());
        }

    }
    
    public static void screenPrintForEx(List list) {
        if (list == null) {
            return;
        }
        int len = list.size();
        for (int i = 0; i < len; i++) {
            screenPrint(list.get(i).toString());
        }

    }
    
    public static void screenPrintForEx(Set v) {
        if (v == null) {
            return;
        }
        for (Object obj: v) {
            screenPrint(obj.toString());
        }

    }

    public static void screenPrintForEx(Hashtable ht) {
        if (ht == null) {
            return;
        }
        Set set = ht.keySet();
        Iterator it = set.iterator();
        String key;
        while (it.hasNext()) {
            key = (String) it.next();
            if (key.toLowerCase().indexOf("password") != -1) {
                screenPrint(" 键值= " + key + " 配置值=" + "******");
            } else {
                screenPrint(" 键值= " + key + " 配置值=" + ht.get(key));
            }
        }
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

    public static String dateOnlyToDayString(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHH");
        return f.format(d);
    }

    public static Date getDateBeforeDate(Date date, int days) {
        Date dateRet = null;
        if (date == null) {
            return null;
        }
        long betInt = date.getTime() - days * 24 * 60 * 60 * 1000L;
        dateRet = new Date(betInt);

        return dateRet;
    }

    public static Date getDateAfterDate(Date date, int days) {
        Date dateRet = null;
        if (date == null) {
            return null;
        }
        long betInt = date.getTime() + days * 24 * 60 * 60 * 1000L;
        dateRet = new Date(betInt);

        return dateRet;
    }
    //--------------------------------------------------

    public static void main(String[] args) {
        //String datePre = DateHelper.getDateBefore("20100704", 3600000 * 24);
        //System.out.println(datePre);
        Date date = DateHelper.getDateAfterDate(new Date(), 1);
        String dateStr = DateHelper.dateOnlyToDayString(date);
        System.out.println(dateStr);
    }
}
