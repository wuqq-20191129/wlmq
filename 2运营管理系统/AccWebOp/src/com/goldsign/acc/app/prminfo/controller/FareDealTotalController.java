/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.FareDealTotal;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.app.prminfo.mapper.FareDealTotalMapper;
import com.goldsign.acc.app.prminfo.mapper.PrmVersionMapper;
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
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.vo.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * @author mh
 */
@Controller
public class FareDealTotalController extends PrmBaseController {

    @Autowired
    private FareDealTotalMapper fareDealTotalMapper;

    @RequestMapping("/FareDealTotal")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/fare_deal_total.jsp");
        FareDealTotal pmb = new FareDealTotal();
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        List<FareDealTotal> agentList = new ArrayList<FareDealTotal>();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.fareDealTotalMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.fareDealTotalMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.fareDealTotalMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.fareDealTotalMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_SUBMIT))//提交操作
                {
                    opResult = this.submit(request, this.fareDealTotalMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_CLONE))//克隆操作
                {
                    opResult = this.clone(request, this.fareDealTotalMapper, this.prmVersionMapper, this.operationLogMapper);

                }
//                if (command.equals(CommandConstant.COMMAND_QUERY) || command.equals(CommandConstant.COMMAND_LIST)) {
//                    opResult = queryByUpdate(request, this.fareDealTotalMapper, agentList,
//                            this.operationLogMapper);
//                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.fareDealTotalMapper, this.operationLogMapper, opResult);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//
        String[] attrNames = {VERSION};

        this.setPageOptions(attrNames, mv, request, response);
