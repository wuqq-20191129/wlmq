/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.test;

import com.goldsign.esmcs.dll.library.EsPkBoxDll;
import com.goldsign.esmcs.dll.library.EsPkChanelDll;
import com.goldsign.esmcs.dll.structure.AlermInf;
import com.goldsign.esmcs.dll.structure.BoxInf;
import com.goldsign.esmcs.dll.structure.BoxSensor;

/**
 *
 * @author lenovo
 */
public class TestEsBoxDll {
    
    public static void main(String[] args) {
       
       //testESBoxAPI_Test();
        ESBoxAPI_InitCom((short)3);
        
       ESBoxAPI_GetCardBoxState();
        //ESBoxAPI_GetSensorState();
        //ESBoxAPI_ResetAllCardBox();
        //ESBoxAPI_UnLoadAllCardBox();
        //ESBoxAPI_ExitCom();
       // ESBoxAPI_ResetOneCardBox((short)1);
       ESBoxAPI_GetCardBoxState();
        testDllVersion();
    }
    
    public static void ESBoxAPI_InitCom(short port){
        boolean result = EsPkBoxDll.INSTANCE.ESBoxAPI_InitCom(port);
        System.out.println("initCom:"+result);
    }
    
    public static void ESBoxAPI_ExitCom(){
        int result = EsPkBoxDll.INSTANCE.ESBoxAPI_ExitCom();
        System.out.println("exitCom:"+result);
    }
    
    public static void ESBoxAPI_GetCardBoxState(){
        
        BoxInf[] boxInfs = (BoxInf[]) new BoxInf().toArray(13);
        AlermInf.ByReference alermInf = new AlermInf.ByReference();
        int result = EsPkBoxDll.INSTANCE.ESBoxAPI_GetCardBoxState(boxInfs, alermInf);
        System.out.println("boxstate:"+result);
        for(BoxInf boxInf: boxInfs){
            System.out.print("  downUp:"+boxInf.Box_CheckDownUp);
            System.out.print("  full:"+boxInf.Box_CheckFull);
            System.out.print("  willFull:"+boxInf.Box_CheckWillFull);
            System.out.print("  fullState:"+boxInf.Box_Full_State);
            System.out.print("  gateOpen:"+boxInf.Box_GateOpen);
            System.out.print("  isSpace:"+boxInf.Box_IsSpace);
            System.out.print("  keyOpen:"+boxInf.Box_KeyOpen);
            System.out.print("  reserve1:"+boxInf.Box_Reserve1);
            System.out.print("  reserve2:"+boxInf.Box_Reserve2);
            System.out.print("  runState:"+boxInf.Box_Run_State);
            System.out.print("  boxStyle:"+boxInf.Box_Style);
            System.out.print("  inf BoxStateORSensorNo:"+boxInf.Inf.BoxStateORSensorNo);
            System.out.print("  inf ErrorBoxNo:"+boxInf.Inf.ErrorBoxNo);
            System.out.print("  inf ErrorCode:"+boxInf.Inf.ErrorCode);
            System.out.print("  alermInf BoxStateORSensorNo:"+alermInf.BoxStateORSensorNo);
            System.out.print("  alermInf ErrorBoxNo:"+alermInf.ErrorBoxNo);
            System.out.print("  alermInf ErrorCode:"+alermInf.ErrorCode);
            System.out.println();
        }
        
    }
    
    public static void ESBoxAPI_GetSensorState(){
        
        BoxSensor[] boxSensors = (BoxSensor[]) new BoxSensor().toArray(5);
        AlermInf.ByReference alermInf = new AlermInf.ByReference();
        int result = EsPkBoxDll.INSTANCE.ESBoxAPI_GetSensorState(boxSensors, alermInf);
        System.out.println("sensorstate:"+result);
        for(BoxSensor boxSensor: boxSensors){
            System.out.print("  s1:"+boxSensor.S1);
            System.out.print("  s2:"+boxSensor.S2);
            System.out.print("  s3:"+boxSensor.S3);
            System.out.print("  s4:"+boxSensor.S4);
            System.out.print("  s5:"+boxSensor.S5);
            System.out.print("  s6:"+boxSensor.S6);
            System.out.print("  alermInf BoxStateORSensorNo:"+alermInf.BoxStateORSensorNo);
            System.out.print("  alermInf ErrorBoxNo:"+alermInf.ErrorBoxNo);
            System.out.print("  alermInf ErrorCode:"+alermInf.ErrorCode);
            System.out.println();
        }
        
    }
     
