/*
 * 文件名：SamCardConstant
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.acc.frame.constant;


/*
 * 业务级别常量
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-8-8
 */

public class SamCardConstant {
    
    //返回页面结果集名称常量
    public static final String RESULT_NAME_PUB_FLAG = "recordFlags";//单据状态
    public static final String RESULT_NAME_IS_BAD = "isBads";//是否损坏
    public static final String RESULT_NAME_SAM_TYPE = "samTypes";//卡类型
    public static final String RESULT_NAME_PRODUCE_ORDER = "produceOrders";//生产工作单
    public static final String RESULT_NAME_EMPTY_SAM_IN = "emptySamIns";//空白卡入库单
    public static final String RESULT_NAME_SAM_LINE_ES = "samLineES";//线路（ES）
    public static final String RESULT_NAME_ISSUE_SAM_OUT = "issueSamOuts";//卡发行出库单
    public static final String RESULT_NAME_MAKE_IN = "produceSamIns";//卡制作入库单
    public static final String RESULT_NAME_RECYCLE_IN = "recycleSamIns";//卡回收入库单
    public static final String RESULT_NAME_DISTRIBUTE_OUT = "distributeSamOuts";//卡分发出库单
    public static final String RESULT_NAME_ORDER_DETAIL = "orderDetails";//订单详细逻辑卡号
    
    public static final String RESULT_NAME_SAM_TYPE_OP = "samTypesOP";//卡类型(运营中心返回数据)
    
    public static final String RESULT_NAME_PRODUCE_TYPE = "produceTypes";//产品类型
    public static final String RESULT_NAME_STOCK_STATE = "stockStates";//库存状态
    public static final String RESULT_NAME_IS_IN_STOCK = "isInstocks";//是否在库
    public static final String RESULT_NAME_SAM_SYS_TYPE = "sysTypes";//系统类型
    public static final String RESULT_NAME_SAM_OPER_TYPE = "operTypes";//操作类型
    //end 返回页面结果集名称常量
    
    //库单类型代码
    public static final String ORDER_TYPE_CODE_EMPTY_IN = "KBRK";//空白卡入库单号前缀（新购卡）
    public static final String ORDER_TYPE_CODE_PRODUCE = "SCDD";//生产工作单号前缀（卡制作）
    public static final String ORDER_TYPE_CODE_RESET = "CZDD";//生产工作单号前缀（卡重置）
    public static final String ORDER_TYPE_CODE_ISSUE_OUT = "KFCK";//卡发行出库单号前缀
    public static final String ORDER_TYPE_CODE_MAKE_IN = "KZRK";//卡制作入库单号前缀
    public static final String ORDER_TYPE_CODE_RECYCLE_IN = "HSRK";//卡回收入库单号前缀
    public static final String ORDER_TYPE_CODE_DISTRIBUTE_OUT = "FFCK";//卡分发出库单号前缀
    //end 库单类型代码
    
    //状态类型 对应表 ic_sam_pub_flag
    public static final int RECORD_FLAG_BILL_TYPE = 0;//单据状态
    public static final int RECORD_FLAG_BILL_FINISH = 1;//订单完成状态
    public static final int RECORD_FLAG_ES_LINE = 2;//线路ES类型
    public static final int RECORD_FLAG_IN_STOCK_STATE = 3;//回库状态
    public static final int RECORD_FLAG_SAM_CARD_IS_BAD = 4;//是否损坏
    public static final int RECORD_FLAG_SAM_CARD_PRODUCE_TYPE = 5;//产品类型
    public static final int RECORD_FLAG_SAM_CARD_STOCK_STATE = 6;//库存状态
    public static final int RECORD_FLAG_SAM_CARD_IS_INSTOCK = 7;//是否在库
    //end 状态类型
    
    //单据状态
    public static final String RECORD_FLAG_UNAUDITED = "0";//未审核
    public static final String RECORD_FLAG_AUDITED = "1";//审核
    //end 单据状态
    
    //订单完成状态 回库状态(0:全未回库,1:部分回库,2:全已回库)
    public static final String RECORD_FLAG_ALL_UNFINISHED = "0";//0:全未完成
    public static final String RECORD_FLAG_SOME_UNFINISHED = "1";//1:部分完成
    public static final String RECORD_FLAG_ALL_FINISHED = "2";//2:全已完成
    //end 订单完成状态 回库状态
    
    //产品类型
    public static final String PRODUCE_TYPE_EMPTY = "00";//00:空白卡
    public static final String PRODUCE_TYPE_PRODUCT = "01";//01:成品卡
    //end 产品类型
    
    //库存状态
    public static final String STOCK_STATE_EMPTY_IN = "00";//00:空白卡入库
    public static final String STOCK_STATE_PRODUCE_ORDER = "01";//01:生产计划单
    public static final String STOCK_STATE_ISSUE_OUT = "02";//02:卡发行出库
    public static final String STOCK_STATE_PRODUCT_IN = "03";//03:卡制作卡入库
    public static final String STOCK_STATE_DISTR_OUT = "04";//04:卡分发出库
    public static final String STOCK_STATE_RECYCLE_IN = "05";//05:卡回收入库
    public static final String STOCK_STATE_MAKED= "06";//06:卡制作
    //end 库存状态
    
    //是否在库
    public static final String STOCK_STATE_OUT = "0";//0:出库
    public static final String STOCK_STATE_IN = "1";//1:在库
    //end 是否在库
    
    //是否损坏
    public static final String SAM_CARD_STATE_BAD = "0";//0:坏卡
    public static final String SAM_CARD_STATE_OK = "1";//1:好卡
    //end 是否损坏
    
    //EmptySamInAction 提示标识（int）
    public static final int EMPTY_SAM_IN_TIP_SUCCESS = 0;//可增加，成功添加
    public static final int EMPTY_SAM_IN_TIP_SAM_TYPE = 1;//sam卡类型不存在
    public static final int EMPTY_SAM_IN_TIP_LOGIC_NO = 2;//订单逻辑卡号段中存在已经添加逻辑卡号
    public static final int EMPTY_SAM_IN_TIP_LOGIC_NUM = 3;//订单逻辑卡号段超出最大值99999
    //end EmptySamInAction 提示标识（int）
    
    public static final int MAX_SAM_CARD_LOGIC_NUM = 9999999;//逻辑卡号后7位最大数值。
    
    //操作日志 操作类型
    public static final String OPRT_LOG_OPER_TYPE_READY_WRITE_CARD_NAME = "准备写卡";
    public static final String OPRT_LOG_OPER_TYPE_WRITE_DB_NAME = "写库";
    
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDD_X = "yyyy/MM/dd";
    
}
