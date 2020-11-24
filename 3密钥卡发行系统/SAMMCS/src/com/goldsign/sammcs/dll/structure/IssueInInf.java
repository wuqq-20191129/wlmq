/*
 * 文件名：AuthInInf
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.sammcs.dll.structure;

import com.sun.jna.Structure;


/*
 * PSAM接口方法psam_issue_dev_op 发行入参 结构体
 * @author     lindaquan
 * @version    V1.0
 */

public class IssueInInf extends Structure{

    //01
    public byte[] treatyVersions = new byte[2];//接口协议版本
    //C0
    public byte[] assistParam = new byte[2];//接口辅助入参
    //00
    public byte[] appType = new byte[2];//卡类型标识
    //02
    public byte[] commType = new byte[2];//命令类型标识
    //0AC8308A
    public byte[] encryptorip = new byte[8];//加密机IP地址
    //0058
    public byte[] encryptorPort = new byte[4];//加密机端口
    //00
    public byte[] keyVerstion = new byte[2];//密钥版本
    //0000100000000001
    public byte[] psamCardNo = new byte[16];//psam卡号
    //00
    public byte[] psamCardVersion = new byte[2];//SAM版本
    //00
    public byte[] psamCardType = new byte[2];//psam卡类型
    //01
    public byte[] keyIndex = new byte[2];//密钥索引号
    //4100073100000000
    public byte[] issuerId = new byte[16];//发行者标识
    //4100073100000000
    public byte[] receiverId = new byte[16];//接收者标识
    //20131001
    public byte[] startDate = new byte[8];//启用日期
    //20231001
    public byte[] validDate = new byte[8];//有效日期
    
    public byte paramEndSign = 0x00;//加密机版本
    
    public static class ByReference extends IssueInInf implements Structure.ByReference{
    }
    
    public static class ByValue extends IssueInInf implements Structure.ByValue{
    }
    
}
