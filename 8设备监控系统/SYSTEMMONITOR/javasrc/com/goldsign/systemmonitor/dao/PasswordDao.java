package com.goldsign.systemmonitor.dao;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FrameCharUtil;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.systemmonitor.util.Encryption;
import java.util.ArrayList;
import java.util.Vector;
import org.apache.log4j.Logger;




public class PasswordDao {

    static Logger logger = Logger.getLogger(PasswordDao.class);

    public PasswordDao() {
        super();
        // TODO Auto-generated constructor stub
    }

    public int modifyPassword(String password, String passwordNew) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;

        ArrayList pStmtValues = new ArrayList();
        int result = 0;
        String dbPassword = "";
        Encryption en = new Encryption();
        passwordNew = en.biEncrypt(Encryption.ENC_KEY, passwordNew);
        pStmtValues.add(passwordNew);



        try {
            dbPassword = this.getPassword();
            if (password == null) {
                password = "";
            }
            if (!dbPassword.equals(password)) {
                throw new Exception(FrameCharUtil.IsoToGbk("旧密码不正确"));
            }

            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            dbHelper.setAutoCommit(false);
            strSql = "update mtr_password set password=? ";

            result = dbHelper.executeUpdate(strSql, pStmtValues.toArray());
            if (result != 1) {
                strSql = "insert into mtr_password(password) values(?)";
                result = dbHelper.executeUpdate(strSql, pStmtValues.toArray());
            }
            dbHelper.commitTran();
        } catch (Exception e) {
            FramePubUtil.handleExceptionForTran(e, logger, dbHelper);
        } finally {
            FramePubUtil.finalProcessForTran(dbHelper);
        }
        return result;
    }

    public String getPassword() throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        String password = "";
        Encryption en = new Encryption();
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select password from mtr_password";

            result = dbHelper.getFirstDocument(strSql);

            if (result) {
                password = dbHelper.getItemValue("password");
                password = en.biDecrypt(Encryption.ENC_KEY, password);
            }

        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        return password;
    }
}
