/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.fm.common;



import java.io.UnsupportedEncodingException;
import java.util.zip.CRC32;


public class CharUtil{

	public static String GbkToIso(String str){
		if(str==null) return str;
		try{
			return new String(str.getBytes("GB18030"),"8859_1");
		}
		catch(UnsupportedEncodingException e){
			return str;
		}
	}

	public static String IsoToGbk(String str){
		if(str==null) return str;
		try{
               //   System.out.println("IsoToGbk:"+new String(str.getBytes("8859_1"),"GB18030"));
			return new String(str.getBytes("8859_1"),"GB18030");
		}
		catch(UnsupportedEncodingException e){
			return str;
		}
	}

    /** For example, byte[] {0x01,0x23,0x45,0x67} will be changed to String "01,23,45,67" */
    public static String byteToHex(byte[] b){
        String result="";
        String tmp="";

        for(int n=0;n<b.length;n++){
            tmp=(java.lang.Integer.toHexString(b[n] & 0XFF));
            if(tmp.length()==1){
            	result=result+"0"+tmp;
            }
            else{
            	result=result+tmp;
            }
            if (n<b.length-1){
            	result=result+",";
            }
        }
        return result.toUpperCase();
    }

	public static long getCRC32Value(byte[] b){
        CRC32 crc = new CRC32();
		crc.update(b);

		return crc.getValue();
	}

    public String intToHex(int iRes) {
        String strRet="";
        strRet=this.toHex(iRes);
        return strRet;
    }

       public String toHex(int aDec) {
        String hexChars = "0123456789ABCDEF";
        if (aDec > 255)
           return null;
        int i = aDec % 16;
        int j = (aDec - i) / 16;
        String result = "";
        result += hexChars.charAt(j);
        result += hexChars.charAt(i);
        return result;
    }


}
