/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.vo.SysLogLevelVo;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class LogLevelDao {

    private static Logger logger = Logger.getLogger(LogLevelDao.class.
            getName());

    public LogLevelDao() {
        super();
        // TODO Auto-generated constructor stub
    }

    public SysLogLevelVo getLogLevel() {
        boolean result = false;
        DbHelper dbHelper = null;
        SysLogLevelVo vo = null;

        try {
            dbHelper = new DbHelper("LogLevelDao", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            String sqlStr = "select sys_code,sys_level,set_time,operator,"
                    + "version_no,record_flag from "+FrameDBConstant.DB_PRE+"OP_LOG_LEVEL where "
                    + "record_flag=? and sys_code=? ";
            Object[] values = {FrameCodeConstant.RECORD_FLAG_CURRENT, FrameCodeConstant.SYSTEM_SETTLE};
            result = dbHelper.getFirstDocument(sqlStr, values);
            if (result) {
                vo = this.getResultRecord(dbHelper);
            }


        } catch (Exception e) {
            logger.error("访问OP_LOG_LEVEL表错误! " + e);
        } finally {
            try {
                if (dbHelper != null) {
                    dbHelper.closeConnection();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return vo;
    }

    private SysLogLevelVo getResultRecord(DbHelper dbHelper) throws Exception {
        SysLogLevelVo vo = new SysLogLevelVo();
        vo.setSysCode(dbHelper.getItemValue("sys_code"));
        vo.setSysLevel(dbHelper.getItemValue("sys_level"));
        vo.setSetTime(dbHelper.getItemValue("set_time"));
        vo.setOperator(dbHelper.getItemValue("operator"));
        vo.setVersionNo(dbHelper.getItemValue("version_no"));
        vo.setRecordFlag(dbHelper.getItemValue("record_flag"));
        return vo;

    }
}
