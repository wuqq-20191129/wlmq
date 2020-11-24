/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.service.impl;

import com.goldsign.csfrm.service.impl.BaseService;
import com.goldsign.csfrm.util.DateHelper;
import com.goldsign.csfrm.util.MessageShowUtil;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.esmcs.exception.EsJniException;
import com.goldsign.esmcs.jni.PtEsDeviceJni;
import com.goldsign.esmcs.service.IEsDeviceService;
import com.goldsign.esmcs.util.Converter;
import com.goldsign.esmcs.vo.EsPortParam;
import com.goldsign.esmcs.vo.OrderVo;
import java.io.UnsupportedEncodingException;
import org.apache.log4j.Logger;

/**
 * ES设备服务类
 * 
 * @author lenovo
 */
public class EsDeviceService extends BaseService implements IEsDeviceService{
    
    private static final Logger logger = Logger.getLogger(EsDeviceService.class.getName());
    
    private PtEsDeviceJni ptEsDeviceJni;
    
    private static final Object PT_LOCK = new Object();//打印机锁
    
    public EsDeviceService(){
        try{
            this.ptEsDeviceJni = new PtEsDeviceJni();
        }catch(java.lang.NoClassDefFoundError e){
            //e.printStackTrace();
        }
    }

    /**
     * 打开打印端口
     * 
     * @param callParam
     * @return
     * @throws EsJniException 
     */
    @Override
    public CallResult openPtPort(CallParam callParam) throws EsJniException {
        
        CallResult callResult = new CallResult();
        
        EsPortParam esPortParam = (EsPortParam)callParam;
        short comNo = esPortParam.getPort();
        short comRate = (short)esPortParam.getComRate();
        long result = 0;
        synchronized(PT_LOCK){
            result = ptEsDeviceJni.printerOpenPrinterCom(comNo, comRate);
        }
        if(result == 0){
            callResult.setResult(true);
        }
        
        callResult.setObj(result);
        
        return callResult;
    }

    /**
     * 关闭打印机端口
     * 
     * @param callParam
     * @return
     * @throws EsJniException 
     */
    @Override
    public CallResult closePtPort(CallParam callParam) throws EsJniException {
        
        CallResult callResult = new CallResult();
        short result = 0;
        synchronized(PT_LOCK){
            result = ptEsDeviceJni.printerClosePrinterCom();
        }
        if(result == 0){
            callResult.setResult(true);
        }
        
        callResult.setObj(result);
        
        return callResult;
    }

    /**
     * 获取打印机DLL版本
     * @param callParam
     * @return
     * @throws EsJniException 
     */
    @Override
    public CallResult printerGetDllVersion() throws EsJniException {
        CallResult callResult = new CallResult();
        
        byte[] ver = new byte[23];
        boolean result = false;
        synchronized(PT_LOCK){
            result = ptEsDeviceJni.printerGetDllVersion(ver);
        }
        if(result){
            callResult.setResult(true);
        }
        
        callResult.setObj(new String(ver).trim());
        return callResult;
    }

    /**
     * 初始化打印机
     * @param callParam
     * @return
     * @throws EsJniException 
     */
    @Override
    public CallResult printerInit() throws EsJniException {
        CallResult callResult = new CallResult();
        
        short result = 0;
        synchronized(PT_LOCK){
            result = ptEsDeviceJni.printerInitPrinter();
        }
        if(result == 0){
            callResult.setResult(true);
        }
        
        callResult.setObj(result);
        return callResult;
    }

    /**
     * 设置打印机字符打印模式
     * @param callParam
     * @return
     * @throws EsJniException 
     */
    @Override
    public CallResult printerSetPrintMode(CallParam callParam) throws EsJniException {
        CallResult callResult = new CallResult();
        
        byte a = ((Integer)callParam.getParam(0)).byteValue();
        byte b = ((Integer)callParam.getParam(1)).byteValue();
        short result = 0;
        synchronized(PT_LOCK){
            result = ptEsDeviceJni.printerSetPrintMode(a,b);
        }
        if(result == 0){
            callResult.setResult(true);
        }
        
        callResult.setObj(result);
        return callResult;
    }

