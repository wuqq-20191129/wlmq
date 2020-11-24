/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.app.prminfo.entity.SystemRateContc;
import com.goldsign.acc.app.prminfo.entity.SystemRateLine;
import com.goldsign.acc.app.prminfo.mapper.PrmVersionMapper;
import com.goldsign.acc.app.prminfo.mapper.SystemRateContcMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.constant.ParameterConstant;
import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.vo.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * @author liudz
 */
@Controller
public class SystemRateContcController extends PrmBaseController {

    @Autowired
    private SystemRateContcMapper systemRateContcMapper;

    @RequestMapping("/SystemRateContc")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/system_rate_contc.jsp");
        SystemRateContc pmb = new SystemRateContc();
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        List<SystemRateContc> systemRateList = new ArrayList<SystemRateContc>();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.systemRateContcMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.systemRateContcMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.systemRateContcMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_SUBMIT))//提交操作
                {
                    opResult = this.submit(request, this.systemRateContcMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_CLONE))//克隆操作
                {
                    opResult = this.clone(request, this.systemRateContcMapper, this.prmVersionMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_QUERY) || command.equals(CommandConstant.COMMAND_LIST)) {
                    opResult = queryByUpdate(request, this.systemRateContcMapper, systemRateList,
                            this.operationLogMapper);
                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.systemRateContcMapper, this.operationLogMapper, opResult);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {CONTCS, VERSION};

        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.getResultSetText((List<SystemRateContc>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);
        return mv;

    }

    private void getResultSetText(List<SystemRateContc> resultSet, ModelAndView mv) {
        List<PubFlag> contcs = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CONTCS);
        for (SystemRateContc sd : resultSet) {
            if (contcs != null && !contcs.isEmpty()) {
                sd.setContc_id_name(DBUtil.getTextByCode(sd.getContc_id(), contcs));
                if (".".equals(sd.getService_rate().substring(0, 1))) {
                    sd.setService_rate("0" + sd.getService_rate());
                    if (sd.getService_rate().length() == 3) {
                        sd.setService_rate(sd.getService_rate() + "0");
                    }
                } else if ("0".equals(sd.getService_rate().substring(0, 1))) {
                    sd.setService_rate(sd.getService_rate() + ".00");
                } else if ("1".equals(sd.getService_rate().substring(0, 1))) {
                    sd.setService_rate(sd.getService_rate() + ".00");
                }
            }

        }

    }

    private SystemRateContc getQueryConditionForOp(HttpServletRequest request) {

        SystemRateContc qCon = new SystemRateContc();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
//            if (command.equals(CommandConstant.COMMAND_DELETE)) {
//                return null;
//            }
            qCon.setContc_id(FormUtil.getParameter(request, "d_contcID"));
            qCon.setService_rate(FormUtil.getParameter(request, "d_serviceRate"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
            }
        }
        this.getBaseParameters(request, qCon);
        //当操作为克隆时，查询克隆后的草稿版本
        if (command.equals(CommandConstant.COMMAND_CLONE)) {
            qCon.setRecord_flag(ParameterConstant.RECORD_FLAG_DRAFT);
        }

        return qCon;
    }

    private SystemRateContc getQueryCondition(HttpServletRequest request) {
        SystemRateContc qCon = new SystemRateContc();
        this.getBaseParameters(request, qCon);
        return qCon;
    }

    public OperationResult queryByUpdate(HttpServletRequest request, SystemRateContcMapper srcMapper, List lineList, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        this.getQueryConditionForOp(request);
        LogUtil logUtil = new LogUtil();
//        SystemRateContc po = new SystemRateContc();
        SystemRateContc queryCondition;
        List<SystemRateContc> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = srcMapper.getSystemRateContc(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);

        return or;

    }

    public OperationResult queryForOp(HttpServletRequest request, SystemRateContcMapper srcMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        SystemRateContc queryCondition;
        List<SystemRateContc> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request);
            if (queryCondition == null) {
                return null;
            }
            resultSet = srcMapper.getSystemRateContcById(queryCondition);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult modify(HttpServletRequest request, SystemRateContcMapper srcMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        SystemRateContc po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "服务费率：" + "主键：" + po.getContc_id() + ":";
        try {
            n = this.modifyByTrans(request, srcMapper, pvMapper, po);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(SystemRateContc po, SystemRateContcMapper srcMapper, OperationLogMapper opLogMapper) {
        List<SystemRateContc> list = srcMapper.getSystemRateContcById(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private int addByTrans(HttpServletRequest request, SystemRateContcMapper srcMapper, PrmVersionMapper pvMapper, SystemRateContc po) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = srcMapper.addSystemRateContc(po);
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

    private void getVersionNoForSubmit(SystemRateContc po, String versionNoMax) {
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

    public int submitByTrans(HttpServletRequest request, SystemRateContcMapper slrMapper, PrmVersionMapper pvMapper, SystemRateContc po, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        List<PrmVersion> prmVersions;
        String versionNoMax;
        int n = 0;
        int n1 = 0;
        try {

            status = txMgr.getTransaction(def);
            //旧的未来或当前参数数据做删除标志
            slrMapper.submitToOldFlag(po);
            //生成新的未来或当前参数的版本号
            versionNoMax = pvMapper.selectPrmVersionForSubmit(po);
            this.getVersionNoForSubmit(po, versionNoMax);

            //添加新的未来或当前参数的数据记录
            n = slrMapper.submitFromDraftToCurOrFur(po);
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

    private int cloneByTrans(HttpServletRequest request, SystemRateContcMapper srcMapper, PrmVersionMapper pvMapper, SystemRateContc po, PrmVersion prmVersion) throws Exception {
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
            srcMapper.deleteSystemRateContcForClone(po);
            //未来或当前参数克隆成草稿版本
            n = srcMapper.cloneFromCurOrFurToDraft(po);
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

    public OperationResult submit(HttpServletRequest request, SystemRateContcMapper srcMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        SystemRateContc po = this.getReqAttributeForSubmit(request);
        SystemRateContc prmVersion = null;
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {

            n = this.submitByTrans(request, srcMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_SUBMIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_SUBMIT, request, LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()), opLogMapper);

        rmsg.addMessage(LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()));

        return rmsg;
    }

    public OperationResult clone(HttpServletRequest request, SystemRateContcMapper srcMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        SystemRateContc po = this.getReqAttributeForClone(request);
        SystemRateContc prmVersion = null;
        // User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.cloneByTrans(request, srcMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CLONE, e, opLogMapper);
        }
        //当前还是未来？
        logUtil.logOperation(CommandConstant.COMMAND_CLONE, request, LogConstant.cloneSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.cloneSuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(HttpServletRequest request, SystemRateContcMapper srcMapper, PrmVersionMapper pvMapper, SystemRateContc po) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = srcMapper.modifySystemRateContc(po);
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

    private int deleteByTrans(HttpServletRequest request, SystemRateContcMapper srcMapper, PrmVersionMapper pvMapper, Vector<SystemRateContc> pos, SystemRateContc prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (SystemRateContc po : pos) {
                n += srcMapper.deleteSystemRateContc(po);
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

    public OperationResult add(HttpServletRequest request, SystemRateContcMapper srcMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        SystemRateContc po = this.getReqAttribute(request);

        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "服务费率：" + "主键：" + po.getContc_id() + ":";

        try {
            if (this.existRecord(po, srcMapper, opLogMapper)) {
                rmsg.addMessage(preMsg + "记录已存在！");
                return rmsg;
            }

            n = this.addByTrans(request, srcMapper, pvMapper, po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult delete(HttpServletRequest request, SystemRateContcMapper srcMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<SystemRateContc> pos = this.getReqAttributeForDelete(request);
        SystemRateContc prmVo = new SystemRateContc();
        this.getBaseParameters(request, prmVo);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, srcMapper, pvMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public SystemRateContc getReqAttribute(HttpServletRequest request) {
        SystemRateContc po = new SystemRateContc();
        po.setContc_id(FormUtil.getParameter(request, "d_contcID"));
        po.setService_rate(FormUtil.getParameter(request, "d_serviceRate"));
        this.getBaseParameters(request, po);
        //--del po.setVersionNo(version);
        //--del po.setParamTypeID(type);
        return po;
    }

    public SystemRateContc getReqAttributeForSubmit(HttpServletRequest request) {
        SystemRateContc po = new SystemRateContc();

        this.getBaseParameters(request, po);
        this.getBaseParametersForSubmit(request, po);

        return po;
    }

    public SystemRateContc getReqAttributeForClone(HttpServletRequest request) {
        SystemRateContc po = new SystemRateContc();

        this.getBaseParameters(request, po);

        return po;
    }

    private Vector<SystemRateContc> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<SystemRateContc> sds = new Vector();
        SystemRateContc sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getSystemRateContc(strIds, "#");
            sds.add(sd);
        }
        return sds;

    }

    private SystemRateContc getSystemRateContc(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        SystemRateContc sd = new SystemRateContc();
        Vector<SystemRateContc> sds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setContc_id(tmp);
                continue;
            }
            if (i == 2) {
                sd.setService_rate(tmp);
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

    private Vector<SystemRateContc> getReqAttributeForDelete(HttpServletRequest request) {
        SystemRateContc po = new SystemRateContc();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<SystemRateContc> selectedItems = this.getDeleteIDs(selectIds);

        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    private String getSystemRateContcRecordFlagOld(SystemRateContc systemRateContc) {
        String recordFlagSubmit = systemRateContc.getRecord_flag_submit();
        System.out.println("recordFlagSubmit====>" + recordFlagSubmit);
        String recordFlagOld = "";
        if (recordFlagSubmit.equals(ParameterConstant.RECORD_FLAG_CURRENT)) {
            recordFlagOld = ParameterConstant.RECORD_FLAG_HISTORY;
        }
        if (recordFlagSubmit.equals(ParameterConstant.RECORD_FLAG_FUTURE)) {
            recordFlagOld = ParameterConstant.RECORD_FLAG_DELETED;
        }
        return recordFlagOld;
    }

    private String getSystemRateContcVersionValidDate(SystemRateContc systemRateContc) {
        String verDateBegin = systemRateContc.getBegin_time_submit();
        System.out.println("verDateBegin====>" + verDateBegin);
        return this.convertValidDate(verDateBegin);

    }
    
     @RequestMapping("/SystemRateContcExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.prminfo.entity.SystemRateContc");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private List<Map<String, String>> entityToMap(List results) {
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            SystemRateContc vo = (SystemRateContc)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("contc_id_name", vo.getContc_id_name());
            map.put("service_rate", vo.getService_rate());
           
            list.add(map);
        }
        return list;
    }

}
