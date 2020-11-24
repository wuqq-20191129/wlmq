/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.controller;

import com.goldsign.acc.app.opma.constant.NonReturnApplyHandleConstant;
import com.goldsign.acc.app.querysys.controller.CodePubFlagController;
import com.goldsign.acc.app.querysys.entity.CodePubFlag;
import com.goldsign.acc.app.opma.entity.NonReturnApplyHandle;
import com.goldsign.acc.app.opma.entity.NonReturnApplyHandleHis;
import com.goldsign.acc.app.opma.mapper.NonReturnApplyHandleHisMapper;
import com.goldsign.acc.app.opma.mapper.NonReturnApplyHandleMapper;
import com.goldsign.acc.app.querysys.entity.NonReturnOpLog;
import com.goldsign.acc.app.querysys.mapper.NonReturnOpLogMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.WebConstant;
import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.DateHelper;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.vo.ModuleDistrVo;
import com.goldsign.login.vo.User;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * @desc:
 * @author:zhongzqi
 * @create date: 2017-6-26
 */
@Controller
public class NonReturnApplyHandleController extends CodePubFlagController {

	@Autowired
	private NonReturnApplyHandleMapper mapper;
	@Autowired
	private NonReturnApplyHandleHisMapper hisMapper;

	@Autowired
	private NonReturnOpLogMapper logMapper;

