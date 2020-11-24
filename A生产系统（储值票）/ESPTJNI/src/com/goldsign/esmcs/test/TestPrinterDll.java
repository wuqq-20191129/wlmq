/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.test;

import com.goldsign.esmcs.dll.library.EsPrinterDll;
import com.goldsign.esmcs.jni.PtEsDeviceJni;
import com.sun.jna.Pointer;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author lenovo
 */
public class TestPrinterDll {
    
    private static PtEsDeviceJni ptEsDeviceJni = new PtEsDeviceJni();
    
    public static void main(String[] args) throws UnsupportedEncodingException, Exception {
       /*for(int i=0;i<2;i++){
            testPrinterOpenPrinterCom();
            testPrinterInitPrinter();
            testPrinterSetPrintMode();
            testPrinterPrinterToChinese();
            testPrinterPrintChars();
            //testPrinterPrintBufAndRunOneRowLF();
            testPrinterPrintBufAndRunOneRowCR();
            testPrinterRunNRows();
            testPrinterClosePrinterCom();
       }*/
        testPrinterGetDllVersion();
    }
    
    public static void testPrinterOpenPrinterCom(){
        
        short comNo = (short)2;
        short baud = (short)9600;
        ptEsDeviceJni.printerOpenPrinterCom(comNo, baud);
    }
    
    public static void testPrinterInitPrinter() {
        
        ptEsDeviceJni.printerInitPrinter();
    }
    
    public static void testPrinterPrintChars() throws Exception {
        
        ptEsDeviceJni.printerPrintChars("中国你好，好好....!abcdfsadf戋333\0".getBytes("gbk"));
        Thread.sleep(100);
        testPrinterPrintBufAndRunOneRowLF();
        ptEsDeviceJni.printerPrintChars("中国你好，好好....!abcdfsadf戋333\0".getBytes("gbk"));
        Thread.sleep(100);
        testPrinterPrintBufAndRunOneRowLF();
        ptEsDeviceJni.printerPrintChars("中国你好，好好....!abcdfsadf戋333\0".getBytes("gbk"));
        Thread.sleep(100);
        testPrinterPrintBufAndRunOneRowLF();
        ptEsDeviceJni.printerPrintChars("中国你好，好好....!abcdfsadf戋333\0".getBytes("gbk"));
        Thread.sleep(100);
        testPrinterPrintBufAndRunOneRowLF();
        ptEsDeviceJni.printerPrintChars("中国你好，好好....!abcdfsadf戋333\0".getBytes("gbk"));
        Thread.sleep(100);
        testPrinterPrintBufAndRunOneRowLF();
        ptEsDeviceJni.printerPrintChars("中国你好，好好....!abcdfsadf戋333\0".getBytes("gbk"));
        Thread.sleep(100);
        testPrinterPrintBufAndRunOneRowLF();
        ptEsDeviceJni.printerPrintChars("中国你好，好好....!abcdfsadf戋333\0".getBytes("gbk"));
        Thread.sleep(100);
        testPrinterPrintBufAndRunOneRowLF();
        ptEsDeviceJni.printerPrintChars("中国你好，好好....!abcdfsadf戋333\0".getBytes("gbk"));
        Thread.sleep(100);
        testPrinterPrintBufAndRunOneRowLF();
        ptEsDeviceJni.printerPrintChars("中国你好，好好....!abcdfsadf戋333\0".getBytes("gbk"));
        Thread.sleep(100);
        testPrinterPrintBufAndRunOneRowLF();
        ptEsDeviceJni.printerPrintChars("中国你好，好好....!abcdfsadf戋333\0".getBytes("gbk"));
        Thread.sleep(100);
        testPrinterPrintBufAndRunOneRowLF();
        ptEsDeviceJni.printerPrintChars("中国你好，好好....!abcdfsadf戋333\0".getBytes("gbk"));
        Thread.sleep(100);
        testPrinterPrintBufAndRunOneRowLF();
        ptEsDeviceJni.printerPrintChars("中国你好，好好....!abcdfsadf戋333\0".getBytes("gbk"));
        Thread.sleep(100);
    }
        
    public static void testPrinterGetDllVersion(){
        
        byte[] version = new byte[12];
        boolean result = EsPrinterDll.INSTANCE.PRINTERAPI_GetDllVersion(version);
        System.out.println(new String(version));
    }
    
    public static void testPrinterClosePrinterCom(){
        ptEsDeviceJni.printerClosePrinterCom();
    }
    
    public static void testPrinterPrintBufAndRunOneRowCR(){
        ptEsDeviceJni.printerPrintBufAndRunOneRowCR();
    }

    public static void testPrinterSetPrintMode(){
        ptEsDeviceJni.printerSetPrintMode((byte)0, (byte)0);
    }
    
    public static void testPrinterPrinterToChinese(){
        ptEsDeviceJni.printerPrinterToChinese((byte)1);
    }
    
    public static void testPrinterRunNRows(){
        ptEsDeviceJni.printerRunNRows((byte)10);
    }
    
    public static void testPrinterPrintBufAndRunOneRowLF(){
        ptEsDeviceJni.printerPrintBufAndRunOneRowLF();
    }
}
