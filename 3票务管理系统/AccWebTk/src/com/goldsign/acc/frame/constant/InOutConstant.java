/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.constant;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hejj
 */
public class InOutConstant {

    public static final String RESULT_NAME_MAIN_TYPE = "MainTypes";//票卡主类型
    public static final String RESULT_NAME_SUB_TYPE = "SubTypes";//票卡子类型
    public static final String RESULT_NAME_STORAGE = "Storages";//仓库
    public static final String RESULT_NAME_ZONE = "Zones";//票区
    public static final String RESULT_NAME_OUT_REASON_FORALL = "OutReasonsForAll";//出库原因仅代码及文本
    public static final String RESULT_NAME_OUT_REASON = "OutReasons";//出库原因包括工作类型
    public static final String RESULT_NAME_OUT_REASON_DISTRIBUTE = "OutReasonsDistribute";//配票出库原因
    public static final String RESULT_NAME_OUT_REASON_BORROW = "OutReasonsBorrow";//借票出库原因
    public static final String RESULT_NAME_LINE = "Lines";//线路
    public static final String RESULT_NAME_STATION = "Stations";//车站
    public static final String RESULT_NAME_BILL_STATUS = "RecordFlags";//单据状态
    public static final String RESULT_NAME_IN_REASON = "InReasons";//入库原因
    public static final String RESULT_NAME_WORK_TYPE = "WorkTypes";//工作类型
    public static final String RESULT_NAME_NEWOLD_FLAG = "NewOldFlags";//新旧票标志
    public static final String RESULT_NAME_MONEY = "Moneys";//面值
    public static final String RESULT_NAME_ES_OPERATOR = "EsOperators";//es操作员
    public static final String RESULT_NAME_ADJUST_REASON = "AdjustReasons";//调帐原因
    public static final String RESULT_NAME_RESTRICTfLAG = "RestrictFlags";//限制本站使用原因
    public static final String RESULT_NAME_BORROWUNIT = "BorrowUnits";//借票原因
    public static final String RESULT_NAME_CONFIRM_STATUS = "ConfirmStatuses";//车票确认状态
    public static final String RESULT_NAME_CONFIRM_TYPE = "ConfirmTypes";//车票确认类型
    public static final String RESULT_NAME_HDL_FLAG = "HdlFlags";//订单执行标志
    public static final String RESULT_NAME_FINISH_FLAG = "FinishFlags";//订单明细上传标志
    public static final String RESULT_NAME_PARA_FLAG = "ParaFlags";//流失量参数标志
    public static final String RESULT_NAME_LIMIT_MODE_FLAG = "limitModes";//乘次票限值模式标志
    public static final String RESULT_NAME_RETURN_TYPE = "returnTypes";//乘次票限值模式标志
    public static final String RESULT_NAME_SALE = "saleFlag";//发售激活标志
    public static final String RESULT_NAME_TEST = "testFlag";//测试标志
    public static final String RESULT_NAME_NAME_FLAG = "NameFlags";//记名卡标志
    public static final String RESULT_ORDER_TYPES = "OrderTypes";//订单类型
    public static final String RESULT_NAME_PRODUCE_FACTORY = "produceFactorys";//生产厂商
    public static final String RESULT_NAME_BLANK_CARD_TYPE = "blankCardTypes";//空白卡订单 票卡类型
    public static final String TYPE_BILL_NO_BC_LOGIC_NO_TEMP = "BC";//临时空白卡管理
    public static final String TYPE_BILL_NO_BC_LOGIC_NO = "BC";//空白卡管理
    public static final String TYPE_BILL_NO_TICKET_MOVE_TEMP = "TM";//票库迁移
    public static final String TYPE_BILL_NO_TICKET_MOVE = "TM";//票库迁移
    public static final String TYPE_BILL_NO_TICKET_MOVE_OUT = "TO";//票库迁出
    public static final String TYPE_BILL_NO_TICKET_MOVE_IN = "TI";//票库迁入
    public static final String RESULT_NAME_SEX = "sex";//
    public static final String RESULT_NAME_IDENTITY = "identity";//
    public static final String RESULT_NAME_AFCLINE = "afcLines";//线路
    public static final String RESULT_NAME_AFCSTATION = "afcStations";//车站
    //20151208 add by mqf
    public static final String RESULT_NAME_STORAGE_LINE = "StorageLines";//仓库对照的线路
    public static final String RESULT_NAME_IS_USED_FLAG = "isUsedFlag";//是否已使用标识
    /*
     * 出入库原因类型
     */
    public static final String TYPE_REASON_OUT = "2";//出库原因
    public static final String TYPE_REASON_IN = "1";//入库原因
    public static final String REASON_ID_BORROW = "21";//借票出库原因
    public static final String REASON_ID_DESTROY = "23";//销毁出库原因
    public static final String REASON_ID_CLEAN = "08";//清洗出库原因
    //20160418 modify by mqf
    public static final String MOVE_REASON_IN = "25";//迁移入库
    public static final String MOVE_REASON_OUT = "26";//迁移出库
    /**
     * 新旧单乘票类型
     */
    public static final String TYPE_SJT_OLD = "0";//旧单乘票
    public static final String TYPE_SJT_NEW = "1";//新单乘票
    /**
     * 单据流水类型
     */
    public static final String TYPE_BILL_NO_OUT_PRODUCE = "SC";//生产出库
    public static final String TYPE_BILL_NO_OUT_DISTRIBUTE = "PC";//配票出库
    public static final String TYPE_BILL_NO_OUT_BORROW = "JC";//借票出库
    public static final String TYPE_BILL_NO_OUT_ADJUST = "TC";//调账出库
    public static final String TYPE_BILL_NO_OUT_DESTROY = "XC";//销毁出库
    public static final String TYPE_BILL_NO_OUT_CANCEL = "HC";//核销出库
    public static final String TYPE_BILL_NO_OUT_CLEAN = "QC";//清洗出库
    public static final String[] TYPE_BILL_NO_OUT = {TYPE_BILL_NO_OUT_PRODUCE,
        TYPE_BILL_NO_OUT_DISTRIBUTE,
        TYPE_BILL_NO_OUT_BORROW,
        TYPE_BILL_NO_OUT_ADJUST,
        TYPE_BILL_NO_OUT_DESTROY,
        TYPE_BILL_NO_OUT_CANCEL,
        TYPE_BILL_NO_OUT_CLEAN,
        TYPE_BILL_NO_TICKET_MOVE_OUT //20160416 add by mqf
};//出库类型
    public static final String TYPE_BILL_NO_OUT_ALL = "OL";//出库类型
    public static final String TYPE_BILL_NO_OUT_PRODUCE_TEMP = "SC";//临时生产出库
    //public static final String TYPE_BILL_NO_OUT_DISTRIBUTE_TEMP = "SC";//临时配票出库
    public static final String TYPE_BILL_NO_OUT_DISTRIBUTE_TEMP = "PC";//临时配票出库单号  modify by zhongzq 20170801
//    public static final String TYPE_BILL_NO_OUT_BORROW_TEMP = "SC";//临时借票出库
    public static final String TYPE_BILL_NO_OUT_BORROW_TEMP = "JC";//临时借票出库 
    public static final String TYPE_BILL_NO_OUT_ADJUST_TEMP = "TC";//临时调账出库
    public static final String TYPE_BILL_NO_OUT_DESTROY_TEMP = "XC";//临时销毁出库
    public static final String TYPE_BILL_NO_OUT_CANCEL_TEMP = "HC";//临时核销出库
    public static final String TYPE_BILL_NO_OUT_CLEAN_TEMP = "QC";//临时清洗出库
    public static final String TYPE_BILL_NO_IN_NEW = "XR";//新票入库
    public static final String TYPE_BILL_NO_IN_PRODUCE = "SR";//生产入库
    public static final String TYPE_BILL_NO_IN_RETURN = "HR";//回收入库
    public static final String TYPE_BILL_NO_IN_BORROW = "JR";//借票归还
    public static final String TYPE_BILL_NO_IN_ADJUST = "TR";//调账入库
    public static final String TYPE_BILL_NO_CHECK = "PD";  //库存盘点
    public static final String TYPE_BILL_NO_IN_NEW_TEMP = "XR";//新票入库
    public static final String TYPE_BILL_NO_IN_PRODUCE_TEMP = "SR";//生产入库
    public static final String TYPE_BILL_NO_IN_RETURN_TEMP = "HR";//回收入库
    public static final String TYPE_BILL_NO_IN_BORROW_TEMP = "JR";//借票归还
    public static final String TYPE_BILL_NO_IN_CLEAN_TEMP = "QR"; //清洗入库
    public static final String TYPE_BILL_NO_IN_CANCEL_TEMP = "CR"; //核销入库
    public static final String TYPE_BILL_NO_IN_ADJUST_TEMP = "TR";//调账入库
    public static final String TYPE_BILL_NO_PRODUCE_PLAN = "RJ";//日工作计划
    public static final String TYPE_BILL_NO_PRODUCE = "SD";//生产工作单
    public static final String TYPE_BILL_NO_DISTRIBUTE_PLAN = "PJ";//配票计划
    public static final String TYPE_BILL_NO_DISTRIBUTE = "PP";//配票计划
    public static final String TYPE_BILL_NO_BORROW = "JP";//借票计划
    public static final String TYPE_BILL_NO_CANCEL_PLAN = "HJ";///核销工作计划
    public static final String TYPE_BILL_NO_PRODUCE_PLAN_TEMP = "RJ";///临时日工作计划
    public static final String TYPE_BILL_NO_PRODUCE_TEMP = "SD";///临时生产工作单
    public static final String TYPE_BILL_NO_DISTRIBUTE_PLAN_TEMP = "PJ";///临时配票计划单号
    public static final String TYPE_BILL_NO_DISTRIBUTE_TEMP = "PP";///临时配票单号
    public static final String TYPE_BILL_NO_BORROW_TEMP = "JP";///临时借票计划
    public static final String TYPE_BILL_NO_CANCEL_PLAN_TEMP = "HJ";///临时核销工作计划
    public static final String TYPE_BILL_NO_CHECK_TEMP = "PD";///临时核销工作计划
    /**
     * 单据流水长度
     *
     */
    public static final int LEN_BILL_NO = 12;//单据长度
//	public static final int LEN_BILL_NO_PLAN=12;//日工作计划流水长度
//	public static final int LEN_BILL_NO_OUT_PRODUCE=12;//生产出库流水长度
//	public static final int LEN_BILL_NO_DISTRIBUTE=12;//配票单流水长度
    public static final int LEN_BILL_NO_IN = 12;//入库流水长度
//	public static final int LEN_BILL_NO_OUT_BORROW=12;//借票流水长度
//	public static final int LEN_BILL_NO_BORROW=12;//借票流水长度
    public static final int LEN_BILL_NO_TEMP = 12;//临时单据长度
//	public static final int LEN_BILL_NO_OUT_PRODUCE_T=12;//临时单据长度
    /**
     * 单据记录标志
     */
    public static final String RECORD_FLAG_BILL_EFFECT = "0";//单据有效
    public static final String RECORD_FLAG_BILL_UNDO = "1";//单据撤消（对未审核单据）
    public static final String RECORD_FLAG_BILL_DELETE = "2";//单据删除（对有效单据）
    public static final String RECORD_FLAG_BILL_UNAUDIT = "3";//单据未审核
    public static final String RECORD_FLAG_BILL_LOCKED = "4";//单据锁定
    /**
     * 单据锁定标志
     */
    public static final String LOCK = "1";//锁定
    public static final String LOCK_NOT = "0";//非锁定
    public static final String LOCK_FINISH = "2";//已出库平帐
    /**
     * 订单执行标志
     *
     */
    public static final String HDL_FLAG_UNDO = "0";//未处理订单
    public static final String HDL_FLAG_DO = "1";//订单已执行
    public static final String HDL_FLAG_NOTFINISH = "2";//订单非完整完成
    public static final String HDL_FLAG_FINISH = "3";//订单已完成
    /**
     * 记名卡标志
     *
     */
    public static final String CARD_NAME_FLAG_NOT = "0";//非记名卡
    public static final String CARD_NAME_FLAG_YES = "1";//记名卡
    /**
     * 票区定义
     *
     */
    public static final String AREA_NEW = "01";//新票区
    public static final String AREA_ENCODE = "02";//编码区
    public static final String AREA_VALUE = "03";//赋值区
    public static final String AREA_CIRCLE = "04";//循环区
    public static final String AREA_ERASE = "05";//注销区
    public static final String AREA_DESTROY = "06";//销毁区
    public static final String AREA_RECLAIM = "07";//回收区
    /**
     * 柜是否满标志
     */
    public static final String FULL_FLAG_EMPTYP = "0";//空
    public static final String FULL_FLAG_YES = "1";//满
    public static final String FULL_FLAG_N0 = "2";//未满
    /**
     * ES工作类型
     *
     */
    public static final String WORK_TYPE_INITAIL = "00";//初始化
    public static final String WORK_TYPE_PRECHARGE = "01";//预赋值
    public static final String WORK_TYPE_ENCODE = "02";//编码
    public static final String WORK_TYPE_ERASE = "03";//注销
    public static final String WORK_TYPE_DESTROY = "04";//销毁
    public static final String WORK_TYPE_CLEAN = "05";//清洗
    /**
     * 配票原因
     *
     */
    public static final String DISTRIBUTE_REASON_STATION = "17";//配票车站
    public static final String DISTRIBUTE_REASON_DEPARTMENT = "18";//配票其他部门
    public static final String DISTRIBUTE_REASON_AGENT = "19";//配票员工票办理点
    /**
     * 配票是否发送至票务系统
     *
     */
    public static final String GRANT_SENDED = "1";//已发送
    public static final String GRANT_UNSENDED = "0";//未送
    /**
     * 票卡类型
     * 2018-5-9 mqf
     * 现在业务不需要在出库时在表w_ic_out_bill_detail的card_type字段写入"000"、旧票"001"、新票"002"
     * 只是在生产出库时使用card_type "0": 非记名卡，"1":记名卡，其他出库都写入""值。
     */
//    public static final String CARD_TYPE_NOT = "000";//未指定
//    public static final String CARD_TYPE_OLD = "001";//旧票
//    public static final String CARD_TYPE_NEW = "002";//新票
    
