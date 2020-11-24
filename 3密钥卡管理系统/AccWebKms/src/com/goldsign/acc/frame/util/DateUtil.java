/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.util;

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

}