    public static void ESBoxAPI_ResetOneCardBox(short boxNo){
        
        BoxInf[] boxInfs = (BoxInf[]) new BoxInf().toArray(13);
        AlermInf.ByReference alermInf = new AlermInf.ByReference();
        int result = EsPkBoxDll.INSTANCE.ESBoxAPI_ResetOneCardBox(boxNo, boxInfs, alermInf);
        System.out.println("resetonebox:"+result);
        for(BoxInf boxInf: boxInfs){
            System.out.print("  downUp:"+boxInf.Box_CheckDownUp);
            System.out.print("  full:"+boxInf.Box_CheckFull);
            System.out.print("  willFull:"+boxInf.Box_CheckWillFull);
            System.out.print("  fullState:"+boxInf.Box_Full_State);
            System.out.print("  gateOpen:"+boxInf.Box_GateOpen);
            System.out.print("  isSpace:"+boxInf.Box_IsSpace);
            System.out.print("  keyOpen:"+boxInf.Box_KeyOpen);
            System.out.print("  reserve1:"+boxInf.Box_Reserve1);
            System.out.print("  reserve2:"+boxInf.Box_Reserve2);
            System.out.print("  runState:"+boxInf.Box_Run_State);
            System.out.print("  boxStyle:"+boxInf.Box_Style);
            System.out.print("  inf BoxStateORSensorNo:"+boxInf.Inf.BoxStateORSensorNo);
            System.out.print("  inf ErrorBoxNo:"+boxInf.Inf.ErrorBoxNo);
            System.out.print("  inf ErrorCode:"+boxInf.Inf.ErrorCode);
            System.out.print("  alermInf BoxStateORSensorNo:"+alermInf.BoxStateORSensorNo);
            System.out.print("  alermInf ErrorBoxNo:"+alermInf.ErrorBoxNo);
            System.out.print("  alermInf ErrorCode:"+alermInf.ErrorCode);
            System.out.println();
        }
        
    }
    
    public static void ESBoxAPI_ResetAllCardBox(){
        
        BoxInf[] boxInfs = (BoxInf[]) new BoxInf().toArray(13);
        AlermInf.ByReference alermInf = new AlermInf.ByReference();
        int result = EsPkBoxDll.INSTANCE.ESBoxAPI_ResetAllCardBox(boxInfs, alermInf);
        System.out.println("resetallbox:"+result);
        for(BoxInf boxInf: boxInfs){
            System.out.print("  downUp:"+boxInf.Box_CheckDownUp);
            System.out.print("  full:"+boxInf.Box_CheckFull);
            System.out.print("  willFull:"+boxInf.Box_CheckWillFull);
            System.out.print("  fullState:"+boxInf.Box_Full_State);
            System.out.print("  gateOpen:"+boxInf.Box_GateOpen);
            System.out.print("  isSpace:"+boxInf.Box_IsSpace);
            System.out.print("  keyOpen:"+boxInf.Box_KeyOpen);
            System.out.print("  reserve1:"+boxInf.Box_Reserve1);
            System.out.print("  reserve2:"+boxInf.Box_Reserve2);
            System.out.print("  runState:"+boxInf.Box_Run_State);
            System.out.print("  boxStyle:"+boxInf.Box_Style);
            System.out.print("  inf BoxStateORSensorNo:"+boxInf.Inf.BoxStateORSensorNo);
            System.out.print("  inf ErrorBoxNo:"+boxInf.Inf.ErrorBoxNo);
            System.out.print("  inf ErrorCode:"+boxInf.Inf.ErrorCode);
            System.out.print("  alermInf BoxStateORSensorNo:"+alermInf.BoxStateORSensorNo);
            System.out.print("  alermInf ErrorBoxNo:"+alermInf.ErrorBoxNo);
            System.out.print("  alermInf ErrorCode:"+alermInf.ErrorCode);
            System.out.println();
        }
        
    }
        
