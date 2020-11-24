/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.vo;

/**
 *
 * @author lenovo
 */
public class FtpLoginParamVo {

    private String ip;
    private int port;//limj
    
    private String userCode;
    
    private String pwd;

    public FtpLoginParamVo(){}
    
    public FtpLoginParamVo(String ip, String userCode, String pwd,int port) {
        this.ip = ip;
        this.port = port;//limj
        this.userCode = userCode;
        this.pwd = pwd;      
    }
    
    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }
    
        /**limj
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**limj
     * @param port the ip to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the userCode
     */
    public String getUserCode() {
        return userCode;
    }

    /**
     * @param userCode the userCode to set
     */
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    /**
     * @return the pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd the pwd to set
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
