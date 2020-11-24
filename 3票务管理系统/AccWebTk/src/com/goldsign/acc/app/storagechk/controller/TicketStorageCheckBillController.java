/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.app.storagechk.controller;

import com.goldsign.acc.app.storagechk.entity.TicketStorageIcChkStorage;
import com.goldsign.acc.app.storagechk.entity.TicketStorageIcChkStorageDetail;
import com.goldsign.acc.app.storagechk.entity.TicketStorageIcChkStorageDetailCard;
import com.goldsign.acc.app.storagechk.entity.TicketStorageIcChkStorageDetailKey;
import com.goldsign.acc.app.storagechk.entity.TicketStorageIcCodBoxDetailKey;
import com.goldsign.acc.app.storagechk.entity.TicketStorageIcCodCardTypeContrast;
import com.goldsign.acc.app.storagechk.entity.TicketStorageIcCodCardTypeContrastKey;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.InOutConstant;
import com.goldsign.acc.frame.constant.ParameterConstant;
import com.goldsign.acc.frame.constant.PubFlagConstant;
import com.goldsign.acc.frame.constant.WebConstant;
import com.goldsign.acc.frame.mapper.BillMapper;
import com.goldsign.acc.frame.mapper.PubFlagMapper;
import com.goldsign.acc.frame.util.FltTabUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;

import com.goldsign.acc.frame.util.PubUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.transaction.TransactionStatus;
import com.goldsign.acc.app.storagechk.mapper.TicketStorageIcChkStorageDetailCardMapper;
import com.goldsign.acc.app.storagechk.mapper.TicketStorageIcChkStorageDetailMapper;
import com.goldsign.acc.app.storagechk.mapper.TicketStorageIcChkStorageMapper;
import com.goldsign.acc.app.storagechk.mapper.TicketStorageIcCodBoxDetailMapper;
import com.goldsign.acc.app.storagechk.mapper.TicketStorageIcCodCardTypeContrastMapper;
import com.goldsign.acc.frame.util.ExpUtil;
import java.util.Iterator;

/**
 * @author  刘粤湘
 * @date    2017-7-25 9:18:19
 * @version V1.0
 * @desc  
 */
@Controller
public class TicketStorageCheckBillController extends StorageOutInBaseController {
    
    @Autowired
    TicketStorageIcChkStorageMapper icChkStorageMapper;
    
    @Autowired
    TicketStorageIcChkStorageDetailMapper icChkStorageDetailMapper;
    
    @Autowired
    protected PubFlagMapper pubFlagMapper;
    
    @Autowired
    TicketStorageIcCodBoxDetailMapper icCodBoxDetailMapper;
    
    @Autowired
    TicketStorageIcChkStorageDetailCardMapper icChkStorageDetailCardMapper;
    
    @Autowired
    TicketStorageIcCodCardTypeContrastMapper icCodCardTypeContrastMapper;
    
    @Autowired
    BillMapper billMapper;
    
    private String SIGN = ";";
    
    private String SIGN_COLON = ":";
    
    private FltTabUtil fltTabUtil;
    //盘点后查询条件
    private String checkBillNo = null;

     @RequestMapping(value = "/ticketStorageCheckBill")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/check/ticketStorageCheckBill.jsp");
        String command = request.getParameter("command");
        
