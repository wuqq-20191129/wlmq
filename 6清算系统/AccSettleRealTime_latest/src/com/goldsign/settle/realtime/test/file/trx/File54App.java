/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.test.file.trx;

import com.goldsign.settle.realtime.frame.util.CrcUtil;
import com.goldsign.settle.realtime.test.file.base.FileBase;
import com.goldsign.settle.realtime.test.file.FileTrx;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hejj
 */
public class File54App extends FileBase {

    public static String serialNo = "054";

    public static void main(String[] arg) {
        File54App ft = new File54App();
        // String path = "D:/en_work/dir_prog_work/settle/模拟数据/程序生成";
        String fileName = "TRX0110." + ft.getCurrentDate() + "." + File54App.serialNo;
        // String fileName = "TRX0110." + "20181016" + "." + File54App.serialNo;
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
        int[] data_type_head = {FileBase.T_STR, FileBase.T_BCD, FileBase.T_BCD, FileBase.T_INT, FileBase.T_INT};
        int[] data_len_head = {4, 7, 7, 2, 4};
        Object[] data_head = {"0110", this.getStartTime(), this.getEndTime(), Integer.parseInt(serialNo), 2};
        /**
         * ******************************************
         */
        /**
         * ****************文件尾**********************
         */
        int[] data_type_tail = {FileBase.T_STR, FileBase.T_STR};
        int[] data_len_tail = {4, 8};
        Object[] data_tail = {"CRC:", "037067c5"};
        /**
         * ******************************************
         */
        /*

         */
        /**
         * *************************************************************************************
         */
        /**
         * *****************钱包交易信息*************
         */
        int[] data_type = {
            T_STR, T_STR, T_STR, T_STR, T_STR,
            T_STR, T_INT, T_BCD, T_BCD, T_STR,
            T_STR, T_INT, T_INT, T_INT, T_INT,
            T_INT, T_STR, T_STR, T_STR, T_BCD,
            T_STR, T_BCD, T_STR, T_BCD, T_STR,
            T_BCD, T_INT, T_STR, T_STR, T_BCD,
            T_BCD, T_STR, T_STR, T_STR, T_STR,
            T_STR, T_STR, T_STR, T_STR, T_STR,
            T_BCD, T_STR, T_STR, T_STR, T_STR,
            T_STR, T_STR, T_STR, T_STR, T_INT,
            T_INT, T_STR, T_INT, T_INT, T_BCD,
            T_INT,T_BCD

        };
        int[] data_len = {
            2, 2, 4, 2, 3,
            16, 4, 7, 2, 20,
            20, 1, 4, 4, 4,
            4, 2, 4, 10, 2,
            16, 7, 6, 1, 16,
            7, 4, 1, 3, 2,
            2, 1, 4, 4, 2,
            12, 1, 11, 2, 4,
            7, 16, 4, 2, 8,
            2, 40, 2, 30, 4,
            4, 6, 4, 4, 7,
            4,7

        };

        Object[] datas = {
            "54", "13", "0101", "04", "001",
            "000000008A654672", 100, this.getDealDatetime(), "0600", "8303801102000001",
            "8A563201", 103, 200, 9800, 100,
            100, "12", "0001", "06A8B090", "0800",
            "8303801102000001", this.getDealDatetime(), "999999", "01", "830000030200002",
            "20160120120000", 200, "1", "000", "0000",
            "0000", "0", "4100", "0003", "09",
            "410000000001", "1", "18812345678", "01", "0001",
            this.getDealDatetime(), "8300", "10", "01", "00000000",
            "01", "hejj", "01", "440108197202115519", 0,
            0,"201912",800,600,this.getDealDatetime(),
            4,this.getDealDatetime()

        };
        Object[] datas_1 = {
            "54", "13", "0101", "04", "001",
            "000000008A654673", 100, this.getDealDatetime(), "0601", "8303801902000001",
            "8A563201", 103, 200, 9800, 100,
            100, "12", "0001", "06A8B090", "0203",
            "830000030200001", this.getDealDatetime(), "999999", "01", "830000030200002",
            this.getDealDatetime(), 200, "1", "000", "0000",
            "0000", "0", "8300", "0003", "09",
            "830000000001", "1", "18812345678", "01", "0001",
            this.getDealDatetime(), "8300", "10", "01", "00000000",
            "01", "王明", "01", "44010819720218888", 0,
            0,"201911",2000,900,this.getDealDatetime(),
            10,this.getDealDatetime()
        };
        /**
         * *****************钱包交易信息*************
         */
        int blen1 = this.getBufferLen(data_len_head); //长度+回车换行符
        int blen2 = this.getBufferLen(data_len);
        int blen3 = this.getBufferLen(data_len_tail);
        int offset = 0;
        bs = new char[blen1 + 2 * blen2 + blen3]; //总字符数
        //文件头
        char[] cs = this.getLine(data_head, data_len_head, data_type_head);
        this.addCharArrayToBuffer(bs, cs, offset);
        offset += blen1;
        //文件记录
        cs = this.getLine(datas, data_len, data_type);
        this.addCharArrayToBuffer(bs, cs, offset);
        offset += blen2;

        cs = this.getLine(datas_1, data_len, data_type);
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
