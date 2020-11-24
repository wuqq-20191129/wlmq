/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.controller;

import com.goldsign.acc.app.opma.entity.CodPubFlag;
import com.goldsign.acc.app.opma.entity.SysGroup;
import com.goldsign.acc.app.opma.entity.SysGroupOperatorKey;
import com.goldsign.acc.app.password.entity.SysOperator;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.BaseController;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.util.Encryption;
import java.math.BigDecimal;
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
import com.goldsign.acc.app.opma.mapper.CodPubFlagMapper;
import com.goldsign.acc.app.opma.mapper.SysGroupMapper;
import com.goldsign.acc.app.opma.mapper.SysGroupOperatorMapper;
import com.goldsign.acc.app.password.mapper.SysOperatorMapper;
import com.goldsign.acc.frame.constant.TypeConstant;
import com.goldsign.acc.frame.util.FormUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author 刘粤湘
 * @date 2017-6-12 8:58:36
 * @version V1.0
 * @desc 权限组模块管理
 */
@Controller
public class GroupController extends BaseController {

    private static Logger logger = Logger.getLogger(GroupController.class.getName());

    @Autowired
    SysGroupMapper sysGrpMapper;

    @Autowired
    SysOperatorMapper sysOprMapper;

    @Autowired
    CodPubFlagMapper codPubFlagMapper;

    @Autowired
    SysGroupOperatorMapper sysGrpOprMapper;

    private String operatorId = null;
    private String grpId = null;
    private String eid = null;

    @RequestMapping("/groupList")
    public ModelAndView groupList(HttpServletRequest request, HttpServletResponse response) {

        try {
            String type = request.getParameter("type");
            ModelAndView mv = null;

            if (type.equals("020102")) {
                mv = new ModelAndView("/jsp/opma/sys_operator.jsp");
            } else {
                mv = new ModelAndView("/jsp/opma/groupList.jsp");
            }

            String command = request.getParameter("command");
            String type2 = request.getParameter("type2");
            String type1 = request.getParameter("type1");
            OperationResult opResult = new OperationResult();
            List<SysGroup> sysGrps = new ArrayList();
            List<SysGroup> sysGrps1 = new ArrayList();
            List<SysOperator> oprs = new ArrayList();
            sysGrps1 = sysGrpMapper.getAll();
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    if (type2 != null && type2.equals("opr")) {
                        opResult = this.add(request, this.sysOprMapper, this.operationLogMapper);
                        sysGrps = sysGrpMapper.getAll();
                    } else {
                        opResult = this.add(request, this.sysGrpMapper, this.operationLogMapper);
                        oprs = sysOprMapper.selectAll(null);

                    }

                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    if (type2 != null && type2.equals("opr")) {
                        opResult = this.delete(request, this.sysOprMapper, this.operationLogMapper);
                    } else {
                        opResult = this.delete(request, this.sysGrpMapper, this.operationLogMapper);
                    }
                }
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    if (type2 != null && type2.equals("opr")) {
                        opResult = this.modify(request, this.sysOprMapper, this.operationLogMapper);
                        sysGrps = sysGrpMapper.getAll();
                    } else {
                        opResult = this.modify(request, this.sysGrpMapper, this.operationLogMapper);
                        oprs = sysOprMapper.selectAll(null);
                    }
                }
                if (command.equals("passReset"))//修改操作
                {
                    opResult = this.resetPass(request, this.sysOprMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    if (type1 != null && type1.equals("opr")) {
                        operatorId = null;
                        oprs = this.query(request, this.sysOprMapper, this.operationLogMapper);
                        opResult.addMessage("成功查询" + oprs.size() + "条记录");
                        sysGrps = sysGrpMapper.getAll();
                    } else {
                        sysGrps = this.query(request, this.sysGrpMapper, this.operationLogMapper);
                        oprs = sysOprMapper.selectAll(null);
                        opResult.addMessage("成功查询" + sysGrps.size() + "条记录");
                    }
                }
            }

            //权限组
            if (command.equals(CommandConstant.COMMAND_LIST) || command.equals(CommandConstant.COMMAND_DELETE)) {
                grpId = null;
                sysGrps = sysGrpMapper.getAll();
            }
            if (grpId != null) {
                SysGroup grp = sysGrpMapper.selectByPrimaryKey(grpId);
                sysGrps.add(grp);
            }
            opResult.setReturnResultSet(sysGrps);
            mv.addObject("sysGrps", sysGrps);
            mv.addObject("sysGrps1", sysGrps1);

            //添加票务仓库类型
            Map<String, Object> map = new HashMap();
            map.put("type", TypeConstant.TYPE_STORAGE);
            List<CodPubFlag> ticketStorages = codPubFlagMapper.find(map);
            mv.addObject("ticketStorages", ticketStorages);

