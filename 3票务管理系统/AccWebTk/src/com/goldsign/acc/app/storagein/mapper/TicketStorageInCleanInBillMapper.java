/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.mapper;

import com.goldsign.acc.app.storagein.entity.TicketStorageInCleanInBill;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author luck
 */
public interface TicketStorageInCleanInBillMapper {

    public List<TicketStorageInCleanInBill> queryPlan(TicketStorageInCleanInBill vo);

    public int addPlan(TicketStorageInCleanInBill vo);

    public int deletePlan(TicketStorageInCleanInBill vo);

    public List<TicketStorageInCleanInBill> queryPlanDiff(TicketStorageInCleanInBill vo);

    public int getNumFromPlanDetial(TicketStorageInCleanInBill vo);

    public List<TicketStorageInCleanInBill> queryPlanDetail(TicketStorageInCleanInBill vo);

    public String getTableName(TicketStorageInCleanInBill vo);

    public List<TicketStorageInCleanInBill> getTableNames();

    public int deletePlanDetail(TicketStorageInCleanInBill vo);

    public int deletePlanDiff(TicketStorageInCleanInBill vo);

    public String getRelatedBillNo(TicketStorageInCleanInBill vo);

    public int updateOutBillInFlag(String outBull);

    public TicketStorageInCleanInBill getInBillInfo(TicketStorageInCleanInBill vo);

    public int getAuditedDetials(TicketStorageInCleanInBill vo);

    public int getDetialsFromDetials(TicketStorageInCleanInBill vo);

    public int getInNumFromDetial(TicketStorageInCleanInBill vo);

    public int getDetialsFromHistory(TicketStorageInCleanInBill vo);

    public int getInNumFromHistory(TicketStorageInCleanInBill vo);

    public int getNoAuditDetials(TicketStorageInCleanInBill vo);

    public int getInNumFromNoAuditDetials(TicketStorageInCleanInBill vo);

    public TicketStorageInCleanInBill getOutBillFinfo(TicketStorageInCleanInBill vo);

    public int addPlanDetailByvalid(TicketStorageInCleanInBill vo);

    public int addPlanDetailByRealBalance(TicketStorageInCleanInBill vo);

    public int addPlanDetailByManUselessNum(TicketStorageInCleanInBill vo);

    public int addInOutDiff(TicketStorageInCleanInBill vo);

    public int updateOutBillInFlagByVo(TicketStorageInCleanInBill vo);

    public int getFlag(TicketStorageInCleanInBill vo);

    public void auditPlan(HashMap<String, Object> params);

}
