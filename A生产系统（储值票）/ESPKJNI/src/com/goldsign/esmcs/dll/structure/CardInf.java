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
public class CardInf extends Structure{
    
    public byte CurrSite;	//当前工位
    public byte TagSite;	//目标工位
    public int CardNo;         //逻辑卡号
    public byte State;         //当前状态 0:空闲（没卡时候）  1:成功发出卡片（在线上） 2:卡回收成功 3:误收（没用）  4:越位（没用）     
    public byte RecyBox;       //回收卡箱（真正票箱序号）
    
    public static class ByReference extends CardInf implements Structure.ByReference{
    }

    public static class ByValue extends CardInf implements Structure.ByValue{
    }

}
