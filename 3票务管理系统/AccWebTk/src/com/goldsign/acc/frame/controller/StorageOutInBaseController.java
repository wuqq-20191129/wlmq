/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.controller;

import com.goldsign.acc.frame.constant.InOutConstant;
import com.goldsign.acc.frame.constant.PubFlagConstant;

import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.BillMapper;
import com.goldsign.acc.frame.util.DateUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.util.PubUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author hejj
 */
public class StorageOutInBaseController extends BaseController {

    public static final String OPER_TYPE_PLAN = "plan";
    public static final String OPER_TYPE_PLAN_DETAIL = "planDetail";
    public static final String OPER_TYPE_OUT_BILL = "outBill";
    public static final String OPER_TYPE_OUT_BILL_DETAIL = "outBillDetail";
    public static final String OPER_TYPE_OUT_LINE_CONTENT = "lineContent";
    public static final String OPER_TYPE_ORDER = "order";
    public static final String OPER_TYPE_CHECK_OUT = "checkOut"; //check
    public static final String OPER_TYPE_CHECK_IN = "checkIn";
    public static final String OPER_TYPE_CHECK_IN_SELECT = "checkInSelect"; //调账入库选择盘点单
    public static final String OPER_TYPE_CHECK_OUT_SELECT = "checkOutSelect"; //调账出库选择盘点单

