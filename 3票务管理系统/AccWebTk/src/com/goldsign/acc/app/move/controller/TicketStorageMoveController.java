/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.app.move.controller;


import com.goldsign.acc.app.basicinfo.entity.TicketStorageCodAreaCardInOutLogic;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageCodAreaCardInOutLogicMapper;
import com.goldsign.acc.app.move.entity.TicketStorageIcOutBillDetail;
import com.goldsign.acc.app.move.entity.TicketStorageInStoreBillDetail;
import com.goldsign.acc.app.move.entity.TicketStorageTicketMove;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.InOutConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.goldsign.acc.frame.util.FltTabUtil;
import com.goldsign.acc.app.move.mapper.TicketStorageIcOutBillDetailMapper;
import com.goldsign.acc.app.move.mapper.TicketStorageInStoreBillDetailMapper;
import com.goldsign.acc.app.move.mapper.TicketStorageTicketMoveMapper;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import java.math.BigDecimal;

/**
 * @author  刘粤湘
 * @date    2017-9-4 9:16:59
 * @version V1.0
 * @desc  
 */
@Controller
public class TicketStorageMoveController extends StorageOutInBaseController{
    
    @Autowired
    TicketStorageTicketMoveMapper ticketMoveMapper;
    
     @Autowired
    TicketStorageIcOutBillDetailMapper icOutBillDetailMapper;
     
      @Autowired
    TicketStorageInStoreBillDetailMapper inStoreBillDetailMapper;
      
      @Autowired
      TicketStorageCodAreaCardInOutLogicMapper icCodAreaCardInOutLogicMapper;
    
   
    private FltTabUtil fltTabUtil;
    
