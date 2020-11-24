/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.AdminReason;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.app.prminfo.mapper.AdminReasonMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.PrmBaseController;

import com.goldsign.acc.frame.util.CharUtil;
import com.goldsign.acc.frame.util.ExpUtil;

import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 行政处理原因
 * @author lind
 */
@Controller
public class AdminReasonController extends PrmBaseController {
    
    @Autowired
    private AdminReasonMapper adminReasonMapper;


    @RequestMapping("/AdminReason")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/admin_reason.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        PrmVersion prmVersion = new PrmVersion();
        prmVersion = this.getPrmVersion(request, response);
        
        try {
//            if (command == null || command.equals(CommandConstant.COMMAND_LIST)) {
//                command = CommandConstant.COMMAND_QUERY;
//            }
//            command = command.trim();
            if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作   
            {
                opResult = this.modify(request, prmVersion);
            }
            if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
            {
                opResult = this.delete(request, prmVersion);
            }
            if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
            {
                opResult = this.add(request, prmVersion);
            }

            if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
            {
                opResult = this.query(request, prmVersion);

            }
            if (command.equals(CommandConstant.COMMAND_SUBMIT))//提交操作
            {
                opResult = this.submit(request, prmVersion);
            }
            if (command.equals(CommandConstant.COMMAND_CLONE))//克隆操作
            {
                opResult = this.clone(request, prmVersion);

            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, opResult, prmVersion);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        String[] attrNames = {VERSION};

        this.setPageOptions(attrNames, mv, request, response);//设置页面版本
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;
    }
    
    private AdminReason getQueryConditionForOp(HttpServletRequest request, PrmVersion prmVersion) {

        AdminReason qCon = new AdminReason();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }

            qCon.setAdminManagerId(FormUtil.getParameter(request, "d_adminManagerID"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setAdminManagerId(FormUtil.getParameter(request, "q_adminManagerID"));
                qCon.setAdminManagerName(FormUtil.getParameter(request,"q_adminManagerName"));
            }
        }
        qCon.setRecordFlag(prmVersion.getRecord_flag());
        //当操作为克隆时，查询克隆后的草稿版本 注释后改为按当前条件查询
//        if (command.equals(CommandConstant.COMMAND_CLONE)) {
//            qCon.setRecordFlag(ParameterConstant.RECORD_FLAG_DRAFT);
//        }

