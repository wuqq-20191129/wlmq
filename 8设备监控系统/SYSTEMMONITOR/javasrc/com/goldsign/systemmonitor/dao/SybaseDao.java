package com.goldsign.systemmonitor.dao;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FrameCharUtil;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.frame.util.FrameUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.systemmonitor.vo.SqlVo;
import com.goldsign.systemmonitor.vo.SybaseVo;
import java.util.Vector;
import org.apache.log4j.Logger;

public class SybaseDao {

    static Logger logger = Logger.getLogger(SybaseDao.class);

    public SybaseDao() {
        super();
    }

    public Vector queryStatuses() throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        SybaseVo vo;
        Vector v = new Vector();
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select A.ip,A.status,A.status_date,A.free_data,A.used_data_rate,"
                    + "A.free_data_1,A.used_data_rate_1,"
                    + "A.free_index,A.used_index_rate,A.backup_size,A.backup_start_time,"
                    + "A.backup_end_time,A.backup_interval,A.remark,"
                    + "A.free_log,A.used_log_rate,"
                    + "A.name,B.type ,B.ip_value "
                    + "from mtr_current_sybase_info A,mtr_db_code B "
                    + "where A.ip =B.ip(+) order by ip";

            result = dbHelper.getFirstDocument(strSql);

