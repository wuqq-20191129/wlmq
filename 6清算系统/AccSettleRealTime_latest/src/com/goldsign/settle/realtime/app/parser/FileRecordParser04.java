/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord01;
import com.goldsign.settle.realtime.app.vo.FileRecord04;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.handler.HandlerBase;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.HashMap;

/**
 *12.5. TVM硬币清空数据
 * @author hejj
 */
public class FileRecordParser04 extends FileRecordParserBase{

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecord04 r = new FileRecord04();

        FileRecord00DetailResult details;
         HashMap subRecords = new HashMap();
        char[] b;
        int offset = 0;
        try {
            b = this.getLineCharFromFile(line);

            //获取公共信息
            details = this.getInfoForCommon(r, b, offset);
            offset = details.getOffsetTotal();
            //硬币单位金额
           // details = this.getInfoForCoinFeeUnit(r, b, offset);
           // offset = details.getOffsetTotal();
            //硬币单位数量
            //details = this.getInfoForCoinNum(r, b, offset);
           // offset = details.getOffsetTotal();  
            details = this.getInfoForSub(r, b, offset,lineAdd);
            offset = details.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTrdType(), HandlerBase.TRAD_SUFIX[0], r.getDetail());

            
            //汇总
            details = this.getInfoForCoinFeeTotal(r, b, offset);
            offset = details.getOffsetTotal();
            
            
            //获取附加信息
            this.addCommonInfo(r, lineAdd);
             //获取分记录信息
            r.setSubRecords(subRecords);


        } catch (Exception ex) {
            throw new RecordParseForFileException(ex.getMessage());
        }

        return r;
    }

    @Override
    public void handleMessage(MessageBase msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private FileRecord00DetailResult getInfoForCommon(FileRecord04 r, char[] b, int offset) throws Exception {
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
        
        len = 6;
        r.setOperatorId(this.getCharString(b, offset, len));//操作员工号
        offset += len;
         //modify by hejj 20160622 long->char(16)
        len = 16;
        r.setCoinBoxId(this.getCharString(b, offset,len));//硬币箱编号
        offset += len;

        len = 7;
        r.setClearDatetime(this.getBcdString(b, offset, len));//硬币箱清空时间
        offset += len;
        
        
        len = 2;
        r.setWaterNoOp(this.getShort(b, offset));//流水号 
        offset += len;
        
        len = 2;
        r.setHopper1UnitFee(this.getShort(b, offset));//硬币Hopper 1单位 
        offset += len;
        
        len = 2;
        r.setHopper1UnitNum(this.getShort(b, offset));//硬币Hopper 1数量 
        offset += len;
        
        len = 2;
        r.setHopper2UnitFee(this.getShort(b, offset));//硬币Hopper 2单位 
        offset += len;
        
        len = 2;
        r.setHopper2UnitNum(this.getShort(b, offset));//硬币Hopper 2数量 
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }
    
    
    private FileRecord00DetailResult getInfoForCoinFeeUnit(FileRecord04 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 2;
        r.setCoinFee1(this.getShort(b, offset));//硬币1单位金额
        offset += len;
        
        len = 2;
        r.setCoinFee2(this.getShort(b, offset));//硬币2单位金额
        offset += len;
        
        len = 2;
        r.setCoinFee3(this.getShort(b, offset));//硬币3单位金额
        offset += len;
        
        len = 2;
        r.setCoinFee4(this.getShort(b, offset));//硬币4单位金额
        offset += len;
        
        len = 2;
        r.setCoinFee5(this.getShort(b, offset));//硬币5单位金额
        offset += len;
        
        len = 2;
        r.setCoinFee6(this.getShort(b, offset));//硬币6单位金额
        offset += len;
        
        len = 2;
        r.setCoinFee7(this.getShort(b, offset));//硬币7单位金额
        offset += len;
        
        len = 2;
        r.setCoinFee8(this.getShort(b, offset));//硬币8单位金额
        offset += len;
        
        
      
        

        result.setOffsetTotal(offset);
        return result;
    }
    
    private FileRecord00DetailResult getInfoForCoinNum(FileRecord04 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 2;
        r.setCoinNum1(this.getShort(b, offset));//硬币1单位数量
        offset += len;
        
        len = 2;
        r.setCoinNum2(this.getShort(b, offset));//硬币2单位数量
        offset += len;
        
        len = 2;
        r.setCoinNum3(this.getShort(b, offset));//硬币3单位数量
        offset += len;
        
        len = 2;
        r.setCoinNum4(this.getShort(b, offset));//硬币4单位数量
        offset += len;
        
        len = 2;
        r.setCoinNum5(this.getShort(b, offset));//硬币5单位数量
        offset += len;
        
        len = 2;
        r.setCoinNum6(this.getShort(b, offset));//硬币6单位数量
        offset += len;
        
        len = 2;
        r.setCoinNum7(this.getShort(b, offset));//硬币7单位数量
        offset += len;
        
        len = 2;
        r.setCoinNum8(this.getShort(b, offset));//硬币8单位数量
        offset += len;
        
        
        

        result.setOffsetTotal(offset);
        return result;
    }
    private FileRecord00DetailResult getInfoForCoinFeeTotal(FileRecord04 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 4;
        r.setCoinFeeTotal(this.getLong(b, offset));//钱箱金额
        offset += len;
        
       
        result.setOffsetTotal(offset);
        return result;
    }

    @Override
    public boolean checkData(FileRecordBase frb) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
