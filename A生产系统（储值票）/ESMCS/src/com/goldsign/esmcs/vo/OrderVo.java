/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.vo;

import com.goldsign.csfrm.util.CharUtil;
import com.goldsign.csfrm.util.DateHelper;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.esmcs.env.AppConstant;
import com.goldsign.esmcs.env.SynLockConstant;
import com.goldsign.esmcs.util.Converter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author lenovo
 */
public class OrderVo {
    
    private String status;
    
    private String workType;
    
    private String employeeId;
    
    private String orderNo;
    
    private String cardTypeCode;
    
    private String cardTypeDesc;
    
    private String cardEffTime;
    
    private String printMoney;
    
    private String deposit;
    
    private String beginReqNo;

    private String endReqNo;
    
    private String beginSeqNo;
    
    private String endSeqNo;
    
    private String date;
    
    private int orderNum;
    
    private String idCode;
    
    private String lineCode;
    
    private String stationCode;
    
    private String tctEffBeginTime;
    
    private String tctEffTime;
    
    private String limitExitLineCode;
    
    private String limitExitStationCode;
    
    private String limitMode;
    
    private int goodCardNum;
    
    private int badCardNum;
    
    private String remark;
   
    private Date beginDate;
    
    private String maxRecharge;//充值上限
    
    private String saleFlag;//销售标记
    
    private String testFlag;//测试标记
    
    //订单中的好卡
    private List<String> goodCards = new ArrayList<String>();
    
    //订单中的坏卡
    private List<String> badCards = new ArrayList<String>();
    
    public void clearCards(){
        this.goodCards.clear();
        this.badCards.clear();
    }
    
    public void addCard(boolean result, String phyNo){
        if(result){
            addGoodCard(phyNo);
        }else{
            addBadCard(phyNo);
        }
    }
    
    public void addGoodCard(String phyNo){
        if(null == phyNo){
            return;
        }
        phyNo = phyNo.trim();
        this.goodCards.add(phyNo);
    }
    
    public boolean containGoodCard(String phyNo){
        if(null != phyNo){
            phyNo = phyNo.trim();
        }
        return this.goodCards.contains(phyNo);
    }
    
    public void addBadCard(String phyNo){
        if(null == phyNo){
            return;
        }
        phyNo = phyNo.trim();
        this.badCards.add(phyNo);
    }
    
    public boolean containBadCard(String phyNo){
        if(null != phyNo){
            phyNo = phyNo.trim();
        }
        return this.badCards.contains(phyNo);
    }
    
    /**
     * @return the workType
     */
    public String getWorkType() {
        return workType;
    }

    /**
     * @param workType the workType to set
     */
    public void setWorkType(String workType) {
        this.workType = workType;
    }

    /**
     * @return the employeeId
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the orderNo
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo the orderNo to set
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * @return the cardTypeCode
     */
    public String getCardTypeCode() {
        return cardTypeCode;
    }

    /**
     * @param cardTypeCode the cardTypeCode to set
     */
    public void setCardTypeCode(String cardTypeCode) {
        this.cardTypeCode = cardTypeCode;
    }

    /**
     * @return the cardTypeDesc
     */
    public String getCardTypeDesc() {
        return cardTypeDesc;
    }

    /**
     * @param cardTypeDesc the cardTypeDesc to set
     */
    public void setCardTypeDesc(String cardTypeDesc) {
        this.cardTypeDesc = cardTypeDesc;
    }

    /**
     * @return the cardEffTime
     */
    public String getCardEffTime() {
        return cardEffTime;
    }

    /**
     * @param cardEffTime the cardEffTime to set
     */
    public void setCardEffTime(String cardEffTime) {
        this.cardEffTime = cardEffTime;
    }

    /**
     * @return the printMoney
     */
    public String getPrintMoney() {
        return printMoney;
    }

    /**
     * @param printMoney the printMoney to set
     */
    public void setPrintMoney(String printMoney) {
        this.printMoney = printMoney;
    }

    /**
     * @return the deposit
     */
    public String getDeposit() {
        return deposit;
    }
    
    public String getDepositYuan(){
        if(null == deposit || deposit.equals("")){
            return deposit;
        }
        return CharUtil.strToInt(deposit)/100+"";
    }

