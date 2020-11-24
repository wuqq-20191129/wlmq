package com.goldsign.acc.app.storageout.mapper;

import com.goldsign.acc.app.storageout.entity.TicketStorageOutDistributeBillDetail;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutDistributeOutBillDetail;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutDistributePlan;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutProduceOrder;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutProduceOutBill;
import java.util.HashMap;
import java.util.List;

public interface TicketStorageOutDistributeMapper {
  
    public List<TicketStorageOutDistributePlan> queryPlan(TicketStorageOutDistributePlan queryCondition);

    public List<TicketStorageOutDistributePlan> queryPlanDetail(TicketStorageOutDistributePlan queryCondition);

    public int addPlan(TicketStorageOutDistributePlan vo);

    public int addPlanDetailForLine(TicketStorageOutDistributePlan vo);

    public void auditPlan(HashMap<String, Object> parmMap);
    
    public int deletePlan(TicketStorageOutDistributePlan vo);
    
    public int deletePlanDetail(TicketStorageOutDistributePlan vo);

    public int modifyPlanDetail(TicketStorageOutDistributePlan vo);

    public List<TicketStorageOutDistributePlan> getExistStorage(TicketStorageOutDistributePlan vo);

    public int addPlanDetail(TicketStorageOutDistributePlan vo);

    public List<TicketStorageOutDistributeOutBillDetail> queryOutBillDetailByTotal(TicketStorageOutDistributeOutBillDetail queryCondition);

    public List<TicketStorageOutDistributeBillDetail> queryDistributeBill(TicketStorageOutDistributeBillDetail queryCondition);

    public List<TicketStorageOutDistributePlan> getExistReason(TicketStorageOutDistributePlan vo);

    public List<TicketStorageOutDistributePlan> getContainDataByLogicalId(TicketStorageOutDistributePlan vo);

    public List<TicketStorageOutDistributePlan> getContainDataByBox(TicketStorageOutDistributePlan vo);

    public int getBoxNum(String boxId);

    public int getBoxRecordNum(TicketStorageOutDistributePlan vo);

    public String getDetailRecordNum(TicketStorageOutDistributePlan po);

    
}