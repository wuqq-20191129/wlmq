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
public class BoxInf extends Structure{
    
    public byte Box_Style;			//票箱形式：0：开放式,1：封闭式							
    public byte Box_Full_State;		//票箱装票状态：(注：此状态只在运行状态下有效)0：正常,1：将满,2：满
    public byte Box_Run_State;		/*票箱运行状态：
                                                            0-无票箱
                                                            1-票箱复位
                                                            2-已有票箱未上锁(暂时未用得)
                                                            3-正在安装票箱
                                                            4-票箱已安装，处于运行状态(状态OK)
                                                            5-腾出接票空间(状态OK)
                                                            6-正在拆卸票箱				
                                                            7-票箱错误(有两种，a：复位时传感器条件(即：未在底但票箱锁未归零，b：腾出票箱空间时，超时出错))
                                                            8-拆卸票箱结束
                                                            9-封闭式票箱的票箱盖传感器异常
                                                            10-票箱发生报警
                                                            11-复位时所要执行的条件不满足
                                                    */
    //uchar Box_Run_Mode;			//当前运行模式
    //票箱传感器状态：	
    public byte Box_Reserve1;		//Bit7-保留，一般填充为0
    public byte Box_Reserve2;		//Bit6-保留，一般填充为0
    public byte Box_GateOpen;		//Bit5-票箱盖打开/关闭(1/0)检测(开放式票箱常1)
    public byte Box_KeyOpen;		//Bit4-票箱锁/开(0/1)检测
    public byte Box_IsSpace;		//Bit3-票箱接票面空间有/无(0/1)检测
    public byte Box_CheckDownUp;            //Bit2-票箱升降安全检测
    public byte Box_CheckWillFull;          //Bit1-票箱将满(1)传感器检测
    public byte Box_CheckFull;              //Bit0-票箱满(1)检测

    public AlermInf.ByValue Inf = new AlermInf.ByValue();                    //票箱报警信息
    
    public static class ByReference extends BoxInf implements Structure.ByReference{
    }
    
    public static class ByValue extends BoxInf implements Structure.ByValue{
    }
}
