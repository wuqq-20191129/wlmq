/*
 * 文件名：AuthInInf
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.kmscommu.dll.structure;

import com.sun.jna.Structure;


/*
 * 授权输入参数结构体
 * @author     lindaquan
 * @version    V1.0
 */

public class AuthInInf extends Structure{

    public byte[] treatyVersions = new byte[2];//接口协议版本
    
    public byte[] assistParam = new byte[2];//接口辅助入参
    
    public byte[] appType = new byte[2];//卡类型标识
    
    public byte[] commType = new byte[2];//命令类型标识
    
    public byte[] encryptorip = new byte[8];//加密机IP地址
    
    public byte[] encryptorPort = new byte[4];//加密机端口
    
    public byte[] encryptorPin = new byte[8];//加密机授权PIN
    
    public byte[] keyVersion = new byte[2];//密钥版本
    
    public byte paramEndSign = 0x00;//字符串结束符
    
    public static class ByReference extends AuthInInf implements Structure.ByReference{
    }
    
    public static class ByValue extends AuthInInf implements Structure.ByValue{
    }
    
}
