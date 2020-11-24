/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord53;

import com.goldsign.settle.realtime.app.vo.FileRecord59;
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
//加锁/解锁
public class FileRecordParser59 extends FileRecordParserBase {

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecord59 r = new FileRecord59();
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
    
     private void setCheckInfo(FileRecord59 r) {
        
        
        r.setCheckDatetime(r.getLockDatetime());//交易时间
        r.setCheckUniqueKey(this.getCheckUniqueKey(r));//唯一性校验主健
       
        
    }
     private String getCheckUniqueKey(FileRecord59 r){
         //唯一性校验主健
         //票卡类型+SAM逻辑卡号+SAM卡交易序号+锁卡时间
         String key =r.getCardMainId()+r.getCardSubId()+
                     r.getSamLogicalId()+r.getSamTradeSeq()+
                     r.getLockDatetime();
         return key;
     }

    @Override
    public void handleMessage(MessageBase msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private FileRecord00DetailResult getInfoForDevice(FileRecord59 r, char[] b, int offset) throws Exception {
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

    private FileRecord00DetailResult getInfoForTrade(FileRecord59 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();


        int len = 1;
        r.setLockFlag(this.getCharString(b, offset,len));//11加解锁标志
        offset += len;
        
         len = 7;
        r.setLockDatetime(this.getBcdString(b, offset, len));//12日期时间
        r.setLockDatetime_s(DateHelper.convertStandFormatDatetime(r.getLockDatetime()));
        offset += len;
        
         len = 6;
        r.setOperatorId(this.getCharString(b, offset, len));//13操作员代码
        offset += len;

        len = 1;
        r.setShiftId(this.getBcdString(b, offset, len));//14BOM班次序号
        offset += len;
        
        len = 1;
        r.setCardAppFlag(this.getCharString(b, offset, len));//15卡应用标识
        offset += len;
        
         len = 1;
        r.setCardAppMode(this.getCharString(b, offset, len));//15卡应用模式
        offset += len;
        
        

        result.setOffsetTotal(offset);
        return result;

    }


}
