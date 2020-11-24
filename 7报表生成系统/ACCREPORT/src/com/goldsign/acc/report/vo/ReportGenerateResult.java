package com.goldsign.acc.report.vo;

public  class ReportGenerateResult {
//	private boolean isNeedRenerate =false;
	private boolean runResult =true;
	private int retCode =-1;
	public ReportGenerateResult() {
	}
	public ReportGenerateResult(boolean result) {
		this.runResult  = result;
	}
	/*
	 public boolean getIsNeedRenerate(){
	 return this.isNeedRenerate;
	 }
	 public void setIsNeedRenerate(boolean isNeedRenerate){
	 this.isNeedRenerate = isNeedRenerate;
	 }
	 */
	public boolean getRunResult(){
		return this.runResult;
	}
	public void setRunResult(boolean runResult){
		this.runResult = runResult;
	}
	
	
	public int getRetCode(){
		return this.retCode;
	}
	public void setRetCode(int retCode){
		this.retCode  = retCode;
	}
	
	
}
