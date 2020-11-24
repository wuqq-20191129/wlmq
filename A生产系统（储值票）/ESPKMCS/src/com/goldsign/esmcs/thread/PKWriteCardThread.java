/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.thread;

import com.goldsign.csfrm.util.MessageShowUtil;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.esmcs.application.PKApplication;
import com.goldsign.esmcs.env.AppConstant;
import com.goldsign.esmcs.env.PKAppConstant;
import com.goldsign.esmcs.env.SynLockConstant;
import com.goldsign.esmcs.exception.CardNotFoundException;
import com.goldsign.esmcs.exception.FileException;
import com.goldsign.esmcs.exception.RwJniException;
import com.goldsign.esmcs.ui.dialog.PKMakeCardDialog;
import com.goldsign.esmcs.util.SerialNumber;
import com.goldsign.esmcs.vo.AnalyzeVo;
import com.goldsign.esmcs.vo.OrderInVo;
import com.goldsign.esmcs.vo.WriteCardParam;
import org.apache.log4j.Logger;

/**
 * 写卡线程类
 * 
 * @author lenovo
 */
public class PKWriteCardThread extends WriteCardThread{

    private static final Logger logger = Logger.getLogger(PKWriteCardThread.class.getName());
    
    private boolean isWritingCard = false;//是否正在写卡
    
    protected PKMakeCardThread makeCardThread;//制卡线程
    
    private String cardType = null;
    
    public PKWriteCardThread(PKMakeCardDialog makeCardDialog, PKMakeCardThread makeCardThread){
    
        super(makeCardDialog);
        this.makeCardThread = makeCardThread;
        this.rwDeviceService = ((PKApplication) PKAppConstant.application).getRwDeviceService();
        if(curOrderVo.getCardTypeCode()!=null&&!"".equals(curOrderVo.getCardTypeCode())){
            cardType = curOrderVo.getCardTypeCode().substring(0, 2);
        }
    }
    
    /**
     * 设置写卡标识
     * 
     * @param isWritingCard 
     */
    public void setWritingCard(boolean isWritingCard){
        
        this.isWritingCard = isWritingCard;
    }
    
    /**
     * 是否正在写卡
     * 
     * @return 
     */
    public boolean isWritingCard(){
        
        return this.isWritingCard;
    }
    
    /**
     * 运行，循环，制卡线程通知写卡，写卡后通知制卡线程
     * 
     */
    @Override
    public void run() {
        
        //暂停，停待制卡线程通知
        waitUntilMakeCardThreadNotice();
        //limj初始化流水号
        if("01".equals(cardType)){
            SerialNumber.resetGrobalSeqNo(curOrderVo.getFinishNum());
        }
        while(true){
            try {
                //写卡
                writeCards();
                
            } catch (Exception ex) {
                logger.error(ex);
                makeCardDialog.writeInfoMsg(ex.getMessage());
                //throw new WriteCardException(ex);
            }finally{
                //完成写卡，通知制卡线程
                noticeMakeCardThread();
                
            }
        }
    }
    
    /**
     * 写卡， 判断写卡工位2有卡时，才写卡
     * 
     * @throws RwJniException
     * @throws FileException
     * @throws InterruptedException 
     */
    protected void writeCards() 
            throws RwJniException, FileException, InterruptedException {
      
        //循环每个张卡，写卡工位才写卡
        for (int i = 0; i < PKAppConstant.ES_CARD_SITE_NUM; i++) {
            
            //是否处于写卡工位
            if (makeCardThread.isSiteWriteCard(i)) {
                
                //写卡
                CallParam callParam = getCallParam(curOrderVo); 
                logger.info("写卡...");
                CallResult callResult = new CallResult();                
                try{
                    callResult = writeCard(callParam);
//                    if(callResult.isSuccess()){
//                        MessageShowUtil.alertErrorMsgSyn("制卡成功！");
//                    }else{
//                        MessageShowUtil.alertErrorMsgSyn("制卡失败！");
//                    }
                }catch(Exception e){
                    logger.error("写卡异常，原因："+e.getMessage());
                  //  MessageShowUtil.alertErrorMsgSyn("制卡失败！");
                }
                //logger.info("写卡结束");
                
                if(((WriteCardParam)callParam).isMakedCard()){
                    Thread.sleep(800);
                }else{
                    //更新订单
                    try{
                        updateOrder(callResult.isSuccess());
                    }catch(Exception e){
                        logger.error("更新订单异常，原因："+e.getMessage());
                    }

                    OrderInVo orderInVo = null;
                    //写文件
                    try{
                        orderInVo = ((WriteCardParam)callParam).getOrderInVo();
                        writeFile(callResult, orderInVo);
                    }catch(Exception e){
                        logger.error("写文件异常，原因："+e.getMessage());
                    }
                    //logger.info("写文件结束");
                    
                    //增加写完卡（好或坏卡）
                    if(null != orderInVo){
                        curOrderVo.addCard(callResult.isSuccess(), orderInVo.getPhysicalID());
                    }
                }
                
                int boxNo = PKAppConstant.BOX_INVAL_DEFAULT_NO;
                try{
                    boxNo = makeCardThread.updateRecvBox(i, callResult.isSuccess());
                }catch(Exception e){
                    logger.error("更新回收箱异常，原因："+e.getMessage());
                }
                
                makeCardThread.recvCard(boxNo, i, ((WriteCardParam)callParam).isMakedCard());
            }
        }
    }
    
