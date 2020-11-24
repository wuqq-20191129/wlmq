/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.env;

/**
 * 同步常量
 * 
 * @author lenovo
 */
public class SynLockConstant {

    //制卡同步常量
    public final static Object SYN_WRITE_CARD_LOCK = new Object(); //写卡锁
    public final static Object SYN_ES_CARD_SITE_LOCK = new Object();//卡位锁
    public final static Object SYN_PAUSE_LOCK = new Object();//暂停锁
    public final static Object SYN_STOP_LOCK = new Object();//停止锁
    public final static Object SYN_FINISH_LOCK = new Object();//完成锁
    public final static Object SYN_FULL_LOCK = new Object();//满锁
    public final static Object SYN_MAKE_CARD_LOCK = new Object();//制卡锁
    public final static Object SYN_SEND_NUM_LOCK = new Object();//发卡数量锁
    public final static Object SYN_WRITING_FILE_LOCK = new Object();//写文件锁
    public final static Object SYN_RUN_STATE_LOCK = new Object();//运行状态锁
    
    public final static Object SYN_WIN_EXIT_LOCK = new Object();//窗口退出锁
    
    public final static Object SYN_NOTICE_MSG_FILE_LOCK = new Object();//订单通知锁
    public final static Object SYN_ISSUE_CARD_LOCK = new Object();//手工发卡使用
    
}
