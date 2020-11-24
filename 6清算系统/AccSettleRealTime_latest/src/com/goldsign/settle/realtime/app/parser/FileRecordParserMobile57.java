/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord57;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.app.vo.FileRecordMobile57;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.util.DateHelper;
import com.goldsign.settle.realtime.frame.util.TradeUtil;

/**
 *
 * @author hejj
 */
public class FileRecordParserMobile57 extends FileRecordParserBase{
    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecordMobile57 r = new FileRecordMobile57();
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



            //获取附加信息
            this.addCommonInfo(r, lineAdd);
            //获取TAC校验需要的字段数据
            this.setTac(r);
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

    private void setCheckInfo(FileRecord57 r) {

        r.setCheckDatetime(r.getReturnDatetime());//交易时间
        r.setCheckUniqueKey(this.getCheckUniqueKey(r));//唯一性校验主健

    }

    private String getCheckUniqueKey(FileRecord57 r) {
        //唯一性校验主健
        //票卡类型+SAM逻辑卡号+SAM卡交易序号+进站时间
        String key = r.getCardMainId() + r.getCardSubId()
                + r.getSamLogicalId() + r.getSamTradeSeq()
                + r.getReturnDatetime();
        return key;
    }

    @Override
    public void handleMessage(MessageBase msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private FileRecord00DetailResult getInfoForDevice(FileRecordMobile57 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 2;
       // r.setTrdType(this.getCharString(b, offset, len));//1交易类型
        r.setTrdType(this.getTrdTypeForMobile(this.getCharString(b, offset, len)));//1交易类型
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
        r.setSamLogicalId(this.getCharString(b, offset, len));//5SAM卡逻辑卡号
        offset += len;

        len = 4;
        r.setSamTradeSeq(this.getLong(b, offset));//6本次交易SAM卡脱机交易流水号
        r.setSamTradeSeq_s(Long.toString(r.getSamTradeSeq()));
        offset += len;


        len = 1;
        r.setCardMainId(this.getBcdString(b, offset, len));//7票卡主类型
        offset += len;

        len = 1;
        r.setCardSubId(this.getBcdString(b, offset, len));//7票卡子类型
        offset += len;

        len = 20;
        r.setCardLogicalId(this.getCharString(b, offset, len));//8票卡逻辑卡号
        offset += len;

        len = 20;
        r.setCardPhysicalId(this.getCharString(b, offset, len));//9票卡物理卡号
        offset += len;

        len = 1;
        r.setCardStatusId(this.getInt(b, offset));//10卡状态代码
        r.setCardStatusId_s(Long.toString(r.getCardStatusId()));
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForTrade(FileRecordMobile57 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 4;
        r.setReturnBalanceFee(this.getLong(b, offset));//11退卡内金额
         r.setReturnBalanceFee_s(TradeUtil.convertFenToYuan(r.getReturnBalanceFee()).toString());
        offset += len;

        len = 2;
        r.setReturnDepositFee(this.getShort(b, offset));//12退押金
        r.setReturnDepositFee_s(TradeUtil.convertFenToYuan(r.getReturnDepositFee()).toString());
        offset += len;

        len = 2;
        r.setPenaltyFee(this.getShort(b, offset));//13罚款
        r.setPenaltyFee_s(TradeUtil.convertFenToYuan(r.getPenaltyFee()).toString());
        offset += len;
        len = 1;
        r.setPenaltyReasonId(this.getBcdString(b, offset, len));//14罚款原因
        offset += len;

        len = 4;
        r.setCardConsumeSeq(this.getLong(b, offset));//15票卡扣款交易计数
         r.setCardConsumeSeq_s(Long.toString(r.getCardConsumeSeq()));
        offset += len;




        len = 1;
        r.setReturnType(this.getCharString(b, offset, len));//16退款类型
        offset += len;

        //如是非即时退款，不校验TAC，在这里设置非即时退款标识 20151223 modified by hejj
        this.setNonReturnFlag(r);

        len = 7;
        r.setReturnDatetime(this.getBcdString(b, offset, len));//17日期时间
         r.setReturnDatetime_s(DateHelper.convertStandFormatDatetime(r.getReturnDatetime()));
        offset += len;

        len = 4;
        r.setReceiptId(this.getCharString(b, offset, len));//18凭证ID
        offset += len;

        len = 7;
        r.setApplyDatetime(this.getBcdString(b, offset, len));//19申请日期时间
        r.setApplyDatetime_s(DateHelper.convertStandFormatDatetime(r.getApplyDatetime()));
        offset += len;

        len = 10;
        r.setTac(this.getCharString(b, offset, len));//20交易认证码
        offset += len;



        len = 6;
        r.setOperatorId(this.getCharString(b, offset, len));//21操作员代码
        offset += len;

        len = 1;
        r.setShiftId(this.getBcdString(b, offset, len));//22BOM班次序号
        offset += len;

        len = 4;
        r.setAuxiFee(this.getLong(b, offset));//23手续费
        r.setAuxiFee_s(TradeUtil.convertFenToYuan(r.getAuxiFee()).toString());
        offset += len;

        len = 1;
        r.setCardAppFlag(this.getCharString(b, offset, len));//24卡应用标识
        offset += len;

        len = 2;
        r.setBusTacDealType(this.getCharString(b, offset, len));//25TAC交易类型
        offset += len;

        len = 12;
        r.setBusTacDevId(this.getCharString(b, offset, len));//26TAC终端编号
        offset += len;

       len = 11;
        r.setMobileNo(this.getCharString(b, offset, len));//手机号
        offset += len;

        len = 2;
        r.setPaidChannelType(this.getCharString(b, offset, len));//支付渠道
        offset += len;

        len = 4;
        r.setPaidChannelCode(this.getCharString(b, offset, len));//支付渠道代码
        offset += len;





        result.setOffsetTotal(offset);
        return result;

    }

    private void setNonReturnFlag(FileRecord57 r) {
        String returnType = r.getReturnType();
        if (returnType == null || returnType.length() == 0) {
            return;
        }
        if (returnType.equals(RETURN_TYPE_NON_INSTANT)) {
            r.setNonReturn(true);
        }
        return;
    }

    private void setTac(FileRecord57 r) {

        r.setPurseOpType(r.getPayModeId());
        r.setTacLSKey(this.getTacLSKey(r.getCardLogicalId()));//票卡逻辑卡号作密钥离散值截取后16位
        r.setTacInRecord(r.getTac());//TAC码
        r.setTacDealAmout(r.getReturnBalanceFee());//交易金额
        //交易类型对照表设置校验的交易类型02:充值 09:复合消费 06:单次消费 05:单程票所有交易
        r.setTacDealType(r.getCardMainId(), r.getTrdType(), r.getPurseOpType());
        r.setTacTerminalNo(r.getSamLogicalId());//SAM卡号作为终端编号,截取后12位
        r.setTacTerminalTradeSeq(r.getSamTradeSeq());;//SAM交易流水作为终端交易流水
        r.setTacDealTime(r.getReturnDatetime());//交易时间
        r.setTacTradeType(r.getTrdType());//原始交易类型
        r.setTacKeyGroupAndInd(r.getCardMainId());//根据票卡主类型设置密钥组、密钥索引
    }
    
}