    /**
     * 设置打印机退出、进入汉字打印模式
     * @param callParam
     * @return
     * @throws EsJniException 
     */
    @Override
    public CallResult printerToChinese(CallParam callParam) throws EsJniException {
        CallResult callResult = new CallResult();
        
        short result = 0;
        synchronized(PT_LOCK){
            result = ptEsDeviceJni.printerPrinterToChinese(((Integer)callParam.getParam()).byteValue());            
        }
        if(result == 0){
            callResult.setResult(true);
        }
        
        callResult.setObj(result);
        return callResult;
    }

    /**
     * 设置打印机字符间距 1/6英寸
     * @return
     * @throws EsJniException 
     */
    @Override
    public CallResult printerSetRowDis16Mile() throws EsJniException {
        CallResult callResult = new CallResult();
        
        short result = 0;
        synchronized(PT_LOCK){
            result = ptEsDeviceJni.printerSetRowDis16Mile();
        }
        if(result == 0){
            callResult.setResult(true);
        }
        
        callResult.setObj(result);
        return callResult;
    }

    /**
     * 设置打印机行间距
     * @param callParam
     * @return
     * @throws EsJniException 
     */
    @Override
    public CallResult printerSetRowDisNPoints(CallParam callParam) throws EsJniException {
        
        CallResult callResult = new CallResult();
        
        short result = 0;
        synchronized(PT_LOCK){
            try{
                Integer n = Integer.valueOf((String)callParam.getParam());
                result = ptEsDeviceJni.printerSetRowDisNPoints(n.byteValue());   
            }catch(Exception ex){
               result = 1;
            }
                    
        }
        if(result == 0){
            callResult.setResult(true);
        }
        
        callResult.setObj(result);
        return callResult;
    }

    /**
     * 设置打印机打印（输入缓冲区）内容
     * @param callParam
     * @return
     * @throws EsJniException 
     */
    @Override
    public CallResult printerPrintChars(CallParam callParam) throws EsJniException {
        CallResult callResult = new CallResult();
        
        short result=-1;
        try {
            synchronized(PT_LOCK){
                result = ptEsDeviceJni.printerPrintChars(((String)callParam.getParam()).getBytes("GB2312"));
            }
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage());
        }
        if(result == 0){
            callResult.setResult(true);
        }
        
