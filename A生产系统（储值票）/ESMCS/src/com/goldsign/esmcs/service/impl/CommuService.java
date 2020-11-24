/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.service.impl;

import com.goldsign.csfrm.service.impl.BaseService;
import com.goldsign.csfrm.util.DateHelper;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.csfrm.vo.*;
import com.goldsign.esmcs.commu.EsCommuClient;
import com.goldsign.esmcs.dao.IOrderDao;
import com.goldsign.esmcs.dao.impl.OrderDao;
import com.goldsign.esmcs.env.AppConstant;
import com.goldsign.esmcs.env.CommuConstant;
import com.goldsign.esmcs.env.ConfigConstant;
import com.goldsign.esmcs.exception.CommuException;
import com.goldsign.esmcs.service.ICommuService;
import com.goldsign.esmcs.thread.ComFileDownThread;
import com.goldsign.esmcs.util.ConfigUtil;
import com.goldsign.esmcs.util.Converter;
import com.goldsign.esmcs.util.Validator;
import com.goldsign.esmcs.vo.*;
import java.io.IOException;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * 通讯服务类
 * 
 * @author lenovo
 */
public class CommuService extends BaseService implements ICommuService{

    private static final Logger logger = Logger.getLogger(CommuService.class.getName());
    
    //通讯锁
    public static final Object COMMU_LOCK = new Object();
    
    private static CommuService commuService;
    
    private CommuService(){}
    
    public static CommuService getInstance(){
        
        if(null == commuService){
            commuService = new CommuService();
        }
        
        return commuService;
    }
    
