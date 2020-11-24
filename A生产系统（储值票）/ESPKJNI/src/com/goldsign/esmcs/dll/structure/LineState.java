/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.dll.structure;

import com.sun.jna.Structure;
import java.util.List;

/**
 *
 * @author lenovo
 */
public class LineState extends Structure{
    
    public byte Reader1;	//读写器1位置卡片运行状态（发卡卡位）
    public byte Reader2;	//读写器2位置卡片运行状态
    public byte Box1;		//票箱1位置卡片运行状态
    public byte Box2;		//票箱2位置卡片运行状态
    public byte Box3;		//票箱3位置卡片运行状态
    public byte Box4;		//票箱4位置卡片运行状态
    public byte Box5;		//票箱5位置卡片运行状态
    public byte Box6;		//票箱6位置卡片运行状态
    public byte Box7;		//票箱7位置卡片运行状态
    public byte Box8;		//票箱8位置卡片运行状态
    public byte Box9;		//票箱9位置卡片运行状态
    public byte Box10;	//票箱10位置卡片运行状态
    public byte Box11;	//票箱11位置卡片运行状态
    public byte Box12;	//票箱12位置卡片运行状态

    public static class ByReference extends LineState implements Structure.ByReference{
    }

    public static class ByValue extends LineState implements Structure.ByValue{
    }

}