        String operType = request.getParameter("operType");
        OperationResult opResult = new OperationResult();
        List<TicketStorageIcChkStorage> rs = new ArrayList();
        try{
              if (command != null) {
                command = command.trim();
                
                fltTabUtil = new FltTabUtil();
                fltTabUtil.initFilterNeed(this.pubFlagMapper);
                fltTabUtil.initFilterNeed(this.pubFlagMapper, request);              
             
              //盘点单查询
              if (command.equals(CommandConstant.COMMAND_QUERY)) {
                  opResult = this.queryIcChkStorages(request);
                  this.filterIcChkStorageTab(opResult.getReturnResultSet());
              }
              //盘点明细查询
              if(command.equals(CommandConstant.COMMAND_QUERY_DETAIL)){
                  mv = new ModelAndView("/jsp/check/ticketStorageCheckBillDetail.jsp");
                  opResult = this.queryIcChkDetail(request,mv);
                  this.filterIcChkStorageDetailTab(opResult.getReturnResultSet());
                  mv.addObject("QueryCondition", request.getParameter("queryCondition"));
                  mv.addObject("billRecordFlag",request.getParameter("billRecordFlag"));
              }
              //盘点单增加
              if (command.equals(CommandConstant.COMMAND_ADD)) {
                  opResult = this.addIcChkStorages(request);
                  this.filterIcChkStorageTab(opResult.getReturnResultSet());
              }              
               //盘点单删除
              if (command.equals(CommandConstant.COMMAND_DELETE)) {
                  opResult = this.delIcChkStorages(request);
                  
              }
               //盘点单审核
              if (command.equals(CommandConstant.COMMAND_AUDIT)) {
                  opResult = this.auditIcChkStorages(request);
                  this.filterIcChkStorageTab(opResult.getReturnResultSet());
              }
              //盘点和盘点后明细查询
              if(command.equals(CommandConstant.COMMAND_CHECK1)){
                  mv = new ModelAndView("/jsp/check/ticketStorageCheckBillDetail.jsp");
                  this.check(request);
                  opResult = this.queryIcChkDetail(request,mv);
                  this.filterIcChkStorageDetailTab(opResult.getReturnResultSet());
                  System.out.println("=======" + request.getParameter("billRecordFlag"));
                  mv.addObject("billRecordFlag",request.getParameter("billRecordFlag"));
              }
              //盘点逻辑卡号，赋值区或者编码区非单程票进行这个操作
               if(command.equals(CommandConstant.COMMAND_CARD)){
                  mv = new ModelAndView("/jsp/check/ticketStorageCheckBillCard.jsp");
                  
                  opResult = this.queryIcChkDetailCard(request,mv);
                 mv.addObject("billRecordFlag",request.getParameter("billRecordFlag"));
              }
                }
            
        }catch(Exception e){
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }
         checkBillNo = null;
         String[] attrNames = this.getAttributeNames();
         this.setPageOptions(attrNames, mv, request, response);
         this.baseHandler(request, response, mv);
         this.divideResultSet(request, mv, opResult);
         this.SaveOperationResult(mv, opResult); 
         return mv;
    }
    
     @RequestMapping(value = "/ticketStorageCheckBillCard")
    public ModelAndView checkBillCard(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/check/ticketStorageCheckBillCard.jsp");
        String command = request.getParameter("command");
        
        String operType = request.getParameter("operType");
        OperationResult opResult = new OperationResult();
        List<TicketStorageIcChkStorage> rs = new ArrayList();
        
        try{
             if (command != null) {
                command = command.trim();

                fltTabUtil = new FltTabUtil();
                fltTabUtil.initFilterNeed(this.pubFlagMapper);
                if (command.equals(CommandConstant.COMMAND_ADD)) {
                    mv = new ModelAndView("/jsp/check/ticketStorageCheckBillCard.jsp");
                    //按盒号增加和按逻辑卡号增加
                    this.chkBillCard(request);
                    //增加后查询
                    opResult = this.queryIcChkDetailCard(request, mv);
                }

                if (command.equals(CommandConstant.COMMAND_DELETE)) {
                    mv = new ModelAndView("/jsp/check/ticketStorageCheckBillCard.jsp");
                    //删除选择逻辑卡号段
                    TicketStorageIcChkStorageDetailCard card = new TicketStorageIcChkStorageDetailCard();
                    card = this.delBillCard(request, card);
                    //删除后查询
                    opResult = this.queryIcChkDetailCardDelAfter(request, mv, card);
                }
            }
            
        }catch(Exception e){
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

    private String[] getAttributeNames() {
        //仓库
        String[] attrNames = {IC_CARD_MAIN, IC_CARD_MAIN_SERIAL, IC_CARD_SUB, IC_CARD_MAIN_SUB,STORAGES,AREAS,STORAGE_AREAS,BILL_STATUES,AFC_CARD_MAIN, AFC_CARD_SUB, CARD_MAIN_SUB,IC_LINES, IC_STATIONS, IC_LINE_STATIONS,CARD_MONEYS};
        return attrNames;
    }
    
   
    
    /**
     * 过滤票卡盘点表格
     * @param icChkStorages 
     */
    private void filterIcChkStorageTab(List<TicketStorageIcChkStorage> icChkStorages){
        if(icChkStorages == null){
            return ;
        }
       
        Iterator<TicketStorageIcChkStorage> it = icChkStorages.iterator();
        while (it.hasNext()) {
            TicketStorageIcChkStorage icChkStorage = it.next();
            if (fltTabUtil.mapStoragesByOpr.get("9999") == null) {
                if (fltTabUtil.mapStoragesByOpr.get(icChkStorage.getIcChkStorageDetails().get(0).getStorageId()) == null) {
                    it.remove();
                }
            }
        }
        
        for(TicketStorageIcChkStorage icChkStorage:icChkStorages){
          
            String icSubType = icChkStorage.getIcChkStorageDetails().get(0).getIcMainType() + icChkStorage.getIcChkStorageDetails().get(0).getIcSubType();
            icChkStorage.setIcMainType(icChkStorage.getIcChkStorageDetails().get(0).getIcMainType());
            icChkStorage.setIcMainTypeText(fltTabUtil.mapMainTypes.get(icChkStorage.getIcChkStorageDetails().get(0).getIcMainType()));
            icChkStorage.setIcSubType(icChkStorage.getIcChkStorageDetails().get(0).getIcSubType());
            icChkStorage.setIcSubTypeText(fltTabUtil.mapMainSubs.get(icSubType));
            icChkStorage.setStorageId(icChkStorage.getIcChkStorageDetails().get(0).getStorageId());
            icChkStorage.setStorageIdText(fltTabUtil.mapStorages.get(icChkStorage.getIcChkStorageDetails().get(0).getStorageId()));
            icChkStorage.setAreaId(icChkStorage.getIcChkStorageDetails().get(0).getAreaId());
            icChkStorage.setAreaIdText(fltTabUtil.mapAreas.get(icChkStorage.getIcChkStorageDetails().get(0).getAreaId()));
            icChkStorage.setRecordFlagText(fltTabUtil.mapBillStatus.get(icChkStorage.getRecordFlag()));
            if(icChkStorage.getCheckDate()!=null){
                icChkStorage.setStartChkDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(icChkStorage.getCheckDate()));
            }
            if(icChkStorage.getVerifyDate()!=null){
                icChkStorage.setEndChkDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(icChkStorage.getVerifyDate()));
            }            
            
        }        
       
      
    }

    /**
     * 查询票卡盘点表
     * @param request
     * @return
     * @throws Exception 
     */
    private OperationResult queryIcChkStorages(HttpServletRequest request) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageIcChkStorage queryCondition = new TicketStorageIcChkStorage();
        List<TicketStorageIcChkStorage> resultSet = null;
        try {
            this.getQueryConditionPlan(request,queryCondition);
            resultSet = icChkStorageMapper.getIcChkStorages(queryCondition);
            or.setReturnResultSet(resultSet);
//            System.out.println("成功查询" + resultSet.size() + "条记录");
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, this.operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", this.operationLogMapper);
        return or;
    }

    private void getQueryConditionPlan(HttpServletRequest request, TicketStorageIcChkStorage queryCondition) {
        if(request == null || queryCondition == null){
            return;
        }
        queryCondition.setCheckBillNo(FormUtil.getParameter(request,"q_billNo" ));
        if(queryCondition.getCheckBillNo()!=null && queryCondition.getCheckBillNo().trim().equals("")){
            queryCondition.setCheckBillNo(null);
        }
        queryCondition.setStorageId(FormUtil.getParameter(request,"q_storage" ));
        if(queryCondition.getStorageId()!=null && queryCondition.getStorageId().trim().equals("")){
            queryCondition.setStorageId(null);
        }
        queryCondition.setAreaId(FormUtil.getParameter(request,"q_area_id" ));
        if(queryCondition.getAreaId()!=null && queryCondition.getAreaId().trim().equals("")){
            queryCondition.setAreaId(null);
        }
        queryCondition.setRecordFlag(FormUtil.getParameter(request,"q_recordFlag" ));
        if(queryCondition.getRecordFlag()!=null && queryCondition.getRecordFlag().trim().equals("")){
            queryCondition.setRecordFlag(null);
        }
        queryCondition.setStartChkDate(FormUtil.getParameter(request,"q_beginTime" ));
         if(queryCondition.getStartChkDate()!=null && queryCondition.getStartChkDate().trim().equals("")){
            queryCondition.setStartChkDate(null);
        }
        queryCondition.setEndChkDate(FormUtil.getParameter(request,"q_endTime" ));
        if(queryCondition.getEndChkDate()!=null && queryCondition.getEndChkDate().trim().equals("")){
            queryCondition.setEndChkDate(null);
        }
    }

    /**
     * 查询票卡盘点明细
     * @param request
     * @return
     * @throws Exception 
     */
    private OperationResult queryIcChkDetail(HttpServletRequest request,ModelAndView mv) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageIcChkStorageDetailKey queryCondition = new TicketStorageIcChkStorageDetailKey();        
        queryCondition.setCheckBillNo(request.getParameter("queryCondition"));
        mv.addObject("ModuleID",request.getParameter("ModuleID"));
        //盘点后查询条件判断
        if(checkBillNo != null){
            queryCondition.setCheckBillNo(checkBillNo);
        }
        List<TicketStorageIcChkStorageDetail> resultSet = null;
        try {            
            resultSet = icChkStorageDetailMapper.selectByPrimaryKey(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, this.operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", this.operationLogMapper);
        return or;
        
    }

    /**
     * 过滤票卡盘点明细表格
     * @param returnResultSet 
     */
    private void filterIcChkStorageDetailTab(List<TicketStorageIcChkStorageDetail> returnResultSet) {
        if(returnResultSet == null){
            return;
        }
        
        //仓库        
        //票区        
        //详细位置 仓库+票区+柜+层+托        
        //票卡主类型        
        //票卡子类型        
        //差额  实际数量-库存数量
        //限制模式
        for(TicketStorageIcChkStorageDetail detail: returnResultSet){
            String chestId = detail.getStorageId() + "-" + detail.getAreaId() + "-" 
                             + (detail.getChestId() == null ? "000" :  detail.getChestId()) + "-"
                             + (detail.getStoreyId() == null ? "00" : detail.getStoreyId()) + "-" 
                             + (detail.getBaseId() == null ? "00" : detail.getBaseId()) ;
            detail.setChestId(chestId);
            detail.setStorageName(fltTabUtil.mapStorages.get(detail.getStorageId()));
            detail.setAreaName(fltTabUtil.mapAreas.get(detail.getAreaId()));
            
            detail.setIcMainName(fltTabUtil.mapMainTypes.get(detail.getIcMainType().trim()));
            detail.setIcSubName(fltTabUtil.mapMainSubs.get(detail.getIcMainType().trim() + detail.getIcSubType()));
            
            detail.setDiffAmount((detail.getRealAmount().intValue() - detail.getSysAmount().intValue()));
             if(detail.getProductDate()!=null){
                detail.setStrProDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(detail.getProductDate()));
            }
            
            detail.setModel(fltTabUtil.mapLimitModels.get(detail.getModel()));
        }
    }

    private void check(HttpServletRequest request) {
        String[] waterNos = request.getParameterValues("waterNo");
        String[] checkBillNos = request.getParameterValues("checkBillNo");     
        String[] storageIds = request.getParameterValues("storageId");
        String[] areaIds = request.getParameterValues("areaId");
        String[] chestIds = request.getParameterValues("chestId");
        String[] boxIds = request.getParameterValues("boxId");
        String[] icMainTypes = request.getParameterValues("icMainType");
        String[] icSubTypes = request.getParameterValues("icSubType");
        String[] sysAmounts = request.getParameterValues("sysAmount");
        String[] realAmounts = request.getParameterValues("realAmount");
        String[] cardMoneys = request.getParameterValues("cardMoney");
        
        TransactionStatus status = null;
        try{
            status = txMgr.getTransaction(this.def);
             //更新盘点单 盘点日期
             TicketStorageIcChkStorage record = new TicketStorageIcChkStorage ();
             record.setStartChkDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//             System.out.println(waterNos);
//             System.out.println(checkBillNos);
             record.setCheckBillNo(checkBillNos[0]);
             //盘点后查询条件设置
             checkBillNo = checkBillNos[0];
             icChkStorageMapper.updateByPrimaryKeySelective(record);
            //更新盘点单明细 实际金额 面值
            int i = 0 ;
            for(String checkBillNo :checkBillNos ){
                TicketStorageIcChkStorageDetail detail = new TicketStorageIcChkStorageDetail();
                detail.setWaterNo(BigDecimal.valueOf(Long.parseLong(waterNos[i])));
                detail.setCheckBillNo(checkBillNo);
                detail.setRealAmount(BigDecimal.valueOf(Long.parseLong(realAmounts[i])));
                detail.setCardMoney(BigDecimal.valueOf(Long.parseLong(cardMoneys[i])));
                icChkStorageDetailMapper.updateByPrimaryKeySelective(detail);
                i++;
            }
            
            txMgr.commit(status);
          
        }catch(Exception e){
            e.printStackTrace();
            
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        
        
    }

    /**
     * 查询盘点单卡明细
     * @param request 
     */
    private OperationResult queryIcChkDetailCard(HttpServletRequest request,ModelAndView mv) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageIcChkStorageDetailCard queryCondition = new TicketStorageIcChkStorageDetailCard();        
        queryCondition.setCheckBillNo(request.getParameter("checkBillNo"));
        queryCondition.setBoxId(request.getParameter("boxId"));
        
        
        
        //先查询明细单内容
        mv.addObject("waterNo",request.getParameter("waterNo"));
        mv.addObject("checkBillNo",request.getParameter("checkBillNo"));
        mv.addObject("storageId",request.getParameter("storageId"));
        mv.addObject("storageName",fltTabUtil.mapStorages.get(request.getParameter("storageId").trim()));
        mv.addObject("areaId",request.getParameter("areaId"));
        mv.addObject("areaName",fltTabUtil.mapAreas.get(request.getParameter("areaId")));
        mv.addObject("icMainType",request.getParameter("icMainType"));
        mv.addObject("icMainName",fltTabUtil.mapMainTypes.get(request.getParameter("icMainType").trim()));
        mv.addObject("icSubType",request.getParameter("icSubType"));
        mv.addObject("icSubName",fltTabUtil.mapMainSubs.get(request.getParameter("icMainType").trim() + request.getParameter("icSubType")));
        mv.addObject("boxId",request.getParameter("boxId"));
     
        //再查询列表内容
        List<TicketStorageIcChkStorageDetailCard> resultSet = null;
        try {            
            resultSet = icChkStorageDetailCardMapper.select(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
            for(TicketStorageIcChkStorageDetailCard card :resultSet){
                card.setStorageName(fltTabUtil.mapStorages.get(card.getStorageId()));
                card.setAreaName(fltTabUtil.mapAreas.get(card.getAreaId()));
                card.setIcMainTypeName(fltTabUtil.mapMainTypes.get(card.getIcMainType().trim()));
                card.setIcSubTypeName(fltTabUtil.mapMainSubs.get(card.getIcMainType().trim() + card.getIcSubType()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, this.operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", this.operationLogMapper);
        return or;
    }

    /**
     * 盘点逻辑卡号
     * @param request 
     */
    private int chkBillCard(HttpServletRequest request) throws Exception {
        String checkBillNo = (String) request.getParameter("d_check_bill");
//        String startCardNo = (String) request.getParameter("d_startcardNo");
        //20180322 mqf
        String startCardNo = (String) request.getParameter("d_startLogicalId");
//        String endCardNo = (String) request.getParameter("d_endcardNo");
        String endCardNo = (String) request.getParameter("d_endLogicalId");
        //String areaName = CharUtil.IsoToUTF8((String)request.getParameter("d_area_name"));
        String boxid = (String) request.getParameter("d_box_id");
        int n = 0;
        try {
            if (this.isForBox(startCardNo, endCardNo, boxid)) {
                n = this.addNewCardForBox(startCardNo, endCardNo, checkBillNo, boxid);
            } else {
                n = this.addNewCardForLogical(startCardNo, endCardNo, checkBillNo, boxid);
            }
        } catch (Exception e) {
            throw e;
        }
        return n;
    }
    private boolean isForBox(String startCardNo, String endCardNo, String boxid) {
        if (this.isValidBox(boxid) && !this.isValidCardNo(startCardNo) && !this.isValidCardNo(endCardNo)) {
            return true;
        }
        return false;
    }
    
    private boolean isValidBox(String boxid) {
        if (boxid == null && boxid.length() == 0) {
            return false;
        }
        return true;
    }

    private boolean isValidCardNo(String cardNo) {
        if (cardNo == null || cardNo.length() == 0) {
            return false;
        }
        return true;
    }
    
     private int addNewCardForBox(String startCardNo, String endCardNo, String checkbillno, String boxid) throws Exception {
         TicketStorageIcChkStorageDetailKey key = new TicketStorageIcChkStorageDetailKey();
         key.setCheckBillNo(checkbillno);
         key.setBoxId(boxid);
         List<TicketStorageIcChkStorageDetail> details = icChkStorageDetailMapper.selectByPrimaryKey(key);
        
        if (!this.isCheckForOut(details.get(0))) {
            throw new Exception("盘点调增不允许按盒调整");
        }
        TicketStorageIcCodBoxDetailKey codBoxDetailKey = new TicketStorageIcCodBoxDetailKey();
        codBoxDetailKey.setBoxId(boxid);
        List<TicketStorageIcCodBoxDetailKey> v = icCodBoxDetailMapper.selectByPrimaryKey(codBoxDetailKey);
        if (!this.isValidBoxLogical(v, details.get(0))) {
            throw new Exception("盒" + details.get(0).getBoxId() + "的所有逻辑卡号段对应的票卡数量" + details.get(0).getValidNumForBoxSection() + "与盘点差额" + details.get(0).getValidNumForDiff() + "不等");
        }
        int n = this.addLogicalsVForBox(details.get(0), v);
        return n;
    }
     
     private boolean isValidBoxLogical(List<TicketStorageIcCodBoxDetailKey> boxes, TicketStorageIcChkStorageDetail vo) throws Exception {
        int n = 0;
        TicketStorageIcCodBoxDetailKey cvo;
        for (int i = 0; i < boxes.size(); i++) {
            cvo = (TicketStorageIcCodBoxDetailKey) boxes.get(i);
            n += PubUtil.calculateSection(cvo.getStartLogicalId(), cvo.getEndLogicalId(), vo.getIcMainType(), vo.getIcSubType());

        }
        int diff = Math.abs((vo.getRealAmount().intValue() - vo.getSysAmount().intValue()));
        vo.setValidNumForBoxSection(n);
        vo.setValidNumForDiff(diff);
        if (diff == n) {
            return true;
        }
        return false;
    }
     
     private boolean isCheckForOut(TicketStorageIcChkStorageDetail vo) {
        if (vo.getRealAmount().intValue() < vo.getSysAmount().intValue()) {
            return true;
        }
        return false;
    }

    /**
     * 增加完逻辑卡号然后查询
     * @param request
     * @param mv
     * @return 
     */
    private OperationResult queryIcChkDetailCardDelAfter(HttpServletRequest request, ModelAndView mv,TicketStorageIcChkStorageDetailCard card) throws Exception {
         OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageIcChkStorageDetailCard queryCondition = new TicketStorageIcChkStorageDetailCard();        
        queryCondition.setCheckBillNo(card.getCheckBillNo());
        queryCondition.setBoxId(card.getBoxId());
        
        //先查询明细单内容
        mv.addObject("waterNo",card.getWaterNo());
        mv.addObject("checkBillNo",card.getCheckBillNo());
        mv.addObject("storageId",card.getStorageId());
        mv.addObject("storageName",fltTabUtil.mapStorages.get(card.getStorageId()));
        mv.addObject("areaId",card.getAreaId());
        mv.addObject("areaName",fltTabUtil.mapAreas.get(card.getAreaId()));
        mv.addObject("icMainType",card.getIcMainType());
        mv.addObject("icMainName",fltTabUtil.mapMainTypes.get(card.getIcMainType().trim()));
        mv.addObject("icSubType",card.getIcSubType());
        mv.addObject("icSubName",fltTabUtil.mapMainSubs.get(card.getIcSubType()));
        mv.addObject("boxId",card.getBoxId());
     
        //再查询列表内容
        List<TicketStorageIcChkStorageDetailCard> resultSet = null;
        try {            
            resultSet = icChkStorageDetailCardMapper.select(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
            for(TicketStorageIcChkStorageDetailCard cardTmp :resultSet){
                cardTmp.setStorageName(fltTabUtil.mapStorages.get(cardTmp.getStorageId()));
                cardTmp.setAreaName(fltTabUtil.mapAreas.get(cardTmp.getAreaId()));
                cardTmp.setIcMainTypeName(fltTabUtil.mapMainTypes.get(cardTmp.getIcMainType().trim()));
                cardTmp.setIcSubTypeName(fltTabUtil.mapMainSubs.get(cardTmp.getIcSubType()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, this.operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", this.operationLogMapper);
        return or;
    }

    private int addLogicalsVForBox(TicketStorageIcChkStorageDetail get, List<TicketStorageIcCodBoxDetailKey> v) throws Exception {
        TicketStorageIcChkStorageDetailCard detailCard = new TicketStorageIcChkStorageDetailCard();
        detailCard.setBoxId(v.get(0).getBoxId());
        detailCard.setCheckBillNo(get.getCheckBillNo());
        List<TicketStorageIcChkStorageDetailCard> cards = icChkStorageDetailCardMapper.select(detailCard);
        if(cards.size()>0){
            throw new Exception("盘点单" + get.getCheckBillNo() + "|盒" + v.get(0).getBoxId() + "已经存在" );
        }
        TransactionStatus status = null;
        int n = 0;
        try{
            status = txMgr.getTransaction(this.def);
               for (int i = 0; i < v.size(); i++) {
                   TicketStorageIcCodBoxDetailKey box = (TicketStorageIcCodBoxDetailKey) v.get(i);
                   detailCard = new TicketStorageIcChkStorageDetailCard();
                   detailCard.setBoxId(box.getBoxId());
                   detailCard.setStartLogicalId(box.getStartLogicalId());
                   detailCard.setEndLogicalId(box.getEndLogicalId());
                   detailCard.setCheckBillNo(get.getCheckBillNo());
                   detailCard.setStorageId(get.getStorageId());
                   detailCard.setAreaId(get.getAreaId());
                   detailCard.setChestId(get.getChestId());
                   detailCard.setStoreyId(get.getStoreyId());
                   detailCard.setBaseId(get.getBaseId());
                   detailCard.setIcMainType(get.getIcMainType());
                   detailCard.setIcSubType(get.getIcSubType());
                   n+=icChkStorageDetailCardMapper .insertSelective(detailCard);
                
            }
             txMgr.commit(status);
          
        }catch(Exception e){
            e.printStackTrace();
            
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private int addNewCardForLogical(String startCardNo, String endCardNo, String checkBillNo, String boxid) throws Exception {
         TicketStorageIcChkStorageDetailKey key = new TicketStorageIcChkStorageDetailKey();
         key.setCheckBillNo(checkBillNo);
         key.setBoxId(boxid);
         List<TicketStorageIcChkStorageDetail> details = icChkStorageDetailMapper.selectByPrimaryKey(key);
        
        if (!this.isValidCardNo(startCardNo) && this.isValidCardNo(endCardNo)) {
            startCardNo = endCardNo;
        }
        if (!this.isValidCardNo(endCardNo) && this.isValidCardNo(startCardNo)) {
            endCardNo = startCardNo;
        }
//        startCardNo = this.getStandardLogical(startCardNo);
//        endCardNo = this.getStandardLogical(endCardNo);
        this.check(startCardNo, endCardNo, checkBillNo, boxid, details.get(0));
        int n = this.addLogicals(startCardNo, endCardNo, details.get(0));
        return n;
    }
    
    private String getStandardLogical(String logicalNo) {
        String tmp = "00000000000000000000";
        tmp = tmp + logicalNo;
        int len = tmp.length();
        return tmp.substring(len - 20, len);
    }

    private void check(String startCardNo, String endCardNo, String checkBillNo, String boxid, TicketStorageIcChkStorageDetail get) throws Exception {
        try{ 
            //判断单据是否已审核
            TicketStorageIcChkStorage record = new TicketStorageIcChkStorage();
            record.setCheckBillNo(checkBillNo);
            //单据未审核
            record.setRecordFlag(ParameterConstant.RECORD_FLAG_DRAFT);
            List<TicketStorageIcChkStorage> records = icChkStorageMapper.selectByPrimaryKey(record);
            if (records.size() > 0) {
                throw new Exception("该单据已审核");
            }
            //盒输入的实际数量是否超过盒的最大值,并将盒单位赋值

            if (!this.isValidCardNumInput(get)) {
                throw new Exception("输入的实际数量" + get.getRealAmount().intValue() + "超过盒定义的数量" + get.getValidNumForboxUnit());
            }
            //判断盒的卡号段是否已存在该卡号段
            if (this.isCheckForOut(get)) {
                if (!this.isValidLogicalSectionForOut(get, startCardNo, endCardNo)) {
                    throw new Exception("输入的卡号段" + startCardNo + "-" + endCardNo + "没有包含在盒已存在的卡号段中");
                }
            } else {
                if (!this.isValidLogicalSectionForIn(get, startCardNo, endCardNo)) {
                    throw new Exception("输入的卡号段" + startCardNo + "-" + endCardNo + "包含在盒已存在的卡号段中");
                }
            }

            //判断票卡段是否重复登记	
            if (!this.isValidLogicalSectionForReapeat(get, startCardNo, endCardNo)) {
                throw new Exception("输入的卡号段" + startCardNo + "-" + endCardNo + "重复登记");
            }
            //判断所有票卡段包含的卡数量是否超过盒单位
            if (!this.isValidCardNumInputForTotal(get, startCardNo, endCardNo)) {
                throw new Exception("已输入的卡号段包含的卡数量" + get.getValidNumForInputSection() + "超过盘点差额" + get.getValidNumForDiff());
            }
        }catch(Exception e){
            
        }
        
    }

    private boolean isValidCardNumInput(TicketStorageIcChkStorageDetail get) throws Exception {
        TicketStorageIcCodCardTypeContrastKey cardTypeContrastKey = new TicketStorageIcCodCardTypeContrastKey();
        cardTypeContrastKey.setIcMainType(get.getIcMainType());
        cardTypeContrastKey.setIcSubType(get.getIcSubType());
        TicketStorageIcCodCardTypeContrast cardTypeContrast = icCodCardTypeContrastMapper.selectByPrimaryKey(cardTypeContrastKey);
        if(cardTypeContrast == null){
            throw new Exception("票卡对照表没有定义主类型" + get.getIcMainType()+ "子类型" + get.getIcSubType() + "的盒单位");
        }
        if(get.getRealAmount().intValue()<cardTypeContrast.getBoxUnit().intValue()){
            return true;
        }
        return false;
    }

    private boolean isValidLogicalSectionForOut(TicketStorageIcChkStorageDetail get, String startCardNo, String endCardNo) {
        TicketStorageIcCodBoxDetailKey codBoxDetailKey = new TicketStorageIcCodBoxDetailKey();
        codBoxDetailKey.setBoxId(get.getBoxId());
        codBoxDetailKey.setStartLogicalId(startCardNo);
        codBoxDetailKey.setEndLogicalId(endCardNo);
        List<TicketStorageIcCodBoxDetailKey> v = icCodBoxDetailMapper.selectByPrimaryKey(codBoxDetailKey);
        if(v.size()>0){
            return true;
        }
        return false;
    }

    private boolean isValidLogicalSectionForIn(TicketStorageIcChkStorageDetail get, String startCardNo, String endCardNo) {
        TicketStorageIcCodBoxDetailKey record = new TicketStorageIcCodBoxDetailKey();
        record.setStartLogicalId(startCardNo);
        record.setEndLogicalId(endCardNo);
        int i = icCodBoxDetailMapper.count(record);
        
        if(i == 0) {
            return true;
        }
        
        return false;
    }

    private boolean isValidLogicalSectionForReapeat(TicketStorageIcChkStorageDetail get, String startCardNo, String endCardNo) {
        TicketStorageIcChkStorageDetailCard detailCard = new TicketStorageIcChkStorageDetailCard();
        detailCard.setStartLogicalId(startCardNo);
        detailCard.setEndLogicalId(endCardNo);
        int i = icChkStorageDetailCardMapper.count(detailCard);
        
        if(i == 0){
            return true;
        }
        
        return false;
    }

    private boolean isValidCardNumInputForTotal(TicketStorageIcChkStorageDetail get, String startCardNo, String endCardNo) throws Exception {
        TicketStorageIcChkStorageDetailCard icChkStorageDetailCard = new TicketStorageIcChkStorageDetailCard();
        icChkStorageDetailCard.setCheckBillNo(get.getCheckBillNo());
        icChkStorageDetailCard.setBoxId(get.getBoxId());
        List<TicketStorageIcChkStorageDetailCard> icChkStorageDetailCards = icChkStorageDetailCardMapper.select(icChkStorageDetailCard);
          
        int n = PubUtil.calculateSection(startCardNo, endCardNo, get.getIcMainType(), get.getIcSubType());
        
        for (int i = 0; i < icChkStorageDetailCards.size(); i++) {
            TicketStorageIcChkStorageDetailCard cvo = (TicketStorageIcChkStorageDetailCard) icChkStorageDetailCards.get(i);
            n += PubUtil.calculateSection(cvo.getStartLogicalId(), cvo.getEndLogicalId(), get.getIcMainType(), get.getIcSubType());

        }
        int diff = Math.abs(get.getRealAmount().intValue() - get.getSysAmount().intValue());
        get.setValidNumForInputSection(n);
        get.setValidNumForDiff(diff);
        if (n <= diff) {
            return true;
        }
        return false;
    }

    private int addLogicals(String startCardNo, String endCardNo, TicketStorageIcChkStorageDetail get) {
        TicketStorageIcChkStorageDetailCard detailCard = new TicketStorageIcChkStorageDetailCard();
        detailCard.setBoxId(get.getBoxId());
        detailCard.setStartLogicalId(startCardNo);
        detailCard.setEndLogicalId(endCardNo);
        detailCard.setCheckBillNo(get.getCheckBillNo());
        detailCard.setStorageId(get.getStorageId());
        detailCard.setAreaId(get.getAreaId());
        detailCard.setChestId(get.getChestId());
        detailCard.setStoreyId(get.getStoreyId());
        detailCard.setBaseId(get.getBaseId());
        detailCard.setIcMainType(get.getIcMainType());
        detailCard.setIcSubType(get.getIcSubType());
        int n = icChkStorageDetailCardMapper.insertSelective(detailCard);
        return n;
    }

    private TicketStorageIcChkStorageDetailCard delBillCard(HttpServletRequest request,TicketStorageIcChkStorageDetailCard card) {   
        
        String allSelectedIDs = request.getParameter("allSelectedIDs");
        if (allSelectedIDs == null) {
            return null;
        }
        String ids[] = allSelectedIDs.split(SIGN);
        String[] wats = ids[0].split("#");
        card = icChkStorageDetailCardMapper.selectByPrimaryKey(BigDecimal.valueOf(Long.parseLong(wats[1])).shortValue());
        
        TransactionStatus status = null;
        try {
            
            status = txMgr.getTransaction(this.def);
            for(String id :ids){
                String getChkWats[] = id.split("#");
                TicketStorageIcChkStorageDetailCard record = new TicketStorageIcChkStorageDetailCard();
                record.setCheckBillNo(getChkWats[0]);
                record.setWaterNo(BigDecimal.valueOf(Long.parseLong(getChkWats[1])));
                
                icChkStorageDetailCardMapper.delete(record);
            }            
            
            txMgr.commit(status);

        } catch (Exception e) {
            e.printStackTrace();

            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        
        return card;
    }

    private OperationResult addIcChkStorages(HttpServletRequest request) {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        String checkBillNo = this.getBillNoTemp(billMapper, InOutConstant.TYPE_BILL_NO_CHECK);
        TransactionStatus status = null;
        int n = 0;
        try{
            status = txMgr.getTransaction(this.def);
            //增加单据
            TicketStorageIcChkStorage storage = new TicketStorageIcChkStorage();
            storage.setCheckBillNo(checkBillNo);
            storage.setCheckDate(new Date());
            storage.setCheckPerson(PageControlUtil.getOperatorFromSession(request));
            storage.setRecordFlag(ParameterConstant.RECORD_FLAG_DRAFT);
            n = icChkStorageMapper.insertSelective(storage);
            or.addMessage("成功增加" + n + "条记录");
            
            //增加单据明细
            TicketStorageIcChkStorageDetail storageDetail = new TicketStorageIcChkStorageDetail();
            storageDetail.setCheckBillNo(checkBillNo);
            storageDetail.setIcMainType(request.getParameter("d_icMainType"));
            storageDetail.setIcSubType(request.getParameter("d_icSubType"));
            storageDetail.setStorageId(request.getParameter("d_storageId"));
            storageDetail.setAreaId(request.getParameter("d_areaId"));
//            icChkStorageDetailMapper.insertSelective(storageDetail);
//            int waterNo= 0;
//            waterNo = icChkStorageDetailMapper.selectDetailSeq(null);
//            storageDetail.setWaterNo(BigDecimal.valueOf(Long.valueOf(waterNo+"")));
            if (PubUtil.isNeedDetailPlace(storageDetail.getAreaId(), storageDetail.getIcMainType())) {
                n = icChkStorageDetailMapper.addBillDetailForBox(storageDetail);
                // 当仅选择票区并且是编码区时，还需增加单程票盘点记录
                if (this.isOnlyEncodeAreaSelected(storageDetail.getAreaId(), storageDetail.getIcMainType())) {
                    storageDetail.setIcMainType(InOutConstant.IC_MAIN_TYPE_SJT);
                    n += icChkStorageDetailMapper.addBillDetailForAreaForSjt(storageDetail);
                }
            } // 盘点区
            else {
                n = icChkStorageDetailMapper.addBillDetailForAreaSub(storageDetail);
            }
            txMgr.commit(status);
            
            List<TicketStorageIcChkStorage> storages = new ArrayList();
            List<TicketStorageIcChkStorageDetailKey> keys = new ArrayList();
            TicketStorageIcChkStorageDetailKey key = new TicketStorageIcChkStorageDetailKey();
            key.setIcMainType(storageDetail.getIcMainType());
            key.setIcSubType(storageDetail.getIcSubType());
            key.setStorageId(storageDetail.getStorageId());
            key.setAreaId(storageDetail.getAreaId());
            keys.add(key);
            storage.setIcChkStorageDetails(keys);
            storages.add(storage);
            
            or.setReturnResultSet(storages);

        } catch (Exception e) {
            e.printStackTrace();

            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }      
        
        return or;
    }
    
    private boolean isOnlyEncodeAreaSelected(String areaId, String icMainType) {
        if ((icMainType == null || icMainType.length() == 0)
                && areaId.equals(InOutConstant.AREA_ENCODE)) {
            return true;
        }
        return false;
    }

    private OperationResult delIcChkStorages(HttpServletRequest request) {
        String strKeyIDs = request.getParameter("allSelectedIDs");
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        
         TransactionStatus status = null;
        int n = 0;
        try{
            status = txMgr.getTransaction(this.def);
            for(String key:strKeyIDs.split(SIGN)){
                n += icChkStorageMapper.deleteByPrimaryKey(key);
                icChkStorageDetailMapper.deleteByPrimaryKey(key);
                icChkStorageDetailCardMapper.deleteByPrimaryKey(key);
            }
            
          
            txMgr.commit(status);
            
            or.addMessage("成功删除"+ n+ "条记录") ;
         

        } catch (Exception e) {
            e.printStackTrace();

            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }      
        
        return or;
    }

    private OperationResult auditIcChkStorages(HttpServletRequest request) {
        String strKeyIDs = request.getParameter("allSelectedIDs");
        //审核人
        String operatorID = PageControlUtil.getOperatorFromSession(request);
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        HashMap<String, Object> map = new HashMap();
        
        
        List<TicketStorageIcChkStorage> resultSet = new ArrayList();
        
         TransactionStatus status = null;
        try{
            status = txMgr.getTransaction(this.def);
            String key = strKeyIDs.split(SIGN)[0];
            map.put("pi_billno", key);
            map.put("pi_operator_id", operatorID);

            icChkStorageMapper.audit(map);
            String retMsg = (String) map.get("po_retMsg");     
            int retCode = (Integer) map.get("po_retCode");
            String billNo = (String) map.get("po_bill_no"); 

            TicketStorageIcChkStorage queryCondition = new TicketStorageIcChkStorage();
            if (retCode ==0){    
                queryCondition.setCheckBillNo(billNo);
                List<TicketStorageIcChkStorage> resultSetTmp = icChkStorageMapper.getIcChkStorages(queryCondition);
                if (resultSetTmp != null) {
                    resultSet.add(resultSetTmp.get(0));
                }    
            }else{
                queryCondition.setCheckBillNo(key);
                List<TicketStorageIcChkStorage> resultSetTmp = icChkStorageMapper.getIcChkStorages(queryCondition);
                if (resultSetTmp != null) {
                    resultSet.add(resultSetTmp.get(0));
                }   
            }
                
            or.setReturnResultSet(resultSet);
          
            txMgr.commit(status);
            or.addMessage(retMsg) ;
         

        } catch (Exception e) {
            e.printStackTrace();

            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }      
        
        return or;
    }
    
    @RequestMapping("/ticketStorageCheckBillExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        List results = this.getBufferElements(request);
        List results = this.getBufferElementsForCurClass(request,"com.goldsign.acc.app.storagechk.entity.TicketStorageIcChkStorage");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields,results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
    
   
}
