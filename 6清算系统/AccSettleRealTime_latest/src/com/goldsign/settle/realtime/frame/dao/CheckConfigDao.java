/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.CheckControlVo;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class CheckConfigDao {
    private static String CARD_TAC_TYPE_SVT="1";//储值类

    private static Logger logger = Logger.getLogger(CheckConfigDao.class.getName());

    public TreeSet<String> getCheckConfigForDeviceSam() throws Exception {
        TreeSet<String> ts = new TreeSet();
        String sql = "select LINE_ID||STATION_ID||DEV_TYPE_ID||DEVICE_ID||SAM_LOGICAL_ID devSam "
                + "from "+FrameDBConstant.DB_PRE+"OP_PRM_SAM_LIST where record_flag=? order by devSam  ";
        DbHelper dbHelper = null;
        Object[] values ={FrameCodeConstant.RECORD_FLAG_CURRENT};
        boolean result;

        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql,values);
            while (result) {
                ts.add(dbHelper.getItemValue("devSam"));
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return ts;
    }
    public TreeSet<String> getCheckConfigForCardSubType() throws Exception {
        TreeSet<String> ts = new TreeSet();
        String sql = "select CARD_MAIN_ID||CARD_SUB_ID cardType "
                + "from "+FrameDBConstant.DB_PRE+"OP_PRM_CARD_SUB  where record_flag=?  order by cardType ";
        DbHelper dbHelper = null;
        Object[] values ={FrameCodeConstant.RECORD_FLAG_CURRENT};
        boolean result;

        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql,values);
            while (result) {
                ts.add(dbHelper.getItemValue("cardType"));
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return ts;
    }
    
    public String[] getCheckConfigForTacCardMainType() throws Exception {

        String sql = "select CARD_MAIN_ID "
                + "from "+FrameDBConstant.DB_PRE+"ST_SYS_CHK_CARD_MAIN  ";
        DbHelper dbHelper = null;
        boolean result;
        Vector<String> v = new Vector();

        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql);
            while (result) {
                v.add(dbHelper.getItemValue("CARD_MAIN_ID"));
                result = dbHelper.getNextDocument();
            }
            
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return this.vectorToArray(v);
    }
    public String[] getCheckConfigForTacCardMainTypeCpu() throws Exception {

        String sql = "select CARD_MAIN_ID "
                + "from "+FrameDBConstant.DB_PRE+"ST_SYS_CHK_CARD_MAIN where CARD_TAC_TYPE=? ";
        DbHelper dbHelper = null;
        
        boolean result;
        Vector<String> v = new Vector();
        String[] values ={CARD_TAC_TYPE_SVT};
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql,values);
            while (result) {
                v.add(dbHelper.getItemValue("CARD_MAIN_ID"));
                result = dbHelper.getNextDocument();
            }
            
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return this.vectorToArray(v);
    }
    private String[] vectorToArray(Vector<String> v){
        if(v==null || v.isEmpty())
            return null;
        String[] arr = new String[v.size()];
        for(int i=0;i<v.size();i++){
            arr[i] =v.get(i);
        }
        return arr;
    }
    
    private CheckControlVo getCheckControlVo(DbHelper dbHelper) throws SQLException{
        CheckControlVo vo = new CheckControlVo();
        vo.setChkId(dbHelper.getItemValue("chk_id"));
        vo.setAppTradeType(dbHelper.getItemValue("app_trade_type"));
        vo.setValidFlag(dbHelper.getItemValue("valid_flag"));
        return vo;
    }
    public Vector<CheckControlVo> getCheckConfigForCheckControls() throws Exception {
        Vector<CheckControlVo> ts = new Vector();
        String sql = "select chk_id,valid_flag,app_trade_type "
                + "from "+FrameDBConstant.DB_PRE+"st_sys_chk_ctr  ";
        DbHelper dbHelper = null;
        CheckControlVo vo;
        
        boolean result;

        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql);
            while (result) {
                vo = this.getCheckControlVo(dbHelper);
                ts.add(vo);
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return ts;
    }
    public HashMap<String, String> getCheckConfigForCheckLimits() throws Exception {
        HashMap<String, String> hm = new HashMap();
        String sql = "select distinct chk_key,chk_value  "
                + "from "+FrameDBConstant.DB_PRE+"st_sys_chk_limit order by chk_key ";
        DbHelper dbHelper = null;
        boolean result;

        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql);
            while (result) {
                this.setConfigValue(dbHelper, hm);
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return hm;
    }
     private void setConfigValue(DbHelper dbHelper, HashMap<String, String> hm) throws SQLException {
        String key = dbHelper.getItemValue("chk_key");
        String value = dbHelper.getItemValue("chk_value");
        hm.put(key, value);

    }
}
