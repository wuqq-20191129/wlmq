/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.util;

import com.goldsign.acc.frame.constant.DBConstant;
import com.goldsign.acc.frame.controller.LoginController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.lib.db.util.DbHelper;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 *
 * @author hejj
 */
public class DBUtil {

    public static BasicDataSource getDataSource(HttpServletRequest request, HttpServletResponse response) {
        String filePath = request.getServletContext().getRealPath(DBConstant.PATH_APP_CONTEXT);

        ApplicationContext applicationContext = new FileSystemXmlApplicationContext(filePath);

        BasicDataSource bds = (BasicDataSource) applicationContext.getBean(DBConstant.DATA_SOUCE_ST);
        return bds;

    }
     public static DataSourceTransactionManager getDataSourceTransactionManager(HttpServletRequest request) {
        String filePath = request.getServletContext().getRealPath(DBConstant.PATH_APP_CONTEXT);

        ApplicationContext applicationContext = new FileSystemXmlApplicationContext(filePath);

        DataSourceTransactionManager tranMgr = (DataSourceTransactionManager) applicationContext.getBean(DBConstant.TRANSACTION_MGR_ST);
        
        return tranMgr;

    }
     public static DefaultTransactionDefinition getTransactionDefinition(HttpServletRequest request) {
        String filePath = request.getServletContext().getRealPath(DBConstant.PATH_APP_CONTEXT);

        ApplicationContext applicationContext = new FileSystemXmlApplicationContext(filePath);

        DefaultTransactionDefinition def = (DefaultTransactionDefinition) applicationContext.getBean(DBConstant.TRANSACTION_DEFINITION_ST);
        
        return def;

    }
      public static SqlSessionFactory getSessionFactory(HttpServletRequest request) throws Exception {
        String filePath = request.getServletContext().getRealPath(DBConstant.PATH_APP_CONTEXT);

        ApplicationContext applicationContext = new FileSystemXmlApplicationContext(filePath);

        //SqlSessionFactoryBean ssfb = (SqlSessionFactoryBean) applicationContext.getBean(DBConstant.SQL_SESSION_FACTORY);
        
      //  SqlSessionFactory SqlSessionFactory =ssfb.getObject();
        SqlSessionFactory SqlSessionFactory = (SqlSessionFactory) applicationContext.getBean(DBConstant.SQL_SESSION_FACTORY);
    
                
        return SqlSessionFactory;

    }

    public static String getTextByCode(String code, String strType, List tableFlags)  {
        PubFlag pv = null;
        for (int i = 0; i < tableFlags.size(); i++) {
            pv = (PubFlag) tableFlags.get(i);
            if (pv.getCode().equals(code) && pv.getCode_type().equals(strType)) {
                return pv.getCode_text();
            }
        }
        return code;

    }
      public static String getTextByCode(String code,  List tableFlags)  {
        PubFlag pv = null;
        for (int i = 0; i < tableFlags.size(); i++) {
            pv = (PubFlag) tableFlags.get(i);
            if (pv.getCode().equals(code) ) {
                return pv.getCode_text();
            }
        }
        return code;

    }

}
