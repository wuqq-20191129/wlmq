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
import com.goldsign.acc.app.prminfo.mapper.SystemRateLineMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.constant.ParameterConstant;
import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.CharUtil;
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
public class SystemRateLineController extends PrmBaseController {

    @Autowired
    private SystemRateLineMapper systemRateLineMapper;

    @RequestMapping("/SystemRateLine")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/system_rate_line.jsp");
        SystemRateLine pmb = new SystemRateLine();
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        List<SystemRateLine> systemRateList = new ArrayList<SystemRateLine>();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.systemRateLineMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.systemRateLineMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.systemRateLineMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_SUBMIT))//提交操作
                {
                    opResult = this.submit(request, this.systemRateLineMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_CLONE))//克隆操作
                {
                    opResult = this.clone(request, this.systemRateLineMapper, this.prmVersionMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_QUERY) || command.equals(CommandConstant.COMMAND_LIST)) {
                    opResult = queryByUpdate(request, this.systemRateLineMapper, systemRateList,
                            this.operationLogMapper);
                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.systemRateLineMapper, this.operationLogMapper, opResult);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {LINES, STATIONS, LINE_STATIONS, DEV_TYPES, MERCHANTS, VERSION};

        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.getResultSetText((List<SystemRateLine>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);
        return mv;

    }

    private void getResultSetText(List<SystemRateLine> resultSet, ModelAndView mv) {
        List<PubFlag> lines = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.LINES);
        for (SystemRateLine sd : resultSet) {
            if (lines != null && !lines.isEmpty()) {
                sd.setLine_id_name(DBUtil.getTextByCode(sd.getLine_id(), lines));
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

    private SystemRateLine getQueryConditionForOp(HttpServletRequest request) {

        SystemRateLine qCon = new SystemRateLine();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
//            if (command.equals(CommandConstant.COMMAND_DELETE)) {
//                return null;
//            }
            qCon.setLine_id(FormUtil.getParameter(request, "d_lineID"));
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

    private SystemRateLine getQueryCondition(HttpServletRequest request) {
        SystemRateLine qCon = new SystemRateLine();

        this.getBaseParameters(request, qCon);
        return qCon;
    }

    public OperationResult queryByUpdate(HttpServletRequest request, SystemRateLineMapper srlMapper, List lineList, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        this.getQueryConditionForOp(request);
        LogUtil logUtil = new LogUtil();
//        SystemRateLine po = new SystemRateLine();
        SystemRateLine queryCondition;
        List<SystemRateLine> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = srlMapper.getSystemRateLine(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);

        return or;

    }

    public OperationResult queryForOp(HttpServletRequest request, SystemRateLineMapper srlMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        // OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        SystemRateLine queryCondition;
        List<SystemRateLine> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request);
            if (queryCondition == null) {
                return null;
            }
            resultSet = srlMapper.getSystemRateLineById(queryCondition);
            opResult.setReturnResultSet(resultSet);
            //or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult modify(HttpServletRequest request, SystemRateLineMapper srlMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        SystemRateLine po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "服务费率：";
        try {
            n = this.modifyByTrans(request, srlMapper, pvMapper, po);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(SystemRateLine po, SystemRateLineMapper srlMapper, OperationLogMapper opLogMapper) {
        List<SystemRateLine> list = srlMapper.getSystemRateLineById(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private int addByTrans(HttpServletRequest request, SystemRateLineMapper srlMapper, PrmVersionMapper pvMapper, SystemRateLine po) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = srlMapper.addSystemRateLine(po);
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

    private void getVersionNoForSubmit(SystemRateLine po, String versionNoMax) {
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

    public int submitByTrans(HttpServletRequest request, SystemRateLineMapper slrMapper, PrmVersionMapper pvMapper, SystemRateLine po, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        List<PrmVersion> prmVersions;
        String versionNoMax;
        int n = 0;
        int n1 = 0;
        // String test=null;
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

    private int cloneByTrans(HttpServletRequest request, SystemRateLineMapper srlMapper, PrmVersionMapper pvMapper, SystemRateLine po, PrmVersion prmVersion) throws Exception {
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
            srlMapper.deleteSystemRateLineForClone(po);
            //未来或当前参数克隆成草稿版本
            n = srlMapper.cloneFromCurOrFurToDraft(po);
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

    public OperationResult submit(HttpServletRequest request, SystemRateLineMapper srlMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        SystemRateLine po = this.getReqAttributeForSubmit(request);
        SystemRateLine prmVersion = null;
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {

            n = this.submitByTrans(request, srlMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_SUBMIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_SUBMIT, request, LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()), opLogMapper);

        rmsg.addMessage(LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()));

        return rmsg;
    }

    public OperationResult clone(HttpServletRequest request, SystemRateLineMapper srlMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        SystemRateLine po = this.getReqAttributeForClone(request);
        SystemRateLine prmVersion = null;
        // User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.cloneByTrans(request, srlMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CLONE, e, opLogMapper);
        }
        //当前还是未来？
        logUtil.logOperation(CommandConstant.COMMAND_CLONE, request, LogConstant.cloneSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.cloneSuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(HttpServletRequest request, SystemRateLineMapper srlMapper, PrmVersionMapper pvMapper, SystemRateLine po) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = srlMapper.modifySystemRateLine(po);
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

    private int deleteByTrans(HttpServletRequest request, SystemRateLineMapper srlMapper, PrmVersionMapper pvMapper, Vector<SystemRateLine> pos, SystemRateLine prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (SystemRateLine po : pos) {
                n += srlMapper.deleteSystemRateLine(po);
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

    public OperationResult add(HttpServletRequest request, SystemRateLineMapper srlMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        SystemRateLine po = this.getReqAttribute(request);
        // User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "服务费率：";
//        if (CharUtil.getDBLenth(po.getDev_name()) > 30) {
//            rmsg.addMessage(preMsg + "车站设备名称最大值不能超过30位(中文字符为两位)。");
//            return rmsg;
//        } else {
        try {
            if (this.existRecord(po, srlMapper, opLogMapper)) {
                rmsg.addMessage(preMsg + "记录已存在！");
                return rmsg;
            }

            n = this.addByTrans(request, srlMapper, pvMapper, po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);
//        }
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult delete(HttpServletRequest request, SystemRateLineMapper srlMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<SystemRateLine> pos = this.getReqAttributeForDelete(request);
        SystemRateLine prmVo = new SystemRateLine();
        this.getBaseParameters(request, prmVo);
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, srlMapper, pvMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public SystemRateLine getReqAttribute(HttpServletRequest request) {
        SystemRateLine po = new SystemRateLine();
        po.setLine_id(FormUtil.getParameter(request, "d_lineID"));
        po.setService_rate(FormUtil.getParameter(request, "d_serviceRate"));
        this.getBaseParameters(request, po);
        //--del po.setVersionNo(version);
        //--del po.setParamTypeID(type);
        return po;
    }

    public SystemRateLine getReqAttributeForSubmit(HttpServletRequest request) {
        SystemRateLine po = new SystemRateLine();

        this.getBaseParameters(request, po);
        this.getBaseParametersForSubmit(request, po);

        return po;
    }

    public SystemRateLine getReqAttributeForClone(HttpServletRequest request) {
        SystemRateLine po = new SystemRateLine();

        this.getBaseParameters(request, po);

        return po;
    }

    private Vector<SystemRateLine> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<SystemRateLine> sds = new Vector();
        SystemRateLine sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getSystemRateLine(strIds, "#");
            sds.add(sd);
        }
        return sds;

    }

    private SystemRateLine getSystemRateLine(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        SystemRateLine sd = new SystemRateLine();
        Vector<SystemRateLine> sds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setLine_id(tmp);
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

    private Vector<SystemRateLine> getReqAttributeForDelete(HttpServletRequest request) {
        SystemRateLine po = new SystemRateLine();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<SystemRateLine> selectedItems = this.getDeleteIDs(selectIds);

        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    private String getSystemRateLineRecordFlagOld(SystemRateLine systemRateLine) {
        String recordFlagSubmit = systemRateLine.getRecord_flag_submit();
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

    private String getSystemRateLineVersionValidDate(SystemRateLine systemRateLine) {
        String verDateBegin = systemRateLine.getBegin_time_submit();
        System.out.println("verDateBegin====>" + verDateBegin);
        return this.convertValidDate(verDateBegin);

    }
    
    @RequestMapping("/SystemRateLineExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.prminfo.entity.SystemRateLine");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private List<Map<String, String>> entityToMap(List results) {
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            SystemRateLine vo = (SystemRateLine)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("line_id_name", vo.getLine_id_name());
            map.put("service_rate", vo.getService_rate());
           
            list.add(map);
        }
        return list;
    }
}
