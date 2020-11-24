/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.commu.controller;

import com.goldsign.acc.app.commu.entity.CmIdxHis;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.DMSBaseController;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

/**
 * 数据交换系统 - 分表记录
 *
 * @author luck
 */
public class CmIdxHisParentController extends DMSBaseController {

    protected CmIdxHis getQueryConditionForOpPlan(HttpServletRequest request, OperationResult opResult) {
        CmIdxHis qCon = new CmIdxHis();
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
                qCon.setHisTable(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_his_table"));          
            }
        }
        return qCon;
    }

    protected CmIdxHis getQueryConditionIn(HttpServletRequest request) {
        CmIdxHis qCon = new CmIdxHis();
        qCon.setOriginTableName(FormUtil.getParameter(request, "q_origin_table_name"));
        qCon.setHisTable(FormUtil.getParameter(request, "q_his_table"));

        return qCon;
    }

}
