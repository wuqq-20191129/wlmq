package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.LineCode;
import com.goldsign.acc.app.prminfo.entity.StationCode;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.app.prminfo.mapper.LineCodeMapper;
import com.goldsign.acc.app.prminfo.mapper.PrmVersionMapper;
import com.goldsign.acc.app.prminfo.mapper.StationCodeMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.constant.ParameterConstant;

import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.mapper.PubFlagMapper;
import com.goldsign.acc.frame.util.CharUtil;
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
 * 运营资料--线路车站--车站表
 * @author xiaowu
 * @version 20170612
 */
@Controller
public class StationCodeController extends PrmBaseController {

    @Autowired
    private StationCodeMapper stationCodeMapper;
    
    @Autowired
    private LineCodeMapper lineCodeMapper;

    @Autowired
    private PubFlagMapper pubFlagMapper;

    @RequestMapping("/StationCode")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/station_code.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
            
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.stationCodeMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.stationCodeMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.stationCodeMapper, this.prmVersionMapper, this.operationLogMapper);
                }

                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.stationCodeMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_SUBMIT))//提交操作
                {
                    opResult = this.submit(request, this.stationCodeMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_CLONE))//克隆操作
                {
                    opResult = this.clone(request, this.stationCodeMapper, this.prmVersionMapper, this.operationLogMapper);
                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT)
                        || command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) 
                        || command.equals(CommandConstant.COMMAND_BACK) || command.equals(CommandConstant.COMMAND_BACKEND)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.stationCodeMapper, this.operationLogMapper, opResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //初始化下拉框
        String[] attrNames = {LINES, CONTCS, STATIONS, LINE_STATIONS, DEV_TYPES, MERCHANTS, VERSION};
        String lineIdWithLcIp = "";
        //线路id 与 LC的IP值 关联
        LineCode lc = new LineCode();
//        this.getBaseParameters(request, lc);
        lc.setRecord_flag("0");   //显示当前版本的路线
        List<LineCode> lcList = lineCodeMapper.getLineCodes(lc);
        if(lcList != null && lcList.size()>0){
            for(LineCode lcTemp : lcList){
                lineIdWithLcIp += lcTemp.getLine_id() + ":" + lcTemp.getLc_ip() + ";";
            }
            //20180614 mqf
            lineIdWithLcIp = lineIdWithLcIp.substring(0,lineIdWithLcIp.length()-1);
        }
