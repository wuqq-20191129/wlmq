/*
 * 文件名：testPSamUssueDll
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.kmscommu.test;

import com.goldsign.kmscommu.dll.library.CardKeyGetDll;
import com.goldsign.kmscommu.dll.structure.*;
import com.goldsign.kmscommu.jni.CardKeyGetJni;
import com.goldsign.kmscommu.vo.CardKeyResult;
import com.goldsign.kmscommu.vo.TokenKeyResult;


/*
 * 单元测试类
 * @author     lindaquan
 * @version    V1.0
 */

public class TestCardKeyDll {
    
    public static void main(String[] args){

        //testAuth();
        //testCardKeyGet();
        //testTokenKeyGet();
        testAuthJni();
        //testCardKeyGetJni();
        testTokenKeyGetJni();
        testTokenKeyGetJni();
        testTokenKeyGetJni();
        testTokenKeyGetJni();
        testTokenKeyGetJni();
        testTokenKeyGetJni();
        testTokenKeyGetJni();
        testTokenKeyGetJni();
        testTokenKeyGetJni();
        
    }
    
    
    private static CardKeyGetJni cardKeyGetJni = new CardKeyGetJni();
    
    public static void testAuthJni(){
        
        byte[] encryptorip = new byte[8];
        encryptorip[0] = (byte)'0';
        encryptorip[1] = (byte)'A';
        encryptorip[2] = (byte)'C';
        encryptorip[3] = (byte)'8';
        encryptorip[4] = (byte)'3';
        encryptorip[5] = (byte)'0';
        encryptorip[6] = (byte)'8';
        encryptorip[7] = (byte)'A';
        
        byte[] encryptorPort = new byte[4];
        encryptorPort[0] = (byte)'0';
        encryptorPort[1] = (byte)'0';
        encryptorPort[2] = (byte)'5';
        encryptorPort[3] = (byte)'8';
        
        byte[] encryptorPin = new byte[8];
        encryptorPin[0] = (byte)'F';
        encryptorPin[1] = (byte)'F';
        encryptorPin[2] = (byte)'F';
        encryptorPin[3] = (byte)'F';
        encryptorPin[4] = (byte)'F';
        encryptorPin[5] = (byte)'F';
        encryptorPin[6] = (byte)'F';
        encryptorPin[7] = (byte)'F';        
        
        byte[] keyVersion = new byte[2];
        keyVersion[0] = '0';
        keyVersion[1] = '0';
        
        CardKeyResult ret = cardKeyGetJni.author(encryptorip, encryptorPort, encryptorPin, keyVersion);
        
        System.out.println(new String(ret.getCode()));
       
    }
    
    public static void testAuth(){
        AuthInInf.ByReference authInInf = new AuthInInf.ByReference();
        AuthOutInf.ByReference authOutInf = new AuthOutInf.ByReference();
        //01 C0 00 01 0A C8308A0058FFFFFFFF
        authInInf.treatyVersions[0] = (byte)'0';
        authInInf.treatyVersions[1] = (byte)'1';
        
        authInInf.assistParam[0] = (byte)'C';
        authInInf.assistParam[1] = (byte)'0';
        
        authInInf.appType[0] = (byte)'0';
        authInInf.appType[1] = (byte)'0';
        
        authInInf.commType[0] = (byte)'0';
        authInInf.commType[1] = (byte)'1';
        //0A C8 30 8A
        authInInf.encryptorip[0] = (byte)'0';
        authInInf.encryptorip[1] = (byte)'A';
        authInInf.encryptorip[2] = (byte)'C';
        authInInf.encryptorip[3] = (byte)'8';
        authInInf.encryptorip[4] = (byte)'3';
        authInInf.encryptorip[5] = (byte)'0';
        authInInf.encryptorip[6] = (byte)'8';
        authInInf.encryptorip[7] = (byte)'A';
        //00 58
        authInInf.encryptorPort[0] = (byte)'0';
        authInInf.encryptorPort[1] = (byte)'0';
        authInInf.encryptorPort[2] = (byte)'5';
        authInInf.encryptorPort[3] = (byte)'8';
        authInInf.encryptorPin[0] = (byte)'F';
        authInInf.encryptorPin[1] = (byte)'F';
        authInInf.encryptorPin[2] = (byte)'F';
        authInInf.encryptorPin[3] = (byte)'F';
        authInInf.encryptorPin[4] = (byte)'F';
        authInInf.encryptorPin[5] = (byte)'F';
        authInInf.encryptorPin[6] = (byte)'F';
        authInInf.encryptorPin[7] = (byte)'F';        
        
        int ret = CardKeyGetDll.INSTANCE.get_card_key_op(authInInf, authOutInf);
        
        System.out.println(new String(authOutInf.response));
       
    }
    
