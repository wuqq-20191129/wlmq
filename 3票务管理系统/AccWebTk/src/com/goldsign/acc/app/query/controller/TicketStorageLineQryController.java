/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.query.controller;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageLineStockWarnPara;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageLineStockWarnParaMapper;
import com.goldsign.acc.app.query.entity.TicketStorageLineQry;
import com.goldsign.acc.app.query.mapper.TicketStorageLineQryMapper;
import com.goldsign.acc.frame.constant.CommandConstant;

import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;

import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;

import com.goldsign.acc.frame.util.LogUtil;

import com.goldsign.acc.frame.vo.OperationResult;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * 线路库存预警查询
 *
 * @author luck
 */
@Controller
public class TicketStorageLineQryController extends StorageOutInBaseController {

    @Autowired
    private TicketStorageLineQryMapper tslqMapper;
    @Autowired
    private TicketStorageLineStockWarnParaMapper tslswpMapper;

    @RequestMapping(value = "/ticketLineStorageQry")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/query/ticketStorageLineQry.jsp");
        String command = request.getParameter("command");
        String operType = request.getParameter("operType");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult = this.query(request, this.tslqMapper, this.operationLogMapper);
                }
                if (this.isUpdateOp(command, operType))//更新操作，增、删、改、审核,查询更新结果或原查询结果
                {
                    this.queryUpdateResult(command, operType, request, tslqMapper, operationLogMapper, opResult, mv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }
        String[] attrNames = {LINES, AFC_CARD_MAIN, AFC_CARD_SUB, CARD_MAIN_SUB};
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.getResultSetText(opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    private void getResultSetText(List<TicketStorageLineQry> resultSet, ModelAndView mv) {
        List<PubFlag> lines = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.LINES);
        List<PubFlag> icCardMains = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AFC_CARD_MAIN);
        List<PubFlag> icCardSubs = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AFC_CARD_SUB);

        for (TicketStorageLineQry sd : resultSet) {
            if (lines != null && !lines.isEmpty()) {
                sd.setLineName(DBUtil.getTextByCode(sd.getDeptId(), lines));
            }
            if (icCardMains != null && !icCardMains.isEmpty()) {
                sd.setCardMainName(DBUtil.getTextByCode(sd.getTickettypeId().substring(0, 2), icCardMains));

            }
            if (icCardSubs != null && !icCardSubs.isEmpty()) {
                sd.setCardSubName(DBUtil.getTextByCode(sd.getTickettypeId().substring(2, 4), sd.getTickettypeId().substring(0, 2), icCardSubs));

            }
            List<TicketStorageLineStockWarnPara> warnPara = this.getWarnPara(sd.getDeptId(), sd.getTickettypeId().substring(0, 2), sd.getTickettypeId().substring(2, 4));
            if (warnPara.size() > 0) {
                if (sd.getCurrenttotal() > warnPara.get(0).getUpperThresh()) {
                    sd.setFlag(1);
                    sd.setFlagName("超过库存安全上限");
                } else if (sd.getCurrenttotal() < warnPara.get(0).getLowerThresh()) {
                    sd.setFlag(2);
                    sd.setFlagName("低于库存安全下限");
                } else {
                    sd.setFlagName("库存正常");
                }
            }

        }

    }

    private List<TicketStorageLineStockWarnPara> getWarnPara(String lineId, String CardMainType, String CardSubType) {
        TicketStorageLineStockWarnPara ticketStorageLineStockWarnPara = new TicketStorageLineStockWarnPara();
        ticketStorageLineStockWarnPara.setLineId(lineId);
        ticketStorageLineStockWarnPara.setIcMainType(CardMainType);
        ticketStorageLineStockWarnPara.setIcSubType(CardSubType);
        List<TicketStorageLineStockWarnPara> queryLineStockWarnPara = tslswpMapper.queryLineStockWarnPara(ticketStorageLineStockWarnPara);
        return queryLineStockWarnPara;
    }

    private void queryUpdateResult(String command, String operType, HttpServletRequest request, TicketStorageLineQryMapper tslqMapper,
            OperationLogMapper opLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        }
        this.queryForOp(request, this.tslqMapper, this.operationLogMapper, opResult);

    }

    public OperationResult queryForOp(HttpServletRequest request, TicketStorageLineQryMapper tslqMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageLineQry queryCondition;
        List<TicketStorageLineQry> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request, opResult);
            if (queryCondition == null) {
                return null;
            }
            resultSet = tslqMapper.query(queryCondition);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult query(HttpServletRequest request, TicketStorageLineQryMapper tslqMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageLineQry queryCondition;
        List<TicketStorageLineQry> resultSet;

        try {
            queryCondition = this.getQueryConditionIn(request);
            resultSet = tslqMapper.query(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    protected TicketStorageLineQry getQueryConditionIn(HttpServletRequest request) {
        TicketStorageLineQry qCon = new TicketStorageLineQry();
        qCon.setDeptId(FormUtil.getParameter(request, "q_lineId"));
        qCon.setTickettypeId(FormUtil.getParameter(request, "q_cardMainCode") + FormUtil.getParameter(request, "q_cardSubCode"));
        return qCon;
    }

    protected TicketStorageLineQry getQueryConditionForOp(HttpServletRequest request, OperationResult opResult) {
        TicketStorageLineQry qCon = new TicketStorageLineQry();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {
            //操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;

            }
        } else {
            //操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setDeptId(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_lineId"));
                qCon.setTickettypeId(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardMainCode") + FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardSubCode"));
            }
        }
        return qCon;
    }

    @RequestMapping("/TicketStorageLineQryExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        List results = this.getBufferElements(request);
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.query.entity.TicketStorageLineQry");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }

}
