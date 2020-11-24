/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prmdev.controller;

import com.goldsign.acc.app.prmdev.entity.DeviceType;
import com.goldsign.acc.app.prmdev.mapper.DeviceTypeMapper;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;

import com.goldsign.acc.frame.util.CharUtil;
import com.goldsign.acc.frame.util.ExpUtil;

import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.vo.OperationResult;
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
 * 设备类型
 * @author lind
 */
@Controller
public class DeviceTypeController extends PrmBaseController {
    
    @Autowired
    private DeviceTypeMapper deviceTypeMapper;


    @RequestMapping("/DeviceType")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prmdev/device_type.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        PrmVersion prmVersion = new PrmVersion();
        prmVersion = this.getPrmVersion(request, response);
        
        try {
            if (command == null || command.equals(CommandConstant.COMMAND_LIST)) {
                command = CommandConstant.COMMAND_QUERY;
            }
            command = command.trim();
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
                    this.queryForOp(request, opResult, prmVersion);
                }
                if (!command.equals(CommandConstant.COMMAND_ADD) || !command.equals(CommandConstant.COMMAND_QUERY)) {
                    this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        String[] attrNames = {DEV_TYPES};
        this.setPageOptions(attrNames, mv, request, response);
        PubFlag recordFlagName = this.pubFlagMapper.getRecordFlags(prmVersion.getRecord_flag());
        this.getRecordFlagName(prmVersion, recordFlagName);
        mv.addObject(this.VERSION, prmVersion);
        this.baseHandler(request, response, mv);
        this.SaveOperationResult(mv, opResult);//返回结果集
        new PageControlUtil().putBuffer(request, opResult.getReturnResultSet());
        return mv;

    }
    
    @RequestMapping("/DeviceTypeExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.prmdev.entity.DeviceType");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
    
    private DeviceType getQueryConditionForOp(HttpServletRequest request, PrmVersion prmVersion) {

        HashMap vQueryControlDefaultValues = null;
        DeviceType qCon = new DeviceType();
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }

