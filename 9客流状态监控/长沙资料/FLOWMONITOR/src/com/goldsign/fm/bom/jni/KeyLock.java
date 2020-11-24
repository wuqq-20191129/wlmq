/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.fm.bom.jni;

import com.goldsign.fm.common.AppConstant;
import com.goldsign.fm.jni.KeyLockThread;

/**
 *
 * @author Administrator
 */
public class KeyLock {

    public KeyLock() {
    }

    public native static boolean DisableTaskKeys(boolean bDisable, boolean bBeep);

    public void startKeyLock() {
        //测试方便屏蔽
        if (AppConstant.isLocked) {
            //System.loadLibrary("keylock");
            KeyLockThread klt = new KeyLockThread();
            klt.start();
        }


    }

    public void disableKeyLock() {
        try {
            if (AppConstant.isLocked) {
                KeyLock.DisableTaskKeys(false, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //   System.loadLibrary("TaskKeyHook");
        System.out.println(System.getProperty("java.library.path"));
        System.loadLibrary("keylock");
         KeyLock kl = new KeyLock();
          boolean result = KeyLock.DisableTaskKeys(true,true);
       // KeyLockThread klt = new KeyLockThread();
        //klt.start();



          System.out.print("success call result=" +result);
        /*
        while(true){
        try {
        Thread.sleep(10000);
        System.out.println("tread is alive");
        } catch (InterruptedException ex) {
        ex.printStackTrace();
        }
        }
         */
    }
}
