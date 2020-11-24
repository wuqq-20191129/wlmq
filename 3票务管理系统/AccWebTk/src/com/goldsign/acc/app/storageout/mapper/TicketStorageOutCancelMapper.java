/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storageout.mapper;

import com.goldsign.acc.app.storageout.entity.TicketStorageOutCancelPlan;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutProduceOutBill;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author luck
 */
public interface TicketStorageOutCancelMapper {

    public int addPlan(TicketStorageOutCancelPlan vo);

    public List<TicketStorageOutCancelPlan> queryPlan(TicketStorageOutCancelPlan vo);

    public int deletePlan(TicketStorageOutCancelPlan vo);

    public int addPlanDetail(TicketStorageOutCancelPlan vo);

    public List<TicketStorageOutCancelPlan> queryPlanDetail(TicketStorageOutCancelPlan vo);

    public String getDistinctStorage(TicketStorageOutCancelPlan vo);

    public int modifyPlanDetail(TicketStorageOutCancelPlan vo);

    public int deletePlanDetail(TicketStorageOutCancelPlan vo);

    public void auditPlan(HashMap<String, Object> params);

    public List<TicketStorageOutProduceOutBill> queryOutBillDetail(TicketStorageOutProduceOutBill vo);

}
