/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.rule.test;

import com.goldsign.lib.db.util.DbHelper;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;

/**
 *
 * @author lenovo
 */
public class TestJndi {

    private static final String jndi = "java:comp/env/jdbc/db_1";
    
    public static void main(String[] args) throws Exception{
    
        bindJndi();
        DbHelper dbHelper =  new DbHelper(jndi);
        boolean result = dbHelper.getFirstDocument("select 1 r from dual");
        System.out.println(result);
    }
    
    public static void bindJndi(){

        try {
            // Create initial context
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
            System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
            Context context = new InitialContext();
            context.createSubcontext("java:");
            context.createSubcontext("java:comp");
            context.createSubcontext("java:comp/env");
            context.createSubcontext("java:comp/env/jdbc");
            // Construct DataSource
            OracleConnectionPoolDataSource dataSource = new OracleConnectionPoolDataSource();
            dataSource.setURL("jdbc:oracle:thin:@172.20.19.203:1521:epay1");
            dataSource.setUser("acc_st");
            dataSource.setPassword("acc_st");
            context.bind(jndi, dataSource);

        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
