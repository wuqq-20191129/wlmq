/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.csfrm.env;

import com.goldsign.csfrm.application.IApplicationListener;
import com.goldsign.csfrm.ui.frame.MainFrame;
import com.goldsign.csfrm.ui.panel.IBaseWindow;
import com.goldsign.csfrm.ui.panel.PublicPanel;
import com.goldsign.csfrm.vo.SysAppVo;
import com.goldsign.csfrm.vo.SysModuleVo;
import com.goldsign.csfrm.vo.SysUserVo;
import java.awt.Color;
import java.util.Vector;

/**
 *
 * @author lenovo
 * 
 * 常量类
 * 应用的常量类，应继承此类
 */
public class BaseConstant {

    public static SysAppVo sysAppVo = new SysAppVo();//应用
    public static final String VERSION_DEFAULT = "V0.0.0";
    public static final String APP_NAME_DEFAULT = "应用系统";//系统名称
    public static final String APP_CODE_DEFAULT = "00";//
    
    public static String APP_NAME_SELF_ADAPTION = "";//系统名称，由子系统赋值而来
    
    //菜单级别
    public static final String MODULE_LEVEL_FIRST="0";             //一级菜单(模块)
    public static final String MODULE_LEVEL_SECOND="1";            //二级菜单(模块)
    public static final String MODULE_LEVEL_THIRD ="2";            //三级菜单(按钮)
    
    //菜单类型
    public static final String MODULE_TYPE_MODULE ="0";            //模块
    public static final String MODULE_TYPE_BUTTON ="1";            //按钮
    
    //是否检查下级权限
    public static final boolean RIGHT_CHECK_NEXT_YES = true;            //检查下级权限
    public static final boolean RIGHT_CHECK_NEXT_NO = false;            //不检查下级权限
    
    //退出按钮
    public static final String TOP_MODULE_LAST = "shift";        //退出按钮代码
    public static final String TOP_MODULE_LAST_TEXT = "换班";    //退出按钮名称
    
    //应用相关变量
    public static IApplicationListener application = null;               //当前应用
    public static MainFrame mainFrame =null;                             //主框架对象
    public static PublicPanel publicPanel =new PublicPanel();            //状态栏操作对象
    public static IBaseWindow baseMainWindow = null;       //主窗口
    public static SysUserVo user = null;                                 //用户对象
    public static Vector<SysModuleVo> availableMenus = new Vector<SysModuleVo>();    //所有菜单
    
    //当前点击菜单
    public static String SECOND_CUR_MODULE_ID ="";         //当前点击的二级击菜单按钮对应的模块ID
    public static String SECOND_CUR_MODULE_NAME ="";       //当前点击的二级击菜单按钮对应的模块名
    
    //工作目录
    public static final String appWorkDir = System.getProperty("user.dir"); //应用程序工作目录
    
    //状态栏
    public static final String STATUS_BAR_STATUS_COLOR_OFF = "断开";    //状态栏-连联状态断
    public static final String STATUS_BAR_STATUS_COLOR_ON = "正常";     //状态栏-连联状态好
    
    
//    public static final Color SYS_DFT_COLOR = new Color(0, 39, 80);//默认#002750   青黑色
//    public static final Color SYS_COM_COLOR = new Color(70, 95, 125);//普通#465F7D 浅黑色
//    public static final Color SYS_INFO_COLOR = new Color(94, 55, 16);//提示#5E3710 深棕色
//    public static final Color SYS_WARN_COLOR = new Color(255, 93, 2);//警告#FF5D02 橙红色
    public static final Color SYS_DFT_COLOR = new Color(0, 39, 80);//默认#002750   青黑色
    public static final Color SYS_COM_COLOR = new Color(70, 95, 125);//普通#465F7D 浅黑色
    public static final Color SYS_INFO_COLOR = new Color(94, 55, 16);//提示#5E3710 深棕色
    public static final Color SYS_WARN_COLOR = new Color(255, 93, 2);//警告#FF5D02 橙红色
    public static final Color SYS_ERROR_COLOR = Color.RED;//错误  红色
    public static final Color SYS_TITLE_FG_COLOR = new Color(94,55,16);//标题前色#5E3710
    public static final Color SYS_TTTIL_BG_COLOR = new Color(185,227,241);//标题底色#B9E3F1
    public static final Color SYS_BG_COLOR = new Color(237,247,249); //#EDF7F9
    public static final Color SYS_POP_COLOR = new Color(91,148,204);//弹出窗##5B94CC
    public static final Color SYS_POP2_COLOR = new Color(55,133,181);//弹出窗2 #3785B5
    
    //分隔符或符号
    public static final String SEP_VER_SIGN = "\\|";    //垂直符号分割符
    public static final String VER_SIGN = "|";          //垂直符号
    public static final String MAO_SIGN = ":";          //冒号
    public static final String DOT_SIGN = ".";          //点号
    public static final String ADD_SIGN = "+";          //加号
    public static final String WELL_SIGN = "#";            //#号
    public static final String SLASH_LIX_SIGN = "/";    //LINIX斜杠   
    public static final String SLASH_WIN_SIGN = "\\";    //WIN斜杠
    public static final String SEP_DOT_SIGN = "\\.";    //点分隔
    public static final String SEP_TAB_SIGN = "\\t";    //tab分隔
    
    //系统风格
    public static final String FRM_WINDOW_STYLE = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"; //框架系统风格
    
    //选择框
    public static final String SELECT_OPTION_ALL = "";
    public static final String SELECT_OPTION_ALL_NAME = "全部";
    
    //确认框类型
    public static final int         DEFAULT_OPTION = -1;
    public static final int         YES_NO_OPTION = 0;
    public static final int         YES_NO_CANCEL_OPTION = 1;
    public static final int         OK_CANCEL_OPTION = 2;
    
    //系统状态
    public static String SYS_APP_CUR_STATUS_LOAD = "1";
    public static String SYS_APP_CUR_STATUS_INIT = "2";
    public static String SYS_APP_CUR_STATUS_FINISH = "3";
    public static String SYS_APP_CUR_STATUS_EXIT = "4";
}
