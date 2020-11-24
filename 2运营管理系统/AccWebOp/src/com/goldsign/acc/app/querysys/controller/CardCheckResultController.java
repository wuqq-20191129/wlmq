/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.app.querysys.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.goldsign.acc.app.config.mapper.ConfigMapper;
import com.goldsign.acc.app.prminfo.entity.BlackListMtr;
import com.goldsign.acc.app.prminfo.entity.BlackListMtrSec;
import com.goldsign.acc.app.prminfo.mapper.BlackListMtrMapper;
import com.goldsign.acc.app.prminfo.mapper.BlackListMtrSecMapper;
import com.goldsign.acc.app.querysys.entity.StCardCheckResult;
import com.goldsign.acc.app.querysys.mapper.StCardCheckResultMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.WebConstant;
import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.vo.User;

/**
 * @author 刘粤湘
 * @date 2017-11-1 15:10:47
 * @version V1.0
 * @desc 取值卡余额差异查询
 */
@Controller
public class CardCheckResultController extends CodePubFlagController {

	@Autowired
	StCardCheckResultMapper crdChkResMapper;

	@Autowired
	private BlackListMtrMapper blackListMtrMapper;

	@Autowired
	private BlackListMtrSecMapper blackListMtrSecMapper;

	@Autowired
	private ConfigMapper configMapper;
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(CardCheckResultController.class.getName());

	@RequestMapping("/CardCheckResult")
	public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/jsp/querysys/card_check_result.jsp");
		OperationResult opResult = new OperationResult();
		String command = request.getParameter("command");

