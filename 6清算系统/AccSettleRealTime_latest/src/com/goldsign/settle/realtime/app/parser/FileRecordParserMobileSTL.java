/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.app.vo.FileRecordMobileSTL;
import com.goldsign.settle.realtime.app.vo.FileRecordSTL;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;

/**
 *
 * @author hejj
 */
public class FileRecordParserMobileSTL extends FileRecordParserBase {

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecordMobileSTL r = new FileRecordMobileSTL();

        FileRecord00DetailResult results;

        char[] b;
        int offset = 0;
        try {
            b = this.getLineCharFromFile(line);

            //获取公共信息
            results = this.getInfoForCommon(r, b, offset);
            offset = results.getOffsetTotal();

            //获取附加信息
            this.addCommonInfo(r, lineAdd);


        } catch (Exception ex) {
            throw new RecordParseForFileException(ex.getMessage());
        }

        return r;
    }

    private FileRecord00DetailResult getInfoForCommon(FileRecordMobileSTL r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();



        int len = 2;
        r.setLineId(this.getCharString(b, offset, len));//2线路代码
        offset += len;

        len = 2;
        r.setStationId(this.getCharString(b, offset, len));//2站点代码
        offset += len;





        len = 4;
        r.setChargeFee(this.getLong(b, offset));//充值总金额
        offset += len;

        len = 4;
        r.setChargeNum(this.getLong(b, offset));//充值总数量
        offset += len;
        
        len = 2;
        r.setPaidChannelTypeCharge(this.getCharString(b, offset, len));//充值通道类型
        offset += len;
        
        
        len = 4;
        r.setPaidChannelCodeCharge(this.getCharString(b, offset, len));//充值通道代码
        offset += len;
        



        len = 4;
        r.setReturnNum(this.getLong(b, offset));//11即时退款总数量
        offset += len;

        len = 4;
        r.setReturnFee(this.getLong(b, offset));//12即时退款总金额
        offset += len;
        
         len = 2;
        r.setPaidChannelTypeReturn(this.getCharString(b, offset, len));//退款通道类型
        offset += len;
        
        
        len = 4;
        r.setPaidChannelCodeReturn(this.getCharString(b, offset, len));//退款通道代码
        offset += len;
        

        len = 4;
        r.setSaleNum(this.getLong(b, offset));//11发售总数量
        offset += len;

        len = 4;
        r.setSaleFee(this.getLong(b, offset));//12发售总金额
        offset += len;

        len = 4;
        r.setLockNum(this.getLong(b, offset));//11锁卡总数量
        offset += len;

        len = 4;
        r.setUnlockNum(this.getLong(b, offset));//11解锁总数量
        offset += len;
        
        len = 4;
        r.setSaleSjtNum(this.getLong(b, offset));//12购单程票总数量
        offset += len;
        
        len = 4;
        r.setSaleSjtFee(this.getLong(b, offset));//13购单程票总金额
        offset += len;
        
         len = 2;
        r.setPaidChannelTypeSaleSjt(this.getCharString(b, offset, len));//通道类型
        offset += len;
        
        
        len = 4;
        r.setPaidChannelCodeSaleSjt(this.getCharString(b, offset, len));//单程票购票通道代码
        offset += len;
        
       //modify by hejj 20191031
        len = 2;
        r.setPaidChannelTypeSale(this.getCharString(b, offset, len));//发售通道类型
        offset += len;
        
        
        len = 4;
        r.setPaidChannelCodeSale(this.getCharString(b, offset, len));//发售通道代码
        offset += len;
        

        len = 4;
        r.setChargeTctTNum(this.getLong(b, offset));//充次总次数
        offset += len;
        
        len = 4;
        r.setChargeTctNum(this.getLong(b, offset));//充次总数量
        offset += len;
        
        len = 4;
        r.setChargeTctFee(this.getLong(b, offset));//购次总金额
        offset += len;
        
        len = 2;
        r.setPaidChannelTypeBuy(this.getCharString(b, offset, len));//购次通道类型
        offset += len;
        
        
        len = 4;
        r.setPaidChannelCodeBuy(this.getCharString(b, offset, len));//购次通道代码
        offset += len;






        result.setOffsetTotal(offset);
        return result;
    }

    @Override
    public void handleMessage(MessageBase msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean checkData(FileRecordBase frb) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
