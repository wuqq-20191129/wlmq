/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.etmcs.service;

import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.etmcs.service.impl.KmsService;
import com.goldsign.etmcs.vo.KmsCardVo;
import com.goldsign.etmcs.vo.KmsCfgParam;
import org.junit.*;

/**
 *
 * @author lenovo
 */
public class IKmsServiceTest {
    
    private IKmsService instance = KmsService.getInstance();
    
    public IKmsServiceTest() {
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
     * Test of author method, of class IKmsService.
     */
    @Test
    public void testAuthor() {
        System.out.println("author");
        KmsCfgParam param = new KmsCfgParam();
        param.setKmsIp("172.20.16.21");
        param.setKmsPort("8");
        param.setKmsPin("83000991");
        param.setKmsVersion("00");
        CallResult result = instance.author(param);
    }

    /**
     * Test of getCardKey method, of class IKmsService.
     */
    @Test
    public void testGetCardKey() {
        testAuthor();
        System.out.println("getCardKey");
        String cardNo = "0000100000000001";
        
        CallResult result = instance.getCardKey(cardNo);
        if(result.isSuccess()){
            KmsCardVo kmsCardVo = (KmsCardVo) result.getObj();
            System.out.println(kmsCardVo);
        }
    }

}
