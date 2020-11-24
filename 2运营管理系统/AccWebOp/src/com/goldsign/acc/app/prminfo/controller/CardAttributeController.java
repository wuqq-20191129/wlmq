/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.CardAttribute;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.app.prminfo.mapper.CardAttributeMapper;
import com.goldsign.acc.app.prminfo.mapper.PrmVersionMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;

import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.mapper.PubFlagMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;

import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.Arrays;
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
 * 票卡属性
 *
 * @author luck
 */
@Controller
public class CardAttributeController extends PrmBaseController {

    @Autowired
    private CardAttributeMapper cardAttributeMapper;
    @Autowired
    private PubFlagMapper pubFlagMapper;

    @RequestMapping("/CardAttribute")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/card_attribute.jsp");
        request.getSession().removeAttribute("queryCondition");
        request.getSession().setAttribute("versionNo", request.getParameter("Version"));
        request.getSession().setAttribute("recordFlag", request.getParameter("VersionType"));
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        String type = request.getParameter("WindowType");
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.cardAttributeMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.cardAttributeMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.cardAttributeMapper, this.prmVersionMapper, this.operationLogMapper);
                }

                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.cardAttributeMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_SUBMIT))//提交操作
                {
                    opResult = this.submit(request, this.cardAttributeMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_CLONE))//克隆操作
                {
                    opResult = this.clone(request, this.cardAttributeMapper, this.prmVersionMapper, this.operationLogMapper);

                }
                if (command.equals("openwindow")) {
                    mv = new ModelAndView("/jsp/prminfo/dev_type.jsp");
                    if (type.equals("1")) {

                    }
                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT) || command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                        || command.equals(CommandConstant.COMMAND_BACKEND)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.cardAttributeMapper, this.operationLogMapper, opResult);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {CARDMAINS, CARDMAIN_SUBS, CARDSUBS, PURSEVALUETYPES, FEETYPES, CHKVALIDPHYLOGICS, CHECKINOUTSEQUENCES, DEVICETYPES, CARDATTRYESORNO, RECHARGE_DEVTYPES, SALE_DEVTYPES, VERSION};

        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.getResultSetText((List<CardAttribute>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }

    private void getResultSetText(List<CardAttribute> resultSet, ModelAndView mv) throws Exception {
        List<PubFlag> cardMains = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDMAINS);
        List<PubFlag> cardSubs = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDSUBS);
        List<PubFlag> purseValueTypes = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.PURSEVALUETYPES);
        List<PubFlag> cardAttrYesOrNo = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDATTRYESORNO);
        List<PubFlag> feeTypes = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.FEETYPES);
        List<PubFlag> deviceTypes = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.DEVICETYPES);
        List<PubFlag> rechargeDeviceTypes = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.RECHARGE_DEVTYPES);
        List<PubFlag> saleDeviceTypes = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.SALE_DEVTYPES);
        List<PubFlag> chkValidPhyLogics = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CHKVALIDPHYLOGICS);
        List<PubFlag> checkInOutSequences = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CHECKINOUTSEQUENCES);

        for (CardAttribute sd : resultSet) {
            if (cardMains != null && !cardMains.isEmpty()) {
                sd.setCard_main_id_text(DBUtil.getTextByCode(sd.getCard_main_id(), cardMains));
            }
            if (cardSubs != null && !cardSubs.isEmpty()) {
                sd.setCard_sub_id_text(DBUtil.getTextByCode(sd.getCard_sub_id(), sd.getCard_main_id(), cardSubs));
            }
            if (purseValueTypes != null && !purseValueTypes.isEmpty()) {
                sd.setPurse_value_type_text(DBUtil.getTextByCode(sd.getPurse_value_type(), purseValueTypes));
            }
            if (feeTypes != null && !feeTypes.isEmpty()) {
                sd.setUpdate_fee_type_text(DBUtil.getTextByCode(sd.getUpdate_fee_type(), feeTypes));
            }
            if (deviceTypes != null && !deviceTypes.isEmpty()) {
                sd.setUse_on_equi_text(convertMultiValueToText(sd.getUse_on_equi(), deviceTypes));
            }
            if (rechargeDeviceTypes != null && !rechargeDeviceTypes.isEmpty()) {
                sd.setAdd_val_equi_type_text(convertMultiValueToText(sd.getAdd_val_equi_type(), rechargeDeviceTypes));
            }
            if (saleDeviceTypes != null && !saleDeviceTypes.isEmpty()) {
                sd.setSale_equi_type_text(convertMultiValueToText(sd.getSale_equi_type(), saleDeviceTypes));
            }

            if (chkValidPhyLogics != null && !chkValidPhyLogics.isEmpty()) {
                sd.setIs_chk_valid_phy_logic_text(DBUtil.getTextByCode(sd.getIs_chk_valid_phy_logic(), chkValidPhyLogics));
            }
            if (checkInOutSequences != null && !checkInOutSequences.isEmpty()) {
                sd.setIs_chk_sequ_text(DBUtil.getTextByCode(sd.getIs_chk_sequ(), checkInOutSequences));
            }
            if (cardAttrYesOrNo != null && !cardAttrYesOrNo.isEmpty()) {
                sd.setIs_overdraw_text(DBUtil.getTextByCode(sd.getIs_overdraw(), cardAttrYesOrNo));
                sd.setIs_recharge_text(DBUtil.getTextByCode(sd.getIs_recharge(), cardAttrYesOrNo));
                sd.setIs_chk_cur_station_text(DBUtil.getTextByCode(sd.getIs_chk_cur_station(), cardAttrYesOrNo));
                sd.setIs_chk_cur_date_text(DBUtil.getTextByCode(sd.getIs_chk_cur_date(), cardAttrYesOrNo));
                sd.setIs_refund_text(DBUtil.getTextByCode(sd.getIs_refund(), cardAttrYesOrNo));
                sd.setIs_allow_postpone_text(DBUtil.getTextByCode(sd.getIs_allow_postpone(), cardAttrYesOrNo));
                sd.setIs_activation_text(DBUtil.getTextByCode(sd.getIs_activation(), cardAttrYesOrNo));
                sd.setIs_chk_blk_text(DBUtil.getTextByCode(sd.getIs_chk_blk(), cardAttrYesOrNo));
                sd.setIs_chk_remain_text(DBUtil.getTextByCode(sd.getIs_chk_remain(), cardAttrYesOrNo));
                sd.setIs_chk_exce_time_text(DBUtil.getTextByCode(sd.getIs_chk_exce_time(), cardAttrYesOrNo));
                sd.setIs_chk_exce_st_text(DBUtil.getTextByCode(sd.getIs_chk_exce_st(), cardAttrYesOrNo));
                sd.setIs_limit_entry_exit_text(DBUtil.getTextByCode(sd.getIs_limit_entry_exit(), cardAttrYesOrNo));
                sd.setIs_limit_station_text(DBUtil.getTextByCode(sd.getIs_limit_station(), cardAttrYesOrNo));
                sd.setIs_limit_sale_entry_text(DBUtil.getTextByCode(sd.getIs_limit_sale_entry(), cardAttrYesOrNo));
                sd.setIs_deposit_refund_text(DBUtil.getTextByCode(sd.getIs_deposit_refund(), cardAttrYesOrNo));
                sd.setIs_chk_valid_day_text(DBUtil.getTextByCode(sd.getIs_chk_valid_day(), cardAttrYesOrNo));
            }
        }

    }

    /**
     * 1/0转换为对应的设备名
     *
     * @param multiValue
     * @param typePubFlagsForRechargeDev
     * @return
     * @throws Exception
     */
    public String convertMultiValueToText(String multiValue, List<PubFlag> typePubFlagsForRechargeDev) throws Exception {
        String result = "";
        DBUtil dbUtil = new DBUtil();
        List<PubFlag> devsOrder = pubFlagMapper.getDevsOrder();
        int num = devsOrder.size();
        for (int i = 0; i < num; i++) {
            String devType = "0";
            if (multiValue.length() > i) {
                devType = multiValue.substring(i, i + 1);
            }
            int code = i + 1;
            if (Integer.parseInt(devType) == 1) {
                result += devsOrder.get(i).getCode_text()
                        + ",";
            }
        }
        if (result.length() != 0) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    private CardAttribute getQueryConditionForOp(HttpServletRequest request) {

        CardAttribute qCon = new CardAttribute();
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

    private CardAttribute getQueryCondition(HttpServletRequest request) {
//        System.out.println("**************getQueryConditions****************");
        CardAttribute qCon = new CardAttribute();
        qCon.setCard_main_id(FormUtil.getParameter(request, "q_cardMainID"));
        qCon.setCard_sub_id(FormUtil.getParameter(request, "q_cardSubID"));

        this.getBaseParameters(request, qCon);
        return qCon;
    }

    public OperationResult query(HttpServletRequest request, CardAttributeMapper caMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        CardAttribute queryCondition;
        List<CardAttribute> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = caMapper.getCardAttributes(queryCondition);
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

    public OperationResult queryForOp(HttpServletRequest request, CardAttributeMapper caMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        // OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        CardAttribute queryCondition;
        List<CardAttribute> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request);
            if (queryCondition == null) {
                return null;
            }
            resultSet = caMapper.getCardAttributes(queryCondition);
            opResult.setReturnResultSet(resultSet);
            //or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult modify(HttpServletRequest request, CardAttributeMapper caMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        CardAttribute po = this.getReqAttribute(request);
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "票卡属性：";

        try {
            n = this.modifyByTrans(request, caMapper, pvMapper, po);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(CardAttribute po, CardAttributeMapper caMapper, OperationLogMapper opLogMapper) {
        List<CardAttribute> list = caMapper.getCardAttributeById(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private int addByTrans(HttpServletRequest request, CardAttributeMapper caMapper, PrmVersionMapper pvMapper, CardAttribute po) throws Exception {
//        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {

//            txMgr = DBUtil.getDataSourceTransactionManager(request);
//            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            status = txMgr.getTransaction(this.def);
            n = caMapper.addCardAttributes(po);
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

    private void getVersionNoForSubmit(CardAttribute po, String versionNoMax) {
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

    public int submitByTrans(HttpServletRequest request, CardAttributeMapper caMapper, PrmVersionMapper pvMapper, CardAttribute po, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        List<PrmVersion> prmVersions;
        String versionNoMax;
        int n = 0;
        int n1 = 0;
        // String test=null;
        try {

            status = txMgr.getTransaction(def);

            //旧的未来或当前参数数据做删除标志
            caMapper.submitToOldFlag(po);
            //生成新的未来或当前参数的版本号
            versionNoMax = pvMapper.selectPrmVersionForSubmit(po);
            this.getVersionNoForSubmit(po, versionNoMax);

            //添加新的未来或当前参数的数据记录
            n = caMapper.submitFromDraftToCurOrFur(po);
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

    private int cloneByTrans(HttpServletRequest request, CardAttributeMapper caMapper, PrmVersionMapper pvMapper, CardAttribute po, PrmVersion prmVersion) throws Exception {
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
            caMapper.deleteCardAttributesForClone(po);
            //未来或当前参数克隆成草稿版本
            n = caMapper.cloneFromCurOrFurToDraft(po);
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

    public OperationResult submit(HttpServletRequest request, CardAttributeMapper caMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        CardAttribute po = this.getReqAttributeForSubmit(request);
        CardAttribute prmVersion = null;
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.submitByTrans(request, caMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_SUBMIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_SUBMIT, request, LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()), opLogMapper);

        rmsg.addMessage(LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()));

        return rmsg;
    }

    public OperationResult clone(HttpServletRequest request, CardAttributeMapper caMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        CardAttribute po = this.getReqAttributeForClone(request);
        CardAttribute prmVersion = null;
        // User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.cloneByTrans(request, caMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CLONE, e, opLogMapper);
        }
        //当前还是未来？
        logUtil.logOperation(CommandConstant.COMMAND_CLONE, request, LogConstant.cloneSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.cloneSuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(HttpServletRequest request, CardAttributeMapper caMapper, PrmVersionMapper pvMapper, CardAttribute po) throws Exception {
//        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {

//            txMgr = DBUtil.getDataSourceTransactionManager(request);
//            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            status = txMgr.getTransaction(this.def);
            n = caMapper.modifyCardAttribute(po);
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

    private int deleteByTrans(HttpServletRequest request, CardAttributeMapper caMapper, PrmVersionMapper pvMapper, Vector<CardAttribute> pos, CardAttribute prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            for (CardAttribute po : pos) {
                n += caMapper.deleteCardAttributes(po);
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

    public OperationResult add(HttpServletRequest request, CardAttributeMapper caMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        CardAttribute po = this.getReqAttribute(request);
        // User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "票卡属性：";

        try {
            if (this.existRecord(po, caMapper, opLogMapper)) {
                rmsg.addMessage(preMsg + "票卡属性已存在！");
                return rmsg;
            }

            // n = sdMapper.addStationDevice(po);
            n = this.addByTrans(request, caMapper, pvMapper, po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult delete(HttpServletRequest request, CardAttributeMapper caMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<CardAttribute> pos = this.getReqAttributeForDelete(request);
        CardAttribute prmVo = new CardAttribute();
        this.getBaseParameters(request, prmVo);
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, caMapper, pvMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public CardAttribute getReqAttribute(HttpServletRequest request) {
        CardAttribute po = new CardAttribute();

        // String cscNumber = ForUtil.getParameter("d_CSCNumber");
        po.setCard_main_id(FormUtil.getParameter(request, "d_cardMainID"));
        po.setCard_sub_id(FormUtil.getParameter(request, "d_cardSubID"));
        po.setPurse_value_type(FormUtil.getParameter(request, "d_purseValueType"));
        po.setMax_store_val(FormUtil.getParameter(request, "d_maxStoreVal"));
        po.setIs_overdraw(FormUtil.getParameter(request, "d_isOverdraw"));
        po.setOverdraw_limit(FormUtil.getParameter(request, "d_overdrawLimit"));
        po.setIs_recharge(FormUtil.getParameter(request, "d_isRecharge"));
        po.setMax_recharge_val(FormUtil.getParameter(request, "d_maxRecharge"));
        po.setUpdate_fee_type(FormUtil.getParameter(request, "d_updateFeeType"));
        po.setIs_chk_cur_station(FormUtil.getParameter(request, "d_isChkCurStation"));
        po.setIs_chk_cur_date(FormUtil.getParameter(request, "d_isChkCurDate"));
        po.setIs_refund(FormUtil.getParameter(request, "d_isRefund"));

        po.setRefund_limit(FormUtil.getParameter(request, "d_refundLimit"));
        po.setDaily_trip_cnt_lmt(FormUtil.getParameter(request, "d_maxTripCountDaily"));
        po.setMonth_trip_cnt_lmt(FormUtil.getParameter(request, "d_monthTripCntLmt"));
        po.setExp_date(FormUtil.getParameter(request, "d_expiredDate"));
        po.setIs_allow_postpone(FormUtil.getParameter(request, "d_isAllowPostpone"));
        po.setExtend_expire_day(FormUtil.getParameter(request, "d_postDays"));
        po.setDeposit_amnt(FormUtil.getParameter(request, "d_deposit"));
        po.setCard_cost(FormUtil.getParameter(request, "d_cardCost"));
        po.setAuxiliary_expenses(FormUtil.getParameter(request, "d_auxiliaryExpenses"));
        po.setIs_activation(FormUtil.getParameter(request, "d_isNeedActivat"));
        po.setIs_chk_blk(FormUtil.getParameter(request, "d_isCheckBlackList"));
        po.setIs_chk_remain(FormUtil.getParameter(request, "d_isCheckBalance"));
        po.setIs_chk_valid_phy_logic(FormUtil.getParameter(request, "d_isChkValidPhyLogic"));

        po.setIs_chk_sequ(FormUtil.getParameter(request, "d_isCheckInOutSequence"));
        po.setIs_chk_exce_time(FormUtil.getParameter(request, "d_isCheckTimeout"));
        po.setIs_chk_exce_st(FormUtil.getParameter(request, "d_isCheckTripOut"));
        po.setIs_limit_entry_exit(FormUtil.getParameter(request, "d_isLimitEntryExit"));
        po.setIs_limit_station(FormUtil.getParameter(request, "d_isLimitStation"));
//        po.setAdd_val_equi_type(convertTextToMultiValue(request.getParameterValues("d_rechargeDeviceType")));
        po.setAdd_val_equi_type(convertTextToMultiValue(request.getParameter("d_rechargeDeviceType"), 1));

        po.setUse_on_equi(convertTextToMultiValue(request.getParameter("d_availableDeviceType"), 2));
        po.setIs_limit_sale_entry(FormUtil.getParameter(request, "d_isLimitSaleEntry"));
        po.setRefund_limit_balance(FormUtil.getParameter(request, "d_refundLimitBalance"));
        po.setRefund_auxiliary_expense(FormUtil.getParameter(request, "d_refundAuxiliaryExpense"));
        po.setSale_equi_type(convertTextToMultiValue(request.getParameter("d_saleEquiType"), 3));
        po.setIs_deposit_refund(FormUtil.getParameter(request, "d_isDepositRefund"));
        po.setIs_chk_valid_day(FormUtil.getParameter(request, "d_isChkValidDay"));
        po.setValid_day(FormUtil.getParameter(request, "d_validDay"));

        po.setIs_lmt_daily_trip("");
        po.setIs_preexamine("");
        po.setPreexamine_cnt_lmt("");
        po.setIs_chk_exp_date("");
        po.setIs_limit_line("");
        po.setLimit_intime("");
        po.setIs_limit_intime("");
        po.setEntry_control("");

        this.getBaseParameters(request, po);
        //--del po.setVersionNo(version);
        //--del po.setParamTypeID(type);
        return po;
    }

    /**
     * 对应设备转换为1/0，1为选中，0为不选中
     *
     * @param st
     * @param type
     * @return
     */
    public String convertTextToMultiValue(String st, int type) {
        List<PubFlag> deviceTypes = null;
//        switch (type) {
//            case 1:
//                deviceTypes = pubFlagMapper.getRechargeDeviceTypes();
//                break;
//            case 2:
//                deviceTypes = pubFlagMapper.getDeviceTypes();
//                break;
//            default:
//                deviceTypes = pubFlagMapper.getSaleDeviceTypes();
//                break;
//        }
        deviceTypes = pubFlagMapper.getDevsOrder();
        String[] a = st.split(",");
        String t[] = new String[deviceTypes.size()];
        String result = "";
        if (a == null || a.length == 0) {
            for (int i = 0; i < t.length; i++) {
                t[i] = "0";
            }
        } else {
            for (int i = 0; i < t.length; i++) {
                if (Arrays.asList(a).contains(deviceTypes.get(i).getCode_text())) {
                    t[i] = "1";
                } else {
                    t[i] = "0";
                }
            }
        }
        for (String s : t) {
            result += s;
        }
        int len = 16 - t.length;
        for (int i = 0; i < len; i++) {
            result += "0";
        }
        return result;
    }

//    public String convertTextToMultiValue(String a[]) {
//        List<PubFlag> deviceTypes = pubFlagMapper.getDeviceTypes();
//        String t[] = new String[deviceTypes.size()];
//        for (int i = 0; i < a.length; i++) {
//            System.out.println("a====>" + a[i]);
//        }
//        String result = "";
//        if (a == null || a.length == 0) {
//            for (int i = 0; i < t.length; i++) {
//                t[i] = "0";
//            }
//        } else {
//            for (int i = 0; i < t.length; i++) {
//                if (Arrays.asList(a).contains(String.valueOf(i + 1))) {
//                    t[i] = "1";
//                } else {
//                    t[i] = "0";
//                }
//            }
//        }
//        for (String s : t) {
//            result += s;
//        }
//        return result;
//    }
    public CardAttribute getReqAttributeForSubmit(HttpServletRequest request) {
        CardAttribute po = new CardAttribute();

        this.getBaseParameters(request, po);
        this.getBaseParametersForSubmit(request, po);

        return po;
    }

    public CardAttribute getReqAttributeForClone(HttpServletRequest request) {
        CardAttribute po = new CardAttribute();

        this.getBaseParameters(request, po);

        return po;
    }

    private Vector<CardAttribute> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<CardAttribute> sds = new Vector();
        CardAttribute sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getCardAttribute(strIds, "#");
            sds.add(sd);
        }
        return sds;

    }

    private CardAttribute getCardAttribute(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        CardAttribute sd = new CardAttribute();;
        Vector<CardAttribute> sds = new Vector();
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

    private Vector<CardAttribute> getReqAttributeForDelete(HttpServletRequest request) {
        CardAttribute po = new CardAttribute();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<CardAttribute> selectedItems = this.getDeleteIDs(selectIds);

        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    @RequestMapping("/CardAttributeExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CardAttribute queryCondition = new CardAttribute();
        if (request.getSession().getAttribute("queryCondition") != null) {
            Object obj = request.getSession().getAttribute("queryCondition");
            if (obj instanceof CardAttribute) {
                queryCondition = (CardAttribute) request.getSession().getAttribute("queryCondition");
            }
        } else {
            queryCondition.setVersion_no((String) request.getSession().getAttribute("versionNo"));
            queryCondition.setRecord_flag((String) request.getSession().getAttribute("recordFlag"));
        }
        List<Map> queryResults = cardAttributeMapper.queryToMap(queryCondition);
        List<PubFlag> cardMains = pubFlagMapper.getCardMains();
        List<PubFlag> cardSubs = pubFlagMapper.getCardSubs();
        List<PubFlag> purseValueTypes = pubFlagMapper.getPurseValueTypes();
        List<PubFlag> cardAttrYesOrNo = pubFlagMapper.getCardAttrYesOrNo();
        List<PubFlag> feeTypes = pubFlagMapper.getFeeTypes();
        List<PubFlag> deviceTypes = pubFlagMapper.getDeviceTypes();
        List<PubFlag> rechargeDeviceTypes = pubFlagMapper.getRechargeDeviceTypes();
        List<PubFlag> saleDeviceTypes = pubFlagMapper.getSaleDeviceTypes();
        List<PubFlag> chkValidPhyLogics = pubFlagMapper.getChkValidPhyLogics();
        List<PubFlag> checkInOutSequences = pubFlagMapper.getCheckInOutSequences();
        for (Map vo : queryResults) {
            if (cardMains != null && !cardMains.isEmpty()) {
                vo.put("CARD_MAIN_ID_TEXT", DBUtil.getTextByCode(vo.get("CARD_MAIN_ID").toString(), cardMains));
            }
            if (cardSubs != null && !cardSubs.isEmpty()) {
                vo.put("CARD_SUB_ID_TEXT", DBUtil.getTextByCode(vo.get("CARD_SUB_ID").toString(), vo.get("CARD_MAIN_ID").toString(), cardSubs));
            }
            if (purseValueTypes != null && !purseValueTypes.isEmpty()) {
                vo.put("PURSE_VALUE_TYPE_TEXT", DBUtil.getTextByCode(vo.get("PURSE_VALUE_TYPE").toString(), purseValueTypes));
            }
            if (feeTypes != null && !feeTypes.isEmpty()) {
                vo.put("UPDATE_FEE_TYPE_TEXT", DBUtil.getTextByCode(vo.get("UPDATE_FEE_TYPE").toString(), feeTypes));
            }
            if (deviceTypes != null && !deviceTypes.isEmpty()) {
                vo.put("USE_ON_EQUI_TEXT", convertMultiValueToText(vo.get("USE_ON_EQUI").toString(), deviceTypes));
            }
            if (rechargeDeviceTypes != null && !rechargeDeviceTypes.isEmpty()) {
                vo.put("ADD_VAL_EQUI_TYPE_TEXT", convertMultiValueToText(vo.get("ADD_VAL_EQUI_TYPE").toString(), rechargeDeviceTypes));
            }
            if (saleDeviceTypes != null && !saleDeviceTypes.isEmpty()) {
                vo.put("SALE_EQUI_TYPE_TEXT", convertMultiValueToText(vo.get("SALE_EQUI_TYPE").toString(), saleDeviceTypes));
            }
            if (chkValidPhyLogics != null && !chkValidPhyLogics.isEmpty()) {
                vo.put("IS_CHK_VALID_PHY_LOGIC_TEXT", DBUtil.getTextByCode(vo.get("IS_CHK_VALID_PHY_LOGIC").toString(), chkValidPhyLogics));
            }
            if (checkInOutSequences != null && !checkInOutSequences.isEmpty()) {
                vo.put("IS_CHK_SEQU_TEXT", DBUtil.getTextByCode(vo.get("IS_CHK_SEQU").toString(), checkInOutSequences));
            }
            if (cardAttrYesOrNo != null && !cardAttrYesOrNo.isEmpty()) {
                vo.put("IS_OVERDRAW_TEXT", DBUtil.getTextByCode(vo.get("IS_OVERDRAW").toString(), cardAttrYesOrNo));
                vo.put("IS_RECHARGE_TEXT", DBUtil.getTextByCode(vo.get("IS_RECHARGE").toString(), cardAttrYesOrNo));
                vo.put("IS_CHK_CUR_STATION_TEXT", DBUtil.getTextByCode(vo.get("IS_CHK_CUR_STATION").toString(), cardAttrYesOrNo));
                vo.put("IS_CHK_CUR_DATE_TEXT", DBUtil.getTextByCode(vo.get("IS_CHK_CUR_DATE").toString(), cardAttrYesOrNo));
                vo.put("IS_REFUND_TEXT", DBUtil.getTextByCode(vo.get("IS_REFUND").toString(), cardAttrYesOrNo));
                vo.put("IS_ALLOW_POSTPONE_TEXT", DBUtil.getTextByCode(vo.get("IS_ALLOW_POSTPONE").toString(), cardAttrYesOrNo));
                vo.put("IS_ACTIVATION_TEXT", DBUtil.getTextByCode(vo.get("IS_ACTIVATION").toString(), cardAttrYesOrNo));
                vo.put("IS_CHK_BLK_TEXT", DBUtil.getTextByCode(vo.get("IS_CHK_BLK").toString(), cardAttrYesOrNo));
                vo.put("IS_CHK_REMAIN_TEXT", DBUtil.getTextByCode(vo.get("IS_CHK_REMAIN").toString(), cardAttrYesOrNo));
                vo.put("IS_CHK_EXCE_TIME_TEXT", DBUtil.getTextByCode(vo.get("IS_CHK_EXCE_TIME").toString(), cardAttrYesOrNo));
                vo.put("IS_CHK_EXCE_ST_TEXT", DBUtil.getTextByCode(vo.get("IS_CHK_EXCE_ST").toString(), cardAttrYesOrNo));
                vo.put("IS_LIMIT_ENTRY_EXIT_TEXT", DBUtil.getTextByCode(vo.get("IS_LIMIT_ENTRY_EXIT").toString(), cardAttrYesOrNo));
                vo.put("IS_LIMIT_STATION_TEXT", DBUtil.getTextByCode(vo.get("IS_LIMIT_STATION").toString(), cardAttrYesOrNo));
                vo.put("IS_LIMIT_SALE_ENTRY_TEXT", DBUtil.getTextByCode(vo.get("IS_LIMIT_SALE_ENTRY").toString(), cardAttrYesOrNo));
                vo.put("IS_DEPOSIT_REFUND_TEXT", DBUtil.getTextByCode(vo.get("IS_DEPOSIT_REFUND").toString(), cardAttrYesOrNo));

                vo.put("IS_CHK_VALID_DAY_TEXT", DBUtil.getTextByCode((String) vo.get("IS_CHK_VALID_DAY"), cardAttrYesOrNo));

            }
        }

        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

}
