/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samquery.controller;

import com.goldsign.acc.app.samquery.entity.SamStockSta;
import com.goldsign.acc.app.samquery.mapper.SamStockStaMapper;
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
public class SamStockStaController extends SamBaseController {

    @Autowired
    private SamStockStaMapper mapper;

    @RequestMapping("/samStockStaAction")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("/jsp/samquery/samStockSta.jsp");
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
        String[] attrNames = {SAM_TYPES};
        this.setPageOptions(attrNames, mv, request, response);
        this.getResultSetText((List<SamStockSta>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);
        return mv;
    }

    private OperationResult query(HttpServletRequest request, SamStockStaMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        SamStockSta queryCondition;
        List<SamStockSta> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = mapper.getSamStockSta(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    private SamStockSta getQueryCondition(HttpServletRequest request) {
        SamStockSta vo = new SamStockSta();
        
        vo.setSam_type(FormUtil.getParameter(request, "q_samType"));
        vo.setBeginLogicNo(FormUtil.getParameter(request, "q_beginLogicNo"));
        vo.setEndLogicNo(FormUtil.getParameter(request, "q_endLogicNo"));
        return vo;
    }

    private void getResultSetText(List<SamStockSta> resultSet, ModelAndView mv) throws Exception {
        List<PubFlag> samTypes = (List<PubFlag>) mv.getModelMap().get(SamBaseController.SAM_TYPES);

        for (SamStockSta vo : resultSet) {
            if (samTypes != null && !samTypes.isEmpty()) {
                vo.setSam_typeText(DBUtil.getTextByCode(vo.getSam_type(), samTypes));
            }
        }
    }
    @RequestMapping("/SamStockStaExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElements(request);
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }
}

