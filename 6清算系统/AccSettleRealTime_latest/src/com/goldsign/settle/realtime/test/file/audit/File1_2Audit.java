/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.test.file.audit;

import com.goldsign.settle.realtime.test.file.audit.File31AgmAudit;
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
public class File1_2Audit extends FileBase{
    public static void main(String[] arg) {
        File1_2Audit ft = new File1_2Audit();
        String path="D:/en_work/dir_prog_work/settle/模拟数据/程序生成";
        String fileName="AUD0201."+ft.getCurrentDate();
        try {
            char[] buffer = ft.getBufferChar();
            ft.writeFile(buffer, path, fileName);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FileTrx.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public char[] getBufferChar() throws UnsupportedEncodingException {
        char[] bs = null;
        /******************文件头***********************/
        int[] data_type_head = {File52App.T_STR, File52App.T_BCD, File52App.T_BCD,  File52App.T_INT};
        int[] data_len_head = {4, 7, 7, 4};
        Object[] data_head = {"0201", this.getStartTime(), this.getEndTime(), 2};
        /*********************************************/
        /******************文件尾***********************/
        int[] data_type_tail = {File52App.T_STR, File52App.T_STR};
        int[] data_len_tail = {4, 8};
        Object[] data_tail = {"CRC:", "037067c5"};
        /*********************************************/
        /*

         */
        /*******************硬币箱数据**************/

        /****************************************************************************************/
        File31AgmAudit f1 = new File31AgmAudit();
        File32TvmAudit f2= new File32TvmAudit();
        
        char[] ds1 = f1.getBufferCharFromLine();
        char[] ds2 = f2.getBufferCharFromLine();
        
        
        
        int blen1 = this.getBufferLen(data_len_head); //长度+回车换行符
        //int blen2 = this.getBufferLen(data_len);
        int blen2  = ds1.length+ds2.length;
        int blen3 = this.getBufferLen(data_len_tail);
        int offset = 0;
        bs = new char[blen1 + blen2 + blen3]; //总字符数
        //文件头
        char[] cs = this.getLine(data_head, data_len_head, data_type_head);
        this.addCharArrayToBuffer(bs, cs, offset);
        offset += blen1;
        //文件记录

        this.addCharArrayToBuffer(bs, ds1, offset);
        offset += ds1.length;
        
         this.addCharArrayToBuffer(bs, ds2, offset);
        offset += ds2.length;
        
        
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
