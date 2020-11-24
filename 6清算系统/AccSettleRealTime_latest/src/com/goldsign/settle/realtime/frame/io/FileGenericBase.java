/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.io;

import com.goldsign.settle.realtime.frame.util.ByteGenUtil;
import com.goldsign.settle.realtime.frame.util.CrcUtil;


/**
 *
 * @author hejj
 */
public class FileGenericBase {

    public static char[] CRLF = {13, 10};
    public static char[] CRLF_3 = {13, 10,13, 10,13, 10};
    public static int T_BCD = 2;
    public static int T_INT = 3;
    public static int T_STR = 1;

    public char[] getBufferCharForLineCrc(char[] bs) throws Exception {
        int[] dataTypeTail = {T_STR, T_STR};
        int[] dataLenTail = {4, 8};
        int len = bs.length;
        String crc = CrcUtil.getCRC32ValueByChar(bs, len, 8);
        Object[] dataTail = {"CRC:", crc};
        char[] cs = this.getLine(dataTail, dataLenTail, dataTypeTail);
        return cs;

    }
    public char[] getBufferCharForLineCrc_3(char[] bs) throws Exception {
        int[] dataTypeTail = {T_STR, T_STR};
        int[] dataLenTail = {4, 8};
        int len = bs.length;
        String crc = CrcUtil.getCRC32ValueByChar(bs, len, 8);
        Object[] dataTail = {"CRC:", crc};
        char[] cs = this.getLine_3(dataTail, dataLenTail, dataTypeTail);
        return cs;

    }

    private int getBufferLen(int[] dataLen) {
        int n = 0;
        for (int i : dataLen) {
            n += i;
        }
        n = n + 2;
        return n;
    }
    private int getBufferLen_3(int[] dataLen) {
        int n = 0;
        for (int i : dataLen) {
            n += i;
        }
        n = n + 6;
        return n;
    }

    protected char[] getLine(Object[] datas, int[] data_len, int[] data_type) throws Exception {
        int blen = this.getBufferLen(data_len);
        char[] bs = new char[blen];
        Object obj;
        int dLen;
        int dType;
        char[] cs;
        int offset = 0;
        for (int i = 0; i < datas.length; i++) {
           // System.out.println("i=" + i);
            obj = datas[i];
            dLen = data_len[i];
            dType = data_type[i];
            cs = this.getData(obj, dLen, dType);
            this.addCharArrayToBuffer(bs, cs, offset);
            offset += dLen;
        }
        this.addCharArrayToBuffer(bs, CRLF, offset);
        return bs;
    }
    protected char[] getLine_3(Object[] datas, int[] data_len, int[] data_type) throws Exception {
        int blen = this.getBufferLen_3(data_len);
        char[] bs = new char[blen];
        Object obj;
        int dLen;
        int dType;
        char[] cs;
        int offset = 0;
        for (int i = 0; i < datas.length; i++) {
           // System.out.println("i=" + i);
            obj = datas[i];
            dLen = data_len[i];
            dType = data_type[i];
            cs = this.getData(obj, dLen, dType);
            this.addCharArrayToBuffer(bs, cs, offset);
            offset += dLen;
        }
        this.addCharArrayToBuffer(bs, CRLF_3, offset);
        return bs;
    }

    protected char[] getData(Object obj, int len, int type) throws Exception {
        char[] cs = null;
        if (type == T_STR) {
            cs = ByteGenUtil.stringToChar((String) obj, len);
            return cs;
        }
        if (type == T_BCD) {
            cs = ByteGenUtil.stringBcdToChar((String) obj, len);
            return cs;
        }
        if (type == T_INT) {
            cs = ByteGenUtil.intToChar(((Integer) obj).intValue(), len);
            return cs;
        }
        return cs;
    }

    private void addCharArrayToBuffer(char[] buf, char[] ca, int offset) {
        for (char c : ca) {
            buf[offset] = c;
            offset++;
        }
    }
}
