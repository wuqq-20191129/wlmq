/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 *
 * @author hejj
 */
public class DateUtil {

    public static String convertDateToDBFormat(String date) {
        if (date == null || date.trim().length() != 10) {
            return date;
        }
        StringTokenizer st = new StringTokenizer(date, "-");
        String result = "";
        if (!st.hasMoreTokens()) {
            return date;
        }
        while (st.hasMoreTokens()) {
            result += st.nextToken();
        }
        result += "235959";
        return result;

    }
    //added by zhongziqi
    public static String convertTimestamptoString (Timestamp date)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = df.format(date);
        return str;
    }
    
    public static String dateToString(Date date,String format) {
        if(date==null){
            return "";
}
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(date);
    }

    public static Date stringToDate(String dateStr,String format) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.parse(dateStr);
    }
}
