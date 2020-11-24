package com.goldsign.systemmonitor.web;

import com.goldsign.systemmonitor.util.Encryption;
import com.goldsign.systemmonitor.vo.AuditResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;



public class LoginAct extends Action {

    private static Logger logger = Logger.getLogger(LoginAct.class.getName());

    public LoginAct() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ActionForward execute(ActionMapping mapping, ActionForm baseForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String password = (String) request.getParameter("Password");
        String dbPassword = "";
        ActionForward af = null;
        AuditResult auditResult = this.AuditPass(password, dbPassword);
        if (auditResult.getResult()) {
            af = mapping.findForward("success");
        } else {
            String strMsg = auditResult.getMsg();
            request.getSession().setAttribute("LoginMsg", strMsg);
            af = mapping.findForward("failure");
        }

        return af;
    }

    private AuditResult AuditPass(String password, String dbPassword) throws Exception {
        AuditResult auditResult = new AuditResult();
        Encryption en = new Encryption();




        if (password == null || password.length() == 0) {
            auditResult.setResult(false);
            auditResult.setMsg("密码不能为空");
        }


        if (!password.equals(en.biDecrypt(Encryption.ENC_KEY, dbPassword))) {
            auditResult.setResult(false);
            auditResult.setMsg("密码错误");


        }
        return auditResult;
    }
}
