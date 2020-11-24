/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.constant;

/**
 *
 * @author hejj
 */
public class FrameSysCfgConstant {

    /**
     * 系统配置的键
     */
    public static String KEY_PATH_TRX = "path.trx";//交易文件目录
    public static String KEY_PATH_TRX_HIS = "path.trx.his";//正常交易文件历史目录
    public static String KEY_PATH_TRX_ERROR = "path.trx.error";//异常交易文件历史目录
    public static String KEY_PATH_BCP = "path.bcp";//BCP的交易文件目录
    public static String KEY_PATH_BCP_CTL = "path.bcp.ctl";//BCP的控制文件目录
    public static String KEY_PATH_BCP_LOG = "path.bcp.log";//BCP的日志文件目录
    public static String KEY_PATH_BCP_BAD = "path.bcp.bad";//BCP的错误记录日志文件目录
    public static String KEY_PATH_AUDIT = "path.audit";//审计文件目录
    public static String KEY_PATH_BASE_HOME_APP = "path.base.home.app";//应用主目录
    public static String KEY_PATH_BASE_HOME_BUSINESS_WORK = "path.base.home.business.work";//业务工作目录
    public static String KEY_PATH_BASE_HOME_BUSINESS_ARCH = "path.base.home.business.archive";//业务归档目录
    /**
     * tac校验相关
     */
    public static String KEY_TAC_SOCKET_SERVER_PRODUCT = "tac.socket.server.product";//tac校验正式密钥加密机地址
    public static String KEY_TAC_SOCKET_PORT_PRODUCT = "tac.socket.port.product";//tac校验正式密钥加密机端口
    public static String KEY_TAC_SOCKET_SERVER_TEST = "tac.socket.server.test";//tac校验测试密钥加密机地址
    public static String KEY_TAC_SOCKET_PORT_TEST = "tac.socket.port.test";//tac校验测试密钥加密机端口
    public static String KEY_TAC_SOCKET_TIMEOUT = "tac.socket.timeout";//tac校验加密机连接超时
    public static String KEY_TAC_KEY_ALG = "tac.key.algorithm";//tac校验密钥算法
    public static String KEY_TAC_KEY_VER = "tac.key.ver";//tac校验密钥版本
    public static String KEY_TAC_KEY_GRP = "tac.key.grp";//tac校验密钥组
    public static String KEY_TAC_KEY_ID = "tac.key.id";//tac校验密钥索引

    public static String KEY_TAC_KEY_TEST_FLAG = "tac.key.test.flag";//tac生成使用测试密钥还是正式密钥 0：正式密钥 1：测试密钥
    public static String KEY_TAC_IDX_TEST_CPU = "tac.key.idx.test.cpu";//cpu卡测试密钥索引
    public static String KEY_TAC_IDX_TEST_M1 = "tac.key.idx.test.m1";//m1或单程票卡测试密钥索引
    public static String KEY_TAC_IDX_PRODUCT_CPU = "tac.key.idx.product.cpu";//cpu卡正式密钥索引
    public static String KEY_TAC_IDX_PRODUCT_M1 = "tac.key.idx.product.m1";//cpu卡正式密钥索引

    /**
     * 系统处理的键
     */
    public static String KEY_SYS_SETTLE_TIME = "sys.settle.time";//系统开始结算时间
    public static String KEY_SYS_SETTLE_TIME_DOWNLOAD = "sys.settle.time.download";//系统下发审计文件开始时间
    public static String KEY_SYS_SQUADDAY = "sys.squadday";//系统运营日
    public static String KEY_FLOW_INTERVAL = "sys.flow.interval";//清算流程整体控制时间间隔
    public static String KEY_FLOW_INTERVAL_FILE = "sys.flow.interval.file";//清算流程文件处理时间间隔
    public static String KEY_FLOW_BUS_CONTROL_TRX = "sys.flow.bus.control.trx";//控制是否处理与公交交互的交易数据
    public static String KEY_FLOW_BUS_CONTROL_SETTLE = "sys.flow.bus.control.settle";//控制是否处理与公交交互的结算数据
    public static String KEY_FLOW_CONTROL_MANU = "sys.flow.control.manu";//控制是否人工处理
    public static String KEY_FLOW_CONTROL_PRESETTLE_ONLY = "sys.flow.control.presettle.only";//控制是否仅预处理
    /**
     * JMS配置的键
     */
    public static String KEY_JMS_URL = "jms.url";//JMS连接ULR
    public static String KEY_JMS_CONTEXT_FACTORY = "jms.context.factory";//JMS语境工厂
    public static String KEY_JMS_CONNECT_FACTORY = "jms.connect.factory";//JMS连接工厂
    public static String KEY_JMS_QUEUE = "jms.queue";//JMS队列
    /**
     * LCC对账
     */
    public static String KEY_LCC_DOWNLOAD_PATH = "path.audit.file.lcc";//下发的LCC审计文件目录
    /**
     * 公交IC卡数据导出、返回相关
     */
    //交易导出
    public static String KEY_OCT_EXPORT_TRX_PATH = "path.oct.export.trx.file";//公交IC卡交易导出文件目录
    public static String KEY_OCT_EXPORT_TRX_PATH_ZIP = "path.oct.export.trx.zip";//公交IC卡交易导出压缩文件目录
    public static String KEY_OCT_EXPORT_TRX_HIS_PATH = "path.oct.export.trx.his";//公交IC卡交易导出历史文件目录
    public static String KEY_OCT_EXPORT_TRX_ERR_PATH = "path.oct.export.trx.error";//公交IC卡交易导出错误文件目录
    //结算导出
    public static String KEY_OCT_EXPORT_SETTLE_PATH = "path.oct.export.settle.file";//结算导出文件目录
    public static String KEY_OCT_EXPORT_SETTLE_PATH_ZIP = "path.oct.export.settle.zip";//结算导出压缩文件目录
    public static String KEY_OCT_EXPORT_SETTLE_HIS_PATH = "path.oct.export.settle.his";//结算导出历史文件目录
    public static String KEY_OCT_EXPORT_SETTLE_ERR_PATH = "path.oct.export.settle.error";//结算导出错误文件目录

