/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storageout.mapper;

import com.goldsign.acc.app.storageout.entity.TicketStorageOutCleanOutBill;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author luck
 */
public interface TicketStorageOutCleanMapper {

    public List<TicketStorageOutCleanOutBill> queryPlan(TicketStorageOutCleanOutBill vo);
//    public List<TicketStorageOutCleanOutBill> queryPlan(HashMap<String,Object> params);

    public int addPlan(TicketStorageOutCleanOutBill vo);

    public int deletePlan(TicketStorageOutCleanOutBill vo);

    public List<TicketStorageOutCleanOutBill> queryPlanDetail(TicketStorageOutCleanOutBill vo);

    public int addPlanDetail(TicketStorageOutCleanOutBill vo);

    public String getDistinctStorage(TicketStorageOutCleanOutBill vo);

    public int modifyPlanDetail(TicketStorageOutCleanOutBill vo);

    public int deletePlanDetail(TicketStorageOutCleanOutBill vo);

    public void auditPlan(HashMap<String, Object> params);

}
