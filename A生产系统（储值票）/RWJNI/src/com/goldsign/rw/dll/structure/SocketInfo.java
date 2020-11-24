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
public class SocketInfo extends Structure{
    
    public int iTmp;
    public byte[] strBuf = new byte[1024];

    public static class ByReference extends SocketInfo implements Structure.ByReference {
    }

    public static class ByValue extends SocketInfo implements Structure.ByValue {
        
    }
}