    public static final String IN_TYPE_XR = "XR";//新票入库标识
    public static final String STORAGE_ALL = "9999";//所有仓库
    public static final String STORAGE_NONE = "0000";//无仓库
    protected static String IC_LINES = "icLines";
    protected static String IC_STATIONS = "icStations";
    protected static String IC_LINES_SERIAL = "icLinesSerial";//线路串
    protected static String IC_LINE_STATIONS = "icLineStations";
    protected static String IC_CARD_MAIN = "icCardMainTypes";//票库票卡主类型
    protected static String IC_CARD_MAIN_LIMIT = "icCardMainTypesLimit";//票库票卡主类型
    protected static String IC_CARD_MAIN_SERIAL = "icCardMainTypesSerial";
    protected static String IC_CARD_SUB = "icCardSubTypes";//票库票卡子类型
    protected static String IC_CARD_MAIN_SUB = "icCardMainSubTypes";//票库票卡主子类型
    protected static String IN_OUT_REASON_PRODUCE = "inOutReasonProduces";
    protected static String IN_OUT_REASON_PRODUCE_SERIAL = "inOutReasonProducesSerial";
    protected static String IN_OUT_REASON_DISTRIBUTE = "inOutReasonDistributes";//配票出库原因
    protected static String IN_OUT_REASON_BORROW = "inOutReasonBorrows";//配票出库原因
    protected static String ES_OPERATORS = "esOperators";
    protected static String STORAGES = "storages";
    protected static String AREAS = "zones";
    protected static String STORAGE_AREAS = "storageZones";
    protected static String AREAS_RECOVER = "zones_recover";
    protected static String STORAGE_AREAS_RECOVER = "storageZones_recover";
    protected static String AREAS_CANCEL = "zones_cancel"; //待注销区
    protected static String AREAS_BORROW_IN = "zones_borrow_in"; //借票归还
    protected static String STORAGE_AREAS_ENCODE_AND_VALUE = "storageZones_cod_and_value"; //编码区赋值区
    protected static String STORAGE_AREAS_BORROW_IN = "storageZonesBorrowIn";
    protected static String STORAGE_AREAS_CANCEL = "storageZones_cancel";
    protected static String STORAGE_AREAS_DESTROY = "storageZonesDestroy";
    protected static String BILL_STATUES = "billStatues";
    protected static String NEW_OLD_FLAGS = "newOldFlags";
    protected static String TEST_FLAGS = "testFlags";
    protected static String ORDER_TYPES = "orderTypes";
    protected static String MODES = "modes";
    protected static String SIGN_CARDS = "signCards";
    protected static String CARD_MONEYS = "cardMoneys";
    protected static String ES_WORK_TYPES = "esWorkTypes";
    protected static String CLEAN_OUT_BILL = "cleanOutBills"; //清洗入库出库单
    protected static String CANCEL_OUT_BILL = "cancelOutBills"; // 核查入库出库单
    protected static String AFC_LINES = "afcLines";
    protected static String AFC_STATIONS = "afcStations";
    protected static String AFC_CARD_MAIN = "afcCardMainTypes";//（运营）票卡主类型
    protected static String AFC_CARD_SUB = "afcCardSubTypes";//（运营）票卡子类型
    protected static String CARD_MAIN_SUB = "cardMainSubs";//（运营）票卡主子类型
    protected static String SALE_FLAGS = "saleFlags";
    protected static String HANDLE_FLAGS = "handleFlags";
    protected static String PDU_PRODUCE_BILLS = "pduProduceBills"; //生产工作单
    protected static String IN_OUT_REASON_FOR_IN = "inOutReasonForIn";
    protected static String IN_OUT_REASON_FOR_IN_PRODUCES = "inOutReasonForInProduces";//出库原因，用于生产入库的入库原因
    protected static String LINES = "lines";        //线路
    protected static String STATIONS = "stations";  //线路车站
    protected static String LINE_STATIONS = "lineStations";  //线路车站
    protected static String LIMIT_STATION_FLAG = "limitStationFlag";  //限站使用标志
    protected static String LIMIT_STATION_FLAG_SERIAL = "limitStationFlagSerial";  //限站使用标志串
    protected static String STORAGE_LINES_SERIAL = "storageLinesSerial";  //仓库线路串
    protected static String PARA_FLAG = "paraFlag";  //流失量参数标志
    protected static String IN_OUT_TYPES = "inOutTypes";//出入库主类型
    protected static String IN_OUT_SUB_TYPES = "inOutSubTypes";//出入库子类型
    protected static String IN_OUT_MAIN_SUB_TYPES = "inOutMainSubTypes";//出入库主子类型
    protected static String DIFF_REASONS = "diffReasons";//出入差额原因
    protected static String USE_FLAGS = "useFlags"; //
    protected static String BORROW_UNIT = "borrowUnits";
    protected static String USELESS_CARD_TYPES = "uselessCardTypes"; //废票遗失票类型
    protected static String CARD_MAIN_FOR_RETURN = "cardMainForReturn";//回收入库票止主类型
    protected static String LEND_BILL_NOS = "lendBillNos";//借出单
    protected static String DEV_CODE_ES = "devCodeESs";//ES机器号
    protected static String BILL_NAMES = "billNames";//单据名称

    List<String> stroageIds = new ArrayList<>();

    public boolean isOperForPlan(String operType) {
        if (operType == null || operType.length() == 0) {
            return true;
        }
        if (operType.equals(this.OPER_TYPE_PLAN)) {
            return true;
        }
        return false;
    }

    public boolean isOperForPlanDetail(String operType) {
        if (operType == null || operType.length() == 0) {
            return false;
        }
        if (operType.equals(this.OPER_TYPE_PLAN_DETAIL)) {
            return true;
        }
        return false;
    }

    public boolean isOperForOutBill(String operType) {
        if (operType == null || operType.length() == 0) {
            return false;
        }
        if (operType.equals(this.OPER_TYPE_OUT_BILL)) {
            return true;
        }
        return false;
    }

    public boolean isOperForOutBillDetail(String operType) {
        if (operType == null || operType.length() == 0) {
            return false;
        }
        if (operType.equals(this.OPER_TYPE_OUT_BILL_DETAIL)) {
            return true;
        }
        return false;
    }

    public boolean isOperForOrder(String operType) {
        if (operType == null || operType.length() == 0) {
            return false;
        }
        if (operType.equals(this.OPER_TYPE_ORDER)) {
            return true;
        }
        return false;
    }

