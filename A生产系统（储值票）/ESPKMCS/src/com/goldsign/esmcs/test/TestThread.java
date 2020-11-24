/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.test;

/**
 *
 * @author lenovo
 */
public class TestThread {

    
    public static Object LOCK = new Object();
    
    public static void main(String[] args) throws InterruptedException{
        
        Thread01 thread01 = new Thread01();
        thread01.start();
        
        while(true){
  
            Thread.sleep(100);
            System.out.println("Thread Main...doSome");
            Thread.sleep(1000);
            System.out.println("Thread Main...doend");
            synchronized(TestThread.LOCK){
                TestThread.LOCK.notifyAll();
                System.out.println("Thread Main...wait");
                TestThread.LOCK.wait();
                System.out.println("Thread Main...wait after");
            }
            System.out.println("Thread Main...waitup");
        }
    }
}
