/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.app.querysys.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.goldsign.acc.app.opma.constant.NonReturnApplyHandleConstant;
import com.goldsign.acc.app.querysys.entity.BomSignCardLossApplyQuery;
import com.goldsign.acc.app.querysys.entity.CodePubFlag;
import com.goldsign.acc.app.querysys.mapper.BomSignCardLossApplyQueryMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;

/**
 * @desc:BOM记名卡挂失申请查询
 * @author:zhongzqi
 * @create date: 2017-6-20
 */
@Controller
public class BomSignCardLossApplyQueryController extends CodePubFlagController {
	@Autowired
	private BomSignCardLossApplyQueryMapper mapper;

	@RequestMapping("/BomSignCardLossApplyQuery")
	public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/jsp/querysys/bom_sign_card_loss_apply_query.jsp");
		String command = request.getParameter("command");
		OperationResult opResult = new OperationResult();
		try {
			if (command != null) {
				command = command.trim();
				// 查询操作
				if (command.equals(CommandConstant.COMMAND_QUERY)) {
					opResult = this.query(request, this.mapper, this.operationLogMapper);
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
		String[] attrNames = { LINES, STATIONS, LINE_STATIONS,CARDMAINS, CARDSUBS, CARDMAIN_SUBS};
		// 设置页面线路
		this.setPageOptions(attrNames, mv, request, response);
		this.baseHandler(request, response, mv);
		this.setResultText(mv,opResult,command);
		// 结果集分页
		this.divideResultSet(request, mv, opResult);
		this.SaveOperationResult(mv, opResult);


		return mv;
	}

	private OperationResult query(HttpServletRequest request, BomSignCardLossApplyQueryMapper mapper,
			OperationLogMapper operationLogMapper) throws Exception {
		OperationResult or = new OperationResult();
		LogUtil logUtil = new LogUtil();
		BomSignCardLossApplyQuery queryCondition;
		List<BomSignCardLossApplyQuery> resultSet;
		try {
			queryCondition = this.getQueryCondition(request);
			resultSet = mapper.getBomSignCardLossApplyQuery(queryCondition);
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

	private BomSignCardLossApplyQuery getQueryCondition(HttpServletRequest request) {
		BomSignCardLossApplyQuery vo = new BomSignCardLossApplyQuery();
		String businessReceiptId = request.getParameter("q_businessReceiptId");
		String beginDateTime = request.getParameter("q_beginDatetime");
		String endDateTime = request.getParameter("q_endDatetime");
		String identifyType = request.getParameter("q_identifyType");
		String identityId = request.getParameter("q_identityId");
		String busnissType = request.getParameter("q_busnissType");
		if (!"".equals(businessReceiptId)) {
			vo.setApplyBill(businessReceiptId);
		}
		if (!"".equals(beginDateTime)) {
			beginDateTime = beginDateTime + " 00:00:00";
			vo.setBeginDatetime(beginDateTime);
		}
		if (!"".equals(endDateTime)) {
			endDateTime = endDateTime + " 23:59:59";
			vo.setEndDatetime(endDateTime);
		}
		if (!"".equals(identifyType)) {
			vo.setIdentifyType(identifyType);
		}
		if (!"".equals(identityId)) {
			vo.setIdentityId(identityId);
		}
		if (!"".equals(busnissType)) {
			vo.setBusnissType(busnissType);
		}
		return vo;
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

	private void setResultText(ModelAndView mv,OperationResult result, String command) {
		List<BomSignCardLossApplyQuery> resultSet = (List<BomSignCardLossApplyQuery>) result.getReturnResultSet();
		this.setIdType(mv, mapper);
		this.setBusinessType(mv);
		if (resultSet == null || resultSet.isEmpty()) {
			return;
		}
		if (command == null || command.equals(CommandConstant.COMMAND_NEXT)
   				|| command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
   				|| command.equals(CommandConstant.COMMAND_BACKEND)||command.equals(CommandConstant.COMMAND_GOPAGE)) {
   			return;
   		}

		List<PubFlag> lines = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.LINES);
		List<PubFlag> stations = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.STATIONS);
		 List<PubFlag> cardMains = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDMAINS);
         List<PubFlag> cardSubs = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDSUBS);
		List<CodePubFlag> idTypes = (List<CodePubFlag>) mv.getModelMap().get("idTypes");
		List<CodePubFlag> businessTypes = (List<CodePubFlag>) mv.getModelMap().get("businessTypes");
		for (BomSignCardLossApplyQuery vo : resultSet) {
			if (idTypes != null && !idTypes.isEmpty()) {
				vo.setIdentifyTypeText(getTextByCode(vo.getIdentifyType(), idTypes));
			}
			if (businessTypes != null && !businessTypes.isEmpty()) {
				vo.setBusnissTypeText(getTextByCode(vo.getBusnissType(), businessTypes));
			}
			if (lines != null && !lines.isEmpty()) {
				vo.setLineName(this.getTextByCodeAdaptNull(vo.getLineId(), lines));
			}
			if (stations != null && !stations.isEmpty()) {
				vo.setStationName(DBUtil.getTextByCode(vo.getStationId(), vo.getLineId(), stations));
			}
			if (cardMains!=null&&!cardMains.isEmpty()){
                vo.setCardMainName(this.getTextByCodeAdaptNull(vo.getCardMainId(), cardMains));
            }
            if (cardSubs!=null&&!cardSubs.isEmpty()){
                 vo.setCardSubName(DBUtil.getTextByCode(vo.getCardSubId(), vo.getCardMainId(),cardSubs));
            }
		}
	}

	private void setIdType(ModelAndView mv, BomSignCardLossApplyQueryMapper mapper) {
		List<CodePubFlag> idTypes = mapper.getIdTypes();
		mv.addObject("idTypes", idTypes);
	}

	private void setBusinessType(ModelAndView mv) {
		List<CodePubFlag> businessTypes = this.getBusinessType();
		mv.addObject("businessTypes", businessTypes);
	}



	private String getLineStaion(String receiptId) {
		// TODO Auto-generated method stub
		int receiptIdListLen = 5;
		String lineStaionId = null;
		if (receiptId == null || receiptId.length() != NonReturnApplyHandleConstant.BUSINESS_RECEIPT_ID_LENGTH) {
			return lineStaionId;
		}
		String[] receiptIdList = receiptId.split("-");
		if (receiptIdList.length == receiptIdListLen) {
			lineStaionId = receiptIdList[2];
		}
		return lineStaionId;
	}

	public String getTextByCodeAdaptNull(String code, List tableFlags) {
		PubFlag pv = null;
		for (int i = 0; i < tableFlags.size(); i++) {
			pv = (PubFlag) tableFlags.get(i);
			if ((pv.getCode().trim()).equals(code)) {
				return pv.getCode_text();
			}
		}
		return code;

	}

	@RequestMapping("/BomSignCardLossApplyQueryExportAll")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request,BomSignCardLossApplyQuery.class.getName());
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields,results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
}
