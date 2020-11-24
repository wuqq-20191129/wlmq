package com.goldsign.acc.report.dao;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.goldsign.acc.report.constant.AppConstant;
import com.goldsign.acc.report.thread.ReportThreadManager;
import com.goldsign.acc.report.util.EncryptionUtil;
import com.goldsign.acc.report.util.PubUtil;
import com.goldsign.acc.report.vo.InitResult;
import com.goldsign.acc.report.vo.Report;
import com.goldsign.acc.report.vo.ReportAttribute;
import com.goldsign.acc.report.vo.ReportControlVo;
import com.goldsign.acc.report.vo.ReportDsVo;
import com.goldsign.acc.report.vo.ResultFromProc;
import com.goldsign.lib.db.util.DbHelper;
import java.sql.SQLException;

public class ReportAttributeDAO {

    private static String RANGE_ALL = "*";//模板或报表代码范围，所有
    private static String ENC_FLAG_YES = "1";//密钥已加密
    private static String ENC_FLAG_NO = "0";//密钥没加密
    //private DbHelper dbHelper;
    private static Logger logger = Logger.getLogger(ReportAttributeDAO.class.
            getName());

    public ReportAttributeDAO() {
        //this.dbHelper = AppConstant.REPORT_DBHELPER;
    }

    public void ReportCodeNum() {
    }

