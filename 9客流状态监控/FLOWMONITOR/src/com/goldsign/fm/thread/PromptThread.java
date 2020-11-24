/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.fm.thread;

import com.goldsign.fm.common.PubUtil;
import javax.swing.JDialog;

/**
 *
 * @author Administrator
 */
public class PromptThread extends Thread{
    JDialog dialog;
    public PromptThread(JDialog dialog){
       this.dialog = dialog; 
    }
    public void run(){
        PubUtil putil = new PubUtil();
        putil.makeContainerInScreenMiddle(dialog);
        dialog.setVisible(true);

    }

}
