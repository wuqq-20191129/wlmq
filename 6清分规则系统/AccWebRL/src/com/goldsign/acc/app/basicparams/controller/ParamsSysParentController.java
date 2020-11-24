/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicparams.controller;

import com.goldsign.acc.app.basicparams.entity.ParamsSys;
import com.goldsign.acc.app.basicparams.entity.ParamsThres;
import com.goldsign.acc.app.basicparams.mapper.ParamsSysMapper;
import com.goldsign.acc.app.basicparams.mapper.ParamsThresMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.RuleConstant;
import com.goldsign.acc.frame.controller.RLBaseController;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.util.PubUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.TransactionStatus;

/**
 * 系统参数设置
 *
 * @author luck
 */
public class ParamsSysParentController extends RLBaseController {

    protected int addPlanByTrans(HttpServletRequest request, ParamsSysMapper ptMapper, ParamsSys vo, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            request.setAttribute("typeCode", vo.getTypeCode());
            request.setAttribute("code", vo.getCode());
            status = txMgr.getTransaction(this.def);
            n = ptMapper.addPlan(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected int auditPlanByTrans(HttpServletRequest request, ParamsSysMapper ptMapper, Vector<ParamsSys> pos, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            List<String> aduitList = new ArrayList<String>();
            for (ParamsSys po : pos) {
                //更新之前的数据为历史版本
                aduitList.add(po.getTypeCode()+po.getCode()+RuleConstant.RECORD_FLAG_USE);
                int updateCurrentToHistory = ptMapper.updateCurrentToHistory(po);
                //审核数据为当前版本
                int auditUpdate = ptMapper.auditUpdate(po);
                n += auditUpdate;
            }
            request.setAttribute("aduitList", aduitList);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        } finally {
            return n;
        }
    }

    protected int deletePlanByTrans(HttpServletRequest request, ParamsSysMapper ptMapper, Vector<ParamsSys> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (ParamsSys po : pos) {
                po.setRecordFlag(RuleConstant.RECORD_FLAG_DRAFT);
                n += ptMapper.deletePlan(po);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected int modifyPlanByTrans(HttpServletRequest request, ParamsSysMapper ptMapper, ParamsSys vo, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            request.setAttribute("typeCode", vo.getTypeCode());
            request.setAttribute("code", vo.getCode());
            status = txMgr.getTransaction(this.def);
            n = ptMapper.modifyPlan(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected ParamsSys getQueryConditionForOpPlan(HttpServletRequest request, OperationResult opResult) {
        ParamsSys qCon = new ParamsSys();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {
            //操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;

            }
            qCon.setCode((String) request.getAttribute("code"));
            qCon.setTypeCode((String) request.getAttribute("typeCode"));
            qCon.setRecordFlag(RuleConstant.RECORD_FLAG_DRAFT);

        }else if(command.equals(CommandConstant.COMMAND_AUDIT)){
            qCon.setAduitList((List<String>) request.getAttribute("aduitList"));
        } 
        else {
            //操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setRecordFlag(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_typeCode"));
                qCon.setRecordFlag(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_code"));
                qCon.setRecordFlag(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_recorqFlag"));
                qCon.setRecordFlag(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_beginTime"));
                qCon.setRecordFlag(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_endTime"));
            }
        }
        return qCon;
    }

    protected ParamsSys getQueryConditionIn(HttpServletRequest request) {
        ParamsSys qCon = new ParamsSys();
        qCon.setTypeCode(FormUtil.getParameter(request, "q_typeCode"));
        qCon.setCode(FormUtil.getParameter(request, "q_code"));
        qCon.setRecordFlag(FormUtil.getParameter(request, "q_recorqFlag"));
        String beginTime = FormUtil.getParameter(request, "q_beginTime");
        String endTime = FormUtil.getParameter(request, "q_endTime");
        if (beginTime != null && !beginTime.trim().equals("")) {
            qCon.setBeginTime(beginTime + " 00:00:00");
        }
        if (endTime != null && !endTime.trim().equals("")) {
            qCon.setEndTime(endTime + " 23:59:59");
        }
        return qCon;
    }

    protected Vector<ParamsSys> getReqAttributeForPlanAudit(HttpServletRequest request) {
        ParamsThres po = new ParamsThres();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<ParamsSys> selectedItems = this.getPlanSelectIDs(selectIds, request);
        return selectedItems;
    }

    protected Vector<ParamsSys> getReqAttributeForPlanDelete(HttpServletRequest request) {
        ParamsSys po = new ParamsSys();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<ParamsSys> selectedItems = this.getPlanSelectIDs(selectIds, request);
        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    public ParamsSys getReqAttributePlan(HttpServletRequest request) {
        ParamsSys vo = new ParamsSys();
        vo.setTypeCode(FormUtil.getParameter(request, "d_typeCode"));
        vo.setTypeDescription(FormUtil.getParameter(request, "d_typeDescription"));
        vo.setCode(FormUtil.getParameter(request, "d_code"));
        vo.setValue(FormUtil.getParameter(request, "d_value"));
        vo.setDescription(FormUtil.getParameter(request, "d_description"));
        vo.setRecordFlag(RuleConstant.RECORD_FLAG_DRAFT);
        vo.setCreateOperator(PageControlUtil.getOperatorFromSession(request));
        return vo;
    }

    protected Vector<ParamsSys> getPlanSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<ParamsSys> sds = new Vector();
        ParamsSys sd;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getPlan(strIds, "#");
//            sd.setCreateOperator(operatorId);
            sds.add(sd);
        }
        return sds;
    }

    protected ParamsSys getPlan(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        ParamsSys sd = new ParamsSys();
        Vector<ParamsSys> sds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setTypeCode(tmp);
                continue;
            }
            if (i == 2) {
                sd.setCode(tmp);
                continue;
            }
            if (i == 3) {
                sd.setVersion(tmp);
                continue;
            }
        }
        return sd;
    }

}
