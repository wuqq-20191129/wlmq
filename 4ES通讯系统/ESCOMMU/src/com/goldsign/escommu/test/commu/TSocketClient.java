/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.test.commu;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lenovo
 */
public class TSocketClient {
    
    private Socket socket = null;
    private OutputStream os = null;
    private InputStream is = null;

    public void connect(){
        
        String ip = "127.0.0.1";
        int port = 9999;
        try {
            socket = new Socket(ip, port);
            System.out.println("连接成功...");
        } catch (UnknownHostException ex) {
            Logger.getLogger(TSocketClient.class.getName()).log(Level.SEVERE, "连接出错！", ex);
        } catch (IOException ex) {
            Logger.getLogger(TSocketClient.class.getName()).log(Level.SEVERE, "连接出错！", ex);
        }
    }
    
    public void getSream(){
        try {
            is = socket.getInputStream();
            os = socket.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(TSocketClient.class.getName()).log(Level.SEVERE, "取输出流出错！", ex);
        }
        
    }
}
