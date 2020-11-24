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
public class BoxSensor extends Structure{
    
    public byte S1;  //票箱升降满检测--未满,满
    public byte S2;  //票箱升降将满检测--未在此位置,票箱将满
    public byte S3;  //票箱上限位检测--未到达,到达
    public byte S4;  //票箱进票空间检测--有空间进票,无空间进票
    public byte S5;  //票箱锁检测--打开,未打开
    public byte S6;  //票箱票箱盖检测--未打开,打开

    public static class ByReference extends BoxSensor implements Structure.ByReference{
    }

    public static class ByValue extends BoxSensor implements Structure.ByValue{
    }

}
