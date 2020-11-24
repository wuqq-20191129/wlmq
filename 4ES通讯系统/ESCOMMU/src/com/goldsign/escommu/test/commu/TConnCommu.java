/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.test.commu;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lenovo
 */
public class TConnCommu implements Runnable{

    private Socket clientSocket = null;
    
    private InputStream is = null;
    
    private OutputStream os = null;
        
    public TConnCommu(Socket socket){
        this.clientSocket = socket;
        try {
            is = socket.getInputStream();
            os = socket.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(TConnCommu.class.getName()).log(Level.SEVERE, "打开输入输出流出错！", ex);
        }
    }

    @Override
    public void run() {
        
        while(true){
        
            System.out.println("开始读...");
            testRead();
            System.out.println("结束读...");
        }
        
    }
    
    public void testRead(){
        try {
            is.read();
        } catch (IOException ex) {
            Logger.getLogger(TConnCommu.class.getName()).log(Level.SEVERE, "读数据出错！", ex);
        }
    }
}
