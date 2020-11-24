package com.goldsign.systemmonitor.dao;

import com.goldsign.frame.constant.AppConfig;
import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FrameCharUtil;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.frame.util.FrameUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.systemmonitor.handler.HandlerCommandIqful;
import com.goldsign.systemmonitor.util.NumberUtil;
import com.goldsign.systemmonitor.vo.DataSourceConfig;
import com.goldsign.systemmonitor.vo.IqVo;
import com.goldsign.systemmonitor.vo.SqlVo;
import java.math.BigDecimal;
import java.util.Vector;
import org.apache.log4j.Logger;

public class IqDao {

    static Logger logger = Logger.getLogger(IqDao.class);

    public IqDao() {
        super();
    }

    private String getRate(String rate) {
        if (rate == null || rate.length() == 0) {
            return "";
        }
        int index = rate.indexOf("%");
        if (index != -1) {
            return rate.substring(0, index);
        }
        return rate;
    }

    public Vector queryStatuses() throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;

        IqVo vo;
        Vector v = new Vector();

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select A.ip,A.status,A.status_date,A.free_data,A.used_data_rate,"
                    + "A.backup_ful_size,A.backup_ful_start_time,"
                    + "A.backup_ful_end_time,A.backup_ful_interval,"
                    + "A.backup_inc_size,A.backup_inc_start_time,"
                    + "A.backup_inc_end_time,A.backup_inc_interval,"
                    + "A.remark,"
                    + //"A.job_water_no,A.job_status," +
                    "B.name,B.type ,B.ip_value "
                    + "from mtr_current_iq_info A,mtr_db_code B "
                    + "where A.ip =B.ip(+) order by ip_value";

            result = dbHelper.getFirstDocument(strSql);

