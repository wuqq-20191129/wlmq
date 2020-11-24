/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.util;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author hejj
 */
public class CharUtil {

    public static boolean isMimiType(HttpServletRequest request) {
        String contentType = request.getContentType();
        if (contentType == null || contentType.length() == 0) {
            return false;
        }
        if (contentType.indexOf("multipart/form-data") != -1) {
            return true;
        }
        return false;
    }

    public static String ChineseToIsoForMimi(String str) {

        return GbkToIsoByGBK(GBKToUTF8ByGBK(str));

    }

    public static String GBKToUTF8ByGBK(String str) {
        if (str == null) {
            return str;
        }
        try {
            return new String(str.getBytes("GBK"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    public static String GbkToIsoByGBK(String str) {
        if (str == null) {
            return str;
        }
        try {
            //return new String(str.getBytes("GB2312"),"8859_1");
            return new String(str.getBytes("GB18030"), "8859_1");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    public static String IsoToUTF8GbkToIso(String str) {
        return GbkToIso(IsoToUTF8(str));
    }

    /**
     * 把iso转为utf8,使用在从xsl取中文数据到java中
     *
     * @param str
     * @return
     */
    public static String IsoToUTF8(String str) {
        if (str == null) {
            return str;
        }
        try {
            return new String(str.getBytes("8859_1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    public static String GbkToIso(String str) {
        if (str == null) {
            return str;
        }
        try {
            //return new String(str.getBytes("GB2312"),"8859_1");
            return new String(str.getBytes("GB18030"), "8859_1");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    public static int getDBLenth(String str) throws Exception {
        if (str == null) {
            return 0;
        }
        String a = new String(str.getBytes("utf-8"), "iso-8859-1");
        return a.length();
    }

}
