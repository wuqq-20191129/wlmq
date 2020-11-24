/*
 * Amendment History:
 * 
 * Date          By             Description
 * ----------    -----------    -------------------------------------------
 * 2004-06-01    Rong Weitao    Create the class
*/

package com.goldsign.frame.struts;

import org.apache.struts.actions.ForwardAction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 * Base forward action to forward the handle to any JSP pages or Servlets
 *
 * To ensure that the user is authenticated and have the right to access the function,
 * each JSP and Servlet besides Struts should be called through this BaseForwardAction.
 * 
 * To configure the use of this action in your struts-config.xml file, create an entry like this:
 * <action path="/testJSP" type="com.hsbcrepublic.efos.core.struts.action.BaseForwardAction" name="testForm" scope="request" parameter="/testJSP.jsp"/>
 *
 * You must check if the JSP or the servelet is called through a BaseForwardAction to ensure security.
 * Add the below statement to your JSP or servelet :
 *
 * Import com.hsbcrepublic.efos.core.struts.action.BaseForwardAction;
 *
 * String forwardedByAction = (String) request.getAttribute(BaseForwardAction.FORWARDED_BY_ACTION_KEY);
 * if (forwardedByAction == null) {
 *     response.sendError(HttpServletResponse.SC_NOT_FOUND);
 *     return;
 * }
 * 
 */
public class BaseForwardAction extends ForwardAction{

    /**
     *  The attribute key of the request object to store flag indicating that the handle is forwarded by this action
     */
   public static final String FORWARDED_BY_ACTION_KEY = BaseForwardAction.class.getName() + ".THROUGHFORWARD";


    /**
     * Forward the handle to an other JSP or servlet
     * 
     * @param   mapping     - The ActionMapping used to select this instance
     * @param   actionForm  - The optional ActionForm bean for this request (if any)
     * @param   request     - The HTTP request we are processing
     * @param   response    - The HTTP response we are creating
     * 
     * @return  ActionForward   Forward to the next handler.
     * 
     * @exception Exception  - if an exception occurs that interrupts the action's normal operation
     * 
     */
    public ActionForward execute (
                ActionMapping mapping,
                ActionForm form,
                HttpServletRequest request,
                HttpServletResponse response)
            throws Exception {
        
        request.setAttribute(BaseForwardAction.FORWARDED_BY_ACTION_KEY, "YES");
        return super.execute(mapping, form, request, response);

    }
   

}