package com.goldsign.systemmonitor.web;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.struts.BaseAction;
import com.goldsign.frame.util.FrameCharUtil;
import com.goldsign.frame.util.FrameDBUtil;
import com.goldsign.frame.util.FrameUtil;
import com.goldsign.systemmonitor.dao.CiscoDao;
import com.goldsign.systemmonitor.vo.CiscoVo;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class CiscoAct extends BaseAction {

    public CiscoAct() {
        super();
    }

    public ActionForward processLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        CiscoDao dao = new CiscoDao();
        Vector statues = new Vector();
        ActionForward af = null;
        ActionMessage am = new ActionMessage("queryMessage", FrameCharUtil.GbkToIso("操作成功"));
        String command = request.getParameter("command");
        try {
            this.query(request, dao, statues, command);
        } catch (Exception e) {
            af = mapping.findForward("error");
            String error = e.getMessage();
            request.setAttribute("Error", error);
            return af;
        }
        if (statues != null && !statues.isEmpty()) {
            this.convertText(statues);
            this.saveResult(request, "Statues", statues);
        }
        af = mapping.findForward("success");
        getMessages(request).add("operationMessage", am);
        return af;
    }

    private void convertText(Vector statues) throws Exception {
        FrameDBUtil dbUtil = new FrameDBUtil();
        Vector pubFlags = dbUtil.getPubFlags();
        Vector statuesFlags = dbUtil.getPubFlagsByType(FrameDBConstant.Flag_type_status, pubFlags);
        CiscoVo vo;
        for (int i = 0; i < statues.size(); i++) {
            vo = (CiscoVo) statues.get(i);
            vo.setTemperatureStatusText(dbUtil.getTextByCode(vo.getTemperatureStatus(), statuesFlags));
            vo.setFanStatusText(dbUtil.getTextByCode(vo.getFanStatus(), statuesFlags));
            vo.setPowerStatusText(dbUtil.getTextByCode(vo.getPowerStatus(), statuesFlags));
        }
    }

    public ActionMessage query(HttpServletRequest request, CiscoDao pmb, Vector statues, String command) throws Exception {
        ActionMessage am = null;
        FrameUtil util = new FrameUtil();
        try {
            statues.addAll(pmb.queryStatuses());
        } catch (Exception e) {
            return util.operationExceptionHandle(request, FrameDBConstant.COMMAND_QUERY, e);
        }
        am = new ActionMessage("queryMessage", FrameCharUtil.GbkToIso("成功查询" + statues.size() + "条记录"));
        return am;
    }
}
