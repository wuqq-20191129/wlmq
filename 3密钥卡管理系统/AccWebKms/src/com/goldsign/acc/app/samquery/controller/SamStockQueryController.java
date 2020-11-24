/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samquery.controller;

import com.goldsign.acc.app.samquery.entity.SamStockQuery;
import com.goldsign.acc.app.samquery.mapper.SamStockQueryMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.SamBaseController; 
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
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
 * @author chenzx
 * 卡库存查询
 */

@Controller
public class SamStockQueryController extends SamBaseController {

    @Autowired
    private SamStockQueryMapper mapper;

    @RequestMapping("/samStockQueryAction")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("/jsp/samquery/samStockQuery.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.mapper, this.operationLogMapper);
                }
                this.saveQueryControlDefaultValues(request, mv); //保存查询条件
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] attrNames = {SAM_TYPES, PRODUCETYPES, STOCKSTATES,ISINSTOCKS,ISBADS};
        this.setPageOptions(attrNames, mv, request, response);
        this.getResultSetText((List<SamStockQuery>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);
        return mv;
    }

    private OperationResult query(HttpServletRequest request, SamStockQueryMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        SamStockQuery queryCondition;
        List<SamStockQuery> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = mapper.getSamStockQuery(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    private SamStockQuery getQueryCondition(HttpServletRequest request) {
        SamStockQuery vo = new SamStockQuery();
        
        vo.setSam_type(FormUtil.getParameter(request, "q_samType"));
        vo.setBeginLogicNo(FormUtil.getParameter(request, "q_beginLogicNo"));
        vo.setEndLogicNo(FormUtil.getParameter(request, "q_endLogicNo"));
        vo.setProduce_type(FormUtil.getParameter(request, "q_produceType"));
        vo.setStock_state(FormUtil.getParameter(request, "q_stockState"));
        vo.setIs_instock(FormUtil.getParameter(request, "q_isInstock"));
        vo.setIs_bad(FormUtil.getParameter(request, "q_isBad"));
        return vo;
    }

    private void getResultSetText(List<SamStockQuery> resultSet, ModelAndView mv) throws Exception {
        List<PubFlag> samTypes = (List<PubFlag>) mv.getModelMap().get(SamBaseController.SAM_TYPES);
        List<PubFlag> produceTypes = (List<PubFlag>) mv.getModelMap().get(SamBaseController.PRODUCETYPES);
        List<PubFlag> stockStates = (List<PubFlag>) mv.getModelMap().get(SamBaseController.STOCKSTATES);
        List<PubFlag> isInstocks = (List<PubFlag>) mv.getModelMap().get(SamBaseController.ISINSTOCKS);
        List<PubFlag> isBads = (List<PubFlag>) mv.getModelMap().get(SamBaseController.ISBADS);

        for (SamStockQuery vo : resultSet) {
            if (samTypes != null && !samTypes.isEmpty()) {
                vo.setSam_typeText(DBUtil.getTextByCode(vo.getSam_type(), samTypes));
            }
            if (produceTypes != null && !produceTypes.isEmpty()) {
                vo.setProduce_typeText(DBUtil.getTextByCode(vo.getProduce_type(), produceTypes));
            }
            if (stockStates != null && !stockStates.isEmpty()) {
                vo.setStock_stateText(DBUtil.getTextByCode(vo.getStock_state(), stockStates));
            }
            if (isInstocks != null && !isInstocks.isEmpty()) {
                vo.setIs_instockText(DBUtil.getTextByCode(vo.getIs_instock(), isInstocks));
            }
            if (isBads != null && !isBads.isEmpty()) {
                vo.setIs_badText(DBUtil.getTextByCode(vo.getIs_bad(), isBads));
            }
        }
    }
    
    @RequestMapping("/SamStockQueryExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElements(request);
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }
}
