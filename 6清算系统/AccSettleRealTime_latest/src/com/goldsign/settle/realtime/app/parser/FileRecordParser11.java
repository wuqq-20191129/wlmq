/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord11;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.handler.HandlerBase;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.util.DateHelper;
import java.util.HashMap;

/**
 *
 * @author hejj
 */
public class FileRecordParser11 extends FileRecordParserBase{

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecord11 r = new FileRecord11();

        FileRecord00DetailResult details;
        HashMap subRecords = new HashMap();
        char[] b;
        int offset = 0;
        try {
            b = this.getLineCharFromFile(line);

            //获取公共信息
            details = this.getInfoForCommon(r, b, offset);
            offset = details.getOffsetTotal();

            details = this.getInfoForSubWithType(r, b, offset, lineAdd);
            offset = details.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTrdType(), HandlerBase.TRAD_SUFIX[0], r.getDetail()); 
            
            
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
    
    private FileRecord00DetailResult getInfoForCommon(FileRecord11 r, char[] b, int offset) throws Exception {
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



        len = 16;
        r.setNoteBoxId(this.getCharString(b, offset, len));//纸币回收箱编号
        offset += len;

        len = 6;
        r.setOperatorId(this.getCharString(b, offset, len));//操作员的员工号
        offset += len;




        len = 7;
        r.setPopDatetime(this.getBcdString(b, offset, len));//取出时间
        r.setPopDatetime_s(DateHelper.convertStandFormatDatetime(r.getPopDatetime()));
        offset += len;


        len = 2;
        r.setWaterNoOp(this.getShort(b, offset));//流水号
        offset += len;
        
        
/*
        len = 2;
        r.setNoteFee1(this.getShort(b, offset));//纸币找零箱1纸币面值
        offset += len;

        len = 2;
        r.setNoteNumReclaim1(this.getShort(b, offset));//纸币找零箱1回收数量
        offset += len;
        
        len = 2;
        r.setNoteFee2(this.getShort(b, offset));//纸币找零箱2纸币面值
        offset += len;
        
        len = 2;
        r.setNoteNumReclaim2(this.getShort(b, offset));//纸币找零箱2回收数量
        offset += len;
        
        len = 2;
        r.setNoteFee3(this.getShort(b, offset));//纸币找零箱3纸币面值
        offset += len;
        
        len = 2;
        r.setNoteNumReclaim3(this.getShort(b, offset));//纸币找零箱3回收数量
        offset += len;
        
        len = 2;
        r.setNoteFee4(this.getShort(b, offset));//纸币找零箱4纸币面值
        offset += len;
        
        len = 2;
        r.setNoteNumReclaim4(this.getShort(b, offset));//纸币找零箱4回收数量
        offset += len;
    */    
      
        

        result.setOffsetTotal(offset);
        return result;
    }
    
}
