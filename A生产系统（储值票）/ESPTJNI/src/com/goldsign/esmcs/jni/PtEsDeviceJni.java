/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.jni;

import com.goldsign.esmcs.dll.library.EsPrinterDll;
import com.sun.jna.Pointer;

/**
 * ES打印机JNI
 * 
 * @author lenovo
 */
public class PtEsDeviceJni {

    private Pointer hCom;
            
    /**
     * 关闭串口
     * 
     * @return 
     */
    public short printerClosePrinterCom(){
        return EsPrinterDll.INSTANCE.PRINTERAPI_ClosePrinterCom(hCom);
    } 
    
    /**
     * 得到动态库版本
     * 
     * @param ver 
     */ 
    public boolean printerGetDllVersion(byte[] ver){
        return EsPrinterDll.INSTANCE.PRINTERAPI_GetDllVersion(ver);
    }  
    
    /**
     * 初始化打印机
     * 
     * @return 
     */
    public short printerInitPrinter(){
        return EsPrinterDll.INSTANCE.PRINTERAPI_InitPrinter(hCom);
    }
    
    /**
     * 打开串口     
     * 
     * @param comNo
     * @param comRate
     * @return 
     */
    public long printerOpenPrinterCom(short comNo, short comRate){
        
        hCom = EsPrinterDll.INSTANCE.PRINTERAPI_OpenPrinterCom(comNo, comRate);
        //long result = Pointer.nativeValue(hCom);
        int hashCode = hCom.hashCode();
        if(hashCode != -1){
            return 0;
        }else{
            return -1;
        }
    }
    
    /**
     * 打印行缓冲器里的内容并向前1行(回车)
     * 
     * @return 
     */
    public short printerPrintBufAndRunOneRowCR(){
        return EsPrinterDll.INSTANCE.PRINTERAPI_PrintBufAndRunOneRow_CR(hCom);
    }

    /**
     * 打印行缓冲器里的内容并向前1行(换行)
     * 
     * @return 
     */
    public short printerPrintBufAndRunOneRowLF(){
        return EsPrinterDll.INSTANCE.PRINTERAPI_PrintBufAndRunOneRow_LF(hCom);
    }
    
    /**
     * 打印字符
     * 
     * @param ch
     * @return 
     */
    public short printerPrintChars(byte[] ch){
        return EsPrinterDll.INSTANCE.PRINTERAPI_PrintChars(hCom, ch);
    }
    
    /**
     * 使打印机进入/退出汉字打印方式
     * 
     * @param mode
     * @return 
     */     
    public short printerPrinterToChinese(byte mode){
        return EsPrinterDll.INSTANCE.PRINTERAPI_PrinterToChinese(hCom, mode);
    }
    
    /**
     * 打印行缓冲器里的内容，并向前走纸n点行
     * 
     * @param n
     * @return 
     */
    public short printerRunNRows(byte n){
        return EsPrinterDll.INSTANCE.PRINTERAPI_RunNRows(hCom, n);
    }
    
    /**
     * 设置字符打印方式
     * 
     * @param printType
     * @param printDiret
     * @return 
     */
    public short printerSetPrintMode(byte printType, byte printDiret){
        return EsPrinterDll.INSTANCE.PRINTERAPI_SetPrintMode(hCom, printType, printDiret);
    }
    
    /**
     * 设置字符行间距为1/6英寸
     * 
     * @return 
     */
    public short printerSetRowDis16Mile(){
        return EsPrinterDll.INSTANCE.PRINTERAPI_SetRowDis_16Mile(hCom);
    }
    
    /**
     * 设置行间距为n点
     * 
     * @param n
     * @return 
     */
    public short printerSetRowDisNPoints(byte n){
        return EsPrinterDll.INSTANCE.PRINTERAPI_SetRowDis_NPoints(hCom, n);
    }  
}