/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.util;
import  com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;

/**
 *
 * @author hejj
 */
public class BusinessUtil {
    public static boolean onBusinessForMoble(){
        if(FrameCodeConstant.BUSINESS_MOBILE_CONTROL ==null)
            return false;
        if(FrameCodeConstant.BUSINESS_MOBILE_CONTROL.equals(FrameCodeConstant.BUSINESS_MOBILE_CONTROL_ON))
            return true;
        return false;
    }
    public static boolean onBusinessForQrCode(){
        if(FrameCodeConstant.BUSINESS_QRCODE_CONTROL ==null)
            return false;
        if(FrameCodeConstant.BUSINESS_QRCODE_CONTROL.equals(FrameCodeConstant.BUSINESS_QRCODE_CONTROL_ON))
            return true;
        return false;
    }
    public static boolean onBusinessForQrCodeMtr(){
        if(FrameCodeConstant.BUSINESS_QRCODE_MTR_CONTROL ==null)
            return false;
        if(FrameCodeConstant.BUSINESS_QRCODE_MTR_CONTROL.equals(FrameCodeConstant.BUSINESS_QRCODE_CONTROL_ON))
            return true;
        return false;
    }
    public static boolean onBusinessForTcc(){
        if(FrameCodeConstant.BUSINESS_TCC_CONTROL ==null)
            return false;
        if(FrameCodeConstant.BUSINESS_TCC_CONTROL.equals(FrameCodeConstant.BUSINESS_TCC_CONTROL_ON))
            return true;
        return false;
    }
    
    public static boolean onBusinessForNetPaid(){
        if(FrameCodeConstant.BUSINESS_NETPAID_CONTROL ==null)
            return false;
        if(FrameCodeConstant.BUSINESS_NETPAID_CONTROL.equals(FrameCodeConstant.BUSINESS_NETPAID_CONTROL_ON))
            return true;
        return false;
    }
    public static boolean onBusinessForMobleTrxDownload(){
        if(FrameCodeConstant.BUSINESS_MOBILE_CONTROL_TRX_DOWNLOAD ==null)
            return false;
        if(FrameCodeConstant.BUSINESS_MOBILE_CONTROL_TRX_DOWNLOAD.equals(FrameCodeConstant.BUSINESS_MOBILE_CONTROL_ON))
            return true;
        return false;
    }
    
}
