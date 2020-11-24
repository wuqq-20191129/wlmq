/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.controller;

import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.InOutConstant;
import com.goldsign.acc.frame.constant.ReportConstant;
import com.goldsign.acc.frame.constant.WebConstant;
import com.goldsign.acc.frame.entity.ReportUser;
import com.goldsign.acc.frame.mapper.ReportUserMapper;
import com.goldsign.acc.frame.util.DateUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.util.ReportUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.acc.frame.vo.ReportParameter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Yangze
 */
@Controller
public class TicketStorageReportController extends TicketStorageReportParentController {
    
    private static final String YEARS = "Years"; // 年

    @Autowired
    private ReportUserMapper reportUserMapper;
    
    @RequestMapping(value = "/ticketStorageReportManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = null;
        OperationResult opResult = new OperationResult();
        List reportAttributes = new ArrayList();
        boolean isShow = false;
//        String operatorId = PageControlUtil.getOperatorFromSession(request);
        String moduleID = request.getParameter("ModuleID");
        mv = this.getModelAndView(moduleID, mv);

        
        try {
            String command = request.getParameter("command");
            if (command != null ) {
//                && !"".equals(command = command.trim())
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_REPORT_QUERY)) {
                    ReportParameter reportParameter = this.genReportParameter(request);
                    if (this.isNeedGenerateForAll(request)) {
                        ReportUtil util = new ReportUtil();
                        ReportUser reportUser = this.getDbLoginInfo(reportUserMapper);
                        // 生成PDF报表
                        util.genReportFile(reportParameter.getDestinationName() + ReportUtil.REPORT_PDF_SUFFIX, 
                                reportParameter.getTemplateName(), ReportUtil.PDF, reportParameter.getReportContext(), 
                                reportParameter.getParameters(),reportUser.getDs_user(),reportUser.getDs_pass());
                        // 生成XSL报表
                        util.genReportFile(reportParameter.getDestinationName() + ReportUtil.REPORT_XSL_SUFFIX, 
                                reportParameter.getTemplateName(), ReportUtil.EXCEL, reportParameter.getReportContext(), 
                                reportParameter.getParameters(),reportUser.getDs_user(),reportUser.getDs_pass());
                    }
                    // 生成报表结果
                    reportAttributes = this.genReportAttributeForAll(request, reportParameter);
                    if (this.isShow(reportAttributes)) {
                        isShow = true;
                        opResult.setReturnResultSet(reportAttributes);
                        opResult.addMessage("成功查询" + reportAttributes.size() + "条记录");
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        this.reportDataHandle(request, mv, isShow);
        
        String[] attrNames = this.getAttributeNames(moduleID);
        this.setPageOptionsForReportQuery(attrNames, mv, request, response); //设置报表页面下拉菜单数据
//        this.setPageOptions(mv);
        this.baseHandler(request, response, mv);
        this.SaveOperationResult(mv, opResult);
        return mv;
    }
    
    private ModelAndView getModelAndView(String moduleID, ModelAndView mv) {
        if ("170201".equals(moduleID)) {
            mv = new ModelAndView("/jsp/report/reportCTOut.jsp");
        }  
        else if ("170202".equals(moduleID)) {
            mv = new ModelAndView("/jsp/report/reportCTIn.jsp");
        }
        else if ("170203".equals(moduleID)) {
            mv = new ModelAndView("/jsp/report/reportCTOutInDifference.jsp");
        }
        else if ("170204".equals(moduleID)) {
            mv = new ModelAndView("/jsp/report/reportCTBorrow.jsp");
        }
        else if ("170205".equals(moduleID)) {//库存现状统计
            mv = new ModelAndView("/jsp/report/reportCTStorage.jsp");
        }
        else if ("170206".equals(moduleID)) {
            mv = new ModelAndView("/jsp/report/reportCTStorageList.jsp");
        }
        else if ("170207".equals(moduleID)) {
            mv = new ModelAndView("/jsp/report/reportCTCheck.jsp");
        }
        else if ("170208".equals(moduleID)) {
            mv = new ModelAndView("/jsp/report/reportCTProduce.jsp");
        }
        else if ("170209".equals(moduleID)) {
            mv = new ModelAndView("/jsp/report/reportCTInExchange.jsp");
        }
        else if ("170210".equals(moduleID)) {
            mv = new ModelAndView("/jsp/report/reportCTOutExchange.jsp");
        }
        else if ("170211".equals(moduleID)) {//车票生产报表
            mv = new ModelAndView("/jsp/report/reportCTProduceTicket.jsp");
        }
        else if ("170212".equals(moduleID)) {//记名卡生产报表
            mv = new ModelAndView("/jsp/report/reportCTProduceSignCard.jsp");
        }
        else if ("170213".equals(moduleID)) {//空发卡报表
            mv = new ModelAndView("/jsp/report/reportCTMbProduce.jsp");
        }
        else if ("170102".equals(moduleID)) {        //配票量线路汇总
            mv = new ModelAndView("/jsp/report/reportSTLineDistributes.jsp");
        }
        else if ("170104".equals(moduleID)) {        //回收量线路汇总
            mv = new ModelAndView("/jsp/report/reportSTTicketReclaimLine.jsp");
        }
        else if ("170105".equals(moduleID)) {        //弃票/回收箱储值票上交明细
            mv = new ModelAndView("/jsp/report/reportSTHandinDetail.jsp");
        }
        else if ("170106".equals(moduleID)) {        //弃票/弃票回收箱储值票上交月报
            mv = new ModelAndView("/jsp/report/reportSTHandinMonth.jsp");
        }
        else if ("170301".equals(moduleID)) {         // 流失量统计
            mv = new ModelAndView("/jsp/report/reportOTLoss.jsp");
        }
        else if ("170302".equals(moduleID)) {         // 持有量综合统计
            mv = new ModelAndView("/jsp/report/reportOTHold.jsp");
        }
        else if ("170303".equals(moduleID)) {         // 车票售票量统计
            mv = new ModelAndView("/jsp/report/reportOTSale.jsp");
        }
        else if ("170304".equals(moduleID)) {         // 到货统计
            mv = new ModelAndView("/jsp/report/reportOTBuy.jsp");
        }
        else if ("170305".equals(moduleID)) {         // ES制卡统计
            mv = new ModelAndView("/jsp/report/reportOTESProcuce.jsp");
        }
        
        return mv;
    }
    
    // 页面需要初始化的查询条件
    protected String[] getAttributeNames(String moduleID) {
        if ("170102".equals(moduleID)) {
            String[] attrNames = {STORAGES, IC_CARD_MAIN, IC_CARD_MAIN_SUB, STORAGE_LINES_SERIAL};
            return attrNames;
        }  
        else if ("170104".equals(moduleID)) {
//            String[] attrNames = {STORAGES, IC_CARD_MAIN, IC_CARD_MAIN_SUB, LINES};
            String[] attrNames = {STORAGES, IC_CARD_MAIN, IC_CARD_MAIN_SUB, STORAGE_LINES_SERIAL};
            
            return attrNames;
        } 
        else if ("170105".equals(moduleID)) { //弃票/回收箱储值票上交明细
            String[] attrNames = {STORAGES};
            return attrNames;
        } 
        else if ("170106".equals(moduleID)) { //弃票/弃票回收箱储值票上交月报
            String[] attrNames = {STORAGES,STORAGE_LINES_SERIAL,YEARS};
            return attrNames;
        } 
        else if ("170201".equals(moduleID)) {
//            String[] attrNames = {STORAGES, STORAGE_AREAS, IC_CARD_MAIN, IC_CARD_MAIN_SUB, OUTSTORAGEREASON, OUTSTORAGEFLAG};
            String[] attrNames = {STORAGES, STORAGE_AREAS, IC_CARD_MAIN, IC_CARD_MAIN_SUB, IN_OUT_REASON_PRODUCE};
            
            return attrNames;
        }          
        else if ("170202".equals(moduleID)) {
//            String[] attrNames = {STORAGES, STORAGE_AREAS, IC_CARD_MAIN, IC_CARD_MAIN_SUB, INSTORAGEREASON, INSTORAGEFLAG};
            String[] attrNames = {STORAGES, STORAGE_AREAS, IC_CARD_MAIN, IC_CARD_MAIN_SUB, IN_OUT_REASON_FOR_IN};
            return attrNames;
        } 
        else if ("170203".equals(moduleID)) {
            String[] attrNames = {STORAGES, STORAGE_AREAS, IC_CARD_MAIN, IC_CARD_MAIN_SUB};
            return attrNames;
        }
        else if ("170204".equals(moduleID)) {
            String[] attrNames = {STORAGES, STORAGE_AREAS, IC_CARD_MAIN, 
                IC_CARD_MAIN_SUB, BORROW_UNIT};
            return attrNames;
        }
        else if ("170205".equals(moduleID)) {
            String[] attrNames = {STORAGES, STORAGE_AREAS, IC_CARD_MAIN,IC_CARD_MAIN_SUB};
            return attrNames;
        }
        else if ("170206".equals(moduleID)) {
            String[] attrNames = {STORAGES, IC_CARD_MAIN, IC_CARD_MAIN_SUB};
            return attrNames;
        }
        else if ("170207".equals(moduleID)) {
            String[] attrNames = {STORAGES, STORAGE_AREAS, IC_CARD_MAIN, IC_CARD_MAIN_SUB};
            return attrNames;
        }
        else if("170208".equals(moduleID)){
            String[] attrNames = {STORAGES,IC_CARD_MAIN, IC_CARD_MAIN_SUB};
            return attrNames;
        }
        else if ("170209".equals(moduleID)) {
            String[] attrNames = {STORAGES};
            return attrNames;
        }
        else if ("170210".equals(moduleID)) {
            String[] attrNames = {STORAGES};
            return attrNames;
        }
        else if ("170211".equals(moduleID)) {
            String[] attrNames = {STORAGES, IC_CARD_MAIN, IC_CARD_MAIN_SUB , ES_WORK_TYPES, ES_OPERATORS};
            return attrNames;
        }
        else if("170212".equals(moduleID)){
            String[] attrNames = {IC_CARD_MAIN, IC_CARD_MAIN_SUB};
            return attrNames;
        }
        else if("170213".equals(moduleID)){
            String[] attrNames = {ES_WORK_TYPES};
            return attrNames;
        }
        else if ("170301".equals(moduleID)) {  //流失量统计
            String[] attrNames = {STORAGES,YEARS};
            return attrNames;
        }
        else if ("170302".equals(moduleID)) {  // 持有量综合统计
            String[] attrNames = {STORAGES, IC_CARD_MAIN, IC_CARD_MAIN_SUB};
            return attrNames;
        } 
        else if ("170303".equals(moduleID)) {  // 车票售票量统计
            String[] attrNames = {STORAGES, IC_CARD_MAIN, IC_CARD_MAIN_SUB,YEARS, STORAGE_LINES_SERIAL};
            return attrNames;
        } 
        else if ("170304".equals(moduleID)) {  // 到货统计
            String[] attrNames = {YEARS,STORAGES, IC_CARD_MAIN, IC_CARD_MAIN_SUB};
            return attrNames;
        } 
        else if ("170305".equals(moduleID)) {  // ES制卡统计
            String[] attrNames = {STORAGES, IC_CARD_MAIN, IC_CARD_MAIN_SUB , ES_WORK_TYPES, ES_OPERATORS};
            return attrNames;
        } 
        
        return new String[0];
    }
    
    public void reportDataHandle(HttpServletRequest request, ModelAndView mv, boolean isShow) {
        this.setTemplate(request, mv);
        this.saveQueryCondition(request, mv); //保存查询控件的缺省值
        this.setTableDisplay(isShow,mv); //设置是否显示页面table
        this.setCurrentDate(request, mv); //设置当前日期
    }
    
    public void setTableDisplay(boolean isShow, ModelAndView modelView) {
        String display = "none";
        if (isShow) {
            display = "1";
        }
        modelView.addObject("Display", display);
    }
    
    protected void setTemplate(HttpServletRequest request, ModelAndView mv) {
        String template = request.getParameter(ReportUtil.REPORT_TEMPLATE_NAME);
        if (template != null && !"".equals(template = template.trim())) {
            mv.addObject("Template", template);
//            request.setAttribute("Template", template);
            
        }
    }
    
    protected void setPageOptionsForReportQuery(String[] attrNames, ModelAndView mv, HttpServletRequest request, HttpServletResponse response) {
        
        this.setPageOptions(attrNames, mv, request, response); //StorageOutInBaseController设置报表页面下拉菜单数据
        //只有报表页面才有的数据
        for (String attrName : attrNames) {
            if (attrName.equals(YEARS)) { // 年
                List<String> years = this.getYears(ReportConstant.BEGIN_YEAR);
                mv.addObject(attrName, years);
            }
        }
    }
    
    /**
     * 保存查询控件的缺省值
     * @param request
     * @param modelView 
     */
    public void saveQueryCondition(HttpServletRequest request, ModelAndView modelView) {
        String ControlDefaultValues = null;

        ControlDefaultValues = this.getControlDefaultValues(request);
        if (ControlDefaultValues != null) {
            modelView.addObject(WebConstant.ATT_QUY_CONTROL_DEFAULT_VALUE, ControlDefaultValues);

        }
    }
    
    /**
     * 设置当前日期
     * @param request
     * @param mv 
     */
    private void setCurrentDate(HttpServletRequest request, ModelAndView mv) {
        String currentDate = DateUtil.getCurrentYearMonthDate(InOutConstant.FLAG_DATE);
        if (currentDate != null) {
            mv.addObject("CurrentDate", currentDate);
            
        }
    }

}
