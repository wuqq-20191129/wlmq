package com.goldsign.acc.systemmonitor.vo;

import java.util.Vector;

public class SqlVo {
	String sql;
	Vector values= new Vector();
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public Vector getValues() {
		return values;
	}
	public void setValues(Vector values) {
		this.values.addAll(values);
	}
	public void addValues(String[] values) {
		for(int i=0;i<values.length;i++){
			this.values.add(values[i]);
		}
	}

	
}