	@RequestMapping("/NonReturnApplyHandle")
	public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/jsp/opma/non_return_apply_handle.jsp");
		String command = request.getParameter("command");
		// 判断查询后是否有点击一条记录
		String selectRowFlag = request.getParameter("selectRowFlag");
		OperationResult opResult = new OperationResult();
		// 第二列表的数据
		List<NonReturnApplyHandleHis> hisResult = new Vector();
		// 用于点击后显示处理框数据
		NonReturnApplyHandle selectVo = new NonReturnApplyHandle();
//		// 用于保存修改数据
//		NonReturnApplyHandle detailVo = new NonReturnApplyHandle();
		try {
			if (command != null) {
				command = command.trim();
				// 普通查询
				if (command.equals(CommandConstant.COMMAND_QUERY)
						&& NonReturnApplyHandleConstant.FLAG_FALSE.equals(selectRowFlag)) {
					opResult = this.query(request, this.mapper, this.operationLogMapper);
				} // 点击一条记录
				if (command.equals(CommandConstant.COMMAND_QUERY)
						&& NonReturnApplyHandleConstant.FLAG_TRUE.equals(selectRowFlag)) {
					this.setOperatorRight(request);
					opResult = this.query(request, this.mapper, this.operationLogMapper);
					selectVo = this.getResultByBusinessReceiptId(opResult, request, this.operationLogMapper);
					hisResult = this.getHisResult(request, selectVo, hisMapper, this.operationLogMapper);
				} // 确认退款
				if (command.equals(CommandConstant.COMMAND_CONFIRM_REFUND)) {
					this.saveQueryControlDefaultValues(request, mv);
					int i = this.confirmRefund(opResult, request, this.mapper, this.operationLogMapper);
					this.setOperatorRight(request);
					opResult = this.queryForOp(request, this.mapper, this.operationLogMapper, opResult);
					selectVo = this.getResultByBusinessReceiptIdAfterConfirm(opResult, request,
							this.operationLogMapper);
					hisResult = this.getHisResult(request, selectVo, hisMapper, this.operationLogMapper);
					if (i == 0) {
						opResult.setMessage("确认退款失败");
					} else {
						writeOperatorLog(selectVo, request, "RefundOk");
						opResult.setMessage("确认退款成功");
					}
				} // 拒绝退款
				if (command.equals(CommandConstant.COMMAND_REFUSED_REFUND)) {
					this.saveQueryControlDefaultValues(request, mv);
					int i = this.refusedRefund(opResult, request, this.mapper, this.operationLogMapper);
					this.setOperatorRight(request);
					opResult = this.queryForOp(request, this.mapper, this.operationLogMapper, opResult);
					selectVo = this.getResultByBusinessReceiptIdAfterConfirm(opResult, request,
							this.operationLogMapper);
					hisResult = this.getHisResult(request, selectVo, hisMapper, this.operationLogMapper);
					if (i == 0) {
						opResult.setMessage("拒绝退款失败");
					} else {
						writeOperatorLog(selectVo, request, "RefundNo");
						opResult.setMessage("拒绝退款成功");
					}

				} // 确认修改
				if (command.equals(CommandConstant.COMMAND_CONFIRM_MODIFY)) {
					this.saveQueryControlDefaultValues(request, mv);

					int i = this.confirmModify(opResult, request, this.mapper, this.operationLogMapper);
					this.setOperatorRight(request);
					opResult = this.queryForOp(request, this.mapper, this.operationLogMapper, opResult);
					selectVo = this.getResultByBusinessReceiptIdAfterConfirm(opResult, request,
							this.operationLogMapper);
					hisResult = this.getHisResult(request, selectVo, hisMapper, this.operationLogMapper);
					if (i == 0) {
						opResult.setMessage("确认修改失败");
					} else {
						writeOperatorLog(selectVo, request, "Modify");
						opResult.setMessage("确认修改成功");
					}
				} // 确认审核
				if (command.equals(CommandConstant.COMMAND_CONFIRM_AUDIT)) {
					this.saveQueryControlDefaultValues(request, mv);
					int i = this.confirmAudit(opResult, request, this.mapper, this.operationLogMapper);
					this.setOperatorRight(request);
					opResult = this.queryForOp(request, this.mapper, this.operationLogMapper, opResult);
					selectVo = this.getResultByBusinessReceiptIdAfterConfirm(opResult, request,
							this.operationLogMapper);
					hisResult = this.getHisResult(request, selectVo, hisMapper, this.operationLogMapper);
					if (i == 0) {
						opResult.setMessage("确认审核失败");
					} else {
						writeOperatorLog(selectVo, request, "Audit");
						opResult.setMessage("确认审核成功");
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		String[] attrNames = { LINES, STATIONS, LINE_STATIONS, PENATLYRESONS };
		// 设置页面线路
		this.setPageOptions(attrNames, mv, request, response);
		this.baseHandler(request, response, mv);

		this.SaveOperationResult(mv, opResult);

		this.setHandleFlag(mv, mapper);
		this.setHandleType(mv, mapper);
		this.setQueryCondition(mv, mapper);
		this.setAuditFlag(mv, mapper);
		this.setHisResult(mv, hisResult, mapper);
		this.setDealType(mv, mapper);
		// detail框的结果
		this.setDetailVo(mv, selectVo);
		this.setResultText(mv);
		return mv;
	}

	private OperationResult query(HttpServletRequest request, NonReturnApplyHandleMapper mapper,
			OperationLogMapper operationLogMapper) throws Exception {
		OperationResult or = new OperationResult();
		LogUtil logUtil = new LogUtil();
		NonReturnApplyHandle queryCondition;
		List<NonReturnApplyHandle> resultSet;
		try {
			queryCondition = this.getQueryCondition(request);
			resultSet = mapper.getNonReturnApplyQuery(queryCondition);
			request.getSession().setAttribute("queryCondition", queryCondition);
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

	private NonReturnApplyHandle getQueryCondition(HttpServletRequest request) {
		NonReturnApplyHandle vo = new NonReturnApplyHandle();
		String qLineId = request.getParameter("q_lineId");
		String qStationId = request.getParameter("q_stationId");
		String beginDateTime = request.getParameter("q_beginDatetime");
		String qCondition = request.getParameter("q_queryCondition");
		String businessReceiptId = request.getParameter("q_businessReceiptId");
		String endDateTime = request.getParameter("q_endDatetime");
		String qHandleType = request.getParameter("q_handleType");
		if (!"".equals(beginDateTime)) {
			beginDateTime = beginDateTime + " 00:00:00";
			vo.setBeginDatetime(beginDateTime);
		}
		if (!"".equals(endDateTime)) {
			endDateTime = endDateTime + " 23:59:59";
			vo.setEndDatetime(endDateTime);
		}
		if (!"".equals(qLineId)) {
			vo.setLineId(qLineId);
		}
		if (!"".equals(qStationId)) {
			vo.setStationId(qStationId);
		}
		if (!"".equals(qCondition) && !"".equals(businessReceiptId)) {
			if (qCondition.equals(NonReturnApplyHandleConstant.QUERY_BY_BUSINESS_RECEIPT_ID)) {
				vo.setBusinessReceiptId(businessReceiptId);
			}
			if (qCondition.equals(NonReturnApplyHandleConstant.QUERY_BY_PRINT_ID)) {
				vo.setCardPrintId(businessReceiptId);
			}
		}
		if (!"".equals(qHandleType)) {
			vo.setHandleType(qHandleType);
		}
		return vo;
	}

	public String getTextByCode(String code, List tableFlags) {
		CodePubFlag pv = null;
		for (int i = 0; i < tableFlags.size(); i++) {
			pv = (CodePubFlag) tableFlags.get(i);
			if (pv.getCode().equals(code)) {
				return pv.getCodeText();
			}
		}
		return code;
	}

	private void setResultText(ModelAndView mv) {
		List<NonReturnApplyHandle> resultSet = (List<NonReturnApplyHandle>) mv.getModelMap()
				.get(WebConstant.ATT_ResultSet);
		if (resultSet == null || resultSet.isEmpty()) {
			return;
		}
		List<PubFlag> lines = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.LINES);
		List<PubFlag> stations = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.STATIONS);
		List<CodePubFlag> handleFlags = (List<CodePubFlag>) mv.getModelMap().get("handleFlags");
		List<CodePubFlag> penatlyReasons = (List<CodePubFlag>) mv.getModelMap().get(PrmBaseController.PENATLYRESONS);
		List<CodePubFlag> auditFlags = (List<CodePubFlag>) mv.getModelMap().get("auditFlags");
		List<CodePubFlag> dealTypes = (List<CodePubFlag>) mv.getModelMap().get("dealTypes");
		for (NonReturnApplyHandle vo : resultSet) {
			if (stations != null && !stations.isEmpty()) {
				vo.setStationText(DBUtil.getTextByCode(vo.getStationId(), vo.getLineId(), stations));
			}
			if (handleFlags != null && !handleFlags.isEmpty()) {
				vo.setHdlFlagText(this.getTextByCode(vo.getHdlFlag(), handleFlags));
			}
			if (penatlyReasons != null && !penatlyReasons.isEmpty()) {
				vo.setPenaltyReasonText(this.getTextByCodeAdaptNull(vo.getPenaltyReason(), penatlyReasons));
			}
			if (auditFlags != null && !auditFlags.isEmpty()) {
				vo.setAuditFlagText(this.getTextByCode(vo.getAuditFlag(), auditFlags));
			}

		}

		List<NonReturnApplyHandleHis> hisResultSet = (List<NonReturnApplyHandleHis>) mv.getModelMap().get("hisResult");
		if (hisResultSet == null || hisResultSet.isEmpty()) {
			return;
		}
		for (NonReturnApplyHandleHis vo : hisResultSet) {
			if (lines != null && !lines.isEmpty()) {
				vo.setLineText(this.getTextByCodeAdaptNull(vo.getLineId(), lines));
			}
			if (stations != null && !stations.isEmpty()) {
				vo.setStationText(DBUtil.getTextByCode(vo.getStationId(), vo.getLineId(), stations));
			}
			if (dealTypes != null && !dealTypes.isEmpty()) {
				vo.setDealTypeText(this.getTextByCode(vo.getDealType(), dealTypes));
			}

		}
		NonReturnApplyHandle detaileVo = (NonReturnApplyHandle) mv.getModelMap().get("selectVo");
		if (detaileVo != null) {
			if (stations != null && !stations.isEmpty()) {
				detaileVo.setStationText(
						DBUtil.getTextByCode(detaileVo.getStationId(), detaileVo.getLineId(), stations));
			}
			if (handleFlags != null && !handleFlags.isEmpty()) {
				detaileVo.setHdlFlagText(this.getTextByCode(detaileVo.getHdlFlag(), handleFlags));
			}
			if (penatlyReasons != null && !penatlyReasons.isEmpty()) {
				detaileVo.setPenaltyReasonText(
						this.getTextByCodeAdaptNull(detaileVo.getPenaltyReason(), penatlyReasons));
			}
			if (auditFlags != null && !auditFlags.isEmpty()) {
				detaileVo.setAuditFlagText(this.getTextByCode(detaileVo.getAuditFlag(), auditFlags));
			}
		}

	}

	private void setBrokenFlag(ModelAndView mv, NonReturnApplyHandleMapper mapper) {
		List<CodePubFlag> brokenFlags = this.getBrokenFlag();
		mv.addObject("brokenFlags", brokenFlags);
	}

	private void setHandleFlag(ModelAndView mv, NonReturnApplyHandleMapper mapper) {
		List<CodePubFlag> handleFlags = this.getHandleFlag();
		List<CodePubFlag> eidtHandleFlags = new Vector();
		mv.addObject("handleFlags", handleFlags);
		for (CodePubFlag vo : handleFlags) {
			if ("2".equals(vo.getCode()) || "6".equals(vo.getCode())) {
				eidtHandleFlags.add(vo);
			}
		}
		mv.addObject("eidtHandleFlags", eidtHandleFlags);
	}

	private void setHandleType(ModelAndView mv, NonReturnApplyHandleMapper mapper) {
		List<CodePubFlag> handleTypes = this.getHandleType();
		mv.addObject("handleTypes", handleTypes);
	}

	private void setQueryCondition(ModelAndView mv, NonReturnApplyHandleMapper mapper) {
		List<CodePubFlag> queryConditons = this.getQueryCondition();
		mv.addObject("queryConditons", queryConditons);
	}

	private void setAuditFlag(ModelAndView mv, NonReturnApplyHandleMapper mapper) {
		List<CodePubFlag> auditFlags = this.getAuditFlag();
		mv.addObject("auditFlags", auditFlags);
	}

	private NonReturnApplyHandle getResultByBusinessReceiptId(OperationResult opResult, HttpServletRequest request,
			OperationLogMapper operationLogMapper) throws Exception {
		NonReturnApplyHandle vo = new NonReturnApplyHandle();
		LogUtil logUtil = new LogUtil();
		try {
			String selectBusinessReceiptId = request.getParameter("selectReceiptId").trim();

			List<NonReturnApplyHandle> resultSet = opResult.getReturnResultSet();
			if ((resultSet != null) && !resultSet.isEmpty()) {
				if (!"".equals(selectBusinessReceiptId)) {
					for (NonReturnApplyHandle po : resultSet) {
						if (selectBusinessReceiptId.equals(po.getBusinessReceiptId())) {
							vo = po;
							break;
						}
					}
				}
			}
			this.isBlackCard(vo);
			this.isApplied(vo);
			this.getPenatly(vo);
			this.getDeposit(vo);
			this.setDefaultBrokenFlag(vo);
			this.getBalance(vo);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
		}
		return vo;
	}

	private List<NonReturnApplyHandleHis> getHisResult(HttpServletRequest request, NonReturnApplyHandle selectVo,
			NonReturnApplyHandleHisMapper hisMapper, OperationLogMapper operationLogMapper) throws Exception {
		List<NonReturnApplyHandleHis> result = new Vector();
                LogUtil logUtil = new LogUtil();
		if (selectVo.getBusinessReceiptId() != null && selectVo.getBusinessReceiptId().trim()
				.length() == NonReturnApplyHandleConstant.BUSINESS_RECEIPT_ID_LENGTH) {
			try {
				result = hisMapper.getHisResult(selectVo);
				if (result.size() == 0) {
					request.setAttribute("hisRecordFlag", "0");
				} else {
					request.setAttribute("hisRecordFlag", "1");
					request.getSession().setAttribute("queryCondition1", selectVo);
				}

			} catch (Exception e) {
                            e.printStackTrace();
                            logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
			}
		}
		return result;
	}

	private void setDetailVo(ModelAndView mv, NonReturnApplyHandle selectVo) {
		mv.addObject("selectVo", selectVo);
	}

	private void isApplied(NonReturnApplyHandle vo) {
		String count = mapper.isApplied(vo);
		if (count.equals(NonReturnApplyHandleConstant.FLAG_NO)) {
			vo.setAppliedFlag(NonReturnApplyHandleConstant.FLAG_NO);
		} else {
			vo.setAppliedFlag(NonReturnApplyHandleConstant.FLAG_YES);
		}

	}

	private void getPenatly(NonReturnApplyHandle vo) {
		// 未处理
		String penatly = "";
		Map<String, Object> map = new HashMap<String, Object>(2);
		if (vo.getHdlFlag().equals(NonReturnApplyHandleConstant.QUERY_BY_NOT_HANDLED)) {
			if (vo.getCardLogicalId() != null && !"".equals(vo.getCardLogicalId())) {
				map.put("cardLogicalId", vo.getCardLogicalId());
				mapper.getPenatly(map);
				penatly = (String) map.get("result");
				vo.setPenaltyFee(new BigDecimal(penatly));
			}
		}
	}

	private void isBlackCard(NonReturnApplyHandle vo) {
		String checkInBlackListMtr = "0";
		String checkInBlackListMtrSec = "0";
		checkInBlackListMtr = mapper.checkInBlackListMtr(vo);
		if (!checkInBlackListMtr.equals(NonReturnApplyHandleConstant.FLAG_YES)) {
			checkInBlackListMtrSec = mapper.checkInBlackListMtrSec(vo);
		}
		if (checkInBlackListMtrSec.equals(NonReturnApplyHandleConstant.FLAG_NO)
				&& checkInBlackListMtr.equals(NonReturnApplyHandleConstant.FLAG_NO)) {
			vo.setBlackCardFlag(NonReturnApplyHandleConstant.FLAG_NO);
		} else {
			vo.setBlackCardFlag(NonReturnApplyHandleConstant.FLAG_YES);
		}
	}

	private void getDeposit(NonReturnApplyHandle vo) {
		String deposit = mapper.getDeposit(vo);
		if (deposit != null) {
			BigDecimal depositFee = new BigDecimal(deposit);
			int judge = depositFee.compareTo(BigDecimal.ZERO);
			if (judge == 1) {

				vo.setDepositFee(depositFee);
			} else {
				vo.setDepositFee(BigDecimal.ZERO);
			}
		} else {
			vo.setDepositFee(BigDecimal.ZERO);
		}

	}

	private void getBalance(NonReturnApplyHandle vo) {
		NonReturnApplyHandle balanceVo = mapper.getBalance(vo);
		if (balanceVo == null || balanceVo.getCardLogicalId() == null) {
			NonReturnApplyHandleHis hisVo = mapper.getHisBalance(vo);
			if (hisVo == null || hisVo.getCardLogicalId() == null) {
				vo.setCardBalanceFee(BigDecimal.ZERO);
			} else {
				vo.setCardBalanceFee(hisVo.getDealBalanceFee());
			}
			vo.setSystemBalanceFee(BigDecimal.ZERO);
		} else {
			vo.setCardBalanceFee(balanceVo.getCardBalanceFee());
			vo.setSystemBalanceFee(balanceVo.getSystemBalanceFee());
		}
	}

	private void setDefaultBrokenFlag(NonReturnApplyHandle vo) {
		if (vo.getIsBroken() == null) {
			vo.setIsBroken("0");
		}
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

	private int confirmRefund(OperationResult opResult, HttpServletRequest request, NonReturnApplyHandleMapper mapper,
			OperationLogMapper operationLogMapper) throws Exception {
		LogUtil logUtil = new LogUtil();
		int result = 0;
		try {
			result = mapper.confirmRefundOrNot(this.getDetailMsg(request));
		} catch (Exception e) {
			 e.printStackTrace();
			logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CONFIRM_REFUND, e, operationLogMapper);
		}
		return result;
	}

	private void setOperatorRight(HttpServletRequest request) {
		String moduleID = request.getParameter("ModuleID");
		HttpSession sess = request.getSession();
		if (moduleID != null) {
			Vector modulePriviledges = (Vector) sess.getAttribute("ModulePrilivedges");
			ModuleDistrVo mv = this.getModulePriviledge(moduleID, modulePriviledges);
			String btnModulesText = mv.getBtnModulesText();
			// 17
			String[] pris = btnModulesText.split("#");
			request.setAttribute("confirmAuditRight", pris[17] + "");
		}
	}

	private void setDealType(ModelAndView mv, NonReturnApplyHandleMapper mapper) {
		List<CodePubFlag> dealTypes = this.getDealType();
		mv.addObject("dealTypes", dealTypes);
	}

	private void setHisResult(ModelAndView mv, List<NonReturnApplyHandleHis> hisResult,
			NonReturnApplyHandleMapper mapper) {
		if (hisResult != null && !hisResult.isEmpty()) {
			List<CodePubFlag> limitRowNum = this.getLimitRowNum();
			String limitRowStr = limitRowNum.get(0).getCode().trim();
			int limitRow = Integer.parseInt(limitRowStr);
			if (limitRow >= hisResult.size()) {
				mv.addObject("hisResult", hisResult);
			} else {
				List<NonReturnApplyHandleHis> displyHisResult = new Vector();
				// 先加固定的行数
				for (int i = 0; i < limitRow; i++) {
					displyHisResult.add(hisResult.get(i));
				}
				// 超过限制的需要做判断
				for (int i = limitRow; i < hisResult.size(); i++) {
					NonReturnApplyHandleHis po = hisResult.get(i);
					if ("3".equals(po.getDealType()) || "5".equals(po.getDealType()) || "7".equals(po.getDealType())) {
						displyHisResult.add(po);
					}
				}
				mv.addObject("hisResult", displyHisResult);
			}
		}
	}

	private NonReturnApplyHandle getDetailMsg(HttpServletRequest request) {
		NonReturnApplyHandle vo = new NonReturnApplyHandle();
		String detailReceiptId = (String) request.getParameter("detailReceiptId");
		String cardBalance = (String) request.getParameter("cardBalanceFee");
		String systemBalance = (String) request.getParameter("systemBalanceFee");
		String deposit = (String) request.getParameter("depositFee");
		String penalty = (String) request.getParameter("d_penaltyFee");
		String remark = (String) request.getParameter("d_remark");
		String penatlyReason = (String) request.getParameter("d_penatlyReason");
		if (penatlyReason == null) {
			penatlyReason = "";
		}
		String hdlFLag = "3";
		BigDecimal cardBalanceFee = new BigDecimal(cardBalance);
		BigDecimal systemBalanceFee = new BigDecimal(systemBalance);
		BigDecimal depositFee = new BigDecimal(deposit);
		BigDecimal penaltyFee = new BigDecimal(penalty);
		BigDecimal retrunBala = new BigDecimal("0");
		vo.setBusinessReceiptId(detailReceiptId);
		vo.setCardBalanceFee(cardBalanceFee);
		vo.setSystemBalanceFee(systemBalanceFee);
		vo.setDepositFee(depositFee);
		vo.setPenaltyFee(penaltyFee);
		retrunBala = retrunBala.add(cardBalanceFee);
		retrunBala = retrunBala.add(depositFee);
		// 理论应退款=票卡余额+押金
		vo.setReturnBala(retrunBala);
		// 实际退款余额=票卡余额+押金-罚金
		retrunBala = retrunBala.subtract(penaltyFee);
		if (retrunBala.compareTo(BigDecimal.ZERO) >= 0) {
			vo.setActualReturnBala(retrunBala);
		} else {
			vo.setActualReturnBala(BigDecimal.ZERO);
		}
		vo.setHdlFlag(hdlFLag);
		vo.setRemark(remark);
		vo.setPenaltyReason(penatlyReason);

		return vo;
	}

	private OperationResult queryForOp(HttpServletRequest request, NonReturnApplyHandleMapper mapper,
			OperationLogMapper operationLogMapper, OperationResult or) throws Exception {
		LogUtil logUtil = new LogUtil();
		NonReturnApplyHandle queryCondition;
		List<NonReturnApplyHandle> resultSet;
		try {
			queryCondition = this.getQueryConditionForOp(request);
			resultSet = mapper.getNonReturnApplyQuery(queryCondition);
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

	private NonReturnApplyHandle getQueryConditionForOp(HttpServletRequest request) {
		NonReturnApplyHandle vo = new NonReturnApplyHandle();
		HashMap vQueryControlDefaultValues = null;
		String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
		vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
		if (!vQueryControlDefaultValues.isEmpty()) {
			String businessReceiptId = FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues,
					"q_businessReceiptId");
			String beginDatetime = FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_beginDatetime");
			String endDatetime = FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_endDatetime");
			String handleType = FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_handleType");
			String queryCondition = FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues,
					"q_queryCondition");
			String lineId = FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_lineId");
			String stationId = FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_stationId");
			if (!"".equals(beginDatetime)) {
				beginDatetime = beginDatetime + " 00:00:00";
				vo.setBeginDatetime(beginDatetime);
			}
			if (!"".equals(endDatetime)) {
				endDatetime = endDatetime + " 23:59:59";
				vo.setEndDatetime(endDatetime);
			}
			if (!"".equals(lineId)) {
				vo.setLineId(lineId);
			}
			if (!"".equals(stationId)) {
				vo.setStationId(stationId);
			}
			if (!"".equals(queryCondition) && !"".equals(businessReceiptId)) {
				if (queryCondition.equals(NonReturnApplyHandleConstant.QUERY_BY_BUSINESS_RECEIPT_ID)) {
					vo.setBusinessReceiptId(businessReceiptId);
				}
				if (queryCondition.equals(NonReturnApplyHandleConstant.QUERY_BY_PRINT_ID)) {
					vo.setCardPrintId(businessReceiptId);
				}
			}
			if (!"".equals(handleType)) {
				vo.setHandleType(handleType);
			}
		}
		return vo;
	}

	private NonReturnApplyHandle getResultByBusinessReceiptIdAfterConfirm(OperationResult opResult,
			HttpServletRequest request, OperationLogMapper operationLogMapper) throws Exception {
		NonReturnApplyHandle vo = new NonReturnApplyHandle();
		LogUtil logUtil = new LogUtil();
		try {
			String businessReceiptId = request.getParameter("detailReceiptId").trim();
			vo = mapper.getNonReturnApplyQueryByBusinessReciptId(businessReceiptId);
			this.isBlackCard(vo);
			this.isApplied(vo);
			this.setDefaultBrokenFlag(vo);
		} catch (Exception e) {
			e.printStackTrace();
			 logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e,
			 operationLogMapper);
		}
		return vo;
	}

	private void writeOperatorLog(NonReturnApplyHandle selectVo, HttpServletRequest request, String type)
			throws Exception {
		NonReturnOpLog vo = new NonReturnOpLog();
		LogUtil logUtil = new LogUtil();
		User user = (User) request.getSession().getAttribute("User");
		vo.setOperatorId(user.getAccount());
		vo.setOperateType(type);
		vo.setReceiptId(selectVo.getBusinessReceiptId());
		vo.setCardPrintId(selectVo.getCardPrintId());
		vo.setCardBalanceFee(selectVo.getCardBalanceFee());
		vo.setIsBroken(selectVo.getIsBroken());
		
                vo.setRemark(selectVo.getRemark());
		

		vo.setDepositFee(selectVo.getDepositFee());
		vo.setPenaltyFee(selectVo.getPenaltyFee());
		vo.setActualReturnBala(selectVo.getActualReturnBala());
		vo.setPenaltyReason(selectVo.getPenaltyReason());
		vo.setHdlFlag(selectVo.getHdlFlag());
		vo.setOldNo("");
		vo.setReturnType("1");
		try {
			int result = logMapper.insertLog(vo);
			if (result==0){
                               throw new Exception("插入操作日志失败："+type);
                        }
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
		}
	}

	private int refusedRefund(OperationResult opResult, HttpServletRequest request, NonReturnApplyHandleMapper mapper,
			OperationLogMapper operationLogMapper) throws Exception {
		LogUtil logUtil = new LogUtil();
		int result = 0;
		try {
			result = mapper.confirmRefundOrNot(this.getDetailMsgForRefused(request));
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_REFUSED_REFUND, e, operationLogMapper);
		}
		return result;
	}

	private NonReturnApplyHandle getDetailMsgForRefused(HttpServletRequest request) {
		NonReturnApplyHandle vo = new NonReturnApplyHandle();
		String detailReceiptId = (String) request.getParameter("detailReceiptId");
		String remark = (String) request.getParameter("d_remark");
		String penatlyReason = "";
		String blackCardFlag = (String) request.getParameter("blackCardFlag");
		String hdlFLag = "1";
		if (NonReturnApplyHandleConstant.FLAG_BLACK_CARD.equals(blackCardFlag)) {
			hdlFLag = "4";
		} else {// 增加标识
			remark = remark + " 拒绝退款";
		}
		vo.setBusinessReceiptId(detailReceiptId);
		vo.setCardBalanceFee(BigDecimal.ZERO);
		vo.setSystemBalanceFee(BigDecimal.ZERO);
		vo.setDepositFee(BigDecimal.ZERO);
		vo.setPenaltyFee(BigDecimal.ZERO);
		vo.setReturnBala(BigDecimal.ZERO);
		vo.setActualReturnBala(BigDecimal.ZERO);
		vo.setHdlFlag(hdlFLag);
		vo.setRemark(remark);
		vo.setPenaltyReason(penatlyReason);

		return vo;
	}

	private int confirmAudit(OperationResult opResult, HttpServletRequest request, NonReturnApplyHandleMapper mapper,
			OperationLogMapper operationLogMapper) throws Exception {
		LogUtil logUtil = new LogUtil();
		int result = 0;
		try {
			result = mapper.confirmAudit(this.getDetailMsgForAudit(request));
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CONFIRM_AUDIT, e, operationLogMapper);
		}
		return result;
	}

