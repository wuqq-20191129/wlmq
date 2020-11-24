/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.parser;

import com.goldsign.settle.realtime.app.parser.FileRecordParser54;
import com.goldsign.settle.realtime.app.vo.FileRecord00DetailBase;
import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord01;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.constant.FrameCheckConstant;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameFileHandledConstant;
import com.goldsign.settle.realtime.frame.dao.TradeBaseDao;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.handler.HandlerBase;
import com.goldsign.settle.realtime.frame.handler.HandlerTrxBase;
import com.goldsign.settle.realtime.frame.util.DateHelper;
import com.goldsign.settle.realtime.frame.vo.CheckControlVo;
import com.goldsign.settle.realtime.frame.vo.CheckData;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordTacBase;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public abstract class FileRecordParserBase extends HandlerBase {

    public static String ADD_CHAR_ZERO = "0";
    protected static String RETURN_TYPE_NON_INSTANT = "1";//非即时退款
    protected static String RETURN_TYPE_INSTANT = "0";//即时退款

    protected static String ZONE_TRADE_MTR = "1";//交易区域，地铁
    protected static String ZONE_TRADE_MAG = "2";//交易区域，磁浮
    protected static String ZONE_TRADE_OCT = "3";//交易区域，公交

    public static int CHECK_PURSE_CHANGE_TYPE_CONSUME = 1;//消费
    public static int CHECK_PURSE_CHANGE_TYPE_CHARGE = 2;//充值
    public static int CHECK_PURSE_CHANGE_TYPE_BUY_TK = 3;//购单程票

    /*
    版本号
     */
    public static String VERSION_NO_11 = "11";
    public static String VERSION_NO_12 = "12";
    public static String VERSION_NO_13 = "13";
    public static String VERSION_NO_14 = "14";

    private static Logger logger = Logger.getLogger(FileRecordParserBase.class.getName());

    public abstract Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException;

    //public abstract boolean checkData(FileRecordBase frb);
    protected boolean isVersionOver11(String versionNo) {
        if (versionNo == null || versionNo.length() == 0) {
            return false;
        }
        if (versionNo.compareTo(VERSION_NO_11) >= 0) {
            return true;
        }
        return false;

    }

    protected boolean isVersionOver12(String versionNo) {
        if (versionNo == null || versionNo.length() == 0) {
            return false;
        }
        if (versionNo.compareTo(VERSION_NO_12) >= 0) {
            return true;
        }
        return false;

    }

    protected boolean isVersionOver13(String versionNo) {
        if (versionNo == null || versionNo.length() == 0) {
            return false;
        }
        if (versionNo.compareTo(VERSION_NO_13) >= 0) {
            return true;
        }
        return false;

    }
      protected boolean isVersionOver14(String versionNo) {
        if (versionNo == null || versionNo.length() == 0) {
            return false;
        }
        if (versionNo.compareTo(VERSION_NO_14) >= 0) {
            return true;
        }
        return false;

    }

    public String removeSpecialSign(String str) {
        String specialSign;
        if (str == null || str.length() == 0) {
            return "";
        }

        for (int i = 0; i < HandlerBase.SPECIAL_SIGNS.length; i++) {
            specialSign = HandlerBase.SPECIAL_SIGNS[i];
            if (str.indexOf(specialSign) != -1) {
                str = str.replaceAll(specialSign, " ");
            }
        }
        return str;

    }

    protected void addCommonInfo(FileRecordBase r, FileRecordAddVo lineAdd) {
        //获取附加信息
        r.setBalanceWaterNo(lineAdd.getBalanceWaterNo());
        r.setBalanceWaterNoSub(lineAdd.getBalanceWaterNoSub());
        r.setFileName(lineAdd.getFileName());
        r.setCheckFlag(FrameFileHandledConstant.RECORD_UNCHECKED);
    }

    protected void addCommonInfo_1(FileRecordBase r, FileRecordAddVo lineAdd) {
        //获取附加信息
        r.setBalanceWaterNo(lineAdd.getBalanceWaterNo());
        r.setFileName(lineAdd.getFileName());
        r.setCheckFlag(FrameFileHandledConstant.RECORD_UNCHECKED);
        r.setTrdType(lineAdd.getTrdType());
    }

    protected void addSubRecords(HashMap subRecords, String tradeType, String tradeTypeSufix, Vector subRecordUnit) {
        String tradeTypeAll = tradeType + tradeTypeSufix;
        Vector v;
        if (subRecords.containsKey(tradeTypeAll)) {
            v = (Vector) subRecords.get(tradeTypeAll);
            v.addAll(subRecordUnit);
            return;
        }
        subRecords.put(tradeTypeAll, subRecordUnit);
    }

    protected int getIntFromStr(String str) {
        str = str.trim();
        return Integer.parseInt(str);

    }

    protected BigDecimal getBigDecimalFromStr(String str) {
        str = str.trim();
        BigDecimal bd = new BigDecimal(str);
        return bd;

    }

    protected boolean checkForDeviceSam(String lineId, String stationId, String devTypeId, String deviceId, String samLogicalId) {
        String key = lineId + stationId + devTypeId + deviceId + samLogicalId;
        if (FrameCheckConstant.DEVICE_SAM_MAPPING.contains(key)) {
            return true;
        }
        return false;

    }

    protected boolean checkForCardType(String cardMainId, String cardSubId) {
        String key = cardMainId + cardSubId;
        if (FrameCheckConstant.CARD_SUB_TYPES.contains(key)) {
            return true;
        }
        return false;

    }

    protected boolean checkForTime(String time, String balanceDay) {
        try {
            if (time == null || time.length() < 8 || balanceDay == null || balanceDay.length() < 8) {
                return false;
            }
            time = time.substring(0, 8);
            balanceDay = balanceDay.substring(0, 8);

            long dif = DateHelper.getDifferInDaysForYYYYMMDD(time, balanceDay);
            if (dif <= FrameCheckConstant.CHK_TIME_MAX_BEFOREDAYS) {
                return true;
            }
        } catch (ParseException ex) {
            return false;
        }
        return false;

    }

    protected boolean checkForTimeForOnline(String time, String onlineDate) {
        try {
            if (time == null || time.length() < 8 || onlineDate == null || onlineDate.length() < 8) {
                return false;
            }
            time = time.substring(0, 8);
            onlineDate = onlineDate.substring(0, 8);
            //diff=onlineDate-time
            long dif = DateHelper.getDifferInDaysForYYYYMMDD(time, onlineDate);
            if (dif <= 0) {
                return true;
            }
        } catch (ParseException ex) {
            return false;
        }
        return false;

    }

    protected boolean isInDefine(String value, String[] values) {
        for (String tmp : values) {
            if (value.equals(tmp)) {
                return true;
            }
        }
        return false;
    }

    protected boolean checkForOther(CheckData cd) {
        try {
            if (cd == null) {
                return false;//
            }
            if (cd.getUpdateArea() == null) {
                return false;
            }
            String updateArea = cd.getUpdateArea();
            boolean result = this.isInDefine(updateArea, FrameCheckConstant.CHK_UPDATE_AREA);
            return result;

        } catch (Exception ex) {
            return false;
        }

    }

    protected boolean checkForFeeUpper(int fee, int limit) {
        if (fee <= limit) {
            return true;
        }
        return false;

    }

    protected boolean isNeedCheck(String tradeType, String checkId) {
        //否定的优先级大于肯定，如所有卡类型校验，除记名卡申请不校验卡类，可配置45-99-1加上45-52-0，这样，只要判断出0,就不校验
        for (CheckControlVo vo : FrameCheckConstant.CHECK_CONTROLS) {
            if (vo.getChkId().equals(checkId) && vo.getValidFlag().equals(FrameCheckConstant.CHECK_FLAG_NO)
                    && (vo.getAppTradeType().equals(FrameCheckConstant.CHECK_TRADE_ALL) || vo.getAppTradeType().equals(tradeType))) {
                return false;

            }

        }
        for (CheckControlVo vo : FrameCheckConstant.CHECK_CONTROLS) {
            if (vo.getChkId().equals(checkId) && vo.getValidFlag().equals(FrameCheckConstant.CHECK_FLAG_YES)
                    && (vo.getAppTradeType().equals(FrameCheckConstant.CHECK_TRADE_ALL) || vo.getAppTradeType().equals(tradeType))) {
                return true;
            }
        }
        return false;

    }

    protected boolean isCharge(String payModeId) {
        if (payModeId == null || payModeId.length() == 0) {
            return false;
        }
        if (payModeId.equals(FrameCodeConstant.TRX_PAY_MODE_CHARGE)) {
            return true;
        }
        return false;
    }

    protected boolean isBuyTk(String payModeId) {
        if (payModeId == null || payModeId.length() == 0) {
            return false;
        }
        if (payModeId.equals(FrameCodeConstant.TRX_PAY_MODE_BUY_SJT)) {
            return true;
        }
        return false;
    }

    protected boolean isChargeConsume(String payModeId) {
        if (payModeId == null || payModeId.length() == 0) {
            return false;
        }
        if (payModeId.equals(FrameCodeConstant.TRX_PAY_MODE_CHARGE_CONSUME)) {
            return true;
        }
        return false;
    }

    /*
     protected boolean checkDataForUnique(FileRecordBase r, HashMap<String, Vector> hm) {
     boolean chkResult = false;
     TradeBaseDao dao;
     String className = TradeBaseDao.CLASS_PREX + r.getTrdType() + "Dao";
     String tradeType;
     //判断是否需要校验
     //校验设备
     try {
     dao = (TradeBaseDao) Class.forName(className).newInstance();
     tradeType = r.getTrdType();
     chkResult = this.isUnique(tradeType, r, hm);
     if (!chkResult) {
     dao.insertError(r, FrameFileHandledConstant.RECORD_ERR_REPEAT[0]);
     logger.error("交易类型" + r.getTrdType() + " 记录重复：" + " 主健值：" + r.getCheckUniqueKey() + " 重复。");
     return chkResult;
     }


     } catch (Exception e) {
     e.printStackTrace();
     return false;
     }

     return true;

     }
     */
    protected boolean checkData(FileRecordBase r) {
        boolean chkResult = false;
        TradeBaseDao dao;
        String className = TradeBaseDao.CLASS_PREX + r.getTrdType() + "Dao";
        String tradeType;

        int maxBalanceFee = FrameCheckConstant.CHK_FEE_MAX_BALANCE;
        int maxChargeFee = FrameCheckConstant.CHK_FEE_MAX_CHARGE;
        int maxDealFee = FrameCheckConstant.CHK_FEE_MAX_DEAL;
        String formalOnline = FrameCheckConstant.CHK_TIME_FORMAL_ONLINE;

        //判断是否需要校验
        //校验设备
        try {
            dao = (TradeBaseDao) Class.forName(className).newInstance();
            tradeType = r.getTrdType();
            if (this.isNeedCheck(tradeType, FrameFileHandledConstant.RECORD_ERR_DEVICE[0])) {

                chkResult = this.checkForDeviceSam(r.getLineId(), r.getStationId(),
                        r.getDevTypeId(), r.getDeviceId(), r.getSamLogicalId());
                if (!chkResult) {
                    dao.insertError(r, FrameFileHandledConstant.RECORD_ERR_DEVICE[0]);
                    logger.error("交易类型" + r.getTrdType() + "记录不合法：" + " 线路：" + r.getLineId() + " 车站：" + r.getStationId() + " 设备类型：" + r.getDevTypeId()
                            + " 设备编码：" + r.getDeviceId() + " SAM逻辑卡号： " + r.getSamLogicalId() + " 对应关系不存在。");
                    return chkResult;
                }
            }
            //校验卡类型
            if (this.isNeedCheck(tradeType, FrameFileHandledConstant.RECORD_ERR_CARD[0])) {
                chkResult = this.checkForCardType(r.getCardMainId(), r.getCardSubId());
                if (!chkResult) {
                    dao.insertError(r, FrameFileHandledConstant.RECORD_ERR_CARD[0]);
                    logger.error("交易类型" + r.getTrdType() + "记录不合法：" + " 票卡主类型：" + r.getCardMainId() + " 票卡子类型：" + r.getCardSubId() + " 不合法。");
                    return chkResult;
                }
            }
            //校验时间(是否正式运营前测试数据）
            if (this.isNeedCheck(tradeType, FrameFileHandledConstant.RECORD_ERR_DATETIME[0])) {

                chkResult = this.checkForTimeForOnline(r.getCheckDatetime(), formalOnline);
                if (!chkResult) {
                    dao.insertError(r, FrameFileHandledConstant.RECORD_ERR_DATETIME[0]);
                    logger.error("交易类型" + r.getTrdType() + "记录不合法：" + " 交易时间：" + r.getCheckDatetime() + " 在正式运营时间：" + formalOnline + "之前");
                    return chkResult;
                }
            }
            //校验时间
            if (this.isNeedCheck(tradeType, FrameFileHandledConstant.RECORD_ERR_DATETIME[0])) {

                chkResult = this.checkForTime(r.getCheckDatetime(), r.getBalanceWaterNo());
                if (!chkResult) {
                    dao.insertError(r, FrameFileHandledConstant.RECORD_ERR_DATETIME[0]);
                    logger.error("交易类型" + r.getTrdType() + "记录不合法：" + " 交易时间：" + r.getCheckDatetime() + " 超过设定的最大滞留天数" + FrameCheckConstant.CHK_TIME_MAX_BEFOREDAYS);
                    return chkResult;
                }
            }

            /**
             * ***********校验金额***********************************************************************************
             */
            //校验最大余额
            if (this.isNeedCheck(tradeType, FrameFileHandledConstant.RECORD_ERR_FEE[0])) {

                chkResult = this.checkForFeeUpper(r.getCheckBalanceFee(), maxBalanceFee);
                if (!chkResult) {
                    dao.insertError(r, FrameFileHandledConstant.RECORD_ERR_FEE[0]);
                    logger.error("交易类型" + r.getTrdType() + "记录不合法：" + " 余额：" + r.getCheckBalanceFee() + " 超过设定的最大值" + FrameCheckConstant.CHK_FEE_MAX_BALANCE);
                    return chkResult;
                }
                //校验最大交易金额,仅消费，充值、冲正不校验
                //if (!r.isCheckCharge()) {
                if (r.isCheckConsumeByFlag()) {
                    chkResult = this.checkForFeeUpper(r.getCheckDealFee(), FrameCheckConstant.CHK_FEE_MAX_DEAL);
                    if (!chkResult) {
                        dao.insertError(r, FrameFileHandledConstant.RECORD_ERR_FEE[0]);
                        logger.error("交易类型" + r.getTrdType() + "记录不合法：" + " 交易金额：" + r.getCheckDealFee() + " 超过设定的最大值" + FrameCheckConstant.CHK_FEE_MAX_DEAL);
                        return chkResult;
                    }
                }

                //校验最大单次充值金额、充值交易使用
                if (r.isCheckChargeByFlag()) {
                    chkResult = this.checkForFeeUpper(r.getCheckDealFee(), FrameCheckConstant.CHK_FEE_MAX_CHARGE);
                    if (!chkResult) {
                        dao.insertError(r, FrameFileHandledConstant.RECORD_ERR_FEE[0]);
                        logger.error("交易类型" + r.getTrdType() + "记录：" + " 单次充值金额：" + r.getCheckDealFee() + "超过设定的最大值" + FrameCheckConstant.CHK_FEE_MAX_CHARGE);
                        return chkResult;
                    }
                }
                //校验最大单次购单程票金额 HCE使用
                if (r.isCheckBuyTkFlag()) {
                    chkResult = this.checkForFeeUpper(r.getCheckDealFee(), FrameCheckConstant.CHK_FEE_MAX_BUY_TK);
                    if (!chkResult) {
                        dao.insertError(r, FrameFileHandledConstant.RECORD_ERR_FEE[0]);
                        logger.error("交易类型" + r.getTrdType() + "记录：" + " 单次购票金额：" + r.getCheckDealFee() + "超过设定的最大值" + FrameCheckConstant.CHK_FEE_MAX_BUY_TK);
                        return chkResult;
                    }
                }
            }
            //校验其他错误
            if (this.isNeedCheck(tradeType, FrameFileHandledConstant.RECORD_ERR_OTHER[0])) {
                chkResult = this.checkForOther(r.getCheckDataOther());
                if (!chkResult) {
                    dao.insertError(r, FrameFileHandledConstant.RECORD_ERR_OTHER[0]);
                    logger.error("交易类型" + r.getTrdType() + " 记录不合法：" + " 交易中的更新区域字段值：" + r.getCheckDataOther().getUpdateArea() + " 不在定义中");
                    return chkResult;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        /**
         * **********************************************************************************************
         */
        return true;
    }

    private int getMaxBalanceFee(String cardMainId) {
        return 0;
    }

    private int getMaxDealFee(String cardMainId) {
        return 0;
    }

    private int getMaxChargeFee(String cardMainId) {
        return 0;
    }

    public String getTrdTypeForMobile(String trdType) {
        return HandlerTrxBase.CLASS_PARSER_SHORT_NAME_MOBILE + trdType;
    }

    public String getTrdTypeForQrCode(String trdType) {
        return HandlerTrxBase.CLASS_PARSER_SHORT_NAME_QRCODE + trdType;
    }

    public String getTrdTypeForNetPaid(String trdType) {
        return HandlerTrxBase.CLASS_PARSER_SHORT_NAME_NETPAID + trdType;
    }

    public void setZoneForTrade(String payModeId, String lineId, FileRecordBase fr) {
        if (!this.isConsumePayMode(payModeId)) {
            return;
        }

        if (lineId.equals(FrameCodeConstant.LINE_ID_MAG)) {
            fr.setZoneForTrade(ZONE_TRADE_MAG);
            return;
        }
        if (lineId.equals(FrameCodeConstant.LINE_ID_OCT)) {
            fr.setZoneForTrade(ZONE_TRADE_OCT);
            return;
        }
        fr.setZoneForTrade(ZONE_TRADE_MTR);

    }

    private boolean isConsumePayMode(String payModeId) {
        if (payModeId == null || payModeId.length() == 0) {
            return false;
        }
        for (String pmi : FrameCheckConstant.CONSUME_PAY_MODES) {
            if (pmi.equals(payModeId)) {
                return true;
            }
        }
        return false;
    }

    protected FileRecord00DetailResult getInfoForSub(FileRecordBase r, char[] b, int offset, FileRecordAddVo lineAdd) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 1;
        int count = this.getInt(b, offset);
        offset += len;

        FileRecord00DetailResult details = this.getDetailForBase(b, count, offset, lineAdd);//面值、数量
        offset = details.getOffsetTotal();
        // r.setSaleTotalNum(details.getTotalNum());//发售总数量
        r.setDetail(details.getDetails());//面值、数量明细

        // len = 4;
        //  r.setSaleTotalFee(this.getLong(b, offset));//11发售总金额
        // offset += len;
        result.setOffsetTotal(offset);
        return result;
    }

    protected FileRecord00DetailResult getInfoForSubWithType(FileRecordBase r, char[] b, int offset, FileRecordAddVo lineAdd) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 1;
        int count = this.getInt(b, offset);
        offset += len;

        FileRecord00DetailResult details = this.getDetailForBaseWithType(b, count, offset, lineAdd);//类型、面值、数量
        offset = details.getOffsetTotal();
        // r.setSaleTotalNum(details.getTotalNum());//发售总数量
        r.setDetail(details.getDetails());//面值、数量明细

        // len = 4;
        //  r.setSaleTotalFee(this.getLong(b, offset));//11发售总金额
        // offset += len;
        result.setOffsetTotal(offset);
        return result;
    }

    protected FileRecord00DetailResult getInfoForSubWithCardAndType(FileRecordBase r, char[] b, int offset, FileRecordAddVo lineAdd) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 1;
        int count = this.getInt(b, offset);
        offset += len;

        FileRecord00DetailResult details = this.getDetailForBaseWithCardAndType(b, count, offset, lineAdd);//类型、面值、数量
        offset = details.getOffsetTotal();

        r.setDetail(details.getDetails());//卡类型 支付方式、数量、金额

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getDetailForBase(char[] b, int count, int offset, FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord00DetailBase fb;
        Vector<FileRecord00DetailBase> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord00DetailBase();
            this.addCommonInfo(fb, lineAdd);

            len = 2;
            fb.setFeeUnit(this.getShort(b, offset));//9面值
            offset += len;
            // totalNum += fb.getNum();

            len = 2;
            fb.setNum(this.getShort(b, offset));//数量
            offset += len;
            totalNum += fb.getNum();

            v.add(fb);
        }
        result.setDetails(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);
        return result;
    }

    private FileRecord00DetailResult getDetailForBaseWithType(char[] b, int count, int offset, FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord00DetailBase fb;
        Vector<FileRecord00DetailBase> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord00DetailBase();
            this.addCommonInfo(fb, lineAdd);

            len = 1;
            fb.setCommonType(this.getInt(b, offset));//类型
            offset += len;

            len = 2;
            fb.setFeeUnit(this.getShort(b, offset));//9面值
            offset += len;
            // totalNum += fb.getNum();

            len = 2;
            fb.setNum(this.getShort(b, offset));//数量
            offset += len;
            totalNum += fb.getNum();

            v.add(fb);
        }
        result.setDetails(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);
        return result;
    }

    private FileRecord00DetailResult getDetailForBaseWithCardAndType(char[] b, int count, int offset, FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord00DetailBase fb;
        Vector<FileRecord00DetailBase> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord00DetailBase();
            this.addCommonInfo(fb, lineAdd);

            len = 1;
            fb.setCardMainId(this.getBcdString(b, offset, len));//卡主类型
            offset += len;

            len = 1;
            fb.setCardSubId(this.getBcdString(b, offset, len));//卡子类型
            offset += len;

            len = 1;
            fb.setCommonType(this.getInt(b, offset));//类型
            offset += len;

            len = 4;
            fb.setNum(this.getShort(b, offset));//数量
            offset += len;
            totalNum += fb.getNum();

            len = 4;
            fb.setFee(this.getShort(b, offset));//金额
            offset += len;
            // totalNum += fb.getNum();

            v.add(fb);
        }
        result.setDetails(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);
        return result;
    }

    public static String trim(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        return str.trim();
    }
}
