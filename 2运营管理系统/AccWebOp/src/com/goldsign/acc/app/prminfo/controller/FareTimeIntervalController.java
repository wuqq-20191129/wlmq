/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.CardMain;
import com.goldsign.acc.app.prminfo.entity.FareTimeInterval;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.app.prminfo.mapper.FareTimeIntervalMapper;
import com.goldsign.acc.app.prminfo.mapper.PrmVersionMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;

import com.goldsign.acc.frame.controller.PrmBaseController;

import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.ExpUtil;

import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;

import java.io.UnsupportedEncodingException;
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
 * 联乘时间间隔
 *
 * @author luck
 */
@Controller
public class FareTimeIntervalController extends PrmBaseController {

    @Autowired
    private FareTimeIntervalMapper fareTimeIntervalMapper;

    @RequestMapping("/FareTimeInterval")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/fare_time_interval.jsp");
        request.getSession().removeAttribute("queryCondition");
        request.getSession().setAttribute("versionNo", request.getParameter("Version"));
        request.getSession().setAttribute("recordFlag", request.getParameter("VersionType"));
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.fareTimeIntervalMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.fareTimeIntervalMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.fareTimeIntervalMapper, this.prmVersionMapper, this.operationLogMapper);
                }

                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {

                    opResult = this.query(request, this.fareTimeIntervalMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_SUBMIT))//提交操作
                {
                    opResult = this.submit(request, this.fareTimeIntervalMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_CLONE))//克隆操作
                {
                    opResult = this.clone(request, this.fareTimeIntervalMapper, this.prmVersionMapper, this.operationLogMapper);

                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT) || command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                        || command.equals(CommandConstant.COMMAND_BACKEND)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.fareTimeIntervalMapper, this.operationLogMapper, opResult);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {VERSION};
        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);
        return mv;

    }

    private FareTimeInterval getQueryConditionForOp(HttpServletRequest request) throws UnsupportedEncodingException {

        FareTimeInterval qCon = new FareTimeInterval();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                this.getBaseParameters(request, qCon);
                return qCon;
            }

            qCon.setTimeId(FormUtil.getParameter(request, "d_timeId"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setTimeId(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "d_timeId"));
            }
        }
        this.getBaseParameters(request, qCon);