    /**
     * 操作员登录
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult loginOperator(CallParam callParam)throws CommuException{
        
        AuthenResult authenResult = new AuthenResult();
        
        LoginParam param = (LoginParam)callParam;
        
        EsCommuWriteParam writeParam = new EsCommuWriteParam();
        writeParam.addStringToMessage(CommuConstant.MSG_OPER_LOGIN, 2);
        writeParam.addStringToMessage(CommuConstant.OPER_LOGIN_FLAG, 1);
        writeParam.addStringToMessage(param.getUserNo(), 8);
        writeParam.addStringToMessage(param.getPasswrod(), 8);
        writeParam.addStringToMessage(DateHelper.curDateToStr19yyyy_MM_dd_HH_mm_ss(), 20);
        writeParam.addStringToMessage(ConfigUtil.getDeviceNo(), 3);
        
        EsCommuReadResult readResult = null;
        try {
            synchronized(COMMU_LOCK){
                EsCommuClient.sendData(writeParam);
                readResult = EsCommuClient.readData();
            }
            if(!readResult.isSuccess()){
                authenResult.setMsg("通讯（操作员登录）异常，错误码："+readResult.getCode()+".");
                return authenResult;
            }
        } catch (Exception ex) {
            throw new CommuException(ex);
        }
        SysUserVo user = new SysUserVo();
        String userLevel = readResult.getCharString(2, 3);
        
        if(Validator.contain(CommuConstant.OPER_TYPE_CODE_SUCC, userLevel)){
            
            user.setAccount(param.getUserNo());
            user.setEmployeeLevel(userLevel);
            user.setUsername(param.getUserNo()+"("+Converter.getCommOperTypeDesc(userLevel)+")");
            AppConstant.USER_NO = param.getUserNo().trim();
            
            authenResult.setObj(user);
            authenResult.setResult(true);
 
        }else{
            authenResult.setMsg("非法制票员或密码错误，错误码："+userLevel+".");
            authenResult.setResult(false);
        }
        
        logger.info("验证操作员,结果："+authenResult.isSuccess());
        
        return authenResult;
    }
    
    /**
     * ES制卡和明细数据
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult uploadEsOrderFile(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
        
        EsCommuWriteParam writeParam = new EsCommuWriteParam();
        writeParam.addStringToMessage(CommuConstant.MSG_ORDER_UPLOAD, 2);
        //writeParam.addStringToMessage(ConfigUtil.getDeviceTypeAndNo(),5);
        writeParam.addStringToMessage(ConfigUtil.getDeviceNo(),5);
        
        List<String> orderFiles = (List<String>) callParam.getParam(0);
        try {
            writeParam.addShortToMessage((short)orderFiles.size());
            for(String orderFile: orderFiles){
                String[] orderFileArr = orderFile.split(AppConstant.MAO_SIGN);
                writeParam.addStringToMessage(orderFileArr[1], 30);
                writeParam.addStringToMessage(orderFileArr[0], 10);
            }
            synchronized(COMMU_LOCK){
                EsCommuClient.sendData(writeParam);
            }
            callResult.setResult(true);
            logger.info("上传ES订单文件消息成功！");
        }catch(Exception e){
            throw new CommuException(e);
        }       
        return callResult;
    }

    /**
     * 打开SOCKET通讯端口
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult openCommu(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
        
        Hashtable esCommus = (Hashtable) AppConstant.configs.get(ConfigConstant.EsCommuTag);
        String IP = (String) esCommus.get(ConfigConstant.EsCommuServerIpTag);
        Integer PORT = StringUtil.getInt((String)esCommus.get(ConfigConstant.EsCommuServerPortTag));      

        try {
            synchronized(COMMU_LOCK){
                EsCommuClient.openConServer(IP, PORT);
            }
            callResult.setResult(true);
            logger.info("SOCKET通讯端口成功！");
        } catch (Exception ex) {
            callResult.setResult(false);
            //throw new CommuException(ex);
            logger.info("SOCKET通讯端口失败！");
        } 

        return callResult;
    }

    /**
     * 接收审计和错误文件
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult downAuditAndErrorFile(CallParam callParam) throws CommuException,IOException,Exception {
        
        CallResult callResult = new CallResult();
        
        EsCommuReadResult readResult = null;
        try {
            synchronized(COMMU_LOCK){
                readResult = EsCommuClient.readData();
            }
            if(!readResult.isSuccess()){
                callResult.setMsg("通讯（下载审计和错误文件）异常，错误码："+readResult.getCode()+".");
                return callResult;
            }
        } catch (Exception ex) {
            throw new CommuException(ex);
        } 
        
        List<FtpFileParamVo> files = new ArrayList<FtpFileParamVo>();
        List<String> fileName = new ArrayList<String>();
        String deviceId = readResult.getCharString(2, 5);
        int len = readResult.getShort(7);
        for(int i=0; i<len; i++){
            String file = readResult.getCharString(i*40+9, 30);
            String operCode = readResult.getCharString(i*40+39, 10);
            FtpFileParamVo fileVo = new FtpFileParamVo(ConfigUtil.getFtpLoginParam());
            fileVo.setFile(file.trim());
            fileVo.setLocalPath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditLocalPathTag));
            fileVo.setRemotePath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditRemotePathTag));
            
            files.add(fileVo);
            fileName.add(file.trim());
        }
        if(!AppConstant.COMMU_AUDIT_ERROR_FILE_DOWN_STATUS){
            ComFileDownThread.push(files);
            AppConstant.COMMU_AUDIT_ERROR_FILE_DOWN_STATUS = true;
//            IOrderDao orderDao = new OrderDao();   //limj
//            orderDao.recordDownAuditAndErrorFile(fileName);
                    
        }
        
        callResult.setObjs(files);
        callResult.setResult(readResult.isSuccess());
        
        logger.info("下载审计和错误文件,结果："+readResult.isSuccess());
        
        return callResult;
    }

    /**
     * 下载参数代码
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult queryParams(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
        
        EsCommuWriteParam writeParam = new EsCommuWriteParam();
        writeParam.addStringToMessage(CommuConstant.MSG_PARAM_QUERY, 2);
        
        EsCommuReadResult readResult = null;
        try {
            synchronized(COMMU_LOCK){
                EsCommuClient.sendData(writeParam);
                readResult = EsCommuClient.readData();
            }
            if (!readResult.isSuccess()) {
                callResult.setMsg("通讯（查询参数）异常，错误码：" + readResult.getCode() + ".");
                return callResult;
            }
        } catch (Exception ex) {
            throw new CommuException(ex);
        }
        if(readResult.isData()){
            
            CityParamVo paramVo = new CityParamVo();
            paramVo.setCityCode(readResult.getCharString(2, 4).trim());
            paramVo.setIndustryCode(readResult.getCharString(6, 4).trim());
            paramVo.setKeyVersion(readResult.getCharString(10, 4).trim()); 
            paramVo.setSenderCode(readResult.getCharString(14, 4).trim());
            paramVo.setCardVersion(readResult.getCharString(18, 4).trim());
            paramVo.setAppVersion(readResult.getCharString(22, 4).trim());
            callResult.setObj(paramVo);
        }
        
        callResult.setResult(readResult.isSuccess());
        
        logger.info("下载城市代码,结果："+readResult.isSuccess());
        return callResult;
    }

    /**
     * 下载票价
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult queryTicketPrice(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
        
        EsCommuWriteParam writeParam = new EsCommuWriteParam();
        writeParam.addStringToMessage(CommuConstant.MSG_TICKET_PRICE, 2);
        EsCommuReadResult readResult = null;
        try {
            synchronized(COMMU_LOCK){
                EsCommuClient.sendData(writeParam);
                readResult = EsCommuClient.readData();
            }
            if (!readResult.isSuccess()) {
                callResult.setMsg("通讯（查询票价）异常，错误码：" + readResult.getCode() + ".");
                return callResult;
            }
        } catch (Exception ex) {
            throw new CommuException(ex);
        }
        List<TicketPriceVo> ticketPriceVos = new ArrayList<TicketPriceVo>();
        int len = readResult.getShort(2);
        for(int i=0; i<len; i++){
            TicketPriceVo ticketPriceVo = new TicketPriceVo();
            ticketPriceVo.setCardPrice(readResult.getCharString(i*10+4, 10).trim());
            
            ticketPriceVos.add(ticketPriceVo);
        }
        
        callResult.setObjs(ticketPriceVos);
        callResult.setResult(readResult.isSuccess());
        
        logger.info("下载票价,结果："+readResult.isSuccess());
        return callResult;
    }

    /**
     * 下载票种
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult queryCardTypes(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
        
        EsCommuWriteParam writeParam = new EsCommuWriteParam();
        writeParam.addStringToMessage(CommuConstant.MSG_TICKET_TYPE, 2);
        EsCommuReadResult readResult = null;
        try {
            synchronized(COMMU_LOCK){
                EsCommuClient.sendData(writeParam);
                readResult = EsCommuClient.readData();
            }
            if (!readResult.isSuccess()) {
                callResult.setMsg("通讯（查询票种）异常，错误码：" + readResult.getCode() + ".");
                return callResult;
            }
        } catch (Exception ex) {
            throw new CommuException(ex);
        }
        List<TicketTypeVo> ticketTypeVos = new ArrayList<TicketTypeVo>();
        int len = readResult.getShort(2);
        for(int i=0; i<len; i++){
            TicketTypeVo ticketTypeVo = new TicketTypeVo();
            ticketTypeVo.setCardCode(readResult.getCharString(i*(34)+4, 4).trim());
            ticketTypeVo.setCardDesc(readResult.getGbkString(i*(34)+8, 30).trim());
            
            ticketTypeVos.add(ticketTypeVo);
        }
        
        callResult.setObjs(ticketTypeVos);
        callResult.setResult(readResult.isSuccess());
        
        logger.info("下载票种,结果："+readResult.isSuccess());
        return callResult;
    }

    /**
     * 下载黑名单
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult queryBlacklist(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
        
        EsCommuWriteParam writeParam = new EsCommuWriteParam();
        writeParam.addStringToMessage(CommuConstant.MSG_CARD_BLACKLIST, 2);
        EsCommuReadResult readResult = null;
        try {
            synchronized(COMMU_LOCK){
                EsCommuClient.sendData(writeParam);
                readResult = EsCommuClient.readData();
            }
            if (!readResult.isSuccess()) {
                callResult.setMsg("通讯（下载黑名单）异常，错误码：" + readResult.getCode() + ".");
                return callResult;
            }
        } catch (Exception ex) {
            throw new CommuException(ex);
        }
        
        List<FtpFileParamVo> files = new ArrayList<FtpFileParamVo>();
        int len = readResult.getShort(2);
        for(int i=0; i<len; i++){
            String file = readResult.getCharString(i*30+4, 30);
            
            FtpFileParamVo fileVo = new FtpFileParamVo(ConfigUtil.getFtpLoginParam());
            fileVo.setFile(file.trim());
            fileVo.setLocalPath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpParamLocalPathTag));
            fileVo.setRemotePath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpParamRemotePathTag));
            
            files.add(fileVo);
        }
        ComFileDownThread.push(files);
        
        callResult.setObjs(files);
        callResult.setResult(readResult.isSuccess());
        
        logger.info("下载黑名单,结果："+readResult.isSuccess());
        return callResult;
    }

    /**
     * 下载SAM列表
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult querySamlist(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
    
        EsCommuWriteParam writeParam = new EsCommuWriteParam();
        writeParam.addStringToMessage(CommuConstant.MSG_SAM_LIST, 2);
        EsCommuReadResult readResult = null;
        try {
            synchronized(COMMU_LOCK){
                EsCommuClient.sendData(writeParam);
                readResult = EsCommuClient.readData();
            }
            if (!readResult.isSuccess()) {
                callResult.setMsg("通讯（下载SAM卡）异常，错误码：" + readResult.getCode() + ".");
                return callResult;
            }
        } catch (Exception ex) {
            throw new CommuException(ex);
        }
        
        List<FtpFileParamVo> files = new ArrayList<FtpFileParamVo>();
        int len = readResult.getShort(2);
        for(int i=0; i<len; i++){
            String file = readResult.getCharString(i*30+4, 30);
            
            FtpFileParamVo fileVo = new FtpFileParamVo(ConfigUtil.getFtpLoginParam());
            fileVo.setFile(file.trim());
            fileVo.setLocalPath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpParamLocalPathTag));
            fileVo.setRemotePath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpParamRemotePathTag));
  
            files.add(fileVo);
        }
        ComFileDownThread.push(files);
        
        callResult.setObjs(files);
        callResult.setResult(readResult.isSuccess());
        
        logger.info("下载SAM列表,结果："+readResult.isSuccess());
        return callResult;
    }

    /**
     * 关闭通讯
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult closeCommu(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
        
        try {
            synchronized (COMMU_LOCK) {
                EsCommuClient.disConServer();
            }
            callResult.setResult(true);
            
            logger.info("ES通讯关闭，成功！");
        } catch (Exception ex) {
            callResult.setResult(false);
            //throw new CommuException(ex);
            
            logger.info("ES通讯关闭，异常！");
        }

        return callResult;
    }

    /**
     * 查询生产订单
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult queryProduceOrders(CallParam callParam) throws CommuException {
        
        CallResult callResult =  new CallResult();
 
        OrderParam orderParam = (OrderParam)callParam;
        
        EsCommuWriteParam writeParam = new EsCommuWriteParam();
        writeParam.addStringToMessage(CommuConstant.MSG_ORDER_QUERY, 2);
        writeParam.addStringToMessage(orderParam.getWorkType(), 2);
        writeParam.addStringToMessage(orderParam.getEmployeeId(), 8);
        
        EsCommuReadResult readResult = null;
        try {
            synchronized(COMMU_LOCK){
                EsCommuClient.sendData(writeParam);
                readResult = EsCommuClient.readData();
            }
            if (!readResult.isSuccess()) {
                callResult.setMsg("通讯（查询生产订单）异常，错误码：" + readResult.getCode() + ".");
                return callResult;
            }
        } catch (Exception ex) {
            throw new CommuException(ex);
        }
        
        List<Object[]> orders = new ArrayList<Object[]>();
        int len = readResult.getShort(2);
        int rRLen = 155;//报文正文长度
        logger.info("订单长度："+len);
        String str =  "";
        Hashtable commons = (Hashtable) AppConstant.configs.get(ConfigConstant.CommonTag);
        String deviceNo = (String) commons.get(ConfigConstant.CommonDeviceNoTag);
        for(int i=0; i<len; i++){
            Object[] order = new Object[30];
            order[0] = Converter.getEsOrderStatusDes(AppConstant.ES_ORDER_STATUS_BEGIN_NO);
            order[1] = readResult.getCharString(i*(rRLen)+4, 14).trim();//订单编号
            order[2] = readResult.getCharString(i*(rRLen)+18, 4).trim();//票卡类型
            order[3] = readResult.getGbkString(i*(rRLen)+22, 30).trim();//票卡名称
            order[4] = readResult.getCharString(i*(rRLen)+52, 8).trim();//票卡有效期
            order[5] = readResult.getLong(i*(rRLen)+60);//面值
            order[6] = readResult.getLong(i*(rRLen)+64);//押金
            order[7] = readResult.getCharString(i*(rRLen)+68, 10).trim();//开始申请编号
            order[8] = readResult.getCharString(i*(rRLen)+78, 10).trim();//结束申请编号
            order[9] = readResult.getCharString(i*(rRLen)+88, 8).trim();//开始序列号
            order[10] = readResult.getCharString(i*(rRLen)+96, 8).trim();//终止序列号
            order[11] = readResult.getCharString(i*(rRLen)+104, 8).trim();//日期
            order[12] = readResult.getLong(i*(rRLen)+112);//数量
            order[13] = 0;
            order[14] = 0;
            order[15] = 0;
            order[16] = readResult.getCharString(i*(rRLen)+116, 3).trim();//标识码
            order[17] = readResult.getCharString(i*(rRLen)+119, 2).trim();//线路代码
            order[18] = readResult.getCharString(i*(rRLen)+121, 2).trim();//站点代码
            order[19] = readResult.getCharString(i*(rRLen)+123, 8).trim();//乘次票应用有效期开始时间
            order[20] = readResult.getCharString(i*(rRLen)+131, 5).trim();//乘次票使用有效期
            order[21] = readResult.getCharString(i*(rRLen)+136, 2).trim();//限制出站线路代码
            order[22] = readResult.getCharString(i*(rRLen)+138, 2).trim();//限制出站站点代码
            order[23] = readResult.getCharString(i*(rRLen)+140, 3).trim();//限制模式
            order[24] = readResult.getCharString(i*(rRLen)+143, 1);//销售标记
            order[25] = readResult.getCharString(i*(rRLen)+144, 1);//测试标记
            order[26] = readResult.getLong(i*(rRLen)+145);//可充值上限
            order[27] = AppConstant.ES_ORDER_STATUS_BEGIN_NO;
            order[28] = orderParam.getWorkType();
            order[29] = orderParam.getEmployeeId();
            str =  readResult.getCharString(i*(rRLen)+149, 10).trim();
            if(str==null||str.equals(deviceNo)||str.equals("")){
                orders.add(order);
            }
        }
       
        callResult.setObjs(orders);
        callResult.setResult(readResult.isSuccess());
        
        logger.info("下载生产订单,结果："+readResult.isSuccess());
        
        return callResult;
    }

    /**
     * 下载记名卡
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult querySignCards(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
        
        OrderVo orderVo = (OrderVo) callParam.getParam();
        
        EsCommuWriteParam writeParam = new EsCommuWriteParam();
        writeParam.addStringToMessage(CommuConstant.MSG_SIGN_CARD, 2);
        writeParam.addStringToMessage(orderVo.getOrderNo(), 14);
        writeParam.addStringToMessage(orderVo.getBeginReqNo(), 10);
        writeParam.addStringToMessage(orderVo.getEndReqNo(), 10);
        
        EsCommuReadResult readResult = null;
        try {
            synchronized(COMMU_LOCK){
                EsCommuClient.sendData(writeParam);
                readResult = EsCommuClient.readData();
            }
            if (!readResult.isSuccess()) {
                callResult.setMsg("通讯（查询记名卡）异常，错误码：" + readResult.getCode() + ".");
                return callResult;
            }
        } catch (Exception ex) {
            throw new CommuException(ex);
        }
        Map<String, SignCardVo> signCardVos = new TreeMap<String, SignCardVo>();
        int len = readResult.getShort(2);
        for(int i=0; i<len; i++){
            SignCardVo signCardVo = new SignCardVo();
            //hwj 20160317 modify name 8char to 20char;                            
            /*
            signCardVo.setReqNo(readResult.getCharString(i*(46)+4, 10));
            signCardVo.setName(readResult.getGbkString(i*(46)+14, 8));
            signCardVo.setGender(readResult.getCharString(i*(46)+22, 1));
            signCardVo.setIdType(readResult.getCharString(i*(46)+23, 1));
            signCardVo.setIdCode(readResult.getCharString(i*(46)+24, 18));
            signCardVo.setIdEffDate(readResult.getCharString(i*(46)+42, 8));
            * 
            */
            signCardVo.setReqNo(readResult.getCharString(i*(98)+4, 10));
            signCardVo.setName(readResult.getUTFSring(i*(98)+14, 60));
            signCardVo.setGender(readResult.getCharString(i*(98)+74, 1));
            signCardVo.setIdType(readResult.getCharString(i*(98)+75, 1));
            signCardVo.setIdCode(readResult.getCharString(i*(98)+76, 18));
            signCardVo.setIdEffDate(readResult.getCharString(i*(98)+94, 8));
           