    public static final String CARD_TYPE_NO_USE = "";//不使用
    /**
     *
     * 票卡主类型
     */
    public static final String IC_MAIN_TYPE_SJT = "12";//单乘票
    public static final String IC_MAIN_TYPE_SVT = "1";//储值票
    public static final String IC_MAIN_TYPE_TCT = "40";//乘次票
    /**
     * 逻辑卡号
     */
    public static final int LEN_LOGICAL = 20;//逻辑卡号长度
    public static final int LEN_LOGICAL_EFFECTIVE = 20;//逻辑卡号长度
    public static final int LEN_LOGICAL_EFFECTIVE_MIN = 16;//逻辑卡号最小长度 add by zhongzq 20180626
    /**
     * 盒号
     */
    //盒号有效长度 add by zhongzq 20180626
    public static final int LEN_BOX_EFFECTIVE = 14;
    /**
     * 票卡主类型
     *
     */
    public static final String IC_CARD_TYPE_SJT = "12";//单乘票
    public static final String IC_CARD_TYPE_SVT = "1";//储值票
    public static final String IC_CARD_TYPE_EMP = "2";//员工票
    public static final String IC_CARD_TYPE_OCT = "6";//一卡通
    public static final String IC_CARD_TYPE_TCT = "40";//乘次票
    public static final String IC_CARD_TYPE_UNIQ = "7";//异型票
    public static final String IC_CARD_TYPE_PHONE = "8";//手机票
    
