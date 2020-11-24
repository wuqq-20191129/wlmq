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
public class File00Pro00_08 extends FileBase {

    public static void main(String[] arg) {
        File00Pro00_08 ft = new File00Pro00_08();
       // String path = "D:/en_work/dir_prog_work/settle/模拟数据/程序生成";
        String fileName = "PRO0100." + ft.getCurrentDate();
        try {
            char[] buffer = ft.getBufferChar();
            ft.writeFile(buffer, path, fileName);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FileTrx.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public char[] getBufferChar() throws UnsupportedEncodingException {
        char[] bs = null;
        /**
         * ****************文件头**********************
         */
        int[] data_type_head = {File52App.T_STR, File52App.T_BCD, File52App.T_BCD, File52App.T_INT};
        int[] data_len_head = {4, 7, 7, 4};
        Object[] data_head = {"0100", this.getStartTime(), this.getEndTime(), 9};
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
        /**
         * *****************BOM班次*************
         */
        /*
         int[] data_type = {
         File52CardApp.T_BCD, File52CardApp.T_STR, File52CardApp.T_STR, File52CardApp.T_BCD, File52CardApp.T_BCD, //0
         File52CardApp.T_BCD, File52CardApp.T_INT, File52CardApp.T_BCD, File52CardApp.T_INT, File52CardApp.T_INT,
                           
         File52CardApp.T_INT, File52CardApp.T_INT, File52CardApp.T_BCD, File52CardApp.T_INT,File52CardApp.T_INT,//10
         File52CardApp.T_INT, File52CardApp.T_INT, File52CardApp.T_BCD, File52CardApp.T_BCD,File52CardApp.T_BCD,
                           
         File52CardApp.T_INT, File52CardApp.T_INT, File52CardApp.T_INT, File52CardApp.T_INT,File52CardApp.T_INT,//20                           
         File52CardApp.T_BCD, File52CardApp.T_INT, File52CardApp.T_INT, File52CardApp.T_BCD,File52CardApp.T_INT,
                           
         File52CardApp.T_INT, File52CardApp.T_INT, File52CardApp.T_BCD, File52CardApp.T_INT,File52CardApp.T_INT,//30
         File52CardApp.T_BCD, File52CardApp.T_INT, File52CardApp.T_INT, File52CardApp.T_INT,File52CardApp.T_STR,
                           
         File52CardApp.T_STR, File52CardApp.T_INT, File52CardApp.T_BCD, File52CardApp.T_INT,File52CardApp.T_INT,//40
         File52CardApp.T_BCD, File52CardApp.T_INT, File52CardApp.T_INT, File52CardApp.T_INT,File52CardApp.T_INT,
                           
         File52CardApp.T_INT, File52CardApp.T_INT, File52CardApp.T_BCD, File52CardApp.T_INT,File52CardApp.T_INT,//50
         File52CardApp.T_BCD, File52CardApp.T_INT, File52CardApp.T_INT, File52CardApp.T_INT,File52CardApp.T_INT,
                           
         File52CardApp.T_BCD, File52CardApp.T_INT, File52CardApp.T_INT, File52CardApp.T_INT//60
                           
                           
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
         "00", "020103001", "000001", "01", "20130101130000", 
         "20130101130059", 1, "0100", 10, 2000, 
                          
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
         */
        /**
         * *************************************************************************************
         */
        File00ProBom f00 = new File00ProBom();
        File01CoinData f01 = new File01CoinData();
        File02NoteData f02 = new File02NoteData();
        File03CoinPut f03 = new File03CoinPut();
        File04CoinClear f04 = new File04CoinClear();
        File05TicketPut f05 = new File05TicketPut();
        File06TicketClear f06 = new File06TicketClear();
        File07AgmTicketData f07 = new File07AgmTicketData();
        File08TicketWaste f08 = new File08TicketWaste();



        int blen2 = 0;

        

        char[] ds00 = f00.getBufferCharForLine();
        blen2 += ds00.length;

        char[] ds01 = f01.getBufferCharForLine();
        blen2 += ds01.length;

        char[] ds02 = f02.getBufferCharForLine();
        blen2 += ds02.length;

        char[] ds03 = f03.getBufferCharForLine();
        blen2 += ds03.length;

        char[] ds04 = f04.getBufferCharForLine();
        blen2 += ds04.length;

        char[] ds05 = f05.getBufferCharForLine();
        blen2 += ds05.length;

        char[] ds06 = f06.getBufferCharForLine();
        blen2 += ds06.length;

        char[] ds07 = f07.getBufferCharForLine();
        blen2 += ds07.length;
        
        char[] ds08 = f08.getBufferCharForLine();
        blen2 += ds08.length;





        int blen1 = this.getBufferLen(data_len_head); //长度+回车换行符
      //  blen2 = this.getBufferLen(data_len);
        int blen3 = this.getBufferLen(data_len_tail);
        int offset = 0;
        bs = new char[blen1 + blen2 + blen3]; //总字符数
        //文件头
        char[] cs = this.getLine(data_head, data_len_head, data_type_head);
        this.addCharArrayToBuffer(bs, cs, offset);
        offset += blen1;
        
        
        //文件记录
       // cs = this.getLine(datas, data_len, data_type);
        this.addCharArrayToBuffer(bs, ds00, offset);
        offset += ds00.length;
        
        this.addCharArrayToBuffer(bs, ds01, offset);
        offset += ds01.length;
        
        this.addCharArrayToBuffer(bs, ds02, offset);
        offset += ds02.length;
        
        this.addCharArrayToBuffer(bs, ds03, offset);
        offset += ds03.length;
        
        this.addCharArrayToBuffer(bs, ds04, offset);
        offset += ds04.length;
        
        this.addCharArrayToBuffer(bs, ds05, offset);
        offset += ds05.length;
        
        this.addCharArrayToBuffer(bs, ds06, offset);
        offset += ds06.length;
        
        this.addCharArrayToBuffer(bs, ds07, offset);
        offset += ds07.length;
        
        this.addCharArrayToBuffer(bs, ds08, offset);
        offset += ds08.length;
/*******************************************************/        

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
