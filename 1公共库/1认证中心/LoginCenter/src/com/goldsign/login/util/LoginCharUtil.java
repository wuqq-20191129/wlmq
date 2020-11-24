/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.login.util;

import java.util.StringTokenizer;

/**
 *
 * @author oywl
 */
public class LoginCharUtil {
//    public String encode(String passwd) {
//        if (passwd == null || passwd.length() == 0) {
//            return "";
//        }
//        String ePasswd = "";
//        char[] passwds = passwd.toCharArray();
//        char[] ePassWds = new char[passwds.length];
//        byte b;
//
//        for (int i = 0; i < passwds.length; i++) {
//            b = (byte) passwds[i];
//            //System.out.println(b);
//            b ^= 127;
//            ePassWds[i] = (char) b;
//            //			System.out.println("b="+b);
//        }
//        ePasswd = new String(ePassWds);
//        //System.out.println("ePasswd="+ePasswd);
//        return ePasswd;
//    }
    static public String convertDateToDBFormat(String date) {
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