//        //当操作为克隆时，查询克隆后的草稿版本
//        if (command.equals(CommandConstant.COMMAND_CLONE)) {
//            qCon.setRecord_flag(ParameterConstant.RECORD_FLAG_DRAFT);
//        }

        return qCon;
    }

    private FareTimeInterval getQueryCondition(HttpServletRequest request) throws UnsupportedEncodingException {
        FareTimeInterval qCon = new FareTimeInterval();
        qCon.setTimeId(FormUtil.getParameter(request, "q_time_id"));
        this.getBaseParameters(request, qCon);
        return qCon;
    }

    public OperationResult query(HttpServletRequest request, FareTimeIntervalMapper ftimMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        FareTimeInterval queryCondition;
        List<FareTimeInterval> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = ftimMapper.query(queryCondition);
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

    public OperationResult queryForOp(HttpServletRequest request, FareTimeIntervalMapper ftimMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        // OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        FareTimeInterval queryCondition;
        List<FareTimeInterval> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request);
            if (queryCondition == null) {
                return null;
            }
            resultSet = ftimMapper.query(queryCondition);
            opResult.setReturnResultSet(resultSet);
            //or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult modify(HttpServletRequest request, FareTimeIntervalMapper ftimMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        FareTimeInterval po = this.getReqAttribute(request);
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "联乘时间间隔定义表：";

        try {
           
            n = this.modifyByTrans(request, ftimMapper, pvMapper, po);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(FareTimeInterval po, FareTimeIntervalMapper ftimMapper, OperationLogMapper opLogMapper) {
        List<FareTimeInterval> list = ftimMapper.query(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    


    private int addByTrans(HttpServletRequest request, FareTimeIntervalMapper ftimMapper, PrmVersionMapper pvMapper, FareTimeInterval po) throws Exception {
//        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {

//            txMgr = DBUtil.getDataSourceTransactionManager(request);
//            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            status = txMgr.getTransaction(this.def);
            n = ftimMapper.add(po);
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

    private void getVersionNoForSubmit(FareTimeInterval po, String versionNoMax) {
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

    public int submitByTrans(HttpServletRequest request, FareTimeIntervalMapper ftimMapper, PrmVersionMapper pvMapper, FareTimeInterval po, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        List<PrmVersion> prmVersions;
        String versionNoMax;
        int n = 0;
        int n1 = 0;
        // String test=null;
        try {

            status = txMgr.getTransaction(def);

            //旧的或未来或当前参数数据做删除标志
            ftimMapper.submitToOldFlag(po);
            //生成新的未来或当前参数的版本号

            versionNoMax = pvMapper.selectPrmVersionForSubmit(po);

            this.getVersionNoForSubmit(po, versionNoMax);

            //添加新的未来或当前参数的数据记录
            n = ftimMapper.submitFromDraftToCurOrFur(po);
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

    private int cloneByTrans(HttpServletRequest request, FareTimeIntervalMapper ftimMapper, PrmVersionMapper pvMapper, FareTimeInterval po, PrmVersion prmVersion) throws Exception {
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
            ftimMapper.deleteForClone(po);
            //未来或当前参数克隆成草稿版本
            n = ftimMapper.cloneFromCurOrFurToDraft(po);
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

    public OperationResult submit(HttpServletRequest request, FareTimeIntervalMapper ftimMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        FareTimeInterval po = this.getReqAttributeForSubmit(request);
        FareTimeInterval prmVersion = null;
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.submitByTrans(request, ftimMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_SUBMIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_SUBMIT, request, LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()), opLogMapper);

        rmsg.addMessage(LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()));

        return rmsg;
    }

    public OperationResult clone(HttpServletRequest request, FareTimeIntervalMapper ftimMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        FareTimeInterval po = this.getReqAttributeForClone(request);
        CardMain prmVersion = null;
        // User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.cloneByTrans(request, ftimMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CLONE, e, opLogMapper);
        }
        //当前还是未来？
        logUtil.logOperation(CommandConstant.COMMAND_CLONE, request, LogConstant.cloneSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.cloneSuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(HttpServletRequest request, FareTimeIntervalMapper ftimMapper, PrmVersionMapper pvMapper, FareTimeInterval po) throws Exception {
//        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {

//            txMgr = DBUtil.getDataSourceTransactionManager(request);
//            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            status = txMgr.getTransaction(this.def);
            n = ftimMapper.modify(po);
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

    private int deleteByTrans(HttpServletRequest request, FareTimeIntervalMapper ftimMapper, PrmVersionMapper pvMapper, Vector<FareTimeInterval> pos, FareTimeInterval prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            for (FareTimeInterval po : pos) {
                n += ftimMapper.delete(po);
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

    public OperationResult add(HttpServletRequest request, FareTimeIntervalMapper ftimMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        FareTimeInterval po = this.getReqAttribute(request);
        // User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "联乘时间间隔定义表：";
        try {
            if (this.existRecord(po, ftimMapper, opLogMapper)) {
                rmsg.addMessage(preMsg + "联乘时间间隔ID已存在！");
                return rmsg;
            }

            // n = sdMapper.addStationDevice(po);
            n = this.addByTrans(request, ftimMapper, pvMapper, po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult delete(HttpServletRequest request, FareTimeIntervalMapper ftimMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<FareTimeInterval> pos = this.getReqAttributeForDelete(request);
        FareTimeInterval prmVo = new FareTimeInterval();
        this.getBaseParameters(request, prmVo);
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {

            n = this.deleteByTrans(request, ftimMapper, pvMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public FareTimeInterval getReqAttribute(HttpServletRequest request) throws UnsupportedEncodingException {
        FareTimeInterval po = new FareTimeInterval();
        po.setTimeId(FormUtil.getParameter(request, "d_timeId"));
        po.setTimeMax(FormUtil.getParameter(request, "d_timeMax"));
        po.setTimeMin(FormUtil.getParameter(request, "d_timeMin"));
        po.setRemark(FormUtil.getParameter(request, "d_remarks"));

        this.getBaseParameters(request, po);
        //--del po.setVersionNo(version);
        //--del po.setParamTypeID(type);
        return po;
    }

    public FareTimeInterval getReqAttributeForSubmit(HttpServletRequest request) {
        FareTimeInterval po = new FareTimeInterval();

        this.getBaseParameters(request, po);
        this.getBaseParametersForSubmit(request, po);
        return po;
    }

    public FareTimeInterval getReqAttributeForClone(HttpServletRequest request) {
        FareTimeInterval po = new FareTimeInterval();

        this.getBaseParameters(request, po);
        return po;
    }

    private Vector<FareTimeInterval> getDeleteIDs(String selectedIds) throws UnsupportedEncodingException {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<FareTimeInterval> sds = new Vector();
        FareTimeInterval sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getCardMain(strIds, "#");
            sds.add(sd);
        }
        return sds;

    }

    private FareTimeInterval getCardMain(String strIDs, String delim) throws UnsupportedEncodingException {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        FareTimeInterval sd = new FareTimeInterval();;
        Vector<FareTimeInterval> sds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setTimeId(tmp);
                continue;
            }
            if (i == 2) {
                sd.setVersion_no(tmp);
                continue;
            }
            if (i == 3) {
                sd.setRecord_flag(tmp);
                continue;
            }

        }
        return sd;

    }

    private Vector<FareTimeInterval> getReqAttributeForDelete(HttpServletRequest request) throws UnsupportedEncodingException {
        FareTimeInterval po = new FareTimeInterval();
        String selectIds = request.getParameter("allSelectedIDs");

        Vector<FareTimeInterval> selectedItems = this.getDeleteIDs(selectIds);

        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    @RequestMapping("/FareTimeIntervalExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        FareTimeInterval queryCondition = new FareTimeInterval();
        if (request.getSession().getAttribute("queryCondition") != null) {
            Object obj = request.getSession().getAttribute("queryCondition");
            if (obj instanceof FareTimeInterval) {
                queryCondition = (FareTimeInterval) request.getSession().getAttribute("queryCondition");
            }
        } else {
            queryCondition.setVersion_no((String) request.getSession().getAttribute("versionNo"));
            queryCondition.setRecord_flag((String) request.getSession().getAttribute("recordFlag"));
        }
        List<Map> queryResults = fareTimeIntervalMapper.queryToMap(queryCondition);

        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

}
