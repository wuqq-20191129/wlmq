/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.service;

import com.goldsign.csfrm.service.IBaseService;
import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.esmcs.exception.EsJniException;
import com.goldsign.esmcs.vo.OrderVo;

/**
 * ES设备服务接口
 * 
 * @author lenovo
 */
public interface IEsDeviceService extends IBaseService{

    /**
     * 打开打印机端口
     * 
     * @param callParam
     * @return
     * @throws EsJniException 
     */
    CallResult openPtPort(CallParam callParam)throws EsJniException;
    
    /**
     * 关闭打印机端口
     * 
     * @param callParam
     * @return
     * @throws EsJniException 
     */
    CallResult closePtPort(CallParam callParam)throws EsJniException;
    
    /**
     * 得到打印动态库版本
     * 
     * @return
     * @throws EsJniException 
     */
    CallResult printerGetDllVersion()throws EsJniException;
    
    /**
     * 初始化打印机
     * 
     * @return
     * @throws EsJniException 
     */
    CallResult printerInit()throws EsJniException;
          
    /**
     * 设置打印机字符打印模式 
     * 
     * @param callParam
     * @return
     * @throws EsJniException 
     */
    CallResult printerSetPrintMode(CallParam callParam)throws EsJniException;
     
    /**
     * 设置打印机退出、进入汉字打印模式   
     * 
     * @param callParam
     * @return
     * @throws EsJniException 
     */
    CallResult printerToChinese(CallParam callParam)throws EsJniException;
    
    /**
     * 设置打印机字符间距  
     * 
     * @return
     * @throws EsJniException 
     */
    CallResult printerSetRowDis16Mile()throws EsJniException;
    
    /**
     * 设置打印机行间距  
     * 
     * @param callParam
     * @return
     * @throws EsJniException 
     */
    CallResult printerSetRowDisNPoints(CallParam callParam)throws EsJniException;
              
    /**
     * 设置打印机打印（输入缓冲区）内容
     * 
     * @param callParam
     * @return
     * @throws EsJniException 
     */
    CallResult printerPrintChars(CallParam callParam)throws EsJniException;
    
    /**
     * 打印行缓冲器里的内容，并向前走纸n点行 
     * 
     * @param callParam
     * @return
     * @throws EsJniException 
     */
    CallResult printerRunNRows(CallParam callParam)throws EsJniException;
    
    /**
     * 打印行缓冲器里的内容，换行 
     * 
     * @return
     * @throws EsJniException 
     */
    CallResult printBufAndRunOneRowLF()throws EsJniException;
    
    /**
     * 打印行缓冲器里的内容，回车 
     * 
     * @return
     * @throws EsJniException 
     */
    CallResult printBufAndRunOneRowCR()throws EsJniException;
    
    /**
     * 打印订单
     * 
     * @param orderVo 
     */
    void printOrder(OrderVo orderVo);
}
