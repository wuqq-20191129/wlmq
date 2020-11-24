/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class StringUtil {

    public static int getDBLenth(String str) throws Exception {
        if (str == null) {
            return 0;
        }
        String a = new String(str.getBytes("gb2312"), "iso-8859-1");
        return a.length();
    }

    public static boolean isHaveChinese(String str) throws Exception {
        boolean flag = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c > +256) {
                flag = true;
                break;
            }
        }
        return flag;
    }

public static boolean checkDateFormatAndValite(String strDateTime,String formatStr) {
        //update it according to your requirement.
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {
            Date ndate = format.parse(strDateTime);
            String str = format.format(ndate);
//            System.out.println(ndate);
//            System.out.println(str);
//            System.out.println("strDateTime=" + strDateTime);
            //success
            if (str.equals(strDateTime))
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }
        
    public static void main(String args[]) throws Exception {
//         System.out.println(checkDateFormatAndValite("20159988","yyyyMMdd"));
//        System.out.println(getDBLenth("中国人1"));
//        String s = "13823（12）";
//        String s1 = "13823(12)";
//        System.out.println(isHaveChinese(s));
//        System.out.println(isHaveChinese(s1));
//        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
//        Matcher matcher = pattern.matcher(s);
//        Matcher matcher1 = pattern.matcher(s1);
//        System.out.println(matcher.matches());
//        System.out.println(matcher1.matches());
    }

    public static String getWhereParam(HashMap params, ArrayList pStmtValues) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        String strOb = null;
        String whereParams = "where ";
        Set keys = params.keySet();
        Iterator it = keys.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            Object ob = params.get(key);
            if (ob == null) {
                continue;
            }
            if (ob.getClass().getName().equals("java.lang.String")) {
                strOb = (String) ob;
                if (strOb.trim().length() == 0) {
                    continue;
                }
            }
            pStmtValues.add(ob);
            whereParams += key + "=?" + " and ";
        }
        if (whereParams.length() == 6) {
            return "";
        }
        whereParams = whereParams.substring(0, whereParams.length() - 5);
        return whereParams;
    }
    
       /**
     * 字段用逗号拼接
     * @param fields
     * @return 
     */
    public static String getFieldsJoin(String[] fields,String joinStr){
        String condition ="";
        if (fields!=null) {
            for (Object str : fields) {
                condition +=str.toString()+joinStr;
            }
        }
        if(condition.length()>1){
            condition = condition.substring(0, condition.length()-joinStr.length());
        }
        return condition;
    }
}
