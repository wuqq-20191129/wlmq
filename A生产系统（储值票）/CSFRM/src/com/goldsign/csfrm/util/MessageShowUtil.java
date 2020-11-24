/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.csfrm.util;

import com.goldsign.csfrm.env.BaseConstant;
import java.awt.Component;
import javax.swing.JOptionPane;

/**
 * 信息提示工具类
 * 状态栏提示、弹出框提示
 * 
 * @author lenovo
 */
public class MessageShowUtil {

    public static String TIP_FONT = "<html><font size=5>";   //提示框字体
    public static String TIP_TITLE = "系统提示";   //提示标题   
    
    //
    // Option types
    //

    /** 
     * Type meaning Look and Feel should not supply any options -- only
     * use the options from the <code>JOptionPane</code>.
     */
    public static final int         DEFAULT_OPTION = -1;
    /** Type used for <code>showConfirmDialog</code>. */
    public static final int         YES_NO_OPTION = 0;
    /** Type used for <code>showConfirmDialog</code>. */
    public static final int         YES_NO_CANCEL_OPTION = 1;
    /** Type used for <code>showConfirmDialog</code>. */
    public static final int         OK_CANCEL_OPTION = 2;

    //
    // Return values.
    //
    /** Return value from class method if YES is chosen. */
    public static final int         YES_OPTION = 0;
    /** Return value from class method if NO is chosen. */
    public static final int         NO_OPTION = 1;
    /** Return value from class method if CANCEL is chosen. */
    public static final int         CANCEL_OPTION = 2;
    /** Return value form class method if OK is chosen. */
    public static final int         OK_OPTION = 0;
    /** Return value from class method if user closes window without selecting
     * anything, more than likely this should be treated as either a
     * <code>CANCEL_OPTION</code> or <code>NO_OPTION</code>. */
    public static final int         CLOSED_OPTION = -1;

    //
    // Message types. Used by the UI to determine what icon to display,
    // and possibly what behavior to give based on the type.
    //
    /** Used for error messages. */
    public static final int  ERROR_MESSAGE = 0;
    /** Used for information messages. */
    public static final int  INFORMATION_MESSAGE = 1;
    /** Used for warning messages. */
    public static final int  WARNING_MESSAGE = 2;
    /** Used for questions. */
    public static final int  QUESTION_MESSAGE = 3;
    /** No icon is used. */
    public static final int   PLAIN_MESSAGE = -1;
    
    /**
     * 结果栏提示
     * 
     * @param msg 
     */
    public static void infoOpMsg(String msg){
        BaseConstant.publicPanel.setOpResult(msg);
    }
    
    /**
     * 结果栏警告
     * 
     * @param msg 
     */
    public static void warnOpMsg(String msg){
        BaseConstant.publicPanel.setOpResultWarn(msg);
    }
    
    /**
     * 结果栏错误
     * 
     * @param msg 
     */
    public static void errorOpMsg(String msg){
        BaseConstant.publicPanel.setOpResultError(msg);
    }
    
    /**
     * 弹出框信息
     * 
     * @param msg 
     */
    public static void alertInfoMsg(String msg){
        alertInfoMsg(BaseConstant.mainFrame, msg);
    }
        
    /**
     * 弹出框信息
     * 
     * @param parentComponent
     * @param msg 
     */
    public static void alertInfoMsg(Component parentComponent, String msg){
        alertMsg(parentComponent, msg, JOptionPane.INFORMATION_MESSAGE);
    }
    
     /**
     * 弹出框警告
     * 
     * @param msg 
     */
    public static void alertWarnMsg(String msg){
        alertWarnMsg(BaseConstant.mainFrame, msg);
    }
        
