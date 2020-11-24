/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.constant;

/**
 *
 * @author 
 */
public class ReportConstant {

    /**
     * 报表查询条件模板
     */
    public static String TEMPLATE_NAME_DATE= "<c:import url=\"/jsp/report/report_qry_template.jsp?template_name=op_report_qry_con_date\" /> \n";
    public static String TEMPLATE_NAME_DATE_CUR= "<c:import url=\"/jsp/report/report_qry_template.jsp?template_name=op_report_qry_con_date_cur\" /> \n";
    
    public static String TEMPLATE_NAME_IS_BALANCE_DATE= "<c:import url=\"/jsp/report/report_qry_template.jsp?template_name=op_report_qry_con_isBalanceDate\" /> \n";
    public static String TEMPLATE_NAME_YEAR= "<c:import url=\"/jsp/report/report_qry_template.jsp?template_name=op_report_qry_con_year\" /> \n";
    public static String TEMPLATE_NAME_YEAR_MONTH= "<c:import url=\"/jsp/report/report_qry_template.jsp?template_name=op_report_qry_con_year_month\" /> \n";
    public static String TEMPLATE_NAME_MONTH= "<c:import url=\"/jsp/report/report_qry_template.jsp?template_name=op_report_qry_con_month\" /> \n";
    public static String TEMPLATE_NAME_LINE_ID= "<c:import url=\"/jsp/report/report_qry_template.jsp?template_name=op_report_qry_con_lineID\" /> \n";
    public static String TEMPLATE_NAME_LINE_ID_LARGE= "<c:import url=\"/jsp/report/report_qry_template.jsp?template_name=op_report_qry_con_lineIDLarge\" /> \n";
    public static String TEMPLATE_NAME_MAIN_TYPE= "<c:import url=\"/jsp/report/report_qry_template.jsp?template_name=op_report_qry_con_mainType\" /> \n";
    public static String TEMPLATE_NAME_SUB_TYPE= "<c:import url=\"/jsp/report/report_qry_template.jsp?template_name=op_report_qry_con_subType\" /> \n";
    
    public static String TEMPLATE_NAME_TR_BEGIN= "<tr class=\"table_edit_tr\">\n";
    
    public static String TEMPLATE_NAME_TR_END= "</tr>\n";
    
    public static String TEMPLATE_NAME_LINE_BREAK= "\n";
    
    
    public static final int FLAG_YEAR = 1;//年标志
    public static final int FLAG_MONTH = 2;//月标志
    public static final int FLAG_DATE = 3;//日期标志
    public static final int FLAG_WEEK = 3;//周标志
    
    public static String BEGIN_YEAR = "2018"; // 报表查询起始年 2018
    
    /**
     * 报表周期类型相关
     */
    
//   public static final String FLAG_PERIOD_MONTH = "2";
//   public static final String FLAG_PERIOD_YEAR = "3";
//   public static final String FLAG_PERIOD_DAY = "1";
   public static final String FLAG_PERIOD_CUSTOMED = "9"; //period_type 9: 定制报表

}
