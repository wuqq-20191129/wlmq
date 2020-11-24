/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.test.function;

import com.goldsign.settle.realtime.frame.util.CrcUtil;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

/**
 *
 * @author hejj
 */
public class ByteArrayConvert {
    public void byteToChar(){
        byte b = -43;
        char c=(char)b;
        System.out.println((int)c);
        
    }
     public void intToChar(){
        int b = 0xd5;
        char c=(char)b;
        System.out.println((int)c);
        
    }
    public void charToByte(){
        char c =200;
        byte b =(byte)c;
        System.out.println(b);
        
    }
    public void StringBufTochar(){
        StringBuffer sb = new StringBuffer();
        sb.append((char)0xD5);
        sb.append((char)0xC3);
        char[] cs =sb.toString().toCharArray();
        
    }
    public static void main(String[] args) {
        ByteArrayConvert con = new ByteArrayConvert();
        //con.byteToChar();
       // con.intToChar();
       con.StringBufTochar();;
        
       
    }
    
}
