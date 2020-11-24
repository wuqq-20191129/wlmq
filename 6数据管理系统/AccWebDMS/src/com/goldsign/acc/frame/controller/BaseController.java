/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.controller;

import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.WebConstant;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.mapper.PubFlagMapper;
import com.goldsign.acc.frame.util.CharUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import static com.goldsign.acc.frame.util.PageControlUtil.NAME_BUFFER;
import static com.goldsign.acc.frame.util.PageControlUtil.NAME_RESULT;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.vo.Menu;
import com.goldsign.login.vo.ModuleDistrVo;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author hejj
 */
@Controller
public class BaseController {

    @Autowired
    protected PubFlagMapper pubFlagMapper;

    @Autowired
    protected OperationLogMapper operationLogMapper;

    @Autowired
    protected DataSourceTransactionManager txMgr;
    @Autowired
    protected DefaultTransactionDefinition def;

    private static Logger log = Logger.getLogger(BaseController.class.getName());

    public void SaveOperationResult(
            ModelAndView modelView, OperationResult opResult) {
        if (opResult == null) {
            return;
        }
        String msg = opResult.getMessage();
        List resultSet = opResult.getReturnResultSet();
        if (msg != null && msg.length() != 0) {
            modelView.addObject(WebConstant.ATT_MESSAGE, msg);
        }
        if (resultSet != null && !resultSet.isEmpty()) {
            modelView.addObject(WebConstant.ATT_ResultSet, resultSet);
        }

    }

    public void divideResultSet(HttpServletRequest request, ModelAndView mv, OperationResult opResult) {
        PageControlUtil pUtil = new PageControlUtil();
        if (pUtil.isNeedDivide(request)) {
            pUtil.seperateResults(request, mv, opResult);
            if (pUtil.isPageControl(request)) {
                pUtil.getMessage(request, opResult);
            }
        }
    }

    public void baseHandler(
            HttpServletRequest request,
            HttpServletResponse response, ModelAndView modelView) {

        Vector modulePriviledges = null;
        ModuleDistrVo mv = null;

        try {

            HttpSession sess = request.getSession();

            String moduleID = request.getParameter(WebConstant.PARM_MODULE_ID);
            if (moduleID != null) {
                modulePriviledges = (Vector) sess.getAttribute(WebConstant.PARM_MODULE_PRIVILEDGES);
                if (modulePriviledges != null) {
                    mv = this.getModulePriviledge(moduleID, modulePriviledges);
                }
                modelView.addObject(WebConstant.PARM_MODULE_ID, moduleID);
                this.savePrecommand(request, modelView);
                if (mv != null) {
//                    把mv通过btn转换为

                    List<PubFlag> btnTypes = this.pubFlagMapper.getButtons();
                    getBtnTypesText(mv, btnTypes);

                    modelView.addObject(WebConstant.PARM_MODULE_PRIVILEDGE, mv);

                }

                this.saveQueryCondition(request, modelView);
                this.saveUpdatePKValue(request, modelView);

            }

        } catch (Exception e) {
            e.printStackTrace();
            // If the exception is not handled by the process method itself, use the default exception handle method.

        }// handle exception
        finally {

        }

    }// 

    private void getBtnTypesText(ModuleDistrVo mv, List<PubFlag> btnTypes) {
        String btnTypesText = "";

        if (mv.getBtnModules() != null && btnTypes != null) {
            for (PubFlag pubFlagVo : btnTypes) {
                if (isHasModule(pubFlagVo, mv.getBtnModules())) {
                    btnTypesText += "1#";
                } else {
                    btnTypesText += "0#";
                }
            }
            btnTypesText = btnTypesText.substring(0, btnTypesText.length() - 1);
        }
        mv.setBtnModulesText(btnTypesText);
    }

