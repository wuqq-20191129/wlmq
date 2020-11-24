/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.controller;

import com.goldsign.acc.app.storagein.entity.TicketStorageInReturnDetail;
import com.goldsign.acc.app.storagein.mapper.TicketStorageInReturnDetailMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.vo.User;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author zhouyang
 * 回收入库明细
 * 20170815
 */
@Controller
public class TicketStorageInReturnDetailController  extends TicketStorageInReturnParentController {

    @Autowired
    private TicketStorageInReturnDetailMapper tsirdMapper;
    
    String operType;
    String RecordFlag;
    String billNo;

    @RequestMapping(value = "/ticketStorageInReturnDetailController")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/storagein/ticketStorageInReturnDetail.jsp");
        String command = request.getParameter("command");
        operType = request.getParameter("operType");
        RecordFlag=request.getParameter("billRecordFlag");
        billNo=request.getParameter("queryCondition");
        OperationResult opResult = new OperationResult();
        User user = (User) request.getSession().getAttribute("User");
        String operatorID = user.getAccount();
        try {
            if (command != null) {
                command = command.trim();
                
                //入库明细操作
                if (command.equals(CommandConstant.COMMAND_QUERY_DETAIL)) {
                    opResult = this.query(request,this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD)) {
                    opResult = this.add(request, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                    opResult = this.modify(request,this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE)) {
                    opResult = this.delete(request,this.operationLogMapper);
                }

//                if (this.isUpdateOp(command, operType))//更新操作，增、删、改、审核,查询更新结果或原查询结果
//                {
//                    this.queryUpdateResult(command, operType, request, tsopMapper, operationLogMapper, opResult, mv);
//                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames1 = {STORAGES,CARD_MONEYS,IC_CARD_MAIN,CARD_MAIN_FOR_RETURN,IC_CARD_MAIN_SUB,
                               STORAGE_AREAS,IC_CARD_SUB,IC_LINES,IC_STATIONS,IC_LINE_STATIONS,LINE_STATIONS,
                               LINES,MODES,IN_OUT_REASON_FOR_IN,AREAS};
        String[] attrNames2 = {"icCardMainType","icCodStorageLine","Line","icCodStation","inReason",};
        this.setPageOptions(attrNames1, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.setPageOptions(attrNames2, mv, request);
        this.setPageOptionsForProduce(operType, mv, request, response); //生产使用参数
        this.getResultSetText(opResult.getReturnResultSet(), mv);
        this.baseHandlerForOutIn(request, response, mv); //出入库基本回传参数
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }
    
    private void setPageOptions(String[] attrNames,ModelAndView mv, HttpServletRequest request){
        List<PubFlag> options;
        for (String attrName : attrNames) {
            if (attrName.equals("icCardMainType")) {//票卡主类型
                options = tsirdMapper.getIcCardMainType();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals("icCodStorageLine")) {//票库线路
                options = tsirdMapper.getIcCodStorageLine();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals("inReason")) {//入库原因
                options = tsirdMapper.getInReason();
                mv.addObject(attrName, options);
                continue;
            }
        }
    }
    
    private void getResultSetText(List<TicketStorageInReturnDetail> resultSet, ModelAndView mv){
        List<PubFlag> storage = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.STORAGES);
        List<PubFlag> ic_card_main = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        List<PubFlag> ic_card_sub = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);
        List<PubFlag> reason = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IN_OUT_REASON_FOR_IN);
        List<PubFlag> areas = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AREAS);
        List<PubFlag> modes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.MODES);
        List<PubFlag> lines = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_LINES);
        List<PubFlag> stations = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_STATIONS);
        
        for (TicketStorageInReturnDetail tsind : resultSet) {
            if (storage != null && !storage.isEmpty()) {
                tsind.setStorage_name(DBUtil.getTextByCode(tsind.getStorage_id(), storage));
            }
            if (ic_card_main != null && !ic_card_main.isEmpty()) {
                tsind.setIc_main_type_name(DBUtil.getTextByCode(tsind.getIc_main_type(), ic_card_main));
            }
            if (ic_card_sub != null && !ic_card_sub.isEmpty()) {
                tsind.setIc_sub_type_name(DBUtil.getTextByCode(tsind.getIc_sub_type(),tsind.getIc_main_type(), ic_card_sub));
            }
            if (reason != null && !reason.isEmpty()) {
                tsind.setReason_name(DBUtil.getTextByCode(tsind.getReason_id(), reason));
            }
            if (areas != null && !areas.isEmpty()) {
                tsind.setArea_name(DBUtil.getTextByCode(tsind.getArea_id(), areas));
            }
            if (modes != null && !modes.isEmpty()) {
                tsind.setModel_name(DBUtil.getTextByCode(tsind.getModel(), modes));
            }
            if (lines != null && !lines.isEmpty()) {
                tsind.setLine_name(DBUtil.getTextByCode(tsind.getLine_id_reclaim(), lines));
            }
            if (stations != null && !stations.isEmpty()) {
                tsind.setStation_name(DBUtil.getTextByCode(tsind.getStation_id_reclaim(),tsind.getLine_id_reclaim(), stations));
            }
            if(tsind.getValid_date()!=null && !tsind.getValid_date().equals("")){
                if("1970-01-01".equals(tsind.getValid_date().substring(0,10))){
                    tsind.setValid_date("");
                }else{
                    tsind.setValid_date(tsind.getValid_date().substring(0,10));
                }
            }
            if(tsind.getReport_date()!=null && !tsind.getReport_date().equals("")){
                if("1970-01-01".equals(tsind.getReport_date().substring(0,10))){
                    tsind.setReport_date("");
                }else{
                    tsind.setReport_date(tsind.getReport_date().substring(0,10));
                }
            }
            tsind.setRecord_flag(RecordFlag);
        }
    }
    
    protected void setPageOptionsForProduce(String operType, ModelAndView mv, HttpServletRequest request, HttpServletResponse response) {
        if (this.isOperForPlanDetail(operType)) {
            String validDays = "100";// bo.getCardValidDays();
            if (validDays != null) {
                mv.addObject("validDays", validDays);//票卡类型有效天数

            }
        }
    }

    public OperationResult query(HttpServletRequest request,OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageInReturnDetail queryCondition;
        List<TicketStorageInReturnDetail> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            
            resultSet = this.getTicketStorageInReturnDetails(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult add(HttpServletRequest request, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageInReturnDetail tsind = this.getReqAttributeDetail(request);
        List<TicketStorageInReturnDetail> results;
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            n = this.addTicketStorageInReturnDetail(request, tsind);
            if(n==-2){
                rmsg.addMessage("增加失败！入库明细中只能存在一种仓库类型！");
                logUtil.logOperation(CommandConstant.COMMAND_ADD, request, "明细增加失败！入库明细中只能存在一种仓库类型！", opLogMapper);
            }
            if(n==-3){
                rmsg.addMessage("增加失败！报表日期为‘"+ tsind.getReport_date() +"’没有车站/收益组当日的上传记录！");
                logUtil.logOperation(CommandConstant.COMMAND_ADD, request, "明细增加失败！没有车站/收益组当日的上传记录！", opLogMapper);
            }
            if(n==-4){
                rmsg.addMessage("增加失败！入库数量总和已超过最大剩余数量！");
                logUtil.logOperation(CommandConstant.COMMAND_ADD, request,"明细增加失败！入库数量总和已超过最大剩余数量！", opLogMapper);
            }
            if(n==-5){
                rmsg.addMessage("增加失败！车站/收益组当日的上传记录已经存在库中！");
                logUtil.logOperation(CommandConstant.COMMAND_ADD, request, "明细增加失败！车站/收益组当日的上传记录已经存在库中！", opLogMapper);
            }
            if(n==-6){
                rmsg.addMessage("增加失败！“入库原因”不是“车站上交”或“收益组移交”时，票卡子类型不能为空！");
                logUtil.logOperation(CommandConstant.COMMAND_ADD, request, "明细增加失败！“入库原因”不是“车站上交”或“收益组移交”时，票卡子类型不能为空！", opLogMapper);
            }
            if(n==-7){
                rmsg.addMessage("增加失败！“入库原因”为“车站上交”或“收益组移交”时，报表日期不能为空！");
                logUtil.logOperation(CommandConstant.COMMAND_ADD, request, "明细增加失败！“入库原因”为“车站上交”或“收益组移交”时，票卡子类型不能为空！！", opLogMapper);
            }
            if(n==-8){
                rmsg.addMessage("增加失败！车站上交/收益组移交在"+tsind.getReport_date()+"上传的记录中，存在错误的票卡类型！");
                logUtil.logOperation(CommandConstant.COMMAND_ADD, request, "增加失败！车站上交/收益组移交在"+tsind.getReport_date()+"上传的记录中，存在错误的票卡类型！", opLogMapper);
            }
            if(n==-1){
                rmsg.addMessage("增加失败");
                logUtil.logOperation(CommandConstant.COMMAND_ADD, request,"明细增加失败！", opLogMapper);
            }
            if(n>=0){
                rmsg.addMessage(LogConstant.addSuccessMsg(n));
                logUtil.logOperation(CommandConstant.COMMAND_ADD, request, "成功添加" + n + "条明细记录！", opLogMapper);
            }
            results=tsirdMapper.getTicketStorageInReturnDetailByBillNo(billNo);
            rmsg.setReturnResultSet(results);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        
        return rmsg;
    }
    
    public TicketStorageInReturnDetail getReqAttributeDetail(HttpServletRequest request){
        TicketStorageInReturnDetail tsind = new TicketStorageInReturnDetail();
        tsind.setBill_no(FormUtil.getParameter(request, "d_billNo"));
        tsind.setWater_no(FormUtil.getParameter(request, "d_waterNo"));
        tsind.setReason_id(FormUtil.getParameter(request, "d_reason_id"));
        tsind.setIc_main_type(FormUtil.getParameter(request, "d_card_main_code"));
        tsind.setIc_sub_type(FormUtil.getParameter(request, "d_card_sub_code"));
        tsind.setStorage_id(FormUtil.getParameter(request, "d_storage_id"));
        tsind.setArea_id(FormUtil.getParameter(request, "d_area_id"));
        tsind.setIn_num(Integer.parseInt(FormUtil.getParameter(request, "d_in_num")));;
        tsind.setCard_money(Integer.parseInt(FormUtil.getParameter(request, "d_card_money")));
        tsind.setValid_date(FormUtil.getParameter(request, "d_valid_date"));
        tsind.setReport_date(FormUtil.getParameter(request, "d_report_date"));
        tsind.setLine_id_reclaim(FormUtil.getParameter(request, "d_line_id_reclaim"));
        tsind.setStation_id_reclaim(FormUtil.getParameter(request, "d_station_id_reclaim"));
        tsind.setStart_logical_id(FormUtil.getParameter(request, "d_start_logical_id"));
        tsind.setEnd_logical_id(FormUtil.getParameter(request, "d_end_logical_id"));
       
        
        return tsind;
    }

    public OperationResult modify(HttpServletRequest request,OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageInReturnDetail vo = this.getReqAttributeDetail(request);
        LogUtil logUtil = new LogUtil();
        List<TicketStorageInReturnDetail> results;
        int n = 0;
        String preMsg = "";
        try {
            n = this.modifyTicketStorageInReturnDetail(request,vo);
            if(n==-2){
                rmsg.addMessage("修改失败！车站上交/收益组织移交不能进行修改操作！");
                logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, "明细修改失败！车站上交/收益组织移交不能进行修改操作！", opLogMapper);
            }
            if(n==-3){
                rmsg.addMessage("修改失败！相同入库单只能存在一种票库类型的记录！");
                logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, "明细修改失败！相同入库单只能存在一种票库类型的记录！", opLogMapper);
            }
            if(n==-4){
                rmsg.addMessage("修改失败！入库数量总和已超过最大剩余数量!");
                logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request,"明细修改失败！入库数量总和已超过最大剩余数量!", opLogMapper);
            }
            if(n>0){
                rmsg.addMessage(LogConstant.modifySuccessMsg(n));
                logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, "成功修改" + n + "条明细记录！", opLogMapper);
            }
            results=tsirdMapper.getTicketStorageInReturnDetailByBillNo(billNo);
            rmsg.setReturnResultSet(results);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        

        return rmsg;
    }

    public OperationResult delete(HttpServletRequest request,OperationLogMapper opLogMapper) throws Exception {
        
        OperationResult rmsg = new OperationResult();
        List<TicketStorageInReturnDetail> pos = this.getReqAttributeForReturnDetailDelete(request);
        List<TicketStorageInReturnDetail> results;
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteReturnDetailByTrans(request, pos);
            if(n==-2){
                rmsg.addMessage("删除失败!删除记录中包含“收益组移交”时，报表日期相同的记录必须全部删除");
                logUtil.logOperation(CommandConstant.COMMAND_DELETE, request,"明细删除失败!删除记录中包含“收益组移交”时，报表日期相同的记录必须全部删除", opLogMapper);
            }
            else if(n<0){
                rmsg.addMessage("删除失败!");
                logUtil.logOperation(CommandConstant.COMMAND_DELETE, request,"明细删除失败!", opLogMapper);
            }else{
                rmsg.addMessage(LogConstant.delSuccessMsg(n));
                logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, "成功删除" + n + "条明细记录！", opLogMapper);
            }
            results=tsirdMapper.getTicketStorageInReturnDetailByBillNo(billNo);
            rmsg.setReturnResultSet(results);
            
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        return rmsg;
    }

}
