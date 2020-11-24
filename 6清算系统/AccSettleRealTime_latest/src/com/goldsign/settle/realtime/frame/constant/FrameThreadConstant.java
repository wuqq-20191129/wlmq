/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.constant;

/**
 *
 * @author hejj
 */
public class FrameThreadConstant {
     /**
     * ************************
     */
    /**
     * ******线程池************************
     */
    //交易文件处理
    public static int threadSleepTimeForFile = 100;
    public static int threadPriorityForFile = 5;
    public static int maxThreadNumberForFile = 5;
    public static int maxSearchNumForFile = 2;
    public static int readThreadPriorityAddForFile = 0;
    public static String unHanledMsgLogDirForFile = "";
    public static int threadBufferCapacityForFile = 1000;
    public static int threadBufferIncrementForFile = 1000;
    public static int priorityThreadBufferCapacityForFile = 1000;
    public static int priorityThreadBufferIncrementForFile = 1000;
    //public static String threadPoolIdForFile = "1";
    //public static String threadPoolNameForFile = "文件处理线程池";

    //BCP文件处理
    public static int threadSleepTimeForBcp = 100;
    public static int threadPriorityForBcp = 5;
    public static int maxThreadNumberForBcp = 5;
    public static int maxSearchNumForBcp = 2;
    public static int readThreadPriorityAddForBcp = 0;
    public static String unHanledMsgLogDirForBcp = "";
    public static int threadBufferCapacityForBcp = 1000;
    public static int threadBufferIncrementForBcp = 1000;
    public static int priorityThreadBufferCapacityForBcp = 1000;
    public static int priorityThreadBufferIncrementForBcp = 1000;
    
    
     //TAC校验处理

    public static int threadSleepTimeForTac = 100;
    public static int threadPriorityForTac = 5;
    public static int maxThreadNumberForTac = 5;
    public static int maxSearchNumForTac = 2;
    public static int readThreadPriorityAddForTac = 0;
    public static String unHanledMsgLogDirForTac = "";
    public static int threadBufferCapacityForTac = 1000;
    public static int threadBufferIncrementForTac = 1000;
    public static int priorityThreadBufferCapacityForTac = 1000;
    public static int priorityThreadBufferIncrementForTac = 1000;
    
    
     //其他文件处理

    public static int threadSleepTimeForOther = 100;
    public static int threadPriorityForOther = 5;
    public static int maxThreadNumberForOther = 5;
    public static int maxSearchNumForOther = 2;
    public static int readThreadPriorityAddForOther = 0;
    public static String unHanledMsgLogDirForOther = "";
    public static int threadBufferCapacityForOther = 1000;
    public static int threadBufferIncrementForOther = 1000;
    public static int priorityThreadBufferCapacityForOther = 1000;
    public static int priorityThreadBufferIncrementForOther = 1000;
    /**
     * ************************************
     */
    /**
     * 线程池标识
     */
    public static String THREAD_POOL_FILE_ID="1";//交易文件处理线程池标识
    public static String THREAD_POOL_FILE_NAME="交易文件处理线程池";//交易文件处理线程池标识
    public static String THREAD_POOL_BCP_ID="2";//交易文件BCP线程池标识
    public static String THREAD_POOL_BCP_NAME="BCP文件处理线程池";//交易文件BCP线程池标识名称
    public static String THREAD_POOL_TAC_ID="3";//TAC校验线程池标识
    public static String THREAD_POOL_TAC_NAME="TAC校验处理线程池";//TAC校验处理线程池
    public static String THREAD_POOL_OTHER_ID="4";//其他处理线程池标识
    public static String THREAD_POOL_OTHER_NAME="收益、审计、寄存器处理线程池";//其他处理线程池
    public static String THREAD_POOL_FILE_MOBILE_ID="5";//手机支付交易文件处理线程池标识
    public static String THREAD_POOL_FILE_MOBILE_NAME="手机支付交易文件处理线程池";//手机支付交易文件处理线程池标识
    public static String THREAD_POOL_OTHER_MOBILE_ID="6";//手机支付其他处理线程池标识
    public static String THREAD_POOL_OTHER_MOBILE_NAME="手机支付对账处理线程池";//其他处理线程池
    public static String THREAD_POOL_FILE_NETPAID_ID="7";//互联网支付交易文件处理线程池标识
    public static String THREAD_POOL_FILE_NETPAID_NAME="互联网支付交易文件处理线程池";//手机支付交易文件处理线程池标识
    public static String THREAD_POOL_OTHER_NETPAID_ID="8";//互联网支付其他处理线程池标识
    public static String THREAD_POOL_OTHER_NETPAID_NAME="互联网支付对账处理线程池";//其他处理线程池
    
