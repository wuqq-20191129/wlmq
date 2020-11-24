/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.dao;
import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.env.FileConstant;
import com.goldsign.escommu.dbutil.DbHelper;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.util.PubUtil;
import com.goldsign.escommu.vo.EsFileReqVo;
import com.goldsign.escommu.vo.EsFileRspVo;
import com.goldsign.escommu.vo.FileAudErrVo;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class EsFileDao {
    private static Logger logger = Logger.getLogger(EsFileDao.class.
			getName());
    
    public List<EsFileRspVo> esFileNotice(List<EsFileReqVo> esFileReqVos) throws Exception {
        boolean result = false;
        DbHelper dbHelper = null;
        
        List<EsFileRspVo> esFileRspVos = new ArrayList<EsFileRspVo>();
        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            //dbHelper.setAutoCommit(false);
            for(EsFileReqVo esFileReqVo: esFileReqVos){
                Object[] values = {esFileReqVo.getDeviceId(), esFileReqVo.getFileName(), 
                    esFileReqVo.getOperCode()};
                Object[] values2 = {esFileReqVo.getDeviceId(), esFileReqVo.getFileName(), 
                    esFileReqVo.getOperCode(),new java.sql.Timestamp(new Date().getTime())};

                EsFileRspVo esFileRspVo = null;
                //********************************************
                String sqlStr = "select * from "+AppConstant.COM_TK_P+"IC_ES_INFO_FILE where device_id=? and file_name=? and operator=? and status='0'"; 
                String sqlStr2 = "insert into "+AppConstant.COM_TK_P+"IC_ES_INFO_FILE(water_no,device_id,file_name,operator,status,info_time)"+
                              " values("+AppConstant.COM_TK_P+"SEQ_"+AppConstant.TABLE_PREFIX+"IC_ES_INFO_FILE.nextval,?,?,?,'0',?)";
                dbHelper.setAutoCommit(false);
                result = dbHelper.getFirstDocument(sqlStr, values);
                if (!result) {
                    dbHelper.executeUpdate(sqlStr2, values2);
                }
                esFileRspVo = getResultRecord(dbHelper);
                esFileRspVos.add(esFileRspVo);
                
                dbHelper.commitTran();
            }
        } catch (Exception e) {
            //PubUtil.handleExceptionForTranNoThrow(e,logger,dbHelper);
            PubUtil.handleExceptionForTran(e,logger,dbHelper);
        } finally {
            PubUtil.finalProcessForTran(dbHelper);
        }

        return esFileRspVos;
    }
    
    private EsFileRspVo  getResultRecord(DbHelper dbHelper) throws Exception{
        
        EsFileRspVo esFileRspVo = new EsFileRspVo();

       

        return esFileRspVo;
    }
    
    public List<FileAudErrVo> getAuditFiles() {

        List<FileAudErrVo> files = new ArrayList<FileAudErrVo>();
        boolean result = false;
        DbHelper dbHelper = null;

        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            String sqlStr = "select water_no,device_id,file_name,info_time,operator from "+AppConstant.COM_TK_P+"IC_ES_INFO_FILE"
                    + " where to_char(info_time+1,'yyyyMMdd')=?";
            Object[] values = {DateHelper.dateToYYYYMMDD(new Date())};
            result = dbHelper.getFirstDocument(sqlStr, values);
            while (result) {
                
                String waterNo = dbHelper.getItemValue("water_no");
                String deviceId = dbHelper.getItemValue("device_id");
                String fileName = dbHelper.getItemValue("file_name");
                String info_time = dbHelper.getItemValue("info_time");
                String operator = dbHelper.getItemValue("operator");
                
                FileAudErrVo file = new FileAudErrVo();
                file.setWaterNo(waterNo);
                file.setDeviceId(deviceId);
                file.setFileName(fileName);
                file.setInfoTime(info_time);
                file.setInfoOperator(operator);
                
                files.add(file);
                
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return files;
    }
    
    public List<FileAudErrVo> getErrorFiles(){
    
        List<FileAudErrVo> files = new ArrayList<FileAudErrVo>();
        boolean result = false;
        DbHelper dbHelper = null;

        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            String sqlStr = "select water_no,device_id,file_name,error_code,gen_time,info_time,info_operator "
                    + " from "+AppConstant.COM_TK_P+"IC_ES_FILE_ERROR "
                    + " where to_char(gen_time+1,'yyyyMMdd')=?";
            Object[] values = {DateHelper.dateToYYYYMMDD(new Date())};
            result = dbHelper.getFirstDocument(sqlStr, values);
            while (result) {
                
                String waterNo = dbHelper.getItemValue("water_no");
                String deviceId = dbHelper.getItemValue("device_id");
                String fileName = dbHelper.getItemValue("file_name");
                String error_code = dbHelper.getItemValue("error_code");
                String gen_time = dbHelper.getItemValue("gen_time");
                String info_time= dbHelper.getItemValue("info_time");
                String operator = dbHelper.getItemValue("info_operator");
                
                FileAudErrVo file = new FileAudErrVo();
                file.setWaterNo(waterNo);
                file.setDeviceId(deviceId);
                file.setFileName(fileName);
                file.setErrorCode(error_code);
                file.setGenTime(gen_time);
                file.setInfoTime(info_time);
                file.setInfoOperator(operator);
                
                files.add(file);
                
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return files;
    }
    
    public List<FileAudErrVo> getAudtErrorFiles(String deviceIp){
        
        List<FileAudErrVo> files = new ArrayList<FileAudErrVo>();
        boolean result = false;
        DbHelper dbHelper = null;

        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            String sqlStr = "select water_no, device_id, file_name, info_time, info_operator from "+AppConstant.COM_TK_P+"IC_ES_FILE_AUDIT";
            sqlStr += " where status=? and device_id=?";
            Object[] values = {FileConstant.FLAG_AUDIT_NOTICE_NO, deviceIp};
            result = dbHelper.getFirstDocument(sqlStr, values);
            while (result) {
                
                String waterNo = dbHelper.getItemValue("water_no");
                String deviceId = dbHelper.getItemValue("device_id");
                String fileName = dbHelper.getItemValue("file_name");
                String info_time= dbHelper.getItemValue("info_time");
                String operator = dbHelper.getItemValue("info_operator");
                
                FileAudErrVo file = new FileAudErrVo();
                file.setWaterNo(waterNo);
                file.setDeviceId(deviceId);
                file.setFileName(fileName);
                file.setInfoTime(info_time);
                file.setInfoOperator(operator);
                
                files.add(file);
                
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return files;    
    }
    
    public void updateAuditErrorFiles(List<FileAudErrVo> auditErrorFiles) throws Exception{
        
        DbHelper dbHelper = null;
      
        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            dbHelper.setAutoCommit(false);
         
            String sqlStr = "update "+AppConstant.COM_TK_P+"IC_ES_FILE_AUDIT set status=? where water_no=? ";
            for(FileAudErrVo auditErrorFile: auditErrorFiles){
                Object[] values = {FileConstant.FLAG_AUDIT_NOTICE, Long.parseLong(auditErrorFile.getWaterNo())};
                dbHelper.executeUpdate(sqlStr, values);
            }
            dbHelper.commitTran();

        } catch (Exception e) {
            //PubUtil.handleExceptionForTranNoThrow(e, logger, dbHelper);
            PubUtil.handleExceptionForTran(e,logger,dbHelper);
        } finally {
            PubUtil.finalProcessForTran(dbHelper);

        }
    }

    public void insertAuditFile(String deviceId, String fileName) throws Exception {
        
        DbHelper dbHelper = null;

        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            dbHelper.setAutoCommit(false);
            Object[] values = {deviceId, fileName, new Timestamp(new Date().getTime()), 
                FileConstant.AUDIT_FILE_NOTICE_OPER, FileConstant.FLAG_AUDIT_NOTICE_NO};
            String sqlStr = "insert into "+AppConstant.COM_TK_P+"IC_ES_FILE_AUDIT(water_no,device_id,file_name,info_time,info_operator,status) "
                    + "values("+AppConstant.COM_TK_P+"SEQ_"+AppConstant.TABLE_PREFIX+"IC_ES_FILE_AUDIT.nextval,?,?,?,?,?)";
           
            dbHelper.executeUpdate(sqlStr, values);

            dbHelper.commitTran();

        } catch (Exception e) {
            //PubUtil.handleExceptionForTranNoThrow(e, logger, dbHelper);
            PubUtil.handleExceptionForTran(e,logger,dbHelper);
        } finally {
            PubUtil.finalProcessForTran(dbHelper);

        }
    }
}
