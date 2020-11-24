/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.controller;

import com.goldsign.acc.frame.constant.InOutConstant;
import com.goldsign.acc.frame.constant.ReportConstant;
import com.goldsign.acc.frame.constant.WebConstant;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.entity.ReportUser;
import com.goldsign.acc.frame.mapper.ReportUserMapper;
import com.goldsign.acc.frame.util.DateUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.util.ReportUtil;
import com.goldsign.acc.frame.vo.ReportAttributes;
import com.goldsign.acc.frame.vo.ReportParameter;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Yangze
 */
public class TicketStorageReportParentController extends StorageOutInBaseController {
    
//    protected String moduleID = "";
//    protected String operatorId = "";
    private static final String ERROR_URL ="jsp/showErrorMessage.jsp";
    // 报表PDF和XLS位置
    private static final String REPORT_RELATIVE_PATH = "report/files/";
    // 所有仓库
//    private static final String STORAGE_ALL = "9999";
    // 仓库
//    private static final String STORAGES = "storages";
    // 仓库票区
//    private static final String STORAGE_AREAS = "storageZones";
    // IC票卡主类型
//    private static final String IC_CARD_MAIN = "icCardMainTypes";
    // IC票卡主子类型
//    private static final String IC_CARD_MAIN_SUB = "icCardMainSubTypes";
    // 借票单位
//    private static final String BORROW_UNIT = "borrowUnit";
    // 入库原因
//    private static final String INSTORAGEREASON = "inStorageReason";
    //入库标志位
//    private static final String INSTORAGEFLAG ="1";   
    //出库原因
//    private static final String OUTSTORAGEREASON = "outStorageReason";
    //出库标志位
//    private static final String OUTSTORAGEFLAG = "2";
    //线路
//    private static final String LINES = "lines";
    
    
    
//    private static final String STORAGE_LINES_SERIAL = "storageLinesSerial"; //仓库线路
    
//    private static final String ES_WORK_TYPES = "esWorkTypes";  //工作类型
    
//    private static final String ES_OPERATORS = "esOperators"; //操作员
    
    
    
