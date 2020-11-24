/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.util;

import com.goldsign.acc.frame.constant.DBConstant;
import com.goldsign.acc.frame.constant.ParameterConstant;
import com.goldsign.acc.frame.entity.PubFlag;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

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
        if(code==null || code.equals("")){
            return code;
        }
        for (int i = 0; i < tableFlags.size(); i++) {
            pv = (PubFlag) tableFlags.get(i);
            if(pv.getCode()!= null && !pv.getCode().equals("")){
                if ((pv.getCode().trim()).equals(code.trim()) ) {
                    return pv.getCode_text();
                }
            }
        }
        return code;

    }
      
    //获取数据库的sqlSessionFactroy 
    public static SqlSessionFactory getSqlSessionFactory() throws Exception { 
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();   
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory)wac.getBean(DBConstant.SQL_SESSION_FACTORY);
        return sqlSessionFactory;
    }
    
    //获取数据库的连接
    public static Connection getConnection()throws Exception {
        Connection connection = getSqlSessionFactory().openSession().getConnection();
        return connection;
    }
    
     /**
     * 
     * @param tableName 表名
     * @param fieldName 字段名
     * @param fieldValue 字段值
     * @param idName id名
     * @param idValue id值
     * @param isParamTable 是否参数 草稿版本
     * @return
     * @throws Exception 
     */
    public static boolean IsSameFieldValueInTable(String tableName, String fieldName,
            String fieldValue,String idName ,String idValue,boolean isParamTable) throws Exception {
        String strSql = null;

        ArrayList pStmtValues = new ArrayList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null; 
        
        if(isParamTable){
            pStmtValues.add(ParameterConstant.RECORD_FLAG_DRAFT);
        }
        pStmtValues.add(fieldValue);
        try {
            connection = getConnection();
            strSql = "select " + fieldName + " from " + tableName
                    + " where  "  ;
            if(isParamTable){
                strSql += " record_flag=? and ";
            }
            strSql += fieldName + "=?";
            if(!idName.isEmpty()&&!idValue.isEmpty()){
                strSql += " and "+idName+" !=?";
                pStmtValues.add(idValue);
            }
            
            ps= connection.prepareStatement(strSql);
            for (int i = 0; i < pStmtValues.size(); i++) {
                ps.setObject(i+1, pStmtValues.get(i));
            }
            resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();  
            }
            if (ps != null) {
                ps.close();  
            }
            if (connection != null) {
                connection.close();
            }
        }
        return resultSet.next();
    } 

    /**
     * 判断在数据库表是否存在重复的内容
     *
     * @param tableName 表名
     * @param fieldName 字段名
     * @param fieldValue 字段值
     * @param isParamTable 是否参数
     * @return
     * @throws Exception
     */
    public static boolean IsSameFieldValueInTable(String tableName, String fieldName,
            String fieldValue,boolean isParamTable) throws Exception {
        String strSql = null;

        ArrayList pStmtValues = new ArrayList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null; 
        
        if(isParamTable){
            pStmtValues.add(ParameterConstant.RECORD_FLAG_DRAFT);
        }
        pStmtValues.add(fieldValue);
        try {
            connection = getConnection();
//            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select " + fieldName + " from " + tableName
                    + " where  " ;
            if(isParamTable){
                strSql += " record_flag=? and ";
            }
            strSql +=  fieldName + "=?";
            ps= connection.prepareStatement(strSql);
            for (int i = 0; i < pStmtValues.size(); i++) {
                ps.setObject(i+1, pStmtValues.get(i));
            }
            resultSet = ps.executeQuery();
            
            return resultSet.next();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();  
            }
            if (ps != null) {
                ps.close();  
            }
            if (connection != null) {
                connection.close();
            }
        }
        return resultSet.next();
    }

    /**
     * 取sequence Id
     *
     * @param seqName
     * @param dbHelper
     * @return
     * @throws Exception
     */
    public static int getTableSequence(String seqName) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        String strSql = "select W_ACC_ST." + seqName + ".nextval from dual";
        try {
            connection = getConnection();
            ps= connection.prepareStatement(strSql);
            resultSet = ps.executeQuery();
            while(resultSet.next()){   
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();  
            }
            if (ps != null) {
                ps.close();  
            }
            if (connection != null) {
                connection.close();
            }
        }
        return -1;
    }
}
