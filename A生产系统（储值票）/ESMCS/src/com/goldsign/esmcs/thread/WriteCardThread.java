/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.thread;

import com.goldsign.csfrm.util.DateHelper;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.esmcs.application.Application;
import com.goldsign.esmcs.env.AppConstant;
import com.goldsign.esmcs.env.ErrorConstant;
import com.goldsign.esmcs.env.SynLockConstant;
import com.goldsign.esmcs.exception.CardParamException;
import com.goldsign.esmcs.exception.FileException;
import com.goldsign.esmcs.exception.RwJniException;
import com.goldsign.esmcs.service.IFileService;
import com.goldsign.esmcs.service.IKmsService;
import com.goldsign.esmcs.service.IRwDeviceService;
import com.goldsign.esmcs.service.impl.KmsService;
import com.goldsign.esmcs.ui.dialog.MakeCardDialog;
import com.goldsign.esmcs.util.Validator;
import com.goldsign.esmcs.vo.*;
import java.util.Date;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author lenovo
 */
public class WriteCardThread extends Thread{
    
    private static final Logger logger = Logger.getLogger(WriteCardThread.class.getName());
    
    protected MakeCardDialog makeCardDialog = null;//制卡对话框
    protected OrderVo curOrderVo;//当前订单
    protected IRwDeviceService rwDeviceService;//RW设备服务
    protected IFileService fileService;//文件服务
    protected IKmsService kmsService;//加密机服务
    
    public WriteCardThread(){}
    
    public WriteCardThread(MakeCardDialog makeCardDialog){
        
        this.makeCardDialog = makeCardDialog;
        this.curOrderVo = makeCardDialog.getCurOrderVo();
        this.fileService = ((Application)AppConstant.application).getFileService();
        this.kmsService = KmsService.getInstance();
    }
    
    /**
     * 读卡前业务判断
     * 
     * @param callParam
     * @return 
     */
    protected CallResult readCardBeforeBusi(CallParam callParam){
        
        CallResult callResult = new CallResult();
        
        
        callResult.setResult(true);
        
        return callResult;
    }
    
    /**
     * 写卡前业务判断
     * 
     * @param callParam
     * @return 
     */
    protected CallResult writeCardBeforeBusi(CallParam callParam){
    
        CallResult callResult = new CallResult();
        
        callResult.setResult(true);
        
        return callResult;
    }
 
    /**
     * 是否是记名卡 注：目前，只有申请号非空就认为是记名卡，以前可能会修改
     *
     * @param order
     * @return
     */
    private boolean isNeedSignCard(WriteCardParam writeCardParam){

        OrderInVo order = writeCardParam.getOrderInVo();
        if(!StringUtil.isEmpty(order.getApplicationNO())){
            return true;
        }
        return false;
    }
    
