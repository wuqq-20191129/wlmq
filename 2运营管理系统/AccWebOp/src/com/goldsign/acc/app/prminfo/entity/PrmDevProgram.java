package com.goldsign.acc.app.prminfo.entity;

import java.io.Serializable;

public class PrmDevProgram implements Serializable {
    private Long waterNo;

    private String parmTypeId;

    private String filePath;

    private String operatorId;

    private String versionNo;

    private String recordFlag;
    private String recordFlag1;

    public String getRecordFlag1() {
        return recordFlag1;
    }

    public void setRecordFlag1(String recordFlag1) {
        this.recordFlag1 = recordFlag1;
    }

    private String appLineName;

    private static final long serialVersionUID = 1L;

    public PrmDevProgram(Long waterNo, String parmTypeId, String filePath, String operatorId, String versionNo, String recordFlag, String appLineName) {
        this.waterNo = waterNo;
        this.parmTypeId = parmTypeId;
        this.filePath = filePath;
        this.operatorId = operatorId;
        this.versionNo = versionNo;
        this.recordFlag = recordFlag;
        this.appLineName = appLineName;
    }

    public PrmDevProgram() {
        super();
    }

    public Long getWaterNo() {
        return waterNo;
    }

    public void setWaterNo(Long waterNo) {
        this.waterNo = waterNo;
    }

    public String getParmTypeId() {
        return parmTypeId;
    }

    public void setParmTypeId(String parmTypeId) {
        this.parmTypeId = parmTypeId == null ? null : parmTypeId.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId == null ? null : operatorId.trim();
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo == null ? null : versionNo.trim();
    }

    public String getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(String recordFlag) {
        this.recordFlag = recordFlag == null ? null : recordFlag.trim();
    }

    public String getAppLineName() {
        return appLineName;
    }

    public void setAppLineName(String appLineName) {
        this.appLineName = appLineName == null ? null : appLineName.trim();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        PrmDevProgram other = (PrmDevProgram) that;
        return (this.getWaterNo() == null ? other.getWaterNo() == null : this.getWaterNo().equals(other.getWaterNo()))
            && (this.getParmTypeId() == null ? other.getParmTypeId() == null : this.getParmTypeId().equals(other.getParmTypeId()))
            && (this.getFilePath() == null ? other.getFilePath() == null : this.getFilePath().equals(other.getFilePath()))
            && (this.getOperatorId() == null ? other.getOperatorId() == null : this.getOperatorId().equals(other.getOperatorId()))
            && (this.getVersionNo() == null ? other.getVersionNo() == null : this.getVersionNo().equals(other.getVersionNo()))
            && (this.getRecordFlag() == null ? other.getRecordFlag() == null : this.getRecordFlag().equals(other.getRecordFlag()))
            && (this.getAppLineName() == null ? other.getAppLineName() == null : this.getAppLineName().equals(other.getAppLineName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getWaterNo() == null) ? 0 : getWaterNo().hashCode());
        result = prime * result + ((getParmTypeId() == null) ? 0 : getParmTypeId().hashCode());
        result = prime * result + ((getFilePath() == null) ? 0 : getFilePath().hashCode());
        result = prime * result + ((getOperatorId() == null) ? 0 : getOperatorId().hashCode());
        result = prime * result + ((getVersionNo() == null) ? 0 : getVersionNo().hashCode());
        result = prime * result + ((getRecordFlag() == null) ? 0 : getRecordFlag().hashCode());
        result = prime * result + ((getAppLineName() == null) ? 0 : getAppLineName().hashCode());
        return result;
    }
}