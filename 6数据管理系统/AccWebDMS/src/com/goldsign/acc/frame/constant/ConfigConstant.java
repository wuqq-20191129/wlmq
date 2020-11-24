/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.constant;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lind
 */
public class ConfigConstant {

    public static Map<String, String> FILTER_PROPERTIES = new HashMap<String, String>();
    public static Map<String, String> SESSION_ID = new HashMap<String, String>();
    public static int MAX_PAGE_NUMBER = 10;//页面列表最大显示行数
    //add by zhongziqi 不要赋值 判断条件
    public static String EXPORT_EXCEL_PATH = "";

    public static String EXPORT_EXCEL_PATH_WINDOWS = "D:\\tmp";

    public static String EXPORT_EXCEL_PATH_KEY = "EXPORT_EXCEL_PATH";
}
