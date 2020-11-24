/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord07;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.util.DateHelper;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;

/**
 *
 * @author hejj
 * 12.9. 闸机票箱数据
 */
public class FileRecordParser07 extends FileRecordParserBase{

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
         FileRecord07 r = new FileRecord07();

        FileRecord00DetailResult details;
        char[] b;
        int offset = 0;
        try {
            b = this.getLineCharFromFile(line);

            //获取公共信息
            details = this.getInfoForCommon(r, b, offset);
            offset = details.getOffsetTotal();
            //卡数量信息
            details = this.getInfoForOther(r, b, offset);
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
    
    private FileRecord00DetailResult getInfoForCommon(FileRecord07 r, char[] b, int offset) throws Exception {
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
        r.setBoxId(this.getCharString(b, offset, len));//票箱编号 
        offset += len;
        

        len = 7;
        r.setPopDatetime(this.getBcdString(b, offset, len));//票箱取出时间
        r.setPopDatetime_s(DateHelper.convertStandFormatDatetime(r.getPopDatetime()));
        offset += len;
        

        
        len = 2;
        r.setWaterNoOp(this.getShort(b, offset));//流水号 
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }
    
    
    private FileRecord00DetailResult getInfoForOther(FileRecord07 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        
        
        int len = 1;
        r.setCardMainId(this.getBcdString(b, offset,len));//票卡主类型
        offset += len;
        
        len = 1;
        r.setCardSubId(this.getBcdString(b, offset,len));//票卡子类型
        offset += len;
        
        
         len = 2;
        r.setNum(this.getShort(b, offset));//数量
        offset += len;
        
        
       
       
        result.setOffsetTotal(offset);
        return result;
    }

    @Override
    public boolean checkData(FileRecordBase frb) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
