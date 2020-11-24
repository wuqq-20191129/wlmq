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
public class AlermInf extends Structure{
    
    public byte ErrorCode;          //错误代码,02-步进电机运行超时报警,03-传感器信号异常
    public byte BoxStateORSensorNo;	//ErrorCode=02 票箱运行状态, ErrorCode=03错误位置的传感器编号
    public byte ErrorBoxNo;		//发生错误的票箱固定模块号
    

    public static class ByReference extends AlermInf implements Structure.ByReference {
    }

    public static class ByValue extends AlermInf implements Structure.ByValue {
    }
}
