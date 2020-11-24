/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.kmscommu.test;

import com.goldsign.kmscommu.jni.CardKeyGetJni;
import com.goldsign.kmscommu.vo.CardKeyResult;
import com.goldsign.kmscommu.vo.TokenKeyResult;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lenovo
 */
public class TestTokenKeyThread extends Thread{

    private static CardKeyGetJni cardKeyGetJni = new CardKeyGetJni();
    
    @Override
    public void run() {
        
        while(true){
            System.out.println(Thread.currentThread().getName());
            testTokenKeyGetJni();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(TestTokenKeyThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

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
    
    public static void testTokenKeyGetJni() {
        
        byte[] cardNo = "A9500000".getBytes();
        byte[] logicNo = "00000314".getBytes();
        
        
        TokenKeyResult ret = cardKeyGetJni.getTokenKey(cardNo, logicNo);
        
        System.out.println(new String(ret.getCode()));
        System.out.println(new String(ret.getMac()));
        System.out.println(new String(ret.getKey()));
    }
        
    public static void main(String[] args){
        testAuthJni();
        TestTokenKeyThread testTokenKeyThread = new TestTokenKeyThread();
        testTokenKeyThread.setName("thread01");
        testTokenKeyThread.start();
        TestTokenKeyThread testTokenKeyThread2= new TestTokenKeyThread();
        testTokenKeyThread2.setName("thread02");
        testTokenKeyThread2.start();
        TestTokenKeyThread testTokenKeyThread3= new TestTokenKeyThread();
        testTokenKeyThread3.setName("thread03");
        testTokenKeyThread3.start();
    }
}
