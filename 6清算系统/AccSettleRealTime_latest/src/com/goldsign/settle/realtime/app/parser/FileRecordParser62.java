/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;

import com.goldsign.settle.realtime.app.vo.FileRecord62;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.util.DateHelper;

/**
 *
 * @author hejj
 */
public class FileRecordParser62 extends FileRecordParserBase{
    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecord62 r = new FileRecord62();
        FileRecord00DetailResult result;
        char[] b;
        int offset = 0;

        try {
            b = this.getLineCharFromFile(line);

            //获取设备相关信息
            result = this.getInfoForDevice(r, b, offset);
            offset = result.getOffsetTotal();
            //获取交易相关信息
            result = this.getInfoForOther(r, b, offset);
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
     private void setCheckInfo(FileRecord62 r) {
   
        r.setCheckIdentityType(r.getIdentityType());//证件类型
        r.setCheckIdentityId(r.getIdentityId());//证件号
        r.setCheckDatetime(r.getApplyDatetime());//交易时间
        r.setCheckUniqueKey(this.getCheckUniqueKey(r));//唯一性校验主健
       
        
    }
     private String getCheckUniqueKey(FileRecord62 r){
         //唯一性校验主健
         //证件类型+证件号码+申请时间
         String key =r.getCheckIdentityType()+r.getCheckIdentityId()+
                     r.getCheckDatetime();
         return key;
     }
         
     

    private FileRecord00DetailResult getInfoForDevice(FileRecord62 r, char[] b, int offset) throws Exception {
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
        ;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForOther(FileRecord62 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 30;
        r.setApplyName(trim(this.getCharString(b, offset, len)));//5姓名
        offset += len;

        len = 1;
        r.setApplySex(this.getCharString(b, offset, len));//6性别
        offset += len;

        len = 1;
        r.setIdentityType(this.getCharString(b, offset, len));//7证件类型
        offset += len;

        len = 18;
        r.setIdentityId(trim(this.getCharString(b, offset, len)));//8证件号码
        offset += len;

        len = 4;
        r.setExpiredDate(this.getBcdString(b, offset, len));//9证件有效期
        offset += len;

        len = 16;
        r.setTelNo(trim(this.getCharString(b, offset, len)));//10电话
        offset += len;

        len = 16;
        r.setFax(trim(this.getCharString(b, offset, len)));//11传真
        offset += len;

        len = 200;
        r.setAddress(trim(this.getCharString(b, offset, len)));//12通讯地址
        offset += len;


        len = 6;
        r.setOperatorId(this.getCharString(b, offset, len));//13操作员代码
        offset += len;


        len = 7;
        r.setApplyDatetime(this.getBcdString(b, offset, len));//14申请日期
        r.setApplyDatetime_s(DateHelper.convertStandFormatDatetime(r.getApplyDatetime()));
        offset += len;

        len = 1;
        r.setShiftId(this.getBcdString(b, offset, len));//15BOM班次序号
        offset += len;

        len = 1;
        r.setCardAppFlag(this.getCharString(b, offset, len));//16卡应用标识
        offset += len;
        
        len = 1;
        r.setCardMainId(this.getBcdString(b, offset, len));//17卡主类型
        offset += len;
        
        len = 1;
        r.setCardSubId(this.getBcdString(b, offset, len));//17卡子类型
        offset += len;
        
        len = 1;
        r.setApplyBusinessType(this.getCharString(b, offset, len));//18业务类型
        offset += len;
        
        len = 4;
        r.setReceiptId(trim(this.getCharString(b, offset, len)));//19凭证ID
        offset += len;
        
        len = 20;
        r.setCardLogicalId(trim(this.getCharString(b, offset, len)));//逻辑卡号 hejj 20180529
        offset += len;
        ;

        result.setOffsetTotal(offset);
        return result;
    }

    @Override
    public void handleMessage(MessageBase msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
