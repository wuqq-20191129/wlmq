/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.dll.structure;

import com.sun.jna.Structure;

/**
 *
 * @author lenovo
 */
public class SAMSTATUS extends Structure{
    public byte bSAMStatus;    // SAM卡状态–(0x00表示正常，其他-后续字段无效)
    public byte[] cSAMID = new byte[16];    // SAM卡ID号

    public static class ByReference extends SAMSTATUS implements Structure.ByReference {
    }

    public static class ByValue extends SAMSTATUS implements Structure.ByValue {
    }
}
