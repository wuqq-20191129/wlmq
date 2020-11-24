/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.controller;

import com.goldsign.acc.app.opma.entity.ReportCfgModuleMapping;
import com.goldsign.acc.app.opma.entity.ReportQryCfg;
import com.goldsign.acc.app.opma.entity.SysModule;
import com.goldsign.acc.app.opma.mapper.ReportQryCfgMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.constant.ReportConstant;
import com.goldsign.acc.frame.controller.BaseController;

import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.FileUtil;

import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 报表查询配置
 * @author mqf
 */
@Controller
public class ReportQryCfgController extends BaseController {
    
    Logger logger = Logger.getLogger(ReportQryCfgController.class);

    @Autowired
    private ReportQryCfgMapper reportQryCfgMapper;
    
    //统计日期、按清算日、年、月、线路、大线路、票卡主类型、票卡子类型
//    public enum QryConCodeType {date, isBalanceDate, year, month, lineID, lineIDLarge, mainType, subType};
    public enum QryConCodeType {date, dateCur, isBalanceDate, year, month, lineID, lineIDLarge, mainType, subType};
    


    @RequestMapping("/ReportQryCfg")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/opma/report_qry_cfg.jsp");
        
        
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.reportQryCfgMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.reportQryCfgMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.reportQryCfgMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_GEN_FILE))//生成JSP文件操作
                {
                    opResult = this.genFile(request, response,this.reportQryCfgMapper, this.operationLogMapper);
                }

                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.reportQryCfgMapper, this.operationLogMapper);

                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        ) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.reportQryCfgMapper, this.operationLogMapper, opResult);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {"reportModules","reportModuleNames"};

        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.getResultSetText((List<ReportQryCfg>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }
    
    
    protected void setPageOptions(String[] attrNames, ModelAndView mv, HttpServletRequest request, HttpServletResponse response) {

        List<PubFlag> options;
        for (String attrName : attrNames) {
            if (attrName.equals("reportModules")) {
                options = pubFlagMapper.getReportModules();
                mv.addObject(attrName, options);
                
            }
            
            if (attrName.equals("reportModuleNames")) {
//                options = pubFlagMapper.getReportModuleNames();
                options = pubFlagMapper.getReportModules();
                String reportModuleNames = this.getReportModuleNames(options);
                mv.addObject(attrName, reportModuleNames);
                
            }
            
        }
    }
    
    //用于明细栏 显示报表名称中文
    public static String getReportModuleNames(List<PubFlag> reportModuleNamesList){
        String reportModuleNames ="";
        for(PubFlag list:reportModuleNamesList){
//            reportModuleNames =reportModuleNames+list.getCode_type()+","+list.getCode()+","+list.getCode_text()+":";
            reportModuleNames =reportModuleNames+list.getCode()+","+list.getCode_text()+":";
        }
        return reportModuleNames;
        
    }

    private void getResultSetText(List<ReportQryCfg> resultSet, ModelAndView mv) {
        List<PubFlag> reportModules = (List<PubFlag>) mv.getModelMap().get("reportModules");

        for (ReportQryCfg rqc : resultSet) {
            if (reportModules != null && !reportModules.isEmpty()) {
                rqc.setReport_name(DBUtil.getTextByCode(rqc.getReport_module(), reportModules));
            }

        }

    }

    private ReportQryCfg getQueryConditionForOp(HttpServletRequest request) {

        ReportQryCfg qCon = new ReportQryCfg();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            
            qCon.setReport_module(FormUtil.getParameter(request, "d_report_module"));
            
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                
                qCon.setReport_module(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_report_module"));
                qCon.setReport_name(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_report_name"));
            }
        }

        return qCon;
    }

    private ReportQryCfg getQueryCondition(HttpServletRequest request) {
        ReportQryCfg qCon = new ReportQryCfg();
        
        qCon.setReport_module(FormUtil.getParameter(request, "q_report_module"));
        qCon.setReport_name(FormUtil.getParameter(request, "q_report_name"));

        return qCon;
    }

    public OperationResult query(HttpServletRequest request, ReportQryCfgMapper rqcMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        ReportQryCfg queryCondition;
        List<ReportQryCfg> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = rqcMapper.getReportQryCfgs(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult queryForOp(HttpServletRequest request, ReportQryCfgMapper rqcMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        // OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        ReportQryCfg queryCondition;
        List<ReportQryCfg> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request);
            if (queryCondition == null) {
                return null;
            }
            resultSet = rqcMapper.getReportQryCfgs(queryCondition);
            opResult.setReturnResultSet(resultSet);
            //or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult modify(HttpServletRequest request, ReportQryCfgMapper rqcMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        ReportQryCfg poForDel = new ReportQryCfg();
        poForDel.setReport_module(FormUtil.getParameter(request, "d_report_module"));
        
        Vector<ReportQryCfg> posForAdd = this.getReqAttribute(request);
                
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "报表查询配置：" + "报表模板" + poForDel.getReport_module() +  ":";
        try {
            n = this.modifyByTrans(request, rqcMapper, poForDel, posForAdd);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(ReportQryCfg po, ReportQryCfgMapper rqcMapper, OperationLogMapper opLogMapper) {
        List<ReportQryCfg> list = rqcMapper.getReportQryCfgById(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private int addByTrans(HttpServletRequest request, ReportQryCfgMapper rqcMapper, Vector<ReportQryCfg> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            for (ReportQryCfg po : pos) {
                n += rqcMapper.addReportQryCfg(po);
            }

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }


    private int modifyByTrans(HttpServletRequest request, ReportQryCfgMapper rqcMapper, ReportQryCfg poForDel, Vector<ReportQryCfg> posForAdd) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            //先删除原有记录
            int delNum = rqcMapper.deleteReportQryCfg(poForDel);
            //增加记录
            for (ReportQryCfg po : posForAdd) {
                n += rqcMapper.addReportQryCfg(po);
            }
            
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private int deleteByTrans(HttpServletRequest request, ReportQryCfgMapper rqcMapper, Vector<ReportQryCfg> pos) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {


            status = txMgr.getTransaction(this.def);
            for (ReportQryCfg po : pos) {
                n += rqcMapper.deleteReportQryCfg(po);
            }

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult add(HttpServletRequest request, ReportQryCfgMapper rqcMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<ReportQryCfg> pos = this.getReqAttribute(request);
//        List<ReportQryCfg> list = convertToReportQryCfgList(po);
       // User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        String checkMsg = this.checkData(pos);
        if (!checkMsg.equals("")) {
            rmsg.addMessage(checkMsg);
            return rmsg;
        }
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "报表查询配置：" + "报表模板" + pos.get(0).getReport_module() +  ":";
        try {
            if (this.existRecord(pos.get(0), rqcMapper, opLogMapper)) {
                rmsg.addMessage("增加记录已存在！");
                return rmsg;
            }

            // n = rqcMapper.addReportQryCfg(po);
            
            n = this.addByTrans(request, rqcMapper, pos);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);
            
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }
    
//    private List convertToReportQryCfgList(ReportQryCfg po) {
////        List<ReportQryCfg> list = new ArrayList<ReportQryCfg>();
//        String qryConCodeString = po.getQryConCodeString();
//        String qryConPosString = po.getQryConPosString();
//                
//        StringTokenizer st = new StringTokenizer(qryConCodeString, ";");
//        String strqryConCode = null;
//        Vector<ReportQryCfg> rqcs = new Vector();
//        ReportQryCfg rqc;
//        while (st.hasMoreTokens()) {
//            strqryConCode = st.nextToken();
//            rqcs.add(rqc);
//        }
//        return rqcs;
//        
//    }

    public OperationResult delete(HttpServletRequest request, ReportQryCfgMapper rqcMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<ReportQryCfg> pos = this.getReqAttributeForDelete(request);
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, rqcMapper, pos);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public Vector<ReportQryCfg> getReqAttribute(HttpServletRequest request) {
        
        Vector<ReportQryCfg> rqcs = new Vector();
        
        ReportQryCfg po1 = new ReportQryCfg();
//
//        po1.setReport_module(FormUtil.getParameter(request, "d_report_module"));
//        po1.setReport_name(FormUtil.getParameter(request, "d_report_name"));
//        if ("1".equals(FormUtil.getParameter(request, "d_date"))) {
//            po1.setQry_con_code(QryConCodeType_date);
//            po1.setQry_con_pos(FormUtil.getParameter(request, "d_date_pos"));
//        }
//        
//        ReportQryCfg po2 = new ReportQryCfg();
//
//        po2.setReport_module(FormUtil.getParameter(request, "d_report_module"));
//        po2.setReport_name(FormUtil.getParameter(request, "d_report_name"));
//        po2.setQry_con_code(FormUtil.getParameter(request, "d_statDate"));
//        po2.setQry_con_pos(FormUtil.getParameter(request, "d_statDate_pos"));
//        
//        rqcs.add(po1);
//        rqcs.add(po2);
        
        for (QryConCodeType type :QryConCodeType.values()) {
            String checkValue = FormUtil.getParameter(request, "d_"+type.name());
            if ("0".equals(checkValue)) {
                ReportQryCfg po = new ReportQryCfg();
                po.setQry_con_code(type.name());
                po.setQry_con_pos(FormUtil.getParameter(request, "d_"+type.name()+"_pos"));
                
                po.setReport_module(FormUtil.getParameter(request, "d_report_module"));
//                po.setReport_name(FormUtil.getParameter(request, "d_report_name"));
        
                rqcs.add(po);
            }
        }
        
        if (rqcs.isEmpty()) {
            ReportQryCfg po = new ReportQryCfg();
            po.setReport_module(FormUtil.getParameter(request, "d_report_module"));
//            po.setReport_name(FormUtil.getParameter(request, "d_report_name"));
            rqcs.add(po);
        }
        
        

//        ReportQryCon reportQryCon1 = new ReportQryCon();
//        reportQryCon1.setQry_con_code(FormUtil.getParameter(request, "d_statDate"));
//        reportQryCon1.setQry_con_name(FormUtil.getParameter(request, "d_statDate_pos"));
//        
//        ReportQryCon reportQryCon2 = new ReportQryCon();
//        reportQryCon2.setQry_con_code(FormUtil.getParameter(request, "d_statDate"));
//        reportQryCon2.setQry_con_name(FormUtil.getParameter(request, "d_statDate_pos"));
//                
//        po.getQryConCodes().add(reportQryCon1);
//        po.getQryConCodes().add(reportQryCon2);
        
        
        
        
//        po.setQryConPosString(FormUtil.getParameter(request, "d_qryConPosString"));
                
        return rqcs;
    }



    private Vector<ReportQryCfg> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<ReportQryCfg> rqcs = new Vector();
        ReportQryCfg rqc;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            rqc = this.getReportQryCfg(strIds, "#");
            rqcs.add(rqc);
        }
        return rqcs;

    }
    
    private Vector<ReportQryCfg> getGenFileIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<ReportQryCfg> rqcs = new Vector();
        ReportQryCfg rqc;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            rqc = this.getReportQryCfg(strIds, "#");
            rqcs.add(rqc);
        }
        return rqcs;

    }

    private ReportQryCfg getReportQryCfg(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        ReportQryCfg rqc = new ReportQryCfg();
        Vector<ReportQryCfg> rqcs = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                rqc.setReport_module(tmp);
                continue;
            }

        }
        return rqc;

    }

    private Vector<ReportQryCfg> getReqAttributeForDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<ReportQryCfg> selectedItems = this.getDeleteIDs(selectIds);

        return selectedItems;
    }
    
    /**
     * 生成JSP文件
     * @param request
     * @param rqcMapper
     * @param opLogMapper
     * @return
     * @throws Exception 
     */
    public OperationResult genFile(HttpServletRequest request,HttpServletResponse response, ReportQryCfgMapper rqcMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<ReportQryCfg> pos = this.getReqAttributeForGenFile(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            List<ReportQryCfg> rqcs = rqcMapper.getReportQryCfgsForGenFile(pos);
            List<ReportCfgModuleMapping> rcmms = rqcMapper.getReportModuleDess(pos); 
            List<SysModule> smsForTitle = rqcMapper.getSysModulesForReportTitle();
            List<SysModule> smsForActionName = rqcMapper.getSysModulesForActionName();
            
            String appRoot = request.getSession().getServletContext().getRealPath("/");
            
            for (ReportQryCfg rqc : rqcs) {
                String reportModule = rqc.getReport_module();
                String qryConCodeString = rqc.getQryConCodeString();
                String qryConPosString = rqc.getQryConPosString();
                
                ReportCfgModuleMapping reportCfgModuleMapping = getSingleReportModuleDes(rcmms,reportModule);
//                SysModule sysModule = getSingleSysModule(sms,reportCfgModuleMapping);
                //报表标题
//                String[] titleArray = getTitlesForReport(rcmms,smsForTitle,reportModule);
                String[] titleArray = getTitlesForReport(smsForTitle, reportCfgModuleMapping);
                //action名
                String actionName = getActionName(smsForActionName, reportCfgModuleMapping);
                //关键字段 ControlNames、ReportType
                String[] cnAndrtAttry = getControlNamesAndReportType(qryConCodeString);
                
                //获取查询条件JSP代码
                StringBuffer sbReportQtyCon = getALLReportQryCon(reportModule, qryConCodeString, qryConPosString); 
                //获取JSP文件内容
                StringBuffer fileContent = getContentFromTemplate(appRoot, sbReportQtyCon, titleArray, actionName, cnAndrtAttry);
                //生成jsp文件
                this.genReportQtyFile(appRoot, actionName, fileContent); 
                n++;
            }


        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_GEN_FILE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_GEN_FILE, request, LogConstant.genFileSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.genFileSuccessMsg(n));

        return rmsg;
    }
    
    private Vector<ReportQryCfg> getReqAttributeForGenFile(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<ReportQryCfg> selectedItems = this.getGenFileIDs(selectIds);

        return selectedItems;
    }
    
    private StringBuffer getALLReportQryCon(String reportModule, String qryConCodeString, String qryConPosString) throws Exception {
        StringBuffer sb = new StringBuffer("");
        String[] qryConCodeArray = qryConCodeString.split(";");
        String[] qryConPosArray = qryConPosString.split(";");
        String qryConCode = null;
        String qryConPos = null;
        Vector<ReportQryCfg> rqcs = new Vector();
        if (qryConCodeArray.length != qryConPosArray.length) {
            throw new Exception();
        }
        String lastQryConPosX = null;
        //查询条件是否选择 月份
        boolean isHasMonth = this.isHasMonth(qryConCodeArray);
//        sb.append(ReportConstant.TEMPLATE_NAME_TR_BEGIN);
        for (int i =0; i<qryConCodeArray.length; i++) {
            qryConCode = qryConCodeArray[i];
            qryConPos = qryConPosArray[i];
            String[] qryConPosSubArray = qryConPos.split(",");
            String qryConPosX = qryConPosSubArray[0];
            if (lastQryConPosX != null  && !qryConPosX.equals(lastQryConPosX)) {
                    sb.append(ReportConstant.TEMPLATE_NAME_TR_END);
                    sb.append(ReportConstant.TEMPLATE_NAME_TR_BEGIN);
            }
            sb.append(this.getReportQtyConTemplate(qryConCode,isHasMonth));
            lastQryConPosX = qryConPosX;
            
        }
//        sb.append(ReportConstant.TEMPLATE_NAME_TR_END);
        
        return sb;
    }
    
    //查询条件是否选择 月份
    private boolean isHasMonth(String[] qryConCodeArray) {
        for (int i =0; i<qryConCodeArray.length; i++) {
            String qryConCode = qryConCodeArray[i];
            if (qryConCode.equals(QryConCodeType.month.name())) {
                return true;
            }
        }
        return false;
    }
    
    private String getReportQtyConTemplate(String qryConCode, boolean isHasMonth) {
        if (qryConCode.equals(QryConCodeType.date.name())) {
            return ReportConstant.TEMPLATE_NAME_DATE;
        } else if (qryConCode.equals(QryConCodeType.dateCur.name())) {
            return ReportConstant.TEMPLATE_NAME_DATE_CUR;
        } else if (qryConCode.equals(QryConCodeType.isBalanceDate.name())) {
            return ReportConstant.TEMPLATE_NAME_IS_BALANCE_DATE;
        } else if (qryConCode.equals(QryConCodeType.year.name())) {
            if (isHasMonth) {
                return ReportConstant.TEMPLATE_NAME_YEAR_MONTH;
            } else {
                return ReportConstant.TEMPLATE_NAME_YEAR;
            }
        } else if (qryConCode.equals(QryConCodeType.month.name())) {
//            return ReportConstant.TEMPLATE_NAME_MONTH;
            return "";
        }  else if (qryConCode.equals(QryConCodeType.lineID.name())) {
            return ReportConstant.TEMPLATE_NAME_LINE_ID;
        } else if (qryConCode.equals(QryConCodeType.lineIDLarge.name())) {
            return ReportConstant.TEMPLATE_NAME_LINE_ID_LARGE;
        } else if (qryConCode.equals(QryConCodeType.mainType.name())) {
            return ReportConstant.TEMPLATE_NAME_MAIN_TYPE;
        } else if (qryConCode.equals(QryConCodeType.subType.name())) {
            return ReportConstant.TEMPLATE_NAME_SUB_TYPE;
        }
        return "";
    }
    
    private StringBuffer getContentFromTemplate(String appRoot, StringBuffer sbReportQtyCon, String[] titleArray, 
            String actionName, String[] cnAndrtAttry) throws FileNotFoundException, IOException {
        
        String reportTitle = titleArray[0];
        String fullReportTitle = titleArray[1];
        String controlNames = cnAndrtAttry[0];
        String reportType = cnAndrtAttry[1];
        
        StringBuffer sb = new StringBuffer();
//        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(appRoot+"jsp/report/report_qry_base_template.jsp")));
            
            String line = null;
            while((line=br.readLine()) != null){//一行一行读，如果不为空，继续运行
//                if(line.matches(".*@(.*)?@.*")){//正则表达
                //替换内容，查询条件、标题等
                if (line.indexOf("@reportQryCon@") != -1) {   
//                    line=line.replaceAll("@.*?@", sbReportQtyCon.toString());//找到两个@ @就把之间的内容替换掉
                    line=line.replaceAll("@reportQryCon@", sbReportQtyCon.toString());
                } 
                if (line.indexOf("@reportTitle@") != -1) {   
                    line=line.replaceAll("@reportTitle@", reportTitle);
                } 
                if (line.indexOf("@fullReportTitle@") != -1) {   
                    line=line.replaceAll("@fullReportTitle@", fullReportTitle);
                }
                if (line.indexOf("@actionName@") != -1) {   
                    line=line.replaceAll("@actionName@", actionName);
                }
                if (line.indexOf("@controlNames@") != -1) {   
                    line=line.replaceAll("@controlNames@", controlNames);
                } 
                if (line.indexOf("@reportType@") != -1) {   
                    line=line.replaceAll("@reportType@", reportType);
                }    
//                System.out.println(line);
                sb.append(line+"\n");
            }
//            sb.setLength(sb.length()-1);//因为多加了一个换行符，所以截掉
            br.close();//关闭输入流
            
//        }catch(FileNotFoundException e){
//            System.out.println("文件未找到");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        
        return sb;
    }
    
    private void genReportQtyFile(String appRoot, String actionName, StringBuffer sb) throws Exception {
//        actionName如: Report?action=IncSysSaleD
        String fileName = getFileNameFromAction(actionName);
        try {
//            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("E:/tesg_gen.jsp")));
//            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(appRoot+"jsp/report/tesg_gen.jsp")));
             String filePath = appRoot+"jsp/report/"+fileName;
             String bakDirPath = appRoot+"jsp/report/"+"bak";
             
             copyFileForBak(filePath, bakDirPath, fileName);
             
             
             PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filePath),"UTF-8"));
            
             out.write(sb.toString());//把sb里面的内容读入文件

             out.flush();
             out.close();
             logger.info("成功生成文件"+filePath);
        } catch (IOException e) {
//            e.printStackTrace();
            throw new Exception("文件生成异常：" + e.getMessage());
        }    
    }
    
    //copy原来JSP文件到/bak目录作为备份
    private void copyFileForBak(String filePathSrc, String pathDes, String fileNameDes) throws FileNotFoundException, IOException {
        
            String bakFilePath = pathDes+"/"+fileNameDes;
             
             File fileSrc = new File(filePathSrc);
             if(fileSrc.exists()){
                File bakDir = new File(pathDes);
                //创建备份目录 /bak
                if (!bakDir.exists()) {
                   bakDir.mkdirs();
                }
                //copy原有文件到备份目录
                FileInputStream inSrc = new FileInputStream(filePathSrc);
                FileOutputStream outBak = new FileOutputStream(new File(bakFilePath));
                FileUtil.copy(inSrc, outBak);
                        
             }
        
    }
    
    private String [] getTitlesForReport(List<SysModule> smsForTitle, ReportCfgModuleMapping reportCfgModuleMapping) {
                
        SysModule sysModule = getSingleSysModuleForTitle(smsForTitle,reportCfgModuleMapping);
        String[] titleArray = new String[2];
        titleArray[0] = reportCfgModuleMapping.getDescription();
        titleArray[1] = sysModule.getModuleName() + "--" + reportCfgModuleMapping.getDescription();
        return titleArray;
    }
    
    private String getActionName(List<SysModule> smsForActionName, ReportCfgModuleMapping reportCfgModuleMapping) {
        String result = "";
        SysModule sysModule = getSingleSysModuleForActionName(smsForActionName,reportCfgModuleMapping);
        if (sysModule != null) {
            String menuUrl = sysModule.getMenuUrl();
//           1. Report?action=PflChangeStaMon.do --> Report?action=PflChangeStaMon
            if (menuUrl.indexOf(".do") != -1) {
                result = menuUrl.substring(0, menuUrl.indexOf(".do"));
            } else {
                result = menuUrl;
            }
//           2.Report?action=PflChangeStaMon -->PflChangeStaMon
            result = result.replace("Report?action=", "");

        }
        return result;
    }
    
    private ReportCfgModuleMapping getSingleReportModuleDes(List<ReportCfgModuleMapping> rcmms, String reportModule) {
        for (ReportCfgModuleMapping rcmm : rcmms) {
            if (rcmm.getReport_module().equals(reportModule)) {
                return rcmm;
            }
        }
        return null;
    }
    
    private SysModule getSingleSysModuleForTitle(List<SysModule> sms, ReportCfgModuleMapping rcmm) {
        if (rcmm != null ) {
            for (SysModule sm : sms) {
                String moduleId = rcmm.getModule_id();
                //父菜单Id
                String parentModuleId = moduleId.substring(0, 4);
                if (sm.getModuleId().equals(parentModuleId)) {
                    return sm;
                }
            }
        }
        return null;
    }
    
    private SysModule getSingleSysModuleForActionName(List<SysModule> smsForActionName, ReportCfgModuleMapping rcmm) {
        if (rcmm != null ) {
            for (SysModule sm : smsForActionName) {
                String moduleId = rcmm.getModule_id();
                if (sm.getModuleId().equals(moduleId)) {
                    return sm;
                }
            }
        }
        return null;
    }
    
    private String[] getControlNamesAndReportType(String qryConCodeString)  {
        String[] cnAndrtArray = new String[2];
        StringBuffer sbControlNames = new StringBuffer("");
        String[] qryConCodeArray = qryConCodeString.split(";");
        String qryConCode = null;
        Vector<ReportQryCfg> rqcs = new Vector();
        String reportType = ""; 
        for (int i =0; i<qryConCodeArray.length; i++) {
            qryConCode = qryConCodeArray[i];
            
            if (qryConCode.equals(QryConCodeType.lineID.name())
                    || qryConCode.equals(QryConCodeType.mainType.name())
                    || qryConCode.equals(QryConCodeType.subType.name())) {
                sbControlNames.append(qryConCode+"#");
            } else if (qryConCode.equals(QryConCodeType.lineIDLarge.name())) {
                //大线路也是使用 lineID
                sbControlNames.append(QryConCodeType.lineID.name()+"#");
            } 
            
            //js判断 1: 年报 2: 月报 3: 日报，与W_RP_CFG_ATTRIBUTE.period_type 定义不同'1日报，2月报，3年报'
            if (qryConCode.equals(QryConCodeType.year.name())) {
                //年，月会同时存在
                if (reportType.equals("")) {
                    reportType = String.valueOf(ReportConstant.FLAG_YEAR);
                }
            } else if (qryConCode.equals(QryConCodeType.month.name())) {
                reportType = String.valueOf(ReportConstant.FLAG_MONTH);
            } else if (qryConCode.equals(QryConCodeType.date.name())) {
                reportType = String.valueOf(ReportConstant.FLAG_DATE);
            } else if (qryConCode.equals(QryConCodeType.dateCur.name())) {
                reportType = String.valueOf(ReportConstant.FLAG_DATE);
            }
        }
        if (sbControlNames.length() > 1) {
            sbControlNames.setLength(sbControlNames.length()-1); //截掉最后的"#"
        }
        cnAndrtArray[0] = sbControlNames.toString();
        cnAndrtArray[1] = reportType;
       
        
        return cnAndrtArray;
    }
    
    /**
     * 根据action获取页面jsp文件名，如IncSysSaleD转换为inc_sys_sale_d.jsp
     * actionName如: Report?action=IncSysSaleD
     * @param action
     * @return 
     */
