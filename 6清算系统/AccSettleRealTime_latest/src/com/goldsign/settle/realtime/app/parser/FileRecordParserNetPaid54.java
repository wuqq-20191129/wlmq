/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord54;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class FileRecordParserNetPaid54 extends FileRecordParserBase{
    private static Logger logger = Logger.getLogger(FileRecordParserNetPaid54.class.getName());
    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecord54 r = new FileRecord54();
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
    
    @Override
    public void handleMessage(MessageBase msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private FileRecord00DetailResult getInfoForDevice(FileRecord54 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        
        int len = 2;
        //r.setTrdType(this.getCharString(b, offset, len));//交易类型
         r.setTrdType(this.getTrdTypeForNetPaid(this.getCharString(b, offset, len)));//交易类型增加NetPaid前缀20161216
        offset += len;
        
        len = 2;
        r.setLineId(this.getCharString(b, offset, len));//线路代码
        offset += len;
        
        len = 2;
        r.setStationId(this.getCharString(b, offset, len));//站点代码
        offset += len;
        len = 2;
        r.setDevTypeId(this.getCharString(b, offset, len));//设备类型
        offset += len;
        
        len = 3;
        r.setDeviceId(this.getCharString(b, offset, len));//设备编号
        offset += len;
        
        len = 16;
        r.setSamLogicalId(this.getCharString(b, offset, len));//SAM卡逻辑卡号
        offset += len;
        
        len = 4;
        r.setSamTradeSeq(this.getLong(b, offset));//本次交易SAM卡脱机交易流水号
        offset += len;
        
        len = 7;
        r.setDealDatetime(this.getBcdString(b, offset, len));//日期时间
        offset += len;
        
        len = 1;
        r.setCardMainId(this.getBcdString(b, offset, len));//票卡主类型
        offset += len;
        
        len = 1;
        r.setCardSubId(this.getBcdString(b, offset, len));//票卡子类型
        offset += len;
        
        len = 20;
        r.setCardLogicalId(this.getCharString(b, offset, len));//票卡逻辑卡号
        offset += len;
        
        len = 20;
        r.setCardPhysicalId(this.getCharString(b, offset, len));//票卡物理卡号
        offset += len;
        
        len = 1;
        r.setCardStatusId(this.getInt(b, offset));//卡状态代码
        offset += len;
        
        
        
        
        result.setOffsetTotal(offset);
        return result;
    }
    
    private FileRecord00DetailResult getInfoForTrade(FileRecord54 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        
        int len = 4;
        r.setDealFee(this.getLong(b, offset));//交易金额
        offset += len;
        
        len = 4;
        r.setDealBalanceFee(this.getLong(b, offset));//余额
        offset += len;
        
        len = 4;
        r.setCardChargeSeq(this.getLong(b, offset));//票卡充值交易计数
        offset += len;
        len = 4;
        r.setCardConsumeSeq(this.getLong(b, offset));//票卡扣款交易计数
        offset += len;
        
        len = 2;
        r.setPayModeId(this.getCharString(b, offset, len));//支付类型

        offset += len;
        len = 4;
        r.setReceiptId(this.getCharString(b, offset, len));//凭证ID
        offset += len;
        
        len = 10;
        r.setTac(this.getCharString(b, offset, len));//交易认证码
        offset += len;
        
        len = 1;
        r.setEntryLineId(this.getBcdString(b, offset, len));//入口线路代码
        offset += len;
        
        len = 1;
        r.setEntryStationId(this.getBcdString(b, offset, len));//入口站点代码
        offset += len;
        
        len = 16;
        r.setEntrySamLogicalId(this.getCharString(b, offset, len));//入口SAM逻辑卡号
        offset += len;
        
        len = 7;
        r.setEntryDatetime(this.getBcdString(b, offset, len));//进站日期时间
        offset += len;
        
        len = 6;
        r.setOperatorId(this.getCharString(b, offset, len));//操作员代码
        offset += len;
        
        len = 1;
        r.setShiftId(this.getBcdString(b, offset, len));//BOM班次序号
        offset += len;
        
        len = 16;
        r.setLastSamLogicalId(this.getCharString(b, offset, len));//上次交易SAM逻辑卡号
        offset += len;
        
        len = 7;
        r.setLastDealDatetime(this.getBcdString(b, offset, len));//上次交易日期时间
        offset += len;
        
        
        len = 4;
        r.setDealNoDiscountFee(this.getLong(b, offset));//钱包交易金额
        offset += len;
        
        len = 1;
        r.setCardAppFlag(this.getCharString(b, offset, len));//卡应用标识
        offset += len;
        
        len = 3;
        r.setLimitMode(this.getCharString(b, offset, len));//限制使用模式
        offset += len;
        
        len = 2;
        r.setLimitEntryStation(this.getBcdString(b, offset, len));//限制进站代码
        offset += len;
        
        len = 2;
        r.setLimitExitStation(this.getBcdString(b, offset, len));//限制出站代码
        offset += len;
        
         len = 1;
        r.setWorkMode(this.getCharString(b, offset, len));//出站工作模式
        offset += len;
        
        len = 4;
        r.setBusCityCode(this.getCharString(b, offset, len));//城市代码
        offset += len;
        
        len = 4;
        r.setBusBusinessCode(this.getCharString(b, offset, len));//行业代码
        offset += len;
        
        len = 2;
        r.setBusTacDealType(this.getCharString(b, offset, len));//TAC交易类型
        offset += len;
        
        len = 12;
        r.setBusTacDevId(this.getCharString(b, offset, len));//TAC终端编号
        offset += len;
        
        len = 1;
        r.setCardAppMode(this.getCharString(b, offset, len));//卡应用模式
        offset += len;
        
         //设置通用金额、余额
      //  r.setCommonFeeByFen(r.getDealFee(), r.getDealBalanceFee());
        
        len = 20;
        r.setOrderNo(this.getCharString(b, offset, len));//订单号
        offset += len;
        
        
        
        
        result.setOffsetTotal(offset);
        return result;
        
    }

    private void setCheckInfo(FileRecord54 r) {
        r.setCheckBalanceFee(r.getDealBalanceFee());//余额
        r.setCheckDealFee(r.getDealFee());//交易金额
        r.setCheckChargeFee(r.getDealFee());//充值金额
        
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
    private String getCheckUniqueKey(FileRecord54 r){
         //唯一性校验主健
         //票卡类型+SAM逻辑卡号+SAM卡交易序号+交易时间+交易金额+余额
        //处理重复扣费问题，增加交易金额+余额字段
         String key =r.getCardMainId()+r.getCardSubId()+
                     r.getSamLogicalId()+r.getSamTradeSeq()+
                     r.getDealDatetime()+r.getDealFee()+r.getDealBalanceFee();
         return key;
     }

    private void setTac(FileRecord54 r) {
        
        r.setPurseOpType(r.getPayModeId());
        r.setTacLSKey(this.getTacLSKey(r.getCardLogicalId()));//票卡逻辑卡号作密钥离散值截取后16位
        r.setTacInRecord(r.getTac());//TAC码
        r.setTacDealAmout(r.getDealFee());//交易金额
        //交易类型对照表设置校验的交易类型02:充值 09:复合消费 06:单次消费 05:单程票所有交易
        r.setTacDealType(r.getCardMainId(), r.getTrdType(), r.getPurseOpType());
       // r.setTacDealType(r.getBusTacDealType());//2014024 校验TAC不通过
        r.setTacTerminalNo(r.getSamLogicalId());//SAM卡号作为终端编号,截取后12位
        r.setTacTerminalTradeSeq(r.getSamTradeSeq());;//SAM交易流水作为终端交易流水
        r.setTacDealTime(r.getDealDatetime());//交易时间
        r.setTacTradeType(r.getTrdType());//原始交易类型
        r.setTacKeyGroupAndInd(r.getCardMainId());//根据票卡主类型设置密钥组、密钥索引

        r.setTacDealBalanceFee(r.getDealBalanceFee());//交易余额
        r.setTacCardChargeSeq(r.getCardChargeSeq());//充值交易计数
    }
    
}
