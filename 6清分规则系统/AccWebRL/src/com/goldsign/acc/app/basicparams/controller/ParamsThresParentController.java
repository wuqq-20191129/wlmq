/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicparams.controller;

import com.goldsign.acc.app.basicparams.entity.ParamsThres;
import com.goldsign.acc.app.basicparams.mapper.ParamsThresMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.RuleConstant;
import com.goldsign.acc.frame.controller.RLBaseController;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.util.PubUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.TransactionStatus;

/**
 * 阀值参数设置
 *
 * @author luck
 */
public class ParamsThresParentController extends RLBaseController {

    protected int addPlanByTrans(HttpServletRequest request, ParamsThresMapper ptMapper, ParamsThres vo, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = ptMapper.addPlan(vo);
            txMgr.commit(status);
            if (n > 0) {
                String id = ptMapper.getMaxId();
                opResult.setAddPrimaryKey(id);
            }
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected int auditPlanByTrans(HttpServletRequest request, ParamsThresMapper ptMapper, Vector<ParamsThres> pos, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (ParamsThres po : pos) {
                opResult.setAddPrimaryKey(String.valueOf(po.getId()));
                //更新之前的数据为历史版本
                int updateCurrentToHistory = ptMapper.updateCurrentToHistory(po);
                //审核数据为当前版本
                int auditUpdate = ptMapper.auditUpdate(po);
                n += auditUpdate;
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        } finally {
            return n;
        }
    }

    protected int deletePlanByTrans(HttpServletRequest request, ParamsThresMapper ptMapper, Vector<ParamsThres> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (ParamsThres po : pos) {
                po.setRecordFlag(RuleConstant.RECORD_FLAG_DRAFT);
                n += ptMapper.deletePlan(po);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected int modifyPlanByTrans(HttpServletRequest request, ParamsThresMapper ptMapper, ParamsThres vo, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = ptMapper.modifyPlan(vo);
            opResult.setAddPrimaryKey(String.valueOf(vo.getId())); //增加操作记录的主健值
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected ParamsThres getQueryConditionForOpPlan(HttpServletRequest request, OperationResult opResult) {
        ParamsThres qCon = new ParamsThres();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request) || command.equals(CommandConstant.COMMAND_AUDIT)) {
            //操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;

            }
            qCon.setId(Integer.valueOf(opResult.getAddPrimaryKey()));
        } else {
            //操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setRecordFlag(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_recorqFlag"));
            }
        }
        return qCon;
    }

    protected ParamsThres getQueryConditionIn(HttpServletRequest request) {
        ParamsThres qCon = new ParamsThres();
        qCon.setRecordFlag(FormUtil.getParameter(request, "q_recorqFlag"));
        return qCon;
    }

    protected Vector<ParamsThres> getReqAttributeForPlanAudit(HttpServletRequest request) {
        ParamsThres po = new ParamsThres();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<ParamsThres> selectedItems = this.getPlanSelectIDs(selectIds, request);
        return selectedItems;
    }

    protected Vector<ParamsThres> getReqAttributeForPlanDelete(HttpServletRequest request) {
        ParamsThres po = new ParamsThres();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<ParamsThres> selectedItems = this.getPlanSelectIDs(selectIds, request);
        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    public ParamsThres getReqAttributePlan(HttpServletRequest request) {
        ParamsThres vo = new ParamsThres();
        vo.setDistanceThres(new BigDecimal(FormUtil.getParameter(request, "d_distanceThres")));
        vo.setStationThres(FormUtil.getParameterIntVal(request, "d_stationThres"));
        vo.setChangeThres(FormUtil.getParameterIntVal(request, "d_changeThres"));
        vo.setTimeThres(FormUtil.getParameterIntVal(request, "d_timeThres"));
        vo.setDescription(FormUtil.getParameter(request, "d_description"));
        vo.setRecordFlag(RuleConstant.RECORD_FLAG_DRAFT);
        vo.setId(FormUtil.getParameterIntVal(request, "d_id"));
        vo.setUpdateOperator(PageControlUtil.getOperatorFromSession(request));
        return vo;
    }

    protected Vector<ParamsThres> getPlanSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<ParamsThres> sds = new Vector();
        ParamsThres sd;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getPlan(strIds, "#");
            sd.setUpdateOperator(operatorId);
            sds.add(sd);
        }
        return sds;
    }

    protected ParamsThres getPlan(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        ParamsThres sd = new ParamsThres();
        ;
        Vector<ParamsThres> sds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setId(Integer.valueOf(tmp));
                continue;
            }
        }
        return sd;
    }

}
