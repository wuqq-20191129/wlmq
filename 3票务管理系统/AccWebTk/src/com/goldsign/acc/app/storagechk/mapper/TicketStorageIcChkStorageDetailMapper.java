package com.goldsign.acc.app.storagechk.mapper;

import com.goldsign.acc.app.storagechk.entity.TicketStorageIcChkStorageDetail;
import com.goldsign.acc.app.storagechk.entity.TicketStorageIcChkStorageDetailKey;
import java.util.List;

public interface TicketStorageIcChkStorageDetailMapper {
    int deleteByPrimaryKey(String chkBillNo);

    int insert(TicketStorageIcChkStorageDetail record);

    int insertSelective(TicketStorageIcChkStorageDetail record);

    List<TicketStorageIcChkStorageDetail> selectByPrimaryKey(TicketStorageIcChkStorageDetailKey key);

    int updateByPrimaryKeySelective(TicketStorageIcChkStorageDetail record);

    int updateByPrimaryKey(TicketStorageIcChkStorageDetail record);

    int addBillDetailForBox(TicketStorageIcChkStorageDetail storageDetail);
    
    int addBillDetailForAreaForSjt(TicketStorageIcChkStorageDetail storageDetail);

    int addBillDetailForAreaSub(TicketStorageIcChkStorageDetail storageDetail);
    
    int selectDetailSeq(TicketStorageIcChkStorageDetailKey key);
}