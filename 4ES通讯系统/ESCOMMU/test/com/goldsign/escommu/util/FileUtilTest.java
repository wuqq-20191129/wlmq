/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.util;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.env.ConfigTagConstant;
import com.goldsign.escommu.exception.ConfigFileException;
import com.goldsign.escommu.vo.FileErrorVo;
import com.goldsign.escommu.vo.ImportConfig;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.*;

/**
 * 
 * @author lenovo
 */
public class FileUtilTest {
    
    public FileUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        Hashtable commuConfig = new Hashtable();
        Hashtable ht = new Hashtable();
        ht.put(ConfigTagConstant.Driver_TAG, "oracle.jdbc.driver.OracleDriver");
        ht.put(ConfigTagConstant.URL_TAG, "jdbc:oracle:thin:@172.20.19.203:1521:epay1");
        ht.put(ConfigTagConstant.User_TAG, "acc_st");
        ht.put(ConfigTagConstant.Password_TAG, "acc_st");
        ht.put(ConfigTagConstant.MaxActive_TAG, "40");
        ht.put(ConfigTagConstant.MaxIdle_TAG, "20");
        ht.put(ConfigTagConstant.MaxWait_TAG, "20000");
        
        commuConfig.put(ConfigTagConstant.DataConnectionPool_TAG, ht);
        try {
            new InitialUtil().initFromConnectionPool(commuConfig);
        } catch (ConfigFileException ex) {
            Logger.getLogger(FileUtilTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @After
    public void tearDown() {
        try {
            AppConstant.DATA_DBCPHELPER.close();
        } catch (SQLException ex) {
            Logger.getLogger(FileUtilTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of processFilesIllegalForOne method, of class FileUtil.
     */
    @Test
    public void testProcessFilesIllegalForOne() throws Exception {
       
        System.out.println("processFilesIllegalForOne");
        AppConstant.FtpLocalDirError = "D:\\testESCOMMU\\ld\\error";
        AppConstant.FtpLocalDir = "D:\\testESCOMMU\\ld";
        String fileNameIllegal = "ES1001.01201209130001";
        String errorCode = "03";
        FileUtil instance = new FileUtil();
        instance.processFilesIllegalForOne(fileNameIllegal, errorCode);
        
    }

    /**
     * Test of processFilesLegalForOne method, of class FileUtil.
     */
    @Test
    public void testProcessFilesLegalForOne() throws Exception {
        
        System.out.println("processFilesLegalForOne");
        AppConstant.FtpLocalDirBcp = "D:\\testESCOMMU\\ld\\bcp";
        AppConstant.FtpLocalDir = "D:\\testESCOMMU\\ld";
        AppConstant.cards.add("0101");
        ImportConfig config = new ImportConfig();
        config.setAccount("acc_tk");
        config.setPassword("acc_tk");
        config.setDb("epay1");
        config.setServer("127.0.0.1");
        AppConstant.importConfig = config;
        String fileName = "ES1001.01201209130001";
        FileUtil instance = new FileUtil();
        instance.processFilesLegalForOne(fileName);
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getTableBcp method, of class FileUtil.
     */
    @Test
    public void testGetTableBcp() throws Exception {
        System.out.println("getTableBcp");
        String fileName = "ES1001.01201209130001";
        FileUtil instance = new FileUtil();
        String expResult = "ACC_TK.IC_ES_HUNCH_INFO_BUF";
        String result = instance.getTableBcp(fileName);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of checkFileByFileLevel method, of class FileUtil.
     */
    @Test
    public void testCheckFileByFileLevel() throws Exception {
        System.out.println("checkFileByFileLevel");
        String fileName = "";
        FileUtil instance = new FileUtil();
        boolean expResult = false;
        boolean result = instance.checkFileByFileLevel(fileName);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFileErrorVoByFileNames method, of class FileUtil.
     */
    @Test
    public void testGetFileErrorVoByFileNames() {
        System.out.println("getFileErrorVoByFileNames");
        Vector fileNames = new Vector();
        fileNames.add("ES1001.01201209130001");
        String errorCode = "00";
        FileUtil instance = new FileUtil();
        Vector<FileErrorVo> results = instance.getFileErrorVoByFileNames(fileNames, errorCode);
        for(FileErrorVo fileErrorVo: results){
            
            System.out.println(fileErrorVo.getRemark());
        }
    }

    /**
     * Test of getFileErrorVoByFileNamesForOne method, of class FileUtil.
     */
    @Test
    public void testGetFileErrorVoByFileNamesForOne() {
        System.out.println("getFileErrorVoByFileNamesForOne");
        String fileName = "ES1001.01201209130001";
        String errorCode = "01";
        FileUtil instance = new FileUtil();
        FileErrorVo result = instance.getFileErrorVoByFileNamesForOne(fileName, errorCode);
        System.out.println(result.getRemark());
    }

}
