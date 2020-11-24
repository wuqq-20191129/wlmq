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
public class READERVERSION extends Structure{
    
    public byte[] cAPIVersion = new byte[8];				// API版本信息XX.XX.XX
    public byte[] cFirmwareVersion = new byte[8];			// Firmware版本信息XX.XX.XX

    public static class ByReference extends READERVERSION implements Structure.ByReference {
    }

    public static class ByValue extends READERVERSION implements Structure.ByValue {
    }
}
