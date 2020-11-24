/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.frame.struts;

import com.goldsign.frame.constant.FrameCodeConstant;
import com.goldsign.frame.util.FrameDBUtil;
import com.goldsign.frame.util.FrameUtil;
import com.goldsign.frame.vo.ModuleDistrVo;
import com.goldsign.frame.vo.VersionVo;
import com.goldsign.rule.util.Util;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;


/**
 *
 * @author hejj
 */
public class BaseAction extends Action {

    /**
     * Default "Forward" name for exception handling
     */
    public final static String EXCEPTION_DEFAULT_PAGE = "EXCEPTION_DEFAULT_PAGE";
    /**
     * The attribute key of the request object to save the result object
     */
    public final static String RESULT_KEY = BaseAction.class.getName() + ".RESULT";
    /**
     * The message logger
     */
    private static Logger log = Logger.getLogger(BaseAction.class.getName());
    public static String MODULE_REPORT = "05";
   // public static String MODULE_DECISION = "08";

    /**
     * This member function is inherited from class "Action". It divides a
     * process into three base steps : preprocess(), processLogic() and
     * postProcess()
     *
     * @param mapping - The ActionMapping used to select this instance
     * @param actionForm - The optional ActionForm bean for this request (if
     * any)
     * @param request - The HTTP request we are processing
     * @param response - The HTTP response we are creating
     *
     * @return NULL Do not need to forward to the next handler.
     * @return ActionForward Forward to the next handler.
     *
     */
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {

        ActionForward actionForward = null;
        Vector modulePriviledges = null;
        Vector pubFlags = null;
        Vector typePubFlagsForYear = null;
        Vector typePubFlagsForMonth = null;
        //DBUtil dbUtil = new DBUtil();


        try {
            //  response.setContentType("text/html");
            // Jobs before the process
            actionForward = preprocess(mapping, form, request, response);

            // Main logic of the action process.
            if (actionForward == null) {
                actionForward = processLogic(mapping, form, request, response);
            }
            HttpSession sess = request.getSession();
            
            FrameCodeConstant.OPERATER_ID = Util.getCurrentOperator(request);
            String moduleID = request.getParameter("ModuleID");
            if (moduleID != null) {
                modulePriviledges = (Vector) sess.getAttribute("ModulePrilivedges");
                ModuleDistrVo mv = this.getModulePriviledge(moduleID, modulePriviledges);
                this.saveResult(request, "ModuleID", moduleID);
                this.savePrecommand(request);
                this.saveCurrentVersionInfo(request);
                if (mv != null) {
                    this.saveResult(request, "ModulePrilivedge", mv);
                }

                this.saveQueryCondition(request);
                this.saveUpdatePKValue(request);

            }

            // Jobs after the process
            if (actionForward == null) {
                actionForward = postprocess(mapping, form, request, response);
            }


        } catch (Exception e) {
            // If the exception is not handled by the process method itself, use the default exception handle method.
            actionForward = handleProcessException(mapping, form, request, response, e);
        }// handle exception
        finally {
            try {
                // process on the actionForward before return
                actionForward = finalAdjust(mapping, form, request, response, actionForward);
            } catch (Exception e) {
                // exception handling
                actionForward = handleProcessException(mapping, form, request, response, e);
            }
        }

        return actionForward;

    }// end execute()

    /**
     * This method is used to turn off the cache to protected the data in the
     * response
     */
    protected final void setResponseCacheOff(HttpServletResponse response) {

        response.setHeader("pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");

    }

    /**
     * This member function is used to save an additional result object to the
     * request object.
     *
     * @param request - The HTTP request we are processing
     * @param name - The name of the result object
     * @param result - The result object
     *
     */
    protected final void saveResult(HttpServletRequest request, String name, Object result) {

        Hashtable resultSet = (Hashtable) request.getAttribute(BaseAction.RESULT_KEY);

        if (request == null) {
            throw new NullPointerException("The HttpServletRequest object can not be null.");
        }

        if (name == null) {
            throw new NullPointerException("Result name can not be null.");
        }

        if (result == null) {
            throw new NullPointerException("Result object can not be null.");
        }

        if (resultSet == null) {
            resultSet = new Hashtable();
            request.setAttribute(BaseAction.RESULT_KEY, resultSet);
        }

        resultSet.put(name, result);

    }

