package com.goldsign.rule.vo;

public class OperResult {
	private int updateNum;
	private Object updateOb;
	private String retMsg;
	private boolean isException =false;

	public OperResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getUpdateNum() {
		return updateNum;
	}

	public void setUpdateNum(int updateNum) {
		this.updateNum = updateNum;
	}

	public Object getUpdateOb() {
		return updateOb;
	}

	public void setUpdateOb(Object updateOb) {
		this.updateOb = updateOb;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public boolean isException() {
		return isException;
	}

	public void setException(boolean isException) {
		this.isException = isException;
	}

}
