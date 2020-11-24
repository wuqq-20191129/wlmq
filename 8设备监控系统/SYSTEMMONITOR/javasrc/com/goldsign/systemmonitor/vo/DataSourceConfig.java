package com.goldsign.systemmonitor.vo;

public class DataSourceConfig {
	private String type;
	private String ip;
	private String segementName;
	private String datasource;
	private String remark;

	public DataSourceConfig() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSegementName() {
		return segementName;
	}

	public void setSegementName(String segementName) {
		this.segementName = segementName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
