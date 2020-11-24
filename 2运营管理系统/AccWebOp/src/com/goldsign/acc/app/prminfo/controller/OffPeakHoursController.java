/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.OffPeakHours;
import com.goldsign.acc.app.prminfo.mapper.OffPeakHoursMapper;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.app.prminfo.mapper.PrmVersionMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.constant.ParameterConstant;

import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
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
 * @author mqf
 */
@Controller
public class OffPeakHoursController extends PrmBaseController {

    @Autowired
    private OffPeakHoursMapper offPeakHoursMapper;


    @RequestMapping("/OffPeakHours")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/off_peak_hours.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.offPeakHoursMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.offPeakHoursMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.offPeakHoursMapper, this.prmVersionMapper, this.operationLogMapper);
                }

//                if (command.equals(CommandConstant.COMMAND_QUERY) || command.equals(CommandConstant.COMMAND_LIST))//查询操作  list：直接查询结果
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作  list：直接查询结果    
                {
                    opResult = this.query(request, this.offPeakHoursMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_SUBMIT))//提交操作
                {
//                    opResult = this.submit(request, this.offPeakHoursMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_CLONE))//克隆操作
                {
                    opResult = this.clone(request, this.offPeakHoursMapper, this.prmVersionMapper, this.operationLogMapper);

                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.offPeakHoursMapper, this.operationLogMapper, opResult);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {DAY_OF_WEEKS, VERSION};

        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.getResultSetText((List<OffPeakHours>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }

    private void getResultSetText(List<OffPeakHours> resultSet, ModelAndView mv) {
        List<PubFlag> dayOfWeeks = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.DAY_OF_WEEKS);

        for (OffPeakHours oph : resultSet) {
            if (dayOfWeeks != null && !dayOfWeeks.isEmpty()) {
                oph.setDay_of_week_desc(DBUtil.getTextByCode(oph.getDay_of_week(), dayOfWeeks));
            }

        }

    }

    private OffPeakHours getQueryConditionForOp(HttpServletRequest request) {

        OffPeakHours qCon = new OffPeakHours();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            
            qCon.setDay_of_week(FormUtil.getParameter(request, "d_day_of_week"));  //week_type
            qCon.setStart_time(FormUtil.getParameter(request, "d_start_time"));  //sdate
            qCon.setEnd_time(FormUtil.getParameter(request, "d_end_time"));  //edate
            
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                
                qCon.setDay_of_week(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_day_of_week"));  //week_type
                qCon.setStart_time(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_start_time"));  //sdate
                qCon.setEnd_time(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_end_time"));  //edate
            }
        }
        this.getBaseParameters(request, qCon);
        //当操作为克隆时，查询克隆后的草稿版本
//        if (command.equals(CommandConstant.COMMAND_CLONE)) {
//            qCon.setRecord_flag(ParameterConstant.RECORD_FLAG_DRAFT);
//        }

        return qCon;
    }

    private OffPeakHours getQueryCondition(HttpServletRequest request) {
        OffPeakHours qCon = new OffPeakHours();
        
        qCon.setDay_of_week(FormUtil.getParameter(request, "q_day_of_week"));  //week_type
        qCon.setStart_time(FormUtil.getParameter(request, "q_start_time"));  //sdate
        qCon.setEnd_time(FormUtil.getParameter(request, "q_end_time"));  //edate

        this.getBaseParameters(request, qCon);
        return qCon;
    }

    public OperationResult query(HttpServletRequest request, OffPeakHoursMapper ophMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        OffPeakHours queryCondition;
        List<OffPeakHours> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = ophMapper.getOffPeakHourss(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult queryForOp(HttpServletRequest request, OffPeakHoursMapper ophMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        // OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        OffPeakHours queryCondition;
        List<OffPeakHours> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request);
            if (queryCondition == null) {
                return null;
            }
            resultSet = ophMapper.getOffPeakHourss(queryCondition);
            opResult.setReturnResultSet(resultSet);
            //or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult modify(HttpServletRequest request, OffPeakHoursMapper ophMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        OffPeakHours po = this.getReqAttribute(request, CommandConstant.COMMAND_MODIFY);
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "非繁忙时间：" + "主键：" + po.getDay_of_week()+"#"+po.getStart_time()+"#"+po.getEnd_time() + ":";
        try {
            if (this.existRecord(po, ophMapper, opLogMapper)) {
                rmsg.addMessage("当天中已存在重复的时间段！");
                return rmsg;
            }
            n = this.modifyByTrans(request, ophMapper, pvMapper, po);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(OffPeakHours po, OffPeakHoursMapper ophMapper, OperationLogMapper opLogMapper) {
        List<OffPeakHours> list = ophMapper.getOffPeakHoursById(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private int addByTrans(HttpServletRequest request, OffPeakHoursMapper ophMapper, PrmVersionMapper pvMapper, OffPeakHours po) throws Exception {
//        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {

//            txMgr = DBUtil.getDataSourceTransactionManager(request);
//            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            status = txMgr.getTransaction(this.def);
            n = ophMapper.addOffPeakHours(po);
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

    private void getVersionNoForSubmit(OffPeakHours po, String versionNoMax) {
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

    

    public int submitByTrans(HttpServletRequest request, OffPeakHoursMapper ophMapper, PrmVersionMapper pvMapper, OffPeakHours po, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        List<PrmVersion> prmVersions;
        String versionNoMax;
        int n = 0;
        int n1 = 0;
       // String test=null;
        try {

            status = txMgr.getTransaction(def);
            

            //旧的未来或当前参数数据做删除标志
            ophMapper.submitToOldFlag(po);
            //生成新的未来或当前参数的版本号
            versionNoMax = pvMapper.selectPrmVersionForSubmit(po);
            this.getVersionNoForSubmit(po, versionNoMax);

            //添加新的未来或当前参数的数据记录
            n = ophMapper.submitFromDraftToCurOrFur(po);
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

    

    private int cloneByTrans(HttpServletRequest request, OffPeakHoursMapper ophMapper, PrmVersionMapper pvMapper, OffPeakHours po, PrmVersion prmVersion) throws Exception {
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
            ophMapper.deleteOffPeakHourssForClone(po);
            //未来或当前参数克隆成草稿版本
            n = ophMapper.cloneFromCurOrFurToDraft(po);
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

    public OperationResult submit(HttpServletRequest request, OffPeakHoursMapper ophMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        OffPeakHours po = this.getReqAttributeForSubmit(request);
        OffPeakHours prmVersion = null;
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.submitByTrans(request, ophMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_SUBMIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_SUBMIT, request, LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()), opLogMapper);

        rmsg.addMessage(LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()));

        return rmsg;
    }

    public OperationResult clone(HttpServletRequest request, OffPeakHoursMapper ophMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        OffPeakHours po = this.getReqAttributeForClone(request);
        OffPeakHours prmVersion = null;
       // User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.cloneByTrans(request, ophMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CLONE, e, opLogMapper);
        }
        //当前还是未来？
        logUtil.logOperation(CommandConstant.COMMAND_CLONE, request, LogConstant.cloneSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.cloneSuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(HttpServletRequest request, OffPeakHoursMapper ophMapper, PrmVersionMapper pvMapper, OffPeakHours po) throws Exception {
//        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {

//            txMgr = DBUtil.getDataSourceTransactionManager(request);
//            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            status = txMgr.getTransaction(this.def);
            n = ophMapper.modifyOffPeakHours(po);
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

    private int deleteByTrans(HttpServletRequest request, OffPeakHoursMapper ophMapper, PrmVersionMapper pvMapper, Vector<OffPeakHours> pos, OffPeakHours prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {


            status = txMgr.getTransaction(this.def);
            for (OffPeakHours po : pos) {
                n += ophMapper.deleteOffPeakHours(po);
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

    public OperationResult add(HttpServletRequest request, OffPeakHoursMapper ophMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        OffPeakHours po = this.getReqAttribute(request, CommandConstant.COMMAND_ADD);
       // User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "非繁忙时间：" + "主键：" + po.getDay_of_week()+ "#" + po.getStart_time() + "#" + po.getEnd_time() + ":";
        try {
            if (this.existRecord(po, ophMapper, opLogMapper)) {
                rmsg.addMessage("当天中已存在重复的时间段！");
                return rmsg;
            }

            // n = ophMapper.addOffPeakHours(po);
            n = this.addByTrans(request, ophMapper, pvMapper, po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);
        
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult delete(HttpServletRequest request, OffPeakHoursMapper ophMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<OffPeakHours> pos = this.getReqAttributeForDelete(request);
        OffPeakHours prmVo = new OffPeakHours();
        this.getBaseParameters(request, prmVo);
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, ophMapper, pvMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public OffPeakHours getReqAttribute(HttpServletRequest request, String operMode) {
        OffPeakHours po = new OffPeakHours();

        
        po.setDay_of_week(FormUtil.getParameter(request, "d_day_of_week"));  //week_type
        po.setStart_time(FormUtil.getParameter(request, "d_start_time"));  //sdate
        po.setEnd_time(FormUtil.getParameter(request, "d_end_time"));  //edate
        //"修改"操作才处理，"增加"操作也存在d_water_no有值的情况。
        if (operMode.equals(CommandConstant.COMMAND_MODIFY) && 
                request.getParameter("d_water_no") != null && !request.getParameter("d_water_no").isEmpty()) { //water_no
            po.setWater_no(Long.parseLong(request.getParameter("d_water_no")));
            
        }
        
        this.getBaseParameters(request, po);
        
        return po;
    }

    public OffPeakHours getReqAttributeForSubmit(HttpServletRequest request) {
        OffPeakHours po = new OffPeakHours();

        this.getBaseParameters(request, po);
        this.getBaseParametersForSubmit(request, po);

        return po;
    }

    public OffPeakHours getReqAttributeForClone(HttpServletRequest request) {
        OffPeakHours po = new OffPeakHours();

        this.getBaseParameters(request, po);

        return po;
    }

    private Vector<OffPeakHours> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<OffPeakHours> ophs = new Vector();
        OffPeakHours oph;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            oph = this.getOffPeakHours(strIds, "#");
            ophs.add(oph);
        }
        return ophs;

    }

    private OffPeakHours getOffPeakHours(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        OffPeakHours oph = new OffPeakHours();;
        Vector<OffPeakHours> ophs = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                oph.setWater_no(Long.parseLong(tmp));
                continue;
            }

        }
        return oph;

    }

    private Vector<OffPeakHours> getReqAttributeForDelete(HttpServletRequest request) {
        OffPeakHours po = new OffPeakHours();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<OffPeakHours> selectedItems = this.getDeleteIDs(selectIds);

        //this.getBaseParameters(request, po);
        return selectedItems;
    }
    
//    private void getBaseParameters(HttpServletRequest request, OffPeakHours offPeakHours) {
//        offPeakHours.setVersion_no(FormUtil.getParameter(request, "Version"));
//        offPeakHours.setRecord_flag(FormUtil.getParameter(request, "VersionType"));
//        offPeakHours.setParm_type_id(FormUtil.getParameter(request, "Type"));
//        User user = (User) request.getSession().getAttribute("User");
//        offPeakHours.setOperator_id(user.getAccount());
//    } 
    
    
    
    
//    private void getBaseParametersForSubmit(HttpServletRequest request, PrmVersion prmVersion) {
//        prmVersion.setBegin_time_submit(FormUtil.getParameter(request, "ver_date_begin"));
//        prmVersion.setEnd_time_submit(FormUtil.getParameter(request, "ver_date_end"));
//        prmVersion.setRecord_flag_submit(FormUtil.getParameter(request, "ver_generate"));
//
//        prmVersion.setRecord_flag_old(this.getRecordFlagOld(prmVersion));
//        prmVersion.setVersion_valid_date(this.convertValidDate(prmVersion.getBegin_time_submit()));//版本的生效日期格式YYYMMDD
//        prmVersion.setBegin_time_new(prmVersion.getBegin_time_submit() + " 00:00:00");//版本的生效的开始时间
//        prmVersion.setEnd_time_new(prmVersion.getEnd_time_submit() + " 23:59:59");//版本的生效的结束时间
//        prmVersion.setRecord_flag_new(prmVersion.getRecord_flag_submit());
//
//    }

}
