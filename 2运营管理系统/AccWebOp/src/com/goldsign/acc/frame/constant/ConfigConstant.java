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
	// 最大黑名单段数量 add by zhongziqi 20170612
	public static int MAX_BLACKLIST_SECTION_NUMBER = 20;
	// 最大地铁黑名单数量 add by zhongziqi 20170613
	// public static int MAX_METRO_BLACKLIST_NUMBER = 10000;

	public static int MAX_PAGE_NUMBER = 10;// 页面列表最大显示行数

	public static String PHYLOGIC_UPLOAD_PATH = "/home/wweb/op/phylogic";

	public static String PARA_UPLOAD_PATH = "/home/wweb/op/parameter";
    //add by zhongziqi 不要赋值 判断条件
	public static String EXPORT_EXCEL_PATH = "";

	public static String EXPORT_EXCEL_PATH_WINDOWS = "D:\\tmp";

	public static String EXPORT_EXCEL_PATH_KEY = "EXPORT_EXCEL_PATH";
}
