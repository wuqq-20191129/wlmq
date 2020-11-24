/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.fm.jni;

import com.goldsign.fm.bom.jni.KeyLock;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class KeyLockThread extends Thread{
     private static Logger logger = Logger.getLogger(KeyLockThread.class);

    /** Creates a new instance of KeyLockThread */
    public KeyLockThread() {
    }


    public void run(){
        KeyLock kl = new KeyLock();
        try {
            System.loadLibrary("keylock");
            Thread.sleep(2000);
             kl.DisableTaskKeys(true,true);
          //   this.setDialogOnTop();

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }


    }

}
