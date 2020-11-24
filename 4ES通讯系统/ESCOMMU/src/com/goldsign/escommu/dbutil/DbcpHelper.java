package com.goldsign.escommu.dbutil;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DataSourceConnectionFactory;
import org.apache.log4j.Logger;

public class DbcpHelper {

    private BasicDataSource bds = new BasicDataSource();
    private ConnectionFactory fac = null;
    private int maxWaitTime;
    private static Logger logger = Logger.getLogger(DbcpHelper.class.getName());

    public DbcpHelper(String driverName, String url, String userId, String password, 
            int maxActive, int maxIdle, int maxWait) throws ClassNotFoundException {

        if (logger.isDebugEnabled()) {
            logger.debug("driverName: " + driverName);
            logger.debug("URL: " + url);
            //logger.debug("userId: " + userId);
            //logger.debug("password: " + password);
            logger.debug("maxActive: " + maxActive);
            logger.debug("maxIdle: " + maxIdle);
            logger.debug("maxWait: " + maxWait);
        }
        this.maxWaitTime = maxWait;
        bds.setDriverClassName(driverName);
        //bds.setUrl(url + "/" + dbName);
        bds.setUrl(url);
        bds.setUsername(userId);
        bds.setPassword(password);
        bds.setMaxActive(maxActive);
        bds.setMaxIdle(maxIdle);
        bds.setMaxWait(maxWait);
        //bds.setRemoveAbandoned(true);
        //bds.setRemoveAbandonedTimeout(10);
        fac = new DataSourceConnectionFactory(bds);

    }

    public Connection getConnection() throws SQLException {
        Connection con = null;
        for (int i = 0; i < 3; i++) { //try 3 times connect
            try {
                if (fac == null) {
                    return null;
                }
                con = fac.createConnection();
                //con=bds.getConnection();
                break;
            } catch (SQLException e) {
                if (i < 2) {
                    try {
                        Thread.sleep(this.maxWaitTime);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    throw e;
                }
            }
        }

        //con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        return con;
    }

    public void close() throws SQLException {
        if (this.bds != null) {
            this.bds.close();
        }
        this.bds = null;
        this.fac = null;
    }
}