    // 页面查询条件ID
//    private String[] getParameterNames() {
//        if("170102".equals(moduleID)) {
//            String[] parameterNames = {"p_storage", "p_line_id", "p_bill_no", "p_cardMainCode", "p_cardSubCode", 
//                "p_begin_day", "p_end_day"};
//            return parameterNames;
//        }
//        else if("170104".equals(moduleID)) {
//            String[] parameterNames = {"p_storage_id", "p_line_id", "p_card_main_type", "p_card_sub_type", 
//                "p_begin_day", "p_end_day"};
//            return parameterNames;
//        }
//        else if("170105".equals(moduleID)) {   //弃票/回收箱储值票上交明细
//            String[] parameterNames = {"p_storage", "p_begin_day", "p_end_day"};
//            return parameterNames;
//        }
//        else if("170106".equals(moduleID)) {  //弃票/弃票回收箱储值票上交月报
//            String[] parameterNames = {"p_storage", "p_line","p_begin_day","p_end_day"};
//            return parameterNames;
//        }
////        else if("170201".equals(moduleID)) {
////            String[] parameterNames = {"p_storage", "p_storageZone", "p_cardMainCode", "p_cardSubCode", 
////                "p_outStorageReason", "p_begin_day", "p_end_day"};
////            return parameterNames;
////        }
//        //testtttttttttttttttttttttttttttttttttt
//        else if("170201".equals(moduleID)) {
//            String[] parameterNames = {"p_storage_id", "p_area_id", "p_card_main_type", "p_card_sub_type", 
//                "p_out_reason_id", "p_begin_day", "p_end_day"};
//            return parameterNames;
//        }
//        else if("170202".equals(moduleID)) {
//            String[] parameterNames = {"p_storage_id", "p_area_id", "p_card_main_type", "p_card_sub_type", 
//                "p_in_reason_id", "p_begin_day", "p_end_day"};
//            return parameterNames;
//        }
//        else if ("170203".equals(moduleID)) {
//            String[] parameterNames = {"p_storage_id", "p_area_id", "p_card_main_type", 
//                "p_card_sub_type", "p_bill_no", "p_adjust_reason_id", "p_begin_day", "p_end_day"};
//            return parameterNames;
//        }
//        else if ("170204".equals(moduleID)) {
//            String[] parameterNames = {"p_storage", "p_storageZone", "p_cardMainCode", 
//                "p_cardSubCode", "p_borrowUnitId", "p_returnFlag", "p_begin_day", "p_end_day"};
//            return parameterNames;
//        }
//        else if ("170205".equals(moduleID)) {
//            String[] parameterNames = {"p_storage_id", "p_area_id", "p_card_main_type", 
//                "p_card_sub_type", "p_begin_day"};
//            return parameterNames;
//        }
//        else if ("170206".equals(moduleID)) {
//            String[] parameterNames = {"p_storage", "p_cardMainCode", "p_cardSubCode", "p_begin_day"};
//            return parameterNames;
//        }
//        else if ("170207".equals(moduleID)) {
//            String[] parameterNames = {"p_storage_id", "p_area_id", "p_card_main_type", 
//                "p_card_sub_type", "p_begin_day", "p_end_day"};
//            return parameterNames;
//        }
//        else if ("170208".equals(moduleID)) {
//            String[] parameterNames = {"p_begin_day", "p_end_day", "p_card_main_type","p_card_sub_type", "p_storage_id"};
//            return parameterNames;
//        }
//        else if ("170209".equals(moduleID)) {
//            String[] parameterNames = {"p_storage_id", "p_begin_day", "p_end_day"};
//            return parameterNames;
//        }else if ("170210".equals(moduleID)) {
//            String[] parameterNames = {"p_storage_id", "p_begin_day", "p_end_day"};
//            return parameterNames;
//        }
//        else if ("170211".equals(moduleID)) {
//            String[] parameterNames = {"p_begin_day", "p_end_day", "p_card_main_type", "p_card_sub_type", 
//                                       "p_storage_id", "p_operator_id", "p_device_id", "p_worktype_id"};
//            return parameterNames;
//        }
//        else if ("170212".equals(moduleID)) {
//            String[] parameterNames = {"p_begin_day", "p_end_day", "p_card_main_type","p_card_sub_type"};
//            return parameterNames;
//        }
//        else if ("170213".equals(moduleID)) {
//            String[] parameterNames = {"p_begin_day", "p_end_day", "p_worktype_id"};
//            return parameterNames;
//        }
//        else if("170301".equals(moduleID)) {  // 流失量统计
//            String[] parameterNames = {"p_year","p_storage_id"};
//            return parameterNames;
//        }
//        else if("170302".equals(moduleID)) {  // 持有量综合统计
//            String[] parameterNames = {"p_storage",  "p_cardMainCode", "p_cardSubCode", "p_begin_day"};
//            return parameterNames;
//        }
//        else if("170303".equals(moduleID)) {  // 车票售票量统计
//            String[] parameterNames = {"p_storage",  "p_cardMainCode", "p_cardSubCode", "p_begin_day","p_line_id"};
//            return parameterNames;
//        }
//        else if("170303".equals(moduleID)) {  // 到货统计
//            String[] parameterNames = {"p_begin_day","p_end_day","p_storage",  "p_cardMainCode", "p_cardSubCode"};
//            return parameterNames;
//        }
//         else if("170305".equals(moduleID)) {  // ES制卡统计
//            String[] parameterNames = {"p_begin_day", "p_end_day", "p_cardMainCode", "p_cardSubCode", "p_storage", "p_esOperator", "p_device_id", "p_worktype"};
//            return parameterNames;
//        }
//        return new String[0];
//    }
    
//    protected void saveTemplate(HttpServletRequest request) {
//        String template = request.getParameter(ReportUtil.REPORT_TEMPLATE_NAME);
//        if (template != null && !"".equals(template = template.trim())) {
//            HttpSession session = request.getSession();
//            session.setAttribute(moduleID, template);
//        }
//    }
    
    
    
    
    
    
    
//    protected void setPageOptions(ModelAndView mv) {
//        List<PubFlag> options;
//        String[] attrNames = getAttributeNames();
//        for (String attrName : attrNames) {
//            if (attrName.equals(STORAGES)) {//仓库
//                options = this.getStorages(operatorId);
//                mv.addObject(attrName, options);
//            }
//            else if (attrName.equals(STORAGE_AREAS)) {//仓库票区
//                options = pubFlagMapper.getStoragesAreas();
//                String MainSubs = FormUtil.getMainSubs(options);
//                mv.addObject(attrName, MainSubs);
//            }
//            else if (attrName.equals(IC_CARD_MAIN)) {//IC票卡主类型
//                options = pubFlagMapper.getCardMainTypesForIc();
//                mv.addObject(attrName, options);
//            }
//            else if (attrName.equals(IC_CARD_MAIN_SUB)) {//IC票卡主子类型
//                options = pubFlagMapper.getCardSubTypesForIc();
//                String cardMainSubs = FormUtil.getMainSubs(options);
//                mv.addObject(attrName, cardMainSubs);
//            }
////            else if (attrName.equals(INSTORAGEREASON)) {//入库原因
////                options = pubFlagMapper.getInOutReasonByFlag(INSTORAGEFLAG);
////                mv.addObject(attrName, options);
////            }
////            else if (attrName.equals(OUTSTORAGEREASON)) {//出库原因
////                options = pubFlagMapper.getInOutReasonByFlag(OUTSTORAGEFLAG);
////                mv.addObject(attrName, options);
////            } 
//            else if (attrName.equals(BORROW_UNIT)) {//借票单位
//                options = pubFlagMapper.getBorrowUnits();
//                mv.addObject(attrName, options);
//            }
//            else if (attrName.equals(LINES)) {//线路
//                options = pubFlagMapper.getLines(operatorId);
//                mv.addObject(attrName, options);
//            }
//            else if (attrName.equals(YEARS)) { // 年
//                List<String> years = this.getYears(ReportConstant.BEGIN_YEAR);
//                mv.addObject(attrName, years);
//            }
//            else if (attrName.equals(STORAGE_LINES_SERIAL)) {//仓库线路串
//                options = pubFlagMapper.getStorageLines();
//                String MainSubs = FormUtil.getMainSubs(options);
//                mv.addObject(attrName, MainSubs);
//            }
//            else if (attrName.equals(ES_WORK_TYPES)) {//ES工作类型
//                options = pubFlagMapper.getEsWorkTypes();
//                mv.addObject(attrName, options);
//            }
//            else if (attrName.equals(ES_OPERATORS)) {//ES操作员
//                options = pubFlagMapper.getEsOperators();
//                mv.addObject(attrName, options);
//            }
//   
//        }
//    }
    
