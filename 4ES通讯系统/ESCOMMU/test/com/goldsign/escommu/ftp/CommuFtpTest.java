/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.ftp;

import com.goldsign.escommu.env.AppConstant;
import java.util.Hashtable;
import java.util.Vector;
import static org.junit.Assert.fail;
import org.junit.*;

/**
 *
 * @author lenovo
 */
public class CommuFtpTest {
    
    public CommuFtpTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        
        AppConstant.FTP_PATHS = new Hashtable();
        AppConstant.FtpLocalDir = "D:\\testESCOMMU\\order2";
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
        String userName = "ACC_ES";
        String password = "ACC_ES0000";
        Vector fileNames = new Vector();
        fileNames.add("ES_TEST2.txt");
        fileNames.add("ES_TEST3.txt");
        fileNames.add("ES_TEST4.txt");
        CommuFtp instance = new CommuFtp();
        AppConstant.FTP_PATHS.put("ES", "\\testIn\\");
        instance.ftpGetFiles(server, userName, password, fileNames);
        //fail("The test case is a prototype.");
    }

    /**
     * Test of ftpGetFileSingle method, of class CommuFtp.
     */
    @Test
    public void testFtpGetFileSingle() throws Exception {
        
        System.out.println("ftpGetFileSingle");
        String server = "127.0.0.1";
        String userName = "ACC_ES";
        String password = "ACC_ES0000";
        String fileName = "ES_TEST.txt";
        CommuFtp instance = new CommuFtp();
        AppConstant.FTP_PATHS.put("ES", "\\");
        instance.ftpGetFileSingle(server, userName, password, fileName);
        //fail("The test case is a prototype.");
    }
}
