/*
 * 文件名：ConnectInInf
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.sammcs.dll.structure;

import com.sun.jna.Structure;


/*
 * PSAM接口方法psam_read_dev_op 发行入参 结构体
 * @author     lijia
 * @version    V1.0
 */

public class ConnectInInf extends Structure{

    //01
    public byte[] treatyVersions = new byte[2];//接口协议版本
    //C0
    public byte[] assistParam = new byte[2];//接口辅助入参
    //00
    public byte[] appType = new byte[2];//卡类型标识
    //03
    public byte[] commType = new byte[2];//命令类型标识
    //无
    public byte[] MKSIp = new byte[8];//命令数据--加密机IP地址
    
    public byte[] KMSPort = new byte[4];//命令数据--加密机端口
    
    public byte KMSVersion = 0x00;//加密机版本
    
    public static class ByReference extends ConnectInInf implements Structure.ByReference{
    }
    
    public static class ByValue extends ConnectInInf implements Structure.ByValue{
    }
    
}
