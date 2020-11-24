package com.goldsign.acc.app.st.controller;

import com.goldsign.acc.app.st.entity.TkIndex;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.DMSBaseController;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

/**
 * 票务管理系统 - 分表记录
 *
 * @author xiaowu
 */
public class TkIndexParentController extends DMSBaseController {

    protected TkIndex getQueryConditionForOpPlan(HttpServletRequest request, OperationResult opResult) {
        TkIndex qCon = new TkIndex();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {
            //操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;

            }
            qCon.setOrigin_table_name(opResult.getAddPrimaryKey());

        } else {
            //操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setOrigin_table_name(FormUtil.getParameter(request, "q_origin_table_name"));
                qCon.setHis_table(FormUtil.getParameter(request, "q_his_table"));
            }
        }
        return qCon;
    }

    protected TkIndex getQueryConditionIn(HttpServletRequest request) {
        TkIndex qCon = new TkIndex();
        qCon.setOrigin_table_name(FormUtil.getParameter(request, "q_origin_table_name"));
        qCon.setHis_table(FormUtil.getParameter(request, "q_his_table"));
        return qCon;
    }

}
