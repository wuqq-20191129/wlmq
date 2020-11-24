/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.frame.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author hejj
 */
public class FrameUtil {

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
            return new String(str.getBytes("GBK"), "8859_1");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    public static String ChineseToIso(String str) {
        return GbkToIso(IsoToUTF8(str));
    }

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

    public static String IsoToGbk(String str) {
        if (str == null) {
            return str;
        }
        try {
            return new String(str.getBytes("8859_1"), "GB18030");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    public String getControlDefaultValues(HttpServletRequest request) {
        String result = "";
        String controlNames = request.getParameter("ControlNames");
        if (controlNames == null || controlNames.length() == 0) {
            return "";
        }
        StringTokenizer st = new StringTokenizer(controlNames, "#");
        String name = "";
        String value = "";
        while (st.hasMoreTokens()) {
            name = st.nextToken();
            value = request.getParameter(name);
            if (value != null) {
                if (this.isMimiType(request)) {
                    value = this.ChineseToIsoForMimi(value);
                } else {
                    value = this.ChineseToIso(value);
                }
            }
            result += name + "#" + value + ";";
        }
        if (result.length() != 0) {
            result = result.substring(0, result.length());
        }
        // System.out.println("getControlDefaultValues="+result);
        return result;
    }

    public String getControlDefaultValues(HttpServletRequest request, String controlNamesParm) {
        String result = "";
        String controlNames = request.getParameter(controlNamesParm);
        if (controlNames == null || controlNames.length() == 0) {
            return "";
        }
        StringTokenizer st = new StringTokenizer(controlNames, "#");
        String name = "";
        String value = "";
        while (st.hasMoreTokens()) {
            name = st.nextToken();
            value = request.getParameter(name);
            result += name + "#" + value + ";";
        }
        if (result.length() != 0) {
            result = result.substring(0, result.length());
        }
        return result;
    }

    public HashMap getConfigPropertiesByAppPath(HttpServletRequest req, String configFile) throws Exception {
        String appRoot = req.getSession().getServletContext().getRealPath("/");
        String fileName = appRoot + "/properties/" + configFile;
        //      InputStream in =this.getClass().getResourceAsStream(configFile);
        FileInputStream fis = new FileInputStream(fileName);
        String line = null;
        int index = -1;
        String key = null;
        String value = null;
        HashMap properties = new HashMap();
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(fis, "GBK");
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }
                index = line.indexOf("=");
                if (index == -1) {
                    continue;
                }
                key = line.substring(0, index);
                value = line.substring(index + 1);
                if (value.startsWith("${ROOT}")) {
                    value = req.getSession().getServletContext().getRealPath("/") + value.substring(7);
                }
                value = value.trim();
                properties.put(key, value);

            }

        } catch (Exception e) {
            //     e.printStackTrace();
            throw e;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (isr != null);
                isr.close();
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return properties;
    }

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

    public String encode(String passwd) {
        if (passwd == null || passwd.length() == 0) {
            return "";
        }
        String ePasswd = "";
        char[] passwds = passwd.toCharArray();
        char[] ePassWds = new char[passwds.length];
        byte b;

        for (int i = 0; i < passwds.length; i++) {

            b = (byte) passwds[i];
            //System.out.println(b);

            b ^= 127;
            ePassWds[i] = (char) b;
            //			System.out.println("b="+b);


        }
        ePasswd = new String(ePassWds);
        //System.out.println("ePasswd="+ePasswd);
        return ePasswd;

    }

    public void setAutoKeyRequestParameter(HttpServletRequest request, String value) {
        request.setAttribute("_updatePKControlNames", value);

    }

    static public String convertDateToViewFormat(String date) {
        if (date == null || date.trim().length() == 0 || date.trim().length() != 14) {
            return date;
        }
        return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);


    }

    public static void getIDs(String strIDs, Vector IDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String ID = null;
        while (st.hasMoreTokens()) {
            ID = st.nextToken();
            IDs.add(ID);
        }
    }
    
    //判断字符串不为空时返回真
    public static boolean stringIsNotEmpty(String str){
        if(str == null || "".equals(str.trim())){
            return false;
        }
        return true;
    }
    
}
