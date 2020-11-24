/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;

import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.app.vo.FileRecordNetPaidOrd;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;

/**
 *
 * @author hejj
 */
public class FileRecordParserNetPaidORD extends FileRecordParserBase {

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecordNetPaidOrd r = new FileRecordNetPaidOrd();
        FileRecord00DetailResult result;
        char[] b;
        int offset = 0;

        try {
            b = this.getLineCharFromFile(line);

            //获取相关信息
            result = this.getInfo(r, b, offset);
            offset = result.getOffsetTotal();


            //获取附加信息
            this.addCommonInfo_1(r, lineAdd);


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

    private void setCheckInfo(FileRecordNetPaidOrd r) {
        r.setCheckBalanceFee(0);//余额
        r.setCheckDealFee(r.getDealFee());//交易金额
        r.setCheckChargeFee(r.getDealFee());//充值金额

        r.setCheckUniqueKey(this.getCheckUniqueKey(r));//唯一性校验主健


    }
    private String getCheckUniqueKey(FileRecordNetPaidOrd r) {
        //唯一性校验主健
        
        String key = r.getOrderNo();
        return key;

    }

    private FileRecord00DetailResult getInfo(FileRecordNetPaidOrd r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 20;
        r.setOrderNo(this.getCharString(b, offset, len));//订单编号
        offset += len;

        len = 2;
        r.setOrderType(this.getCharString(b, offset, len));//订单类型
        offset += len;

        len = 2;
        r.setStatus(this.getCharString(b, offset, len));//订单状态
        offset += len;

        len = 7;
        r.setGenerateDatetime(this.getBcdString(b, offset, len));//订单生成时间
        offset += len;

        len = 7;
        r.setPaidDatetime(this.getBcdString(b, offset, len));//订单支付时间
        offset += len;

        len = 1;
        r.setCardMainId(this.getBcdString(b, offset, len));//票卡主类型
        offset += len;

        len = 1;
        r.setCardSubId(this.getBcdString(b, offset, len));//票卡子类型
        offset += len;

        len = 1;
        r.setLineIdStart(this.getBcdString(b, offset, len));//起点站线路代码
        offset += len;

        len = 1;
        r.setStationIdStart(this.getBcdString(b, offset, len));//起点站站点代码
        offset += len;

        len = 1;
        r.setLineIdEnd(this.getBcdString(b, offset, len));//终点站线路代码
        offset += len;

        len = 1;
        r.setStationIdEnd(this.getBcdString(b, offset, len));//终点站站点代码
        offset += len;



        len = 4;
        r.setDealUnitFee(this.getLong(b, offset));//单价
        offset += len;

        len = 4;
        r.setDealNum(this.getLong(b, offset));//订单张数
        offset += len;

        len = 4;
        r.setDealFee(this.getLong(b, offset));//订单总金额
        offset += len;

        len = 1;
        r.setOrderTypeBuy(this.getCharString(b, offset, len));//订单购票类型
        offset += len;

        len = 2;
        r.setPaidChannelType(this.getCharString(b, offset, len));//支付渠道类型
        offset += len;

        len = 4;
        r.setPaidChannelCode(this.getCharString(b, offset, len));//支付渠道代码
        offset += len;

        len = 11;
        r.setMobileNo(this.getCharString(b, offset, len));//手机号码
        offset += len;


        result.setOffsetTotal(offset);
        return result;
    }

    @Override
    public void handleMessage(MessageBase msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
