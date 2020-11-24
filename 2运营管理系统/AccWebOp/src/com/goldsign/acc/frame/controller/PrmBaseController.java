/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.controller;

import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.frame.constant.OpCodeConstant;
import com.goldsign.acc.frame.constant.ParameterConstant;
import com.goldsign.acc.frame.constant.TypeConstant;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.login.vo.User;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author hejj
 */
public class PrmBaseController extends BaseController {

    protected static String LINES = "lines";
    protected static String STATIONS = "stations";
    protected static String LINE_STATIONS = "lineStations";
    protected static String DEV_TYPES = "devTypes";
    protected static String FIL_DEV_TYPES = "filDevTypes";//参数模块设备类型过滤
    protected static String MERCHANTS = "merchants";
    protected static String VERSION = "version";//版本号
    protected static String RECORDFLAG = "recordFlag";//版本标志  zhouyang 20170609
    protected static String CARDMAINS = "cardMains";//票卡主类型  zhouyang 20170607
    protected static String CARDMAIN_SUBS = "cardMainSubs";//票卡主类型对应的子类型 zhouyang 20170608
    protected static String CARDSUBS = "cardSubs";//票卡子类型  zhouyang 20170607
    protected static String DISCOUNT = "discounts";//是否优惠票  zhouyang 20170607
    protected static String SOUNDS = "sounds";//是否启用语音提示  zhouyang 20170607
    protected static String TIMETABLE_IDS = "timeTableIds";//节假日时间表类型 zhouyang 20170611
    protected static String TIMETABLE_ALL_IDS = "timeTableAllIds";//所有时间表类型 zhouyang 20170615
    protected static String FARENAMES = "fareNames";//收费区段 mqf 20170612
    protected static String PURSEVALUETYPES = "purseValueTypes";//钱包值类型 luck 20170613
    protected static String FEETYPES = "feeTypes";//收费方式 luck 20170613
    protected static String CHKVALIDPHYLOGICS = "chkValidPhyLogics";//逻辑及物理有效期检查 luck 20170613
    protected static String CHECKINOUTSEQUENCES = "checkInOutSequences";//进出站次序检查 luck 20170613
    protected static String DEVICETYPES = "deviceTypes"; //票卡属性可用设备 luck 20170613
    protected static String RECHARGE_DEVTYPES = "rechargeDevTypes";//票卡属性充值设备 20170620
    protected static String SALE_DEVTYPES = "saleDevTypes";//票卡属性发售设备 20170620
    protected static String CARDATTRYESORNO = "cardAttrYesOrNo"; //票卡属性是否 luck 20170613
    protected static String CONTCS = "contcs";           //运营商
    protected static String LCC_LINES = "lccLines";     //LCC线路
    protected static String AGENTS = "agents";     //代理商
    protected static String CHARGE_SERVER_TYPES = "chargeServerTypes";     //设备优先级 20170614
    protected static String OPERATORS = "operators";     //操作员   20170614
    protected static String ADMINHANDLEREASONS = "adminHandleReasons";//行政处理原因 20170616
    protected static String FARERIDETYPES = "fareRideTypes"; //  乘车费率类型 luck 20170616
    protected static String SAMTYPES = "samTypes";     //sam卡类型
    protected static String MODETYPES = "modeTypes";     //模式类型
    protected static String HOLIDAY_TYPES = "holidayTypes";//节假日代码
    protected static String DAY_OF_WEEKS = "dayOfWeeks";//星期名称
    protected static String ACC_STATUS_VALUES = "accStatusValues";     //设备运营状态
    protected static String FARE_TABLES = "fareTables";     //票价表
    protected static String TIME_CODES = "timeCodes";     //乘车时间类型
    protected static String OP_TYPE = "opTypes";//操作类型  zhouyang 20170622
    protected static String PARAMLOG_PARAM_TYPE = "paramLogParamTypes";//参数操作日志——参数类型 //zhouyang 20170622
    protected static String PARMTYPEIDS = "parmTypeIds";//参数类型ID --liudz  20170622
    protected static String DEVRECORDFLAGS = "devRecordFlags";//设备版本标志 --liudz 20170622
    protected static String ISEFFECT = "isEffect";//设备参数生效情况（是否有效）  zhouyang 20170623
    protected static String PARM_TYPES = "parmTypes";//参数类型
    protected static String STATUSES = "statuses";//参数类型
    protected static String PENATLYRESONS = "penatlyReasons";//罚款原因 zhongziqi 20170703
    protected static String DIRECTION_FLAGS = "directionFlags";//客流方向 xiaowu 20171127
    protected static String ACC_STATUS_VALUE = "ACCStatusValue";//ACC状态
    protected static String REPORTMODULES = "reportModules";
    protected static String REPORTCODES = "reportCodes";
    protected static String ADMIN_WAYS = "adminWays";//事务代码 zhongzq20180306
    protected static String FARE_TIME_IDS = "fareTimeIds";//联乘时间间隔代码
    protected static String FARE_DEAL_IDS = "fareDealIds";//联乘时间间隔代码
    protected static String LINE_FLAGS = "lineFlags";//线路标识（0 实体线路；1 虚拟线路）
    protected static String PUBLISHER_FLAGS = "publisherFlags";//卡发行商标识