    protected ReportParameter genReportParameter(HttpServletRequest request) throws Exception {
            String operatorId = PageControlUtil.getOperatorFromSession(request);

            ReportParameter rp = new ReportParameter();
            String templateName = this.RPGetTemplateName(request);
            HashMap params = this.RPGetReportParameters(request,operatorId );
            String reportContext  = this.RAGetReportUrlContext(request);

            rp.setDestinationName(this.RPGetDestinationFileName(request, templateName, operatorId));
            rp.setTemplateName(templateName);
            this.processParameterDate(params);
            rp.setParameters(params);
            rp.setReportContext(reportContext);
            return rp;
    }
    
    // 获取报表模板名称
    private String RPGetTemplateName(HttpServletRequest request) throws Exception {
//        HttpSession session = request.getSession();
//		String tempName = (String) session.getAttribute(moduleID);
                String tempName = request.getParameter(ReportUtil.REPORT_TEMPLATE_NAME);
//                String tempName = (String) request.getAttribute(ReportUtil.REPORT_TEMPLATE_NAME);
		if (tempName == null || tempName.trim().length() == 0) {
            throw new Exception("缺少生成报表的模板名称");
        }
		return tempName;
	}
    
    // 获取报表参数
//    private HashMap RPGetReportParameters(HttpServletRequest request) throws Exception {
//        HashMap parameters = new HashMap();
//        parameters.put("operatorId", operatorId);
//        
//        String[] parameterNames = this.getParameterNames();
//        for (String name : parameterNames) {
//            String value = request.getParameter(name);
//            if (value == null || value.trim().length() == 0) {
//                if(name.toLowerCase().equals("p_storage_id")){
//                    List<PubFlag> storages = this.getStorages(operatorId);
//                    int i = 0;
//                    for (PubFlag pf : storages) {
//                        i++;
//                        value += pf.getCode();
//                        if (i < storages.size()) {
//                            value += "','";
//                        }
//                    }
//                }
//                else {
//                    value = "";
//                }
//            }
//            parameters.put(name, value);
//        }
//        return parameters;
//    }
    
