/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.esmcs.service;

import com.goldsign.csfrm.util.CharUtil;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.esmcs.service.impl.KmsService;
import com.goldsign.esmcs.service.impl.RwDeviceService;
import com.goldsign.esmcs.vo.*;
import org.junit.*;

/**
 *
 * @author lenovo
 */
public class IRwDeviceServiceTest {

    private RwDeviceService rwDeviceService;
    private IKmsService kmsService;
    
    public IRwDeviceServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() throws Exception {
        rwDeviceService = new RwDeviceService();
        testOpenRwPort();
        
    }
    
    private void kmsAuthor(){
        kmsService = KmsService.getInstance();
        KmsCfgParam param = new KmsCfgParam();
        param.setKmsIp("127.0.0.1");
        //param.setKmsIp("10.99.1.21");
        param.setKmsPort("8");
        param.setKmsPin("FFFFFFFF");
        param.setKeyVer("01");
        param.setLock(false);
        CallResult result = kmsService.author(param);
    }

    @After
    public void tearDown() throws Exception {
        testCloseRwPort();
    }
        
    /**
     * Test of openRwPort method, of class IRwDeviceService.
     */
    @Test
    public void testOpenRwPort() throws Exception {
        
        RwPortParam rwPortParam = new RwPortParam();
        rwPortParam.setPort("COM2");
        rwPortParam.setStationId("0103");
        rwPortParam.setDeviceType("09");
        rwPortParam.setDeviceId("003");
        
        CallResult callResult = rwDeviceService.openRwPort(rwPortParam);
        System.out.println(callResult.isSuccess());
        System.out.println(callResult.getCode());
        System.out.println(callResult.getMsg());
    }

    /**
     * Test of closeRwPort method, of class IRwDeviceService.
     */
    @Test
    public void testCloseRwPort() throws Exception {
        
        CallResult callResult = rwDeviceService.closeRwPort(null);
        System.out.println(callResult.isSuccess());
        System.out.println(callResult.getCode());
        System.out.println(callResult.getMsg());
    }

    /**
     * Test of readCard method, of class IRwDeviceService.
     */
    @Test
    public void testReadCard() throws Exception {
        
        CallResult callResult = rwDeviceService.readCard(null);
        System.out.println(callResult.isSuccess());
        System.out.println(callResult.getCode());
        System.out.println(callResult.getMsg());
        if(callResult.isSuccess()){
            AnalyzeVo analyzeVo = (AnalyzeVo) callResult.getObj();
            System.out.println(analyzeVo);
        }
        
    }
    
    @Test
    public void testClearPK() throws Exception {
        AnalyzeVo analyzeVo = new AnalyzeVo();
        CallResult callResult = rwDeviceService.readCard(null);
        System.out.println(callResult.isSuccess());
        System.out.println(callResult.getCode());
        System.out.println(callResult.getMsg());
        if(callResult.isSuccess()){
            analyzeVo = (AnalyzeVo) callResult.getObj();
            //System.out.println(analyzeVo);
        }
        
        kmsAuthor();
        String logicNo = analyzeVo.getcLogicalID().substring(4);//"4100000000021576";
        callResult = kmsService.getCardKey(logicNo);
        KmsCardVo kmsCardVo = (KmsCardVo) callResult.getObj();
        
        OrderInVo orderInVo = new OrderInVo();
        orderInVo.setLogicalID(logicNo);
        callResult = rwDeviceService.clear(orderInVo, null, kmsCardVo);
        System.out.println(callResult.isSuccess());
        System.out.println(callResult.getCode());
        System.out.println(callResult.getMsg());
        testReadCard();
    }
    
    @Test
    public void testClearTK() throws Exception {
        AnalyzeVo analyzeVo = new AnalyzeVo();
        CallResult callResult = rwDeviceService.readCard(null);
        System.out.println(callResult.isSuccess());
        System.out.println(callResult.getCode());
        System.out.println(callResult.getMsg());
        if (callResult.isSuccess()) {
            analyzeVo = (AnalyzeVo) callResult.getObj();
            //System.out.println(analyzeVo);
        }

        kmsAuthor();
        String phyNo = analyzeVo.getcPhysicalID().substring(6, 14);
        String logicNo = analyzeVo.getcLogicalID().substring(8,16);
        callResult = kmsService.getTokenKey(phyNo, logicNo);
        KmsCardVo kmsCardVo = (KmsCardVo) callResult.getObj();

        callResult = rwDeviceService.clear(null, null, kmsCardVo);
        System.out.println(callResult.isSuccess());
        System.out.println(callResult.getCode());
        System.out.println(callResult.getMsg());
        testReadCard();
    }