    public static String THREAD_POOL_FILE_QRCODE_ID="9";//二维码平台交易文件处理线程池标识
    public static String THREAD_POOL_FILE_QRCODE_NAME="二维码平台交易文件处理线程池";//手机支付交易文件处理线程池标识
    public static String THREAD_POOL_OTHER_QRCODE_ID="10";//二维码平台其他处理线程池标识
    public static String THREAD_POOL_OTHER_QRCODE_NAME="二维码平台对账处理线程池";//其他处理线程池
    
    public static String[] THREAD_POOL_IDS ={THREAD_POOL_FILE_ID,THREAD_POOL_BCP_ID,
                                             THREAD_POOL_TAC_ID,THREAD_POOL_OTHER_ID,
                                             THREAD_POOL_FILE_MOBILE_ID,THREAD_POOL_OTHER_MOBILE_ID,
                                             THREAD_POOL_FILE_NETPAID_ID,THREAD_POOL_OTHER_NETPAID_ID,
                                             THREAD_POOL_FILE_QRCODE_ID,THREAD_POOL_OTHER_QRCODE_ID,
                                             
                                              };
     public static String[] THREAD_POOL_NAMES ={THREAD_POOL_FILE_NAME,THREAD_POOL_BCP_NAME,
                                             THREAD_POOL_TAC_NAME,THREAD_POOL_OTHER_NAME,
                                             THREAD_POOL_FILE_MOBILE_NAME,
                                             THREAD_POOL_OTHER_MOBILE_NAME,
                                             THREAD_POOL_FILE_NETPAID_NAME,
                                             THREAD_POOL_OTHER_NETPAID_NAME,
                                             THREAD_POOL_FILE_QRCODE_NAME,
                                             THREAD_POOL_OTHER_QRCODE_NAME
                                              };
    
    /**
     * 线程处理类
     */
    public static String CLASS_HANDLE_FILE="com.goldsign.settle.realtime.frame.handler.HandlerTrx";//交易文件处理线程线程类
    public static String CLASS_HANDLE_BCP="com.goldsign.settle.realtime.frame.handler.HandlerBcp";//交易文件Bcp线程类
    public static String CLASS_HANDLE_TAC="com.goldsign.settle.realtime.frame.handler.HandlerTac";//TAC处理线程类
    public static String CLASS_HANDLE_OTHER_PREFIX="com.goldsign.settle.realtime.frame.handler.Handler";//其他处理线程类前缀
    public static String CLASS_HANDLE_FILE_MOBILE="com.goldsign.settle.realtime.frame.handler.HandlerTrxMobile";//手机支付交易文件处理线程线程类
    public static String CLASS_HANDLE_OTHER_MOBILE_PREFIX="com.goldsign.settle.realtime.frame.handler.HandlerMobile";//手机支付其他处理线程类前缀
     public static String CLASS_HANDLE_FILE_QRCODE="com.goldsign.settle.realtime.frame.handler.HandlerTrxQrCode";//二维码平台交易文件处理线程线程类
    public static String CLASS_HANDLE_OTHER_QRCODE_PREFIX="com.goldsign.settle.realtime.frame.handler.HandlerQrCode";//二维码平台其他处理线程类前缀
    //added by hejj 20161124
    public static String CLASS_HANDLE_FILE_NETPAID="com.goldsign.settle.realtime.frame.handler.HandlerTrxNetPaid";//互联网支付交易文件处理线程线程类
    public static String CLASS_HANDLE_OTHER_NETPAID_PREFIX="com.goldsign.settle.realtime.frame.handler.HandlerNetPaid";//互联网支付其他处理线程类前缀
    
    
    //手机支付交易文件处理
    public static int threadSleepTimeForFileMobile = 100;
    public static int threadPriorityForFileMobile = 5;
    public static int maxThreadNumberForFileMobile = 5;
    public static int maxSearchNumForFileMobile = 2;
    public static int readThreadPriorityAddForFileMobile = 0;
    public static String unHanledMsgLogDirForFileMobile = "";
    public static int threadBufferCapacityForFileMobile = 1000;
    public static int threadBufferIncrementForFileMobile = 1000;
    public static int priorityThreadBufferCapacityForFileMobile = 1000;
    public static int priorityThreadBufferIncrementForFileMobile = 1000;
    
    
     //手机支付其他文件处理

