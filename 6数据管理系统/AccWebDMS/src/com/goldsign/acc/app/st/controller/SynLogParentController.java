package com.goldsign.acc.app.st.controller;

import com.goldsign.acc.app.st.entity.SynLog;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.DMSBaseController;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

/**
 * 票务管理系统 - 同步日志
 *
 * @author xiaowu
 */
public class SynLogParentController extends DMSBaseController {

    protected SynLog getQueryConditionForOpPlan(HttpServletRequest request, OperationResult opResult) {
        SynLog qCon = new SynLog();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {
            //操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;

            }
            qCon.setSrc_table(opResult.getAddPrimaryKey());

        } else {
            //操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setSrc_table(FormUtil.getParameter(request, "q_src_table"));
                qCon.setDest_table(FormUtil.getParameter(request, "q_dest_table"));
                qCon.setStart_syn_time(FormUtil.getParameter(request, "q_start_syn_time"));
                qCon.setEnd_syn_time(FormUtil.getParameter(request, "q_end_syn_time"));
            }
        }
        return qCon;
    }

    protected SynLog getQueryConditionIn(HttpServletRequest request) {
        SynLog qCon = new SynLog();
        qCon.setSrc_table(FormUtil.getParameter(request, "q_src_table"));
        qCon.setDest_table(FormUtil.getParameter(request, "q_dest_table"));
        qCon.setStart_syn_time(FormUtil.getParameter(request, "q_start_syn_time"));
        qCon.setEnd_syn_time(FormUtil.getParameter(request, "q_end_syn_time"));
        return qCon;
    }

}
