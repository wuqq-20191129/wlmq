/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.esmcs.ftp;

import java.util.List;
import java.util.Vector;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author lenovo
 */
public class CommuFtpTest {
    
    public CommuFtpTest() {
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
     * Test of ftpGetFiles method, of class CommuFtp.
     */
    @Test
    public void testFtpGetFiles() {
        
        System.out.println("ftpGetFiles");
        String server = "127.0.0.1";
        String userName = "ES_ACC";
        String password = "ES_ACC0000";
        Vector fileNames = new Vector();
        fileNames.add("audit_test2.txt");
        fileNames.add("audit_test3.txt");
        fileNames.add("audit_test4.txt");
        String fileName = "audit_test.txt";
        String localDir = "D:/project/csMetro/程序开发/ES票卡生产系统/代码/ESPKMCS/data/download/param";
        String remoteDir = "param";
        CommuFtp instance = new CommuFtp();
        instance.ftpGetFiles(server, userName, password, fileNames, localDir, remoteDir);
        //fail("The test case is a prototype.");
    }

    /**
     * Test of ftpGetFileSingle method, of class CommuFtp.
     */
    @Test
    public void testFtpGetFileSingle() throws Exception {
        
        System.out.println("ftpGetFileSingle");
        String server = "127.0.0.1";
        String userName = "ES_ACC";
        String password = "ES_ACC0000";
        String fileName = "audit_test.txt";
        String localDir = "D:\\testESCOMMU\\audit2";
        String remoteDir = "\\";
        CommuFtp instance = new CommuFtp();
        instance.ftpGetFileSingle(server, userName, password, fileName, localDir, remoteDir);
        //fail("The test case is a prototype.");
    }
}
