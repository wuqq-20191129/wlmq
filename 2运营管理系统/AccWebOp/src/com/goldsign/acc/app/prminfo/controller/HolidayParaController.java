/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.HolidayPara;
import com.goldsign.acc.app.prminfo.mapper.HolidayParaMapper;
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
public class HolidayParaController extends PrmBaseController {

    @Autowired
    private HolidayParaMapper holidayParaMapper;


    @RequestMapping("/HolidayPara")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/holiday_para.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.holidayParaMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.holidayParaMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.holidayParaMapper, this.prmVersionMapper, this.operationLogMapper);
                }

                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.holidayParaMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_SUBMIT))//提交操作
                {
//                    opResult = this.submit(request, this.holidayParaMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_CLONE))//克隆操作
                {
                    opResult = this.clone(request, this.holidayParaMapper, this.prmVersionMapper, this.operationLogMapper);

                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.holidayParaMapper, this.operationLogMapper, opResult);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {HOLIDAY_TYPES, VERSION};

        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.getResultSetText((List<HolidayPara>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }

    private void getResultSetText(List<HolidayPara> resultSet, ModelAndView mv) {
        List<PubFlag> holidayTypes = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.HOLIDAY_TYPES);

        for (HolidayPara hp : resultSet) {
            if (holidayTypes != null && !holidayTypes.isEmpty()) {
                hp.setHoliday_desc(DBUtil.getTextByCode(hp.getHoliday_type(), holidayTypes));
            }

        }

    }

    private HolidayPara getQueryConditionForOp(HttpServletRequest request) {

        HolidayPara qCon = new HolidayPara();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            
            qCon.setHoliday_type(FormUtil.getParameter(request, "d_holiday_desc"));
            qCon.setStart_date(this.converDateString(request.getParameter("d_start_date"))); //b_time
            qCon.setEnd_date(this.converDateString(request.getParameter("d_end_date")));  //e_time
            
        
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setHoliday_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_holiday_type"));
            }
        }
        this.getBaseParameters(request, qCon);
        //当操作为克隆时，查询克隆后的草稿版本
