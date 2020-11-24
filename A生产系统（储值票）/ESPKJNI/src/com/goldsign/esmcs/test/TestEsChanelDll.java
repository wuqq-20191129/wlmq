/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.test;

import com.goldsign.esmcs.dll.library.EsPkChanelDll;
import com.goldsign.esmcs.dll.structure.CardInf;
import com.goldsign.esmcs.dll.structure.LineState;

/**
 *
 * @author lenovo
 */
public class TestEsChanelDll {
    
    public static void main(String[] args) throws InterruptedException {
      
        //testACCAPI_GetCardRunError();
        //testACCAPI_GetDllVersion();
        //testACCAPI_GetDevVersion();
        testACCAPI_Open((short)1);
        ACCAPI_Init();
        ACCAPI_SendCard();
        //Thread.sleep(1000);
        ACCAPI_MoveCard();
        //ACCAPI_GetStationInf();
        //ACCAPI_GetACCStatus();
        //ACCAPI_Continue();
        //ACCAPI_Pause();
        //testACCAPI_GetCardRunError();
        //testACCAPI_Close();
    }
    



    
    public static void testACCAPI_Open(short port){
        
        int result = EsPkChanelDll.INSTANCE.ACCAPI_Open(port);
        System.out.println("open port:"+result);
    }
    
     public static void testACCAPI_Close(){
        
        int result = EsPkChanelDll.INSTANCE.ACCAPI_Close();
        System.out.println("close port:"+result);
    }
     
    public static void ACCAPI_Init(){
        
        int result = EsPkChanelDll.INSTANCE.ACCAPI_Init();
        System.out.println("init:"+result);
    }
    
    public static void ACCAPI_ResetACC(){
        CardInf[] cardInfs = new CardInf[15];
        int result = EsPkChanelDll.INSTANCE.ACCAPI_ResetACC(cardInfs, null);
        System.out.println("reset:"+result);
        for(CardInf cardInf: cardInfs){
            System.out.print("CardNo:"+cardInf.CardNo);
            System.out.print("  CurrSite:"+cardInf.CurrSite);
            System.out.print("  RecyBox:"+cardInf.RecyBox);
            System.out.print("  State:"+cardInf.State);
            System.out.print("  TagSite:"+cardInf.TagSite);
            System.out.println();
        }
        
    }
    
    public static void ACCAPI_MADAToZero(){
        
        int result = EsPkChanelDll.INSTANCE.ACCAPI_MADAToZero();
        System.out.println("zero:"+result);
    }
    
    static CardInf[] cardInfs = (CardInf[]) new CardInf().toArray(15);
    public static void ACCAPI_SendCard(){
        //byte[] cardNo = new byte[4];
        //cardNo[3] = 1;
        
        int result = EsPkChanelDll.INSTANCE.ACCAPI_SendCard(12, cardInfs, null);
        System.out.println("sendcard:"+result);
        for(CardInf cardInf: cardInfs){
            System.out.print("  CurrSite:"+cardInf.CurrSite);
            System.out.print("  TagSite:"+cardInf.TagSite);
            System.out.print("  CardNo:"+cardInf.CardNo);   
            System.out.print("  State:"+cardInf.State);
            System.out.print("  RecyBox:"+cardInf.RecyBox);
            System.out.println();
        }
    }
    
    public static void ACCAPI_MoveCard(){
        
        //CardInf[] cardInfs = (CardInf[]) new CardInf().toArray(15);cardInfs
        
        int result = EsPkChanelDll.INSTANCE.ACCAPI_MoveCard(cardInfs, null);
        System.out.println("movecard:"+result);
        for(CardInf cardInf: cardInfs){
            System.out.print("CardNo:"+cardInf.CardNo);
            System.out.print("  CurrSite:"+cardInf.CurrSite);
            System.out.print("  RecyBox:"+cardInf.RecyBox);
            System.out.print("  State:"+cardInf.State);
            System.out.print("  TagSite:"+cardInf.TagSite);
            System.out.println();
        }
        
    }
    
    public static void ACCAPI_Pause(){
        
        int result = EsPkChanelDll.INSTANCE.ACCAPI_PauseOrContinue((byte)0);
        System.out.println("pause:"+result);
    }
    
    public static void ACCAPI_Continue(){
        
        int result = EsPkChanelDll.INSTANCE.ACCAPI_PauseOrContinue((byte)1);
        System.out.println("Continue:"+result);
    }
    
    public static void ACCAPI_GetStationInf(){
        CardInf[] cardInfs2 = (CardInf[]) new CardInf().toArray(15);
        int result = EsPkChanelDll.INSTANCE.ACCAPI_GetStationInf(cardInfs2, new short[100]);
        System.out.println("stationInf:"+result);
        for(CardInf cardInf: cardInfs2){
            System.out.print("CardNo:"+cardInf.CardNo);
            System.out.print("  CurrSite:"+cardInf.CurrSite);
            System.out.print("  RecyBox:"+cardInf.RecyBox);
            System.out.print("  State:"+cardInf.State);
            System.out.print("  TagSite:"+cardInf.TagSite);
            System.out.println();
        }
    }
    
    public static void ACCAPI_GetACCStatus(){
        byte[] status = new byte[9];
        int result = EsPkChanelDll.INSTANCE.ACCAPI_GetACCStatus(status);
        System.out.println("status:"+result);
        for(byte s: status){
            System.out.print("s:"+s);
            System.out.println();
        }
    }
    
    public static void testACCAPI_GetDllVersion(){
        
        byte[] Version = new byte[30];
        int result = EsPkChanelDll.INSTANCE.ACCAPI_GetDllVersion(Version);
        System.out.println("dll:"+new String(Version));
    }
    
    public static void testACCAPI_GetDevVersion(){
        
        byte[] Version = new byte[100];
        int result = EsPkChanelDll.INSTANCE.ACCAPI_GetDevVersion(Version);
        System.out.println(new String("版本："+Version));
    }
    
    public static void testACCAPI_GetCardRunError(){
        
        LineState.ByReference lineState = new LineState.ByReference();
        int result = EsPkChanelDll.INSTANCE.ACCAPI_GetCardRunError(lineState);
        System.out.println("error:"+result);
        //for(LineState lineState: lineStates){
            System.out.print("  Box1:"+lineState.Box1);
            System.out.print("  Box2:"+lineState.Box2);
            System.out.print("  Box3:"+lineState.Box3);
            System.out.print("  Box4:"+lineState.Box4);
            System.out.print("  Box5:"+lineState.Box5);
            System.out.print("  Box6:"+lineState.Box6);
            System.out.print("  Box7:"+lineState.Box7);
            System.out.print("  Box8:"+lineState.Box8);
            System.out.print("  Box9:"+lineState.Box9);
            System.out.print("  Reader1:"+lineState.Reader1);
            System.out.print("  Reader2:"+lineState.Reader2);
            System.out.println();
        //}
        System.out.println("runError:"+result);
    }
}
