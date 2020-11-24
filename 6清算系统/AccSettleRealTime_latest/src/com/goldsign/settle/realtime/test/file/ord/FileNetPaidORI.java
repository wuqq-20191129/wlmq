/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.test.file.ord;

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
public class FileNetPaidORI extends FileBase{
    public static String serialNo = "001";
    public static void main(String[] arg) {
        FileNetPaidORI ft = new FileNetPaidORI();
       // String path="D:/en_work/dir_prog_work/settle/模拟数据/程序生成";
       String fileName = "ORI82." + ft.getCurrentDate() + "." + FileNetPaidORI.serialNo;
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
        int[] data_len_head = {2, 7, 7, 4};
        Object[] data_head = {"82", this.getStartTime(), this.getEndTime(), 1};
        /*********************************************/
        /******************文件尾***********************/
        int[] data_type_tail = {File52App.T_STR, File52App.T_STR};
        int[] data_len_tail = {4, 8};
        Object[] data_tail = {"CRC:", "037067c5"};
        /*********************************************/
        /*

         */
        /*******************订单数据**************/
        int[] data_type = { FileBase.T_STR,FileBase.T_STR,FileBase.T_BCD,FileBase.T_STR,FileBase.T_STR,
                            FileBase.T_STR,FileBase.T_STR,FileBase.T_BCD,FileBase.T_INT,FileBase.T_INT,
                            FileBase.T_INT,FileBase.T_INT,FileBase.T_INT
            
                          };
        int[] data_len = {20,2,7,4,2,
                          3,2,2,4,4,
                          4,4,4
                    
                          };
        Object[] datas = {"012016112500000001", "01","20161125120000","0201", "11",
                           "001","01","0100",2,0,
                           400,0,0
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
    
}