    // 获取报表参数
    private HashMap RPGetReportParameters(HttpServletRequest request, String operatorId) throws Exception {
        String result = "";
        String parameterNames = request.getParameter("parameterNames");
        HashMap parameters = new HashMap();

        if (parameterNames == null || parameterNames.length() == 0)
            return parameters;
        StringTokenizer st = new StringTokenizer(parameterNames, "#");
        String name = "";
        String value = "";
        parameters.put("operatorId", operatorId);
        while (st.hasMoreTokens()) {
                name = st.nextToken();
                value = request.getParameter(name);
                if (value == null || value.trim().length() == 0){
                    if(name.toLowerCase().equals("p_storage_id")){
                        List<PubFlag> storages = this.getStorages(operatorId);
                            int i = 0;
                            for (PubFlag pf : storages) {
                                i++;
                                value += pf.getCode();
                                if (i < storages.size()) {
                                    value += "','";
                                }
                            }
                    }else{
                        value = "";
                    }
                }
                parameters.put(name, value);
        }

        return parameters;
    }
    
//    private List<PubFlag> getStorages(String operatorId) {
//        List<PubFlag> storagesAll = pubFlagMapper.getStoragesAll();
//        List<PubFlag> storagesOperator = pubFlagMapper.getStoragesForOperator(operatorId);
//        if (this.isAllStorages(storagesOperator)) {
//            return storagesAll;
//        }
//        return this.getStoragesByOperator(storagesAll, storagesOperator);
//    }
    
