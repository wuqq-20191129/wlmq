/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.kmscommu.jni;

import com.goldsign.kmscommu.dll.library.CardKeyGetDll;
import com.goldsign.kmscommu.dll.structure.*;
import com.goldsign.kmscommu.vo.CardKeyResult;
import com.goldsign.kmscommu.vo.TokenKeyResult;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 *
 * @author lenovo
 */
public class CardKeyGetJni {

    private static final byte[] TREATY_VERSIONS = new byte[]{(byte)'0', (byte)'1'};
    private static final byte[] ASSIST_PARAM = new byte[]{(byte)'0', (byte)'0'};
    private static final byte[] APP_TYPE = new byte[]{(byte)'0', (byte)'0'};
    private static final byte[] COMM_TYPE_AUTHOR = new byte[]{(byte)'0', (byte)'1'};
    private static final byte[] COMM_TYPE_CK_GET = new byte[]{(byte)'0', (byte)'2'};
    private static final byte[] COMM_TYPE_TK_GET = new byte[]{(byte)'0', (byte)'3'};
    
    private static final Object LOCK = new Object();
    /**
     * 授权
     * 
     * @param kmIp ASCII 如10.200.48.138，则为(byte)'0'(byte)'A'(byte)'C'(byte)'8'(byte)'3'(byte)'0'(byte)'8'(byte)'A';
     * @param kmPort ASCII 如88, 则为 (byte)'0'(byte)'0'(byte)'5'(byte)'8';
     * @param pin ASCII 如FFFFFFFF，则为(byte)'F'(byte)'F'(byte)'F'(byte)'F'(byte)'F'(byte)'F'(byte)'F';(byte)'F'; 
     * * @param keyVer ASCII 如00，则为(byte)'0'(byte)'0';
     * @return 
     */
    public CardKeyResult author(byte[] kmIp, byte[] kmPort, byte[] pin, byte[] keyVer){
        
        AuthInInf.ByReference authInInf = new AuthInInf.ByReference();
        AuthOutInf.ByReference authOutInf = new AuthOutInf.ByReference();
        
        authInInf.treatyVersions = TREATY_VERSIONS;
        authInInf.assistParam = ASSIST_PARAM;
        authInInf.appType = APP_TYPE;
        authInInf.commType = COMM_TYPE_AUTHOR;
        System.arraycopy(kmIp, 0, authInInf.encryptorip, 0, kmIp.length);
        System.arraycopy(kmPort, 0, authInInf.encryptorPort, 0, kmPort.length);
        System.arraycopy(pin, 0, authInInf.encryptorPin, 0, pin.length);
        System.arraycopy(keyVer, 0, authInInf.keyVersion, 0, keyVer.length);
       
        int ret = CardKeyGetDll.INSTANCE.get_card_key_op(authInInf, authOutInf);
        
        CardKeyResult result = new CardKeyResult();
        result.setCode(authOutInf.response);
        
        return result;
    }
            
    /**
     * 取卡密钥
     * 
     * @param cardNo ASCII 如0000100000000001，则为“0000100000000001”.getBytes();
     * @return 
     */
    public CardKeyResult getCardKey(byte[] cardNo){
    
        CardKeyInInf.ByReference cardKeyInInf = new CardKeyInInf.ByReference();
        CardKeyOutInf.ByReference cardKeyOutInf = new CardKeyOutInf.ByReference();
        
        cardKeyInInf.treatyVersions = TREATY_VERSIONS;
        cardKeyInInf.assistParam = ASSIST_PARAM; 
        cardKeyInInf.appType = APP_TYPE;
        cardKeyInInf.commType = COMM_TYPE_CK_GET;
        System.arraycopy(cardNo, 0, cardKeyInInf.cardNo, 0, cardNo.length);      
        /*
        byte[] responseBytes = ByteBuffer.allocate(552).array();
        int len = responseBytes.length;
        for(int i=0; i<len; i++){
            responseBytes[i] = 0;
        }
        
        cardKeyOutInf.treatyVersions = Arrays.copyOfRange(responseBytes, 0, 2);
        cardKeyOutInf.response = Arrays.copyOfRange(responseBytes, 2, 4);
        cardKeyOutInf.cardType = Arrays.copyOfRange(responseBytes, 4, 6);
        cardKeyOutInf.commType = Arrays.copyOfRange(responseBytes, 6, 8);
        cardKeyOutInf.resultData = Arrays.copyOfRange(responseBytes, 8, 552);
        */
        /*
        System.arraycopy(responseBytes, 0, cardKeyOutInf.treatyVersions, 0, 2);
        System.arraycopy(responseBytes, 2, cardKeyOutInf.response, 0, 2);
        System.arraycopy(responseBytes, 4, cardKeyOutInf.cardType, 0, 2);
        System.arraycopy(responseBytes, 6, cardKeyOutInf.commType, 0, 2);
        System.arraycopy(responseBytes, 8, cardKeyOutInf.resultData, 0, 544);
        *
        */
        
        synchronized(LOCK){
            int ret = CardKeyGetDll.INSTANCE.get_card_key_op(cardKeyInInf, cardKeyOutInf);
        }
        
        CardKeyResult result = new CardKeyResult();
        result.setCode(cardKeyOutInf.response);
        result.setMsg(cardKeyOutInf.resultData);
        
        return result;
        
    }
    
    /**
     * 取TOKEN密钥
     *
     * param phyNo ASCII 则为“00000000”.getBytes();
     * @param logicNo ASCII 如0，则为“00000000”.getBytes();
     * @return
     */
    public TokenKeyResult getTokenKey(byte[] phyNo, byte[] logicNo){
    
        TokenKeyInInf.ByReference tokenKeyInInf = new TokenKeyInInf.ByReference();
        TokenKeyOutInf.ByReference tokenKeyOutInf = new TokenKeyOutInf.ByReference();
        
        tokenKeyInInf.treatyVersions = TREATY_VERSIONS;
        tokenKeyInInf.assistParam = ASSIST_PARAM; 
        tokenKeyInInf.appType = APP_TYPE;
        tokenKeyInInf.commType = COMM_TYPE_TK_GET;
        System.arraycopy(phyNo, 0, tokenKeyInInf.phyNo, 0, phyNo.length);
        System.arraycopy(logicNo, 0, tokenKeyInInf.logicNo, 0, logicNo.length);
        synchronized(LOCK){
            int ret = CardKeyGetDll.INSTANCE.get_card_key_op(tokenKeyInInf, tokenKeyOutInf);
        }
        TokenKeyResult result = new TokenKeyResult();
        result.setCode(tokenKeyOutInf.response);
        result.setMac(tokenKeyOutInf.authenMac);
        result.setKey(tokenKeyOutInf.authenKey);
        
        return result;
        
    }
}
