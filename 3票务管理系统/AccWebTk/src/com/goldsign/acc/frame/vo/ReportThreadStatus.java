/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.vo;

/**
 *
 * @author hejj
 */
public class ReportThreadStatus {
    public ReportThreadStatus() {
        super();
        // TODO Auto-generated constructor stub
    }
    private boolean isFinished = false;
    private Exception runException = null;

    public boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public Exception getRunException() {
        return runException;
    }

    public void setRunException(Exception runException) {
        this.runException = runException;
    }
    
}