            while (result) {
                vo = new SybaseVo();
                vo.setIp(dbHelper.getItemValue("ip"));
                vo.setStatus(dbHelper.getItemValue("status"));
                vo.setStatusDate(dbHelper.getItemValue("status_date"));
                vo.setFreeData(dbHelper.getItemValue("free_data"));
                vo.setUsedDataRate(dbHelper.getItemValue("used_data_rate"));
                vo.setFreeData_1(dbHelper.getItemValue("free_data_1"));
                vo.setUsedDataRate_1(dbHelper.getItemValue("used_data_rate_1"));
                vo.setFreeIndex(dbHelper.getItemValue("free_index"));
                vo.setUsedIndexRate(dbHelper.getItemValue("used_index_rate"));
                vo.setBackupSize(dbHelper.getItemValue("backup_size"));
                vo.setBackupStartTime(dbHelper.getItemValue("backup_start_time"));
                vo.setBackupEndTime(dbHelper.getItemValue("backup_end_time"));
                vo.setBackupInterval(dbHelper.getItemValue("backup_interval"));
                vo.setFreeLog(dbHelper.getItemValue("free_log"));
                vo.setUsedLogRate(dbHelper.getItemValue("used_log_rate"));
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
/*
    private Vector getDbConfig() throws Exception {

        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        Vector v = new Vector();
        DataSourceConfig vo;
        String[] values = {FrameDBConstant.Db_type_sybase};
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "SELECT type,ip,segement_name,datasource, remark "
                    + "FROM mtr_db_config where type=? order by ip";
            result = dbHelper.getFirstDocument(strSql, values);
            while (result) {
                vo = new DataSourceConfig();
                vo.setType(dbHelper.getItemValue("type"));
                vo.setIp(dbHelper.getItemValue("ip"));
                vo.setSegementName(dbHelper.getItemValueIso("segement_name"));
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
   
    private Vector getDbConfig(String type) throws Exception {

        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        Vector v = new Vector();
        DataSourceConfig vo;
        String[] values = {type};
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "SELECT type,ip,segement_name,datasource, remark "
                    + "FROM mtr_db_config where type=? order by ip";
            result = dbHelper.getFirstDocument(strSql, values);
            while (result) {
                vo = new DataSourceConfig();
                vo.setType(dbHelper.getItemValue("type"));
                vo.setIp(dbHelper.getItemValue("ip"));
                vo.setSegementName(dbHelper.getItemValueIso("segement_name"));
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
 
     private SybaseVo getSybaseVo(String tableSpaceName, String totalMb, String usedPct,String usedMb) {
       
     BigDecimal bTotalPages = NumberUtil.getBigDecimalValue(totalPages, "1", 2);
     BigDecimal bFreePages = NumberUtil.getBigDecimalValue(freePages, "0", 2);
     BigDecimal bUsedPages = NumberUtil.getBigDecimalValue(usedPages, "0", 2);
     BigDecimal bMul = new BigDecimal("2.00");
     BigDecimal bDiv = new BigDecimal("1024.00");
     BigDecimal bMul_1 = new BigDecimal("100");

     BigDecimal free = bFreePages.multiply(bMul).divide(bDiv, BigDecimal.ROUND_HALF_UP);
     BigDecimal usedRate = bUsedPages.divide(bTotalPages, 2, BigDecimal.ROUND_HALF_UP).multiply(bMul_1);
     usedRate.setScale(2);
       
     SybaseVo vo = new SybaseVo();
     if (tableSpaceName.equals(FrameDBConstant.Segment_name_default)) {
     vo.setFreeData_1("");
     vo.setUsedDataRate_1("");

     vo.setFreeData(free.toString());
     vo.setUsedDataRate(usedRate.toString());
     vo.setFreeIndex("");
     vo.setUsedIndexRate("");
     vo.setFreeLog("");
     vo.setUsedLogRate("");

     }
     if (tableSpaceName.equals(FrameDBConstant.Segment_name_index)) {
     vo.setFreeData_1("");
     vo.setUsedDataRate_1("");

     vo.setFreeData("");
     vo.setUsedDataRate("");
     vo.setFreeIndex(free.toString());
     vo.setUsedIndexRate(usedRate.toString());
     vo.setFreeLog("");
     vo.setUsedLogRate("");
     }
     if (tableSpaceName.equals(FrameDBConstant.Segment_name_log)) {
     vo.setFreeData_1("");
     vo.setUsedDataRate_1("");
     vo.setFreeData("");
     vo.setUsedDataRate("");
     vo.setFreeIndex("");
     vo.setUsedIndexRate("");
     vo.setFreeLog(free.toString());
     vo.setUsedLogRate(usedRate.toString());
     }
     if (tableSpaceName.equals(FrameDBConstant.Segment_name_data_1)) {
     vo.setFreeData_1(free.toString());
     vo.setUsedDataRate_1(usedRate.toString());
     vo.setFreeData("");
     vo.setUsedDataRate("");
     vo.setFreeIndex("");
     vo.setUsedIndexRate("");
     vo.setFreeLog("");
     vo.setUsedLogRate("");
     }
     return vo;
     }
     *  */
    /*
     private SybaseVo getDbInfo(DataSourceConfig vo) throws Exception {
     SybaseVo svo = new SybaseVo();
     String sql = "exec sp_helpsegment ?";
     String[] values = {vo.getSegementName()};
     MonitorDBHelper dbHelper = null;
     boolean result = false;
     String totalSize;
     String totalPages;
     String freePages;
     String usedPages;
     try {
     dbHelper = new MonitorDBHelper(AppConfig.getJndiPrefix() + vo.getDatasource());
     result = dbHelper.getLastDocumentSP(sql, values, "total_pages");
     if (result) {

     totalSize = dbHelper.getItemValue("total_size");
     totalPages = dbHelper.getItemValue("total_pages");
     freePages = dbHelper.getItemValue("free_pages");
     usedPages = dbHelper.getItemValue("used_pages");
     svo = this.getSybaseVo(vo.getSegementName(), totalSize, totalPages, freePages, usedPages);
     svo.setIp(vo.getIp());
     }

     } catch (Exception e) {
     FramePubUtil.handleException(e, logger);
     } finally {
     FramePubUtil.finalProcess(dbHelper);
     }
     return svo;
     }
     

    private SybaseVo getDbInfoForError(DataSourceConfig vo) throws Exception {
        SybaseVo svo = new SybaseVo();
        svo.setStatus(FrameDBConstant.Status_failure);
        svo.setIp(vo.getIp());
        return svo;
    }
    * */
    /*
     private SybaseVo getDbInfoForDbcc(DataSourceConfig vo) throws Exception {
     SybaseVo svo = new SybaseVo();
     String sql = " dbcc checktable(?) ";
     String[] values = {vo.getSegementName()};
     MonitorDBHelper dbHelper = null;
     boolean result = false;
     String totalSize;
     String totalPages;
     String freePages;
     String usedPages;
     try {
     dbHelper = new MonitorDBHelper(AppConfig.getJndiPrefix()
     + vo.getDatasource());
     result = dbHelper.getLastDocumentSP(sql, values, "total_pages");
     if (result) {
     totalSize = dbHelper.getItemValue("total_size");
     totalPages = dbHelper.getItemValue("total_pages");
     freePages = dbHelper.getItemValue("free_pages");
     usedPages = dbHelper.getItemValue("used_pages");
     svo = this.getSybaseVo(vo.getSegementName(), totalSize, totalPages, freePages, usedPages);
     svo.setIp(vo.getIp());
     }
     } catch (Exception e) {
     FramePubUtil.handleException(e, logger);
     } finally {
     FramePubUtil.finalProcess(dbHelper);
     }
     return svo;
     }
    

     public Vector getDbInfos() throws Exception {
     Vector v = new Vector();
     Vector dbConfigs = this.getDbConfig();
     DataSourceConfig vo = null;
     SybaseVo svo;
     for (int i = 0; i < dbConfigs.size(); i++) {
     try {
     vo = (DataSourceConfig) dbConfigs.get(i);
     svo = this.getDbInfo(vo);
     v.add(svo);
     } catch (Exception e) {
     //异常时更新对应的状态为错误
     this.updateStatusForError(vo.getIp());
     e.printStackTrace();
     }
     }
     return v;
     }
     */

    public Vector getDbMessage(String ip) throws Exception {
        
        String sql = "select total.tablespace_name,round(total.MB, 2) as Total_MB,round(total.MB - free.MB, 2) as Used_MB,"
                + "   DECODE(TRUNC(round((1 - free.MB / total.MB) * 100, 2)), 0, REPLACE(TO_CHAR(round((1 - free.MB / total.MB) * 100, 2)),'.','0.'), TO_CHAR(round((1 - free.MB / total.MB) * 100, 2)))||'%' as Used_Pct from (select tablespace_name, sum(bytes) / 1024 / 1024 as MB "
                + "from dba_free_space group by tablespace_name) free,(select tablespace_name, sum(bytes) / 1024 / 1024 as MB "
                + "from dba_data_files where tablespace_name in ('TBS_ST_DATA','TBS_TK_DATA','ACC_COMMU','ACC_ONLINE','ACC_STLRULE','ACC_MONITOR','TBS_ST_HIS_DATA','TBS_ST_IDX','TBS_ST_HIS_IDX') "
                + "group by tablespace_name) total where free.tablespace_name = total.tablespace_name";
        //String[] values = {vo.getSegementName()};
        DbHelper dbHelper = null;
        boolean result = false;
        String totalMb;
        String usedPct;
        String usedMb;
        String tableSpaceName;
        Vector resultList = new Vector();


        try {
            //处理40
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);//test
            //处理11
            //dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE_MON);
            result = dbHelper.getFirstDocument(sql);
            logger.info(sql);
            while (result) {
                SybaseVo svo = new SybaseVo();
                tableSpaceName = dbHelper.getItemValue("tablespace_name");
                totalMb = dbHelper.getItemValue("total_mb");
                usedPct = dbHelper.getItemValue("used_pct");
                usedMb = dbHelper.getItemValue("used_mb");
                //System.out.println("usedMb===="+usedMb);
                svo.setFreeIndex(usedMb);
                svo.setUsedIndexRate(usedPct);
                svo.setIp(ip);
                if(tableSpaceName.equals("ACC_COMMU")){
                    tableSpaceName="通讯系统表空间";
                }else if(tableSpaceName.equals("ACC_MONITOR")){
                    tableSpaceName="监控系统表空间";
                }else if(tableSpaceName.equals("ACC_ONLINE")){
                    tableSpaceName="充值在线系统表空间";
                }else if(tableSpaceName.equals("TBS_TK_DATA")){
                    tableSpaceName="票务系统表空间";
                }else if(tableSpaceName.equals("TBS_ST_DATA")){
                    tableSpaceName="运营系统表空间";
                }else if(tableSpaceName.equals("ACC_STLRULE")){
                    tableSpaceName="清分规则系统表空间";
                }else if(tableSpaceName.equals("TBS_ST_HIS_DATA")){
                    tableSpaceName="运营系统历史表空间";
                }else if(tableSpaceName.equals("TBS_ST_IDX")){
                    tableSpaceName="索引空间";
                }else if(tableSpaceName.equals("TBS_ST_HIS_IDX")){
                    tableSpaceName="历史表索引空间";
                }
                svo.setName(tableSpaceName);
                resultList.add(svo);
                result = dbHelper.getNextDocument();
            }

        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        return resultList;
    }
    /*
     public Vector getDbInfosForDbcc() throws Exception {
     Vector v = new Vector();
     Vector dbConfigs = this.getDbConfig(FrameDBConstant.Db_type_sybase_dbcc);
     DataSourceConfig vo;
     SybaseVo svo;
     for (int i = 0; i < dbConfigs.size(); i++) {
     try {
     vo = (DataSourceConfig) dbConfigs.get(i);
     svo = this.getDbInfo(vo);
     v.add(svo);
     } catch (Exception e) {
     e.printStackTrace();
     }
     }
     return v;
     }
     */

    public void addStatus(SybaseVo vo, DbHelper dbHelper) throws Exception {
        String sql = null;
        int n;
//		ip,status,status_date,free_data,used_data_rate,free_index,used_index_rate,
        //backup_size,backup_start_time,backup_end_time,backup_interval,remark
        Object[] values = {vo.getStatus(),
            vo.getStatusDate(),
            vo.getFreeData(),
            vo.getUsedDataRate(),
            vo.getFreeIndex(),
            vo.getUsedIndexRate(),
            vo.getBackupSize(),
            vo.getBackupStartTime(),
            vo.getBackupEndTime(),
            vo.getBackupInterval(),
            FrameCharUtil.GbkToIso(vo.getRemark()),
            vo.getIp()};
        Object[] values1 = {
            vo.getIp(),
            vo.getStatus(),
            vo.getStatusDate(),
            vo.getFreeData(),
            vo.getUsedDataRate(),
            vo.getFreeIndex(),
            vo.getUsedIndexRate(),
            vo.getBackupSize(),
            vo.getBackupStartTime(),
            vo.getBackupEndTime(),
            vo.getBackupInterval(),
            FrameCharUtil.GbkToIso(vo.getRemark()),};
        sql = "update mtr_current_sybase_info set status=?,status_date=to_date(?,'yyyy-mm-dd hh24:mi:ss'),free_data=?,used_data_rate=?,"
                + "free_index=?,used_index_rate=?,backup_size=?,backup_start_time=?,backup_end_time=?,"
                + "backup_interval=?,remark=? "
                + "where ip=? ";

        n = dbHelper.executeUpdate(sql, values);
        if (n != 1) {
            sql = "insert into mtr_current_sybase_info(ip,status,status_date,free_data,used_data_rate,"
                    + "free_index,used_index_rate,backup_size,backup_start_time,backup_end_time,backup_interval,remark) "
                    + "values(?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?,?,?,?,?) ";
            n = dbHelper.executeUpdate(sql, values1);
        }
        sql = "insert into mtr_his_sybase_info(ip,status,status_date,free_data,used_data_rate,"
                + "free_index,used_index_rate,backup_size,backup_start_time,backup_end_time,backup_interval,remark) "
                + "values(?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?,?,?,?,?) ";
        n = dbHelper.executeUpdate(sql, values1);
    }

    public void updateStatus(SybaseVo vo) throws Exception {
        String sql = null;
        int n;
//		ip,status,status_date,free_data,used_data_rate,free_index,used_index_rate,
        //backup_size,backup_start_time,backup_end_time,backup_interval,remark
        //free_log,used_log_rate
        Object[] values = {
            vo.getFreeLog(),
            vo.getUsedLogRate(),
            vo.getIp()};
        Object[] values1 = {vo.getIp(),
            vo.getStatus(),
            vo.getStatusDate(),
            vo.getFreeLog(),
            vo.getUsedLogRate()
        };

        DbHelper dbHelper = null;

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE_T);
            dbHelper.setAutoCommit(false);
            sql = "update mtr_current_sybase_info set free_log=? ,used_log_rate=? where ip=? ";
            n = dbHelper.executeUpdate(sql, values);
            if (n != 1) {
                sql = "insert into mtr_current_sybase_info(ip,status,status_date,free_log,used_log_rate)"
                        + " values(?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?) ";
                n = dbHelper.executeUpdate(sql, values1);
            }
            sql = "insert into mtr_his_sybase_info(ip,status,status_date,free_log,used_log_rate) "
                    + " values(?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?) ";
            n = dbHelper.executeUpdate(sql, values1);

            dbHelper.commitTran();

        } catch (Exception e) {
            FramePubUtil.handleExceptionForTran(e, logger, dbHelper);
        } finally {
            FramePubUtil.finalProcessForTran(dbHelper);
        }




    }

    public int updateStatusForError(String ip) {
        String sql = null;
        int n = 0;
        String[] values = {FrameDBConstant.Status_failure, ip};
        DbHelper dbHelper = null;

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE_T);
            sql = "update mtr_current_sybase_info set status=?  where ip=? ";
            n = dbHelper.executeUpdate(sql, values);
        } catch (Exception e) {
            FramePubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        return n;
    }

    public void addStatusByField(SybaseVo vo, DbHelper dbHelper) throws Exception {

        String sql = null;
        int n;
        String[] values = {vo.getStatus(),
            vo.getStatusDate(),
            vo.getFreeData(),
            vo.getUsedDataRate(),
            vo.getFreeIndex(),
            vo.getUsedIndexRate(),
            vo.getBackupSize(),
            vo.getBackupStartTime(),
            vo.getBackupEndTime(),
            vo.getBackupInterval(),
            FrameCharUtil.GbkToIso(vo.getRemark()),
            vo.getFreeLog(),
            vo.getUsedLogRate(),
            vo.getFreeData_1(),
            vo.getUsedDataRate_1(),
            vo.getName()
        };

        String[] valuesWhere = {
            vo.getIp(), vo.getName()};

        String[] values1 = {
            vo.getIp(),
            vo.getStatus(),
            vo.getStatusDate(),
            vo.getFreeData(),
            vo.getUsedDataRate(),
            vo.getFreeIndex(),
            vo.getUsedIndexRate(),
            vo.getBackupSize(),
            vo.getBackupStartTime(),
            vo.getBackupEndTime(),
            vo.getBackupInterval(),
            FrameCharUtil.GbkToIso(vo.getRemark()),
            vo.getFreeLog(),
            vo.getUsedLogRate(),
            vo.getFreeData_1(),
            vo.getUsedDataRate_1(),
            vo.getName()
        };

        String[] fieldNames = {"status", "status_date", "free_data", "used_data_rate",
            "free_index", "used_index_rate", "backup_size", "backup_start_time",
            "backup_end_time", "backup_interval", "remark", "free_log", "used_log_rate",
            "free_data_1", "used_data_rate_1", "name"};
        String[] fieldNames1 = {"ip", "status", "status_date", "free_data", "used_data_rate",
            "free_index", "used_index_rate", "backup_size", "backup_start_time",
            "backup_end_time", "backup_interval", "remark", "free_log", "used_log_rate",
            "free_data_1", "used_data_rate_1", "name"};
        String[] fieldNamesWhere = {"ip", "name"};
        FrameUtil util = new FrameUtil();
        SqlVo sqlVoUpdate = util.getSqlForUpdate(values, fieldNames, "mtr_current_sybase_info", valuesWhere, fieldNamesWhere);
        SqlVo sqlVoInsert = util.getSqlForInsert(values1, fieldNames1, "mtr_current_sybase_info");
        SqlVo sqlVoInsertHis = util.getSqlForInsert(values1, fieldNames1, "mtr_his_sybase_info");

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
        SybaseVo vo;
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);//test
            dbHelper.setAutoCommit(false);
            for (int i = 0; i < vos.size(); i++) {
                vo = (SybaseVo) vos.get(i);
                this.addStatusByField(vo, dbHelper);
            }
            dbHelper.commitTran();
        } catch (Exception e) {
            FramePubUtil.handleExceptionForTran(e, logger, dbHelper);
        } finally {
            FramePubUtil.finalProcessForTran(dbHelper);
        }
    }
}
