package com.goldsign.systemmonitor.vo;

public class DiskSpaceVo {
	private String ip;
	private String fileSystem;
	private String avail;
	private String capacity;
	private String status;
	private String statusDate;
	private String remark;
	private String name;
	private String type;
	private String mountedOn;
	private String style;

	public String getMountedOn() {
		return mountedOn;
	}

	public void setMountedOn(String mountedOn) {
		this.mountedOn = mountedOn;
	}

	public DiskSpaceVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getAvail() {
		return avail;
	}

	public void setAvail(String avail) {
		this.avail = avail;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getFileSystem() {
		return fileSystem;
	}

	public void setFileSystem(String fileSystem) {
		this.fileSystem = fileSystem;
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

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

}