    protected void getRecordFlagName(PrmVersion version, PubFlag recordFlagName) {
        if (recordFlagName != null && version.getRecord_flag().equals(recordFlagName.getCode())) {
            version.setRecord_flag_name(recordFlagName.getCode_text());
        }

    }

    protected PrmVersion getPrmVersion(HttpServletRequest request, HttpServletResponse response) {
        PrmVersion vo = new PrmVersion();

        vo.setParm_type_id(FormUtil.getParameter(request, "Type"));
        vo.setRecord_flag(FormUtil.getParameter(request, "VersionType"));
        vo.setVersion_no(FormUtil.getParameter(request, "Version"));
        vo.setBegin_time(FormUtil.getParameter(request, "StartDate"));
        vo.setEnd_time(FormUtil.getParameter(request, "EndDate"));
        User user = (User) request.getSession().getAttribute("User");
        vo.setOperator_id(user.getAccount());

        return vo;

    }

    protected void setPageOptions(String[] attrNames, ModelAndView mv, HttpServletRequest request, HttpServletResponse response) {

        List<PubFlag> options;
        for (String attrName : attrNames) {
            if (attrName.equals(LINES)) {//线路
                options = pubFlagMapper.getLines();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(STATIONS)) {//车站
                options = pubFlagMapper.getStations();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(LINE_STATIONS)) {//线路车站
                options = pubFlagMapper.getStations();
                String lineStations = FormUtil.getLineStations(options);
                mv.addObject(attrName, lineStations);
                continue;
            }
            if (attrName.equals(LINE_FLAGS)) {//线路标识
                options = pubFlagMapper.getLineFlags();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(DEV_TYPES)) {//设备类型
                options = pubFlagMapper.getDevTypes();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(FIL_DEV_TYPES)) {//参数模块设备类型过滤
                options = pubFlagMapper.getFilDevTypes();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(MERCHANTS)) {//商户
                options = pubFlagMapper.getMerchants();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(CARDMAINS)) {//票卡主类型  zhouyang 20170607
                options = pubFlagMapper.getCardMains();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(CARDSUBS)) {//票卡子类型  zhouyang 20170607
                options = pubFlagMapper.getCardSubs();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(CARDMAIN_SUBS)) {//票卡主类型对应的子类型  zhouyang 20170607
                options = pubFlagMapper.getCardSubs();
                String cardMainSubs = FormUtil.getCardMainSubs(options);
                mv.addObject(attrName, cardMainSubs);
                continue;
            }
            if (attrName.equals(DISCOUNT)) {//是否优惠票  zhouyang 20170607
                options = pubFlagMapper.getDiscounts();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(SOUNDS)) {//是否启用语音提示  zhouyang 20170607
                options = pubFlagMapper.getSounds();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(RECORDFLAG)) {//版本标志  zhouyang 20170607
                options = pubFlagMapper.getRecordFlagAll();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(TIMETABLE_IDS)) {//节假日时间表类型  zhouyang 201706011
                options = pubFlagMapper.getTimeTableIds();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(TIMETABLE_ALL_IDS)) {//工作日及周末时间表类型  zhouyang 201706011
                options = pubFlagMapper.getTimeTableAllIds();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(VERSION)) {//参数版本
                PrmVersion version = this.getPrmVersion(request, response);
                PubFlag recordFlagName = pubFlagMapper.getRecordFlags(version.getRecord_flag());
                this.getRecordFlagName(version, recordFlagName);
                mv.addObject(attrName, version);
                continue;
            }
            if (attrName.equals(FARENAMES)) {//收费区段
                options = pubFlagMapper.getFareNames();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(PURSEVALUETYPES)) {//钱包值类型 luck 20170613
                options = pubFlagMapper.getPurseValueTypes();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(FEETYPES)) {//收费方式 luck 20170613
                options = pubFlagMapper.getFeeTypes();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(CHKVALIDPHYLOGICS)) {//逻辑及物理有效期检查 luck 20170613
                options = pubFlagMapper.getChkValidPhyLogics();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(CHECKINOUTSEQUENCES)) {//进出站次序检查 luck 20170613
                options = pubFlagMapper.getCheckInOutSequences();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(DEVICETYPES)) {//票卡属性可用设备 luck 20170613
                options = pubFlagMapper.getDeviceTypes();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(RECHARGE_DEVTYPES)) {//票卡属性充值设备 luck 20170620
                options = pubFlagMapper.getRechargeDeviceTypes();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(SALE_DEVTYPES)) {//票卡属性发售设备 luck 20170620
                options = pubFlagMapper.getSaleDeviceTypes();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(CARDATTRYESORNO)) {//票卡属性是否 luck 20170613
                options = pubFlagMapper.getCardAttrYesOrNo();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(CONTCS)) {//运营商
                options = pubFlagMapper.getContcs();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(LCC_LINES)) {//LCC线路
                options = pubFlagMapper.getLccLines();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(AGENTS)) {//代理商
                options = pubFlagMapper.getAgents();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(CHARGE_SERVER_TYPES)) {//设备优先级
                options = pubFlagMapper.getChargeServerTypes();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(OPERATORS)) {//操作员
                options = pubFlagMapper.getOperators();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(ADMINHANDLEREASONS)) {//行政处理原因
                options = pubFlagMapper.getAdminHandleReasons(ParameterConstant.RECORD_FLAG_DRAFT);
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(FARERIDETYPES)) {//乘车费率类型
                options = pubFlagMapper.getFareRideTypes();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(SAMTYPES)) {//sam卡类型
                options = pubFlagMapper.getPubFlagsByType(OpCodeConstant.COD_PF_SAM_TYPE);
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(MODETYPES)) {//模式类型
                options = pubFlagMapper.getModeTypes();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(HOLIDAY_TYPES)) {//节假日代码
                options = pubFlagMapper.getHolidayTypes();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(DAY_OF_WEEKS)) {//星期名称
                options = pubFlagMapper.getDayOfWeeks();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(ACC_STATUS_VALUES)) {//设备运营状态
                options = pubFlagMapper.getAccStatusValues();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(FARE_TABLES)) {//票价表
                options = pubFlagMapper.getFareTables();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(TIME_CODES)) {//乘车时间类型
                options = pubFlagMapper.getTimeCodes();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(OP_TYPE)) {//操作类型
                options = pubFlagMapper.getOpTypes();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(PARAMLOG_PARAM_TYPE)) {//参数操作日志——参数类型
                options = pubFlagMapper.getParamLogParamTypes();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(PARMTYPEIDS)) {//参数类型ID
                options = pubFlagMapper.getParmTypeIds();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(DEVRECORDFLAGS)) {//设备版本标志
                options = pubFlagMapper.getDevRecordFlags();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(ISEFFECT)) {//设备参数生效情况（是否有效）
                options = pubFlagMapper.getIsEffectFlags();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(PARM_TYPES)) {//参数类型
                options = pubFlagMapper.getParmTypes();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(STATUSES)) {//请求状态
                options = pubFlagMapper.getStatuses();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(PENATLYRESONS)) {
                options = pubFlagMapper.getPenatlyResaons();//罚款原因
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(DIRECTION_FLAGS)) {
                options = pubFlagMapper.getPubFlagsByType(TypeConstant.TYPE_DIRECTION_FLAG);//客流方向
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(ACC_STATUS_VALUE)) {
                options = pubFlagMapper.getACCStatusValue();//ACC设备状态
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(REPORTMODULES)) {
                options = pubFlagMapper.getReportModules();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(REPORTCODES)) {
                options = pubFlagMapper.getALLReportCodes();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(ADMIN_WAYS)) {
                options = pubFlagMapper.getAdminWays();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(FARE_TIME_IDS)) {
                options = pubFlagMapper.getFareTimeId();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(FARE_DEAL_IDS)) {
                options = pubFlagMapper.getFareDealId();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(PUBLISHER_FLAGS)) {
                options = pubFlagMapper.getPubFlags(76);
                mv.addObject(attrName, options);
            }

        }
    }

