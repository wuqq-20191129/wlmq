
package com.goldsign.acc.app.storagein.mapper;

import com.goldsign.acc.app.storagein.entity.TicketStorageInNew;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutProducePlan;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhouy
 * 新票入库
 * 20170727
 */
public interface TicketStorageInNewMapper {
    //查询
    public List<TicketStorageInNew> getTicketStorageInNew(TicketStorageInNew ticketStorageInNew);
    
    //查询当前用户权限下的所有仓库
    public List<TicketStorageInNew> getUserStorege(String operator);
    
    //根据入库单号查询入库明细表中的记录
    public List<String> getStorageInDetailByBillNo(String billNo);
    
    //查询是否存在历史表
    public List<TicketStorageInNew> getHistTable();
    
    //增加新票入库单
    public List<TicketStorageInNew> addTicketStorageInNew(Map map);
    
    //查询入库单
    public List<TicketStorageInNew> getTicketStorageInNewByBillNo(String billNo);
    
    //删除入库单
    public int deleteTicketStorageInNew(TicketStorageInNew vo);
    
    //查询入库单明细表
    public List<TicketStorageInNew> getDetailByBillNo(String billNo);
    
    //审核入库单
    public void auditNew(HashMap<String, Object> params);
}