    public void saveActionResult(HttpServletRequest request, String name, Object result) {

        Hashtable resultSet = (Hashtable) request.getAttribute(BaseAction.RESULT_KEY);

        if (request == null) {
            throw new NullPointerException("The HttpServletRequest object can not be null.");
        }

        if (name == null) {
            throw new NullPointerException("Result name can not be null.");
        }

        if (result == null) {
            throw new NullPointerException("Result object can not be null.");
        }

        if (resultSet == null) {
            resultSet = new Hashtable();
            request.setAttribute(BaseAction.RESULT_KEY, resultSet);
        }

        resultSet.put(name, result);

    }

    /**
     * This member function is used to get the ActionMessages object from the
     * request object.
     *
     * @param request - The HTTP request we are processing
     *
     * @reutrn ActionMessages The ActionMessages object in the request object
     *
     */
    public void savePrecommand(HttpServletRequest request) {
        String command = request.getParameter("command");
        String precommand = request.getParameter("precommand");
        if (command != null) {
            this.saveResult(request, "Precommand", command);
        }
        if (precommand != null) {
            this.saveResult(request, "PreTwoCommand", precommand);
        }
    }

    protected final ActionMessages getMessages(HttpServletRequest request) {

        if (request == null) {
            throw new NullPointerException("The HttpServletRequest object can not be null.");
        }

        ActionMessages messages = null;

        // return the messages object which is stored in the request object
        if (request != null) {
            messages = (ActionMessages) request.getAttribute(Globals.MESSAGE_KEY);
            if (messages == null) {
                messages = new ActionMessages();
                request.setAttribute(Globals.MESSAGE_KEY, messages);
            }
        }

        return messages;

    }// end getMessages()

    /**
     * This member function is used to do the jobs before the main process
     * logic.
     *
     * @param mapping - The ActionMapping used to select this instance
     * @param actionForm - The optional ActionForm bean for this request (if
     * any)
     * @param request - The HTTP request we are processing
     * @param response - The HTTP response we are creating
     *
     * @return NULL Continue to perform the main process logic.
     * @return ActionForward Forward to the next handler.
     *
     * @exception Exception - if an exception occurs that interrupts the jobs'
     * normal operation
     *
     */
    protected ActionForward preprocess(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        // override to provide functionality
        return null;

    }// end preprocess()

    /**
     * This member function is used to execute the main logic of the process.
     *
     * @param mapping - The ActionMapping used to select this instance
     * @param actionForm - The optional ActionForm bean for this request (if
     * any)
     * @param request - The HTTP request we are processing
     * @param response - The HTTP response we are creating
     *
     * @return NULL Do not need to forward to another handler.
     * @return ActionForward Next process handler.
     *
     * @exception Exception - if an exception occurs that interrupts the
     * process's normal operation
     *
     */
    protected ActionForward processLogic(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        // override to provide functionality
        return null;

    }// end processLogic()

    /**
     * This member function is used to do the jobs after the main process logic.
     *
     * @param mapping - The ActionMapping used to select this instance
     * @param actionForm - The optional ActionForm bean for this request (if
     * any)
     * @param request - The HTTP request we are processing
     * @param response - The HTTP response we are creating
     *
     * @return NULL Do not need to forward the handle to others.
     * @return ActionForward Forward to the next handler.
     *
     * @exception Exception - if an exception occurs that interrupts the jobs'
     * normal operation
     *
     */
    protected ActionForward postprocess(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        // override to provide functionality
        return null;

    }// end postprocess()

    /**
     * This member function is used to handle the exceptions not catched by the
     * process methods.
     *
     * @param mapping - The ActionMapping used to select this instance
     * @param actionForm - The optional ActionForm bean for this request (if
     * any)
     * @param request - The HTTP request we are processing
     * @param response - The HTTP response we are creating
     * @param e - Exception need to handle
     *
     * @return NULL Do not need to forward to the next handler.
     * @return ActionForward Forward to the next handler.
     *
     */
    protected ActionForward handleProcessException(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response,
            Exception e) {

        // override to provide functionality
        log.error("Exception catched by BaseAction", e);
        return mapping.findForward(BaseAction.EXCEPTION_DEFAULT_PAGE);

    }// end handleProcessException()

