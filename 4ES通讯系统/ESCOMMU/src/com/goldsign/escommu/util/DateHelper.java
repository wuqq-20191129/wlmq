/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.util;

import com.goldsign.escommu.env.AppConstant;
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
            SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
            date = sd.parse(d);
        } catch (ParseException ex) {
            logger.error(ex);
        }
        return date;
    }
    
    public static Date getDatetimeFromYYYYMM_DDHHMMSS(String d) {
        Date date = null;
        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd HHmmss");
            date = sd.parse(d);
        } catch (ParseException ex) {
            logger.error(ex);
        }
        return date;
    }
    
    public static Date getDatetimeFromYYYY_MM_DD_HH_MM_SS(String d) {
        Date date = null;
        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sd.parse(d);
        } catch (ParseException ex) {
            logger.error(ex);
        }
        return date;
    }
        
    public static String datetimeToYYYYMMDD(java.util.Date d) {
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

    }

    public static String dateToYYYYMMDDHHMMSS(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
        return f.format(d);
    }

    public static String dateToYYYYMMDD(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        return f.format(d);
    }

    public static String dateToYYYY_MM_DD_HH_MM_SS_BL(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
        return f.format(d);
    }

    public static String datetimeToYYYY_MM_DD_HH_MM_SS_ML(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.format(d);
    }

    public static void screenPrint(String str) {
        if (AppConstant.ConsolePrint) {
            System.out.println(datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date()) + "  " + str);
        }
    }

    public static String currentToYYYYMMDDHHMMSS() {
        Date d = new Date(System.currentTimeMillis());
        return dateToYYYYMMDDHHMMSS(d);
    }

    public static java.sql.Timestamp utilDateToSqlTimestamp(java.util.Date utilDate) {
        return new java.sql.Timestamp(utilDate.getTime());
    }

    public static java.sql.Timestamp getCurrentSqlTimestamp() {
        Date utilDate = new Date(System.currentTimeMillis());
        return new java.sql.Timestamp(utilDate.getTime());
    }

    public static String dateToYYYYMMDDHH(java.util.Date d) {
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
    
    
    public static void screenPrintForEx(String str) {
        screenPrint(str);
    }

    public static void screenPrintForEx(Vector v) {
        if (v == null) {
            return;
        }
        for (int i = 0; i < v.size(); i++) {
            screenPrint(v.get(i).toString());
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

    //--------------------------------------------------

}
