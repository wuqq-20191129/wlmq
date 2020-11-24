/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.mapper;

import com.goldsign.acc.app.storagein.entity.TicketStorageInNew;
import com.goldsign.acc.app.storagein.entity.TicketStorageInReturn;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhouyang
 * 回收入库
 * 20170815
 */
public interface TicketStorageInReturnMapper {
    //查询
    public List<TicketStorageInReturn> getTicketStorageInReturn(TicketStorageInReturn ticketStorageInReturn);
    
    //查询当前用户权限下的所有仓库
    public List<TicketStorageInReturn> getUserStorege(String operator);
    
    //根据入库单号查询入库明细表中的记录
    public List<String> getStorageInDetailByBillNo(String billNo);
    
    //查询是否存在历史表
    public List<TicketStorageInReturn> getHistTable();
    
    //增加新票入库单
    public List<TicketStorageInReturn> addTicketStorageInReturn(Map map);
    
    //查询入库单
    public List<TicketStorageInReturn> getTicketStorageInReturnByBillNo(String billNo);
    
    //删除入库单
    public int deleteTicketStorageInReturn(TicketStorageInReturn vo);
    
    //查询入库单明细表
    public List<TicketStorageInReturn> getDetailByBillNo(String billNo);
    
    //审核入库单
    public void audit(HashMap<String, Object> params);
}
