/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.test;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lenovo
 */
public class Thread02 extends Thread{

    private int i = 10;
    
    @Override
    public void run() {
        
        
        
        synchronized(TestThread.LOCK){
            while(true){
                if(i==20){
                    TestThread.LOCK.notifyAll();
                    i = 10;
                    try {
                        TestThread.LOCK.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Thread01.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    System.out.println("thread02:" + (i++));
                }
            }
        }
    }
}
