/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.vo;

/**
 *
 * @author hejj
 */
public class ThreadAttrVo {
    private  int threadSleepTime = 100;
    private  int threadPriority = 5;
    private  int maxThreadNumber = 5;
    private  int maxSearchNum = 2;
    private  int readThreadPriorityAdd = 0;
    private  String unHanledMsgLogDir = "";
    private  int threadBufferCapacity = 1000;
    private  int threadBufferIncrement = 1000;
    private  int priorityThreadBufferCapacity = 1000;
    private  int priorityThreadBufferIncrement = 1000;
    private String msgHandleClass="";
    private String threadPoolId="";
    private String threadPoolName="";
    
    private   int nextThread = 0;
    private  int nextThreadSeq = 0;
    
    private String msgHandleClassPrifix="";

    /**
     * @return the threadSleepTime
     */
    public int getThreadSleepTime() {
        return threadSleepTime;
    }

    /**
     * @param threadSleepTime the threadSleepTime to set
     */
    public void setThreadSleepTime(int threadSleepTime) {
        this.threadSleepTime = threadSleepTime;
    }

    /**
     * @return the threadPriority
     */
    public int getThreadPriority() {
        return threadPriority;
    }

    /**
     * @param threadPriority the threadPriority to set
     */
    public void setThreadPriority(int threadPriority) {
        this.threadPriority = threadPriority;
    }

    /**
     * @return the maxThreadNumber
     */
    public int getMaxThreadNumber() {
        return maxThreadNumber;
    }

    /**
     * @param maxThreadNumber the maxThreadNumber to set
     */
    public void setMaxThreadNumber(int maxThreadNumber) {
        this.maxThreadNumber = maxThreadNumber;
    }

    /**
     * @return the maxSearchNum
     */
    public int getMaxSearchNum() {
        return maxSearchNum;
    }

    /**
     * @param maxSearchNum the maxSearchNum to set
     */
    public void setMaxSearchNum(int maxSearchNum) {
        this.maxSearchNum = maxSearchNum;
    }

    /**
     * @return the readThreadPriorityAdd
     */
    public int getReadThreadPriorityAdd() {
        return readThreadPriorityAdd;
    }

    /**
     * @param readThreadPriorityAdd the readThreadPriorityAdd to set
     */
    public void setReadThreadPriorityAdd(int readThreadPriorityAdd) {
        this.readThreadPriorityAdd = readThreadPriorityAdd;
    }

    /**
     * @return the unHanledMsgLogDir
     */
    public String getUnHanledMsgLogDir() {
        return unHanledMsgLogDir;
    }

    /**
     * @param unHanledMsgLogDir the unHanledMsgLogDir to set
     */
    public void setUnHanledMsgLogDir(String unHanledMsgLogDir) {
        this.unHanledMsgLogDir = unHanledMsgLogDir;
    }

    /**
     * @return the threadBufferCapacity
     */
    public int getThreadBufferCapacity() {
        return threadBufferCapacity;
    }

    /**
     * @param threadBufferCapacity the threadBufferCapacity to set
     */
    public void setThreadBufferCapacity(int threadBufferCapacity) {
        this.threadBufferCapacity = threadBufferCapacity;
    }

    /**
     * @return the threadBufferIncrement
     */
    public int getThreadBufferIncrement() {
        return threadBufferIncrement;
    }

    /**
     * @param threadBufferIncrement the threadBufferIncrement to set
     */
    public void setThreadBufferIncrement(int threadBufferIncrement) {
        this.threadBufferIncrement = threadBufferIncrement;
    }

    /**
     * @return the priorityThreadBufferCapacity
     */
    public int getPriorityThreadBufferCapacity() {
        return priorityThreadBufferCapacity;
    }

    /**
     * @param priorityThreadBufferCapacity the priorityThreadBufferCapacity to set
     */
    public void setPriorityThreadBufferCapacity(int priorityThreadBufferCapacity) {
        this.priorityThreadBufferCapacity = priorityThreadBufferCapacity;
    }

    /**
     * @return the priorityThreadBufferIncrement
     */
    public int getPriorityThreadBufferIncrement() {
        return priorityThreadBufferIncrement;
    }

    /**
     * @param priorityThreadBufferIncrement the priorityThreadBufferIncrement to set
     */
    public void setPriorityThreadBufferIncrement(int priorityThreadBufferIncrement) {
        this.priorityThreadBufferIncrement = priorityThreadBufferIncrement;
    }

    /**
     * @return the msgHandleClass
     */
    public String getMsgHandleClass() {
        return msgHandleClass;
    }

    /**
     * @param msgHandleClass the msgHandleClass to set
     */
    public void setMsgHandleClass(String msgHandleClass) {
        this.msgHandleClass = msgHandleClass;
    }

    /**
     * @return the threadPoolId
     */
    public String getThreadPoolId() {
        return threadPoolId;
    }

    /**
     * @param threadPoolId the threadPoolId to set
     */
    public void setThreadPoolId(String threadPoolId) {
        this.threadPoolId = threadPoolId;
    }

    /**
     * @return the threadPoolName
     */
    public String getThreadPoolName() {
        return threadPoolName;
    }

    /**
     * @param threadPoolName the threadPoolName to set
     */
    public void setThreadPoolName(String threadPoolName) {
        this.threadPoolName = threadPoolName;
    }

    /**
     * @return the nextThread
     */
    public int getNextThread() {
        return nextThread;
    }

    /**
     * @param nextThread the nextThread to set
     */
    public void setNextThread(int nextThread) {
        this.nextThread = nextThread;
    }

    /**
     * @return the nextThread_seq
     */
    public int getNextThreadSeq() {
        return nextThreadSeq;
    }

    /**
     * @param nextThread_seq the nextThread_seq to set
     */
    public void setNextThreadSeq(int nextThreadSeq) {
        this.nextThreadSeq = nextThreadSeq;
    }

    /**
     * @return the msgHandleClassPrifix
     */
    public String getMsgHandleClassPrifix() {
        return msgHandleClassPrifix;
    }

    /**
     * @param msgHandleClassPrifix the msgHandleClassPrifix to set
     */
    public void setMsgHandleClassPrifix(String msgHandleClassPrifix) {
        this.msgHandleClassPrifix = msgHandleClassPrifix;
    }
    
}
