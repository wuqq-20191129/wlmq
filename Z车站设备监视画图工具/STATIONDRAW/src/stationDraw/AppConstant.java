/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stationDraw;

import java.sql.Connection;

/**
 *
 * @author Administrator
 */
public class AppConstant {
    /** 存储车站监控的设备信息的表 */
    public static String db_tab = "w_fm_dev_monitor";
    /**
     * 设置设备节点类型dev_type对应db_tab中的node_type字段
     * 原始：01动态图,02静态图,03文字,04线条,05动态文本
     */
    public static String node_type_dynamic_pic = "01";//动态图片
    public static String node_type_static_pic = "02";//静态图片
    public static String node_type_static_word = "03";//静态文字
    public static String node_type_line = "04";//线条
    public static String node_type_dynamic_word = "05";//动态文字
    
    /**
     * 设置按钮类型TSOURCE
     */
    public static int source_button_blank = 0;//未点击按钮
    
    public static int source_button_sc = 1;//车站计算机
    public static int source_button_tvm = 2;//自动售票机
    public static int source_button_bom = 3;//票房售票机/半自动售票机
    public static int source_button_eg = 4;//进站闸机
    public static int source_button_xeg = 5;//出站闸机
    public static int source_button_twg = 6;//双向闸机
    public static int source_button_aqm = 7;//自动查询机
    public static int source_button_pca = 8;//便携式验票机
    public static int source_button_es = 9;//编码分拣机
    public static int source_button_itm = 10;//综合验票机
    
    public static int source_button_line = 11;// 线条
    public static int source_button_word = 12;// 文本
    public static int source_button_pic = 13;// 图片
    public static int source_button_border_line = 14;// 图例边框线
    
    public static int source_button_lc = 98;//线路计算机系统
    public static int source_button_acc = 99;//清分中心计算机系统
    
    /**
     * 设置设备类型type、dev_type 对应db_tab中的DEV_TYPE_ID字段
     */
    public static String dev_type_tvm = "02";//自动售票机
    public static String dev_type_bom = "03";//票亭
    public static String dev_type_eg = "04";//进站闸机
    public static String dev_type_xeg = "05";//出站闸机
    public static String dev_type_twg = "06";//双向闸机
    public static String dev_type_aqm = "07";//自动查询机
    public static String dev_type_pca = "08";//便携式验票机
    public static String dev_type_itm = "10";//综合验票机
    
    /**
     * 设置设备对应图片
     */
     /** 票亭的图片 */
    public static String bomImg = "images/bom01.gif";
    /** 验票机的图片 */
    public static String pcaImg = "images/pca01.gif";
    /** 综合验票机的图片 */
    public static String itmImg = "images/itm01.gif";
    /** 售票机的图片 */
    public static String tvmImg = "images/tvm01.gif";
    /** 自动查询机的图片 */
    public static String aqmImg = "images/aqm01.gif";
    /** 不同方向的进站闸机的图片 */
    public static String[] egImg = {"images/eg_left.gif","images/eg_right.gif",
        "images/eg_up.gif","images/eg_down.gif"};
    /** 不同方向的出站闸机的图片 */
    public static String[] xegImg = {"images/xeg_left.gif","images/xeg_right.gif",
        "images/xeg_up.gif","images/xeg_down.gif"};
    /** 双向闸机的图片 */
    public static String[] twgImg={"images/twg_h.gif","images/twg_v.gif"};
    /** 进出站闸机的4种方向 */
    public static String[] amgDirection={"01","02","11","12",};
    /** 双向闸机的2种方向 */
    public static String[] twgDirection={"00","11"};
    
    /** 用来连接数据的连接 */
    public static Connection con;
    /** 线路数据库连接 **/
    public static Connection con2;
}
