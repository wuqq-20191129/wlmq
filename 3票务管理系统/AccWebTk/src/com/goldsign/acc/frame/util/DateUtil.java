/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.util;

import com.goldsign.acc.frame.constant.InOutConstant;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

/**
 *
 * @author hejj
 */
public class DateUtil {

    public static java.util.Date strToUtilDate(String strDateOnly) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        return f.parse(strDateOnly);

    }

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
    
    public static String getCurrentYearMonthDate(int flag) {
        GregorianCalendar current = new GregorianCalendar();
        int year = current.get(GregorianCalendar.YEAR);
        int month = current.get(GregorianCalendar.MONTH) + 1;
        int day = current.get(GregorianCalendar.DAY_OF_MONTH);
        int week = current.get(GregorianCalendar.WEEK_OF_MONTH);
        String sYear = Integer.toString(year);
        String sMonth = Integer.toString(month);
        String sDay = Integer.toString(day);
        String sDate = sYear + "-" + sMonth + "-" + sDay;
        String sWeek = "第" + week + "周";

//    System.out.println("sDate="+sDate+" sWeek="+week);

        if (sMonth.length() < 2) {
            sMonth = "0" + sMonth;
        }
        if (sDay.length() < 2) {
            sDay = "0" + sDay;
        }
        sDate = sYear + "-" + sMonth + "-" + sDay;

        if (flag == InOutConstant.FLAG_YEAR) {
            return sYear;
        }
        if (flag == InOutConstant.FLAG_MONTH) {
            return sMonth;
        }
        if (flag == InOutConstant.FLAG_DATE) {
            return sDate;
        }
        if (flag == InOutConstant.FLAG_WEEK) {
            return sWeek;
        }



        return sDate;

    }

}
