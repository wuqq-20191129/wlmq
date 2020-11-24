/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.vo;

import java.util.Vector;

/**
 *
 * @author mh
 */
public class ImportResult {
    private String fileName;
	private String versonNo;
	private int recordNum;
	
	private String tile;
	private Vector records;
	
	private Vector fileNames=new Vector();
	private Vector recordNums =new Vector();

	public ImportResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getVersonNo() {
		return versonNo;
	}

	public void setVersonNo(String versonNo) {
		this.versonNo = versonNo;
	}

	public int getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(int recordNum) {
		this.recordNum = recordNum;
	}

	public Vector getRecords() {
		return records;
	}

	public void setRecords(Vector records) {
		this.records = records;
	}

	public String getTile() {
		return tile;
	}

	public void setTile(String tile) {
		this.tile = tile;
	}

	public Vector getFileNames() {
		return fileNames;
	}

	public void setFileNames(Vector fileNames) {
		this.fileNames = fileNames;
	}
	public void setFileNames(String fileName) {
		this.fileNames.add(fileName);
	}

	public Vector getRecordNums() {
		return recordNums;
	}

	public void setRecordNums(Vector recordNums) {
		this.recordNums = recordNums;
	}
	public void setRecordNums(String  recordNum) {
		this.recordNums.add(recordNum);
	}

}

