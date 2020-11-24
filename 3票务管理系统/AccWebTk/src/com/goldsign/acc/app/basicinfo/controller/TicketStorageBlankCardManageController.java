package com.goldsign.acc.app.basicinfo.controller;

import com.goldsign.acc.app.basicinfo.mapper.TicketStorageBlankCardManageMapper;
import com.goldsign.acc.app.basicinfo.entity.TicketStorageBlankCardManage;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.util.NumberUtil;
import com.goldsign.acc.frame.constant.InOutConstant;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.vo.User;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @desc:空白卡订单管理
 * @author:xiaowu
 * @create date: 2017-08-4
 */
@Controller
public class TicketStorageBlankCardManageController extends StorageOutInBaseController {

    @Autowired
    private TicketStorageBlankCardManageMapper mapper;

    private String returnViewId;
    private List<String> singleTickList;
    private List<String> prepaidTickList;
    private List<String> phoneTickList;

    @RequestMapping(value = "/ticketStorageBlankCardManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageBlankCardManage.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        singleTickList = mapper.getCardTypeList(InOutConstant.CARD_LARGE_TYPE_SJT); //单程票
        prepaidTickList = mapper.getCardTypeList(InOutConstant.CARD_LARGE_TYPE_SVT); //储值票
        phoneTickList = mapper.getCardTypeList(InOutConstant.CARD_LARGE_TYPE_PT); //手机票

        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult = this.queryParm(request, this.mapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD)) {
                    opResult = this.addParm(request, this.mapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE)) {
                    opResult = this.deleteParm(request, this.mapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                    opResult = this.modifyParm(request, this.mapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_AUDIT)) {
                    opResult = this.auditPlan(request, this.mapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY) || command.equals(CommandConstant.COMMAND_AUDIT)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.mapper, this.operationLogMapper, opResult, command);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        //下拉框
        this.getListShow(mv);
        String[] attrNames = {};
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.getResultSetText((List<TicketStorageBlankCardManage>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    private void getResultSetText(List<TicketStorageBlankCardManage> resultSet, ModelAndView mv) {
        List<PubFlag> recordflags = pubFlagMapper.getCodesByType(10);
        List<PubFlag> produceFactorys = pubFlagMapper.getProduceFactory();
        List<PubFlag> isUsedFlag = pubFlagMapper.getCodesByType(42);
        List<PubFlag> cityCodes = pubFlagMapper.getCodesByType(61);				//城市代码         乌市邮编前 2 位，填写 83
        List<PubFlag> industryCodes = pubFlagMapper.getCodesByType(62);			//行业代码 		地铁行业代码后 2 位，填写 03
        List<TicketStorageBlankCardManage> logicCardTypes = mapper.getBlankCardTypes();         //逻辑卡类型
        List<PubFlag> appIdentifiers = pubFlagMapper.getCodesByType(63);		//应用标识    1 位，正式票：0，测试票：1

        for (TicketStorageBlankCardManage dl : resultSet) {
            if (recordflags != null && !recordflags.isEmpty()) {
                dl.setRecord_flag_text(DBUtil.getTextByCode(dl.getRecord_flag(), recordflags));
            }
            if (produceFactorys != null && !produceFactorys.isEmpty()) {
                dl.setProduce_factory_text(DBUtil.getTextByCode(dl.getProduce_factory_id(), produceFactorys));
            }
            if (isUsedFlag != null && !isUsedFlag.isEmpty()) {
                dl.setIs_used_text(DBUtil.getTextByCode(dl.getIs_used(), isUsedFlag));
            }
            if (logicCardTypes != null && !logicCardTypes.isEmpty()) {
                for (TicketStorageBlankCardManage tsbc : logicCardTypes) {
                    if (tsbc.getBlank_card_type().equals(dl.getBlank_card_type())) {
                        dl.setBlank_card_type_text(tsbc.getRemark());       //remark 传回 类型中文名
                    }
                }
            }
            if (cityCodes != null && !cityCodes.isEmpty()) {
                dl.setCity_code_text(DBUtil.getTextByCode(dl.getCity_code(), cityCodes));
            }
            if (industryCodes != null && !industryCodes.isEmpty()) {
                dl.setIndustry_code_text(DBUtil.getTextByCode(dl.getIndustry_code(), industryCodes));
            }
            if (appIdentifiers != null && !appIdentifiers.isEmpty()) {
                dl.setApp_identifier_text(DBUtil.getTextByCode(dl.getApp_identifier(), appIdentifiers));
            }
            //格式化审核时间
//            String verifyDate = dl.getVerify_date();
//            if (verifyDate != null && verifyDate.split(" ").length > 0) {
//                dl.setVerify_date(verifyDate.split(" ")[0]);
//            }
            //格式化制单时间
//            String billDate = dl.getBill_date();
//            if (billDate != null && billDate.split(" ").length > 0) {
//                dl.setBill_date(billDate.split(" ")[0]);
//            }
        }
    }

    public OperationResult auditPlan(HttpServletRequest request, TicketStorageBlankCardManageMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageBlankCardManage> pos = this.getReqAttributeForDelete(request);
        //为解决下面修改事务锁表问题  放到事务之外
        String waterNoString = mapper.getCurrentBillNo(InOutConstant.TYPE_BILL_NO_BC_LOGIC_NO);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        TransactionStatus status = null;

        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageBlankCardManage tsbm : pos) {
                List<TicketStorageBlankCardManage> tsbcms = mapper.getTicketStorageBlankCardManage(tsbm);
                for (TicketStorageBlankCardManage ts : tsbcms) {
                    //非普通单程票
                    if (!singleTickList.contains(ts.getBlank_card_type())) {
                        TicketStorageBlankCardManage vo = new TicketStorageBlankCardManage();
                        vo.setBill_no(ts.getBill_no());
                        vo.setStart_logicno(ts.getStart_logicno());
                        vo.setEnd_logicno(ts.getEnd_logicno());
                        vo.setApp_identifier(ts.getApp_identifier());
                        vo.setCity_code(ts.getCity_code());
                        vo.setIndustry_code(ts.getIndustry_code());
                        vo.setBlank_card_type(ts.getBlank_card_type());
                        vo.setRecord_flag(InOutConstant.RECORD_FLAG_BILL_EFFECT);  //查询有效的单
                        List<TicketStorageBlankCardManage> tsList = mapper.isExisting(vo);
                        if (tsList != null && tsList.size() > 0) {
                            rmsg.addMessage("数据库中已存在重复的逻辑卡号!");
                            return rmsg;
                        }
                    }
                }
            }

            for (TicketStorageBlankCardManage tsbm : pos) {
                List<TicketStorageBlankCardManage> tsbcms = mapper.getTicketStorageBlankCardManage(tsbm);
                if (tsbcms != null && tsbcms.size() > 0) {
                    TicketStorageBlankCardManage poManage = tsbcms.get(0);
                    TicketStorageBlankCardManage poNew = new TicketStorageBlankCardManage();
                    poNew.setYear(poManage.getYear());
                    String batchNo = "";
                    poNew.setProduce_factory_id(poManage.getProduce_factory_id());
                    //储值票类
                    if (prepaidTickList.contains(poManage.getBlank_card_type())) {
                        poNew.setCardTypeList(prepaidTickList);
                    } else if (singleTickList.contains(poManage.getBlank_card_type())) { // 单程票类
                        poNew.setCardTypeList(singleTickList);
                    } else if (phoneTickList.contains(poManage.getBlank_card_type())) { //手机票
                        poNew.setCardTypeList(phoneTickList);
                    }
                    batchNo = mapper.getCurrentBatchNo(poNew);
                    if (batchNo != null && !"".equals(batchNo)) {
                        batchNo = String.valueOf(Integer.valueOf(batchNo) + 1);
                    } else {
                        batchNo = "1";  //新的一年，从 1 开始
                    }
                    if (batchNo.length() == 1) {
                        batchNo = '0' + batchNo;
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
                    User user = (User) request.getSession().getAttribute("User");
                    poManage.setForm_maker(user.getAccount());
                    poManage.setRecord_flag_text(InOutConstant.RECORD_FLAG_BILL_EFFECT);
                    poManage.setRecord_flag(InOutConstant.RECORD_FLAG_BILL_UNAUDIT);
                    poManage.setBill_date(sdf.format(new Date()));
                    poManage.setBatch_no(batchNo);
                    poManage.setFile_prefix("PRT" + poManage.getProduce_factory_id() + "." + poManage.getYear().substring(2, 4) + batchNo);
                    int result = mapper.auditTicketStorageBlankCardManage(poManage);
                    if (result != 1) {
                        throw new Exception("单号" + poManage.getBill_no() + "状态不能被审核");
                    }
                    //审核 
                    //非普通单程票
                    if (!singleTickList.contains(poManage.getBlank_card_type())) {
                        String currentStartLogicNo = mapper.getCurrentStartLogicNo(poManage);
                        if (currentStartLogicNo != null && !currentStartLogicNo.equals("")) {
                            TicketStorageBlankCardManage tstemp = new TicketStorageBlankCardManage();
                            tstemp.setBill_no(poManage.getBill_no());
                            tstemp.setCurrent_no(poManage.getEnd_logicno());
                            tstemp.setCurrent_int_no(poManage.getEnd_logicno());
                            tstemp.setBlank_card_type(poManage.getBlank_card_type());
                            result = mapper.updateCurrentLogicNo(tstemp);
                        } else {
                            TicketStorageBlankCardManage tstemp = new TicketStorageBlankCardManage();
                            tstemp.setBill_no(poManage.getBill_no());
                            tstemp.setEnd_logicno(poManage.getEnd_logicno());
                            tstemp.setStart_logicno(poManage.getStart_logicno());
                            tstemp.setBlank_card_type(poManage.getBlank_card_type());
                            result = mapper.addCurrentLogicNo(tstemp);
                        }
                        if (result != 1) {
                            throw new Exception("单号" + poManage.getBill_no() + "更新逻辑卡号表失败");
                        }
                    }

                    //出库单及出库单明细更换临时单号为正式单号
                    String newBillNo = "";
                    if (waterNoString != null) {
                        newBillNo = InOutConstant.TYPE_BILL_NO_BC_LOGIC_NO + sdfYear.format(new Date()) + NumberUtil.formatNumber(String.valueOf(Integer.parseInt(waterNoString) + 1), InOutConstant.LEN_BILL_NO - 6);
                        TicketStorageBlankCardManage tstemp = new TicketStorageBlankCardManage();
                        tstemp.setCurrent_int_no(String.valueOf(Integer.parseInt(waterNoString) + 1));
                        tstemp.setBill_main_type_id(InOutConstant.TYPE_BILL_NO_BC_LOGIC_NO);
                        mapper.updateCurrentBillNo(tstemp);
                    } else {
                        newBillNo = InOutConstant.TYPE_BILL_NO_BC_LOGIC_NO + sdfYear.format(new Date()) + NumberUtil.formatNumber("1", InOutConstant.LEN_BILL_NO - 6);
                        TicketStorageBlankCardManage tstemp = new TicketStorageBlankCardManage();
                        tstemp.setCurrent_int_no("1");
                        tstemp.setCurrent_no("");
                        tstemp.setBill_main_type_id(InOutConstant.TYPE_BILL_NO_BC_LOGIC_NO);
                        mapper.addCurrentBillNo(tstemp);
                    }
                    TicketStorageBlankCardManage tstemp1 = new TicketStorageBlankCardManage();
                    tstemp1.setOld_bill_no(poManage.getBill_no());
                    tstemp1.setBill_no(newBillNo);
                    if (phoneTickList.contains(poManage.getBlank_card_type())){
                        tstemp1.setIs_used("0");
                    }else{
                        tstemp1.setIs_used("1");
                    }
                    mapper.updateBillNo(tstemp1);
                    returnViewId = newBillNo;
                    n += 1;
                }
            }
            txMgr.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            txMgr.rollback(status);
            rmsg.addMessage(e.getMessage());
            return rmsg;
        }
        logUtil.logOperation(CommandConstant.COMMAND_AUDIT, request, LogConstant.auditSuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.auditSuccessMsg(n));
        return rmsg;
    }

    public OperationResult queryParm(HttpServletRequest request, TicketStorageBlankCardManageMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageBlankCardManage queryCondition;
        List<TicketStorageBlankCardManage> resultSet;
        OperationResult or = new OperationResult();
        try {
            queryCondition = this.getQueryCondition(request, or);
            if (queryCondition == null) {
                return null;
            }
            String billEndTime = queryCondition.getBill_date_end();
            if (billEndTime != null && !billEndTime.equals("")) {
                queryCondition.setBill_date_end(billEndTime + " 23:59:59");
            }
            resultSet = mapper.getTicketStorageBlankCardManage(queryCondition);
            or.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;
    }

    public OperationResult addParm(HttpServletRequest request, TicketStorageBlankCardManageMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageBlankCardManage po = this.getQueryConditionForOp(request);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        User user = (User) request.getSession().getAttribute("User");
        po.setForm_maker(user.getAccount());
        po.setRecord_flag(InOutConstant.RECORD_FLAG_BILL_UNAUDIT);
        po.setBill_date(sdf.format(new Date()));
        List<String> cardLogicType = mapper.getCardLogicList();
        //校验选择 生产厂商 80地铁HCE，81银行HCE时，只能选择手机票，选择其他厂商时，不能选择手机票 
        if (po.getProduce_factory_id().equals(InOutConstant.PRODUCE_FACTORY_CODE_METRO_HCE) || po.getProduce_factory_id().equals(InOutConstant.PRODUCE_FACTORY_CODE_BANK_HCE)) {
            //if(!po.getBlank_card_type().equals(InOutConstant.PRODUCE_FACTORY_CODE_METRO_HCE)){
            if (!cardLogicType.contains(po.getBlank_card_type())) {
                rmsg.addMessage("增加失败，当生产厂商为'地铁HCE'或者‘银行HCE’时，只能选择HCE手机类的票卡类型");
                //回显输入框的内容
                this.returnViewInput(request);
                return rmsg;
            } else {
                if (po.getProduce_factory_id().equals(InOutConstant.PRODUCE_FACTORY_CODE_METRO_HCE) && !po.getStart_logicno().substring(0, 1).equals("1")) {
                    rmsg.addMessage("增加失败，生产厂商为'地铁HCE'，开始序列号首位应该为1");
                    //回显输入框的内容
                    this.returnViewInput(request);
                    return rmsg;
                } else if (po.getProduce_factory_id().equals(InOutConstant.PRODUCE_FACTORY_CODE_BANK_HCE) && !po.getStart_logicno().substring(0, 1).equals("9")) {
                    rmsg.addMessage("增加失败，生产厂商为'银行HCE'，开始序列号首位应该为9");
                    //回显输入框的内容
                    this.returnViewInput(request);
                    return rmsg;
                }
            }

        } else {
            //if(po.getBlank_card_type().equals(InOutConstant.PRODUCE_FACTORY_CODE_METRO_HCE)){
            if (cardLogicType.contains(po.getBlank_card_type())) {
                rmsg.addMessage("增加失败，当生产厂商不为'地铁HCE'或者‘银行HCE时，不能选择HCE手机类的票卡类型");
                //回显输入框的内容
                this.returnViewInput(request);
                return rmsg;
            }
        }
//        if (po.getIndustry_code().equals("03")) {
//            if (!po.getStart_logicno().substring(0, 1).equals("1")) {
//                rmsg.addMessage("增加失败，行业代码为地铁时，开始序列号首位应该为1");
//                return rmsg;
//            }
//        }
//        if (po.getIndustry_code().equals("99")) {
//            if (!po.getStart_logicno().substring(0, 1).equals("9")) {
//                rmsg.addMessage("增加失败，行业代码为银行时，开始序列号首位应该为9");
//                return rmsg;
//            }
//        }
        //根据逻辑卡种类型 判断 输入的起始序列号是否合法  //储值票 序列号应为9位
        if (po.getBlank_card_type() != null && !po.getBlank_card_type().trim().equals("") && !singleTickList.contains(po.getBlank_card_type())) {
            if (!Pattern.matches("[0-9]{9}", po.getStart_logicno())) {
                rmsg.addMessage("储值票起始序列号应为9位数字!");
                //回显输入框的内容
                this.returnViewInput(request);
                return rmsg;
            }
            Long startLongicNoNum = Long.valueOf(po.getStart_logicno().trim());
            long endLogicNo = startLongicNoNum + Integer.valueOf(po.getQty()) - 1;
            if (endLogicNo > 999999999) {
                rmsg.addMessage("储值票结束序列号大于999999999(起始序列号+数量-1)!");
                //回显输入框的内容
                this.returnViewInput(request);
                return rmsg;
            }
            po.setEnd_logicno(String.format("%09d", Integer.parseInt(String.valueOf(endLogicNo))));
        }

        LogUtil logUtil = new LogUtil();
        int n = 0;
        po.setBill_no(null);
        if (po.getRemark() == null) {
            po.setRemark("");
        }
        try {
            //非单程票 并且 开始逻辑卡号为空
            if (!singleTickList.contains(po.getBlank_card_type()) && (po.getStart_logicno() == null || po.getStart_logicno().trim().equals(""))) {
                rmsg.addMessage("非普通单程票逻辑卡号不能为空");
                //回显输入框的内容
                this.returnViewInput(request);
                return rmsg;
            }

            //票卡类型：普通单程票    防止输入,置空
            if (singleTickList.contains(po.getBlank_card_type())) {
                po.setStart_logicno("");
                po.setEnd_logicno("");
                po.setStart_logic_all("");
                po.setEnd_logic_all("");
            } else {
                //判断是否存在的是当前版本的
                List<TicketStorageBlankCardManage> listTemp = mapper.isExisting(po);
                if (listTemp != null && listTemp.size() > 0) {
                    returnViewId = listTemp.get(0).getBill_no();
                    this.returnViewInput(request);
                    rmsg.addMessage("数据库中已存在重复的逻辑卡号!");
                    return rmsg;
                }
//                if (!checkQty(po)) {
//                    rmsg.addMessage("计算的结束逻辑卡号大于逻辑卡号最大序列!");
//                    return rmsg;
//                }
                //通过开始逻辑卡号、数量计算出结束逻辑卡号
//                po.setEnd_logicno(this.calcEndLogicNo(po));
                this.setLogicNoAll(po);    //生成16位的逻辑卡号
            }

            n = this.addByTrans(request, mapper, po);
        } catch (Exception e) {
            this.returnViewInput(request);
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, LogConstant.addSuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.addSuccessMsg(n));
        return rmsg;
    }

    /**
     * 通过开始逻辑卡号、数量计算出结束逻辑卡号
     *
     * @param vo
     * @return
     */
    private String calcEndLogicNo(TicketStorageBlankCardManage vo) {
        Long startLongicNoNum = Long.valueOf(vo.getStart_logicno().trim());
        int qty = Integer.valueOf(vo.getQty());
        String endLogicNo = String.valueOf(startLongicNoNum + qty - 1);
        int startLongicNoLen = vo.getStart_logicno().trim().length();
        String prefix = vo.getStart_logicno().trim().substring(0, startLongicNoLen - endLogicNo.length());

        return prefix + endLogicNo;
    }

    /**
     * 校验计算的结束逻辑卡号是否大于最大序列号（8位）
     *
     * @param vo
     * @return
     */
    private boolean checkQty(TicketStorageBlankCardManage vo) {
        Long startLongicNoNum = Long.valueOf(vo.getStart_logicno().trim());
        int qty = Integer.valueOf(vo.getQty());
        String endLogicNo = String.valueOf(startLongicNoNum + qty - 1);
        return !(endLogicNo.length() > vo.getStart_logicno().trim().length());  //如果有进位,说明超过了99999999
    }

    //获取查询参数
    private TicketStorageBlankCardManage getQueryCondition(HttpServletRequest request, OperationResult or) {
        TicketStorageBlankCardManage qCon = new TicketStorageBlankCardManage();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        User user = (User) request.getSession().getAttribute("User");
//        String operatorID = user.getAccount();
        qCon.setBill_no(FormUtil.getParameter(request, "q_bill_no"));
        qCon.setYear(FormUtil.getParameter(request, "q_year"));
        qCon.setBatch_no(FormUtil.getParameter(request, "q_batch_no"));
        qCon.setProduce_factory_id(FormUtil.getParameter(request, "q_produce_factory_id"));
        qCon.setBlank_card_type(FormUtil.getParameter(request, "q_blank_card_type"));
        qCon.setBill_date_start(FormUtil.getParameter(request, "q_beginTime"));
        qCon.setBill_date_end(FormUtil.getParameter(request, "q_endTime"));
        qCon.setRecord_flag(FormUtil.getParameter(request, "q_record_flag"));
        qCon.setIs_used(FormUtil.getParameter(request, "q_is_used"));
        qCon.setApp_identifier(FormUtil.getParameter(request, "q_app_identifier"));
//        qCon.setBill_date(sdf.format(new Date()));
//        qCon.setForm_maker(operatorID);

//        if (qCon.getStart_logicno() != null && !qCon.getStart_logicno().trim().isEmpty()) {
//            Long startLongicNoNum = Long.valueOf(qCon.getStart_logicno().trim());
//            int qty = Integer.valueOf(qCon.getQty());
//            String endLogicNo = String.valueOf(startLongicNoNum + qty - 1);
//            qCon.setEnd_logicno(String.format("%08d", Integer.parseInt(endLogicNo)));
//        }
        return qCon;
    }

    public TicketStorageBlankCardManage getReqAttribute(HttpServletRequest request) {
        TicketStorageBlankCardManage po = new TicketStorageBlankCardManage();
        po.setBill_no(FormUtil.getParameter(request, "q_bill_no"));
        po.setYear(FormUtil.getParameter(request, "q_year"));
        po.setBatch_no(FormUtil.getParameter(request, "q_batch_no"));
        po.setProduce_factory_id(FormUtil.getParameter(request, "q_produce_factory_id"));
        po.setBlank_card_type(FormUtil.getParameter(request, "q_blank_card_type"));
        po.setBill_date_start(FormUtil.getParameter(request, "q_beginTime"));
        po.setBill_date_end(FormUtil.getParameter(request, "q_endTime"));
        po.setRecord_flag(FormUtil.getParameter(request, "q_record_flag"));
        po.setIs_used(FormUtil.getParameter(request, "q_is_used"));
        return po;
    }

    private int addByTrans(HttpServletRequest request, TicketStorageBlankCardManageMapper mapper, TicketStorageBlankCardManage po) throws Exception {
        String waterNoString = mapper.getCurrentBillNoTemp(InOutConstant.TYPE_BILL_NO_BC_LOGIC_NO_TEMP);
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);

            SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
            //出库单及出库单明细更换临时单号为正式单号
            String newBillNo = "";
            if (waterNoString != null) {
                newBillNo = InOutConstant.TYPE_BILL_NO_BC_LOGIC_NO + "T" + sdfYear.format(new Date()) + NumberUtil.formatNumber(String.valueOf(Integer.parseInt(waterNoString) + 1), InOutConstant.LEN_BILL_NO - 7);
                TicketStorageBlankCardManage tstemp = new TicketStorageBlankCardManage();
                tstemp.setCurrent_int_no(String.valueOf(Integer.parseInt(waterNoString) + 1));
                tstemp.setBill_main_type_id(InOutConstant.TYPE_BILL_NO_BC_LOGIC_NO);
                mapper.updateCurrentBillNoTemp(tstemp);
            } else {
                newBillNo = InOutConstant.TYPE_BILL_NO_BC_LOGIC_NO + "T" + sdfYear.format(new Date()) + NumberUtil.formatNumber("1", InOutConstant.LEN_BILL_NO - 7);
                TicketStorageBlankCardManage tstemp = new TicketStorageBlankCardManage();
                tstemp.setCurrent_int_no("1");
                tstemp.setCurrent_no("");
                tstemp.setBill_main_type_id(InOutConstant.TYPE_BILL_NO_BC_LOGIC_NO);
                mapper.addCurrentBillNoTemp(tstemp);
            }
            po.setBill_no(newBillNo);
            returnViewId = newBillNo;

            n = mapper.addTicketStorageBlankCardManage(po);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            e.printStackTrace();
        }
        return n;
    }

    public OperationResult modifyParm(HttpServletRequest request, TicketStorageBlankCardManageMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageBlankCardManage po = this.getQueryConditionForOp(request);
        returnViewId = po.getBill_no();

        //“审核通过”的就不能修改
        List<TicketStorageBlankCardManage> tsList = mapper.getTicketStorageBlankCardManageById(po);
        if (tsList != null && tsList.size() > 0) {
            String recordFlag = tsList.get(0).getRecord_flag();
            if (recordFlag != null && recordFlag.equals("0")) {
                rmsg.addMessage("订单已经审核通过，不能修改！");
                //回显输入框的内容
                this.returnViewInput(request);
                return rmsg;
            }
        }
//        if (po.getIndustry_code().equals("03")) {
//            if (!po.getStart_logicno().substring(0, 1).equals("1")) {
//                rmsg.addMessage("修改失败，行业代码为地铁时，开始序列号首位应该为1");
//                return rmsg;
//            }
//        }
//        if (po.getIndustry_code().equals("99")) {
//            if (!po.getStart_logicno().substring(0, 1).equals("9")) {
//                rmsg.addMessage("修改失败，行业代码为地铁时，开始序列号首位应该为9");
//                return rmsg;
//            }
//        }
        LogUtil logUtil = new LogUtil();
        int n = 0;
        if (po.getRemark() == null) {
            po.setRemark("");
        }
        try {
            //校验选择 生产厂商 80地铁HCE，81银行HCE时，只能选择手机票，选择其他厂商时，不能选择手机票 
//        if(po.getProduce_factory_id().equals(InOutConstant.PRODUCE_FACTORY_CODE_METRO_HCE)||po.getProduce_factory_id().equals(InOutConstant.PRODUCE_FACTORY_CODE_BANK_HCE))
//        {
//            if(!po.getBlank_card_type().equals(InOutConstant.PRODUCE_FACTORY_CODE_METRO_HCE)){
//                rmsg.addMessage("修改失败，当生产厂商为'地铁HCE'或者‘银行HCE’时，票卡类型只能选择手机票");
//                return rmsg;
//            }
//        }else{
//            if(po.getBlank_card_type().equals(InOutConstant.PRODUCE_FACTORY_CODE_METRO_HCE)){
//                rmsg.addMessage("修改失败，当生产厂商不为'地铁HCE'或者‘银行HCE时，票卡类型不能选择手机票");
//                return rmsg;
//            }
//        }
            List<String> cardLogicType = mapper.getCardLogicList();
            //校验选择 生产厂商 80地铁HCE，81银行HCE时，只能选择手机票，选择其他厂商时，不能选择手机票 
            if (po.getProduce_factory_id().equals(InOutConstant.PRODUCE_FACTORY_CODE_METRO_HCE) || po.getProduce_factory_id().equals(InOutConstant.PRODUCE_FACTORY_CODE_BANK_HCE)) {
                //if(!po.getBlank_card_type().equals(InOutConstant.PRODUCE_FACTORY_CODE_METRO_HCE)){
                if (!cardLogicType.contains(po.getBlank_card_type())) {
                    rmsg.addMessage("修改失败，当生产厂商为'地铁HCE'或者‘银行HCE’时，只能选择HCE手机类的票卡类型");
                    //回显输入框的内容
                    this.returnViewInput(request);
                    return rmsg;
                } else {
                    if (po.getProduce_factory_id().equals(InOutConstant.PRODUCE_FACTORY_CODE_METRO_HCE) && !po.getStart_logicno().substring(0, 1).equals("1")) {
                        rmsg.addMessage("修改失败，生产厂商为'地铁HCE'，开始序列号首位应该为1");
                        //回显输入框的内容
                        this.returnViewInput(request);
                        return rmsg;
                    } else if (po.getProduce_factory_id().equals(InOutConstant.PRODUCE_FACTORY_CODE_BANK_HCE) && !po.getStart_logicno().substring(0, 1).equals("9")) {
                        rmsg.addMessage("修改失败，生产厂商为'银行HCE'，开始序列号首位应该为9");
                        //回显输入框的内容
                        this.returnViewInput(request);
                        return rmsg;
                    }
                }

            } else {
                //if(po.getBlank_card_type().equals(InOutConstant.PRODUCE_FACTORY_CODE_METRO_HCE)){
                if (cardLogicType.contains(po.getBlank_card_type())) {
                    rmsg.addMessage("修改失败，当生产厂商不为'地铁HCE'或者‘银行HCE时，不能选择HCE手机类的票卡类型");
                    //回显输入框的内容
                    this.returnViewInput(request);
                    return rmsg;
                }
            }
//            if (po.getProduce_factory_id().equals(InOutConstant.PRODUCE_FACTORY_CODE_METRO_HCE) || po.getProduce_factory_id().equals(InOutConstant.PRODUCE_FACTORY_CODE_BANK_HCE)) {
//                //if(!po.getBlank_card_type().equals(InOutConstant.PRODUCE_FACTORY_CODE_METRO_HCE)){
//                if (!cardLogicType.contains(po.getBlank_card_type())) {
//                    rmsg.addMessage("修改失败，当生产厂商为'地铁HCE'或者‘银行HCE’时，票卡类型只能选择手机票");
//                    return rmsg;
//                }
//            } else {
//                //if(po.getBlank_card_type().equals(InOutConstant.PRODUCE_FACTORY_CODE_METRO_HCE)){
//                if (cardLogicType.contains(po.getBlank_card_type())) {
//                    rmsg.addMessage("修改失败，当生产厂商不为'地铁HCE'或者‘银行HCE时，票卡类型不能选择手机票");
//                    return rmsg;
//                }
//            }
            //非普通单程票 并且 开始逻辑卡号为空
            if (!singleTickList.contains(po.getBlank_card_type()) && (po.getStart_logicno() == null || po.getStart_logicno().trim().equals(""))) {
                rmsg.addMessage("非普通单程票逻辑卡号不能为空");
                //回显输入框的内容
                this.returnViewInput(request);
                return rmsg;
            }
            //票卡类型：单程票    
            if (singleTickList.contains(po.getBlank_card_type())) {
                po.setStart_logicno("");
                po.setEnd_logicno("");
                po.setStart_logic_all("");
                po.setEnd_logic_all("");
            } else {
//                TicketStorageBlankCardManage poTemp = new TicketStorageBlankCardManage();
//                poTemp.setStart_logicno(po.getStart_logicno());
                //根据逻辑卡种类型 判断 输入的起始序列号是否合法  //储值票 序列号应为9位
                if (po.getBlank_card_type() != null && !po.getBlank_card_type().trim().equals("") && !singleTickList.contains(po.getBlank_card_type())) {
                    if (!Pattern.matches("[0-9]{9}", po.getStart_logicno())) {
                        rmsg.addMessage("储值票起始序列号应为9位数字!");
                        //回显输入框的内容
                        this.returnViewInput(request);
                        return rmsg;
                    }
                    Long startLongicNoNum = Long.valueOf(po.getStart_logicno().trim());
                    long endLogicNo = startLongicNoNum + Integer.valueOf(po.getQty()) - 1;
                    if (endLogicNo > 999999999) {
                        rmsg.addMessage("储值票结束序列号大于999999999(起始序列号+数量-1)!");
                        //回显输入框的内容
                        this.returnViewInput(request);
                        return rmsg;
                    }
                    po.setEnd_logicno(String.format("%09d", Integer.parseInt(String.valueOf(endLogicNo))));
                }

//                poTemp.setEnd_logicno(this.calcEndLogicNo(po));
                List<TicketStorageBlankCardManage> listTemp = mapper.isExisting(po);
                if (listTemp != null && listTemp.size() > 0) {
                    returnViewId = listTemp.get(0).getBill_no();
                    rmsg.addMessage("数据库中已存在重复的逻辑卡号!");
                    //回显输入框的内容
                    this.returnViewInput(request);
                    return rmsg;
                }
//                if (!checkQty(po)) {
//                    rmsg.addMessage("计算的结束逻辑卡号大于逻辑卡号最大序列!");
//                    return rmsg;
//                }
                //通过开始逻辑卡号、数量计算出结束逻辑卡号
//                po.setEnd_logicno(this.calcEndLogicNo(po));
                this.setLogicNoAll(po);
            }
            n = this.modifyByTrans(request, mapper, po);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));
        return rmsg;
    }

