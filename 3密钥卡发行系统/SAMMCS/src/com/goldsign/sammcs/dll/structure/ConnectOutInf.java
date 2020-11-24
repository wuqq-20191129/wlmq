/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.sammcs.dll.structure;

import com.sun.jna.Structure;

/**
 *
 * @author Administrator
 */
public class ConnectOutInf extends Structure {
    public byte[] treatyVersions = new byte[2];//接口协议版本
    
    public byte[] response = new byte[2];//执行响应
    
    public byte[] cardType = new byte[2];//卡类标识
    
    public byte[] commType = new byte[2];//命令类型标识
    
    public static class ByReference extends ConnectOutInf implements Structure.ByReference{
    }
    
    public static class ByValue extends ConnectOutInf implements Structure.ByValue{
    }
    
}
