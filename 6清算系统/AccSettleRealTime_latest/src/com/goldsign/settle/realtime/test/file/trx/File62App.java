/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.test.file.trx;

import com.goldsign.settle.realtime.frame.util.CrcUtil;
import com.goldsign.settle.realtime.test.file.FileTrx;
import com.goldsign.settle.realtime.test.file.base.FileBase;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hejj
 */
public class File62App extends FileBase {

    public static String serialNo = "062";

    public static void main(String[] arg) {
        File62App ft = new File62App();
        //  String path="D:/en_work/dir_prog_work/settle/模拟数据/程序生成";
        String fileName = "TRX0112." + ft.getCurrentDate() + "." + File62App.serialNo;
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
        int[] data_type_head = {File52App.T_STR, File52App.T_BCD, File52App.T_BCD, File52App.T_INT, File52App.T_INT};
        int[] data_len_head = {4, 7, 7, 2, 4};
        Object[] data_head = {"0112", this.getStartTime(), this.getEndTime(), Integer.parseInt(serialNo), 1};
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
         * *****************挂失信息*************
         */
        int[] data_type = {File52App.T_STR, File52App.T_STR, File52App.T_STR, File52App.T_STR, File52App.T_STR,
            File52App.T_STR, File52App.T_STR, File52App.T_STR, File52App.T_STR, File52App.T_BCD,
            File52App.T_STR, File52App.T_STR, File52App.T_STR, File52App.T_STR, File52App.T_BCD,
            File52App.T_BCD, File52App.T_STR, File52App.T_BCD, File52App.T_STR, File52App.T_STR,
            File52App.T_STR};
        int[] data_len = {2, 2, 4, 2, 3,
            30, 1, 1, 18, 4,
            16, 16, 200, 6, 7,
            1, 1, 2, 1, 4,
            20};
        Object[] datas = {"62", "10", "0112", "03", "001",
            "张力", "1", "1", "230103198402015519", "20200101",
            "81891712", "81891712", "湖南长沙铭鸿分公司", "888888", this.getStartTime(),
            "01", "0", "0201", "2", "9999",
            "8300000300000001"};
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
