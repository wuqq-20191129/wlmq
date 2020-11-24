package com.goldsign.acc.app.produceorder.controller;

import com.goldsign.acc.app.produceorder.entity.LogicNos;
import com.goldsign.acc.app.produceorder.entity.ProduceOrder;
import com.goldsign.acc.app.produceorder.mapper.LogicNosMapper;
import com.goldsign.acc.app.produceorder.mapper.ProduceOrderMapper;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 生产单制作
 *
 * @author xiaowu 20170828
 */
@Controller
public class ProduceOrderController extends ProduceOrderParentController {

    @Autowired
    private ProduceOrderMapper mapper;

    @Autowired
    private LogicNosMapper detailMapper;

    List<ProduceOrder> returnViewId = new ArrayList<>();

    @RequestMapping(value = "/produceOrderAction")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/produceorder/produceOrder.jsp");
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
                    this.queryUpdateResult(command, operType, request, mapper, opResult, mv);
                }

            }
        } catch (Exception e) {
            opResult.addMessage(e.getMessage());
        }

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

    @RequestMapping("/produceOrderExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElements(request);
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    public OperationResult queryInfoDetail(HttpServletRequest request, ProduceOrderMapper mapper, OperationLogMapper opLogMapper) throws Exception {
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
                resultSet = new ArrayList<>();
            }
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;
    }

    private String[] getAttributeNames(String command, String operType) {
        if (this.isOperForPlan(operType)) {
            String[] attrNames = {BILL_STATUES, SAM_TYPES, RECORD_FLAG_BILL_FINISH, ISBADS};
            return attrNames;
        }
        return new String[0];

    }

    private void queryUpdateResult(String command, String operType, HttpServletRequest request, ProduceOrderMapper mapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        }
        if (this.isOperForPlan(operType)) { //计划单查询     
            List<ProduceOrder> poList = new ArrayList<>();
            if (returnViewId != null && returnViewId.size() > 0) {
                for (ProduceOrder po : returnViewId) {
                    poList.addAll(mapper.queryPlanForModify(po));
                }
            } else {  //其它如删除查询回显全部
                poList.addAll(mapper.queryPlan(new ProduceOrder()));
            }
            opResult.setReturnResultSet(poList);
        }
    }

    private void getResultSetText(List resultSet, ModelAndView mv, String operType) {
        if (this.isOperForPlan(operType)) {
            this.getResultSetTextForPlan(resultSet, mv);
        }
    }

    private void getResultSetTextForPlan(List<ProduceOrder> resultSet, ModelAndView mv) {
        List<PubFlag> billStatues = (List<PubFlag>) mv.getModelMap().get(SamBaseController.BILL_STATUES);
        List<PubFlag> samTypes = (List<PubFlag>) mv.getModelMap().get(SamBaseController.SAM_TYPES);
        List<PubFlag> billFinishs = (List<PubFlag>) mv.getModelMap().get(SamBaseController.RECORD_FLAG_BILL_FINISH);
        List<PubFlag> isBads = (List<PubFlag>) mv.getModelMap().get(SamBaseController.ISBADS);

        for (ProduceOrder sd : resultSet) {
            if (billStatues != null && !billStatues.isEmpty()) {
                sd.setOrder_state_text(DBUtil.getTextByCode(sd.getOrder_state(), billStatues));
            }
        }
        for (ProduceOrder sd : resultSet) {
            if (samTypes != null && !samTypes.isEmpty()) {
                sd.setSam_type_text(DBUtil.getTextByCode(sd.getSam_type(), samTypes));
            }
        }
        for (ProduceOrder sd : resultSet) {
            if (billFinishs != null && !billFinishs.isEmpty()) {
                sd.setFinish_flag_text(DBUtil.getTextByCode(sd.getFinish_flag(), billFinishs));
            }
        }
        for (ProduceOrder sd : resultSet) {
            if (isBads != null && !isBads.isEmpty()) {
                sd.setIsBadName(DBUtil.getTextByCode(sd.getIs_bad(), isBads));
            }
        }
    }

    public OperationResult queryForOpPlan(HttpServletRequest request, ProduceOrderMapper mapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        ProduceOrder queryCondition;
        List<ProduceOrder> resultSet;

        try {
            queryCondition = this.getQueryConditionForOpPlan(request, opResult);
            if (queryCondition == null) {
                return null;
            }
            resultSet = mapper.queryPlan(queryCondition);
            opResult.setReturnResultSet(resultSet);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult queryPlan(HttpServletRequest request, ProduceOrderMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        ProduceOrder queryCondition;
        List<ProduceOrder> resultSet;

        try {
            queryCondition = this.getQueryConditionIn(request);
            if (queryCondition.getOrder_no() != null && !queryCondition.getOrder_no().equals("")) {
                queryCondition.setOrder_no("%" + queryCondition.getOrder_no() + "%");
            }
            resultSet = mapper.queryPlan(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult addPlan(HttpServletRequest request, ProduceOrderMapper mapper, BillMapper billMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        ProduceOrder vo = this.getReqAttributePlan(request);  
        //拦截起始逻辑卡号“0000000”这种情况
        if (this.isMin(vo)) {
            rmsg.addMessage("新增失败！起始逻辑卡号段最小值为0000001！");
            return rmsg;
        }
        if (vo.getIs_bad().equals("2") && !vo.getOrder_num().equals("1")) {
            rmsg.addMessage("新增失败！增加锁卡记录时只能单张添加");
            return rmsg;
        }
        //判断重复订单
//校验新增订单逻辑卡号是否存在在原来的订单表中，并且状态不是全部完成
        String isExist = mapper.checkInfo(vo.getStart_logic_no());
        if ((isExist !=null) &&((isExist.trim()).equals("0") ||(isExist.trim()).equals("1"))) {
            rmsg.addMessage("新增失败！逻辑卡号重复！");
//            int isInStock = mapper.isExitsInStock(vo.getStart_logic_no());
//            if (isInStock == 0) {
//                rmsg.addMessage("新增失败！逻辑卡号重复！");
//            }
            return rmsg;
        }

        LogUtil logUtil = new LogUtil();
        String preMsg = "";
        String logicNote = "";
        try {
            if (vo.getIs_bad().equals("2")) {
                vo.setProduce_type(SamCardConstant.PRODUCE_TYPE_PRODUCT);
            } else {
                vo.setProduce_type(SamCardConstant.PRODUCE_TYPE_EMPTY);
            }

            vo.setIs_instock(SamCardConstant.STOCK_STATE_IN);
            String startLogicNo = vo.getStart_logic_no();
            if (startLogicNo != null && !startLogicNo.equals("")) {
                vo.setStLogicNoEle(getStLogicNoEle(startLogicNo));
                vo.setStLogicNoSeven(getStLogicNoSeven(startLogicNo));
                int orderNum = Integer.parseInt(vo.getOrder_num());
                String stLogicSeven = getStLogicNoSeven(startLogicNo);
                String returnString = "";
                int stInt = Integer.parseInt(stLogicSeven);
                String stEle = startLogicNo.substring(0, 9);
                String logicNoNote = "";
                for (int i = 0; i < orderNum; i++) {
                    if (stInt + i > 9999999) {
                        returnString += "逻辑卡号后7位已越过9999999!";
                        break;
                    }
                    if (vo.getIs_bad().equals("2")) {
                        //判断锁卡情况
                        ProduceOrder poTemp = new ProduceOrder();
                        poTemp.setSam_type(vo.getSam_type());
                        poTemp.setStock_state_first(SamCardConstant.STOCK_STATE_RECYCLE_IN);  ////00:卡回收入库
//                        poTemp.setStock_state_second(" ");  ////
                        poTemp.setProduce_type(SamCardConstant.PRODUCE_TYPE_PRODUCT);//成品卡
                        poTemp.setIs_instock(SamCardConstant.STOCK_STATE_IN);
                        poTemp.setIs_bad(vo.getIs_bad());
                        DecimalFormat df = new DecimalFormat("0000000");
                        String startLogicNoString = stEle + df.format((stInt + i));
                        poTemp.setStart_logic_no(startLogicNoString);
                        String checkAddInfoExistResult = mapper.checkResetInfoExist(poTemp);
                        if (checkAddInfoExistResult == null || checkAddInfoExistResult.equals("")) { //即不可用或已使用的逻辑卡号，拦截并提示
                            logicNoNote += (startLogicNoString + "、");
                             returnString += "库存不足";
                        }
                    } else {
                        ProduceOrder poTemp = new ProduceOrder();
                        poTemp.setSam_type(vo.getSam_type());
                        poTemp.setStock_state_first(SamCardConstant.STOCK_STATE_EMPTY_IN);  ////00:空白卡入库
                        poTemp.setStock_state_second(SamCardConstant.STOCK_STATE_PRODUCT_IN);  ////03:卡制作卡入库
                        poTemp.setProduce_type(SamCardConstant.PRODUCE_TYPE_EMPTY);
                        poTemp.setIs_instock(SamCardConstant.STOCK_STATE_IN);
                        DecimalFormat df = new DecimalFormat("0000000");
                        String startLogicNoString = stEle + df.format((stInt + i));
                        poTemp.setStart_logic_no(startLogicNoString);
                        String checkAddInfoExistResult = mapper.checkAddInfoExist(poTemp);
                        if (checkAddInfoExistResult == null || checkAddInfoExistResult.equals("")) { //即不可用或已使用的逻辑卡号，拦截并提示
                            logicNoNote += (startLogicNoString + "、");
                            returnString += "库存不足";
                        }
                    }
                }
                if (!returnString.equals("")) {
//                if(!returnString.equals("") || !logicNoNote.equals("")){
//                    if(!logicNoNote.equals("")){ 
//                        logicNoNote = logicNoNote.substring(0, logicNoNote.length()-1) + "逻辑卡号已使用或不可用！";
//                    }
//                    rmsg.addMessage("添加失败！"+ logicNoNote + returnString);
                    rmsg.addMessage("添加失败！" + logicNoNote + returnString);
                    return rmsg;
                }
                if (!logicNoNote.equals("") && !vo.getIs_bad().equals("2")) {
                    logicNote = "该订单存在中间断号的情况！";  //有提示，但保存成功
                    rmsg.addMessage("添加失败！" + logicNoNote + returnString);
                    return rmsg;
//                    logicNoNote = logicNoNote.substring(0, logicNoNote.length()-1) + "逻辑卡号已使用或不可用！";
                }
            }
//            ProduceOrder voTemp = new ProduceOrder();
//            voTemp.setStart_logic_no(startLogicNo);
//            List<ProduceOrder> poList = mapper.queryPlanWithStartLN(voTemp);
//            if (poList != null && poList.size() >0) {  //起始逻辑卡号重复，请在审核前（临时单）即进行重复判断
//                returnViewId.addAll(poList);
//                rmsg.addMessage("添加失败！该起始逻辑卡号已存在！");
//                return rmsg;
//            }
            int tempNum = mapper.getCountStock(vo);//卡库存 (sam卡类型，00:空白卡)
            if (tempNum >= Integer.valueOf(vo.getOrder_num())) {
                vo.setOrder_state(SamCardConstant.RECORD_FLAG_UNAUDITED);
                vo.setFinish_flag(SamCardConstant.RECORD_FLAG_ALL_UNFINISHED);
                if (vo.getIs_bad().equals("2")) {
                    vo.setProduce_type(SamCardConstant.PRODUCE_TYPE_PRODUCT);
                } else {
                    vo.setProduce_type(SamCardConstant.PRODUCE_TYPE_EMPTY);
                }
//                vo.setProduce_type(SamCardConstant.PRODUCE_TYPE_EMPTY);
                vo.setIs_instock(SamCardConstant.STOCK_STATE_IN);
                //插入条件：对应 Sam卡类型, 产品类型为空白卡，在库的 库存数据大于订单生产数量
                int n = this.addPlanByTrans(request, mapper, billMapper, vo, rmsg, returnViewId);
                logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);
                rmsg.addMessage(LogConstant.addSuccessMsg(n) + logicNote);
            } else {
                List<PubFlag> samTypes = pubFlagMapper.getSamType();
                rmsg.addMessage("添加失败！Sam卡类型：" + DBUtil.getTextByCode(vo.getSam_type(), samTypes) + "，库存不足！");
            }
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        return rmsg;
    }

    public OperationResult deletePlan(HttpServletRequest request, ProduceOrderMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        List<ProduceOrder> pos = this.getReqAttributeForPlanDelete(request);
        LogUtil logUtil = new LogUtil();
        try {
            int n = this.deletePlanByTrans(request, mapper, pos);
            logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);
            rmsg.addMessage(LogConstant.delSuccessMsg(n));
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        return rmsg;
    }

    public OperationResult auditPlan(HttpServletRequest request, ProduceOrderMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        List<ProduceOrder> pos = this.getReqAttributeForPlanAudit(request);
        LogUtil logUtil = new LogUtil();
        try {
            int n = this.auditPlanByTrans(request, mapper, pos, returnViewId);
            logUtil.logOperation(CommandConstant.COMMAND_AUDIT, request, LogConstant.auditSuccessMsg(n), opLogMapper);
            rmsg.addMessage(LogConstant.auditSuccessMsg(n));
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_AUDIT, e, opLogMapper);
        }
        return rmsg;
    }

    public OperationResult modifyPlan(HttpServletRequest request, ProduceOrderMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        ProduceOrder vo = this.getReqAttributePlan(request);
        //拦截起始逻辑卡号“0000000”这种情况
        if (this.isMin(vo)) {
            rmsg.addMessage("修改失败！起始逻辑卡号段最小值为0000001！");
            returnViewId.add(vo);
            return rmsg;
        }
        String ids = vo.getOrder_no().substring(0, 4);
        if (ids.equals(SamCardConstant.ORDER_TYPE_CODE_RESET)) {
            returnViewId.add(vo);
            rmsg.addMessage("修改失败！锁卡订单不能进行修改，请删除后重新生成订单");
            
            return rmsg;
        }
        LogUtil logUtil = new LogUtil();
        String preMsg = "";
        try {
            if (vo.getIs_bad().equals("2")) {
                vo.setProduce_type(SamCardConstant.PRODUCE_TYPE_PRODUCT);
            } else {
                vo.setProduce_type(SamCardConstant.PRODUCE_TYPE_EMPTY);
            }
            vo.setIs_instock(SamCardConstant.STOCK_STATE_IN);

            String startLogicNo = vo.getStart_logic_no();
            if (startLogicNo != null && !startLogicNo.equals("")) {
                vo.setStLogicNoEle(this.getStLogicNoEle(startLogicNo));
                vo.setStLogicNoSeven(this.getStLogicNoSeven(startLogicNo));
            }
            int tempNum = mapper.getCountStock(vo);//卡库存 (sam卡类型，00:空白卡)
            if (tempNum >= Integer.valueOf(vo.getOrder_num())) {
                vo.setOrder_state(SamCardConstant.RECORD_FLAG_UNAUDITED);
                if (vo.getIs_bad().equals("2")) {
                    vo.setProduce_type(SamCardConstant.PRODUCE_TYPE_PRODUCT);
                } else {
                    vo.setProduce_type(SamCardConstant.PRODUCE_TYPE_EMPTY);
                }
//                vo.setProduce_type(SamCardConstant.PRODUCE_TYPE_EMPTY);
                vo.setIs_instock(SamCardConstant.STOCK_STATE_IN);
//                vo.setFinish_flag(SamCardConstant.RECORD_FLAG_ALL_UNFINISHED);
                //更新条件：未审核订单对应 Sam卡类型, 产品类型为空白卡，在库的 库存数据大于订单生产数量
                int n = this.modifyPlanByTrans(request, mapper, vo, rmsg, returnViewId);
                if (n == 0) {
                    //已经审核不能修改
                    throw new Exception("单号" + vo.getOrder_no() + "当前状态下不能修改或库存不足！");
                }
                logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
                rmsg.addMessage(LogConstant.modifySuccessMsg(n));
            } else {
                List<PubFlag> samTypes = pubFlagMapper.getSamType();
                rmsg.addMessage("修改失败！Sam卡类型：" + DBUtil.getTextByCode(vo.getSam_type(), samTypes) + "，库存不足！");
            }
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        return rmsg;
    }
}
