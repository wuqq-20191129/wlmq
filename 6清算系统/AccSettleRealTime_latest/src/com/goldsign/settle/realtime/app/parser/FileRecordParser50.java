/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord31;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.app.vo.FileRecord50;
import com.goldsign.settle.realtime.app.vo.FileRecord53;

import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.constant.FrameFileHandledConstant;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserConstantBase;
import com.goldsign.settle.realtime.frame.util.DateHelper;
import com.goldsign.settle.realtime.frame.util.TradeUtil;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;

/**
 *
 * @author hejj
 * 单程票售票记录
 * 
 */
public class FileRecordParser50 extends FileRecordParserConstantBase {

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecord50 r = new FileRecord50();
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
            //获取交易其他相关信息
            result = this.getInfoForOther(r, b, offset);
            offset = result.getOffsetTotal();
            
            //获取订单号20190103
            result = this.getInfoForOrderNo(r, b, offset);
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
     private void setCheckInfo(FileRecord50 r) {
        r.setCheckBalanceFee(0);//余额
        r.setCheckDealFee(r.getSaleFee());//交易金额
        r.setCheckChargeFee(r.getSaleFee());//充值金额
        
        r.setCheckDatetime(r.getSaleTime());//交易时间
        r.setCheckUniqueKey(this.getCheckUniqueKey(r));//唯一性校验主健
       
        
    }
     private String getCheckUniqueKey(FileRecord50 r){
         //唯一性校验主健
         //票卡类型+SAM卡逻辑卡号+SAM卡交易流水+交易时间+交易金额+交易余额
         String key =r.getCardMainId()+r.getCardSubId()+
                     r.getSamLogicalId()+r.getSamTradeSeq()+
                     r.getSaleTime();
         return key;
         
     }