    /**
     * @param deposit the deposit to set
     */
    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }
    
    public String getReqNo(){
        /*
        String reqNoStr = "";
        if(StringUtil.isLong(beginReqNo)){
            long reqNo = StringUtil.getLong(beginReqNo)+getFinishNum();
            reqNoStr = StringUtil.addZeroBefore(reqNo+"", 8);
        }*/
        return getReqNo(getFinishNum());
    }
    
    public String getReqNo(int index){
        
        String reqNoStr = "";
        if(StringUtil.getLong(endReqNo)>0){
            if(StringUtil.isLong(beginReqNo)){
                long reqNo = StringUtil.getLong(beginReqNo)+index;
                reqNoStr = StringUtil.addZeroBefore(reqNo+"", 8);
            }
        }
        return reqNoStr;
    }

    /**
     * @return the beginReqNo
     */
    public String getBeginReqNo() {
        return beginReqNo;
    }

    /**
     * @param beginReqNo the beginReqNo to set
     */
    public void setBeginReqNo(String beginReqNo) {
        this.beginReqNo = beginReqNo;
    }

    /**
     * @return the endReqNo
     */
    public String getEndReqNo() {
        return endReqNo;
    }

    /**
     * @param endReqNo the endReqNo to set
     */
    public void setEndReqNo(String endReqNo) {
        this.endReqNo = endReqNo;
    }

    public String getSeqNo16(){
        long seqNo = StringUtil.getLong(beginSeqNo)+getFinishNum();
        return StringUtil.addZeroBefore(seqNo+"",16);
    }
    
    public String getSeqNo16(int seqNo) {

        long seqNoRet = StringUtil.getLong(beginSeqNo)+seqNo;
        return StringUtil.addZeroBefore(seqNoRet+"",16);
    }
    
    public String getSeqNo20(int seqNo) {

        long seqNoRet = StringUtil.getLong(beginSeqNo) + seqNo;
        return StringUtil.addZeroBefore(seqNoRet + "", 20);
    }
    
    //limj
    public String getSeqNum16(int seqNo) {

        String str = getSeqNum8(seqNo);
        if(testFlag.equals("1")){
            str = AppConstant.TK_CITY_BUSSINESS_CODE+AppConstant.TYPE_TEST_CODE1+str;
        }
        else{
            str = AppConstant.TK_CITY_BUSSINESS_CODE+AppConstant.TYPE_TEST_CODE2+str;   
        }
        return str;
    }
    
    public String getSeqNo8(int seqNo) {

        System.out.println("beginSeqNo:" + beginSeqNo);
        long seqNoRet = StringUtil.getLong(beginSeqNo) + seqNo;
        return StringUtil.addZeroBefore(seqNoRet + "", 8);
    }
    
    public String getSeqNum8(int seqNo) {

        System.out.println("beginSeqNo:" + beginSeqNo);
        long seqNoRet = StringUtil.getLong(beginSeqNo) + seqNo;
        String str = seqNoRet + "";
        int strlen = str.length();
        if(strlen - 8 > 0){
            str = str.substring(strlen - 8, strlen);
        }
        return StringUtil.addZeroBefore(seqNoRet + "", 8);
    }
    
    /**
     * @return the beginSeqNo
     */
    public String getBeginSeqNo() {
        return beginSeqNo;
    }

    /**
     * @param beginSeqNo the beginSeqNo to set
     */
    public void setBeginSeqNo(String beginSeqNo) {
        this.beginSeqNo = beginSeqNo;
    }

    /**
     * @return the endSeqNo
     */
    public String getEndSeqNo() {
        return endSeqNo;
    }

    /**
     * @param endSeqNo the endSeqNo to set
     */
    public void setEndSeqNo(String endSeqNo) {
        this.endSeqNo = endSeqNo;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the orderNum
     */
    public int getOrderNum() {
        return orderNum;
    }

    /**
     * @param orderNum the orderNum to set
     */
    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * @return the idCode
     */
    public String getIdCode() {
        return idCode;
    }

    /**
     * @param idCode the idCode to set
     */
    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    /**
     * @return the lineCode
     */
    public String getLineCode() {
        return lineCode;
    }

    /**
     * @param lineCode the lineCode to set
     */
    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    /**
     * @return the stationCode
     */
    public String getStationCode() {
        return stationCode;
    }

    /**
     * @param stationCode the stationCode to set
     */
    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    /**
     * @return the tctEffBeginTime
     */
    public String getTctEffBeginTime() {
        return tctEffBeginTime;
    }
    
    public String getTctEffBeginTime2() {

        Date cardEffTimeTmp = DateHelper.str8yyyyMMddToDate(cardEffTime);
        int daysTmp = StringUtil.getInt(tctEffTime);
        Date beginTimeTmp = DateHelper.addDaysToDate(cardEffTimeTmp, -1*daysTmp);

        return DateHelper.dateToStr8yyyyMMdd(beginTimeTmp);
    }

    /**
     * @param tctEffBeginTime the tctEffBeginTime to set
     */
    public void setTctEffBeginTime(String tctEffBeginTime) {
        this.tctEffBeginTime = tctEffBeginTime;
    }

    /**
     * @return the tctEffTime
     */
    public String getTctEffTime() {
        return tctEffTime;
    }

    /**
     * @param tctEffTime the tctEffTime to set
     */
    public void setTctEffTime(String tctEffTime) {
        this.tctEffTime = tctEffTime;
    }
    
    public String getTctEffEndTime(){
    
        Date beginTimeTmp = DateHelper.str8yyyyMMddToDate(tctEffBeginTime);
        int daysTmp = StringUtil.getInt(tctEffTime);
        Date endTimeTmp = DateHelper.addDaysToDate(beginTimeTmp, daysTmp);
        
        return DateHelper.dateToStr8yyyyMMdd(endTimeTmp);
    }

    /**
     * @return the limitExitLineCode
     */
    public String getLimitExitLineCode() {
        return limitExitLineCode;
    }

    /**
     * @param limitExitLineCode the limitExitLineCode to set
     */
    public void setLimitExitLineCode(String limitExitLineCode) {
        this.limitExitLineCode = limitExitLineCode;
    }

    /**
     * @return the limitExitStationCode
     */
    public String getLimitExitStationCode() {
        return limitExitStationCode;
    }

    /**
     * @param limitExitStationCode the limitExitStationCode to set
     */
    public void setLimitExitStationCode(String limitExitStationCode) {
        this.limitExitStationCode = limitExitStationCode;
    }

    /**
     * @return the limitMode
     */
    public String getLimitMode() {
        return limitMode;
    }

    /**
     * @param limitMode the limitMode to set
     */
    public void setLimitMode(String limitMode) {
        this.limitMode = limitMode;
    }

    /**
     * @return the goodCardNum
     */
    public int getGoodCardNum() {
        return goodCardNum;
    }

    /**
     * @param goodCardNum the goodCardNum to set
     */
    public void setGoodCardNum(int goodCardNum) {
        this.goodCardNum = goodCardNum;
    }

    /**
     * @return the badCardNum
     */
    public int getBadCardNum() {
        return badCardNum;
    }

    /**
     * @param badCardNum the badCardNum to set
     */
    public void setBadCardNum(int badCardNum) {
        this.badCardNum = badCardNum;
    }

    public int getUnFinishNum(){
        return getOrderNum()-getGoodCardNum()-getBadCardNum();
    }
    
    public int getFinishNum(){
        return getGoodCardNum()+getBadCardNum();
    }
    
    public int getFinishPercent(){
        return (getFinishNum()*100)/orderNum;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * @return the statusDesc
     */
    public String getStatusDesc() {

        return Converter.getEsOrderStatusDes(status);
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public boolean isFinish(){
        synchronized(SynLockConstant.SYN_FINISH_LOCK){
            return getUnFinishNum() <= 0; 
        }
    }
    
    public boolean isStatusFinishOrEnd(){
        return AppConstant.ES_ORDER_STATUS_END.equals(status)
                || AppConstant.ES_ORDER_STATUS_FINISH.equals(status);
    }

    /**
     * @return the beginDate
     */
    public Date getBeginDate() {
        return beginDate;
    }

    /**
     * @param beginDate the beginDate to set
     */
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }
    
    private long runBeginTime=0;//运行开始时间
    private long runEndTime=0;//运行结束时间
    public void setBeginRunTime(){
        runBeginTime = System.currentTimeMillis();
    }
    
    public void setEndRunTime(){
        runEndTime = System.currentTimeMillis();
    }
    
    public float getRunSpeed(){
        float inv = runEndTime-runBeginTime;
        float speed = inv/1000;
        if(speed < 0){
            return 0;
        }
        return speed;
    }

    public String getMaxRecharge() {
        return maxRecharge;
    }

    public void setMaxRecharge(String maxRecharge) {
        this.maxRecharge = maxRecharge;
    }
    
    public String getMaxRechargeYuan(){
        if(null == maxRecharge || maxRecharge.equals("")){
            return maxRecharge;
        }
        return CharUtil.strToInt(maxRecharge)/100+"";
    }

    public String getSaleFlag() {
        return saleFlag;
    }

    public void setSaleFlag(String saleFlag) {
        this.saleFlag = saleFlag;
    }

    public String getTestFlag() {
        return testFlag;
    }

    public void setTestFlag(String testFlag) {
        this.testFlag = testFlag;
    }

    @Override
    public String toString() {
        return "OrderVo{" + "status=" + status + ", workType=" + workType + ", employeeId=" + employeeId + ", orderNo=" + orderNo + ", cardTypeCode=" + cardTypeCode + ", cardTypeDesc=" + cardTypeDesc + ", cardEffTime=" + cardEffTime + ", printMoney=" + printMoney + ", deposit=" + deposit + ", beginReqNo=" + beginReqNo + ", endReqNo=" + endReqNo + ", beginSeqNo=" + beginSeqNo + ", endSeqNo=" + endSeqNo + ", date=" + date + ", orderNum=" + orderNum + ", idCode=" + idCode + ", lineCode=" + lineCode + ", stationCode=" + stationCode + ", tctEffBeginTime=" + tctEffBeginTime + ", tctEffTime=" + tctEffTime + ", limitExitLineCode=" + limitExitLineCode + ", limitExitStationCode=" + limitExitStationCode + ", limitMode=" + limitMode + ", goodCardNum=" + goodCardNum + ", badCardNum=" + badCardNum + ", remark=" + remark + ", beginDate=" + beginDate + ", maxRecharge=" + maxRecharge + ", saleFlag=" + saleFlag + ", testFlag=" + testFlag + ", runBeginTime=" + runBeginTime + ", runEndTime=" + runEndTime + '}';
    }
}
