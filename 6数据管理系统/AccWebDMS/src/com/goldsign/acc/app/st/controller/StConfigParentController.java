/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.st.controller;

import com.goldsign.acc.app.st.entity.StConfig;
import com.goldsign.acc.app.st.mapper.StConfigMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.DMSBaseController;
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
 * 清算系统 - 配置信息
 *
 * @author luck
 */
public class StConfigParentController extends DMSBaseController {

    protected int modifyPlanByTrans(HttpServletRequest request, StConfigMapper stcMapper, StConfig vo, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            opResult.setAddPrimaryKey(vo.getOriginTableName());
            status = txMgr.getTransaction(this.def);
            n = stcMapper.modifyPlan(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected StConfig getQueryConditionForOpPlan(HttpServletRequest request, OperationResult opResult) {
        StConfig qCon = new StConfig();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {
            //操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;

            }
            qCon.setOriginTableName(opResult.getAddPrimaryKey());

        } else {
            //操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setOriginTableName(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_origin_table_name"));
                qCon.setDbType(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_status"));

            }
        }
        return qCon;
    }

    protected StConfig getQueryConditionIn(HttpServletRequest request) {
        StConfig qCon = new StConfig();
        qCon.setOriginTableName(FormUtil.getParameter(request, "q_origin_table_name"));
        qCon.setDbType(FormUtil.getParameter(request, "q_status"));
        return qCon;
    }

    public StConfig getReqAttributePlan(HttpServletRequest request) {
        StConfig vo = new StConfig();
        vo.setOriginTableName(FormUtil.getParameter(request, "d_origin_table_name"));
        vo.setKeepDays(FormUtil.getParameterIntVal(request, "d_keep_days"));
        vo.setDivideRecdCount(FormUtil.getParameterIntVal(request, "d_divide_recd_count"));
        return vo;
    }

}
