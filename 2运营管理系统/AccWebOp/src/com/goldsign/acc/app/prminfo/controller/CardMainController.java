/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.CardMain;
import com.goldsign.acc.app.prminfo.entity.CardSub;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.app.prminfo.mapper.CardMainMapper;
import com.goldsign.acc.app.prminfo.mapper.CardSubMapper;
import com.goldsign.acc.app.prminfo.mapper.PrmVersionMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;

import com.goldsign.acc.frame.controller.PrmBaseController;

import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.CharUtil;
import com.goldsign.acc.frame.util.ExpUtil;

import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;

import java.io.UnsupportedEncodingException;
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
 * 票卡主类型
 *
 * @author luck
 */
@Controller
public class CardMainController extends PrmBaseController {

    @Autowired
    private CardMainMapper cardMainMapper;
    @Autowired
    private CardSubMapper cardSubMapper;

    @RequestMapping("/CardMain")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/card_main.jsp");
        request.getSession().removeAttribute("queryCondition");
        request.getSession().setAttribute("versionNo", request.getParameter("Version"));
        request.getSession().setAttribute("recordFlag", request.getParameter("VersionType"));
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
//                if(!command.equals(CommandConstant.COMMAND_QUERY) && !command.equals(CommandConstant.COMMAND_BACK) && !command.equals(CommandConstant.COMMAND_NEXT)){
//                     request.getSession().removeAttribute("card_main_name");
//                }
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.cardMainMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.cardMainMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.cardMainMapper, this.prmVersionMapper, this.operationLogMapper);
                }

                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {

                    opResult = this.query(request, this.cardMainMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_SUBMIT))//提交操作
                {
                    opResult = this.submit(request, this.cardMainMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_CLONE))//克隆操作
                {
                    opResult = this.clone(request, this.cardMainMapper, this.prmVersionMapper, this.operationLogMapper);

                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT) || command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                        || command.equals(CommandConstant.COMMAND_BACKEND)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.cardMainMapper, this.operationLogMapper, opResult);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {VERSION};
        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);
        return mv;

    }

    private CardMain getQueryConditionForOp(HttpServletRequest request) throws UnsupportedEncodingException {

        CardMain qCon = new CardMain();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                this.getBaseParameters(request, qCon);
                return qCon;
            }

            qCon.setCard_main_id(FormUtil.getParameter(request, "d_cardMainId"));
