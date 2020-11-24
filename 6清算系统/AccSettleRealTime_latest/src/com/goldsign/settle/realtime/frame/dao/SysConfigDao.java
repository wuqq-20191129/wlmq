/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.ConfigRecordFmtVo;
import java.sql.SQLException;
import java.util.HashMap;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class SysConfigDao {

    private static Logger logger = Logger.getLogger(SysConfigDao.class.getName());
    private boolean isRunInDevMode(){
        if(FrameCodeConstant.RUN_MODE.equals(FrameCodeConstant.RUN_MODE_DEV))
            return  true;
        return false;
        
        
    }
    public HashMap<String, String> getSysConfig() throws Exception {
        if(this.isRunInDevMode())
            return this.getSysConfigForDevMode();
        return this.getSysConfigForProductMode();
        
    }

    public HashMap<String, String> getSysConfigForProductMode() throws Exception {
        HashMap<String, String> hm = new HashMap();
        String sql = "select distinct cfg_key,cfg_value from "+FrameDBConstant.DB_PRE+"st_cfg_sys_base order by cfg_key ";
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
    
    public HashMap<String, String> getSysConfigForDevMode() throws Exception {
        HashMap<String, String> hm = new HashMap();
        String sql = "select distinct cfg_key,cfg_value from "+FrameDBConstant.DB_PRE+"st_cfg_sys_base_dev order by cfg_key ";
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
        String key = dbHelper.getItemValue("cfg_key");
        String value = dbHelper.getItemValue("cfg_value");
        hm.put(key, value);

    }
}
