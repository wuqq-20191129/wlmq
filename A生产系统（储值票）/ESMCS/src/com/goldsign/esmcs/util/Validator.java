/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.util;

import com.goldsign.csfrm.util.CharUtil;
import com.goldsign.esmcs.env.AppConstant;

/**
 * 数据验证工具类
 * 
 * @author lenovo
 */
public class Validator {

    /**
     * 数组是否包含指定元素
     * 
     * @param arr
     * @param obj
     * @return 
     */
    public static boolean contain(Object[] arr, Object obj){
        
        if(null == obj){
            return false;
        }
        if(null == arr){
            return false;
        }
        for(Object o: arr){
            if(o == obj){
                return true;
            }
            if(o.equals(obj)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * 位是否设置
     *
     * @param b
     * @return
     */
    public static boolean isBitSet(char b) {
        return (b + "").equals(AppConstant.YES_ONE_SET);
    }
    
    /**
     * 位是否设置
     *
     * @param b
     * @return
     */
    public static boolean isBitSet(byte b) {
        return (b + "").equals(AppConstant.YES_ONE_SET);
    }
    
    /**
     * 指定字节、指定位是否置位
     *
     * @param bytePos
     * @param bitPos
     * @return
     * @throws TkEsJniException
     */
    public static boolean isStatusBytePosBitSet(byte statusByte, int bitPos){
        
        String status = CharUtil.byteToBinReverse8bStr(statusByte);
        char b = status.charAt(bitPos);
        boolean result = isBitSet(b);
        return result;
    }
  
    /**
     * 指定字节、指定位是否置位
     *
     * @param bytePos
     * @param bitPos
     * @return
     * @throws TkEsJniException
     */
    public static boolean isStatusBytesPosBitSet(byte[] statusBytes, byte bytePos, int bitPos) {

        byte statusByte = statusBytes[bytePos];
        return isStatusBytePosBitSet(statusByte, bitPos);
    }
}
