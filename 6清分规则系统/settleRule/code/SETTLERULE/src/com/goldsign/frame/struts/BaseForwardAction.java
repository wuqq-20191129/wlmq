/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.frame.struts;
import org.apache.struts.actions.ForwardAction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hejj
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
