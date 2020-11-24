/*
 * 文件名：OutInf
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.sammcs.dll.structure;

import com.sun.jna.Structure;


/*
 * PSAM接口方法psam_issue_dev_op 出参 结构体
 * @author     lindaquan
 * @version    V1.0
 */

public class IssueOutInf extends Structure {

    public byte[] treatyVersions = new byte[2];//接口协议版本
    
    public byte[] response = new byte[2];//执行响应
    
    public byte[] cardType = new byte[2];//卡类标识
    
    public byte[] commType = new byte[2];//命令类型标识
    
    public byte[] resultData = new byte[16];//响应数据(psam芯片号)
    
    //20160126 add by mqf 
    public byte[] cardProducerCode = new byte[4];//响应数据(psam制作厂商编码)
    
    public static class ByReference extends IssueOutInf implements Structure.ByReference{
    }
    
    public static class ByValue extends IssueOutInf implements Structure.ByValue{
    }
    
}
