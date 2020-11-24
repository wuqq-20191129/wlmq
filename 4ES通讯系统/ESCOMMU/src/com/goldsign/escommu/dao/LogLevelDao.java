package com.goldsign.escommu.dao;


import com.goldsign.escommu.dbutil.DbHelper;
import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.util.PubUtil;
import com.goldsign.escommu.vo.SysLogLevelVo;
import org.apache.log4j.Logger;



public class LogLevelDao {
    
    private static Logger logger = Logger.getLogger(LogLevelDao.class.getName());

    public LogLevelDao() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public SysLogLevelVo getLogLevel() {
        boolean result = false;
        DbHelper dbHelper = null;
        SysLogLevelVo vo = null;

        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            String sqlStr = "select sys_code,sys_level,set_time,operator,"
                    + "version_no,record_flag from "+AppConstant.COM_CM_P+"CM_EC_LOG_LEVEL where "
                    + "record_flag=? and sys_code=? ";
            Object[] values = {AppConstant.RECORD_FLAG_CURRENT, AppConstant.SYSTEM_COMMU};
            result = dbHelper.getFirstDocument(sqlStr, values);
            if (result) {
                vo = this.getResultRecord(dbHelper);
            }
        } catch (Exception e) {
            //logger.error("访问snd_sys_log_level表错误! " + e);
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
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