    public boolean isOperTypeForXR(String operType) {
        if (operType == null || operType.length() == 0) {
            return true;
        }
        if (operType.equals(this.IN_TYPE_XR)) {
            return true;
        }
        return false;
    }

    public boolean isOperForCheckOut(String operType) {
        if (operType == null || operType.length() == 0) {
            return false;
        }
        if (operType.equals(this.OPER_TYPE_CHECK_OUT)) {
            return true;
        }
        return false;
    }

    public boolean isOperForCheckOutSelect(String operType) {
        if (operType == null || operType.length() == 0) {
            return false;
        }
        if (operType.equals(this.OPER_TYPE_CHECK_OUT_SELECT)) {
            return true;
        }
        return false;
    }

    public List<PubFlag> getStorages(String operatorId) {
        List<PubFlag> storagesAll = pubFlagMapper.getStoragesAll();
        List<PubFlag> storagesOperator = pubFlagMapper.getStoragesForOperator(operatorId);
        if (this.isAllStorages(storagesOperator)) {
            return storagesAll;
        }
        return this.getStoragesByOperator(storagesAll, storagesOperator);

    }

    private List<PubFlag> getStoragesByOperator(List<PubFlag> storagesAll, List<PubFlag> storagesOperator) {
        Vector<PubFlag> vs = new Vector();
        for (PubFlag pf : storagesAll) {
            if (this.isInStoragesOperator(pf, storagesOperator)) {
                vs.add(pf);
            }

        }
        return vs;
    }

    private boolean isInStoragesOperator(PubFlag pf, List<PubFlag> storagesOperator) {
        for (PubFlag stg : storagesOperator) {
            if (stg.getCode().equals(pf.getCode())) {
                return true;
            }

        }
        return false;
    }

    private boolean isAllStorages(List<PubFlag> storagesOperator) {
        for (PubFlag stg : storagesOperator) {
            if (stg.getCode().equals(STORAGE_ALL)) {
                return true;
            }

        }
        return false;
    }

    protected void baseHandlerForOutIn(HttpServletRequest request, HttpServletResponse response, ModelAndView modelView) {
        String operType = FormUtil.getParameter(request, "operType");
        String queryCondition = FormUtil.getParameter(request, "queryCondition");
        String billRecordFlag = FormUtil.getParameter(request, "billRecordFlag");
        if (operType != null && operType.length() != 0) {
            modelView.addObject("OperType", operType);
        }
        if (queryCondition != null && queryCondition.length() != 0) {
            modelView.addObject("QueryCondition", queryCondition);
        }
        if (billRecordFlag != null && billRecordFlag.length() != 0) {
            modelView.addObject("BillRecordFlag", billRecordFlag);
        }

    }

