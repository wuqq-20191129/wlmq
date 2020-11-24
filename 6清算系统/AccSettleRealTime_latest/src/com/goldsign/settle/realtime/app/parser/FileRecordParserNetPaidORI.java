/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;

import com.goldsign.settle.realtime.app.vo.FileRecordNetPaidOrdImp;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;

/**
 *
 * @author hejj
 */
public class FileRecordParserNetPaidORI extends FileRecordParserBase{
    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecordNetPaidOrdImp r = new FileRecordNetPaidOrdImp();
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

    private void setCheckInfo(FileRecordNetPaidOrdImp r) {
        r.setCheckBalanceFee(0);//余额
        r.setCheckDealFee(r.getDealFee());//交易金额
        r.setCheckChargeFee(r.getDealFee());//充值金额

        r.setCheckUniqueKey(this.getCheckUniqueKey(r));//唯一性校验主健


    }
    private String getCheckUniqueKey(FileRecordNetPaidOrdImp r) {
        //唯一性校验主健
        
        String key = r.getOrderNo();
        return key;

    }

    private FileRecord00DetailResult getInfo(FileRecordNetPaidOrdImp r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 20;
        r.setOrderNo(this.getCharString(b, offset, len));//订单编号
        offset += len;

        len = 2;
        r.setOrderType(this.getCharString(b, offset, len));//订单类型
        offset += len;
        
        len = 7;
        r.setFinishDatetime(this.getBcdString(b, offset, len));//订单生成时间
        offset += len;
        
        len = 2;
        r.setLineId(this.getCharString(b, offset, len));//线路
        offset += len;
        
        len = 2;
        r.setStationId(this.getCharString(b, offset, len));//车站
        offset += len;
        
        len = 2;
        r.setDevTypeId(this.getCharString(b, offset, len));//设备类型
        offset += len;
        
        len = 3;
        r.setDeviceId(this.getCharString(b, offset, len));//设备代码
        offset += len;
        
        len = 2;
        r.setStatus(this.getCharString(b, offset, len));//订单状态
        offset += len;
        
        len = 1;
        r.setCardMainId(this.getBcdString(b, offset, len));//票卡主类型
        offset += len;

        len = 1;
        r.setCardSubId(this.getBcdString(b, offset, len));//票卡子类型
        offset += len;
        
        len = 4;
        r.setDealNum(this.getLong(b, offset));//出票数量
        offset += len;
        
        len = 4;
        r.setDealNumNot(this.getLong(b, offset));//未出票数量
        offset += len;
        
        len = 4;
        r.setDealFee(this.getLong(b, offset));//出票金额
        offset += len;
        
        len = 4;
        r.setRefundFee(this.getLong(b, offset));//退款金额
        offset += len;
        
        len = 4;
        r.setAuxiFee(this.getLong(b, offset));//退款手续费
        offset += len;
        
        
        



        result.setOffsetTotal(offset);
        return result;
    }

    @Override
    public void handleMessage(MessageBase msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