    private int modifyByTrans(HttpServletRequest request, TicketStorageBlankCardManageMapper mapper, TicketStorageBlankCardManage po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = mapper.modifyTicketStorageBlankCardManage(po);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult deleteParm(HttpServletRequest request, TicketStorageBlankCardManageMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageBlankCardManage> pos = this.getReqAttributeForDelete(request);
        TicketStorageBlankCardManage prmVo = new TicketStorageBlankCardManage();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            //含有已经“审核通过”的就不能删除
            for (TicketStorageBlankCardManage tsbcm : pos) {
                List<TicketStorageBlankCardManage> tsList = mapper.getTicketStorageBlankCardManageById(tsbcm);
                if (tsList != null && tsList.size() > 0) {
                    String recordFlag = tsList.get(0).getRecord_flag();
                    if (recordFlag != null && recordFlag.equals("0")) {
                        rmsg.addMessage("订单已经审核通过，不能删除！");
                        return rmsg;
                    }
                }
            }
            n = this.deleteByTrans(request, mapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.delSuccessMsg(n));
        return rmsg;
    }

    private Vector<TicketStorageBlankCardManage> getReqAttributeForDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageBlankCardManage> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private int deleteByTrans(HttpServletRequest request, TicketStorageBlankCardManageMapper tsbudmMapper, Vector<TicketStorageBlankCardManage> pos, TicketStorageBlankCardManage prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageBlankCardManage po : pos) {
                n += tsbudmMapper.deleteTicketStorageBlankCardManage(po);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private Vector<TicketStorageBlankCardManage> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageBlankCardManage> sds = new Vector();
        TicketStorageBlankCardManage sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getTicketStorageBlankCardManage(strIds, "#");
            sds.add(sd);
        }
        return sds;
    }

