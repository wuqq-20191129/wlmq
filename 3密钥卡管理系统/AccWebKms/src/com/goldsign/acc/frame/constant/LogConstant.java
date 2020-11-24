/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.constant;

/**
 *
 * @author hejj
 */
public class LogConstant {

    public final static String OP_ADD = "1";//增加
    public final static String OP_DELETE = "2";//删除
    public final static String OP_MODIFY = "3";//修改
    public final static String OP_QUERY = "4";//查询
    public final static String OP_REPORTQUERY = "5";//报表查询
    public final static String OP_STATISTIC = "6";//报表统计
    public final static String OP_CLONE = "7";//克隆
    public final static String OP_SUBMIT = "8";//提交
    public final static String OP_IMPORT = "9";//导入
    public final static String OP_CHECK = "10";//盘点
    public final static String OP_DISTRIBUTE = "11";//分配
    public final static String OP_DOWNLOAD = "12";//下发
    public final static String OP_UPDATE = "13";//确认退款
    public final static String OP_HMD = "14";//拒绝退款
    public final static String OP_AUDIT = "15";//审核

    // 日志消息提示
    public static final String OPERATIION_SUCCESS_LOG_MESSAGE = "操作成功";
    public static final String OPERATIION_FAIL_LOG_MESSAGE = "操作失败";

    public final static String KEY_MODIFY = "modifyMessage";
    public final static String KEY_ADD = "addMessage";
    public final static String KEY_DELETE = "deleteMessage";
    public final static String KEY_CLONE = "addMessage";
    public final static String KEY_SUBMIT = "addMessage";

    public static String querySuccessMsg(int n) {
        return "成功查询" + n + "条记录！";
    }

    public static String addSuccessMsg(int n) {
        return "成功添加" + n + "条记录！";
    }

    public static String modifySuccessMsg(int n) {
        return "成功修改" + n + "条记录！";
    }

    public static String delSuccessMsg(int n) {
        return "成功删除" + n + "条记录！";
    }

    public static String auditSuccessMsg(int n) {
        return "成功审核" + n + "条记录！";
    }

    public static String submitSuccessMsg(int n, String verFlag) {
        String msg = "成功提交" + n + "条记录";

        if (verFlag != null && verFlag.equals(ParameterConstant.RECORD_FLAG_CURRENT)) {
            msg += "，已生成新的当前参数！";
        } else if (verFlag != null && verFlag.equals(ParameterConstant.RECORD_FLAG_FUTURE)) {
            msg += "，已生成新的未来参数！";
        } else {
            msg += "，已生成新的当前参数或未来参数！";
        }
        return msg;
    }

    public static String cloneSuccessMsg(int n) {
        return "成功克隆" + n + "条记录，已生成新的草稿参数！";
    }

}