    private boolean isInList(List<PubFlag> list, String str) {
        for (PubFlag pf : list) {
            if (str.equals(pf.getCode())) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isAllStorages(List<PubFlag> storagesOperator) {
        return isInList(storagesOperator, STORAGE_ALL);
    }
    
    private List<PubFlag> getStoragesByOperator(List<PubFlag> storagesAll, List<PubFlag> storagesOperator) {
        List<PubFlag> list = new ArrayList();
        for (PubFlag pf : storagesAll) {
            if (this.isInStoragesOperator(pf, storagesOperator)) {
                list.add(pf);
            }
        }
        return list;
    }
    
    private boolean isInStoragesOperator(PubFlag pf, List<PubFlag> storagesOperator) {
        return isInList(storagesOperator, pf.getCode());
    }
    
    // 生成报表存放路径及名称，名称=报表模板+查询条件，使用"."作为分隔符
    private String RPGetDestinationFileName(HttpServletRequest request, String templateName, String operatorId) throws Exception {
            String desFileName = this.RPGetDestinationPath(request) + 
                templateName + this.RPGetReportParamtersText(request, operatorId);
            return desFileName;
    }
    
    private String RPGetDestinationPath(HttpServletRequest request) throws Exception {
            String destPath = this.getContextRealPath(request);
            String moduleID = request.getParameter("ModuleID");
            destPath += "/" + REPORT_RELATIVE_PATH + moduleID + "/";
            this.createDir(destPath);
            return destPath;
    }
    
    private String getContextRealPath(HttpServletRequest request) {
            String contextPath = request.getSession().getServletContext().getRealPath(request.getContextPath());
            String pathDelim = System.getProperty("file.separator");
            int index = contextPath.lastIndexOf(pathDelim);
            contextPath = contextPath.substring(0, index);
            return contextPath;
    }
    
    private void createDir(String destPath) {
		File path = new File(destPath);
		if (!path.exists()) {
            path.mkdirs();
        }
	}
    
//    private String RPGetReportParamtersText(HttpServletRequest request) {
//        String text = "";
//        String[] parameterNames = this.getParameterNames();
//        for (String name : parameterNames) {
//            String value = request.getParameter(name);
//            if (value == null || value.trim().length() == 0) {
//				continue;
//			}
//            if (this.isDateControl(name)) {
//                value = this.trimDate(value);
//            }
//			text += "." + value;
//        }
//        text = "." + operatorId + text;
//		return text;
//    }
    
    private String RPGetReportParamtersText(HttpServletRequest request, String operatorId) {
        String parameterNames = request.getParameter("parameterNames");


        if (parameterNames == null || parameterNames.length() == 0)
                return "";
        StringTokenizer st = new StringTokenizer(parameterNames, "#");
        String name = "";
        String value = "";
        String text = "";
        while (st.hasMoreTokens()) {
                name = st.nextToken();
                value = request.getParameter(name);
                if (value == null || value.trim().length() == 0){
                        value = "";
                        continue;
                }
                if (this.isDateControl(name))
                        value = this.trimDate(value);//value.replaceAll("-","");
                text += "." + value;
        }

        text = "." + operatorId + text;

        return text;
    }
    
    private boolean isDateControl(String controlName) {
        for (String dateName : ReportUtil.DATE_CONTROLS) {
            if (dateName.equals(controlName)) {
                return true;
            }
        }
		return false;
	}
    
    private String trimDate(String date) {
		if (date == null || date.length() == 0) {
            return "";
        }
		return date.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
	}
    
    private void processParameterDate(HashMap params) {
        if (params.isEmpty()) {
            return;
        }
        for (String dateName : ReportUtil.DATE_CONTROLS) {
            if (params.containsKey(dateName)) {
                String date = (String) params.get(dateName);
                date = this.trimDate(date);
                params.put(dateName, date);
            }
        }
    }
    
    protected ReportUser getDbLoginInfo(ReportUserMapper mapper) {
        ReportUser user = mapper.getReportUser(ReportConstant.REPORT_DS_ID_TK);
        ReportUtil util = new ReportUtil();
        user.setDs_pass(util.getPass(user.getDs_pass()));
        return user;
    }
    
    protected boolean isNeedGenerateForAll(HttpServletRequest request) {
		String bufferFlag = request.getParameter("bufferFlag");
		return bufferFlag == null || bufferFlag.length() == 0;
	}
    
    protected List genReportAttributeForAll(HttpServletRequest request, ReportParameter reportParameter) throws Exception {
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        
        List reportAttributes = new ArrayList();
        String templateName = reportParameter.getTemplateName();
        String errorURL = reportParameter.getReportContext() + ERROR_URL + "?Error=";
        
        ReportAttributes ra = new ReportAttributes();
        ra.setReportURL(this.RAGetReportUrl(request, templateName, operatorId) + ReportUtil.REPORT_PDF_SUFFIX);
        ra.setQueryDate(this.RAGetQueryDate(request));
        if (!this.isExistFile(reportParameter, ReportUtil.PDF)) {
                String listName = "PDF报表还没生成";
                ra.setReportURL(errorURL + listName);
                ra.setListName(listName);
        }
        else {
            ra.setListName("PDF报表");
        }
        reportAttributes.add(ra);

        ra = new ReportAttributes();
        ra.setReportURL(this.RAGetReportUrl(request, templateName, operatorId) + ReportUtil.REPORT_XSL_SUFFIX);
        ra.setQueryDate(this.RAGetQueryDate(request));
        if (!this.isExistFile(reportParameter, ReportUtil.EXCEL)) {
            String listName = "XLS报表还没生成";
            ra.setReportURL(errorURL + listName);
			ra.setListName(listName);
		}
        else {
            ra.setListName("XLS报表");
        }
        reportAttributes.add(ra);
        return reportAttributes;
    }
    
    private String RAGetReportUrl(HttpServletRequest request, String templateName, String operatorId) throws Exception {
        String moduleID = request.getParameter("ModuleID");
        String reportUrl = "";
		String reportContext = this.RAGetReportUrlContext(request);
		reportUrl += reportContext;
		reportUrl += REPORT_RELATIVE_PATH + moduleID + "/";
		reportUrl += templateName + this.RPGetReportParamtersText(request, operatorId);
		return reportUrl;
	}
    
    private String RAGetReportUrlContext(HttpServletRequest request) throws Exception {
//        String reqUrl = request.getRequestURL().toString();
//        String contextPath = request.getContextPath();
//        int index = reqUrl.indexOf(contextPath);
//        index = reqUrl.indexOf("/", index + 1);
//        return reqUrl.substring(0, index + 1);
        String reportUrl = "";
        String reqUrl = request.getRequestURL().toString();
        String contextPath = request.getContextPath();

        int index = reqUrl.indexOf(contextPath);
        index = reqUrl.indexOf("/", index + 1);
        reportUrl = reqUrl.substring(0, index + 1);
        return reportUrl;
    }
    
//    private String RAGetQueryDate(HttpServletRequest request) {
//        String beginDateName = ReportUtil.DATE_CONTROLS[0];
//		String endDateName = ReportUtil.DATE_CONTROLS[1];
//        String beginDate = request.getParameter(beginDateName);
//		String endDate = request.getParameter(endDateName);
//		String conSymbol = "";
//        if (beginDate == null || beginDate.length() == 0) {
//            beginDate = "";
//        }
//		if (endDate == null || endDate.length() == 0) {
//            endDate = "";
//        }
//		beginDate = this.trimDate(beginDate);
//		endDate = this.trimDate(endDate);
//		if (beginDate.length() != 0 && endDate.length() != 0) {
//            conSymbol = "-";
//        }
//		return beginDate + conSymbol + endDate;
//    }
    
    private String RAGetQueryDate(HttpServletRequest request) {
            String queryDateNames = request.getParameter("QueryDateNames");
            StringTokenizer st = new StringTokenizer(queryDateNames,"#");
            int i=0;
            String beginDateName ="";
            String endDateName ="";

            while(st.hasMoreTokens()){
                    if(i==0)
                            beginDateName= st.nextToken();
                    if(i==1)
                            endDateName = st.nextToken();
                    i++;

            }
            String beginDate = request.getParameter(beginDateName);
            String endDate = request.getParameter(endDateName);
            String conSymbol = "";
            if (beginDate == null || beginDate.length() == 0)
                    beginDate = "";
            if (endDate == null || endDate.length() == 0)
                    endDate = "";
            beginDate = this.trimDate(beginDate);//beginDate.replaceAll("-","");
            endDate = this.trimDate(endDate);//endDate.replaceAll("-","");

            if (endDate.length() != 0&&beginDate.length()!=0)
                    conSymbol = "-";
            return beginDate + conSymbol + endDate;

    }
    
    private boolean isExistFile(ReportParameter reportParameter, String type) {
		String destFile = reportParameter.getDestinationName();
		if (destFile == null || destFile.length() == 0) {
            return false;
        }
        if (type.equals(ReportUtil.PDF)) {
            String pdfDestFile = destFile + ReportUtil.REPORT_PDF_SUFFIX;
            File filePdf = new File(pdfDestFile);
            if (filePdf.exists()) {
                return true;
            }
        }
        if (type.equals(ReportUtil.EXCEL)) {
            String xslDestFile = destFile + ReportUtil.REPORT_XSL_SUFFIX;
            File fileXls = new File(xslDestFile);
            if (fileXls.exists()) {
                return true;
            }
        }
		return false;
	}
    
    protected boolean isShow(List reportAttributes) {
        if (reportAttributes == null || reportAttributes.isEmpty()) {
            return false;
        }
        for (int i = 0; i < reportAttributes.size(); i++) {
            ReportAttributes ra = (ReportAttributes) reportAttributes.get(i);
            if (ra.getReportURL() == null) {
                return false;
            }
        }
        return true;
    }
    
    
    protected List<String> getYears(String beginYear){
        List<String> years = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String currYear = dateFormat.format(date);
        for(int i = Integer.parseInt(beginYear) ; i<= Integer.parseInt(currYear) ; i++ ){
            years.add(Integer.toString(i));
        }
        return years;
    }
    
    /**
     * 返回查询控件的缺省值
     * @param request
     * @return 
     */
    public String getControlDefaultValues(HttpServletRequest request) {
            String result = "";
            String controlNames = request.getParameter("ControlNames");
            if (controlNames == null || controlNames.length() == 0)
                    return "";
            StringTokenizer st = new StringTokenizer(controlNames, "#");
            String name = "";
            String value = "";
            while (st.hasMoreTokens()) {
                    name = st.nextToken();
                    value = request.getParameter(name);
//                    if (name.equals("reportFlag")) {
//                            if (value != null)
//                                    value = "0";//Radio控件为选上
//                    }
                    if (name.equals("bufferFlag")) {
                            if (value != null)
                                    value = "0";//Radio控件为选上
                    }
                    result += name + "#" + value + ";";
            }
            if (result.length() != 0)
                    result = result.substring(0, result.length());
            return result;
    }
    
    
    
}
