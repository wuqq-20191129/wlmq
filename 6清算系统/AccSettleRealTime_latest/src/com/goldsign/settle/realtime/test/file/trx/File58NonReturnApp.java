/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.test.file.trx;

import com.goldsign.settle.realtime.test.file.trx.File52App;
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
public class File58NonReturnApp extends FileBase{
     public static String serialNo="058";
     public static void main(String[] arg) {
        File58NonReturnApp ft = new File58NonReturnApp();
      //  String path="D:/en_work/dir_prog_work/settle/模拟数据/程序生成";
       String fileName="TRX0110."+ft.getCurrentDate()+"."+File58NonReturnApp.serialNo;
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
        int[] data_type_head = {File52App.T_STR, File52App.T_BCD, File52App.T_BCD, File52App.T_INT, File52App.T_INT};
        int[] data_len_head = {4, 7, 7, 2, 4};
        Object[] data_head = {"0110",this.getStartTime(), this.getEndTime(),  Integer.parseInt(serialNo), 1};
        /*********************************************/
        /******************文件尾***********************/
        int[] data_type_tail = {File52App.T_STR, File52App.T_STR};
        int[] data_len_tail = {4, 8};
        Object[] data_tail = {"CRC:", "037067c5"};
        /*********************************************/
        /*******************申请信息**************/
        int[] data_type = {File52App.T_STR,File52App.T_STR, File52App.T_STR, File52App.T_STR, File52App.T_STR,
                           File52App.T_BCD,File52App.T_STR, File52App.T_STR, File52App.T_STR, File52App.T_BCD,
                           File52App.T_STR,File52App.T_STR, File52App.T_STR, File52App.T_STR, File52App.T_STR, 
                           File52App.T_STR, File52App.T_BCD, File52App.T_BCD, File52App.T_STR};
        int[] data_len = {2,2, 4, 2, 3,
                          2, 20, 20,16, 7,
                          4, 6, 30, 12, 1,
                          18,1,1,1};
        Object[] datas = {"58","10", "0110", "03", "001", 
                          "0200", "00001000000030000016", "920868B0000000", "201405010100001", this.getDealDatetime(),
                          "0001", "000001", "张力", "81891712", "1",
                          "230119870101115519", "0","01","0"};
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
