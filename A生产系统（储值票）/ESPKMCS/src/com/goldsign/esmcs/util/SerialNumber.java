/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.esmcs.util;

/**
 *
 * @author limingjin
 */
public class SerialNumber {
    
    public static final Object LOCK = new Object();
    
    private static int grobalSeqNo = 0;
    
    private static int seqNo;
    
    public static void resetGrobalSeqNo(int finishNum){
        synchronized(LOCK){
            SerialNumber.grobalSeqNo = finishNum;
        }
    }
    
    public static int getSeqNoPlus(){
        synchronized(LOCK){   
            SerialNumber.seqNo = SerialNumber.grobalSeqNo++; 
            return SerialNumber.seqNo;
        }
    }
    
     public static int getSeqNo(){
        synchronized(LOCK){
            return SerialNumber.seqNo;
        }
    }
}
