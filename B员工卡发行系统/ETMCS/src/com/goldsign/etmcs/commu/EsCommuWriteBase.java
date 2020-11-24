/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.etmcs.commu;

import com.goldsign.etmcs.exception.MessageException;
import java.io.UnsupportedEncodingException;

/**
 * 通讯写辅助类
 * 
 * @author lenovo
 */
public class EsCommuWriteBase {
    
    private final int maxLength = 65536;
    private byte[] message;//数据
    private int pointer;//当前指针
    
    public EsCommuWriteBase(){
        initMessage();
    }
    
    /**
     * 添加长度字符串数据
     * len is the bytes length after transform
     */
    public void addStringToMessage(String str, int len) {
        byte[] b = str.getBytes();
        addByteArrayToMessage(b, len);
    }
    
    /**
     * 添加长度字符串数据GBK
     * 
     * @param str
     * @param len 
     */
    public void addGbkStringToMessage(String str, int len) {
        byte[] b = null;
        try {
            b = str.getBytes("gbk");
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        addByteArrayToMessage(b, len);
    }

    /**
     * 添加长度BCD数据
     * len is the bytes length after transform
     */
    public void addBcdToMessage(String str, int len) throws MessageException {
        byte[] b = bcdStringToByteArray(str);
        if (b == null) {
            throw new MessageException("BCD transform error!");
        }
        addByteArrayToMessage(b, len);
    }

    /**
     * 添加长度整数（单字节）数据
     * len is the bytes length after transform
     */
    //public void addIntToMessage(int i, int len) throws MessageException {
    public void addIntToMessage(int i) throws MessageException {
        if (i > 256) {
            throw new MessageException("Message repeat count > 256! ");
        }
        byte[] b = {(byte) i};
        addByteArrayToMessage(b, 1);
    }

    /**
     * 添加长整数（四字节）数据
     * 
     * @param l
     * @throws MessageException 
     */
    public void addLongToMessage(long l) throws MessageException {
        byte[] b = longToByteArray((int) l);
        addByteArrayToMessage(b, 4);
    }

    /**
     * 长整数转字节
     * 将最低位保存在最低位
     * 
     * @param number
     * @return 
     */
    public byte[] longToByteArray(long number) {
        int temp = (int) number;
        byte[] b = new byte[4];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(temp & 0xff).byteValue();//将最低位保存在最低位 
            temp = temp >> 8;// 向右移8位    
        }
        return b;
    }

    /**
     * 添加短整数（两字节）数据
     * 
     * @param s
     * @throws MessageException 
     */
    public void addShortToMessage(short s) throws MessageException {
        byte[] b = shortToByteArray((short) s);
        addByteArrayToMessage(b, 2);
    }

    /**
     * 短整数转字节
     * 将最低位保存在最低位
     * 
     * @param number
     * @return 
     */
    public byte[] shortToByteArray(short number) {
        int temp = number;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(temp & 0xff).byteValue();//将最低位保存在最低位
            temp = temp >> 8;// 向右移8位    
        }
        return b;
    }    

    /**
     * 添加长度字节
     * 
     * @param b
     * @param len 
     */
    private void addByteArrayToMessage(byte[] b, int len) {
        if (len > b.length) {
            System.arraycopy(b, 0, message, pointer, b.length);
            pointer = pointer + b.length;
            //char space = ' ';
            byte[] space = {0x20};
            for (int i = (len - b.length); i > 0; i--) {
                System.arraycopy(space, 0, message, pointer, 1);
                pointer = pointer + 1;
            }
        } else {
            System.arraycopy(b, 0, message, pointer, len);
            pointer = pointer + len;
        }
    }

    /**
     * 取真实字节数据
     * 
     * @return 
     */
    //delete unused byte[] in the latter
    public byte[] trimMessage() {
        byte[] result = new byte[pointer];
        System.arraycopy(message, 0, result, 0, pointer);
        return result;
    }
    
    /**
     * 取真实字节数据
     *
     * @return
     */
    public byte[] getMessage(){
        return trimMessage();
    }

    /**
     * 初始化数据
     */
    public void initMessage() {
        this.message = new byte[maxLength];
        this.pointer = 0;
    }

    /**
     * BCD 转 字节
     * 
     * @param i
     * @return 
     */
    //when transform 2 decimal number for example 98,run this method to get 0x98
    private byte bcd2ToByte1(int i) {
        return (byte) (i / 10 * 16 + i % 10);
    }

    /**
     * BCD 转 字节数组
     * 
     * @param str
     * @return 
     */
    //run this method example : transform "123456789" to {0x01,0x23,0x45,0x67,0x89}
    private byte[] bcdStringToByteArray(String str) {
        try {
            int len = str.length();
            if (str.length() == 0) {
                return null;
            }
            if (len % 2 == 1) {
                str = "0" + str;
                len = len + 1;
            }
            if (len / 2 > maxLength) {
                throw new MessageException("Transform string to BCD length > " + maxLength);
            }
            byte[] tmp = new byte[maxLength];
            int p = 0;
            for (int i = 0; i < len; i = i + 2) {
                int value = Integer.parseInt(str.substring(i, i + 2));
                byte[] b = {bcd2ToByte1(value)};
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
}
