/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.test.file.oct;

import com.goldsign.settle.realtime.test.file.base.FileBase;
import com.goldsign.settle.realtime.test.file.FileTrx;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hejj
 */
public class FileOctAUS extends FileBase {

    public static void main(String[] arg) {
        FileOctAUS ft = new FileOctAUS();
        String path = "D:/en_work/dir_prog_work/settle/模拟数据/程序生成";
        String fileName = "AUS_BUS_1001_" + ft.getCurrentDate() + "." + "TXT";
        try {
            char[] buffer = ft.getBufferChar();
            ft.writeFile(buffer, path, fileName);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FileTrx.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getChineseStr(String str) throws UnsupportedEncodingException {
        String strNew = new String(str.getBytes(), "gbk");
        return strNew;
    }

    public char[] getBufferChar() throws UnsupportedEncodingException {
        char[] bs = null;

        /**
         * *****************采集状态信息*************
         */
        int[] data_type = {FileBase.T_STR, FileBase.T_STR};
        int[] data_len = {25, 2};
        Object[][] datas = {{"AUF_MTR_1000_20140418.TXT", "00"},
                            {"BLA_MTR_1000_20140418.TXT", "00"},
                            {"TRX_MTR_1000_20140418.TXT", "00"}
                           };

        /**
         * *************************************************************************************
         */
        int blen2 = this.getBufferLenForOct(data_len);
        int blen3 = blen2 * datas.length;
        int offset = 0;
        bs = new char[blen3]; //总字符数
        
        for (int i = 0; i < datas.length; i++) {
            char[] cs = this.getLineForOct(datas[i], data_len, data_type);
            this.addCharArrayToBuffer(bs, cs, offset);
            offset += blen2;
            
        }

        return bs;
    }
}
