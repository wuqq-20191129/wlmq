/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.test.function;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hejj
 */
public class ChineseUtil {

    public static void main(String[] arg) {
        String str;
        try {
           // str = new String("张力".getBytes("gbk"), "gbk");
            str ="张力";

            //String str = "ab";
            char[] cs = str.toCharArray();
            byte[] bs = str.getBytes("gbk");
            System.out.println("len=" + cs.length);
             System.out.println("blen=" + bs.length);
            int i;
            for (char c : cs) {
               
                System.out.print(c + " ");
                System.out.print((int)c + " ");
            }
            for (byte b : bs) {
               
                System.out.print(b + " ");
            }
        } catch (Exception ex) {
            Logger.getLogger(ChineseUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
