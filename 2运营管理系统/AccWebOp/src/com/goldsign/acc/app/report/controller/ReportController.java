/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.report.controller;

import com.goldsign.acc.app.opma.entity.ReportCfgAttr;
import com.goldsign.acc.app.opma.entity.ReportCfgModuleMapping;
import com.goldsign.acc.app.report.entity.ReportAttribute;
import com.goldsign.acc.app.report.exception.ReportConnectionException;
import com.goldsign.acc.app.report.mapper.ReportMapper;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.constant.ReportConstant;
import com.goldsign.acc.frame.constant.TypeConstant;
import com.goldsign.acc.frame.controller.BaseController;

import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;

import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.util.ReportUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 报表查询
 * @author mqf
 */
@Controller
public class ReportController extends BaseController {

    @Autowired
    private ReportMapper reportMapper;
    
    @Autowired
    protected OperationLogMapper operationLogMapper;


    @RequestMapping("/Report")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        String action = this.getActionName(request);
        String viewName = this.getViewNameFromAction(action);
        
        //页面文件格式od_station_passenger_by_line_card_day.jsp
        ModelAndView mv = new ModelAndView("/jsp/report/"+viewName);
        

        OperationResult opResult = new OperationResult();
        
        String command = request.getParameter("command");
        String moduleID = request.getParameter("ModuleID");
        String reportNamePrefix = request.getParameter("ReportNamePrefix");
        String display = "none";
        Vector reportAttributes = null;
        String controlDefaultValues = null;
        showAllParameters(request);
        LogUtil logUtil = new LogUtil();
        
        //1、初始化默认值 2、获取按清算日、按运算日查询选项
        String isBalanceDate = this.getIsBalanceDateValue(request);
        
        
        try {
            if (command != null) {
                command = command.trim();
                
                if (isBalanceDate.equals("1")) {
                    if (this.isMonthReport(reportNamePrefix)) {
                        reportNamePrefix = this.getBalanceReportNamePrfix(reportNamePrefix);
                    }
                }
                
                if (reportNamePrefix != null && command != null && command.equals("reportQuery")) 
                {
                    try {
                        reportAttributes = this.getReportNamesByServletForDiffServers(reportNamePrefix, moduleID, isBalanceDate);
                        controlDefaultValues = this.getControlDefaultValues(request);
                        if (!reportAttributes.isEmpty()) {
                            display = "1";
                        }
                        //20171029 moqf
                        opResult.setReturnResultSet(reportAttributes);
                        
                          //报表查询不用写操作日志
//                        logUtil.logOperation("reportQuery", request,  LogConstant.OPERATIION_SUCCESS_LOG_MESSAGE, operationLogMapper);
                    } catch (ReportConnectionException e) {
            //            throw new Exception("报表查找服务器连接失败");
//                        LogUtil logUtil = new LogUtil();
                        opResult = logUtil.operationExceptionHandle(request, "reportQuery", e, operationLogMapper);
                        mv.addObject("Display", "none");
                    }

                }
            }

//        } catch (ReportConnectionException e) {
////            throw new Exception("报表查找服务器连接失败");
////            e.printStackTrace();
//            LogUtil logUtil = new LogUtil();
//            opResult = logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        } catch (Exception e) {
            e.printStackTrace();
           
        }
        //设置返回消息
        this.addMessage(opResult,command,reportNamePrefix,reportAttributes);
        this.setPageOptions(mv, request, response, moduleID, isBalanceDate);
        
        
        this.baseHandlerForReport(mv, controlDefaultValues, reportAttributes);
        this.baseHandler(request, response, mv);
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }

    public boolean isMonthReport(String reportNamePrefix) {
        int index = reportNamePrefix.indexOf(".");
        if (index == -1) {
            return false;
        }
        String date = reportNamePrefix.substring(0, index);
        if (date.length() != 8) {
            return false;
        }
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.substring(6, 8);
        if (day.equals("00")) {
            return true;
        }
        return false;
    }
    
    public String getBalanceReportNamePrfix(String reportNamePrefix) {
        int index = reportNamePrefix.indexOf(".");
        if (index == -1) {
            return "";
        }
        String date = reportNamePrefix.substring(0, index);
        String sufix = reportNamePrefix.substring(index);
        if (date.length() != 8) {
            return "";
        }
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.substring(6, 8);

        if (month.equals("00")) {
            month = "12";
        }
        int iYear = new Integer(year).intValue();
        int iMonth = new Integer(month).intValue() - 1;
        int iDay = 1;
        GregorianCalendar gc = new GregorianCalendar(iYear, iMonth, iDay);
        iDay = gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        day = Integer.toString(iDay);
        
        //20181112 moqf 如果是定制报表，取定制报表的定点日期
        day = setDayForCustomedReport(day,sufix);
        
        if (day.length() != 2) {
            day = "0" + day;
        }
        return year + month + day + sufix;


    }
    
