/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.dll.library;

import com.goldsign.esmcs.dll.structure.CardInf;
import com.goldsign.esmcs.dll.structure.LineState;
import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * 储值票通道DLL
 * 
 * @author lenovo
 */
public interface EsPkChanelDll extends Library {

    EsPkChanelDll INSTANCE = (EsPkChanelDll) Native.loadLibrary("JC8200ES2-zp1", EsPkChanelDll.class);
    //EsPkChanelDll INSTANCE = (EsPkChanelDll) Native.loadLibrary("JC8200ES2-zp1-130515", EsPkChanelDll.class);
    
    /*************************************
    //函数说明：卡机关闭
    //入口：
    //返回：操作结果成功true或失败false
    *************************************/
    short ACCAPI_Close();
    
    /*************************************
    //函数说明：读机器动作状态
    //入口：StatusVal[0]机器状态：
    StatusVal[0]
    StatusVal[1]
    StatusVal[2]
    StatusVal[3]输入点
    StatusVal[4]
    StatusVal[5]输出点
    StatusVal[6]机器状态
    StatusVal[7]
    StatusVal[8]卡机故障
    //返回：操作结果成功true或失败false
    *************************************/           
    short ACCAPI_GetACCStatus(byte[] StatusVal);
   
    /*************************************
    //函数说明：得到函数版本
    //入口：*Version 版本字符串
    //返回：操作结果成功true或失败false
    *************************************/
    short ACCAPI_GetDllVersion(byte []Version);
            
    /*************************************
    //函数说明：得到全部工位卡的信息
    //入口：
    //返回：操作结果成功true或失败false
    *************************************/           
    short ACCAPI_GetStationInf(CardInf[] Card, short []BoxIsCard);
            
    /*************************************
    //函数说明：卡机初始化
    //入口：
    //返回：操作结果成功true或失败false
    *************************************/           
    short ACCAPI_Init();

    /*************************************
    //函数说明：步进电机零位
    //入口：
    //返回：操作结果成功true或失败false
    *************************************/
    short ACCAPI_MADAToZero();
    
    /*************************************
    //函数说明：走卡
    //入口：ActNo动作号
    //1 手动发卡
    //2 手动走卡
    //返回：操作结果成功true或失败false
    *************************************/           
    short ACCAPI_MoveCard(CardInf[] Card, short []BoxIsCard);
            
    /*************************************
    //函数说明：卡机打开
    //入口：
    //返回：操作结果成功true或失败false
    *************************************/
    short ACCAPI_Open(short port);
            
    //ACCAPI_Open_Message
 
    /*************************************
    //函数说明：暂停继续命令
    //入口：
    //返回：操作结果成功true或失败false
    *************************************/
    short ACCAPI_PauseOrContinue(byte X);
 
    //得到个工位卡的信息
    short ACCAPI_ResetACC(CardInf[] Card, short[] BoxIsCard);
    
    /*************************************
    //函数说明：发卡
    //入口：
    //返回：操作结果成功true或失败false
    *************************************/        
    short ACCAPI_SendCard(int CardNo, CardInf[] Card, short[] BoxIsCard);
      
    /*************************************
    //函数说明：得到卡运行时错误信息
    //入口：
    //返回：操作结果成功true或失败false
    *************************************/
    short ACCAPI_GetCardRunError(LineState.ByReference State);
    
    /**
     * 取设备版本号
     * 
     * @param Version
     * @return 
     */
    short ACCAPI_GetDevVersion(byte []Version);
  
}