        qCon.setVersionNo(prmVersion.getVersion_no());
        return qCon;
    }

    public OperationResult query(HttpServletRequest request, PrmVersion prmVersion) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        AdminReason queryCondition;
        List<AdminReason> resultSet;

        try {
            queryCondition = this.getQueryCondition(request, prmVersion);
            resultSet = adminReasonMapper.selectAdminReasons(queryCondition);
            or.setReturnResultSet(resultSet);
            request.getSession().setAttribute("queryCondition", queryCondition);   
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, this.operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", this.operationLogMapper);
        return or;

    }
    
    private AdminReason getQueryCondition(HttpServletRequest request, PrmVersion prmVersion) {
        AdminReason qCon = new AdminReason();
        qCon.setAdminManagerId(FormUtil.getParameter(request, "q_adminManagerID"));
        qCon.setAdminManagerName(FormUtil.getParameter(request,"q_adminManagerName"));
        qCon.setVersionNo(prmVersion.getVersion_no());
        qCon.setRecordFlag(prmVersion.getRecord_flag());

        return qCon;
    }

    public OperationResult queryForOp(HttpServletRequest request, OperationResult opResult, PrmVersion prmVersion) throws Exception {
        LogUtil logUtil = new LogUtil();
        AdminReason queryCondition;
        List<AdminReason> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request,prmVersion);
            if (queryCondition == null) {
                return null;
            }
            resultSet = adminReasonMapper.selectAdminReasons(queryCondition);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, this.operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", this.operationLogMapper);
        return opResult;

    }

    public OperationResult modify(HttpServletRequest request, PrmVersion prmVersion) throws Exception {
        OperationResult rmsg = new OperationResult();
        AdminReason ar = this.getReqAttribute(request,prmVersion);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "行政处理原因：" + "主键：" + "ID:" + ar.getAdminManagerId()+ "版本号" +ar.getVersionNo() + "生效标志"
                + ar.getRecordFlag() + ":";
        if (CharUtil.getDBLenth(ar.getAdminManagerId()) > 2) {
            rmsg.addMessage(preMsg + "行政处理原因ID不能超过2位。");
            return rmsg;
        } else {
            try {
                n = this.modifyByTrans(ar);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, this.operationLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), this.operationLogMapper);
        }
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(AdminReason ar) {
        AdminReason tmp = adminReasonMapper.selectByPrimaryKey(ar);
        if (tmp==null) {
            return false;
        }
        return true;

    }

    private int addByTrans(AdminReason ar) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = adminReasonMapper.insert(ar);
            this.prmVersionMapper.modifyPrmVersionForDraft(ar.getPrmVersion());

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private void getVersionNoForSubmit(PrmVersion prmVersion, String versionNoMax) {
        String max;
        int n;
        if (versionNoMax != null && versionNoMax.length() == 10) {
            max = versionNoMax.substring(8, 10);
            if (max.length() == 2) {
                n = new Integer(max).intValue();
                n++;
                max = Integer.toString(n);
                if (max.length() == 1) {
                    max = "0" + max;
                }
            }
        } else {
            max = "01";
        }
        String versionNoNew = prmVersion.getVersion_valid_date() + max;
        prmVersion.setVersion_no_new(versionNoNew);
        prmVersion.setRecord_flag_new(prmVersion.getRecord_flag_submit());

    }

    

    public int submitByTrans(HttpServletRequest request, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        String versionNoMax;
        int n = 0;
        try {
            status = txMgr.getTransaction(def);

            //旧的未来或当前参数数据做删除标志
            adminReasonMapper.submitToOldFlag(prmVersion);
            //生成新的未来或当前参数的版本号
            versionNoMax = this.prmVersionMapper.selectPrmVersionForSubmit(prmVersion);
            this.getVersionNoForSubmit(prmVersion, versionNoMax);

            //添加新的未来或当前参数的数据记录
            n = adminReasonMapper.submitFromDraftToCurOrFur(prmVersion);
            // 重新生成参数表中的未来或当前参数记录
            this.prmVersionMapper.modifyPrmVersionForSubmit(prmVersion);
            this.prmVersionMapper.addPrmVersion(prmVersion);
          //  test.getBytes();

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    

    private int cloneByTrans(PrmVersion prmVersion) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);

            //删除草稿版本
            adminReasonMapper.deleteDeviceTypesForClone(prmVersion);
            //未来或当前参数克隆成草稿版本
            n = adminReasonMapper.cloneFromCurOrFurToDraft(prmVersion);
            //更新参数版本索引信息
            this.prmVersionMapper.modifyPrmVersionForDraft(prmVersion);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult submit(HttpServletRequest request, PrmVersion prmVersion) throws Exception {
        OperationResult rmsg = new OperationResult();
        this.getReqAttributeForSubmit(request, prmVersion);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.submitByTrans(request, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_SUBMIT, e, this.operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_SUBMIT, request, LogConstant.submitSuccessMsg(n, prmVersion.getRecord_flag_new()), this.operationLogMapper);

        rmsg.addMessage(LogConstant.submitSuccessMsg(n, prmVersion.getRecord_flag_new()));

        return rmsg;
    }

    public OperationResult clone(HttpServletRequest request, PrmVersion prmVersion) throws Exception {
        OperationResult rmsg = new OperationResult();
        
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.cloneByTrans(prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CLONE, e, this.operationLogMapper);
        }
        //当前还是未来？
        logUtil.logOperation(CommandConstant.COMMAND_CLONE, request, LogConstant.cloneSuccessMsg(n), this.operationLogMapper);

        rmsg.addMessage(LogConstant.cloneSuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(AdminReason ar) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = adminReasonMapper.updateByPrimaryKeySelective(ar);
            this.prmVersionMapper.modifyPrmVersionForDraft(ar.getPrmVersion());

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private int deleteByTrans( Vector<AdminReason> dts, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (AdminReason ar : dts) {
                n += adminReasonMapper.deleteByPrimaryKey(ar);
            }
            this.prmVersionMapper.modifyPrmVersionForDraft(prmVersion);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult add(HttpServletRequest request, PrmVersion prmVersion) throws Exception {
        OperationResult rmsg = new OperationResult();
        AdminReason ar = this.getReqAttribute(request,prmVersion);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "行政处理原因：" + "主键：" + "ID:" + ar.getAdminManagerId()+ "版本号" +ar.getVersionNo() + "生效标志"
                + ar.getRecordFlag() + ":";
        if (CharUtil.getDBLenth(ar.getAdminManagerId()) > 2) {
            rmsg.addMessage(preMsg + "行政处理原因ID不能超过2位。");
            return rmsg;
        } else {
            try {
                if (this.existRecord(ar)) {
                    rmsg.addMessage("增加记录已存在！");
                    return rmsg;
                }

                n = this.addByTrans(ar);

            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, this.operationLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), this.operationLogMapper);
        }
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult delete(HttpServletRequest request, PrmVersion prmVersion) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<AdminReason> dts = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(dts, prmVersion);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, this.operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), this.operationLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public AdminReason getReqAttribute(HttpServletRequest request, PrmVersion prmVersion) {
        AdminReason ar = new AdminReason();

        ar.setAdminManagerId(FormUtil.getParameter(request, "d_adminManagerID"));
        ar.setAdminManagerName(FormUtil.getParameter(request,"d_adminManagerName"));
        ar.setVersionNo(prmVersion.getVersion_no());
        ar.setRecordFlag(prmVersion.getRecord_flag());
        ar.setPrmVersion(prmVersion);
        return ar;
    }

    public void getReqAttributeForSubmit(HttpServletRequest request, PrmVersion prmVersion) {
        this.getBaseParametersForSubmit(request, prmVersion);
    }

    public AdminReason getReqAttributeForClone(HttpServletRequest request) {
        AdminReason ar = new AdminReason();
        return ar;
    }

    private Vector<AdminReason> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<AdminReason> dts = new Vector();
        AdminReason ar;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            ar = this.getAdminReasons(strIds, "#");
            dts.add(ar);
        }
        return dts;

    }

    private AdminReason getAdminReasons(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        AdminReason ar = new AdminReason();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                ar.setAdminManagerId(tmp);
                continue;
            }
            if (i == 2) {
                ar.setVersionNo(tmp);
                continue;
            }
            if (i == 3) {
                ar.setRecordFlag(tmp);
                continue;
            }
        }
        return ar;

    }

    private Vector<AdminReason> getReqAttributeForDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<AdminReason> selectedItems = this.getDeleteIDs(selectIds);

        return selectedItems;
    }
    
    
    
    
     @RequestMapping("/AdminReasonExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	AdminReason queryCondition = null;
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.prminfo.entity.AdminReason");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private List<Map<String, String>> entityToMap(List results) {
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            AdminReason vo = (AdminReason)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("adminManagerId", vo.getAdminManagerId());
            map.put("adminManagerName", vo.getAdminManagerName());
            list.add(map);
        }
        return list;
    }
}
