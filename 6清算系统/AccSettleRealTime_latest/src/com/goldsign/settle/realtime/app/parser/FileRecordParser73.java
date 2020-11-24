/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord73;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;

import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;


/**
 *
 * @author hejj
 */
public class FileRecordParser73 extends FileRecordParserQrCode53{
    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecord73 r = new FileRecord73();
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
            
             //获取一卡通优惠相关信息
            result = this.getInfoForOctDiscount(r, b, offset);
            offset = result.getOffsetTotal();
             //获取二维码相关信息
            result = this.getInfoForQrCode(r, b, offset);
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

    protected FileRecord00DetailResult getInfoForDevice(FileRecord73 r, char[] b, int offset) throws Exception {
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
        r.setSamTradeSeq(this.getLong(b, offset));//6SAM卡脱机流水号
        r.setSamTradeSeq_s(Long.toString(r.getSamTradeSeq()));
        offset += len;

        len = 1;
        r.setCardMainId(this.getBcdString(b, offset, len));//7主类型代码
        offset += len;

        len = 1;
        r.setCardSubId(this.getBcdString(b, offset, len));//7子类型代码
        offset += len;

        len = 20;
        r.setCardLogicalId(trim(this.getCharString(b, offset, len)));//8票卡逻辑卡号
        offset += len;

        len = 20;
        r.setCardPhysicalId(trim(this.getCharString(b, offset, len)));//9票卡物理卡号
        offset += len;



        result.setOffsetTotal(offset);
        return result;
    }
    
     private void setCheckInfo(FileRecord73 r) {
                
        r.setCheckDatetime(r.getEntryDatetime());//交易时间
        r.setCheckUniqueKey(this.getCheckUniqueKey(r));//唯一性校验主健
       
        
    }
     private String getCheckUniqueKey(FileRecord73 r){
         //唯一性校验主健
         //票卡类型+SAM逻辑卡号+SAM卡交易序号+进站时间+票卡逻辑卡号
         String key =r.getCardMainId()+r.getCardSubId()+
                     r.getSamLogicalId()+r.getSamTradeSeq()+
                     r.getEntryDatetime()+r.getCardLogicalId();
         return key;
     }
    
}