    protected void sleepTime(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            logger.error(ex);
        }
    }
    
    /**
     * 写卡
     * 
     * @param callParam
     * @return 
     */
    protected CallResult writeCard(CallParam callParam) throws RwJniException{
        
        curOrderVo.setBeginRunTime();//写卡开始
        
        CallResult callResult = readCardBeforeBusi(callParam);
        if(!callResult.isSuccess()){
            logger.warn("读卡前业务判断失败，"+callResult.getMsg());
            return callResult;
        }
        
        logger.info(Thread.currentThread().getId()+"线程,开始票卡分析...");
        for(int i=0;i<5;i++){
            callResult = readCardRw(callParam);// rwDeviceService.readCard(callParam);//hwj modify 20150315
            if(callResult.isSuccess()){
                logger.info("读卡成功！");
                break;
            }
            logger.warn("读卡"+(i+1)+"次失败！");
            sleepTime(500);//写卡前，停顿下下
        }
        if (!callResult.isSuccess()) {
            logger.warn("票卡分析失败，"+callResult.getMsg());
            return callResult;
        }
        logger.info("票卡分析成功！");   
        AnalyzeVo analyzeVo = (AnalyzeVo) callResult.getObj();
        
        String phyNo = analyzeVo.getcPhysicalID();
        if(curOrderVo.containGoodCard(phyNo)){
            logger.info("已制好卡,物理卡号："+phyNo);
            ((WriteCardParam)callParam).setMakedCard(true);
            callResult.setResult(true);
            return callResult;
        }
        if(curOrderVo.containBadCard(phyNo)){
            logger.info("已制坏卡,物理卡号："+phyNo);
            ((WriteCardParam)callParam).setMakedCard(true);
            callResult.setResult(false);
            return callResult;
        }
        
        callResult = setOrderInParam(callParam, analyzeVo);
        if (!callResult.isSuccess()) {
             logger.warn("设置卡参数失败," + callResult.getMsg());
            return callResult;
        }
        callParam.setParam(analyzeVo);
        logger.info("设置卡参数成功！");

        //找记名卡参数
        if(isNeedSignCard((WriteCardParam)callParam)){
            callResult = setSignCardParam((WriteCardParam)callParam);
            if(!callResult.isSuccess()){
                logger.warn("设置记名卡失败，"+callResult.getMsg());
                return callResult;
            }
            logger.info("设置记名卡成功！");
        }

        callResult = writeCardBeforeBusi(callParam);
        if(!callResult.isSuccess()){
            logger.warn("写卡前业务判断失败，"+callResult.getMsg());
            return callResult;
        }
        logger.info("写卡前业务判断成功！");
        
        callResult = getKmsCardKey(callParam);
        if(!callResult.isSuccess()){
            logger.warn("取密钥失败，错误码："+callResult.getCode());
            if(ErrorConstant.KMS_CALL_ERROR_REAUTHEN.equals(callResult.getCode())){
                callResult = regetKmsCardKey(callParam);
            }
            if(!callResult.isSuccess()){
                logger.warn("重取密钥失败，错误码："+callResult.getCode());
                return callResult;
            }
        }
        logger.info("取密钥成功！");
        KmsCardVo kmsCardVo = (KmsCardVo) callResult.getObj(); 
        kmsCardVo.setCardProducerCode(analyzeVo.getCardProducerCode());//hwj add 20160224 增加卡商代码
        callParam.setParam(kmsCardVo);             
        
        logger.info("开始写卡...");
        callResult = writeCardRw(callParam);// rwDeviceService.writeCard(callParam); //hwj modify 20150315
                
        curOrderVo.setEndRunTime();//写卡结束        
        
        return callResult;
    }
    
    protected CallResult readCardRw(CallParam callParam)throws RwJniException{
    
        return rwDeviceService.readCard(callParam);
    }
    
    protected CallResult writeCardRw(CallParam callParam)throws RwJniException{
    
        return rwDeviceService.writeCard(callParam);
    }
    
    /**
     * 重取密钥
     * 
     * @param callParam
     * @return 
     */
    private CallResult regetKmsCardKey(CallParam callParam){
    
        CallResult callResult = new CallResult();
        callResult = kmsService.reauthor();
        if(!callResult.isSuccess()){
            logger.warn("加密机重认证失败，"+callResult.getMsg());
            return callResult;
        }
        return getKmsCardKey(callParam);
    }
    
    /**
     * 记名卡参数
     *
     * @param reqNo
     * @return
     */
    private CallResult setSignCardParam(WriteCardParam writeCardParam){
        
        CallResult callResult = new CallResult();
        
        OrderInVo orderInVo = writeCardParam.getOrderInVo();
        String reqNo = orderInVo.getApplicationNO();
        SignCardVo signCardVo = AppConstant.signCardVos.get(reqNo);
        if(null == signCardVo){
            callResult.setMsg("找不到记名卡资料，申请号："+reqNo);
            logger.warn("找不到记名卡资料，申请号："+reqNo);
            return callResult;
        }
        
        //换成真正的记名卡申请号
        orderInVo.setApplicationNO(signCardVo.getReqNo());
        
        //
        SignCardParam signCardParam = new SignCardParam();
        signCardParam.setEmployeeNo(signCardVo.getIdCode());
        signCardParam.setEmployeeName(signCardVo.getName());
        signCardParam.setSex(signCardVo.getGender());
        signCardParam.setIdType(signCardVo.getIdType());
        
        writeCardParam.setSignCardParam(signCardParam);
        callResult.setResult(true);
        
        return callResult;
    }
    
    /**
     * 取得密钥
     * 
     * @param analyzeVo
     * @return 
     */
    protected CallResult getKmsCardKey(CallParam callParam){

        CallResult callResult = new CallResult();
        callResult.setResult(true);
        
        return callResult;
    }
    
    /**
     * 更新当前订单
     *
     * @param result
     */
    protected void updateOrder(boolean result) {

        synchronized(SynLockConstant.SYN_FINISH_LOCK){
            if (result) {
                curOrderVo.setGoodCardNum(curOrderVo.getGoodCardNum() + 1);
            } else {
                curOrderVo.setBadCardNum(curOrderVo.getBadCardNum() + 1);
            }
        }
    }
    
    /**
     * 写文件
     *
     * @param callResult
     * @throws FileException
     */
    protected void writeFile(CallResult callResult, OrderInVo orderInVo) 
            throws FileException {

        OrderOutVo orderOutVo = getOrderOutVo(callResult, orderInVo);
        EsOrderDetailVo esOrderDetailVo = getEsOrderDetailVo(orderOutVo);

        if (callResult.isSuccess()) {
            fileService.writeGoodOrder(esOrderDetailVo);//写好文件
        } else {
            fileService.writeBadOrder(esOrderDetailVo);//写坏文件
        }

        fileService.writeMakingOrder(curOrderVo);//写制卡文件
    }
    
    
    /**
     * 取得写卡参数，包括工作类型
     *
     * @return
     */
    protected CallParam getCallParam(OrderVo orderVo) {

        WriteCardParam callParam = new WriteCardParam();
        String workType = orderVo.getWorkType();
        callParam.setWorkType(workType);

        return callParam;
    }
    
    /**
     * 取得写卡参数，包括工作类型和输入订单
     * 
     * @return 
     */
    protected CallResult setOrderInParam(CallParam callParam, AnalyzeVo analyzeVo){
    
        CallResult callResult = new CallResult();
        
        try{
            WriteCardParam writeCardParam = (WriteCardParam)callParam;
            String workType = writeCardParam.getWorkType();
            OrderInVo orderInVo = getOrderInVo(workType, analyzeVo);
            writeCardParam.setOrderInVo(orderInVo);
            
            callResult.setResult(true);
        }catch(Exception e){
            callResult.setMsg(e.getMessage());
            WriteCardParam writeCardParam = (WriteCardParam)callParam;
            String workType = writeCardParam.getWorkType();
            OrderInVo orderInVo = getOrderInVos(workType, analyzeVo);
            writeCardParam.setOrderInVo(orderInVo);
        }
        
        return callResult;
    }
    
    /**
     * 取得输入订单，由当前订单生成输入订单
     * 
     * @return 
     */
    protected OrderInVo getOrderInVo(String workType, AnalyzeVo analyzeVo) {
        
        if(AppConstant.WORK_TYPE_INITI.equals(workType)){
            return getInitOrderInVo(analyzeVo);
        }else if(AppConstant.WORK_TYPE_HUNCH.equals(workType)){
            return getHunchOrderInVo(analyzeVo);
        }else if(AppConstant.WORK_TYPE_AGAIN.equals(workType)){
            return getAgainOrderInVo(analyzeVo);
        }else if(AppConstant.WORK_TYPE_LOGOUT.equals(workType)){
            return getLogoutOrderInVo(analyzeVo);
        }else if(AppConstant.WORK_TYPE_CLEAR.equals(workType)){
            return getClearOrderInVo(analyzeVo);
        }
        
        return null;
    }
    
     /**
     * 取得输入订单，由当前订单生成输入订单
     * 
     * @return 
     */
    protected OrderInVo getOrderInVos(String workType, AnalyzeVo analyzeVo) {
        
        if(AppConstant.WORK_TYPE_INITI.equals(workType)){
            return getInitOrderInVos(analyzeVo);
        }else if(AppConstant.WORK_TYPE_HUNCH.equals(workType)){
            return getHunchOrderInVo(analyzeVo);
        }else if(AppConstant.WORK_TYPE_AGAIN.equals(workType)){
            return getAgainOrderInVo(analyzeVo);
        }else if(AppConstant.WORK_TYPE_LOGOUT.equals(workType)){
            return getLogoutOrderInVo(analyzeVo);
        }else if(AppConstant.WORK_TYPE_CLEAR.equals(workType)){
            return getClearOrderInVo(analyzeVo);
        }
        
        return null;
    }
    
    /**
     * 初始化参数
     * 
     * @param analyzeVo
     * @return 
     */
    protected OrderInVo getInitOrderInVo(AnalyzeVo analyzeVo) throws CardParamException{
        
        OrderInVo orderInVo = new OrderInVo();
        orderInVo.setOrderNo(curOrderVo.getOrderNo());
        orderInVo.setApplicationNO(curOrderVo.getReqNo());
        orderInVo.setTicketType(curOrderVo.getCardTypeCode());
        orderInVo.setPhysicalID(analyzeVo.getcPhysicalID());
        orderInVo.setDeposite(curOrderVo.getDeposit());
        orderInVo.setValue(curOrderVo.getPrintMoney());
        orderInVo.setRechargeTopValue(curOrderVo.getMaxRecharge());
        orderInVo.setSaleActiveFlag(curOrderVo.getSaleFlag());
        orderInVo.setSenderCode(AppConstant.cityParamVo.getSenderCode());//"5320"
        orderInVo.setCityCode(AppConstant.cityParamVo.getCityCode());//"4100"
        orderInVo.setBusiCode(AppConstant.cityParamVo.getIndustryCode());//"0000"
        orderInVo.setTestFlag(curOrderVo.getTestFlag());
        orderInVo.setIssueDate(DateHelper.curDateToStr8yyyyMMdd());
        orderInVo.setCardVersion(AppConstant.cityParamVo.getCardVersion());//"0805"
        orderInVo.setAppVersion(AppConstant.cityParamVo.getAppVersion());//"01"
        
        //if(curOrderVo.getWorkType().equals("00")||curOrderVo.getWorkType().equals("02")){
            
        orderInVo.setLogicDate("00000000"); 
        orderInVo.setLogicDays("000"); 
        if(!Validator.contain(AppConstant.CARD_TYPE_TOKEN, orderInVo.getTicketType())){
            orderInVo.setcEndExpire(curOrderVo.getCardEffTime());
            orderInVo.setcStartExpire(DateHelper.curDateToStr8yyyyMMdd());  
            
        }else{
            orderInVo.setcEndExpire("00000000");
            orderInVo.setcStartExpire("00000000");              
        }
       // }
//        if(curOrderVo.getWorkType().equals("01")){
//            
//            if(Validator.contain(AppConstant.CARD_TYPE_TCT, orderInVo.getTicketType())){
//                orderInVo.setLogicDate(curOrderVo.getTctEffBeginTime());
//                orderInVo.setLogicDays(curOrderVo.getTctEffTime());  
//
//            }else{
//
//                orderInVo.setLogicDate(curOrderVo.getDate());
//                long day2 = DateHelper.str8yyyyMMddToDate(curOrderVo.getCardEffTime()).getTime()- DateHelper.str8yyyyMMddToDate(curOrderVo.getDate()).getTime();
//                orderInVo.setLogicDays(Long.toString(day2/AppConstant.DAY));  
//            } 
//            orderInVo.setcEndExpire("00000000");
//            orderInVo.setcStartExpire("00000000");  
//        }
        orderInVo.setEntryLineStation(curOrderVo.getLineCode()+curOrderVo.getStationCode());
        orderInVo.setExitEntryMode(curOrderVo.getLimitMode());//limj
        orderInVo.setExitLineStation(curOrderVo.getLimitExitLineCode()+curOrderVo.getLimitExitStationCode());//limj        
        orderInVo.setSamID(getSamNo());
        
        //hwj add 20160117增加卡商代码
        orderInVo.setCardProducerCode(analyzeVo.getCardProducerCode());
        orderInVo.setLogicalID(getCardLogicNo(analyzeVo).substring(0,16));
      
        return orderInVo;
    }
    
    
       /**
     * 初始化参数
     * 
     * @param analyzeVo
     * @return 
     */
    protected OrderInVo getInitOrderInVos(AnalyzeVo analyzeVo) throws CardParamException{
        
        OrderInVo orderInVo = new OrderInVo();
        orderInVo.setOrderNo(curOrderVo.getOrderNo());
        orderInVo.setApplicationNO(curOrderVo.getReqNo());
        orderInVo.setTicketType(curOrderVo.getCardTypeCode());
        orderInVo.setPhysicalID(analyzeVo.getcPhysicalID());
        orderInVo.setDeposite(curOrderVo.getDeposit());
        orderInVo.setValue(curOrderVo.getPrintMoney());
        orderInVo.setRechargeTopValue(curOrderVo.getMaxRecharge());
        orderInVo.setSaleActiveFlag(curOrderVo.getSaleFlag());
        orderInVo.setSenderCode(AppConstant.cityParamVo.getSenderCode());//"5320"
        orderInVo.setCityCode(AppConstant.cityParamVo.getCityCode());//"4100"
        orderInVo.setBusiCode(AppConstant.cityParamVo.getIndustryCode());//"0000"
        orderInVo.setTestFlag(curOrderVo.getTestFlag());
        orderInVo.setIssueDate(DateHelper.curDateToStr8yyyyMMdd());
        orderInVo.setCardVersion(AppConstant.cityParamVo.getCardVersion());//"0805"
        orderInVo.setAppVersion(AppConstant.cityParamVo.getAppVersion());//"01"
        
        //if(curOrderVo.getWorkType().equals("00")||curOrderVo.getWorkType().equals("02")){
            
        orderInVo.setLogicDate("00000000"); 
        orderInVo.setLogicDays("000"); 
        if(!Validator.contain(AppConstant.CARD_TYPE_TOKEN, orderInVo.getTicketType())){
            orderInVo.setcEndExpire(curOrderVo.getCardEffTime());
            orderInVo.setcStartExpire(DateHelper.curDateToStr8yyyyMMdd());  
            
        }else{
            orderInVo.setcEndExpire("00000000");
            orderInVo.setcStartExpire("00000000");              
        }
       // }
//        if(curOrderVo.getWorkType().equals("01")){
//            
//            if(Validator.contain(AppConstant.CARD_TYPE_TCT, orderInVo.getTicketType())){
//                orderInVo.setLogicDate(curOrderVo.getTctEffBeginTime());
//                orderInVo.setLogicDays(curOrderVo.getTctEffTime());  
//
//            }else{
//
//                orderInVo.setLogicDate(curOrderVo.getDate());
//                long day2 = DateHelper.str8yyyyMMddToDate(curOrderVo.getCardEffTime()).getTime()- DateHelper.str8yyyyMMddToDate(curOrderVo.getDate()).getTime();
//                orderInVo.setLogicDays(Long.toString(day2/AppConstant.DAY));  
//            } 
//            orderInVo.setcEndExpire("00000000");
//            orderInVo.setcStartExpire("00000000");  
//        }
        orderInVo.setEntryLineStation(curOrderVo.getLineCode()+curOrderVo.getStationCode());
        orderInVo.setExitEntryMode(curOrderVo.getLimitMode());//limj
        orderInVo.setExitLineStation(curOrderVo.getLimitExitLineCode()+curOrderVo.getLimitExitStationCode());//limj        
        orderInVo.setSamID(getSamNo());       
        //hwj add 20160117增加卡商代码
        orderInVo.setCardProducerCode(analyzeVo.getCardProducerCode());
      
        return orderInVo;
    }
    
    
    /**
     * 取逻辑卡号
     * 
     * @param analyzeVo
     * @return 
     */
    protected String getCardLogicNo(AnalyzeVo analyzeVo){
        return curOrderVo.getSeqNo16();
    }
    
    /**
     * 取得SAM卡号
     * 
     * @return 
     */
    protected String getSamNo(){
        return "0000000000000000";
    }
    
    /**
     * 预赋值参数
     * 
     * @param analyzeVo
     * @return 
     */
    protected OrderInVo getHunchOrderInVo(AnalyzeVo analyzeVo) {
        
        OrderInVo orderInVo = new OrderInVo();
        orderInVo.setOrderNo(curOrderVo.getOrderNo());
        orderInVo.setApplicationNO(curOrderVo.getReqNo());
        orderInVo.setTicketType(curOrderVo.getCardTypeCode());
        orderInVo.setLogicalID(analyzeVo.getcLogicalID());
        orderInVo.setDeposite(curOrderVo.getDeposit());
        orderInVo.setValue(curOrderVo.getPrintMoney());
        orderInVo.setRechargeTopValue(curOrderVo.getMaxRecharge());
        orderInVo.setSaleActiveFlag(curOrderVo.getSaleFlag());
        if(Validator.contain(AppConstant.CARD_TYPE_TCT, orderInVo.getTicketType())){
            orderInVo.setLogicDate(curOrderVo.getTctEffBeginTime());
            orderInVo.setLogicDays(curOrderVo.getTctEffTime());  

        }else if(Validator.contain(AppConstant.CARD_TYPE_TOKEN, orderInVo.getTicketType())){
            orderInVo.setLogicDate(curOrderVo.getCardEffTime());
            orderInVo.setLogicDays("1"); 
        }else{

            orderInVo.setLogicDate(DateHelper.curDateToStr8yyyyMMdd());
            long day2 = DateHelper.str8yyyyMMddToDate(curOrderVo.getCardEffTime()).getTime()- DateHelper.str8yyyyMMddToDate(DateHelper.curDateToStr8yyyyMMdd()).getTime();
            orderInVo.setLogicDays(Long.toString(day2/AppConstant.DAY));   
        } 
                  
        orderInVo.setExitEntryMode(curOrderVo.getLimitMode());
        orderInVo.setEntryLineStation(curOrderVo.getLineCode()+curOrderVo.getStationCode());
        orderInVo.setExitLineStation(curOrderVo.getLimitExitLineCode()+curOrderVo.getLimitExitStationCode());
        orderInVo.setcEndExpire("00000000");
        orderInVo.setcStartExpire("00000000");  
        orderInVo.setPhysicalID(analyzeVo.getcPhysicalID());
        orderInVo.setSamID(getSamNo());
        
         //hwj add 20160117增加卡商代码
        orderInVo.setCardProducerCode(analyzeVo.getCardProducerCode());
        
        return orderInVo;     
    }
    
      /**
     * 预赋值参数
     * 
     * @param analyzeVo
     * @return 
     */
    protected OrderInVo getHunchOrderInVos(AnalyzeVo analyzeVo) {
        
        OrderInVo orderInVo = new OrderInVo();
        orderInVo.setOrderNo(curOrderVo.getOrderNo());
        orderInVo.setApplicationNO(curOrderVo.getReqNo());      
        orderInVo.setPhysicalID(analyzeVo.getcPhysicalID());
        orderInVo.setTicketType(curOrderVo.getCardTypeCode()); 
        orderInVo.setLogicalID(analyzeVo.getcLogicalID());
        orderInVo.setDeposite(curOrderVo.getDeposit());
        orderInVo.setValue(curOrderVo.getPrintMoney());
        orderInVo.setRechargeTopValue(curOrderVo.getMaxRecharge());
        orderInVo.setSaleActiveFlag(curOrderVo.getSaleFlag());
        if(Validator.contain(AppConstant.CARD_TYPE_TCT, orderInVo.getTicketType())){
            orderInVo.setLogicDate(curOrderVo.getTctEffBeginTime());
            orderInVo.setLogicDays(curOrderVo.getTctEffTime());  

        }else if(Validator.contain(AppConstant.CARD_TYPE_TOKEN, orderInVo.getTicketType())){
            orderInVo.setLogicDate(curOrderVo.getCardEffTime());
            orderInVo.setLogicDays("1"); 
        }else{

            orderInVo.setLogicDate(DateHelper.curDateToStr8yyyyMMdd());
            long day2 = DateHelper.str8yyyyMMddToDate(curOrderVo.getCardEffTime()).getTime()- DateHelper.str8yyyyMMddToDate(DateHelper.curDateToStr8yyyyMMdd()).getTime();
            orderInVo.setLogicDays(Long.toString(day2/AppConstant.DAY));   
        } 
                  
        orderInVo.setExitEntryMode(curOrderVo.getLimitMode());
        orderInVo.setEntryLineStation(curOrderVo.getLineCode()+curOrderVo.getStationCode());
        orderInVo.setExitLineStation(curOrderVo.getLimitExitLineCode()+curOrderVo.getLimitExitStationCode());
        orderInVo.setcEndExpire("00000000");
        orderInVo.setcStartExpire("00000000");  
        orderInVo.setSamID(getSamNo());
        
         //hwj add 20160117增加卡商代码
        orderInVo.setCardProducerCode(analyzeVo.getCardProducerCode());
        
        return orderInVo;     
    }
    
    /**
     * 重编码参数
     * 
     * @param analyzeVo
     * @return 
     */
    protected OrderInVo getAgainOrderInVo(AnalyzeVo analyzeVo) {

        OrderInVo orderInVo = new OrderInVo();
        orderInVo.setOrderNo(curOrderVo.getOrderNo());
        orderInVo.setApplicationNO(curOrderVo.getReqNo());
        orderInVo.setTicketType(curOrderVo.getCardTypeCode());
        orderInVo.setLogicalID(analyzeVo.getcLogicalID());
        orderInVo.setDeposite(curOrderVo.getDeposit());
        orderInVo.setValue(curOrderVo.getPrintMoney());
        orderInVo.setRechargeTopValue(curOrderVo.getMaxRecharge());
        orderInVo.setSaleActiveFlag(curOrderVo.getSaleFlag());
        orderInVo.setSenderCode(AppConstant.cityParamVo.getSenderCode());//"5320"
        orderInVo.setCityCode(AppConstant.cityParamVo.getCityCode());//"4100"
        orderInVo.setBusiCode(AppConstant.cityParamVo.getIndustryCode());//"0000"
        orderInVo.setTestFlag(curOrderVo.getTestFlag());
        orderInVo.setIssueDate(DateHelper.curDateToStr8yyyyMMdd());
        orderInVo.setCardVersion(AppConstant.cityParamVo.getCardVersion());//"0805"
        orderInVo.setLogicDate("00000000"); 
        orderInVo.setLogicDays("000"); 
        if(!Validator.contain(AppConstant.CARD_TYPE_TOKEN, orderInVo.getTicketType())){
            orderInVo.setcEndExpire(curOrderVo.getCardEffTime());
            orderInVo.setcStartExpire(DateHelper.curDateToStr8yyyyMMdd());  
            
        }else{
            orderInVo.setcEndExpire("00000000");
            orderInVo.setcStartExpire("00000000");                          
           
        }
        orderInVo.setExitEntryMode(curOrderVo.getLimitMode());
        orderInVo.setEntryLineStation(curOrderVo.getLineCode()+curOrderVo.getStationCode());
        orderInVo.setExitLineStation(curOrderVo.getLimitExitLineCode()+curOrderVo.getLimitExitStationCode());
        orderInVo.setAppVersion(AppConstant.cityParamVo.getAppVersion());//"01"
        if(Validator.contain(AppConstant.CARD_TYPE_TOKEN, orderInVo.getTicketType())){
        }else{   
        }
        orderInVo.setPhysicalID(analyzeVo.getcPhysicalID());
        orderInVo.setSamID(getSamNo());
        
         //hwj add 20160117增加卡商代码
        orderInVo.setCardProducerCode(analyzeVo.getCardProducerCode());
        
        return orderInVo;
    }
    
    /**
     * 注销参数
     * 
     * @param analyzeVo
     * @return 
     */
    protected OrderInVo getLogoutOrderInVo(AnalyzeVo analyzeVo) {
        
        OrderInVo orderInVo = new OrderInVo();
        orderInVo.setOrderNo(curOrderVo.getOrderNo());
        orderInVo.setApplicationNO(curOrderVo.getReqNo());
        orderInVo.setTicketType(curOrderVo.getCardTypeCode());
        orderInVo.setLogicalID(analyzeVo.getcLogicalID());
        orderInVo.setDeposite(curOrderVo.getDeposit());
        orderInVo.setValue(curOrderVo.getPrintMoney());
        orderInVo.setPhysicalID(analyzeVo.getcPhysicalID());
        orderInVo.setSamID(getSamNo());
        
         //hwj add 20160117增加卡商代码
        orderInVo.setCardProducerCode(analyzeVo.getCardProducerCode());
        
        return orderInVo;        
    }
    
    /**
     * 洗卡参数
     * 
     * @param analyzeVo
     * @return 
     */
    protected OrderInVo getClearOrderInVo(AnalyzeVo analyzeVo) {
        
        OrderInVo orderInVo = new OrderInVo();
        
        orderInVo.setLogicalID(analyzeVo.getcLogicalID());
        orderInVo.setPhysicalID(analyzeVo.getcPhysicalID());
        
        return orderInVo;
    }

        
    /**
     * 取得输出参数
     * 
     * @param order
     * @param analyzeVo
     * @return 
     */
    private OrderOutVo getOrderOutVo(CallResult callResult, OrderInVo order){
    
        OrderOutVo orderOutVo = new OrderOutVo();
        orderOutVo.setRetCode(callResult.getCode());
        orderOutVo.setRetMsg(callResult.getMsg());
        if(null != order){
            orderOutVo.setApplicationNO(order.getApplicationNO());
            orderOutVo.setPhysicalID(order.getPhysicalID());
            orderOutVo.setLogicalID(order.getLogicalID());
            orderOutVo.setSamID(order.getSamID());
            orderOutVo.setCardProducerCode(order.getCardProducerCode());//HWJ ADD 20160107增加卡商代码
        }
        return orderOutVo;
    }
    
    /**
     * 取得ES订单明细
     *
     * @return
     */
    private EsOrderDetailVo getEsOrderDetailVo(OrderOutVo orderOutVo){
    
        EsOrderDetailVo esOrderDetailVo = new EsOrderDetailVo();
        esOrderDetailVo.setWorkType(curOrderVo.getWorkType());
        esOrderDetailVo.setOrderNo(curOrderVo.getOrderNo());
        esOrderDetailVo.setCardTypeCode(curOrderVo.getCardTypeCode());
        esOrderDetailVo.setReqNo(StringUtil.fmtStrLeftZeroLen(orderOutVo.getApplicationNO(),10));
        esOrderDetailVo.setLogicNo(StringUtil.fmtStrRightEmptyLen(orderOutVo.getLogicalID(),20));
        if(!curOrderVo.getCardTypeCode().substring(0, 2).equals("01")){
              esOrderDetailVo.setPrintNo(StringUtil.fmtStrRightEmptyLen(orderOutVo.getLogicalID(),20));      //待定，HWJ 20160107由16位修改为20位
        }     
        esOrderDetailVo.setPhyNo(StringUtil.fmtStrRightEmptyLen(orderOutVo.getPhysicalID(),20));
        esOrderDetailVo.setDate(DateHelper.dateToStr14yyyyMMddHHmmss(new Date()));
        esOrderDetailVo.setPrintMoney(StringUtil.fmtStrLeftZeroLen(curOrderVo.getPrintMoney(),12));
        esOrderDetailVo.setCardEffTime(StringUtil.fmtStrLeftZeroLen(curOrderVo.getCardEffTime(),8));
        esOrderDetailVo.setSamNo(StringUtil.fmtStrLeftZeroLen(orderOutVo.getSamID(),16));
        esOrderDetailVo.setLineCode(StringUtil.fmtStrLeftZeroLen(curOrderVo.getLineCode(),2));
        esOrderDetailVo.setStationCode(StringUtil.fmtStrLeftZeroLen(curOrderVo.getStationCode(),2));
        esOrderDetailVo.setTctEffBeginTime(StringUtil.fmtStrLeftZeroLen(curOrderVo.getTctEffBeginTime(),8));
        esOrderDetailVo.setTctEffTime(StringUtil.fmtStrLeftZeroLen(curOrderVo.getTctEffTime(),5));
        esOrderDetailVo.setLimitExitLineCode(StringUtil.fmtStrLeftZeroLen(curOrderVo.getLimitExitLineCode(),2));
        esOrderDetailVo.setLimitExitStationCode(StringUtil.fmtStrLeftZeroLen(curOrderVo.getLimitExitStationCode(),2));
        esOrderDetailVo.setLimitMode(StringUtil.fmtStrLeftZeroLen(curOrderVo.getLimitMode(),3));
        esOrderDetailVo.setCardProducerCode(StringUtil.fmtStrLeftZeroLen(orderOutVo.getCardProducerCode(),4));//hwj 20160107 add增加卡商代码
        esOrderDetailVo.setPhoneNo(StringUtil.fmtStrLeftZeroLen("0",11));//hwj 20160107 add增加手机号码
        esOrderDetailVo.setCode(orderOutVo.getRetCode());
        esOrderDetailVo.setMsg(orderOutVo.getRetMsg());
        
        return esOrderDetailVo;
    }
}