    public static final String IC_CARD_TYPE_CT = "10";//纪念票

    /**
     * 票卡子类型
     *
     */
//    public static final String IC_SUB_CARD_TYPE_MULDAYS = "41";//乘次票 多日票
    /**
     * 需按逻辑卡号有效位计算的卡类型 单乘票、储值票、员工票、乘次票
     */
    public static final String[] IC_CARD_TYPE_LOGICAL_EFFECT = {
        IC_CARD_TYPE_SJT, IC_CARD_TYPE_SVT, IC_CARD_TYPE_EMP, IC_CARD_TYPE_TCT
    };
    /**
     * 需按逻辑卡号出库的卡类型 储值票、员工票、乘次票
     */
//    public static final String[] IC_CARD_TYPE_LOGICAL = {
//        IC_CARD_TYPE_SVT, IC_CARD_TYPE_EMP, IC_CARD_TYPE_OCT, IC_CARD_TYPE_PAPER, IC_CARD_TYPE_BILL, IC_CARD_TYPE_TCT, IC_CARD_TYPE_UNIQ, IC_CARD_TYPE_PHONE
//    };
    /**
     * 借票归还标志
     */
    public static final String RETURN_FLAG_NOT = "0";//归还中
    public static final String RETURN_FLAG_YES = "1";//已全部归还
    