    /**
     * 读卡前业务判断
     *
     * @param callParam
     * @return
     */
    @Override
    protected CallResult readCardBeforeBusi(CallParam callParam) {

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
    @Override
    protected CallResult writeCardBeforeBusi(CallParam callParam) {

        CallResult callResult = null;
        WriteCardParam writeCardParam = (WriteCardParam)callParam;
        String workType = writeCardParam.getWorkType();
        OrderInVo orderInVo = writeCardParam.getOrderInVo();
        AnalyzeVo analyzeVo = (AnalyzeVo) writeCardParam.getParam();
        if(AppConstant.WORK_TYPE_INITI.equals(workType)){
            callResult = initEvaluateBeforeBusi(orderInVo, analyzeVo);
        }else if(AppConstant.WORK_TYPE_HUNCH.equals(workType)){
            callResult = evaluateBeforeBusi(orderInVo, analyzeVo);
        }else if(AppConstant.WORK_TYPE_AGAIN.equals(workType)){
            callResult = recodeBeforeBusi(orderInVo, analyzeVo);
        }else if(AppConstant.WORK_TYPE_LOGOUT.equals(workType)){
            callResult = destroyBeforeBusi(orderInVo, analyzeVo);
        }else if(AppConstant.WORK_TYPE_CLEAR.equals(workType)){
            callResult = clearBeforeBusi(orderInVo, analyzeVo);
        }else{
            callResult = new CallResult();
        }

        return callResult;
    }
    
    /**
     * 初始化业务判断
     * 
     * @param writeCardParam
     * @return 
     */
    private CallResult initEvaluateBeforeBusi(OrderInVo orderInVo, AnalyzeVo analyzeVo){
        
        CallResult callResult = new CallResult();

        if(StringUtil.isEmpty(orderInVo.getLogicalID())){
            callResult.setMsg("票卡逻辑卡号为空，物理卡号："+analyzeVo.getcPhysicalID());
            return callResult;
        }
        if(!PKAppConstant.CARD_ISSUE_NO.equals(analyzeVo.getbIssueStatus())){
            callResult.setMsg("票卡已发行，逻辑卡号："+analyzeVo.getcLogicalID());
            return callResult;
        }
        
        callResult.setResult(true);
        return callResult;
    }
    
    /**
     * 预赋值业务判断
     * 
     * @param writeCardParam
     * @return 
     */    
    private CallResult evaluateBeforeBusi(OrderInVo orderInVo, AnalyzeVo analyzeVo) {
        CallResult callResult = new CallResult();
        
        if (!PKAppConstant.CARD_ISSUE_YES.equals(analyzeVo.getbIssueStatus())) {
            callResult.setMsg("票卡未发行，物理卡号：" + analyzeVo.getcPhysicalID());
            return callResult;
        }
        if (!PKAppConstant.CARD_STATUS_INIT.equals(analyzeVo.getbStatus())&&!PKAppConstant.CARD_STATUS_EVALUATE.equals(analyzeVo.getbStatus())) {
            callResult.setMsg("票卡未初始化，逻辑卡号：" + analyzeVo.getcLogicalID());
            return callResult;
        }
        
        callResult.setResult(true);
        return callResult;
    }
  
    /**
     * 重编码业务判断
     * 
     * @param writeCardParam
     * @return 
     */
    private CallResult recodeBeforeBusi(OrderInVo orderInVo, AnalyzeVo analyzeVo) {
        
        CallResult callResult = new CallResult();
        if (!PKAppConstant.CARD_ISSUE_DESTROY.equals(analyzeVo.getbIssueStatus())) {
            callResult.setMsg("票卡未注销，逻辑卡号：" + analyzeVo.getcLogicalID());
            return callResult;
        }
        if(!PKAppConstant.CARD_STATUS_DESTROY.equals(analyzeVo.getbStatus())){
            callResult.setMsg("票卡未注销，逻辑卡号：" + analyzeVo.getcLogicalID());
            return callResult;
        }
        
        callResult.setResult(true);
        return callResult;
    }
  
    /**
     * 注销业务判断
     *
     * @param writeCardParam
     * @return
     */
    private CallResult destroyBeforeBusi(OrderInVo orderInVo, AnalyzeVo analyzeVo) {
        
        CallResult callResult = new CallResult();
        if (!PKAppConstant.CARD_ISSUE_YES.equals(analyzeVo.getbIssueStatus())) {
            callResult.setMsg("票卡未发行，物理卡号：" + analyzeVo.getcPhysicalID());
            return callResult;
        }
        if (PKAppConstant.CARD_ISSUE_DESTROY.equals(analyzeVo.getbIssueStatus())) {
            callResult.setMsg("票卡已注销，逻辑卡号：" + analyzeVo.getcLogicalID());
            return callResult;
        }
        
        callResult.setResult(true);
        return callResult;
    }
  
    /**
     * 洗卡业务判断
     *
     * @param writeCardParam
     * @return
     */
    private CallResult clearBeforeBusi(OrderInVo orderInVo, AnalyzeVo analyzeVo) {

        CallResult callResult = new CallResult();
        if (PKAppConstant.CARD_ISSUE_NO.equals(analyzeVo.getbIssueStatus())) {
            callResult.setMsg("票卡未发行，物理卡号：" + analyzeVo.getcPhysicalID());
            return callResult;
        }
        
        callResult.setResult(true);
        return callResult;
    }
    
    /**
     * 取逻辑卡号
     * 
     * @param analyzeVo
     * @return 
     */
    @Override
    protected String getCardLogicNo(AnalyzeVo analyzeVo) {
        
        String logicNo = null;
        if("01".equals(cardType)){    
            logicNo = curOrderVo.getSeqNum16(SerialNumber.getSeqNoPlus());
        }else{
            String phyNo = analyzeVo.getcPhysicalID();
            logger.info("物理卡号：" + phyNo);
            logicNo = AppConstant.phyLogicVos.get(phyNo).trim();
            /*if(null != logicNo){
                if(logicNo.length() > PKAppConstant.LEN_LOGICAL){
                    logicNo = logicNo.substring(logicNo.length() - PKAppConstant.LEN_LOGICAL);
                }
            }*/
            if(StringUtil.isEmpty(logicNo)){
                logger.error("物理卡号(" + phyNo+")没找到相应的逻辑卡号！");
                throw new CardNotFoundException("物理卡号(" + phyNo+")没找到相应的逻辑卡号！");
            }
        }
        logger.info("逻辑卡号：" + logicNo);
        return logicNo;
    }
    
    /**
     * 取得SAM卡号
     *
     * @return
     */
    @Override
    protected String getSamNo() {
        return PKAppConstant.STATUS_BAR_RW_PORT_STATE.samNo;
    }
        
    /**
     * 取得密钥
     *
     * @param analyzeVo
     * @return
     */
    @Override
    protected CallResult getKmsCardKey(CallParam callParam){

        CallResult callResult = null;
        try{
            WriteCardParam writeCardParam = (WriteCardParam)callParam;
            OrderInVo orderInVo = writeCardParam.getOrderInVo(); 
            if("01".equals(cardType)){
                String phyNo = orderInVo.getPhysicalID().substring(6, 14);
                String logicNo = orderInVo.getLogicalID().substring(8, 16);
                logger.info("物理卡号：" + phyNo +",逻辑卡号："+logicNo);
                for(int i=0;i<3;i++){
                    callResult = kmsService.getTokenKey(phyNo, logicNo);
                    if(callResult.isSuccess()){
                        break;
                    }
                    logger.warn((i+1)+"次，取密钥失败！");
                }
            }else{
                logger.info("取密钥，逻辑卡号："+orderInVo.getLogicalID());
                for(int i=0;i<3;i++){
                    callResult = kmsService.getCardKey(orderInVo.getLogicalID());
                    if(callResult.isSuccess()){
                        break;
                    }
                    logger.warn((i+1)+"次，取密钥失败");
                }
            }
        }catch(Exception e){
            logger.error(e);
            return new CallResult("取密钥失败，"+e.getMessage());
        }
        
        return callResult;
    }
    
    /**
     * 完成写卡，通知制卡线程
     * 
     */
    private void noticeMakeCardThread() {
        
        synchronized (SynLockConstant.SYN_WRITE_CARD_LOCK) { 
            logger.info("完成写卡，通知制卡线程.");
            this.setWritingCard(false);  
            try {
                SynLockConstant.SYN_WRITE_CARD_LOCK.notifyAll();
                SynLockConstant.SYN_WRITE_CARD_LOCK.wait();
            } catch (InterruptedException ex) {
                logger.error(ex);
            }
            
        }
    }
  
    /**
     * 制卡线暂停，停待制卡线程通知
     * 
     */
    private void waitUntilMakeCardThreadNotice(){
       
        synchronized(SynLockConstant.SYN_WRITE_CARD_LOCK){
            try {
                logger.info("写卡线暂停，停待制卡线程通知.");
                SynLockConstant.SYN_WRITE_CARD_LOCK.wait();
            } catch (InterruptedException ex) {
                logger.error(ex);
            }
        }
    }

}
