/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.goldsign.acc.app.prminfo.entity.BlackListMtrSec;
import com.goldsign.acc.app.prminfo.mapper.BlackListMtrSecMapper;
import com.goldsign.acc.app.querysys.controller.CodePubFlagController;
import com.goldsign.acc.app.querysys.entity.CodePubFlag;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.ConfigConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.constant.WebConstant;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DateUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.vo.User;

/**
 * @desc:地铁黑名单段
 * @author:zhongzqi
 * @create date: 2017-6-12
 */
@Controller
public class BlackListMtrSecController extends CodePubFlagController {

    @Autowired
    private BlackListMtrSecMapper blackListMtrSecMapper;

    @RequestMapping("/BlackListMtrSec")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/prminfo/black_list_mtr_sec.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))
                {
                    opResult = this.modify(request, this.blackListMtrSecMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))
                {
                    opResult = this.delete(request, this.blackListMtrSecMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))
                {
                    opResult = this.add(request, this.blackListMtrSecMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_QUERY))
                {
                    opResult = this.query(request, this.blackListMtrSecMapper, this.operationLogMapper);
                }
            }
            if (command == null) {
                opResult = this.queryNum(request, this.blackListMtrSecMapper, this.operationLogMapper);
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        //保存查询条件
                        this.saveQueryControlDefaultValues(request, mv);
                    }
                    this.queryForOp(request, this.blackListMtrSecMapper, this.operationLogMapper, opResult);
                }
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
    	List<BlackListMtrSec> resultSet = (List<BlackListMtrSec>) opResult.getReturnResultSet();
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

        for (BlackListMtrSec vo : resultSet) {
            if (blackResons != null && !blackResons.isEmpty()) {
                vo.setBlackTypeText(getTextByCode(vo.getBlackType(), blackResons));
            }
        }
	}

	private void getDistinctOperatorId(ModelAndView mv) {
		// TODO Auto-generated method stub
    	List<BlackListMtrSec> operatorIds = blackListMtrSecMapper.getDistinctOperatorId();
        mv.addObject("operatorIds", operatorIds);

	}

	private OperationResult query(HttpServletRequest request, BlackListMtrSecMapper blackListMtrSecMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        BlackListMtrSec queryCondition;
        List<BlackListMtrSec> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = blackListMtrSecMapper.getBlackListMtrSec(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    public OperationResult queryForOp(HttpServletRequest request, BlackListMtrSecMapper mapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        // OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        BlackListMtrSec vo;
        List<BlackListMtrSec> resultSet;
        try {
            vo = this.getQueryConditionForOp(request);
            resultSet = mapper.getBlackListMtrSec(vo);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    private BlackListMtrSec getQueryCondition(HttpServletRequest request) {
        BlackListMtrSec qCon = new BlackListMtrSec();
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

    private BlackListMtrSec getReqAttribute(HttpServletRequest request, String type) {
        BlackListMtrSec vo = new BlackListMtrSec();
        vo.setBeginLogicalId(FormUtil.getParameter(request, "d_beginLogicalId"));
        vo.setEndLogicalId(FormUtil.getParameter(request, "d_endLogicalId"));
        vo.setBlackType(FormUtil.getParameter(request, "d_blackType"));
        vo.setGenDatetime(FormUtil.getParameter(request, "d_genDate") + " 00:00:00");
        vo.setRemark(FormUtil.getParameter(request, "d_remark"));
        vo.setActionType(FormUtil.getParameter(request, "d_actionType"));
        Date today = new Date();
        Timestamp curternime = new Timestamp(today.getTime());
        User user = (User) request.getSession().getAttribute("User");
        if (CommandConstant.COMMAND_MODIFY.equals(type) || CommandConstant.COMMAND_DELETE.equals(type)) {
            String balanceWaterNo = blackListMtrSecMapper.getMaxBalanceWaterNo();
            if ("".equals(balanceWaterNo) || balanceWaterNo == null) {
                vo.setBalanceWaterNo("0000000000");
            } else {
                vo.setBalanceWaterNo(balanceWaterNo);
            }
            vo.setHandleDatetime(DateUtil.convertTimestamptoString(curternime));
            vo.setHandleOperatorId(user.getAccount());
            vo.setHandleType(type);
        } else if (CommandConstant.COMMAND_ADD.equals(type)) {
            vo.setOperatorId(user.getAccount());
            vo.setCreateDatetime(DateUtil.convertTimestamptoString(curternime));
        }
        return vo;
    }
//private boolean existRecord()

    private OperationResult modify(HttpServletRequest request, BlackListMtrSecMapper blackListMtrSecMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        BlackListMtrSec vo = this.getReqAttribute(request, CommandConstant.COMMAND_MODIFY);
        String preMsg = "地铁黑名单:" + "开始逻辑卡号：" + vo.getBeginLogicalId() + "" + " 结束逻辑卡号：" + vo.getEndLogicalId();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.modifyByTrans(request, blackListMtrSecMapper, vo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), operationLogMapper);
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));
        return rmsg;
    }

    private void getBlackResonAndSetResultBlackText(ModelAndView mv) {
        List<CodePubFlag> blackResons = this.getBlackReson();
        mv.addObject("blackResons", blackResons);
        List<BlackListMtrSec> resultSet = (List<BlackListMtrSec>) mv.getModelMap().get(WebConstant.ATT_ResultSet);
        if (resultSet != null && !resultSet.isEmpty()) {
            for (BlackListMtrSec vo : resultSet) {
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

    private BlackListMtrSec getQueryConditionForOp(HttpServletRequest request) {

        BlackListMtrSec qCon = new BlackListMtrSec();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            qCon.setBeginLogicalId(FormUtil.getParameter(request, "d_beginLogicalId"));
            qCon.setEndLogicalId(FormUtil.getParameter(request, "d_endLogicalId"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setBeginLogicalId(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_beginLogicalId"));
                qCon.setEndLogicalId(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_endLogicalId"));
            }
        }
        return qCon;
    }

    private int modifyByTrans(HttpServletRequest request, BlackListMtrSecMapper blackListMtrMapper, BlackListMtrSec po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = blackListMtrSecMapper.insertIntoLog(po);
            n = blackListMtrSecMapper.updateWithModel(po);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private OperationResult delete(HttpServletRequest request, BlackListMtrSecMapper blackListMtrSecMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        BlackListMtrSec prmVo = this.getReqAttribute(request, CommandConstant.COMMAND_DELETE);
        Vector<BlackListMtrSec> pos = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.deleteByTrans(request, blackListMtrSecMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), operationLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    private Vector<BlackListMtrSec> getReqAttributeForDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<BlackListMtrSec> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private Vector<BlackListMtrSec> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<BlackListMtrSec> list = new Vector();
        BlackListMtrSec po;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            po = this.getBlackListMtrWithDeleteKey(strIds, "#");
            list.add(po);
        }
        return list;
    }

    private BlackListMtrSec getBlackListMtrWithDeleteKey(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        BlackListMtrSec po = new BlackListMtrSec();;
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                po.setBeginLogicalId(tmp);
                continue;
            }
            if (i == 2) {
                po.setEndLogicalId(tmp);
                continue;
            }

        }
        return po;
    }

    private int deleteByTrans(HttpServletRequest request, BlackListMtrSecMapper blackListMtrSecMapper, Vector<BlackListMtrSec> pos, BlackListMtrSec prmVo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (BlackListMtrSec po : pos) {
                prmVo.setBeginLogicalId(po.getBeginLogicalId());
                prmVo.setEndLogicalId(po.getEndLogicalId());
                blackListMtrSecMapper.insertIntoLog(prmVo);
                n += blackListMtrSecMapper.deleteByCardLogicalId(po);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private OperationResult add(HttpServletRequest request, BlackListMtrSecMapper blackListSecMtrMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        BlackListMtrSec vo = this.getReqAttribute(request, CommandConstant.COMMAND_ADD);
        String preMsg = "地铁黑名单段:" + "开始逻辑卡号：" + vo.getBeginLogicalId() + " " + "结束逻辑卡号:" + vo.getEndLogicalId();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            int maxNum = blackListSecMtrMapper.queryNum();
            if (maxNum >= ConfigConstant.MAX_BLACKLIST_SECTION_NUMBER) {
                rmsg.addMessage("地铁黑名单段数量累加已到达限定的" + ConfigConstant.MAX_BLACKLIST_SECTION_NUMBER + "个，不能再添加新的黑名单段！");
                return rmsg;
            }
            String checkInBlackListMtr;
            checkInBlackListMtr = this.checkInBlackListMtr(vo, blackListSecMtrMapper);
            if (!"".equals(checkInBlackListMtr) && checkInBlackListMtr != null) {
                rmsg.addMessage("存在地铁单个黑名单如！" + checkInBlackListMtr + "在" + vo.getBeginLogicalId() + "和" + vo.getEndLogicalId() + "之间");
                return rmsg;
            }
            String checkResult = null;
            checkResult = this.existRecord(vo, blackListSecMtrMapper);
            if (!"".equals(checkResult)) {
                rmsg.addMessage(preMsg + "记录已存在！与下列段" + checkResult + "存在重合");
                return rmsg;
            }
            n = this.addByTrans(request, blackListSecMtrMapper, vo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), operationLogMapper);
        rmsg.addMessage(LogConstant.addSuccessMsg(n));
        return rmsg;
    }

    private String existRecord(BlackListMtrSec vo, BlackListMtrSecMapper blackListMtrSecMapper) {
        String result = "";
        List<BlackListMtrSec> list = blackListMtrSecMapper.getAllBlackListMtrSecForCheck();
        if (list.size() > 0) {
            StringBuffer buf = new StringBuffer();
            for (BlackListMtrSec po : list) {
                String beginLogic = po.getBeginLogicalId();
                String endLogic = po.getEndLogicalId();
                if (!(vo.getBeginLogicalId().compareTo(endLogic) > 0 || vo.getEndLogicalId().compareTo(beginLogic) < 0)) {
                    buf.append(beginLogic).append("-").append(endLogic);
//                    result += beginLogic + "-" + endLogic;
                }
            }
            result = buf.toString();
        }
        return result;
    }

    private int addByTrans(HttpServletRequest request, BlackListMtrSecMapper blackListMtrSecMapper, BlackListMtrSec vo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = blackListMtrSecMapper.addWithModel(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private OperationResult queryNum(HttpServletRequest request, BlackListMtrSecMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = mapper.queryNum();
            if (n >= ConfigConstant.MAX_BLACKLIST_SECTION_NUMBER) {
                or.addMessage("地铁黑名单段累计数量已不小于限额" + ConfigConstant.MAX_BLACKLIST_SECTION_NUMBER + "条记录,不能再添加和黑名单段");
            } else {
                or.addMessage("地铁黑名单段累计已有" + n + "条记录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + n + "条记录", operationLogMapper);
        return or;
    }

    private String checkInBlackListMtr(BlackListMtrSec vo, BlackListMtrSecMapper blackListSecMtrMapper) {
        String result = "";
        result = blackListSecMtrMapper.checkInBlackListMtr(vo);
        return result;
    }
    @RequestMapping("/BlackListMtrSecExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request,BlackListMtrSec.class.getName());
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields,results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
}
