/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.test.file.profit;

import com.goldsign.settle.realtime.frame.util.CrcUtil;
import com.goldsign.settle.realtime.test.file.FileTrx;
import com.goldsign.settle.realtime.test.file.base.FileBase;
import com.goldsign.settle.realtime.test.file.trx.File52App;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hejj
 */
public class File11NoteWastData extends FileBase{
    public static void main(String[] arg) {
        File11NoteWastData ft = new File11NoteWastData();
       // String path="D:/en_work/dir_prog_work/settle/0programGen";
        String fileName="PRO0111."+ft.getCurrentDate();
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
        Object[] data_head = {"0111", "20160623100000", "20160623100059", 1};
        /*********************************************/
        /******************文件尾***********************/
        int[] data_type_tail = {File52App.T_STR, File52App.T_STR};
        int[] data_len_tail = {4, 8};
        Object[] data_tail = {"CRC:", "037067c5"};
        /*********************************************/
        /*

         */
        /*******************纸币回收箱数据**************/
        int[] data_type = {File52App.T_BCD, File52App.T_STR, File52App.T_STR, File52App.T_STR, File52App.T_BCD,
                           File52App.T_INT, File52App.T_INT,
                           File52App.T_INT,File52App.T_INT, File52App.T_INT,
                           File52App.T_INT, File52App.T_INT, File52App.T_INT,
                           File52App.T_INT, File52App.T_INT, File52App.T_INT,
                           File52App.T_INT, File52App.T_INT, File52App.T_INT
                          };
        int[] data_len = {1, 9, 16,  6,7,
                          2, 1,
                          1,2,2,
                          1,2,2,
                          1,2,2,
                          1,2,2
                          
                          };
        Object[] datas = {"11", "020102001",  "9999999999999999", "000000",this.getDealDatetime(), 
                           99,4,
                           1,100, 200,
                           2,100,300,
                           3,100,400,
                           4,100,500};
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
        int[] data_type = {File52App.T_BCD, File52App.T_STR, File52App.T_STR, File52App.T_INT, File52App.T_BCD,
                           File52App.T_INT, File52App.T_INT
                          };
        int[] data_len = {1, 9, 6,  2,7,
                          2, 2
                          
                          };
        Object[] datas = {"08", "020102001",  "000001", 1,this.getStartTime(), 
                           100, 200};
        /****************************************************************************************/
        
        //文件记录
        char[] cs = this.getLine(datas, data_len, data_type);
       
        return cs;
    }
    
}
