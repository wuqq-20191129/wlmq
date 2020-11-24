/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.produce.controller;

import com.goldsign.acc.app.produce.entity.TicketStorageProduceUselessDetail;
import com.goldsign.acc.app.produce.mapper.TicketStorageProduceBillLostDetailMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.util.PubUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.HashMap;
import java.util.List;
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
public class TicketStorageProduceBillLostDetailController extends StorageOutInBaseController {
    
    @Autowired
    private TicketStorageProduceBillLostDetailMapper tsLostDetailMapper;
    

    @RequestMapping(value = "/ticketStorageProduceBillLostDetail")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/produce/ticketStorageProduceBillLostDetail.jsp");
        String command = request.getParameter("command");
//        String billNo = request.getParameter("d_bill_no"); //生产工作单号
        String billNo = request.getParameter("queryCondition"); //生产工作单号
        if (billNo == null || "".equals(billNo)) {
            billNo = (String) request.getAttribute("bill_no");
        }
        
        OperationResult opResult = new OperationResult();
        try {
            if (command == null) {
                command = CommandConstant.COMMAND_QUERY;
            } else {
                command = command.trim();
            }
            if (command.equals(CommandConstant.COMMAND_QUERY)) {
                opResult = this.query(request, this.tsLostDetailMapper, this.operationLogMapper);
            }
            if (command.equals(CommandConstant.COMMAND_ADD)) {
                opResult = this.add(request, this.tsLostDetailMapper, this.operationLogMapper);
            }
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                opResult = this.delete(request, this.tsLostDetailMapper, this.operationLogMapper);
            }

            if (this.isUpdateOp(command, null))//更新操作，增、删、改、审核,查询更新结果或原查询结果
            {
                this.queryUpdateResult(command, request, tsLostDetailMapper, operationLogMapper, opResult, mv);
            }
                

        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = this.getAttributeNames();
        this.setPageOptions(attrNames, mv, request, response); //
//        this.setOperatorId(mv, request);
        this.setBillNo(billNo,mv); //生产工作单号
        this.getResultSetText(opResult.getReturnResultSet(), mv);
        this.baseHandlerForOutIn(request, response, mv); //出入库基本回传参数，用到billRecordFlag
//        this.baseHandlerForBillNo(request, response, mv); //回传参数 单号
        this.baseHandler(request, response, mv);
//        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }
    
    private String[] getAttributeNames() {
        
        
        String[] attrNames = {IC_CARD_MAIN,IC_CARD_SUB,IC_LINES,IC_STATIONS,MODES,USELESS_CARD_TYPES};
        return attrNames;

    }
    
