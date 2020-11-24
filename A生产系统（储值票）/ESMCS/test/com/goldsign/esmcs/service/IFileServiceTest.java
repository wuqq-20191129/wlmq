/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.esmcs.service;

import com.goldsign.csfrm.env.BaseConstant;
import com.goldsign.csfrm.util.DateHelper;
import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.esmcs.env.AppConstant;
import com.goldsign.esmcs.env.ConfigConstant;
import com.goldsign.esmcs.exception.FileException;
import com.goldsign.esmcs.service.impl.FileService;
import com.goldsign.esmcs.vo.EsOrderDetailVo;
import com.goldsign.esmcs.vo.NoticeParam;
import com.goldsign.esmcs.vo.OrderParam;
import com.goldsign.esmcs.vo.OrderVo;
import com.sun.crypto.provider.RSACipher;
import java.util.Hashtable;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author lenovo
 */
public class IFileServiceTest {
    
    private IFileService instance;
    
    public IFileServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() throws Exception {
        instance = new FileService();
        testLoadConfigAndLogFile();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadConfigAndLogFile method, of class IFileService.
     */
    @Test
    public void testLoadConfigAndLogFile() throws Exception {
        System.out.println("loadConfigAndLogFile");
        CallParam callParam = null;
        ConfigConstant.CONFIG_FILE = "D:/project/csMetro/程序开发/ES票卡生产系统/代码/ESPKMCS/config/CommonConfig.xml";
        ConfigConstant.LOG_FILE = "D:/project/csMetro/程序开发/ES票卡生产系统/代码/ESPKMCS/config/Log4jConfig.xml";
        CallResult result = instance.loadConfigAndLogFile(callParam);
        System.out.println(result.isSuccess());
        Hashtable hashtable = (Hashtable) result.getObj();
        ConfigConstant.configs = hashtable;
        System.out.println(hashtable.size());
    }

    /**
     * Test of getMakingOrders method, of class IFileService.
     */
    @Test
    public void testGetMakingOrders() throws Exception {
        System.out.println("getMakingOrders");
        OrderParam orderParam = new OrderParam();
        orderParam.setEmployeeId("000001");
        orderParam.setWorkType("00");
        CallResult result = instance.getMakingOrders(orderParam);
        System.out.println((String)result.getObj());
    }

    /**
     * Test of getFinishOrders method, of class IFileService.
     */
    @Test
    public void testGetFinishOrders() throws Exception {
        System.out.println("getFinishOrders");
        OrderParam orderParam = new OrderParam();
        orderParam.setEmployeeId("000001");
        CallResult result = instance.getFinishOrders(orderParam);
        List<Object[]> objs = result.getObjs();
        
    }

    /**
     * Test of writeFinishOrder method, of class IFileService.
     */
    @Test
    public void testWriteFinishOrder() throws Exception {
        System.out.println("writeFinishOrder");
        OrderVo orderVo = new OrderVo();
        orderVo.setEmployeeId("000001_1");
        orderVo.setWorkType("00");
        
        orderVo.setOrderNo("00201309030002");
        orderVo.setCardTypeCode("0101");
        orderVo.setCardTypeDesc("成人储值票");
        orderVo.setCardEffTime("20130416");
        orderVo.setPrintMoney("2000");
        orderVo.setDeposit("500");
        orderVo.setBeginReqNo("0000001001");
        orderVo.setEndReqNo("0000002000");
        orderVo.setBeginSeqNo("00001001");
        orderVo.setEndSeqNo("00002000");
        orderVo.setDate("20130415");
        orderVo.setOrderNum(100);
        orderVo.setGoodCardNum(90);
        orderVo.setBadCardNum(10);
        orderVo.setIdCode("002");
        orderVo.setLineCode("06");
        orderVo.setStationCode("17");
        orderVo.setTctEffBeginTime("00000000");
        orderVo.setTctEffTime("000");
        orderVo.setLimitExitLineCode("00");
        orderVo.setLimitExitStationCode("00");
        orderVo.setLimitMode("000");
        
        orderVo.setStatus(AppConstant.ES_ORDER_STATUS_BEGIN_YES);
        
        CallResult result = instance.writeFinishOrder(orderVo);
        
    }

    /**
     * Test of writeMakingOrder method, of class IFileService.
     */
    @Test
    public void testWriteMakingOrder() throws Exception {
        System.out.println("writeMakingOrder");
        OrderVo orderVo = new OrderVo();
        orderVo.setEmployeeId("000003");
        orderVo.setWorkType("01");
        
        orderVo.setOrderNo("22225678922222");
        orderVo.setCardTypeCode("0202");
        orderVo.setCardTypeDesc("成人票");
        orderVo.setCardEffTime("20130416");
        orderVo.setPrintMoney("2000");
        orderVo.setDeposit("500");
        orderVo.setBeginReqNo("0000001001");
        orderVo.setEndReqNo("0000002000");
        orderVo.setBeginSeqNo("00001001");
        orderVo.setEndSeqNo("00002000");
        orderVo.setDate("20130415");
        orderVo.setOrderNum(100);
        orderVo.setGoodCardNum(90);
        orderVo.setBadCardNum(10);
        orderVo.setIdCode("002");
        orderVo.setLineCode("06");
        orderVo.setStationCode("17");
        orderVo.setTctEffBeginTime("00000000");
        orderVo.setTctEffTime("000");
        orderVo.setLimitExitLineCode("00");
        orderVo.setLimitExitStationCode("00");
        orderVo.setLimitMode("001");
        
        CallResult result = instance.writeMakingOrder(orderVo);
    }

    /**
     * Test of writeGoodOrder method, of class IFileService.
     */
    @Test
    public void testWriteGoodOrder() throws Exception {
        System.out.println("writeGoodOrder");
        EsOrderDetailVo orderVo = new EsOrderDetailVo();

        orderVo.setWorkType("01");
        orderVo.setOrderNo("55555678955555");
        orderVo.setCardTypeCode("0202");
        orderVo.setReqNo("0000001001");
        orderVo.setLogicNo("00001001");
        orderVo.setPrintNo("00001001");
        orderVo.setPhyNo("00001001");
        orderVo.setDate("20130415");
        orderVo.setPrintMoney("2000");   
        orderVo.setCardEffTime("20130416");
        orderVo.setSamNo("0000002000");
        orderVo.setLineCode("06");
        orderVo.setStationCode("17");
        orderVo.setTctEffBeginTime("00000000");
        orderVo.setTctEffTime("000");
        orderVo.setLimitExitLineCode("00");
        orderVo.setLimitExitStationCode("00");
        orderVo.setLimitMode("001");
        
        CallResult result = instance.writeGoodOrder(orderVo);

    }

    /**
     * Test of writeBadOrder method, of class IFileService.
     */
    @Test
    public void testWriteBadOrder() throws Exception {
        System.out.println("writeBadOrder");
        EsOrderDetailVo orderVo = new EsOrderDetailVo();
        
        orderVo.setWorkType("01");
        orderVo.setOrderNo("15555678955551");
        orderVo.setCardTypeCode("0202");
        orderVo.setReqNo("0000001001");
        orderVo.setLogicNo("00001001");
        orderVo.setPrintNo("00001001");
        orderVo.setPhyNo("00001001");
        orderVo.setDate("20130415");
        orderVo.setPrintMoney("2000");   
        orderVo.setCardEffTime("20130416");
        orderVo.setSamNo("0000002000");
        orderVo.setLineCode("06");
        orderVo.setStationCode("17");
        orderVo.setTctEffBeginTime("00000000");
        orderVo.setTctEffTime("000");
        orderVo.setLimitExitLineCode("00");
        orderVo.setLimitExitStationCode("00");
        orderVo.setLimitMode("001");
        
        CallResult result = instance.writeBadOrder(orderVo);
    }

    /**
     * Test of getUnNoticeOrderMsg method, of class IFileService.
     */
    @Test
    public void testGetUnNoticeOrderMsg() throws Exception {
        System.out.println("getUnNoticeOrderMsg");
        CallParam callParam = null;
        CallResult result = instance.getUnNoticeOrderMsg(callParam);
        List<String> ls = result.getObjs();
        for(String l: ls){
            System.out.println(l);
        }
    }

    /**
     * Test of writeUnNoticeOrderMsg method, of class IFileService.
     */
    @Test
    public void testWriteUnNoticeOrderMsg() throws Exception {
        System.out.println("writeUnNoticeOrderMsg");
        //OrderVo orderVo = new OrderVo();
        //orderVo.setOrderNo("30005678900003");
        String file = "hwj:30005678900003";
        CallResult result = instance.writeUnNoticeOrderMsg(file);
        
    }

    /**
     * Test of writeAuditFile method, of class IFileService.
     */
    @Test
    public void testWriteAuditFile() throws Exception {
        System.out.println("writeAuditFile");
        //OrderVo orderVo = new OrderVo();
        //orderVo.setOrderNo("40005678900002");
        String file = "hwj:30005678900003";
        CallResult result = instance.writeAuditFile(file);

    }

    /**
     * Test of getLocalParamFileDetails method, of class IFileService.
     */
    @Test
    public void testGetLocalParamFileDetails() throws Exception {
        System.out.println("getLocalParamFileDetails");
        String fileName = "PRM002.0203.20130815.01";
        String filePath = "D:/testESCOMMU/files/param";
        CallResult result = instance.getLocalParamFileDetails(fileName, filePath);
        List<Object[]> ls = result.getObjs();
        for(Object[] os: ls){
            System.out.println(os[0].toString());
        }
    }

    /**
     * Test of getEsNoticeFiles method, of class IFileService.
     */
    @Test
    public void testGetEsNoticeFiles() throws Exception {
        System.out.println("getEsNoticeFiles");
        NoticeParam noticeParam = null;
        CallResult result = instance.getEsNoticeFiles(noticeParam);

    }

    /**
     * Test of getEsUnNoticeFiles method, of class IFileService.
     */
    @Test
    public void testGetEsUnNoticeFiles() throws Exception {
        System.out.println("getEsUnNoticeFiles");
        NoticeParam noticeParam = null;
        CallResult result = instance.getEsUnNoticeFiles(noticeParam);
    }
}
