/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.goldsign.acc.app.prminfo.entity.BlackListOctSec;
import com.goldsign.acc.app.prminfo.mapper.BlackListOctSecMapper;
import com.goldsign.acc.app.querysys.controller.CodePubFlagController;
import com.goldsign.acc.app.querysys.entity.CodePubFlag;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.WebConstant;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;

/**
 * @desc:一卡通黑名单段
 * @author:zhongzqi
 * @create date: 2017-6-14
 */
@Controller
public class BlackListOctSecController extends CodePubFlagController {

    @Autowired
    private BlackListOctSecMapper mapper;

    @RequestMapping("/BlackListOctSec")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/prminfo/black_list_oct_sec.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY))
                {
                    opResult = this.query(request, this.mapper, this.operationLogMapper);
                }
            }
            if (command == null) {
                opResult = this.queryNum(request, this.mapper, this.operationLogMapper);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.baseHandler(request, response, mv);
        this.setResultText(mv,opResult,command);
        this.divideResultSet(request, mv, opResult);
        this.SaveOperationResult(mv, opResult);
        this.getDistinctOperatorId(mv);
        return mv;
    }

    private void setResultText(ModelAndView mv, OperationResult opResult, String command) {
		// TODO Auto-generated method stub
    	List<BlackListOctSec> resultSet = (List<BlackListOctSec>) mv.getModelMap().get(WebConstant.ATT_ResultSet);
        List<CodePubFlag> blackResons = this.getBlackReson();
        mv.addObject("blackResons", blackResons);
    	if(resultSet==null ||resultSet.isEmpty()){
            return ;
        }
        if (command == null || command.equals(CommandConstant.COMMAND_NEXT)
				|| command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
				|| command.equals(CommandConstant.COMMAND_BACKEND)||command.equals(CommandConstant.COMMAND_GOPAGE)) {
			return;

		}

	    for (BlackListOctSec vo : resultSet) {
	        if (blackResons != null && !blackResons.isEmpty()) {
	            vo.setBlackTypeText(getTextByCode(vo.getBlackType(), blackResons));
	        }
	    }
	}

	private void getDistinctOperatorId(ModelAndView mv) {
		// TODO Auto-generated method stub
    	List<BlackListOctSec> operatorIds = mapper.getDistinctOperatorId();
        mv.addObject("operatorIds", operatorIds);

	}

	private OperationResult query(HttpServletRequest request, BlackListOctSecMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        BlackListOctSec queryCondition;
        List<BlackListOctSec> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = mapper.getBlackListOctSec(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    private BlackListOctSec getQueryCondition(HttpServletRequest request) {
        BlackListOctSec qCon = new BlackListOctSec();
        qCon.setBeginLogicalId(FormUtil.getParameter(request, "q_beginLogicalId"));
        qCon.setEndLogicalId(FormUtil.getParameter(request, "q_endLogicalId"));
        String beginTime = FormUtil.getParameter(request, "q_beginDatetime");
        String endTime = FormUtil.getParameter(request, "q_endDatetime");
//        if (!"".equals(beginTime)) {
//            beginTime = beginTime + " 00:00:00";
//        }
//        if (!"".equals(endTime)) {
//            endTime = endTime + " 23:59:59";
//        }
        qCon.setBlackType(FormUtil.getParameter(request, "q_blackType"));
        qCon.setOperatorId(FormUtil.getParameter(request, "q_operatorId"));
        qCon.setBeginEffectiveTime(beginTime);
        qCon.setEndEffectiveTime(endTime);
        return qCon;
    }

    private void getBlackResonAndSetResultBlackText(ModelAndView mv) {
        List<CodePubFlag> blackResons = this.getBlackReson();
        mv.addObject("blackResons", blackResons);
        List<BlackListOctSec> resultSet = (List<BlackListOctSec>) mv.getModelMap().get(WebConstant.ATT_ResultSet);
        if (resultSet != null && !resultSet.isEmpty()) {
            for (BlackListOctSec vo : resultSet) {
                if (blackResons != null && !blackResons.isEmpty()) {
                    vo.setBlackTypeText(getTextByCode(vo.getBlackType(), blackResons));
                }
            }
        }
    }

    public static String getTextByCode(String code, List tableFlags) {
        CodePubFlag pv = null;
        for (int i = 0; i < tableFlags.size(); i++) {
            pv = (CodePubFlag) tableFlags.get(i);
            if (pv.getCode().equals(code)) {
                return pv.getCodeText();
            }
        }
        return code;
    }

    private OperationResult queryNum(HttpServletRequest request, BlackListOctSecMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = mapper.queryNum();
            or.addMessage("一卡通黑名单段累计已有" + n + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + n + "条记录", operationLogMapper);
        return or;
    }
    @RequestMapping("/BlackListOctSecExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request,BlackListOctSec.class.getName());
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields,results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
}
