/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.rw.dll.structure;

import com.sun.jna.Structure;

/**
 *
 * @author lenovo
 */
public class READERSTATUS extends Structure{
    
    public byte bReaderReadyStatus;			// 读写器就绪状态
    public SAMSTATUS[]  SamStatus = new SAMSTATUS[8];	// 各SAM卡状态信息

    public static class ByReference extends READERSTATUS implements Structure.ByReference {
    }

    public static class ByValue extends READERSTATUS implements Structure.ByValue {
    }
}
