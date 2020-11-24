/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.test;

import com.goldsign.rwcommu.connection.RWSerialConnection;
import com.goldsign.rwcommu.exception.SerialException;
import com.goldsign.rwcommu.util.Converter;
import java.io.IOException;

/**
 *
 * @author lenovo
 */
public class TestRwSerial {
    
    private static RWSerialConnection serialConnection = null;
    
    static{
        serialConnection = new RWSerialConnection("COM7");
    }

    public static void main(String[] args) throws IOException, SerialException {
        serialConnection.open();
        //getVerion();
        getSamCard();
        serialConnection.close();
    }

    //取版本号
    private static void getVerion() throws IOException, SerialException {
        byte command = (byte)0x01;
        byte[] bytesRet = serialConnection.callSerial(command, null);
        
        //00 00 00 00 00 13 00 00 06 20 13 07 16 01 02 20 13 07 06 01 01 20 13 07 06 01 00 00
        System.out.println(Converter.bytesToHexString(bytesRet));
    }
    
    //取SAM卡号
    private static void getSamCard() throws IOException, SerialException {
        byte command = (byte) 0x02;
        byte[] bytesRet = serialConnection.callSerial(command, null);

        //00 00 00 00 00 13 00 00 06 20 13 07 16 01 02 20 13 07 06 01 01 20 13 07 06 01 00 00
        System.out.println(Converter.bytesToHexString(bytesRet));
    }
}
