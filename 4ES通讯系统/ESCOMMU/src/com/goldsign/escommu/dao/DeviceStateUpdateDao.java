/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.dao;
import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.dbutil.DbHelper;
import com.goldsign.escommu.util.PubUtil;
import com.goldsign.escommu.vo.DeviceStateReqVo;
import com.goldsign.escommu.vo.DeviceStateRspVo;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class DeviceStateUpdateDao {
    private static Logger logger = Logger.getLogger(DeviceStateUpdateDao.class.
			getName());
    
    public DeviceStateRspVo deviceStateUpdate(DeviceStateReqVo deviceStateReqVo) throws Exception {
        boolean result = false;
        DbHelper dbHelper = null;
        DeviceStateRspVo deviceStateRspVo = null;
        
        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            
            Object[] values = {deviceStateReqVo.getDeviceId(),deviceStateReqVo.getChangeTime(), 
                deviceStateReqVo.getState()};
                        
            Object[] values2 = {deviceStateReqVo.getDeviceId(), deviceStateReqVo.getOperCode(), 
                deviceStateReqVo.getChangeTime(),deviceStateReqVo.getState(), deviceStateReqVo.getDesc()};
            
            //********************************************
            String sqlStr = "select * from "+AppConstant.COM_TK_P+"IC_ES_STATUS where device_id=? and status_time=? and status=?";
            String sqlStr2 = "insert into "+AppConstant.COM_TK_P+"IC_ES_STATUS(device_id,operator_id,status_time,status,remark)"
                        +" values(?, ?, ?, ?, ?)";
            dbHelper.setAutoCommit(false);
            result = dbHelper.getFirstDocument(sqlStr, values);
            if (!result) {
                dbHelper.executeUpdate(sqlStr2, values2);
            }
            deviceStateRspVo = getResultRecord(dbHelper);
            dbHelper.commitTran();
        } catch (Exception e) {
            //PubUtil.handleExceptionForTranNoThrow(e,logger,dbHelper);
            PubUtil.handleExceptionForTran(e,logger,dbHelper);
        } finally {
            PubUtil.finalProcessForTran(dbHelper);
        }

        return deviceStateRspVo;
    }
    
    private DeviceStateRspVo  getResultRecord(DbHelper dbHelper) throws Exception{
        
        DeviceStateRspVo deviceStateRspVo = new DeviceStateRspVo();

       

        return deviceStateRspVo;
    }
}
