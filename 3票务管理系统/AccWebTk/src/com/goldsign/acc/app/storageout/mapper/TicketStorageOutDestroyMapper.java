package com.goldsign.acc.app.storageout.mapper;

import com.goldsign.acc.app.storageout.entity.TicketStorageOutDestroyOutBill;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutDestroyOutBillDetail;
import java.util.HashMap;
import java.util.List;

public interface TicketStorageOutDestroyMapper {
    public List<TicketStorageOutDestroyOutBill> queryOutBill(TicketStorageOutDestroyOutBill queryCondition);

    public int addBill(TicketStorageOutDestroyOutBill vo);
    
    public int deleteOutBill(TicketStorageOutDestroyOutBill vo);

    public int addOutBillDetail(TicketStorageOutDestroyOutBillDetail vo);

    public List<TicketStorageOutDestroyOutBill> queryOutBillDetail(TicketStorageOutDestroyOutBill queryCondition);

    public int modifyOutBillDetail(TicketStorageOutDestroyOutBillDetail vo);
    
      public int deleteOutBillDetail(TicketStorageOutDestroyOutBillDetail vo);

    public void auditOutBill(HashMap<String, Object> parmMap);

    public List<TicketStorageOutDestroyOutBillDetail> getExistStorage(TicketStorageOutDestroyOutBillDetail vo);

    public String getDetailRecordNum(TicketStorageOutDestroyOutBill vo);

}