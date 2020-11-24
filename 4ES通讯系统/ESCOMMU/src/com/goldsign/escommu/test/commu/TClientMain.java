/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.test.commu;

/**
 *
 * @author lenovo
 */
public class TClientMain {

    
    public static void main(String[] args){
        
        TSocketClient socketClient = new TSocketClient();
        socketClient.connect();
        while(true){
            continue;
        }
    }
    
}
