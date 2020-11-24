/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prmdev.controller;

import com.goldsign.acc.app.prmdev.entity.ACCDevice;
import com.goldsign.acc.app.prmdev.mapper.ACCDeviceMapper;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;

import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.util.CharUtil;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;

import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.vo.User;
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
 * ACC设备
 * @author hejj
 */
@Controller
public class ACCDeviceController extends PrmBaseController {

    @Autowired
    private ACCDeviceMapper accDeviceMapper;


    @RequestMapping("/ACCDevice")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prmdev/acc_device.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request);
                }

                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request);

                }
                if (command.equals(CommandConstant.COMMAND_SUBMIT))//提交操作
                {
                    opResult = this.submit(request);
                }
                if (command.equals(CommandConstant.COMMAND_CLONE))//克隆操作
                {
                    opResult = this.clone(request);

                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT)) {
                    this.queryForOp(request, opResult);
                }
                if (!command.equals(CommandConstant.COMMAND_ADD) || !command.equals(CommandConstant.COMMAND_QUERY)) {
                    this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {DEV_TYPES, MERCHANTS, VERSION};

        this.setPageOptions(attrNames, mv, request, response);//设置页面选项值、版本
        this.getResultSetText((List<ACCDevice>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }
    
    @RequestMapping("/ACCDeviceExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.prmdev.entity.ACCDevice");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private void getResultSetText(List<ACCDevice> resultSet, ModelAndView mv) {
        List<PubFlag> devTypes = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.DEV_TYPES);
        List<PubFlag> merchants = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.MERCHANTS);

        for (ACCDevice sd : resultSet) {
            if (devTypes != null && !devTypes.isEmpty()) {
                sd.setDev_type_id_name(DBUtil.getTextByCode(sd.getDev_type_id(), devTypes));
            }
            if (merchants != null && !merchants.isEmpty()) {
                sd.setStore_id_name(DBUtil.getTextByCode(sd.getStore_id(), merchants));
            }

        }

    }

    private ACCDevice getQueryConditionForOp(HttpServletRequest request) {

        ACCDevice qCon = new ACCDevice();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }

            qCon.setDev_type_id(FormUtil.getParameter(request,"d_deviceType"));
            qCon.setDevice_id(FormUtil.getParameter(request,"d_deviceID"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setDev_type_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_deviceType"));
                qCon.setDevice_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_deviceID"));

                qCon.setDev_serial(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_deviceSerial"));
            }
        }
        this.getBaseParameters(request, qCon);
        //当操作为克隆时，查询克隆后的草稿版本 注释后改为按当前条件查询
//        if (command.equals(CommandConstant.COMMAND_CLONE)) {
//            qCon.setRecord_flag(ParameterConstant.RECORD_FLAG_DRAFT);
//        }

        return qCon;
    }

    private ACCDevice getQueryCondition(HttpServletRequest request) {
        ACCDevice qCon = new ACCDevice();
        qCon.setDev_type_id(FormUtil.getParameter(request, "q_deviceType"));
        qCon.setDevice_id(FormUtil.getParameter(request, "q_deviceID"));
        qCon.setDev_serial(FormUtil.getParameter(request, "q_deviceSerial"));

        this.getBaseParameters(request, qCon);
        return qCon;
    }

    public OperationResult query(HttpServletRequest request) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        ACCDevice queryCondition;
        List<ACCDevice> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = accDeviceMapper.getACCDevices(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, this.operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", this.operationLogMapper);
        return or;

    }

    public OperationResult queryForOp(HttpServletRequest request, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        ACCDevice queryCondition;
        List<ACCDevice> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request);
            if (queryCondition == null) {
                return null;
            }
            resultSet = accDeviceMapper.getACCDevices(queryCondition);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, this.operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", this.operationLogMapper);
        return opResult;

    }

    public OperationResult modify(HttpServletRequest request) throws Exception {
        OperationResult rmsg = new OperationResult();
        ACCDevice po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "ACC设备," + "主键：" + "设备类型ID:" + po.getDev_type_id() + " 设备ID:" + po.getDevice_id() + ",";
        if (CharUtil.getDBLenth(po.getDev_name()) > 30) {
            rmsg.addMessage(preMsg + "ACC设备名称最大值不能超过30位(中文字符为两位)。");
            return rmsg;
        } else {
            try {
                n = this.modifyByTrans(request, po);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, this.operationLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), this.operationLogMapper);
        }
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(ACCDevice po) {
        List<ACCDevice> list = accDeviceMapper.getACCDeviceById(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private int addByTrans(ACCDevice po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = accDeviceMapper.addACCDevice(po);
            this.prmVersionMapper.modifyPrmVersionForDraft(po);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private void getVersionNoForSubmit(ACCDevice po, String versionNoMax) {
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
        String versionNoNew = po.getVersion_valid_date() + max;
        po.setVersion_no_new(versionNoNew);
        po.setRecord_flag_new(po.getRecord_flag_submit());

    }

    

    public int submitByTrans(HttpServletRequest request, ACCDevice po, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        String versionNoMax;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            
            //旧的未来或当前参数数据做删除标志
            accDeviceMapper.submitToOldFlag(po);
            //生成新的未来或当前参数的版本号
            versionNoMax = this.prmVersionMapper.selectPrmVersionForSubmit(po);
            this.getVersionNoForSubmit(po, versionNoMax);

            //添加新的未来或当前参数的数据记录
            n = accDeviceMapper.submitFromDraftToCurOrFur(po);
            // 重新生成参数表中的未来或当前参数记录
            this.prmVersionMapper.modifyPrmVersionForSubmit(po);
            this.prmVersionMapper.addPrmVersion(po);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    

    private int cloneByTrans(ACCDevice po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);

            //删除草稿版本
            accDeviceMapper.deleteACCDevicesForClone(po);
            //未来或当前参数克隆成草稿版本
            n = accDeviceMapper.cloneFromCurOrFurToDraft(po);
            //更新参数版本索引信息
            this.prmVersionMapper.modifyPrmVersionForDraft(po);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult submit(HttpServletRequest request) throws Exception {
        OperationResult rmsg = new OperationResult();
        ACCDevice po = this.getReqAttributeForSubmit(request);
        ACCDevice prmVersion = null;
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.submitByTrans(request, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_SUBMIT, e, this.operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_SUBMIT, request, LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()), this.operationLogMapper);

        rmsg.addMessage(LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()));

        return rmsg;
    }

    public OperationResult clone(HttpServletRequest request) throws Exception {
        OperationResult rmsg = new OperationResult();
        ACCDevice po = this.getReqAttributeForClone(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.cloneByTrans(po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CLONE, e, this.operationLogMapper);
        }
        //当前还是未来？
        logUtil.logOperation(CommandConstant.COMMAND_CLONE, request, LogConstant.cloneSuccessMsg(n), this.operationLogMapper);

        rmsg.addMessage(LogConstant.cloneSuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(HttpServletRequest request, ACCDevice po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = accDeviceMapper.modifyACCDevice(po);
            this.prmVersionMapper.modifyPrmVersionForDraft(po);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private int deleteByTrans(Vector<ACCDevice> pos, ACCDevice prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (ACCDevice po : pos) {
                n += accDeviceMapper.deleteACCDevice(po);
            }
            this.prmVersionMapper.modifyPrmVersionForDraft(prmVo);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult add(HttpServletRequest request) throws Exception {
        OperationResult rmsg = new OperationResult();
        ACCDevice po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "ACC设备," + "主键：" + "设备类型ID:" + po.getDev_type_id() + " 设备ID:" + po.getDevice_id() + ",";
        if (CharUtil.getDBLenth(po.getDev_name()) > 30) {
            rmsg.addMessage(preMsg + "ACC设备名称最大值不能超过30位(中文字符为两位)。");
            return rmsg;
        } else {
            try {
                if (this.existRecord(po)) {
                    rmsg.addMessage("增加记录已存在！");
                    return rmsg;
                }

                n = this.addByTrans(po);

            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, this.operationLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), this.operationLogMapper);
        }
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult delete(HttpServletRequest request) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<ACCDevice> pos = this.getReqAttributeForDelete(request);
        ACCDevice prmVo = new ACCDevice();
        this.getBaseParameters(request, prmVo);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, this.operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), this.operationLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public ACCDevice getReqAttribute(HttpServletRequest request) {
        ACCDevice po = new ACCDevice();

        po.setDev_type_id(FormUtil.getParameter(request, "d_deviceType"));
        po.setDevice_id(FormUtil.getParameter(request, "d_deviceID"));

        po.setCsc_num(Integer.parseInt(FormUtil.getParameter(request, "d_CSCNumber")));

        po.setArray_id(FormUtil.getParameter(request, "d_arrayID"));
        po.setConcourse_id(FormUtil.getParameter(request, "d_zoneID"));
        po.setDev_serial(FormUtil.getParameter(request, "d_deviceSerial"));
        po.setIp_address(FormUtil.getParameter(request, "d_IPAddress"));
        po.setStore_id(FormUtil.getParameter(request, "d_venderID"));

        po.setConfig_date(FormUtil.getParameter(request, "d_configDate"));
        po.setDev_name(FormUtil.getParameter(request, "d_devName"));
        this.getBaseParameters(request, po);
        return po;
    }

    public ACCDevice getReqAttributeForSubmit(HttpServletRequest request) {
        ACCDevice po = new ACCDevice();

        this.getBaseParameters(request, po);
        this.getBaseParametersForSubmit(request, po);

        return po;
    }

    public ACCDevice getReqAttributeForClone(HttpServletRequest request) {
        ACCDevice po = new ACCDevice();

        this.getBaseParameters(request, po);

        return po;
    }

    private Vector<ACCDevice> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<ACCDevice> sds = new Vector();
        ACCDevice sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getACCDevice(strIds, "#");
            sds.add(sd);
        }
        return sds;

    }

    private ACCDevice getACCDevice(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        ACCDevice sd = new ACCDevice();;
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setDev_type_id(tmp);
                continue;
            }
            if (i == 2) {
                sd.setDevice_id(tmp);
                continue;
            }
            if (i == 3) {
                sd.setVersion_no(tmp);
                continue;
            }
            if (i == 4) {
                sd.setRecord_flag(tmp);
                continue;
            }

        }
        return sd;

    }

    private Vector<ACCDevice> getReqAttributeForDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<ACCDevice> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }
    
    private void getBaseParameters(HttpServletRequest request, ACCDevice accDevice) {
        accDevice.setVersion_no(FormUtil.getParameter(request, "Version"));
        accDevice.setRecord_flag(FormUtil.getParameter(request, "VersionType"));
        accDevice.setParm_type_id(FormUtil.getParameter(request, "Type"));
        User user = (User) request.getSession().getAttribute("User");
        accDevice.setOperator_id(user.getAccount());
    }

}
