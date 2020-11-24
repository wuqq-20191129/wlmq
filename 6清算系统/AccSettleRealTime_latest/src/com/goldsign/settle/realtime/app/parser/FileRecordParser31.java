/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;


import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;

import com.goldsign.settle.realtime.app.vo.FileRecord31;
import com.goldsign.settle.realtime.app.vo.FileRecord1Detail;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.handler.HandlerBase;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.util.DateHelper;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author hejj
 * AGM审计数据
 */
public class FileRecordParser31 extends FileRecordParserBase {

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecord31 r = new FileRecord31();

        FileRecord00DetailResult result;
        char[] b;
        int offset = 0;
        HashMap subRecords = new HashMap();
        try {
            b = this.getLineCharFromFile(line);

            //获取公共信息
            result = this.getInfoForCommon(r,b, offset);
            offset = result.getOffsetTotal();
            //明细
            result = this.getInfoForDetail(b, offset,lineAdd);
            offset = result.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTradeType(), HandlerBase.TRAD_SUFIX[0], result.getDetails());
            
            
            r.setTotalNum(result.getTotalNum());
            r.setTotalFee(result.getTotalFee());
            r.setDealDetail(result.getDetails());
            
           
            

            //获取附加信息
             this.addCommonInfo(r, lineAdd);
             //设置分记录
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

    private FileRecord00DetailResult getInfoForCommon( FileRecord31 r,char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 2;
        r.setTradeType(this.getCharString(b, offset, len));//交易类型
        offset += len;
        len = 4;
        r.setOpDate(this.getBcdString(b, offset, len));//操作日期
        r.setOpDate_s(DateHelper.convertStandFormatOnlyDate(r.getOpDate()));
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
        r.setCurDatetime(this.getBcdString(b, offset, len));//当前时间
        r.setCurDatetime_s(DateHelper.convertStandFormatDatetime(r.getCurDatetime()));
        offset += len;
        len = 1;
        r.setBoxPopNum(this.getInt(b, offset));//SJT票箱取出次数
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForDetail(char[] b, int offset,FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord1Detail fb;
        Vector<FileRecord1Detail> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;
        int totalFee = 0;

        len = 1;
        int count = this.getInt(b, offset);
        offset += len;
        for (int i = 0; i < count; i++) {
            fb = new FileRecord1Detail();
            this.addCommonInfo(fb, lineAdd);
            
            len = 2;
            fb.setTrdType(this.getCharString(b, offset, len));//交易类型
            offset += len;

            len = 2;
            fb.setPaymodeId(this.getCharString(b, offset, len));//支付类型
            offset += len;



            len = 1;
            fb.setCardMainId(this.getBcdString(b, offset, len));//票卡主类型
            offset += len;

            len = 1;
            fb.setCardSubId(this.getBcdString(b, offset, len));//票卡子类型
            offset += len;


            len = 4;
            fb.setDealNum(this.getLong(b, offset));//交易数量
            offset += len;
            totalNum += fb.getDealNum();


            len = 4;
            fb.setDealFee(this.getLong(b, offset));//交易金额
            offset += len;
            totalFee += fb.getDealFee();



            v.add(fb);
        }
        result.setDetails(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);
        result.setTotalFee(totalFee);
        return result;
    }

    @Override
    public boolean checkData(FileRecordBase frb) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