    public static void testCardKeyGet(){
        CardKeyInInf.ByReference in = new CardKeyInInf.ByReference();
        CardKeyOutInf.ByReference out = new CardKeyOutInf.ByReference();
        //01 C0 00 01 0A C8308A0058FFFFFFFF
        in.treatyVersions[0] = (byte)'0';
        in.treatyVersions[1] = (byte)'1';
        
        in.assistParam[0] = (byte)'C';
        in.assistParam[1] = (byte)'0';
        
        in.appType[0] = (byte)'0';
        in.appType[1] = (byte)'0';
        
        in.commType[0] = (byte)'0';
        in.commType[1] = (byte)'2';
       
        //0000100000000001
        in.cardNo[0] = (byte)'0';
        in.cardNo[1] = (byte)'0';
        in.cardNo[2] = (byte)'0';
        in.cardNo[3] = (byte)'0';
        in.cardNo[4] = (byte)'1';
        in.cardNo[5] = (byte)'0';
        in.cardNo[6] = (byte)'0';
        in.cardNo[7] = (byte)'0';
        in.cardNo[8] = (byte)'0';
        in.cardNo[9] = (byte)'0';
        in.cardNo[10] = (byte)'0';
        in.cardNo[11] = (byte)'0';
        in.cardNo[12] = (byte)'0';
        in.cardNo[13] = (byte)'0';
        in.cardNo[14] = (byte)'0';
        in.cardNo[15] = (byte)'1';
        
       int ret = CardKeyGetDll.INSTANCE.get_card_key_op(in, out);
        
        
        System.out.println(new String(out.response));
        System.out.println(new String(out.resultData));
        
    }
    
    public static void testTokenKeyGet(){
        TokenKeyInInf.ByReference in = new TokenKeyInInf.ByReference();
        TokenKeyOutInf.ByReference out = new TokenKeyOutInf.ByReference();
        //01 C0 00 01 0A C8308A0058FFFFFFFF
        in.treatyVersions[0] = (byte)'0';
        in.treatyVersions[1] = (byte)'1';
        
        in.assistParam[0] = (byte)'C';
        in.assistParam[1] = (byte)'0';
        
        in.appType[0] = (byte)'0';
        in.appType[1] = (byte)'0';
        
        in.commType[0] = (byte)'0';
        in.commType[1] = (byte)'3';
       
        //0000100000000001
        in.phyNo =   "00001000".getBytes();
        in.logicNo = "00000001".getBytes();
  
       int ret = CardKeyGetDll.INSTANCE.get_card_key_op(in, out);
        
        
        System.out.println(new String(out.response));
        System.out.println(new String(out.authenMac));
        System.out.println(new String(out.authenKey));
        
    }

    public static void testCardKeyGetJni(){
        
        byte[] cardNo = new byte[16];
        cardNo[0] = (byte)'0';
        cardNo[1] = (byte)'0';
        cardNo[2] = (byte)'0';
        cardNo[3] = (byte)'0';
        cardNo[4] = (byte)'1';
        cardNo[5] = (byte)'0';
        cardNo[6] = (byte)'0';
        cardNo[7] = (byte)'0';
        cardNo[8] = (byte)'0';
        cardNo[9] = (byte)'0';
        cardNo[10] = (byte)'0';
        cardNo[11] = (byte)'0';
        cardNo[12] = (byte)'0';
        cardNo[13] = (byte)'0';
        cardNo[14] = (byte)'0';
        cardNo[15] = (byte)'1';
        
        CardKeyResult ret = cardKeyGetJni.getCardKey(cardNo);
        
        System.out.println(new String(ret.getCode()));
        System.out.println(new String(ret.getMsg()));
        
    }
    
    public static void testTokenKeyGetJni() {
        
        byte[] cardNo = "A9500000".getBytes();
        byte[] logicNo = "00000314".getBytes();
        
        
        TokenKeyResult ret = cardKeyGetJni.getTokenKey(cardNo, logicNo);
        
        System.out.println(new String(ret.getCode()));
        System.out.println(new String(ret.getMac()));
        System.out.println(new String(ret.getKey()));
    }
}
