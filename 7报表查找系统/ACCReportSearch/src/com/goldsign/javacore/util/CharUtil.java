/*
 * Amendment History:
 *
 * Date          By             Description
 * ----------    -----------    -------------------------------------------
 * 2004-06-01    Rong Weitao    Create the class
 * 2005-04-13    何建军         增加方法
*/

package com.goldsign.javacore.util;

import java.io.UnsupportedEncodingException;

public class CharUtil{

	public static String GbkToIso(String str){
		if(str==null) return str;
		try{
			return new String(str.getBytes("GB2312"),"8859_1");
		}
		catch(UnsupportedEncodingException e){
			return str;
		}
	}
        public static String ChineseToIso(String str){
           return GbkToIso(IsoToUTF8(str));
        }

	public static String IsoToGbk(String str){
		if(str==null) return str;
		try{
			return new String(str.getBytes("8859_1"),"GB2312");
		}
		catch(UnsupportedEncodingException e){
			return str;
		}
	}
        public static String UTF8ToIso(String str){
                if(str==null) return str;
                try{
                        return new String(str.getBytes("UTF-8"),"8859_1");
                }
                catch(UnsupportedEncodingException e){
                        return str;
                }
        }
        public static String UTF8ToGbk(String str){
                if(str==null) return str;
                try{
                        return new String(str.getBytes("UTF-8"),"GBK");
                }
                catch(UnsupportedEncodingException e){
                        return str;
                }
        }


        public static String IsoToUTF8(String str){
                if(str==null) return str;
                try{
                        return new String(str.getBytes("8859_1"),"UTF-8");
                }
                catch(UnsupportedEncodingException e){
                        return str;
                }
        }


}
