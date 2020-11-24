package com.goldsign.escommu.message;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.exception.CommuException;
import com.goldsign.escommu.vo.BridgeBetweenConnectionAndMessage;

public abstract class MessageBase {

    protected String messageFrom;
    protected String messageSequ;
    protected String thisClassName = this.getClass().getName();
    protected byte[] data;
    protected long hdlStartTime; //处理的起始时间
    protected long hdlEndTime;//处理的结束时间
    protected String threadNum;//处理线程号
    protected String level = AppConstant.LOG_LEVEL_INFO;//日志级别
    protected String remark = "";//备注
    protected BridgeBetweenConnectionAndMessage bridge;

    public void init(String ip, String sequ, byte[] b, String threadNum, BridgeBetweenConnectionAndMessage bridge, 
            String messageId) throws Exception {
        messageFrom = ip;
        messageSequ = sequ;
        data = b;
        thisClassName = thisClassName.substring(thisClassName.lastIndexOf(".") + 1, thisClassName.length());
        this.threadNum = threadNum;
        if (bridge != null) {
            this.bridge = bridge;
            this.bridge.setMessageProcessor(this);
            this.bridge.setMsgType(messageId);
        }

    }

    public abstract void run() throws Exception;

    //when use in.read() get an int(byte) for example 152(0x98),run this method to get "98";
    protected String byte1ToBcd2(int i) {
        return (new Integer(i / 16)).toString() + (new Integer(i % 16)).toString();
    }

    //when transform one byte for example (byte)0x98,run this method to get "98";
    protected String byte1ToBcd2(byte b) {
        int i = 0;
        if (b < 0) {
            i = 256 + b;
        } else {
            i = b;
        }
        return (new Integer(i / 16)).toString() + (new Integer(i % 16)).toString();
    }

    protected String getBcdString(int offset, int length) throws CommuException {
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

    protected char byteToChar(byte b) {
        return (char) b;
    }

    protected String getCharString(int offset, int length) throws CommuException {
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

    protected String getGbkString(int offset, int length) throws CommuException {

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

    //when transform one byte for example (byte)0x98,run this method to get 104;
    protected int byteToInt(byte b) {
        int i = 0;
        if (b < 0) {
            i = 256 + b;
        } else {
            i = b;
        }
        return i;
    }

    protected int getInt(int offset) {
        return byteToInt(data[offset]);
    }

    //when transform one short(two bytes) for example 0x12(low),0x34(high),run this method to get 13330
    protected int getShort(int offset) {
        int low = byteToInt(data[offset]);
        int high = byteToInt(data[offset + 1]);
        return high * 16 * 16 + low;
    }

    //when transform one long(two shorts) for example 0x12,0x34,0x56,0x78
    protected int getLong(int offset) {
        int low = getShort(offset);
        int high = getShort(offset + 2);
        return high * 16 * 16 * 16 * 16 + low;
    }

}
