/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.test.file.profit;

import com.goldsign.settle.realtime.frame.util.CrcUtil;
import com.goldsign.settle.realtime.test.file.trx.File52App;
import com.goldsign.settle.realtime.test.file.base.FileBase;
import com.goldsign.settle.realtime.test.file.FileTrx;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hejj
 */
public class File05TicketPut extends FileBase{
     public static void main(String[] arg) {
        File05TicketPut ft = new File05TicketPut();
        //String path="D:/en_work/dir_prog_work/settle/模拟数据/程序生成";
        String fileName="PRO0105."+ft.getCurrentDate();
        try {
            char[] buffer = ft.getBufferChar();
            ft.writeFile(buffer, path, fileName);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FileTrx.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private String getChineseStr(String str) throws UnsupportedEncodingException{
        String strNew = new String(str.getBytes(),"gbk");
        return strNew;
    }
    
    public char[] getBufferChar() throws UnsupportedEncodingException {
        char[] bs = null;
        /******************文件头***********************/
        int[] data_type_head = {File52App.T_STR, File52App.T_BCD, File52App.T_BCD,  File52App.T_INT};
        int[] data_len_head = {4, 7, 7, 4};
        Object[] data_head = {"0105", "20130810100000", "20130810100059", 1};
        /*********************************************/
        /******************文件尾***********************/
        int[] data_type_tail = {File52App.T_STR, File52App.T_STR};
        int[] data_len_tail = {4, 8};
        Object[] data_tail = {"CRC:", "037067c5"};
        /*********************************************/
        /*

         */
        /*******************硬币箱数据**************/
        int[] data_type = {File52App.T_BCD, File52App.T_STR, File52App.T_INT, File52App.T_STR, File52App.T_STR,
                           File52App.T_BCD, File52App.T_INT, File52App.T_BCD, File52App.T_INT};
        int[] data_len = {1, 9, 1, 6, 16,
                          7, 2, 2, 2 
                          
                          };
        Object[] datas = {"05", "020102001", 1, "000001", "1", 
                          "20130810050000", 100, "0100", 200};
        /****************************************************************************************/
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
    
    
    public char[] getBufferCharForLine() throws UnsupportedEncodingException {

        /*******************硬币箱数据**************/
        int[] data_type = {File52App.T_BCD, File52App.T_STR, File52App.T_INT, File52App.T_STR, File52App.T_INT,
                           File52App.T_BCD, File52App.T_INT, File52App.T_BCD, File52App.T_INT};
        int[] data_len = {1, 9, 1, 6, 4,
                          7, 2, 2, 2 
                          
                          };
        Object[] datas = {"05", "020102001", 1, "000001", 1, 
                          this.getStartTime(), 100, "0100", 200};
        /****************************************************************************************/
       
        //文件记录
        char[] cs = this.getLine(datas, data_len, data_type);
        
        return cs;
    }
    
    
}
