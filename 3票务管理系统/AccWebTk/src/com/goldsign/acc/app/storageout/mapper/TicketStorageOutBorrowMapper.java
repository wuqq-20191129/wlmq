package com.goldsign.acc.app.storageout.mapper;

import com.goldsign.acc.app.storageout.entity.TicketStorageOutBorrowOutBillDetail1;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutBorrowPlan;
import java.util.HashMap;
import java.util.List;

public interface TicketStorageOutBorrowMapper {
    
    public int addPlan(TicketStorageOutBorrowPlan vo);

    public List<TicketStorageOutBorrowPlan> queryPlan(TicketStorageOutBorrowPlan queryCondition);
    
    public int deletePlan(TicketStorageOutBorrowPlan vo);

    public String getDetailRecordNum(TicketStorageOutBorrowPlan po);

    public List<TicketStorageOutBorrowPlan> queryPlanDetail(TicketStorageOutBorrowPlan queryCondition);

    public String getExistStorage(TicketStorageOutBorrowPlan vo);

    public int addPlanDetail(TicketStorageOutBorrowPlan vo);
    
    public int deletePlanDetail(TicketStorageOutBorrowPlan vo);

    public int modifyPlanDetail(TicketStorageOutBorrowPlan vo);

    public void auditPlan(HashMap<String, Object> parmMap);

    public List<TicketStorageOutBorrowOutBillDetail1> queryOutBillDetailByTotal(TicketStorageOutBorrowOutBillDetail1 queryCondition);

    public List<TicketStorageOutBorrowOutBillDetail1> queryOutBillDetailByLogical(TicketStorageOutBorrowOutBillDetail1 queryCondition);
    
    public List<TicketStorageOutBorrowPlan> getContainData(TicketStorageOutBorrowPlan queryCondition);
}