    private void getResultSetText(List<TicketStorageProduceUselessDetail> resultSet, ModelAndView mv) {
        List<PubFlag> icCardMainTypes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        List<PubFlag> icCardSubTypes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);
        List<PubFlag> icLines = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_LINES);
        List<PubFlag> icStations = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_STATIONS);
        List<PubFlag> modes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.MODES);
        List<PubFlag> uselessCardTypes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.USELESS_CARD_TYPES);
        
        for (TicketStorageProduceUselessDetail uselessDetail : resultSet) {
            if (icCardMainTypes != null && !icCardMainTypes.isEmpty()) {
                uselessDetail.setIc_main_type_name(DBUtil.getTextByCode(uselessDetail.getIc_main_type(), icCardMainTypes));
            }
            if (icCardSubTypes != null && !icCardSubTypes.isEmpty()) {
                uselessDetail.setIc_sub_type_name(DBUtil.getTextByCode(uselessDetail.getIc_sub_type(), uselessDetail.getIc_main_type(), icCardSubTypes));
            }
            if (icLines != null && !icLines.isEmpty()) {
                uselessDetail.setLine_id_name(DBUtil.getTextByCode(uselessDetail.getLine_id(), icLines));
                uselessDetail.setExit_line_id_name(DBUtil.getTextByCode(uselessDetail.getExit_line_id(), icLines));
            }
            if (icStations != null && !icStations.isEmpty()) {
                uselessDetail.setStation_id_name(DBUtil.getTextByCode(uselessDetail.getStation_id(), uselessDetail.getLine_id(), icStations));
                uselessDetail.setExit_station_id_name(DBUtil.getTextByCode(uselessDetail.getExit_station_id(), uselessDetail.getExit_line_id(), icStations));
            }
            if (modes != null && !modes.isEmpty()) {
                uselessDetail.setModel_name(DBUtil.getTextByCode(uselessDetail.getModel(), modes));
            }
            if (uselessCardTypes != null && !uselessCardTypes.isEmpty()) {
                uselessDetail.setCard_type_name(DBUtil.getTextByCode(uselessDetail.getCard_type(), uselessCardTypes));
            }
        }

    }
    
    private void setBillNo(String billNo, ModelAndView mv) {
        mv.addObject("bill_no", billNo);
    }
    
    private void queryUpdateResult(String command, HttpServletRequest request, TicketStorageProduceBillLostDetailMapper tsLostDetailMapper,
            OperationLogMapper opLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        }
        this.queryForOp(request, tsLostDetailMapper, opLogMapper,opResult);

    }
    
    public OperationResult query(HttpServletRequest request, TicketStorageProduceBillLostDetailMapper tsLostDetailMapper, 
            OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageProduceUselessDetail queryCondition;
        List<TicketStorageProduceUselessDetail> resultSet;

        try {
            queryCondition = this.getQueryConditionProduceUselessDetail(request);
            resultSet = tsLostDetailMapper.getProduceUselessDetailList(queryCondition);
            
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }
   
    public OperationResult queryForOp(HttpServletRequest request, TicketStorageProduceBillLostDetailMapper tsLostDetailMapper, 
            OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageProduceUselessDetail queryCondition;
        List<TicketStorageProduceUselessDetail> resultSet;

        try {
            queryCondition = this.getQueryConditionProduceUselessDetail(request);
            resultSet = tsLostDetailMapper.getProduceUselessDetailList(queryCondition);
            
            opResult.setReturnResultSet(resultSet);
//            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }
    
    public OperationResult add(HttpServletRequest request, TicketStorageProduceBillLostDetailMapper tsLostDetailMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageProduceUselessDetail vo = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        HashMap<String, Object> hmParm;
        String retMsg = "";

        try {
                this.checkAddData(vo);
                
//                单据已审核
                hmParm = this.addLostDetailByTrans(request, tsLostDetailMapper, vo);
                retMsg = this.handleResultForAdd(hmParm);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
//        +LogConstant.addSuccessMsg(n)
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, retMsg, opLogMapper);

//        rmsg.addMessage(LogConstant.addSuccessMsg(n));
        rmsg.addMessage(retMsg);

        return rmsg;
    }
    
    public void checkAddData(TicketStorageProduceUselessDetail vo) throws Exception {
        if ((vo.getOrder_no() == null || vo.getOrder_no().equals("")) 
                && (vo.getCard_no() == null || vo.getCard_no().equals(""))) {
            throw new Exception("票卡号盒号不能同时为空");
        }
        if (vo.getBill_no() == null || vo.getBill_no().equals("")) {
            throw new Exception("工作单不存在");
        }
        if (vo.getCard_no() == null && vo.getOrder_no() == null) {
            throw new Exception("订单号和卡号不能同时为空");
        }
    }
    
    private HashMap<String, Object> addLostDetailByTrans(HttpServletRequest request, 
            TicketStorageProduceBillLostDetailMapper tsLostDetailMapper, TicketStorageProduceUselessDetail vo) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        HashMap<String, Object> parmMap = new HashMap();
        try {
            status = txMgr.getTransaction(this.def);
            this.setMapParamsForAdd(parmMap, vo);
            tsLostDetailMapper.addLostDetail(parmMap);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        } finally {
            return parmMap;
        }
    }
    
    private void setMapParamsForAdd(HashMap<String, Object> parmMap, TicketStorageProduceUselessDetail vo) {
        parmMap.put("pi_billno", vo.getBill_no());
        parmMap.put("pi_orderno",vo.getOrder_no()); 
        parmMap.put("pi_cardid", vo.getCard_no());
        parmMap.put("pi_cardtype", vo.getCard_type());
    }
    
    
    private String handleResultForAdd(HashMap<String, Object> resultReturn) throws Exception {
        if (resultReturn == null || resultReturn.isEmpty()) {
            throw new Exception("执行存储过程没有返回结果！");
        }
//        String retMsg = "";
        String retMsg = (String) resultReturn.get("po_retMsg");    
        int retCode = (Integer) resultReturn.get("po_retCode");
        if (retCode == 0) {
            retMsg = "增加废票明细成功!";
            return retMsg;
        }
        if (retCode == -1) {
            //返回 po_retMsg
        } else if (retCode == -5) {
            retMsg = "ES不存在该卡号或订单号!";
        } else if (retCode == -2) {
            retMsg = "更新废票明细失败!";
        } else if (retCode == -3) {
            retMsg = "删除制票结果失败!";
        } else if (retCode == -4) {
            retMsg = "更新废票明细失败!";
        } else if (retCode == -10) {  //-27改为-10
            retMsg = "票卡已登记!";
        } else if (retCode == -11) {
            retMsg = "订单号和卡号不能同时为空!";
        } else if (retCode == -12) {
            retMsg = "非法订单号!";
        } else if (retCode == -13) {
            retMsg = "工作单明细无法找到该订单号!";
        } else if (retCode == -14) {
            retMsg = "工作单号或废票类型不能为空!";
        } else if (retCode == -28) {
            retMsg = "非法票卡号!";
        } else if (retCode == -29) {
            retMsg = "卡号不能为空!";
        } else if (retCode == -30) {
            retMsg = "票卡数量已超过该废票定义总数!";
        } else if (retCode == -31) {
            retMsg = "订单号不能为空!";
        } else if (retCode == -40) {
            retMsg = "工作类型代码不能为空!";
        } else {
            retMsg = "增加失败!";
        } 
        
        throw new Exception(retMsg);
    }
    
    public OperationResult delete(HttpServletRequest request, TicketStorageProduceBillLostDetailMapper tsLostDetailMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageProduceUselessDetail> pos = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            //明细表有没有记录
            if (this.isBillStateAudit(tsLostDetailMapper, pos)) {
                rmsg.addMessage("单据已审核！");
                return rmsg;
            }
            n = this.deleteInBillByTrans(request, tsLostDetailMapper, pos);
            if (n<=0) {
                rmsg.addMessage("删除失败,删除记录出错！");
                return rmsg;
            }
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }
    
    private Vector<TicketStorageProduceUselessDetail> getReqAttributeForDelete(HttpServletRequest request) {
        TicketStorageProduceUselessDetail po = new TicketStorageProduceUselessDetail();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageProduceUselessDetail> selectedItems = this.getUselessDetailSelectIDs(selectIds, request);
        return selectedItems;
    }
    
    private Vector<TicketStorageProduceUselessDetail> getUselessDetailSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageProduceUselessDetail> pudls = new Vector();
        TicketStorageProduceUselessDetail pudl;
//        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            pudl = this.getUselessDetail(strIds, "#");
//            pudl.setOperator(operatorId);
            pudls.add(pudl);
        }
        return pudls;
    }
    
    protected TicketStorageProduceUselessDetail getUselessDetail(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageProduceUselessDetail pudl = new TicketStorageProduceUselessDetail();
        ;
        Vector<TicketStorageProduceUselessDetail> pudls = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                pudl.setCard_type(tmp);
                continue;
            }
            if (i == 2) {
                pudl.setBill_no(tmp);
                continue;
            }
            if (i == 3) {
                pudl.setCard_no(tmp);
                continue;
            }
        }
        return pudl;
    }
    
    private boolean isBillStateAudit(TicketStorageProduceBillLostDetailMapper tsLostDetailMapper,
            Vector<TicketStorageProduceUselessDetail> pos) {
        if (pos != null && pos.size()>0) {
            TicketStorageProduceUselessDetail uselessDetail = (TicketStorageProduceUselessDetail)pos.get(0);
            int uselessDetailCount = tsLostDetailMapper.getProduceBillAuditCount(uselessDetail.getBill_no());
            if (uselessDetailCount > 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    
    private int deleteInBillByTrans(HttpServletRequest request, TicketStorageProduceBillLostDetailMapper tsLostDetailMapper,
            Vector<TicketStorageProduceUselessDetail> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        int result = 0;
        try {
            status = txMgr.getTransaction(this.def);
            
            for (TicketStorageProduceUselessDetail po : pos) {
                //检查废票明细
                this.checkUselessDetail(po);
                // '1'：ES废票，'2'：人工废票，'3'：遗失票
                if (po.getCard_type().equals("1") || po.getCard_type().equals("2")) {
                    List<TicketStorageProduceUselessDetail> uselessDetailList = tsLostDetailMapper.getUselessDetailByCardNo(po);
                    if (uselessDetailList.size()>0) {
                        TicketStorageProduceUselessDetail uselessDetail = uselessDetailList.get(0);
                        String flag = uselessDetail.getFlag();
                        String orderNo = uselessDetail.getOrder_no();
                        //flag ：'1'：单张票卡插入遗失明细，删除遗失明细时将单张票卡；'2'：整盒插入遗失明细，删除遗失明细时将整盒删除
                        if (flag.equals("2")) {
                            System.out.println("删除整盒");
                            result = tsLostDetailMapper.deleteUselessDetailForBox(uselessDetail);
                            if (result <=0) {
                                break;
                            }
                        } else {
                            //删除单张票卡
                            System.out.println("删除单张票卡");
                            result = tsLostDetailMapper.deleteUselessDetailForCardNo(po);
                            if (result <=0) {
                                break;
                            }
                        }
                    }
                } else {
                    //'3'：遗失票
                    System.out.println("删除票卡类型:"+po.getCard_type());
                    result = tsLostDetailMapper.deleteUselessDetailForCardType(po);
                    if (result <=0) {
                        break;
                    }
                }
//                统计删除记录数
                n = n + result;
            } //for
            //改为全部记录删除成功才提交
            if (result > 0) {
                txMgr.commit(status);
            } else {
                txMgr.rollback(status);
            }
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }
    
    private void checkUselessDetail(TicketStorageProduceUselessDetail po) throws Exception {
        if (po.getCard_type().equals("") && po.getCard_no().equals("")) {
            throw new Exception("票卡类型、票卡号都为空无法删除");
        }
    }
    
    
    private TicketStorageProduceUselessDetail getQueryConditionProduceUselessDetail(HttpServletRequest request) {
        TicketStorageProduceUselessDetail qCon = new TicketStorageProduceUselessDetail();
//        String billNo = request.getParameter("d_bill_no"); //生产工作单号
//temppppppppppp
String billNo2 = request.getParameter("d_bill_no"); //生产工作单号
        String billNo = request.getParameter("queryCondition"); //生产工作单号
        if (billNo == null || "".equals(billNo)) {
            billNo = (String) request.getAttribute("bill_no");
        }
//        qCon.setBill_no(request.getParameter("d_bill_no"));  
        qCon.setBill_no(billNo); 
        return qCon;
    }
    
    private TicketStorageProduceUselessDetail getReqAttribute(HttpServletRequest request) {
        TicketStorageProduceUselessDetail vo = new TicketStorageProduceUselessDetail();

        vo.setBill_no(FormUtil.getParameter(request, "d_bill_no"));
        vo.setOrder_no(FormUtil.getParameter(request, "d_order_no"));
        vo.setCard_no(FormUtil.getParameter(request, "d_card_no"));
        vo.setCard_type(FormUtil.getParameter(request, "d_card_type"));
        

        return vo;
    }
      
}