    /**
     * 弹出框警告
     * 
     * @param parentComponent
     * @param msg 
     */
    public static void alertWarnMsg(Component parentComponent, String msg){
        alertMsg(parentComponent, msg, JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * 弹出框错误
     * 
     * @param msg 
     */
    public static void alertErrorMsg(String msg){
        alertErrorMsg(BaseConstant.mainFrame, msg);
    }
    
     /**
     * 弹出框错误
     * 
     * @param msg 
     */
    public static void alertErrorMsgSyn(String msg){
        alertErrorMsgSyn(BaseConstant.mainFrame, msg);
    }
        
    /**
     *  弹出框错误
     * 
     * @param parentComponent
     * @param msg 
     */
    public static void alertErrorMsg(Component parentComponent, String msg){
        alertMsg(parentComponent, msg, JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     *  弹出框错误
     * 
     * @param parentComponent
     * @param msg 
     */
    public static void alertErrorMsgSyn(Component parentComponent, String msg){
        alertMsgSyn(parentComponent, msg, JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * 弹出框
     * 
     * @param parentComponent
     * @param msg
     * @param MESSAGE 
     */
    private static void alertMsg(String msg, int MESSAGE) {
        alertMsg(BaseConstant.mainFrame, msg, MESSAGE);
    }    
    
     /**
     * 弹出框
     * 
     * @param parentComponent
     * @param msg
     * @param MESSAGE 
     */
    private static void alertMsgSyn(String msg, int MESSAGE) {
        alertMsgSyn(BaseConstant.mainFrame, msg, MESSAGE);
    }  
    
    /**
     * 弹出框
     * 
     * @param parentComponent
     * @param msg
     * @param MESSAGE 
     */
    private static void alertMsg(Component parentComponent, String msg, int MESSAGE) {
        alertMsg(parentComponent, msg, TIP_TITLE, MESSAGE);
    }
    
     /**
     * 弹出框
     * 
     * @param parentComponent
     * @param msg
     * @param MESSAGE 
     */
    private static void alertMsgSyn(Component parentComponent, String msg, int MESSAGE) {
        alertMsgSyn(parentComponent, msg, TIP_TITLE, MESSAGE);
    }
    
    /**
     * 弹出框
     *
     * @param parentComponent
     * @param msg
     * @param MESSAGE
     */
    private static void alertMsg(final Component parentComponent, final String msg, final String title, final int MESSAGE) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(parentComponent, TIP_FONT + msg, title, MESSAGE);
            }
        });

    }
    
    /**
     * 弹出框
     *
     * @param parentComponent
     * @param msg
     * @param MESSAGE
     */
    private static void alertMsgSyn(final Component parentComponent, final String msg, final String title, final int MESSAGE) {
        
        JOptionPane.showMessageDialog(parentComponent, TIP_FONT + msg, title, MESSAGE);
    }
    
    /**
     * 弹出框确认
     * @param msg
     * @param title
     * @param optionType DEFAULT_OPTION = -1; YES_NO_OPTION = 0; YES_NO_CANCEL_OPTION = 1; OK_CANCEL_OPTION = 2;
     * @return 
     */
    public static int confirmInfoMsg(String msg, int optionType) {
        return confirmMsg(BaseConstant.mainFrame, msg, TIP_TITLE, optionType);
    }
    
    /**
     * 弹出框确认
     * 
     * @param msg
     * @return 
     */
    public static int confirmInfoMsgYesOrNo(String msg) {
        return confirmInfoMsg(msg, YES_NO_OPTION);
    }

    /**
     * 确认框
     *
     * @param parentComponent
     * @param msg
     * @param title
     * @param optionType
     * @return
     */
    private static int confirmMsg(Component parentComponent, String msg, String title, int optionType){
        
        return JOptionPane.showConfirmDialog(parentComponent, TIP_FONT+msg, title, optionType);
    }
    
    /**
     * 确认YES
     * 
     * @param option
     * @return 
     */
    public static boolean isConfirmYes(int option){
        return YES_OPTION == option;
    }
    
    /**
     * 确认No
     * 
     * @param option
     * @return 
     */
    public static boolean isConfirmNo(int option){
        return NO_OPTION == option;
    }
    
    /**
     * 确认cancel
     * 
     * @param option
     * @return 
     */
    public static boolean isConfirmCancel(int option){
        return CANCEL_OPTION == option;
    }
    
    /**
     * 确认close
     * 
     * @param option
     * @return 
     */
    public static boolean isConfirmClose(int option){
        return CLOSED_OPTION == option;
    }
}