    /**
     * This member function is used to do the final adjustment jobs before
     * returning the ActonForward object.
     *
     * @param mapping - The ActionMapping used to select this instance
     * @param actionForm - The optional ActionForm bean for this request (if
     * any)
     * @param request - The HTTP request we are processing
     * @param response - The HTTP response we are creating
     * @param actionForward - The ActionForward object
     *
     * @return NULL Do not need to forward to the next handler.
     * @return ActionForward Forward to the next handler.
     *
     */
    private ActionForward finalAdjust(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response,
            ActionForward actionForward) {

        if (actionForward != null && actionForward instanceof Action2XMLForward) {
            Action2XMLForward forward = (Action2XMLForward) actionForward;

            // if a XSL file is given in the struts-config.xml, save it to the request object
            String xslFile = forward.getXSLFile();
            if (xslFile != null && !xslFile.equals("")) {
                request.setAttribute(Action2XML.XSL_FILE_KEY, xslFile);
            }

            // if the "include parameters" flag is given in struts-config.xml, save it to the request object
            if (log.isDebugEnabled()) {
                log.debug("forward.getIncludeParm() : " + forward.getIncludeParm());
            }
            request.setAttribute(Action2XML.INCLUDE_PARM_KEY, forward.getIncludeParm());
        }

        return actionForward;

    }// end finalAdjust()

    private ModuleDistrVo getModulePriviledge(String moduleID, Vector modulePriviledges) {
        ModuleDistrVo mv = new ModuleDistrVo();
        for (int i = 0; i < modulePriviledges.size(); i++) {
            mv = (ModuleDistrVo) modulePriviledges.get(i);
            //    System.out.println("moduleID="+mv.getModuleID());
            if (mv.getModuleID().trim().equals(moduleID)) {
                return mv;
            }
        }
        return mv;
    }
    
    public void saveQueryControlDefaultValues(HttpServletRequest request) {
        String queryControlDefaultValues = "";
        if (FrameUtil.isMimiType(request)) {
            queryControlDefaultValues = FrameUtil.ChineseToIsoForMimi(request.getParameter("queryControlDefaultValues"));
        } else {
            queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        }

        if (queryControlDefaultValues != null) {
            this.saveResult(request, "ControlDefaultValues", queryControlDefaultValues);
        }


    }
    

    public void saveQueryCondition(HttpServletRequest request) {
        String ControlDefaultValues = null;
        String command = request.getParameter("command");
        if (command == null) {
            return;
        }

        if (!command.equals("query") && !command.startsWith("query")
                && !command.equals("back")
                && !command.equals("backEnd")
                && !command.equals("next")
                && !command.equals("nextEnd")) {
            return;
        }
        FrameUtil util = new FrameUtil();

        ControlDefaultValues = util.getControlDefaultValues(request);
        if (ControlDefaultValues != null) {
            this.saveResult(request, "ControlDefaultValues", ControlDefaultValues);
        }
    }

    public void saveCurrentVersionInfo(HttpServletRequest request) throws Exception {
        String paramTypeID = null;
        String[] paramTypeNames = {"Type", "paramTypeID"};
        String paramTypeName = null;
        for (int i = 0; i < paramTypeNames.length; i++) {
            paramTypeName = paramTypeNames[i];
            paramTypeID = request.getParameter(paramTypeName);
            if (paramTypeID != null) {
                break;
            }
        }
        if (paramTypeID == null) {
            return;
        }
        FrameDBUtil dbUtil = new FrameDBUtil();
        VersionVo vo = dbUtil.getCurrentVersionInfo(paramTypeID);
        if (vo != null) {
            this.saveResult(request, "CurrentVersionInformation", vo);
        }
    }

    public void saveUpdatePKValue(HttpServletRequest request) {
        String ControlDefaultValues = null;
        String command = request.getParameter("command");
        if (command == null) {
            return;
        }
        if (!command.equals("add") && !command.equals("modify") && !command.equals("audit")) {
            return;
        }
        FrameUtil util = new FrameUtil();
        ControlDefaultValues = util.getControlDefaultValues(request, "_updatePKControlNames");

        if (ControlDefaultValues != null) {
            if (request.getAttribute("_updatePKControlNames") != null) {
                ControlDefaultValues = (String) request.getAttribute("_updatePKControlNames");
                request.removeAttribute("_updatePKControlNames");
            }

            this.saveResult(request, "UpdatePKControlNames", ControlDefaultValues);
        }

    }

}