    /**
      * 库存迁移
      * @param request
      * @param response
      * @return 
      */
    @RequestMapping(value = "/ticketStorageMove")
    public ModelAndView storageMove(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/move/ticketStorageMove.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        fltTabUtil = new FltTabUtil();
        fltTabUtil.initFilterNeed(this.pubFlagMapper);
        int i = 0;
        try {
            if (command != null) {
                command = command.trim();
                TicketStorageTicketMove ticketMove = new TicketStorageTicketMove();
                List<TicketStorageTicketMove> ticketMoves = new ArrayList();
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    
                    ticketMove = this.getQryTktMoveObj(request,ticketMove);
                     ticketMoves = ticketMoveMapper.qryTktMove(ticketMove);
                    ticketMoves = this.fltTktMovs(ticketMoves);
                    opResult.setReturnResultSet(ticketMoves);
                    opResult.addMessage("成功查询" + ticketMoves.size() + "条记录");
                }
                if (command.equals(CommandConstant.COMMAND_ADD)) {
                    
                    ticketMove = this.getInTktMoveObj(request,ticketMove,CommandConstant.COMMAND_ADD);
                    ticketMoves = new ArrayList();
                    ticketMoves.add(ticketMove);
                    ticketMoves = this.fltTktMovs(ticketMoves);
                    
//                    if(ticketMove.getOutStorageId().equals(ticketMove.getInStorageId())){
//                        opResult.addMessage("迁出仓库与迁入仓库不能相同");
//                    }
//                    else if(!ticketMove.getOutAreaId().equals(ticketMove.getInAreaId())){
//                        opResult.addMessage("迁出票区与迁入票区必须相同");
//                    }
//                    else if(!this.chkOutFun(ticketMove)&&ticketMove.getStartBoxId()!=null){
//                        ticketMove = ticketMoves.get(0);
//                        opResult.addMessage("" + ticketMove.getOutStorageIdText() +
//                                             ":" + ticketMove.getOutAreaId()+
//                                             ":" + ticketMove.getIcMainTypeText() + 
//                                             ":" + ticketMove.getIcSubType() +
//                                              "不能按盒号迁移");
//                    }
//                    //20190331 add by moqf
//                    else if(this.chkOutFun(ticketMove)&& (ticketMove.getStartBoxId()==null ||"".equals(ticketMove.getStartBoxId())
//                            ||ticketMove.getEndBoxId()==null ||"".equals(ticketMove.getEndBoxId()))){
//                        ticketMove = ticketMoves.get(0);
//                        opResult.addMessage("" + ticketMove.getOutStorageIdText() +
//                                             ":" + ticketMove.getOutAreaId()+
//                                             ":" + ticketMove.getIcMainTypeText() + 
//                                             ":" + ticketMove.getIcSubType() +
//                                              "只能按盒迁移，始盒号、止盒号不能为空；");
//                    }
//                    else if(ticketMove.getQuantity()==null && ticketMove.getStartBoxId()==null && ticketMove.getEndBoxId()==null){
//                        opResult.addMessage("数量及盒号不可同时为空");
//                    }
//                    else{
//                        ticketMove.setIcSubType(ticketMove.getOldIcSubType());
                    //20190331 add by moqf
                    if (checkMoveInfo(ticketMove, opResult)) {
//                        ticketMove.setIcSubType(ticketMove.getIcSubType());
                        
//                        System.out.println(ticketMove.getIcSubType());
//                        ticketMove.setOutAreaId(ticketMove.getOldOutAreaId());
                        
//                        ticketMove.setOutAreaId(ticketMove.getOutAreaId());
                        
//                        System.out.println(ticketMove.getOutAreaId());
//                        ticketMove.setInAreaId(ticketMove.getOldInAreaId());
                        
//                        ticketMove.setInAreaId(ticketMove.getInAreaId());
                        
//                        System.out.println(ticketMove.getInAreaId());
                        i = ticketMoveMapper.insertSelective(ticketMove);
                       // ticketMoves.add(ticketMove);
                        ticketMoves = this.fltTktMovs(ticketMoves);
                        opResult.setReturnResultSet(ticketMoves);
                        opResult.addMessage("成功增加" + i + "条记录");
                    }
                    
                }
                if (command.equals(CommandConstant.COMMAND_DELETE)) {
                    String strKeyIDs = request.getParameter("allSelectedIDs");                    
                    String[] szKeyIds = strKeyIDs.split(";");
                    
                     TransactionStatus status = null;

                    try {
                        status = txMgr.getTransaction(this.def);
                        for (String keyId : szKeyIds) {
                            i += ticketMoveMapper.delete(keyId);
                        }

                        opResult.addMessage("成功删除" + i + "条记录");
                        txMgr.commit(status);

                    } catch (Exception e) {
                        e.printStackTrace();

                        if (txMgr != null) {
                            txMgr.rollback(status);
                        }
                        throw e;
                    }
               
                }
                 if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                    
                    ticketMove = this.getInTktMoveObj(request,ticketMove,CommandConstant.COMMAND_MODIFY);
                    //20190331 add by moqf
                    if (checkMoveInfo(ticketMove, opResult)) {
                        i = ticketMoveMapper.modify(ticketMove);
    //                    System.out.println(ticketMove.getBillNo());
                        opResult.addMessage("成功修改" + i + "条记录");
                    }
                    ticketMoves.add(ticketMove);
                    ticketMoves = this.fltTktMovs(ticketMoves);
                    opResult.setReturnResultSet(ticketMoves);
                }
                 if (command.equals(CommandConstant.COMMAND_AUDIT)) {
                     opResult = this.auditTkMove(request);
                     ticketMove = (TicketStorageTicketMove)opResult.getReturnResultSet().get(0);
                     ticketMoves = ticketMoveMapper.qryTktMove(ticketMove);
                     ticketMoves = this.fltTktMovs(ticketMoves);
                     opResult.setReturnResultSet(ticketMoves);
                 } 
                 
                 if(command.equals("outBillDetail")){
                     mv = new ModelAndView("/jsp/move/ticketStorageMoveOutBillDetail.jsp");
                     String billNo = request.getParameter("queryCondition");
                     TicketStorageIcOutBillDetail record = new TicketStorageIcOutBillDetail();
                     record.setBillNo(billNo);
                     List<Map> records = icOutBillDetailMapper.qryOutBillDetail(record);
                     records = this.fltOutBillDetail(records);
                     opResult.setReturnResultSet(records);
                     opResult.addMessage("成功查询" + records.size() + "条记录");
                 }
                 if(command.equals("inBillDetail")){
                     mv = new ModelAndView("/jsp/move/ticketStorageMoveInBillDetail.jsp");
                     String billNo = request.getParameter("queryCondition");
                     TicketStorageInStoreBillDetail record = new TicketStorageInStoreBillDetail();
                     record.setBillNo(billNo);
                     List<TicketStorageInStoreBillDetail> records = inStoreBillDetailMapper.qryInBillDetail(record);
                     records = this.fltInBillDetail(records);
                     opResult.setReturnResultSet(records);
                     opResult.addMessage("成功查询" + records.size() + "条记录");
                     
                 }
                 
            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

       
        String[] attrNames = this.getAttributeNames();
        this.setPageOptions(attrNames, mv, request, response);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);
        this.SaveOperationResult(mv, opResult);