            for (Object obj : opResult.getReturnResultSet()) {
                SysGroup sysGrp = (SysGroup) obj;
                for (CodPubFlag codPubFlag : ticketStorages) {
                    if (sysGrp.getSysStorageId().equals(codPubFlag.getCode())) {
                        sysGrp.setSysStorageName(codPubFlag.getCodeText());
                    }
                }
            }

            //操作员
            if (command.equals(CommandConstant.COMMAND_LIST) || command.equals(CommandConstant.COMMAND_DELETE)) {
                operatorId = null;
                oprs = sysOprMapper.selectAll(null);
            }
            if (operatorId != null) {
                SysOperator opr = sysOprMapper.selectByOperatorId(operatorId);
                if (opr == null && eid != null) {
                    SysOperator selectByEmployeeId = sysOprMapper.selectByEmployeeId(eid);
                    if (selectByEmployeeId != null) {
                        oprs.add(selectByEmployeeId);
                    }
                }

                if (opr != null) {
                    oprs.add(opr);
                }

            }

            map.put("type", TypeConstant.TYPE_LOCKED);
            List<CodPubFlag> codPubFlags = codPubFlagMapper.find(map);
            mv.addObject("lockeds", codPubFlags);
            if (oprs.size() > 0) {
                for (SysOperator sysOpr : oprs) {
                    for (CodPubFlag codPubFlag : codPubFlags) {
                        if (sysOpr.getSysStatus().equals(codPubFlag.getCode())) {
                            sysOpr.setSysStatus(codPubFlag.getCodeText());
                        }
                    }
                }
            }
            mv.addObject("ResultSet1", oprs);

            this.baseHandler(request, response, mv);
//            this.divideResultSet(request, mv, opResult);
            this.SaveOperationResult(mv, opResult);

