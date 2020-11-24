/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord63;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import static com.goldsign.settle.realtime.frame.parser.FileRecordParserBase.trim;
import com.goldsign.settle.realtime.frame.util.DateHelper;
import com.goldsign.settle.realtime.frame.util.TradeUtil;

/**
 *
 * @author hejj
 */
//预制票售票记录
public class FileRecordParser63 extends FileRecordParserBase{
    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecord63 r = new FileRecord63();
        FileRecord00DetailResult result;
        char[] b;
        int offset = 0;

        try {
            b = this.getLineCharFromFile(line);

            //获取设备、票卡相关信息
            result = this.getInfoForDeviceCard(r, b, offset);
            offset = result.getOffsetTotal();
            //获取交易相关信息
            result = this.getInfoForTrade(r, b, offset);
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

    private void setCheckInfo(FileRecord63 r) {
        r.setCheckDatetime(r.getSaleTime());//交易时间

        r.setCheckUniqueKey(this.getCheckUniqueKey(r));//唯一性校验主健

    }

    private String getCheckUniqueKey(FileRecord63 r) {
        //唯一性校验主健
        //票卡类型+业务流水+交易时间+余额+数量
        
         String key =r.getCardMainId()+r.getCardSubId()+
                     r.getWaterNoBusiness()+
                     r.getSaleTime()+r.getBalanceFee_s()+r.getSaleNum();
         
        //SAM卡逻辑卡号，重复判断由记录重复改为业务重复判断，业务重复判断覆盖记录重复
       // String key = r.getCardLogicalId();
        return key;

    }

    @Override
    public void handleMessage(MessageBase msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private FileRecord00DetailResult getInfoForDeviceCard(FileRecord63 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 2;
        r.setTrdType(this.getCharString(b, offset, len));//1交易类型
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

        len = 1;
        r.setCardMainId(this.getBcdString(b, offset, len));//5票卡类型主类型
        offset += len;

        len = 1;
        r.setCardSubId(this.getBcdString(b, offset, len));//5票卡类型子类型
        offset += len;

        len = 20;
        r.setCardLogicalId(trim(this.getCharString(b, offset, len)));//6票卡逻辑卡号
        offset += len;

        len = 20;
        r.setCardPhysicalId(trim(this.getCharString(b, offset, len)));//7票卡物理卡号
        offset += len;

        len = 1;
        r.setCardStatusId(this.getInt(b, offset));//8卡状态代码
        r.setCardStatusId_s(Long.toString(r.getCardStatusId()));
        offset += len;

        len = 12;
        r.setWaterNoBusiness(this.getCharString(b, offset, len));//9脱机业务流水
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForTrade(FileRecord63 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 16;
        r.setSamLogicalId(trim(this.getCharString(b, offset, len)));//10SAM卡逻辑卡号
        offset += len;

        len = 4;
        r.setSamTradeSeq(this.getLong(b, offset));//11SAM卡脱机交易流水号
        r.setSamTradeSeq_s(Long.toString(r.getSamTradeSeq()));
        offset += len;

        len = 1;
        r.setDepositType(this.getBcdString(b, offset, len));//12金额类型

        offset += len;

        len = 2;
        r.setDepositFee(this.getShort(b, offset));//13押金
        r.setDepositFee_s(TradeUtil.convertFenToYuan(r.getDepositFee()).toString());
        offset += len;
        
        
        len = 4;
        r.setBalanceFee(this.getLong(b, offset));//余额
        r.setBalanceFee_s(TradeUtil.convertFenToYuan(r.getBalanceFee()).toString());
        offset += len;
        
        len = 4;
        r.setSaleNum(this.getLong(b, offset));//发售数量
        r.setSaleNum_s(Long.toString(r.getSaleNum()));
        offset += len;
        

        len = 7;
        r.setSaleTime(this.getBcdString(b, offset, len));//14销售时间
        r.setSaleTime_s(DateHelper.convertStandFormatDatetime(r.getSaleTime()));
        offset += len;

        len = 4;
        r.setReceiptId(this.getCharString(b, offset, len));//15凭证ID
        offset += len;

        len = 6;
        r.setOperatorId(this.getCharString(b, offset, len));//16操作员代码
        offset += len;

        len = 1;
        r.setShiftId(this.getBcdString(b, offset, len));//17	BOM班次序号
        offset += len;

        len = 4;
        r.setAuxiFee(this.getLong(b, offset));//18手续费
        r.setAuxiFee_s(TradeUtil.convertFenToYuan(r.getAuxiFee()).toString());
        offset += len;

        len = 1;
        r.setCardAppFlag(this.getCharString(b, offset, len));//19卡应用标识
        offset += len;
        

        
        len = 4;
        r.setSaleFee(this.getLong(b, offset));//次票发售金额
        r.setSaleFee_s(TradeUtil.convertFenToYuan(r.getSaleFee()).toString());
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }
    
}
