/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.fm.dao;

import com.goldsign.fm.common.AppConstant;
import com.goldsign.fm.common.PubUtil;
import com.goldsign.lib.db.util.DbHelper;
import java.util.HashMap;
import org.apache.log4j.Logger;

/**
 * 时间配置
 * @author Administrator
 */
public class ConfigDao {

    private static Logger logger = Logger.getLogger(ConfigDao.class.getName());

    public HashMap getConfiguration() throws Exception {
        DbHelper dbHelper = null;
        String sql = "select config_name,config_value from w_acc_mn.w_fm_config ";
        HashMap configs = new HashMap();
        boolean result = false;
        String name;
        String value;
        try {
            dbHelper = new DbHelper("",AppConstant.dbcpHelper2.getConnection());
            result = dbHelper.getFirstDocument(sql);
            while (result) {
                name = dbHelper.getItemValue("config_name");
                value = dbHelper.getItemValue("config_value");
                configs.put(name, value);
                result = dbHelper.getNextDocument();

            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return configs;

    }
}