            return mv;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return null;
    }

    public List<SysGroup> query(HttpServletRequest request, SysGroupMapper sgMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        SysGroup queryCondition;
        List<SysGroup> resultSet = null;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = sgMapper.querySysGroup(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
//            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return resultSet;

    }

    public List<SysOperator> query(HttpServletRequest request, SysOperatorMapper soMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        SysOperator queryCondition;
        List<SysOperator> resultSet = null;

        try {
            queryCondition = this.getQueryCondition1(request);
            resultSet = soMapper.querySysOperators(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
//            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return resultSet;

    }

    private SysGroup getQueryCondition(HttpServletRequest request) {
        SysGroup qCon = new SysGroup();
        qCon.setSysGroupId(FormUtil.getParameter(request, "q_sysGroupId"));
        qCon.setSysGroupName(FormUtil.getParameter(request, "q_sysGroupName"));
        qCon.setSysStorageId(FormUtil.getParameter(request, "q_storageName"));
        return qCon;
    }

    private SysOperator getQueryCondition1(HttpServletRequest request) {
        SysOperator qCon = new SysOperator();
        qCon.setSysOperatorId(FormUtil.getParameter(request, "q_sysOperatorId"));
        qCon.setSysOperatorName(FormUtil.getParameter(request, "q_sysOperatorName"));
        qCon.setSysEmployeeId(FormUtil.getParameter(request, "q_sysEmployeeId"));
        qCon.setSysStatus(FormUtil.getParameter(request, "q_status"));
        qCon.setSysGroupId(FormUtil.getParameter(request, "q_sysGroupName_1"));
        return qCon;
    }

    public OperationResult add(HttpServletRequest request, SysGroupMapper sysGrpMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        try {
            SysGroup sysGrp = this.getReqAttribute(request);
            if (sysGrpMapper.count(sysGrp) > 0) {
                or.addMessage("组别名称重复");
                return or;
            }
            List<SysGroup> sysGroups = sysGrpMapper.getAll();
            String sysGrpId = "";
            for (int n = 1; n < 100; n++) {
                for (SysGroup sysGrpTmp : sysGroups) {
                    sysGrpId = n + "";
                    if (sysGrpId.length() == 1) {
                        sysGrpId = "0" + sysGrpId;
                    }
                    if (sysGrpId.equals(sysGrpTmp.getSysGroupId())) {
                        sysGrpId = "";
                        break;
                    }
                }
                if (!sysGrpId.equals("")) {
                    break;
                }
            }
            this.grpId = sysGrpId;
            sysGrp.setSysGroupId(sysGrpId);

            int i = sysGrpMapper.insert(sysGrp);
            or.addMessage("成功增加权限组" + i + "条记录");
            logUtil.logOperation(CommandConstant.COMMAND_ADD, request,
                    LogConstant.addSuccessMsg(i) + "名称：" + sysGrp.getSysGroupName() + "ID：" + sysGrp.getSysGroupId(), opLogMapper);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        return or;
    }

    private SysGroup getReqAttribute(HttpServletRequest request) throws Exception {
        SysGroup sysGrp = new SysGroup();
        sysGrp.setSysGroupId(request.getParameter("d_groupID"));
        if (sysGrp.getSysGroupId() != null && sysGrp.getSysGroupId().trim().equals("")) {
            sysGrp.setSysGroupId(null);
        }
        grpId = sysGrp.getSysGroupId();
        String d_groupName = request.getParameter("d_groupName");
        sysGrp.setSysGroupName(d_groupName);
        sysGrp.setSysStorageId(request.getParameter("d_storage_id"));

        return sysGrp;
    }

    private OperationResult modify(HttpServletRequest request, SysGroupMapper sysGrpMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        SysGroup sysGrp = this.getReqAttribute(request);
        int i = 0;
        try {
            i = sysGrpMapper.updateByPrimaryKeySelective(sysGrp);
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(i) + "ID：" + sysGrp.getSysGroupId(), operationLogMapper);
            or.addMessage("成功修改权限组" + i + "条记录！");
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, operationLogMapper);
        }
        return or;
    }

    private OperationResult add(HttpServletRequest request, SysOperatorMapper sysOprMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        Map<String, Object> map = new HashMap();
        try {
            SysOperator sysOpr = this.getReqAttributeOpr(request);
            if (sysOpr.getSysPasswordHash() == null) {
                or.addMessage("密码为空");
                return or;
            }
            map.put("sysOperatorId", sysOpr.getSysOperatorId());
            map.put("sysEmployeeId", sysOpr.getSysEmployeeId());
            int i = sysOprMapper.count(map);
            if (i > 0) {
                or.addMessage("操作员ID重复");
                return or;
            }
            i = sysOprMapper.checkEmployeeId(map);
            if (i > 0) {
                eid = sysOpr.getSysEmployeeId();
                or.addMessage("工号重复");
                return or;
            }

            i = sysOprMapper.insertSelective(sysOpr);
            or.addMessage("成功增加操作员" + i + "条记录");
            logUtil.logOperation(CommandConstant.COMMAND_ADD, request,
                    LogConstant.addSuccessMsg(i) + "名称：" + sysOpr.getSysOperatorName() + "ID：" + sysOpr.getSysOperatorId(), operationLogMapper);
            String[] grpIds = sysOpr.getSysGroupId().split(",");
            for (String grpId : grpIds) {
                SysGroupOperatorKey sysGrpOpr = new SysGroupOperatorKey();
                sysGrpOpr.setSysGroupId(grpId);
                sysGrpOpr.setSysOperatorId(sysOpr.getSysOperatorId());
                i = sysGrpOprMapper.insertSelective(sysGrpOpr);
                //or.addMessage(LogConstant.addSuccessMsg(i));
                logUtil.logOperation(CommandConstant.COMMAND_ADD, request,
                        LogConstant.addSuccessMsg(i) + "ID：" + sysGrpOpr.getSysOperatorId() + "ID：" + sysGrpOpr.getSysGroupId(), operationLogMapper);
            }

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
        }
        return or;
    }

    /**
     * 修改操作员，先删除操作员对应的组再加上修改的组
     *
     * @param request
     * @param sysOprMapper
     * @param operationLogMapper
     * @return
     * @throws Exception
     */
    private OperationResult modify(HttpServletRequest request, SysOperatorMapper sysOprMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        try {
            SysOperator sysOpr = this.getReqAttributeOpr(request);
            //修改操作员信息
            sysOpr.setPasswordEditDate(null);
            int i = sysOprMapper.updateByPrimaryKeySelective(sysOpr);
            or.addMessage("成功修改操作员" + i + "条记录！");
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request,
                    LogConstant.modifySuccessMsg(i) + "名称：" + sysOpr.getSysOperatorName() + "ID：" + sysOpr.getSysOperatorId(), operationLogMapper);
            //先删除操作员对应的组
            i = sysGrpOprMapper.deleteGrpOprByOprId(sysOpr.getSysOperatorId());
            //or.addMessage(LogConstant.delSuccessMsg(i));
            logUtil.logOperation(CommandConstant.COMMAND_DELETE, request,
                    LogConstant.delSuccessMsg(i) + "名称：" + sysOpr.getSysOperatorName() + "ID：" + sysOpr.getSysOperatorId(), operationLogMapper);

            //再增加操作员对应的组
            String[] grpIds = sysOpr.getSysGroupId().split(",");
            for (String grpId : grpIds) {
                SysGroupOperatorKey sysGrpOpr = new SysGroupOperatorKey();
                sysGrpOpr.setSysGroupId(grpId);
                sysGrpOpr.setSysOperatorId(sysOpr.getSysOperatorId());
                int k = sysGrpOprMapper.countByPrimaryKey(sysGrpOpr);
                if (k == 0) {
                    i = sysGrpOprMapper.insertSelective(sysGrpOpr);
                    //or.addMessage(LogConstant.addSuccessMsg(i));
                    logUtil.logOperation(CommandConstant.COMMAND_ADD, request,
                            LogConstant.addSuccessMsg(i) + "ID：" + sysGrpOpr.getSysOperatorId() + "ID：" + sysGrpOpr.getSysGroupId(), operationLogMapper);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, operationLogMapper);
        }
        return or;
    }

    private OperationResult resetPass(HttpServletRequest request, SysOperatorMapper sysOprMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        try {
            SysOperator sysOpr = this.getReqAttributeOpr(request);
            int i = sysOprMapper.resetPassword(sysOpr);
            if (i > 0) {
                or.addMessage("成功重置密码！");
            } else {
                or.addMessage("成功重置失败！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, operationLogMapper);
        }
        return or;
    }

    private SysOperator getReqAttributeOpr(HttpServletRequest request) throws Exception {
        SysOperator sysOpr = new SysOperator();

        sysOpr.setSysOperatorId(request.getParameter("operatorID"));
        this.operatorId = sysOpr.getSysOperatorId();
        String password = request.getParameter("password");
        String encPassword = "";
        if (password == null) {
            encPassword = null;
        }
        if (password != null && password.trim().equals("")) {
            encPassword = null;
        }
        if (password != null && !password.trim().equals("")) {
            encPassword = Encryption.biEncrypt(password);
        }
        sysOpr.setSysPasswordHash(encPassword);
        String name = request.getParameter("name");
//        new String(name.getBytes("iso-8859-1"),"utf-8")
        sysOpr.setSysOperatorName(name);
        sysOpr.setSysEmployeeId(request.getParameter("employeeID"));
        String expiredDate = request.getParameter("expiredDate");
        String expiredDates[] = expiredDate.split("-");
        expiredDate = expiredDates[0] + expiredDates[1] + expiredDates[2] + "235959";
        sysOpr.setSysExpiredDate(expiredDate);
        sysOpr.setSysStatus(request.getParameter("status"));

        sysOpr.setSysGroupId(this.getMultiValueParameter(request, "groupID_1"));
        sysOpr.setLoginNum(BigDecimal.ZERO);
        sysOpr.setFailedNum(BigDecimal.ZERO);
        sysOpr.setSessionId(request.getSession().getId());
        SimpleDateFormat sy1 = new SimpleDateFormat("yyyyMMdd");
        String passwordEditDate = sy1.format(new Date());

        sysOpr.setPasswordEditDate(passwordEditDate);
        sysOpr.setEditPastDays(BigDecimal.valueOf(Long.parseLong("-1")));
        return sysOpr;
    }

    private String getMultiValueParameter(HttpServletRequest req, String key) {
        String result = "";
        String values[] = req.getParameterValues(key);
        if (values == null) {
            return "";
        }
        for (int i = 0; i < values.length; i++) {
            result += values[i] + ",";
        }
        if (result.length() != 0) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    private OperationResult delete(HttpServletRequest request, SysOperatorMapper sysOprMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        int i = 0;
        try {//allSelectedIDs
            String oprIds = request.getParameter("allSelectedIDs");
            if (oprIds != null) {
                String[] oprIdss = oprIds.split(";");
                for (String oprId : oprIdss) {
                    i += sysOprMapper.deleteByPrimaryKey(oprId);
                    sysGrpOprMapper.deleteGrpOprByOprId(oprId);
                }
            }

            logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(i), opLogMapper);

            or.addMessage("成功删除操作员" + i + "条记录");
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }

        return or;
    }

    private OperationResult delete(HttpServletRequest request, SysGroupMapper sysGrpMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        int i = 0;
        try {//allSelectedIDs
            String grpIds = request.getParameter("allSelectedIDs");
            if (grpIds != null) {
                String[] grpIdss = grpIds.split(";");
                List<String> grpIdList = new ArrayList();
                for (String grpId : grpIdss) {

                    grpIdList.add(grpId);

                }
                //校验该组下有操作员
                Map<String, Object> map = new HashMap();
                map.put("grpIds", grpIdList);
                List<Object> objs = sysGrpOprMapper.selectByGroupId(map);
                if (objs != null && objs.size() > 0) {
                    or.addMessage("该组下有关联的操作员不能删除。");
                    return or;
                }
                for (String grpId : grpIdss) {
                    //删除操作员
                    i += sysGrpMapper.deleteByPrimaryKey(grpId);

//                    sysGrpOprMapper.deleteGrpOprByGrpId(grpId);
                }
            }

            logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(i), opLogMapper);

            or.addMessage("成功删除权限组" + i + "条记录");
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }

        return or;
    }

}
