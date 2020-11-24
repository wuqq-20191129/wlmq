/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.sammcs.service;

import com.goldsign.csfrm.exception.LoadException;
import com.goldsign.csfrm.service.impl.ConfigFileService;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.csfrm.vo.ConfigParam;
import com.goldsign.csfrm.vo.XmlTagVo;
import com.goldsign.sammcs.env.AppConstant;
import com.goldsign.sammcs.env.ConfigConstant;
import com.goldsign.sammcs.service.impl.MakeCardService;
import com.goldsign.sammcs.util.DbcpHelper;
import com.goldsign.sammcs.vo.MakeCardQueryVo;
import com.goldsign.sammcs.vo.MakeCardVo;
import java.io.File;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
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
public class IMakeCardServiceTest {
    
    public IMakeCardServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        com.goldsign.sammcs.util.DbcpHelper dbcpHelper = null;
        String driverName = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@172.20.19.203:1521:epay1";
        String userId =  "acc_tk";
        String password = "acc_tk";
        String maxActive = "40";
        String maxIdle = "20";
        String maxWait = "20000";
        try {
            dbcpHelper = new com.goldsign.sammcs.util.DbcpHelper(driverName, url, userId, password,
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
     * Test of getMakeCards method, of class IMakeCardService.
     */
    @Test
    public void testGetMakeCards() {
        System.out.println("getMakeCards");
        MakeCardQueryVo queryVo = new MakeCardQueryVo();
        String queryOrderNo = "SCDD2013082900000003";
        queryVo.setOrderNo(queryOrderNo);
        queryVo.setSamType("08");
        queryVo.setMakeCardTimeBegin("20130901");
        queryVo.setMakeCardTimeEnd("20130910");
        queryVo.setFinishFlag("1");
        IMakeCardService instance = new MakeCardService();
        List expResult = null;
        List<Object[]> result = instance.getMakeCards(queryVo);
        Object[] obj = result.get(0);
        Object orderNo = obj[0];
        assertEquals(queryOrderNo, orderNo);
    }

    /**
     * Test of writeLocalFile method, of class IMakeCardService.
     */
    @Test
    public void testWriteLocalFile() throws Exception {
        CallResult callResult = null;
        
        //加载配置和日志文件
        callResult = loadConfigAndLogFile();
        if (!callResult.isSuccess()) {
            System.out.println("加载配置文件失败！");
        }
        //保存配置文件信息
        AppConstant.configs = (Hashtable) callResult.getObj();
        
        
        System.out.println("writeLocalFile");
        MakeCardVo vo = new MakeCardVo();
        vo.setOrderNo("SCDD2013082900000003");
        vo.setSamType("08");
        vo.setStartLogicNo("efbc30000b000080");
        vo.setCurLogicNo("efbc30000b000080");
        vo.setOrderNum("50");
        vo.setFinishNum("20");
        
        String phyNo = "1A972309210B1111";
        String cardProducerCode = "";
        IMakeCardService instance = new MakeCardService();
        File expResult = null;
        File result = instance.writeLocalFile(vo, phyNo, cardProducerCode);
        if (result.exists()) {
            System.out.println("success");
        } else {
            fail("The test case is a prototype.");
            
        }
    }

    /**
     * Test of writeMakeCard method, of class IMakeCardService.
     */
    @Test
    public void testWriteMakeCard() throws Exception {
        System.out.println("writeMakeCard");
        MakeCardVo vo = new MakeCardVo();
        vo.setOrderNo("SCDD2013082900000003");
        vo.setSamType("08");
        vo.setStartLogicNo("efbc30000b000080");
        vo.setCurLogicNo("efbc30000b000080");
        vo.setOrderNum("50");
        vo.setFinishNum("20");
        
        String phyNo = "1A972309210B1111";
        String cardProducerCode = "";
        IMakeCardService instance = new MakeCardService();
        Boolean expResult = null;
        Boolean result = instance.writeMakeCard(vo, phyNo, cardProducerCode, "testUser");
        assertEquals(true, result);
    }

    /**
     * Test of checkOrderPlan method, of class IMakeCardService.
     */
    @Test
    public void testCheckOrderPlan() {
        System.out.println("checkOrderPlan");
        String orderNo = "SCDD2013082900000003";
        IMakeCardService instance = new MakeCardService();
        boolean expResult = false;
        boolean result = instance.checkOrderPlan(orderNo);
        if (result) {
            System.out.println("订单未完成！");
        } else {
            System.out.println("订单已完成！");
        }
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurLogicNo method, of class IMakeCardService.
     */
    @Test
    public void testGetCurLogicNo() {
        System.out.println("getCurLogicNo");
        String orderNo = "SCDD2013082900000002";
        IMakeCardService instance = new MakeCardService();
        String expResult = "";
        String result = instance.getCurLogicNo(orderNo);
        System.out.println("CurLogicNo:"+result);
    }

    /**
     * Test of readLocalFile method, of class IMakeCardService.
     */
    @Test
    public void testReadLocalFile() throws Exception {
        System.out.println("readLocalFile");
        File readFile = new File("E:/svn/Goldsign/长沙地铁清分项目/密钥卡发行系统/代码开发/SAMMCS/data/backup/markcard/efbc30000b000080.txt");
        IMakeCardService instance = new MakeCardService();
        Boolean expResult = null;
        Boolean result = instance.readLocalFile(readFile,"testUser");
        if (result) {
            System.out.println("备份数据写入数据库成功");
        } else {
            System.out.println("备份数据写入数据库失败");
        }
    }

    /**
     * Test of queryIssueDetails method, of class IMakeCardService.
     */
    @Test
    public void testQueryIssueDetails() {
        System.out.println("queryIssueDetails");
        MakeCardQueryVo queryVo = new MakeCardQueryVo();
        queryVo.setMakeCardTimeBegin("20130901");
        queryVo.setMakeCardTimeEnd("20130910");
        IMakeCardService instance = new MakeCardService();
        List<Object[]> result = instance.queryIssueDetails(queryVo);
        if (result != null && result.size() > 0) {
            Object[] objs = result.get(0);
            System.out.println("OrderNo:"+objs[0]);
        } else {
            System.out.println("未查询到记录");
        }
    }

    /**
     * Test of issueDetailIsExist method, of class IMakeCardService.
     */
    @Test
    public void testIssueDetailIsExist() {
        System.out.println("issueDetailIsExist");
        String logicNo = "efbc30000b000080";
        IMakeCardService instance = new MakeCardService();
        Boolean expResult = true;
        Boolean result = instance.issueDetailIsExist(logicNo);
        assertEquals(expResult, result);
    }

    /**
     * Test of getOrderPlanData method, of class IMakeCardService.
     */
    @Test
    public void testGetOrderPlanData() {
        System.out.println("getOrderPlanData");
        String orderNo = "efbc30000b000080";
        IMakeCardService instance = new MakeCardService();
        Object[] results = instance.getOrderPlanData(orderNo);
        boolean notAllComplete = (Boolean)results[0];
        String finishNum = (String)results[1];
        String samType = (String)results[2];
        System.out.println("notAllComplete:"+notAllComplete);
        System.out.println("finishNum:"+finishNum);
        System.out.println("samType:"+samType);
    }
    
    @Test
    public void testCancelOrder() throws SQLException {
            System.out.println("cancelOrder");
            String orderNo = "efbc30000b000080";
            String userId = "moqf";
            IMakeCardService instance = new MakeCardService();
            Boolean result = instance.cancelOrder(orderNo, userId);
            if (result) {
                System.out.println("结束订单成功！");
            } else {
                System.out.println("结束订单失败！");
            }
        }
    
    public static CallResult loadConfigAndLogFile() {

        ConfigParam configParam = new ConfigParam(ConfigConstant.CONFIG_FILE, ConfigConstant.LOG_FILE,
                new XmlTagVo[]{
                    new XmlTagVo(ConfigConstant.CommonTag), new XmlTagVo(ConfigConstant.DataConnectionPoolTag),
                    new XmlTagVo(ConfigConstant.KmsCommuTag),
                    new XmlTagVo(ConfigConstant.SamIssueTag),new XmlTagVo(ConfigConstant.BackupTag)});
        try {
            return new ConfigFileService().loadConfigAndLogFile(configParam);
        } catch (LoadException ex) {
//            logger.error(ex);
            return new CallResult(ex.getMessage());
        }

    }

    public class IMakeCardServiceImpl implements IMakeCardService {

        public List<Object[]> getMakeCards(MakeCardQueryVo queryVo) {
            return null;
        }

        public File writeLocalFile(MakeCardVo vo, String phyNo, String cardProducerCode) throws IOException {
            return null;
        }

        public Boolean writeMakeCard(MakeCardVo vo, String phyNo, String cardProducerCode, String userName) throws SQLException {
            return null;
        }

        public boolean checkOrderPlan(String orderNo) {
            return false;
        }

        public String getCurLogicNo(String orderNo) {
            return "";
        }

        public Boolean readLocalFile(File file, String userName) throws IOException, SQLException {
            return null;
        }

        public List<Object[]> queryIssueDetails(MakeCardQueryVo queryVo) {
            return null;
        }

        public Boolean issueDetailIsExist(String logicNo) {
            return null;
        }

        public Object[] getOrderPlanData(String orderNo) {
            return null;
        }
        
        public Boolean cancelOrder(String orderNo,String userId) throws SQLException {
            return false;
        }
    }
}
