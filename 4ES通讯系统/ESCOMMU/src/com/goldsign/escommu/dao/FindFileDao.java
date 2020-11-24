/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.dao;

import com.goldsign.escommu.dbutil.DbHelper;
import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.util.PubUtil;
import com.goldsign.escommu.vo.FindFileVo;
import java.math.BigDecimal;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class FindFileDao {

    private static Logger logger = Logger.getLogger(FindFileDao.class.getName());

    public Vector getFiles() throws Exception {
        boolean result = false;
        DbHelper dbHelper = null;
        FindFileVo vo = null;
        Vector v = new Vector();
        Object[] values = {AppConstant.FTP_STATUS_UNGET};
        Object[] values_1 = {AppConstant.FTP_STATUS_SUCCESS, AppConstant.FTP_STATUS_UNGET};

        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            dbHelper.setAutoCommit(false);
            String sqlStr = "select water_no,device_id,file_name,status,info_time,"
                    + "operator from "+AppConstant.COM_TK_P+"IC_ES_INFO_FILE where status=?  ";

            result = dbHelper.getFirstDocument(sqlStr, values);
            while (result) {
                vo = this.getResultRecord(dbHelper);
                v.add(vo);
                result = dbHelper.getNextDocument();
            }
            //hwj modify 20170801 修改只更新对应流水的订单
            //sqlStr = "update ACC_TK.IC_ES_INFO_FILE set status=?,update_time=sysdate where status=? ";
            //dbHelper.executeUpdate(sqlStr, values_1);
            for(Object findFileObj: v){
                FindFileVo findFileVo = (FindFileVo)findFileObj;
                sqlStr = "update "+AppConstant.COM_TK_P+"IC_ES_INFO_FILE set status=?,update_time=sysdate where status=? ";
                sqlStr += " and water_no=" + findFileVo.getWaterNo() + " ";
                dbHelper.executeUpdate(sqlStr, values_1);
            }

            dbHelper.commitTran();
        } catch (Exception e) {
            //PubUtil.handleExceptionForTranNoThrow(e, logger, dbHelper);
            PubUtil.handleExceptionForTran(e,logger,dbHelper);
        } finally {
            PubUtil.finalProcessForTran(dbHelper);
        }

        return v;
    }

    private FindFileVo getResultRecord(DbHelper dbHelper) throws Exception {

        FindFileVo vo = new FindFileVo();
        vo.setWaterNo(dbHelper.getItemValue("water_no"));
        //vo.setIp(dbHelper.getItemValue("ip"));
        vo.setDeviceId(dbHelper.getItemValue("device_id"));
        vo.setFileName(dbHelper.getItemValue("file_name"));
        vo.setInfoTime(dbHelper.getItemValue("info_time"));
        vo.setOperator(dbHelper.getItemValue("operator"));
        return vo;

    }

    public int updateFileStatus(FindFileVo vo) throws Exception {
        int result = 0;
        DbHelper dbHelper = null;
        Vector v = new Vector();

        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            String sqlStr = "update "+AppConstant.COM_TK_P+"IC_ES_INFO_FILE set status=? ,update_time=sysdate where  water_no=? ";
            Object[] values = {vo.getStatus(), new BigDecimal(vo.getWaterNo())};
            result = dbHelper.executeUpdate(sqlStr, values);

        } catch (Exception e) {
            //logger.error("访问ic_commu_info_file表错误! " + e);
            //PubUtil.handleExceptionNoThrow(e, logger);
            PubUtil.handleException(e,logger);
        } finally {
            PubUtil.finalProcess(dbHelper);

        }

        return result;
    }
}
