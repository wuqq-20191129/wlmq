/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.controller;

import com.goldsign.acc.app.storagein.entity.TicketStorageIcChkStorageForCheckIn;
import com.goldsign.acc.app.storagein.mapper.TicketStorageInAdjustCheckMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author mqf
 */
@Controller
public class TicketStorageInAdjustCheckController extends StorageOutInBaseController {

    @Autowired
    private TicketStorageInAdjustCheckMapper tsInAdjustCheckMapper;

    @RequestMapping(value = "/ticketStorageInAdjustCheck")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        String command = request.getParameter("command");
//        String inType = this.getInType(request);
        String operType = request.getParameter("operType");
        if (!(operType.equals(OPER_TYPE_CHECK_IN) || operType.equals(OPER_TYPE_CHECK_OUT)
                || operType.equals(OPER_TYPE_CHECK_IN_SELECT))) {
            return null;
        }
        ModelAndView mv = this.getModelAndView(operType);

        OperationResult opResult = new OperationResult();
        try {
            if (command == null) {
                command = CommandConstant.COMMAND_QUERY;
            }
            command = command.trim();

            if (command.equals(CommandConstant.COMMAND_QUERY)) {
                if (operType.equals(OPER_TYPE_CHECK_IN)) {
                  opResult = this.queryForCheckIn(request, this.tsInAdjustCheckMapper, this.operationLogMapper, operType);
                } else if (operType.equals(OPER_TYPE_CHECK_IN_SELECT)) {
                    opResult = this.queryForCheckInSelect(request, this.tsInAdjustCheckMapper, this.operationLogMapper, operType);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = this.getAttributeNames(operType);
        this.setPageOptions(attrNames, mv, request, response); 
        this.setOperatorId(mv, request);
        this.getResultSetText(opResult.getReturnResultSet(), mv, operType);
        this.baseHandlerForOutIn(request, response, mv); //出入库基本回传参数
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }
    
    private String getInType(HttpServletRequest request) {
        String inType = "";
        String billNo = (String) request.getParameter("queryCondition");
//        String inType = request.getParameter("inType"); //入库类型:生产入库 "SR";生产入出库差额:"DF"
        if (((String) request.getParameter("in_type")) != null) {
            inType = (String) request.getParameter("in_type");
        } else {
            inType = billNo.substring(0, 2);
        }
        return inType;
    }

    private ModelAndView getModelAndView(String operType) {
        if (OPER_TYPE_CHECK_IN.equals(operType)) {
            return new ModelAndView("/jsp/storagein/ticketStorageInAdjustCheck.jsp");
        }
        if (OPER_TYPE_CHECK_IN_SELECT.equals(operType)) {
            return new ModelAndView("/jsp/storagein/ticketStorageInAdjustSelectCheck.jsp");
        }
        return new ModelAndView("");
    }

    private String[] getAttributeNames(String operType) {

        if (OPER_TYPE_CHECK_IN.equals(operType)) {
            String[] attrNames = {STORAGES,AREAS,IC_CARD_MAIN,IC_CARD_SUB};
            return attrNames;
        } 
        return new String[0];

    }

    private void getResultSetText(List resultSet, ModelAndView mv,String operType) {
        if (OPER_TYPE_CHECK_IN.equals(operType)) {
            this.getResultSetTextForCheckIn(resultSet,mv);
        } 

    }
    
    private void getResultSetTextForCheckIn(List<TicketStorageIcChkStorageForCheckIn> resultSet, ModelAndView mv) {
        List<PubFlag> storages = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.STORAGES);
        
        List<PubFlag> areas = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AREAS);
        List<PubFlag> icCardMainTypes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        List<PubFlag> icCardSubTypes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);

        for (TicketStorageIcChkStorageForCheckIn chkStorage : resultSet) {
            if (storages != null && !storages.isEmpty()) {
                chkStorage.setStorageName(DBUtil.getTextByCode(chkStorage.getStorageId(), storages));
            }
            if (areas != null && !areas.isEmpty()) {
                chkStorage.setAreaName(DBUtil.getTextByCode(chkStorage.getAreaId(), areas));
            }
            if (icCardMainTypes != null && !icCardMainTypes.isEmpty()) {
                chkStorage.setIcMainName(DBUtil.getTextByCode(chkStorage.getIcMainType(), icCardMainTypes));
            }
            if (icCardSubTypes != null && !icCardSubTypes.isEmpty()) {
                chkStorage.setIcSubName(DBUtil.getTextByCode(chkStorage.getIcSubType(), chkStorage.getIcMainType(), icCardSubTypes));
            }
            
        }
        
    }
    
    public OperationResult queryForCheckIn(HttpServletRequest request, TicketStorageInAdjustCheckMapper tsInAdjustCheckMapper, OperationLogMapper opLogMapper, String operType) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageIcChkStorageForCheckIn queryCondition;
        List<TicketStorageIcChkStorageForCheckIn> resultSet = new ArrayList<TicketStorageIcChkStorageForCheckIn>();
        String preMsg = "调账入库明细:";

        try {
            queryCondition = this.getQueryCondition(request,operType);
            resultSet = tsInAdjustCheckMapper.getChkStoragesList(queryCondition);

            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + or.getReturnResultSet().size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + or.getReturnResultSet().size() + "条记录", opLogMapper);
        return or;

    }
    
    public OperationResult queryForCheckInSelect(HttpServletRequest request, TicketStorageInAdjustCheckMapper tsInAdjustCheckMapper, OperationLogMapper opLogMapper, String operType) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageIcChkStorageForCheckIn queryCondition = new TicketStorageIcChkStorageForCheckIn();
        List<TicketStorageIcChkStorageForCheckIn> resultSet = new ArrayList<TicketStorageIcChkStorageForCheckIn>();
        String preMsg = "调账入库明细:";

        try {
//            setStorageIdListForQueryCondition(request,queryCondition); ////根据操作员的仓库权限设置仓库列表
            List storageIdList = getStorageIdListForQueryCondition(request,null); //根据操作员的仓库权限设置仓库列表
            queryCondition.setStorageIdList(storageIdList);
            resultSet = tsInAdjustCheckMapper.getChkStoragesListForSelect(queryCondition);

            or.setReturnResultSet(resultSet);
//            or.addMessage("成功查询" + or.getReturnResultSet().size() + "条记录");
            or.addMessage("");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + or.getReturnResultSet().size() + "条记录", opLogMapper);
        return or;

    }


    protected TicketStorageIcChkStorageForCheckIn getQueryCondition(HttpServletRequest request, String operType) {
        TicketStorageIcChkStorageForCheckIn qCon = new TicketStorageIcChkStorageForCheckIn();

//        qCon.setRecord_flag(request.getParameter("billRecordFlag"));
        qCon.setCheckBillNo(request.getParameter("queryCondition"));  
        return qCon;
    }
    
    /**
     * 返回操作员的权限仓库列表
     * @param request
     * @param queryCondition 
     */
//    protected void setStorageIdListForQueryCondition(HttpServletRequest request, TicketStorageIcChkStorageForCheckIn queryCondition) {
//        
//        String operatorId = PageControlUtil.getOperatorFromSession(request);
//        //查询当前操作员的有权限的仓库
//        List<PubFlag> storageIdOps =this.getStorages(operatorId);
//        List <String> storageIdList = new ArrayList<String>();
//        for (PubFlag op:storageIdOps) {
//            storageIdList.add(op.getCode());
//        }
//        queryCondition.setStorageIdList(storageIdList);
//    }
    

}
