/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.esmcs.service;

import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.esmcs.service.impl.KmsService;
import com.goldsign.esmcs.vo.KmsCardVo;
import com.goldsign.esmcs.vo.KmsCfgParam;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
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
        //param.setKmsIp("10.99.1.21");
        param.setKmsIp("10.99.1.22");
        param.setKmsPort("8");
        param.setKmsPin("FFFFFFFF");
        param.setKeyVer("00");

        CallResult result = instance.author(param);
        
    }

    /**
     * Test of getCardKey method, of class IKmsService.
     */
    @Test
    public void testGetCardKey() {
        
        testAuthor();
        System.out.println("getCardKey");
        String cardNo = "2013122300000102";
                 //01C000010AC8308A0058FFFFFFFF
               //01C000029000000011000011
        CallResult result = instance.getCardKey(cardNo);
        if(result.isSuccess()){
            KmsCardVo kmsCardVo = (KmsCardVo) result.getObj();
            System.out.println(kmsCardVo);
        }
    }
    
    @Test
    public void testGetTokeKey() {
        
        testAuthor();
        System.out.println("getTokeKey");
        String phyNo = "A9500000";
        String cardNo = "00000315";

        CallResult result = instance.getTokenKey(phyNo, cardNo);
        if(result.isSuccess()){
            KmsCardVo kmsCardVo = (KmsCardVo) result.getObj();
            System.out.println(kmsCardVo);
        }
    }
}
