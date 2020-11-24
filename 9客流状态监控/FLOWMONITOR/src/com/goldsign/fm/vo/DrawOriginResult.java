/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.fm.vo;

/**
 *
 * @author Administrator
 */
public class DrawOriginResult {
	private String linekey;
	private int rowkey;
	private double value;
	private int entryTotal =0;
	private int exportTotal =0;



	/**
	 * Returns the linekey.
	 * @return String
	 */
	public String getLinekey() {
          if(this.linekey == null)
            return "";
		return linekey;
	}

	/**
	 * Returns the rowkey.
	 * @return int
	 */
	public int getRowkey() {

		return rowkey;
	}
	public int getEntryTotal() {
		return this.entryTotal;
	}
	public void setEntryTotal(int entryTotal){
		this.entryTotal = entryTotal;
	}
	public int getExportTotal() {
		return this.exportTotal ;
	}
	public void setExportTotal(int exportTotal){
		this.exportTotal = exportTotal;
	}

	/**
	 * Returns the value.
	 * @return double
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Sets the linekey.
	 * @param linekey The linekey to set
	 */
	public void setLinekey(String linekey) {
		this.linekey = linekey;
	}

	/**
	 * Sets the rowkey.
	 * @param rowkey The rowkey to set
	 */
	public void setRowkey(int rowkey) {
		this.rowkey = rowkey;
	}

	/**
	 * Sets the value.
	 * @param value The value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}

}