        return mv;
    }
    
   
    
     private TicketStorageTicketMove getQryTktMoveObj(HttpServletRequest request, TicketStorageTicketMove ticketMove) {
        if(ticketMove == null){
            return ticketMove;
        }
        String q_billNo = request.getParameter("q_billNo");
        if(q_billNo !=null && q_billNo.trim().equals("")){
            q_billNo = null;
        }
        ticketMove.setBillNo(q_billNo);
        
        String q_operator = request.getParameter("q_operator");
        if(q_operator !=null && q_operator.trim().equals("")){
            q_operator = null;
        }
        ticketMove.setFormMaker(q_operator);
        
         String q_beginTime = request.getParameter("q_beginTime");
        if(q_beginTime !=null && q_beginTime.trim().equals("")){
            q_beginTime = null;
        }
        ticketMove.setStartBillDate(q_beginTime);
        
         String q_endTime = request.getParameter("q_endTime");
        if(q_endTime !=null && q_endTime.trim().equals("")){
            q_endTime = null;
        }
        ticketMove.setEndBillDate(q_endTime);
        
         String q_cardMainCode = request.getParameter("q_cardMainCode");
        if(q_cardMainCode !=null && q_cardMainCode.trim().equals("")){
            q_cardMainCode = null;
        }
        ticketMove.setIcMainType(q_cardMainCode);
        
          String q_cardSubCode = request.getParameter("q_cardSubCode");
        if(q_cardSubCode !=null && q_cardSubCode.trim().equals("")){
            q_cardSubCode = null;
        }
        ticketMove.setIcSubType(q_cardSubCode);
        
         String q_out_storage = request.getParameter("q_out_storage");
        if(q_out_storage !=null && q_out_storage.trim().equals("")){
            q_out_storage = null;
        }
        ticketMove.setOutStorageId(q_out_storage);
        
         String q_in_storage = request.getParameter("q_in_storage");
        if(q_in_storage !=null && q_in_storage.trim().equals("")){
            q_in_storage = null;
        }
        ticketMove.setInStorageId(q_in_storage);
        
          String q_recordFlag = request.getParameter("q_recordFlag");
        if(q_recordFlag !=null && q_recordFlag.trim().equals("")){
            q_recordFlag = null;
        }
        ticketMove.setRecordFlag(q_recordFlag);
        
        return ticketMove;
    }

    private List<TicketStorageTicketMove> fltTktMovs(List<TicketStorageTicketMove> ticketMoves) {
        if(ticketMoves == null){
            return null;
        }
        for(TicketStorageTicketMove ticketMove:ticketMoves){
            ticketMove.setRecordFlagText(fltTabUtil.mapBillStatus.get(ticketMove.getRecordFlag()));
            String icSubType = ticketMove.getIcMainType() + ticketMove.getIcSubType();
            ticketMove.setIcMainTypeText(fltTabUtil.mapMainTypes.get(ticketMove.getIcMainType()));
//            ticketMove.setOldIcSubType(ticketMove.getIcSubType());
//            ticketMove.setIcSubType(fltTabUtil.mapMainSubs.get(icSubType));
            //20180501 moqf
//            ticketMove.setIcSubType(ticketMove.getIcSubType());
            ticketMove.setIcSubTypeText(fltTabUtil.mapMainSubs.get(icSubType));
            
            ticketMove.setOutStorageIdText(fltTabUtil.mapStorages.get(ticketMove.getOutStorageId()));
//            ticketMove.setOldOutAreaId(ticketMove.getOutAreaId());
//            ticketMove.setOutAreaId(fltTabUtil.mapAreas.get(ticketMove.getOutAreaId()));
            
//            ticketMove.setOutAreaId(ticketMove.getOutAreaId());
            ticketMove.setOutAreaIdText(fltTabUtil.mapAreas.get(ticketMove.getOutAreaId()));
            
            ticketMove.setInStorageIdText(fltTabUtil.mapStorages.get(ticketMove.getInStorageId()));
//            ticketMove.setOldInAreaId(ticketMove.getInAreaId());
//            ticketMove.setInAreaId(fltTabUtil.mapAreas.get(ticketMove.getInAreaId()));
            
//            ticketMove.setInAreaId(ticketMove.getInAreaId());
            ticketMove.setInAreaIdText(fltTabUtil.mapAreas.get(ticketMove.getInAreaId()));
            
            //20190331 moqf 页面屏蔽有效期输入
//            String validDate  = "";
//            if(ticketMove.getValidDate()!=null){
//                validDate = new SimpleDateFormat("yyyy-MM-dd").format(ticketMove.getValidDate());
//                if(validDate.equals(InOutConstant.DEFAULT_VALUE_VALID_DATE)){
//                    validDate  = "";
//                }
//            }
//            if(ticketMove.getStrValidDate()==null){
//                ticketMove.setStrValidDate(validDate);
//            }
            if(ticketMove.getStrBillDate()==null){
                ticketMove.setStrBillDate(ticketMove.getBillDate()==null ? null : new SimpleDateFormat(fltTabUtil.DATE_FORMAT).format(ticketMove.getBillDate()));
            }
            if(ticketMove.getStrVerifyDate()==null){
                ticketMove.setStrVerifyDate(ticketMove.getVerifyDate()==null ? null : new SimpleDateFormat(fltTabUtil.TIME_FORMAT).format(ticketMove.getVerifyDate()));
            }
            
        }
        return ticketMoves;
    }

    private TicketStorageTicketMove getInTktMoveObj(HttpServletRequest request, TicketStorageTicketMove ticketMove,String command) {
        if(ticketMove == null){
            return ticketMove;
        }
        if(command.equals(CommandConstant.COMMAND_ADD)){
            ticketMove.setBillNo(this.getBillNoTemp(billMapper, InOutConstant.TYPE_BILL_NO_TICKET_MOVE_TEMP));
            ticketMove.setOutBillNo(this.getBillNoTemp(billMapper, InOutConstant.TYPE_BILL_NO_TICKET_MOVE_OUT));
            ticketMove.setInBillNo(this.getBillNoTemp(billMapper, InOutConstant.TYPE_BILL_NO_TICKET_MOVE_IN));
        }
         if(command.equals(CommandConstant.COMMAND_MODIFY) ){
            ticketMove.setBillNo(request.getParameter("d_billNo"));
        }
        
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        ticketMove.setFormMaker(operatorId);
        ticketMove.setStrBillDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        ticketMove.setRecordFlag(InOutConstant.RECORD_FLAG_BILL_UNAUDIT);
        
        String d_icMainType = request.getParameter("d_icMainType");
        if(d_icMainType!=null && d_icMainType.trim().equals("")){
            d_icMainType = null;            
        }
        ticketMove.setIcMainType(d_icMainType);
        
         String d_icSubType = request.getParameter("d_icSubType");
        if(d_icSubType!=null && d_icSubType.trim().equals("")){
            d_icSubType = null;            
        }
        ticketMove.setIcSubType(d_icSubType);
        
         String d_outStorageId = request.getParameter("d_outStorageId");
        if(d_outStorageId!=null && d_outStorageId.trim().equals("")){
            d_outStorageId = null;            
        }
        ticketMove.setOutStorageId(d_outStorageId);
        
         String d_outAreaId = request.getParameter("d_outAreaId");
        if(d_outAreaId!=null && d_outAreaId.trim().equals("")){
            d_outAreaId = null;            
        }
        ticketMove.setOutAreaId(d_outAreaId);
        
        String d_inStorageId = request.getParameter("d_inStorageId");
        if(d_inStorageId!=null && d_inStorageId.trim().equals("")){
            d_inStorageId = null;            
        }
        ticketMove.setInStorageId(d_inStorageId);
        
         String d_inAreaId = request.getParameter("d_inAreaId");
        if(d_inAreaId!=null && d_inAreaId.trim().equals("")){
            d_inAreaId = null;            
        }
        ticketMove.setInAreaId(d_inAreaId);
        
//        String d_cardMoney = request.getParameter("d_cardMoney");
//        if(d_cardMoney!=null && d_cardMoney.trim().equals("")){
//            d_cardMoney = null;            
//        }
//        ticketMove.setCardMoney(d_cardMoney == null ? 0 : Short.parseShort(d_cardMoney));
        ticketMove.setCardMoney(BigDecimal.valueOf(FormUtil.getParameterIntVal(request, "d_cardMoney")));
//        detail.setCardMoney(BigDecimal.valueOf(Long.parseLong(cardMoneys[i])));
        
        //20190331 moqf 页面屏蔽有效期输入
//         String d_strValidDate = request.getParameter("d_strValidDate");
//        if(d_strValidDate!=null && d_strValidDate.trim().equals("")){
//            d_strValidDate = null;            
//        }
//        ticketMove.setStrValidDate(d_strValidDate);
        
//        s
//        ticketMove.setQuantity(d_quantity == null ? null : Short.parseShort(d_quantity));
        ticketMove.setQuantity(Integer.valueOf(FormUtil.getParameterIntVal(request, "d_quantity")));
        
        String d_startBoxId = request.getParameter("d_startBoxId");
        if(d_startBoxId!=null && d_startBoxId.trim().equals("")){
            d_startBoxId = null;            
        }
        ticketMove.setStartBoxId(d_startBoxId);
        
         String d_endBoxId = request.getParameter("d_endBoxId");
        if(d_endBoxId!=null && d_endBoxId.trim().equals("")){
            d_endBoxId = null;            
        }
        ticketMove.setEndBoxId(d_endBoxId);
        
         String d_distributeMan = request.getParameter("d_distributeMan");
        if(d_distributeMan!=null && d_distributeMan.trim().equals("")){
            d_distributeMan = null;            
        }
        ticketMove.setDistributeMan(d_distributeMan);
        
         String d_receiveMan = request.getParameter("d_receiveMan");
        if(d_receiveMan!=null && d_receiveMan.trim().equals("")){
            d_receiveMan = null;            
        }
        ticketMove.setReceiveMan(d_receiveMan);
        
          String d_remark = request.getParameter("d_remark");
        if(d_remark!=null && d_remark.trim().equals("")){
            d_remark = null;            
        }
        ticketMove.setRemark(d_remark);
        
        return ticketMove;
    }
    
     private OperationResult auditTkMove(HttpServletRequest request) {
        String strKeyIDs = request.getParameter("allSelectedIDs");
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        OperationResult or = new OperationResult();
        TicketStorageTicketMove ticketMove = new TicketStorageTicketMove();
        List rList = new ArrayList();
        
        int n = 0;
        try {

              Map map = new HashMap();
              map.put("pi_billNoTmp", strKeyIDs.split(";")[0]);
              map.put("pi_operator_id", operatorId);
              ticketMoveMapper.auditStorageMove(map);
 
            if(map.get("po_billNoNormal") != null){
                ticketMove.setBillNo((String)map.get("po_billNoNormal"));
            }
            
            if(map.get("po_retCode")!=null){
                if((Integer)map.get("po_retCode")==0){
                    n ++;
                }
                
            }            
            
//            or.addMessage("正式单号" + map.get("poBillNoNormal"));
//            or.addMessage("返回码" + map.get("poRetCode"));
            or.addMessage("" + map.get("po_retMsg"));
            if (n >0) {
                or.addMessage("成功审核" + n + "条记录。");
            }
            if(n == 0){
                ticketMove.setBillNo((String)map.get("pi_billNoTmp"));
            }
            rList.add(ticketMove);
            or.setReturnResultSet(rList);
        } catch (Exception e) {
            e.printStackTrace();

          
            throw e;
        }
        
        return or;
    }
     
      private List<Map> fltOutBillDetail(List<Map> records) {
        if(records == null){
            return null;
        }
        
        for(Map map : records){
            String icSubType = (String)map.get("IC_MAIN_TYPE") + map.get("IC_SUB_TYPE");
            map.put("IC_MAIN_TYPE", fltTabUtil.mapMainTypes.get(map.get("IC_MAIN_TYPE")));
            map.put("IC_SUB_TYPE", fltTabUtil.mapMainSubs.get(icSubType));
            map.put("REASON_ID", "迁移出库");
            map.put("STORAGE_ID", fltTabUtil.mapStorages.get(map.get("STORAGE_ID")));
            map.put("AREA_ID", fltTabUtil.mapAreas.get(map.get("AREA_ID")));
            if(map.get("VALID_DATE")!=null){
                map.put("VALID_DATE",new SimpleDateFormat(fltTabUtil.DATE_FORMAT).format(map.get("VALID_DATE")));
            }
        }
        
        return records;
    }
      
      private List<TicketStorageInStoreBillDetail> fltInBillDetail(List<TicketStorageInStoreBillDetail> records) {
          if(records == null){
            return null;
        }
        
        for(TicketStorageInStoreBillDetail record : records){
            String icSubType = record.getIcMainType()+record.getIcSubType();
            record.setIcMainType(fltTabUtil.mapMainTypes.get(record.getIcMainType()));
//            record.setIcSubType(icSubType);
            record.setIcSubType(fltTabUtil.mapMainSubs.get(icSubType));
            record.setReasonId("迁移入库");
            record.setStorageId(fltTabUtil.mapStorages.get(record.getStorageId()));
            record.setAreaId(fltTabUtil.mapAreas.get(record.getAreaId()));

            record.setStrValidDate(new SimpleDateFormat(fltTabUtil.DATE_FORMAT).format(record.getValidDate()));
        }
        
        return records;
    }
      
      private String[] getAttributeNames() {
        //仓库
        String[] attrNames = {IC_CARD_MAIN, IC_CARD_MAIN_SERIAL, IC_CARD_SUB, IC_CARD_MAIN_SUB,STORAGES,AREAS,STORAGE_AREAS,BILL_STATUES,AFC_CARD_MAIN, AFC_CARD_SUB, CARD_MAIN_SUB,IC_LINES, IC_STATIONS, IC_LINE_STATIONS,CARD_MONEYS};
        return attrNames;
    }

    private boolean chkOutFun(TicketStorageTicketMove ticketMove) {
        TicketStorageCodAreaCardInOutLogic queryCondition = new TicketStorageCodAreaCardInOutLogic();
        queryCondition.setStorage_id(ticketMove.getOutStorageId());
        queryCondition.setArea_id(ticketMove.getOutAreaId());
        queryCondition.setIc_main_type(ticketMove.getIcMainType());
        queryCondition.setIc_sub_type(ticketMove.getIcSubType());
        List<TicketStorageCodAreaCardInOutLogic> list = icCodAreaCardInOutLogicMapper.queryParm(queryCondition);
        if(list.size()>0){
            return true;
        }
        return false;
    }
        
    
    @RequestMapping("/ticketStorageMoveExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        List results = this.getBufferElements(request);
        List results = this.getBufferElementsForCurClass(request,"com.goldsign.acc.app.move.entity.TicketStorageTicketMove");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields,results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
    
    //20190331 add by moqf
    private boolean checkMoveInfo(TicketStorageTicketMove ticketMove, OperationResult opResult) {
        boolean result = true;
        boolean isFillMsgCorrect = true;
        boolean isCardInOutLogic = false;
        if(ticketMove.getOutStorageId().equals(ticketMove.getInStorageId())){
            opResult.addMessage("迁出仓库与迁入仓库不能相同；");
            isFillMsgCorrect = false;
        }
        if(!ticketMove.getOutAreaId().equals(ticketMove.getInAreaId())){
            opResult.addMessage("迁出票区与迁入票区必须相同；");
            isFillMsgCorrect = false;
        }
//        if((ticketMove.getQuantity()==null || ticketMove.getQuantity().intValue() == 0) 
        if((ticketMove.getQuantity()==null || ticketMove.getQuantity() == 0) 
                && (ticketMove.getStartBoxId()==null || ticketMove.getEndBoxId()==null)){
            opResult.addMessage("数量及盒号不可同时为空；");
            isFillMsgCorrect = false;
        }
        //20200817 add by moqf
        if ((ticketMove.getCardMoney().compareTo(BigDecimal.ZERO)>0) 
                && !InOutConstant.AREA_VALUE.equals(ticketMove.getOutAreaId())) {
            opResult.addMessage("票库迁移非赋值区票卡,只能选择面值0");
            isFillMsgCorrect = false;
        }
        //仓库、票区不正确，不检查是否按盒或按数量迁移
        if (!isFillMsgCorrect) {
            return false; 
        }
        isCardInOutLogic =this.chkOutFun(ticketMove);
        if(!isCardInOutLogic && ticketMove.getStartBoxId()!=null){
//            ticketMove = ticketMoves.get(0);
            opResult.addMessage("" + ticketMove.getOutStorageIdText() +
                                 ":" + ticketMove.getOutAreaIdText()+
                                 ":" + ticketMove.getIcMainTypeText() + 
                                 ":" + ticketMove.getIcSubTypeText() +
                                  "不能按盒号迁移；");
            result = false;
        }
        //20190331 add by moqf
        if(isCardInOutLogic && (ticketMove.getStartBoxId()==null ||"".equals(ticketMove.getStartBoxId())
                ||ticketMove.getEndBoxId()==null ||"".equals(ticketMove.getEndBoxId()))){
//            ticketMove = ticketMoves.get(0);
            opResult.addMessage("" + ticketMove.getOutStorageIdText() +
                                 ":" + ticketMove.getOutAreaIdText()+
                                 ":" + ticketMove.getIcMainTypeText() + 
                                 ":" + ticketMove.getIcSubTypeText() +
                                  "只能按盒迁移，始盒号、止盒号不能为空；");
            result = false;
        }
        
        return result;
    }
}
