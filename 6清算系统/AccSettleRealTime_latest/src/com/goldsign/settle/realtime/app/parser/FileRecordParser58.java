/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord57;

import com.goldsign.settle.realtime.app.vo.FileRecord58;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.util.DateHelper;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;

/**
 *
 * @author hejj 非即时退款申请

 */
//非即时退款申请

public class FileRecordParser58 extends FileRecordParserBase {

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecord58 r = new FileRecord58();
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
     private void setCheckInfo(FileRecord58 r) {

        
        r.setCheckDatetime(r.getApplyDatetime());//交易时间
        r.setCheckUniqueKey(this.getCheckUniqueKey(r));//唯一性校验主健

       
        
    }
      private String getCheckUniqueKey(FileRecord58 r){
         //唯一性校验主健

         //票卡类型+SAM逻辑卡号+印刻号+申请时间
          //20151204唯一改为票卡类型+SAM逻辑卡号+印刻号+逻辑卡号
         String key =r.getCardMainId()+r.getCardSubId()+
                     r.getCardPrintId()+
                     r.getCardLogicalId();
                     //r.getApplyDatetime();
         return key;
     }

    @Override
    public void handleMessage(MessageBase msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private FileRecord00DetailResult getInfoForDevice(FileRecord58 r, char[] b, int offset) throws Exception {
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



        len = 1;
        r.setCardMainId(this.getBcdString(b, offset, len));//5票卡主类型

        offset += len;

        len = 1;
        r.setCardSubId(this.getBcdString(b, offset, len));//5票卡子类型

        offset += len;

        len = 20;
        r.setCardLogicalId(trim(this.getCharString(b, offset, len)));//6票卡逻辑卡号
        offset += len;

        len = 20;
        r.setCardPhysicalId(trim(this.getCharString(b, offset, len)));//7票卡物理卡号
        offset += len;

        len = 16;
        r.setCardPrintId(trim(this.getCharString(b, offset, len)));//8票卡印刻号

        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForTrade(FileRecord58 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 7;
        r.setApplyDatetime(this.getBcdString(b, offset, len));//9日期时间
        r.setApplyDatetime_s(DateHelper.convertStandFormatDatetime(r.getApplyDatetime()));
        offset += len;

        len = 4;
        r.setReceiptId(this.getCharString(b, offset, len));//10凭证ID
        offset += len;

        len = 6;
        r.setOperatorId(this.getCharString(b, offset, len));//11操作员代码

        offset += len;



        len = 30;
        r.setApplyName(trim(this.getCharString(b, offset, len)));//12乘客姓名
        offset += len;

        len = 12;
        r.setTelNo(trim(this.getCharString(b, offset, len)));//13乘客电话
        offset += len;
        len = 1;
        r.setIdentityType(this.getCharString(b, offset, len));//14证件类型
        offset += len;

        len = 18;
        r.setIdentityId(trim(this.getCharString(b, offset, len)));//15证件号码
        offset += len;


        len = 1;
        r.setIsBroken(this.getBcdString(b, offset, len));//16票卡是否折损
        offset += len;

        len = 1;
        r.setShiftId(this.getBcdString(b, offset, len));//17BOM班次序号
        offset += len;

        len = 1;
        r.setCardAppFlag(this.getCharString(b, offset, len));//18卡应用标识

        offset += len;



        result.setOffsetTotal(offset);
        return result;

    }


}
