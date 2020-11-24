package com.goldsign.systemmonitor.dao;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.systemmonitor.util.MonitorDBHelper;
import org.apache.log4j.Logger;

public class CommandModuleMappingDao {

    static Logger logger = Logger.getLogger(CommandModuleMappingDao.class);

    public CommandModuleMappingDao() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void updateMenuStatus(String command) throws Exception {
        DbHelper dbHelper = null;
        String sql = null;
        //Object[] values = {command};
        int[] pInIndexes = {1};//存储过程输入参数索引列表
        Object[] pInStmtValues = {command};//存储过程输入参数值
        int[] pOutIndexes = {2};//存储过程输出参数索引列表
        int[] pOutTypes = {
            DbHelper.PARAM_OUT_TYPE_VACHAR
        };//存储过程输出参数值类型
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE_T);
            //dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            sql = "{call UP_MTR_UPDATE_MENUT_STATUS(?,?)} ";
            dbHelper.runStoreProcForOracle(sql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes);//执行存储过程
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
    }
}