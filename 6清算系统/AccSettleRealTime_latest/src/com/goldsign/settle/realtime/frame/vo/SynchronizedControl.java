/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.vo;

import java.util.Vector;

/**
 *
 * @author hejj
 */
public class SynchronizedControl {

    private static Vector CONTROL = new Vector();
    private int allowMax = 1;
    private int currentSyn = 0;
    private boolean isFinished = false;

    public SynchronizedControl() {
    }

    public void plusCurrentSyn() {
        synchronized (CONTROL) {
            this.currentSyn = this.currentSyn + 1;
        }
    }

    public void subCurrentSyn() {
        synchronized (CONTROL) {
            this.currentSyn = this.currentSyn - 1;
        }
    }

    public boolean isNeedSyn() {
        synchronized (CONTROL) {
           // if (this.currentSyn > allowMax) {
             if (this.currentSyn %2 ==0) {
                return true;
            }
            return false;
        }
    }

    public SynchronizedControl(int allowMax) {
        this.allowMax = allowMax;

    }

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public boolean getFinished() {
        return this.isFinished;
    }

    /**
     * @return the allowMax
     */
    public int getAllowMax() {
        return allowMax;
    }

    /**
     * @param allowMax the allowMax to set
     */
    public void setAllowMax(int allowMax) {
        this.allowMax = allowMax;
    }

    /**
     * @return the currentSyn
     */
    public int getCurrentSyn() {
        synchronized (CONTROL) {
            return currentSyn;
        }
    }

    /**
     * @param currentSyn the currentSyn to set
     */
    public void setCurrentSyn(int currentSyn) {
        this.currentSyn = currentSyn;
    }
}
