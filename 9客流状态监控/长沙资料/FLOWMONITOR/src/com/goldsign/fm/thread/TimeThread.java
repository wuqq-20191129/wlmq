/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.fm.thread;

import com.goldsign.fm.common.DateHelper;

import javax.swing.JLabel;

/**
 *
 * @author Administrator
 */
public class TimeThread extends Thread {

    JLabel jLabelTime;

    public TimeThread(JLabel jLabelTime) {
        this.jLabelTime = jLabelTime;
    }

    public void run() {

        try {
            while (true) {
                String current = DateHelper.getCurrentStringDatetime();
                this.jLabelTime.setText(current);
                TimeThread.sleep(1000);
            }
        } catch (InterruptedException ex) {
        }
    }
}