        callResult.setObj(result);
        return callResult;
    }

    /**
     * 打印行缓冲器里的内容，并向前走纸n点行
     * @param callParam
     * @return
     * @throws EsJniException 
     */
    @Override
    public CallResult printerRunNRows(CallParam callParam) throws EsJniException {
        CallResult callResult = new CallResult();
        
        short result = 1;
        synchronized(PT_LOCK){
            result = ptEsDeviceJni.printerRunNRows(Integer.valueOf((String)callParam.getParam()).byteValue());
        }
        if(result == 0){
            callResult.setResult(true);
        }
        
        callResult.setObj(result);
        return callResult;
    }

    /**
     * 打印行缓冲器里的内容，换行 
     * @return
     * @throws EsJniException 
     */
    @Override
    public CallResult printBufAndRunOneRowLF() throws EsJniException {
        CallResult callResult = new CallResult();
        short result = 1;
        synchronized(PT_LOCK){
            result = ptEsDeviceJni.printerPrintBufAndRunOneRowLF();
        }
        if(result == 0){
            callResult.setResult(true);
        }
        
        callResult.setObj(result);
        return callResult;
    }

    /**
     * 打印行缓冲器里的内容，回车 
     * @return
     * @throws EsJniException 
     */
    @Override
    public CallResult printBufAndRunOneRowCR() throws EsJniException {
        CallResult callResult = new CallResult();
        
        short result = 1;
        synchronized(PT_LOCK){
            result = ptEsDeviceJni.printerPrintBufAndRunOneRowCR();
        }
        if(result == 0){
            callResult.setResult(true);
        }
        
        callResult.setObj(result);
        return callResult;
    }
    
    //打印小票
    @Override
    public void printOrder(OrderVo orderVo) {
        
        CallParam callParam = new CallParam();        
        try {
            setPrintMode();//设置打印模式
            String str = "     \n工作类型: "+Converter.getEsWorkTypeDes(orderVo.getWorkType())+
                            "\n订单号: "+orderVo.getOrderNo()+"\n";
            String printMoney = orderVo.getPrintMoney();
              if(StringUtil.isInt(printMoney)){
                str = str+"票卡金额: "+(Integer.parseInt(printMoney)/100)+"元\n";
            }else{
                 str = str+"票卡金额: "+printMoney+"\n";
            }
            str = str+"生产数量: "+orderVo.getOrderNum()+"\n";
            str = str+"好卡数量: "+orderVo.getGoodCardNum()+"\n";
            str = str+"坏卡数量: "+orderVo.getBadCardNum()+"\n";
            str = str+"结余数量: "+orderVo.getUnFinishNum()+"\n";
            str = str+"操作员: "+orderVo.getEmployeeId()+"\n";
            str = str+"打印时间: "+DateHelper.curDateToStr19yyyy_MM_dd_HH_mm_ss()+"\n     \n\n";
            callParam.resetParam(str);
            printerPrintChars(callParam);
            printBufAndRunOneRowLF();
            printBufAndRunOneRowLF();  
//            //printBufAndRunOneRowLF();
//            //printBufAndRunOneRowLF();            
//            callParam.resetParam("工作类型: "+Converter.getEsWorkTypeDes(orderVo.getWorkType())+"\0");
//            printerPrintChars(callParam);
//            printBufAndRunOneRowLF();
//           // printOneTimeSleep();
//            
//            callParam.resetParam("订单号: "+orderVo.getOrderNo()+"\0");
//            printerPrintChars(callParam);
//            printBufAndRunOneRowLF();
//            //printOneTimeSleep();
//       
//            String printMoney = orderVo.getPrintMoney();
//            if(StringUtil.isInt(printMoney)){
//                callParam.resetParam("票卡金额: "+(Integer.parseInt(printMoney)/100)+"元\0");
//            }else{
//                callParam.resetParam("票卡金额: "+printMoney+"\0");
//            }
//            printerPrintChars(callParam);
//            printBufAndRunOneRowLF();
//           // printOneTimeSleep();
//            
//            callParam.resetParam("生产数量: "+orderVo.getOrderNum()+"\0");
//            printerPrintChars(callParam);
//            printBufAndRunOneRowLF();
//           // printOneTimeSleep();
//            
//            callParam.resetParam("好卡数量: "+orderVo.getGoodCardNum()+"\0");
//            printerPrintChars(callParam);
//            printBufAndRunOneRowLF();
//           // printOneTimeSleep();
//            
//            callParam.resetParam("坏卡数量: "+orderVo.getBadCardNum()+"\0");
//            printerPrintChars(callParam);
//            printBufAndRunOneRowLF();
//            //printOneTimeSleep();
//            
//            callParam.resetParam("结余数量: "+orderVo.getUnFinishNum()+"\0");
//            printerPrintChars(callParam);
//            printBufAndRunOneRowLF();
//           // printOneTimeSleep();
//            
//            callParam.resetParam("操作员: "+orderVo.getEmployeeId()+"\0");
//            printerPrintChars(callParam);
//            printBufAndRunOneRowLF();
//            //printOneTimeSleep();
//            
//            callParam.resetParam("打印时间: "+DateHelper.curDateToStr19yyyy_MM_dd_HH_mm_ss()+"\0");
//            printerPrintChars(callParam);
//            printBufAndRunOneRowLF();
//            //printOneTimeSleep();
//            
//            printBufAndRunOneRowLF();
//            printBufAndRunOneRowLF();
//            printBufAndRunOneRowLF();
//            printBufAndRunOneRowLF();
//            printBufAndRunOneRowLF();
            
        } catch (EsJniException ex) {
            logger.error(ex);
        }
    }
    
    /**
     * 打印一次休息时间
     * 
     */
    private void printOneTimeSleep(){
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            logger.error(ex);
        }
    }
    
    /**
     * 设置打印模式
     * 
     * @throws EsJniException 
     */
    private void setPrintMode() throws EsJniException{
        
        CallParam callParam = new CallParam();
        
        callParam.setParam(new Integer(0));
        callParam.setParam(new Integer(0));
        printerSetPrintMode(callParam);
        
        callParam.resetParam(new Integer(1));//中文
        printerToChinese(callParam);
    }
}
