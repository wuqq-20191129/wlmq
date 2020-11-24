/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.sammcs.util;

import com.goldsign.sammcs.env.AppConstant;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Administrator
 */
public class Util {
    
    /**
    * 格式化字符串时间
    * 
    * @param str 时间字符串
    * @return Date
    */
   public static Date fmtStrToDay(String str){

           Date date = null;
           SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstant.YYYY_MM_DD, Locale.US);
           SimpleDateFormat dateFormat2 = new SimpleDateFormat(AppConstant.YYYYMMDD, Locale.US);
           SimpleDateFormat dateFormat3 = new SimpleDateFormat(AppConstant.YYYYMMDD_X, Locale.US);
           try{
                if(str == null){
                        //date = null;
                }else if(str.length() == 10){
                        try{
                        date = dateFormat.parse(str);
                        }catch (ParseException e) {
                                date = dateFormat3.parse(str);
                        }
                }else if(str.length() == 8){
                        date = dateFormat2.parse(str);
                }
           }catch(ParseException e){
                   
           }
           return date;
   }
    
}