    public Vector getReportUserPass() {
        boolean result = false;
        DbHelper dbHelper = null;
        Vector v = new Vector();

        try {
            dbHelper = new DbHelper("", AppConstant.REPORT_DBCPHELPER.getConnection());
            String sqlStr = "select ds_id,ds_user,ds_pass,enc_flag from " + AppConstant.ST_USER + "W_OP_CFG_REPORT_PASS  ";

            result = dbHelper.getFirstDocument(sqlStr);
            String encFlag, password, dsId;
            while (result) {
                ReportDsVo vo = new ReportDsVo();
                encFlag = dbHelper.getItemValue("enc_flag");
                dsId = dbHelper.getItemValue("ds_id");
                vo.setDsId(dsId);
                vo.setDsUser(dbHelper.getItemValue("ds_user"));
                password = dbHelper.getItemValue("ds_pass");
                if (encFlag.equals(ENC_FLAG_NO)) {//密码加密；更新回数据库
                    password = this.getEncPassword(password);
                    this.updatePassword(dbHelper, dsId, password);
                }
                vo.setDsPass(password);

                v.add(vo);
                result = dbHelper.getNextDocument();
            }


        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);

        } finally {
            PubUtil.finalProcess(dbHelper);

        }
        return v;

    }

    public int updatePassword(DbHelper dbHelper, String dsId, String password) throws Exception {
        String sql = "update " + AppConstant.ST_USER + "W_OP_CFG_REPORT_PASS  "
                + "set ds_pass=?,enc_flag=? where ds_id=?";
        Object[] values = {password, ENC_FLAG_YES, dsId};
        int n = dbHelper.executeUpdate(sql, values);
        return n;

    }

    private String getEncPassword(String password) {

        String enPass = new EncryptionUtil().biEncrypt(EncryptionUtil.ENC_KEY, password);
        return enPass;
    }

    @Deprecated
    public Vector delete_getReportUserPass() {
        boolean result = false;
        DbHelper dbHelper = null;
        Vector v = new Vector();

        try {
            dbHelper = new DbHelper("", AppConstant.REPORT_DBCPHELPER.getConnection());
            String sqlStr = "select ds_id,ds_user,ds_pass from OP_CFG_REPORT_PASS ";

            result = dbHelper.getFirstDocument(sqlStr);
            while (result) {
                ReportDsVo vo = new ReportDsVo();
                vo.setDsId(dbHelper.getItemValue("ds_id"));
                vo.setDsUser(dbHelper.getItemValue("ds_user"));
                vo.setDsPass(dbHelper.getItemValue("ds_pass"));

                v.add(vo);
                result = dbHelper.getNextDocument();
            }


        } catch (Exception e) {
            logger.error("Access table op_report_attribute error! " + e);
        } finally {
            try {
                if (dbHelper != null) {
                    dbHelper.closeConnection();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return v;

    }

    private ReportDsVo getDs(Vector ds, String dsId) {
        ReportDsVo vo;
        for (int i = 0; i < ds.size(); i++) {
            vo = (ReportDsVo) ds.get(i);
            if (vo.getDsId().equals(dsId)) {
                return vo;
            }
        }
        return null;
    }

    @Deprecated
    public void delete_getReportAttributeByGroupForThread(String parm, String code) {
        boolean result = false;
        DbHelper dbHelper = null;

        String reportModule = "";
        String reportCode = "";
        String like = "";
        Vector ds = this.getReportUserPass();
        ReportDsVo vo;
        //配置报表模板
        if (parm.length() > 3) { //in terms of 1:99-999 or 2:99-999
            if (parm.length() <= 4) {//in terms of 1:99 or 2:99某一类型报表
                if (parm.substring(0, 1).equals("1")) {
                    like = " and substr(report_module,1,2) like '" + parm.substring(2) + "%'";
                }
                if (parm.substring(0, 1).equals("2")) {
                    like = " and substr(report_module,1,2) not like '" + parm.substring(2) + "%'";
                }
            } else {
                if (parm.substring(0, 1).equals("1")) {
                    like = " and report_module like '" + parm.substring(2) + "%'";
                }
                if (parm.substring(0, 1).equals("2")) {
                    like = " and report_module not like '" + parm.substring(2) + "%'";
                }
            }
        }

        //报表代码
        if (code.length() >= 3) { //in terms of 1:99-999 or 2:99-999
            if (code.substring(0, 1).equals("1")) {
                like = " and report_code like '" + code.substring(2) + "%'";
            }
            if (parm.substring(0, 1).equals("2")) {
                like = " and report_code not like '" + code.substring(2) + "%'";
            }
        }


        try {
            dbHelper = new DbHelper("", AppConstant.REPORT_DBCPHELPER.getConnection());
            String sqlStr = "select report_code,report_name,report_module,report_type,period_type,"
                    + "line_id,card_main_id,card_sub_id,data_table,out_type,ds_id  "
                    + "from op_report_attribute "
                    + "where report_lock='0' "
                    + like + " order by period_type,report_module,report_code";
            logger.info("报表查询:" + sqlStr);
            result = dbHelper.getFirstDocument(sqlStr);
            while (result) {
                ReportAttribute ra = new ReportAttribute();
                reportCode = dbHelper.getItemValue("report_code");
                ra.setReportCode(dbHelper.getItemValue("report_code"));
                ra.setReportName(dbHelper.getItemValue("report_name"));
                ra.setReportModule(dbHelper.getItemValue("report_module"));
                ra.setReportType(dbHelper.getItemValue("report_type"));
                ra.setPeriodType(dbHelper.getItemValue("period_type"));
                ra.setLineId(dbHelper.getItemValue("line_id"));
                ra.setCardMainCode(dbHelper.getItemValue("card_main_id"));
                ra.setCardSubCode(dbHelper.getItemValue("card_sub_id"));
                ra.setDataTable(dbHelper.getItemValue("data_table"));
                ra.setOutType(dbHelper.getItemValue("out_type"));
                ra.setDsId(dbHelper.getItemValue("ds_id"));
                vo = this.getDs(ds, ra.getDsId());
                if (vo == null) {
                    throw new Exception("数据源" + ra.getDsId() + "没有定义访问账户及密码");
                }
                ra.setDsUser(vo.getDsUser());
                ra.setDsPass(vo.getDsPass());

                reportModule = dbHelper.getItemValue("report_module");



                if (this.isInQueue(reportModule, reportCode)) {
                    ReportThreadManager.setReportModuleCodesQueueSeperate(reportModule, ra);//普通队列
                } else if (this.isInPriority(reportModule)) {
                    ReportThreadManager.setReportModuleCodesPrioritySeperate(reportModule, ra);//优先队列
                } else if (this.isInMax(reportModule)) {
                    ReportThreadManager.setReportModuleCodesMaxSeperate(reportModule, ra);//大报表队列
                } else {
                    ReportThreadManager.setReportModuleCodesNomalSeperate(reportModule, ra);//普通报表
                }
                result = dbHelper.getNextDocument();
            }


        } catch (Exception e) {
            logger.error("Access table op_report_attribute error! " + e);
        } finally {
            try {
                if (dbHelper != null) {
                    dbHelper.closeConnection();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public Vector<ReportControlVo> getReportControls(String balanceWaterNo) {
        boolean result = false;
        DbHelper dbHelper = null;
        Vector<ReportControlVo> v = new Vector();

        try {
            dbHelper = new DbHelper("", AppConstant.REPORT_DBCPHELPER.getConnection());
            String sqlStr = "select BALANCE_WATER_NO,REPORT_MODULE,REPORT_CODE,VALID_FLAG "
                    + "from " + AppConstant.ST_USER + "W_ST_SYS_FLOW_REPORT_CTR where balance_water_no=? and valid_flag=?  ";
            String[] values = {balanceWaterNo, AppConstant.VALID_FLAG_YES};

            result = dbHelper.getFirstDocument(sqlStr, values);
            while (result) {
                ReportControlVo vo = new ReportControlVo();
                vo.setBalanceWaterNo(dbHelper.getItemValue("BALANCE_WATER_NO"));
                vo.setReportModule(dbHelper.getItemValue("REPORT_MODULE"));
                vo.setReportCode(dbHelper.getItemValue("REPORT_CODE"));
                vo.setValidFlag(dbHelper.getItemValue("VALID_FLAG"));

                v.add(vo);
                result = dbHelper.getNextDocument();
            }

        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);

        } finally {
            PubUtil.finalProcess(dbHelper);

        }
        return v;

    }

    public ResultFromProc getPeriod(String balanceWaterNo) {
        String procName = AppConstant.ST_USER + "w_up_rp_get_period";
        String sql = "{call " + procName + "(?,?,?,?,?)}"; //存储过程调用语句
        DbHelper dbHelper = null;
        ResultFromProc ret = null;
        boolean result;

        int retCode; //存储过程参数返回的执行结果代码
        String retMsg; //存储过程参数返回的执行结果消息
        int[] pInIndexes = {1, 2}; //存储过程输入参数索引列表
        Object[] pInStmtValues = {balanceWaterNo, "*"}; //存储过程输入参数值
        int[] pOutIndexes = {3, 4, 5}; //存储过程输出参数索引列表
        int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_INTEGER, DbHelper.PARAM_OUT_TYPE_VACHAR,
            DbHelper.PARAM_OUT_TYPE_CURSOR}; //存储过程输出参数值类型
        Vector<ReportAttribute> v = new Vector();
        ReportAttribute vo;
        try {
            dbHelper = new DbHelper("", AppConstant.REPORT_DBCPHELPER.getConnection());
            dbHelper.runStoreProcForOracle(sql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes); //执行存储过程
            /*
             * 取输出参数值
             */
            retCode = dbHelper.getOutParamIntValue(3); //获取返回代码输出参数值
            retMsg = dbHelper.getOutParamStringValue(4); //获取返回消息输出参数值

            logger.info("返回代码：" + retCode + " 返回消息：" + retMsg);
            result = dbHelper.getFirstDocumentForOracle();//判断结果集是否为空
            while (result) {
                vo = this.getVo(dbHelper);
                v.add(vo);
                result = dbHelper.getNextDocument();

            }

            ret = new ResultFromProc(retCode, retMsg, v);
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return ret;

    }

    private ReportAttribute getVo(DbHelper dbHelper) throws SQLException {
        ReportAttribute ra = new ReportAttribute();
        ra.setPeriodDetailType(dbHelper.getItemValue("PERIOD_DETAIL_TYPE"));
        ra.setBeginDate(dbHelper.getItemValue("BEGIN_DATE"));
        ra.setEndDate(dbHelper.getItemValue("END_DATE"));
        return ra;
    }

    public void getReportAttributeByGroupForThread(String balanceWaterNo, InitResult iResult) {
        boolean result = false;
        DbHelper dbHelper = null;

        Vector ds = this.getReportUserPass();
        Vector<ReportControlVo> ReportControls = this.getReportControls(balanceWaterNo);
        ResultFromProc rfp = this.getPeriod(balanceWaterNo);
        Vector<ReportAttribute> periods = rfp.getRetValues();
        ReportDsVo vo;



        try {
            dbHelper = new DbHelper("", AppConstant.REPORT_DBCPHELPER.getConnection());
            String sqlStr = "select report_code,report_name,report_module,report_type,period_type,"
                    + "line_id,station_id,card_main_id,card_sub_id,data_table,out_type,ds_id,generate_date,period_detail_type  "
                    + "from " + AppConstant.ST_USER + "w_rp_cfg_attribute "
                    + "where report_lock='0' "
                    + " order by period_type,report_module,report_code";
            logger.info("报表查询:" + sqlStr);
            result = dbHelper.getFirstDocument(sqlStr);
            while (result) {
                this.getVo(dbHelper, ds, ReportControls, iResult,periods);
                result = dbHelper.getNextDocument();
            }


        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);

        } finally {
            PubUtil.finalProcess(dbHelper);

        }

    }

    private ReportAttribute getVo(DbHelper dbHelper, Vector ds, Vector<ReportControlVo> reportControls, InitResult iResult, Vector<ReportAttribute> periods) throws Exception {
        ReportAttribute ra = new ReportAttribute();
        ReportAttribute period;
        String reportCode = dbHelper.getItemValue("report_code");
        ra.setReportCode(dbHelper.getItemValue("report_code"));
        ra.setReportName(dbHelper.getItemValue("report_name"));
        ra.setReportModule(dbHelper.getItemValue("report_module"));
        ra.setReportType(dbHelper.getItemValue("report_type"));
        ra.setPeriodType(dbHelper.getItemValue("period_type"));
        ra.setLineId(dbHelper.getItemValue("line_id"));
        // ra.setStataionId(dbHelper.getItemValue("station_id"));
        ra.setCardMainCode(dbHelper.getItemValue("card_main_id"));
        ra.setCardSubCode(dbHelper.getItemValue("card_sub_id"));
        ra.setDataTable(dbHelper.getItemValue("data_table"));
        ra.setOutType(dbHelper.getItemValue("out_type"));
        ra.setDsId(dbHelper.getItemValue("ds_id"));
        ra.setGenerateDate(dbHelper.getItemValue("generate_date"));
        ra.setPeriodDetailType(dbHelper.getItemValue("period_detail_type"));
        if (this.isReportCustomed(ra)) {//定制报表设置报表开始时间、结束时间
            period = this.getPeriod(ra.getPeriodDetailType(), periods);
            if (period == null) {
                throw new Exception("报表周期子类型" + ra.getPeriodDetailType() + "没有定义周期的开始、结束时间");
            }
            ra.setBeginDate(period.getBeginDate());
            ra.setEndDate(period.getEndDate());

        }

        ReportDsVo vo = this.getDs(ds, ra.getDsId());
        if (vo == null) {
            throw new Exception("数据源" + ra.getDsId() + "没有定义访问账户及密码");
        }
        ra.setDsUser(vo.getDsUser());
        ra.setDsPass(vo.getDsPass());

        String reportModule = dbHelper.getItemValue("report_module");
 /*       
        if(reportModule.equals("02-032")){
            logger.debug("报表模板："+reportModule);
        }
*/

        //报表放入生成队列
        //通过配置表控制生成报表的种类
        //配置：控制标识 1：生效 0：不生效 范围：* 表示所有
        if (this.isNeedGenReport(reportModule, reportCode, reportControls)) {
            // this.putReportToQueue(reportModule, reportCode, ra);
            //根据当前清算日确定是否需要生成月报、年报、定制报表
            ReportThreadManager.setReportModuleCodes(reportModule, ra, iResult);
        }

        return ra;

    }

    private ReportAttribute getPeriod(String periodDetailType, Vector<ReportAttribute> periods) {
        for (ReportAttribute ra : periods) {
            if (ra.getPeriodDetailType().equals(periodDetailType)) {
                return ra;
            }
        }
        return null;
    }

    private boolean isReportCustomed(ReportAttribute ra) {
        if (ra.getPeriodType().equals(AppConstant.FLAG_PERIOD_CUSTOMED)) {
            return true;
        }
        return false;
    }

    private void putReportToQueue(String reportModule, String reportCode, ReportAttribute ra) {
        if (this.isInQueue(reportModule, reportCode)) {
            ReportThreadManager.setReportModuleCodesQueueSeperate(reportModule, ra);//普通队列
        } else if (this.isInPriority(reportModule)) {
            ReportThreadManager.setReportModuleCodesPrioritySeperate(reportModule, ra);//优先队列
        } else if (this.isInMax(reportModule)) {
            ReportThreadManager.setReportModuleCodesMaxSeperate(reportModule, ra);//大报表队列
        } else {
            ReportThreadManager.setReportModuleCodesNomalSeperate(reportModule, ra);//其他报表
        }

    }

    private boolean isNeedGenReport(String reportModule, String reportCode,
            Vector<ReportControlVo> reportControls) {
        if (reportControls == null || reportControls.isEmpty()) {
            return true;
        }

        for (ReportControlVo vo : reportControls) {
            if (this.compareReport(vo.getReportModule(), vo.getReportCode(), reportModule, reportCode)) {
                return true;
            }

        }
        return false;

    }

    private boolean compareReport(String ctrReportModule, String ctrReportCode,
            String reportModule, String reportCode) {
        boolean bResultModule = this.compareReportUnit(ctrReportModule, reportModule);
        boolean bEqualCode = this.compareReportUnit(ctrReportCode, reportCode);
        return bResultModule & bEqualCode;


    }

    private boolean compareReportUnit(String ctrValue, String value) {

        if (ctrValue.equals(RANGE_ALL)) {
            return true;
        }
        if (ctrValue.equals(value)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 队列报表
     *
     * @param reportModule
     * @param reportCode
     * @return
     */
    public boolean isInQueue(String reportModule, String reportCode) {
        if ((AppConstant.threadDelayQueue == null || AppConstant.threadDelayQueue.length() == 0)
                && (AppConstant.threadDelayQueueReportCode == null || AppConstant.threadDelayQueueReportCode.length() == 0)) {
            return false;
        }

        int index = -1;
        int index1 = -1;
        index = AppConstant.threadDelayQueue.indexOf(reportModule);
        index1 = AppConstant.threadDelayQueueReportCode.indexOf(reportCode);
        if (index == -1 && index1 == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 优先生成的报表
     *
     * @param reportModule
     * @return
     */
    public boolean isInPriority(String reportModule) {
        if (AppConstant.threadPriorityQueue == null || AppConstant.threadPriorityQueue.length() == 0) {
            return false;
        }
        int index = -1;
        index = AppConstant.threadPriorityQueue.indexOf(reportModule);
        if (index == -1) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * 大报表
     *
     * @param reportModule
     * @return
     */
    public boolean isInMax(String reportModule) {
        if (AppConstant.threadMaxQueue == null || AppConstant.threadMaxQueue.length() == 0) {
            return false;
        }
        int index = -1;
        index = AppConstant.threadMaxQueue.indexOf(reportModule);
        if (index == -1) {
            return false;
        } else {
            return true;
        }

    }

    public boolean isInReportModules(Vector reportModules, String reportModule) {
        if (reportModules == null || reportModules.isEmpty()) {
            return false;
        }
        String rm = "";
        boolean result = false;

        for (int i = 0; i < reportModules.size(); i++) {
            rm = (String) reportModules.get(i);
            if (rm.length() == 2) {
                if (reportModule.startsWith(rm)) {
                    return true;
                }
            } else {
                if (reportModule.equals(rm)) {
                    return true;
                }
            }
        }
        return result;
    }
}