            signCardVos.put(orderVo.getReqNo(i), signCardVo);
        }
        
        callResult.setObj(signCardVos);
        callResult.setResult(readResult.isSuccess());
        
        logger.info("下载记名卡,结果："+readResult.isSuccess());
        return callResult;
    }

    /**
     * 更新订单状态
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult updateOrderStatus(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
        String workType = (String) callParam.getParam(0);
        String employeeId = (String) callParam.getParam(1);
        List<String> orders = (List<String>) callParam.getParam(2);
        
        EsCommuWriteParam writeParam = new EsCommuWriteParam();
        writeParam.addStringToMessage(CommuConstant.MSG_ORDER_UPDATE, 2);
        writeParam.addStringToMessage(workType, 2);
        writeParam.addStringToMessage(employeeId, 8);
        writeParam.addStringToMessage(ConfigUtil.getDeviceNo(), 3);
        EsCommuReadResult readResult = null;
        try {
            int len = orders.size();
            writeParam.addShortToMessage((short)len);
            for(int i=0; i<len; i++){
                writeParam.addStringToMessage(orders.get(i), 14);
            }
            synchronized(COMMU_LOCK){
                EsCommuClient.sendData(writeParam);
                readResult = EsCommuClient.readData();
            }
            if (!readResult.isSuccess()) {
                callResult.setMsg("通讯（更新订单状态）异常，错误码：" + readResult.getCode() + ".");
                return callResult;
            }
        } catch (Exception ex) {
            throw new CommuException(ex);
        }
        List<KeyValueVo> orderVos = new ArrayList<KeyValueVo>();
        int len = readResult.getShort(2);
        for(int i=0; i<len; i++){
            
            KeyValueVo orderRet = new KeyValueVo();
            orderRet.setKey(readResult.getCharString(i*(16)+4, 14));
            orderRet.setValue(readResult.getCharString(i*(16)+18, 2));

            orderVos.add(orderRet);
            logger.info("更改订单状态,结果key："+orderRet.getKey());  
            logger.info("更改订单状态,结果value："+orderRet.getValue());
        }
        
        callResult.setObjs(orderVos);
        callResult.setResult(readResult.isSuccess());
        
        logger.info("更改订单状态,结果："+readResult.isSuccess());   
 
        return callResult;
    }
    
    /**
     * 更改设备状态
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult updateDeviceStatus(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
        String employeeId = (String) callParam.getParam(0); 
        String status = (String) callParam.getParam(1);
        String remark = (String) callParam.getParam(2);
        
        EsCommuWriteParam writeParam = new EsCommuWriteParam();
        writeParam.addStringToMessage(CommuConstant.MSG_DEVICE_UPDATE, 2);
        writeParam.addStringToMessage(ConfigUtil.getDeviceTypeAndNo(), 5);
        writeParam.addStringToMessage(employeeId, 10);
        writeParam.addStringToMessage(DateHelper.curDateToStr15yyyyMMdd_HHmmss(), 20);
        writeParam.addStringToMessage(status, 4);
        writeParam.addGbkStringToMessage(remark, 30);
        
        try {
            synchronized(COMMU_LOCK){
                EsCommuClient.sendData(writeParam);
            }
        }catch(Exception e){
            throw new CommuException(e);
        }
        
        callResult.setResult(true);
        
        logger.info("更改设备状态成功！");
        return callResult;
    }

    /**
     * 是否通讯正常
     * 
     * @return
     * @throws CommuException 
     */
    @Override
    public boolean isCommuOk() throws CommuException {
        try {
            synchronized(COMMU_LOCK){
                boolean result = EsCommuClient.isConnected();
                logger.info("ES通讯测试连接，结果："+result);
                return result;
            }
        }catch(Exception e){
            throw new CommuException(e);
        }
    }

    /**
     * 设置加密机锁
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult setKmsLock(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
        String isLock = (String) callParam.getParam();
       
        EsCommuWriteParam writeParam = new EsCommuWriteParam();
        writeParam.addStringToMessage(CommuConstant.MSG_KMS_LOCK, 2);
        writeParam.addStringToMessage(isLock, 1);
        EsCommuReadResult readResult = null;
        try {
            synchronized(COMMU_LOCK){
                EsCommuClient.sendData(writeParam);
                readResult = EsCommuClient.readData();
            }
            if (!readResult.isSuccess()) {
                callResult.setMsg("通讯（锁加密机）异常，错误码：" + readResult.getCode() + ".");
                return callResult;
            }
        } catch (Exception ex) {
            throw new CommuException(ex);
        }
        String result = readResult.getCharString(2, 1);
        
        callResult.setObj(result);
        callResult.setResult(readResult.isSuccess());
        
        logger.info("锁加密机,结果："+readResult.isSuccess());
        return callResult;
    }
}
