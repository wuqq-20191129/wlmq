/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.etmcs.service;

import com.goldsign.etmcs.vo.OperateLogParam;
import com.goldsign.etmcs.vo.OperateLogVo;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author lenovo
 */
public class IOperateLogServiceTest {
    
    public IOperateLogServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getOperaterLogs method, of class IOperateLogService.
     */
    @Test
    public void testGetOperaterLogs() {
        System.out.println("getOperaterLogs");
        OperateLogParam operateLogParam = null;
        IOperateLogService instance = new IOperateLogServiceImpl();
        List expResult = null;
        List result = instance.getOperaterLogs(operateLogParam);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of insertOperaterLogs method, of class IOperateLogService.
     */
    @Test
    public void testInsertOperaterLogs() {
        System.out.println("insertOperaterLogs");
        OperateLogVo vo = null;
        IOperateLogService instance = new IOperateLogServiceImpl();
        instance.insertOperaterLogs(vo);
        fail("The test case is a prototype.");
    }

    public class IOperateLogServiceImpl implements IOperateLogService {

        public List<Object[]> getOperaterLogs(OperateLogParam operateLogParam) {
            return null;
        }

        public void insertOperaterLogs(OperateLogVo vo) {
        }
    }
}