    public boolean isHasModule(PubFlag pubFlagVo, List<Menu> btnModules) {
        boolean flag = false;
        for (Menu menu : btnModules) {
            if (menu.getBtnId().equals(pubFlagVo.getCode())) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public void saveUpdatePKValue(HttpServletRequest request, ModelAndView modelView) {
        String ControlDefaultValues = null;
        String command = request.getParameter("command");
        if (command == null) {
            return;
        }
        if (!command.equals("add") && !command.equals("modify") && !command.equals("audit")) {
            return;
        }
        //    CharUtil util = new CharUtil();
        ControlDefaultValues = this.getControlDefaultValues(request, "updatePKControlNames");

        if (ControlDefaultValues != null) {
            if (request.getAttribute("_updatePKControlNames") != null) {
                ControlDefaultValues = (String) request.getAttribute("_updatePKControlNames");
                request.removeAttribute("_updatePKControlNames");
            }

            // this.saveResult(request, "UpdatePKControlNames", ControlDefaultValues);
            modelView.addObject("UpdatePKControlNames", ControlDefaultValues);
        }

    }

    public String getControlDefaultValues(HttpServletRequest request, String controlNamesParm) {
        String result = "";
        String controlNames = request.getParameter(controlNamesParm);
        if (controlNames == null || controlNames.length() == 0) {
            return "";
        }
        StringTokenizer st = new StringTokenizer(controlNames, "#");
        String name = "";
        String value = "";
        while (st.hasMoreTokens()) {
            name = st.nextToken();
            value = request.getParameter(name);
            result += name + "#" + value + ";";
        }
        if (result.length() != 0) {
            result = result.substring(0, result.length());
        }
        return result;
    }

    private boolean isNeedSaveQueryCondition(HttpServletRequest request) {
        String command = request.getParameter("command");
        if (command == null) {
            return false;
        }
        if (command.equals(CommandConstant.COMMAND_DELETE)) {
            return true;
        }

        if (!command.equals(CommandConstant.COMMAND_QUERY) && !command.startsWith(CommandConstant.COMMAND_QUERY) 
                //                && !command.equals("back")
                //                && !command.equals("backEnd")
                //                && !command.equals("next")
                //                && !command.equals("nextEnd")    // modify by luck 20170915 翻页保存查询条件
                ) {
            return false;
        }
        return true;
    }

    public void saveQueryCondition(HttpServletRequest request, ModelAndView modelView) {
        String ControlDefaultValues = null;

        if (!this.isNeedSaveQueryCondition(request)) {
            return;
        }

        String lineName = request.getParameter(WebConstant.PARM_REQ_LINE_NAME);
        String stationName = request.getParameter(WebConstant.PARM_REQ_STATION_NAME);
        String mainCardName = request.getParameter(WebConstant.PARM_REQ_CARD_MAIN_NAME);
        String subCardName = request.getParameter(WebConstant.PARM_REQ_CARD_SUB_NAME);
        String stationCommonVariable = request.getParameter(WebConstant.PARM_REQ_VAR_STATION_NAME);
        String subCardCommonVariable = request.getParameter(WebConstant.PARM_REQ_VAR_CARD_SUB_NAME);

        ControlDefaultValues = this.getControlDefaultValues(request);

        if (ControlDefaultValues != null) {
            modelView.addObject(WebConstant.ATT_QUY_CONTROL_DEFAULT_VALUE, ControlDefaultValues);

        }
        if (lineName != null) {
            modelView.addObject(WebConstant.ATT_QUY_LINE_NAME, lineName);

        }
        if (stationName != null) {
            modelView.addObject(WebConstant.ATT_QUY_STATION_NAME, stationName);

        }
        if (mainCardName != null) {
            modelView.addObject(WebConstant.ATT_QUY_CARD_MAIN_NAME, mainCardName);

        }
        if (subCardName != null) {
            modelView.addObject(WebConstant.ATT_QUY_CARD_SUB_NAME, subCardName);

        }
        if (stationCommonVariable != null) {
            modelView.addObject(WebConstant.ATT_QUY_VAR_STATION, stationCommonVariable);

        }
        if (subCardCommonVariable != null) {
            modelView.addObject(WebConstant.ATT_QUY_VAR_CARD_SUB, subCardCommonVariable);

        }
    }

    public void saveQueryControlDefaultValues(HttpServletRequest request, ModelAndView modelView) {
        String queryControlDefaultValues = "";
        if (CharUtil.isMimiType(request)) {
            queryControlDefaultValues = CharUtil.ChineseToIsoForMimi(request.getParameter("queryControlDefaultValues"));
        } else {
            queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        }

        String lineName = request.getParameter("_lineName");
        String stationName = request.getParameter("_stationName");
        String mainCardName = request.getParameter("_mainCardName");
        String subCardName = request.getParameter("_subCardName");
        String stationCommonVariable = request.getParameter("_stationCommonVariable");
        String subCardCommonVariable = request.getParameter("_subCardCommonVariable");
        if (lineName != null) {
            modelView.addObject("QueryLineName", lineName);

        }
        if (stationName != null) {
            modelView.addObject("QueryStationName", stationName);

        }
        if (mainCardName != null) {
            modelView.addObject("QueryMainCardName", mainCardName);

        }
        if (subCardName != null) {
            modelView.addObject("QuerySubCardName", subCardName);

        }
        if (stationCommonVariable != null) {
            modelView.addObject("StationCommonVariable", stationCommonVariable);

        }
        if (subCardCommonVariable != null) {
            modelView.addObject("SubCardCommonVariable", subCardCommonVariable);

        }
        if (queryControlDefaultValues != null) {
            modelView.addObject("ControlDefaultValues", queryControlDefaultValues);

        }

    }

    public String getControlDefaultValues(HttpServletRequest request) {
        String result = "";
        String controlNames = request.getParameter("ControlNames");
        if (controlNames == null || controlNames.length() == 0) {
            return "";
        }
        StringTokenizer st = new StringTokenizer(controlNames, "#");
        String name = "";
        String value = "";
        while (st.hasMoreTokens()) {
            name = st.nextToken();
            value = request.getParameter(name);
//            if (value != null) {
//                if (CharUtil.isMimiType(request)) {
//                    value = CharUtil.ChineseToIsoForMimi(value);
//                } else {
//                    value = CharUtil.IsoToUTF8GbkToIso(value);
//                }
//            }
            result += name + "#" + value + ";";
        }
        if (result.length() != 0) {
            result = result.substring(0, result.length());
        }
        // System.out.println("getControlDefaultValues="+result);
        return result;
    }

    public void savePrecommand(HttpServletRequest request, ModelAndView modelView) {
        String command = request.getParameter("command");
        String precommand = request.getParameter("precommand");
        if (command != null) {
            modelView.addObject("Precommand", command);
        }
        if (precommand != null) {
            modelView.addObject("PreTwoCommand", precommand);

        }
    }

    protected ModuleDistrVo getModulePriviledge(String moduleID, Vector modulePriviledges) {
        ModuleDistrVo mv = null;
        for (int i = 0; i < modulePriviledges.size(); i++) {
            mv = (ModuleDistrVo) modulePriviledges.get(i);
            if (mv.getModuleID().trim().equals(moduleID)) {
                return mv;
            }
        }
        return null;
    }

    protected boolean isUpdateOp(String command, String operType) {
        if (command == null) {
            return false;
        }
        if (command != null) {
            // modify by luck 20170915 翻页保存查询条件
            if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY) || command.equals(CommandConstant.COMMAND_AUDIT)
                    || command.equals(CommandConstant.COMMAND_BACK) || command.equals(CommandConstant.COMMAND_BACKEND) || command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND)) {
                return true;
            }

        }
        return false;
    }
	//从缓存中获取结果集  20180309 add by zhouy
    public List getBufferElements(HttpServletRequest request) {
        HashMap buffer = (HashMap) request.getSession().getAttribute(NAME_BUFFER);
        List app = ( List) request.getSession().getAttribute(NAME_RESULT);
        Enumeration<String> s = request.getSession().getAttributeNames();
        System.out.println(app.size());
        List results = new ArrayList();
        List list = new ArrayList();
        if(buffer!=null){
            for(int key = 1;key<=buffer.size();key++){
                list=(List) buffer.get(key);
                results.addAll(list);
            }
        }
        return results;
    }

    /**
     * 从缓存中获取当前模块的结果集
     * @param request
     * @param className
     * @return
     */
    public List getBufferElementsForCurClass(HttpServletRequest request,String className) {
//        HashMap buffer = (HashMap)request.getSession().getAttribute(NAME_BUFFER);
        //modify by zhongzq 20189417
        HashMap buffer = (HashMap)request.getSession().getAttribute(PageControlUtil.NAME_BUFFER);
        List results = new ArrayList();
        List list = new ArrayList();
        if(buffer!=null){
            list=(List) buffer.get(1); //取第一页的记录
            if (list == null || list.size() ==0) {
                return results;
            }
            String bufferClassName = list.get(0).getClass().getName();
            //判断是否当前模块的类
            if (!bufferClassName.equals(className)) {
                return results;
            }

            for(int key = 1;key<=buffer.size();key++){
                list=(List) buffer.get(key);
                results.addAll(list);
            }
        }
        return results;
    }

}
