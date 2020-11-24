/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.util.EncryptionUtil;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.ConfigBcpVo;
import com.goldsign.settle.realtime.frame.vo.SynchronizedControl;


import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class ConfigBcpDao {

    private static final SynchronizedControl SYN = new SynchronizedControl();
     private static final SynchronizedControl SYN_2 = new SynchronizedControl();
    private static final SynchronizedControl SYN1 = new SynchronizedControl(4);
    private static Logger logger = Logger.getLogger(ConfigBcpDao.class.getName());

    public ConfigBcpVo getConfigBcp() throws Exception {

        String sql = "select db_server,db_sid,db_account,db_password,enc_flag,remark "
                + "from "+FrameDBConstant.DB_PRE+"st_cfg_bcp ";
        DbHelper dbHelper = null;
        boolean result;
        ConfigBcpVo vo = null;
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql);
            if (result) {
                vo = this.getConfigBcpVo(dbHelper);
                if (!this.isEnc(vo.getEncFlag())) {
                    this.encPass(vo.getDbPassword());
                }

            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return vo;
    }

    public ConfigBcpVo getConfigBcpForAutoCommit() throws Exception {
     //   synchronized (SYN) {

            String sql = "select db_server,db_sid,db_account,db_password,enc_flag,remark "
                    + "from "+FrameDBConstant.DB_PRE+"st_cfg_bcp ";
            DbHelper dbHelper = null;
            boolean result;
            ConfigBcpVo vo = null;
            try {
                dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
                result = dbHelper.getFirstDocument(sql);
                if (result) {
                    vo = this.getConfigBcpVo(dbHelper);

                    this.encPassForAutoCommit(vo.getDbPassword());


                }
            } catch (Exception e) {
                PubUtil.handleException(e, logger);
            } finally {
                PubUtil.finalProcess(dbHelper);
            }

            return vo;
     //   }
    }

    public ConfigBcpVo getConfigBcpForSynControl() throws Exception {
        ConfigBcpVo vo;
       // logger.info("当前同步的访问数：" + SYN1.getCurrentSyn());
        SYN1.plusCurrentSyn();

        if (SYN1.isNeedSyn()) {
           // logger.info("当前同步的访问数：" + SYN1.getCurrentSyn());
            logger.info("执行同步");
            synchronized (SYN) {
                vo = this.getConfigBcpForSyn();
            }
        } else {
            synchronized (SYN) {
                 logger.info("执行同步2");
                vo = this.getConfigBcpForSyn();
            }
        }
       // SYN1.subCurrentSyn();
        return vo;


    }

    public ConfigBcpVo getConfigBcpForSynControlCode() throws Exception {
        ConfigBcpVo vo;
        SYN.plusCurrentSyn();

        if (SYN.isNeedSyn()) {
            logger.info("当前同步的访问数：" + SYN.getCurrentSyn());
            //logger.info("执行同步");
            synchronized (SYN) {
                vo = this.getConfigBcpForSyn();
            }
        } else {
            synchronized (SYN) {
                vo = this.getConfigBcpForSyn();
            }
        }
        SYN.subCurrentSyn();
        return vo;


    }

    public ConfigBcpVo getConfigBcpForSyn() throws Exception {



        String sql = "select db_server,db_sid,db_account,db_password,enc_flag,remark "
                + "from "+FrameDBConstant.DB_PRE+"st_cfg_bcp ";
        DbHelper dbHelper = null;
        boolean result;
        ConfigBcpVo vo = null;
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql);
            if (result) {
                vo = this.getConfigBcpVo(dbHelper);

                this.encPassForAutoCommit(vo.getDbPassword());


            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return vo;

    }

    public ConfigBcpVo getConfigBcpForTran() throws Exception {

        String sql = "select db_server,db_sid,db_account,db_password,enc_flag,remark "
                + "from "+FrameDBConstant.DB_PRE+"st_cfg_bcp ";
        DbHelper dbHelper = null;
        boolean result;
        ConfigBcpVo vo = null;
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            dbHelper.setAutoCommit(false);
            result = dbHelper.getFirstDocument(sql);
            if (result) {
                vo = this.getConfigBcpVo(dbHelper);
                if (!this.isEnc(vo.getEncFlag())) {
                    this.encPassForTran(vo.getDbPassword(), dbHelper);
                }

            }
            dbHelper.commitTran();
        } catch (Exception e) {
            PubUtil.handleExceptionForTran(e, logger, dbHelper);
        } finally {
            PubUtil.finalProcessForTran(dbHelper);
        }

        return vo;
    }

    private ConfigBcpVo getConfigBcpVo(DbHelper dbHelper) throws SQLException {
        ConfigBcpVo vo = new ConfigBcpVo();
        String pass = dbHelper.getItemValue("db_password");
        vo.setDbServer(dbHelper.getItemValue("db_server"));
        vo.setDbSid(dbHelper.getItemValue("db_sid"));
        vo.setDbAccount(dbHelper.getItemValue("db_account"));
        vo.setEncFlag(dbHelper.getItemValue("enc_flag"));
        vo.setDbPassword(this.getPassword(pass, vo.getEncFlag()));
        vo.setRemark(dbHelper.getItemValue("remark"));
        return vo;
    }

    private String getPassword(String password, String encFlag) {
        if (!this.isEnc(encFlag)) {
            return password;
        }
        return new EncryptionUtil().biDecrypt(FrameCodeConstant.KEY, password);
    }

    private boolean isEnc(String encFlag) {
        if (encFlag == null || encFlag.trim().length() == 0
                || encFlag.equals("0")) {
            return false;
        }
        if (encFlag.equals("1")) {
            return true;
        }
        return false;
    }

    public int encPass(String password) throws Exception {
        DbHelper dbHelper = null;
        int result = 0;
        String sql = "update  "+FrameDBConstant.DB_PRE+"st_cfg_bcp set db_password=? ,enc_flag=? ";

        String encPass = new EncryptionUtil().biEncrypt(FrameCodeConstant.KEY,
                password);
        String[] values = {encPass, FrameCodeConstant.FLAG_ENC};
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.executeUpdate(sql, values);

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return result;

    }

    public int encPassForAutoCommit(String password) throws Exception {
        DbHelper dbHelper = null;
        int result = 0;
        String sql = "update  "+FrameDBConstant.DB_PRE+"st_cfg_bcp set remark=? ";

        String encPass = new EncryptionUtil().biEncrypt(FrameCodeConstant.KEY,
                password);
        String[] values = {encPass};
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
           // dbHelper.setTransactonIsolationLevel(DbHelper.TRANSACTION_READ_COMMITTED);
            // result = dbHelper.executeUpdateForOracleAutoCommit(sql, values);
            result = dbHelper.executeUpdate(sql, values);
            //Thread.sleep(10);

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return result;

    }

    public int encPassForTran(String password, DbHelper dbHelper) throws Exception {

        int result = 0;
        String sql = "update  "+FrameDBConstant.DB_PRE+"st_cfg_bcp set db_password=? ,enc_flag=? ";

        String encPass = new EncryptionUtil().biEncrypt(FrameCodeConstant.KEY,
                password);
        String[] values = {encPass, FrameCodeConstant.FLAG_ENC};
        result = dbHelper.executeUpdate(sql, values);

        return result;

    }
}
