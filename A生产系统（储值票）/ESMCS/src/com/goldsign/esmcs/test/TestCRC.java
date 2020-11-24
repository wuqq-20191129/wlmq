/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.test;

import com.goldsign.csfrm.util.CharUtil;
import com.goldsign.esmcs.util.Converter;
import com.goldsign.publib.io.FileBufferReader;
import java.io.*;

/**
 *
 * @author lenovo
 */
public class TestCRC {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, IOException, Exception{
        //StringBuffer str = new StringBuffer("12345678");//D:/project/csMetro/程序开发/ES票卡生产系统/代码/ESPKMCS/data/upload/order
        //String crc = Converter.getCRC32Value(str);
        //System.out.println(crc);//0972d361 9ae0daaf
        getCommuCRC();
        //System.out.println(getCRC32("12345678"));
    }
    
    private static String getCommuCRC() throws FileNotFoundException, UnsupportedEncodingException, IOException, Exception{
        
        //String fileName = "D:/project/csMetro/程序开发/ES票卡生产系统/代码/ESPKMCS/data/upload/order/ES119.00201309030003";
        String fileName = "D:\\project\\csMetro\\程序开发\\ES票卡生产系统\\代码\\ESPKMCS\\data\\upload\\order\\ES118.03201310150010v2";
        BufferedReader fis = new FileBufferReader(fileName);
        String line = null;
        StringBuffer sb = new StringBuffer();
        while ((line = fis.readLine()) != null) {
            if (isCrcData(line)) {
                String crc = getCRC(sb.toString());
                //CRC校验
                System.out.println("crc:"+crc);
            }else{
                addToBufferForLine(sb, line);
            }
        }
        String crc = Converter.getCRC32Value(sb);
        System.out.println("crc:"+crc);
        return null;
        
    }
    
    private static String getCRC(String str) {

        byte[] b = null;
        b = str.getBytes();
        long crc32 = CharUtil.getCRC32Value(b);
        String crc = Long.toHexString(crc32);
        for (int i = crc.length(); i < 8; i++) {
            crc = "0" + crc;
        }
        return crc;

    }
    
    public static final String RECORD_PREFIX_CRC = "CRC:";
    private static boolean isCrcData(String line) {
        if (line.startsWith(RECORD_PREFIX_CRC)) {
            return true;
        }
        return false;
    }
    
    public final static char[] CRLF_1 = {0x0d, 0x0a};//换行符
    private static void addToBufferForLine(StringBuffer sb, String line) {
        sb.append(line);
        sb.append(CRLF_1);
    }
    
    public static int getCRC32(String _source) throws UnsupportedEncodingException { 
        int crc = 0xFFFFFFFF;// initial contents of LFBSR 
        int poly = 0xEDB88320; // reverse polynomial 
        byte[] bytes = _source.getBytes("utf-8"); 
        for (byte b : bytes) { 
            int temp = (crc ^ b) & 0xff;// read 8 bits one at a time 
            for (int i = 0; i < 8; i++) { 
                if ((temp & 1) == 1) 
                    temp = (temp >>> 1) ^ poly;
                else 
                    temp = (temp >>> 1); 
            } 
            crc = (crc >>> 8 ^ temp); 
        } // flip bits 
        crc = crc ^ 0xffffffff; 
        return crc; 
    } 
}
