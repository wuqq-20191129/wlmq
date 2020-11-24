/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.sammcs.vo;

import com.goldsign.csfrm.util.CharUtil;
import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.sammcs.env.AppConstant;

/**
 *
 * @author lenovo
 */
public class KmsCfgParam extends CallParam {

    private String kmsIp;
    
    private String kmsPort;
    
    private String kmsPin;
    
    private String kmsKeyVerstion;

    /**
     * @return the kmsIp
     */
    public String getKmsIp() {
        return kmsIp;
    }
    
    public byte[] getKmsIpBs(){
    
        String ipStr = "";
        String[] ipArr = kmsIp.split(AppConstant.SEP_DOT_SIGN);
        for(int i=0; i<4; i++){
            ipStr += CharUtil.intToHex(Integer.parseInt(ipArr[i]));
        }
        
        return ipStr.getBytes();
    }

    /**
     * @param kmsIp the kmsIp to set
     */
    public void setKmsIp(String kmsIp) {
        this.kmsIp = kmsIp;
    }

    /**
     * @return the kmsPort
     */
    public String getKmsPort() {
        return kmsPort;
    }
    
    public byte[] getKmsPortBs(){
        
        int port = Integer.parseInt(kmsPort);
        String portStr = Integer.toHexString(port);
        portStr = portStr.toUpperCase();
        int len = 4-portStr.length();
        for(int i=0; i<len; i++){
            portStr = "0" + portStr;
        }
        return portStr.getBytes();
    }

    /**
     * @param kmsPort the kmsPort to set
     */
    public void setKmsPort(String kmsPort) {
        this.kmsPort = kmsPort;
    }

    /**
     * @return the kmsPin
     */
    public String getKmsPin() {
        return kmsPin;
    }
    
    public byte[] getKmsPinBs(){
        return kmsPin.getBytes();
    }

    /**
     * @param kmsPin the kmsPin to set
     */
    public void setKmsPin(String kmsPin) {
        this.kmsPin = kmsPin;
    }

    /**
     * 
     * @return kmsKeyVerstion
     */
    public String getKmsKeyVerstion() {
        return kmsKeyVerstion;
    }
    
    public byte[] getKmsKeyVerstionBs(){
        return kmsKeyVerstion.getBytes();
    }

    public void setKmsKeyVerstion(String kmsKeyVerstion) {
        this.kmsKeyVerstion = kmsKeyVerstion;
    }
    
    
    
}
