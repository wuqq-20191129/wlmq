/*
 * 文件名：AuthInInf
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.kmscommu.dll.structure;

import com.sun.jna.Structure;


/*
 * 取密钥输入参数结构体
 * @author     lindaquan
 * @version    V1.0
 */

public class TokenKeyInInf extends Structure{

    //01
    public byte[] treatyVersions = new byte[2];//接口协议版本
    //C0
    public byte[] assistParam = new byte[2];//接口辅助入参
    //00
    public byte[] appType = new byte[2];//卡类型标识
    //02
    public byte[] commType = new byte[2];//命令类型标识
    
    //
    public byte[] phyNo = new byte[8];//物理卡号
    
    public byte[] logicNo = new byte[8];//逻辑卡号
    
    public byte paramEndSign = 0x00;//字符串结束符
    
    public static class ByReference extends TokenKeyInInf implements Structure.ByReference{
    }
    
    public static class ByValue extends TokenKeyInInf implements Structure.ByValue{
    }
    
}
