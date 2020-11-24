/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.constant;

import com.goldsign.settle.realtime.frame.vo.SynchronizedControl;

/**
 *
 * @author hejj
 */
public class FrameSynConstant {

    public static SynchronizedControl SYN_BCP_CONFIG = new SynchronizedControl();
    //审计文件下发控制
    public static final SynchronizedControl SYN_FILE_DOWNLOAD = new SynchronizedControl();
    //当天交易文件、收益文件处理完成标志控制
    public static final SynchronizedControl SYN_FILE_FINISH_HANDLED = new SynchronizedControl();
    //公交IC卡结算返回
    public static final SynchronizedControl SYN_OCT_IMPORT_SETTLE = new SynchronizedControl();
    //公交IC卡交易返回
    //public static final SynchronizedControl SYN_OCT_IMPORT_TRX = new SynchronizedControl();
     //公交IC卡结算返回
    public static final SynchronizedControl SYN_ETTLE_LOG = new SynchronizedControl();
     public static final SynchronizedControl SYN_SETTLE_DETAIL_LOG = new SynchronizedControl();
    
     //公交IC卡交易数据上传
    public static final SynchronizedControl SYN_OCT_IMPORT_TRX = new SynchronizedControl();
    
     //最后一次实时清算控制
    public static final SynchronizedControl SYN_FINAL_SETTLE = new SynchronizedControl();
     //实时清算控制
    public static final SynchronizedControl SYN_SETTLE = new SynchronizedControl();
}
