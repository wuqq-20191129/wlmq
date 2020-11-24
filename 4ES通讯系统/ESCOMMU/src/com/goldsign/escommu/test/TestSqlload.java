/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.test;

import java.io.*;

/**
 *
 * @author lenovo
 */
public class TestSqlload {

    public static void main(String[] args) throws IOException{
        String ctl = "D:/testload/ctl/a2.ctl";
        System.out.println(ctl);
        String data = "D:/testload/data/a.es";
        String log = " log=D:/testload/a23.log ";
        String cmd = "sqlldr userid=acc_practice/acc_practice@epay1 control="+ctl+log+" data="+data;
        Process p = Runtime.getRuntime().exec(cmd, null);
        InputStream is =  p.getErrorStream();
        InputStreamReader bin = new InputStreamReader(is);
        BufferedReader r = new BufferedReader(bin);
        String line;
        while ((line = r.readLine()) != null) {
            System.out.println("here:"+new String(line.getBytes("ISO8859_1"),"GBK"));
        }
        p.destroy();
    }
}
