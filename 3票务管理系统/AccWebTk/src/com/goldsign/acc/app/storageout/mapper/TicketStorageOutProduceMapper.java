/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storageout.mapper;

import com.goldsign.acc.app.storageout.entity.TicketStorageOutProduceOrder;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutProduceOutBill;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutProducePlan;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public interface TicketStorageOutProduceMapper {

    public int addPlan(TicketStorageOutProducePlan vo);

    public void auditPlan(HashMap<String, Object> params);

    public int deletePlan(TicketStorageOutProducePlan vo);

    public List<TicketStorageOutProducePlan> queryPlan(TicketStorageOutProducePlan vo);

    public int addPlanDetail(TicketStorageOutProducePlan vo);

    public int modifyPlanDetail(TicketStorageOutProducePlan vo);

    public int deletePlanDetail(TicketStorageOutProducePlan vo);

    public List<TicketStorageOutProducePlan> queryPlanDetail(TicketStorageOutProducePlan vo);

    public List<TicketStorageOutProduceOutBill> queryOutBill(TicketStorageOutProduceOutBill vo);

    public List<TicketStorageOutProduceOutBill> queryOutBillDetail(TicketStorageOutProduceOutBill vo);

    public void auditOutBill(HashMap<String, Object> params);

    public List<TicketStorageOutProduceOrder> queryOrder(TicketStorageOutProduceOrder vo);
    
    public int getPlanDetailListCount(String billNo);
    
    public int getOutDetailCountForDeleteOutBill(Vector<TicketStorageOutProducePlan> vo);

}
