/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.st.controller;

import com.goldsign.acc.app.st.entity.StClear;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.DMSBaseController;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 * 清算系统 - 清理日志
 *
 * @author luck
 */
public class StClearParentController extends DMSBaseController {

    protected StClear getQueryConditionForOpPlan(HttpServletRequest request, OperationResult opResult) {
        StClear qCon = new StClear();
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
                qCon.setDestTableName(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_dest_table_name"));
                qCon.setBalanceWaterNo(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_balance_water_no"));
                qCon.setBeginClearDatetime(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_begin_clear_datetime"));
                qCon.setEndClearDatetime(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_end_clear_datetime"));
            }
        }
        return qCon;
    }

    protected StClear getQueryConditionIn(HttpServletRequest request) {
        StClear qCon = new StClear();
        qCon.setOriginTableName(FormUtil.getParameter(request, "q_origin_table_name"));
        qCon.setDestTableName(FormUtil.getParameter(request, "q_dest_table_name"));
        qCon.setBalanceWaterNo(FormUtil.getParameter(request, "q_balance_water_no"));
        qCon.setBeginClearDatetime(FormUtil.getParameter(request, "q_begin_clear_datetime"));
        qCon.setEndClearDatetime(FormUtil.getParameter(request, "q_end_clear_datetime"));

        return qCon;
    }

}