    private TicketStorageBlankCardManage getTicketStorageBlankCardManage(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageBlankCardManage sd = new TicketStorageBlankCardManage();
        Vector<TicketStorageBlankCardManage> sds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setBill_no(tmp);
                continue;
            }
        }
        return sd;

    }

    public OperationResult queryForOp(HttpServletRequest request, TicketStorageBlankCardManageMapper mapper, OperationLogMapper opLogMapper, OperationResult opResult, String command) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageBlankCardManage queryCondition;
        List<TicketStorageBlankCardManage> resultSet;

        try {
            if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_MODIFY) || command.equals(CommandConstant.COMMAND_AUDIT)) {
                TicketStorageBlankCardManage ts = new TicketStorageBlankCardManage();
                ts.setBill_no(returnViewId);
                resultSet = mapper.getTicketStorageBlankCardManageById(ts);
            } else {
                queryCondition = this.getQueryConditionForOp(request);
                resultSet = mapper.getTicketStorageBlankCardManage(queryCondition);
            }
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    private TicketStorageBlankCardManage getQueryConditionForOp(HttpServletRequest request) {
        TicketStorageBlankCardManage qCon = new TicketStorageBlankCardManage();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            qCon.setBill_no(FormUtil.getParameter(request, "d_bill_no"));
            qCon.setYear(FormUtil.getParameter(request, "d_year"));
            qCon.setProduce_factory_id(FormUtil.getParameter(request, "d_produce_factory_id"));
            qCon.setBlank_card_type(FormUtil.getParameter(request, "d_blank_card_type"));
            qCon.setStart_logicno(FormUtil.getParameter(request, "d_start_logicno"));
            qCon.setEnd_logicno(FormUtil.getParameter(request, "d_end_logicno"));
            qCon.setQty(FormUtil.getParameter(request, "d_qty"));
            qCon.setRemark(FormUtil.getParameter(request, "d_remark"));
            qCon.setCity_code(FormUtil.getParameter(request, "d_city_code"));   //城市代码
            qCon.setIndustry_code(FormUtil.getParameter(request, "d_industry_code"));     //行业代码
            qCon.setApp_identifier(FormUtil.getParameter(request, "d_app_identifier"));     //应用标识
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setBill_no(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_bill_no"));
                qCon.setYear(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_year"));
                qCon.setBatch_no(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_batch_no"));
                qCon.setProduce_factory_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_produce_factory_id"));
                qCon.setBlank_card_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_blank_card_type"));
                qCon.setBill_date_start(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_beginTime"));
                qCon.setBill_date_end(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_endTime"));
                qCon.setRecord_flag(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_record_flag"));
                qCon.setIs_used(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_is_used"));
            }
        }
        return qCon;
    }

    private void getListShow(ModelAndView mv) {
        List<PubFlag> recordflags = pubFlagMapper.getCodesByType(10);
        List<PubFlag> produceFactorys = pubFlagMapper.getProduceFactory();
        List<PubFlag> isUsedFlag = pubFlagMapper.getCodesByType(42);
        List<TicketStorageBlankCardManage> logicCardTypes = mapper.getBlankCardTypes();         //逻辑卡类型
        List<PubFlag> cityCodes = pubFlagMapper.getCodesByType(61);				//城市代码         乌市邮编前 2 位，填写 83
        List<PubFlag> industryCodes = pubFlagMapper.getCodesByType(62);                         //行业代码 		地铁行业代码后 2 位，填写 03
        List<PubFlag> appIdentifiers = pubFlagMapper.getCodesByType(63);                        //应用标识    1 位，正式票：0，测试票：1

        mv.addObject("produceFactorys", produceFactorys);
//        mv.addObject("blankCardTypes", blankCardTypes);
        mv.addObject("isUsedFlag", isUsedFlag);
        mv.addObject("recordFlags", recordflags);
        mv.addObject("cityCodes", cityCodes);
        mv.addObject("industryCodes", industryCodes);
        mv.addObject("logicCardTypes", logicCardTypes);
        mv.addObject("appIdentifiers", appIdentifiers);
    }

    private void returnViewInput(HttpServletRequest request) {
        request.setAttribute("d_bill_no", FormUtil.getParameter(request, "d_bill_no"));
        request.setAttribute("d_produce_factory_id", FormUtil.getParameter(request, "d_produce_factory_id"));
        request.setAttribute("d_year", FormUtil.getParameter(request, "d_year"));
        request.setAttribute("d_blank_card_type", FormUtil.getParameter(request, "d_blank_card_type"));
        request.setAttribute("d_start_logicno", FormUtil.getParameter(request, "d_start_logicno"));
        request.setAttribute("d_end_logicno", FormUtil.getParameter(request, "d_end_logicno"));
        request.setAttribute("d_qty", FormUtil.getParameter(request, "d_qty"));
        request.setAttribute("d_remark", FormUtil.getParameter(request, "d_remark"));
        request.setAttribute("d_city_code", FormUtil.getParameter(request, "d_city_code"));
        request.setAttribute("d_industry_code", FormUtil.getParameter(request, "d_industry_code"));
        request.setAttribute("d_app_identifier", FormUtil.getParameter(request, "d_app_identifier"));
    }

    @RequestMapping("/ticketStorageBlankCardManageExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.basicinfo.entity.TicketStorageBlankCardManage");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    /**
     * 组合生成16位的开始和结束逻辑卡号 城市代码 + 行业代码 + 逻辑卡类型代码 + 应用标识 + 预留位(0) + 8位序列号
     *
     * @param po
     */
    private void setLogicNoAll(TicketStorageBlankCardManage po) {
        //城市代码 + 行业代码 + 逻辑卡类型代码 + 应用标识 + 9位序列号
        String prefix = po.getCity_code() + po.getIndustry_code() + po.getBlank_card_type() + po.getApp_identifier();
        po.setStart_logic_all(prefix + po.getStart_logicno());
        po.setEnd_logic_all(prefix + po.getEnd_logicno());
    }

}
