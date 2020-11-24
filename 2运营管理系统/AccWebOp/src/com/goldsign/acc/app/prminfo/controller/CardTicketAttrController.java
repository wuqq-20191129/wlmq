/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.CardTicketAttr;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.app.prminfo.mapper.CardTicketAttrMapper;
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
 * 乘次票属性
 *
 * @author luck
 */
@Controller
public class CardTicketAttrController extends PrmBaseController {

    @Autowired
    private CardTicketAttrMapper cardTicketAttrMapper;

    @RequestMapping("/CardTicketAttr")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/card_ticket_attr.jsp");
        request.getSession().removeAttribute("queryCondition");
        request.getSession().setAttribute("versionNo", request.getParameter("Version"));
        request.getSession().setAttribute("recordFlag", request.getParameter("VersionType"));
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.cardTicketAttrMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.cardTicketAttrMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.cardTicketAttrMapper, this.prmVersionMapper, this.operationLogMapper);
                }

                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.cardTicketAttrMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_SUBMIT))//提交操作
                {
                    opResult = this.submit(request, this.cardTicketAttrMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_CLONE))//克隆操作
                {
                    opResult = this.clone(request, this.cardTicketAttrMapper, this.prmVersionMapper, this.operationLogMapper);

                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT) || command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                        || command.equals(CommandConstant.COMMAND_BACKEND)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.cardTicketAttrMapper, this.operationLogMapper, opResult);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {CARDMAINS, CARDMAIN_SUBS, CARDSUBS, VERSION};

        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.getResultSetText((List<CardTicketAttr>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }

    private void getResultSetText(List<CardTicketAttr> resultSet, ModelAndView mv) {
        List<PubFlag> cardMains = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDMAINS);
        List<PubFlag> cardSubs = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDSUBS);
        for (CardTicketAttr sd : resultSet) {
            if (cardMains != null && !cardMains.isEmpty()) {
                sd.setCard_main_name(DBUtil.getTextByCode(sd.getCard_main_id(), cardMains));
            }
            if (cardSubs != null && !cardSubs.isEmpty()) {
                sd.setCard_sub_name(DBUtil.getTextByCode(sd.getCard_sub_id(), sd.getCard_main_id(), cardSubs));
            }
        }

    }

    private CardTicketAttr getQueryConditionForOp(HttpServletRequest request) {

        CardTicketAttr qCon = new CardTicketAttr();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                this.getBaseParameters(request, qCon);
                return qCon;
            }

            qCon.setCard_main_id(FormUtil.getParameter(request, "d_cardMainID"));
            qCon.setCard_sub_id(FormUtil.getParameter(request, "d_cardSubID"));
            qCon.setPackage_id(FormUtil.getParameter(request, "d_packageID"));
            qCon.setOnce_charge_money(FormUtil.getParameter(request, "d_onceChargeMoney"));
            qCon.setOnce_charge_count(FormUtil.getParameter(request, "d_onceChargeCount"));
            qCon.setAdd_begin_day(FormUtil.getParameter(request, "d_addBeginDay"));
            qCon.setAdd_valid_day(FormUtil.getParameter(request, "d_addValidDay"));
            qCon.setExit_timeout_count(FormUtil.getParameter(request, "d_exitTimeoutCount"));
            qCon.setExit_timeout_money(FormUtil.getParameter(request, "d_exitTimeoutMoney"));
            qCon.setEntry_timeout_count(FormUtil.getParameter(request, "d_entryTimeoutCount"));
            qCon.setEntry_timeout_money(FormUtil.getParameter(request, "d_entryTimeoutMoney"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setCard_main_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardMainID"));
                qCon.setCard_sub_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardSubID"));

            }
        }
        this.getBaseParameters(request, qCon);
