/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.esmcs.service;

import com.goldsign.csfrm.vo.*;
import com.goldsign.esmcs.env.ConfigConstant;
import com.goldsign.esmcs.service.impl.CommuService;
import com.goldsign.esmcs.util.ConfigUtil;
import com.goldsign.esmcs.vo.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.junit.*;

/**
 *
 * @author lenovo
 */
public class ICommuServiceTest {

    private ICommuService commuService;
    
    public ICommuServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() throws Exception {
        
        commuService = CommuService.getInstance();
        
        testOpenCommu();
        
        testDownAuditAndErrorFile();
    }

    @After
    public void tearDown() throws Exception {
        
        testCloseCommu();
        
        commuService = null;
    }

    /**
     * Test of openCommu method, of class ICommuService.
     */
    //@Test
    public void testOpenCommu() throws Exception {

        CallParam callParam = new CallParam();
        String IP = "127.0.0.1";
        callParam.setParam(IP);
        Integer PORT = 5001;   
        callParam.setParam(PORT);
        
        CallResult callResult = commuService.openCommu(callParam);
        System.out.println("打开通讯结果："+callResult.isSuccess());
    }

    /**
     * Test of loginOperator method, of class ICommuService.
     */
    @Test
    public void testLoginOperator() throws Exception {
        
        LoginParam callParam = new LoginParam();
        callParam.setUserNo("123456");
        callParam.setPasswrod("abcdef");
        callParam.setParam("2");
        callParam.setParam("002"); 
        CallResult callResult = commuService.loginOperator(callParam);
        System.out.println("登录结果："+callResult.isSuccess());
        System.out.println("返回代码："+callResult.getCode());
        System.out.println("返回信息："+callResult.getMsg());
        if(callResult.isSuccess()){
            SysUserVo user = (SysUserVo) callResult.getObj();
            System.out.println("账号:"+user.getAccount());
            System.out.println("名称:"+user.getUsername());
        }
        
    }

    /**
     * Test of uploadEsOrderFile method, of class ICommuService.
     */
    @Test
    public void testUploadEsOrderFile() throws Exception {
        
        CallParam callParam = new CallParam();
        callParam.setParam("07002");
        List<String> files = new ArrayList<String>();
        files.add("ouywl:ES002.01201104010001");
        files.add("ouywl:ES002.01201104010002");
        files.add("ouywl:ES002.01201104010003");
        callParam.setParam(files);
        CallResult callResult = commuService.uploadEsOrderFile(callParam);
        System.out.println("结果："+callResult.isSuccess());
        System.out.println("返回代码："+callResult.getCode());
        System.out.println("返回信息："+callResult.getMsg());
    }

    /**
     * Test of downAuditAndErrorFile method, of class ICommuService.
     */
    @Test
    public void testDownAuditAndErrorFile() throws Exception {
        
        CallParam callParam = new CallParam();
        String localPath = "d:\\localPath";
        String remotePath = "d:\\remotePath";
        callParam.setParam(new FtpLoginParamVo());
        callParam.setParam(localPath);
        callParam.setParam(remotePath);
        CallResult callResult = commuService.downAuditAndErrorFile(callParam);
        System.out.println("结果：" + callResult.isSuccess());
        System.out.println("返回代码：" + callResult.getCode());
        System.out.println("返回信息：" + callResult.getMsg());
        if (callResult.isSuccess()) {
            List<FtpFileParamVo> ftpFileParamVos = callResult.getObjs();
            for (FtpFileParamVo ftpFileParamVo : ftpFileParamVos) {
                System.out.println("文件：" + ftpFileParamVo.getFile());
            }
        }
    }

    /**
     * Test of queryParams method, of class ICommuService.
     */
    @Test
    public void testQueryParams() throws Exception {
        
        CallParam callParam = new CallParam();
        CallResult callResult = commuService.queryParams(callParam);
        System.out.println("结果："+callResult.isSuccess());
        System.out.println("返回代码："+callResult.getCode());
        System.out.println("返回信息："+callResult.getMsg());
        if(callResult.isSuccess()){
            CityParamVo cityParamVo = (CityParamVo) callResult.getObj();
            System.out.println("城市代码："+cityParamVo.getCityCode());
            System.out.println("工业代码："+cityParamVo.getIndustryCode());
            System.out.println("密钥版本："+cityParamVo.getKeyVersion());
        }
    }

    /**
     * Test of queryTicketPrice method, of class ICommuService.
     */
    @Test
    public void testQueryTicketPrice() throws Exception {
        
        CallParam callParam = new CallParam();
        CallResult callResult = commuService.queryTicketPrice(callParam);
        System.out.println("结果：" + callResult.isSuccess());
        System.out.println("返回代码：" + callResult.getCode());
        System.out.println("返回信息：" + callResult.getMsg());
        if (callResult.isSuccess()) {
            List<TicketPriceVo> ticketPriceVos = callResult.getObjs();
            for(TicketPriceVo ticketPriceVo: ticketPriceVos){
                System.out.println("票价：" + ticketPriceVo.getCardPrice());
            }
        }
    }

    /**
     * Test of queryCardTypes method, of class ICommuService.
     */
    @Test
    public void testQueryCardTypes() throws Exception {
        
        CallParam callParam = new CallParam();
        CallResult callResult = commuService.queryCardTypes(callParam);
        System.out.println("结果：" + callResult.isSuccess());
        System.out.println("返回代码：" + callResult.getCode());
        System.out.println("返回信息：" + callResult.getMsg());
        if (callResult.isSuccess()) {
            List<TicketTypeVo> ticketTypeVos = callResult.getObjs();
            for (TicketTypeVo ticketTypeVo : ticketTypeVos) {
                System.out.println("票种代码：" + ticketTypeVo.getCardCode());
                System.out.println("票种描述：" + ticketTypeVo.getCardDesc());
            }
        }
    }

