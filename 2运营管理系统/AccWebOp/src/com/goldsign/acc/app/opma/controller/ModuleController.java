/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.controller;

import com.goldsign.acc.app.opma.entity.CodBtnType;
import com.goldsign.acc.app.opma.entity.CodPubFlag;
import com.goldsign.acc.app.opma.entity.SysModule;

import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.BaseController;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.goldsign.acc.app.opma.mapper.CodBtnTypeMapper;
import com.goldsign.acc.app.opma.mapper.CodPubFlagMapper;
import com.goldsign.acc.app.opma.mapper.SysModuleMapper;
import com.goldsign.acc.frame.constant.TypeConstant;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import java.util.ArrayList;

/**
 * @author 刘粤湘
 * @date 2017-6-6 15:35:13
 * @version V1.0
 * @desc 权限模块管理controller
 */
@Controller
public class ModuleController extends BaseController {

    private static Logger logger = Logger.getLogger(ModuleController.class.getName());

    @Autowired
    SysModuleMapper moduleMapper;

    @Autowired
    CodPubFlagMapper codPubFlagMapper;

    @Autowired
    CodBtnTypeMapper codBtnTypeMapper;

    private String gModuleId = "";

    @RequestMapping("/moduleList")
    public ModelAndView moduleList(HttpServletRequest request, HttpServletResponse response) {
        try {
            String type = (String) request.getParameter("type");
            String action = (String) request.getParameter("action");

            ModelAndView mv = new ModelAndView("/jsp/opma/moduleList.jsp");
            String command = request.getParameter("command");
            OperationResult opResult = new OperationResult();
            List<SysModule> resultSet = new ArrayList();
            SysModule sysModule = new SysModule();
            if (command != null) {
                command = command.trim();

                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.moduleMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.moduleMapper, this.operationLogMapper);
                    sysModule.setModuleId(this.gModuleId);
                    resultSet = moduleMapper.qModulesByCon(sysModule);
                    opResult.setReturnResultSet(resultSet);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.moduleMapper, this.operationLogMapper);
                    resultSet = moduleMapper.qModulesByCon(sysModule);
                    opResult.setReturnResultSet(resultSet);
                }
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.moduleMapper, this.operationLogMapper);
                    sysModule.setModuleId(this.gModuleId);
                    resultSet = moduleMapper.qModulesByCon(sysModule);
                    opResult.setReturnResultSet(resultSet);
                }

            }
            //设置权限
            this.baseHandler(request, response, mv);
            //添加系统类型
            Map<String, Object> map = new HashMap();
            map.put("type", TypeConstant.TYPE_SYS);
            List<CodPubFlag> codPubFlags = codPubFlagMapper.find(map);
            mv.addObject("codPubFlags", codPubFlags);
            //添加锁定类型
            map = new HashMap();
            map.put("type", TypeConstant.TYPE_LOCKED);
            List<CodPubFlag> lockeds = codPubFlagMapper.find(map);
            mv.addObject("lockeds", lockeds);
            //添加模块权限类型
            map = new HashMap();
            map.put("type", TypeConstant.TYPE_MODULE_PER);
            List<CodPubFlag> modulePers = codPubFlagMapper.find(map);
            mv.addObject("modulePers", modulePers);
            //添加按钮权限类型
            List<CodBtnType> btns = codBtnTypeMapper.selectBy("");
            mv.addObject("btns", btns);

            //转换表格锁定类型
            for (Object obj : opResult.getReturnResultSet()) {
                SysModule sysModuleTmp = (SysModule) obj;
                for (CodPubFlag codPubFlag : lockeds) {
                    if (sysModuleTmp.getLocked().equals(codPubFlag.getCode())) {
                        sysModuleTmp.setLocked(codPubFlag.getCodeText());
                    }
                }
                //转换模块权限类型
                for (CodPubFlag modulePer : modulePers) {
                    if (sysModuleTmp.getModuleType().equals(modulePer.getCode())) {
                        sysModuleTmp.setModuleType(modulePer.getCodeText());
                    }
                }
                //转换按钮权限类型
                for (CodBtnType btn : btns) {
                    if (sysModuleTmp.getBtnId() != null && sysModuleTmp.getBtnId().equals(btn.getBtnId())) {
                        sysModuleTmp.setBtnId(btn.getBtnName());
                    }
                }

                //转换系统类型
                for (CodPubFlag sysType : codPubFlags) {
                    if (sysModuleTmp.getSysFlag() != null && sysModuleTmp.getSysFlag().equals(sysType.getCode())) {
                        sysModuleTmp.setSysFlag(sysType.getCodeText());
                    }
                }
            }
            this.divideResultSet(request, mv, opResult);
            this.SaveOperationResult(mv, opResult);
            return mv;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    public OperationResult query(HttpServletRequest request, SysModuleMapper moduleMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        SysModule queryCondition;
        List<SysModule> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = moduleMapper.qModulesByCon(queryCondition);
            request.getSession().setAttribute("queryCondition", queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult add(HttpServletRequest request, SysModuleMapper moduleMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        try {
            SysModule sysModule = this.getReqAttribute(request);
            int i = moduleMapper.count(sysModule.getModuleId());
            if (i > 0) {
                or.addMessage("记录已存在");
                return or;
            }
            gModuleId = sysModule.getModuleId();
            i = moduleMapper.insertSelective(sysModule);
            or.addMessage(LogConstant.addSuccessMsg(i));
            logUtil.logOperation(CommandConstant.COMMAND_ADD, request,
                    LogConstant.addSuccessMsg(i) + "模块名称：" + sysModule.getModuleName() + "模块ID：" + sysModule.getModuleId(), opLogMapper);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }

        return or;
    }

    public OperationResult delete(HttpServletRequest request, SysModuleMapper moduleMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        int i = 0;
        try {//allSelectedIDs
            String moduleIds = request.getParameter("allSelectedIDs");
            if (moduleIds != null) {
                String[] moduleIdss = moduleIds.split(";");
                for (String moduleId : moduleIdss) {
                    i += moduleMapper.deleteByPrimaryKey(moduleId);
                }
            }

            logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(i), opLogMapper);

            or.addMessage(LogConstant.delSuccessMsg(i));
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }

        return or;
    }

    public OperationResult modify(HttpServletRequest request, SysModuleMapper moduleMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        SysModule sysModule = this.getReqAttribute(request);
        int i = 0;
        gModuleId = sysModule.getModuleId();
        try {
            i = moduleMapper.updateByPrimaryKeySelective(sysModule);
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(i) + "模块ID：" + sysModule.getModuleId(), opLogMapper);
            or.addMessage(LogConstant.modifySuccessMsg(i));
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        return or;
    }

    private SysModule getQueryCondition(HttpServletRequest request) {
        SysModule qCon = new SysModule();
        String qModuleId = request.getParameter("q_moduleID");
        if (qModuleId != null && qModuleId.equals("")) {
            qModuleId = null;
        }
        String qName = request.getParameter("q_name");
        if (qName != null && qName.equals("")) {
            qName = null;
        }
        String qTopId = request.getParameter("q_topID");
        if (qTopId != null && qTopId.equals("")) {
            qTopId = null;
        }
        String qParentId = request.getParameter("q_parentID");
        if (qParentId != null && qParentId.equals("")) {
            qParentId = null;
        }
        String qSysFlag = request.getParameter("q_sysFlag");
        if (qSysFlag != null && qSysFlag.equals("")) {
            qSysFlag = null;
        }
        qCon.setModuleId(qModuleId);
        qCon.setModuleName(qName);
        qCon.setTopMenuId(qTopId);
        qCon.setParentId(qParentId);
        qCon.setSysFlag(qSysFlag);

        return qCon;
    }

    private SysModule getReqAttribute(HttpServletRequest request) {
        SysModule sysModule = new SysModule();
        sysModule.setModuleId(request.getParameter("d_moduleId"));
        sysModule.setModuleName(request.getParameter("d_moduleName"));
        sysModule.setTopMenuId(request.getParameter("d_topMenuId"));
        sysModule.setParentId(request.getParameter("d_parentID"));
        sysModule.setMenuUrl(request.getParameter("d_menuUrl"));
        sysModule.setMenuIcon(request.getParameter("d_menuIcon"));
        sysModule.setLocked(request.getParameter("d_locked"));
        sysModule.setSysFlag(request.getParameter("d_sysFlag"));
        sysModule.setModuleType(request.getParameter("d_moduleType"));
        sysModule.setBtnId(request.getParameter("d_btnId"));
        return sysModule;
    }

    @RequestMapping("/SysModuleExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SysModule queryCondition = new SysModule();

        if (request.getSession().getAttribute("queryCondition") != null) {
            Object obj = request.getSession().getAttribute("queryCondition");
            if (obj instanceof SysModule) {
                queryCondition = (SysModule) request.getSession().getAttribute("queryCondition");
            }
        }
        Map<String, Object> map = new HashMap();
        map.put("type", TypeConstant.TYPE_SYS);
        List<CodPubFlag> codPubFlags = codPubFlagMapper.find(map);
        //添加锁定类型
        map = new HashMap();
        map.put("type", TypeConstant.TYPE_LOCKED);
        List<CodPubFlag> lockeds = codPubFlagMapper.find(map);
        //添加模块权限类型
        map = new HashMap();
        map.put("type", TypeConstant.TYPE_MODULE_PER);
        List<CodPubFlag> modulePers = codPubFlagMapper.find(map);
        //添加按钮权限类型
        List<CodBtnType> btns = codBtnTypeMapper.selectBy("");
        List<Map> queryResults = moduleMapper.queryToMap(queryCondition);
        /* 查询结果部分内容转换中文 */
        for (Map vo : queryResults) {
            if (lockeds != null && !lockeds.isEmpty()) {
                for (CodPubFlag codPubFlag : lockeds) {
                    if (vo.get("LOCKED").equals(codPubFlag.getCode())) {
                        vo.put("LOCKED_TEXT",codPubFlag.getCodeText());
                    }
                }
            }
            if (codPubFlags != null && !codPubFlags.isEmpty()) {
                for(CodPubFlag sysType : codPubFlags){
                    if (vo.get("SYS_FLAG")!=null && vo.get("SYS_FLAG").equals(sysType.getCode())) {
                        vo.put("SYS_FLAG_TEXT",sysType.getCodeText());
                        
                    }
                }
                
            }
            if (modulePers != null && !modulePers.isEmpty()) {
                for (CodPubFlag codPubFlag : modulePers) {
                    if (vo.get("MODULE_TYPE")!=null && vo.get("MODULE_TYPE").equals(codPubFlag.getCode())) {
                        vo.put("MODULE_TYPE_TEXT",codPubFlag.getCodeText());
                    }
                }             
            }
            if (btns != null && !btns.isEmpty()) {
                 for (CodBtnType btn : btns) {
                    if (vo.get("BTN_ID")!=null && vo.get("BTN_ID").equals(btn.getBtnId())) {
                        vo.put("BTN_ID_TEXT", btn.getBtnName());
                        
                    }
                }
                
            }
        }

        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

}
