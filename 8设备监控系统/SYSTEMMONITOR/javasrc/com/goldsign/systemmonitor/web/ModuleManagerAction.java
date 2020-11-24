package com.goldsign.systemmonitor.web;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.struts.BaseAction;
import com.goldsign.frame.util.FrameCharUtil;
import com.goldsign.frame.util.FrameDBUtil;
import com.goldsign.frame.vo.Menu;
import com.goldsign.systemmonitor.dao.MenuDao;
import com.goldsign.systemmonitor.util.Encryption;
import com.goldsign.systemmonitor.util.Util;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Vector;
import org.apache.struts.action.ActionMessage;
import java.util.Enumeration;
import java.util.HashMap;

public class ModuleManagerAction extends BaseAction {

    public ModuleManagerAction() {
    }

    public ActionForward processLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        MenuDao mb = new MenuDao();
        Vector modules = new Vector();
        ActionForward af = null;
        ActionMessage am = new ActionMessage("queryMessage", FrameCharUtil.GbkToIso("操作成功"));
        String command = request.getParameter("command");
        FrameDBUtil dbUtil = new FrameDBUtil();
        Vector pubFlags = dbUtil.getPubFlags();
        Vector locks = dbUtil.getPubFlagsByType(FrameDBConstant.Flag_type_lock, pubFlags);
        Vector newWindowFlags = dbUtil.getPubFlagsByType(FrameDBConstant.Flag_type_new_window_flag, pubFlags);
        this.showAllParameters(request);

        try {
            if (command != null) {
                command = command.trim();
                if (command.equals("modify")) {
                    am = this.modifyModule(request, mb);
                }
                if (command.equals("delete")) {
                    am = this.deleteModules(request, mb);
                }
                if (command.equals("add")) {
                    am = this.addModule(request, mb);
                }
                if (command.equals("query")) {
                    am = this.queryModuleByUpdate(request, mb, modules, command);
                }

            }
            if (command != null) {
                if (command.equals("add") || command.equals("delete") || command.equals("modify") || command.equals("clone") || command.equals("submit")) {
                    if (!command.equals("add")) {
                        this.saveQueryControlDefaultValues(request);
                    }
                    this.queryModuleByUpdate(request, mb, modules, command);
                }
            }

        } catch (Exception e) {

            af = mapping.findForward("error");
            String error = e.getMessage();
            request.setAttribute("Error", error);
            return af;
        }
        if (modules != null && !modules.isEmpty()) {
            this.saveResult(request, "Modules", modules);
        }
        if (locks != null && !locks.isEmpty()) {
            this.saveResult(request, "Locks", locks);
        }
        if (newWindowFlags != null && !newWindowFlags.isEmpty()) {
            this.saveResult(request, "NewWindowFlags", newWindowFlags);
        }

        af = mapping.findForward("success");
        getMessages(request).add("operationMessage", am);

        return af;

    }

    public ActionMessage queryModuleByUpdate(HttpServletRequest request, MenuDao pmb, Vector modules, String command) throws Exception {
        ActionMessage am = null;
        Menu vo = new Menu();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");

        Util util = new Util();




        if (util.isAddMode(request)) {
            if (command.equals("delete")) {
                return null;
            }
            vo.setMenuId(request.getParameter("moduleID"));
        } else {
            if (command.equals("query")) {
                vo.setMenuId(request.getParameter("q_moduleID"));
                vo.setMenuName(FrameCharUtil.ChineseToIso(request.getParameter("q_name")));
                vo.setTopMenuId(request.getParameter("q_topID"));
                vo.setParentId(request.getParameter("q_parentID"));
            } else {
                vQueryControlDefaultValues = util.getQueryControlDefaultValues(queryControlDefaultValues);
                if (!vQueryControlDefaultValues.isEmpty()) {
                    vo.setMenuId(util.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_moduleID"));
                    vo.setMenuName(util.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_name"));
                    vo.setTopMenuId(util.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_topID"));
                    vo.setParentId(util.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_parentID"));
                }
            }
        }



        try {
            modules.addAll(pmb.getModulesByCondition(vo));
        } catch (Exception e) {
            return util.operationExceptionHandle(request, FrameDBConstant.COMMAND_QUERY, e);
        }


        am = new ActionMessage("queryMessage", FrameCharUtil.GbkToIso("成功查询" + modules.size() + "条记录"));
        return am;
    }

    public ActionMessage addModule(HttpServletRequest request, MenuDao pmb) throws Exception {
        ActionMessage am = null;
        Menu mo = this.getModuleAttribute(request);
        int n = 0;
        Util util = new Util();


        try {
            n = pmb.addMoule(mo);
        } catch (Exception e) {
            return util.operationExceptionHandle(request, FrameDBConstant.COMMAND_ADD, e);
        }

        if (n == 0) {
            am = new ActionMessage("addMessage", FrameCharUtil.GbkToIso("模块代码重复"));
        } else {

            am = new ActionMessage("addMessage", FrameCharUtil.GbkToIso("成功添加" + n + "记录"));
        }

        return am;
    }

    public ActionMessage modifyModule(HttpServletRequest request, MenuDao pmb) throws Exception {
        ActionMessage am = null;
        Menu mo = this.getModuleAttribute(request);
        int n = 0;
        Util util = new Util();

        try {
            n = pmb.modifyMoule(mo);
        } catch (Exception e) {
            return util.operationExceptionHandle(request, FrameDBConstant.COMMAND_MODIFY, e);
        }

        am = new ActionMessage("modifyMessage", FrameCharUtil.GbkToIso("成功修改" + n + "记录"));

        return am;
    }

    public ActionMessage deleteModules(HttpServletRequest request, MenuDao pmb) throws Exception {
        ActionMessage am = null;
        String strModuleIDs = request.getParameter("allSelectedIDs");//allSelectedGroupIDs
        Vector moduleIDs = new Vector();
        Util util = new Util();
        int n = 0;
        util.getIDs(strModuleIDs, moduleIDs);


        try {
            n = pmb.deleteModules(moduleIDs);
        } catch (Exception e) {
            return util.operationExceptionHandle(request, FrameDBConstant.COMMAND_DELETE, e);
        }

        am = new ActionMessage("deleteMessage", FrameCharUtil.GbkToIso("成功删除" + n + "记录"));
        return am;
    }

    public Menu getModuleAttribute(HttpServletRequest request) {
        Menu mo = new Menu();
        String url = "";
        String postParameter = "";
        mo.setMenuId(request.getParameter("moduleID"));
        mo.setMenuName(FrameCharUtil.ChineseToIso(request.getParameter("name")));
        mo.setTopMenuId(request.getParameter("topID"));
        mo.setParentId(request.getParameter("parentID"));
        url = request.getParameter("url");
        //   System.out.println("url="+url);
        url = url.replaceAll("amp;", "");
        mo.setUrl(url);
        mo.setNewWindowFlag(request.getParameter("newWindowFlag"));

        postParameter = request.getParameter("d_postParameter");
        if (postParameter != null && postParameter.length() != 1) {
            postParameter = postParameter.replaceAll("amp;", "");
            postParameter = new Encryption().biEncrypt(Encryption.ENC_KEY, postParameter);
        }
        mo.setPostParameter(postParameter);

        mo.setIcon(request.getParameter("icon"));
        mo.setLocked(request.getParameter("locked"));
        return mo;

    }

    public void showAllParameters(HttpServletRequest req) {
        String encoding = req.getCharacterEncoding();
        System.out.println("ecoding=" + encoding);
        Enumeration e = req.getParameterNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            String value = (String) req.getParameter(key);
            System.out.println(key + "=" + value);
        }

    }
}
