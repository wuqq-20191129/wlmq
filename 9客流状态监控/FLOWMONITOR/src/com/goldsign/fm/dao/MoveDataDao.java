/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.fm.dao;

import com.goldsign.fm.common.AppConstant;
import com.goldsign.fm.common.PubUtil;
import com.goldsign.lib.db.util.DbHelper;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class MoveDataDao {

    private static Logger logger = Logger.getLogger(PassDao.class.getName());
    private static final String RESULT_SUCESS = "0";
    private static final String RESULT_FAILURE = "1";

    public void moveData() throws Exception {
        DbHelper dbHelper = null;
        String sql = "exec em_move_data";

        boolean result;
        String sRet;

        try {
            dbHelper = new DbHelper("",AppConstant.dbcpHelper1.getConnection());
            result = dbHelper.getFirstDocument(sql);
            if (result) {
                sRet = dbHelper.getItemValue("result");

                if (sRet.equals(RESULT_FAILURE)) {
                    throw new Exception("数据导历史表失败");
                }
            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

    }
}
