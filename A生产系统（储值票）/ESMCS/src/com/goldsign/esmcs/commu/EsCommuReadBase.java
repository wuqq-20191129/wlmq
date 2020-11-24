/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.commu;

import com.goldsign.esmcs.exception.CommuException;


/**
 * 通讯读数据辅助类
 * 
 * @author lenovo
 */
public class EsCommuReadBase {

    private byte[] data;//数据
    
    public EsCommuReadBase(){}
    
    public EsCommuReadBase(byte[] data){
        this.data = data;
    }
    
    /**
     * 设置数据
     * 
     * @param data 
     */
    public void setData(byte[] data){
        this.data = data;
    }
    
    /**
     * 取数据
     * 
     * @return 
     */
    public byte[] getData(){
        return this.data;
    }
    
    /**
     * BYTE 转 BCD码
     * 
     * @param i
     * @return 
     */
    public String byte1ToBcd2(int i) {
        return (new Integer(i / 16)).toString() + (new Integer(i % 16)).toString();
    }

    /**
     * BYTE 转 BCD码
     * 
     * @param b
     * @return 
     */
    //when transform one byte for example (byte)0x98,run this method to get "98";
    public String byte1ToBcd2(byte b) {
        int i = 0;
        if (b < 0) {
            i = 256 + b;
        } else {
            i = b;
        }
        return (new Integer(i / 16)).toString() + (new Integer(i % 16)).toString();
    }

    /**
     * 取BCD字符串
     * 
     * @param offset
     * @param length
     * @return
     * @throws CommuException 
     */
    public String getBcdString(int offset, int length) throws CommuException {
        StringBuffer sb = new StringBuffer();
        try {
            for (int i = 0; i < length; i++) {
                sb.append(byte1ToBcd2(data[offset + i]));
            }
        } catch (Exception e) {
            throw new CommuException(" " + e);
        }
        return sb.toString();
    }

    /**
     * BYTE 转 字符
     * 
     * @param b
     * @return 
     */
    public char byteToChar(byte b) {
        return (char) b;
    }

    /**
     * 取字符串
     * 
     * @param offset
     * @param length
     * @return
     * @throws CommuException 
     */
    public String getCharString(int offset, int length) throws CommuException {
        StringBuffer sb = new StringBuffer();
        try {
            for (int i = 0; i < length; i++) {
                sb.append(byteToChar(data[offset + i]));
            }
        } catch (Exception e) {
            throw new CommuException(" " + e);
        }
        return sb.toString();
    }

    /**
     * 取GBK字符串
     * 
     * @param offset
     * @param length
     * @return
     * @throws CommuException 
     */
    public String getGbkString(int offset, int length) throws CommuException {

        String str = null;
        try {
            byte[] tb = new byte[length];
            for (int i = 0; i < length; i++) {
                tb[i] = data[i + offset];
            }
            str = new String(tb, "gbk");
        } catch (Exception e) {
            throw new CommuException(" " + e);
        }

        return str;
    }
    
      /**
     * 取GBK字符串
     * 
     * @param offset
     * @param length
     * @return
     * @throws CommuException 
     */
    public String getUTFSring(int offset, int length) throws CommuException {

        String str = null;
        try {
            byte[] tb = new byte[length];
            for (int i = 0; i < length; i++) {
                tb[i] = data[i + offset];
            }
            str = new String(tb, "utf-8");
        } catch (Exception e) {
            throw new CommuException(" " + e);
        }

        return str;
    }

    /**
     * BYTE 转 INT
     * 
     * @param b
     * @return 
     */
    //when transform one byte for example (byte)0x98,run this method to get 104;
    public int byteToInt(byte b) {
        int i = 0;
        if (b < 0) {
            i = 256 + b;
        } else {
            i = b;
        }
        return i;
    }

    /**
     * 取整数（单字节）
     * 
     * @param offset
     * @return 
     */
    public int getInt(int offset) {
        return byteToInt(data[offset]);
    }

    /**
     * 取短整数（双字节）
     * 
     * @param offset
     * @return 
     */
    //when transform one short(two bytes) for example 0x12(low),0x34(high),run this method to get 13330
    public int getShort(int offset) {
        int low = byteToInt(data[offset]);
        int high = byteToInt(data[offset + 1]);
        return high * 16 * 16 + low;
    }

    /**
     * 取长整数（四字节）
     * 
     * @param offset
     * @return 
     */
    //when transform one long(two shorts) for example 0x12,0x34,0x56,0x78
    public int getLong(int offset) {
        int low = getShort(offset);
        int high = getShort(offset + 2);
        return high * 16 * 16 * 16 * 16 + low;
    }

}
