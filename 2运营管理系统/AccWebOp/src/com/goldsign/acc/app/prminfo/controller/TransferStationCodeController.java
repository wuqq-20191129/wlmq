package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.TransferStationCode;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.app.prminfo.entity.StationCode;
import com.goldsign.acc.app.prminfo.mapper.PrmVersionMapper;
import com.goldsign.acc.app.prminfo.mapper.StationCodeMapper;
import com.goldsign.acc.app.prminfo.mapper.TransferStationCodeMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.constant.ParameterConstant;

import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.mapper.PubFlagMapper;
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
 * 运营资料--线路车站--车站表
 * @author xiaowu
 * @version 20170612
 */
@Controller
public class TransferStationCodeController extends PrmBaseController {

    @Autowired
    private TransferStationCodeMapper transferStationCodeMapper;
    
    @Autowired
    private StationCodeMapper stationCodeMapper;

    @Autowired
    private PubFlagMapper pubFlagMapper;

    @RequestMapping("/TransferStationCode")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/transfer_station_code.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.transferStationCodeMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.transferStationCodeMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.transferStationCodeMapper, this.prmVersionMapper, this.operationLogMapper);
                }

                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.transferStationCodeMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_SUBMIT))//提交操作
                {
                    opResult = this.submit(request, this.transferStationCodeMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_CLONE))//克隆操作
                {
                    opResult = this.clone(request, this.transferStationCodeMapper, this.prmVersionMapper, this.operationLogMapper);
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
                    this.queryForOp(request, this.transferStationCodeMapper, this.operationLogMapper, opResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //初始化下拉框
        String[] attrNames = {LINES, STATIONS, LINE_STATIONS, DEV_TYPES, MERCHANTS, VERSION};
        
        //初始化 查询下拉框
        List<PubFlag> options = new ArrayList<PubFlag>();
        TransferStationCode ts = new TransferStationCode();
        this.getBaseParameters(request, ts);
        options = transferStationCodeMapper.getTransferStationCodeLines(ts);
        mv.addObject("queryLines", options);
        
        List<PubFlag> options1 = new ArrayList<PubFlag>();
        options1 = transferStationCodeMapper.getTransferStationCodeStations(ts);
        String lineStations = FormUtil.getLineStations(options1);
        mv.addObject("queryLineStaions", lineStations);
        

        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.getResultSetText((List<TransferStationCode>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }
    
    private void getResultSetText(List<TransferStationCode> resultSet, ModelAndView mv) {
        //线路名
        List<PubFlag> lines = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.LINES);
        //车站名
        List<StationCode> stationCodes = stationCodeMapper.getStationCodesRecordFlagIs3();
        for (TransferStationCode transferStationCode : resultSet) {
            if (transferStationCode != null) {
                PubFlag pv = new PubFlag();
                for (int i = 0; i < lines.size(); i++) {
                    pv = (PubFlag) lines.get(i);
                    if (pv.getCode().equals(transferStationCode.getLine_id())) {
                        transferStationCode.setLine_name(pv.getCode_text());
                    }
                    if (pv.getCode().equals(transferStationCode.getTransfer_line_id())) {
                        transferStationCode.setTransfer_line_name(pv.getCode_text());
                    }
                }
                StationCode stationCode = new StationCode();
                for (int i = 0; i < stationCodes.size(); i++) {
                    stationCode = stationCodes.get(i);
                    if (stationCode.getStation_id().equals(transferStationCode.getStation_id()) && stationCode.getLine_id().equals(transferStationCode.getLine_id())) {
                        transferStationCode.setStation_name(stationCode.getChinese_name());
                    }
                    if (stationCode.getStation_id().equals(transferStationCode.getTransfer_station_id()) && stationCode.getLine_id().equals(transferStationCode.getTransfer_line_id())) {
                        transferStationCode.setTransfer_station_name(stationCode.getChinese_name());
                    }
                }
            }
        }
    }
    
    private TransferStationCode getQueryConditionForOp(HttpServletRequest request) {

        TransferStationCode transferStationCode = new TransferStationCode();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues"); 
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                this.getBaseParameters(request, transferStationCode);
                return transferStationCode;
            }
            transferStationCode.setLine_id(FormUtil.getParameter(request, "d_line_id"));
            transferStationCode.setStation_id(FormUtil.getParameter(request,"d_station_id"));
            transferStationCode.setTransfer_line_id(FormUtil.getParameter(request, "d_transfer_line_id"));
            transferStationCode.setTransfer_station_id(FormUtil.getParameter(request,"d_transfer_station_id"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                transferStationCode.setLine_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_line_id"));
                transferStationCode.setStation_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_station_id"));
                transferStationCode.setTransfer_line_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_line_id_2"));
                transferStationCode.setTransfer_station_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_station_id_2"));
            }
        }
        this.getBaseParameters(request, transferStationCode);
        //当操作为克隆时，查询克隆后的草稿版本
        if (command.equals(CommandConstant.COMMAND_CLONE)) {
            transferStationCode.setRecord_flag(ParameterConstant.RECORD_FLAG_DRAFT);
        }

        return transferStationCode;
    }

    //获取查询参数
    private TransferStationCode getQueryCondition(HttpServletRequest request) {
        TransferStationCode transferStationCode = new TransferStationCode();
        String q_line_id = FormUtil.getParameter(request, "q_line_id");
        String q_station_id = FormUtil.getParameter(request, "q_station_id");
        String q_line_id_2 = FormUtil.getParameter(request, "q_line_id_2");
        String q_station_id_2 = FormUtil.getParameter(request, "q_station_id_2");
        transferStationCode.setLine_id(q_line_id);
        transferStationCode.setStation_id(q_station_id);
        transferStationCode.setTransfer_line_id(q_line_id_2);
        transferStationCode.setTransfer_station_id(q_station_id_2);
        
        
        //字段回显
//        request.setAttribute("q_line_id", q_line_id);
//        request.setAttribute("q_contc_id", q_contc_id);
        
        this.getBaseParameters(request, transferStationCode);
        return transferStationCode;
    }

    public OperationResult query(HttpServletRequest request, TransferStationCodeMapper transferStationCodeMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TransferStationCode queryCondition;
        List<TransferStationCode> resultSet;
        //线路名
        List<PubFlag> lines = pubFlagMapper.getLines();
        //运营商
        List<PubFlag> stations = pubFlagMapper.getStations();

        try {
            queryCondition = this.getQueryCondition(request);
            request.getSession().setAttribute("queryCondition", queryCondition);
            resultSet = transferStationCodeMapper.getTransferStationCodes(queryCondition);
            for (TransferStationCode transferStationCode : resultSet) {
                if (transferStationCode != null) {
                    PubFlag pv = new PubFlag();
                    for (int i = 0; i < lines.size(); i++) {
                        pv = (PubFlag) lines.get(i);
                        if (pv.getCode().equals(transferStationCode.getLine_id())) {
                            transferStationCode.setLine_name(pv.getCode_text());
                        }
                        if (pv.getCode().equals(transferStationCode.getTransfer_line_id())) {
                            transferStationCode.setTransfer_line_name(pv.getCode_text());
                        }
                    }
                    for (int i = 0; i < stations.size(); i++) {
                        pv = (PubFlag) stations.get(i);
                        if (pv.getCode().equals(transferStationCode.getStation_id())) {
                            transferStationCode.setStation_name(pv.getCode_text());
                        }
                        if (pv.getCode().equals(transferStationCode.getTransfer_station_id())) {
                            transferStationCode.setTransfer_station_name(pv.getCode_text());
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
    
    @RequestMapping("/TransferStationCodeExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
	List results = this.getBufferElements(request);
	String expAllFields = request.getParameter("expAllFields");
	List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields, results);
	ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    /**
     * 带查询条件查询记录
     * @param request
     * @param transferStationCodeMapper
     * @param opLogMapper
     * @param opResult
     * @return
     * @throws Exception 
     */
    public OperationResult queryForOp(HttpServletRequest request, TransferStationCodeMapper transferStationCodeMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TransferStationCode transferStationCode;
        List<TransferStationCode> resultSet;

        try {
            transferStationCode = this.getQueryConditionForOp(request);
//            if (transferStationCode == null) {
//                return null;
//            }
            resultSet = transferStationCodeMapper.getTransferStationCodes(transferStationCode);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;
    }

    public OperationResult modify(HttpServletRequest request, TransferStationCodeMapper transferStationCodeMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TransferStationCode transferStationCode = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
       
        try {
            n = this.modifyByTrans(request, transferStationCodeMapper, pvMapper, transferStationCode);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(n), opLogMapper);
        
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(TransferStationCode po, TransferStationCodeMapper transferStationCodeMapper, OperationLogMapper opLogMapper) {
        List<TransferStationCode> list = transferStationCodeMapper.getTransferStationCodeById(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private int addByTrans(HttpServletRequest request, TransferStationCodeMapper transferStationCodeMapper, PrmVersionMapper pvMapper, TransferStationCode transferStationCode) throws Exception {
//        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {
//            txMgr = DBUtil.getDataSourceTransactionManager(request);
//            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            status = txMgr.getTransaction(this.def);
            n = transferStationCodeMapper.addTransferStationCode(transferStationCode);
            pvMapper.modifyPrmVersionForDraft(transferStationCode);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private void getVersionNoForSubmit(TransferStationCode po, String versionNoMax) {
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

    

    public int submitByTrans(HttpServletRequest request, TransferStationCodeMapper transferStationCodeMapper, PrmVersionMapper pvMapper, TransferStationCode transferStationCode, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        String versionNoMax;
        int n = 0;
        try {

            status = txMgr.getTransaction(def);

            //旧的未来或当前参数数据做删除标志
            transferStationCodeMapper.submitToOldFlag(transferStationCode);
            //生成新的未来或当前参数的版本号
            versionNoMax = pvMapper.selectPrmVersionForSubmit(transferStationCode);
            this.getVersionNoForSubmit(transferStationCode, versionNoMax);

            //添加新的未来或当前参数的数据记录
            n = transferStationCodeMapper.submitFromDraftToCurOrFur(transferStationCode);
            // 重新生成参数表中的未来或当前参数记录
            pvMapper.modifyPrmVersionForSubmit(transferStationCode);
            pvMapper.addPrmVersion(transferStationCode);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    

    private int cloneByTrans(HttpServletRequest request, TransferStationCodeMapper transferStationCodeMapper, PrmVersionMapper pvMapper, TransferStationCode transferStationCode, PrmVersion prmVersion) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);

            //删除草稿版本
            transferStationCodeMapper.deleteTransferStationCodeForClone(transferStationCode);
            //未来或当前参数克隆成草稿版本
            n = transferStationCodeMapper.cloneFromCurOrFurToDraft(transferStationCode);
            //更新参数版本索引信息
            pvMapper.modifyPrmVersionForDraft(transferStationCode);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult submit(HttpServletRequest request, TransferStationCodeMapper transferStationCodeMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TransferStationCode po = this.getReqAttributeForSubmit(request);
        PrmVersion prmVersion = null;
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.submitByTrans(request, transferStationCodeMapper, pvMapper, po, prmVersion);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_SUBMIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_SUBMIT, request, LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()), opLogMapper);

        rmsg.addMessage(LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()));

        return rmsg;
    }

    public OperationResult clone(HttpServletRequest request, TransferStationCodeMapper transferStationCodeMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TransferStationCode po = this.getReqAttributeForClone(request);
        PrmVersion prmVersion = null;
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.cloneByTrans(request, transferStationCodeMapper, pvMapper, po, prmVersion);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CLONE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_CLONE, request, LogConstant.cloneSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.cloneSuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(HttpServletRequest request, TransferStationCodeMapper transferStationCodeMapper, PrmVersionMapper pvMapper, TransferStationCode transferStationCode) throws Exception {
//        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {
//            txMgr = DBUtil.getDataSourceTransactionManager(request);
//            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            status = txMgr.getTransaction(this.def);
            n = transferStationCodeMapper.modifyTransferStationCode(transferStationCode);
            pvMapper.modifyPrmVersionForDraft(transferStationCode);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private int deleteByTrans(HttpServletRequest request, TransferStationCodeMapper transferStationCodeMapper, PrmVersionMapper pvMapper, Vector<TransferStationCode> transferStationCodes, TransferStationCode transferStationCode) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TransferStationCode po : transferStationCodes) {
                n += transferStationCodeMapper.deleteTransferStationCode(po);
            }
            pvMapper.modifyPrmVersionForDraft(transferStationCode);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult add(HttpServletRequest request, TransferStationCodeMapper transferStationCodeMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TransferStationCode transferStationCode = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        List<TransferStationCode> transferStationCodes = transferStationCodeMapper.getTransferStationCodes(transferStationCode);
        if (transferStationCodes != null && transferStationCodes.size() > 0) {
            rmsg.addMessage("记录已存在");   // 线路ID + 车站ID + 版本号 + 版本标志   组合判断记录是否已存在
            return rmsg;
        }
        
        try {
            n = this.addByTrans(request, transferStationCodeMapper, pvMapper, transferStationCode);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, LogConstant.addSuccessMsg(n), opLogMapper);
        
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult delete(HttpServletRequest request, TransferStationCodeMapper transferStationCodeMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TransferStationCode> transferStationCodes = this.getReqAttributeForDelete(request);
        TransferStationCode transferStationCode = new TransferStationCode();
        this.getBaseParameters(request, transferStationCode);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, transferStationCodeMapper, pvMapper, transferStationCodes, transferStationCode);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public TransferStationCode getReqAttribute(HttpServletRequest request) {
        TransferStationCode transferStationCode = new TransferStationCode();

        transferStationCode.setLine_id(FormUtil.getParameter(request, "d_line_id"));
        transferStationCode.setStation_id(FormUtil.getParameter(request,"d_station_id"));
        transferStationCode.setTransfer_line_id(FormUtil.getParameter(request,"d_transfer_line_id"));
        transferStationCode.setTransfer_station_id(FormUtil.getParameter(request,"d_transfer_station_id"));
       
        getBaseParameters(request, transferStationCode);
        
        return transferStationCode;
    }

    public TransferStationCode getReqAttributeForSubmit(HttpServletRequest request) {
        TransferStationCode sc = new TransferStationCode();

        getBaseParameters(request, sc);
        getBaseParametersForSubmit(request, sc);
        
        return sc;
        
    }

    public TransferStationCode getReqAttributeForClone(HttpServletRequest request) {
        TransferStationCode transferStationCode = new TransferStationCode();

        this.getBaseParameters(request, transferStationCode);

        return transferStationCode;
    }

    private Vector<TransferStationCode> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TransferStationCode> transferStationCodes = new Vector();
        TransferStationCode sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getTransferStationCode(strIds, "#");
            transferStationCodes.add(sd);
        }
        return transferStationCodes;

    }

    private TransferStationCode getTransferStationCode(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TransferStationCode transferStationCode = new TransferStationCode();;
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                transferStationCode.setLine_id(tmp);
                continue;
            }
            if (i == 2) {
                transferStationCode.setStation_id(tmp);
                continue;
            }
            if (i == 3) {
                transferStationCode.setTransfer_line_id(tmp);
                continue;
            }
            if (i == 4) {
                transferStationCode.setTransfer_station_id(tmp);
                continue;
            }
            if (i == 5) {
                transferStationCode.setVersion_no(tmp);
                continue;
            }
            if (i == 6) {
                transferStationCode.setRecord_flag(tmp);
                continue;
            }
        }
        return transferStationCode;

    }

    private Vector<TransferStationCode> getReqAttributeForDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TransferStationCode> selectedItems = this.getDeleteIDs(selectIds);

        return selectedItems;
    }
    
    private String getRecordFlagOld( TransferStationCode lineCode){
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
    
    private String getVersionValidDate(TransferStationCode transferStationCode){
	String verDateBegin =transferStationCode.getBegin_time_submit();
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
