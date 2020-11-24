/*
 * 文件名：ReadOutInf
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.sammcs.dll.structure;

import com.sun.jna.Structure;


/*
 * PSAM接口方法psam_read_dev_op 出参 结构体
 * @author     mqf
 * @version    V1.0
 */

public class ReadOutInf extends Structure {

    public byte[] treatyVersions = new byte[2];//接口协议版本
    
    public byte[] response = new byte[2];//执行响应
    
    public byte[] cardType = new byte[2];//卡类标识
    
    public byte[] commType = new byte[2];//命令类型标识
    
    public byte[] issueState = new byte[2];//响应数据(制卡状态) 1已制
    
//    public byte[] psamCardNoPrefix = new byte[4];//响应数据(psam卡号长沙地铁前缀)
    
    public byte[] psamCardNo = new byte[16];//响应数据(psam卡号)
    
    public byte[] psamCardVersion = new byte[2];//响应数据(psam版本)
    
    public byte[] psamCardType = new byte[2];//响应数据(psam类型)
    
    public byte[] phyNo = new byte[16];//响应数据(psam芯片号)/物理卡号
    
    public static class ByReference extends ReadOutInf implements Structure.ByReference{
    }
    
    public static class ByValue extends ReadOutInf implements Structure.ByValue{
    }
    
}