//        this.getResultSetText((List<FareDealTotal>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);
        return mv;

    }

    private FareDealTotal getQueryConditionForOp(HttpServletRequest request) {

        FareDealTotal qCon = new FareDealTotal();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            qCon.setDeal_id(FormUtil.getParameter(request, "d_deal_id"));
            qCon.setDeal_min(FormUtil.getParameter(request, "d_deal_min"));
            qCon.setDeal_max(FormUtil.getParameter(request, "d_deal_max"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setDeal_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_deal_id"));
                qCon.setDeal_min(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_deal_min"));
                qCon.setDeal_max(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_deal_max"));
            }
        }
        this.getBaseParameters(request, qCon);
        //当操作为克隆时，查询克隆后的草稿版本
        if (command.equals(CommandConstant.COMMAND_CLONE)) {
            qCon.setRecord_flag(ParameterConstant.RECORD_FLAG_DRAFT);
        }

        return qCon;
    }

    private FareDealTotal getQueryCondition(HttpServletRequest request) {
        FareDealTotal qCon = new FareDealTotal();
        String q_deal_id = FormUtil.getParameter(request, "q_deal_id");
        String q_deal_min = FormUtil.getParameter(request, "q_deal_min");
        String q_deal_max = FormUtil.getParameter(request, "q_deal_max");
        qCon.setDeal_id(q_deal_id);
        qCon.setDeal_min(q_deal_min);
        qCon.setDeal_max(q_deal_max);
        this.getBaseParameters(request, qCon);
        return qCon;
    }

    public OperationResult queryByUpdate(HttpServletRequest request, FareDealTotalMapper arMapper, List lineList, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        this.getQueryConditionForOp(request);
        LogUtil logUtil = new LogUtil();
        FareDealTotal queryCondition;
        List<FareDealTotal> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = arMapper.getFareDealTotal(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);

        return or;

    }

    public OperationResult queryForOp(HttpServletRequest request, FareDealTotalMapper arMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        FareDealTotal queryCondition;
        List<FareDealTotal> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request);
            if (queryCondition == null) {
                return null;
            }
            resultSet = arMapper.getFareDealTotal(queryCondition);
            opResult.setReturnResultSet(resultSet);
            //or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult modify(HttpServletRequest request, FareDealTotalMapper arMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        FareDealTotal po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "累计消费额ID：" + "主键：" + po.getDeal_id()+ ":";

        try {
            n = this.modifyByTrans(request, arMapper, pvMapper, po);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(FareDealTotal po, FareDealTotalMapper arMapper, OperationLogMapper opLogMapper) {
        List<FareDealTotal> list = arMapper.getFareDealTotalById(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private int addByTrans(HttpServletRequest request, FareDealTotalMapper arMapper, PrmVersionMapper pvMapper, FareDealTotal po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = arMapper.addFareDealTotal(po);
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

    private void getVersionNoForSubmit(FareDealTotal po, String versionNoMax) {
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

    public int submitByTrans(HttpServletRequest request, FareDealTotalMapper arMapper, PrmVersionMapper pvMapper, FareDealTotal po, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        List<PrmVersion> prmVersions;
        String versionNoMax;
        int n = 0;
        int n1 = 0;
        // String test=null;
        try {

            status = txMgr.getTransaction(def);

            //旧的未来或当前参数数据做删除标志
            arMapper.submitToOldFlag(po);
            //生成新的未来或当前参数的版本号
            versionNoMax = pvMapper.selectPrmVersionForSubmit(po); 
            this.getVersionNoForSubmit(po, versionNoMax);

            //添加新的未来或当前参数的数据记录
            n = arMapper.submitFromDraftToCurOrFur(po);
            // 重新生成参数表中的未来或当前参数记录
            pvMapper.modifyPrmVersionForSubmit(po);
            pvMapper.addPrmVersion(po);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private int cloneByTrans(HttpServletRequest request, FareDealTotalMapper arMapper, PrmVersionMapper pvMapper, FareDealTotal po, PrmVersion prmVersion) throws Exception {
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
            arMapper.deleteFareDealTotalForClone(po);
            //未来或当前参数克隆成草稿版本
            n = arMapper.cloneFromCurOrFurToDraft(po);
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

    public OperationResult submit(HttpServletRequest request, FareDealTotalMapper arMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        FareDealTotal po = this.getReqAttributeForSubmit(request);
        FareDealTotal prmVersion = null;
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {

            n = this.submitByTrans(request, arMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_SUBMIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_SUBMIT, request, LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()), opLogMapper);

        rmsg.addMessage(LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()));

        return rmsg;
    }

    public OperationResult clone(HttpServletRequest request, FareDealTotalMapper arMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        FareDealTotal po = this.getReqAttributeForClone(request);
        FareDealTotal prmVersion = null;

        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.cloneByTrans(request, arMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CLONE, e, opLogMapper);
        }
        //当前还是未来？
        logUtil.logOperation(CommandConstant.COMMAND_CLONE, request, LogConstant.cloneSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.cloneSuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(HttpServletRequest request, FareDealTotalMapper arMapper, PrmVersionMapper pvMapper, FareDealTotal po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = arMapper.modifyFareDealTotal(po);
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

    private int deleteByTrans(HttpServletRequest request, FareDealTotalMapper arMapper, PrmVersionMapper pvMapper, Vector<FareDealTotal> pos, FareDealTotal prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (FareDealTotal po : pos) {
                n += arMapper.deleteFareDealTotal(po);
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

    public OperationResult add(HttpServletRequest request, FareDealTotalMapper arMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        FareDealTotal po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "服务费率：" + "主键：" + po.getDeal_id()+ ":";

        try {
//            if (this.existRecord(po, arMapper, opLogMapper)) {
//                rmsg.addMessage(preMsg + "记录已存在！");
//                return rmsg;
//            }

            n = this.addByTrans(request, arMapper, pvMapper, po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult delete(HttpServletRequest request, FareDealTotalMapper arMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<FareDealTotal> pos = this.getReqAttributeForDelete(request);
        FareDealTotal prmVo = new FareDealTotal();
        this.getBaseParameters(request, prmVo);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, arMapper, pvMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public FareDealTotal getReqAttribute(HttpServletRequest request) {
        FareDealTotal po = new FareDealTotal();
        if (request.getParameter("d_water_no") != null && !request.getParameter("d_water_no").isEmpty()) {
            po.setWater_no(Long.parseLong(request.getParameter("d_water_no")));
            
        }
           

        po.setDeal_id(FormUtil.getParameter(request, "d_deal_id"));
        po.setDeal_min(FormUtil.getParameter(request, "d_deal_min"));
        po.setDeal_max(FormUtil.getParameter(request, "d_deal_max"));
        this.getBaseParameters(request, po);
        return po;
    }

    public FareDealTotal getReqAttributeForSubmit(HttpServletRequest request) {
        FareDealTotal po = new FareDealTotal();

        this.getBaseParameters(request, po);
        this.getBaseParametersForSubmit(request, po);

        return po;
    }

    public FareDealTotal getReqAttributeForClone(HttpServletRequest request) {
        FareDealTotal po = new FareDealTotal();

        this.getBaseParameters(request, po);

        return po;
    }

    private Vector<FareDealTotal> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<FareDealTotal> sds = new Vector();
        FareDealTotal sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getFareDealTotal(strIds, "#");
            sds.add(sd);
        }
        return sds;

    }

    private FareDealTotal getFareDealTotal(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        FareDealTotal sd = new FareDealTotal();
        Vector<FareDealTotal> sds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setWater_no(Long.parseLong(tmp));
                continue;
            }
           
            if (i == 2) {
                sd.setDeal_id(tmp);
                continue;
            }
            if (i == 3) {
                sd.setDeal_min(tmp);
                continue;
            }
            if (i == 4) {
                sd.setDeal_max(tmp);
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

    private Vector<FareDealTotal> getReqAttributeForDelete(HttpServletRequest request) {
        FareDealTotal po = new FareDealTotal();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<FareDealTotal> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

//    private String getFareDealTotalRecordFlagOld(FareDealTotal agentRate) {
//        String recordFlagSubmit = agentRate.getRecord_flag_submit();
//        String recordFlagOld = "";
//        if (recordFlagSubmit.equals(ParameterConstant.RECORD_FLAG_CURRENT)) {
//            recordFlagOld = ParameterConstant.RECORD_FLAG_HISTORY;
//        }
//        if (recordFlagSubmit.equals(ParameterConstant.RECORD_FLAG_FUTURE)) {
//            recordFlagOld = ParameterConstant.RECORD_FLAG_DELETED;
//        }
//        return recordFlagOld;
//    }

//    private String getFareDealTotalVersionValidDate(FareDealTotal agentRate) {
//        String verDateBegin = agentRate.getBegin_time_submit();
//        System.out.println("verDateBegin====>" + verDateBegin);
//        return this.convertValidDate(verDateBegin);
//
//    }

    @RequestMapping("/FareDealTotalExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List results = this.getBufferElements(request);
        List<Map<String, String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private List<Map<String, String>> entityToMap(List results) {
        List<Map<String, String>> list = new ArrayList<>();
        int row = results.size();
        for (int n = 0; n < row; n++) {
            FareDealTotal vo = (FareDealTotal) results.get(n);
            Map<String, String> map = new HashMap<>();
            map.put("deal_id", vo.getDeal_id());
            map.put("deal_min", vo.getDeal_min());
            map.put("deal_max", vo.getDeal_max());

            list.add(map);
        }
        return list;
    }

    public OperationResult query(HttpServletRequest request, FareDealTotalMapper fareDealTotalMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        FareDealTotal queryCondition;
        List<FareDealTotal> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = fareDealTotalMapper.getFareDealTotal(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }
}
