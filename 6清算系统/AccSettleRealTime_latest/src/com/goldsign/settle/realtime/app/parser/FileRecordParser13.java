/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord12;
import com.goldsign.settle.realtime.app.vo.FileRecord13;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.util.DateHelper;

/**
 *12.14. TVM结账数据
 * @author hejj
 */
public class FileRecordParser13 extends FileRecordParserBase{

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
         FileRecord13 r = new FileRecord13();

        FileRecord00DetailResult details;
        char[] b;
        int offset = 0;
        try {
            b = this.getLineCharFromFile(line);

            //获取公共信息
            details = this.getInfoForCommon(r, b, offset);
            offset = details.getOffsetTotal();
            

            //获取附加信息
            this.addCommonInfo(r, lineAdd);


        } catch (Exception ex) {
            throw new RecordParseForFileException(ex.getMessage());
        }

        return r;
    }

    @Override
    public void handleMessage(MessageBase msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private FileRecord00DetailResult getInfoForCommon(FileRecord13 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 1;
        r.setTrdType(this.getBcdString(b, offset, len));//交易类型
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
        
        len = 7;
        r.setAutoBalDatetimeBegein(this.getBcdString(b, offset, len));//自动结存周期开始时间
        r.setAutoBalDatetimeBegein_s(DateHelper.convertStandFormatDatetime(r.getAutoBalDatetimeBegein()));
        offset += len;
        
        len = 7;
        r.setAutoBalDatetimeEnd(this.getBcdString(b, offset, len));//自动结存周期结束时间
        r.setAutoBalDatetimeEnd_s(DateHelper.convertStandFormatDatetime(r.getAutoBalDatetimeEnd()));
        offset += len;
        
        len = 4;
        r.setSjtSaleFeeTotal1(this.getLong(b, offset));//单程票箱1发售总金额
        offset += len;
        
        len = 4;
        r.setSjtSaleFeeTotal2(this.getLong(b, offset));//单程票箱2发售总金额
        offset += len;
        
        len = 4;
        r.setChargeFeeTotal(this.getLong(b, offset));//充值总金额
        offset += len;
        
        len = 4;
        r.setNoteRecvFeeTotal(this.getLong(b, offset));//纸币接收总金额
        offset += len;
        
        len = 4;
        r.setCoinRecvFeeTotal(this.getLong(b, offset));//硬币接收总金额
        offset += len;
        
        len = 4;
        r.setNoteRecvFeeRepTotal(this.getLong(b, offset));//更换纸币接收钱箱累计金额
        offset += len;
        
        len = 4;
        r.setCoinRecvFeeRepTotal(this.getLong(b, offset));//更换硬币接收钱箱累计金额
        offset += len;
        
        len = 4;
        r.setCoinBalFeeTotal(this.getLong(b, offset));//硬币钱箱结存金额
        offset += len;
        
        len = 4;
        r.setNoteBalFeeTotal(this.getLong(b, offset));//纸币接收钱箱结存金额
        offset += len;
        
        len = 4;
        r.setNoteChgFeePutTotal1(this.getLong(b, offset));//存入纸币找零箱1总金额
        offset += len;
        
        len = 4;
        r.setNoteChgFeePutTotal2(this.getLong(b, offset));//存入纸币找零箱2总金额
        offset += len;
        
        len = 4;
        r.setCoinChgFeePutTotal1(this.getLong(b, offset));//存入硬币找零HOPPER 1总金额
        offset += len;
        
        len = 4;
        r.setCoinChgFeePutTotal2(this.getLong(b, offset));//存入硬币找零HOPPER 2总金额
        offset += len;
        
        len = 4;
        r.setCoinChgFeePopTotal1(this.getLong(b, offset));//硬币找零HOPPER 1找零总金额
        offset += len;
        
        len = 4;
        r.setCoinChgFeePopTotal2(this.getLong(b, offset));//硬币找零HOPPER 2找零总金额
        offset += len;
        
        len = 4;
        r.setNoteChgFeePopTotal1(this.getLong(b, offset));//纸币找零箱1找零总金额
        offset += len;
        
        len = 4;
        r.setNoteChgFeePopTotal2(this.getLong(b, offset));//纸币找零箱2找零总金额
        offset += len;
        
        len = 4;
        r.setCoinChgFeeBalTotal1(this.getLong(b, offset));//硬币找零HOPPER 1结存总金额
        offset += len;
        
        len = 4;
        r.setCoinChgFeeBalTotal2(this.getLong(b, offset));//硬币找零HOPPER 2结存总金额
        offset += len;
        
        len = 4;
        r.setNoteChgFeeBalTotal1(this.getLong(b, offset));//纸币找零钱箱1结存总金额
        offset += len;
        
        len = 4;
        r.setNoteChgFeeBalTotal2(this.getLong(b, offset));//纸币找零钱箱2结存总金额
        offset += len;
        
        len = 4;
        r.setCoinChgFeeClearTotal1(this.getLong(b, offset));//硬币找零HOPPER 1清空总金额
        offset += len;
        
        len = 4;
        r.setCoinChgFeeClearTotal2(this.getLong(b, offset));//硬币找零HOPPER 2清空总金额
        offset += len;
        
        len = 4;
        r.setCoinChgFeeBalSumTotal1(this.getLong(b, offset));//清币结账后，硬币找零HOPPER1结存累计金额
        offset += len;
        
        len = 4;
        r.setCoinChgFeeBalSumTotal2(this.getLong(b, offset));//清币结账后，硬币找零HOPPER2结存累计金额
        offset += len;
        
        len = 4;
        r.setNoteReclaimChgNum(this.getLong(b, offset));//已更换纸币找零回收箱的回收数量
        offset += len;
        
        len = 4;
        r.setNoteReclaimBalNum(this.getLong(b, offset));//纸币找零回收箱回收结存数量
        offset += len;
        
        len = 4;
        r.setSjtNumPutTotal1(this.getLong(b, offset));//单程票箱1存入总数量
        offset += len;
        
        len = 4;
        r.setSjtNumPutTotal2(this.getLong(b, offset));//单程票箱2存入总数量
        offset += len;
        
        len = 4;
        r.setSjtNumSaleTotal1(this.getLong(b, offset));//单程票箱1发售数量
        offset += len;
        
        
        len = 4;
        r.setSjtNumSaleTotal2(this.getLong(b, offset));//单程票箱2发售数量
        offset += len;
        
        len = 4;
        r.setSjtNumClearTotal1(this.getLong(b, offset));//单程票箱1清空数量
        offset += len;
        
        len = 4;
        r.setSjtNumClearTotal2(this.getLong(b, offset));//单程票箱2清空数量
        offset += len;
        
        len = 4;
        r.setSjtNumBalTotal1(this.getLong(b, offset));//单程票箱1结存数量
        offset += len;
        
        len = 4;
        r.setSjtNumBalTotal2(this.getLong(b, offset));//单程票箱2结存数量
        offset += len;
        
        len = 4;
        r.setSjtNumWasteTotal(this.getLong(b, offset));//单程票废票数量
        offset += len;
        
        len = 4;
        r.setTvmBalFeeTotal(this.getLong(b, offset));//TVM结存金额
        offset += len;
        
        
        

        result.setOffsetTotal(offset);
        return result;
    }
    
}
