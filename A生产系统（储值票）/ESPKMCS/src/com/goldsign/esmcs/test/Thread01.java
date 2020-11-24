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
public class Thread01 extends Thread{

    private int i = 0;
    @Override
    public void run() {
        
        System.out.println("Thread01: ");
        while(true){
            
            synchronized(TestThread.LOCK){
                System.out.println("Thread01: wait!");
                try {
                    TestThread.LOCK.wait();
                    System.out.println("Thread01: wait after!");
                } catch (InterruptedException ex) {
                    Logger.getLogger(Thread01.class.getName()).log(Level.SEVERE, null, ex);
                }   
            }    
            System.out.println("Thread01: waitup!");
            try {
                System.out.println("Thread01: dosome!");
                Thread.sleep(1000);
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Thread01.class.getName()).log(Level.SEVERE, null, ex);
            }    
            System.out.println("Thread01: doend");
            synchronized(TestThread.LOCK){
                TestThread.LOCK.notifyAll();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Thread01.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Thread01: notifyAll");
            }    
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Thread01.class.getName()).log(Level.SEVERE, null, ex);
            }
        }     
            /*
            synchronized(TestThread.LOCK){
                while(true){
                    if(i==10000){
                        TestThread.LOCK.notifyAll();
                        
                        i = 0;
                        
                        try {
                            TestThread.LOCK.wait();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Thread01.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else{
                        System.out.println("thread01:" + (i++));
                    }
                }
            }*/
        
    }

}
