package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageBlankCardManage;
import java.util.List;
/**
 * 空白卡订单管理
 * @author xiaowu
 */
public interface TicketStorageBlankCardManageMapper {
  
    public List<TicketStorageBlankCardManage> getTicketStorageBlankCardManage(TicketStorageBlankCardManage vo);
    
    public List<TicketStorageBlankCardManage> queryByUpdateBillNoList(TicketStorageBlankCardManage vo);
    
    public List<TicketStorageBlankCardManage> isExisting(TicketStorageBlankCardManage vo);
    
    public String getCurrentStartLogicNo(TicketStorageBlankCardManage vo);
    
    public String getCurrentBillNoTemp(String bill_main_type_id);
    
    public String getCurrentBillNo(String bill_main_type_id);
    
    public String getCurrentBatchNo(TicketStorageBlankCardManage vo);
    
    public int addTicketStorageBlankCardManage(TicketStorageBlankCardManage vo);
    
    public int addCurrentBillNoTemp(TicketStorageBlankCardManage vo);
    
    public int addCurrentBillNo(TicketStorageBlankCardManage vo);
    
    public int addCurrentLogicNo(TicketStorageBlankCardManage vo);

    public int modifyTicketStorageBlankCardManage(TicketStorageBlankCardManage vo);
    
    public int auditTicketStorageBlankCardManage(TicketStorageBlankCardManage vo);
    
    public int updateCurrentLogicNo(TicketStorageBlankCardManage vo);
    
    public int updateCurrentBillNoTemp(TicketStorageBlankCardManage vo);
    
    public int updateCurrentBillNo(TicketStorageBlankCardManage vo);
    
    public int updateBillNo(TicketStorageBlankCardManage vo);

    public List<TicketStorageBlankCardManage> getTicketStorageBlankCardManageById(TicketStorageBlankCardManage vo);
    
    public List<TicketStorageBlankCardManage> getTicketStorageBlankCardManageByStock(TicketStorageBlankCardManage vo);
    
    /**
     * 查询空白卡订单对应逻辑卡号卡类型代码及中文名         用于下拉框
     * @return 
     */
    public List<TicketStorageBlankCardManage> getBlankCardTypes();
    
    public int deleteTicketStorageBlankCardManage(TicketStorageBlankCardManage vo);
    
    public List<String> getCardTypeList(String type);

    public List<String> getCardLogicList();
}

