/*
 * 文件名：BaseServiceTest
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.bo;

import com.goldsign.frame.constant.FrameDBConstant;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;


/*
 * 〈一句话功能简述〉
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-8-20
 */

public class BaseServiceTest {
    
    public BaseServiceTest() {
    }
    private static Context context;
    
    @BeforeClass
    public static void setUpClass() {
        FrameDBConstant.MAIN_DATASOURCE="java:comp/env/jdbc/db";

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
            dataSource.setURL("jdbc:oracle:thin:@10.2.99.3:1521:ACC");
            dataSource.setUser("acc_st");
            dataSource.setPassword("acc_st");
            context.bind("java:comp/env/jdbc/db", dataSource);

        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
//        try {
//                    context.close();
//            } catch (NamingException e) {
//                    e.printStackTrace();
//            }
    }
    
    @Before
    public void setUp() throws Exception {
    }
    
    @After
    public void tearDown() {
    }

}
