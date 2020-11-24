/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.AdminManager;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.app.prminfo.mapper.AdminManagerMapper;
import com.goldsign.acc.app.querysys.entity.CodePubFlag;
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
 * 行政收费
 * @author lind
 */
@Controller
public class AdminManagerController extends PrmBaseController {
    
    @Autowired
    private AdminManagerMapper adminManagerMapper;


    @RequestMapping("/AdminManager")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/admin_manager.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        PrmVersion prmVersion = new PrmVersion();
        prmVersion = this.getPrmVersion(request, response);
        
        try {
            if (command != null) {
                command = command.trim();
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
                if (command.equals(CommandConstant.COMMAND_CLONE))//克隆操作
                {
                    opResult = this.clone(request, prmVersion);

                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT)) {
                    this.queryForOp(request, opResult, prmVersion);
                }
                if (!command.equals(CommandConstant.COMMAND_ADD) || !command.equals(CommandConstant.COMMAND_QUERY)) {
                    this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        String[] attrNames = {LINES, STATIONS, LINE_STATIONS, CARDMAINS, CARDSUBS, CARDMAIN_SUBS, ADMINHANDLEREASONS, VERSION};

        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.getResultSetText((List<AdminManager>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;
    }
    
    private void getResultSetText(List<AdminManager> resultSet, ModelAndView mv) {
        List<PubFlag> lines = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.LINES);
        List<PubFlag> stations = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.STATIONS);
        List<PubFlag> cardMains = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDMAINS);
        List<PubFlag> cardSubs = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDSUBS);
        List<PubFlag> adminReasons = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.ADMINHANDLEREASONS);

        for (AdminManager am : resultSet) {
            if (lines != null && !lines.isEmpty()) {
                am.setLineIdText(DBUtil.getTextByCode(am.getLineId(), lines));
                if(am.getLineIdText().equals("00")){
                    am.setLineIdText("全部");
                }
            }
            if (stations != null && !stations.isEmpty()) {
                am.setStationIdText(DBUtil.getTextByCode(am.getStationId(), am.getLineId(), stations));
                if(am.getStationIdText().equals("00")){
                    am.setStationIdText("全部");
                }
            }
            if (cardMains != null && !cardMains.isEmpty()) {
                am.setCardMainIdText(DBUtil.getTextByCode(am.getCardMainId(), cardMains));
            }
            if (stations != null && !stations.isEmpty()) {
                am.setCardSubIdText(DBUtil.getTextByCode(am.getCardSubId(), am.getCardMainId(), cardSubs));
            }
            if (adminReasons != null && !adminReasons.isEmpty()) {
                am.setAdminManagerIdText(DBUtil.getTextByCode(am.getAdminManagerId(), adminReasons));
            }

        }

    }

    private AdminManager getQueryConditionForOp(HttpServletRequest request, PrmVersion prmVersion) {

        AdminManager qCon = new AdminManager();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }

            qCon.setLineId(FormUtil.getParameter(request, "d_lineID"));
            qCon.setStationId(FormUtil.getParameter(request,"d_stationID"));
            qCon.setCardMainId(FormUtil.getParameter(request,"d_cardMainID"));
            qCon.setCardSubId(FormUtil.getParameter(request,"d_cardSubID"));
            qCon.setAdminManagerId(FormUtil.getParameter(request,"d_adminManagerID"));
//            qCon.setAdminCharge(FormUtil.getParameter(request,"d_adminCharge"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setLineId(FormUtil.getParameter(request, "q_lineID"));
                qCon.setStationId(FormUtil.getParameter(request,"q_stationID"));
                qCon.setCardMainId(FormUtil.getParameter(request,"q_cardMainID"));
                qCon.setCardSubId(FormUtil.getParameter(request,"q_cardSubID"));
                qCon.setAdminManagerId(FormUtil.getParameter(request,"q_adminManagerID"));
//                qCon.setAdminCharge(FormUtil.getParameter(request,"q_adminCharge"));
            }
        }
        qCon.setRecordFlag(prmVersion.getRecord_flag());
        //当操作为克隆时，查询克隆后的草稿版本 注释后改为按当前条件查询
