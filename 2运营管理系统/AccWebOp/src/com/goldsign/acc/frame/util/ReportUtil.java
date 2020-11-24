/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.util;

import com.goldsign.acc.app.opma.entity.ReportCfgModuleMapping;
import com.goldsign.acc.app.report.mapper.ReportMapper;
import com.goldsign.acc.frame.constant.ReportConstant;
import com.goldsign.acc.frame.controller.BaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.PubFlagMapper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


/**
 *
 * @author mqf
 */
public class ReportUtil {
    
    
    public static Vector MODULE_REPORT_TEMPLATE_MAPPING = new Vector();
    
//    public Vector getModuleReportTemplateMapping() throws Exception {
//        if (!this.MODULE_REPORT_TEMPLATE_MAPPING.isEmpty()) {
//            return this.MODULE_REPORT_TEMPLATE_MAPPING;
//        }
//        
//                List<PubFlag> aa = reportMapper.getReportModules();
//        
//        Vector<ReportCfgModuleMapping> moduleMappings = reportMapper.getReportModule();
//
//        if (!moduleMappings.isEmpty()) {
//            this.MODULE_REPORT_TEMPLATE_MAPPING.addAll(moduleMappings);
//        }
//        return moduleMappings;
//    }
    
    
    public static String getBalanceYearMonth(int flag) {
        GregorianCalendar current = new GregorianCalendar();
        if (flag == ReportConstant.FLAG_YEAR || flag == ReportConstant.FLAG_MONTH) {
            //if(current.get(GregorianCalendar.MONTH )==0)
            //	current.add(GregorianCalendar.YEAR,-1);
            current.add(GregorianCalendar.MONTH, -1);

        }
        int year = current.get(GregorianCalendar.YEAR);
        int month = current.get(GregorianCalendar.MONTH) + 1;
        int day = current.get(GregorianCalendar.DAY_OF_MONTH);
        int week = current.get(GregorianCalendar.WEEK_OF_MONTH);
        String sYear = Integer.toString(year);
        String sMonth = Integer.toString(month);
        String sDay = Integer.toString(day);
        String sDate = sYear + "-" + sMonth + "-" + sDay;
        String sWeek = CharUtil.GbkToIso("第" + week + "周");

//	    System.out.println("sDate="+sDate+" sWeek="+week);

        if (sMonth.length() < 2) {
            sMonth = "0" + sMonth;
        }
        if (sDay.length() < 2) {
            sDay = "0" + sDay;
        }
        sDate = sYear + "-" + sMonth + "-" + sDay;

        if (flag == ReportConstant.FLAG_YEAR) {
            return sYear;
        }
        if (flag == ReportConstant.FLAG_MONTH) {
            return sMonth;
        }
        if (flag == ReportConstant.FLAG_DATE) {
            return sDate;
        }
        if (flag == ReportConstant.FLAG_WEEK) {
            return sWeek;
        }

        return sDate;

    }
    
    /**
     * 根据moduleID查找report_module
     * @param moduleID
     * @return
     * @throws Exception 
     */
    public String getReportModuleByModuleId(String moduleID,Vector<ReportCfgModuleMapping> moduleMappings) throws Exception {
//        Vector<ReportCfgModuleMapping> moduleMappings = this.getModuleReportTemplateMapping();
        
        if (moduleMappings.isEmpty()) {
            return moduleID;
        }
        for (ReportCfgModuleMapping reportCfgModuleMapping :moduleMappings) {
            if (reportCfgModuleMapping.getModule_id().equals(moduleID)) {
                return reportCfgModuleMapping.getReport_module();
            }

        }
        return moduleID;

    }
    
    public static String getCurrentYearMonthDate(int flag) {
        GregorianCalendar current = new GregorianCalendar();
        int year = current.get(GregorianCalendar.YEAR);
        int month = current.get(GregorianCalendar.MONTH) + 1;
        int day = current.get(GregorianCalendar.DAY_OF_MONTH);
        int week = current.get(GregorianCalendar.WEEK_OF_MONTH);
        String sYear = Integer.toString(year);
        String sMonth = Integer.toString(month);
        String sDay = Integer.toString(day);
        String sDate = sYear + "-" + sMonth + "-" + sDay;
        String sWeek = CharUtil.GbkToIso("第" + week + "周");

//    System.out.println("sDate="+sDate+" sWeek="+week);

        if (sMonth.length() < 2) {
            sMonth = "0" + sMonth;
        }
        if (sDay.length() < 2) {
            sDay = "0" + sDay;
        }
        sDate = sYear + "-" + sMonth + "-" + sDay;

        if (flag == ReportConstant.FLAG_YEAR) {
            return sYear;
        }
        if (flag == ReportConstant.FLAG_MONTH) {
            return sMonth;
        }
        if (flag == ReportConstant.FLAG_DATE) {
            return sDate;
        }
        if (flag == ReportConstant.FLAG_WEEK) {
            return sWeek;
        }

        return sDate;

    }
    
    public static String getCurrentDateBeforeOne() {
        long curInMill = System.currentTimeMillis();
        curInMill = curInMill - 24 * 3600 * 1000;

        Date cur = new Date(curInMill);
        return DateHelper.dateToStringByFormat(cur, "yyyy-MM-dd");

    }
    
    //20190102 moqf 默认查询当前清算日
    public static String getCurrentDate() {
        long curInMill = System.currentTimeMillis();
        Date cur = new Date(curInMill);
        return DateHelper.dateToStringByFormat(cur, "yyyy-MM-dd");

    }
    
    public static String getLastDateBeforeOneMonth() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(gc.MONTH, -1);
        int day = gc.getActualMaximum(gc.DAY_OF_MONTH);
        int year = gc.get(gc.YEAR);
        int month = gc.get(gc.MONTH) + 1;
        String sMonth = Integer.toString(month);
        String sDay = Integer.toString(day);
        String sYear = Integer.toString(year);
        if (month < 10) {
            sMonth = "0" + sMonth;
        }
        if (day < 10) {
            sDay = "0" + sDay;
        }
        return sYear + "-" + sMonth + "-" + sDay;

    }
    
    public static List<PubFlag> getYears(String beginYear){
        List<PubFlag> years = new  ArrayList<PubFlag>();
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String currYear = dateFormat.format(date);
        for(int i = Integer.parseInt(beginYear) ; i<= Integer.parseInt(currYear) ; i++ ){
            PubFlag pubFlag = new PubFlag();
            pubFlag.setCode(Integer.toString(i));
            pubFlag.setCode_text(Integer.toString(i));
            years.add(pubFlag);
        }
        return years;
    }

    

}
