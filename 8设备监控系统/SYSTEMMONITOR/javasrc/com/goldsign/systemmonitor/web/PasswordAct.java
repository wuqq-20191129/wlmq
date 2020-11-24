package com.goldsign.systemmonitor.web;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.struts.BaseAction;
import com.goldsign.frame.util.FrameCharUtil;
import com.goldsign.frame.util.FrameUtil;
import com.goldsign.systemmonitor.dao.PasswordDao;
import com.goldsign.systemmonitor.vo.PasswordVo;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;



public class PasswordAct extends BaseAction {

    public PasswordAct() {
        super();
    }

    public ActionForward processLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PasswordDao mb = new PasswordDao();

        ActionForward af = null;
        ActionMessage am = new ActionMessage("queryMessage", FrameCharUtil.GbkToIso("操作成功"));
        String command = request.getParameter("command");
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals("modify")) {
                    am = this.modifyPassword(request, mb);
                }
            }
        } catch (Exception e) {

            af = mapping.findForward("error");
            String error = e.getMessage();
            request.setAttribute("Error", error);
            return af;
        }
        af = mapping.findForward("success");
        getMessages(request).add("operationMessage", am);

        return af;
    }

    private PasswordVo getAttribute(HttpServletRequest request) {
        PasswordVo vo = new PasswordVo();
        vo.setPassword(request.getParameter("password"));
        vo.setPasswordNew(request.getParameter("passwordNew"));
        vo.setPasswordConfirm(request.getParameter("passwordConfirm"));
        return vo;

    }

    public ActionMessage modifyPassword(HttpServletRequest request, PasswordDao pmb) throws Exception {
        ActionMessage am = null;
        PasswordVo mo = this.getAttribute(request);
        int n = 0;
        FrameUtil util = new FrameUtil();

        try {
            n = pmb.modifyPassword(mo.getPassword(), mo.getPasswordNew());
        } catch (Exception e) {
            return util.operationExceptionHandle(request, FrameDBConstant.COMMAND_MODIFY, e);
        }
        am = new ActionMessage("modifyMessage", FrameCharUtil.GbkToIso("成功修改密码"));
        return am;
    }
}
