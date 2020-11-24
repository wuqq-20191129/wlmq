/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.mapper;

import com.goldsign.acc.app.storagein.entity.TicketStorageInNew;
import com.goldsign.acc.app.storagein.entity.TicketStorageInNewDetail;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhouyang
 * 新票入库明细
 * 20170803
 */
public interface TicketStorageInNewDetailMapper {
    //根据入库单号获取明细表中的记录
    public List<TicketStorageInNewDetail> getTicketStorageInNewDetailByBillNo(String billNo);
    
    //查询判断历史索引表是否存在分表
    public String getHisTable(String billNo);
    
    //根据入库单号获取历史表中的记录
    public List<TicketStorageInNewDetail> getTicketStorageInNewHisByBillNo(Map queryMap);
    
    //添加入库明细
    public int addTicketStorageInNewDetail(TicketStorageInNewDetail ticketStorageInNewDetail);
    
    //查询入库明细表中是否存在与之不同的票库记录，用于判断加入的记录是否与其它记录的票库相同
    public List<TicketStorageInNewDetail> getDetailByStorage(TicketStorageInNewDetail ticketStorageInNewDetail);
    
    //删除明细记录
    public int deleteTicketStorageInNewDetail(TicketStorageInNewDetail ticketStorageInNewDetail);
    
    //获取当前入库单所有记录的数量之和
    public int getAllNumByBillNo(TicketStorageInNewDetail ticketStorageInNewDetail);
    
    //获取票区最大剩余数量
    public int getMaxNumOfArea(TicketStorageInNewDetail ticketStorageInNewDetail);
    
    //修改入库明细
    public int modifyTicketStorageInNewDetail(TicketStorageInNewDetail ticketStorageInNewDetail);
}
