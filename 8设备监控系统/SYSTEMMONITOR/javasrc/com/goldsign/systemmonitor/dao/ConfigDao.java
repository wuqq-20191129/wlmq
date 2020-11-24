package com.goldsign.systemmonitor.dao;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FramePubUtil;
//import com.goldsign.systemmonitor.util.MonitorDBHelper;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.systemmonitor.vo.DbConfig;
import java.util.HashMap;
import java.util.Vector;
import org.apache.log4j.Logger;

public class ConfigDao {

    static Logger logger = Logger.getLogger(ConfigDao.class);

    public ConfigDao() {
        super();
    }

    public void getAppConfigs() throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        DbConfig config;
        String type = "";
        try {
            System.out.print("======="+FrameDBConstant.MAIN_DATASOURCE);
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select type,config_name,config_value,remark from mtr_config order by type";
            result = dbHelper.getFirstDocument(strSql);
            while (result) {
                config = new DbConfig();
                type = dbHelper.getItemValue("type");
                config.setConfigName(dbHelper.getItemValue("config_name"));
                config.setConfigValue(dbHelper.getItemValue("config_value"));
                config.setType(type);
                config.setRemark(dbHelper.getItemValue("remark"));
                this.addConfig(type, FrameDBConstant.APP_CONFIGS, config);
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
    }

    private void addConfig(String type, HashMap configs, DbConfig config) {
        if (configs.containsKey(type)) {
            ((Vector) configs.get(type)).add(config);
        } else {
            Vector v = new Vector();
            configs.put(type, v);
            v.add(config);
        }
    }
}
