/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.FareConf;
import com.goldsign.acc.app.prminfo.mapper.FareConfMapper;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.app.prminfo.mapper.PrmVersionMapper;
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
 *
 * @author mqf
 */
@Controller
public class FareConfController extends PrmBaseController {

    @Autowired
    private FareConfMapper fareConfMapper;


    @RequestMapping("/FareConf")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/fare_conf.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.fareConfMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.fareConfMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.fareConfMapper, this.prmVersionMapper, this.operationLogMapper);
                }

                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.fareConfMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_SUBMIT))//提交操作
                {
//                    opResult = this.submit(request, this.fareConfMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_CLONE))//克隆操作
                {
                    opResult = this.clone(request, this.fareConfMapper, this.prmVersionMapper, this.operationLogMapper);

                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.fareConfMapper, this.operationLogMapper, opResult);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {CARDMAINS, CARDSUBS, CARDMAIN_SUBS, FARE_TABLES, TIME_CODES, VERSION,FARE_TIME_IDS,FARE_DEAL_IDS};

        this.setPageOptions(attrNames, mv, request, response);//设置下拉菜单等选项值、版本
        this.getResultSetText((List<FareConf>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }

    private void getResultSetText(List<FareConf> resultSet, ModelAndView mv) {
        List<PubFlag> cardMains = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDMAINS);
        List<PubFlag> cardSubs = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDSUBS);
        List<PubFlag> timeCodes = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.TIME_CODES);

        for (FareConf fc : resultSet) {
            if (cardMains != null && !cardMains.isEmpty()) {
                fc.setCard_main_id_text(DBUtil.getTextByCode(fc.getCard_main_id(), cardMains));
            }
            if (cardSubs != null && !cardSubs.isEmpty()) {
                fc.setCard_sub_id_text(DBUtil.getTextByCode(fc.getCard_sub_id(), fc.getCard_main_id(), cardSubs));
            }
            if (timeCodes != null && !timeCodes.isEmpty()) {
                fc.setTime_code_text(DBUtil.getTextByCode(fc.getTime_code(), timeCodes));
            }

        }

    }

    private FareConf getQueryConditionForOp(HttpServletRequest request) {

        FareConf qCon = new FareConf();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }

            qCon.setCard_main_id(FormUtil.getParameter(request, "d_card_main_id"));
            qCon.setCard_sub_id(FormUtil.getParameter(request, "d_card_sub_id"));
            qCon.setTime_code(FormUtil.getParameter(request, "d_time_code"));
            qCon.setFare_table_id(FormUtil.getParameter(request, "d_fare_table_id"));
            
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                
                qCon.setCard_main_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_card_main_id"));
                qCon.setCard_sub_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_card_sub_id"));
                qCon.setTime_code(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_time_code"));
                qCon.setFare_table_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_fare_table_id"));
                
            }
        }
        this.getBaseParameters(request, qCon);
        //当操作为克隆时，查询克隆后的草稿版本