    public static void ESBoxAPI_UnLoadOneCardBox(short boxNo){
        
        BoxInf[] boxInfs = (BoxInf[]) new BoxInf().toArray(13);
        AlermInf.ByReference alermInf = new AlermInf.ByReference();
        int result = EsPkBoxDll.INSTANCE.ESBoxAPI_UnLoadOneCardBox(boxNo, boxInfs, alermInf);
        System.out.println("unloadonebox:"+result);
        for(BoxInf boxInf: boxInfs){
            System.out.print("  downUp:"+boxInf.Box_CheckDownUp);
            System.out.print("  full:"+boxInf.Box_CheckFull);
            System.out.print("  willFull:"+boxInf.Box_CheckWillFull);
            System.out.print("  fullState:"+boxInf.Box_Full_State);
            System.out.print("  gateOpen:"+boxInf.Box_GateOpen);
            System.out.print("  isSpace:"+boxInf.Box_IsSpace);
            System.out.print("  keyOpen:"+boxInf.Box_KeyOpen);
            System.out.print("  reserve1:"+boxInf.Box_Reserve1);
            System.out.print("  reserve2:"+boxInf.Box_Reserve2);
            System.out.print("  runState:"+boxInf.Box_Run_State);
            System.out.print("  boxStyle:"+boxInf.Box_Style);
            System.out.print("  inf BoxStateORSensorNo:"+boxInf.Inf.BoxStateORSensorNo);
            System.out.print("  inf ErrorBoxNo:"+boxInf.Inf.ErrorBoxNo);
            System.out.print("  inf ErrorCode:"+boxInf.Inf.ErrorCode);
            System.out.print("  alermInf BoxStateORSensorNo:"+alermInf.BoxStateORSensorNo);
            System.out.print("  alermInf ErrorBoxNo:"+alermInf.ErrorBoxNo);
            System.out.print("  alermInf ErrorCode:"+alermInf.ErrorCode);
            System.out.println();
        }
        
    }
    
    public static void ESBoxAPI_UnLoadAllCardBox(){
        
        BoxInf[] boxInfs = (BoxInf[]) new BoxInf().toArray(13);
        AlermInf.ByReference alermInf = new AlermInf.ByReference();
        int result = EsPkBoxDll.INSTANCE.ESBoxAPI_UnLoadAllCardBox(boxInfs, alermInf);
        System.out.println("unloadallbox:"+result);
        for(BoxInf boxInf: boxInfs){
            System.out.print("  downUp:"+boxInf.Box_CheckDownUp);
            System.out.print("  full:"+boxInf.Box_CheckFull);
            System.out.print("  willFull:"+boxInf.Box_CheckWillFull);
            System.out.print("  fullState:"+boxInf.Box_Full_State);
            System.out.print("  gateOpen:"+boxInf.Box_GateOpen);
            System.out.print("  isSpace:"+boxInf.Box_IsSpace);
            System.out.print("  keyOpen:"+boxInf.Box_KeyOpen);
            System.out.print("  reserve1:"+boxInf.Box_Reserve1);
            System.out.print("  reserve2:"+boxInf.Box_Reserve2);
            System.out.print("  runState:"+boxInf.Box_Run_State);
            System.out.print("  boxStyle:"+boxInf.Box_Style);
            System.out.print("  inf BoxStateORSensorNo:"+boxInf.Inf.BoxStateORSensorNo);
            System.out.print("  inf ErrorBoxNo:"+boxInf.Inf.ErrorBoxNo);
            System.out.print("  inf ErrorCode:"+boxInf.Inf.ErrorCode);
            System.out.print("  alermInf BoxStateORSensorNo:"+alermInf.BoxStateORSensorNo);
            System.out.print("  alermInf ErrorBoxNo:"+alermInf.ErrorBoxNo);
            System.out.print("  alermInf ErrorCode:"+alermInf.ErrorCode);
            System.out.println();
        }
        
    }
        
    public static void testESBoxAPI_Test(){
        int result = EsPkBoxDll.INSTANCE.ESBoxAPI_Test();
        System.out.print(result);
    }
    
    public static void testDllVersion(){
        
        byte[] Version = new byte[30];
        boolean result = EsPkBoxDll.INSTANCE.ESBoxAPI_GetDllVer(Version);
        System.out.println(new String(Version));
    }
}
