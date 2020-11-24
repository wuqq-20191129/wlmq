/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.controller;

import com.goldsign.acc.app.storagein.entity.TicketStorageInNew;
import com.goldsign.acc.app.storagein.entity.TicketStorageInNewDetail;
import com.goldsign.acc.app.storagein.mapper.TicketStorageInNewDetailMapper;
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
 * 新票入库明细
 * 20170807
 */
@Controller
public class TicketStorageInNewDetailController  extends TicketStorageInNewParentController {

    @Autowired
    private TicketStorageInNewDetailMapper tsindMapper;
    
    String operType;
    String RecordFlag;
    String billNo;

    @RequestMapping(value = "/ticketStorageInNewDetailController")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/storagein/ticketStorageInNewDetail.jsp");
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
                    opResult = this.queryNewDetail(request,this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD)) {
                    opResult = this.addNewDetail(request, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                    opResult = this.modifyNewDetail(request,this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE)) {
                    opResult = this.deleteNewDetail(request,this.operationLogMapper);
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

        String[] attrNames = {STORAGES,IC_CARD_MAIN,IC_CARD_MAIN_SERIAL,IC_CARD_MAIN_SUB,IC_CARD_SUB,IN_OUT_REASON_FOR_IN,AREAS,MODES,STORAGE_AREAS};
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.setPageOptionsForProduce(operType, mv, request, response); //生产使用参数
        this.getResultSetText(opResult.getReturnResultSet(), mv);
        this.baseHandlerForOutIn(request, response, mv); //出入库基本回传参数
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }
    
    private void getResultSetText(List<TicketStorageInNewDetail> resultSet, ModelAndView mv){
        List<PubFlag> storage = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.STORAGES);
        List<PubFlag> ic_card_main = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        List<PubFlag> ic_card_sub = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);
        List<PubFlag> reason = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IN_OUT_REASON_FOR_IN);
        List<PubFlag> areas = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AREAS);
        List<PubFlag> modes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.MODES);

        for (TicketStorageInNewDetail tsind : resultSet) {
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

    public TicketStorageInNew getQueryConditionNew(HttpServletRequest request) {
        TicketStorageInNew qCon = new TicketStorageInNew();
        qCon.setBill_no(FormUtil.getParameter(request, "q_billNo"));
        qCon.setBegin_time(FormUtil.getParameter(request, "q_beginTime"));
        qCon.setEnd_time(FormUtil.getParameter(request, "q_endTime"));
        qCon.setStorage_id(FormUtil.getParameter(request, "q_storage"));
        qCon.setIc_main_type(FormUtil.getParameter(request, "q_cardMainCode"));
        qCon.setIc_sub_type(FormUtil.getParameter(request, "q_cardSubCode"));
        qCon.setReason_id(FormUtil.getParameter(request, "q_inReason"));
        qCon.setRecord_flag(FormUtil.getParameter(request, "q_recordFlag"));
        qCon.setInType("XR");
        
        return qCon;
    }
    
     //一次处理多条单据时候合并重复单据

    public String relatedTotalCut(String relatedTotal)
            throws Exception {
        String relatedNew = "";
        try {
            int n, same = 0;
            int lenth = relatedTotal.length();
            String[] relatedDim = new String[lenth];
            if (lenth <= 13) {
                return relatedTotal;
            }
            for (int i = 1; i <= (lenth / 13); i++) {
                relatedDim[i] = relatedTotal.substring(0, 13);
                n = 1;
                same = 0;
                while (i > 1 && n < i) {
                    if (relatedDim[n].equals(relatedDim[i])) {
                        same = 1;
                    }
                    n++;
                }
                if (same == 0) {
                    relatedNew += relatedDim[i];
                }
                relatedTotal = relatedTotal.substring(13);
            }
        } catch (Exception e) {
        }
        return relatedNew;
    }

    public OperationResult queryNewDetail(HttpServletRequest request,OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageInNewDetail queryCondition;
        List<TicketStorageInNewDetail> resultSet;

        try {
            queryCondition = this.getQueryConditionNewDetail(request);
            
            resultSet = this.getTicketStorageInNewDetails(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult addNewDetail(HttpServletRequest request, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageInNewDetail tsind = this.getReqAttributeNewDetail(request);
        List<TicketStorageInNewDetail> results;
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            n = this.addTicketStorageInNewDetail(request, tsind);
            if(n==-2){
                rmsg.addMessage("增加失败！入库明细中只能存在一种仓库类型！");
                logUtil.logOperation(CommandConstant.COMMAND_ADD, request, "明细增加失败！入库明细中只能存在一种仓库类型！", opLogMapper);
            }
            if(n==-3){
                rmsg.addMessage("增加失败！入库数量总和已超过最大剩余数量!");
                logUtil.logOperation(CommandConstant.COMMAND_ADD, request, "明细增加失败！入库数量总和已超过最大剩余数量!", opLogMapper);
            }
            if(n==-1){
                rmsg.addMessage("增加失败");
                logUtil.logOperation(CommandConstant.COMMAND_ADD, request, "明细增加失败！", opLogMapper);
            }
            if(n>0){
                rmsg.addMessage(LogConstant.addSuccessMsg(n));
                logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + "成功添加"+ n + "条明细记录！", opLogMapper);
            }
            results=tsindMapper.getTicketStorageInNewDetailByBillNo(billNo);
            rmsg.setReturnResultSet(results);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        return rmsg;
    }
    
    public TicketStorageInNewDetail getReqAttributeNewDetail(HttpServletRequest request){
        TicketStorageInNewDetail tsind = new TicketStorageInNewDetail();
        tsind.setBill_no(FormUtil.getParameter(request, "d_billNo"));
        tsind.setWater_no(FormUtil.getParameter(request, "d_waterNo"));
        tsind.setIc_main_type(FormUtil.getParameter(request, "d_cardMainType"));
        tsind.setIc_sub_type(FormUtil.getParameter(request, "d_cardSubType"));
        tsind.setStorage_id(FormUtil.getParameter(request, "d_storageId"));
        tsind.setArea_id(FormUtil.getParameter(request, "d_area"));
        tsind.setIn_num(FormUtil.getParameter(request, "d_inNum"));
        tsind.setReason_id("01");
        
        return tsind;
    }

    public OperationResult modifyNewDetail(HttpServletRequest request,OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageInNewDetail vo = this.getReqAttributeNewDetail(request);
        LogUtil logUtil = new LogUtil();
        List<TicketStorageInNewDetail> results;
        int n = 0;
        String preMsg = "";
        try {
            n = this.modifyTicketStorageInNewDetail(request,vo);
            if(n==-2){
                rmsg.addMessage("修改失败！相同入库单只能存在一种票库类型的记录！");
                logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, "明细修改失败！相同入库单只能存在一种票库类型的记录！", opLogMapper);
            }
            if(n==-3){
                rmsg.addMessage("修改失败！入库数量总和已超过最大剩余数量!");
                logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, "明细修改失败！入库数量总和已超过最大剩余数量!", opLogMapper);
            }
            if(n>0){
                rmsg.addMessage(LogConstant.modifySuccessMsg(n));
                logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, "成功修改" + n + "条明细记录！", opLogMapper);
            }
            results=tsindMapper.getTicketStorageInNewDetailByBillNo(billNo);
            rmsg.setReturnResultSet(results);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        
        return rmsg;
    }

    public OperationResult deleteNewDetail(HttpServletRequest request,OperationLogMapper opLogMapper) throws Exception {
        
        OperationResult rmsg = new OperationResult();
        List<TicketStorageInNewDetail> pos = this.getReqAttributeForNewDetailDelete(request);
        List<TicketStorageInNewDetail> results;
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteNewDetailByTrans(request, pos);
            if(n>0){
                results=tsindMapper.getTicketStorageInNewDetailByBillNo(billNo);
                rmsg.setReturnResultSet(results);
            }
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        
       if(n<0){
            rmsg.addMessage("删除失败!");
            logUtil.logOperation(CommandConstant.COMMAND_DELETE, request,"明细删除失败!", opLogMapper);
        }else{
            rmsg.addMessage(LogConstant.delSuccessMsg(n));
            logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, "成功删除"+ n + "条明细记录！", opLogMapper);
        }
        return rmsg;
    }

}
