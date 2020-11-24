package com.goldsign.acc.systemmonitor.util;

import java.math.BigDecimal;
import java.math.BigInteger;


//modyfy by zhongzq 优化代码 20180627
public class NumberUtil {

	public NumberUtil() {
		super();
		// TODO Auto-generated constructor stub
	}
	public static String formatNumber(int number,int len){
		if(number <0) {
			return "";
		}
		String sn = Integer.toString(number);
		StringBuffer tmp = new StringBuffer();
		for(int i=0;i<len-sn.length();i++) {
			tmp.append("0");
		}
		return tmp.append(sn).toString();
	}

	public static String formatNumber(BigInteger number,int len){
		
		String sn = number.toString();
		StringBuffer tmp = new StringBuffer();
		for(int i=0;i<len-sn.length();i++) {
			tmp.append("0");
		}
		return tmp.append(sn).toString();
	}
	public static String formatNumber(String number,int len){

		String sn = number;
		StringBuffer tmp =new StringBuffer();
		for(int i=0;i<len-sn.length();i++) {
			tmp.append("0");
		}
		return tmp.append(sn).toString();
	}
	public static Integer getIntegerValue(String value,int defaultValue){
		if(value ==null || value.length() ==0) {
			return  Integer.valueOf(defaultValue);
		}
		return Integer.valueOf(value);
	}
	public static Integer getIntegerValue(String value){
		if(value ==null || value.length() ==0) {
			return null;
		}
		return Integer.valueOf(value);
	}
	public static String getIntegerStrValue(String value,int defaultValue){
		if(value ==null || value.length() ==0) {
			return  Integer.toString(defaultValue);
		}
		return value;
	}
	public static String getDateTimeValue(String value,String defaultValue){
		if(value ==null || value.length() ==0) {
			return defaultValue;
		}
		return value;
	}
	public static Object getDateTimeValue(String value,Object defaultValue){
		if(value ==null || value.length() ==0) {
			return defaultValue;
		}
		return value;
	}
	public static String getDateTimeBeginValue(String value){
		if(value ==null || value.length() ==0) {
			return "";
		}
		return value+" 00:00:00";
	}
	public static String getDateTimeEndValue(String value){
		if(value ==null || value.length() ==0) {
			return "";
		}
		return value+" 23:59:59";
	}
	
	public static String getDateValue(String value){
		if(value ==null || value.length() ==0) {
			return "";
		}
		return value.substring(0,10);
	}
	public static String getStringValue(String value){
		if(value ==null || value.length() ==0) {
			return "";
		}
		return value;
	}
	public static String getStringValue(String value,String defaultValue){
		if(value ==null || value.length() ==0) {
			return defaultValue;
		}
		return value;
	}

	
	public static BigDecimal getBigDecimalValue(String value,String defaultValue){
		if(value ==null || value.length() ==0) {
			return new BigDecimal(defaultValue);
		}
		return new BigDecimal(value);
	}
	public static BigDecimal getBigDecimalValue(String value,String defaultValue,int scale){
		if(value ==null || value.length() ==0) {
			value = defaultValue;
		}
		value = value.trim();
		BigDecimal bd = new BigDecimal(value);
		bd.setScale(scale);
		return bd;
	}
	public static String add(String n,int addNum){
		return Integer.toString(Integer.parseInt(n)+addNum);
	}
	
	
	public static void main(String[] args){
		String tmp ="60";
		BigDecimal bg = new BigDecimal(tmp);
		
	//	bg.setScale(3);
		System.out.println(bg.toString());
	//	System.out.println("n ="+NumberUtil.formatNumber(new BigInteger("20080101"),10));
		
	}

}