		try {
			if (command != null) {
				command = command.trim();
				if (command.equals(CommandConstant.COMMAND_QUERY))// 查询操作
				{
					StCardCheckResult qryCrdChkResCon = new StCardCheckResult();
					String qCrdMainId = request.getParameter("q_cardMainID");
					String qSubId = request.getParameter("q_cardSubID");
					if (qCrdMainId != null && "".equals(qCrdMainId.trim())) {
						qCrdMainId = null;
					}
					if (qSubId != null && "".equals(qSubId.trim())) {
						qSubId = null;
					}
					qryCrdChkResCon.setCardMainId(qCrdMainId);
					qryCrdChkResCon.setCardSubId(qSubId);
					qryCrdChkResCon.setCardLogicalId(request.getParameter("q_tkLogicNo"));
					qryCrdChkResCon.setDiffCardSysFee(BigDecimal.valueOf(Long.parseLong(
							request.getParameter("q_rtnBala") == null ? "0" : request.getParameter("q_rtnBala"))));
					qryCrdChkResCon.setOperator(request.getParameter("q_rtnOper"));

					List<StCardCheckResult> cardCheckResults = crdChkResMapper.carChkQry(qryCrdChkResCon);
//					request.getSession().setAttribute("qryCrdChkResCon", qryCrdChkResCon);
					opResult.setReturnResultSet(cardCheckResults);
				}
				if (command.equals(CommandConstant.COMMAND_IN_BLACK)) {
					String allSelectedIDs = request.getParameter("allSelectedIDs");
					String exitIds = "";
					String exitSecIds = "";
					List<BlackListMtrSec> blackListMtrSecs = blackListMtrSecMapper.getAllBlackListMtrSecForCheck();
					int n = 0;
					Map map = new HashMap();
					List list = new ArrayList();
					if (allSelectedIDs != null) {
						String[] logicIds = allSelectedIDs.split(";");
						start: for (String logicId : logicIds) {
							// 加入黑名单
							// 查询黑名单表总数
							List lAll = blackListMtrMapper.getBlackListMtr(null);
							if (lAll != null && lAll.size() > 10000) {
								opResult.addMessage("地铁黑名单数量累加已到达限定的10000个，不能再添加新的地铁黑名单");
							}
							// 判断是否已在黑名单表中存在
							BlackListMtr qCon = new BlackListMtr();
							qCon.setCardLogicalId(logicId);
							List l = blackListMtrMapper.getBlackListMtr(qCon);
							if (l != null && l.size() > 0) {
								exitIds += logicId;
								exitIds += ";";
								continue;
							}

							// 判断是否已在黑名单段表中存在
							for (BlackListMtrSec blackListMtrSec : blackListMtrSecs) {
								if (logicId.compareTo(blackListMtrSec.getBeginLogicalId()) >= 0
										&& logicId.compareTo(blackListMtrSec.getEndLogicalId()) <= 0) {

									exitSecIds += logicId;
									exitSecIds += ";";
									continue start;
								}
							}

							// 加入黑名单
							BlackListMtr blackListMtr = new BlackListMtr();
							blackListMtr.setGenDatetime(new SimpleDateFormat("yyyy-MM-dd ").format(new Date()));

							blackListMtr.setCardLogicalId(logicId);
							// System.out.println(logicId);
							blackListMtr.setBlackType("99");
							blackListMtr.setRemark("储值卡余额差异查询模块增加");
							blackListMtr.setActionType("000");
							blackListMtr
									.setCreateDatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
							User user = (User) request.getSession().getAttribute("User");
							blackListMtr.setOperatorId(user.getAccount());
							// System.out.println(blackListMtr.getOperatorId());
							TransactionStatus status = null;

							try {
								status = txMgr.getTransaction(this.def);
								blackListMtrMapper.addWithModel(blackListMtr);
								txMgr.commit(status);
								list.add(logicId);
								n++;
							} catch (Exception e) {
								if (txMgr != null) {
									txMgr.rollback(status);
								}
								throw e;
							}

						}
						if (!"".equals(exitIds)) {
							opResult.addMessage("逻辑卡号" + exitIds + "已在黑名单表中存在");
						}
						if (!"".equals(exitSecIds)) {
							opResult.addMessage("逻辑卡号" + exitSecIds + "已在黑名单段表中存在");
						}
						opResult.addMessage("成功增加" + n + "条记录");
						if (n > 0) {
							map.put("logicMaps", list);
							List<StCardCheckResult> cardCheckResults = crdChkResMapper.cardQryByMap(map);
							opResult.setReturnResultSet(cardCheckResults);
						}

						// System.out.println(cardCheckResults.size());
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (request.getParameterMap().containsKey("balaCheck")) {
			mv.addObject("balaCheckFlag", "true");
		} else {
			mv.addObject("balaCheckFlag", "false");
		}

		String[] attrNames = { CARDMAINS, CARDSUBS, CARDMAIN_SUBS };

		this.setPageOptions(attrNames, mv, request, response);// 设置页面线路、车站等选项值、版本
		this.baseHandler(request, response, mv);
		this.setResultText(mv,opResult,command);
		if (command != null && !"inBlack".equals(command)) {
			this.divideResultSet(request, mv, opResult);// 结果集分页
		}
		this.SaveOperationResult(mv, opResult);

//		if (command != null && !command.equals(CommandConstant.COMMAND_EXP_ALL)) {
//
//		}
//		if (command != null && command.equals(CommandConstant.COMMAND_EXP_ALL)) {
//			try {
//				/* 从session中获取查询条件 */
//				StCardCheckResult qryCrdChkResCon = null;
//				if (request.getSession().getAttribute("qryCrdChkResCon") != null) {
//					qryCrdChkResCon = (StCardCheckResult) request.getSession().getAttribute("qryCrdChkResCon");
//					/* 删除查询条件 */
//					request.getSession().removeAttribute("qryCrdChkResCon");
//				}
//
//				/* 获得数据集 */
//				logger.info("获得数据集");
//				List<Map> cardCheckResults = crdChkResMapper.carChkQryToMap(qryCrdChkResCon);
//				logger.info("获得数据集结束,数量：" + cardCheckResults.size());
//				List<PubFlag> cardSubs = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDSUBS);
//				/* 过滤票卡子类型 */
//				for (Map vo : cardCheckResults) {
//					if (cardSubs != null && !cardSubs.isEmpty()) {
//						vo.put("CARD_SUB_ID", DBUtil.getTextByCode((String) vo.get("CARD_SUB_ID"),
//								(String) vo.get("CARD_MAIN_ID"), cardSubs));
//					}
//				}
//
//				/* 获得下载文件流 */
//				ResponseEntity<byte[]> re = ExpUtil.expAll(cardCheckResults, request, configMapper);
//				return re;
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//		}
		return mv;
	}
	/**
	 * @author zhongziqi
	 * @param mv
	 * @param opResult
	 * @param command
	 * @throws Exception
	 */
	private void setResultText(ModelAndView mv, OperationResult opResult, String command) {
		// TODO Auto-generated method stub
		List<StCardCheckResult> resultSet = (List<StCardCheckResult>) opResult.getReturnResultSet();
		if (resultSet == null || resultSet.isEmpty()) {
			return;
		}
		if (command == null || command.equals(CommandConstant.COMMAND_NEXT)
				|| command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
				|| command.equals(CommandConstant.COMMAND_BACKEND) || command.equals(CommandConstant.COMMAND_GOPAGE)) {
			return;

		}
		// System.out.println(list.size());
		List<PubFlag> cardSubs = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDSUBS);
		for (StCardCheckResult vo : resultSet) {
			if (cardSubs != null && !cardSubs.isEmpty()) {
				// System.out.println(vo.getCardMainId() + vo.getCardSubId());
				vo.setCardSubId(DBUtil.getTextByCode(vo.getCardSubId(), vo.getCardMainId(), cardSubs));
			}
		}

	}

	/**
	 * @author zhongziqi
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/CardCheckResultExportAll")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request,StCardCheckResult.class.getName());
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields,results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
}
