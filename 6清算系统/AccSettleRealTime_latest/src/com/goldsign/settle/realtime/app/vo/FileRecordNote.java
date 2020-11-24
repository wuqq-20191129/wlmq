/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

import com.goldsign.settle.realtime.frame.vo.FileRecordBase;

/**
 *
 * @author hejj
 */
public class FileRecordNote extends FileRecordBase {

    /**
     * @return the putDatetime_s
     */
    public String getPutDatetime_s() {
        return putDatetime_s;
    }

    /**
     * @param putDatetime_s the putDatetime_s to set
     */
    public void setPutDatetime_s(String putDatetime_s) {
        this.putDatetime_s = putDatetime_s;
    }

    /**
     * @return the popDatetime_s
     */
    public String getPopDatetime_s() {
        return popDatetime_s;
    }

    /**
     * @param popDatetime_s the popDatetime_s to set
     */
    public void setPopDatetime_s(String popDatetime_s) {
        this.popDatetime_s = popDatetime_s;
    }

    private int noteBoxType;//纸币箱类型
    private String noteBoxId;//纸币箱编号
    private String operatorId;//操作员的员工号
    private String putDatetime;//存入时间
    private String popDatetime;//取出时间
     private String putDatetime_s;//存入时间
    private String popDatetime_s;//取出时间
    private int waterNoOp;//流水号

    /**
     * @return the noteBoxType
     */
    public int getNoteBoxType() {
        return noteBoxType;
    }

    /**
     * @param noteBoxType the noteBoxType to set
     */
    public void setNoteBoxType(int noteBoxType) {
        this.noteBoxType = noteBoxType;
    }

    /**
     * @return the noteBoxId
     */
    public String getNoteBoxId() {
        return noteBoxId;
    }

    /**
     * @param noteBoxId the noteBoxId to set
     */
    public void setNoteBoxId(String noteBoxId) {
        this.noteBoxId = noteBoxId;
    }

    /**
     * @return the operatorId
     */
    public String getOperatorId() {
        return operatorId;
    }

    /**
     * @param operatorId the operatorId to set
     */
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * @return the putDatetime
     */
    public String getPutDatetime() {
        return putDatetime;
    }

    /**
     * @param putDatetime the putDatetime to set
     */
    public void setPutDatetime(String putDatetime) {
        this.putDatetime = putDatetime;
    }

    /**
     * @return the popDatetime
     */
    public String getPopDatetime() {
        return popDatetime;
    }

    /**
     * @param popDatetime the popDatetime to set
     */
    public void setPopDatetime(String popDatetime) {
        this.popDatetime = popDatetime;
    }

    /**
     * @return the waterNoOp
     */
    public int getWaterNoOp() {
        return waterNoOp;
    }

    /**
     * @param waterNoOp the waterNoOp to set
     */
    public void setWaterNoOp(int waterNoOp) {
        this.waterNoOp = waterNoOp;
    }
}
