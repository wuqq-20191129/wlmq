/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.dao;

import com.goldsign.escommu.dbutil.DbHelper;
import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.util.Encryption;
import com.goldsign.escommu.util.PubUtil;
import com.goldsign.escommu.vo.ImportConfig;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class BcpDao {

    private static Logger logger = Logger.getLogger(BcpDao.class.getName());

    public ImportConfig getImportConfig() throws Exception {
        DbHelper dbHelper = null;
        boolean result = false;
        String sql = "select server,db,account,password,enc_flag,remark from "+AppConstant.COM_TK_P+"IC_ES_BCP_CONFIG";
        ImportConfig vo = null;
        String password;
        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql);
            if (result) {
                vo = new ImportConfig();
                vo.setServer(dbHelper.getItemValue("server"));
                vo.setDb(dbHelper.getItemValue("db"));
                vo.setAccount(dbHelper.getItemValue("account"));
                vo.setPassword(dbHelper.getItemValue("password"));
                vo.setEncFlag(dbHelper.getItemValue("enc_flag"));
                vo.setRemark(dbHelper.getItemValue("remark"));
                // 获取密码，如密码加密了，应解密
                password = this.getPassword(vo.getPassword(), vo.getEncFlag());
                // 如密码没有加密，加密后写入库
                if (!this.isEnc(vo.getEncFlag())) {
                    this.encPass(vo.getPassword());
                }

                vo.setPassword(password);

            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return vo;

    }

    private String getPassword(String password, String encFlag) {
        if (!this.isEnc(encFlag)) {
            return password;
        }
        return new Encryption().biDecrypt(AppConstant.KEY, password);
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
        String sql = "update "+AppConstant.COM_TK_P+"IC_ES_BCP_CONFIG set password=? ,enc_flag=? ";

        String encPass = new Encryption().biEncrypt(AppConstant.KEY,
                password);
        String[] values = {encPass, AppConstant.FLAG_ENC};
        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.executeUpdate(sql, values);

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return result;

    }
}