//        request.setAttribute("lineIdWithLcIp", lineIdWithLcIp.substring(0,lineIdWithLcIp.length()-1));
        request.setAttribute("lineIdWithLcIp", lineIdWithLcIp);

        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.getResultSetText((List<StationCode>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }
    
    private void getResultSetText(List<StationCode> resultSet, ModelAndView mv) {
        //线路名
        List<PubFlag> lines = pubFlagMapper.getLines();
        //运营商
        List<PubFlag> contcs = pubFlagMapper.getContcs();
        for (StationCode sc : resultSet) {
            if (sc != null) {
                PubFlag pv = new PubFlag();
                for (int i = 0; i < lines.size(); i++) {
                    pv = (PubFlag) lines.get(i);
                    if (pv.getCode().equals(sc.getLine_id())) {
                        sc.setLine_name(pv.getCode_text());
                    }
                    if (pv.getCode().equals(sc.getBelong_line_id())) {
                        sc.setBelong_line_name(pv.getCode_text());
                    }
                }
                for (int i = 0; i < contcs.size(); i++) {
                    pv = (PubFlag) contcs.get(i);
                     if (pv.getCode().equals(sc.getContc_id())) {
                        sc.setContc_name(pv.getCode_text());
                    }
                }
            }
        }
    }
    
    private StationCode getQueryConditionForOp(HttpServletRequest request) {

        StationCode stationCode = new StationCode();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            stationCode.setLine_id(FormUtil.getParameter(request, "d_line_id"));
            stationCode.setStation_id(FormUtil.getParameter(request,"d_station_id"));
            stationCode.setChinese_name(FormUtil.getParameter(request,"d_chinese_name"));
            stationCode.setEnglish_name(FormUtil.getParameter(request,"d_english_name"));
            stationCode.setContc_id(FormUtil.getParameter(request,"d_contc_id"));
            stationCode.setSc_ip(FormUtil.getParameter(request,"d_sc_ip"));
            stationCode.setLc_ip(FormUtil.getParameter(request,"d_lcc_ip"));
            stationCode.setSequence(FormUtil.getParameter(request,"d_sequence"));
            stationCode.setBelong_line_id(FormUtil.getParameter(request,"d_belong_line_id"));
            stationCode.setUygur_name(FormUtil.getParameter(request,"d_uygur_name"));
            
            
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                stationCode.setLine_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_line_id"));
                stationCode.setContc_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_contc_id"));
            }
        }
        this.getBaseParameters(request, stationCode);
        //当操作为克隆时，查询克隆后的草稿版本
        if (command.equals(CommandConstant.COMMAND_CLONE)) {
            stationCode.setRecord_flag(ParameterConstant.RECORD_FLAG_DRAFT);
        }

        return stationCode;
    }

    //获取查询参数
    private StationCode getQueryCondition(HttpServletRequest request) {
        StationCode stationCode = new StationCode();
        String q_line_id = FormUtil.getParameter(request, "q_line_id");
        String q_contc_id = FormUtil.getParameter(request, "q_contc_id");
        stationCode.setLine_id(q_line_id);
        stationCode.setContc_id(q_contc_id);
        
        //字段回显
        request.setAttribute("q_line_id", q_line_id);
        request.setAttribute("q_contc_id", q_contc_id);
        
        this.getBaseParameters(request, stationCode);
        return stationCode;
    }

    public OperationResult query(HttpServletRequest request, StationCodeMapper stationCodeMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        StationCode queryCondition;
        List<StationCode> resultSet;
        //线路名
        List<PubFlag> lines = pubFlagMapper.getLines();
        //运营商
        List<PubFlag> contcs = pubFlagMapper.getContcs();

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = stationCodeMapper.getStationCodes(queryCondition);
            request.getSession().setAttribute("queryCondition", queryCondition);
            for (StationCode sc : resultSet) {
                if (sc != null) {
                    PubFlag pv = new PubFlag();
                    for (int i = 0; i < lines.size(); i++) {
                        pv = (PubFlag) lines.get(i);
                        if (pv.getCode().equals(sc.getLine_id())) {
                            sc.setLine_name(pv.getCode_text());
                        }
                        if (pv.getCode().equals(sc.getBelong_line_id())) {
                            sc.setBelong_line_name(pv.getCode_text());
                        }
                    }
                    for (int i = 0; i < contcs.size(); i++) {
                        pv = (PubFlag) contcs.get(i);
                         if (pv.getCode().equals(sc.getContc_id())) {
                            sc.setContc_name(pv.getCode_text());
                        }
                    }
                }
            }
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    /**
     * 带查询条件查询线路记录
     * @param request
     * @param stationCodeMapper
     * @param opLogMapper
     * @param opResult
     * @return
     * @throws Exception 
     */
    public OperationResult queryForOp(HttpServletRequest request, StationCodeMapper stationCodeMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        StationCode stationCode;
        List<StationCode> resultSet;

        try {
            stationCode = this.getQueryConditionForOp(request);
//            if (stationCode == null) {
//                return null;
//            }
            resultSet = stationCodeMapper.getStationCodes(stationCode);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;
    }

    public OperationResult modify(HttpServletRequest request, StationCodeMapper stationCodeMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        StationCode stationCode = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        if(CharUtil.getDBLenth(stationCode.getChinese_name())>40){
            rmsg.addMessage("车站中文名称最大值不能超过40位(中文字符为三位)。");
            return rmsg;
        }
        
        String oldScIp = "";
        //‘车站计算机IP地址’唯一性的校验；
        StationCode sc = new StationCode();
        sc.setLine_id(stationCode.getLine_id());
        sc.setStation_id(stationCode.getStation_id());
        sc.setRecord_flag(stationCode.getRecord_flag());
        sc.setVersion_no(stationCode.getVersion_no());
        List<StationCode> stationCodes = stationCodeMapper.getStationCodes(sc);
        if (stationCodes != null && stationCodes.size() > 0) {
            oldScIp = stationCodes.get(0).getSc_ip();
        }
        
        String scIp = stationCode.getSc_ip();
        if(!oldScIp.equals(scIp)){
            sc = new StationCode();
            sc.setSc_ip(scIp);
            sc.setRecord_flag(stationCode.getRecord_flag());
            sc.setVersion_no(stationCode.getVersion_no());
            stationCodes = stationCodeMapper.getStationCodes(sc);
            if (stationCodes != null && stationCodes.size() > 0) {
                rmsg.addMessage("修改失败，\"车站计算机IP地址\"已存在");   
                return rmsg;
            }
        }
      
       
        try {
            n = this.modifyByTrans(request, stationCodeMapper, pvMapper, stationCode);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(n), opLogMapper);
        
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }
    
    @RequestMapping("/stationCodeExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
	List results = this.getBufferElements(request);
	String expAllFields = request.getParameter("expAllFields");
	List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields, results);
	ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private boolean existRecord(StationCode po, StationCodeMapper stationCodeMapper, OperationLogMapper opLogMapper) {
        List<StationCode> list = stationCodeMapper.getStationCodeById(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private int addByTrans(HttpServletRequest request, StationCodeMapper stationCodeMapper, PrmVersionMapper pvMapper, StationCode stationCode) throws Exception {
//        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {
//            txMgr = DBUtil.getDataSourceTransactionManager(request);
//            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            status = txMgr.getTransaction(this.def);
            n = stationCodeMapper.addStationCode(stationCode);
            pvMapper.modifyPrmVersionForDraft(stationCode);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private void getVersionNoForSubmit(StationCode po, String versionNoMax) {
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

    

    public int submitByTrans(HttpServletRequest request, StationCodeMapper stationCodeMapper, PrmVersionMapper pvMapper, StationCode stationCode, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        String versionNoMax;
        int n = 0;
        try {

            status = txMgr.getTransaction(def);

            //旧的未来或当前参数数据做删除标志
            stationCodeMapper.submitToOldFlag(stationCode);
            //生成新的未来或当前参数的版本号
            versionNoMax = pvMapper.selectPrmVersionForSubmit(stationCode);
            this.getVersionNoForSubmit(stationCode, versionNoMax);

            //添加新的未来或当前参数的数据记录
            n = stationCodeMapper.submitFromDraftToCurOrFur(stationCode);
            // 重新生成参数表中的未来或当前参数记录
            pvMapper.modifyPrmVersionForSubmit(stationCode);
            pvMapper.addPrmVersion(stationCode);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    

    private int cloneByTrans(HttpServletRequest request, StationCodeMapper stationCodeMapper, PrmVersionMapper pvMapper, StationCode stationCode, PrmVersion prmVersion) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);

            //删除草稿版本
            stationCodeMapper.deleteStationCodeForClone(stationCode);
            //未来或当前参数克隆成草稿版本
            n = stationCodeMapper.cloneFromCurOrFurToDraft(stationCode);
            //更新参数版本索引信息
            pvMapper.modifyPrmVersionForDraft(stationCode);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult submit(HttpServletRequest request, StationCodeMapper stationCodeMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        StationCode po = this.getReqAttributeForSubmit(request);
        PrmVersion prmVersion = null;
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.submitByTrans(request, stationCodeMapper, pvMapper, po, prmVersion);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_SUBMIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_SUBMIT, request, LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()), opLogMapper);

        rmsg.addMessage(LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()));

        return rmsg;
    }

    public OperationResult clone(HttpServletRequest request, StationCodeMapper stationCodeMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        StationCode po = this.getReqAttributeForClone(request);
        PrmVersion prmVersion = null;
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.cloneByTrans(request, stationCodeMapper, pvMapper, po, prmVersion);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CLONE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_CLONE, request, LogConstant.cloneSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.cloneSuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(HttpServletRequest request, StationCodeMapper stationCodeMapper, PrmVersionMapper pvMapper, StationCode stationCode) throws Exception {
//        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {
//            txMgr = DBUtil.getDataSourceTransactionManager(request);
//            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            status = txMgr.getTransaction(this.def);
            n = stationCodeMapper.modifyStationCode(stationCode);
            pvMapper.modifyPrmVersionForDraft(stationCode);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private int deleteByTrans(HttpServletRequest request, StationCodeMapper stationCodeMapper, PrmVersionMapper pvMapper, Vector<StationCode> stationCodes, StationCode stationCode) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (StationCode po : stationCodes) {
                n += stationCodeMapper.deleteStationCode(po);
            }
            pvMapper.modifyPrmVersionForDraft(stationCode);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult add(HttpServletRequest request, StationCodeMapper stationCodeMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        StationCode stationCode = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        if(CharUtil.getDBLenth(stationCode.getChinese_name())>40){
            rmsg.addMessage("车站中文名称最大值不能超过40位(中文字符为三位)。");
            return rmsg;
        }
        StationCode sc = new StationCode();
        sc.setLine_id(stationCode.getLine_id());
        sc.setStation_id(stationCode.getStation_id());
        sc.setRecord_flag(stationCode.getRecord_flag());
        sc.setVersion_no(stationCode.getVersion_no());
//        List<StationCode> stationCodes = new ArrayList<>();
//        stationCodes = stationCodeMapper.getStationCodes(sc);
        List<StationCode> stationCodes = stationCodeMapper.getStationCodes(sc);
        if (stationCodes != null && stationCodes.size() > 0) {
            rmsg.addMessage("增加失败，记录已存在");   // 线路ID + 车站ID + 版本号 + 版本标志   组合判断记录是否已存在
            return rmsg;
        }
        
        //‘车站计算机IP地址’唯一性的校验；
        sc = new StationCode();
        sc.setSc_ip(stationCode.getSc_ip());
        sc.setRecord_flag(stationCode.getRecord_flag());
        sc.setVersion_no(stationCode.getVersion_no());
        stationCodes = stationCodeMapper.getStationCodes(sc);
        if (stationCodes != null && stationCodes.size() > 0) {
            rmsg.addMessage("增加失败，\"车站计算机IP地址\"已存在");   
            return rmsg;
        }
        
        try {
            n = this.addByTrans(request, stationCodeMapper, pvMapper, stationCode);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, LogConstant.addSuccessMsg(n), opLogMapper);
        
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult delete(HttpServletRequest request, StationCodeMapper stationCodeMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<StationCode> stationCodes = this.getReqAttributeForDelete(request);
        StationCode stationCode = new StationCode();
        this.getBaseParameters(request, stationCode);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, stationCodeMapper, pvMapper, stationCodes, stationCode);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public StationCode getReqAttribute(HttpServletRequest request) {
        StationCode stationCode = new StationCode();

        stationCode.setLine_id(FormUtil.getParameter(request, "d_line_id"));
        stationCode.setStation_id(FormUtil.getParameter(request,"d_station_id"));
        stationCode.setChinese_name(FormUtil.getParameter(request,"d_chinese_name"));
        stationCode.setEnglish_name(FormUtil.getParameter(request,"d_english_name"));
        stationCode.setContc_id(FormUtil.getParameter(request,"d_contc_id"));
        stationCode.setSc_ip(FormUtil.getParameter(request,"d_sc_ip"));
        stationCode.setLc_ip(FormUtil.getParameter(request,"d_lc_ip"));
        stationCode.setSequence(FormUtil.getParameter(request,"d_sequence"));
        stationCode.setBelong_line_id(FormUtil.getParameter(request,"d_belong_line_id"));
        stationCode.setUygur_name(FormUtil.getParameter(request,"d_uygur_name"));
       
        getBaseParameters(request, stationCode);
        
        return stationCode;
    }

    public StationCode getReqAttributeForSubmit(HttpServletRequest request) {
        StationCode sc = new StationCode();

        getBaseParameters(request, sc);
        getBaseParametersForSubmit(request, sc);
        
        return sc;
        
    }

    public StationCode getReqAttributeForClone(HttpServletRequest request) {
        StationCode stationCode = new StationCode();

        this.getBaseParameters(request, stationCode);

        return stationCode;
    }

    private Vector<StationCode> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<StationCode> stationCodes = new Vector();
        StationCode sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getStationCode(strIds, "#");
            stationCodes.add(sd);
        }
        return stationCodes;

    }

    private StationCode getStationCode(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        StationCode stationCode = new StationCode();;
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                stationCode.setLine_id(tmp);
                continue;
            }
            if (i == 2) {
                stationCode.setStation_id(tmp);
                continue;
            }
            if (i == 3) {
                stationCode.setVersion_no(tmp);
                continue;
            }
            if (i == 4) {
                stationCode.setRecord_flag(tmp);
                continue;
            }
        }
        return stationCode;

    }

    private Vector<StationCode> getReqAttributeForDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<StationCode> selectedItems = this.getDeleteIDs(selectIds);

        return selectedItems;
    }
    
    private String getRecordFlagOld( StationCode lineCode){
         String recordFlagSubmit = lineCode.getRecord_flag_submit();
         String recordFlagOld ="";
        if (recordFlagSubmit.equals(ParameterConstant.RECORD_FLAG_CURRENT)) {
            recordFlagOld = ParameterConstant.RECORD_FLAG_HISTORY;
        }
        if (recordFlagSubmit.equals(ParameterConstant.RECORD_FLAG_FUTURE)) {
            recordFlagOld = ParameterConstant.RECORD_FLAG_DELETED;
        }
        return recordFlagOld;
    }
    
    private String getVersionValidDate(StationCode stationCode){
	String verDateBegin =stationCode.getBegin_time_submit();
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
