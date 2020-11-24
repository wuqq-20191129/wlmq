/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.app.prminfo.entity.TvmStopsellDef;
import com.goldsign.acc.app.prminfo.mapper.PrmVersionMapper;
import com.goldsign.acc.app.prminfo.mapper.TvmStopsellDefMapper;
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
import com.goldsign.acc.frame.util.PageControlUtil;
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
 * 20170613
 * 工作日时间表参数
 */
@Controller
public class TvmStopsellDefController extends PrmBaseController {
    @Autowired
    private TvmStopsellDefMapper tvmStopsellDefMapper;
    
    @RequestMapping("/TvmStopsellDef")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/tvm_stop_sell_def.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if(request.getParameter("command")==null || request.getParameter("command").equals("list")){//清缓存
                    request.getSession().setAttribute(PageControlUtil.NAME_BUFFER, null);
                }
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.tvmStopsellDefMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.tvmStopsellDefMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.tvmStopsellDefMapper, this.prmVersionMapper, this.operationLogMapper);
                }

                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.tvmStopsellDefMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_SUBMIT))//提交操作
                {
                    opResult = this.submit(request, this.tvmStopsellDefMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_CLONE))//克隆操作
                {
                    opResult = this.clone(request, this.tvmStopsellDefMapper, this.prmVersionMapper, this.operationLogMapper);

                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.tvmStopsellDefMapper, this.operationLogMapper, opResult);

                }
                if (command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                        || command.equals(CommandConstant.COMMAND_BACKEND)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {LINES, STATIONS, LINE_STATIONS,TIMETABLE_ALL_IDS,VERSION};

        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.getResultSetText((List<TvmStopsellDef>) opResult.getReturnResultSet(), mv,request,response);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }

    private void getResultSetText(List<TvmStopsellDef> resultSet, ModelAndView mv,HttpServletRequest request,HttpServletResponse response) {
        List<PubFlag> lines = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.LINES);
        List<PubFlag> stations = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.STATIONS);
        List<PubFlag> timeTables = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.TIMETABLE_ALL_IDS);
        
        //获取页面标题名称
        TvmStopsellDef tvmStopsellDef = new TvmStopsellDef();
        PrmVersion version = this.getPrmVersion(request, response);
        String timeTable_id = version.getParm_type_id().substring(2,4);
        if(timeTables != null && !timeTables.isEmpty()){
            tvmStopsellDef.setTimetable_name(DBUtil.getTextByCode(timeTable_id, timeTables));
            mv.addObject("timeTable", tvmStopsellDef);
        }

        //将结果集中的id转换为名称
        for (TvmStopsellDef td : resultSet) {
            if (lines != null && !lines.isEmpty()) {
                if(td.getStart_station() != null && !td.getStart_station().isEmpty()){
                    td.setStart_line_name(DBUtil.getTextByCode(td.getStart_station().substring(0,2), lines));
                }
                td.setStart_line_id(td.getStart_station().substring(0,2));
                if(td.getDest_station() != null && !td.getDest_station().isEmpty()){
                    td.setDest_line_name(DBUtil.getTextByCode(td.getDest_station().substring(0,2), lines));
                }
                td.setDest_line_id(td.getDest_station().substring(0,2));
            }
            if (stations != null && !stations.isEmpty()) {
                if(td.getStart_station() != null && !td.getStart_station().isEmpty()){
                    td.setStart_station_name(DBUtil.getTextByCode(td.getStart_station().substring(2,4),td.getStart_station().substring(0,2), stations));
                }
                if(td.getDest_station() != null && !td.getDest_station().isEmpty()){
                    td.setDest_station_name(DBUtil.getTextByCode(td.getDest_station().substring(2,4),td.getDest_station().substring(0,2), stations));
                }
            }
            if(timeTables != null && !timeTables.isEmpty()){
                td.setTimetable_name(DBUtil.getTextByCode(td.getTimetable_id(), timeTables));
            }
            if(td.getStopsell_time1() !=null && !td.getStopsell_time1().isEmpty()){
                td.setStopsell_time1(td.getStopsell_time1().substring(0,2) + ":" + td.getStopsell_time1().substring(2,4));
            }
            if(td.getStopsell_time2() !=null && !td.getStopsell_time2().isEmpty()){
                td.setStopsell_time2(td.getStopsell_time2().substring(0,2) + ":" + td.getStopsell_time2().substring(2,4));
            }
            if(td.getStopsell_endtime() !=null && !td.getStopsell_endtime().isEmpty()){
                td.setStopsell_endtime(td.getStopsell_endtime().substring(0,2) + ":" + td.getStopsell_endtime().substring(2,4));
            }
        }

    }

    private TvmStopsellDef getQueryConditionForOp(HttpServletRequest request) {

        TvmStopsellDef qCon = new TvmStopsellDef();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                this.getBaseParameters(request, qCon);
                return qCon;
            }

            qCon.setStart_line_id(FormUtil.getParameter(request, "d_startLine"));
            qCon.setStart_station(FormUtil.getParameter(request,"d_startStation"));
            qCon.setDest_line_id(FormUtil.getParameter(request,"d_destLine"));
            qCon.setDest_station(FormUtil.getParameter(request,"d_destStation"));
            qCon.setStopsell_time1(FormUtil.getParameter(request,"d_stopsell_time1_hh")+FormUtil.getParameter(request,"d_stopsell_time1_mm"));
            qCon.setStopsell_time2(FormUtil.getParameter(request,"d_stopsell_time2_hh")+FormUtil.getParameter(request,"d_stopsell_time2_mm"));
            qCon.setStopsell_endtime(FormUtil.getParameter(request,"d_stopsell_endtime_hh")+FormUtil.getParameter(request,"d_stopsell_endtime_mm"));
            
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setStart_line_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_startLine"));
                qCon.setStart_station(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_startStation"));
                qCon.setDest_line_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_destLine"));
                qCon.setDest_station(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_destStation"));
            }
        }
        this.getBaseParameters(request, qCon);

        return qCon;
    }
    

    private TvmStopsellDef getQueryCondition(HttpServletRequest request) {
        TvmStopsellDef qCon = new TvmStopsellDef();
        qCon.setStart_line_id(FormUtil.getParameter(request, "q_startLine"));
        qCon.setStart_station(FormUtil.getParameter(request, "q_startStation"));
        qCon.setDest_line_id(FormUtil.getParameter(request, "q_destLine"));
        qCon.setDest_station(FormUtil.getParameter(request, "q_destStation"));

        this.getBaseParameters(request, qCon);
        return qCon;
    }

    public OperationResult query(HttpServletRequest request, TvmStopsellDefMapper tdMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TvmStopsellDef queryCondition;
        List<TvmStopsellDef> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = tdMapper.getTvmStopsellDefs(queryCondition);
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

    public OperationResult queryForOp(HttpServletRequest request, TvmStopsellDefMapper tdMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        
        LogUtil logUtil = new LogUtil();
        TvmStopsellDef queryCondition;
        List<TvmStopsellDef> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request);
            if (queryCondition == null) {
                return null;
            }
            resultSet = tdMapper.getTvmStopsellDefs(queryCondition);
            opResult.setReturnResultSet(resultSet);
            //or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult modify(HttpServletRequest request, TvmStopsellDefMapper tdMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TvmStopsellDef po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg ="出发车站" + po.getStart_station() + "目的车站" + po.getDest_station() + "停售开始时间1"
                + po.getStopsell_time1() + "停售开始时间2" + po.getStopsell_time2() + "停售结束时间"+po.getStopsell_endtime()+":";
        try {
            n = this.modifyByTrans(po);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(TvmStopsellDef po, TvmStopsellDefMapper tdMapper, OperationLogMapper opLogMapper) {
        List<TvmStopsellDef> list = tdMapper.getTvmStopsellDefById(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private int addByTrans(TvmStopsellDef po) throws Exception {
        
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = tvmStopsellDefMapper.addTvmStopsellDef(po);
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

    private void getVersionNoForSubmit(TvmStopsellDef po, String versionNoMax) {
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

    

    public int submitByTrans(HttpServletRequest request, TvmStopsellDefMapper tdMapper, PrmVersionMapper pvMapper, TvmStopsellDef po, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        String versionNoMax;
        int n = 0;
        int n1 = 0;
        try {

            status = txMgr.getTransaction(def);
            

            //旧的未来或当前参数数据做删除标志
            tdMapper.submitToOldFlag(po);
            //生成新的未来或当前参数的版本号
            versionNoMax = pvMapper.selectPrmVersionForSubmit(po);
            this.getVersionNoForSubmit(po, versionNoMax);

            //添加新的未来或当前参数的数据记录
            n = tdMapper.submitFromDraftToCurOrFur(po);
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

    

    private int cloneByTrans(HttpServletRequest request, TvmStopsellDefMapper tdMapper, PrmVersionMapper pvMapper, TvmStopsellDef po) throws Exception {
        ;
        TransactionStatus status = null;
        int n = 0;
        int n1 = 0;
        try {

           // txMgr = DBUtil.getDataSourceTransactionManager(request);
            status = txMgr.getTransaction(this.def);

            //删除草稿版本
            tdMapper.deleteTvmStopsellDefsForClone(po);
            //未来或当前参数克隆成草稿版本
            n = tdMapper.cloneFromCurOrFurToDraft(po);
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

    public OperationResult submit(HttpServletRequest request, TvmStopsellDefMapper tdMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TvmStopsellDef po = this.getReqAttributeForSubmit(request);
        TvmStopsellDef prmVersion = null;
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.submitByTrans(request, tdMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_SUBMIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_SUBMIT, request, LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()), opLogMapper);

        rmsg.addMessage(LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()));

        return rmsg;
    }

    public OperationResult clone(HttpServletRequest request, TvmStopsellDefMapper tdMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TvmStopsellDef po = this.getReqAttributeForClone(request);
        TvmStopsellDef prmVersion = null;
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.cloneByTrans(request, tdMapper, pvMapper, po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CLONE, e, opLogMapper);
        }
        //当前还是未来？
        logUtil.logOperation(CommandConstant.COMMAND_CLONE, request, LogConstant.cloneSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.cloneSuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(TvmStopsellDef po) throws Exception {
        
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = tvmStopsellDefMapper.modifyTvmStopsellDef(po);
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

    private int deleteByTrans(HttpServletRequest request, TvmStopsellDefMapper tdMapper, PrmVersionMapper pvMapper, List<TvmStopsellDef> pos, TvmStopsellDef prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {


            status = txMgr.getTransaction(this.def);
            for (TvmStopsellDef po : pos) {
                n += tdMapper.deleteTvmStopsellDef(po);
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

    public OperationResult add(HttpServletRequest request, TvmStopsellDefMapper tdMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TvmStopsellDef po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "出发车站" + po.getStart_station() + "目的车站" + po.getDest_station() + "停售开始时间1"
                + po.getStopsell_time1() + "停售开始时间2" + po.getStopsell_time2() + "停售结束时间"+po.getStopsell_endtime()+":";
        
        try {
            if (this.existRecord(po, tdMapper, opLogMapper)) {
                rmsg.addMessage("该“出发车站”对应的“目的车站”记录已存在，请选择其它“出发车站”或“目的车站”！");
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

    public OperationResult delete(HttpServletRequest request, TvmStopsellDefMapper tdMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        List<TvmStopsellDef> pos = this.getReqAttributeForDelete(request);
        TvmStopsellDef prmVo = new TvmStopsellDef();
        this.getBaseParameters(request, prmVo);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, tdMapper, pvMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public TvmStopsellDef getReqAttribute(HttpServletRequest request) {
        TvmStopsellDef po = new TvmStopsellDef();
        po.setStart_line_id(FormUtil.getParameter(request, "d_startLine"));
        po.setStart_station_id(FormUtil.getParameter(request, "d_startStation"));
        po.setStart_station(po.getStart_line_id()+po.getStart_station_id());
        po.setDest_line_id(FormUtil.getParameter(request, "d_destLine"));
        po.setDest_station_id(FormUtil.getParameter(request, "d_destStation"));
        po.setDest_station(po.getDest_line_id()+po.getDest_station_id());
        po.setStopsell_time1(FormUtil.getParameter(request, "d_stopsell_time1_hh")+FormUtil.getParameter(request, "d_stopsell_time1_mm"));
        po.setStopsell_time2(FormUtil.getParameter(request, "d_stopsell_time2_hh")+FormUtil.getParameter(request, "d_stopsell_time2_mm"));
        po.setStopsell_endtime(FormUtil.getParameter(request, "d_stopsell_endtime_hh")+FormUtil.getParameter(request, "d_stopsell_endtime_mm"));

        this.getBaseParameters(request, po);
        if(po.getParm_type_id() != null && !po.getParm_type_id().isEmpty()){
            po.setTimetable_id(po.getParm_type_id().substring(2,4));
        }
        return po;
    }

    public TvmStopsellDef getReqAttributeForSubmit(HttpServletRequest request) {
        TvmStopsellDef po = new TvmStopsellDef();
        this.getBaseParameters(request, po);
        this.getBaseParametersForSubmit(request, po);

        return po;
    }
    
    private String getRecordFlagOld(TvmStopsellDef tvmStopsellDef) {
        String recordFlagSubmit = tvmStopsellDef.getRecord_flag_submit();
        String recordFlagOld = "";
        if (recordFlagSubmit.equals(ParameterConstant.RECORD_FLAG_CURRENT)) {
            recordFlagOld = ParameterConstant.RECORD_FLAG_HISTORY;
        }
        if (recordFlagSubmit.equals(ParameterConstant.RECORD_FLAG_FUTURE)) {
            recordFlagOld = ParameterConstant.RECORD_FLAG_DELETED;
        }
        return recordFlagOld;
    }

    public TvmStopsellDef getReqAttributeForClone(HttpServletRequest request) {
        TvmStopsellDef po = new TvmStopsellDef();

        this.getBaseParameters(request, po);

        return po;
    }

    private List<TvmStopsellDef> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        List<TvmStopsellDef> sds = new ArrayList();
        TvmStopsellDef sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getTvmStopsellDef(strIds, "#");
            sds.add(sd);
        }
        return sds;

    }

    private TvmStopsellDef getTvmStopsellDef(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TvmStopsellDef td = new TvmStopsellDef();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                td.setStart_station(tmp);
                continue;
            }
            if (i == 2) {
                td.setDest_station(tmp);
                continue;
            }
            if (i == 3) {
                td.setVersion_no(tmp);
                continue;
            }
            if (i == 4) {
                td.setRecord_flag(tmp);
                continue;
            }
            if(i == 5){
                td.setParm_type_id(tmp);
            }

        }
        return td;

    }

    private List<TvmStopsellDef> getReqAttributeForDelete(HttpServletRequest request) {
        TvmStopsellDef po = new TvmStopsellDef();
        String selectIds = request.getParameter("allSelectedIDs");
        List<TvmStopsellDef> selectedItems = this.getDeleteIDs(selectIds);

        return selectedItems;
    }
    @RequestMapping("/TvmStopsellDefExportAll")
    public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.prminfo.entity.TvmStopsellDef");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
    
    private List<Map<String,String>> entityToMap(List results){
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            TvmStopsellDef vo = (TvmStopsellDef)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("timetable_name", vo.getTimetable_name());
            map.put("start_line_name", vo.getStart_line_name());
            map.put("start_station_name", vo.getStart_station_name());
            map.put("dest_line_name", vo.getDest_line_name());
            map.put("dest_station_name", vo.getDest_station_name());
            map.put("stopsell_time1", vo.getStopsell_time1());
            map.put("stopsell_time2", vo.getStopsell_time2());
            map.put("stopsell_endtime", vo.getStopsell_endtime());
            list.add(map);
        }
        return list;
    }
}
