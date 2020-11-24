/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.util;

import com.goldsign.escommu.env.FileConstant;
import com.goldsign.escommu.vo.BcpResult;
import com.goldsign.escommu.vo.ImportConfig;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author lenovo
 */
public class BcpUtilTest {
    
    public BcpUtilTest() {
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
     * Test of getBcp method, of class BcpUtil.
     */
    @Test
    public void testGetBcp() {
        
        System.out.println("getBcp");
        ImportConfig config = new ImportConfig();
        config.setAccount("acc_tk");
        config.setPassword("acc_tk");
        config.setDb("epay1");
        config.setServer("127.0.0.1");
        String tableName = FileConstant.TABLE_ES_INITIAL;
        String fileName = "D:\\testESCOMMU\\ld\\ES1001.01201209130001";
        String terminator = "";
        BcpUtil instance = new BcpUtil();
       
        String result = instance.getBcp(config, tableName, fileName, terminator);
        System.out.println(result);
        //String appWorkDir = System.getProperty("user.dir");
        //System.out.println(appWorkDir);
    }

    /**
     * Test of bcpFile method, of class BcpUtil.
     */
    @Test
    public void testBcpFile() throws Exception {
        
        System.out.println("bcpFile");
        String cmd = "sqlldr userid=acc_tk/acc_tk@epay1 control=D:/project/csMetro/程序开发/ES通讯系统/代码开发/ESCOMMU/control/ACC_TK.IC_ES_INITI_INFO_BUF.ctl log=D:/testESCOMMU/ld/log.txt data=D:/testESCOMMU/ld/bcp.ES119.00201309030003";
        //String cmd = "sqlldr userid=acc_tk/acc_tk@epay1 control=D:/project/csMetro/程序开发/ES通讯系统/代码开发/ESCOMMU/control/ACC_TK.IC_ES_INITI_INFO_BUF.ctl log=D:/testESCOMMU/ld/log.txt data=D:/testESCOMMU/ld/bcp.ES1001.00201201050001";
        //String cmd = "sqlldr userid=acc_tk/acc_tk@epay1 control=D:/project/csMetro/程序开发/ES通讯系统/代码开发/ESCOMMU/control/ACC_TK.IC_ES_AGAIN_INFO_BUF.ctl log=D:/testESCOMMU/ld/log.txt data=D:/testESCOMMU/ld/bcp.ES6017.02201205280011";
        //String cmd = "sqlldr userid=acc_tk/acc_tk@epay1 control=D:/project/csMetro/程序开发/ES通讯系统/代码开发/ESCOMMU/control/ACC_TK.IC_ES_LOGOUT_INFO_BUF.ctl log=D:/testESCOMMU/ld/log.txt data=D:/testESCOMMU/ld/bcp.ES1001.00201202230031";
        
        /*String ctl = "D:/testload/ctl/a2.ctl";
        String data = "D:/testload/data/a.es";
        String cmd = "sqlldr userid=acc_practice/acc_practice@epay1 control="+ctl+" data="+data;*/
        int n = 0;
        BcpUtil instance = new BcpUtil();
        BcpResult expResult = null;
        BcpResult result = instance.bcpFile(cmd, n);
        //assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }
}
