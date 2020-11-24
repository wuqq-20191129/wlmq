/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.sammcs.service;

import com.goldsign.csfrm.env.BaseConstant;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.sammcs.env.AppConstant;
import com.goldsign.sammcs.service.impl.OperateLogService;
import com.goldsign.sammcs.util.DbcpHelper;
import com.goldsign.sammcs.vo.OperateLogVo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Administrator
 */
public class IOperateLogServiceTest {
    
    public IOperateLogServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        DbcpHelper dbcpHelper = null;
        String driverName = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@172.20.19.203:1521:epay1";
        String userId =  "acc_tk";
        String password = "acc_tk";
        String maxActive = "40";
        String maxIdle = "20";
        String maxWait = "20000";
        try {
            dbcpHelper = new DbcpHelper(driverName, url, userId, password,
                    StringUtil.getInt(maxActive), StringUtil.getInt(maxIdle), StringUtil.getInt(maxWait));
            
            AppConstant.dbcpHelper = dbcpHelper;
            
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of insertOperaterLogs method, of class IOperateLogService.
     */
    @Test
    public void testInsertOperaterLogs() {
        System.out.println("insertOperaterLogs");
        OperateLogVo logVo = new OperateLogVo();
        IOperateLogService instance = new OperateLogService();
        logVo.setOperId("testUser");
        logVo.setModuleId("7101");
        logVo.setOperType("方法测试");
        logVo.setDescription("方法测试");
        instance.insertOperaterLogs(logVo);
        System.out.println("插入日志记录！");
    }

    public class IOperateLogServiceImpl implements IOperateLogService {

        public void insertOperaterLogs(OperateLogVo vo) {
        }
    }
}
