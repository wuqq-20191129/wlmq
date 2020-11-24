/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.vo;

/**
 *
 * @author hejj
 */
public class TaskFinishControl {

    private final SynchronizedControl CONTROL = new SynchronizedControl();
    private boolean finished = false;

    /**
     * @return the finished
     */
    public boolean isFinished() {
        synchronized (CONTROL) {
            return finished;
        }
    }

    /**
     * @param finished the finished to set
     */
    public void setFinished(boolean finished) {
        synchronized (CONTROL) {
            this.finished = finished;
        }
    }
}