//        if (command.equals(CommandConstant.COMMAND_CLONE)) {
//            qCon.setRecordFlag(ParameterConstant.RECORD_FLAG_DRAFT);
//        }

        qCon.setVersionNo(prmVersion.getVersion_no());
        return qCon;
    }

    private AdminManager getQueryCondition(HttpServletRequest request, PrmVersion prmVersion) {
        AdminManager qCon = new AdminManager();
        qCon.setLineId(FormUtil.getParameter(request, "q_lineID"));
        qCon.setStationId(FormUtil.getParameter(request,"q_stationID"));
        qCon.setCardMainId(FormUtil.getParameter(request,"q_cardMainID"));
        qCon.setCardSubId(FormUtil.getParameter(request,"q_cardSubID"));
        qCon.setAdminManagerId(FormUtil.getParameter(request,"q_adminManagerID"));
        qCon.setAdminCharge(FormUtil.getParameter(request,"q_adminCharge"));
        qCon.setVersionNo(prmVersion.getVersion_no());
        qCon.setRecordFlag(prmVersion.getRecord_flag());

        return qCon;
    }

    public OperationResult query(HttpServletRequest request, PrmVersion prmVersion) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        AdminManager queryCondition;
        List<AdminManager> resultSet;

        try {
            queryCondition = this.getQueryCondition(request, prmVersion);
            resultSet = adminManagerMapper.selectAdminManagers(queryCondition);
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
        AdminManager queryCondition;
        List<AdminManager> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request,prmVersion);
            if (queryCondition == null) {
                return null;
            }
            resultSet = adminManagerMapper.selectAdminManagers(queryCondition);
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
        AdminManager am = this.getReqAttribute(request,prmVersion);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "行政收费：版本号" +am.getVersionNo() + "生效标志" + am.getRecordFlag() + ":";
        if (CharUtil.getDBLenth(am.getAdminCharge()) > 4) {
            rmsg.addMessage(preMsg + "行政处理罚金最大值不能超过位。");
            return rmsg;
        } else {
            try {
                n = this.modifyByTrans(am);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, this.operationLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), this.operationLogMapper);
        }
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(AdminManager am) {
        AdminManager tmp = adminManagerMapper.selectByPrimaryKey(am);
        if (tmp==null) {
            return false;
        }
        return true;

    }

    private int addByTrans(AdminManager am) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = adminManagerMapper.insert(am);
            this.prmVersionMapper.modifyPrmVersionForDraft(am.getPrmVersion());

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

    

    public int submitByTrans(HttpServletRequest request, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        String versionNoMax;
        int n = 0;
        try {
            status = txMgr.getTransaction(def);

            //旧的未来或当前参数数据做删除标志
            adminManagerMapper.submitToOldFlag(prmVersion);
            //生成新的未来或当前参数的版本号
            versionNoMax = this.prmVersionMapper.selectPrmVersionForSubmit(prmVersion);
            this.getVersionNoForSubmit(prmVersion, versionNoMax);

            //添加新的未来或当前参数的数据记录
            n = adminManagerMapper.submitFromDraftToCurOrFur(prmVersion);
            // 重新生成参数表中的未来或当前参数记录
            this.prmVersionMapper.modifyPrmVersionForSubmit(prmVersion);
            this.prmVersionMapper.addPrmVersion(prmVersion);
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

    

    private int cloneByTrans(PrmVersion prmVersion) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);

            //删除草稿版本
            adminManagerMapper.deleteDeviceTypesForClone(prmVersion);
            //未来或当前参数克隆成草稿版本
            n = adminManagerMapper.cloneFromCurOrFurToDraft(prmVersion);
            //更新参数版本索引信息
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

    public OperationResult submit(HttpServletRequest request, PrmVersion prmVersion) throws Exception {
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

    public OperationResult clone(HttpServletRequest request, PrmVersion prmVersion) throws Exception {
        OperationResult rmsg = new OperationResult();
//        AdminManager am = this.getReqAttributeForClone(request);
        
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.cloneByTrans(prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CLONE, e, this.operationLogMapper);
        }
        //当前还是未来？
        logUtil.logOperation(CommandConstant.COMMAND_CLONE, request, LogConstant.cloneSuccessMsg(n), this.operationLogMapper);

        rmsg.addMessage(LogConstant.cloneSuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(AdminManager am) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = adminManagerMapper.updateByPrimaryKeySelective(am);
            this.prmVersionMapper.modifyPrmVersionForDraft(am.getPrmVersion());

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private int deleteByTrans( Vector<AdminManager> ams, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (AdminManager am : ams) {
                n += adminManagerMapper.deleteByPrimaryKey(am);
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
        AdminManager am = this.getReqAttribute(request,prmVersion);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "行政收费：版本号" +am.getVersionNo() + "生效标志" + am.getRecordFlag() + ":";
        if (CharUtil.getDBLenth(am.getAdminCharge()) > 4) {
            rmsg.addMessage(preMsg + "行政处理罚金最大值不能超过位。");
            return rmsg;
        } else {
            try {
                if (this.existRecord(am)) {
                    rmsg.addMessage("增加记录已存在！");
                    return rmsg;
                }

                n = this.addByTrans(am);

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
        Vector<AdminManager> ams = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(ams, prmVersion);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, this.operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), this.operationLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public AdminManager getReqAttribute(HttpServletRequest request, PrmVersion prmVersion) {
        AdminManager am = new AdminManager();

        am.setLineId(FormUtil.getParameter(request, "d_lineID"));
        am.setStationId(FormUtil.getParameter(request,"d_stationID"));
        am.setCardMainId(FormUtil.getParameter(request,"d_cardMainID"));
        am.setCardSubId(FormUtil.getParameter(request,"d_cardSubID"));
        am.setAdminManagerId(FormUtil.getParameter(request,"d_adminManagerID"));
        am.setAdminCharge(FormUtil.getParameter(request,"d_adminCharge"));
        am.setVersionNo(prmVersion.getVersion_no());
        am.setRecordFlag(prmVersion.getRecord_flag());
        am.setPrmVersion(prmVersion);
        return am;
    }

    public void getReqAttributeForSubmit(HttpServletRequest request, PrmVersion prmVersion) {
        this.getBaseParametersForSubmit(request, prmVersion);
    }

    public AdminManager getReqAttributeForClone(HttpServletRequest request) {
        AdminManager am = new AdminManager();
        return am;
    }

    private Vector<AdminManager> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<AdminManager> ams = new Vector();
        AdminManager am;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            am = this.getAdminManagers(strIds, "#");
            ams.add(am);
        }
        return ams;

    }

    private AdminManager getAdminManagers(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        AdminManager am = new AdminManager();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                am.setLineId(tmp);
                continue;
            }
            if (i == 2) {
                am.setStationId(tmp);
                continue;
            }
            if (i == 3) {
                am.setCardMainId(tmp);
                continue;
            }
            if (i == 4) {
                am.setCardSubId(tmp);
                continue;
            }
            if (i == 5) {
                am.setAdminManagerId(tmp);
                continue;
            }
            if (i == 6) {
                am.setVersionNo(tmp);
                continue;
            }
            if (i == 7) {
                am.setRecordFlag(tmp);
                continue;
            }
        }
        return am;

    }

    private Vector<AdminManager> getReqAttributeForDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<AdminManager> selectedItems = this.getDeleteIDs(selectIds);

        return selectedItems;
    }
    
    
    @RequestMapping("/AdminManagerExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	AdminManager queryCondition = null;
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.prminfo.entity.AdminManager");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private List<Map<String, String>> entityToMap(List results) {
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            AdminManager vo = (AdminManager)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("adminManagerIdText", vo.getAdminManagerIdText());
            map.put("adminCharge", vo.getAdminCharge());
            map.put("cardMainIdText", vo.getCardMainIdText());
            map.put("cardSubIdText", vo.getCardSubIdText());
            map.put("lineIdText", vo.getLineIdText());
             map.put("stationIdText", vo.getStationIdText());
            map.put("versionNo", vo.getVersionNo());
            list.add(map);
        }
        return list;
    }
}
