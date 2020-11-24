/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.app.vo.FileRecordQrCode53;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import static com.goldsign.settle.realtime.frame.parser.FileRecordParserBase.trim;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserConstantBase;
import com.goldsign.settle.realtime.frame.util.DateHelper;
import com.goldsign.settle.realtime.frame.util.TradeUtil;

/**
 *
 * @author hejj
 */
public class FileRecordParserQrCode53 extends FileRecordParserConstantBase {

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecordQrCode53 r = new FileRecordQrCode53();
        FileRecord00DetailResult result;
        char[] b;
        int offset = 0;

        try {
            b = this.getLineCharFromFile(line);

            //获取设备相关信息
            result = this.getInfoForDevice(r, b, offset);
            offset = result.getOffsetTotal();
            //获取交易相关信息
            result = this.getInfoForTrade(r, b, offset);
            offset = result.getOffsetTotal();

            //获取一卡通优惠相关信息
            result = this.getInfoForOctDiscount(r, b, offset);
            offset = result.getOffsetTotal();
            //获取二维码相关信息
            result = this.getInfoForQrCode(r, b, offset);
            offset = result.getOffsetTotal();

            //获取二维码发码平台信息20200706
            result = this.getInfoForIssueQrCodePlatformFlag(r, b, offset);
            offset = result.getOffsetTotal();

            //获取附加信息
            this.addCommonInfo(r, lineAdd);
            //校验数据合法性，如果数据不合法，数据插入错误表
            this.setCheckInfo(r);
            if (!this.checkData(r)) {
                return null;
            }

        } catch (Exception ex) {
            throw new RecordParseForFileException(ex.getMessage());
        }