    //交易导入
    public static String KEY_OCT_IMPORT_TRX_PATH = "path.oct.import.trx.file";//公交上传的交易目录
    public static String KEY_OCT_IMPORT_TRX_PATH_ZIP = "path.oct.import.trx.zip";//公交上传的交易压缩目录  
    public static String KEY_OCT_IMPORT_TRX_PATH_HIS = "path.oct.import.trx.his";//公交上传的交易历史目录
    public static String KEY_OCT_IMPORT_TRX_PATH_ERR = "path.oct.import.trx.err";//公交上传的交易错误目录
    //结算导入
    public static String KEY_OCT_IMPORT_SETTLE_PATH = "path.oct.import.settle.file";//公交IC卡交易返回文件目录
    public static String KEY_OCT_IMPORT_SETTLE_PATH_ZIP = "path.oct.import.settle.zip";//公交IC卡交易返回压缩文件目录  
    public static String KEY_OCT_IMPORT_SETTLE_PATH_HIS = "path.oct.import.settle.his";//公交IC卡交易返回文件历史目录
    public static String KEY_OCT_IMPORT_SETTLE_PATH_ERR = "path.oct.import.settle.err";//公交IC卡交易返回文件错误目录

    //外部FTP目录
    public static String KEY_FILE_OCT_UPLOAD = "path.oct.upload";
    public static String KEY_FILE_OCT_DOWNLOAD = "path.oct.download";
    public static String KEY_FILE_TCC_DOWNLOAD = "path.tcc.download";//tcc历史数据下发路径 20190620
     
    //交易文件行分隔符控制
    public static String KEY_BUSINESS_FILE_DELIMIT_CONTROL = "business.file.delimit.control";//交易文件行分隔符控制0：单个分隔符 1：3个分隔符

    //手机支付业务控制
    public static String KEY_BUSINESS_MOBILE_CONTROL = "business.mobile.control";//1：启用 0：不启用
    public static String KEY_MOBILE_PATH_MTX = "path.audit.file.mobile.mtx";//下发手机支付平台的交易文件目录
    //消费下发控制：1：启用 0：不启用 20160324  modified by hejj
    public static String KEY_BUSINESS_MOBILE_CONTROL_TRX_DOWNLOAD = "business.mobile.control.trx.download";
    public static String KEY_MOBILE_PATH_EXPORT_TRX_HIS = "path.mobile.export.trx.his";//导出（手机支付消费交易数据）历史
    public static String KEY_MOBILE_PATH_EXPORT_TRX_ERR = "path.mobile.export.trx.error";//导出（手机支付消费交易数据）错误
    public static String KEY_MOBILE_PATH_EXPORT_TRX_FILE = "path.mobile.export.trx.file";//导出（手机支付消费交易数据）文件生成临时目录
    
    
    
    
    

    //互联网支付业务控制
    public static String KEY_BUSINESS_NETPAID_CONTROL = "business.netpaid.control";//1：启用 0：不启用
    
     //二维码平台业务控制 20190614
    public static String KEY_BUSINESS_QRCODE_CONTROL = "business.qrcode.control";//1：启用 0：不启用
    //地铁二维码平台业务控制 20200706
    public static String KEY_BUSINESS_QRCODE_MTR_CONTROL = "business.qrcode.mtr.control";//1：启用 0：不启用
    
     //tcc业务控制 20190620
    public static String KEY_BUSINESS_TCC_CONTROL = "business.tcc.control";//1：启用 0：不启用
    

    //文件处理时间与生成时间间隔
    public static String KEY_FILE_PROCESSED_BEFORE_TIME = "file.processed.before.time";

    //公交文件上传最迟时间点
    //公交上传交易最迟时间点；0：不限制，清算一直等待 其他：到点，清算跳过导入，强制进行下一步处理
    public static String KEY_SYS_SETTLE_OCT_IMPORT_TRX_LIMIT_TIME = "sys.settle.oct.import.trx.limit.time";
    //公交上传结算结果最迟时间点；0：不限制，清算一直等待 其他：到点，清算跳过导入，强制进行下一步处理
    public static String KEY_SYS_SETTLE_OCT_IMPORT_SETTLE_LIMIT_TIME = "sys.settle.oct.import.settle.limit.time";

    //批次文件数量限制 
    public static String KEY_SYS_SETTLE_FILE_ONCE_LIMIT_FLAG = "sys.settle.file.once.limit.flag";
    public static String KEY_SYS_SETTLE_FILE_ONCE_LIMIT_NUMBER = "sys.settle.file.once.limit.number";
    
    //文件是否下发控制
    public static String KEY_SYS_FLOW_CTR_DOWNLOAD_BLACKLIST = "sys.flow.ctr.download.blacklist";
    public static String KEY_SYS_FLOW_CTR_DOWNLOAD_AUDITFILE = "sys.flow.ctr.download.auditfile";
    public static String KEY_SYS_FLOW_CTR_DOWNLOAD_AUDITLCC = "sys.flow.ctr.download.auditlcc";
    public static String KEY_SYS_FLOW_CTR_DOWNLOAD_AUDITLCC_MOBILE = "sys.flow.ctr.download.auditlcc.mobile";
    public static String KEY_SYS_FLOW_CTR_EXP_OCT_TRX = "sys.flow.ctr.export.oct.trx";

}
