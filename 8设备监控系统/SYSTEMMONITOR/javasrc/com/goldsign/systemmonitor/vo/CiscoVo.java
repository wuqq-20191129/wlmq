package com.goldsign.systemmonitor.vo;

public class CiscoVo {
	private String statusDate;
	private String ip;
	private String status ;
	private String powerStatus;
	private String temperatureStatus;
	private String fanStatus ;
	private String redundancy;
	private String mainframe ;
	private String remark  ;
	private String name;
	private String type;
	private String powerStatusText;
	private String temperatureStatusText;
	private String fanStatusText ;

	public CiscoVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getFanStatus() {
		return fanStatus;
	}

	public void setFanStatus(String fanStatus) {
		this.fanStatus = fanStatus;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMainframe() {
		return mainframe;
	}

	public void setMainframe(String mainframe) {
		this.mainframe = mainframe;
	}

	public String getPowerStatus() {
		return powerStatus;
	}

	public void setPowerStatus(String powerStatus) {
		this.powerStatus = powerStatus;
	}

	public String getRedundancy() {
		return redundancy;
	}

	public void setRedundancy(String redundancy) {
		this.redundancy = redundancy;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(String statusDate) {
		this.statusDate = statusDate;
	}

	public String getTemperatureStatus() {
		return temperatureStatus;
	}

	public void setTemperatureStatus(String temperatureStatus) {
		this.temperatureStatus = temperatureStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFanStatusText() {
		return fanStatusText;
	}

	public void setFanStatusText(String fanStatusText) {
		this.fanStatusText = fanStatusText;
	}

	public String getPowerStatusText() {
		return powerStatusText;
	}

	public void setPowerStatusText(String powerStatusText) {
		this.powerStatusText = powerStatusText;
	}

	public String getTemperatureStatusText() {
		return temperatureStatusText;
	}

	public void setTemperatureStatusText(String temperatureStatusText) {
		this.temperatureStatusText = temperatureStatusText;
	}

}
