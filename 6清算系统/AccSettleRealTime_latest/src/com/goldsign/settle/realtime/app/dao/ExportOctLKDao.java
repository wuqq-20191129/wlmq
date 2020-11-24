/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.app.vo.ExportDbResult;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import java.math.BigDecimal;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class ExportOctLKDao {
    private static String BLACKE_TYPE_SINGLE="0";//单个名单
    private static String CARD_STATUS_LOCK="01";//锁定状态
    private static String CARD_LOGICAL_ID_DEFAULT="00000000000000000000";//缺省逻辑卡号
    private static String FLAG_LOCKED ="1";//锁卡
    private static String FLAG_UNLOCKED ="2";//解锁
    private static Logger logger = Logger.getLogger(ExportOctLKDao.class.getName());

    public ExportDbResult getRecords(String balanceWaterNo) throws Exception {

        String sql = "select sam_logical_id,'1',to_char(lock_datetime,'yyyymmddhh24miss') lock_datetime,card_logical_id,card_physical_id,"
                + "card_main_id_oct,card_sub_id_oct,'0000000000000000' from "+FrameDBConstant.DB_PRE+"ST_QUE_LOCK_OCT "
                + "where balance_water_no=? and lock_flag=?"
                + "order by CARD_LOGICAL_ID,card_physical_id";

        DbHelper dbHelper = null;
        boolean result = false;

        Vector<String[]> v = new Vector();
        ExportDbResult resultExp = new ExportDbResult();

        Object[] values = {balanceWaterNo,FLAG_LOCKED};
        int totalFeeFen = 0;
        
        String[] arr;
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            while (result) {
                arr = this.getArray(dbHelper);

                v.add(arr);


                result = dbHelper.getNextDocument();
            }
            resultExp.setTotalFeeFen(totalFeeFen);
            resultExp.setTotalNum(v.size());
            resultExp.setRecords(v);



        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return resultExp;


    }
     private String[] getArray(DbHelper dbHelper) throws Exception {
 
        String[] arr = new String[8];
        arr[0] = dbHelper.getItemValue("sam_logical_id");//终端编号
        arr[1] = "1";//终端标志
        arr[2] = dbHelper.getItemValue("lock_datetime");;////锁卡时间
        arr[3] =dbHelper.getItemValue("card_logical_id");;//票卡逻辑卡号 
        arr[4] =dbHelper.getItemValue("card_physical_id");;//票卡物理卡号
        arr[5] =dbHelper.getItemValue("card_main_id_oct");;//票卡主类型
        arr[6] =dbHelper.getItemValue("card_sub_id_oct");;//票卡子类型
        arr[7] ="0000000000000000";//预留字段
        return arr;

    }
    
}
