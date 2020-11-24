package com.goldsign.escommu.vo;

public class SynchronizedControl {

    private boolean isFinished = false;

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public boolean getFinished() {
        return this.isFinished;
    }
}
