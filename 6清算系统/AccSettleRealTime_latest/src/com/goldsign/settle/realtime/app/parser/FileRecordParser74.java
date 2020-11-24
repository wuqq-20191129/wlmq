/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord54;
import com.goldsign.settle.realtime.app.vo.FileRecord74;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.app.vo.FileRecordQrCode54;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import static com.goldsign.settle.realtime.frame.parser.FileRecordParserBase.trim;
import com.goldsign.settle.realtime.frame.util.DateHelper;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class FileRecordParser74 extends FileRecordParserQrCode54{
    private static Logger logger = Logger.getLogger(FileRecordParser74.class.getName());

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecord74 r = new FileRecord74();
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
            result = this.getInfoForOctDiscountFile(r, b, offset);
            offset = result.getOffsetTotal();
            //获取二维码平台相关信息
            result = this.getInfoForQrCode(r, b, offset);
            offset = result.getOffsetTotal();
             //获取次票激活时间20191110
            result = this.getInfoForTctActivateDatetime(r, b, offset);
            offset = result.getOffsetTotal();
            

            //获取附加信息
            this.addCommonInfo(r, lineAdd);
            //获取TAC校验需要的字段数据
            //this.setTac(r);

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
    private FileRecord00DetailResult getInfoForTctActivateDatetime(FileRecord74 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result;
        if (this.isVersionOver12(r.getRecordVer())) {//12版本
            result = this.getInfoForTctActivateDatetimeFromFile(r, b, offset);

        } else//10/11版本
        {
            result = this.getInfoForTctActivateDatetimeFromDefault(r, b, offset);
        }
        return result;
    }
    private FileRecord00DetailResult getInfoForTctActivateDatetimeFromFile(FileRecord74 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 7;
        r.setTctActiveDatetime(this.getBcdString(b, offset, len));//次票激活时间
        r.setTctActiveDatetime_s(DateHelper.convertStandFormatDatetime(r.getTctActiveDatetime()));
        offset += len;
        
       

        result.setOffsetTotal(offset);
        return result;

    }
     private FileRecord00DetailResult getInfoForTctActivateDatetimeFromDefault(FileRecord74 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 7;
        r.setTctActiveDatetime(this.DEF_TCT_ACTIVATE_DATETIME);//次票激活时间
        r.setTctActiveDatetime_s(DateHelper.convertStandFormatDatetime(r.getTctActiveDatetime()));
        offset += len;
        
       

        result.setOffsetTotal(offset);
        return result;

    }
    
    protected FileRecord00DetailResult getInfoForDevice(FileRecord74 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 2;
        r.setTrdType(this.getCharString(b, offset, len));//交易类型 
        offset += len;
        //
        len = 2;
        r.setRecordVer(this.getCharString(b, offset, len));//记录版本
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
        r.setSamLogicalId(trim(this.getCharString(b, offset, len)));//SAM卡逻辑卡号
        offset += len;

        len = 4;

        r.setSamTradeSeq(this.getLong(b, offset));//本次交易SAM卡脱机交易流水号
        r.setSamTradeSeq_s(Long.toString(r.getSamTradeSeq()));
        offset += len;

        len = 7;
        r.setDealDatetime(this.getBcdString(b, offset, len));//日期时间
        r.setDealDatetime_s(DateHelper.convertStandFormatDatetime(r.getDealDatetime()));
        offset += len;

        len = 1;
        r.setCardMainId(this.getBcdString(b, offset, len));//票卡主类型
        offset += len;

        len = 1;
        r.setCardSubId(this.getBcdString(b, offset, len));//票卡子类型
        offset += len;

        len = 20;
        r.setCardLogicalId(trim(this.getCharString(b, offset, len)));//票卡逻辑卡号
        offset += len;

        len = 20;
        r.setCardPhysicalId(trim(this.getCharString(b, offset, len)));//票卡物理卡号
        offset += len;

        len = 1;
        r.setCardStatusId(this.getInt(b, offset));//卡状态代码
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }
    
}
