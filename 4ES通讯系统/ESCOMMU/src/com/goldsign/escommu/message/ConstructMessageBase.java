package com.goldsign.escommu.message;

import com.goldsign.escommu.exception.MessageException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import org.apache.log4j.Logger;

public class ConstructMessageBase {

    private final int maxLength = 65536;
    private byte[] message;
    private int pointer;
    private static Logger logger = Logger.getLogger(ConstructMessageBase.class.getName());

    protected ConstructMessageBase() {
    }

    /**
     * len is the bytes length after transform
     */
    protected void AddStringToMessage(String str, int len) {
        byte[] b = str.getBytes();
        AddByteArrayToMessage(b, len);
    }
    
    /**
     * gbk
     * 
     * @param str
     * @param len 
     */
    protected void AddGbkStringToMessage(String str, int len) {
        byte[] b = null;
        try {
            b = str.getBytes("GBK");
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex);
        }
        AddByteArrayToMessage(b, len);
    }
    
    /**
     * gbk
     * 
     * @param str
     * @param len 
     */
    protected void AddUtf8StringToMessage(String str, int len) {
        byte[] b = null;
        try {
            b = str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex);
        }
        AddByteArrayToMessage(b, len);
    }

    /**
     * len is the bytes length after transform
     */
    protected void AddBcdToMessage(String str, int len) throws MessageException {
        byte[] b = bcdStringToByteArray(str);
        if (b == null) {
            throw new MessageException("BCD transform error!");
        }
        AddByteArrayToMessage(b, len);
    }

    /**
     * len is the bytes length after transform
     */
    protected void AddIntToMessage(int i, int len) throws MessageException {
        if (i > 256) {
            throw new MessageException("Message repeat count > 256! ");
        }
        byte[] b = {(byte) i};
        AddByteArrayToMessage(b, 1);
    }

    protected void AddLongToMessage(long l) throws MessageException {
        byte[] b = longToByteArray((int) l);
        AddByteArrayToMessage(b, 4);
    }

    public byte[] longToByteArray(long number) {
        int temp = (int) number;
        byte[] b = new byte[4];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(temp & 0xff).byteValue();//将最低位保存在最低位 
            temp = temp >> 8;// 向右移8位    
        }
        return b;
    }

    protected void AddShortToMessage(short s) throws MessageException {
        byte[] b = shortToByteArray((short) s);
        AddByteArrayToMessage(b, 2);
    }

    public byte[] shortToByteArray(short number) {
        int temp = number;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(temp & 0xff).byteValue();//将最低位保存在最低位
            temp = temp >> 8;// 向右移8位    
        }
        return b;
    }    

    private void AddByteArrayToMessage(byte[] b, int len) {
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

    //delete unused byte[] in the latter
    protected byte[] trimMessage() {
        byte[] result = new byte[pointer];
        System.arraycopy(message, 0, result, 0, pointer);
        return result;
    }

    protected void initMessage() {
        this.message = new byte[maxLength];
        this.pointer = 0;
    }

    //when transform 2 decimal number for example 98,run this method to get 0x98
    private byte bcd2ToByte1(int i) {
        return (byte) (i / 10 * 16 + i % 10);
    }

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
            logger.error(e);
            return null;
        }
    }

    /*
     * for test public static void main(String[] args){ ConstructMessageBase mc
     * = new ConstructMessageBase(); byte[] b =
     * mc.bcdStringToByteArray("0123456789"); for(int i=0;i<b.length;i++){
     * DateHelper.screenPrintForEx("b:"+b[i]); } }
     */
}