//            qCon.setCard_main_name(new String(FormUtil.getParameter(request, "d_cardMainName").getBytes("iso-8859-1"), "utf-8"));
            qCon.setCard_main_name(FormUtil.getParameter(request, "d_cardMainName"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setCard_main_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardMainId"));
//                qCon.setCard_main_name(new String(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardMainName").getBytes("iso-8859-1"), "utf-8"));
                qCon.setCard_main_name(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardMainName"));
            }
        }
        this.getBaseParameters(request, qCon);
//        //当操作为克隆时，查询克隆后的草稿版本
//        if (command.equals(CommandConstant.COMMAND_CLONE)) {
//            qCon.setRecord_flag(ParameterConstant.RECORD_FLAG_DRAFT);
//        }

        return qCon;
    }

    private CardMain getQueryCondition(HttpServletRequest request) throws UnsupportedEncodingException {
        CardMain qCon = new CardMain();
        qCon.setCard_main_id(FormUtil.getParameter(request, "q_cardMainId"));
//        qCon.setCard_main_name(new String(FormUtil.getParameter(request, "q_cardMainName").getBytes("iso-8859-1"), "utf-8"));
        qCon.setCard_main_name(FormUtil.getParameter(request, "q_cardMainName"));
//        request.getSession().setAttribute("card_main_name", qCon.getCard_main_name());
        this.getBaseParameters(request, qCon);
        return qCon;
    }

    public OperationResult query(HttpServletRequest request, CardMainMapper cmMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        CardMain queryCondition;
        List<CardMain> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = cmMapper.getCardMains(queryCondition);
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

    public OperationResult queryForOp(HttpServletRequest request, CardMainMapper cmMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        // OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        CardMain queryCondition;
        List<CardMain> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request);
            if (queryCondition == null) {
                return null;
            }
            resultSet = cmMapper.getCardMains(queryCondition);
            opResult.setReturnResultSet(resultSet);
            //or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult modify(HttpServletRequest request, CardMainMapper cmMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        CardMain po = this.getReqAttribute(request);
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "票卡主类型：";
        if (CharUtil.getDBLenth(po.getCard_main_name()) > 30) {
            rmsg.addMessage(preMsg + "票卡名称最大值不能超过30位(中文字符为三位)。");
            return rmsg;
        } else {
            try {
                if (this.existName(po, cmMapper, opLogMapper)) {
                    rmsg.addMessage(preMsg + "票卡名称已存在！");
                    return rmsg;
                }
                n = this.modifyByTrans(request, cmMapper, pvMapper, po);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        }
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(CardMain po, CardMainMapper cmMapper, OperationLogMapper opLogMapper) {
        List<CardMain> list = cmMapper.getCardMainById(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean existName(CardMain po, CardMainMapper cmMapper, OperationLogMapper opLogMapper) {
        List<CardMain> list = cmMapper.getCardMainByName(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    //检查与票卡子类型的关联
    private boolean isExistingRelativeRecord(CardMain po, CardMainMapper cmMapper, OperationLogMapper opLogMapper) {
        CardSub cardSub = new CardSub();
        cardSub.setCard_main_id(po.getCard_main_id());
        List<CardSub> list = cardSubMapper.getCardSubByCardMainId(cardSub);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private int addByTrans(HttpServletRequest request, CardMainMapper cmMapper, PrmVersionMapper pvMapper, CardMain po) throws Exception {
//        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {

//            txMgr = DBUtil.getDataSourceTransactionManager(request);
//            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            status = txMgr.getTransaction(this.def);
            n = cmMapper.addCardMain(po);
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

    private void getVersionNoForSubmit(CardMain po, String versionNoMax) {
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
//        System.out.println("getVersionNoForSubmit.date====>"+po.getVersion_valid_date());
//        System.out.println("getVersionNoForSubmit.versionNoNew====>"+versionNoMax);
        po.setVersion_no_new(versionNoNew);
        po.setRecord_flag_new(po.getRecord_flag_submit());

    }

    public int submitByTrans(HttpServletRequest request, CardMainMapper cmMapper, PrmVersionMapper pvMapper, CardMain po, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        List<PrmVersion> prmVersions;
        String versionNoMax;
        int n = 0;
        int n1 = 0;
        // String test=null;
        try {

            status = txMgr.getTransaction(def);

            //旧的或未来或当前参数数据做删除标志
//            System.out.println("getRecord_flag_new====>"+po.getRecord_flag_new());
//            System.out.println("getRecord_flag_old()====>"+po.getRecord_flag_old());
//            System.out.println("parm_type_id====>"+po.getParm_type_id());
//            System.out.println("Version_valid_date====>"+po.getVersion_valid_date());
            cmMapper.submitToOldFlag(po);
            //生成新的未来或当前参数的版本号

            versionNoMax = pvMapper.selectPrmVersionForSubmit(po);

            this.getVersionNoForSubmit(po, versionNoMax);

            //添加新的未来或当前参数的数据记录
            n = cmMapper.submitFromDraftToCurOrFur(po);
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

    private int cloneByTrans(HttpServletRequest request, CardMainMapper cmMapper, PrmVersionMapper pvMapper, CardMain po, PrmVersion prmVersion) throws Exception {
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
            cmMapper.deleteCardMainForClone(po);
            //未来或当前参数克隆成草稿版本
            n = cmMapper.cloneFromCurOrFurToDraft(po);
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

    public OperationResult submit(HttpServletRequest request, CardMainMapper cmMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        CardMain po = this.getReqAttributeForSubmit(request);
        CardMain prmVersion = null;
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.submitByTrans(request, cmMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_SUBMIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_SUBMIT, request, LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()), opLogMapper);

        rmsg.addMessage(LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()));

        return rmsg;
    }

    public OperationResult clone(HttpServletRequest request, CardMainMapper cmMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        CardMain po = this.getReqAttributeForClone(request);
        CardMain prmVersion = null;
        // User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.cloneByTrans(request, cmMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CLONE, e, opLogMapper);
        }
        //当前还是未来？
        logUtil.logOperation(CommandConstant.COMMAND_CLONE, request, LogConstant.cloneSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.cloneSuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(HttpServletRequest request, CardMainMapper cmMapper, PrmVersionMapper pvMapper, CardMain po) throws Exception {
//        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {

//            txMgr = DBUtil.getDataSourceTransactionManager(request);
//            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            status = txMgr.getTransaction(this.def);
            n = cmMapper.modifyCardMain(po);
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

    private int deleteByTrans(HttpServletRequest request, CardMainMapper cmMapper, PrmVersionMapper pvMapper, Vector<CardMain> pos, CardMain prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            for (CardMain po : pos) {
                n += cmMapper.deleteCardMain(po);
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

    public OperationResult add(HttpServletRequest request, CardMainMapper cmMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        CardMain po = this.getReqAttribute(request);
        // User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "票卡主类型：";
        if (CharUtil.getDBLenth(po.getCard_main_name()) > 30) {
            rmsg.addMessage(preMsg + "票卡名称最大值不能超过30位(中文字符为三位)。");
            return rmsg;
        } else {
            try {
                if (this.existRecord(po, cmMapper, opLogMapper)) {
                    rmsg.addMessage(preMsg + "票卡类型已存在！");
                    return rmsg;
                }
                if (this.existName(po, cmMapper, opLogMapper)) {
                    rmsg.addMessage(preMsg + "票卡名称已存在！");
                    return rmsg;
                }

                // n = sdMapper.addStationDevice(po);
                n = this.addByTrans(request, cmMapper, pvMapper, po);

            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);
        }
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult delete(HttpServletRequest request, CardMainMapper cmMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<CardMain> pos = this.getReqAttributeForDelete(request);
        CardMain prmVo = new CardMain();
        this.getBaseParameters(request, prmVo);
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            for (CardMain po : pos) {
                if (this.isExistingRelativeRecord(po, cmMapper, opLogMapper)) {
                    rmsg.addMessage("票卡主类型删除失败，票卡子类型表中存在关联的记录！");
                    return rmsg;
                }
            }
            n = this.deleteByTrans(request, cmMapper, pvMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public CardMain getReqAttribute(HttpServletRequest request) throws UnsupportedEncodingException {
        CardMain po = new CardMain();

        // String cscNumber = ForUtil.getParameter("d_CSCNumber");
        po.setCard_main_id(FormUtil.getParameter(request, "d_cardMainId"));
//        po.setCard_main_name(new String(FormUtil.getParameter(request, "d_cardMainName").getBytes("iso-8859-1"), "utf-8"));
        po.setCard_main_name(FormUtil.getParameter(request, "d_cardMainName"));
        this.getBaseParameters(request, po);
        //--del po.setVersionNo(version);
        //--del po.setParamTypeID(type);
        return po;
    }

    public CardMain getReqAttributeForSubmit(HttpServletRequest request) {
        CardMain po = new CardMain();

        this.getBaseParameters(request, po);
        this.getBaseParametersForSubmit(request, po);
        return po;
    }

    public CardMain getReqAttributeForClone(HttpServletRequest request) {
        CardMain po = new CardMain();

        this.getBaseParameters(request, po);
        return po;
    }

    private Vector<CardMain> getDeleteIDs(String selectedIds) throws UnsupportedEncodingException {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<CardMain> sds = new Vector();
        CardMain sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getCardMain(strIds, "#");
            sds.add(sd);
        }
        return sds;

    }

    private CardMain getCardMain(String strIDs, String delim) throws UnsupportedEncodingException {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        CardMain sd = new CardMain();;
        Vector<CardMain> sds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setCard_main_id(tmp);
                continue;
            }
            if (i == 2) {
                sd.setCard_main_name(tmp);
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

    private Vector<CardMain> getReqAttributeForDelete(HttpServletRequest request) throws UnsupportedEncodingException {
        CardMain po = new CardMain();
        String selectIds = request.getParameter("allSelectedIDs");

        Vector<CardMain> selectedItems = this.getDeleteIDs(selectIds);

        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    @RequestMapping("/CardMainExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CardMain queryCondition = new CardMain();
        if (request.getSession().getAttribute("queryCondition") != null) {
            Object obj = request.getSession().getAttribute("queryCondition");
            if (obj instanceof CardMain) {
                queryCondition = (CardMain) request.getSession().getAttribute("queryCondition");
            }
        } else {
            queryCondition.setVersion_no((String) request.getSession().getAttribute("versionNo"));
            queryCondition.setRecord_flag((String) request.getSession().getAttribute("recordFlag"));
        }
        List<Map> queryResults = cardMainMapper.queryToMap(queryCondition);
        

        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

}