    public static int threadSleepTimeForOtherMobile = 100;
    public static int threadPriorityForOtherMobile = 5;
    public static int maxThreadNumberForOtherMobile = 5;
    public static int maxSearchNumForOtherMobile = 2;
    public static int readThreadPriorityAddForOtherMobile = 0;
    public static String unHanledMsgLogDirForOtherMobile = "";
    public static int threadBufferCapacityForOtherMobile = 1000;
    public static int threadBufferIncrementForOtherMobile = 1000;
    public static int priorityThreadBufferCapacityForOtherMobile = 1000;
    public static int priorityThreadBufferIncrementForOtherMobile = 1000;
    
     //二维码平台交易文件处理
    public static int threadSleepTimeForFileQrCode = 100;
    public static int threadPriorityForFileQrCode = 5;
    public static int maxThreadNumberForFileQrCode = 5;
    public static int maxSearchNumForFileQrCode = 2;
    public static int readThreadPriorityAddForFileQrCode = 0;
    public static String unHanledMsgLogDirForFileQrCode = "";
    public static int threadBufferCapacityForFileQrCode = 1000;
    public static int threadBufferIncrementForFileQrCode = 1000;
    public static int priorityThreadBufferCapacityForFileQrCode = 1000;
    public static int priorityThreadBufferIncrementForFileQrCode = 1000;
    
    
     //二维码平台其他文件处理

    public static int threadSleepTimeForOtherQrCode = 100;
    public static int threadPriorityForOtherQrCode = 5;
    public static int maxThreadNumberForOtherQrCode = 5;
    public static int maxSearchNumForOtherQrCode = 2;
    public static int readThreadPriorityAddForOtherQrCode = 0;
    public static String unHanledMsgLogDirForOtherQrCode = "";
    public static int threadBufferCapacityForOtherQrCode = 1000;
    public static int threadBufferIncrementForOtherQrCode = 1000;
    public static int priorityThreadBufferCapacityForOtherQrCode = 1000;
    public static int priorityThreadBufferIncrementForOtherQrCode = 1000;
    
    
    
     //互联网支付交易文件处理
    public static int threadSleepTimeForFileNetPaid = 100;
    public static int threadPriorityForFileNetPaid = 5;
    public static int maxThreadNumberForFileNetPaid = 5;
    public static int maxSearchNumForFileNetPaid = 2;
    public static int readThreadPriorityAddForFileNetPaid = 0;
    public static String unHanledMsgLogDirForFileNetPaid = "";
    public static int threadBufferCapacityForFileNetPaid = 1000;
    public static int threadBufferIncrementForFileNetPaid = 1000;
    public static int priorityThreadBufferCapacityForFileNetPaid = 1000;
    public static int priorityThreadBufferIncrementForFileNetPaid = 1000;
    
    
     //互联网支付其他文件处理

    public static int threadSleepTimeForOtherNetPaid = 100;
    public static int threadPriorityForOtherNetPaid = 5;
    public static int maxThreadNumberForOtherNetPaid = 5;
    public static int maxSearchNumForOtherNetPaid = 2;
    public static int readThreadPriorityAddForOtherNetPaid = 0;
    public static String unHanledMsgLogDirForOtherNetPaid = "";
    public static int threadBufferCapacityForOtherNetPaid = 1000;
    public static int threadBufferIncrementForOtherNetPaid = 1000;
    public static int priorityThreadBufferCapacityForOtherNetPaid = 1000;
    public static int priorityThreadBufferIncrementForOtherNetPaid = 1000;
    
    
}