    protected void setPageOptions(String[] attrNames, ModelAndView mv, HttpServletRequest request, HttpServletResponse response) {

        List<PubFlag> options;
        String optionsSerial;
        for (String attrName : attrNames) {
            if (attrName.equals(IC_LINES)) {//IC线路
                options = pubFlagMapper.getLinesForIc();
                PubUtil.sortPubFlagList(options);
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(IC_STATIONS)) {//IC车站
                options = pubFlagMapper.getStationsForIc();
                PubUtil.sortPubFlagList(options);
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(IC_LINES_SERIAL)) {//IC线路串
                options = pubFlagMapper.getLinesForIc();
                optionsSerial = FormUtil.getMainSubs(options);
                mv.addObject(attrName, optionsSerial);
                continue;
            }
            if (attrName.equals(AFC_LINES)) {//AFC线路
                options = pubFlagMapper.getLinesForAfc();
                mv.addObject(attrName, options);
                continue;
            }

            if (attrName.equals(AFC_STATIONS)) {//AFC车站
                options = pubFlagMapper.getStationsForAfc();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(IC_LINE_STATIONS)) {//IC线路车站
                options = pubFlagMapper.getStationsForIc();
                PubUtil.sortPubFlagList(options);
                String lineStations = FormUtil.getLineStations(options);
                mv.addObject(attrName, lineStations);
                continue;
            }
            if (attrName.equals(IC_CARD_MAIN)) {//IC票卡主类型
                options = pubFlagMapper.getCardMainTypesForIc();
                PubUtil.sortPubFlagList(options);
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(IC_CARD_MAIN_LIMIT)) {//IC票卡主类型限制
                options = pubFlagMapper.getCardMainTypesForIcLimit();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(AFC_CARD_MAIN)) {//AFC票卡主类型
                options = pubFlagMapper.getCardMainTypesForAfc();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(IC_CARD_MAIN_SERIAL)) {//IC票卡主类型串
                options = pubFlagMapper.getCardMainTypesForIc();
                PubUtil.sortPubFlagList(options);
                optionsSerial = FormUtil.getMainSubs(options);
                mv.addObject(attrName, optionsSerial);
                continue;
            }

            if (attrName.equals(IC_CARD_SUB)) {//IC票卡子类型
                options = pubFlagMapper.getCardSubTypesForIc();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(AFC_CARD_SUB)) {//AFC票卡子类型
                options = pubFlagMapper.getCardSubTypesForAfc();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(IC_CARD_MAIN_SUB)) {//IC票卡主子类型
                options = pubFlagMapper.getCardSubTypesForIc();
                PubUtil.sortPubFlagList(options);
                String cardMainSubs = FormUtil.getMainSubs(options);
                mv.addObject(attrName, cardMainSubs);
                continue;
            }

            if (attrName.equals(CARD_MAIN_SUB)) {//（运营）票卡主子类型
                options = pubFlagMapper.getCardSubTypesForAfc();
                String cardMainSubs = FormUtil.getMainSubs(options);
                mv.addObject(attrName, cardMainSubs);
                continue;
            }

            if (attrName.equals(IN_OUT_REASON_PRODUCE)) {//生产出库原因
                options = pubFlagMapper.getInOutReasonByFlag(InOutConstant.TYPE_REASON_OUT);
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(IN_OUT_REASON_DISTRIBUTE)) {//配票出库原因
                options = pubFlagMapper.getInOutReasonForDistribute();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(IN_OUT_REASON_BORROW)) {//借票出库原因
                options = pubFlagMapper.getInOutReasonForBorrow();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(BORROW_UNIT)) {//借票出库原因
                options = pubFlagMapper.getBorrowUnits();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(IN_OUT_REASON_PRODUCE_SERIAL)) {//生产出库原因串
                options = pubFlagMapper.getInOutReasonByFlag(InOutConstant.TYPE_REASON_OUT);
                optionsSerial = FormUtil.getMainSubs(options);
                mv.addObject(attrName, optionsSerial);
                continue;
            }
            if (attrName.equals(ES_OPERATORS)) {//ES操作员
                options = pubFlagMapper.getEsOperators();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(STORAGES)) {//仓库
                String operatorId = PageControlUtil.getOperatorFromSession(request);
                options = this.getStorages(operatorId);
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(AREAS)) {//仓库票区
                options = pubFlagMapper.getStoragesAreas();
                PubUtil.sortPubFlagList(options);
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(STORAGE_AREAS)) {//仓库票区
                options = pubFlagMapper.getStoragesAreas();
                String MainSubs = FormUtil.getMainSubs(options);
                mv.addObject(attrName, MainSubs);
                continue;
            }
            if (attrName.equals(AREAS_RECOVER)) {//仓库回收区
                options = pubFlagMapper.getStoragesAreasRecover();

                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(STORAGE_AREAS_ENCODE_AND_VALUE)) {//编码区和赋值区
                options = pubFlagMapper.getStoragesAreasEncodeAndValue();
                String CodAssi = FormUtil.getMainSubs(options);
                mv.addObject(attrName, CodAssi);
                continue;
            }
            if (attrName.equals(STORAGE_AREAS_RECOVER)) {//仓库回收区
                options = pubFlagMapper.getStoragesAreasRecover();
                String MainSubs = FormUtil.getMainSubs(options);
                mv.addObject(attrName, MainSubs);
                continue;
            }

            if (attrName.equals(AREAS_CANCEL)) {//仓库待注销
                options = pubFlagMapper.getStoragesAreasCancel();

                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(AREAS_BORROW_IN)) {
                options = pubFlagMapper.getStoragesAreasBorrowIn();

                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(STORAGE_AREAS_BORROW_IN)) {
                options = pubFlagMapper.getStoragesAreasBorrowIn();
                String MainSubs = FormUtil.getMainSubs(options);
                mv.addObject(attrName, MainSubs);
                continue;
            }
            if (attrName.equals(STORAGE_AREAS_CANCEL)) {//仓库待注销
                options = pubFlagMapper.getStoragesAreasCancel();
                String MainSubs = FormUtil.getMainSubs(options);
                mv.addObject(attrName, MainSubs);
                continue;
            }
            if (attrName.equals(STORAGE_AREAS_DESTROY)) {//待销毁
                options = pubFlagMapper.getStoragesAreasDestroy();
                String MainSubs = FormUtil.getMainSubs(options);
                mv.addObject(attrName, MainSubs);
                continue;
            }
            if (attrName.equals(ES_WORK_TYPES)) {//ES工作类型
                options = pubFlagMapper.getEsWorkTypes();
                mv.addObject(attrName, options);
                continue;
            }
             if (attrName.equals(IN_OUT_TYPES)) {//出入库主类型
                options = pubFlagMapper.getInOutTypes();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(IN_OUT_SUB_TYPES)) {//出入库子类型
                options = pubFlagMapper.getInOutSubTypes();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(IN_OUT_MAIN_SUB_TYPES)) {//出入库主子类型
                options = pubFlagMapper.getInOutSubTypes(); 
                String inOutMainSubTypes = FormUtil.getLineStations(options);
                mv.addObject(attrName, inOutMainSubTypes);
                continue;
            }
            
            
            if (attrName.equals(CLEAN_OUT_BILL)) {//清洗入库出库单
                List<PubFlag> storages = this.getStorages(PageControlUtil.getOperatorFromSession(request));
                for (PubFlag s : storages) {
                    stroageIds.add(s.getCode());
                }
                options = pubFlagMapper.getCleanOutBills(stroageIds);
                mv.addObject(attrName, options);
                continue;
            }

            if (attrName.equals(CANCEL_OUT_BILL)) {//核查入库出库单

                List<PubFlag> storages = this.getStorages(PageControlUtil.getOperatorFromSession(request));
                for (PubFlag s : storages) {
                    stroageIds.add(s.getCode());
                }
                options = pubFlagMapper.getCancelOutBills(stroageIds);
                mv.addObject(attrName, options);
                continue;
            }

            if (attrName.equals(PDU_PRODUCE_BILLS)) {//生产工作单
                String operatorId = PageControlUtil.getOperatorFromSession(request);
                options = pubFlagMapper.getPduProduceBills(operatorId);
                mv.addObject(attrName, options);
                continue;
            }

            if (attrName.equals(IN_OUT_REASON_FOR_IN)) {//入库原因
                options = pubFlagMapper.getInOutReasonByFlag(InOutConstant.TYPE_REASON_IN);
                mv.addObject(attrName, options);
                continue;
            }

            if (attrName.equals(IN_OUT_REASON_FOR_IN_PRODUCES)) {//出库原因，用于生产入库的入库原因
                options = pubFlagMapper.getInOutReasonForInProduces();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(LEND_BILL_NOS)) {//借票单号
                options = pubFlagMapper.getLendBillNos();
                mv.addObject(attrName, options);
                continue;
            }
            /**
             * 从PUBFLAG获取的选项数据
             */
            if (attrName.equals(BILL_STATUES)) {//单据状态
                options = pubFlagMapper.getCodesByType(PubFlagConstant.PUB_FLAG_TYPE_BILL_STATE);
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(NEW_OLD_FLAGS)) {//新旧票标识
                options = pubFlagMapper.getCodesByType(PubFlagConstant.PUB_FLAG_TYPE_NEW_OLD_CARD_FLAG);
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(PARA_FLAG)) {//流失量
                options = pubFlagMapper.getCodesByType(PubFlagConstant.PUB_FLAG_TYPE_PARA_FLAG);
                mv.addObject(attrName, options);
                continue;
            }

            if (attrName.equals(CARD_MONEYS)) {//面值
                options = pubFlagMapper.getCodesByType(PubFlagConstant.PUB_FLAG_TYPE_MONEY);
                mv.addObject(attrName, options);
                continue;
            }

            if (attrName.equals(TEST_FLAGS)) {//测试标志
                options = pubFlagMapper.getCodesByType(PubFlagConstant.PUB_FLAG_TYPE_TEST_FLAG);
                mv.addObject(attrName, options);
                continue;
            }

            if (attrName.equals(ORDER_TYPES)) {//订单类型
                options = pubFlagMapper.getCodesByType(PubFlagConstant.PUB_FLAG_TYPE_ORDER_TYPE);
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(MODES)) {//乘次票限制模式
                options = pubFlagMapper.getCodesByType(PubFlagConstant.PUB_FLAG_TYPE_MODE);
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(SIGN_CARDS)) {//记名卡标志
                options = pubFlagMapper.getCodesByType(PubFlagConstant.PUB_FLAG_TYPE_SIGN_CARD);
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(HANDLE_FLAGS)) {//订单处理标识
                options = pubFlagMapper.getCodesByType(PubFlagConstant.PUB_FLAG_TYPE_ORDER_HANDLE_FLAG);
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(SALE_FLAGS)) {//发售激活标识
                options = pubFlagMapper.getCodesByType(PubFlagConstant.PUB_FLAG_TYPE_ORDER_SALE_FLAG);
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(LINES)) {//线路
                options = pubFlagMapper.getLines(PubFlagConstant.PARAM_TYPE_ID_LINE);
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(STATIONS)) {//线路车站
                options = pubFlagMapper.getStations(PubFlagConstant.PARAM_TYPE_ID_STATION);
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(LINE_STATIONS)) {//线路车站
                options = pubFlagMapper.getStations(PubFlagConstant.PARAM_TYPE_ID_STATION);
                String lineStations = FormUtil.getLineStations(options);
                mv.addObject(attrName, lineStations);
                continue;
            }
            if (attrName.equals(LIMIT_STATION_FLAG)) {//限站使用标志
                options = pubFlagMapper.getCodesByType(PubFlagConstant.PUB_FLAG_TYPE_RESTRICTFLAG);
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(LIMIT_STATION_FLAG_SERIAL)) {//限站使用标志串 
                options = pubFlagMapper.getCodesByType(PubFlagConstant.PUB_FLAG_TYPE_RESTRICTFLAG);
                String MainSubs = FormUtil.getMainSubs(options);
                mv.addObject(attrName, MainSubs);
                continue;
            }
            if (attrName.equals(STORAGE_LINES_SERIAL)) {//仓库线路串
                options = pubFlagMapper.getStorageLines();
                String MainSubs = FormUtil.getMainSubs(options);
                mv.addObject(attrName, MainSubs);
                continue;
            }
            if (attrName.equals(DIFF_REASONS)) {//出入差额原因
                options = pubFlagMapper.getDiffReasons();
                mv.addObject(attrName, options);
                continue;
            }

            if (attrName.equals(USE_FLAGS)) {//票卡生产类别
                options = pubFlagMapper.getCodesByType(PubFlagConstant.PUB_FLAG_TYPE_USE_FLAG);
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(USELESS_CARD_TYPES)) {//废票遗失票类型
                options = pubFlagMapper.getCodesByType(PubFlagConstant.PUB_FLAG_TYPE_USELESS_CARD_TYPE);
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(CARD_MAIN_FOR_RETURN)) {//仓库票区
                options = pubFlagMapper.getCardMainTypesForReturn();
                String MainSubs = FormUtil.getMainSubs(options);
                mv.addObject(attrName, MainSubs);
                continue;
            }
            if (attrName.equals(DEV_CODE_ES)) {//ES机器号
                options = pubFlagMapper.getDevCodeES();
                mv.addObject(attrName, options);
                continue;
            }

            if (attrName.equals(BILL_NAMES)) {//单据名称
                options = pubFlagMapper.getBillName();
                mv.addObject(attrName, options);
                continue;
            }
        }
    }

