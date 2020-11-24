/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.thread;

import com.goldsign.csfrm.util.DateHelper;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.esmcs.env.AppConstant;
import com.goldsign.esmcs.env.PKAppConstant;
import com.goldsign.esmcs.exception.FileException;
import com.goldsign.esmcs.exception.RwJniException;
import com.goldsign.esmcs.ui.dialog.PKSortCardDialog;
import com.goldsign.esmcs.vo.AnalyzeVo;
import com.goldsign.esmcs.vo.SortCardCondVo;
import org.apache.log4j.Logger;

/**
 * 写卡线程类
 * 
 * @author lenovo
 */
public class PKReadCardThread extends PKWriteCardThread{

    private static final Logger logger = Logger.getLogger(PKReadCardThread.class.getName());
    
    protected PKSortCardDialog sortCardDialog = null;//分拣对话框
    
    public PKReadCardThread(PKSortCardDialog sortCardDialog, PKSortCardThread sortCardThread){
    
        super(sortCardDialog, sortCardThread);
        this.sortCardDialog = sortCardDialog;
    }
    
    /**
     * 写卡， 判断写卡工位2有卡时，才写卡
     * 
     * @throws RwJniException
     * @throws FileException
     * @throws InterruptedException 
     */
    @Override
    protected void writeCards() 
            throws RwJniException, FileException, InterruptedException {
      
        //循环每个张卡，写卡工位才写卡
        for (int i = 0; i < PKAppConstant.ES_CARD_SITE_NUM; i++) {
            
            sortCardDialog.setUIWriteCardProgress(0);
            //是否处于写卡工位
            if (makeCardThread.isSiteWriteCard(i)) {
                
                sortCardDialog.setUIWriteCardProgress(30);
                //写卡
                CallResult callResult = new CallResult();
                try{
                    for(int j=0;j<5;j++){
                        callResult = rwDeviceService.readCard(null); 
                        if(callResult.isSuccess()){
                            logger.info("读卡成功！");
                            break;
                        }
                        logger.warn("读卡"+(j+1)+"次失败！");
                        Thread.sleep(500);//写卡前，停顿下下
                    }
                }catch(Exception e){
                    logger.error("卡分析异常，原因："+e.getMessage());
                }
                //查询过滤
                if(callResult.isSuccess()){
                    sortCardDialog.setUIWriteCardProgress(50);
                }else{
                    sortCardDialog.setUIWriteCardProgressErr(50);
                }
                
                //分拣条件
                try{
                    sortConditions(callResult);
                }catch(Exception e){
                    logger.error("设置分拣条件异常,原因："+e.getMessage());
                }
                
                if(callResult.isSuccess()){
                    sortCardDialog.setUIWriteCardProgress(100);
                }else{
                    sortCardDialog.setUIWriteCardProgressErr(100);
                }
                
                //设置分拣信息
                try{
                    sortCardDialog.setSortCardDetail(callResult.isSuccess(), 
                            (AnalyzeVo)callResult.getObj());
                }catch(Exception e){
                    logger.error("设置分拣信息异常，原因："+e.getMessage());
                }
                
                //更新订单
                try{
                    updateOrder(callResult.isSuccess());
                }catch(Exception e){
                    logger.error("更新订单异常，原因："+e.getMessage());
                }
                
                int boxNo = PKAppConstant.BOX_INVAL_DEFAULT_NO;
                try{
                    boxNo = makeCardThread.updateRecvBox(i, callResult.isSuccess());
                }catch(Exception e){
                    logger.error("更新回收箱异常，原因："+e.getMessage());
                }
                makeCardThread.recvCard(boxNo, i, false);
                
                Thread.sleep(500);
            }
        }
    }
    
