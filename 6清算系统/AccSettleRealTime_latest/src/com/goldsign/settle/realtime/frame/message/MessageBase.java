/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.message;

import com.goldsign.settle.realtime.frame.vo.MessageAttribute;
import com.goldsign.settle.realtime.frame.vo.SynchronizedControl;
import com.goldsign.settle.realtime.frame.vo.TaskFinishControl;

/**
 *
 * @author hejj
 */
public class MessageBase {

    /**
     * @return the balanceWaterNoSub
     */
    public int getBalanceWaterNoSub() {
        return balanceWaterNoSub;
    }

    /**
     * @param balanceWaterNoSub the balanceWaterNoSub to set
     */
    public void setBalanceWaterNoSub(int balanceWaterNoSub) {
        this.balanceWaterNoSub = balanceWaterNoSub;
    }

    protected String path;
    protected String pathBcp;
    protected String fileName;
    private String fileType;
    protected String bcpFileName;
    protected String tradType;
    private String balanceWaterNo;
    private int balanceWaterNoSub;
    private String pathHis;
    private String pathHisError;
    private boolean finished = false;
    private final SynchronizedControl syn = new SynchronizedControl();
    private TaskFinishControl tfc;
    
    private MessageAttribute messageAttribute;
    
    

    public MessageBase() {
    }


    public MessageBase(String path, String pathBcp, String pathHis, String pathHisError, 
                         String fileName, String balanceWaterNo,int balanceWaterNoSub,TaskFinishControl tfc,MessageAttribute messageAttibute) {
        this.path = path;
        this.fileName = fileName;
        this.pathBcp = pathBcp;
        this.pathHis = pathHis;
        this.pathHisError = pathHisError;
        this.balanceWaterNo = balanceWaterNo;
        this.balanceWaterNoSub=balanceWaterNoSub;
        this.tfc=tfc;
        this.messageAttribute=messageAttibute;
    }

    //protected byte[] data;
    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the pathBcp
     */
    public String getPathBcp() {
        return pathBcp;
    }

    /**
     * @param pathBcp the pathBcp to set
     */
    public void setPathBcp(String pathBcp) {
        this.pathBcp = pathBcp;
    }

    /**
     * @return the bcpFileName
     */
    public String getBcpFileName() {
        return bcpFileName;
    }

    /**
     * @param bcpFileName the bcpFileName to set
     */
    public void setBcpFileName(String bcpFileName) {
        this.bcpFileName = bcpFileName;
    }

    /**
     * @return the tradType
     */
    public String getTradType() {
        return tradType;
    }

    /**
     * @param tradType the tradType to set
     */
    public void setTradType(String tradType) {
        this.tradType = tradType;
    }

    /**
     * @return the balanceWaterNo
     */
    public String getBalanceWaterNo() {
        return balanceWaterNo;
    }

    /**
     * @param balanceWaterNo the balanceWaterNo to set
     */
    public void setBalanceWaterNo(String balanceWaterNo) {
        this.balanceWaterNo = balanceWaterNo;
    }

    /**
     * @return the pathHis
     */
    public String getPathHis() {
        return pathHis;
    }

    /**
     * @param pathHis the pathHis to set
     */
    public void setPathHis(String pathHis) {
        this.pathHis = pathHis;
    }

    /**
     * @return the pathHisError
     */
    public String getPathHisError() {
        return pathHisError;
    }

    /**
     * @param pathHisError the pathHisError to set
     */
    public void setPathHisError(String pathHisError) {
        this.pathHisError = pathHisError;
    }

    /**
     * @return the fileType
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * @param fileType the fileType to set
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * @return the finished
     */
    public boolean isFinished() {
        synchronized (this.syn) {
            return finished;
        }
    }

    /**
     * @param finished the finished to set
     */
    public void setFinished(boolean finished) {
        synchronized (this.syn) {
            this.finished = finished;
        }
    }

    /**
     * @return the tfc
     */
    public TaskFinishControl getTfc() {
        return tfc;
    }

    /**
     * @param tfc the tfc to set
     */
    public void setTfc(TaskFinishControl tfc) {
        this.tfc = tfc;
    }

    /**
     * @return the messageAttribute
     */
    public MessageAttribute getMessageAttribute() {
        return messageAttribute;
    }

    /**
     * @param messageAttribute the messageAttribute to set
     */
    public void setMessageAttribute(MessageAttribute messageAttribute) {
        this.messageAttribute = messageAttribute;
    }
}