//    private String getFileNameFromAction(String fullAction) {
    private String getFileNameFromAction(String action) {
        StringBuffer sbAction = new StringBuffer("");
//        int pos = fullAction.indexOf("=");
//        if (pos == -1 || fullAction.length()== pos+1) {
//            return null;
//        }
//        String action = fullAction.substring(pos+1, fullAction.length());
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
    
    private String checkData(Vector<ReportQryCfg> pos) {
        String result = "";
        
        if (pos == null || pos.isEmpty() 
                || pos.get(0).getQry_con_code() == null ) {
            return "配置的查询条件不能为空!";
        }
        for (ReportQryCfg rqc : pos) {
            if (rqc.getQry_con_pos() == null || rqc.getQry_con_pos().equals("")) {
                return "所选查询条件的[位置]不能为空!";
            }
        }
        return result;
    };
    
    public static void main(String[] args){
//                    System.out.print(System.getProperty("user.dir"));
//                    request.getSession().getServletContext().getRealPath("") 
 System.out.println(QryConCodeType.date);
//        try {
//
//            
////            BufferedReader br = new BufferedReader(new FileReader(new File("E:/test.jsp")));
//            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("E:/test.jsp")));
//            StringBuffer sb = new StringBuffer();
//            String line = null;
//            while((line=br.readLine()) != null){//一行一行读，如果不为空，继续运行
//                if(line.matches(".*@(.*)?@.*")){//正则表达
//                    line=line.replaceAll("@.*?@", "第一行dfdfs\n第二行");//找到两个@ @就把之间的内容替换掉
// 
//                }
//                System.out.println(line);
//                sb.append(line+"\n");
//            }
//            sb.setLength(sb.length()-1);//因为多加了一个换行符，所以截掉
//            br.close();//关闭输入流
//            
////写入
//           PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("E:/tesg_gen.jsp")));
//            out.write(sb.toString());//把sb里面的内容读入E:test.txt中
//             
//            out.flush();
//            out.close();
//            
//        }catch(FileNotFoundException e){
//            System.out.println("文件未找到");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
