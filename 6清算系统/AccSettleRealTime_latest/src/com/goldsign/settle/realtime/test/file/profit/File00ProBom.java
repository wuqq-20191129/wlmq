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
public class File00ProBom extends FileBase{
    public static void main(String[] arg) {
        File00ProBom ft = new File00ProBom();
        //String path="D:/en_work/dir_prog_work/settle/模拟数据/程序生成";
        String fileName="PRO0100."+ft.getCurrentDate();
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
        Object[] data_head = {"0100", "20131106020000", "20131106235959",  1};
        /*********************************************/
        /******************文件尾***********************/
        int[] data_type_tail = {File52App.T_STR, File52App.T_STR};
        int[] data_len_tail = {4, 8};
        Object[] data_tail = {"CRC:", "037067c5"};
        /*********************************************/
        /*******************BOM班次**************/
        int[] data_type = {
                           File52App.T_BCD, File52App.T_STR, File52App.T_STR, File52App.T_BCD, File52App.T_BCD, //0
                           File52App.T_BCD, File52App.T_INT, File52App.T_BCD, File52App.T_BCD, File52App.T_INT,
                           
                           File52App.T_INT, File52App.T_INT, File52App.T_INT, File52App.T_BCD,File52App.T_BCD,//10
                           File52App.T_INT, File52App.T_INT, File52App.T_INT, File52App.T_INT,File52App.T_BCD,
                           
                           File52App.T_BCD,File52App.T_BCD, File52App.T_INT, File52App.T_INT, File52App.T_INT,//20                           
                           File52App.T_INT,File52App.T_INT,File52App.T_BCD, File52App.T_INT, File52App.T_INT, 
                           
                           File52App.T_BCD,File52App.T_INT,File52App.T_INT, File52App.T_INT, File52App.T_BCD, //30
                           File52App.T_INT, File52App.T_INT, File52App.T_BCD, File52App.T_INT,File52App.T_INT,
                           
                           File52App.T_INT,File52App.T_STR,File52App.T_STR, File52App.T_INT, File52App.T_BCD, //40
                           File52App.T_INT,File52App.T_INT,File52App.T_BCD, File52App.T_INT, File52App.T_INT,
                           
                           File52App.T_INT,File52App.T_INT,File52App.T_INT, File52App.T_INT, File52App.T_BCD, //50
                           File52App.T_INT,File52App.T_INT,File52App.T_BCD, File52App.T_INT, File52App.T_INT, 
                           
                           File52App.T_INT,File52App.T_INT,File52App.T_BCD, File52App.T_INT, File52App.T_INT, //60
                           File52App.T_INT
                           
                           
                          };
        int[] data_len = {1, 9, 6, 1, 7, 
                          7, 1,2, 1,  2,  
                          
                          4,4, 1, 2, 1,//10
                          2,4, 4, 1, 2,
                          
                          1,1, 2, 4, 4,//20
                          4,1, 1, 2, 4,
                          
                          1,4, 4, 1, 2,//30
                          2,1, 2, 2, 4,
                          
                          4,1, 1, 2, 1,//40
                          4,1, 2, 2, 4,
                          
                          4,4, 4, 1, 2,//50
                          2,1, 2, 2, 4,
                          
                          4,1, 2, 2, 4,
                          4
                          };
        Object[] datas = {
                          "00", "020103001", "000001", "01", "20130101130000", 
                          "20130101130059", 1, "0100","01", 10,
                          
                           2000, 2000, 1, "0200","01", //10
                          1,2000,2000,1,"0100",
                          
                          "03","01",1,200,200,//20
                          200,1,"03",1,200,
                          
                          "23",0,200,1,"0203",//30
                          1,1,"0100",1,400,
                          
                          0,"0","0",0,"00",//40
                          400,1,"0200",1,2000,
                          
                          2000,0,4000,1,"0200",//50
                          1,1,"0200",1,10000,
                          
                          10000,1,"0200",1,13200,//60
                          13200
                          
                         };
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
         /*********************************************/
        /*******************BOM班次**************/
        int[] data_type = {
                           File52App.T_BCD, File52App.T_STR, File52App.T_STR, File52App.T_BCD, File52App.T_BCD, //0
                           File52App.T_BCD, File52App.T_INT, File52App.T_BCD, File52App.T_INT, File52App.T_INT,
                           
                           File52App.T_INT, File52App.T_INT, File52App.T_BCD, File52App.T_INT,File52App.T_INT,//10
                           File52App.T_INT, File52App.T_INT, File52App.T_BCD, File52App.T_BCD,File52App.T_BCD,
                           
                           File52App.T_INT, File52App.T_INT, File52App.T_INT, File52App.T_INT,File52App.T_INT,//20                           
                           File52App.T_BCD, File52App.T_INT, File52App.T_INT, File52App.T_BCD,File52App.T_INT,
                           
                           File52App.T_INT, File52App.T_INT, File52App.T_BCD, File52App.T_INT,File52App.T_INT,//30
                           File52App.T_BCD, File52App.T_INT, File52App.T_INT, File52App.T_INT,File52App.T_STR,
                           
                           File52App.T_STR, File52App.T_INT, File52App.T_BCD, File52App.T_INT,File52App.T_INT,//40
                           File52App.T_BCD, File52App.T_INT, File52App.T_INT, File52App.T_INT,File52App.T_INT,
                           
                           File52App.T_INT, File52App.T_INT, File52App.T_BCD, File52App.T_INT,File52App.T_INT,//50
                           File52App.T_BCD, File52App.T_INT, File52App.T_INT, File52App.T_INT,File52App.T_INT,
                           
                           File52App.T_BCD, File52App.T_INT, File52App.T_INT, File52App.T_INT//60
                           
                           
                          };
        int[] data_len = {1, 9, 6, 1, 7, 
                          7, 1,2, 2, 4, 
                          
                          4, 1, 2, 2,4,//10
                          4,1,2,1,1,
                          
                          2,4,4,4,1,//20
                          1,2,4,1,4,
                          
                          4,1,2,2,1,//30
                          2,2,4,4,1,
                          
                          1,2,1,4,1,//40
                          2,2,4,4,4,
                          
                          4,1,2,2,1,//50
                          2,2,4,4,1,
                          
                          2,2,4,4
                          
                          };
        Object[] datas = {
                          "00", "020103001", "000001", "01",this.getStartTime(), 
                          this.getEndTime(), 1, "0100", 10, 2000, 
                          
                          2000, 1, "0200", 1,2000,//10
                          2000,1,"0100","03","01",
                          
                          1,200,200,200,1,//20
                          "03",1,200,"23",0,
                          
                          200,1,"0203",1,1,//30
                          "0100",1,400,0,"0",
                          
                          "0",0,"00",400,1,//40
                          "0200",1,2000,2000,0,
                          
                          4000,1,"0200",1,1,//50
                          "0200",1,10000,10000,1,
                          
                          "0200",1,13200,13200//60
                
                          
                         };
        /****************************************************************************************/

        //文件记录
        char[] cs  = this.getLine(datas, data_len, data_type);

        return cs;
    }
    
    
}