    /**
     * Test of writeCard method, of class IRwDeviceService.
     */
    @Test
    public void testWriteCardPK() throws Exception {
        
        WriteCardParam writeCardParam = new WriteCardParam();
        writeCardParam.setWorkType("00");
        OrderInVo order = new OrderInVo();
        order.setOrderNo("00201309250003");
        order.setApplicationNO("");
        order.setTicketType("0200");
        order.setLogicalID("2013122300000103");
        order.setDeposite("20");
        order.setValue("10000");
        order.setRechargeTopValue("510");
        order.setSaleActiveFlag("1");
        order.setSenderCode("5320");
        order.setCityCode("4100");
        order.setBusiCode("0000");
        order.setTestFlag("1");
        order.setIssueDate("20140102");
        order.setCardVersion("0805");
        order.setcStartExpire("20140103");
        order.setcEndExpire("20150103");
        //
        order.setLogicDate("20140103");//20140103010203
        order.setLogicDays("0");
        //
        order.setAppVersion("01");
        order.setExitEntryMode("00");
        order.setEntryLineStation("0000");
        order.setExitLineStation("0000");
        
        writeCardParam.setOrderInVo(order);
        
        kmsAuthor();//认证
        
        long startTime = System.currentTimeMillis();
        CallResult callResult = rwDeviceService.readCard(writeCardParam);
        AnalyzeVo analyzeVo = (AnalyzeVo) callResult.getObj();
        writeCardParam.setParam(analyzeVo);
        
        callResult = kmsService.getCardKey(order.getLogicalID());
        KmsCardVo kmsCardVo = (KmsCardVo) callResult.getObj();
        writeCardParam.setParam(kmsCardVo);
        
        callResult = rwDeviceService.writeCard(writeCardParam);
        
        long endTime = System.currentTimeMillis();
        System.out.println("运行时间："+(endTime-startTime)/1000+"秒");
        
        System.out.println(callResult.isSuccess());
        System.out.println(callResult.getCode());
        System.out.println(callResult.getMsg());
    }

    @Test
    public void testWriteCardTK() throws Exception {
        
        WriteCardParam writeCardParam = new WriteCardParam();
        writeCardParam.setWorkType("00");
        OrderInVo order = new OrderInVo();
        order.setOrderNo("00201309250003");
        order.setApplicationNO("");
        order.setTicketType("0100");
        order.setLogicalID("1000000010000007");
        order.setDeposite("0");
        order.setValue("0");
        order.setRechargeTopValue("56");
        order.setSaleActiveFlag("0");
        order.setSenderCode("5320");
        order.setCityCode("4100");
        order.setBusiCode("0000");
        order.setTestFlag("1");
        order.setIssueDate("20140102");
        order.setCardVersion("0805");
        order.setcStartExpire("20140104");//
        order.setcEndExpire("20150106");
        //
        order.setLogicDate("20140108");
        order.setLogicDays("3");
        //
        order.setAppVersion("01");
        order.setExitEntryMode("01");
        order.setEntryLineStation("0201");
        order.setExitLineStation("0304");
        
        writeCardParam.setOrderInVo(order);
        
        CallResult callResult = rwDeviceService.readCard(writeCardParam);
        AnalyzeVo analyzeVo = (AnalyzeVo) callResult.getObj();
        writeCardParam.setParam(analyzeVo);
        
        kmsAuthor();
        String phyNo = analyzeVo.getcPhysicalID().substring(6, 14);
        String logicNo = order.getLogicalID().substring(8,16);
        callResult = kmsService.getTokenKey(phyNo, logicNo);
        KmsCardVo kmsCardVo = (KmsCardVo) callResult.getObj();
        writeCardParam.setParam(kmsCardVo);
        
        callResult = rwDeviceService.writeCard(writeCardParam);
        System.out.println(callResult.isSuccess());
        System.out.println(callResult.getCode());
        System.out.println(callResult.getMsg());
    }
    
    @Test
    public void testGetSamCard() throws Exception {
        
        CallResult callResult = rwDeviceService.getSamCard(null);
        System.out.println(callResult.isSuccess());
        System.out.println(callResult.getCode());
        System.out.println(callResult.getMsg());
        if(callResult.isSuccess()){
            SamCardVo samCardVo = (SamCardVo) callResult.getObj();
            System.out.println(samCardVo.getSamNo()+":"+samCardVo.getSamStatus());
        }
        
    }
    
    public static void main(String[] args){
        
        char[] ca = new char[]{'a','b','c','d','e','f'};
        char[] ta = new char[]{'1','2'};
        System.arraycopy(ta, 0, ca, 3, ta.length);
        System.out.println(new String(ca));
    }
}