    public String getBillNoTemp(BillMapper billMapper, String billType) {
        HashMap<String, String> parmMap = new HashMap();
        parmMap.put("piBillMainType", billType);
        billMapper.getBillNoTemp(parmMap);

        String billNo = parmMap.get("poBillNo");
        return billNo;
        // parmMap.put("piBillMainType", InOutConstant.TYPE_BILL_NO_PRODUCE_PLAN_TEMP);
    }
    //判断多日票。。。。。。

//    public boolean isTicketForMulDays(String icSubType, String esWorkType) {
//        if (icSubType.equals(InOutConstant.IC_SUB_CARD_TYPE_MULDAYS) && esWorkType.equals(InOutConstant.WORK_TYPE_PRECHARGE)) {
//            return true;
//        }
//        return false;
//    }

    public String getCardValidDate(String validDate, String validDays) throws Exception {
        Date dValidDate = DateUtil.strToUtilDate(validDate);
        int iValidDays = Integer.parseInt(validDays);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dValidDate);
        calendar.add(Calendar.DAY_OF_YEAR, iValidDays);
        String datetime = formatter.format(calendar.getTime());
        return datetime;
    }

    public String getCardValidDateInt(String validDate, int validDays) throws Exception {
        Date dValidDate = DateUtil.strToUtilDate(validDate);
        // int iValidDays = validDays;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dValidDate);
        calendar.add(Calendar.DAY_OF_YEAR, validDays);
        String datetime = formatter.format(calendar.getTime());
        return datetime;
    }

    public int getCardMoneyForMulDays(String cardAvaDays) {
        int cardMoney = (new Integer(cardAvaDays).intValue()) * 500;
        return cardMoney;

    }

    protected void setOperatorId(ModelAndView mv, HttpServletRequest request) {
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        mv.addObject("OperatorID", operatorId);
    }

    public boolean isOutByLogical(String startLogicalId, String endLogicalId) {
        if (startLogicalId != null && endLogicalId != null && startLogicalId.length() != 0 && endLogicalId.length() != 0) {
            return true;
        }
        return false;
    }

    public static boolean isPlanForLine(String reasonId) {
        if (reasonId == null || reasonId.length() == 0) {
            return false;
        }
        if (reasonId.equals(InOutConstant.DISTRIBUTE_REASON_STATION)) {
            return true;
        }
        return false;
    }

    public static boolean isValidCard(String card) {
        if (card == null) {
            return false;
        }
        if (!(card.length() <= 20 && card.length() >= 16)) {
            return false;
        }
        return true;
    }

    /**
     * 根据操作员的仓库权限设置仓库列表
     *
     * @param request
     * @return
     */
    public List getStorageIdListForQueryCondition(HttpServletRequest request, String storage_id) {

        String operatorId = PageControlUtil.getOperatorFromSession(request);
        //查询当前操作员的有权限的仓库
        List<PubFlag> storageIdOps = this.getStorages(operatorId);
        List<String> storageIdList = new ArrayList<String>();
        if (storage_id == null || "".equals(storage_id) || storage_id.isEmpty()) {
            for (PubFlag op : storageIdOps) {
                storageIdList.add(op.getCode());
            }
        } else {
            storageIdList.add(storage_id);
        }

        return storageIdList;
    }
}