    /**
     * 查询过滤
     * 
     * @param callResult 
     */
    private void sortConditions(CallResult callResult){
    
        boolean result = true;
        
        AnalyzeVo analyzeVo = (AnalyzeVo)callResult.getObj();
        SortCardCondVo sortCardCondVo = sortCardDialog.getSortConditions();
        //分析成功
        if(callResult.isSuccess()){
            //坏卡
            if(result
                    && sortCardCondVo.getIsBad().equals(AppConstant.CARD_PHY_STATUS_BAD)
                    ){
                result = false;
            }
            //发行状态
            if(result 
                    && !sortCardCondVo.getIssueStatus().isEmpty() 
                    && !analyzeVo.getbIssueStatus().equals(sortCardCondVo.getIssueStatus())){
                result = false;
            }
            //卡片状态
            if(result 
                    && !sortCardCondVo.getStatus().isEmpty() 
                    && !analyzeVo.getbStatus().equals(sortCardCondVo.getStatus())){
                result = false;
            }
            //票卡类型
            if(result 
                    && !sortCardCondVo.getTicketType().isEmpty() 
                    && !analyzeVo.getcTicketType().equals(sortCardCondVo.getTicketType())){
                result = false;
            }
            //发行时间
            if(result 
                    && !sortCardCondVo.getIssueBeginDate().isEmpty()){
                result = DateHelper.str10yyyy_MM_ddToDate(sortCardCondVo.getIssueBeginDate()).compareTo(
                            DateHelper.str14yyyyMMddHHmmssToDate(analyzeVo.getcIssueDate())
                            )<=0;
            }
            if(result 
                    && !sortCardCondVo.getIssueEndDate().isEmpty()){
                result = DateHelper.str10yyyy_MM_ddToDate(sortCardCondVo.getIssueEndDate()).compareTo(
                            DateHelper.str14yyyyMMddHHmmssToDate(analyzeVo.getcIssueDate())
                            )>=0;
            }
            //交易次数
            if(result 
                    && !sortCardCondVo.getLifeBeginCycle().isEmpty()){
                result = StringUtil.getInt(sortCardCondVo.getLifeBeginCycle())<=analyzeVo.getTradeCount();
            }
            if(result 
                    && !sortCardCondVo.getLifeEndCycle().isEmpty()){
                result = StringUtil.getInt(sortCardCondVo.getLifeEndCycle())>=analyzeVo.getTradeCount();
            }
            //卡序号段
            if (result
                    && !sortCardCondVo.getCardBeginSeqNo().isEmpty()) {
                result = StringUtil.fmtStrLeftZeroLen(sortCardCondVo.getCardBeginSeqNo(),8).compareTo(
                            StringUtil.fmtStrLeftZeroLen(analyzeVo.getcLogicalID().trim(), 8)) <= 0;
            }
            if (result
                    && !sortCardCondVo.getCardEndSeqNo().isEmpty()) {
                result = StringUtil.fmtStrLeftZeroLen(sortCardCondVo.getCardEndSeqNo(),8).compareTo(
                            StringUtil.fmtStrLeftZeroLen(analyzeVo.getcLogicalID().trim(), 8)) >= 0;
            }
        //分析失败
        }else{
            //坏卡
            if(result
                    && sortCardCondVo.getIsBad().equals(AppConstant.CARD_PHY_STATUS_GOOD)
                    ){
                result = false;
            }
            //发行状态
            if(result 
                    && !sortCardCondVo.getIssueStatus().isEmpty()
                    && analyzeVo != null && !analyzeVo.getbIssueStatus().equals(sortCardCondVo.getIssueStatus())){
                result = false;
            }
            //卡片状态
            if(result 
                    && !sortCardCondVo.getStatus().isEmpty() 
                    && analyzeVo != null && !analyzeVo.getbStatus().equals(sortCardCondVo.getStatus())){
                result = false;
            }
            //票卡类型
            if(result 
                    && !sortCardCondVo.getTicketType().isEmpty() 
                    && analyzeVo != null && !analyzeVo.getcTicketType().equals(sortCardCondVo.getTicketType())){
                result = false;
            }
            //发行时间
            if(result 
                    && !sortCardCondVo.getIssueBeginDate().isEmpty() && analyzeVo != null){
                result = DateHelper.str10yyyy_MM_ddToDate(sortCardCondVo.getIssueBeginDate()).compareTo(
                                DateHelper.str14yyyyMMddHHmmssToDate(analyzeVo.getcIssueDate()))<=0;
            }
            if(result 
                    && !sortCardCondVo.getIssueEndDate().isEmpty() && analyzeVo != null){
                result = DateHelper.str10yyyy_MM_ddToDate(sortCardCondVo.getIssueEndDate()).compareTo(
                                DateHelper.str14yyyyMMddHHmmssToDate(analyzeVo.getcIssueDate()))>=0;
            }
            //交易次数
            if(result 
                    && !sortCardCondVo.getLifeBeginCycle().isEmpty() && analyzeVo != null){
                result = StringUtil.getInt(sortCardCondVo.getLifeBeginCycle())<=analyzeVo.getTradeCount();
            }
            if(result 
                    && !sortCardCondVo.getLifeEndCycle().isEmpty() && analyzeVo != null){
                result = StringUtil.getInt(sortCardCondVo.getLifeEndCycle())>=analyzeVo.getTradeCount();
            }
            //卡序号段
            if (result
                    && !sortCardCondVo.getCardBeginSeqNo().isEmpty() && analyzeVo != null) {
                result = StringUtil.fmtStrLeftZeroLen(sortCardCondVo.getCardBeginSeqNo(),8).compareTo(
                                    StringUtil.fmtStrLeftZeroLen(analyzeVo.getcLogicalID().trim(), 8)) <= 0;
            }
            if (result
                    && !sortCardCondVo.getCardEndSeqNo().isEmpty() && analyzeVo != null) {
                    result = StringUtil.fmtStrLeftZeroLen(sortCardCondVo.getCardEndSeqNo(),8).compareTo(
                                StringUtil.fmtStrLeftZeroLen(analyzeVo.getcLogicalID().trim(), 8)) >= 0;
            }
        }
        
        callResult.setResult(result);
    }

}
