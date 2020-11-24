/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.frame.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class MenuManager extends Action {

    private static Logger logger = Logger.getLogger(MenuManager.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm baseForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        DynaActionForm form = (DynaActionForm) baseForm;

        String menuId = (String) form.get("MenuId");

        return (mapping.findForward("success"));
    }
}
