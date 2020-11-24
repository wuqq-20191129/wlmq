/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.etmcs.service;

import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.etmcs.service.impl.KmsService;
import com.goldsign.etmcs.service.impl.RwDeviceService;
import com.goldsign.etmcs.vo.AnalyzeVo;
import com.goldsign.etmcs.vo.CommonCfgParam;
import com.goldsign.etmcs.vo.KmsCfgParam;
import com.goldsign.etmcs.vo.MakeCardVo;
import com.goldsign.etmcs.vo.SignCardParam;
import org.junit.*;

/**
 *
 * @author lenovo
 */
public class IRwDeviceServiceTest {
    
    private IRwDeviceService instance;
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
    public void setUp() {
        instance = new RwDeviceService();
        testOpenConnection();
    }
    
    @After
    public void tearDown() {
    }
    
    private void kmsAuthor(){
        kmsService = KmsService.getInstance();
        KmsCfgParam param = new KmsCfgParam();
        param.setKmsIp("10.99.1.22");
        param.setKmsPort("8888");
        param.setKmsPin("FFFFFFFF");

        CallResult result = kmsService.author(param);
    }

    /**
     * Test of openConnection method, of class IRwDeviceService.
     */
    @Test
    public void testOpenConnection() {
        System.out.println("openConnection");
        String portNo = "COM4";
        CallResult result = instance.openConnection(portNo);
        System.out.println(result.isSuccess());
    }

    /**
     * Test of initDevice method, of class IRwDeviceService.
     */
    @Test
    public void testInitDevice() {
        System.out.println("initDevice");
        CommonCfgParam param = new CommonCfgParam();
        param.setStationId("0107");
        param.setDeviceType("07");
        param.setDeviceNo("003");
     
        CallResult result = instance.initDevice(param);
        System.out.println(result.isSuccess());
    }

    /**
     * Test of signCard method, of class IRwDeviceService.
     */
    @Test
    public void testSignCard() {
        System.out.println("signCard");
        SignCardParam param = new SignCardParam();
        param.setEmployeeNo("abc");
        param.setEmployeeName("另一个");
        param.setSex("1");
        CallResult result = instance.signCard(param);
        System.out.println(result.isSuccess());
    }

    /**
     * Test of destroy method, of class IRwDeviceService.
     */
    @Test
    public void testDestroy() {
        System.out.println("destroy");
        testAnalyze();
        MakeCardVo vo = new MakeCardVo();
        vo.setEmployeeId(null);
        CallResult result = instance.destroy(vo);
        testAnalyze();
        System.out.println(result.isSuccess());
    }

    /**
     * Test of analyze method, of class IRwDeviceService.
     */
    @Test
    public void testAnalyze() {
        System.out.println("analyze");
        CallResult result = instance.analyze();
        if(result.isSuccess()){
            AnalyzeVo analyzeVo = (AnalyzeVo) result.getObj();
            System.out.println(analyzeVo);
        }
    }

    /**
     * Test of closeConnection method, of class IRwDeviceService.
     */
    @Test
    public void testCloseConnection() {
        System.out.println("closeConnection");
        CallResult result = instance.closeConnection();
        System.out.println(result.isSuccess());
    }
}
