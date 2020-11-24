/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.test.file.oct;

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
public class FileOctErr extends FileBase{

   
    
    public static void main(String[] arg) {
        FileOctErr ft = new FileOctErr();
       // String path="D:/en_work/dir_prog_work/settle/模拟数据/程序生成";
       //String fileName="ERR_BUS_1001_"+ft.getCurrentDate()+"."+"TXT";
        String fileName="ERR_BUS_1001_20140425"+".TXT";
        try {
            char[] buffer = ft.getBufferChar();
            ft.writeFile(buffer, FileBase.path, fileName);
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

        /*******************错误交易信息**************/
        int[] data_type = {FileBase.T_STR, FileBase.T_STR, FileBase.T_STR, FileBase.T_STR, FileBase.T_STR, 
                          FileBase.T_STR, FileBase.T_STR};
        int[] data_len = {10, 16, 20, 20, 14,
                         2,6};
        Object[] datas = {"0000000000", "0000474ab3002168", "00000000000011138291", "4CDE4368            ", "20140417153110",
                          "01","2"};
        /****************************************************************************************/

        int blen2 = this.getBufferLenForOct(data_len);
        int offset = 0;
        bs = new char[ blen2 ]; //总字符数

        char[] cs = this.getLineForOct(datas, data_len, data_type);
        this.addCharArrayToBuffer(bs, cs, offset);
        offset += blen2;
   
        return bs;
    }
    
}
