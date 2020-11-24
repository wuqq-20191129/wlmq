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

import com.goldsign.acc.app.prminfo.entity.BlackListMtr;
import com.goldsign.acc.app.prminfo.entity.BlackListMtrSec;
import com.goldsign.acc.app.prminfo.mapper.BlackListMtrMapper;
import com.goldsign.acc.app.querysys.controller.CodePubFlagController;
import com.goldsign.acc.app.querysys.entity.CodePubFlag;
import com.goldsign.acc.frame.constant.CommandConstant;
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
 * @desc:地铁黑名单 参考StationDeviceController
 * @author:zhongzqi
 * @create date: 2017-6-7
 */
@Controller
public class BlackListMtrController extends CodePubFlagController {

    @Autowired
    private BlackListMtrMapper blackListMtrMapper;

    @RequestMapping("/BlackListMtr")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/prminfo/black_list_mtr.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))
                {
                    opResult = this.modify(request, this.blackListMtrMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))
                {
                    opResult = this.delete(request, this.blackListMtrMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))
                {
                    opResult = this.add(request, this.blackListMtrMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_QUERY))
                {
                    opResult = this.query(request, this.blackListMtrMapper, this.operationLogMapper);
                }
            }
            if (command == null) {
                opResult = this.queryNum(request, this.blackListMtrMapper, this.operationLogMapper);
            }
            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE)
                        || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE)
                        || command.equals(CommandConstant.COMMAND_SUBMIT)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        // 保存查询条件
                        this.saveQueryControlDefaultValues(request, mv);
                    }
                    this.queryForOp(request, this.blackListMtrMapper, this.operationLogMapper, opResult);
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
        List<BlackListMtr> resultSet = (List<BlackListMtr>) opResult.getReturnResultSet();
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

        for (BlackListMtr vo : resultSet) {
            if (blackResons != null && !blackResons.isEmpty()) {
                vo.setBlackTypeText(getTextByCode(vo.getBlackType(), blackResons));
            }
        }
	}

	private OperationResult query(HttpServletRequest request, BlackListMtrMapper blackListMtrMapper,
            OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        BlackListMtr queryCondition;
        List<BlackListMtr> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = blackListMtrMapper.getBlackListMtr(queryCondition);
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

    public OperationResult queryForOp(HttpServletRequest request, BlackListMtrMapper blackListMtrMapper,
            OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        // OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        BlackListMtr vo;
        List<BlackListMtr> resultSet;
        try {
            vo = this.getQueryConditionForOp(request);
            resultSet = blackListMtrMapper.getBlackListMtr(vo);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }


    private BlackListMtr getQueryCondition(HttpServletRequest request) {
        BlackListMtr qCon = new BlackListMtr();
        String beginTime = FormUtil.getParameter(request, "q_beginDatetime");
        String endTime = FormUtil.getParameter(request, "q_endDatetime");
        // if (!"".equals(beginTime)) {
        // beginTime = beginTime + " 00:00:00";
        // }
        // if (!"".equals(endTime)) {
        // endTime = endTime + " 23:59:59";
        // }
        qCon.setCardLogicalId(FormUtil.getParameter(request, "q_cardLogicalId"));
        qCon.setBlackType(FormUtil.getParameter(request, "q_blackType"));
        qCon.setOperatorId(FormUtil.getParameter(request, "q_operatorId"));
        qCon.setBeginEffectiveTime(beginTime);
        qCon.setEndEffectiveTime(endTime);

        return qCon;
    }

    private BlackListMtr getReqAttribute(HttpServletRequest request, String type) {
        BlackListMtr vo = new BlackListMtr();
        vo.setCardLogicalId(FormUtil.getParameter(request, "d_cardLogicalId"));
        vo.setBlackType(FormUtil.getParameter(request, "d_blackType"));
        vo.setGenDatetime(FormUtil.getParameter(request, "d_genDate") + " 00:00:00");
        vo.setRemark(FormUtil.getParameter(request, "d_remark"));
        vo.setActionType(FormUtil.getParameter(request, "d_actionType"));
        Date today = new Date();
        Timestamp curternime = new Timestamp(today.getTime());
        User user = (User) request.getSession().getAttribute("User");
        if (CommandConstant.COMMAND_MODIFY.equals(type) || CommandConstant.COMMAND_DELETE.equals(type)) {
            String balanceWaterNo = blackListMtrMapper.getMaxBalanceWaterNo();
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

    private OperationResult modify(HttpServletRequest request, BlackListMtrMapper blackListMtrMapper,
            OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        BlackListMtr vo = this.getReqAttribute(request, CommandConstant.COMMAND_MODIFY);
        String preMsg = "地铁黑名单:" + "逻辑卡号：" + vo.getCardLogicalId() + "";
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.modifyByTrans(request, blackListMtrMapper, vo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n),
                operationLogMapper);
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));
        return rmsg;
    }

    private void getBlackResonAndSetResultBlackText(ModelAndView mv) {
        List<CodePubFlag> blackResons = this.getBlackReson();
        mv.addObject("blackResons", blackResons);
        List<BlackListMtr> resultSet = (List<BlackListMtr>) mv.getModelMap().get(WebConstant.ATT_ResultSet);
        if (resultSet != null && !resultSet.isEmpty()) {
            for (BlackListMtr vo : resultSet) {
                if (blackResons != null && !blackResons.isEmpty()) {
                    vo.setBlackTypeText(getTextByCode(vo.getBlackType(), blackResons));
                }
            }
        }
    }
    private void getDistinctOperatorId(ModelAndView mv) {
        List<BlackListMtr> operatorIds = blackListMtrMapper.getDistinctOperatorId();
        mv.addObject("operatorIds", operatorIds);

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

    private BlackListMtr getQueryConditionForOp(HttpServletRequest request) {

        BlackListMtr qCon = new BlackListMtr();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        // 操作处于添加或修改模式
        if (FormUtil.isAddOrModifyMode(request)) {
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            qCon.setCardLogicalId(FormUtil.getParameter(request, "d_cardLogicalId"));
        } else {
            // 操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setCardLogicalId(
                        FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardLogicalId"));

            }
        }
        return qCon;
    }

    private int modifyByTrans(HttpServletRequest request, BlackListMtrMapper blackListMtrMapper, BlackListMtr po)
            throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = blackListMtrMapper.insertIntoLog(po);
            n = blackListMtrMapper.updateWithModel(po);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private OperationResult delete(HttpServletRequest request, BlackListMtrMapper blackListMtrMapper,
            OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        BlackListMtr prmVo = this.getReqAttribute(request, CommandConstant.COMMAND_DELETE);
        Vector<BlackListMtr> pos = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.deleteByTrans(request, blackListMtrMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), operationLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    private Vector<BlackListMtr> getReqAttributeForDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<BlackListMtr> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private Vector<BlackListMtr> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<BlackListMtr> list = new Vector();
        BlackListMtr po;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            po = this.getBlackListMtrWithDeleteKey(strIds, "#");
            list.add(po);
        }
        return list;
    }

    private BlackListMtr getBlackListMtrWithDeleteKey(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        BlackListMtr po = new BlackListMtr();
        ;
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                po.setCardLogicalId(tmp);
                continue;
            }

        }
        return po;
    }

    private int deleteByTrans(HttpServletRequest request, BlackListMtrMapper blackListMtrMapper,
            Vector<BlackListMtr> pos, BlackListMtr prmVo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (BlackListMtr po : pos) {
                prmVo.setCardLogicalId(po.getCardLogicalId());
                blackListMtrMapper.insertIntoLog(prmVo);
                n += blackListMtrMapper.deleteByCardLogicalId(po);
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

    private OperationResult add(HttpServletRequest request, BlackListMtrMapper blackListMtrMapper,
            OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        BlackListMtr vo = this.getReqAttribute(request, CommandConstant.COMMAND_ADD);
        String preMsg = "地铁黑名单:" + "逻辑卡号：" + vo.getCardLogicalId() + "";
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            if (this.existRecord(vo, blackListMtrMapper)) {
                rmsg.addMessage(preMsg + "记录已存在！");
                return rmsg;
            }
            String checkResult = null;
            checkResult = this.checkInBlackListMtrSec(vo, blackListMtrMapper);
            if (!"".equals(checkResult)) {
                rmsg.addMessage(preMsg + "已在下列段中" + checkResult + "");
                return rmsg;
            }
            n = this.addByTrans(request, blackListMtrMapper, vo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n),
                operationLogMapper);
        rmsg.addMessage(LogConstant.addSuccessMsg(n));
        return rmsg;
    }

    private boolean existRecord(BlackListMtr vo, BlackListMtrMapper blackListMtrMapper) {
        List<BlackListMtr> list = blackListMtrMapper.checkInBlackListMtr(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private int addByTrans(HttpServletRequest request, BlackListMtrMapper blackListMtrMapper, BlackListMtr vo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = blackListMtrMapper.addWithModel(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private OperationResult queryNum(HttpServletRequest request, BlackListMtrMapper blackListMtrMapper,
            OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = blackListMtrMapper.queryNum();
            // if (n >= ConfigConstant.MAX_METRO_BLACKLIST_NUMBER) {
            // or.addMessage("请注意!!地铁黑名单累计数量已不小于限额" +
            // ConfigConstant.MAX_METRO_BLACKLIST_NUMBER
            // + "条,不能再添加地铁黑名单");
            // } else {
            or.addMessage("地铁黑名单累计已有" + n + "条记录");
            // }
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + n + "条记录", operationLogMapper);
        return or;
    }

    private String checkInBlackListMtrSec(BlackListMtr vo, BlackListMtrMapper blackListMtrMapper) {
        String result = "";
        List<BlackListMtrSec> list = blackListMtrMapper.getAllBlackListMtrForCheckSec();

        if (list.size() > 0) {
            StringBuffer buf = new StringBuffer();
            for (BlackListMtrSec po : list) {
                String beginLogic = po.getBeginLogicalId();
                String endLogic = po.getEndLogicalId();
                if (!(vo.getCardLogicalId().compareTo(endLogic) > 0
                        || vo.getCardLogicalId().compareTo(beginLogic) < 0)) {
                    buf.append(beginLogic).append("-").append(endLogic);
//                    result += beginLogic + "-" + endLogic;
                    break;
                }
            }
            result = buf.toString();
        }
        return result;
    }

    @RequestMapping("/BlackListMtrExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request,BlackListMtr.class.getName());
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields,results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }


}
