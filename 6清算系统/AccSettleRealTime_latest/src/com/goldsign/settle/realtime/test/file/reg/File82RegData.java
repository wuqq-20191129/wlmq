/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.test.file.reg;

import com.goldsign.settle.realtime.frame.util.CrcUtil;
import com.goldsign.settle.realtime.test.file.FileTrx;
import com.goldsign.settle.realtime.test.file.base.FileBase;
import static com.goldsign.settle.realtime.test.file.base.FileBase.path;
import com.goldsign.settle.realtime.test.file.trx.File52App;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hejj
 */
public class File82RegData extends FileBase {

    public static void main(String[] arg) {
        File82RegData ft = new File82RegData();
        //  String path="D:/en_work/dir_prog_work/settle/模拟数据/程序生成";
        String fileName = "REG0182." + ft.getCurrentDate();;
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
         * ****************文件头**********************
         */
        int[] data_type_head = {File52App.T_STR, File52App.T_BCD, File52App.T_BCD, File52App.T_INT};
        int[] data_len_head = {4, 7, 7, 4};
        Object[] data_head = {"0182", this.getStartTime(), this.getEndTime(), 1};
        /**
         * ******************************************
         */
        /**
         * ****************文件尾**********************
         */
        int[] data_type_tail = {File52App.T_STR, File52App.T_STR};
        int[] data_len_tail = {4, 8};
        Object[] data_tail = {"CRC:", "037067c5"};
        /**
         * ******************************************
         */
        /*

         */
        /**
         * *****************数据*************
         */
        int[] data_type = {File52App.T_STR, File52App.T_BCD, File52App.T_STR, File52App.T_STR, File52App.T_STR,
            File52App.T_BCD, File52App.T_INT, File52App.T_INT, File52App.T_INT, File52App.T_INT,
            File52App.T_INT, File52App.T_INT, File52App.T_INT, File52App.T_INT, File52App.T_INT,
            File52App.T_INT, File52App.T_INT, File52App.T_INT, File52App.T_INT, File52App.T_INT,
            File52App.T_INT, File52App.T_INT, File52App.T_INT, File52App.T_INT, File52App.T_INT,
            File52App.T_INT, File52App.T_INT, File52App.T_INT, File52App.T_INT, File52App.T_INT,
            File52App.T_INT, File52App.T_INT

        };
        int[] data_len = {2, 7, 4, 2, 3,
            7, 4, 4, 4, 4,
            4, 4, 4, 4, 4,
            4, 4, 4, 4, 4,
            4, 4, 4, 4, 4,
            4, 4, 4, 4, 4,
            4, 4

        };
        Object[] datas = {"82", this.getStartTime(), "0201", "04", "001",
            this.getStartTime(), 0, 1, 200, 1,
            100, 2, 200, 0, 0,
            11, 12, 13, 14, 15,
            1, 200, 1, 200, 1,
            100, 2, 200, 1, 0,
            0, 0
        };
        /**
         * *************************************************************************************
         */
        int blen1 = this.getBufferLen(data_len_head); //长度+回车换行符
        int blen2 = this.getBufferLen(data_len);
        int blen3 = this.getBufferLen(data_len_tail);
        int offset = 0;
        bs = new char[blen1 + blen2 + blen3]; //总字符数
        //文件头
        char[] cs = this.getLine(data_head, data_len_head, data_type_head);
        this.addCharArrayToBuffer(bs, cs, offset);
        offset += blen1;
        //文件记录
        cs = this.getLine(datas, data_len, data_type);
        this.addCharArrayToBuffer(bs, cs, offset);
        offset += blen2;

        //获取CRC
        String crc = CrcUtil.getCRC32ValueByChar(bs, offset, 8);
        data_tail[1] = crc;
        //文件尾
        cs = this.getLine(data_tail, data_len_tail, data_type_tail);
        this.addCharArrayToBuffer(bs, cs, offset);
        offset += blen3;
        return bs;
    }

}