	private NonReturnApplyHandle getDetailMsgForAudit(HttpServletRequest request) {
		NonReturnApplyHandle vo = new NonReturnApplyHandle();
		String detailReceiptId = (String) request.getParameter("detailReceiptId");
		vo.setBusinessReceiptId(detailReceiptId);
		return vo;
	}

	private int confirmModify(OperationResult opResult, HttpServletRequest request, NonReturnApplyHandleMapper mapper,
			OperationLogMapper operationLogMapper) throws Exception {
		NonReturnApplyHandle vo = new NonReturnApplyHandle();
		LogUtil logUtil = new LogUtil();
		int result = 0;
		try {
			vo = this.getDetailMsgForModify(request);
			result = mapper.confirmModify(vo);
			if (NonReturnApplyHandleConstant.FLAG_TICKET_NO_HANDLE.equals(vo.getHdlFlag())) {
				hisMapper.deletByCardLogicalId(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CONFIRM_REFUND, e, operationLogMapper);
		}
		return result;
	}

	private NonReturnApplyHandle getDetailMsgForModify(HttpServletRequest request) {
		NonReturnApplyHandle vo = new NonReturnApplyHandle();
		String detailReceiptId = (String) request.getParameter("detailReceiptId");
		String remark = (String) request.getParameter("d_remark");
		String hdlFLag = (String) request.getParameter("d_handleFlag");
		String cardLogicalId = (String) request.getParameter("cardLogicalId");
		// 操作员修改
		if (hdlFLag == null) {
			String cardBalance = (String) request.getParameter("cardBalanceFee");
			String deposit = (String) request.getParameter("depositFee");
			String penalty = (String) request.getParameter("d_penaltyFee");
                        String penaltyReason = (String) request.getParameter("d_penatlyReason");
                        vo.setPenaltyReason(penaltyReason);
			BigDecimal cardBalanceFee = new BigDecimal(cardBalance);
			BigDecimal depositFee = new BigDecimal(deposit);
			BigDecimal penaltyFee = new BigDecimal(penalty);
			BigDecimal retrunBala = new BigDecimal("0");
			vo.setPenaltyFee(penaltyFee);
			retrunBala = retrunBala.add(cardBalanceFee);
			retrunBala = retrunBala.add(depositFee);
                        //理论应退款=票卡余额+押金
                        vo.setReturnBala(retrunBala);
                        //实际应退款=票卡余额+押金-罚金
			retrunBala = retrunBala.subtract(penaltyFee);
			
			if (retrunBala.compareTo(BigDecimal.ZERO) >= 0) {
				vo.setActualReturnBala(retrunBala);
			} else {
				vo.setActualReturnBala(BigDecimal.ZERO);
			}
		}
		vo.setCardLogicalId(cardLogicalId);
		vo.setBusinessReceiptId(detailReceiptId);
		vo.setHdlFlag(hdlFLag);
		vo.setRemark(remark);
		return vo;
	}

	@RequestMapping("/NonReturnApplyHandleExportAll")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		NonReturnApplyHandle queryCondition = null;
		if (request.getSession().getAttribute(NonReturnApplyHandleConstant.EXPORT_EXCEL_VO_KEY_FIRST) != null) {
			Object obj = request.getSession().getAttribute(NonReturnApplyHandleConstant.EXPORT_EXCEL_VO_KEY_FIRST);
			if (obj instanceof NonReturnApplyHandle) {
				queryCondition = (NonReturnApplyHandle) request.getSession().getAttribute(NonReturnApplyHandleConstant.EXPORT_EXCEL_VO_KEY_FIRST);
			}
		}
		List<Map> queryResults = mapper.queryToMap(queryCondition);
		List<CodePubFlag> handleFlags = this.getHandleFlag();
		List<CodePubFlag> auditFlags = this.getAuditFlag();
		List<PubFlag> stations = pubFlagMapper.getStations();
		/* 查询结果部分内容转换中文 */
		for (Map vo : queryResults) {
			if (stations != null && !stations.isEmpty()) {
				vo.put("STATION_ID",
						DBUtil.getTextByCode(vo.get("STATION_ID").toString(), vo.get("LINE_ID").toString(), stations));
			}
			if (handleFlags != null && !handleFlags.isEmpty()) {
				if (vo.get("HDL_FLAG") != null) {
					vo.put("HDL_FLAG", this.getTextByCode(vo.get("HDL_FLAG").toString(), handleFlags));
				}
			}
			if (auditFlags != null && !auditFlags.isEmpty()) {
				if (vo.get("AUDIT_FLAG") != null) {
					vo.put("AUDIT_FLAG", this.getTextByCode(vo.get("AUDIT_FLAG").toString(), auditFlags));
				}
			}
			// 转换申请时间
			vo.put("APPLY_DAYS", DateHelper.daysBetweenTwo(DateHelper.stringToDate(vo.get("APPLY_DATETIME").toString()),
					new Date()));

		}
		ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
	}