    //乘次票限制模式
    public static final String MODE_LIMIT_NONE = "000";
    public static final String MODE_LIMIT_ENTRY = "001";
    public static final String MODE_LIMIT_OUT = "002";
    public static final String MODE_LIMIT_ENTRY_OUT = "003";
    /**
     * 应急票标志
     *
     */
//    public static final String FLAGE_EMERGENT_TICKET_NONE = "0";//非应急票
//    public static final String FLAGE_EMERGENT_TICKET = "1";//应急票
    /**
     * 配票发送标志
     */
    public static final String FLAG_SENDED = "1";//已发送
    public static final String FLAG_SENDED_NO = "0";//未发送
    /**
     * 空白卡订单 票卡大类
     */
    public static final String CARD_LARGE_TYPE_SJT = "1";//单程票
    public static final String CARD_LARGE_TYPE_SVT = "2";//储值票
    public static final String CARD_LARGE_TYPE_PT = "3";//手机票

    
    public static final String DEFAULT_VALUE_VALID_DATE = "1970-01-01";
    
    //年标志
    public static final int FLAG_YEAR = 1;
    //月标志
    public static final int FLAG_MONTH = 2;
    //日期标志
    public static final int FLAG_DATE = 3;
    //周标志
    public static final int FLAG_WEEK = 4;
    // add by zhongzq 20180626
    public static final String RECORD_NUM_ZREO = "0";
    
    //生产厂商 HCE厂商 定义
    public static final String PRODUCE_FACTORY_CODE_METRO_HCE = "80";//80:地铁HCE
    public static final String PRODUCE_FACTORY_CODE_BANK_HCE = "81";//81:银行HCE
}
