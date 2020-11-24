/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.mapper;

import com.goldsign.acc.frame.entity.PubFlag;
import java.util.List;

/**
 *
 * @author hejj
 */
public interface PubFlagMapper {

    public List<PubFlag> getLines();

    public List<PubFlag> getStations();

    public List<PubFlag> getDevTypes();

    public List<PubFlag> getFilDevTypes();//参数模块设备类型过滤

    public List<PubFlag> getButtons();

    public List<PubFlag> getMerchants();

    public PubFlag getRecordFlags(String code);

    public List<PubFlag> getCardMains(); //票卡主类型 zhouyang 20170607

    public List<PubFlag> getCardSubs();//票卡子类型 zhouyang 20170607

    public List<PubFlag> getDiscounts();//是否优惠票 zhouyang 20170607

    public List<PubFlag> getSounds();//是否启用语音提示 zhouyang 20170607

    public List<PubFlag> getRecordFlagAll();//版本标志 zhouyang 20170607

    public List<PubFlag> getTimeTableIds();//节假日时间表类型 zhouyang 201706011

    public List<PubFlag> getTimeTableAllIds();//获取所有时间表类型 zhouyang 201706011

    public List<PubFlag> getFareNames();//收费区段

    public List<PubFlag> getPurseValueTypes();//钱包值类型

    public List<PubFlag> getFeeTypes();//收费方式

    public List<PubFlag> getChkValidPhyLogics();//逻辑及物理有效期检查

    public List<PubFlag> getCheckInOutSequences();//进出站次序检查

    public List<PubFlag> getDeviceTypes();//票卡属性设备

    public List<PubFlag> getRechargeDeviceTypes();//票卡属性设备

    public List<PubFlag> getSaleDeviceTypes();//票卡属性设备

    public List<PubFlag> getCardAttrYesOrNo();//票卡属性是否

    public List<PubFlag> getLccLines();   //Lcc线路

    public List<PubFlag> getContcs();    //运营商

    public List<PubFlag> getAgents();    //代理商

    public List<PubFlag> getChargeServerTypes();//设备优先级

    public List<PubFlag> getOperators();//操作员

    public List<PubFlag> getAdminHandleReasons(String recordFlag);//行政处理原因

    public List<PubFlag> getFareRideTypes();//乘车费率类型

    public List<PubFlag> getPubFlagsByType(String type);    //按类型查配置

    public List<PubFlag> getModeTypes();    //模式类型

    public List<PubFlag> getHolidayTypes();    //节假日代码

    public List<PubFlag> getDayOfWeeks();    //星期名称

    public List<PubFlag> getAccStatusValues();    //设备运营状态

    public List<PubFlag> getFareTables();    //票价表

    public List<PubFlag> getTimeCodes();    //乘车时间类型

    public List<PubFlag> getOpTypes();    //操作类型 zhouyang 20170622

    public List<PubFlag> getParamLogParamTypes();    //参数操作日志——参数类型 zhouyang 20170622

    public List<PubFlag> getParmTypeIds();     //参数类型ID liudz 20170622

    public List<PubFlag> getDevRecordFlags();  //设备版本标志 liudz 20170622

    public List<PubFlag> getIsEffectFlags();  //设备参数生效情况（是否有效） zhouyang 20170623

    public List<PubFlag> getParmTypes();     //参数类型

    public List<PubFlag> getStatuses();     //请求状态

    public List<PubFlag> getPenatlyResaons(); //罚款原因 zhongziqi 20170703

    public List<PubFlag> getReportModules(); //报表模板

    public List<PubFlag> getLineTypes(); //大线路

    public List<PubFlag> getPubFlags(int pubFlagType); //通过类型查询

    public List<PubFlag> getReportCodes(String moduleID); //报表代码

    public List<PubFlag> getACCStatusValue(); //ACC状态

    public List<PubFlag> getALLReportCodes();

	public List<PubFlag> getAdminWays();
        
    public List<PubFlag> getDevsOrder(); //获取设备顺序

    public List<PubFlag> getFareTimeId();//获取联乘时间间隔代码
    
    public List<PubFlag> getFareDealId();//获取累计消费额代码

    public List<PubFlag> getLineFlags();//线路标识

    public List<PubFlag> getLinesForRptQry(); //报表查询线路

}