//    public void saveReportData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String moduleID) throws Exception {
//        Vector lines = null;
//        Vector stations = null;
//        Vector cardMainType = null;
//        Vector cardSubType = null;
//        Vector pubFlags = null;
//        Vector typeFlagsForReport = null;
//        Vector moduleFlagsForReport = null;
//        Vector typePubFlagsForYear = null;
//        Vector typePubFlagsForMonth = null;
//        Vector typePubFlagsForCardSubType = null;
//        Vector ESOperators = null;
//        String currentDate = null;
//        String balanceDate = null;
//        String monthBalanceDate = null;
//        Vector lineTypes = null;
//        try {
//            DBUtil dbUtil = new DBUtil();
//            lines = TableFlagUtil.findLines();
//            //20160322 add by mqf
//            lineTypes = TableFlagUtil.findLineTypes();
//            stations = TableFlagUtil.findStations();
//            cardMainType = TableFlagUtil.findCardMain();
//            cardSubType = TableFlagUtil.findCardSub();
////            ESOperators = dbUtil.getTableFlags("iccs_operator", "oper_id", "oper_name");
//            pubFlags = dbUtil.getPubFlags();
//
//            typePubFlagsForYear = dbUtil.getPubFlagsByTypeForYear(PubFlagType.PUT_FLAG_TYPE_YEAR, pubFlags);
//            typePubFlagsForMonth = dbUtil.getPubFlagsByTypeForMonth(PubFlagType.PUT_FLAG_TYPE_MONTH, pubFlags);
//            typePubFlagsForCardSubType = dbUtil.getPubFlagsByCardType(PubFlagType.PUT_FLAG_TYPE_CARD_SUBTYPE, pubFlags);
//
//
//            typeFlagsForReport = dbUtil.getPubFlagsByType(PubFlagType.PUB_FLAG_TYPE_REPORT, pubFlags);
//            moduleFlagsForReport = dbUtil.getPubFlagsByModule(moduleID, typeFlagsForReport);
//            currentDate = dbUtil.getCurrentYearMonthDate(FrameCodeConstant.FLAG_DATE);
//            balanceDate = dbUtil.getCurrentDateBeforeOne();
//            monthBalanceDate = dbUtil.getLastDateBeforeOneMonth();
//
//
//
//        } catch (Exception e) {
//            throw e;
//        }
//        if (typePubFlagsForYear != null) {
//            this.saveResult(request, "Years", typePubFlagsForYear);
//        }
//        if (typePubFlagsForMonth != null) {
//            this.saveResult(request, "Months", typePubFlagsForMonth);
//        }
//        if (typePubFlagsForCardSubType != null) {
//            this.saveResult(request, "ReportCardSubTypes", typePubFlagsForCardSubType);
//        }
//        if (lines != null) {
//            this.saveResult(request, "Lines", lines);
//        }
//        if (stations != null) {
//            this.saveResult(request, "Stations", stations);
//        }
//        if (cardMainType != null) {
//            this.saveResult(request, "CardMainTypes", cardMainType);
//        }
//        if (cardSubType != null) {
//            this.saveResult(request, "CardSubTypes", cardSubType);
//        }
//        if (moduleFlagsForReport != null) {
//            this.saveResult(request, "ReportCodeMappings", moduleFlagsForReport);
//        }
//        if (ESOperators != null) {
//            this.saveResult(request, "ESOperators", ESOperators);
//        }
//        if (currentDate != null) {
//            this.saveResult(request, "CurrentDate", currentDate);
//        }
//        if (balanceDate != null) {
//            this.saveResult(request, "BalanceDate", balanceDate);
//        }
//        if (monthBalanceDate != null) {
//            this.saveResult(request, "MonthBalanceDate", monthBalanceDate);
//        }
//        //20160322 add by mqf
//        if (lineTypes != null) {
//            this.saveResult(request, "LineTypes", lineTypes);
//        }
//
//
//
//    }
    
    protected void setPageOptions(ModelAndView mv, HttpServletRequest request, HttpServletResponse response, 
            String moduleID, String isBalanceDate) {

//        lines = TableFlagUtil.findLines();
//            //20160322 add by mqf
//            lineTypes = TableFlagUtil.findLineTypes();
//            stations = TableFlagUtil.findStations();
//            cardMainType = TableFlagUtil.findCardMain();
//            cardSubType = TableFlagUtil.findCardSub();
////            ESOperators = dbUtil.getTableFlags("iccs_operator", "oper_id", "oper_name");
//            pubFlags = dbUtil.getPubFlags();
//            temppp
                    
        if (moduleID == null) {
            return;
        }

        List<PubFlag> options;
        
        //大线路
        options = pubFlagMapper.getLineTypes();
        mv.addObject("LineTypes", options);
        
//        options = pubFlagMapper.getLines();
        //20200907 moqf 报表查询线路
        options = pubFlagMapper.getLinesForRptQry();
        mv.addObject("Lines", options);
        
            
        options = pubFlagMapper.getStations();
        mv.addObject("Stations", options);
        
//        if (attrName.equals(LINE_STATIONS)) {//线路车站
//            options = pubFlagMapper.getStations();
//            String lineStations = FormUtil.getLineStations(options);
//            mv.addObject(attrName, lineStations);
//            continue;
//        }
        options = pubFlagMapper.getCardMains();
        mv.addObject("CardMainTypes", options);
//        cardMains
        
        options = pubFlagMapper.getCardSubs();
        String cardMainSubs = FormUtil.getCardMainSubs(options);
//        CardSubTypes
        mv.addObject("cardMainSubs", cardMainSubs);
//        cardMainSubs
        options = this.getYears();
        mv.addObject("Years", options);
        
        options = getPubFlagsByTypeForMonth();
        mv.addObject("Months", options);
        
//        moduleFlagsForReport = dbUtil.getPubFlagsByModule(moduleID, typeFlagsForReport);
//        if (moduleFlagsForReport != null) {
//                    this.saveResult(request, "ReportCodeMappings", moduleFlagsForReport);
//        }
        options = pubFlagMapper.getReportCodes(moduleID);
        String reportCodeMappings =options.get(0).getCode();
        mv.addObject("ReportCodeMappings", reportCodeMappings);


        String currentDate = ReportUtil.getCurrentYearMonthDate(ReportConstant.FLAG_DATE);
        String balanceDate = ReportUtil.getCurrentDateBeforeOne();
        //20190102 moqf 默认查询当前清算日，实时报表查询
        String balanceDateCur = ReportUtil.getCurrentDate();
        
        String monthBalanceDate = ReportUtil.getLastDateBeforeOneMonth();
        
//        if (typePubFlagsForYear != null) {
//            mv.addObject("Years", typePubFlagsForYear);
//        }
//        if (typePubFlagsForMonth != null) {
//            mv.addObject("Months", typePubFlagsForMonth);
//        }
        
        if (currentDate != null) {
             mv.addObject("CurrentDate", currentDate);
        }
        if (balanceDate != null) {
            mv.addObject("BalanceDate", balanceDate);
        }
        if (balanceDateCur != null) {
            mv.addObject("BalanceDateCur", balanceDateCur);
        }
        if (monthBalanceDate != null) {
            mv.addObject("MonthBalanceDate", monthBalanceDate);
        }
        //返回按清算日、按运算日查询选项
        mv.addObject("isBalanceDate", isBalanceDate);
                
        
        
    }
    
    private List<PubFlag> getYears() {
//        List<PubFlag> options = pubFlagMapper.getPubFlags(TypeConstant.PUT_FLAG_TYPE_YEAR);
        List<PubFlag> options = ReportUtil.getYears(ReportConstant.BEGIN_YEAR);
        String curYear = ReportUtil.getBalanceYearMonth(ReportConstant.FLAG_YEAR);
        return getPubFlagsByTypeForDefault(options, curYear);
    }
    
    
    
    
    
    public List<PubFlag> getPubFlagsByTypeForDefault(List<PubFlag> pubFlags, String curDefValue) {
        String defaultValue = "";
        for (int i = 0; i < pubFlags.size(); i++) {
            PubFlag pv = (PubFlag) pubFlags.get(i);
            defaultValue = pv.getCode();
            pv.setIsDefaultValue(false);
            if (defaultValue != null) {
                if (curDefValue.equals(defaultValue)) {
                    pv.setIsDefaultValue(true);
                }
            }

        }
        return pubFlags;

    }
    
    private List<PubFlag>  getPubFlagsByTypeForMonth() {
        List<PubFlag> options = pubFlagMapper.getPubFlags(TypeConstant.PUT_FLAG_TYPE_MONTH);
        String curMonth = ReportUtil.getBalanceYearMonth(ReportConstant.FLAG_MONTH);
        return this.getPubFlagsByTypeForDefault(options, curMonth);
    }
    
    
    //获取action名称
    private String getActionName(HttpServletRequest request) {
        //进入查询报表界面
        String action = (String) request.getParameter("action");
        if (action == null) {
            //查询报表时
            action = (String) request.getParameter("actionName");
        }
        
        if (action !=null && action.indexOf(".do") != -1) {
                action = action.substring(0, action.indexOf(".do"));
        }
        return action;
    }
    
    /**
     * 根据action获取页面jsp文件名，如IncSysSaleD转换为inc_sys_sale_d.jsp
     * @param action
     * @return 
     */
    private String getViewNameFromAction(String action) {
        StringBuffer sbAction = new StringBuffer("");
        for (int i=0; i< action.length();i++) {
            char c = action.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i != 0) {
                    sbAction.append("_");
                }
                sbAction.append(Character.toLowerCase(c));
            } else {
                sbAction.append(c);
            }
        }
        sbAction.append(".jsp");
        return sbAction.toString();
    }
    
    public Vector getReportNamesByServletForDiffServers(String reportNamePrefix, String moduleID, String isBalanceDate) throws ReportConnectionException, Exception {
        String pathName = "";
        String basePath = System.getProperty("report.dir");//报表查找时文件系统的基本路径
        String baseURL = System.getProperty("report.url");//报表文件访问时的链接基本路径
        String actionURL = System.getProperty("report.action.url");//报表文件所在服务器上的查找应用路径

        Vector basePaths = this.getVector(basePath);
        Vector baseURLs = this.getVector(baseURL);
        Vector actionURLs = this.getVector(actionURL);
        String basePathEach = "";
        String baseURLEach = "";
        String actionURLEach = "";

        if (basePaths.size() != baseURLs.size() || basePaths.size() != actionURLs.size()) {
            throw new Exception("配置的报表基本路径、显示URL、查找URL不一致，多服务器查找需用#分隔");
        }

//        ReportUtil dbUtil = new ReportUtil();
//        Vector mapping = dbUtil.getModuleReportTemplateMapping();
//        String reportTemplateCode = dbUtil.getReportTemplateCodeByModuleID(mapping, moduleID);
        
        //根据moduleID查找report_module
        Vector<ReportCfgModuleMapping> moduleMappings = this.getModuleReportTemplateMapping();
         ReportUtil reportUtil = new ReportUtil();
         String reportModule = reportUtil.getReportModuleByModuleId(moduleID, moduleMappings);
         
         
        
        String[] reportNames = null;

        for (int i = 0; i < actionURLs.size(); i++) {
            basePathEach = (String) basePaths.get(i);
            baseURLEach = (String) baseURLs.get(i);
            actionURLEach = (String) actionURLs.get(i);
            pathName = basePathEach + "/" + reportModule;
            System.out.println("pathname=" + pathName);

            reportNames = this.getReportFileNamesByHttp(pathName, reportNamePrefix, isBalanceDate, actionURLEach);
            if (reportNames != null && reportNames.length != 0) {
                break;
            }

        }

        
        Vector reportAttributes = new Vector();
        String reportURL = "";
        String balanceDate = "";
        String opDate = "";


        System.out.println("reportNames:" + reportNames);
        if (reportNames == null || reportNames.length == 0) {
            return reportAttributes;
        }
        for (int i = 0; i < reportNames.length; i++) {
            ReportAttribute ra = new ReportAttribute();
            //      System.out.println("moduleID="+moduleID+" reportNames "+reportNames[i]);
            balanceDate = this.getReportBalanceDate(reportNames[i]);
            ra.setModuleID(moduleID);
            ra.setReportName(reportNames[i]);
            ra.setTemplateCode(reportModule);
            reportURL = baseURLEach + "/" + reportModule + "/" + reportNames[i];
            System.out.println(reportURL);
            ra.setReportURL(reportURL);
            ra.setBalanceDate(balanceDate);
            opDate = this.getReportOpTime(reportNames[i]);
            ra.setOperationDate(opDate);
            reportAttributes.add(ra);

        }
        //对返回的报表文件记录排序
        sortReportAttributeRecord(reportAttributes);
                
        return reportAttributes;


    }
    
    private Vector getVector(String strs) {
        StringTokenizer st = new StringTokenizer(strs, "#");
        String str = null;
        Vector v = new Vector();

        while (st.hasMoreTokens()) {
            str = st.nextToken();
            v.add(str);
        }
        return v;
    }
    
    
    public String[] getReportFileNamesByHttp(String pathName, String reportFilePrefix, String isBalanceDate, String actionURL) throws ReportConnectionException, Exception {
        String[] reportFileNames = null;
        // String actionURL = System.getProperty("report.action.url");
        URL url = null;
        String params = null;
        String strReportFileNames = "";
        System.out.println("actionURL=" + actionURL);

        try {
            url = new URL(actionURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            this.setRequestAttribute(connection);
            connection.connect();

            params = "pathName=" + pathName + "&reportNamePrefix=" + reportFilePrefix + "&isBalanceDate=" + isBalanceDate;
            OutputStream out = connection.getOutputStream();
            out.write(params.getBytes());
            out.flush();
            out.close();

            System.out.println("returncode:" + connection.getResponseCode() + " message:" + connection.getResponseMessage());

            InputStream in = connection.getInputStream();
            strReportFileNames = this.getFileNamesFromInputStream(in);

        } catch (IOException e) {
            throw new ReportConnectionException(e.getMessage());
        }
        if (strReportFileNames.trim().length() == 0) {
            return null;
        }
        reportFileNames = this.convert(strReportFileNames, "#");

        return reportFileNames;
    }
    
    public void setRequestAttribute(HttpURLConnection connection) throws Exception {
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", "Mozilla/4.7 [en] (Win98; I)");
        connection.setRequestProperty("Accept-Language", "zh-cn");
        connection.setRequestProperty("Cache-Control", "no-cache");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        connection.setDoOutput(true);
        connection.setDoInput(true);
    }
    
    public String getFileNamesFromInputStream(InputStream in) throws Exception {
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(isr);
        String strReportFileNames = "";
        String line = null;
        int index = -1;
        int index1 = -1;
        int index2 = -1;

        while ((line = br.readLine()) != null) {
            //System.out.println("line="+line);
            index = line.indexOf("reportNames=");
            if (index == -1) {
                continue;
            }
            index1 = line.indexOf("=", index + 1);
            index2 = line.indexOf(";", index + 1);
            if (index2 == -1) {
                return "";
            }
            strReportFileNames = line.substring(index1 + 1, index2);
            break;
        }
        return strReportFileNames;
    }
    
    public String[] convert(String fileNames, String delim) {
        ArrayList files = new ArrayList();
        String[] result = null;
        StringTokenizer st = new StringTokenizer(fileNames, delim);
        String token = null;
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            files.add(token);
        }
        result = new String[files.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = (String) files.get(i);
        }
        return (String[]) result;
    }
    
    public String getReportBalanceDate(String reportName) {
        int index_1 = -1;
        int index_2 = -1;

        index_1 = reportName.indexOf(".");
        if (index_1 == -1) {
            return "";
        }
        index_2 = reportName.indexOf(".", index_1 + 1);
        if (index_2 == -1) {
            return "";
        }

        return reportName.substring(index_2 + 1, index_2 + 9);


    }
    
    public String getReportOpTime(String reportName) {
        int index_1 = -1;
        String year = "";
        String month = "";
        String day = "";
        String date = "";


        index_1 = reportName.indexOf(".");
        if (index_1 == -1) {
            return "";
        }
        date = reportName.substring(0, index_1);
        if (date.length() != 8) {
            return "";
        }
        year = date.substring(0, 4);
        month = date.substring(4, 6);
        day = date.substring(6);
        if (month.equals("00")) {
            return year;
        }
        if (day.equals("00")) {
            return year + "-" + month;
        }
        return year + "-" + month + "-" + day;



    }
    
    public String getControlDefaultValues(HttpServletRequest request) {
        String result = "";
        String controlNames = request.getParameter("ControlNames");
        if (controlNames == null || controlNames.length() == 0) {
            return "";
        }
        StringTokenizer st = new StringTokenizer(controlNames, "#");
        String name = "";
        String value = "";
        while (st.hasMoreTokens()) {
            name = st.nextToken();
            value = request.getParameter(name);
            if (name.equals("isBalanceDate")) {
//                if (value != null) {
//                    value = "0";//值为"0"时，"按清算日"Radio控件为选上; 选"按清算日"时，页面控件value="0"
//                } 
                continue; //"按清算日"、"按运营日"选项值不需要返回  

            }
            result += name + "#" + value + ";";
        }
        if (result.length() != 0) {
            result = result.substring(0, result.length());
        }
        return result;
    }
    
    public void showAllParameters(HttpServletRequest req) {
        String encoding = req.getCharacterEncoding();
        //System.out.println("ecoding="+encoding);
        Enumeration e = req.getParameterNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            String value = (String) req.getParameter(key);
            //System.out.println(key + "=" + value);
        }

    }
    
    //设置返回消息
    private void addMessage(OperationResult opResult, String command, String reportNamePrefix, Vector reportAttributes) {
        if (reportNamePrefix == null) {
            //进入报表查询模块，没有查询时
            if (command == null) {
                opResult.addMessage(LogConstant.OPERATIION_SUCCESS_LOG_MESSAGE);
            }
        } else {
            String msg = "";
            if (reportAttributes == null) {
                msg = "请使用查询按钮提交";
            } else {
                
                if (reportAttributes.isEmpty()) {
                    msg = "查询的报表没有生成";
                } else {
                    msg ="查询的报表有" + reportAttributes.size() + "张";
                }
            }
            opResult.addMessage(msg);
            
        }
    }
    
    public void baseHandlerForReport(ModelAndView modelView,String controlDefaultValues,Vector reportAttributes) {
        if (controlDefaultValues != null) {
            modelView.addObject("ControlDefaultValues", controlDefaultValues);
        }
        if (reportAttributes != null) {
            modelView.addObject("ReportNames", reportAttributes);
        }
    }
    
    public Vector getModuleReportTemplateMapping() throws Exception {
        if (!ReportUtil.MODULE_REPORT_TEMPLATE_MAPPING.isEmpty()) {
            return ReportUtil.MODULE_REPORT_TEMPLATE_MAPPING;
        }
        
        Vector<ReportCfgModuleMapping> moduleMappings = reportMapper.getReportModule();

        if (!moduleMappings.isEmpty()) {
            ReportUtil.MODULE_REPORT_TEMPLATE_MAPPING.addAll(moduleMappings);
        }
        return moduleMappings;
    }
    
    /**
     * 获取按清算日、按运算日查询选项
     * @param request
     * @return 
     */
    private String getIsBalanceDateValue(HttpServletRequest request) {
        String isBalanceDate = request.getParameter("isBalanceDate");
//        if (isBalanceDate == null || isBalanceDate.length() == 0) {
//            isBalanceDate = "0"; //按运算日
//        } else {
//            isBalanceDate = "1"; //按清算日
//        }
        //原来使用页面使用checkbox，id="isBalanceDate" 接收controlDefaultValues的值，isBalanceDate有值(为"0")即为选择了"按清算日"
        //现改为使用type="radio"，通过"${isBalanceDate == '1'}"页面控制
        //打开报表查询页面默认为：按清算日
        if (isBalanceDate == null || isBalanceDate.length() == 0 || isBalanceDate.equals("1")) {
            isBalanceDate = "1"; //按清算日
        } else {
            isBalanceDate = "0"; //按运算日
        }
        return isBalanceDate;
    }
    
    /**
     * 对返回的报表文件记录排序
     * @param reportAttributes
     * @return 
     */
    public static Vector sortReportAttributeRecord(Vector reportAttributes){
	Collections.sort(reportAttributes,new Comparator<ReportAttribute>(){  
		public int compare(ReportAttribute o1, ReportAttribute o2){ 
                        int sortOperationDate = o1.getOperationDate().compareTo(o2.getOperationDate());
                        if (sortOperationDate == 0) {
                            return o1.getFileTypeText().compareTo(o2.getFileTypeText()); //PDF、XLS 正序
                        } else {
                            return (-1)*sortOperationDate; //运营日期倒序
                        }
                }  
		
        });  
	return reportAttributes;
    }
    
    /**
     * 取定制报表的定点日期，如果非定制报表不改day
     * @param day
     * @param sufix
     * @return 
     */
    public String setDayForCustomedReport(String day, String sufix) {
        String result = day;
        
        String reportCode = sufix.substring(1); //去掉.
        Vector<ReportCfgAttr> reportGfgAttrs = reportMapper.getReportGfgAttrs(reportCode);
        if (!reportGfgAttrs.isEmpty()) {
            ReportCfgAttr rCfgAttr = reportGfgAttrs.get(0); //取第一条记录
            String periodType = rCfgAttr.getPeriodType();
            //判断是否定制报表period_type:"9"
            if (periodType.equals(ReportConstant.FLAG_PERIOD_CUSTOMED)) {
                result = rCfgAttr.getGenerateDate().trim(); //定制报表 定点生成，如果为"*"为每天生成不是定制月报
            }
            
        }
        return result;
    }
    
    
    

}
