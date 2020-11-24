package com.goldsign.csfrm.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.zip.CRC32;

/**
 * 
 * @author lenovo
 * 
 * 字符工具类
 * 
 */
public class CharUtil {

    /**
     * 字节数组转GBK解码
     * 
     * @param bytes
     * @return 
     */
    public static String bytesToGbkStr(byte[] bytes){
        try {
            return new String(bytes,"GBK");
        } catch (UnsupportedEncodingException ex) {
            return new String(bytes);
        }
    }
    /**
     * 字节数组转UTF-8解码
     * 
     * @param bytes
     * @return 
     */
    public static String bytesToUtfStr(byte[] bytes){
        try {
            return new String(bytes,"UTF-8");
        } catch (UnsupportedEncodingException ex) {
            return new String(bytes);
        }
    }
    
    /**
     * GBK字符串转字节数组编码
     * 
     * @param str
     * @return 
     */
    public static byte[] gbkStrToBytes(String str){
        try {
            return str.getBytes("GBK");
        } catch (UnsupportedEncodingException ex) {
            return str.getBytes();
        }
    }
    
    /**
     * GBK转ISO
     * 
     * @param str
     * @return 
     */
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

    /**
     * UTF-8字符串转字节数组编码
     * 
     * @param str
     * @return 
     */
    public static byte[] utfStrToBytes(String str){
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            return str.getBytes();
        }
    }
    
    /**
     * ISO转GKB
     * 
     * @param str
     * @return 
     */
    public static String IsoToGbk(String str) {
        if (str == null) {
            return str;
        }
        try {
            //   System.out.println("IsoToGbk:"+new String(str.getBytes("8859_1"),"GB18030"));
            return new String(str.getBytes("8859_1"), "GBK");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    /**
     * 字节数组转为十六进制字符串
     * 
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
     * 字节转为8位二进制字符串
     * 
     * @param b
     * @return 
     */
    public static String byteToBin8bStr(byte b) {

        String str = Integer.toBinaryString(b);
        if (str.length() < 8) {
            str = StringUtil.addZeroBefore(str, 8);
        }

        return str;
    }
    
    /**
     * 字节转为反8位二进制字符串
     * 
     * @param b
     * @return 
     */
    public static String byteToBinReverse8bStr(byte b) {

        String str = Integer.toBinaryString(b);
        if (str.length() < 8) {
            str = StringUtil.addZeroBefore(str, 8);
        }

        return StringUtil.reverse(str);
    }

    /**
     * 取文件CRC码
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
     * 整数(1字节)转为十六进制字符串
     * 
     * @param iRes
     * @return 
     */
    public static String intToHex(int iRes) {
        String strRet = "";
        strRet = toHex(iRes);
        return strRet;
    }

    private static String toHex(int aDec) {
        String hexChars = "0123456789ABCDEF";
        if (aDec > 255) {
            return null;
        }
        int i = aDec % 16;
        int j = (aDec - i) / 16;
        String result = "";
        result += hexChars.charAt(j);
        result += hexChars.charAt(i);
        return result;
    }
    
    /**
     * BCD 转 字节数组
     *
     * @param str
     * @return
     */
    //run this method example : transform "123456789" to {0x01,0x23,0x45,0x67,0x89}
    public static byte[] bcdStringToByteArray(String str) {
        try {
            int len = str.length();
            if (str.length() == 0) {
                return null;
            }
            if (len % 2 == 1) {
                str = "0" + str;
                len = len + 1;
            }
            byte[] tmp = new byte[len/2];
            int p = 0;
            for (int i = 0; i < len; i = i + 2) {
                int value = Integer.parseInt(str.substring(i, i + 2));
                byte[] b = {(byte) (value / 10 * 16 + value % 10)};
                System.arraycopy(b, 0, tmp, p, 1);
                p = p + 1;
            }

            byte[] bb = new byte[p];
            System.arraycopy(tmp, 0, bb, 0, p);
            return bb;
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 取指定位置长度字节数组
     * 
     * @param b
     * @param start
     * @param len
     * @return 
     */
    public static byte[] getByteArr(byte[] b, int start, int len){
    
        return Arrays.copyOfRange(b, start, start+len);
    }
  
    /**
     * 取指定位置长度的字节数组，转BYTE字符串
     * 
     * @param data
     * @param start
     * @param len
     * @return 
     */
    public static String getByteString(byte[] data, int start, int len){
    
        String str = "";

        for(int i=start; i<start+len; i++){
            int d = 0;
            if (data[i] < 0) {
                d = 256 + data[i];
            } else {
                d = data[i];
            }
            str += d;
        }
        
        return str;
    }
    
    /**
     * 取指定位置长度的字节数组，转CHAR字符串
     * 
     * @param data
     * @param start
     * @param len
     * @return 
     */
    public static String getByteCharString(byte[] data, int start, int len){
    
        String str = "";
        for(int i=start; i<start+len; i++){
            str += (char)data[i];
        }
        
        return str;
    }
    
     /**
     * 字符序列转换为16进制字符串，低字节序
     * 
     * @param src
     * @return 
     */
    public static String bytesToHexStringNoSpaceLow(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = src.length-1; i >= 0; i--) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }
    
    /**
     * 字符序列转换为16进制字符串
     * 
     * @param src
     * @return 
     */
    public static String bytesToHexStringNoSpace(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }
    
    /**
     * 字符序列转换为16进制字符串
     * 
     * @param src
     * @return 
     */
    public static String bytesToAsciiStringNoSpace(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = new Character((char) v).toString();
//			if (hv.length() < 2) {
//				stringBuilder.append(0);
//			}
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }
    
    /**
     * byte数组转成long,低位在前
     * 
     * @param b
     * @return 
     */
    public static long byteToLong(byte[] b) {
        long s = 0;
        long s0 = b[0] & 0xff;// 最低位
        long s1 = b[1] & 0xff;
        long s2 = b[2] & 0xff;
        long s3 = b[3] & 0xff;// 最低位

        // s0不变
        s1 <<= 8;
        s2 <<= 16;
        s3 <<= 24;
        s = s0 | s1 | s2 | s3;
        return s;
    }
    
    /**
     * byte arr to long ,低位在前
     *
     */
    public static String byteToLongStr(byte[] b){
        
        long i = CharUtil.byteToLong(CharUtil.getByteArr(b, 0, 4));
        return StringUtil.addZeroBefore(i+"", 4);
    }
    
    /**
     * 从输入数组中取指定的byte数组转为长整型 ,低位在前
     * 
     * @param b
     * @param start
     * @param len
     * @return 
     */
    public static String byteToLongStr(byte[] b, int start, int len){
        
        return byteToLongStr(getByteArr(b, start, len));
    }
    
    /**
     * byte数组转成int,低位在前
     *
     * @param b
     * @return
     */
    public static int byteToInt(byte[] b) {
        int s = 0;
        int s0 = b[0] & 0xff;// 最低位
        int s1 = b[1] & 0xff;

        // s0不变
        s1 <<= 8;
        s = s0 | s1;
        return s;
    }
    
    /**
     * byte arr to int ,低位在前
     * 
     */
    public static String byteToIntStr(byte[] b){
        
        String str = "";
        int i = CharUtil.byteToInt(CharUtil.getByteArr(b, 0, 2));
        if(i<10){
            str = "0" + i;
        }else{
            str = "" + i;
        }
        return str;
    }
    
    /**
     * 从输入数组中取指定的byte数组转为整型 ,低位在前
     * 
     * @param b
     * @param start
     * @param len
     * @return 
     */
    public static String byteToIntStr(byte[] b, int start, int len){
        
        return byteToIntStr(getByteArr(b, start, len));
    }
    
    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    
    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
    
    /**
     * 整型转2字节数组
     * 
     * @param a
     * @return 
     */
    public static byte[] to2Byte(int a) {
        return new byte[]{(byte) (0x000000ff & (a)),
                    (byte) (0x000000ff & (a >>> 8))};
    }
    
    /**
     * 整型转4字节数组
     *
     * @param a
     * @return
     */
    public static byte[] to4Byte(int i) {
        
        byte[] result = new byte[4];
        result[0] = (byte) (i & 0xFF);
        result[1] = (byte) ((i >>> 8) & 0xFF);
        result[2] = (byte) ((i >>> 16) & 0xFF);
        result[3] = (byte) ((i >>> 24) & 0xFF);
        
        return result;
    }
    
    /**
     * 打印输出字节数组(正反)位
     * 
     * @param bs 
     */
    public static void printByteBitStr(byte[] bs) {
        int i = 0;
        for (byte b : bs) {
            String bStr = Integer.toBinaryString(b);
            bStr = StringUtil.addZeroBefore(bStr, 8);
            String rStr = StringUtil.reverse(bStr);
            System.out.println("[" + i + "]: " + bStr + " , " + rStr);
            i++;
        }
    }
    
    /**
     * 打印一个字节的位字符
     * 
     * @param b 
     */
    public static void printByteBitStr(byte b) {
        String bStr = Integer.toBinaryString(b);
        bStr = StringUtil.addZeroBefore(bStr, 8);
        String rStr = StringUtil.reverse(bStr);
        System.out.println(bStr + " , " + rStr);
    }
    
    /**
     * 取字符串的字节值
     *
     */
    public static byte strToByte(String str) {
        byte ret = 0;
        try {
            //ret = Byte.parseByte(text);
            ret = (byte)Short.parseShort(str);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return ret;
    }
 
    /**
     * 取字符串的字节值
     *
     */
    public static int strToInt(String str) {
        int ret = 0;
        try {
            ret = Integer.parseInt(str);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return ret;
    }
    
    /**
     * 取指定长度的BYTE数组
     * 
     * @param str
     * @param len
     * @return 
     */
    public static byte[] strToLenByteArr(String str, int len){
        
        byte[] bytes = new byte[len];
        if(null != str){
            byte[] src = str.getBytes();
            System.arraycopy(src, 0, bytes, 0, src.length);
        }
        return bytes;
    }
 
    /**
     * 取指定长度的BYTE数组
     *
     * @param str
     * @param len
     * @return
     */
    public static byte[] hexStrToLenByteArr(String str, int len){
        byte[] bytes = new byte[len];
        if(null != str){
            byte[] src = hexStringToBytes(str);
            System.arraycopy(src, 0, bytes, 0, src.length);
        }
        return bytes;
    }

    /**
     * 取指定长度的BYTE数组
     *
     * @param str
     * @param len
     * @return
     */
    public static byte[] bcdStrToLenByteArr(String str, int len) {
        
        byte[] bytes = new byte[len];
        if(null != str&&!str.equals("")){
            byte[] src = bcdStringToByteArray(str);
            System.arraycopy(src, 0, bytes, 0, src.length);
        }
        return bytes;
    }
}