            while (result) {
                vo = new IqVo();
                vo.setIp(dbHelper.getItemValue("ip"));
                vo.setStatus(dbHelper.getItemValue("status"));
                vo.setStatusDate(dbHelper.getItemValue("status_date"));

                vo.setFreeData(dbHelper.getItemValue("free_data"));
                vo.setUsedDataRate(this.getRate(dbHelper.getItemValue("used_data_rate")));

                vo.setBackupFulSize(dbHelper.getItemValue("backup_ful_size"));
                vo.setBackupFulStartTime(dbHelper.getItemValue("backup_ful_start_time"));
                vo.setBackupFulEndTime(dbHelper.getItemValue("backup_ful_end_time"));
                vo.setBackupFulInterval(dbHelper.getItemValue("backup_ful_interval"));

                vo.setBackupIncSize(dbHelper.getItemValue("backup_inc_size"));
                vo.setBackupIncStartTime(dbHelper.getItemValue("backup_inc_start_time"));
                vo.setBackupIncEndTime(dbHelper.getItemValue("backup_inc_end_time"));
                vo.setBackupIncInterval(dbHelper.getItemValue("backup_inc_interval"));
                vo.setRemark(dbHelper.getItemValueIso("remark"));
                vo.setName(dbHelper.getItemValueIso("name"));
                vo.setType(dbHelper.getItemValue("type"));
                v.add(vo);

                result = dbHelper.getNextDocument();
            }

        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        return v;

    }

    private Vector getDbConfig() throws Exception {

        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        Vector v = new Vector();
        DataSourceConfig vo;
        String[] values = {FrameDBConstant.Db_type_iq};

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "SELECT type,ip,segement_name,datasource, remark "
                    + "FROM mtr_db_config where type=? order by ip";


            result = dbHelper.getFirstDocument(strSql, values);

            while (result) {
                vo = new DataSourceConfig();
                vo.setType(dbHelper.getItemValue("type"));
                vo.setIp(dbHelper.getItemValue("ip"));
                vo.setSegementName(dbHelper.getItemValue("segement_name"));
                vo.setDatasource(dbHelper.getItemValue("datasource"));
                vo.setRemark(dbHelper.getItemValueIso("remark"));
                v.add(vo);
                result = dbHelper.getNextDocument();
            }

        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        return v;
    }

    private IqVo getIqVo(String[] lineValues) {
        String usedDataRate = lineValues[0];
        String freeData = lineValues[0];
        IqVo vo = new IqVo();
        int start = freeData.indexOf(FrameDBConstant.Command_iq_db_key_start);
        int end = freeData.indexOf(FrameDBConstant.Command_iq_db_key_end);
        usedDataRate = usedDataRate.substring(0, start);
        if (end != -1) {
            freeData = freeData.substring(start + 1, end);
        } else {
            end = freeData.indexOf(FrameDBConstant.Command_iq_db_key_end_1);
            if (end != -1) {
                freeData = freeData.substring(start + 1, end);
                freeData = this.getFreeData(freeData);

            }
        }
        vo.setFreeData(freeData);
        vo.setUsedDataRate(usedDataRate);
        return vo;
    }

    private String getFreeData(String freeData) {
        BigDecimal bFreeData = NumberUtil.getBigDecimalValue(freeData, "0", 3);

        BigDecimal bDiv = new BigDecimal("1000.000");

        bFreeData = bFreeData.divide(bDiv, 3, BigDecimal.ROUND_HALF_UP);
        return bFreeData.toString();
    }

    private IqVo getDbInfo(DataSourceConfig vo) throws Exception {
        IqVo svo = new IqVo();
        String sql = "exec sp_iqstatus ";

        DbHelper dbHelper = null;
        boolean result = false;

        String fieldName;
        String fieldValue;
        HandlerCommandIqful util = new HandlerCommandIqful();
        int[] fieldIndexes = {1};
        String[] lineValues;
        try {
            dbHelper = new DbHelper(AppConfig.getJndiPrefix()
                    + vo.getDatasource());

            result = dbHelper.getFirstDocument(sql);

            while (result) {

                fieldName = dbHelper.getItemValue("Name");
                fieldValue = dbHelper.getItemValue("Value");
                if (fieldName.indexOf(FrameDBConstant.Command_iq_db_key) != -1) {
                    lineValues = util.getLineFields(fieldValue, fieldIndexes, FrameDBConstant.Command_seperator_comma);
                    svo = this.getIqVo(lineValues);
                    svo.setIp(vo.getIp());
                    break;
                }
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        return svo;
    }

    private void getDbInfoForJobsDefault(IqVo svo) {
        svo.setJobStatus("");
        svo.setJobWaterNo("");
    }

    private IqVo getDbInfoForJobs(DataSourceConfig vo, IqVo svo) throws Exception {
        //IqVo svo =new IqVo();
        String sql = "select lastwaterno,jobstatus from dba.etl_job "
                + "where lastwaterno in (select max(lastwaterno) from dba.etl_job)  "
                + "group by  lastwaterno,jobstatus ";

        DbHelper dbHelper = null;
        boolean result = false;

        String jobWaterNo = "";
        String jobStatusName;
        String jobStatus = "0";

        try {
            dbHelper = new DbHelper(AppConfig.getJndiPrefix()
                    + vo.getDatasource());

            result = dbHelper.getFirstDocument(sql);
            if (!result) {
                jobStatus = "";
                jobWaterNo = "";
            }
            while (result) {

                jobWaterNo = dbHelper.getItemValue("lastwaterno");
                jobStatusName = dbHelper.getItemValue("jobStatus");
                if (jobStatusName.equals(FrameDBConstant.Command_iq_db_key_job_failure)) {
                    jobStatus = "1";
                    break;
                }
                result = dbHelper.getNextDocument();
            }
            svo.setJobStatus(jobStatus);
            //svo.setJobStatus("1");
            svo.setJobWaterNo(jobWaterNo);

        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        return svo;
    }

    private boolean isNeedJobInfo(DataSourceConfig vo) {
        for (int i = 0; i < FrameDBConstant.Command_iq_ip_jobs.length; i++) {
            if (vo.getIp().equals(FrameDBConstant.Command_iq_ip_jobs[i])) {
                return true;
            }
        }
        return false;
    }

    public Vector getDbInfos() throws Exception {
        Vector v = new Vector();
        Vector dbConfigs = this.getDbConfig();
        DataSourceConfig vo = null;
        IqVo svo;
        for (int i = 0; i < dbConfigs.size(); i++) {
            try {
                vo = (DataSourceConfig) dbConfigs.get(i);
                svo = this.getDbInfo(vo);
                if (this.isNeedJobInfo(vo))//增加作业的信息

                {
                    this.getDbInfoForJobs(vo, svo);
                } else {
                    this.getDbInfoForJobsDefault(svo);
                }
                v.add(svo);
            } catch (Exception e) {
                if (vo != null) {
                    this.updateStatusForError(vo.getIp());
                }
                e.printStackTrace();
            }
        }
        return v;
    }

    public int updateStatusForError(String ip) {

        String sql = null;

        int n = 0;

        String[] values = {FrameDBConstant.Status_failure, ip};
        DbHelper dbHelper = null;
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE_T);
            sql = "update mtr_current_iq_info set status=?  where ip=? ";
            n = dbHelper.executeUpdate(sql, values);
        } catch (Exception e) {
            FramePubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        return n;
    }

    public void addStatus(IqVo vo, DbHelper dbHelper) throws Exception {

        String sql = null;

        int n;
//		ip,status,status_date,free_data,used_data_rate,free_index,used_index_rate,
        //backup_size,backup_start_time,backup_end_time,backup_interval,remark
        String[] values = {vo.getStatus(),
            vo.getStatusDate(),
            vo.getFreeData(),
            vo.getUsedDataRate(),
            vo.getBackupFulSize(),
            vo.getBackupFulStartTime(),
            vo.getBackupFulEndTime(),
            vo.getBackupFulInterval(),
            vo.getBackupIncSize(),
            vo.getBackupIncStartTime(),
            vo.getBackupIncEndTime(),
            vo.getBackupIncInterval(),
            FrameCharUtil.GbkToIso(vo.getRemark()),
            vo.getJobWaterNo(),
            vo.getJobStatus()
        };
        String[] valuesWhere = {
            vo.getIp()};

        String[] values1 = {
            vo.getIp(),
            vo.getStatus(),
            vo.getStatusDate(),
            vo.getFreeData(),
            vo.getUsedDataRate(),
            vo.getBackupFulSize(),
            vo.getBackupFulStartTime(),
            vo.getBackupFulEndTime(),
            vo.getBackupFulInterval(),
            vo.getBackupIncSize(),
            vo.getBackupIncStartTime(),
            vo.getBackupIncEndTime(),
            vo.getBackupIncInterval(),
            FrameCharUtil.GbkToIso(vo.getRemark()),
            vo.getJobWaterNo(),
            vo.getJobStatus()
        };
        String[] fieldNames = {"status", "status_date", "free_data", "used_data_rate",
            "backup_ful_size", "backup_ful_start_time", "backup_ful_end_time", "backup_ful_interval",
            "backup_inc_size", "backup_inc_start_time", "backup_inc_end_time", "backup_inc_interval", "remark",
            "job_water_no", "job_status"};
        String[] fieldNames1 = {"ip", "status", "status_date", "free_data", "used_data_rate",
            "backup_ful_size", "backup_ful_start_time", "backup_ful_end_time", "backup_ful_interval",
            "backup_inc_size", "backup_inc_start_time", "backup_inc_end_time", "backup_inc_interval", "remark",
            "job_water_no", "job_status"};
        String[] fieldNamesWhere = {"ip"};
        FrameUtil util = new FrameUtil();

        SqlVo sqlVoUpdate = util.getSqlForUpdate(values, fieldNames, "mtr_current_iq_info", valuesWhere, fieldNamesWhere);
        SqlVo sqlVoInsert = util.getSqlForInsert(values1, fieldNames1, "mtr_current_iq_info");
        SqlVo sqlVoInsertHis = util.getSqlForInsert(values1, fieldNames1, "mtr_his_iq_info");
        sql = sqlVoUpdate.getSql();
        n = dbHelper.executeUpdate(sql, sqlVoUpdate.getValues().toArray());
        if (n != 1) {
            sql = sqlVoInsert.getSql();
            n = dbHelper.executeUpdate(sql, sqlVoInsert.getValues().toArray());
        }
        sql = sqlVoInsertHis.getSql();
        n = dbHelper.executeUpdate(sql, sqlVoInsertHis.getValues().toArray());
    }

    public void addStatuses(Vector vos) throws Exception {
        DbHelper dbHelper = null;
        IqVo vo;

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE_T);
            dbHelper.setAutoCommit(false);
            for (int i = 0; i < vos.size(); i++) {
                vo = (IqVo) vos.get(i);
                this.addStatus(vo, dbHelper);
            }
            dbHelper.commitTran();
        } catch (Exception e) {
            FramePubUtil.handleExceptionForTran(e, logger, dbHelper);
        } finally {
            FramePubUtil.finalProcessForTran(dbHelper);
        }
    }
}