    private String getRecordFlagOld(PrmVersion prmVersion) {
        String recordFlagOld = "";
        String recordFlagSubmit = prmVersion.getRecord_flag_submit();
        if (recordFlagSubmit.equals(ParameterConstant.RECORD_FLAG_CURRENT)) {
            recordFlagOld = ParameterConstant.RECORD_FLAG_HISTORY;
        }
        if (recordFlagSubmit.equals(ParameterConstant.RECORD_FLAG_FUTURE)) {
            recordFlagOld = ParameterConstant.RECORD_FLAG_DELETED;
        }
        return recordFlagOld;

    }

    public String convertValidDate(String validDate) {
        if (validDate == null || validDate.trim().length() == 0 || validDate.trim().length() != 10) {
            return validDate;
        }
        int index = validDate.indexOf("-");
        int index1 = validDate.indexOf("-", index + 1);
        return validDate.substring(0, index) + validDate.substring(index + 1, index1) + validDate.substring(index1 + 1);
    }

    protected void getBaseParametersForSubmit(HttpServletRequest request, PrmVersion prmVersion) {
        prmVersion.setBegin_time_submit(FormUtil.getParameter(request, "ver_date_begin"));
        prmVersion.setEnd_time_submit(FormUtil.getParameter(request, "ver_date_end"));
        prmVersion.setRecord_flag_submit(FormUtil.getParameter(request, "ver_generate"));
        prmVersion.setRemark(FormUtil.getParameter(request, "ver_remark")); //版本备注

        prmVersion.setRecord_flag_old(this.getRecordFlagOld(prmVersion));
        prmVersion.setVersion_valid_date(this.convertValidDate(prmVersion.getBegin_time_submit()));//版本的生效日期格式YYYMMDD
        prmVersion.setBegin_time_new(prmVersion.getBegin_time_submit() + " 00:00:00");//版本的生效的开始时间
        prmVersion.setEnd_time_new(prmVersion.getEnd_time_submit() + " 23:59:59");//版本的生效的结束时间
        prmVersion.setRecord_flag_new(prmVersion.getRecord_flag_submit());

        prmVersion.setApp_line_name(OpCodeConstant.APP_LINE_NAME_NO_DOWN); //默认 "未下发"

    }

    protected void getBaseParameters(HttpServletRequest request, PrmVersion prmVersion) {
        prmVersion.setVersion_no(FormUtil.getParameter(request, "Version"));
        prmVersion.setRecord_flag(FormUtil.getParameter(request, "VersionType"));
        prmVersion.setParm_type_id(FormUtil.getParameter(request, "Type"));
        User user = (User) request.getSession().getAttribute("User");
        prmVersion.setOperator_id(user.getAccount());

        /*
        version = request.getParameter("Version");
        type = request.getParameter("Type");
        verStartDate = request.getParameter("StartDate");
        verEndDate = request.getParameter("EndDate");
        versionType = request.getParameter("VersionType");

        startDate = request.getParameter("ver_date_begin");
        endDate = request.getParameter("ver_date_end");
        verFlag = request.getParameter("ver_generate");
         */
    }

}
