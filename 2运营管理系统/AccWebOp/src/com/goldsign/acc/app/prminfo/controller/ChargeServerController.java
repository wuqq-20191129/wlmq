package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.ChargeServer;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.app.prminfo.mapper.PrmVersionMapper;
import com.goldsign.acc.app.prminfo.mapper.ChargeServerMapper;
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
import com.goldsign.acc.frame.util.PageControlUtil;
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
 * 充值终端通讯参数
 * @author xiaowu
 * @version 20170615
 */
@Controller
public class ChargeServerController extends PrmBaseController {

    @Autowired
    private ChargeServerMapper chargeServerMapper;

    @Autowired
    private PubFlagMapper pubFlagMapper;

    @RequestMapping("/charge_server")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/charge_server.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.chargeServerMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.chargeServerMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.chargeServerMapper, this.prmVersionMapper, this.operationLogMapper);
                }

                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.chargeServerMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_SUBMIT))//提交操作
                {
                    opResult = this.submit(request, this.chargeServerMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_CLONE))//克隆操作
                {
                    opResult = this.clone(request, this.chargeServerMapper, this.prmVersionMapper, this.operationLogMapper);
                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_CLONE)
                        || command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) 
                        || command.equals(CommandConstant.COMMAND_BACK) || command.equals(CommandConstant.COMMAND_BACKEND)
                        || command.equals(CommandConstant.COMMAND_SUBMIT)) {
                    this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    this.queryForOp(request, this.chargeServerMapper, this.operationLogMapper, opResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //初始化下拉框
        String[] attrNames = {LINES, CHARGE_SERVER_TYPES, STATIONS, LINE_STATIONS, DEV_TYPES, MERCHANTS, VERSION};

        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.getResultSetText((List<ChargeServer>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        if(!command.equals(CommandConstant.COMMAND_CLONE)){
            this.divideResultSet(request, mv, opResult);//结果集分页
        }
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }
    
    private void getResultSetText(List<ChargeServer> resultSet, ModelAndView mv) {
        //主机设备类型
        List<PubFlag> chargeServerTypes = pubFlagMapper.getChargeServerTypes();
        for (ChargeServer chargeServer : resultSet) {
            if (chargeServer != null) {
                PubFlag pv = new PubFlag();
                for (int i = 0; i < chargeServerTypes.size(); i++) {
                    pv = (PubFlag) chargeServerTypes.get(i);
                    if (pv.getCode().equals(chargeServer.getServer_level())) {
                        chargeServer.setServer_lever_name(pv.getCode_text());
                    }
                }
            }
        }
    }
    
    private ChargeServer getQueryConditionForOp(HttpServletRequest request) {

        ChargeServer chargeServer = new ChargeServer();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues"); 
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                this.getBaseParameters(request, chargeServer);
                return chargeServer;	
            }
            chargeServer.setServer_ip(FormUtil.getParameter(request, "d_server_ip"));
            chargeServer.setServer_port(FormUtil.getParameter(request,"d_server_port"));
            chargeServer.setServer_desc(FormUtil.getParameter(request, "d_server_desc"));
            chargeServer.setServer_level(FormUtil.getParameter(request,"d_server_level"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                chargeServer.setServer_desc(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_server_desc"));
            }
        }
        this.getBaseParameters(request, chargeServer);
        //当操作为克隆时，查询克隆后的草稿版本
//        if (command.equals(CommandConstant.COMMAND_CLONE)) {
//            chargeServer.setRecord_flag(ParameterConstant.RECORD_FLAG_DRAFT);
//        }

        return chargeServer;
    }

    //获取查询参数
    private ChargeServer getQueryCondition(HttpServletRequest request) {
        ChargeServer chargeServer = new ChargeServer();
        String q_server_desc = FormUtil.getParameter(request, "q_server_desc");
        chargeServer.setServer_desc(q_server_desc);
        
        request.setAttribute("q_server_desc", q_server_desc);
        
        this.getBaseParameters(request, chargeServer);
        return chargeServer;
    }

    public OperationResult query(HttpServletRequest request, ChargeServerMapper chargeServerMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        ChargeServer queryCondition;
        List<ChargeServer> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            //设备描述  使用模糊查询
            queryCondition.setServer_desc("%" + queryCondition.getServer_desc() + "%");
            request.getSession().setAttribute("queryCondition", queryCondition);
            resultSet = this.chargeServerMapper.getChargeServersLikeDesc(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }
    
    @RequestMapping("/ChargeServerExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
	List results = this.getBufferElements(request);
	String expAllFields = request.getParameter("expAllFields");
	List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields, results);
	ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    /**
     * 带查询条件查询记录
     * @param request
     * @param chargeServerMapper
     * @param opLogMapper
     * @param opResult
     * @return
     * @throws Exception 
     */
    public OperationResult queryForOp(HttpServletRequest request, ChargeServerMapper chargeServerMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        ChargeServer chargeServer;
        List<ChargeServer> resultSet;

        try {
            chargeServer = this.getQueryConditionForOp(request);
//            if (chargeServer == null) {
//                return null;
//            }
            resultSet = chargeServerMapper.getChargeServers(chargeServer);
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

    public OperationResult modify(HttpServletRequest request, ChargeServerMapper chargeServerMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        ChargeServer chargeServer = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
         ChargeServer chargeServerTemp = new ChargeServer();
        chargeServerTemp.setServer_desc(chargeServer.getServer_desc());
        chargeServerTemp.setRecord_flag(ParameterConstant.RECORD_FLAG_DRAFT);
        List<ChargeServer> chargeServers = chargeServerMapper.getChargeServers(chargeServerTemp);
        if (chargeServers != null && chargeServers.size() > 0) {
            rmsg.setReturnResultSet(chargeServers);
            rmsg.addMessage("修改失败，已存在相同“设备描述”的充值终端通讯参数！");   
            return rmsg;
        }
       
        try {
            n = this.modifyByTrans(request, chargeServerMapper, pvMapper, chargeServer);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(n), opLogMapper);
        
        List<ChargeServer> chargeServerResult = new ArrayList<ChargeServer>();
        chargeServerResult.add(chargeServer);
        rmsg.setReturnResultSet(chargeServerResult);
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(ChargeServer po, ChargeServerMapper chargeServerMapper, OperationLogMapper opLogMapper) {
        List<ChargeServer> list = chargeServerMapper.getChargeServerById(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private int addByTrans(HttpServletRequest request, ChargeServerMapper chargeServerMapper, PrmVersionMapper pvMapper, ChargeServer chargeServer) throws Exception {
//        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {
//            txMgr = DBUtil.getDataSourceTransactionManager(request);
//            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            status = txMgr.getTransaction(this.def);
            n = chargeServerMapper.addChargeServer(chargeServer);
            pvMapper.modifyPrmVersionForDraft(chargeServer);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private void getVersionNoForSubmit(ChargeServer po, String versionNoMax) {
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

    

    public int submitByTrans(HttpServletRequest request, ChargeServerMapper chargeServerMapper, PrmVersionMapper pvMapper, ChargeServer chargeServer, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        String versionNoMax;
        int n = 0;
        try {

            status = txMgr.getTransaction(def);

            //旧的未来或当前参数数据做删除标志
            chargeServerMapper.submitToOldFlag(chargeServer);
            //生成新的未来或当前参数的版本号
            versionNoMax = pvMapper.selectPrmVersionForSubmit(chargeServer);
            this.getVersionNoForSubmit(chargeServer, versionNoMax);

            //添加新的未来或当前参数的数据记录
            n = chargeServerMapper.submitFromDraftToCurOrFur(chargeServer);
            // 重新生成参数表中的未来或当前参数记录
            pvMapper.modifyPrmVersionForSubmit(chargeServer);
            pvMapper.addPrmVersion(chargeServer);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    

    private int cloneByTrans(HttpServletRequest request, ChargeServerMapper chargeServerMapper, PrmVersionMapper pvMapper, ChargeServer chargeServer, PrmVersion prmVersion) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);

            //删除草稿版本
            chargeServerMapper.deleteChargeServerForClone(chargeServer);
            //未来或当前参数克隆成草稿版本
            n = chargeServerMapper.cloneFromCurOrFurToDraft(chargeServer);
            //更新参数版本索引信息
            pvMapper.modifyPrmVersionForDraft(chargeServer);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult submit(HttpServletRequest request, ChargeServerMapper chargeServerMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        ChargeServer po = this.getReqAttributeForSubmit(request);
        PrmVersion prmVersion = null;
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.submitByTrans(request, chargeServerMapper, pvMapper, po, prmVersion);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_SUBMIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_SUBMIT, request, LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()), opLogMapper);

        rmsg.addMessage(LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()));

        return rmsg;
    }

    public OperationResult clone(HttpServletRequest request, ChargeServerMapper chargeServerMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        ChargeServer po = this.getReqAttributeForClone(request);
        PrmVersion prmVersion = null;
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.cloneByTrans(request, chargeServerMapper, pvMapper, po, prmVersion);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CLONE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_CLONE, request, LogConstant.cloneSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.cloneSuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(HttpServletRequest request, ChargeServerMapper chargeServerMapper, PrmVersionMapper pvMapper, ChargeServer chargeServer) throws Exception {
//        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {
//            txMgr = DBUtil.getDataSourceTransactionManager(request);
//            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            status = txMgr.getTransaction(this.def);
            n = chargeServerMapper.modifyChargeServer(chargeServer);
            pvMapper.modifyPrmVersionForDraft(chargeServer);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private int deleteByTrans(HttpServletRequest request, ChargeServerMapper chargeServerMapper, PrmVersionMapper pvMapper, Vector<ChargeServer> chargeServers, ChargeServer chargeServer) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (ChargeServer po : chargeServers) {
                n += chargeServerMapper.deleteChargeServer(po);
            }
            pvMapper.modifyPrmVersionForDraft(chargeServer);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult add(HttpServletRequest request, ChargeServerMapper chargeServerMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        ChargeServer chargeServer = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        ChargeServer chargeServerTemp = new ChargeServer();
        chargeServerTemp.setServer_ip(chargeServer.getServer_ip());
        chargeServerTemp.setServer_port(chargeServer.getServer_port());
        chargeServerTemp.setServer_level(chargeServer.getServer_level());
        chargeServerTemp.setServer_desc(chargeServer.getServer_desc());
        chargeServerTemp.setRecord_flag(ParameterConstant.RECORD_FLAG_DRAFT);
        List<ChargeServer> chargeServers = chargeServerMapper.checkChargeServers(chargeServerTemp);
        if (chargeServers != null && chargeServers.size() > 0) {
            String errStr = "";
            for(ChargeServer cs : chargeServers){
                if(chargeServer.getServer_ip().equals(cs.getServer_ip())){
                    errStr = "设备IP";
                    break;
                }
                //20180622 开放 端口号的校验
//                if(chargeServer.getServer_port().equals(cs.getServer_port())){
//                    errStr = "设备端口";
//                    break;
//                }
                if(chargeServer.getServer_desc().equals(cs.getServer_desc())){
                    errStr = "设备描述";
                    break;
                }
                if(chargeServer.getServer_level().equals(cs.getServer_level())){
                    errStr = "优先级";
                    break;
                }
            }
            
            rmsg.setReturnResultSet(chargeServers);
            rmsg.addMessage("增加失败，已存在相同“"+ errStr +"”的充值终端通讯参数！");   
            return rmsg;
        }
        
        try {
            n = this.addByTrans(request, chargeServerMapper, pvMapper, chargeServer);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, LogConstant.addSuccessMsg(n), opLogMapper);
        
        List<ChargeServer> chargeServerResult = new ArrayList<ChargeServer>();
        chargeServerResult.add(chargeServer);
        rmsg.setReturnResultSet(chargeServerResult);
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult delete(HttpServletRequest request, ChargeServerMapper chargeServerMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<ChargeServer> chargeServers = this.getReqAttributeForDelete(request);
        ChargeServer chargeServer = new ChargeServer();
        this.getBaseParameters(request, chargeServer);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, chargeServerMapper, pvMapper, chargeServers, chargeServer);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public ChargeServer getReqAttribute(HttpServletRequest request) {
        ChargeServer chargeServer = new ChargeServer();

        chargeServer.setServer_ip(FormUtil.getParameter(request, "d_server_ip"));
        chargeServer.setServer_port(FormUtil.getParameter(request,"d_server_port"));
        chargeServer.setServer_desc(FormUtil.getParameter(request, "d_server_desc"));
        chargeServer.setServer_level(FormUtil.getParameter(request,"d_server_level"));
       
        getBaseParameters(request, chargeServer);
        
        return chargeServer;
    }

    public ChargeServer getReqAttributeForSubmit(HttpServletRequest request) {
        ChargeServer sc = new ChargeServer();

        getBaseParameters(request, sc);
        getBaseParametersForSubmit(request, sc);
        
        return sc;
        
    }

    public ChargeServer getReqAttributeForClone(HttpServletRequest request) {
        ChargeServer chargeServer = new ChargeServer();

        this.getBaseParameters(request, chargeServer);

        return chargeServer;
    }

    private Vector<ChargeServer> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<ChargeServer> chargeServers = new Vector();
        ChargeServer sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getChargeServer(strIds, "#");
            chargeServers.add(sd);
        }
        return chargeServers;

    }

    private ChargeServer getChargeServer(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        ChargeServer chargeServer = new ChargeServer();;
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                chargeServer.setServer_ip(tmp);
                continue;
            }
            if (i == 2) {
                chargeServer.setServer_port(tmp);
                continue;
            }
            if (i == 3) {
                chargeServer.setVersion_no(tmp);
                continue;
            }
            if (i == 4) {
                chargeServer.setRecord_flag(tmp);
                continue;
            }
            if (i == 5) {
                chargeServer.setServer_level(tmp);
                continue;
            }
        }
        return chargeServer;

    }

    private Vector<ChargeServer> getReqAttributeForDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<ChargeServer> selectedItems = this.getDeleteIDs(selectIds);

        return selectedItems;
    }
    
    private String getRecordFlagOld( ChargeServer lineCode){
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
    
    private String getVersionValidDate(ChargeServer chargeServer){
	String verDateBegin =chargeServer.getBegin_time_submit();
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
