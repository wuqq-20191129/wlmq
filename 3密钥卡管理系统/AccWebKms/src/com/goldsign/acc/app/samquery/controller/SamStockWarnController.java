/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samquery.controller;

import com.goldsign.acc.app.samquery.entity.SamStockWarn;
import com.goldsign.acc.app.samquery.mapper.SamStockWarnMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.constant.SamCardConstant;
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
 * 卡库存预警
 * @author taidb
 */
@Controller
public class SamStockWarnController extends SamBaseController{
        
    @Autowired
    private  SamStockWarnMapper samStockWarnMapper;
    
    @RequestMapping("/samStockWarnAction")
    public ModelAndView service (HttpServletRequest request, HttpServletResponse response) throws Exception{
        ModelAndView mv = new ModelAndView("/jsp/samquery/samStockWarnQuery.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_QUERY)){
                    opResult = this.query(request, this.samStockWarnMapper, this.operationLogMapper);
                }   
            }
        } catch (Exception e) {
            e.printStackTrace();
	    opResult.addMessage(e.getMessage());
        }
        if(command ==  null)
            opResult.addMessage(LogConstant.OPERATIION_SUCCESS_LOG_MESSAGE);
        
        String[] attrNames = this.getReqAttributeNames(request, command);
        this.setPageOptions(attrNames, mv, request, response);
        this.getResultSetText((List<SamStockWarn>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);
        return mv;
    }
    
    private void getResultSetText(List<SamStockWarn> resultSet, ModelAndView mv) throws Exception {
        List<PubFlag> samTypes = (List<PubFlag>) mv.getModelMap().get(SamBaseController.SAM_TYPES);
        
        for (SamStockWarn vo : resultSet) {
            if (samTypes != null && !samTypes.isEmpty()) {
                vo.setSamTypeText(DBUtil.getTextByCode(vo.getSamType(), samTypes));
            }
            if(vo.getEtyWarnState()!=null && vo.getEtyWarnState().equals("1")){
                vo.setEtyWarnStateText("异常");
            }else{
                vo.setEtyWarnStateText("正常");
            }
            if(vo.getPduWarnState()!=null && vo.getPduWarnState().equals("1")){
                vo.setPduWarnStateText("异常");
            }else{
                vo.setPduWarnStateText("正常");
            }
        }
    }
    
    private OperationResult query(HttpServletRequest request, SamStockWarnMapper samStockWarnMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        SamStockWarn ssw;
        List<SamStockWarn> resultSet;
        try {
            ssw = this.getQueryCondition(request);
            resultSet = samStockWarnMapper.getSamStockWarn(ssw);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }
    
    private String[] getReqAttributeNames(HttpServletRequest request, String command) {        
        String[] attrNames = {SAM_TYPES};
        return attrNames;
    }
    private SamStockWarn getQueryCondition(HttpServletRequest request) {
        SamStockWarn qCon = new SamStockWarn(); 
        qCon.setSamType(FormUtil.getParameter(request, "q_samType")); 
        qCon.setProduce_Type_Empty(SamCardConstant.PRODUCE_TYPE_EMPTY);
        qCon.setStock_State_In(SamCardConstant.STOCK_STATE_IN);
        qCon.setProduce_Type_Product(SamCardConstant.PRODUCE_TYPE_PRODUCT);
        qCon.setSam_Card_State_Ok(SamCardConstant.SAM_CARD_STATE_OK);
        return qCon;
    }
    @RequestMapping("/SamStockWarnExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElements(request);
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }
}
