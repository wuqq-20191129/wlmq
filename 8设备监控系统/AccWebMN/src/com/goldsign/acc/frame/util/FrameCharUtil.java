/*
��
*/

package com.goldsign.acc.frame.util;

import java.io.UnsupportedEncodingException;

public class FrameCharUtil{

	public static String GbkToIso(String str){
		if(str==null) return str;
		try{
			return new String(str.getBytes("GB18030"),"8859_1");
		}
		catch(UnsupportedEncodingException e){
			return str;
		}
	}
	public static String GbkToIsoByGB2312(String str){
		if(str==null) return str;
		try{
			return new String(str.getBytes("GB2312"),"8859_1");
		}
		catch(UnsupportedEncodingException e){
			return str;
		}
	}
	public static String GbkToIsoByGBK(String str){
		if(str==null) return str;
		try{
			return new String(str.getBytes("GBK"),"8859_1");
		}
		catch(UnsupportedEncodingException e){
			return str;
		}
	}
	public static String GbkToIsoForMimi(String str){
		return FrameCharUtil.GbkToIso(FrameCharUtil.GBKToUTF8(str));
	}
        public static String ChineseToIso(String str){
           return GbkToIso(IsoToUTF8(str));
        }
        public static String ChineseToIsoForMimi(String str) {
        	return GbkToIsoByGBK(GBKToUTF8ByGBK(str));
         }
        

	public static String IsoToGbk(String str){
		if(str==null) return str;
		try{
			return new String(str.getBytes("8859_1"),"GB18030");
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
        public static String UTF8ToGBK(String str){
                if(str==null) return str;
                try{
                        return new String(str.getBytes("UTF-8"),"GB18030");
                }
                catch(UnsupportedEncodingException e){
                        return str;
                }
        }
        public static String GBKToUTF8(String str){
            if(str==null) return str;
            try{
                    return new String(str.getBytes("GB18030"),"UTF-8");
            }
            catch(UnsupportedEncodingException e){
                    return str;
            }
    }
        public static String GBKToUTF8ByGBK(String str){
            if(str==null) return str;
            try{
                    return new String(str.getBytes("GBK"),"UTF-8");
            }
            catch(UnsupportedEncodingException e){
                    return str;
            }
    }
        public static String GBKToUTF8ByGB2312(String str){
            if(str==null) return str;
            try{
                    return new String(str.getBytes("GB2312"),"UTF-8");
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
        public static void main(String[] args){
        	String str = "������";
        	byte[] bs;
        	byte[] bsgbk;
        	byte[] bsutf8;
        	FrameCharUtil u=new FrameCharUtil();
			try {
				bs = str.getBytes("8859_1");
				bsgbk = str.getBytes("GBK");
				bsutf8 = str.getBytes("UTF-8");
				u.print(bs);
				
				u.print(bsgbk);
				u.print(bsutf8);
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	
        }
        public void print(byte[] bs){
        	for(int i=0;i<bs.length;i++){
        		System.out.print(bs[i]+" ");
        	}
        }
}
