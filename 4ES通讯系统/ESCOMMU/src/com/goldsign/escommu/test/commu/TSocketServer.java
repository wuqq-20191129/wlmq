/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.test.commu;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lenovo
 */
public class TSocketServer {

    public void startServer(){
        
        int port = 9999;
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        TConnCommu clientServerCommu = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("启动服务...");
            clientSocket = serverSocket.accept();
            System.out.println("连接成功...");
            clientServerCommu = new TConnCommu(clientSocket);
            new Thread(clientServerCommu).start();
        } catch (IOException ex) {
            Logger.getLogger(TSocketServer.class.getName()).log(Level.SEVERE, "启动SOCKET服务出错！", ex);
        }
    }
}
