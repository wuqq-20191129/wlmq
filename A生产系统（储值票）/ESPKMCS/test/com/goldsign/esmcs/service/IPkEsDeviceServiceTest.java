/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.esmcs.service;

import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.esmcs.application.PKApplication;
import com.goldsign.esmcs.dll.library.EsPkChanelDll;
import com.goldsign.esmcs.env.PKAppConstant;
import com.goldsign.esmcs.service.impl.PkEsDeviceService;
import com.goldsign.esmcs.vo.EsPortParam;
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
public class IPkEsDeviceServiceTest {
    
    private IPkEsDeviceService instance = new PkEsDeviceService();
    
    public IPkEsDeviceServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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
     * Test of openBoxPort method, of class IPkEsDeviceService.
     */
    @Test
    public void testOpenBoxPort() throws Exception {
        System.out.println("openBoxPort");
        
        CallParam callParam = new CallParam();
        EsPortParam epp = new EsPortParam();
        epp.setPort(StringUtil.getShort("2"));
        callParam.setParam(epp);
        boolean expResult = true;
        CallResult result = instance.openBoxPort(callParam);
        assertEquals(expResult, result);
    }

    /**
     * Test of openChannelPort method, of class IPkEsDeviceService.
     */
    @Test
    public void testOpenChannelPort() throws Exception {
        System.out.println("openChannelPort");
        CallParam callParam = new CallParam();
        EsPortParam epp = new EsPortParam();
        epp.setPort(StringUtil.getShort("2"));
        callParam.setParam(epp);
        boolean expResult = true;
        CallResult result = instance.openChannelPort(callParam);
        assertEquals(expResult, result);
    }

    /**
     * Test of resetChannel method, of class IPkEsDeviceService.
     */
    @Test
    public void testResetChannel() throws Exception {
        System.out.println("resetChannel");
        CallParam callParam = new CallParam();
        EsPortParam epp = new EsPortParam();
        epp.setPort(StringUtil.getShort("1"));
        callParam.setParam(epp);
        boolean expResult = true;
        CallResult result = instance.resetChannel(callParam);
        assertEquals(expResult, result);
    }

    /**
     * Test of closeBoxPort method, of class IPkEsDeviceService.
     */
    @Test
    public void testCloseBoxPort() throws Exception {
        System.out.println("closeBoxPort");
        CallParam callParam = new CallParam();
        EsPortParam epp = new EsPortParam();
        epp.setPort(StringUtil.getShort("1"));
        callParam.setParam(epp);
        boolean expResult = true;
        CallResult result = instance.closeBoxPort(callParam);
        assertEquals(expResult, result);
    }

    /**
     * Test of closeChannelPort method, of class IPkEsDeviceService.
     */
    @Test
    public void testCloseChannelPort() throws Exception {
        System.out.println("closeChannelPort");
        CallParam callParam = new CallParam();
        EsPortParam epp = new EsPortParam();
        epp.setPort(StringUtil.getShort("1"));
        callParam.setParam(epp);
        boolean expResult = true;
        CallResult result = instance.closeChannelPort(callParam);
        assertEquals(expResult, result);
    }

    /**
     * Test of resetOneBox method, of class IPkEsDeviceService.
     */
    @Test
    public void testResetOneBox() throws Exception {
        System.out.println("resetOneBox");
        CallParam callParam = new CallParam();
        EsPortParam epp = new EsPortParam();
        epp.setPort(StringUtil.getShort("1"));
        callParam.setParam(epp);
        boolean expResult = true;
        CallResult result = instance.resetOneBox(callParam);
        assertEquals(expResult, result);
    }

    /**
     * Test of resetAllBox method, of class IPkEsDeviceService.
     */
    @Test
    public void testResetAllBox() throws Exception {
        System.out.println("resetAllBox");
        CallParam callParam = new CallParam();
        EsPortParam epp = new EsPortParam();
        epp.setPort(StringUtil.getShort("1"));
        callParam.setParam(epp);
        boolean expResult = true;
        CallResult result = instance.resetAllBox(callParam);
        assertEquals(expResult, result);
    }

    /**
     * Test of uploadOneBox method, of class IPkEsDeviceService.
     */
    @Test
    public void testUploadOneBox() throws Exception {
        System.out.println("uploadOneBox");
        CallParam callParam = new CallParam();
        EsPortParam epp = new EsPortParam();
        epp.setPort(StringUtil.getShort("1"));
        callParam.setParam(epp);
        boolean expResult = true;
        CallResult result = instance.uploadOneBox(callParam);
        assertEquals(expResult, result);
    }

    /**
     * Test of uploadAllBox method, of class IPkEsDeviceService.
     */
    @Test
    public void testUploadAllBox() throws Exception {
        System.out.println("uploadAllBox");
        CallParam callParam = null;
        CallResult expResult = null;
        CallResult result = instance.uploadAllBox(callParam);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllBoxState method, of class IPkEsDeviceService.
     */
    @Test
    public void testGetAllBoxState() throws Exception {
        System.out.println("getAllBoxState");
        CallParam callParam = new CallParam();
        EsPortParam epp = new EsPortParam();
        epp.setPort(StringUtil.getShort("1"));
        callParam.setParam(epp);
        boolean expResult = true;
        CallResult result = instance.getAllBoxState(callParam);
        assertEquals(expResult, result);
    }

    /**
     * Test of sendCard method, of class IPkEsDeviceService.
     */
    @Test
    public void testSendCard() throws Exception {
        System.out.println("sendCard");
        CallParam callParam = new CallParam();
        EsPortParam epp = new EsPortParam();
        epp.setParam(123);
        callParam.setParam(epp);
        boolean expResult = true;
        CallResult result = instance.sendCard(callParam);
        assertEquals(expResult, result);
    }

    /**
     * Test of moveCard method, of class IPkEsDeviceService.
     */
    @Test
    public void testMoveCard() throws Exception {
        System.out.println("moveCard");
        CallParam callParam = new CallParam();
        EsPortParam epp = new EsPortParam();
        epp.setParam(123);
        callParam.setParam(epp);
        boolean expResult = true;
        CallResult result = instance.moveCard(callParam);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCardRunError method, of class IPkEsDeviceService.
     */
    @Test
    public void testGetCardRunError() throws Exception {
        System.out.println("getCardRunError");
        CallParam callParam = new CallParam();
        EsPortParam epp = new EsPortParam();
        epp.setParam(123);
        callParam.setParam(epp);
        boolean expResult = true;
        CallResult result = instance.getCardRunError(callParam);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetChanelDllVersion() throws Exception{
        
        System.out.println("getChanelDllVersion");
        CallResult result = instance.getChanelDllVersion();
        System.out.println(result.getObj());
    }
    
    @Test
    public void testGetDevVersion() throws Exception{
        
        System.out.println("getDevVersion");
        CallResult result = instance.getDevVersion();
        System.out.println(result.getObj());
    }
    
    @Test
    public void testGetBoxDllVersion() throws Exception{
        
        System.out.println("getBoxDllVersion");
        CallResult result = instance.getBoxDllVersion();
        System.out.println(result.getObj());
    }
}
