/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.fm.common;

import com.goldsign.fm.vo.TreeNode;
import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Hashtable;

/**
 *
 * @author Administrator
 */
public class AppConstant {

    public static String configFile = "config/CommonConfig.xml";//应用程序配置文件名称
    public static Hashtable dbConfig = new Hashtable(); //数据库配置
    public static DbcpHelper dbcpHelper1 = null;//数据库连接池工具(线路基础库)
    public static DbcpHelper dbcpHelper2 = null;//数据库连接池工具(设备状态库)
    public static String log4jConfigFile = "config/Log4jConfig.xml";//log4j配置文件名称;
    public static String appWorkDir = System.getProperty("user.dir");//应用程序工作目录
    public static String logFileName = "flow.log";
    
    //设备状态图片大小配置
    public static HashMap IMAGE_SIZES_BUFFER = null;
    public static String IMAGE_PROPERTIES_NAME = "devMonitorimage.properties";
    
    //实际设备大小与理想大小比率
    public static double BATE_WIDTH = 1;
    public static double BATE_HEIGHT = 1;
    //大屏幕时段图的大小
    public static int SCREEN_GRAPH_WIDTH = 1182;
    public static int SCREEN_GRAPH_HEIGHT = 430;
    //大屏幕餠图的大小
    public static int SCREEN_PIE_GRAPH_WIDTH = 630;
    public static int SCREEN_PIE_GRAPH_HEIGHT = 450;
    //大屏幕票卡餠图的大小
    public static int SCREEN_PIE_CARD_GRAPH_WIDTH = 1240;
    public static int SCREEN_PIE_CARD_GRAPH_HEIGHT = 430;
    //大屏幕餠图文本
    public static final String SCREEN_CAPTION_IN = "进";
    public static final String SCREEN_CAPTION_OUT = "出";
    public static final String SCREEN_CAPTION_TOTAL = "总计";
    public static final String SCREEN_CAPTION_SUM = "合计";
    public static final String SCREEN_CAPTION_SUM_PRE = "[";
    //大屏幕坐标的的单位
    public static final String SCREEN_CAPTION_ROW_HOUR = "小时";
    public static final String SCREEN_CAPTION_ROW_MIN = "分钟";
    public static final String SCREEN_CAPTION_ROW_YEAR = "年";
    public static final String SCREEN_CAPTION_ROW_MONTH = "月";
    public static final String SCREEN_CAPTION_ROW_DAY = "日";
    public static final String SCREEN_CAPTION_COLUMN_PERSON = "人次";
    //所有线路标志
    public static int FLAG_ALLLINE = 0;
    //单线路标志
    public static int FLAG_SINGLELINE = 1;
    //车站标志
    public static int FLAG_STATION = 2;
    //进站标志
    public static String FLAG_IN = "0";
    //出站标志
    public static String FLAG_OUT = "1";
    public static String DICISION_IMG_PATH = "/images/chart/";
    //节点类型
    public static String NODE_TYPE_NET = "0";//线网
    public static String NODE_TYPE_LINE = "1";//线路
    public static String NODE_TYPE_STATION = "2";//车站
    public static Color COLOR_SHOW_BACKGROUND = new Color(0, 101, 153); //显示区背景色
    public static Color COLOR_TABLE_HEAD_BACKGROUND = new Color(0, 51, 51); //表头背景颜色
    public static Color COLOR_TABLE_DATA_BACKGROUND = new Color(0, 102, 102); //表背景颜色
    public static Color COLOR_TABLE_DATA = new Color(255, 255, 255); //表数据颜色
    public static Color COLOR_TABLE_DATA_TOTAL = new Color(0, 255, 102);//new Color(255, 128, 128);//new Color(0, 255, 102); //表合计数据颜色

    public static Font FONT_TABLE_HEAD = new Font("黑体", Font.BOLD, 18);//表头字体
    public static Font FONT_TABLE_DATA = new Font("黑体", Font.PLAIN, 16);//表数据字体
    public static int HIGH_TABLE_ROW = 20;//表行高度
    public static int MODE_SHOW_HOUR = 1;//小时客流模式
    public static int MODE_SHOW_MIN = 2;//分钟客流模式
    public static int MODE_SHOW_DEV = 3;//设备状态模式
    public static int MODE_SHOW_CARD_TYPE = 4;//票卡类型状态模式
    public static TreeNode CURRENT_NODE_CLICK;//当前选择节点
    public static int CURRENT_MODE;//当前显示模式
    public static long TIME_INTERVAL_REFRESH = 60000;//刷新时间间隔
    public static boolean isLocked =false;//控制是否锁键盘

     public static Color TREE_COLOR_SHOW_BACKGROUND = new Color(0, 101, 255); //显示区背景色
     public static Color TREE_COLOR_FONT_FOREGROUND = new Color(255,255,255); //显示区字体颜色
     public static Font TREE_FONT = new Font("宋体", Font.BOLD, 12);//表头字体
     
     public static int IN_LINE_ID=98;//进站线路ID
     public static int OUT_LINE_ID=99;//出站线路ID

     //票卡类型标识
     public static String CARD_IN_MAIN = "inMain";
     public static String CARD_OUT_MAIN = "outMain";
     public static String CARD_IN_SUB = "inSub";
     public static String CARD_OUT_SUB = "outSub";
     public static String CARD_MAIN = "MAIN";//主类型
     public static String CARD_SUB = "SUB";//子类型
}
