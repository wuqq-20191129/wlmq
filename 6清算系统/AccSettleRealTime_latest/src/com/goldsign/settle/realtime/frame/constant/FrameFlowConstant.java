/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.constant;

/**
 *
 * @author hejj
 */
public class FrameFlowConstant {

    /**
     * 流程控制相关
     */
    //文件处理相关
    public static String FLOW_ID_FILE_TRX = "101";//交易文件
    public static String FLOW_ID_FILE_OTHER = "102";//收益文件、寄存器文件、审计文件
    //审计文件下发标志
    public static boolean IS_TIME_DOWNLOAD = false;//下发审计文件控制标识
    //当天交易文件、收益文件处理完成标志
    public static boolean IS_FINISH_FILE_HANDLED = false;
    //公交IC卡结算返回标志
    public static boolean IS_TIME_OCT_IMPORT_SETTLE = false;//公交IC卡结算返回
    // public static boolean IS_TIME_OCT_IMPORT_TRX = false;//公交IC卡交易数据返回
    //公交交易数据上传完成标志
    public static boolean IS_TIME_OCT_IMPORT_TRX = false;//公交交易数据上传完成标志
    //功能完成控制
    public static String FLAG_FINISH = "1";//完成
    public static String FLAG_FINISH_NOT = "0";//没有完成
    public static String FLAG_OK = "00";//没有错误
    /*
     * 结算各步骤
     */
    public static String STEP_FTP_FILE_DOWN = "10";//FTP审计文件下发
    public static String STEP_FILE_PROCESS = "13";//文件导入及校验
    public static String STEP_FILE_EXPORT_OCT_TRX = "20";//外部系统数据导出
    public static String STEP_FILE_IMPORT_OCT_TRX = "21";//外部系统数据导入
    public static String STEP_ERR_FILE_DOWN = "80";//错误审计、黑名单下发
    public static String STEP_FILE_EXPORT_OCT_SETTLE = "81";//储值卡结算数据返回
    public static String STEP_FILE_IMPORT_OCT_SETTLE = "83";//公交IC卡结算数据返回
    //存储过程调用
    public static String STEP_SETTLE_DATA_COMBINE = "30";//数据结算（合并队列）
    public static String STEP_SETTLE_FOR_OPERATOR = "33";//数据结算（运营商）
    public static String STEP_SETTLE_FOR_OPERATOR_MAX = "36";//数据结算（最大运营商）
    public static String STEP_SETTLE_DATA_TO_STAT = "39";//数据结算（导中间表）
    public static String STEP_SETTLE_EXTERNAL = "42";//外部系统对账
    public static String STEP_SETTLE_FLOW_OD = "45";//客流、OD统计
    public static String STEP_SETTLE_INCOME = "48";//收益统计
    public static String STEP_SETTLE_LCC = "51";//LCC对账
    public static String STEP_SETTLE_MOBILE = "52";//移动支付结算 add by hejj 20160106
    public static String STEP_SETTLE_NETPAID = "53";//互联网支付结算 add by hejj 20161129
    public static String STEP_SETTLE_AUD_EXCEP = "54";//审计、异常统计
    public static String STEP_SETTLE_BALANCE = "57";//余额统计分析
    public static String STEP_SETTLE_SAM = "60";//SAM断号、重号分析
    public static String STEP_SETTLE_REG = "63";//寄存器统计
    public static String STEP_SETTLE_OTHER = "66";//其他处理
    public static String STEP_SETTLE_HIS = "69";//数据导历史
    //移动支付结算 add by hejj 20160106
    //互联网支付结算 add by hejj 20161129
    //删除外部系统对帐 add by hejj 20170929
    public static String[] SETTLE_STEPS = {STEP_SETTLE_DATA_COMBINE, STEP_SETTLE_FOR_OPERATOR,
        STEP_SETTLE_FOR_OPERATOR_MAX, STEP_SETTLE_DATA_TO_STAT,  STEP_SETTLE_FLOW_OD,
        STEP_SETTLE_INCOME, STEP_SETTLE_LCC, STEP_SETTLE_MOBILE, STEP_SETTLE_NETPAID, STEP_SETTLE_AUD_EXCEP, STEP_SETTLE_BALANCE,
        STEP_SETTLE_SAM, STEP_SETTLE_REG, STEP_SETTLE_OTHER, STEP_SETTLE_HIS
    };
    /**
     * 结算步骤名称
     */
    public static String STEP_FTP_FILE_DOWN_NAME = "FTP审计文件下发";//FTP审计文件下发
    public static String STEP_FILE_PROCESS_NAME = "文件导入及校验";//文件导入及校验
    public static String STEP_FILE_EXPORT_OCT_TRX_NAME = "外部系统数据导出";//外部系统数据导出
    public static String STEP_ERR_FILE_DOWN_NAME = "错误审计、黑名单下发、LCC对账结果下发";//错误审计、黑名单下发
    public static String STEP_FILE_IMPORT_OCT_TRX_NAME = "公交IC卡清算返回导入";
    public static String STEP_SETTLE_DATA_COMBINE_NAME = "数据结算（合并队列）";//数据结算（合并队列）
    public static String STEP_SETTLE_FOR_OPERATOR_NAME = "数据结算（运营商）";//数据结算（运营商）
    public static String STEP_SETTLE_FOR_OPERATOR_MAX_NAME = "数据结算（最大运营商）";//数据结算（最大运营商）
    public static String STEP_SETTLE_DATA_TO_STAT_NAME = "数据结算（导中间表）";//数据结算（导中间表）
    public static String STEP_SETTLE_EXTERNAL_NAME = "外部系统对账";//外部系统对账
    public static String STEP_SETTLE_FLOW_OD_NAME = "客流、OD统计";//客流、OD统计
    public static String STEP_SETTLE_INCOME_NAME = "收益统计";//收益统计
    public static String STEP_SETTLE_LCC_NAME = "LCC对账";//LCC对账
    public static String STEP_SETTLE_MOBILE_NAME = "手机支付结算";
    public static String STEP_SETTLE_NETPAID_NAME = "互联网支付结算";
    public static String STEP_SETTLE_AUD_EXCEP_NAME = "审计、异常统计";//审计、异常统计
    public static String STEP_SETTLE_BALANCE_NAME = "余额统计分析";//余额统计分析
    public static String STEP_SETTLE_SAM_NAME = "SAM断号、重号分析";//SAM断号、重号分析
    public static String STEP_SETTLE_REG_NAME = "寄存器统计";//寄存器统计
    public static String STEP_SETTLE_OTHER_NAME = "其他处理";//其他处理
    public static String STEP_SETTLE_HIS_NAME = "数据导历史";//数据导历史
    public static String STEP_FILE_IMPORT_OCT_SETTLE_NAME = "外部储值卡消费数据导入";//外部系统数据导入
    public static String STEP_FILE_EXPORT_OCT_SETTLE_NAME = "外部储值卡结算数据导出";//储值卡结算数据返回
    public static String STEP_SETTLE = "数据结算、外部系统对账、客流、OD统计、收益统计、LCC对账、异常统计、SAM及余额统计分析、寄存器统计、其他处理、数据导历史";
    public static String[] STEP_KEYS = {STEP_FTP_FILE_DOWN,
        STEP_FILE_PROCESS,
        STEP_FILE_EXPORT_OCT_TRX,
        STEP_ERR_FILE_DOWN,
        STEP_FILE_IMPORT_OCT_SETTLE,
        STEP_SETTLE_DATA_COMBINE,
        STEP_SETTLE_FOR_OPERATOR,
        STEP_SETTLE_FOR_OPERATOR_MAX,
        STEP_SETTLE_DATA_TO_STAT,
        
        STEP_SETTLE_FLOW_OD,
        STEP_SETTLE_INCOME,
        STEP_SETTLE_LCC,
        STEP_SETTLE_MOBILE,
        STEP_SETTLE_NETPAID,
        STEP_SETTLE_AUD_EXCEP,
        STEP_SETTLE_BALANCE,
        STEP_SETTLE_SAM,
        STEP_SETTLE_REG,
        STEP_SETTLE_OTHER,
        STEP_SETTLE_HIS,
        STEP_FILE_IMPORT_OCT_TRX,
        STEP_FILE_EXPORT_OCT_SETTLE
    };
    public static String[] STEP_KEYS_NAME = {STEP_FTP_FILE_DOWN_NAME,
        STEP_FILE_PROCESS_NAME,
        STEP_FILE_EXPORT_OCT_TRX_NAME,
        STEP_ERR_FILE_DOWN_NAME,
        STEP_FILE_IMPORT_OCT_TRX_NAME,
        STEP_SETTLE_DATA_COMBINE_NAME,
        STEP_SETTLE_FOR_OPERATOR_NAME,
        STEP_SETTLE_FOR_OPERATOR_MAX_NAME,
        STEP_SETTLE_DATA_TO_STAT_NAME,
        
        STEP_SETTLE_FLOW_OD_NAME,
        STEP_SETTLE_INCOME_NAME,
        STEP_SETTLE_LCC_NAME,
        STEP_SETTLE_MOBILE_NAME,
        STEP_SETTLE_NETPAID_NAME,
        STEP_SETTLE_AUD_EXCEP_NAME,
        STEP_SETTLE_BALANCE_NAME,
        STEP_SETTLE_SAM_NAME,
        STEP_SETTLE_REG_NAME,
        STEP_SETTLE_OTHER_NAME,
        STEP_SETTLE_HIS_NAME,
        STEP_FILE_IMPORT_OCT_SETTLE_NAME,
        STEP_FILE_EXPORT_OCT_SETTLE_NAME
    };

//是否最后一次结算
    public static String FINAL_SETTLE_FLAG = "0";//
    public static String FINAL_SETTLE_YES = "1";//最后一次结算
    public static String FINAL_SETTLE_NO = "0";//非最后一次结算

    public static String getStepKeyName(String key) {
        for (int i = 0; i < STEP_KEYS.length; i++) {
            if (key.equals(STEP_KEYS[i])) {
                return STEP_KEYS_NAME[i];
            }
        }
        return "";
    }

    public static String getStepKeyNamesForSettle() {

        return STEP_SETTLE;
    }
}