    private FileRecord00DetailResult getInfoForOther(FileRecord50 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 1;
        r.setZoneId(this.getBcdString(b, offset, len));//区段代码
        offset += len;

        len = 10;
        r.setTac(this.getCharString(b, offset, len));//TAC
        offset += len;

        len = 1;
        r.setDepositType(this.getBcdString(b, offset, len));//成本押金
       
        offset += len;

        len = 2;
        r.setDepositFee(this.getShort(b, offset));//成本押金金额
         r.setDepositFee_s(TradeUtil.convertFenToYuan(r.getDepositFee()).toString());
        offset += len;

        len = 6;
        r.setOperatorId(this.getCharString(b, offset, len));//操作员代码
        offset += len;

        len = 1;
        r.setShiftId(this.getBcdString(b, offset, len));//BOM班次序号
        offset += len;

        len = 4;
        r.setAuxiFee(this.getLong(b, offset));//手续费
        r.setAuxiFee_s(TradeUtil.convertFenToYuan(r.getAuxiFee()).toString());
        offset += len;

        len = 1;
        r.setCardAppFlag(this.getCharString(b, offset, len));//卡应用标识
        offset += len;
        
        len = 2;
        r.setBusTacDealType(this.getCharString(b, offset, len));//TAC交易类型
        offset += len;
        
        len = 12;
        r.setBusTacDevId(this.getCharString(b, offset, len));//TAC终端编号
        offset += len;


        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForTrade(FileRecord50 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 4;
        r.setSamTradeSeq(this.getLong(b, offset));//SAM卡脱机交易流水号
        r.setSamTradeSeq_s(Long.toString(r.getSamTradeSeq()));
        offset += len;

        len = 7;
        r.setSaleTime(this.getBcdString(b, offset, len));//交易时间
        r.setSaleTime_s(DateHelper.convertStandFormatDatetime(r.getSaleTime()));
        offset += len;

        len = 1;
        r.setPayType(this.getBcdString(b, offset, len));//支付方式
        offset += len;
        len = 20;
        r.setCardLogicIdPay(trim(this.getCharString(b, offset, len)));//支付票卡逻辑卡号
        offset += len;

        len = 4;
        r.setCardCountUsed(this.getLong(b, offset));//单程票使用次数
        r.setCardCountUsed_s(Long.toString(r.getCardCountUsed()));
        offset += len;
        len = 20;
        r.setCardLogicalId(trim(this.getCharString(b, offset, len)));//单程票逻辑ID
        offset += len;
        len = 20;
        r.setCardPhysicalId(trim(this.getCharString(b, offset, len)));//单程票物理ID
        offset += len;

        len = 1;
        r.setCardStatusId(this.getInt(b, offset));//单程票状态
        r.setCardStatusId_s(Long.toString(r.getCardStatusId()));
        offset += len;

        len = 2;
        r.setSaleFee(this.getShort(b, offset));//充值金额
        r.setSaleFee_s(TradeUtil.convertFenToYuan(r.getSaleFee()).toString());
        offset += len;

        len = 1;
        r.setCardMainId(this.getBcdString(b, offset, len));//票卡主类型
        offset += len;

        len = 1;
        r.setCardSubId(this.getBcdString(b, offset, len));//票卡子类型
        offset += len;

        result.setOffsetTotal(offset);
        return result;

    }

    private FileRecord00DetailResult getInfoForDevice(FileRecord50 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 2;
        r.setTrdType(this.getCharString(b, offset, len));//交易类型
        offset += len;
        
        len = 2;
        r.setRecordVer(this.getCharString(b, offset, len));//记录版本20180528 by hejj
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
        r.setSamLogicalId(trim(this.getCharString(b, offset, len)));//SAM卡逻辑卡号
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private void setTac(FileRecord50 r) {
        
        r.setTacLSKey(this.getTacLSKey(r.getCardLogicalId()));//票卡逻辑卡号作密钥离散值截取前16位
        r.setTacInRecord(r.getTac());//TAC码
        r.setTacDealAmout(r.getSaleFee());//交易金额
        //交易类型对照表设置校验的交易类型02:充值 09:复合消费 06:单次消费05:单程票发售
        r.setTacDealType(r.getCardMainId(),r.getTrdType(), r.getPurseOpType());
        r.setTacTerminalNo(r.getSamLogicalId());//SAM卡号作为终端编号,截取后12位
        r.setTacTerminalTradeSeq(r.getSamTradeSeq());;//SAM交易流水作为终端交易流水
        r.setTacDealTime(r.getSaleTime());//交易时间
        r.setTacTradeType(r.getTrdType());//原始交易类型
        r.setTacPurseOpType(r.getPurseOpType());//钱包操作类型
        r.setTacKeyGroupAndInd(r.getCardMainId());//根据票卡主类型设置密钥组、密钥索引
        
        r.setTacCardLogicalId(r.getCardLogicalId());//卡逻辑卡号
         r.setTacDealBalanceFee(r.getSaleFee());//交易余额
         r.setTacCardChargeSeq(0);//充值交易计数
    }

    @Override
    public void handleMessage(MessageBase msg) {
    }
    
    private FileRecord00DetailResult getInfoForOrderNo(FileRecord50 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result;
        if (this.isVersionOver11(r.getRecordVer())) {//11版本
            result = this.getInfoForOrderNoFile(r, b, offset);

        } else//10版本
        {
            result = this.getInfoForOrderNoDefault(r, b, offset);
        }
        return result;
    }

    private FileRecord00DetailResult getInfoForOrderNoFile(FileRecord50 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 14;
        r.setOrderNo(this.getCharString(b, offset, len));//订单号
        offset += len;

       

        result.setOffsetTotal(offset);
        return result;

    }

    private FileRecord00DetailResult getInfoForOrderNoDefault(FileRecord50 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 14;
        r.setOrderNo(this.DEF_ORDER_NO);//订单号
        offset += len;

        result.setOffsetTotal(offset);
        return result;

    }


}