	@RequestMapping("/NonReturnApplyHandleHisExportAll")
	public void exportHisExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		NonReturnApplyHandle queryCondition = new NonReturnApplyHandle();

		if (request.getSession().getAttribute(NonReturnApplyHandleConstant.EXPORT_EXCEL_VO_KEY_SECOND) != null) {
			Object obj = request.getSession().getAttribute(NonReturnApplyHandleConstant.EXPORT_EXCEL_VO_KEY_SECOND);
			if (obj instanceof NonReturnApplyHandle) {
				queryCondition = (NonReturnApplyHandle) request.getSession().getAttribute(NonReturnApplyHandleConstant.EXPORT_EXCEL_VO_KEY_SECOND);
			}
		}else {
			//一开始不让她导出全部数据
			queryCondition.setCardLogicalId("1");
		}
		List<Map> queryResults =null;
		List<Map> needResults = null;
		queryResults = hisMapper.queryToMap(queryCondition);
		List<CodePubFlag> limitRowNum = this.getLimitRowNum();
		String limitRowStr = limitRowNum.get(0).getCode().trim();
		int limitRow = Integer.parseInt(limitRowStr);
		if (limitRow < queryResults.size()) {
			needResults = new ArrayList<>(queryResults.subList(0, limitRow));
			for(int i =limitRow; i <  queryResults.size() ; i++) {
				Map po = queryResults.get(i);
				if(po.get("DEAL_TYPE")!=null) {
					if ("3".equals(po.get("DEAL_TYPE").toString()) || "5".equals(po.get("DEAL_TYPE").toString()) || "7".equals(po.get("DEAL_TYPE").toString())) {
						needResults.add(po);
					}
				}
			}
		}else {
			needResults = queryResults;
		}

		List<CodePubFlag> dealTypes = this.getDealType();
		List<PubFlag> lines = pubFlagMapper.getStations();
		List<PubFlag> stations = pubFlagMapper.getStations();
		/* 查询结果部分内容转换中文 */
		for (Map vo : needResults) {
			if (stations != null && !stations.isEmpty()) {
				vo.put("STATION_ID",
						DBUtil.getTextByCode(vo.get("STATION_ID").toString(), vo.get("LINE_ID").toString(), stations));
			}
			if (lines != null && !lines.isEmpty()) {
				vo.put("LINE_ID",
						DBUtil.getTextByCode(vo.get("LINE_ID").toString(), lines));
			}

			if (dealTypes != null && !dealTypes.isEmpty()) {
				if (vo.get("DEAL_TYPE") != null) {
					vo.put("DEAL_TYPE", this.getTextByCode(vo.get("DEAL_TYPE").toString(), dealTypes));
				}
			}

		}
		ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(needResults, request));
	}
}
