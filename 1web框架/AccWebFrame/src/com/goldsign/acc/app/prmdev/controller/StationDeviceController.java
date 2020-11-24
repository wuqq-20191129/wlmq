/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prmdev.controller;

import com.goldsign.acc.app.prmdev.entity.StationDevice;
import com.goldsign.acc.app.prmdev.mapper.StationDeviceMapper;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.app.prminfo.mapper.PrmVersionMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.constant.ParameterConstant;

import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.CharUtil;
import com.goldsign.acc.frame.util.DBUtil;

import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.vo.User;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author hejj
 */
@Controller
public class StationDeviceController extends PrmBaseController {

    @Autowired
    private StationDeviceMapper stationDeviceMapper;


    @RequestMapping("/StationDevice")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prmdev/station_device.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.stationDeviceMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.stationDeviceMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.stationDeviceMapper, this.prmVersionMapper, this.operationLogMapper);
                }

                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.stationDeviceMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_SUBMIT))//提交操作
                {
                    opResult = this.submit(request, this.stationDeviceMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_CLONE))//克隆操作
                {
                    opResult = this.clone(request, this.stationDeviceMapper, this.prmVersionMapper, this.operationLogMapper);

                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.stationDeviceMapper, this.operationLogMapper, opResult);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {LINES, STATIONS, LINE_STATIONS, DEV_TYPES, MERCHANTS, VERSION};

        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.getResultSetText((List<StationDevice>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }

    private void getResultSetText(List<StationDevice> resultSet, ModelAndView mv) {
        List<PubFlag> lines = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.LINES);
        List<PubFlag> stations = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.STATIONS);
        List<PubFlag> devTypes = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.DEV_TYPES);
        List<PubFlag> merchants = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.MERCHANTS);

        for (StationDevice sd : resultSet) {
            if (lines != null && !lines.isEmpty()) {
                sd.setLine_id_name(DBUtil.getTextByCode(sd.getLine_id(), lines));
            }
            if (stations != null && !stations.isEmpty()) {
                sd.setStation_id_name(DBUtil.getTextByCode(sd.getStation_id(), sd.getLine_id(), stations));
            }
            if (devTypes != null && !devTypes.isEmpty()) {
                sd.setDev_type_id_name(DBUtil.getTextByCode(sd.getDev_type_id(), devTypes));
            }
            if (merchants != null && !merchants.isEmpty()) {
                sd.setStore_id_name(DBUtil.getTextByCode(sd.getStore_id(), merchants));
            }

        }

    }

    private StationDevice getQueryConditionForOp(HttpServletRequest request) {

        StationDevice qCon = new StationDevice();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }

            qCon.setLine_id(FormUtil.getParameter(request, "d_lineID"));
            qCon.setStation_id(FormUtil.getParameter(request,"d_stationID"));
            qCon.setDev_type_id(FormUtil.getParameter(request,"d_deviceType"));
            qCon.setDevice_id(FormUtil.getParameter(request,"d_deviceID"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setLine_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_lineID"));
                qCon.setStation_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_stationID"));
                qCon.setDev_type_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_deviceType"));
                qCon.setDevice_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_deviceID"));

                qCon.setDev_serial(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_deviceSerial"));
            }
        }
        this.getBaseParameters(request, qCon);
        //当操作为克隆时，查询克隆后的草稿版本
        if (command.equals(CommandConstant.COMMAND_CLONE)) {
            qCon.setRecord_flag(ParameterConstant.RECORD_FLAG_DRAFT);
        }

        return qCon;
    }

    private StationDevice getQueryCondition(HttpServletRequest request) {
        StationDevice qCon = new StationDevice();
        qCon.setLine_id(FormUtil.getParameter(request, "q_lineID"));
        qCon.setStation_id(FormUtil.getParameter(request, "q_stationID"));
        qCon.setDev_type_id(FormUtil.getParameter(request, "q_deviceType"));
        qCon.setDevice_id(FormUtil.getParameter(request, "q_deviceID"));
        qCon.setDev_serial(FormUtil.getParameter(request, "q_deviceSerial"));

        this.getBaseParameters(request, qCon);
        return qCon;
    }

    public OperationResult query(HttpServletRequest request, StationDeviceMapper sdMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        StationDevice queryCondition;
        List<StationDevice> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = sdMapper.getStationDevices(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult queryForOp(HttpServletRequest request, StationDeviceMapper sdMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        // OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        StationDevice queryCondition;
        List<StationDevice> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request);
            if (queryCondition == null) {
                return null;
            }
            resultSet = sdMapper.getStationDevices(queryCondition);
            opResult.setReturnResultSet(resultSet);
            //or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult modify(HttpServletRequest request, StationDeviceMapper sdMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        StationDevice po = this.getReqAttribute(request);
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "车站设备：" + "主键：" + "线路" + po.getLine_id() + "车站" + po.getStation_id() + "设备类型"
                + po.getDev_type_id() + "设备ID" + po.getDevice_id() + ":";
        if (CharUtil.getDBLenth(po.getDev_name()) > 30) {
            rmsg.addMessage(preMsg + "车站设备名称最大值不能超过30位(中文字符为两位)。");
            return rmsg;
        } else {
            try {
                n = this.modifyByTrans(request, sdMapper, pvMapper, po);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        }
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(StationDevice po, StationDeviceMapper sdMapper, OperationLogMapper opLogMapper) {
        List<StationDevice> list = sdMapper.getStationDeviceById(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private int addByTrans(HttpServletRequest request, StationDeviceMapper sdMapper, PrmVersionMapper pvMapper, StationDevice po) throws Exception {
        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {

            txMgr = DBUtil.getDataSourceTransactionManager(request);
            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            n = sdMapper.addStationDevice(po);
            pvMapper.modifyPrmVersionForDraft(po);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private void getVersionNoForSubmit(StationDevice po, String versionNoMax) {
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

    

    public int submitByTrans(HttpServletRequest request, StationDeviceMapper sdMapper, PrmVersionMapper pvMapper, StationDevice po, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        List<PrmVersion> prmVersions;
        String versionNoMax;
        int n = 0;
        int n1 = 0;
       // String test=null;
        try {

            status = txMgr.getTransaction(def);
            

            //旧的未来或当前参数数据做删除标志
            sdMapper.submitToOldFlag(po);
            //生成新的未来或当前参数的版本号
            versionNoMax = pvMapper.selectPrmVersionForSubmit(po);
            this.getVersionNoForSubmit(po, versionNoMax);

            //添加新的未来或当前参数的数据记录
            n = sdMapper.submitFromDraftToCurOrFur(po);
            // 重新生成参数表中的未来或当前参数记录
            pvMapper.modifyPrmVersionForSubmit(po);
            pvMapper.addPrmVersion(po);
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

    

    private int cloneByTrans(HttpServletRequest request, StationDeviceMapper sdMapper, PrmVersionMapper pvMapper, StationDevice po, PrmVersion prmVersion) throws Exception {
        //DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        List<PrmVersion> prmVersions;
       // String versionNoMax;
        int n = 0;
        int n1 = 0;
        try {

           // txMgr = DBUtil.getDataSourceTransactionManager(request);
            status = txMgr.getTransaction(this.def);

            //删除草稿版本
            sdMapper.deleteStationDevicesForClone(po);
            //未来或当前参数克隆成草稿版本
            n = sdMapper.cloneFromCurOrFurToDraft(po);
            //更新参数版本索引信息
            pvMapper.modifyPrmVersionForDraft(po);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult submit(HttpServletRequest request, StationDeviceMapper sdMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        StationDevice po = this.getReqAttributeForSubmit(request);
        StationDevice prmVersion = null;
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.submitByTrans(request, sdMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_SUBMIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_SUBMIT, request, LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()), opLogMapper);

        rmsg.addMessage(LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()));

        return rmsg;
    }

    public OperationResult clone(HttpServletRequest request, StationDeviceMapper sdMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        StationDevice po = this.getReqAttributeForClone(request);
        StationDevice prmVersion = null;
       // User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.cloneByTrans(request, sdMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CLONE, e, opLogMapper);
        }
        //当前还是未来？
        logUtil.logOperation(CommandConstant.COMMAND_CLONE, request, LogConstant.cloneSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.cloneSuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(HttpServletRequest request, StationDeviceMapper sdMapper, PrmVersionMapper pvMapper, StationDevice po) throws Exception {
        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {

            txMgr = DBUtil.getDataSourceTransactionManager(request);
            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            n = sdMapper.modifyStationDevice(po);
            pvMapper.modifyPrmVersionForDraft(po);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private int deleteByTrans(HttpServletRequest request, StationDeviceMapper sdMapper, PrmVersionMapper pvMapper, Vector<StationDevice> pos, StationDevice prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {


            status = txMgr.getTransaction(this.def);
            for (StationDevice po : pos) {
                n += sdMapper.deleteStationDevice(po);
            }
            pvMapper.modifyPrmVersionForDraft(prmVo);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult add(HttpServletRequest request, StationDeviceMapper sdMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        StationDevice po = this.getReqAttribute(request);
       // User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "车站设备：" + "主键：" + "线路" + po.getLine_id() + "车站" + po.getStation_id() + "设备类型"
                + po.getDev_type_id() + "设备ID" + po.getDevice_id() + ":";
        if (CharUtil.getDBLenth(po.getDev_name()) > 30) {
            rmsg.addMessage(preMsg + "车站设备名称最大值不能超过30位(中文字符为两位)。");
            return rmsg;
        } else {
            try {
                if (this.existRecord(po, sdMapper, opLogMapper)) {
                    rmsg.addMessage(preMsg + "记录已存在！");
                    return rmsg;
                }

                // n = sdMapper.addStationDevice(po);
                n = this.addByTrans(request, sdMapper, pvMapper, po);

            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);
        }
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult delete(HttpServletRequest request, StationDeviceMapper sdMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<StationDevice> pos = this.getReqAttributeForDelete(request);
        StationDevice prmVo = new StationDevice();
        this.getBaseParameters(request, prmVo);
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, sdMapper, pvMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public StationDevice getReqAttribute(HttpServletRequest request) {
        StationDevice po = new StationDevice();

        // String cscNumber = ForUtil.getParameter("d_CSCNumber");
        po.setLine_id(FormUtil.getParameter(request, "d_lineID"));
        po.setStation_id(FormUtil.getParameter(request, "d_stationID"));
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
        //--del po.setVersionNo(version);
        //--del po.setParamTypeID(type);
        return po;
    }

    public StationDevice getReqAttributeForSubmit(HttpServletRequest request) {
        StationDevice po = new StationDevice();

        this.getBaseParameters(request, po);
        this.getBaseParametersForSubmit(request, po);

        return po;
    }

    public StationDevice getReqAttributeForClone(HttpServletRequest request) {
        StationDevice po = new StationDevice();

        this.getBaseParameters(request, po);

        return po;
    }

    private Vector<StationDevice> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<StationDevice> sds = new Vector();
        StationDevice sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getStationDevice(strIds, "#");
            sds.add(sd);
        }
        return sds;

    }

    private StationDevice getStationDevice(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        StationDevice sd = new StationDevice();;
        Vector<StationDevice> sds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setLine_id(tmp);
                continue;
            }
            if (i == 2) {
                sd.setStation_id(tmp);
                continue;
            }
            if (i == 3) {
                sd.setDev_type_id(tmp);

                continue;
            }
            if (i == 4) {
                sd.setDevice_id(tmp);
                continue;
            }
            if (i == 5) {
                sd.setVersion_no(tmp);
                continue;
            }
            if (i == 6) {
                sd.setRecord_flag(tmp);
                continue;
            }

        }
        return sd;

    }

    private Vector<StationDevice> getReqAttributeForDelete(HttpServletRequest request) {
        StationDevice po = new StationDevice();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<StationDevice> selectedItems = this.getDeleteIDs(selectIds);

        //this.getBaseParameters(request, po);
        return selectedItems;
    }

}
