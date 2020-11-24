/*
  AFCLogger.java 1.0 2013-04-12
  
*/
package com.goldsign.rule.util;

import com.goldsign.frame.constant.FrameCodeConstant;
import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.rule.env.RuleConstant;
import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;


/**
 * <p>Title:AFC操作日志</p> <p>Description: 日志表增加数据</p> <p>Copyright: Copyright (c)
 * 2013</p> <p>Company: golsign</p>
 *
 * @author liamgminglong
 * @version 1.0
 */
public class AFCLogger {

    public final static String OP_ADD = "增加";
    public final static String OP_DELETE = "删除";
    public final static String OP_MODIFY = "修改";
    public final static String OP_QUERY = "查询";
    public final static String OP_REPORTQUERY = "报表查询";
    public final static String OP_STATISTIC = "报表统计";
    public final static String OP_CLONE = "克隆";
    public final static String OP_SUBMIT = "提交";
    public final static String OP_IMPORT = "导入";
    public final static String OP_CHECK = "盘点";
    public final static String OP_DISTRIBUTE = "分配";
    public final static String OP_DOWNLOAD = "下发";
    public final static String OP_UPDATE = "确认退款";
    public final static String OP_HMD = "拒绝退款";
    public final static String OP_AUDIT = "审核";

    public AFCLogger() {
    }

    public static synchronized int logger(String operatorID, String moduleID, String opType, String description) throws Exception {
            DbHelper dbHelper = null;
            String strSql = null;
            Logger logger = Logger.getLogger(AFCLogger.class);
            ArrayList pStmtValues = new ArrayList();
            int result = 0;
            String opTime = DateHelper.datetimeToString(new Date());

            pStmtValues.add(operatorID);
            pStmtValues.add(opTime);
            pStmtValues.add(moduleID);
            pStmtValues.add(opType);
            pStmtValues.add(description);
            pStmtValues.add(FrameCodeConstant.SYS_FLAG);

            try{
                dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
                strSql = "insert into SR_LOG(serialno,operator_id,op_time,module_id,oper_type,description,sys_type)"
                        + " values(SEQ_SR_LOG.nextval,?,to_date(?,'yyyy-MM-dd hh24:mi:ss'),?,?,?,?)";
                result =dbHelper.executeUpdate(strSql,pStmtValues.toArray());
            } catch (Exception e) {
                FramePubUtil.handleException(e, logger);
            } finally {
                FramePubUtil.finalProcess(dbHelper);
            }
            return result;

    }

}
