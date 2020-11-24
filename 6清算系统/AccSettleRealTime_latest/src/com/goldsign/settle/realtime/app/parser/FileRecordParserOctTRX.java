/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord54;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.app.vo.FileRecordOctTRX;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.exception.RecordParseIgnoreException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.util.FileUtil;
import com.goldsign.settle.realtime.frame.util.TradeUtil;
import com.goldsign.settle.realtime.frame.vo.FileNameSection;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class FileRecordParserOctTRX extends FileRecordParserBase {

    private static Logger logger = Logger.getLogger(FileRecordParser54.class.getName());

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecordOctTRX r = new FileRecordOctTRX();
        FileRecord00DetailResult result;
        char[] b;
        int offset = 0;

        try {
            b = this.getLineCharFromFile(line);


            //获取交易相关信息
            //设置所有记录的交易类型
            this.setTradeType(lineAdd, r);

            result = this.getInfoForTrade(r, b, offset);
            if(result ==null)
                return null;
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

    private void setTradeType(FileRecordAddVo lineAdd, FileRecordBase fr) {
        String fileName = lineAdd.getFileName();
        FileNameSection fns = FileUtil.getFileSectForOct(fileName);

        String tradType = FileUtil.getTradeTypeForOct(fns.getTradType());
        fr.setTrdType(tradType);

    }

    @Override
    public void handleMessage(MessageBase msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private FileRecord00DetailResult getInfoForTrade(FileRecordOctTRX r, char[] b, int offset) throws RecordParseIgnoreException {
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        try {
            int len = 10;
            r.setStrSamTradeSeq(this.getCharString(b, offset, len));//脱机交易流水号
            r.setSamTradeSeq(TradeUtil.getIntValue(r.getStrSamTradeSeq()));
            offset += len + 1;

            len = 2;
            r.setCardMainId(this.getCharString(b, offset, len));//票卡主类型
            offset += len;

            len = 2;
            r.setCardSubId(this.getCharString(b, offset, len));//票卡子类型
            offset += len + 1;


            len = 20;
            //20140707 modify by hejj 逻辑卡号左补0
            r.setCardLogicalId(TradeUtil.formatFieldForRight(this.getCharString(b, offset, len),len,ADD_CHAR_ZERO));//票卡逻辑卡号
            offset += len + 1;

            len = 20;
            r.setCardPhysicalId(this.getCharString(b, offset, len));//票卡物理卡号
            offset += len + 1;

            /**
             * ***************************************************
             */
            len = 16;
            r.setLastSamLogicalId(this.getCharString(b, offset, len));//上次交易SAM卡逻辑卡号
            offset += len + 1;

            len = 14;
            r.setLastDealDatetime(this.getCharString(b, offset, len));//上次交易日期时间
            offset += len + 1;

            len = 16;
            r.setSamLogicalId(this.getCharString(b, offset, len));//本次交易SAM卡逻辑卡号
            offset += len + 1;

            len = 14;
            r.setDealDatetime(this.getCharString(b, offset, len));//本次交易日期时间
            offset += len + 1;
            /**
             * *************************************************
             */
            len = 16;
            r.setEntrySamLogicalId(this.getCharString(b, offset, len));//入口SAM卡逻辑卡号
            offset += len + 1;

            len = 14;
            r.setEntryDatetime(this.getCharString(b, offset, len));//入口日期时间
            offset += len + 1;

            len = 2;
            r.setPayModeId(this.getCharString(b, offset, len));//交易类型-支付类型
            offset += len + 1;

            len = 6;
            r.setDealFee_s(this.getCharString(b, offset, len));//交易金额
            offset += len + 1;
            /**
             * *****************************************
             */
            len = 8;
            r.setDealBalance_s(this.getCharString(b, offset, len));//本次余额
            offset += len + 1;
           

            //设置通用金额、余额
            r.setCommonFeeByYuan(r.getDealFee_s(), r.getDealBalance_s());

            len = 10;
            r.setStrCardConsumeSeq(this.getCharString(b, offset, len));//票卡消费交易计数
            r.setCardConsumeSeq(TradeUtil.getIntValue(r.getStrCardConsumeSeq()));
            offset += len + 1;

            len = 10;
            r.setTac(this.getCharString(b, offset, len));//交易认证码
            offset += len + 1;

            len = 4;
            r.setBusCityCode(this.getCharString(b, offset, len));//城市代码
            offset += len + 1;

            /**
             * ************************************
             */
            len = 4;
            r.setBusBusinessCode(this.getCharString(b, offset, len));//行业代码
            offset += len + 1;

            len = 2;
            r.setBusTacDealType(this.getCharString(b, offset, len));//TAC交易类型
            offset += len + 1;

            len = 12;
            r.setBusTacDevId(this.getCharString(b, offset, len));//TAC终端编号
            offset += len + 1;




            result.setOffsetTotal(offset);
        } catch (Exception e) {
            logger.error("解释文件错误：忽略错误，继续下一条记录解释。"+e);
            return null;
            
        }
        return result;

    }

    private void setCheckInfo(FileRecordOctTRX r) {
        // r.setCheckBalanceFee(this.getDealBalance(r.getStrDealBalance()));//余额分
        //r.setCheckDealFee(this.getDealAmount(r.getStrDealFee()));//交易金额分
        r.setCheckBalanceFee(r.getBalanceFeeFen());//余额分
        r.setCheckDealFee(r.getTradeFeeFen());//交易金额分
        r.setCheckChargeFee(0);//充值金额

        r.setCheckDatetime(r.getDealDatetime());//交易时间
         if (this.isCharge(r.getPayModeId()) || this.isChargeConsume(r.getPayModeId())) {
            // r.setCheckCharge(true);//是否充值交易标志
            r.setCheckPurseChangeType(CHECK_PURSE_CHANGE_TYPE_CHARGE);//20180929 modify by hejj
        }
        if (this.isBuyTk(r.getPayModeId())) {
            r.setCheckPurseChangeType(CHECK_PURSE_CHANGE_TYPE_BUY_TK);//20180929 modify by hejj
        }
         r.setCheckUniqueKey(this.getCheckUniqueKey(r));//唯一性校验主健

    }
    private String getCheckUniqueKey(FileRecordOctTRX r){
         //唯一性校验主健
         //票卡类型+SAM逻辑卡号+SAM卡交易序号+交易时间
         String key =r.getCardMainId()+r.getCardSubId()+
                     r.getSamLogicalId()+r.getSamTradeSeq()+
                     r.getDealDatetime();
         return key;
     }

    private int getDealAmount(String dealFeeYuan) {
        return TradeUtil.convertYuanToFen(dealFeeYuan);
    }

    private int getDealBalance(String balanceFeeYuan) {
        return TradeUtil.convertYuanToFen(balanceFeeYuan);
    }

    private int getSamTradeSeq(String strSamTradeSeq) {
        return Integer.parseInt(strSamTradeSeq);
    }

    private void setTac(FileRecordOctTRX r) {
        r.setPurseOpType(r.getPayModeId());
        r.setTacLSKey(this.getTacLSKey(r.getCardLogicalId().trim()));//票卡逻辑卡号作密钥离散值截取后16位
        r.setTacInRecord(r.getTac());//TAC码
        //r.setTacDealAmout(this.getDealAmount(r.getStrDealFee()));//交易金额单位：分
        r.setTacDealAmout(r.getTradeFeeFen());//交易金额单位：分
        //交易类型对照表设置校验的交易类型02:充值 09:复合消费 06:单次消费 05:单程票所有交易
       //  r.setTacDealType(r.getCardMainId(), r.getTrdType(), r.getPurseOpType());
        
         r.setTacDealType(r.getBusTacDealType());//直接取交易的TAC交易类型 20140424 TAC校验不过，有问题
         r.setTacTerminalNo(r.getSamLogicalId());//SAM卡号作为终端编号,截取后12位
      //  r.setTacTerminalNo(r.getBusTacDevId());//直接取交易的终端编号 20140424 TAC校验不过，有问题
        // r.setTacTerminalTradeSeq(this.getSamTradeSeq(r.getStrSamTradeSeq()));;//SAM交易流水作为终端交易流水
        r.setTacTerminalTradeSeq(r.getSamTradeSeq());;//SAM交易流水作为终端交易流水
        r.setTacDealTime(r.getDealDatetime());//交易时间
        r.setTacTradeType(r.getTrdType());//原始交易类型
        r.setTacKeyGroupAndInd(r.getCardMainId());//根据票卡主类型设置密钥组、密钥索引

        // r.setTacDealBalanceFee(r.getDealBalanceFee());//交易余额
        //r.setTacCardChargeSeq(r.getCardChargeSeq());//充值交易计数
    }
}
