/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.dll.library;

import com.goldsign.esmcs.dll.structure.AlermInf;
import com.goldsign.esmcs.dll.structure.BoxInf;
import com.goldsign.esmcs.dll.structure.BoxSensor;
import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * 储值票票箱DLL
 * 
 * @author lenovo
 */
public interface EsPkBoxDll extends Library {

    EsPkBoxDll INSTANCE = (EsPkBoxDll) Native.loadLibrary("ESBoxDll-zp1", EsPkBoxDll.class);
    
    /**
     * 关闭串口
     * 
     * @return 
     */
    short ESBoxAPI_ExitCom();
 
    /**
     * 得到所有票箱状态
     * 
     * @param CardBox
     * @param ErrInf
     * @return 
     */
    short ESBoxAPI_GetCardBoxState(BoxInf[] CardBox, AlermInf.ByReference ErrInf);
    
    /**
     * 得到所有光偶状态
     * 
     * @param Sensor
     * @param ErrInf
     * @return 
     */
    short ESBoxAPI_GetSensorState(BoxSensor[] Sensor, AlermInf.ByReference ErrInf);
     
    /**
     * 初始化串口
     * 
     * @param ComNo
     * @return 
     */
    boolean ESBoxAPI_InitCom(short ComNo);
    
    //ESBoxAPI_PressCardBox
    
    /**
     * 复位所有票箱
     * 
     * @param CardBox
     * @param ErrInf
     * @return 
     */
    short ESBoxAPI_ResetAllCardBox(BoxInf[] CardBox, AlermInf.ByReference ErrInf);
    
    /**
     * 复位单个票箱 1-5号票箱
     * 
     * @param CardBoxNo
     * @param CardBox
     * @param ErrInf
     * @return 
     */
    short ESBoxAPI_ResetOneCardBox(short CardBoxNo, BoxInf[] CardBox, AlermInf.ByReference ErrInf);
    
    /**
     * 测试
     * 
     * @return 
     */
    short ESBoxAPI_Test();
    
    /**
     * 卸载所有票箱
     * 
     * @param CardBox
     * @param ErrInf
     * @return 
     */
    short ESBoxAPI_UnLoadAllCardBox(BoxInf[] CardBox, AlermInf.ByReference ErrInf);
    
    /**
     * 卸载单个票箱 1-5号票箱
     * 
     * @param CardBoxNo
     * @param CardBox
     * @param ErrInf
     * @return 
     */
    short ESBoxAPI_UnLoadOneCardBox(short CardBoxNo, BoxInf[] CardBox, AlermInf.ByReference ErrInf);
    
    //取动态库版本号
    //short ESBoxAPI_GetDllVer(byte []Version);
    
    /*************************************
    //函数说明：得到函数版本
    //入口：*Version 版本字符串
    //返回：操作结果成功true或失败false
    *************************************/
    boolean ESBoxAPI_GetDllVer(byte []Version);
    
    
}