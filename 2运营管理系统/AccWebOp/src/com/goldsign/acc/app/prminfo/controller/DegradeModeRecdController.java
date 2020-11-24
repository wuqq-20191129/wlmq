/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.DegradeModeRecd;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.app.prminfo.mapper.DegradeModeRecdMapper;
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
import java.util.ArrayList;
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
 * 降级模式使用记录
 * @author lind
 */
@Controller
public class DegradeModeRecdController extends PrmBaseController {
    
    @Autowired
    private DegradeModeRecdMapper degradeModeRecdMapper;
    
    private long seqId=1;


    @RequestMapping("/DegradeModeRecd")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/degrade_mode_history.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        PrmVersion prmVersion = new PrmVersion();
        prmVersion = this.getPrmVersion(request, response);
        
        try {
//            if (command == null || command.equals(CommandConstant.COMMAND_LIST)) {
//                command = CommandConstant.COMMAND_QUERY;
//            }
//            command = command.trim();
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

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)) {
                    this.queryForOp(request, opResult, prmVersion);
                }
                if (!command.equals(CommandConstant.COMMAND_ADD) || !command.equals(CommandConstant.COMMAND_QUERY)) {
                    this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        String[] attrNames = {LINES, STATIONS, LINE_STATIONS, MODETYPES, VERSION};

        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值
        this.getResultSetText((List<DegradeModeRecd>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;
    }
    
    private void getResultSetText(List<DegradeModeRecd> resultSet, ModelAndView mv) {
        List<PubFlag> lines = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.LINES);
        List<PubFlag> stations = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.STATIONS);
        List<PubFlag> modeTypes = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.MODETYPES);

        for (DegradeModeRecd dmr : resultSet) {
            if (lines != null && !lines.isEmpty()) {
                dmr.setLineIdText(DBUtil.getTextByCode(dmr.getLineId(), lines));
            }
            if (stations != null && !stations.isEmpty()) {
                dmr.setStationIdText(DBUtil.getTextByCode(dmr.getStationId(), dmr.getLineId(), stations));
            }
            if (modeTypes != null && !modeTypes.isEmpty()) {
                dmr.setDegradeModeIdText(DBUtil.getTextByCode(dmr.getDegradeModeId(), modeTypes));
            }
            dmr.setHdlFlagText(dmr.getHdlFlag());
        }

    }

    private DegradeModeRecd getQueryConditionForOp(HttpServletRequest request, PrmVersion prmVersion) {

        DegradeModeRecd qCon = new DegradeModeRecd();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }

            qCon.setLineId(FormUtil.getParameter(request, "d_lineID"));
            qCon.setStationId(FormUtil.getParameter(request,"d_stationID"));
            if(command.equals(CommandConstant.COMMAND_ADD)){
                qCon.setWaterNo(seqId);
            }else{
                qCon.setWaterNo(Long.parseLong(FormUtil.getParameter(request,"d_waterNo")));
            }
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setLineId(FormUtil.getParameter(request, "q_lineID"));
                qCon.setStationId(FormUtil.getParameter(request,"q_stationID"));
                qCon.setDegradeModeId(FormUtil.getParameter(request,"q_degradeModeID"));
                qCon.setHdlFlag(FormUtil.getParameter(request,"q_hdlFlag"));
            }
        }

        qCon.setVersionNo(prmVersion.getVersion_no());
        return qCon;
    }

    private DegradeModeRecd getQueryCondition(HttpServletRequest request, PrmVersion prmVersion) {
        DegradeModeRecd qCon = new DegradeModeRecd();
        qCon.setLineId(FormUtil.getParameter(request, "q_lineID"));
        qCon.setStationId(FormUtil.getParameter(request,"q_stationID"));
        qCon.setDegradeModeId(FormUtil.getParameter(request,"q_degradeModeID"));
        qCon.setHdlFlag(FormUtil.getParameter(request,"q_hdlFlag"));
        qCon.setVersionNo(prmVersion.getVersion_no());

        return qCon;
    }

    public OperationResult query(HttpServletRequest request, PrmVersion prmVersion) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        DegradeModeRecd queryCondition;
        List<DegradeModeRecd> resultSet;

        try {
            queryCondition = this.getQueryCondition(request,prmVersion);
            resultSet = degradeModeRecdMapper.selectDegradeModeRecds(queryCondition);
            or.setReturnResultSet(resultSet);
            request.getSession().setAttribute("queryCondition", queryCondition);   
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
        DegradeModeRecd queryCondition;
        List<DegradeModeRecd> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request,prmVersion);
            if (queryCondition == null) {
                return null;
            }
            resultSet = degradeModeRecdMapper.selectDegradeModeRecds(queryCondition);
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
        DegradeModeRecd dmr = this.getReqAttribute(request,prmVersion);
        dmr.setWaterNo(Long.parseLong(FormUtil.getParameter(request,"d_waterNo")));
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "降级模式：版本号" +dmr.getVersionNo() + "流水号"  + dmr.getWaterNo()+ ":";
        if (CharUtil.getDBLenth(dmr.getSetOperId()) > 6||CharUtil.getDBLenth(dmr.getCancelOperId()) > 6) {
            rmsg.addMessage(preMsg + "操作员长度不能超过6位。");
            return rmsg;
        } else {
            try {
                n = this.modifyByTrans(dmr,prmVersion);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, this.operationLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), this.operationLogMapper);
        }
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(DegradeModeRecd dmr) {
//        DegradeModeRecd tmp = degradeModeRecdMapper.selectByPrimaryKey(dmr.getWaterNo());
        DegradeModeRecd tmp = degradeModeRecdMapper.existselectRecord(dmr);
        if (tmp==null) {
            return false;
        }
        return true;

    }

    private int addByTrans(DegradeModeRecd dmr, PrmVersion prmVersion) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = degradeModeRecdMapper.insert(dmr);
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

    private int modifyByTrans(DegradeModeRecd dmr, PrmVersion prmVersion) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = degradeModeRecdMapper.updateByPrimaryKeySelective(dmr);
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

    private int deleteByTrans( Vector<DegradeModeRecd> dts, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (DegradeModeRecd dmr : dts) {
                n += degradeModeRecdMapper.deleteByPrimaryKey(dmr.getWaterNo());
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
        DegradeModeRecd dmr = this.getReqAttribute(request,prmVersion);
        seqId = degradeModeRecdMapper.selectSeqNextval();
        dmr.setWaterNo(seqId);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "降级模式：版本号" +dmr.getVersionNo();
        if (CharUtil.getDBLenth(dmr.getSetOperId()) > 6||CharUtil.getDBLenth(dmr.getCancelOperId()) > 6) {
            rmsg.addMessage(preMsg + "操作员长度不能超过6位。");
            return rmsg;
        }else {
            try {
                if (this.existRecord(dmr))  {
                    rmsg.addMessage("增加记录已存在！");
                    return rmsg;
                }

                n = this.addByTrans(dmr,prmVersion);

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
        Vector<DegradeModeRecd> dts = this.getReqAttributeForDelete(request);
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

    public DegradeModeRecd getReqAttribute(HttpServletRequest request, PrmVersion prmVersion) {
        DegradeModeRecd dmr = new DegradeModeRecd();

        dmr.setLineId(FormUtil.getParameter(request, "d_lineID"));
        dmr.setStationId(FormUtil.getParameter(request,"d_stationID"));
        dmr.setDegradeModeId(FormUtil.getParameter(request,"d_degradeModeID"));
        dmr.setSetOperId(FormUtil.getParameter(request,"d_setOperID"));
        dmr.setCancelOperId(FormUtil.getParameter(request,"d_cancelOperID"));
        dmr.setStartTime(FormUtil.getParameter(request,"d_startTime"));
        dmr.setEndTime(FormUtil.getParameter(request,"d_startTime"));
        dmr.setHdlFlag("2");
//        try {
//            Date sdate = DateUtil.stringToDate(FormUtil.getParameter(request,"d_startTime"), "yyyy-MM-dd HH:mm:ss");
//            dmr.setStartTime(DateUtil.dateToString(sdate, "yyyyMMddHHmmss"));
//            Date edate = DateUtil.stringToDate(FormUtil.getParameter(request,"d_startTime"), "yyyy-MM-dd HH:mm:ss");
//            dmr.setEndTime(DateUtil.dateToString(edate, "yyyyMMddHHmmss"));
//        } catch (ParseException ex) {
//            ex.printStackTrace();
//        }
        
//        dmr.setVersionNo(prmVersion.getVersion_no());
dmr.setVersionNo("0000000000");
        
        return dmr;
    }

    public void getReqAttributeForSubmit(HttpServletRequest request, PrmVersion prmVersion) {
        this.getBaseParametersForSubmit(request, prmVersion);
    }

    public DegradeModeRecd getReqAttributeForClone(HttpServletRequest request) {
        DegradeModeRecd dmr = new DegradeModeRecd();
        return dmr;
    }

    private Vector<DegradeModeRecd> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<DegradeModeRecd> dts = new Vector();
        DegradeModeRecd dmr;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            dmr = this.getDegradeModeRecds(strIds, "#");
            dts.add(dmr);
        }
        return dts;

    }

    private DegradeModeRecd getDegradeModeRecds(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        DegradeModeRecd dmr = new DegradeModeRecd();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 3) {
                dmr.setWaterNo(Long.parseLong(tmp));
                continue;
            }
            if (i == 4) {
                dmr.setVersionNo(tmp);
                continue;
            }
            if (i == 5) {
                dmr.setHdlFlag(tmp);
                continue;
            }
        }
        return dmr;

    }

    private Vector<DegradeModeRecd> getReqAttributeForDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<DegradeModeRecd> selectedItems = this.getDeleteIDs(selectIds);

        return selectedItems;
    }

    private OperationResult submit(HttpServletRequest request, PrmVersion prmVersion) throws Exception {
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
    
    public int submitByTrans(HttpServletRequest request, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        String versionNoMax;
        int n = 0;
        try {
            status = txMgr.getTransaction(def);

            //生成新的未来或当前参数的版本号
            versionNoMax = this.prmVersionMapper.selectPrmVersionForSubmit(prmVersion);
            this.getVersionNoForSubmit(prmVersion, versionNoMax);

            // 重新生成参数表中的未来或当前参数记录
            this.prmVersionMapper.modifyPrmVersionForSubmit(prmVersion);
            this.prmVersionMapper.addPrmVersion(prmVersion);

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
    
    @RequestMapping("/DegradeModeRecdExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.prminfo.entity.DegradeModeRecd");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private List<Map<String, String>> entityToMap(List results) {
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            DegradeModeRecd vo = (DegradeModeRecd)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("lineIdText", vo.getLineIdText());
            map.put("stationIdText", vo.getStationIdText());
            map.put("degradeModeIdText", vo.getDegradeModeIdText());
            map.put("startTime", vo.getStartTime());
            map.put("endTime", vo.getEndTime());
            map.put("setOperId", vo.getSetOperId());
            map.put("cancelOperId", vo.getCancelOperId());
            map.put("hdlFlagText", vo.getHdlFlagText());
            list.add(map);
        }
        return list;
    }
}
