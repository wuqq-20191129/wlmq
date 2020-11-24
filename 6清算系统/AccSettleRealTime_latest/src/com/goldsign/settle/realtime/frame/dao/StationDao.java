/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.ConfigBcpVo;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class StationDao {

    private static Logger logger = Logger.getLogger(StationDao.class.getName());

    public Vector<String> getStationCode() throws Exception {
        Vector<String> stations = new Vector();
        String sql = "select distinct line_id||station_id stationCode "
                + "from "+FrameDBConstant.DB_PRE+"op_prm_station where record_flag='0'  ";
        DbHelper dbHelper = null;
        boolean result;
        ConfigBcpVo vo = null;
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql);
            while (result) {
                stations.add(dbHelper.getItemValue("stationCode"));
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return stations;
    }
}
