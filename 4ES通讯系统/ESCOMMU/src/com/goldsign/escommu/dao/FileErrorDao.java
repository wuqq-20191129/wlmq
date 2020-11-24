/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.dao;

import com.goldsign.escommu.dbutil.DbHelper;
import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.env.FileConstant;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.util.FileUtil;
import com.goldsign.escommu.util.PubUtil;
import com.goldsign.escommu.vo.FileErrorVo;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class FileErrorDao {

    private static Logger logger = Logger.getLogger(FileErrorDao.class.getName());

    public int insertErrorFiles(Vector filesNames) throws Exception {
        int result = 0;
        DbHelper dbHelper = null;

        Vector v = new Vector();
        FileUtil fUtil = new FileUtil();
        v = fUtil.getFileErrorVoByFileNames(filesNames, FileConstant.FILE_ERRO_CODE_FILE_NAME);
        FileErrorVo vo;

        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            dbHelper.setAutoCommit(false);
            for (int i = 0; i < v.size(); i++) {
                vo = (FileErrorVo) v.get(i);
                this.insertErrorFile(vo, dbHelper);

            }
            dbHelper.commitTran();

        } catch (Exception e) {
            PubUtil.handleExceptionForTran(e, logger, dbHelper);
        } finally {
            PubUtil.finalProcessForTran(dbHelper);

        }

        return result;
    }

    public int insertErrorFilesForOne(String filesName, String errorCode) throws Exception {
        int result = 0;
        DbHelper dbHelper = null;

        //Vector v = new Vector();
        FileUtil fUtil = new FileUtil();
        FileErrorVo vo = fUtil.getFileErrorVoByFileNamesForOne(filesName, errorCode);
        //FileErrorVo vo ;

        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            dbHelper.setAutoCommit(false);
            // for(int i=0;i<v.size();i++){
            //     vo =(FileErrorVo) v.get(i);
            this.insertErrorFile(vo, dbHelper);

            //  }
            dbHelper.commitTran();

        } catch (Exception e) {
            PubUtil.handleExceptionForTran(e, logger, dbHelper);
        } finally {
            PubUtil.finalProcessForTran(dbHelper);

        }

        return result;
    }

    private int insertErrorFile(FileErrorVo vo, DbHelper dbHelper) throws Exception {
        
        Object[] values = {vo.getDeviceId(), vo.getFileName(), vo.getErrorCode(), 
            DateHelper.utilDateToSqlTimestamp(DateHelper.getDatetimeFromYYYY_MM_DD_HH_MM_SS(vo.getGenTime())), 
            vo.getInfoFlag(), vo.getRemark()};
        String sql = "insert into "+AppConstant.COM_TK_P+"IC_ES_FILE_ERROR(water_no,device_id,file_name,error_code,gen_time,info_flag,remark)"
                + " values("+AppConstant.COM_TK_P+"SEQ_"+AppConstant.TABLE_PREFIX+"IC_ES_FILE_ERROR.nextval,?,?,?,?,?,?)";
        int n = dbHelper.executeUpdate(sql, values);
        return n;


    }
}
