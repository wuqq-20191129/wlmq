/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.app.querysys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.goldsign.acc.app.querysys.entity.CodePubFlag;
import com.goldsign.acc.app.querysys.entity.TicketCardDealHis;
import com.goldsign.acc.app.querysys.mapper.TicketCardDealHisMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;

/**
 * @desc:票卡交易记录查询
 * @author:zhongzqi
 * @create date: 2017-6-8
 */
@Controller
public class TicketCardDealHisController extends CodePubFlagController {
	@Autowired
	private TicketCardDealHisMapper ticketCardDealHisMapper;

	@RequestMapping("/TicketCardDealHis")
	public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/jsp/querysys/ticket_card_deal_his.jsp");
		String command = request.getParameter("command");
		OperationResult opResult = new OperationResult();
		try {
			if (command != null) {
				command = command.trim();
				if (command.equals(CommandConstant.COMMAND_QUERY))
				{
					opResult = this.query(request, this.ticketCardDealHisMapper, this.operationLogMapper);
				}
				if (command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND)
						|| command.equals(CommandConstant.COMMAND_BACK)
						|| command.equals(CommandConstant.COMMAND_BACKEND)) {
					// 保存查询条件
					this.saveQueryControlDefaultValues(request, mv);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.baseHandler(request, response, mv);
		this.divideResultSet(request, mv, opResult);
		this.SaveOperationResult(mv, opResult);
		List<CodePubFlag> dealTypes = this.getTicketCardDealType();
		mv.addObject("dealTypes", dealTypes);
		return mv;
	}

	private OperationResult query(HttpServletRequest request, TicketCardDealHisMapper ticketCardDealHisMapper,
			OperationLogMapper operationLogMapper) throws Exception {
		OperationResult or = new OperationResult();
		LogUtil logUtil = new LogUtil();
		Map<String, Object> queryCondition;
		List<TicketCardDealHis> resultSet;
		try {
			queryCondition = this.getQueryCondition(request);
			ticketCardDealHisMapper.getTicketCardDealHisList(queryCondition);
			resultSet = (List<TicketCardDealHis>) queryCondition.get("result");
			or.setReturnResultSet(resultSet);
			or.addMessage("成功查询" + resultSet.size() + "条记录");
		} catch (Exception e) {
			e.printStackTrace();
			return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
		}
		logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录",
				operationLogMapper);
		return or;
	}

	private Map<String, Object> getQueryCondition(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>(5);
		String benDatetime = FormUtil.getParameter(request, "q_beginDatetime");
		String endDatetime = FormUtil.getParameter(request, "q_endDatetime");
		String cardLogicalId = FormUtil.getParameter(request, "q_cardLogicalId");
		String dealType = FormUtil.getParameter(request, "q_dealType");
		if (!"".equals(benDatetime)) {
			map.put("benDatetime", benDatetime + " 00:00:00");
		}
		if (!"".equals(endDatetime)) {
			map.put("endDatetime", endDatetime + " 23:59:59");
		}
		if (!"".equals(cardLogicalId)) {
			map.put("cardLogicalId", cardLogicalId);
		}
		if (!"".equals(dealType)) {
			map.put("dealType", dealType);
		} else {
			map.put("dealType", "%");
		}
		return map;
	}

	@RequestMapping("/TicketCardDealHisExportAll")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request,TicketCardDealHis.class.getName());
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields,results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

}
