/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.constant;

import java.math.BigDecimal;

/**
 *
 * @author liudz
 */
public class ImportConstant {
    public static String OP_TYPE_ADD = "1";// 增加

	public static String OP_TYPE_DELETE = "2";// 删除
	

	public static String PATH_BASE_BCP = "/bcp/";
	
	/*
	 * 交易分配表
	 */
	public static String TABLE_NAME_DEAL_CONTC = "w_op_prm_deal_assign_contc";
        public static String TABLE_NAME_DEAL_LINE = "w_op_prm_deal_assign_line";
        
	public static String FILE_NAME_BCP_DEAL = "deal_assign_para.bcp";

	public static String FILE_NAME_DEAL_CONTC_SQL = "运营商交易分配表";
        public static String FILE_NAME_DEAL_LINE_SQL = "线路交易分配表";

	/*
	 * 交易分配比例字段属性
	 */
	public static int FIELD_SIZE_DEAL_CONTC = 6;
        public static int FIELD_SIZE_DEAL_LINE = 6;
        
	public static int[] ICCS_ASSIGN_VALID_DIGIT = {};

	public static int[] ICCS_ASSIGN_VALID_LENGTH = {};

	public static int[] ICCS_ASSIGN_LENGTH = {};

	public static int ICCS_ASSIGN_VERSION_INDEX = 0;

	public static String FIELD_NAME_DEAL_CONTC = "b_line_id,b_station_id,e_line_id,e_station_id,contc_id,in_percent,version_no,record_flag";
        public static String FIELD_NAME_DEAL_LINE = "b_line_id,b_station_id,e_line_id,e_station_id,line_id_dispart,in_percent,version_no,record_flag";
        
	public static String[] FIELD_TYPE_DEAL_CONTC = { "string", "string","string", "string", "string", "decimal", "string", "string"};
        public static String[] FIELD_TYPE_DEAL_LINE = { "string", "string","string", "string", "string", "decimal", "string", "string"};
        
	public static int[] ICCS_ASSIGN_FIELD_NOT_NEED = {};

	public static int ICCS_ASSIGN_VERSION_INDEX_SQL = 0;

	public static int TRAN_UNIT_COUNT = 10000;

	public static BigDecimal RATE_STAND = new BigDecimal("1.000000");

	public static int SIZE_INIT = 10000;

	public static int SIZE_INCREMENT = 5000;
	
	/** 导入表标题**/
	public  static String TITLE_NAME_ZONE="收费区段表";
	public  static String TITLE_NAME_TABLE="票价表";
	public  static String TITLE_NAME_ASSIGN="交易分配表";
	public  static String TITLE_NAME_CONF="收费配置表";
	/** 导入文件名称**/
	public  static String FILE_NAME_BCP_FARE_ZONE="snd_fare_zone.bcp";
	public  static String FILE_NAME_BCP_FARE_CONF="snd_fare_conf.bcp";
	public  static String FILE_NAME_BCP_FARE_TABLE="snd_fare_table.bcp";
	public  static String FILE_NAME_BCP_DEAL_ASSIGN="snd_deal_assign_para.bcp";
	/** 导入表名**/
	public  static String TABLE_NAME_FARE_ZONE="snd_fare_zone";
	public  static String TABLE_NAME_FARE_CONF="snd_fare_conf";
	public  static String TABLE_NAME_FARE_TABLE="snd_fare_table";
	public  static String TABLE_NAME_DEAL_ASSIGN="snd_deal_assign_para";
	/**导入字段数量***/
	public static  int   FIELD_SIZE_ZONE=11;
	public static  int   FIELD_SIZE_CONF=9;
	public static  int   FIELD_SIZE_TABLE=7;
	public static  int   FIELD_SIZE_ASSIGN=8;
	
	
	
	/** 导入类型 **/
	public static  int importType_dealAssign=1;//交易分配表
	public static int importType_fareZone=2;//区段表
	public static int importType_fareTable=3;//票价表
	public static int importType_fareConf=4;//配置表
	

	public ImportConstant() {
		super();
	}
}