            qCon.setDevTypeId(FormUtil.getParameter(request, "d_devTypeId"));
            qCon.setInstallLocation(FormUtil.getParameter(request, "d_installLocation"));
            qCon.setIsSale(FormUtil.getParameter(request, "d_isSale"));
            qCon.setIsCharge(FormUtil.getParameter(request, "d_isCharge"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setInstallLocation(FormUtil.getParameter(request, "q_installLocation"));
                qCon.setDevTypeId(FormUtil.getParameter(request, "q_devTypeId"));
                qCon.setDevTypeName(FormUtil.getParameter(request, "q_deviceTypeName"));
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

    private DeviceType getQueryCondition(HttpServletRequest request, PrmVersion prmVersion) {
        DeviceType qCon = new DeviceType();
        qCon.setVersionNo(prmVersion.getVersion_no());
        qCon.setRecordFlag(prmVersion.getRecord_flag());
        qCon.setInstallLocation(FormUtil.getParameter(request, "q_installLocation"));
        qCon.setDevTypeId(FormUtil.getParameter(request, "q_devTypeId"));
        qCon.setDevTypeName(FormUtil.getParameter(request, "q_deviceTypeName"));

        return qCon;
    }

    public OperationResult query(HttpServletRequest request, PrmVersion prmVersion) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        DeviceType queryCondition;
        List<DeviceType> resultSet;

        try {
            queryCondition = this.getQueryCondition(request, prmVersion);
            resultSet = deviceTypeMapper.selectDeviceTypes(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, this.operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", this.operationLogMapper);
        return or;

    }

    public OperationResult queryForOp(HttpServletRequest request, OperationResult opResult, PrmVersion prmVersion) throws Exception {
        LogUtil logUtil = new LogUtil();
        DeviceType queryCondition;
        List<DeviceType> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request,prmVersion);
            if (queryCondition == null) {
                return null;
            }
            resultSet = deviceTypeMapper.selectDeviceTypes(queryCondition);
            opResult.setReturnResultSet(resultSet);
            //or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, this.operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", this.operationLogMapper);
        return opResult;

    }

    public OperationResult modify(HttpServletRequest request, PrmVersion prmVersion) throws Exception {
        OperationResult rmsg = new OperationResult();
        DeviceType dt = this.getReqAttribute(request,prmVersion);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "设备类型," + "主键：" + "设备类型ID:" + dt.getDevTypeId() + " 版本号:" +dt.getVersionNo() + " 生效标志:"
                + dt.getRecordFlag() + ",";
        if (CharUtil.getDBLenth(dt.getDevTypeName()) > 30) {
            rmsg.addMessage(preMsg + "设备类型名称最大值不能超过30位(中文字符为两位)。");
            return rmsg;
        } else {
            try {
                n = this.modifyByTrans(dt);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, this.operationLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), this.operationLogMapper);
        }
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(DeviceType dt) {
        DeviceType tmp = deviceTypeMapper.selectByPrimaryKey(dt);
        if (tmp==null) {
            return false;
        }
        return true;

    }

    private int addByTrans(DeviceType dt) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = deviceTypeMapper.insert(dt);
            this.prmVersionMapper.modifyPrmVersionForDraft(dt.getPrmVersion());

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
            deviceTypeMapper.submitToOldFlag(prmVersion);
            //生成新的未来或当前参数的版本号
            versionNoMax = this.prmVersionMapper.selectPrmVersionForSubmit(prmVersion);
            this.getVersionNoForSubmit(prmVersion, versionNoMax);

            //添加新的未来或当前参数的数据记录
            n = deviceTypeMapper.submitFromDraftToCurOrFur(prmVersion);
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
            deviceTypeMapper.deleteDeviceTypesForClone(prmVersion);
            //未来或当前参数克隆成草稿版本
            n = deviceTypeMapper.cloneFromCurOrFurToDraft(prmVersion);
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
//        DeviceType dt = this.getReqAttributeForClone(request);
        
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

    private int modifyByTrans(DeviceType dt) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = deviceTypeMapper.updateByPrimaryKeySelective(dt);
            this.prmVersionMapper.modifyPrmVersionForDraft(dt.getPrmVersion());

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private int deleteByTrans( Vector<DeviceType> dts, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (DeviceType dt : dts) {
                n += deviceTypeMapper.deleteByPrimaryKey(dt);
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
        DeviceType dt = this.getReqAttribute(request,prmVersion);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "设备类型," + "主键：" + "设备类型ID:" + dt.getDevTypeId() + " 版本号:" +dt.getVersionNo() + " 生效标志:"
                + dt.getRecordFlag() + ",";
        if (CharUtil.getDBLenth(dt.getDevTypeName()) > 30) {
            rmsg.addMessage(preMsg + "设备类型名称最大值不能超过30位(中文字符为两位)。");
            return rmsg;
        } else {
            try {
                if (this.existRecord(dt)) {
                    rmsg.addMessage("增加记录已存在！");
                    return rmsg;
                }

                n = this.addByTrans(dt);

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
        Vector<DeviceType> dts = this.getReqAttributeForDelete(request);
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

    public DeviceType getReqAttribute(HttpServletRequest request, PrmVersion prmVersion) {
        DeviceType dt = new DeviceType();

        dt.setDevTypeId(FormUtil.getParameter(request, "d_devTypeId"));
        dt.setDevTypeName(FormUtil.getParameter(request, "d_devTypeName"));
        dt.setInstallLocation(FormUtil.getParameter(request, "d_installLocation"));
        dt.setIsSale(FormUtil.getParameter(request, "d_isSale"));
        dt.setIsCharge(FormUtil.getParameter(request, "d_isCharge"));
        dt.setVersionNo(prmVersion.getVersion_no());
        dt.setRecordFlag(prmVersion.getRecord_flag());
        dt.setPrmVersion(prmVersion);
        return dt;
    }

    public void getReqAttributeForSubmit(HttpServletRequest request, PrmVersion prmVersion) {
        this.getBaseParametersForSubmit(request, prmVersion);
    }

    public DeviceType getReqAttributeForClone(HttpServletRequest request) {
        DeviceType dt = new DeviceType();
        return dt;
    }

    private Vector<DeviceType> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<DeviceType> dts = new Vector();
        DeviceType dt;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            dt = this.getDeviceTypes(strIds, "#");
            dts.add(dt);
        }
        return dts;

    }

    private DeviceType getDeviceTypes(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        DeviceType dt = new DeviceType();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                dt.setDevTypeId(tmp);
                continue;
            }
            if (i == 2) {
                dt.setVersionNo(tmp);
                continue;
            }
            if (i == 3) {
                dt.setRecordFlag(tmp);
                continue;
            }
        }
        return dt;

    }

    private Vector<DeviceType> getReqAttributeForDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<DeviceType> selectedItems = this.getDeleteIDs(selectIds);

        return selectedItems;
    }

}
