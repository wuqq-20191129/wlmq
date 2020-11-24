/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.mapper;

import com.goldsign.acc.app.storagein.entity.TicketStorageInBorrowBill;
import com.goldsign.acc.app.storagein.entity.TicketStorageInBorrowBillDetail;
import com.goldsign.acc.app.storagein.entity.TicketStorageInBorrowInDetail;
import java.util.HashMap;
import java.util.List;


/**
 * @desc:
 * @author:zhongzqi
 * @create date: 2017-9-1
 */
public interface TicketStorageInBorrowMapper {

    public void addInBill(HashMap<String, Object> parmMap);

    public List<TicketStorageInBorrowBill> queryBorrowBill(TicketStorageInBorrowBill queryCondition);

    public String getDetailRecordNum(TicketStorageInBorrowBill po);

    public int updateReturnFlag(String billNo);

    public int updateDeleteFlag(TicketStorageInBorrowBill po);

    public List<TicketStorageInBorrowBillDetail> queryBorrowBillDetail(TicketStorageInBorrowBillDetail queryCondition);

    public void getMaxReturnNum(HashMap<String, Object> parmMap);

    public List<TicketStorageInBorrowBillDetail> getCurrentData(TicketStorageInBorrowBillDetail vo);

    public int modifyPlanDetail(TicketStorageInBorrowBillDetail vo);

    public int modifyPlanDetailForId(TicketStorageInBorrowBillDetail vo);

    public int insertNewStartSectionForDetail(TicketStorageInBorrowBillDetail vo);

    public int insertNewEndSectionForDetail(TicketStorageInBorrowBillDetail vo);

    public int getLendQuantity(TicketStorageInBorrowBillDetail vo);

    public String getLendBillNo(TicketStorageInBorrowBillDetail vo);

    public int getLendBillNo(String lendBillNo);

    public int updateReturnFlagTrue(String lendBillNo);

    public int updateReturnFlagFalse(String lendBillNo);

    public int getReturnQuantity(String lendBillNo);

    public String getBoxFlag(String waterNo);

    public int getLendNumFromDetailBox(String lendWaterNo);

    public int getLendNumFromDetail(String lendWaterNo);

    public int getRetunTotal(String lendWaterNo);

    public List<TicketStorageInBorrowBillDetail> queryReturnDetail(String lendWaterNo);

     public int deletePlanDetail(TicketStorageInBorrowBillDetail po);

    public int getReturnNum(TicketStorageInBorrowBillDetail po);

    public void auditInBorrowBill(HashMap<String, Object> parmMap);
    
    public List<TicketStorageInBorrowInDetail> getInBillDetail(TicketStorageInBorrowInDetail queryCondition);
    
    public void auditInBill(HashMap<String, Object> parmMap);

    public List<TicketStorageInBorrowInDetail> getHistory(TicketStorageInBorrowInDetail queryCondition);

    public List<TicketStorageInBorrowInDetail> getQueryHis(TicketStorageInBorrowInDetail vo);
}
