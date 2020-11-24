/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord54;
import com.goldsign.settle.realtime.app.vo.FileRecord55;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.util.DateHelper;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;

/**
 *
 * @author hejj
 */
//票卡延期
public class FileRecordParser55 extends FileRecordParserBase{

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
       FileRecord55 r = new FileRecord55();
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
    private void setCheckInfo(FileRecord55 r) {
        
        
        r.setCheckDatetime(r.getOpDateTime());//交易时间
        r.setCheckUniqueKey(this.getCheckUniqueKey(r));//唯一性校验主健
       
        
    }
    private String getCheckUniqueKey(FileRecord55 r){
         //唯一性校验主健
         //票卡类型+SAM逻辑卡号+SAM卡交易序号+延期操作时间
         String key =r.getCardMainId()+r.getCardSubId()+
                     r.getSamLogicalId()+r.getSamTradeSeq()+
                     r.getOpDateTime();
         return key;
     }

    @Override
    public void handleMessage(MessageBase msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private FileRecord00DetailResult getInfoForDevice(FileRecord55 r, char[] b, int offset) throws Exception {
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

        len = 16;
        r.setSamLogicalId(trim(this.getCharString(b, offset, len)));//5SAM卡逻辑卡号
        offset += len;

        len = 4;
        r.setSamTradeSeq(this.getLong(b, offset));//6本次交易SAM卡脱机交易流水号  
        r.setSamTradeSeq_s(Long.toString(r.getSamTradeSeq()));
        offset += len;

        len = 1;
        r.setCardMainId(this.getBcdString(b, offset, len));//7票卡主类型
        offset += len;

        len = 1;
        r.setCardSubId(this.getBcdString(b, offset, len));//7票卡子类型
        offset += len;

        len = 20;
        r.setCardLogicalId(trim(this.getCharString(b, offset, len)));//8票卡逻辑卡号
        offset += len;

        len = 20;
        r.setCardPhysicalId(trim(this.getCharString(b, offset, len)));//9票卡物理卡号
        offset += len;

        len = 1;
        r.setCardStatusId(this.getInt(b, offset));//10卡状态代码
        r.setCardStatusId_s(Long.toString(r.getCardStatusId()));
        offset += len;




        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForTrade(FileRecord55 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 7;
        r.setOldExpireDatetime(this.getBcdString(b, offset,len));//11原有效时间
        r.setOldExpireDatetime_s(DateHelper.convertStandFormatDatetime(r.getOldExpireDatetime()));
        offset += len;

        len = 7;
        r.setNewExpireDatetime(this.getBcdString(b, offset,len));//12现有效时间
        r.setNewExpireDatetime_s(DateHelper.convertStandFormatDatetime(r.getNewExpireDatetime()));
        offset += len;
        
        len = 7;
        r.setOpDateTime(this.getBcdString(b, offset,len));//13更新时间
        r.setOpDateTime_s(DateHelper.convertStandFormatDatetime(r.getOpDateTime()));
        offset += len;
        
         len = 6;
        r.setOperatorId(this.getCharString(b, offset, len));//14操作员代码
        offset += len;

        len = 1;
        r.setShiftId(this.getBcdString(b, offset, len));//15BOM班次序号
        offset += len;
        
        len = 1;
        r.setCardAppFlag(this.getCharString(b, offset, len));//16卡应用标识
        offset += len;
 

        result.setOffsetTotal(offset);
        return result;

    }


    
    
}