//        if (command.equals(CommandConstant.COMMAND_CLONE)) {
//            qCon.setRecord_flag(ParameterConstant.RECORD_FLAG_DRAFT);
//        }

        return qCon;
    }

    private FareConf getQueryCondition(HttpServletRequest request) {
        FareConf qCon = new FareConf();
        
        qCon.setCard_main_id(FormUtil.getParameter(request, "q_card_main_id"));
        qCon.setCard_sub_id(FormUtil.getParameter(request, "q_card_sub_id"));
        qCon.setTime_code(FormUtil.getParameter(request, "q_time_code"));
        qCon.setFare_table_id(FormUtil.getParameter(request, "q_fare_table_id"));
        qCon.setFare_time_id(FormUtil.getParameter(request, "q_fare_time_id"));
        qCon.setFare_deal_id(FormUtil.getParameter(request, "q_fare_deal_id"));

        this.getBaseParameters(request, qCon);
        return qCon;
    }

    public OperationResult query(HttpServletRequest request, FareConfMapper fcMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        FareConf queryCondition;
        List<FareConf> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = fcMapper.getFareConfs(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult queryForOp(HttpServletRequest request, FareConfMapper fcMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        // OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        FareConf queryCondition;
        List<FareConf> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request);
            if (queryCondition == null) {
                return null;
            }
            resultSet = fcMapper.getFareConfs(queryCondition);
            opResult.setReturnResultSet(resultSet);
            //or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult modify(HttpServletRequest request, FareConfMapper fcMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        FareConf po = this.getReqAttribute(request);
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        
//        String preMsg = "收费配置：" + "主键：" + po.getCard_main_id() + "#" + po.getCard_sub_id() + "#" + po.getTime_code() + ":";
        String preMsg = "收费配置：" + "主键：" + po.getCard_main_id() + "#" + po.getCard_sub_id() + "#" + po.getTime_code() + "#" + po.getFare_table_id() + ":";
        try {
            if (this.existRepeatTimesRecord(po, fcMapper, opLogMapper)) {
                rmsg.addMessage("此票卡类型下，该时间区段的票价配置的次数重复！");
                return rmsg;
            }
            
            n = this.modifyByTrans(request, fcMapper, pvMapper, po);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(FareConf po, FareConfMapper fcMapper, OperationLogMapper opLogMapper) {
        List<FareConf> list = fcMapper.getFareConfById(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private int addByTrans(HttpServletRequest request, FareConfMapper fcMapper, PrmVersionMapper pvMapper, FareConf po) throws Exception {
//        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {

//            txMgr = DBUtil.getDataSourceTransactionManager(request);
//            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            status = txMgr.getTransaction(this.def);
            n = fcMapper.addFareConf(po);
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

    private void getVersionNoForSubmit(FareConf po, String versionNoMax) {
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

    

    public int submitByTrans(HttpServletRequest request, FareConfMapper fcMapper, PrmVersionMapper pvMapper, FareConf po, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        List<PrmVersion> prmVersions;
        String versionNoMax;
        int n = 0;
        int n1 = 0;
       // String test=null;
        try {

            status = txMgr.getTransaction(def);
            

            //旧的未来或当前参数数据做删除标志
            fcMapper.submitToOldFlag(po);
            //生成新的未来或当前参数的版本号
            versionNoMax = pvMapper.selectPrmVersionForSubmit(po);
            this.getVersionNoForSubmit(po, versionNoMax);

            //添加新的未来或当前参数的数据记录
            n = fcMapper.submitFromDraftToCurOrFur(po);
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

    

    private int cloneByTrans(HttpServletRequest request, FareConfMapper fcMapper, PrmVersionMapper pvMapper, FareConf po, PrmVersion prmVersion) throws Exception {
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
            fcMapper.deleteFareConfsForClone(po);
            //未来或当前参数克隆成草稿版本
            n = fcMapper.cloneFromCurOrFurToDraft(po);
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

    public OperationResult submit(HttpServletRequest request, FareConfMapper fcMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        FareConf po = this.getReqAttributeForSubmit(request);
        FareConf prmVersion = null;
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.submitByTrans(request, fcMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_SUBMIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_SUBMIT, request, LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()), opLogMapper);

        rmsg.addMessage(LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()));

        return rmsg;
    }

    public OperationResult clone(HttpServletRequest request, FareConfMapper fcMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        FareConf po = this.getReqAttributeForClone(request);
        FareConf prmVersion = null;
       // User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.cloneByTrans(request, fcMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CLONE, e, opLogMapper);
        }
        //当前还是未来？
        logUtil.logOperation(CommandConstant.COMMAND_CLONE, request, LogConstant.cloneSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.cloneSuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(HttpServletRequest request, FareConfMapper fcMapper, PrmVersionMapper pvMapper, FareConf po) throws Exception {
//        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {

//            txMgr = DBUtil.getDataSourceTransactionManager(request);
//            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            status = txMgr.getTransaction(this.def);
            n = fcMapper.modifyFareConf(po);
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

    private int deleteByTrans(HttpServletRequest request, FareConfMapper fcMapper, PrmVersionMapper pvMapper, Vector<FareConf> pos, FareConf prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {


            status = txMgr.getTransaction(this.def);
            for (FareConf po : pos) {
                n += fcMapper.deleteFareConf(po);
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

    public OperationResult add(HttpServletRequest request, FareConfMapper fcMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        FareConf po = this.getReqAttribute(request);
       // User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "收费配置：" + "主键：" + po.getCard_main_id() + "#" + po.getCard_sub_id() + "#" + po.getTime_code() + "#" + po.getFare_table_id() + ":";
        try {
            if (this.existRecord(po, fcMapper, opLogMapper)) {
                rmsg.addMessage("此票卡类型下，该时间区段的票价配置已存在！");
                return rmsg;
            }
            
            if (this.existRepeatTimesRecord(po, fcMapper, opLogMapper)) {
                rmsg.addMessage("此票卡类型下，该时间区段的票价配置的次数重复！");
                return rmsg;
            }
            

            // n = fcMapper.addFareConf(po);
            n = this.addByTrans(request, fcMapper, pvMapper, po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);
        
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult delete(HttpServletRequest request, FareConfMapper fcMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<FareConf> pos = this.getReqAttributeForDelete(request);
        FareConf prmVo = new FareConf();
        this.getBaseParameters(request, prmVo);
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, fcMapper, pvMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public FareConf getReqAttribute(HttpServletRequest request) {
        FareConf po = new FareConf();

        po.setCard_main_id(FormUtil.getParameter(request, "d_card_main_id"));
        po.setCard_sub_id(FormUtil.getParameter(request, "d_card_sub_id"));
        po.setTime_code(FormUtil.getParameter(request, "d_time_code"));
        po.setFare_table_id(FormUtil.getParameter(request, "d_fare_table_id"));
        if (request.getParameter("d_water_no") != null && !request.getParameter("d_water_no").isEmpty()) { //water_no
            po.setWater_no(Long.parseLong(request.getParameter("d_water_no")));
        }
        po.setFare_time_id(FormUtil.getParameter(request, "d_fare_time_id"));
        po.setFare_deal_id(FormUtil.getParameter(request, "d_fare_deal_id"));
                
        this.getBaseParameters(request, po);

        return po;
    }

    public FareConf getReqAttributeForSubmit(HttpServletRequest request) {
        FareConf po = new FareConf();

        this.getBaseParameters(request, po);
        this.getBaseParametersForSubmit(request, po);

        return po;
    }

    public FareConf getReqAttributeForClone(HttpServletRequest request) {
        FareConf po = new FareConf();

        this.getBaseParameters(request, po);

        return po;
    }

    private Vector<FareConf> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<FareConf> fcs = new Vector();
        FareConf fc;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            fc = this.getFareConf(strIds, "#");
            fcs.add(fc);
        }
        return fcs;

    }

    private FareConf getFareConf(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        FareConf fc = new FareConf();;
        Vector<FareConf> fcs = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                fc.setTime_code(tmp);
                continue;
            }
            if (i == 2) {
                fc.setCard_main_id(tmp);
                continue;
            }
            if (i == 3) {
                fc.setCard_sub_id(tmp);
                continue;
            }
            if (i == 4) {
                fc.setFare_table_id(tmp);

                continue;
            }
            if (i == 5) {
                fc.setVersion_no(tmp);
                continue;
            }
            if (i == 6) {
                fc.setRecord_flag(tmp);
                continue;
            }

        }
        return fc;

    }

    private Vector<FareConf> getReqAttributeForDelete(HttpServletRequest request) {
        FareConf po = new FareConf();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<FareConf> selectedItems = this.getDeleteIDs(selectIds);

        //this.getBaseParameters(request, po);
        return selectedItems;
    }
    
    private boolean existRepeatTimesRecord(FareConf po, FareConfMapper fcMapper, OperationLogMapper opLogMapper) {
        int count = fcMapper.getFareConfForRepeatTimes(po);
        if (count <= 0) {
            return false;
        }
        return true;

    }
    
    @RequestMapping("/FareConfExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElements(request);
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields,results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

}
