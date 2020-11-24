/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.env;

/**
 *
 * @author lenovo
 */
public class CommuConstant {
    
     public static final String ENCRYPT_KEY = "GOLDSIGN";
     
     public static final String CONN_CLOSED = "0";    //连接关闭
     public static final String CONN_OPENED = "1";    //连接打开
     
     public static final int CONN_IPCHECK_NORMAL = 0;                   //IP正常
     public static final int CONN_IPCHECK_ILLEGAL = 1;                   //IP非法
     public static final int CONN_IPCHECK_OPENED = 2;             //IP已打开
     
     public static final byte STX_B = (byte) 0xEB;
     public static final byte ETX = 0x03;
     public static final byte QRY = 0x01;
     public static final byte NDT = 0x02;
     public static final byte DTA = 0x04;
    
     public static final String RESULT_CODE_NDT = "0101";   //成功，数据长度为0
     public static final String RESULT_CODE_DTA = "0100";   //成功，数据长度不为0
     public static final String RESULT_CODE_SUC = "0";   //成功
     
}
