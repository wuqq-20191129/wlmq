/*
 * 文件名：OutInf
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.kmscommu.dll.structure;

import com.sun.jna.Structure;


/*
 * 取密钥输出参数结构体
 * @author     lindaquan
 * @version    V1.0
 */

public class TokenKeyOutInf extends Structure {

    public byte[] treatyVersions = new byte[2];//接口协议版本
    
    public byte[] response = new byte[2];//执行响应
    
    public byte[] cardType = new byte[2];//卡类标识
    
    public byte[] commType = new byte[2];//命令类型标识
    
    public byte[] authenMac = new byte[8];//mac
    
    public byte[] authenKey = new byte[12];//key
    
    public byte paramEndSign = 0x00;//字符串结束符
    
    public static class ByReference extends TokenKeyOutInf implements Structure.ByReference{
    }
    
    public static class ByValue extends TokenKeyOutInf implements Structure.ByValue{
    }
    
}
