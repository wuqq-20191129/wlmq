/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.esmcs.util;

import com.goldsign.csfrm.env.BaseConstant;
import com.goldsign.csfrm.util.MessageShowUtil;
import com.goldsign.lib.db.util.DbHelper;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DataSourceConnectionFactory;
import org.apache.log4j.Logger;

/**
 *
 * @author limj
 */
public class DbcpHelper {
    private BasicDataSource bds = new BasicDataSource();
    private ConnectionFactory fac = null;
    private int maxWaitTime;
    private static Logger logger = Logger.getLogger(DbcpHelper.class.getName());
    public DbcpHelper(String driverName,String url,String userName,String password,int maxActive,
            int maxIdle,int maxWait)throws ClassNotFoundException{
    
        if(logger.isDebugEnabled()){
            logger.debug("driverName"+ driverName);
            logger.debug("URL:"+url);
            logger.debug("maxActive:"+maxActive);
            logger.debug("maxIdle"+maxIdle);
            logger.debug("maxWait"+maxWait);
        }
        this.maxWaitTime = maxWait;
        bds.setDriverClassName(driverName);
        bds.setUrl(url);
        bds.setUsername(userName);
        bds.setPassword(password);
        bds.setMaxActive(maxActive);
        bds.setMaxIdle(maxIdle);
        bds.setMaxWait(maxWait);
        fac = new DataSourceConnectionFactory(bds);
    }
    
    public DbHelper getDbHelper() throws SQLException{
        return new DbHelper("",getConnection());
    }
    
    public Connection getConnection() throws SQLException{
        Connection con = null;
        for(int i = 0; i<3; i++){
            try{
                if(fac == null){
                    return null;
                }
                con = fac.createConnection();
                break;
            }catch(SQLException e){
                if(i < 2){
                    try{
                        Thread.sleep(this.maxWaitTime);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }else {
                    throw e;
                }
            }
        }
       // con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);//缓存查询
        con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);//实时查询
        return con;
    }
    
    public void close() throws SQLException{
        if(this.bds != null){
            this.bds.close();
        }
        this.bds = null;
        this.fac = null;
    }
    
    /**
     检查数据库连接是否正常*/
    public Boolean checkDBConnect(){
        boolean result = false;
        DbHelper dbHelper = null;
        try{
            dbHelper = new DbHelper("",getConnection());
            String sqlStr = "SELECT 1 FROM dual";
            result = dbHelper.getFirstDocument(sqlStr);
        }catch(Exception e){
            PubUtil.handleDBNotConnected(e,logger);
            return false;
        }finally{
            PubUtil.finalProcess(dbHelper);
        }
        return result;
    }
 
}
