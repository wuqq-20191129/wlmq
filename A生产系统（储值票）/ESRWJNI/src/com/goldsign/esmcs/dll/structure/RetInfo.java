/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.dll.structure;

import com.sun.jna.Structure;

/**
 *
 * @author lenovo
 */
public class RetInfo extends Structure{
    
    public short wErrCode;					// 错误码
    public byte	bNoticeCode;					// 关联提示码
    public byte[] bRfu = new byte[8];				// 扩充保留字段

    public static class ByReference extends RetInfo implements Structure.ByReference {
    }

    public static class ByValue extends RetInfo implements Structure.ByValue {
    }
}