//        //当操作为克隆时，查询克隆后的草稿版本
//        if (command.equals(CommandConstant.COMMAND_CLONE)) {
//            qCon.setRecord_flag(ParameterConstant.RECORD_FLAG_DRAFT);
//        }

        return qCon;
    }

    private CardTicketAttr getQueryCondition(HttpServletRequest request) {
        CardTicketAttr qCon = new CardTicketAttr();
        qCon.setCard_main_id(FormUtil.getParameter(request, "q_cardMainID"));
        qCon.setCard_sub_id(FormUtil.getParameter(request, "q_cardSubID"));
        this.getBaseParameters(request, qCon);
        return qCon;
    }

    public OperationResult query(HttpServletRequest request, CardTicketAttrMapper ckaMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        CardTicketAttr queryCondition;
        List<CardTicketAttr> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = ckaMapper.getCardTicketAttrs(queryCondition);
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

    public OperationResult queryForOp(HttpServletRequest request, CardTicketAttrMapper ctaMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        // OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        CardTicketAttr queryCondition;
        List<CardTicketAttr> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request);
            if (queryCondition == null) {
                return null;
            }
            resultSet = ctaMapper.getCardTicketAttrs(queryCondition);
            opResult.setReturnResultSet(resultSet);
            //or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult modify(HttpServletRequest request, CardTicketAttrMapper ctaMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        CardTicketAttr po = this.getReqAttribute(request);
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "乘次票属性：";

        try {
            n = this.modifyByTrans(request, ctaMapper, pvMapper, po);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(CardTicketAttr po, CardTicketAttrMapper ctaMapper, OperationLogMapper opLogMapper) {
        List<CardTicketAttr> list = ctaMapper.getCardTicketAttrById(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private int addByTrans(HttpServletRequest request, CardTicketAttrMapper ctaMapper, PrmVersionMapper pvMapper, CardTicketAttr po) throws Exception {
//        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {

//            txMgr = DBUtil.getDataSourceTransactionManager(request);
//            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            status = txMgr.getTransaction(this.def);
            n = ctaMapper.addCardTicketAttr(po);
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

    private void getVersionNoForSubmit(CardTicketAttr po, String versionNoMax) {
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

    public int submitByTrans(HttpServletRequest request, CardTicketAttrMapper ctaMapper, PrmVersionMapper pvMapper, CardTicketAttr po, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        List<PrmVersion> prmVersions;
        String versionNoMax;
        int n = 0;
        int n1 = 0;
        // String test=null;
        try {

            status = txMgr.getTransaction(def);

            //旧的未来或当前参数数据做删除标志
            ctaMapper.submitToOldFlag(po);
            //生成新的未来或当前参数的版本号
            versionNoMax = pvMapper.selectPrmVersionForSubmit(po);
            this.getVersionNoForSubmit(po, versionNoMax);

            //添加新的未来或当前参数的数据记录
            n = ctaMapper.submitFromDraftToCurOrFur(po);
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

    private int cloneByTrans(HttpServletRequest request, CardTicketAttrMapper ctaMapper, PrmVersionMapper pvMapper, CardTicketAttr po, PrmVersion prmVersion) throws Exception {
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
            ctaMapper.deleteCardTicketAttrsForClone(po);
            //未来或当前参数克隆成草稿版本
            n = ctaMapper.cloneFromCurOrFurToDraft(po);
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

    public OperationResult submit(HttpServletRequest request, CardTicketAttrMapper ctaMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        CardTicketAttr po = this.getReqAttributeForSubmit(request);
        CardTicketAttr prmVersion = null;
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.submitByTrans(request, ctaMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_SUBMIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_SUBMIT, request, LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()), opLogMapper);

        rmsg.addMessage(LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()));

        return rmsg;
    }

    public OperationResult clone(HttpServletRequest request, CardTicketAttrMapper ctaMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        CardTicketAttr po = this.getReqAttributeForClone(request);
        CardTicketAttr prmVersion = null;
        // User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.cloneByTrans(request, ctaMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CLONE, e, opLogMapper);
        }
        //当前还是未来？
        logUtil.logOperation(CommandConstant.COMMAND_CLONE, request, LogConstant.cloneSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.cloneSuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(HttpServletRequest request, CardTicketAttrMapper ctaMapper, PrmVersionMapper pvMapper, CardTicketAttr po) throws Exception {
//        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {

//            txMgr = DBUtil.getDataSourceTransactionManager(request);
//            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            status = txMgr.getTransaction(this.def);
            n = ctaMapper.modifyCardTicketAttr(po);
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

    private int deleteByTrans(HttpServletRequest request, CardTicketAttrMapper ctaMapper, PrmVersionMapper pvMapper, Vector<CardTicketAttr> pos, CardTicketAttr prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            for (CardTicketAttr po : pos) {
                n += ctaMapper.deleteCardTicketAttr(po);
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

    public OperationResult add(HttpServletRequest request, CardTicketAttrMapper ctaMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        CardTicketAttr po = this.getReqAttribute(request);
        // User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "乘次票属性：";

        try {
            if (this.existRecord(po, ctaMapper, opLogMapper)) {
                rmsg.addMessage(preMsg + "该票卡子类型套餐的乘次票属性已存在，请重新选择其他票卡子类型或其他套餐！");
                return rmsg;
            }

            // n = sdMapper.addStationDevice(po);
            n = this.addByTrans(request, ctaMapper, pvMapper, po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult delete(HttpServletRequest request, CardTicketAttrMapper ctaMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<CardTicketAttr> pos = this.getReqAttributeForDelete(request);
        CardTicketAttr prmVo = new CardTicketAttr();
        this.getBaseParameters(request, prmVo);
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, ctaMapper, pvMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public CardTicketAttr getReqAttribute(HttpServletRequest request) {
        CardTicketAttr po = new CardTicketAttr();

        po.setCard_main_id(FormUtil.getParameter(request, "d_cardMainID"));
        po.setCard_sub_id(FormUtil.getParameter(request, "d_cardSubID"));
        po.setPackage_id(FormUtil.getParameter(request, "d_packageID"));
        po.setOnce_charge_money(FormUtil.getParameter(request, "d_onceChargeMoney"));
        po.setOnce_charge_count(FormUtil.getParameter(request, "d_onceChargeCount"));
        po.setAdd_begin_day(FormUtil.getParameter(request, "d_addBeginDay"));
        po.setAdd_valid_day(FormUtil.getParameter(request, "d_addValidDay"));
        po.setExit_timeout_count(FormUtil.getParameter(request, "d_exitTimeoutCount"));
        po.setExit_timeout_money(FormUtil.getParameter(request, "d_exitTimeoutMoney"));
        po.setEntry_timeout_count(FormUtil.getParameter(request, "d_entryTimeoutCount"));
        po.setEntry_timeout_money(FormUtil.getParameter(request, "d_entryTimeoutMoney"));
        this.getBaseParameters(request, po);

        return po;
    }

    public CardTicketAttr getReqAttributeForSubmit(HttpServletRequest request) {
        CardTicketAttr po = new CardTicketAttr();

        this.getBaseParameters(request, po);
        this.getBaseParametersForSubmit(request, po);

        return po;
    }

    public CardTicketAttr getReqAttributeForClone(HttpServletRequest request) {
        CardTicketAttr po = new CardTicketAttr();
        this.getBaseParameters(request, po);
        return po;
    }

    private Vector<CardTicketAttr> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<CardTicketAttr> sds = new Vector();
        CardTicketAttr sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getCardTicketAttr(strIds, "#");
            sds.add(sd);
        }
        return sds;

    }

    private CardTicketAttr getCardTicketAttr(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        CardTicketAttr sd = new CardTicketAttr();;
        Vector<CardTicketAttr> sds = new Vector();
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
                sd.setPackage_id(tmp);

                continue;
            }
            if (i == 4) {
                sd.setVersion_no(tmp);
                continue;
            }
            if (i == 5) {
                sd.setRecord_flag(tmp);
                continue;
            }
        }
        return sd;

    }

    private Vector<CardTicketAttr> getReqAttributeForDelete(HttpServletRequest request) {
        CardTicketAttr po = new CardTicketAttr();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<CardTicketAttr> selectedItems = this.getDeleteIDs(selectIds);

        //this.getBaseParameters(request, po);
        return selectedItems;
    }
    
    @RequestMapping("/CardTicketAttrExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CardTicketAttr queryCondition = new CardTicketAttr();

        if (request.getSession().getAttribute("queryCondition") != null) {
            Object obj = request.getSession().getAttribute("queryCondition");
            if (obj instanceof CardTicketAttr) {
                queryCondition = (CardTicketAttr) request.getSession().getAttribute("queryCondition");
            }
        } else {
            queryCondition.setVersion_no((String) request.getSession().getAttribute("versionNo"));
            queryCondition.setRecord_flag((String) request.getSession().getAttribute("recordFlag"));
        }
        List<Map> queryResults = cardTicketAttrMapper.queryToMap(queryCondition);
        List<PubFlag> cardMains = pubFlagMapper.getCardMains();
        List<PubFlag> cardSubs = pubFlagMapper.getCardSubs();
        /* 查询结果部分内容转换中文 */
        for (Map vo : queryResults) {
            if (cardMains != null && !cardMains.isEmpty()) {
                vo.put("CARD_MAIN_NAME", DBUtil.getTextByCode(vo.get("CARD_MAIN_ID").toString(), cardMains));
            }
            if (cardSubs != null && !cardSubs.isEmpty()) {
                vo.put("CARD_SUB_NAME", DBUtil.getTextByCode(vo.get("CARD_SUB_ID").toString(), vo.get("CARD_MAIN_ID").toString(), cardSubs));
            }
        }
        

        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

}
