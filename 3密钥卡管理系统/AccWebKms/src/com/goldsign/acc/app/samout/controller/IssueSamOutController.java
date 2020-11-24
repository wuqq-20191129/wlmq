package com.goldsign.acc.app.samout.controller;

import com.goldsign.acc.app.samout.controller.*;
import com.goldsign.acc.app.produceorder.entity.LogicNos;
import com.goldsign.acc.app.produceorder.entity.ProduceOrder;
import com.goldsign.acc.app.samout.entity.IssueSamOut;
import com.goldsign.acc.app.produceorder.mapper.LogicNosMapper;
import com.goldsign.acc.app.produceorder.mapper.ProduceOrderMapper;
import com.goldsign.acc.app.samout.mapper.IssueSamOutMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.constant.SamCardConstant;

import com.goldsign.acc.frame.controller.SamBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.BillMapper;
import com.goldsign.acc.frame.mapper.OperationLogMapper;

import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 卡发行出库
 *
 * @author xiaowu 20170829
 */
@Controller
public class IssueSamOutController extends IssueSamOutParentController {

    @Autowired
    private IssueSamOutMapper mapper;

    @Autowired
    private ProduceOrderMapper produceOrderMapper;

    @Autowired
    private LogicNosMapper detailMapper;

    List<IssueSamOut> returnViewId = new ArrayList<IssueSamOut>();

