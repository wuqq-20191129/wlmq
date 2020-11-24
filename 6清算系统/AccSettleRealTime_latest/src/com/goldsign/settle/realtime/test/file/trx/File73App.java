/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.test.file.trx;

import com.goldsign.settle.realtime.frame.util.CrcUtil;
import com.goldsign.settle.realtime.test.file.FileTrx;
import com.goldsign.settle.realtime.test.file.base.FileBase;
import static com.goldsign.settle.realtime.test.file.base.FileBase.T_BCD;
import static com.goldsign.settle.realtime.test.file.base.FileBase.T_INT;
import static com.goldsign.settle.realtime.test.file.base.FileBase.T_STR;
import static com.goldsign.settle.realtime.test.file.base.FileBase.path;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hejj
 */
public class File73App extends FileBase{
    public static String serialNo = "073";

    public static void main(String[] arg) {
        File73App ft = new File73App();
        // String path="D:/en_work/dir_prog_work/settle/模拟数据/程序生成";
        String fileName = "TRX0110." + ft.getCurrentDate() + "." + File73App.serialNo;
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
        Object[] data_head = {"0110", this.getStartTime(), this.getEndTime(), Integer.parseInt(serialNo), 1};
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
         * *****************进站信息*************
         */
        int[] data_type = {
            T_STR, T_STR, T_STR, T_STR, T_STR,
            T_STR, T_INT, T_BCD, T_STR, T_STR,
            T_BCD, T_INT, T_INT, T_STR, T_STR,
            T_BCD, T_BCD, T_STR, T_INT, T_STR,
            T_BCD, T_STR, T_INT, T_INT, T_BCD,
            T_STR,T_STR
        };
        int[] data_len = {
            2, 2, 4, 2, 3,
            16, 4, 2, 20, 20,
            7, 1, 4, 1, 3,
            2, 2, 1, 4, 1,
            7, 6, 4, 4, 7,
            40,11
        };
        Object[] datas = {
            "73", "11", "0102", "04", "001",
            "000000008A654672", 100, "1100", "8303C11234567010", "8A563201",
            this.getDealDatetime(), 108, 9800, "0", "000",
            "0000", "0000", "0", 100, "1",
            this.getDealDatetime(),"201901",5000,180,this.getDealDatetime(),
            "0000000000000000000000000000000000000001","18899990000"
        };
        /**
         * *****************进站信息*************
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
