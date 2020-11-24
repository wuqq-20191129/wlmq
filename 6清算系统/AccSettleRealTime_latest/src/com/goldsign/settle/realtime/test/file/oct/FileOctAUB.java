/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.test.file.oct;

import com.goldsign.settle.realtime.test.file.base.FileBase;
import com.goldsign.settle.realtime.test.file.FileTrx;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hejj
 */
public class FileOctAUB extends FileBase{
     public static void main(String[] arg) {
        FileOctAUB ft = new FileOctAUB();
        String path="D:/en_work/dir_prog_work/settle/模拟数据/程序生成";
       String fileName="AUB_BUS_1001_"+ft.getCurrentDate()+"."+"TXT";
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

        /*******************交易结算返回信息**************/
        int[] data_type = {FileBase.T_STR, FileBase.T_STR, FileBase.T_STR, FileBase.T_STR};
        int[] data_len = {16, 2, 8, 12};
        Object[] datas = { "0000474ab3002168", "11", "00000009", "        0.14",};
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