        return r;
    }

    private void setCheckInfo(FileRecordQrCode53 r) {

        r.setCheckDatetime(r.getEntryDatetime());//交易时间
        r.setCheckUniqueKey(this.getCheckUniqueKey(r));//唯一性校验主健

    }

    private String getCheckUniqueKey(FileRecordQrCode53 r) {
        //唯一性校验主健
        //票卡类型+SAM逻辑卡号+SAM卡交易序号+进站时间+票卡逻辑卡号
        String key = r.getCardMainId() + r.getCardSubId()
                + r.getSamLogicalId() + r.getSamTradeSeq()
                + r.getEntryDatetime() + r.getCardLogicalId();
        return key;
    }

    @Override
    public void handleMessage(MessageBase msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected FileRecord00DetailResult getInfoForDevice(FileRecordQrCode53 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 2;
        r.setTrdType(getTrdTypeForQrCode(this.getCharString(b, offset, len)));//1交易类型 增加QrCode标识
        offset += len;

        len = 2;
        r.setRecordVer(this.getCharString(b, offset, len));//记录版本20180528 by hejj
        offset += len;

        len = 2;
        r.setLineId(this.getCharString(b, offset, len));//2线路代码
        offset += len;

        len = 2;
        r.setStationId(this.getCharString(b, offset, len));//2站点代码
        offset += len;
        len = 2;
        r.setDevTypeId(this.getCharString(b, offset, len));//3设备类型
        offset += len;

        len = 3;
        r.setDeviceId(this.getCharString(b, offset, len));//4设备编号
        offset += len;

        len = 16;
        r.setSamLogicalId(trim(this.getCharString(b, offset, len)));//5SAM卡逻辑卡号
        offset += len;

        len = 4;
        r.setSamTradeSeq(this.getLong(b, offset));//6SAM卡脱机流水号
        r.setSamTradeSeq_s(Long.toString(r.getSamTradeSeq()));
        offset += len;

        len = 1;
        r.setCardMainId(this.getBcdString(b, offset, len));//7主类型代码
        offset += len;

        len = 1;
        r.setCardSubId(this.getBcdString(b, offset, len));//7子类型代码
        offset += len;

        len = 20;
        r.setCardLogicalId(trim(this.getCharString(b, offset, len)));//8票卡逻辑卡号
        offset += len;

        len = 20;
        r.setCardPhysicalId(trim(this.getCharString(b, offset, len)));//9票卡物理卡号
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    protected FileRecord00DetailResult getInfoForTrade(FileRecordQrCode53 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 7;
        r.setEntryDatetime(this.getBcdString(b, offset, len));//10进站日期时间
        r.setEntryDatetime_s(DateHelper.convertStandFormatDatetime(r.getEntryDatetime()));
        offset += len;

        len = 1;
        r.setCardStatusId(this.getInt(b, offset));//11卡状态代码
        offset += len;
        len = 4;
        r.setBalanceFee(this.getLong(b, offset));//12余额
        r.setBalanceFee_s(TradeUtil.convertFenToYuan(r.getBalanceFee()).toString());
        offset += len;

        len = 1;
        r.setCardAppFlag(this.getCharString(b, offset, len));//13卡应用标识
        offset += len;
        len = 3;
        r.setLimitMode(this.getCharString(b, offset, len));//14限制使用模式
        offset += len;
        len = 2;
        r.setLimitEntryStation(this.getBcdString(b, offset, len));//15限制进站代码
        offset += len;

        len = 2;
        r.setLimitExitStation(this.getBcdString(b, offset, len));//16限制出站代码
        offset += len;

        len = 1;
        r.setWorkMode(this.getCharString(b, offset, len));//16进站工作模式
        offset += len;

        len = 4;
        r.setCardConsumeSeq(this.getLong(b, offset));//票卡扣款交易计数
        r.setCardConsumeSeq_s(Long.toString(r.getCardConsumeSeq()));
        offset += len;

        len = 1;
        r.setCardAppMode(this.getCharString(b, offset, len));//卡应用模式
        offset += len;

        len = 7;
        r.setTctActiveDatetime(this.getBcdString(b, offset, len));//乘车票激活日期时间
        r.setTctActiveDatetime_s(DateHelper.convertStandFormatDatetime(r.getTctActiveDatetime()));
        offset += len;

        result.setOffsetTotal(offset);
        return result;

    }

    protected FileRecord00DetailResult getInfoForOctDiscount(FileRecordQrCode53 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result;
        result = this.getInfoForOctDiscountFile(r, b, offset);

        return result;
    }

    private FileRecord00DetailResult getInfoForOctDiscountFile(FileRecordQrCode53 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 6;
        r.setDiscountYearMonth(this.getCharString(b, offset, len));//优惠月
        offset += len;

        len = 4;
        r.setAccumulateConsumeFee(this.getLong(b, offset));//优惠月累计消费金额
        r.setAccumulateConsumeFee_s(TradeUtil.convertFenToYuan(r.getAccumulateConsumeFee()).toPlainString());
        offset += len;

        len = 4;
        r.setIntervalBetweenBusMetro(this.getLong(b, offset));//联乘时间间隔
        r.setIntervalBetweenBusMetro_s(Long.toString(r.getIntervalBetweenBusMetro()));
        offset += len;

        len = 7;
        r.setLastBusDealDatetime(this.getBcdString(b, offset, len));//上次公交交易时间
        r.setLastBusDealDatetime_s(DateHelper.convertStandFormatDatetime(r.getLastBusDealDatetime()));
        offset += len;

        result.setOffsetTotal(offset);
        return result;

    }

    protected FileRecord00DetailResult getInfoForQrCode(FileRecordQrCode53 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 40;
        //20200914 增加trim()方法,预防LC填写乱码值
        r.setBusinessWaterNo(this.getCharString(b, offset, len).trim());//业务流水
        offset += len;

        len = 11;
        r.setMobileNo(this.getCharString(b, offset, len));//手机号码
        offset += len;

        result.setOffsetTotal(offset);
        return result;

    }

    protected FileRecord00DetailResult getInfoForIssueQrCodePlatformFlagFromFile(FileRecordQrCode53 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 2;
        r.setIssueQrcodePlatformFlag(this.getCharString(b, offset, len));//发码平台标识
        offset += len;

        result.setOffsetTotal(offset);
        return result;

    }

    protected FileRecord00DetailResult getInfoForIssueQrCodePlatformFlagFromDefault(FileRecordQrCode53 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 2;
        r.setIssueQrcodePlatformFlag(this.DEF_ISSUE_QRCODE_PLATFORM_FLAG);//发码平台标识
        offset += len;

        result.setOffsetTotal(offset);
        return result;

    }

    protected FileRecord00DetailResult getInfoForIssueQrCodePlatformFlag(FileRecordQrCode53 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        if (this.isVersionOver12(r.getRecordVer())) {//>=12版本
            result = this.getInfoForIssueQrCodePlatformFlagFromFile(r, b, offset);

        } else//10/11/版本
        {
            result = this.getInfoForIssueQrCodePlatformFlagFromDefault(r, b, offset);
        }

        return result;

    }
    /*
    private FileRecord00DetailResult getInfoForOctDiscountDefault(FileRecord53 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 6;
        r.setDiscountYearMonth(this.DEF_OCT_DISC_YEAR_MONTH);//优惠月
        offset += len;

        len = 4;
        r.setAccumulateConsumeFee(this.DEF_OCT_DISC_ACCUMUlATE_CONSUME_FEE);//优惠月累计消费金额
        r.setAccumulateConsumeFee_s(TradeUtil.convertFenToYuan(r.getAccumulateConsumeFee()).toPlainString());
        offset += len;

        len = 4;
        r.setIntervalBetweenBusMetro(this.DEF_OCT_DISC_INTERVAL_BETWEEN_BUS_METRO);//联乘时间间隔
        r.setIntervalBetweenBusMetro_s(Long.toString(r.getIntervalBetweenBusMetro()));
        offset += len;

        len = 7;
        r.setLastBusDealDatetime(this.DEF_OCT_DISC_LAST_BUS_DEAL_DATETIME);//上次公交交易时间
        r.setLastBusDealDatetime_s(DateHelper.convertStandFormatDatetime(r.getLastBusDealDatetime()));
        offset += len;

        result.setOffsetTotal(offset);
        return result;

    }
     */

}
