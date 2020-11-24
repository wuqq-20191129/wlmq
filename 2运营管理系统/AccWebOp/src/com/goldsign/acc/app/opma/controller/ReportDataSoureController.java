/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.controller;

import com.goldsign.acc.app.opma.entity.ReportDataSoure;
import com.goldsign.acc.app.opma.mapper.ReportDataSoureMapper;
import com.goldsign.acc.app.querysys.controller.CodePubFlagController;
import com.goldsign.acc.app.querysys.entity.CodePubFlag;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.constant.WebConstant;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.CharUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.util.Encryption;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @desc:报表数据源管理
 * @author:zhongzqi
 * @create date: 2017-6-14
 */
@Controller
public class ReportDataSoureController extends CodePubFlagController {

    @Autowired
    private ReportDataSoureMapper mapper;

    @RequestMapping("/ReportDataSource")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/opma/report_datasource.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))
                {
                    opResult = this.modify(request, this.mapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))
                {
                    opResult = this.delete(request, this.mapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))
                {
                    opResult = this.add(request, this.mapper, this.operationLogMapper);
                }

                if (command.equals(CommandConstant.COMMAND_QUERY))
                {
                    opResult = this.query(request, this.mapper, this.operationLogMapper);

                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        //保存查询条件
                        this.saveQueryControlDefaultValues(request, mv);
                    }
                    this.queryForOp(request, this.mapper, this.operationLogMapper, opResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.baseHandler(request, response, mv);
        this.SaveOperationResult(mv, opResult);
        this.saveAllDataSource(mv);
        this.setDataSourceIdText(mv);
        return mv;
    }

    private void saveAllDataSource(ModelAndView mv) {
        List<CodePubFlag> list = this.getReportDataSource();
        mv.addObject("reportDataSources", list);
    }

    private void setDataSourceIdText(ModelAndView mv) {

        List<ReportDataSoure> resultSet = (List<ReportDataSoure>) mv.getModelMap().get(WebConstant.ATT_ResultSet);
        List<CodePubFlag> list = (List<CodePubFlag>) mv.getModelMap().get("reportDataSources");
        if (resultSet != null && !resultSet.isEmpty()) {
            for (ReportDataSoure vo : resultSet) {
                if (list != null && !list.isEmpty()) {
                    vo.setDsName(getTextByCode(vo.getDsId(), list));
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

    private OperationResult add(HttpServletRequest request, ReportDataSoureMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        ReportDataSoure vo = this.getReqAttribute(request, CommandConstant.COMMAND_ADD);
        String preMsg = "报表数据源:"  + vo.getDsName()+ "";
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            if (this.existRecord(vo, mapper)) {
                rmsg.addMessage(preMsg + "记录已存在！");
                return rmsg;
            }
            n = this.addByTrans(request, mapper, vo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), operationLogMapper);
        rmsg.addMessage(LogConstant.addSuccessMsg(n));
        return rmsg;
    }

    private ReportDataSoure getReqAttribute(HttpServletRequest request, String type)  {
        ReportDataSoure vo = new ReportDataSoure();
        vo.setDsId(FormUtil.getParameter(request, "d_dsId"));
        vo.setDsUser(FormUtil.getParameter(request, "d_dsUser"));
        vo.setRemark(FormUtil.getParameter(request, "d_remark"));
        if (CommandConstant.COMMAND_ADD.equals(type) || CommandConstant.COMMAND_MODIFY.equals(type)) {
            String password = FormUtil.getParameter(request, "d_dsPass").trim();
            if (password.trim().length() != 0) {
                password = Encryption.biEncrypt(password);
                vo.setDsPass(password);
            }
        }
        return vo;
    }

    private boolean existRecord(ReportDataSoure vo, ReportDataSoureMapper mapper) {
        List<ReportDataSoure> list = mapper.getReportDataSourceList(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private int addByTrans(HttpServletRequest request, ReportDataSoureMapper mapper, ReportDataSoure vo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = mapper.addWithModel(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private OperationResult query(HttpServletRequest request, ReportDataSoureMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        ReportDataSoure queryCondition;
        List<ReportDataSoure> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = mapper.getReportDataSourceList(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    private ReportDataSoure getQueryCondition(HttpServletRequest request) {
        ReportDataSoure qCon = new ReportDataSoure();
        qCon.setDsId(FormUtil.getParameter(request, "q_dsId"));
        return qCon;
    }

    private OperationResult modify(HttpServletRequest request, ReportDataSoureMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        ReportDataSoure vo = this.getReqAttribute(request, CommandConstant.COMMAND_MODIFY);
        String preMsg = "报表数据源:" + vo.getDsName()+ ",数据源账号:"+vo.getDsName();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.modifyByTrans(request, mapper, vo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), operationLogMapper);
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));
        return rmsg;
    }

    private int modifyByTrans(HttpServletRequest request, ReportDataSoureMapper mapper, ReportDataSoure vo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = mapper.updateWithModel(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private OperationResult queryForOp(HttpServletRequest request, ReportDataSoureMapper mapper, OperationLogMapper operationLogMapper, OperationResult opResult) throws Exception {
         LogUtil logUtil = new LogUtil();
        ReportDataSoure vo;
        List<ReportDataSoure> resultSet;
        try {
            vo = this.getQueryConditionForOp(request);
            resultSet = mapper.getReportDataSourceList(vo);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return opResult;
    }

    private ReportDataSoure getQueryConditionForOp(HttpServletRequest request) {
        ReportDataSoure qCon = new ReportDataSoure();
        qCon.setDsId(FormUtil.getParameter(request, "d_dsId"));
        return qCon;
    }

    private OperationResult delete(HttpServletRequest request, ReportDataSoureMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
         OperationResult rmsg = new OperationResult();
        Vector<ReportDataSoure> pos = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.deleteByTrans(request, mapper,pos);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), operationLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    private Vector<ReportDataSoure> getReqAttributeForDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<ReportDataSoure> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private Vector<ReportDataSoure> getDeleteIDs(String selectIds) {
        StringTokenizer st = new StringTokenizer(selectIds, ";");
        String strIds = null;
        Vector<ReportDataSoure> list = new Vector();
        ReportDataSoure po;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            po = this.geDeleteKey(strIds, "#");
            list.add(po);
        }
        return list;
    }

    private ReportDataSoure geDeleteKey(String strIds, String delim) {
        StringTokenizer st = new StringTokenizer(strIds, delim);
        String tmp = null;
        int i = 0;
        ReportDataSoure po = new ReportDataSoure();;
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                po.setDsId(tmp);
                continue;
            }

        }
        return po;
    }

    private int deleteByTrans(HttpServletRequest request, ReportDataSoureMapper mapper, Vector<ReportDataSoure> pos) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (ReportDataSoure po : pos) {
                n += mapper.deleteByDsId(po);
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

}
