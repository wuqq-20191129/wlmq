/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.controller;

import com.goldsign.acc.app.storagein.entity.TicketStorageIcIdxHistory;
import com.goldsign.acc.app.storagein.entity.TicketStorageInBill;
import com.goldsign.acc.app.storagein.entity.TicketStorageInNew;
import com.goldsign.acc.app.storagein.entity.TicketStorageInNewDetail;
import com.goldsign.acc.app.storagein.mapper.TicketStorageInNewDetailMapper;
import com.goldsign.acc.app.storagein.mapper.TicketStorageInNewMapper;
import com.goldsign.acc.app.storagein.mapper.TicketStorageInParentMapper;
import com.goldsign.acc.frame.constant.InOutConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.BillMapper;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.util.PubUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;



/**
 *
 * @author mh
 */
public class TicketStorageInParentController  extends StorageOutInBaseController{
    
    @Autowired
    private TicketStorageInParentMapper tsInParentMapper;
    
     @Autowired
    private TicketStorageInNewMapper tsinMapper;
    
     @Autowired
    private TicketStorageInNewDetailMapper tsindMapper;
    /**
     * 查询索引表的分表信息
     * @param billNo
     * @return 
     */
    protected List<TicketStorageIcIdxHistory> getIcIdxHistoryListForIn(String billNo) {
        if (billNo ==null || billNo.equals("")) {
            List<TicketStorageIcIdxHistory> list = tsInParentMapper.getIcIdxHistoryListForIn();
            return list;
        } else {
            //如果查询条件的入库单号不为空，并且能在入库明细表w_ic_in_store_detail查询到记录，则不需要到分表查询
            int detailCount = tsInParentMapper.getIcInDetailCount(billNo);
            if (detailCount<=0) {
                List<TicketStorageIcIdxHistory> list = tsInParentMapper.getIcIdxHistoryListForIn();
                return list;
            } else {
                return new ArrayList<TicketStorageIcIdxHistory>();
            }
            
        }
    }
    
    /**
     * 查询queryCondition设置分表记录列表
     * @param request
     * @param queryCondition 
     */
    protected void setIcIdxHisListForQueryCondition(HttpServletRequest request, TicketStorageInBill queryCondition) {
        List<TicketStorageIcIdxHistory> icIdxHisList = getIcIdxHistoryListForIn(queryCondition.getBill_no());
        queryCondition.setIcIdxHisList(icIdxHisList);
    }
        
    /**
     * 查询queryCondition 根据操作员的仓库权限设置仓库列表
     * @param request
     * @param queryCondition
     * @return 
     */
    protected void setStorageIdListForQueryCondition(HttpServletRequest request, TicketStorageInBill queryCondition) {
        
//        String operatorId = PageControlUtil.getOperatorFromSession(request);
//        //查询当前操作员的有权限的仓库
//        List<PubFlag> storageIdOps =this.getStorages(operatorId);
//        List <String> storageIdList = new ArrayList<String>();
//        if (queryCondition.getStorage_id() == null || queryCondition.getStorage_id().isEmpty()) {
//            for (PubFlag op:storageIdOps) {
//                storageIdList.add(op.getCode());
//            }
//        } else {
//            storageIdList.add(queryCondition.getStorage_id());
//        }
//        queryCondition.setStorageIdList(storageIdList);
        List storageIdList = getStorageIdListForQueryCondition(request, queryCondition.getStorage_id()); 
        queryCondition.setStorageIdList(storageIdList);
    }
    
    
    /**
     * 查询入库单
     * @param queryCondition
     * @return 
     */
    protected List<TicketStorageInBill> queryForInBill(HttpServletRequest request, TicketStorageInBill queryCondition) {
        //查询索引表的分表信息,并设置查询queryCondition
        setIcIdxHisListForQueryCondition(request,queryCondition);
        //根据操作员的仓库权限设置仓库列表
        setStorageIdListForQueryCondition(request,queryCondition);
        
        List<TicketStorageInBill> resultSet = new ArrayList<TicketStorageInBill>();
        //入库原因、票卡主类型、票卡子类型这三个查询条件只有全部为空时，才查询明细没有记录的入库单
        if((queryCondition.getReason_id()==null || queryCondition.getReason_id().equals(""))
           && (queryCondition.getIc_main_type()==null || queryCondition.getIc_main_type().equals(""))
           && (queryCondition.getIc_sub_type()==null || queryCondition.getIc_sub_type().equals(""))){
            resultSet.addAll(tsInParentMapper.queryForNotExistsSubTb(queryCondition));
        }
        resultSet.addAll(tsInParentMapper.queryForExistsSubTb(queryCondition));
        //20180611 mqf 对当前表、分表数据统一排序
        PubUtil.sortInBillList(resultSet);
        
        return resultSet;
        
    }
    
    
    
    /**
     * 检查入库数量是否超库存上限
     * @param pos
     * @return 
     */
    protected String validStorageZone(Vector<TicketStorageInBill> pos) {
        String result = "";
        List<Map> vszList = new ArrayList<Map>();
        for (TicketStorageInBill po : pos) {
                vszList = tsInParentMapper.validStorageZone(po.getBill_no());
                if (!vszList.isEmpty()) {
                    Map vsz = vszList.get(0);
                    BigDecimal remainNum = (BigDecimal)vsz.get("REMAIN_NUM"); //key值必须大写
                    if (remainNum == null || remainNum.doubleValue()<=0) {
                        result = po.getBill_no()+"无法审核，入库数量超过区上限!";
                        return result;
                    }
                }
        }
        return result;
    }
    
    protected TicketStorageInBill getQueryConditionIn(HttpServletRequest request,String inType) {
        TicketStorageInBill qCon = new TicketStorageInBill();
        
        qCon.setIn_type(inType);
        String begin_date = FormUtil.getParameter(request, "q_beginTime");
        String end_date = FormUtil.getParameter(request, "q_endTime");
        if(begin_date!=null && !begin_date.equals(""))
            qCon.setBill_date_begin(begin_date + " 00:00:00");
        if(end_date!=null && !end_date.equals(""))
            qCon.setBill_date_end(end_date + " 23:59:59");

        qCon.setBill_no(request.getParameter("q_billNo"));  
        qCon.setRecord_flag(request.getParameter("q_recordFlag"));
        qCon.setIc_main_type(request.getParameter("q_cardMainCode"));
        qCon.setIc_sub_type(request.getParameter("q_cardSubCode"));
        qCon.setReason_id(request.getParameter("q_inReason"));
        qCon.setStorage_id(request.getParameter("q_storage"));
        return qCon;
    }
    
}
