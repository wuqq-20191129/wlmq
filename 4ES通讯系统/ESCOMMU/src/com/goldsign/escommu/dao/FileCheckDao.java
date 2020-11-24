/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.dao;

import com.goldsign.escommu.dbutil.DbHelper;
import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.util.PubUtil;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class FileCheckDao {

    private static Logger logger = Logger.getLogger(FileCheckDao.class.getName());

    public boolean checkForDevice(String devId) throws Exception {
        boolean result = false;
        DbHelper dbHelper = null;
        Object[] values = {devId};
        int num = -1;


        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            //String sqlStr = "select count(*) num from ACC_ST.OP_PRM_DEV_CODE where record_flag='0' and dev_type_id='09' and device_id=?  ";//hwj 20150906 modify for 兼容多种设备类型
            String sqlStr = "select count(*) num from "+AppConstant.COM_ST_P+"OP_PRM_DEV_CODE_ACC a inner join "+AppConstant.COM_TK_P+"IC_ES_LEGAL_DEVTYPE b on a.DEV_TYPE_ID=b.DEV_TYPE_ID where record_flag='0' and device_id=?  ";

            result = dbHelper.getFirstDocument(sqlStr, values);
            if (result) {
                num = dbHelper.getItemIntValue("num");
                if (num == 1) {
                    return true;
                }
            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);

        }

        return false;
    }

    public boolean checkForOrderStatus(String orderNo, boolean isMBpdType) throws Exception {
        boolean result = false;
        DbHelper dbHelper = null;
        Object[] values = {orderNo};
        int num = -1;


        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            String sqlStr = "";
            if(isMBpdType){
                sqlStr = "select count(*) num from "+AppConstant.COM_TK_P+"IC_MB_PDU_ORDER_FORM where order_no=? ";
            }else{
                sqlStr = "select count(*) num from "+AppConstant.COM_TK_P+"IC_PDU_ORDER_FORM where order_no=? and hdl_flag in ('2','3')  ";
            }

            result = dbHelper.getFirstDocument(sqlStr, values);
            if (result) {
                num = dbHelper.getItemIntValue("num");
                if (num != 0) {
                    return false;
                }
            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);

        }

        return true;
    }

    public boolean checkForOrder(String orderNo) throws Exception {
        boolean result = false;
        DbHelper dbHelper = null;
        Object[] values = {orderNo};
        int num = -1;


        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            String sqlStr = "select count(*) num from "+AppConstant.COM_TK_P+"IC_PDU_ORDER_FORM where order_no=? and hdl_flag<>'0'  ";

            result = dbHelper.getFirstDocument(sqlStr, values);
            if (result) {
                num = dbHelper.getItemIntValue("num");
                if (num == 1) {
                    return true;
                }
            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);

        }

        return false;
    }
}