    @RequestMapping(value = "/issueSamOutAction")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/samout/issueSamOut.jsp");
        String command = request.getParameter("command");
        String operType = request.getParameter("operType");
        OperationResult opResult = new OperationResult();
        returnViewId.clear();
        try {
            if (command != null) {
                command = command.trim();
                if (this.isOperForPlan(operType)) {
                    //生产单操作
                    if (command.equals(CommandConstant.COMMAND_QUERY)) {
                        opResult = this.queryPlan(request, this.mapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_ADD)) {
                        opResult = this.addPlan(request, this.mapper, this.billMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_DELETE)) {
                        opResult = this.deletePlan(request, this.mapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_AUDIT)) {
                        opResult = this.auditPlan(request, this.mapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                        opResult = this.modifyPlan(request, this.mapper, this.operationLogMapper);
                    }
                    if (command.equals("detail")) {
                        mv = new ModelAndView("/jsp/produceorder/produceOrderDetail.jsp");
                        opResult = this.queryInfoDetail(request, this.mapper, this.operationLogMapper);
                    }
                }
                if (this.isUpdateOp(command, operType))//更新操作，增、删、改、审核,查询更新结果或原查询结果
                {
                    this.queryUpdateResult(command, operType, request, mapper, operationLogMapper, opResult, mv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        //初始化生产订单下拉框
        this.saveResultProduceOrder(request);
        String[] attrNames = this.getAttributeNames(command, operType);
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        if (command != null && !command.equals("detail")) {
            this.getResultSetText(opResult.getReturnResultSet(), mv, operType);
        }
        this.baseHandlerForOutIn(request, response, mv); //出入库基本回传参数
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    @RequestMapping("/issueSamOutExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElements(request);
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    public OperationResult queryInfoDetail(HttpServletRequest request, IssueSamOutMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        LogicNos queryCondition = new LogicNos();
        List<LogicNos> resultSet;
        String orderNo = (String) request.getParameter("orderNo");

        try {
            if (orderNo != null) {
                queryCondition.setOrder_no(orderNo);
                resultSet = detailMapper.getLogicNos(queryCondition);
            } else {
                resultSet = new ArrayList<LogicNos>();
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

    private String[] getAttributeNames(String command, String operType) {
        if (this.isOperForPlan(operType)) {
            String[] attrNames = {BILL_STATUES, SAM_TYPES, RECORD_FLAG_IN_STOCK_STATE, ISBADS};
            return attrNames;
        }
        return new String[0];
    }

    private void queryUpdateResult(String command, String operType, HttpServletRequest request, IssueSamOutMapper mapper,
            OperationLogMapper opLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        }
        if (this.isOperForPlan(operType)) { //计划单查询     
            List<IssueSamOut> poList = new ArrayList<IssueSamOut>();
            if (returnViewId != null && returnViewId.size() > 0) {
                for (IssueSamOut po : returnViewId) {
                    poList.addAll(mapper.queryPlanForModify(po));
                }
            } else {  //其它如删除查询回显全部
                poList.addAll(mapper.queryPlan(new IssueSamOut()));
            }
            opResult.setReturnResultSet(poList);
        }

    }

    private void getResultSetText(List resultSet, ModelAndView mv, String operType) {
        if (this.isOperForPlan(operType)) {
            this.getResultSetTextForPlan(resultSet, mv);
            return;

        }
    }

    private void getResultSetTextForPlan(List<IssueSamOut> resultSet, ModelAndView mv) {
        List<PubFlag> billStatues = (List<PubFlag>) mv.getModelMap().get(SamBaseController.BILL_STATUES);
        List<PubFlag> samTypes = (List<PubFlag>) mv.getModelMap().get(SamBaseController.SAM_TYPES);
        List<PubFlag> inStockStates = (List<PubFlag>) mv.getModelMap().get(SamBaseController.RECORD_FLAG_IN_STOCK_STATE);
        List<PubFlag> isBads = (List<PubFlag>) mv.getModelMap().get(SamBaseController.ISBADS);

        for (IssueSamOut sd : resultSet) {
            if (billStatues != null && !billStatues.isEmpty()) {
                sd.setOrder_state_text(DBUtil.getTextByCode(sd.getOrder_state(), billStatues));
            }
        }
        for (IssueSamOut sd : resultSet) {
            if (samTypes != null && !samTypes.isEmpty()) {
                sd.setSam_type_text(DBUtil.getTextByCode(sd.getSam_type(), samTypes));
            }
        }
        for (IssueSamOut sd : resultSet) {
            if (inStockStates != null && !inStockStates.isEmpty()) {
                sd.setIn_stock_state_text(DBUtil.getTextByCode(sd.getIn_stock_state(), inStockStates));
            }
        }
        for (IssueSamOut sd : resultSet) {
            if (isBads != null && !isBads.isEmpty()) {
                sd.setIsBadName(DBUtil.getTextByCode(sd.getIs_bad(), isBads));
            }
        }
    }

    public OperationResult queryForOpPlan(HttpServletRequest request, IssueSamOutMapper mapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        IssueSamOut queryCondition;
        List<IssueSamOut> resultSet;

        try {
            queryCondition = this.getQueryConditionForOpPlan(request, opResult);
            if (queryCondition == null) {
                return null;
            }
            resultSet = mapper.queryPlan(queryCondition);
            opResult.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult queryPlan(HttpServletRequest request, IssueSamOutMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        IssueSamOut queryCondition;
        List<IssueSamOut> resultSet;

        try {
            queryCondition = this.getQueryConditionIn(request);
            if (queryCondition.getOrder_no() != null && !queryCondition.getOrder_no().equals("")) {
                queryCondition.setOrder_no("%" + queryCondition.getOrder_no() + "%");
            }
            resultSet = mapper.queryPlan(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult addPlan(HttpServletRequest request, IssueSamOutMapper mapper, BillMapper billMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        IssueSamOut vo = this.getReqAttributePlan(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            //取生产工作单信息
            ProduceOrder pvo = new ProduceOrder();
            ProduceOrder pvoCheck = new ProduceOrder();
            pvo.setOrder_no(vo.getPdu_order_no());
            pvo = (ProduceOrder) produceOrderMapper.queryPlan(pvo).get(0);
            List<ProduceOrder> poList;
            //检验工作单对应逻辑卡号库存是否足够
            if (vo.getIs_bad().equals("2")) {
                pvoCheck.setOrder_no(vo.getPdu_order_no());
                pvoCheck.setStock_state_first(SamCardConstant.STOCK_STATE_PRODUCE_ORDER);
                pvoCheck.setProduce_type(SamCardConstant.PRODUCE_TYPE_PRODUCT);
                pvoCheck.setIs_instock(SamCardConstant.STOCK_STATE_IN);
                poList = produceOrderMapper.checkLogicNosPlanOrder(pvoCheck);
            } else {
                pvoCheck.setOrder_no(vo.getPdu_order_no());
                pvoCheck.setStock_state_first(SamCardConstant.STOCK_STATE_PRODUCE_ORDER);
                pvoCheck.setProduce_type(SamCardConstant.PRODUCE_TYPE_EMPTY);
                pvoCheck.setIs_instock(SamCardConstant.STOCK_STATE_IN);
                poList = produceOrderMapper.checkLogicNosPlanOrder(pvoCheck);
            }

            if (poList.size() == Integer.valueOf(pvo.getOrder_num())) {
                vo.setOrder_state(SamCardConstant.RECORD_FLAG_UNAUDITED);
                vo.setIn_stock_state(SamCardConstant.RECORD_FLAG_ALL_UNFINISHED);
                //插入条件：对应 Sam卡类型, ic_sam_order_plan工作单表
                n = this.addPlanByTrans(request, mapper, billMapper, vo, rmsg, returnViewId); //新建发行出库单
            } else {
                ProduceOrder svo = new ProduceOrder();
                String stLogicNo = "";
                if (poList.size() > 0) {
                    svo = (ProduceOrder) poList.get(0);
                    stLogicNo = svo.getStart_logic_no();
                }
                rmsg.addMessage("添加失败！逻辑卡" + stLogicNo + "没有库存！");
                return rmsg;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult deletePlan(HttpServletRequest request, IssueSamOutMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<IssueSamOut> pos = this.getReqAttributeForPlanDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deletePlanByTrans(request, mapper, pos);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public OperationResult auditPlan(HttpServletRequest request, IssueSamOutMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<IssueSamOut> pos = this.getReqAttributeForPlanAudit(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.auditPlanByTrans(request, mapper, pos, returnViewId);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_AUDIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_AUDIT, request, LogConstant.auditSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.auditSuccessMsg(n));
        return rmsg;
    }

    public OperationResult modifyPlan(HttpServletRequest request, IssueSamOutMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        IssueSamOut vo = this.getReqAttributePlan(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        String ids = vo.getPdu_order_no().substring(0, 4);
        if (ids.equals(SamCardConstant.ORDER_TYPE_CODE_RESET)) {
            rmsg.addMessage("修改失败！锁卡订单不能进行修改，请删除后重新生成订单");
            vo.setOrder_state(SamCardConstant.RECORD_FLAG_UNAUDITED);
            returnViewId.add(vo);
            return rmsg;  
        }
        try {
            n = this.modifyPlanByTrans(request, mapper, vo, rmsg, returnViewId);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private void saveResultProduceOrder(HttpServletRequest request) {
        IssueSamOut iso = new IssueSamOut();
        iso.setOrder_state(SamCardConstant.RECORD_FLAG_AUDITED);
        List<IssueSamOut> isoList = mapper.saveResultProduceOrder(iso);
        List<PubFlag> pfList = new ArrayList<PubFlag>();
        for (IssueSamOut is : isoList) {
            PubFlag pf = new PubFlag();
            pf.setCode(is.getOrder_no());
            pf.setCode_text(is.getOrder_no());
            pfList.add(pf);
        }
        request.setAttribute("produceOrderList", pfList);
    }

}
