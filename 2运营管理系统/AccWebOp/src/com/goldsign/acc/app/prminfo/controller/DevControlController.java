package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.DevControl;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.app.prminfo.mapper.PrmVersionMapper;
import com.goldsign.acc.app.prminfo.mapper.DevControlMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.constant.ParameterConstant;

import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.mapper.PubFlagMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;

import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 运营资料--设备控制--设备控制
 * @author xiaowu
 * @version 20170614
 */
@Controller
public class DevControlController extends PrmBaseController {

    @Autowired
    private DevControlMapper devControlMapper;

    @Autowired
    private PubFlagMapper pubFlagMapper;

    @RequestMapping("/DevControl")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/dev_control.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_LIST))//默认显示
                {
                    opResult = this.query(request, this.devControlMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.devControlMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_SUBMIT))//提交操作
                {
                    opResult = this.submit(request, this.devControlMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_CLONE))//克隆操作
                {
                    opResult = this.clone(request, this.devControlMapper, this.prmVersionMapper, this.operationLogMapper);
                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_LIST) ||command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT)
                        || command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) 
                        || command.equals(CommandConstant.COMMAND_BACK) || command.equals(CommandConstant.COMMAND_BACKEND)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.devControlMapper, this.operationLogMapper, opResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //初始化下拉框
        String[] attrNames = {LINES, STATIONS, LINE_STATIONS, DEV_TYPES, MERCHANTS, VERSION};

        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.getResultSetText((List<DevControl>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        //克隆不回显列表内容
//        PageControlUtil pUtil = new PageControlUtil();  
//        pUtil.seperateResults(request, mv, opResult);
        this.divideResultSet(request, mv, opResult);//结果集分页
        //缓存用于导出全部
        PageControlUtil pageUtil = new PageControlUtil();
        pageUtil.putBuffer(request, opResult.getReturnResultSet());
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }
    
    public OperationResult query(HttpServletRequest request, DevControlMapper devControlMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        DevControl queryCondition = new DevControl();
        List<DevControl> resultSet;

        try {
            this.getBaseParameters(request, queryCondition);
            resultSet = devControlMapper.getDevControls(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }
    
    @RequestMapping("/DevControlExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
	List results = this.getBufferElements(request);
	String expAllFields = request.getParameter("expAllFields");
	List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields, results);
	ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
    
    private void getResultSetText(List<DevControl> resultSet, ModelAndView mv) {
        
    }
    
    private DevControl getQueryConditionForOp(HttpServletRequest request) {

        DevControl devControl = new DevControl();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues"); 
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            devControl.setLogoff_idle_sc(FormUtil.getParameter(request, "d_logoff_idle_sc"));
            devControl.setInterval_connectsc(FormUtil.getParameter(request, "d_interval_connectsc"));
            devControl.setDegrade_time(FormUtil.getParameter(request, "d_degrade_time"));
            devControl.setLogoff_idle_bom(FormUtil.getParameter(request, "d_logoff_idle_bom"));
            devControl.setTime_out_pass(FormUtil.getParameter(request, "d_time_out_pass"));
            devControl.setUnder_sjt(FormUtil.getParameter(request, "d_under_sjt"));
            devControl.setUnder_amount(FormUtil.getParameter(request, "d_under_amount"));
            devControl.setUnder_count(FormUtil.getParameter(request, "d_under_count"));
            devControl.setUpper_sjt(FormUtil.getParameter(request, "d_upper_sjt"));
            devControl.setUpper_amount(FormUtil.getParameter(request, "d_upper_amount"));
            devControl.setUpper_count(FormUtil.getParameter(request, "d_upper_count"));
        } 
        this.getBaseParameters(request, devControl);
        //当操作为克隆时，查询克隆后的草稿版本
//        if (command.equals(CommandConstant.COMMAND_CLONE)) {
//            devControl.setRecord_flag(ParameterConstant.RECORD_FLAG_DRAFT);
//        }

        return devControl;
    }

    /**
     * 带查询条件查询记录
     * @param request
     * @param devControlMapper
     * @param opLogMapper
     * @param opResult
     * @return
     * @throws Exception 
     */
    public OperationResult queryForOp(HttpServletRequest request, DevControlMapper devControlMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        DevControl devControl;
        List<DevControl> resultSet;

        try {
            devControl = this.getQueryConditionForOp(request);
//            if (devControl == null) {
//                return null;
//            }
            resultSet = devControlMapper.getDevControls(devControl);
            String command = request.getParameter("command");
            //提交 和 克隆
            if(command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT)){
                PageControlUtil pageControlUtil = new PageControlUtil();
                pageControlUtil.putBuffer(request, resultSet);
            }
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;
    }

    public OperationResult modify(HttpServletRequest request, DevControlMapper devControlMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        DevControl devControl = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
       
        try {
            n = this.modifyByTrans(request, devControlMapper, pvMapper, devControl);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(n), opLogMapper);
        
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private int addByTrans(HttpServletRequest request, DevControlMapper devControlMapper, PrmVersionMapper pvMapper, DevControl devControl) throws Exception {
//        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {
//            txMgr = DBUtil.getDataSourceTransactionManager(request);
//            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            status = txMgr.getTransaction(this.def);
            n = devControlMapper.addDevControl(devControl);
            pvMapper.modifyPrmVersionForDraft(devControl);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private void getVersionNoForSubmit(DevControl po, String versionNoMax) {
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

    

    public int submitByTrans(HttpServletRequest request, DevControlMapper devControlMapper, PrmVersionMapper pvMapper, DevControl devControl, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        String versionNoMax;
        int n = 0;
        try {

            status = txMgr.getTransaction(def);

            //旧的未来或当前参数数据做删除标志
            devControlMapper.submitToOldFlag(devControl);
            //生成新的未来或当前参数的版本号
            versionNoMax = pvMapper.selectPrmVersionForSubmit(devControl);
            this.getVersionNoForSubmit(devControl, versionNoMax);

            //添加新的未来或当前参数的数据记录
            n = devControlMapper.submitFromDraftToCurOrFur(devControl);
            // 重新生成参数表中的未来或当前参数记录
            pvMapper.modifyPrmVersionForSubmit(devControl);
            pvMapper.addPrmVersion(devControl);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    

    private int cloneByTrans(HttpServletRequest request, DevControlMapper devControlMapper, PrmVersionMapper pvMapper, DevControl devControl, PrmVersion prmVersion) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);

            //删除草稿版本
            devControlMapper.deleteDevControlForClone(devControl);
            //未来或当前参数克隆成草稿版本
            n = devControlMapper.cloneFromCurOrFurToDraft(devControl);
            //更新参数版本索引信息
            pvMapper.modifyPrmVersionForDraft(devControl);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult submit(HttpServletRequest request, DevControlMapper devControlMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        DevControl po = this.getReqAttributeForSubmit(request);
        PrmVersion prmVersion = null;
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.submitByTrans(request, devControlMapper, pvMapper, po, prmVersion);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_SUBMIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_SUBMIT, request, LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()), opLogMapper);

        rmsg.addMessage(LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()));

        return rmsg;
    }

    public OperationResult clone(HttpServletRequest request, DevControlMapper devControlMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        DevControl po = this.getReqAttributeForClone(request);
        PrmVersion prmVersion = null;
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.cloneByTrans(request, devControlMapper, pvMapper, po, prmVersion);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CLONE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_CLONE, request, LogConstant.cloneSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.cloneSuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(HttpServletRequest request, DevControlMapper devControlMapper, PrmVersionMapper pvMapper, DevControl devControl) throws Exception {
//        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {
//            txMgr = DBUtil.getDataSourceTransactionManager(request);
//            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            status = txMgr.getTransaction(this.def);
            n = devControlMapper.modifyDevControl(devControl);
            pvMapper.modifyPrmVersionForDraft(devControl);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private int deleteByTrans(HttpServletRequest request, DevControlMapper devControlMapper, PrmVersionMapper pvMapper, Vector<DevControl> devControls, DevControl devControl) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (DevControl po : devControls) {
                n += devControlMapper.deleteDevControl(po);
            }
            pvMapper.modifyPrmVersionForDraft(devControl);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult add(HttpServletRequest request, DevControlMapper devControlMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        DevControl devControl = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        List<DevControl> devControls = devControlMapper.getDevControls(devControl);
        if (devControls != null && devControls.size() > 0) {
            rmsg.addMessage("记录已存在");   // 线路ID + 车站ID + 版本号 + 版本标志   组合判断记录是否已存在
            return rmsg;
        }
        
        try {
            n = this.addByTrans(request, devControlMapper, pvMapper, devControl);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, LogConstant.addSuccessMsg(n), opLogMapper);
        
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult delete(HttpServletRequest request, DevControlMapper devControlMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<DevControl> devControls = this.getReqAttributeForDelete(request);
        DevControl devControl = new DevControl();
        this.getBaseParameters(request, devControl);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, devControlMapper, pvMapper, devControls, devControl);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public DevControl getReqAttribute(HttpServletRequest request) {
        DevControl devControl = new DevControl();

        devControl.setLogoff_idle_sc(FormUtil.getParameter(request, "d_logoff_idle_sc"));
        devControl.setInterval_connectsc(FormUtil.getParameter(request, "d_interval_connectsc"));
        devControl.setDegrade_time(FormUtil.getParameter(request, "d_degrade_time"));
        devControl.setLogoff_idle_bom(FormUtil.getParameter(request, "d_logoff_idle_bom"));
        devControl.setTime_out_pass(FormUtil.getParameter(request, "d_time_out_pass"));
        devControl.setUnder_sjt(FormUtil.getParameter(request, "d_under_sjt"));
        devControl.setUnder_amount(FormUtil.getParameter(request, "d_under_amount"));
        devControl.setUnder_count(FormUtil.getParameter(request, "d_under_count"));
        devControl.setUpper_sjt(FormUtil.getParameter(request, "d_upper_sjt"));
        devControl.setUpper_amount(FormUtil.getParameter(request, "d_upper_amount"));
        devControl.setUpper_count(FormUtil.getParameter(request, "d_upper_count"));
        
        getBaseParameters(request, devControl);
        
        return devControl;
    }

    public DevControl getReqAttributeForSubmit(HttpServletRequest request) {
        DevControl sc = new DevControl();

        getBaseParameters(request, sc);
        getBaseParametersForSubmit(request, sc);
        
        return sc;
        
    }

    public DevControl getReqAttributeForClone(HttpServletRequest request) {
        DevControl devControl = new DevControl();

        this.getBaseParameters(request, devControl);

        return devControl;
    }

    private Vector<DevControl> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<DevControl> devControls = new Vector();
        DevControl sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getDevControl(strIds, "#");
            devControls.add(sd);
        }
        return devControls;

    }

    private DevControl getDevControl(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        DevControl devControl = new DevControl();;
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                devControl.setVersion_no(tmp);
                continue;
            }
        }
        return devControl;

    }

    private Vector<DevControl> getReqAttributeForDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<DevControl> selectedItems = this.getDeleteIDs(selectIds);

        return selectedItems;
    }
    
    private String getRecordFlagOld( DevControl devControl){
         String recordFlagSubmit = devControl.getRecord_flag_submit();
         String recordFlagOld ="";
        if (recordFlagSubmit.equals(ParameterConstant.RECORD_FLAG_CURRENT)) {
            recordFlagOld = ParameterConstant.RECORD_FLAG_HISTORY;
        }
        if (recordFlagSubmit.equals(ParameterConstant.RECORD_FLAG_FUTURE)) {
            recordFlagOld = ParameterConstant.RECORD_FLAG_DELETED;
        }
        return recordFlagOld;
    }
    
    private String getVersionValidDate(DevControl devControl){
	String verDateBegin =devControl.getBegin_time_submit();
	return this.convertValidDate(verDateBegin);
    }

    public String convertValidDate(String validDate) {
        if (validDate == null || validDate.trim().length() == 0 || validDate.trim().length() != 10) {
                return validDate;
        }
        int index = validDate.indexOf("-");
        int index1 = validDate.indexOf("-", index + 1);
        return validDate.substring(0, index) + validDate.substring(index + 1, index1) + validDate.substring(index1 + 1);
    }
}
