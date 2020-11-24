/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.test.commu;

/**
 *
 * @author lenovo
 */
public class TServerMain {

    public static void main(String[] args){
        
        TSocketServer socketServer = new TSocketServer();
        socketServer.startServer();
        
    }
}
