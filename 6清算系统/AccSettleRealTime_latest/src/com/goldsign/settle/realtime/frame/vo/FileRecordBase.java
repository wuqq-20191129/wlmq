/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.vo;

import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.util.TradeUtil;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileRecordBase {

    /**
     * @return the checkPurseChangeType
     */
    public int getCheckPurseChangeType() {
        return checkPurseChangeType;
    }

    /**
     * @param checkPurseChangeType the checkPurseChangeType to set
     */
    public void setCheckPurseChangeType(int checkPurseChangeType) {
        this.checkPurseChangeType = checkPurseChangeType;
    }
        /**
     *
     * *
     * 金额通用
     */
    private BigDecimal tradeFeeYuan;
    private int tradeFeeFen;
    private BigDecimal balanceFeeYuan;
    private int balanceFeeFen;
    private BigDecimal tradeFeeYuanA;
    private int tradeFeeFenA;
    /**
     * *
     * 唯一型校验字段
     */
    /**
     *
     * 非即时退款判断
     */
    private boolean nonReturn = false;
    //处理时使用的交易类型，如手机支付54 转为Mobile54以区分不同的54交易处理，如校验等
    //add by hejj 20160118
    private String trdTypeHandled;//交易类型
    /**
     * 
     * 区分交易区域，不同的区域对消费金额的限值不同
     */
    private String checkZoneForTrade="1"; //1:地铁 2:磁悬浮 3：公交
    private String checkCardBigType="1";//1:地铁卡（包括单程票01、储值票02、员工票、异形卡、手机票）
                                        //2：公交卡：

  
    
    private String waterNo;//流水号
    private int commonNum;//通用数量
    private int commonFee;//通用金额
    /**
     * 设备相关
     */
    private String lineId;//线路ID
    private String stationId;//车站ID
    private String devTypeId;//设备类型ID
    private String deviceId;//设备ID
    /**
     * 卡相关
     */
    private String cardMainId;//票卡主类型（5）
    private String cardSubId;//票卡子类型（5）
    private String cardLogicalId;//票卡逻辑卡号（6）
    private String cardPhysicalId;//票卡物理卡号（7）
    private String cardAppFlag;//应用标识（19）
    private int cardStatusId;//卡状态
    /**
     * SAM
     */
    private String samLogicalId;//SAM卡逻辑卡号（10）
    /**
     * 操作
     */
    private String operatorId;//操作员ID（16）
    private String shiftId;//BOM班次序号（17）
    private String trdType;//交易类型
    private String balanceWaterNo;//清算流水号
    private int balanceWaterNoSub;//清算子流水号
    private String fileName;//文件名
    private String checkFlag;//校验标志
    private int samTradeSeq; //本次交易SAM卡脱机交易流水号
    private HashMap subRecords;
    private Vector detail;//币面值、数量明细
    
    private String samTradeSeq_s; //本次交易SAM卡脱机交易流水号
    private String cardStatusId_s;//卡状态
    
    private String mobileNo;
    private String paidChannelType;
    private String paidChannelCode;
    /**
     * 字段校验使用
     */
    private int checkBalanceFee;
    private int checkDealFee;
    private int checkChargeFee;
    private String checkDatetime;
    private boolean checkCharge = false;
    private String checkUpdateArea;//12	更新区域
    private CheckData checkDataOther;//其他校验数据
    private String checkIdentityType;//7	证件类型
    private String checkIdentityId;//8	证件号码
    private String checkUniqueKey;//唯一性校验主键
    
    private String recordVer;//记录版本
    
    private int checkPurseChangeType=1;//1:消费 2：充值 3：购单程票
    
    
     private String issueQrcodePlatformFlag;//二维码发码平台标识20200706
    
    
      /**
     * @return the recordVer
     */
    public String getRecordVer() {
        return recordVer;
    }

    /**
     * @param recordVer the recordVer to set
     */
    public void setRecordVer(String recordVer) {
        this.recordVer = recordVer;
    }

    /**
     * @return the commonNum
     */
    public int getCommonNum() {
        return commonNum;
    }

    /**
     * @param commonNum the commonNum to set
     */
    public void setCommonNum(int commonNum) {
        this.commonNum = commonNum;
    }

    /**
     * @return the commonFee
     */
    public int getCommonFee() {
        return commonFee;
    }

    /**
     * @param commonFee the commonFee to set
     */
    public void setCommonFee(int commonFee) {
        this.commonFee = commonFee;
    }

    /**
     * @return the detail
     */
    public Vector getDetail() {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(Vector detail) {
        this.detail = detail;
    }

    /**
     * @return the cardStatusId_s
     */
    public String getCardStatusId_s() {
        return cardStatusId_s;
    }

    /**
     * @param cardStatusId_s the cardStatusId_s to set
     */
    public void setCardStatusId_s(String cardStatusId_s) {
        this.cardStatusId_s = cardStatusId_s;
    }

    /**
     * @return the balanceWaterNoSub
     */
    public int getBalanceWaterNoSub() {
        return balanceWaterNoSub;
    }

    /**
     * @param balanceWaterNoSub the balanceWaterNoSub to set
     */
    public void setBalanceWaterNoSub(int balanceWaterNoSub) {
        this.balanceWaterNoSub = balanceWaterNoSub;
    }

    /**
     * @return the samTradeSeq_s
     */
    public String getSamTradeSeq_s() {
        return samTradeSeq_s;
    }

    /**
     * @param samTradeSeq_s the samTradeSeq_s to set
     */
    public void setSamTradeSeq_s(String samTradeSeq_s) {
        this.samTradeSeq_s = samTradeSeq_s;
    }


    

    public void setCommonFeeByYuan(String tradeFee, String balanceFee) {

        if (tradeFee != null && tradeFee.length() != 0) {
            tradeFee = tradeFee.trim();
            this.tradeFeeFen = TradeUtil.convertYuanToFen(tradeFee);
            this.tradeFeeYuan = new BigDecimal(tradeFee);
        }
        if (balanceFee != null && balanceFee.length() != 0) {
            balanceFee = balanceFee.trim();
            this.balanceFeeFen = TradeUtil.convertYuanToFen(balanceFee);
            this.balanceFeeYuan = new BigDecimal(balanceFee);
        }

    }
    /*
     public void setCommonFeeByYuan(int fenTradeFee, int fenBalanceFee) {
     this.tradeFeeFen = fenTradeFee;
     this.tradeFeeYuan =TradeUtil.convertFenToYuan(fenTradeFee);
         
     this.balanceFeeFen = fenBalanceFee;
     this.balanceFeeYuan = TradeUtil.convertFenToYuan(fenBalanceFee);
        


     }
     */

    public void setCommonFeeByFen(int tradeFee, int balanceFee) {

        this.tradeFeeFen = tradeFee;
        this.tradeFeeYuan = TradeUtil.convertFenToYuan(tradeFee);


        this.balanceFeeFen = balanceFee;
        this.balanceFeeYuan = TradeUtil.convertFenToYuan(balanceFee);


    }

    public void setCommonFeeByFen(int tradeFee, int tradeFeeA, int balanceFee) {

        this.tradeFeeFen = tradeFee;
        this.tradeFeeYuan = TradeUtil.convertFenToYuan(tradeFee);

        this.tradeFeeFenA = tradeFeeA;
        this.tradeFeeYuanA = TradeUtil.convertFenToYuan(tradeFeeA);


        this.balanceFeeFen = balanceFee;
        this.balanceFeeYuan = TradeUtil.convertFenToYuan(balanceFee);


    }

    /**
     *
     * @return the lineId
     */
    public String getLineId() {
        return lineId;
    }

    /**
     * @param lineId the lineId to set
     */
    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    /**
     * @return the stationId
     */
    public String getStationId() {
        return stationId;
    }

    /**
     * @param stationId the stationId to set
     */
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    /**
     * @return the devTypeId
     */
    public String getDevTypeId() {
        return devTypeId;
    }

    /**
     * @param devTypeId the devTypeId to set
     */
    public void setDevTypeId(String devTypeId) {
        this.devTypeId = devTypeId;
    }

    /**
     * @return the deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId the deviceId to set
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return the balanceWaterNo
     */
    public String getBalanceWaterNo() {
        return balanceWaterNo;
    }

    /**
     * @param balanceWaterNo the balanceWaterNo to set
     */
    public void setBalanceWaterNo(String balanceWaterNo) {
        this.balanceWaterNo = balanceWaterNo;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the checkFlag
     */
    public String getCheckFlag() {
        return checkFlag;
    }

    /**
     * @param checkFlag the checkFlag to set
     */
    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }

    /**
     * @return the waterNo
     */
    public String getWaterNo() {
        return waterNo;
    }

    /**
     * @param waterNo the waterNo to set
     */
    public void setWaterNo(String waterNo) {
        this.waterNo = waterNo;
    }

    /**
     * @return the cardMainId
     */
    public String getCardMainId() {
        return cardMainId;
    }

    /**
     * @param cardMainId the cardMainId to set
     */
    public void setCardMainId(String cardMainId) {
        this.cardMainId = cardMainId;
    }

    /**
     * @return the cardSubId
     */
    public String getCardSubId() {
        return cardSubId;
    }

    /**
     * @param cardSubId the cardSubId to set
     */
    public void setCardSubId(String cardSubId) {
        this.cardSubId = cardSubId;
    }

    /**
     * @return the cardLogicalId
     */
    public String getCardLogicalId() {
        return cardLogicalId;
    }

    /**
     * @param cardLogicalId the cardLogicalId to set
     */
    public void setCardLogicalId(String cardLogicalId) {
        this.cardLogicalId = cardLogicalId;
    }

    /**
     * @return the cardPhysicalId
     */
    public String getCardPhysicalId() {
        return cardPhysicalId;
    }

    /**
     * @param cardPhysicalId the cardPhysicalId to set
     */
    public void setCardPhysicalId(String cardPhysicalId) {
        this.cardPhysicalId = cardPhysicalId;
    }

    /**
     * @return the cardAppFlag
     */
    public String getCardAppFlag() {
        return cardAppFlag;
    }

    /**
     * @param cardAppFlag the cardAppFlag to set
     */
    public void setCardAppFlag(String cardAppFlag) {
        this.cardAppFlag = cardAppFlag;
    }

    /**
     * @return the samLogicalId
     */
    public String getSamLogicalId() {
        return samLogicalId;
    }

    /**
     * @param samLogicalId the samLogicalId to set
     */
    public void setSamLogicalId(String samLogicalId) {
        this.samLogicalId = samLogicalId;
    }

    /**
     * @return the operatorId
     */
    public String getOperatorId() {
        return operatorId;
    }

    /**
     * @param operatorId the operatorId to set
     */
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * @return the shiftId
     */
    public String getShiftId() {
        return shiftId;
    }

    /**
     * @param shiftId the shiftId to set
     */
    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }

    /**
     * @return the trdType
     */
    public String getTrdType() {
        return trdType;
    }

    /**
     * @param trdType the trdType to set
     */
    public void setTrdType(String trdType) {
        this.trdType = trdType;
    }

    /**
     * @return the samTradeSeq
     */
    public int getSamTradeSeq() {
        return samTradeSeq;
    }

    /**
     * @param samTradeSeq the samTradeSeq to set
     */
    public void setSamTradeSeq(int samTradeSeq) {
        this.samTradeSeq = samTradeSeq;
    }

    /**
     * @return the cardStatusId
     */
    public int getCardStatusId() {
        return cardStatusId;
    }

    /**
     * @param cardStatusId the cardStatusId to set
     */
    public void setCardStatusId(int cardStatusId) {
        this.cardStatusId = cardStatusId;
    }

    /**
     * @return the subRecords
     */
    public HashMap getSubRecords() {
        return subRecords;
    }

    /**
     * @param subRecords the subRecords to set
     */
    public void setSubRecords(HashMap subRecords) {
        this.subRecords = subRecords;
    }

    /**
     * @return the checkBalanceFee
     */
    public int getCheckBalanceFee() {
        return checkBalanceFee;
    }

    /**
     * @param checkBalanceFee the checkBalanceFee to set
     */
    public void setCheckBalanceFee(int checkBalanceFee) {
        this.checkBalanceFee = checkBalanceFee;
    }

    /**
     * @return the checkDealFee
     */
    public int getCheckDealFee() {
        return checkDealFee;
    }

    /**
     * @param checkDealFee the checkDealFee to set
     */
    public void setCheckDealFee(int checkDealFee) {
        this.checkDealFee = checkDealFee;
    }

    /**
     * @return the checkChargeFee
     */
    public int getCheckChargeFee() {
        return checkChargeFee;
    }

    /**
     * @param checkChargeFee the checkChargeFee to set
     */
    public void setCheckChargeFee(int checkChargeFee) {
        this.checkChargeFee = checkChargeFee;
    }

    /**
     * @return the checkCharge
     */
    public boolean isCheckCharge1() {
        return checkCharge;
    }
    public boolean isCheckChargeByFlag(){
        if(this.checkPurseChangeType == FileRecordParserBase.CHECK_PURSE_CHANGE_TYPE_CHARGE)
            return true;
        return false;
    }
    public boolean isCheckConsumeByFlag(){
        if(this.checkPurseChangeType == FileRecordParserBase.CHECK_PURSE_CHANGE_TYPE_CONSUME)
            return true;
        return false;
    }
    public boolean isCheckBuyTkFlag(){
        if(this.checkPurseChangeType == FileRecordParserBase.CHECK_PURSE_CHANGE_TYPE_BUY_TK)
            return true;
        return false;
    }

    /**
     * @param checkCharge the checkCharge to set
     */
    public void setCheckCharge1(boolean checkCharge) {
        this.checkCharge = checkCharge;
    }

    /**
     * @return the checkDatetime
     */
    public String getCheckDatetime() {
        return checkDatetime;
    }

    /**
     * @param checkDatetime the checkDatetime to set
     */
    public void setCheckDatetime(String checkDatetime) {
        this.checkDatetime = checkDatetime;
    }

    /**
     * @return the tradeFeeYuan
     */
    public BigDecimal getTradeFeeYuan() {
        return tradeFeeYuan;
    }

    /**
     * @param tradeFeeYuan the tradeFeeYuan to set
     */
    public void setTradeFeeYuan(BigDecimal tradeFeeYuan) {
        this.tradeFeeYuan = tradeFeeYuan;
    }

    /**
     * @return the tradeFeeYuan
     */
    public BigDecimal getTradeFeeYuanA() {
        return tradeFeeYuanA;
    }

    /**
     * @param tradeFeeYuan the tradeFeeYuan to set
     */
    public void setTradeFeeYuanA(BigDecimal tradeFeeYuanA) {
        this.tradeFeeYuanA = tradeFeeYuanA;
    }

    /**
     * @return the tradeFeeFen
     */
    public int getTradeFeeFen() {
        return tradeFeeFen;
    }

    /**
     * @param tradeFeeFen the tradeFeeFen to set
     */
    public void setTradeFeeFen(int tradeFeeFen) {
        this.tradeFeeFen = tradeFeeFen;
    }

    /**
     * @return the tradeFeeFen
     */
    public int getTradeFeeFenA() {
        return tradeFeeFenA;
    }

    /**
     * @param tradeFeeFen the tradeFeeFen to set
     */
    public void setTradeFeeFenA(int tradeFeeFeA) {
        this.tradeFeeFenA = tradeFeeFeA;
    }

    /**
     * @return the balanceFeeYuan
     */
    public BigDecimal getBalanceFeeYuan() {
        return balanceFeeYuan;
    }

    /**
     * @param balanceFeeYuan the balanceFeeYuan to set
     */
    public void setBalanceFeeYuan(BigDecimal balanceFeeYuan) {
        this.balanceFeeYuan = balanceFeeYuan;
    }

    /**
     * @return the balanceFeeFen
     */
    public int getBalanceFeeFen() {
        return balanceFeeFen;
    }

    /**
     * @param balanceFeeFen the balanceFeeFen to set
     */
    public void setBalanceFeeFen(int balanceFeeFen) {
        this.balanceFeeFen = balanceFeeFen;
    }

    /**
     * @return the checkUpdateArea
     */
    public String getCheckUpdateArea() {
        return checkUpdateArea;
    }

    /**
     * @param checkUpdateArea the checkUpdateArea to set
     */
    public void setCheckUpdateArea(String checkUpdateArea) {
        this.checkUpdateArea = checkUpdateArea;
    }

    /**
     * @return the checkDataOther
     */
    public CheckData getCheckDataOther() {
        return checkDataOther;
    }

    /**
     * @param checkDataOther the checkDataOther to set
     */
    public void setCheckDataOther(CheckData checkDataOther) {
        this.checkDataOther = checkDataOther;
    }

    /**
     * @return the checkUniqueKey
     */
    public String getCheckUniqueKey() {
        return checkUniqueKey;
    }

    /**
     * @param checkUniqueKey the checkUniqueKey to set
     */
    public void setCheckUniqueKey(String checkUniqueKey) {
        this.checkUniqueKey = checkUniqueKey;
    }

    /**
     * @return the nonReturn
     */
    public boolean isNonReturn() {
        return nonReturn;
    }

    /**
     * @param nonReturn the nonReturn to set
     */
    public void setNonReturn(boolean nonReturn) {
        this.nonReturn = nonReturn;
    }

    /**
     * @return the trdTypeHandled
     */
    public String getTrdTypeHandled() {
        return trdTypeHandled;
    }

    /**
     * @param trdTypeHandled the trdTypeHandled to set
     */
    public void setTrdTypeHandled(String trdTypeHandled) {
        this.trdTypeHandled = trdTypeHandled;
    }

    /**
     * @return the checkIdentityType
     */
    public String getCheckIdentityType() {
        return checkIdentityType;
    }

    /**
     * @param checkIdentityType the checkIdentityType to set
     */
    public void setCheckIdentityType(String checkIdentityType) {
        this.checkIdentityType = checkIdentityType;
    }

    /**
     * @return the checkIdentityId
     */
    public String getCheckIdentityId() {
        return checkIdentityId;
    }

    /**
     * @param checkIdentityId the checkIdentityId to set
     */
    public void setCheckIdentityId(String checkIdentityId) {
        this.checkIdentityId = checkIdentityId;
    }

    /**
     * @return the zoneForTrade
     */
    public String getZoneForTrade() {
        return checkZoneForTrade;
    }

    /**
     * @param zoneForTrade the zoneForTrade to set
     */
    public void setZoneForTrade(String zoneForTrade) {
        this.checkZoneForTrade = zoneForTrade;
    }

    /**
     * @return the mobileNo
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * @param mobileNo the mobileNo to set
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     * @return the paidChannelType
     */
    public String getPaidChannelType() {
        return paidChannelType;
    }

    /**
     * @param paidChannelType the paidChannelType to set
     */
    public void setPaidChannelType(String paidChannelType) {
        this.paidChannelType = paidChannelType;
    }

    /**
     * @return the paidChannelCode
     */
    public String getPaidChannelCode() {
        return paidChannelCode;
    }

    /**
     * @param paidChannelCode the paidChannelCode to set
     */
    public void setPaidChannelCode(String paidChannelCode) {
        this.paidChannelCode = paidChannelCode;
    }

    /**
     * @return the issueQrcodePlatformFlag
     */
    public String getIssueQrcodePlatformFlag() {
        return issueQrcodePlatformFlag;
    }

    /**
     * @param issueQrcodePlatformFlag the issueQrcodePlatformFlag to set
     */
    public void setIssueQrcodePlatformFlag(String issueQrcodePlatformFlag) {
        this.issueQrcodePlatformFlag = issueQrcodePlatformFlag;
    }

    
}
