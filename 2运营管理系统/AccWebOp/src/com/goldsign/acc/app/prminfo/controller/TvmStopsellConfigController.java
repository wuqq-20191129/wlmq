/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.app.prminfo.entity.TvmStopSellConfig;
import com.goldsign.acc.app.prminfo.mapper.PrmVersionMapper;
import com.goldsign.acc.app.prminfo.mapper.TvmStopSellConfigMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author zhouyang
 * 20170611
 * TVM停售时间配置参数
 */
@Controller
public class TvmStopsellConfigController extends PrmBaseController {
    
    @Autowired
    private TvmStopSellConfigMapper tvmStopSellConfigMapper;
    private int n =0;


    @RequestMapping("/TvmStopSellConfig")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/tvm_stop_sell_config.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.tvmStopSellConfigMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.tvmStopSellConfigMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.tvmStopSellConfigMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.tvmStopSellConfigMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_SUBMIT))//提交操作
                {
                    opResult = this.submit(request, this.tvmStopSellConfigMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_CLONE))//克隆操作
                {
                    opResult = this.clone(request, this.tvmStopSellConfigMapper, this.prmVersionMapper, this.operationLogMapper);
                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.tvmStopSellConfigMapper, this.operationLogMapper, opResult);

                }
                if (command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                        || command.equals(CommandConstant.COMMAND_BACKEND)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {TIMETABLE_IDS,RECORDFLAG,VERSION};

        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.getResultSetText((List<TvmStopSellConfig>) opResult.getReturnResultSet(), mv);
        this.changeDateType((List<TvmStopSellConfig>) opResult.getReturnResultSet());//将字符串转换成符合日期格式
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }

    private void getResultSetText(List<TvmStopSellConfig> resultSet, ModelAndView mv) {
        List<PubFlag> timeTableId = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.TIMETABLE_IDS);
        List<PubFlag> recordFlag = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.RECORDFLAG);
        for (TvmStopSellConfig tc : resultSet) {
            if (recordFlag != null && !recordFlag.isEmpty()) {
                tc.setRecord_flag_name(DBUtil.getTextByCode(tc.getRecord_flag(), recordFlag));
            }
            if (timeTableId != null && !timeTableId.isEmpty()) {
                tc.setTimeTable_id_name(DBUtil.getTextByCode(tc.getTimeTable_id(), timeTableId));
            }
        }

    }
    
    private void changeDateType(List<TvmStopSellConfig> resultSet){
        String date;
        String newDate;
        for (TvmStopSellConfig tc : resultSet) {
            if(tc.getBegin_date() !=null && !tc.getBegin_date().isEmpty()){
                date=tc.getBegin_date();
                newDate = date.substring(0, 4) +"-"+ date.substring(4, 6)+"-"
                    + date.substring(6, 8)+" " + date.substring(8, 10)+":"
                    + date.substring(10, 12)+":00";
                tc.setBegin_date(newDate);
            }
            if(tc.getEnd_date() !=null && !tc.getEnd_date().isEmpty()){
                date=tc.getEnd_date();
                newDate = date.substring(0, 4) +"-"+ date.substring(4, 6)+"-"
                    + date.substring(6, 8)+" " + date.substring(8, 10)+":"
                    + date.substring(10, 12)+":00";
                tc.setEnd_date(newDate);
            }
        }
    }

    private TvmStopSellConfig getQueryConditionForOp(HttpServletRequest request) {

        TvmStopSellConfig qCon = new TvmStopSellConfig();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                this.getBaseParameters(request, qCon);
                return qCon;
            }
            qCon.setBegin_date(FormUtil.getParameter(request, "d_beginDate"));
            qCon.setEnd_date(FormUtil.getParameter(request,"d_endDate"));
            qCon.setTimeTable_id(FormUtil.getParameter(request,"d_timeTableID"));
            this.changeDateToStr(qCon);
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                String q_beginDate = FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_beginDate");
                String q_endDate = FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_endDate");
                if(q_beginDate != null && !q_beginDate.equals("")){
                    qCon.setBegin_date(q_beginDate +  " 00:00:00");
                }
               if(q_endDate != null && !q_endDate.equals("")){
                    qCon.setEnd_date(q_endDate +  " 23:59:59");
                }
                this.changeDateToStr(qCon);//去掉日期中的“-”与“：”符号
                qCon.setTimeTable_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_timeTableID"));
            }
        }
        this.getBaseParameters(request, qCon);

        return qCon;
    }

    private TvmStopSellConfig getQueryCondition(HttpServletRequest request) {
        TvmStopSellConfig qCon = new TvmStopSellConfig();
        if(FormUtil.getParameter(request, "q_beginDate") != null && !FormUtil.getParameter(request, "q_beginDate").equals("")){
            qCon.setBegin_date(FormUtil.getParameter(request, "q_beginDate") + " 00:00:00");
        }
        if(FormUtil.getParameter(request, "q_endDate") != null && !FormUtil.getParameter(request, "q_endDate").equals("")){
            qCon.setEnd_date(FormUtil.getParameter(request, "q_endDate") + " 23:59:59");
        }
        this.changeDateToStr(qCon);//去掉日期中的“-”与“：”符号
        qCon.setTimeTable_id(FormUtil.getParameter(request, "q_timeTableID"));
        this.getBaseParameters(request, qCon);
        return qCon;
    }
    
    private void changeDateToStr(TvmStopSellConfig tvmStopSellConfig){
        String beginDate = tvmStopSellConfig.getBegin_date();
        String endDate = tvmStopSellConfig.getEnd_date();
        if(beginDate!=null && !beginDate.isEmpty()){
            beginDate= beginDate.substring(0,4)+beginDate.substring(5,7)
                        +beginDate.substring(8,10)+beginDate.substring(11,13)
                        +beginDate.substring(14,16);
            tvmStopSellConfig.setBegin_date(beginDate);
        }
        if(endDate!=null && !endDate.isEmpty()){
            endDate= endDate.substring(0,4)+endDate.substring(5,7)
                        +endDate.substring(8,10)+endDate.substring(11,13)
                        +endDate.substring(14,16);
            tvmStopSellConfig.setEnd_date(endDate);
        }
    }

    public OperationResult query(HttpServletRequest request, TvmStopSellConfigMapper tcMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TvmStopSellConfig queryCondition;
        List<TvmStopSellConfig> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = tcMapper.getTvmStopSellConfigs(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult queryForOp(HttpServletRequest request, TvmStopSellConfigMapper tcMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        
        LogUtil logUtil = new LogUtil();
        TvmStopSellConfig queryCondition;
        List<TvmStopSellConfig> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request);
            if (queryCondition == null) {
                return null;
            }
            if(n==0){//如果是0则不是删除操作
                resultSet = tcMapper.getTvmStopSellConfigById(queryCondition);
            }else{
                resultSet = tcMapper.getTvmStopSellConfigs(queryCondition);
            }
            opResult.setReturnResultSet(resultSet);
            //or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult modify(HttpServletRequest request, TvmStopSellConfigMapper tcMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TvmStopSellConfig po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "TVM停售时间配置参数：";
        try {
            n = this.modifyByTrans(po);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(TvmStopSellConfig po, TvmStopSellConfigMapper sdMapper) {
        List<TvmStopSellConfig> list = sdMapper.getTvmStopSellConfigById(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private int addByTrans(TvmStopSellConfig po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = tvmStopSellConfigMapper.addTvmStopSellConfig(po);
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

    private void getVersionNoForSubmit(TvmStopSellConfig po, String versionNoMax) {
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

    

    public int submitByTrans(HttpServletRequest request, TvmStopSellConfigMapper tcMapper, PrmVersionMapper pvMapper, TvmStopSellConfig po, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        List<PrmVersion> prmVersions;
        String versionNoMax;
        int n = 0;
        int n1 = 0;
       // String test=null;
        try {

            status = txMgr.getTransaction(def);
            

            //旧的未来或当前参数数据做删除标志
            tcMapper.submitToOldFlag(po);
            //生成新的未来或当前参数的版本号
            versionNoMax = pvMapper.selectPrmVersionForSubmit(po);
            this.getVersionNoForSubmit(po, versionNoMax);

            //添加新的未来或当前参数的数据记录
            n = tcMapper.submitFromDraftToCurOrFur(po);
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

    

    private int cloneByTrans(HttpServletRequest request, TvmStopSellConfigMapper tcMapper, PrmVersionMapper pvMapper, TvmStopSellConfig po) throws Exception {
        
        TransactionStatus status = null;
        int n = 0;
        int n1 = 0;
        try {

            status = txMgr.getTransaction(this.def);

            //删除草稿版本
            tcMapper.deleteTvmStopSellConfigsForClone();
            //未来或当前参数克隆成草稿版本
            n = tcMapper.cloneFromCurOrFurToDraft(po);
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

    public OperationResult submit(HttpServletRequest request, TvmStopSellConfigMapper tcMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TvmStopSellConfig po = this.getReqAttributeForSubmit(request);
        TvmStopSellConfig prmVersion = null;
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.submitByTrans(request, tcMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_SUBMIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_SUBMIT, request, LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()), opLogMapper);

        rmsg.addMessage(LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()));

        return rmsg;
    }

    public OperationResult clone(HttpServletRequest request, TvmStopSellConfigMapper tcMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TvmStopSellConfig po = this.getReqAttributeForClone(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.cloneByTrans(request, tcMapper, pvMapper, po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CLONE, e, opLogMapper);
        }
        //当前还是未来？
        logUtil.logOperation(CommandConstant.COMMAND_CLONE, request, LogConstant.cloneSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.cloneSuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(TvmStopSellConfig po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = tvmStopSellConfigMapper.modifyTvmStopSellConfig(po);
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

    private int deleteByTrans(TvmStopSellConfigMapper tvMapper, PrmVersionMapper pvMapper, List<TvmStopSellConfig> pos, TvmStopSellConfig prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {


            status = txMgr.getTransaction(this.def);
            for (TvmStopSellConfig po : pos) {
                this.changeDateToStr(po);
                n += tvMapper.deleteTvmStopSellConfig(po);
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

    public OperationResult add(HttpServletRequest request, TvmStopSellConfigMapper tcMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TvmStopSellConfig po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();

    String preMsg = "开始时间:" + po.getBegin_date() +" 结束时间:"+po.getEnd_date() +":";
        
        try {
            if (this.existRecord(po, tcMapper)) {
                rmsg.addMessage(preMsg + "该时间段已存在");
                return rmsg;
            }
            n = this.addByTrans(po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);
        
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult delete(HttpServletRequest request, TvmStopSellConfigMapper tcMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        List<TvmStopSellConfig> pos = this.getReqAttributeForDelete(request);
        TvmStopSellConfig prmVo = new TvmStopSellConfig();
        this.getBaseParameters(request, prmVo);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(tcMapper, pvMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public TvmStopSellConfig getReqAttribute(HttpServletRequest request) {
        String comman =request.getParameter("command");
        TvmStopSellConfig po = new TvmStopSellConfig();
        if(!comman.equals(CommandConstant.COMMAND_ADD)){
            po.setFlow_id(FormUtil.getParameter(request, "flow_id"));
        }
        po.setTimeTable_id(FormUtil.getParameter(request, "d_timeTableID"));
        po.setBegin_date(FormUtil.getParameter(request, "d_beginDate"));
        po.setEnd_date(FormUtil.getParameter(request, "d_endDate"));
        po.setParam_type_id("09"+FormUtil.getParameter(request, "d_timeTableID"));
        if (comman != null) {
            comman = comman.trim();
            if(comman.equals(CommandConstant.COMMAND_ADD) || comman.equals(CommandConstant.COMMAND_MODIFY)){
                this.changeDateToStr(po);
            }
        }
        this.getBaseParameters(request, po);
        return po;
    }

    public TvmStopSellConfig getReqAttributeForSubmit(HttpServletRequest request) {
        TvmStopSellConfig po = new TvmStopSellConfig();

        this.getBaseParameters(request, po);
        this.getBaseParametersForSubmit(request, po);

        return po;
    }

    public TvmStopSellConfig getReqAttributeForClone(HttpServletRequest request) {
        TvmStopSellConfig po = new TvmStopSellConfig();
        this.getBaseParameters(request, po);
        return po;
    }

    private List<TvmStopSellConfig> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        List<TvmStopSellConfig> sds = new ArrayList();
        TvmStopSellConfig sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getStationDevice(strIds, "#");
            sds.add(sd);
        }
        return sds;

    }

    private TvmStopSellConfig getStationDevice(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TvmStopSellConfig sd = new TvmStopSellConfig();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setBegin_date(tmp);
                continue;
            }
            if (i == 2) {
                sd.setEnd_date(tmp);
                continue;
            }
            if (i == 3) {
                sd.setFlow_id(tmp);

                continue;
            }
            if (i == 4) {
                sd.setTimeTable_id(tmp);
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

    private List<TvmStopSellConfig> getReqAttributeForDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        List<TvmStopSellConfig> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }
    
    @RequestMapping("/TvmStopSellConfigExportAll")
    public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.prminfo.entity.TvmStopSellConfig");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
    
    private List<Map<String,String>> entityToMap(List results){
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            TvmStopSellConfig vo = (TvmStopSellConfig)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("timeTable_id_name", vo.getTimeTable_id_name());
            map.put("begin_date", vo.getBegin_date());
            map.put("end_date", vo.getEnd_date());
            map.put("version_no", vo.getVersion_no());
            map.put("record_flag_name", vo.getRecord_flag_name());
            list.add(map);
        }
        return list;
    }

}
