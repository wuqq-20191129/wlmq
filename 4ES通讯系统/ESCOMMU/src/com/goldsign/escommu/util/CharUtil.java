package com.goldsign.escommu.util;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.zip.CRC32;

public class CharUtil {

    public static String GbkToIso(String str) {
        if (str == null) {
            return str;
        }
        try {
            return new String(str.getBytes("GBK"), "8859_1");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    public static String IsoToGbk(String str) {
        if (str == null) {
            return str;
        }
        try {
            //   System.out.println("IsoToGbk:"+new String(str.getBytes("8859_1"),"GBK"));
            return new String(str.getBytes("8859_1"), "GBK");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    /**
     * For example, byte[] {0x01,0x23,0x45,0x67} will be changed to String
     * "01,23,45,67"
     */
    public static String byteToHex(byte[] b) {
        String result = "";
        String tmp = "";

        for (int n = 0; n < b.length; n++) {
            tmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (tmp.length() == 1) {
                result = result + "0" + tmp;
            } else {
                result = result + tmp;
            }
            if (n < b.length - 1) {
                result = result + ",";
            }
        }
        return result.toUpperCase();
    }

    /**
     * 取得CRC32码
     * 
     * @param b
     * @return 
     */
    public static long getCRC32Value(byte[] b) {
        CRC32 crc = new CRC32();
        crc.update(b);

        return crc.getValue();
    }
    
    /**
     * 取得本地IP
     * 
     * @return 
     */
    public static String getLocalIp() {
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }

        return ip;
    }
    
    /**
     * 除去两边空格，为NULL时，返回空
     * 
     * @param str
     * @return 
     */
    public static String trimStr(String str){
        if(str==null)
            return "";
        return str.trim();
    }
}
