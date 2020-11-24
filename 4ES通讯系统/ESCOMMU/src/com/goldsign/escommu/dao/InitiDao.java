/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.dao;

import com.goldsign.escommu.dbutil.DbHelper;
import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.util.PubUtil;
import java.util.Hashtable;
import java.util.TreeSet;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class InitiDao {

    private static Logger logger = Logger.getLogger(InitiDao.class.getName());

    public Vector getPriorities() throws Exception {
        boolean result = false;
        DbHelper dbHelper = null;

        Vector v = new Vector();
        String msgId;

        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            String sqlStr = "select msg_id from "+AppConstant.COM_CM_P+"CM_EC_MSG_PRIORITY where type=?  ";
            Object[] values = {AppConstant.TYPE_PRIORITY_FIRST};
            result = dbHelper.getFirstDocument(sqlStr, values);
            while (result) {
                msgId = dbHelper.getItemValue("msg_id");
                v.add(msgId);
                result = dbHelper.getNextDocument();
            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);

        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return v;
    }

    public TreeSet getStations() throws Exception {
        boolean result = false;
        DbHelper dbHelper = null;

        TreeSet v = new TreeSet();
        String lineStation;

        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            String sqlStr = "select distinct line_id||station_id lineStation from "+AppConstant.COM_ST_P+"OP_PRM_STATION where "
                    + "record_flag=?  ";
            Object[] values = {AppConstant.RECORD_FLAG_CURRENT};
            result = dbHelper.getFirstDocument(sqlStr, values);
            while (result) {
                lineStation = dbHelper.getItemValue("lineStation");
                v.add(lineStation);
                result = dbHelper.getNextDocument();
            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);

        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return v;
    }

    public TreeSet getLines() throws Exception {
        boolean result = false;
        DbHelper dbHelper = null;

        TreeSet v = new TreeSet();
        String line;

        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            String sqlStr = "select distinct line_id  from "+AppConstant.COM_ST_P+"OP_PRM_LINE where "
                    + "record_flag=?  ";
            Object[] values = {AppConstant.RECORD_FLAG_CURRENT};
            result = dbHelper.getFirstDocument(sqlStr, values);
            while (result) {
                line = dbHelper.getItemValue("line_id");
                v.add(line);
                result = dbHelper.getNextDocument();
            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);

        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return v;
    }

    public TreeSet getCardSubType() throws Exception {
        boolean result = false;
        DbHelper dbHelper = null;

        TreeSet v = new TreeSet();
        String card;

        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            String sqlStr = "select distinct CARD_MAIN_ID||CARD_SUB_ID card  from "+AppConstant.COM_ST_P+"OP_PRM_CARD_SUB where "
                    + "record_flag=?  ";
            Object[] values = {AppConstant.RECORD_FLAG_CURRENT};
            result = dbHelper.getFirstDocument(sqlStr, values);
            while (result) {
                card = dbHelper.getItemValue("card");
                v.add(card);
                result = dbHelper.getNextDocument();
            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);

        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return v;
    }

    public Hashtable getDevices() {
        boolean result = false;
        DbHelper dbHelper = null;

        Vector v = new Vector();
        String deviceId;
        String ipAddress;
        Hashtable ht = new Hashtable();

        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            //String sqlStr = "select distinct device_id,ip_address from "+AppConstant.COM_ST_P+"OP_PRM_DEV_CODE where record_flag='0' and dev_type_id='09' ";//hwj 20150906 modify for 兼容多种设备类型
            String sqlStr = "select distinct device_id,ip_address from "+AppConstant.COM_ST_P+"OP_PRM_DEV_CODE_ACC a inner join "+AppConstant.COM_TK_P+"IC_ES_LEGAL_DEVTYPE b on a.DEV_TYPE_ID=b.DEV_TYPE_ID where record_flag='0' ";

            result = dbHelper.getFirstDocument(sqlStr);
            while (result) {
                deviceId = dbHelper.getItemValue("device_id");
                ipAddress = dbHelper.getItemValue("ip_address");
                ht.put(deviceId, ipAddress);

                result = dbHelper.getNextDocument();
            }

        } catch (Exception e) {
            logger.error("访问dev_code表错误! " + e);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return ht;
    }
}
