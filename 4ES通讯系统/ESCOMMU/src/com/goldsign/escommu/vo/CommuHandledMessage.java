package com.goldsign.escommu.vo;

import java.util.Vector;

public class CommuHandledMessage {

    private String ip = null;
    private Vector readResult = null;
    private BridgeBetweenConnectionAndMessage bridge = null;

    public CommuHandledMessage(String ip, Vector readResult) {
        this.ip = ip;
        this.readResult = readResult;

    }

    public CommuHandledMessage(String ip, Vector readResult, BridgeBetweenConnectionAndMessage bridge) {
        this.ip = ip;
        this.readResult = readResult;
        this.bridge = bridge;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Vector getReadResult() {
        return this.readResult;
    }

    public void setReadResult(Vector readResult) {
        this.readResult = readResult;
    }

    /**
     * @return the bridge
     */
    public BridgeBetweenConnectionAndMessage getBridge() {
        return bridge;
    }

    /**
     * @param bridge the bridge to set
     */
    public void setBridge(BridgeBetweenConnectionAndMessage bridge) {
        this.bridge = bridge;
    }
}
