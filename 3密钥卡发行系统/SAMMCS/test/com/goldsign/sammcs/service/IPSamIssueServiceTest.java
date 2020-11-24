/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.sammcs.service;

import com.goldsign.sammcs.service.impl.PSamIssueService;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.sammcs.vo.IssueVo;
import com.goldsign.sammcs.vo.KmsCfgParam;
import com.goldsign.sammcs.vo.ReadOutInfVo;
import org.junit.*;

/**
 *
 * @author lenovo
 */
public class IPSamIssueServiceTest {
    
    private IPSamIssueService instance;
    
    public IPSamIssueServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        instance = new PSamIssueService();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of author method, of class IPSamIssueService.
     */
    @Test
    public void testAuthor() {
        System.out.println("author");
        KmsCfgParam param = new KmsCfgParam();
//        param.setKmsIp("10.200.48.138");
//        param.setKmsPort("88");
        param.setKmsIp("10.99.1.21");
//        param.setKmsPort("8");
//        param.setKmsIp("172.20.18.43");
        param.setKmsPort("8");
        param.setKmsPin("FFFFFFFF");
        param.setKmsKeyVerstion("01"); //密钥版本信息 测试：00 正式：01

        CallResult result = instance.author(param);
        System.out.println(result.isSuccess());
    }

    /**
     * Test of issue method, of class IPSamIssueService.
     */
    @Test
    public void testIssue() {
        
        testAuthor();
        
        System.out.println("issue");
        
        IssueVo issueVo = new IssueVo();
        issueVo.setKeyVerstion("00");
        issueVo.setPsamCardNo("4100000302E91201");//"0000100000000001"   //0C0F040083E80005
        //0C0S040083E80005
        //0C00040083E80005
        //4100000301010013
        //1-9、A-F
        issueVo.setPsamCardVersion("00");
        issueVo.setPsamCardType("08"); //"00"
        issueVo.setKeyIndex("01");
        issueVo.setIssuerId("4100073100000000");
        issueVo.setReceiverId("4100073100000000");
        issueVo.setStartDate("20130801");
        issueVo.setValidDate("20231001"); //20231001
        CallResult result = instance.issue(issueVo);
        System.out.println(result.isSuccess());
        if(result.isSuccess()){
            IssueVo issueVoRet = (IssueVo) result.getObj();
            System.out.println(issueVoRet.getPsamCardPhyNo());
        }
        System.out.println("返回代码："+result.getCode());
    }
    
     @Test
    public void testRead() {
        
        testAuthor();
        
        System.out.println("read");
        
        CallResult result = instance.read();
        System.out.println(result.isSuccess());
        System.out.println("返回代码 Code:"+result.getCode());
        
        if(result.isSuccess()){
            ReadOutInfVo readOutInfVo = (ReadOutInfVo)result.getObj();
            
            System.out.println("制卡状态 issueState:"+readOutInfVo.getIssueState());
            System.out.println("psamCardNo:"+readOutInfVo.getPsamCardNo());
            System.out.println("phyNo:"+readOutInfVo.getPhyNo());
            
        } 
    }
    
    

//    public class IPSamIssueServiceImpl implements IPSamIssueService {
//
//        public CallResult author(KmsCfgParam param) {
//            return null;
//        }
//
//        public CallResult issue(IssueVo issueVo) {
//            return null;
//        }
//    }

}
