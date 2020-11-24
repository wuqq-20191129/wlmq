/*
 * 文件名：AuthInInf
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.sammcs.dll.structure;

import com.goldsign.sammcs.env.ConfigConstant;
import com.goldsign.sammcs.util.ConfigUtil;
import com.sun.jna.Structure;


/*
 * PSAM接口方法psam_issue_dev_op 授权入参 结构体
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
    
    //add by mqf 20140312 
    public byte[] keyVerstion = new byte[2];//密钥版本信息
    
    public byte paramEndSign = 0x00;//加密机版本
    
    public static class ByReference extends AuthInInf implements Structure.ByReference{
    }
    
    public static class ByValue extends AuthInInf implements Structure.ByValue{
    }
    
}
