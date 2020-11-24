/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.app.query.controller;

import com.goldsign.acc.app.query.entity.TicketStorageMbInitiInfo;
import com.goldsign.acc.app.query.entity.TicketStoragePduOrder;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.vo.OperationResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.PubFlagConstant;
import com.goldsign.acc.frame.entity.PubFlag;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.goldsign.acc.app.query.mapper.TicketStorageMbInitiInfoMapper;
import com.goldsign.acc.app.query.mapper.TicketStoragePduOrderMapper;

/**
 * 空发卡查询
 * 
 * @author  刘粤湘
 * @date    2017-8-18 16:43:19
 * @version V1.0
 * @desc  
 */
@Controller
public class TicketStorageQueryMbPduOrderController extends StorageOutInBaseController {
    
    @Autowired
    TicketStoragePduOrderMapper pduOrderMapper;
    
    @Autowired
    TicketStorageMbInitiInfoMapper mbInitInfoMapper;
    
    private Map mapEsWorkType;
    private Map mapBillStatu;
    
      @RequestMapping(value = "/ticketStorageQueryMbPduOrder")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/query/ticketStorageQueryMbPduOrder.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        TicketStoragePduOrder pduOrder = new TicketStoragePduOrder();
        
       pduOrder =  this.getQryCondition(request,pduOrder );
          if (command != null) {
              command = command.trim();
              if (command.equals(CommandConstant.COMMAND_QUERY)) {
                  List<TicketStoragePduOrder> pduOrders = pduOrderMapper.qryPduOrder(pduOrder);

                  pduOrders = this.fltPduOrders(pduOrders);
                  opResult.setReturnResultSet(pduOrders);
              }
            
          }
       
        
         this.baseHandler(request, response, mv);
         this.divideResultSet(request, mv, opResult);
         this.SaveOperationResult(mv, opResult); 
         return mv;
    }
    
     @RequestMapping(value = "/ticketStorageQueryMbPduInitInfo")
    public ModelAndView mbInitiInfo(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/query/ticketStorageQueryMbPduInitInfo.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        TicketStoragePduOrder pduOrder = new TicketStoragePduOrder();
        
       pduOrder =  this.getQryCondition(request,pduOrder );
          if (command != null) {
              command = command.trim();
              if (command.equals("qryInit")) {
                  TicketStorageMbInitiInfo record = new TicketStorageMbInitiInfo();
                  record = this.getQryCondition(request, record);
                List<TicketStorageMbInitiInfo> records = mbInitInfoMapper.qryInitInfo(record);
                opResult.setReturnResultSet(records);
              }
            
          }
       
        
         this.baseHandler(request, response, mv);
//         this.divideResultSet(request, mv, opResult);
         this.SaveOperationResult(mv, opResult); 
         return mv;
    }

    private TicketStoragePduOrder getQryCondition(HttpServletRequest request, TicketStoragePduOrder pduOrder) {
        if(pduOrder == null){
            return null;
        }
        
        String q_order_no = request.getParameter("q_order_no");
        if(q_order_no!=null && q_order_no.trim().equals("")){
            q_order_no = null;
        }
        pduOrder.setOrderNo(q_order_no);
        
         String q_beginTime = request.getParameter("q_beginTime");
        if(q_beginTime!=null && q_beginTime.trim().equals("")){
            q_beginTime = null;
        }
        pduOrder.setStartAchieveTime(q_beginTime);
        
         String q_endTime = request.getParameter("q_endTime");
        if(q_endTime!=null && q_endTime.trim().equals("")){
            q_endTime = null;
        }
        pduOrder.setEndAchieveTime(q_endTime);
        
        return pduOrder;
    }
    
     private TicketStorageMbInitiInfo getQryCondition(HttpServletRequest request, TicketStorageMbInitiInfo mbInitInfo) {
        if(mbInitInfo == null){
            return null;
        }
        
        String q_order_no = request.getParameter("q_order_no");
        if(q_order_no!=null && q_order_no.trim().equals("")){
            q_order_no = null;
        }
        mbInitInfo.setOrderNo(q_order_no);
      
        
        return mbInitInfo;
    }

    private List<TicketStoragePduOrder> fltPduOrders(List<TicketStoragePduOrder> pduOrders) {
        if(pduOrders == null){
            return pduOrders;
        }
        this.initFltMap();
        for(TicketStoragePduOrder pduOrder: pduOrders){
            pduOrder.setStartAchieveTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pduOrder.getAchieveTime()));
            pduOrder.setEsWorktypeId(mapEsWorkType.get(pduOrder.getEsWorktypeId()) == null ? null : (String)mapEsWorkType.get(pduOrder.getEsWorktypeId()));
            pduOrder.setHdlFlag(mapBillStatu.get(pduOrder.getHdlFlag())== null ? null : (String)mapBillStatu.get(pduOrder.getHdlFlag()));
        }
        return pduOrders;
    }

    private void initFltMap() {
        List<PubFlag> mapEsWorkTypes = pubFlagMapper.getEsWorkTypes();
        mapEsWorkType = new HashMap();
        for (PubFlag workType : mapEsWorkTypes) {
            mapEsWorkType.put(workType.getCode(), workType.getCode_text());
        }
        
        List<PubFlag> billStatus = pubFlagMapper.getCodesByType(PubFlagConstant.PUB_FLAG_TYPE_ORDER_HANDLE_FLAG);
        mapBillStatu = new HashMap();
        for (PubFlag billStatu : billStatus) {
            mapBillStatu.put(billStatu.getCode(), billStatu.getCode_text());
        }
    }

}
