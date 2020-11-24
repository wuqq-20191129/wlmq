/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.vo;

import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author hejj
 */
public class SocketAttribute {

    private int timeout;
    private String server;
    private int port;
    private InputStream in;
    private OutputStream out;

    public SocketAttribute(String server,int port,int timeout) {
        this.server = server;
        this.port =port;
        this.timeout =timeout;
    }

    /**
     * @return the timeout
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * @param timeout the timeout to set
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * @return the server
     */
    public String getServer() {
        return server;
    }

    /**
     * @param server the server to set
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the in
     */
    public InputStream getIn() {
        return in;
    }

    /**
     * @param in the in to set
     */
    public void setIn(InputStream in) {
        this.in = in;
    }

    /**
     * @return the out
     */
    public OutputStream getOut() {
        return out;
    }

    /**
     * @param out the out to set
     */
    public void setOut(OutputStream out) {
        this.out = out;
    }
}