//        if (command.equals(CommandConstant.COMMAND_CLONE)) {
//            qCon.setRecord_flag(ParameterConstant.RECORD_FLAG_DRAFT);
//        }

        return qCon;
    }

    private HolidayPara getQueryCondition(HttpServletRequest request) {
        HolidayPara qCon = new HolidayPara();
        qCon.setHoliday_type(FormUtil.getParameter(request, "q_holiday_type"));
        

        this.getBaseParameters(request, qCon);
        return qCon;
    }

    public OperationResult query(HttpServletRequest request, HolidayParaMapper hpMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        HolidayPara queryCondition;
        List<HolidayPara> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = hpMapper.getHolidayParas(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult queryForOp(HttpServletRequest request, HolidayParaMapper hpMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        // OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        HolidayPara queryCondition;
        List<HolidayPara> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request);
            if (queryCondition == null) {
                return null;
            }
            resultSet = hpMapper.getHolidayParas(queryCondition);
            opResult.setReturnResultSet(resultSet);
            //or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult modify(HttpServletRequest request, HolidayParaMapper hpMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        HolidayPara po = this.getReqAttribute(request,CommandConstant.COMMAND_MODIFY);
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "节假日：" + "主键：" + po.getHoliday_type() + "#" + po.getStart_date() + "#" + po.getEnd_date() + ":";
        try {
            if (this.existRecord(po, hpMapper, opLogMapper)) {
                rmsg.addMessage("此节假日类型中已存在重复的时间段！");
                return rmsg;
            }
            n = this.modifyByTrans(request, hpMapper, pvMapper, po);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(HolidayPara po, HolidayParaMapper hpMapper, OperationLogMapper opLogMapper) {
        List<HolidayPara> list = hpMapper.getHolidayParaById(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }
    
    private int addByTrans(HttpServletRequest request, HolidayParaMapper hpMapper, PrmVersionMapper pvMapper, HolidayPara po) throws Exception {
//        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {

//            txMgr = DBUtil.getDataSourceTransactionManager(request);
//            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            status = txMgr.getTransaction(this.def);
            n = hpMapper.addHolidayPara(po);
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

    private void getVersionNoForSubmit(HolidayPara po, String versionNoMax) {
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

    

    public int submitByTrans(HttpServletRequest request, HolidayParaMapper hpMapper, PrmVersionMapper pvMapper, HolidayPara po, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        List<PrmVersion> prmVersions;
        String versionNoMax;
        int n = 0;
        int n1 = 0;
       // String test=null;
        try {

            status = txMgr.getTransaction(def);
            

            //旧的未来或当前参数数据做删除标志
            hpMapper.submitToOldFlag(po);
            //生成新的未来或当前参数的版本号
            versionNoMax = pvMapper.selectPrmVersionForSubmit(po);
            this.getVersionNoForSubmit(po, versionNoMax);

            //添加新的未来或当前参数的数据记录
            n = hpMapper.submitFromDraftToCurOrFur(po);
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

    

    private int cloneByTrans(HttpServletRequest request, HolidayParaMapper hpMapper, PrmVersionMapper pvMapper, HolidayPara po, PrmVersion prmVersion) throws Exception {
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
            hpMapper.deleteHolidayParasForClone(po);
            //未来或当前参数克隆成草稿版本
            n = hpMapper.cloneFromCurOrFurToDraft(po);
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

    public OperationResult submit(HttpServletRequest request, HolidayParaMapper hpMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        HolidayPara po = this.getReqAttributeForSubmit(request);
        HolidayPara prmVersion = null;
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.submitByTrans(request, hpMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_SUBMIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_SUBMIT, request, LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()), opLogMapper);

        rmsg.addMessage(LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()));

        return rmsg;
    }

    public OperationResult clone(HttpServletRequest request, HolidayParaMapper hpMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        HolidayPara po = this.getReqAttributeForClone(request);
        HolidayPara prmVersion = null;
       // User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.cloneByTrans(request, hpMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CLONE, e, opLogMapper);
        }
        //当前还是未来？
        logUtil.logOperation(CommandConstant.COMMAND_CLONE, request, LogConstant.cloneSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.cloneSuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(HttpServletRequest request, HolidayParaMapper hpMapper, PrmVersionMapper pvMapper, HolidayPara po) throws Exception {
//        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {

//            txMgr = DBUtil.getDataSourceTransactionManager(request);
//            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            status = txMgr.getTransaction(this.def);
            n = hpMapper.modifyHolidayPara(po);
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

    private int deleteByTrans(HttpServletRequest request, HolidayParaMapper hpMapper, PrmVersionMapper pvMapper, Vector<HolidayPara> pos, HolidayPara prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {


            status = txMgr.getTransaction(this.def);
            for (HolidayPara po : pos) {
                n += hpMapper.deleteHolidayPara(po);
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

    public OperationResult add(HttpServletRequest request, HolidayParaMapper hpMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        HolidayPara po = this.getReqAttribute(request, CommandConstant.COMMAND_ADD);
       // User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "节假日：" + "主键：" + po.getHoliday_type() + "#" + po.getStart_date() + "#" + po.getEnd_date() + ":";
        try {
            if (this.existRecord(po, hpMapper, opLogMapper)) {
                rmsg.addMessage("此节假日类型中已存在重复的时间段！");
                return rmsg;
            }

            // n = hpMapper.addHolidayPara(po);
            n = this.addByTrans(request, hpMapper, pvMapper, po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);
        
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult delete(HttpServletRequest request, HolidayParaMapper hpMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<HolidayPara> pos = this.getReqAttributeForDelete(request);
        HolidayPara prmVo = new HolidayPara();
        this.getBaseParameters(request, prmVo);
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, hpMapper, pvMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public HolidayPara getReqAttribute(HttpServletRequest request, String operMode) {
        HolidayPara po = new HolidayPara();
        
        po.setHoliday_type(FormUtil.getParameter(request, "d_holiday_desc")); //holiday_type
        po.setStart_date(this.converDateString(request.getParameter("d_start_date"))); //b_time
        po.setEnd_date(this.converDateString(request.getParameter("d_end_date")));  //e_time
        //"修改"操作才处理，"增加"操作也存在d_water_no有值的情况。
        if (operMode.equals(CommandConstant.COMMAND_MODIFY) 
                && request.getParameter("d_water_no") != null && !request.getParameter("d_water_no").isEmpty()) {
            po.setWater_no(Long.parseLong(request.getParameter("d_water_no"))); //water_no
            
        }
        
        
        this.getBaseParameters(request, po);
        //--del po.setVersionNo(version);
        //--del po.setParamTypeID(type);
        return po;
    }
    

    public HolidayPara getReqAttributeForSubmit(HttpServletRequest request) {
        HolidayPara po = new HolidayPara();

        this.getBaseParameters(request, po);
        this.getBaseParametersForSubmit(request, po);

        return po;
    }

    public HolidayPara getReqAttributeForClone(HttpServletRequest request) {
        HolidayPara po = new HolidayPara();

        this.getBaseParameters(request, po);

        return po;
    }

    private Vector<HolidayPara> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<HolidayPara> hps = new Vector();
        HolidayPara hp;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            hp = this.getHolidayPara(strIds, "#");
            hps.add(hp);
        }
        return hps;

    }

    private HolidayPara getHolidayPara(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        HolidayPara hp = new HolidayPara();;
        Vector<HolidayPara> hps = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                hp.setWater_no(Long.parseLong(tmp));
                continue;
            }

        }
        return hp;

    }

    private Vector<HolidayPara> getReqAttributeForDelete(HttpServletRequest request) {
        HolidayPara po = new HolidayPara();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<HolidayPara> selectedItems = this.getDeleteIDs(selectIds);

        //this.getBaseParameters(request, po);
        return selectedItems;
    }
    
    
    private String getRecordFlagOld(HolidayPara holidayPara) {
        String recordFlagSubmit = holidayPara.getRecord_flag_submit();
        String recordFlagOld = "";
        if (recordFlagSubmit.equals(ParameterConstant.RECORD_FLAG_CURRENT)) {
            recordFlagOld = ParameterConstant.RECORD_FLAG_HISTORY;
        }
        if (recordFlagSubmit.equals(ParameterConstant.RECORD_FLAG_FUTURE)) {
            recordFlagOld = ParameterConstant.RECORD_FLAG_DELETED;
        }
        return recordFlagOld;

    }
    
    
    private String converDateString(String srcString) {
        String desString = null;
        if (srcString != null && !srcString.isEmpty()) {
            desString = srcString.substring(0, 4) + srcString.substring(5, 7) + srcString.substring(8, 10);
        }
        return desString;
    }

}
