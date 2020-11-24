/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.produce.controller;

import com.goldsign.acc.app.produce.entity.TicketStorageProduceBillDetail;
import com.goldsign.acc.app.produce.mapper.TicketStorageProduceBillDetailMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author mqf
 */
@Controller
public class TicketStorageProduceBillDetailController extends StorageOutInBaseController {
    
    @Autowired
    private TicketStorageProduceBillDetailMapper tsProduceBillDetailMapper;
    

    @RequestMapping(value = "/ticketStorageProduceBillDetail")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/produce/ticketStorageProduceBillDetail.jsp");
//        String command = request.getParameter("command");
        
        OperationResult opResult = new OperationResult();
        try {
//            if (command == null) {
//                command = CommandConstant.COMMAND_QUERY;
//            }
                
                
                opResult = this.query(request, this.tsProduceBillDetailMapper, this.operationLogMapper);
               
                
//                if (this.isUpdateOp(command, null))//更新操作，增、删、改、审核,查询更新结果或原查询结果
//                {
//                    this.queryUpdateResult(command, request, tsProduceBillDetailMapper, operationLogMapper, opResult, mv);
//                }
//                if (command != null) {
//                    if (command.equals("add") || command.equals("delete") || command.equals("modify") || command.equals("audit") || command.equals("submit")) {
//                        if (!command.equals("add")) {
////                            this.saveQueryControlDefaultValues(request);//保存查询条件
//                            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
//                        }
//
//                    }
//                }

                

//            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = this.getAttributeNames();
        this.setPageOptions(attrNames, mv, request, response); //
//        this.setOperatorId(mv, request);
        this.getResultSetText(opResult.getReturnResultSet(), mv);
        this.baseHandlerForBillNo(request, response, mv); //回传参数 单号
        this.baseHandler(request, response, mv);
//        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }
    
    private String[] getAttributeNames() {
        
        
        String[] attrNames = {IC_CARD_MAIN,IC_CARD_SUB,IC_LINES,IC_STATIONS,MODES};
        return attrNames;

    }
    
    private void getResultSetText(List<TicketStorageProduceBillDetail> resultSet, ModelAndView mv) {
        List<PubFlag> icCardMainTypes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        List<PubFlag> icCardSubTypes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);
        List<PubFlag> icLines = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_LINES);
        List<PubFlag> icStations = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_STATIONS);
        List<PubFlag> modes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.MODES);
        
        for (TicketStorageProduceBillDetail produceBillDetail : resultSet) {
            if (icCardMainTypes != null && !icCardMainTypes.isEmpty()) {
                produceBillDetail.setIc_main_type_name(DBUtil.getTextByCode(produceBillDetail.getIc_main_type(), icCardMainTypes));
            }
            if (icCardSubTypes != null && !icCardSubTypes.isEmpty()) {
//                produceBillDetail.setIc_sub_type_name(DBUtil.getTextByCode(produceBillDetail.getIc_sub_type(), icCardSubTypes));
                produceBillDetail.setIc_sub_type_name(DBUtil.getTextByCode(produceBillDetail.getIc_sub_type(),produceBillDetail.getIc_main_type(), icCardSubTypes));
            }
            if (icLines != null && !icLines.isEmpty()) {
                produceBillDetail.setLine_id_name(DBUtil.getTextByCode(produceBillDetail.getLine_id(), icLines));
                produceBillDetail.setExit_line_id_name(DBUtil.getTextByCode(produceBillDetail.getExit_line_id(), icLines));
            }
            if (icStations != null && !icStations.isEmpty()) {
                produceBillDetail.setStation_id_name(DBUtil.getTextByCode(produceBillDetail.getStation_id(), produceBillDetail.getLine_id(), icStations));
                produceBillDetail.setExit_station_id_name(DBUtil.getTextByCode(produceBillDetail.getExit_station_id(), produceBillDetail.getExit_line_id(), icStations));
            }
            if (modes != null && !modes.isEmpty()) {
                produceBillDetail.setModel_name(DBUtil.getTextByCode(produceBillDetail.getModel(), modes));
            }
        }

    }
    
    private void queryUpdateResult(String command, HttpServletRequest request, TicketStorageProduceBillDetailMapper tsProduceBillDetailMapper,
            OperationLogMapper opLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        }
        this.query(request, tsProduceBillDetailMapper, opLogMapper);

    }
   
    public OperationResult query(HttpServletRequest request, TicketStorageProduceBillDetailMapper tsProduceBillDetailMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageProduceBillDetail queryCondition;
        List<TicketStorageProduceBillDetail> resultSet;

        try {
            queryCondition = this.getQueryConditionProduceBillDetail(request);
            resultSet = tsProduceBillDetailMapper.getProduceBillDetailList(queryCondition);
            
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }
    
    private TicketStorageProduceBillDetail getQueryConditionProduceBillDetail(HttpServletRequest request) {
        TicketStorageProduceBillDetail qCon = new TicketStorageProduceBillDetail();
        qCon.setBill_no(request.getParameter("d_bill_no"));  
        
        return qCon;
    }
    
    private void baseHandlerForBillNo(HttpServletRequest request, HttpServletResponse response, ModelAndView modelView) {
        String billNo = FormUtil.getParameter(request, "d_bill_no");
        if (billNo != null && billNo.length() != 0) {
            modelView.addObject("d_bill_no", billNo);
        }

    }
    
}
