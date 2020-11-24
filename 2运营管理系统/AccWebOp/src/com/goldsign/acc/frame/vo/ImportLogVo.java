/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.vo;

/**
 *
 * @author mh
 */
public class ImportLogVo {
    private String waterNo ;
	private String operator ;
        private String operatorText ; 
	private String opTime  ;
	private int opType  ;
	private String tableName ;
	private Integer recordCount ;
	private String versionNo;
	private String remark;
        
        private String fileName="";
        
        private String q_start_time ;
        private String q_end_time ;
        
        private String  begin_logical_id="";
        private String  end_logical_id="";
        
	public ImportLogVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOpTime() {
		return opTime;
	}

	public void setOpTime(String opTime) {
		this.opTime = opTime;
	}

	public int getOpType() {
		return opType;
	}

	public void setOpType(int opType) {
		this.opType = opType;
	}


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String getWaterNo() {
		return waterNo;
	}

	public void setWaterNo(String waterNo) {
		this.waterNo = waterNo;
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
     * @return the operatorText
     */
    public String getOperatorText() {
        return operatorText;
    }

    /**
     * @param operatorText the operatorText to set
     */
    public void setOperatorText(String operatorText) {
        this.operatorText = operatorText;
    }

    /**
     * @return the q_start_time
     */
    public String getQ_start_time() {
        return q_start_time;
    }

    /**
     * @param q_start_time the q_start_time to set
     */
    public void setQ_start_time(String q_start_time) {
        this.q_start_time = q_start_time;
    }

    /**
     * @return the q_end_time
     */
    public String getQ_end_time() {
        return q_end_time;
    }

    /**
     * @param q_end_time the q_end_time to set
     */
    public void setQ_end_time(String q_end_time) {
        this.q_end_time = q_end_time;
    }

    /**
     * @return the recordCount
     */
    public Integer getRecordCount() {
        return recordCount;
    }

    /**
     * @param recordCount the recordCount to set
     */
    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }

    /**
     * @return the begin_logical_id
     */
    public String getBegin_logical_id() {
        return begin_logical_id;
    }

    /**
     * @param begin_logical_id the begin_logical_id to set
     */
    public void setBegin_logical_id(String begin_logical_id) {
        this.begin_logical_id = begin_logical_id;
    }

    /**
     * @return the end_logical_id
     */
    public String getEnd_logical_id() {
        return end_logical_id;
    }

    /**
     * @param end_logical_id the end_logical_id to set
     */
    public void setEnd_logical_id(String end_logical_id) {
        this.end_logical_id = end_logical_id;
    }

}

