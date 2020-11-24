package com.goldsign.rule.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateHelper{

	public static String dateToString(java.util.Date d){
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		return f.format(d);
	}
	public static String yearToString(java.util.Date d){
		SimpleDateFormat f = new SimpleDateFormat("yyyy");
		return f.format(d);
	}
	
	public static String dateToStringName(java.util.Date d){
		SimpleDateFormat f = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
		return f.format(d);
	}
	public static String dateToYYYYMMDD(String date){
		if(date ==null || date.length() ==0)
			return "";
		return date.replaceAll("-","");
	}
	public static String curentDateToHH(){
		SimpleDateFormat f = new SimpleDateFormat("HH");
		return f.format(new Date());
	}
	public static String curentDateToHHMM(){
		SimpleDateFormat f = new SimpleDateFormat("HHmm");
		return f.format(new Date());
	}
	
	public static String datetimeToStringOnlyDate(java.util.Date d){
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		return f.format(d);
	}
	public static String datetimeToStringOnlyDate(String d) throws Exception{
        if(d ==null || d.trim().length() ==0)
        	return "";
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		
		Date d1 =f.parse(d);

		return f.format(d1);
	}

	
	public static String datetimeToStringOnlyDateF(java.util.Date d){
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		return f.format(d);
	}
	
	public static String datetimeToString(java.util.Date d){
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return f.format(d);
	}
	public static void screenPrint(String str){
        System.out.println(datetimeToString(new Date())+"  "+str);
	}

	public static String currentTodToString(){
		Date d = new Date(System.currentTimeMillis());
		return dateToString(d);
	}

    public static java.sql.Timestamp utilDateToSqlTimestamp(java.util.Date utilDate){
		return new java.sql.Timestamp(utilDate.getTime());
    }

    public static java.sql.Timestamp getCurrentSqlTimestamp(){
    	Date utilDate = new Date(System.currentTimeMillis());
		return new java.sql.Timestamp(utilDate.getTime());
    }
    
    public static java.sql.Date strToDate(String strDate) throws ParseException{
    	SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return new java.sql.Date(f.parse(strDate).getTime());
    }
    
    public static java.util.Date strToUtilDate(String strDateOnly) throws ParseException{
    	SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
    	return f.parse(strDateOnly);
    	
    }
    
    public static java.sql.Date strToDate(String strDate,String defaultValue) throws ParseException{
    	if(strDate == null || strDate.trim().length() ==0)
    		strDate =defaultValue; 
    	SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return new java.sql.Date(f.parse(strDate).getTime());
    }
    
    public static java.sql.Timestamp strToTimeStamp(String strDate) throws ParseException{
    	SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return new java.sql.Timestamp(f.parse(strDate).getTime());
    }
    public static java.sql.Timestamp strToTimeStamp(String strDate,String defaultValue) throws ParseException{
    	if(strDate == null || strDate.trim().length() ==0)
    		strDate =defaultValue; 
    	SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return new java.sql.Timestamp(f.parse(strDate).getTime());
    }
    public static void main(String[] args){
    	String d ="2009-1-2 00:00:00";
    	/*
    	Date cur = new Date();
    	Timestamp sqlCur = new java.sql.Timestamp(cur.getTime());
    	System.out.println("cur="+cur.toString()+" sqlCur="+sqlCur.toString());
    	*/
    	String tmp ="";
    	GregorianCalendar gc = new GregorianCalendar();
    	gc.add(GregorianCalendar.DAY_OF_YEAR,365);
    	//Date newDate = new Date(gc.gett)
    	System.out.println(DateHelper.datetimeToString(gc.getTime()));
    	
    	
    	try {
		//	String sd =DateHelper.datetimeToStringOnlyDate(d);
		//	System.out.println(sd);
    		System.out.println(DateHelper.strToTimeStamp(null,"1900-01-01 00:00:00"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

}