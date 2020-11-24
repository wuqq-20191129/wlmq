package com.goldsign.systemmonitor.web;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.struts.BaseAction;
import com.goldsign.frame.util.FrameCharUtil;
import com.goldsign.frame.util.FrameDBUtil;
import com.goldsign.frame.util.FrameUtil;
import com.goldsign.systemmonitor.dao.HardwareDao;
import com.goldsign.systemmonitor.vo.HardwareVo;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;


/**
 * 继承于BaseAction父类与定义的一个HardwareAct(硬盘状态)
 *
 * @author ABC
 *
 */
public class HardwareAct extends BaseAction {

    public HardwareAct() {
        super();
    }
    /**
     * 逻辑进程方法
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward processLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HardwareDao dao = new HardwareDao();
        Vector statues = new Vector();
        ActionForward af = null;
        //构造一个与转制来iso8859-1的queryMessage信息
        ActionMessage am = new ActionMessage("queryMessage", FrameCharUtil.GbkToIso("查询成功"));
        //从请求中。。通过request取得参数command
        String command = request.getParameter("command");
        try {
            //直接调用本类中的查询方法query
            this.query(request, dao, statues, command);
        } catch (Exception e) {

            //如果出现异常。通过mapping.findForward("error")转发到/jsp/showErrorMessage.jsp
            af = mapping.findForward("error");
            //封装错误信息到request中
            String error = e.getMessage();
            request.setAttribute("Error", error);
            return af;
        }
        //查询到statues。如果有。直接调用converTest..然后再调用继承父类中的saveResult()
        if (statues != null && !statues.isEmpty()) {
            this.convertText(statues);
            this.saveResult(request, "Statues", statues);
        }

        //成功转发到"success"--><forward name="success" path="/Action2XML.do" className="com.goldsign.javacore.struts.Action2XMLForward">
        //并通过了Action2XM这个类，把xml通过xsl的样式展示出来
        af = mapping.findForward("success");
        getMessages(request).add("operationMessage", am);
        return af;
    }

    /**
     * 通过这个方法可以转换对象到TEXT中 以便于显示
     */
    private void convertText(Vector statues) throws Exception {
        FrameDBUtil dbUtil = new FrameDBUtil();
        Vector pubFlags = dbUtil.getPubFlags();
        Vector statuesFlags = dbUtil.getPubFlagsByType(FrameDBConstant.Flag_type_status, pubFlags);
        HardwareVo vo;

        //循环封装
        for (int i = 0; i < statues.size(); i++) {
            vo = (HardwareVo) statues.get(i);
            vo.setStatusText(dbUtil.getTextByCode(vo.getStatus(), statuesFlags));


        }
    }

    public ActionMessage query(HttpServletRequest request, HardwareDao pmb, Vector statues, String command) throws Exception {
        ActionMessage am = null;
        FrameUtil util = new FrameUtil();
        try {
            //在其DAO类中的queryStatuses()方法可以封装数据到statues中
            statues.addAll(pmb.queryStatuses());
        } catch (Exception e) {
            return util.operationExceptionHandle(request, FrameDBConstant.COMMAND_QUERY, e);
        }

        //查询成功并把信息封装到ActionMessage中
        am = new ActionMessage("queryMessage", FrameCharUtil.GbkToIso("成功查询" + statues.size() + "条记录！"));
        return am;
    }
}
