/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.mapper;

import com.goldsign.acc.app.storagein.entity.TicketStorageInCleanInBill;
import java.util.HashMap;

/**
 * 核销入库
 *
 * @author luck
 */
public interface TicketStorageInCancelMapper {

    public int addPlan(TicketStorageInCleanInBill vo);

    public int updateOutBillInFlag(TicketStorageInCleanInBill vo);

    public int getPlanDetailsNum(TicketStorageInCleanInBill vo);

    public int deletePlan(TicketStorageInCleanInBill vo);

    public TicketStorageInCleanInBill getInBillInfo(TicketStorageInCleanInBill vo);

    public TicketStorageInCleanInBill getOutBillFinfo(TicketStorageInCleanInBill vo);

    public int addPlanDetailByvalid(TicketStorageInCleanInBill vo);

    public int addPlanDetailByRealBalance(TicketStorageInCleanInBill vo);

    public int addPlanDetailByManUselessNum(TicketStorageInCleanInBill vo);

    public int addPlanDetailByCancelMoreNum(TicketStorageInCleanInBill vo);

    public int addInOutDiff(TicketStorageInCleanInBill vo);

    public void auditPlan(HashMap<String, Object> params);
    
    public int isExistBill(TicketStorageInCleanInBill vo);

}
