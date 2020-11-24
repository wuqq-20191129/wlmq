/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.kmscommu.test;

import com.goldsign.kmscommu.jni.CardKeyGetJni;
import com.goldsign.kmscommu.vo.CardKeyResult;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lenovo
 */
public class TestCardKeyThread extends Thread{

    private static CardKeyGetJni cardKeyGetJni = new CardKeyGetJni();
    
    private String cardNo;
    
    public TestCardKeyThread(String cardNo){
        this.cardNo = cardNo;
    }
    
    @Override
    public void run() {
        
        while(true){
            System.out.println(Thread.currentThread().getName());
            testTokenKeyGetJni();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(TestCardKeyThread.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void testTokenKeyGetJni() {
        
        byte[] cardNoBs = this.cardNo.getBytes();
        
        
        CardKeyResult ret = cardKeyGetJni.getCardKey(cardNoBs);
        
        System.out.println(new String(ret.getCode()));
        System.out.println(new String(ret.getMsg()));
    }
        
    public static void main(String[] args){
        testAuthJni();
        TestCardKeyThread testCardKeyThread = new TestCardKeyThread("9000000011000019");
        testCardKeyThread.setName("thread01");
        testCardKeyThread.start();
        TestCardKeyThread testCardKeyThread2= new TestCardKeyThread("9000000011000020");
        testCardKeyThread2.setName("thread02");
        testCardKeyThread2.start();
        TestCardKeyThread testCardKeyThread3= new TestCardKeyThread("9000000011000021");
        testCardKeyThread3.setName("thread03");
        testCardKeyThread3.start();
    }
}
