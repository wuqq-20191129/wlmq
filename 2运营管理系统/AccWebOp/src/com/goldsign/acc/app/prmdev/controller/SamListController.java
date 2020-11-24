/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prmdev.controller;

import com.goldsign.acc.app.prmdev.entity.SamList;
import com.goldsign.acc.app.prmdev.mapper.SamListMapper;
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
 * sam卡对照表
 * @author lind
 */
@Controller
public class SamListController extends PrmBaseController {
    
    @Autowired
    private SamListMapper samListMapper;


    @RequestMapping("/SamList")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prmdev/sam_list.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        PrmVersion prmVersion = new PrmVersion();
        prmVersion = this.getPrmVersion(request, response);
        
        try {
//            if (command == null || command.equals(CommandConstant.COMMAND_LIST)) {
//                command = CommandConstant.COMMAND_QUERY;
//            }
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
        
        String[] attrNames = {LINES, STATIONS, LINE_STATIONS, DEV_TYPES, SAMTYPES, VERSION};

        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.getResultSetText((List<SamList>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;
    }
    
    @RequestMapping("/SamListExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.prmdev.entity.SamList");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
    
    private void getResultSetText(List<SamList> resultSet, ModelAndView mv) {
        List<PubFlag> lines = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.LINES);
        List<PubFlag> stations = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.STATIONS);
        List<PubFlag> devTypes = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.DEV_TYPES);
        List<PubFlag> samTypes = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.SAMTYPES);

        for (SamList sd : resultSet) {
            if (lines != null && !lines.isEmpty()) {
                sd.setLineIdText(DBUtil.getTextByCode(sd.getLineId(), lines));
            }
            if (stations != null && !stations.isEmpty()) {
                sd.setStationIdText(DBUtil.getTextByCode(sd.getStationId(), sd.getLineId(), stations));
            }
            if (devTypes != null && !devTypes.isEmpty()) {
                sd.setDeviceTypeIdText(DBUtil.getTextByCode(sd.getDevTypeId(), devTypes));
            }
            if (samTypes != null && !samTypes.isEmpty()) {
                sd.setSamTypeCodeText(DBUtil.getTextByCode(sd.getSamTypeId(), samTypes));
            }

        }

    }

    private SamList getQueryConditionForOp(HttpServletRequest request, PrmVersion prmVersion) {

        SamList qCon = new SamList();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }

            qCon.setLineId(FormUtil.getParameter(request, "d_lineID"));
            qCon.setStationId(FormUtil.getParameter(request,"d_stationID"));
            qCon.setSamLogicalId(FormUtil.getParameter(request,"d_samLogicalID"));
            qCon.setDevTypeId(FormUtil.getParameter(request,"d_deviceTypeID"));
            qCon.setDeviceId(FormUtil.getParameter(request,"d_deviceID"));
            qCon.setSamTypeId(FormUtil.getParameter(request,"d_samTypeCode"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setLineId(FormUtil.getParameter(request, "q_lineID"));
                qCon.setStationId(FormUtil.getParameter(request,"q_stationID"));
                qCon.setSamLogicalId(FormUtil.getParameter(request,"q_samLogicalID"));
                qCon.setSamTypeId(FormUtil.getParameter(request,"q_samTypeId"));
                qCon.setDevTypeId(FormUtil.getParameter(request,"q_devTypeId"));
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

    private SamList getQueryCondition(HttpServletRequest request, PrmVersion prmVersion) {
        SamList qCon = new SamList();
        qCon.setLineId(FormUtil.getParameter(request, "q_lineID"));
        qCon.setStationId(FormUtil.getParameter(request,"q_stationID"));
        qCon.setSamLogicalId(FormUtil.getParameter(request,"q_samLogicalID"));
        qCon.setSamTypeId(FormUtil.getParameter(request,"q_samTypeId"));
        qCon.setDevTypeId(FormUtil.getParameter(request,"q_devTypeId"));
        qCon.setVersionNo(prmVersion.getVersion_no());
        qCon.setRecordFlag(prmVersion.getRecord_flag());

        return qCon;
    }

    public OperationResult query(HttpServletRequest request, PrmVersion prmVersion) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        SamList queryCondition;
        List<SamList> resultSet;

        try {
            queryCondition = this.getQueryCondition(request, prmVersion);
            resultSet = samListMapper.selectSamLists(queryCondition);
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
        SamList queryCondition;
        List<SamList> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request,prmVersion);
            if (queryCondition == null) {
                return null;
            }
            resultSet = samListMapper.selectSamLists(queryCondition);
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
        SamList sl = this.getReqAttribute(request,prmVersion);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "SAM卡对照表," + "主键：" + "SAM卡号:" + sl.getSamLogicalId()+ " 版本号:" +sl.getVersionNo() + "生效标志:"
                + sl.getRecordFlag() + ",";
        if (CharUtil.getDBLenth(sl.getSamLogicalId()) > 20) {
            rmsg.addMessage(preMsg + "SAM卡号最大值不能超过20位。");
            return rmsg;
        } else {
            try {
                n = this.modifyByTrans(sl);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, this.operationLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), this.operationLogMapper);
        }
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(SamList sl) {
        SamList tmp = samListMapper.selectByPrimaryKey(sl);
        if (tmp==null) {
            return false;
        }
        return true;

    }

    private int addByTrans(SamList sl) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = samListMapper.insert(sl);
            this.prmVersionMapper.modifyPrmVersionForDraft(sl.getPrmVersion());

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
            samListMapper.submitToOldFlag(prmVersion);
            //生成新的未来或当前参数的版本号
            versionNoMax = this.prmVersionMapper.selectPrmVersionForSubmit(prmVersion);
            this.getVersionNoForSubmit(prmVersion, versionNoMax);

            //添加新的未来或当前参数的数据记录
            n = samListMapper.submitFromDraftToCurOrFur(prmVersion);
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
            samListMapper.deleteDeviceTypesForClone(prmVersion);
            //未来或当前参数克隆成草稿版本
            n = samListMapper.cloneFromCurOrFurToDraft(prmVersion);
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
//        SamList sl = this.getReqAttributeForClone(request);
        
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

    private int modifyByTrans(SamList sl) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = samListMapper.updateByPrimaryKeySelective(sl);
            this.prmVersionMapper.modifyPrmVersionForDraft(sl.getPrmVersion());

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private int deleteByTrans( Vector<SamList> dts, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (SamList sl : dts) {
                n += samListMapper.deleteByPrimaryKey(sl);
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
        SamList sl = this.getReqAttribute(request,prmVersion);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "SAM卡对照表," + "主键：" + "SAM卡号:" + sl.getSamLogicalId()+ " 版本号:" +sl.getVersionNo() + "生效标志:"
                + sl.getRecordFlag() + ",";
        if (CharUtil.getDBLenth(sl.getSamLogicalId()) > 20) {
            rmsg.addMessage(preMsg + "SAM卡号最大值不能超过20位。");
            return rmsg;
        } else {
            try {
                if (this.existRecord(sl)) {
                    rmsg.addMessage("增加记录已存在！");
                    return rmsg;
                }

                n = this.addByTrans(sl);

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
        Vector<SamList> dts = this.getReqAttributeForDelete(request);
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

    public SamList getReqAttribute(HttpServletRequest request, PrmVersion prmVersion) {
        SamList sl = new SamList();

        sl.setLineId(FormUtil.getParameter(request, "d_lineID"));
        sl.setStationId(FormUtil.getParameter(request,"d_stationID"));
        sl.setSamLogicalId(FormUtil.getParameter(request,"d_samLogicalID"));
        sl.setDevTypeId(FormUtil.getParameter(request,"d_deviceTypeID"));
        sl.setDeviceId(FormUtil.getParameter(request,"d_deviceID"));
        sl.setSamTypeId(FormUtil.getParameter(request,"d_samTypeCode"));
        sl.setVersionNo(prmVersion.getVersion_no());
        sl.setRecordFlag(prmVersion.getRecord_flag());
        sl.setPrmVersion(prmVersion);
        return sl;
    }

    public void getReqAttributeForSubmit(HttpServletRequest request, PrmVersion prmVersion) {
        this.getBaseParametersForSubmit(request, prmVersion);
    }

    public SamList getReqAttributeForClone(HttpServletRequest request) {
        SamList sl = new SamList();
        return sl;
    }

    private Vector<SamList> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<SamList> dts = new Vector();
        SamList sl;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sl = this.getSamLists(strIds, "#");
            dts.add(sl);
        }
        return dts;

    }

    private SamList getSamLists(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        SamList sl = new SamList();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 3) {
                sl.setSamLogicalId(tmp);
                continue;
            }
            if (i == 4) {
                sl.setVersionNo(tmp);
                continue;
            }
            if (i == 5) {
                sl.setRecordFlag(tmp);
                continue;
            }
        }
        return sl;

    }

    private Vector<SamList> getReqAttributeForDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<SamList> selectedItems = this.getDeleteIDs(selectIds);

        return selectedItems;
    }
}
