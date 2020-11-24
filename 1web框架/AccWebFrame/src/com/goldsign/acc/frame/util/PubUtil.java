/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.util;

import com.goldsign.lib.db.util.DbHelper;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class PubUtil {
    public static void finalProcess(DbHelper dbHelper, Logger logger) {
        try {
            if (dbHelper != null) {
                dbHelper.closeConnection();
            }
        } catch (SQLException e) {
            logger.error("关闭连接错误", e);
        }
    }

    public static void finalProcessForTran(DbHelper dbHelper, Logger logger) {
        try {

            if (dbHelper != null) {
                dbHelper.closeConnection();
            }

        } catch (SQLException e) {
            logger.error("\"关闭连接错误", e);
        }
    }
    
}
