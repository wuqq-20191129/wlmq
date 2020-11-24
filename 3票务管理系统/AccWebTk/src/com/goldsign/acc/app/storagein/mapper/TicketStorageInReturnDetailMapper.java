/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.mapper;
import com.goldsign.acc.app.storagein.entity.TicketStorageInReturnDetail;
import com.goldsign.acc.frame.entity.PubFlag;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhouyang
 * 回收入库明细
 * 20170817
 */
public interface TicketStorageInReturnDetailMapper {
    //获取票卡主类型
    public List<PubFlag> getIcCardMainType();
    
    //获取票库线路
    public List<PubFlag> getIcCodStorageLine();
    
    //入库原因
    public List<PubFlag> getInReason();
    
    //根据入库单号获取明细表中的记录
    public List<TicketStorageInReturnDetail> getTicketStorageInReturnDetailByBillNo(String billNo);
    
    //查询判断历史索引表是否存在分表
    public String getHisTable(String billNo);
    
    //根据入库单号获取历史表中的记录
    public List<TicketStorageInReturnDetail> getTicketStorageInReturnDetailHisByBillNo(Map queryMap);
    
    //添加入库明细
    public int addTicketStorageInReturnDetail(TicketStorageInReturnDetail vo);
    
    //查询入库明细表中是否存在与之不同的票库记录，用于判断加入的记录是否与其它记录的票库相同
    public List<TicketStorageInReturnDetail> getDetailByStorage(TicketStorageInReturnDetail vo);
    
    //删除明细记录
    public int deleteTicketStorageInReturnDetail(TicketStorageInReturnDetail vo);
    
    //根据表名与对应条件，获取需要的字段。
    public List<PubFlag> getTableFlagsByCondition(TicketStorageInReturnDetail vo);
    
    //修改入库明细
    public int modifyTicketStorageInReturnDetail(TicketStorageInReturnDetail vo);
    
    //车站上交，查询是否有车站当日的上传记录
    public List<String> getUploadRecordOfStationHandIn(TicketStorageInReturnDetail vo);
    
    //车站上交，查询票区是否有足够的剩余量
    public List<Integer> getAreaOfStationHandIn(TicketStorageInReturnDetail vo);
    
    //车站上交：更新w_ic_inf_station_handin的流水号
    public int modifyStationDetailNo(TicketStorageInReturnDetail vo);
     
    //车站上交：使用w_ic_inf_station_handin的数据插入w_ic_in_store_detail中
    public int addByStationHandIn(TicketStorageInReturnDetail vo);
    
    //车站上交：更新表w_acc_tk.w_ic_inf_station_handin中in_flag状态为1
    public int modifyStationInFlag(TicketStorageInReturnDetail vo);
     
     //收益组移交：查询是否有收益组当日的上传记录
    public List<String> getUploadRecordOfIncomeDepHandIn(TicketStorageInReturnDetail vo);
    
    //收益组移交：查询收益组当日的上传记录是否已经存在库中
    public List<TicketStorageInReturnDetail> getDetailTableRecordOfIncomeDepHandIn(TicketStorageInReturnDetail vo);
    
     //收益组移交：查询票区是否有足够的剩余量
    public List<Integer> getAreaOfIncomeDepHandIn(TicketStorageInReturnDetail vo);
    
    //收益组移交：先给表w_acc_tk.w_ic_inf_incomedep_handin中的in_store_detail_no字段增加流水号
    public int modifyIncomedepDetailNo(TicketStorageInReturnDetail vo);
    
    //收益组移交：将数据插入到明细表中
    public int addByIncomeDepHandIn(TicketStorageInReturnDetail vo);
    
    ///收益组移交：更新表w_acc_tk.w_ic_inf_incomedep_handin中in_flag状态为1
    public int modifyIncomedepInFlag(TicketStorageInReturnDetail vo);
    
    //其它入库原因：查询票区是否有足够的剩余量
    public List<Integer> getAreaOfOthers(TicketStorageInReturnDetail vo);
    
     //其它入库原因：将数据插入到明细表中
    public int addByOthers(TicketStorageInReturnDetail vo);
    
    //根据流水号获取该记录的入库原因
    public String getInReasonByWaterNo(TicketStorageInReturnDetail vo);
    
    //车站上交：修改w_ic_inf_station_handin的入库状态为0：未入库
    public int UpdateINFlagForStationDelete(TicketStorageInReturnDetail vo);
    
    //收益组移交：修改w_ic_inf_incomedep_handin的入库状态为0：未入库
    public int UpdateINFlagForIncomedepDelete(TicketStorageInReturnDetail vo);
    
    //根据流水号获取该记录的报表日期
    public String getReporDateByWaterNo(TicketStorageInReturnDetail vo);
    
    //根据入库原因与报表日期获取流水号
    public List<String> getWaterNoByInReasonRepordate(TicketStorageInReturnDetail vo);
}