    /**
     * Test of updateDeviceStatus method, of class ICommuService.
     */
    @Test
    public void testUpdateDeviceStatus() throws Exception {
        
        CallParam callParam = new CallParam();
        callParam.setParam("07002");
        callParam.setParam("ouywl");
        callParam.setParam("0001");
        callParam.setParam("测试试");
        CallResult callResult = commuService.updateDeviceStatus(callParam);
        System.out.println("结果："+callResult.isSuccess());
        System.out.println("返回代码："+callResult.getCode());
        System.out.println("返回信息："+callResult.getMsg());
    }

    /**
     * Test of queryBlacklist method, of class ICommuService.
     */
    @Test
    public void testQueryBlacklist() throws Exception {
        
        CallParam callParam = new CallParam();
        String localPath = "d:\\localPath";
        String remotePath = "d:\\remotePath";
        callParam.setParam(new FtpLoginParamVo());
        callParam.setParam(localPath);
        callParam.setParam(remotePath);
        CallResult callResult = commuService.queryBlacklist(callParam);
        System.out.println("结果：" + callResult.isSuccess());
        System.out.println("返回代码：" + callResult.getCode());
        System.out.println("返回信息：" + callResult.getMsg());
        if (callResult.isSuccess()) {
            List<FtpFileParamVo> ftpFileParamVos = callResult.getObjs();
            for (FtpFileParamVo ftpFileParamVo : ftpFileParamVos) {
                System.out.println("文件：" + ftpFileParamVo.getFile());
            }
        }
    }

    /**
     * Test of querySamlist method, of class ICommuService.
     */
    @Test
    public void testQuerySamlist() throws Exception {
        
        CallParam callParam = new CallParam();
        String localPath = "d:\\localPath";
        String remotePath = "d:\\remotePath";
        callParam.setParam(new FtpLoginParamVo());
        callParam.setParam(localPath);
        callParam.setParam(remotePath);
        CallResult callResult = commuService.querySamlist(callParam);
        System.out.println("结果：" + callResult.isSuccess());
        System.out.println("返回代码：" + callResult.getCode());
        System.out.println("返回信息：" + callResult.getMsg());
        if (callResult.isSuccess()) {
            List<FtpFileParamVo> ftpFileParamVos = callResult.getObjs();
            for (FtpFileParamVo ftpFileParamVo : ftpFileParamVos) {
                System.out.println("文件：" + ftpFileParamVo.getFile());
            }
        }
    }

    /**
     * Test of querySignCards method, of class ICommuService.
     */
    @Test
    public void testQuerySignCards() throws Exception {
        
        CallParam callParam = new CallParam();
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderNo("111111111");
        orderVo.setBeginReqNo("11");
        orderVo.setEndReqNo("22");
        callParam.setParam(orderVo);
        CallResult callResult = commuService.querySignCards(callParam);
        System.out.println("结果：" + callResult.isSuccess());
        System.out.println("返回代码：" + callResult.getCode());
        System.out.println("返回信息：" + callResult.getMsg());
        if (callResult.isSuccess()) {
            List<SignCardVo> signCardVos = callResult.getObjs();
            for (SignCardVo signCardVo : signCardVos) {
                System.out.println("文件：" + signCardVo.getName());
            }
        }
    }

    /**
     * Test of queryProduceOrders method, of class ICommuService.
     */
    @Test
    public void testQueryProduceOrders() throws Exception {
        
        OrderParam orderParam = new OrderParam();
        orderParam.setWorkType("00");
        orderParam.setEmployeeId("luojun");
        CallResult callResult = commuService.queryProduceOrders(orderParam);
        System.out.println("结果：" + callResult.isSuccess());
        System.out.println("返回代码：" + callResult.getCode());
        System.out.println("返回信息：" + callResult.getMsg());
        if (callResult.isSuccess()) {
            List<Object[]> orders = callResult.getObjs();
            for (Object[] order : orders) {
                System.out.println(order[1]+"-"+order[2]+"-"+order[3]+"-"+order[23]);
            }
        }
    }

    /**
     * Test of updateOrderStatus method, of class ICommuService.
     */
    @Test
    public void testUpdateOrderStatus() throws Exception {
        
        CallParam callParam = new CallParam();
        String workType = "00";
        String employeeId = "luojun";
        String deviceId = "002";
        List<String> orders = new ArrayList<String>();
        String order1 = "00201308160002";
        String order2 = "00201308160004";
        orders.add(order1);
        orders.add(order2);
        callParam.setParam(workType);
        callParam.setParam(employeeId);
        callParam.setParam(deviceId);
        callParam.setParam(orders);
        
        CallResult callResult = commuService.updateOrderStatus(callParam);
        System.out.println("结果：" + callResult.isSuccess());
        System.out.println("返回代码：" + callResult.getCode());
        System.out.println("返回信息：" + callResult.getMsg());
        if (callResult.isSuccess()) {
            List<KeyValueVo> keyValueVos = callResult.getObjs();
            for (KeyValueVo keyValueVo : keyValueVos) {
                System.out.println(keyValueVo.getKey()+":"+keyValueVo.getValue());
            }
        }
    }

    /**
     * Test of closeCommu method, of class ICommuService.
     */
    //@Test
    public void testCloseCommu() throws Exception {
        
        CallResult callResult = commuService.closeCommu(null);
        System.out.println("关闭通讯结果："+callResult.isSuccess());
    }

}
