/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.test.file.base;

import com.goldsign.settle.realtime.frame.util.ByteGenUtil;
import com.goldsign.settle.realtime.frame.util.DateHelper;
import com.goldsign.settle.realtime.test.file.FileTrx;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hejj
 */
public class FileBase {
     public static String path="D:/en_work/dir_prog_work/settleRealtime/0programGen";

    //public static char[] CRLF = {13, 10};
     public static char[] CRLF = {13, 10,13,10,13,10};
    public static int T_BCD = 2;
    public static int T_INT = 3;
    public static int T_STR = 1;
    private static String hmsStart = "020000";
    private static String hmsEnd = "235959";
    /**
     * 数据
     */
    protected String D_STATION = "0201";
    protected String D_DEV_TYPE_BOM = "03";
    protected String D_CARD_SJT = "0100";
    protected String D_CARD_SVT = "0200";
    protected String D_TEL = "87136411";
    protected String D_FAX = "87136411";
    protected String D_ID_TYPE = "01";
    protected String D_ID = "43010119780101115519";
    protected String D_NAME = "张力";
    protected String D_ADDRESS = "湖南长沙铭鸿分公司";

    protected void addCharArrayToBuffer(char[] buf, char[] ca, int offset) {
        for (char c : ca) {
            buf[offset] = c;
            offset++;
        }
    }

    protected void close(OutputStream os) {
        try {
            if (os != null) {
                os.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(FileTrx.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected int getBufferLen(int[] dataLen) {
        int n = 0;
        for (int i : dataLen) {
            n += i;
        }
        n = n + 6;
        return n;
    }
    protected int getBufferLenForOct(int[] dataLen) {
        int n = 0;
        for (int i : dataLen) {
            n += i;
        }
        n = n + 2+dataLen.length-1;
        return n;
    }

    public void writeFile(char[] bs, String path, String fileName) {
        String fileNameFull = path + "/" + fileName;
        File f = new File(fileNameFull);
        FileOutputStream os = null;
        DataOutputStream dos = null;
        try {
            os = new FileOutputStream(f);
            dos = new DataOutputStream(os);
            for (char c : bs) {
                dos.write((int) c);
            }
            dos.flush();
        } catch (Exception ex) {
            Logger.getLogger(FileTrx.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.close(dos);
        }
    }

    protected char[] getData(Object obj, int len, int type) throws UnsupportedEncodingException {
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

    protected char[] getLine(Object[] datas, int[] data_len, int[] data_type) throws UnsupportedEncodingException {
        int blen = this.getBufferLen(data_len);
        char[] bs = new char[blen];
        Object obj;
        int dLen;
        int dType;
        char[] cs;
        int offset = 0;
        for (int i = 0; i < datas.length; i++) {
            System.out.println("i=" + i);
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
    protected char[] getLineForOct(Object[] datas, int[] data_len, int[] data_type) throws UnsupportedEncodingException {
        int blen = this.getBufferLenForOct(data_len);
        char[] bs = new char[blen];
        Object obj;
        int dLen;
        int dType;
        char[] cs;
        int offset = 0;
        char[] delim={','};
        for (int i = 0; i < datas.length; i++) {
            System.out.println("i=" + i);
            obj = datas[i];
            dLen = data_len[i];
            dType = data_type[i];
            cs = this.getData(obj, dLen, dType);
            this.addCharArrayToBuffer(bs, cs, offset);
            offset += dLen;
            if(i !=datas.length-1)
            {
                 this.addCharArrayToBuffer(bs, delim, offset);
                 offset += 1;
            }
        }
        this.addCharArrayToBuffer(bs, CRLF, offset);
        return bs;
    }

    protected String getStartTime() {
        return DateHelper.datetimeToStringOnlyDateF(new Date()) +FileBase.hmsStart;
    }
    protected String getEndTime() {
        return DateHelper.datetimeToStringOnlyDateF(new Date()) +FileBase.hmsEnd;
    }
    protected String getCurrentDate() {
        return DateHelper.datetimeToStringOnlyDateF(new Date());
    }
     protected String getDealDatetime() {
        return DateHelper.dateToString(new Date());
    }
}
