/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.GateApproEnterPara;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.app.prminfo.mapper.GateApproEnterParaMapper;
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
 * 2070607
 * 闸机专用通道参数
 */
@Controller
public class GateApproEnterParaController extends PrmBaseController{
    
    @Autowired
    private GateApproEnterParaMapper gateApproEnterParaMapper;


    @RequestMapping("/GateApproEnterPara")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/gate_appro_enter_para.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.gateApproEnterParaMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.gateApproEnterParaMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.gateApproEnterParaMapper, this.prmVersionMapper, this.operationLogMapper);
                }

                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.gateApproEnterParaMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_SUBMIT))//提交操作
                {
                    opResult = this.submit(request, this.gateApproEnterParaMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_CLONE))//克隆操作
                {
                    opResult = this.clone(request, this.gateApproEnterParaMapper, this.prmVersionMapper, this.operationLogMapper);

                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.gateApproEnterParaMapper, this.operationLogMapper, opResult);

                }
                if (command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                        || command.equals(CommandConstant.COMMAND_BACKEND)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {LINES,STATIONS,LINE_STATIONS, CARDMAINS, CARDMAIN_SUBS,CARDSUBS, DISCOUNT, SOUNDS,RECORDFLAG,VERSION};

        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.getResultSetText((List<GateApproEnterPara>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }

    private void getResultSetText(List<GateApproEnterPara> resultSet, ModelAndView mv) {
        List<PubFlag> cardMains = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDMAINS);
        List<PubFlag> cardSubs = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDSUBS);
        List<PubFlag> discounts = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.DISCOUNT);
        List<PubFlag> sounds = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.SOUNDS);
        List<PubFlag> recordFlag = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.RECORDFLAG);

        for (GateApproEnterPara sd : resultSet) {
            if (cardMains != null && !cardMains.isEmpty()) {
                sd.setCard_main_id_name(DBUtil.getTextByCode(sd.getCard_main_id(), cardMains));
            }
            if (cardSubs != null && !cardSubs.isEmpty()) {
                sd.setCard_sub_id_name(DBUtil.getTextByCode(sd.getCard_sub_id(),sd.getCard_main_id(),cardSubs));
            }
            if (discounts != null && !discounts.isEmpty()) {
                sd.setDiscount_name(DBUtil.getTextByCode(sd.getDiscount(), discounts));
            }
            if (sounds != null && !sounds.isEmpty()) {
                sd.setSound_name(DBUtil.getTextByCode(sd.getSound(), sounds));
            }
            if (recordFlag != null && !recordFlag.isEmpty()) {
                sd.setRecord_flag_name(DBUtil.getTextByCode(sd.getRecord_flag(), recordFlag));
            }

        }

    }

    private GateApproEnterPara getQueryConditionForOp(HttpServletRequest request) {

        GateApproEnterPara qCon = new GateApproEnterPara();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                this.getBaseParameters(request, qCon);
                return qCon;
            }

            qCon.setCard_main_id(FormUtil.getParameter(request,"d_cardMainID"));
            qCon.setCard_sub_id(FormUtil.getParameter(request,"d_cardSubID"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setCard_main_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardMainID"));
                qCon.setCard_sub_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardSubID"));
            }
        }
        this.getBaseParameters(request, qCon);
        return qCon;
    }

    private GateApproEnterPara getQueryCondition(HttpServletRequest request) {
        GateApproEnterPara gateAEP = new GateApproEnterPara();
        gateAEP.setCard_main_id(FormUtil.getParameter(request, "q_cardMainID"));
        gateAEP.setCard_sub_id(FormUtil.getParameter(request, "q_cardSubID"));

        this.getBaseParameters(request, gateAEP);
        return gateAEP;
    }

    public OperationResult query(HttpServletRequest request, GateApproEnterParaMapper gepMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        GateApproEnterPara queryCondition;
        List<GateApproEnterPara> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = gepMapper.getGateApproEnterParas(queryCondition);
            or.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult queryForOp(HttpServletRequest request, GateApproEnterParaMapper gpMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        
        LogUtil logUtil = new LogUtil();
        GateApproEnterPara queryCondition;
        List<GateApproEnterPara> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request);
            if (queryCondition == null) {
                return null;
            }
            resultSet = gpMapper.getGateApproEnterParas(queryCondition);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询 " + resultSet.size() + " 条记录", opLogMapper);
        return opResult;

    }

    public OperationResult modify(HttpServletRequest request, GateApproEnterParaMapper gpMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        GateApproEnterPara po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.modifyByTrans(po);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(n), opLogMapper);
        
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(GateApproEnterPara po, GateApproEnterParaMapper GPMapper) {
        List<GateApproEnterPara> list = GPMapper.getGateApproEnterParaById(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private int addByTrans( GateApproEnterPara po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            
            status = txMgr.getTransaction(this.def);
            n = gateApproEnterParaMapper.addGateApproEnterPara(po);
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

    private void getVersionNoForSubmit(GateApproEnterPara po, String versionNoMax) {
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

    

    public int submitByTrans(HttpServletRequest request, GateApproEnterParaMapper gpMapper, PrmVersionMapper pvMapper, GateApproEnterPara po, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        List<PrmVersion> prmVersions;
        String versionNoMax;
        int n = 0;
        int n1 = 0;
        try {

            status = txMgr.getTransaction(def);
            

            //旧的未来或当前参数数据做删除标志
            gpMapper.submitToOldFlag(po);
            //生成新的未来或当前参数的版本号
            versionNoMax = pvMapper.selectPrmVersionForSubmit(po);
            this.getVersionNoForSubmit(po, versionNoMax);

            //添加新的“未来”或“当前”参数的数据记录
            n = gpMapper.submitFromDraftToCurOrFur(po);
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

    

    private int cloneByTrans(HttpServletRequest request, GateApproEnterParaMapper gpMapper, PrmVersionMapper pvMapper, GateApproEnterPara po) throws Exception {
        
        TransactionStatus status = null;
        int n = 0;
        int n1 = 0;
        try {
            status = txMgr.getTransaction(this.def);

            //删除草稿版本
            gpMapper.deleteGateApproEnterParasForClone(po);
            //未来或当前参数克隆成草稿版本
            n = gpMapper.cloneFromCurOrFurToDraft(po);
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

    public OperationResult submit(HttpServletRequest request, GateApproEnterParaMapper gpMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        GateApproEnterPara po = this.getReqAttributeForSubmit(request);
        GateApproEnterPara prmVersion = null;
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.submitByTrans(request, gpMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_SUBMIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_SUBMIT, request, LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()), opLogMapper);

        rmsg.addMessage(LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()));

        return rmsg;
    }

    public OperationResult clone(HttpServletRequest request, GateApproEnterParaMapper gpMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        GateApproEnterPara po = this.getReqAttributeForClone(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.cloneByTrans(request, gpMapper, pvMapper, po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CLONE, e, opLogMapper);
        }
        //当前还是未来？
        logUtil.logOperation(CommandConstant.COMMAND_CLONE, request, LogConstant.cloneSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.cloneSuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(GateApproEnterPara po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = gateApproEnterParaMapper.modifyGateApproEnterPara(po);
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

    private int deleteByTrans(HttpServletRequest request, GateApproEnterParaMapper gpMapper, PrmVersionMapper pvMapper, List<GateApproEnterPara> pos, GateApproEnterPara prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {


            status = txMgr.getTransaction(this.def);
            for (GateApproEnterPara po : pos) {
                n += gpMapper.deleteGateApproEnterPara(po);
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

    public OperationResult add(HttpServletRequest request, GateApproEnterParaMapper GPMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        GateApproEnterPara po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "该“票卡子类型”记录已存在，请选择其它“票卡子类型”！";
        try {
            if (this.existRecord(po, GPMapper)) {
                rmsg.addMessage(preMsg);
                return rmsg;
            }
            // n = sdMapper.addStationDevice(po);
            n = this.addByTrans(po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);
        
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult delete(HttpServletRequest request, GateApproEnterParaMapper sdMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        List<GateApproEnterPara> pos = this.getReqAttributeForDelete(request);
        GateApproEnterPara prmVo = new GateApproEnterPara();
        this.getBaseParameters(request, prmVo);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, sdMapper, pvMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public GateApproEnterPara getReqAttribute(HttpServletRequest request) {
        GateApproEnterPara gp = new GateApproEnterPara();

        gp.setCard_main_id(FormUtil.getParameter(request, "d_cardMainID"));
        gp.setCard_sub_id(FormUtil.getParameter(request, "d_cardSubID"));
        gp.setDiscount(FormUtil.getParameter(request, "d_discount"));
        gp.setSound(FormUtil.getParameter(request, "d_sound"));
        this.getBaseParameters(request, gp);
        return gp;
    }

    public  GateApproEnterPara getReqAttributeForSubmit(HttpServletRequest request) {
         GateApproEnterPara po = new  GateApproEnterPara();

        this.getBaseParameters(request, po);
        this.getBaseParametersForSubmit(request, po);

        return po;
    }

    public GateApproEnterPara getReqAttributeForClone(HttpServletRequest request) {
        GateApproEnterPara po = new GateApproEnterPara();

        this.getBaseParameters(request, po);

        return po;
    }

    private List<GateApproEnterPara> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        List<GateApproEnterPara> sds = new ArrayList();
        GateApproEnterPara sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getGateApproEnterPara(strIds, "#");
            sds.add(sd);
        }
        return sds;

    }

    private GateApproEnterPara getGateApproEnterPara(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        GateApproEnterPara sd = new GateApproEnterPara();;
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setCard_main_id(tmp);

                continue;
            }
            if (i == 2) {
                sd.setCard_sub_id(tmp);
                continue;
            }
            if (i == 3) {
                sd.setVersion_no(tmp);
                continue;
            }
            if (i == 4) {
                sd.setRecord_flag(tmp);
                continue;
            }

        }
        return sd;

    }

    private List<GateApproEnterPara> getReqAttributeForDelete(HttpServletRequest request) {
        GateApproEnterPara po = new GateApproEnterPara();
        String selectIds = request.getParameter("allSelectedIDs");
        List<GateApproEnterPara> selectedItems = this.getDeleteIDs(selectIds);

        return selectedItems;
    }
    
    @RequestMapping("/GateApproEnterParaExportAll")
    public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.prminfo.entity.GateApproEnterPara");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }
}
