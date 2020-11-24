/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord81Detail;
import com.goldsign.settle.realtime.app.vo.FileRecord82;
import com.goldsign.settle.realtime.app.vo.FileRecord82Detail;
import com.goldsign.settle.realtime.app.vo.FileRecord83Detail;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.handler.HandlerBase;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.HashMap;
import java.util.Vector;

/**
 *13.4. BOM寄存器数据
 * @author hejj
 */
public class FileRecordParser83 extends FileRecordParserBase{
    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecord83 r = new FileRecord83();

        FileRecord00DetailResult result;
        HashMap subRecords = new HashMap();
        char[] b;
        int offset = 0;
        try {
            b = this.getLineCharFromFile(line);

            //获取公共信息
            result = this.getInfoForCommon(r, b, offset);
            offset = result.getOffsetTotal();
            //储值票发售明细
            result = this.getInfoForDetailSaleSvt(r, b, offset, lineAdd);
            offset = result.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTradeType(), HandlerBase.TRAD_SUFIX[0], r.getDetailSaleSvt());
            //充值明细
            result = this.getInfoForDetailCharge(r, b, offset, lineAdd);
            offset = result.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTradeType(), HandlerBase.TRAD_SUFIX[1], r.getDetailCharge());
            //更新明细
            result = this.getInfoForDetailUpdate(r, b, offset, lineAdd);
            offset = result.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTradeType(), HandlerBase.TRAD_SUFIX[2], r.getDetailUpdate());
             //退款明细
            result = this.getInfoForDetailReturn(r, b, offset, lineAdd);
            offset = result.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTradeType(), HandlerBase.TRAD_SUFIX[3], r.getDetailReturn());
             //非即时明细
            result = this.getInfoForDetailReturnNon(r, b, offset, lineAdd);
            offset = result.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTradeType(), HandlerBase.TRAD_SUFIX[4], r.getDetailReturnNon());

            r.setTotalNum(result.getTotalNum());
            r.setDealDetail(result.getDetails());

            //获取附加信息
            this.addCommonInfo(r, lineAdd);
            //  r.setBalanceWaterNo(lineAdd.getBalanceWaterNo());
            // r.setFileName(lineAdd.getFileName());
            //获取分记录信息
            r.setSubRecords(subRecords);

        } catch (Exception ex) {
            throw new RecordParseForFileException(ex.getMessage());
        }

        return r;
    }

    private FileRecord00DetailResult getInfoForCommon(FileRecord83 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 2;
        r.setTradeType(this.getCharString(b, offset, len));//交易类型
        offset += len;
        len = 7;
        r.setCommuDatetime(this.getBcdString(b, offset, len));//通讯消息时间
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
        r.setGenDatetime(this.getBcdString(b, offset, len));//产生日期/时间
        offset += len;

        len = 4;
        r.setOverflowNum(this.getLong(b, offset));//已溢出次数
        offset += len;

        len = 4;
        r.setSaleSjtNum(this.getLong(b, offset));//单程票发售数量
        offset += len;

        len = 4;
        r.setSaleSjtFee(this.getLong(b, offset));//单程票发售金额
        offset += len;

        len = 4;
        r.setTkExiFreeNum(this.getLong(b, offset));//免费出站票发售数量
        offset += len;

        len = 4;
        r.setTkExitChargeNum(this.getLong(b, offset));//付费出站票发售数量
        offset += len;

        len = 4;
        r.setTkExitChargeFee(this.getLong(b, offset));//付费出站票发售金额
        offset += len;

        

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForDetailSaleSvt(FileRecord83 r, char[] b, int offset, FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord83Detail fb;
        Vector<FileRecord83Detail> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        len = 4;
        int count = this.getLong(b, offset);//重复次数
        offset += len;
        totalNum = count;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord83Detail();
            this.addCommonInfo(fb, lineAdd);

            len = 1;
            fb.setCardMainId(this.getBcdString(b, offset,len));//票卡主类型
            offset += len;
            
            len = 1;
            fb.setCardSubId(this.getBcdString(b, offset,len));//票卡子类型
            offset += len;

            len = 4;
            fb.setCommonNum(this.getLong(b, offset));//发售数量
            offset += len;

            len = 4;
            fb.setCommonFee(this.getLong(b, offset));//发售金额
            offset += len;

            v.add(fb);
        }
        // result.setDetailsEntry(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);

        r.setDetailSaleSvt(v);

        return result;
    }
    private FileRecord00DetailResult getInfoForDetailCharge(FileRecord83 r, char[] b, int offset, FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord83Detail fb;
        Vector<FileRecord83Detail> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        len = 4;
        int count = this.getLong(b, offset);//重复次数
        offset += len;
        totalNum = count;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord83Detail();
            this.addCommonInfo(fb, lineAdd);

            len = 1;
            fb.setCardMainId(this.getBcdString(b, offset,len));//票卡主类型
            offset += len;
            
            len = 1;
            fb.setCardSubId(this.getBcdString(b, offset,len));//票卡子类型
            offset += len;

            len = 4;
            fb.setCommonNum(this.getLong(b, offset));//发售数量
            offset += len;

            len = 4;
            fb.setCommonFee(this.getLong(b, offset));//发售金额
            offset += len;

            v.add(fb);
        }
        // result.setDetailsEntry(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);

        r.setDetailCharge(v);

        return result;
    }
    private FileRecord00DetailResult getInfoForDetailUpdate(FileRecord83 r, char[] b, int offset, FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord83Detail fb;
        Vector<FileRecord83Detail> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        len = 4;
        int count = this.getLong(b, offset);//重复次数
        offset += len;
        totalNum = count;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord83Detail();
            this.addCommonInfo(fb, lineAdd);

            len = 1;
            fb.setCardMainId(this.getBcdString(b, offset,len));//票卡主类型
            offset += len;
            
            len = 1;
            fb.setCardSubId(this.getBcdString(b, offset,len));//票卡子类型
            offset += len;
            
            len = 1;
            fb.setUpdateReasonId(this.getBcdString(b, offset,len));//更新原因
            offset += len;

            len = 4;
            fb.setCommonNum(this.getLong(b, offset));//数量
            offset += len;

            len = 4;
            fb.setCommonFee(this.getLong(b, offset));//金额
            offset += len;

            v.add(fb);
        }
        // result.setDetailsEntry(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);

        r.setDetailUpdate(v);

        return result;
    }
    
    private FileRecord00DetailResult getInfoForDetailReturn(FileRecord83 r, char[] b, int offset, FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord83Detail fb;
        Vector<FileRecord83Detail> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        len = 4;
        int count = this.getLong(b, offset);//重复次数
        offset += len;
        totalNum = count;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord83Detail();
            this.addCommonInfo(fb, lineAdd);

            len = 1;
            fb.setCardMainId(this.getBcdString(b, offset,len));//票卡主类型
            offset += len;
            
            len = 1;
            fb.setCardSubId(this.getBcdString(b, offset,len));//票卡子类型
            offset += len;

            len = 4;
            fb.setCommonNum(this.getLong(b, offset));//数量
            offset += len;

            len = 4;
            fb.setCommonFee(this.getLong(b, offset));//金额
            offset += len;

            v.add(fb);
        }
        // result.setDetailsEntry(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);

        r.setDetailReturn(v);

        return result;
    }
    private FileRecord00DetailResult getInfoForDetailReturnNon(FileRecord83 r, char[] b, int offset, FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord83Detail fb;
        Vector<FileRecord83Detail> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        len = 4;
        int count = this.getLong(b, offset);//重复次数
        offset += len;
        totalNum = count;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord83Detail();
            this.addCommonInfo(fb, lineAdd);

            len = 1;
            fb.setCardMainId(this.getBcdString(b, offset,len));//票卡主类型
            offset += len;
            
            len = 1;
            fb.setCardSubId(this.getBcdString(b, offset,len));//票卡子类型
            offset += len;

            len = 4;
            fb.setCommonNum(this.getLong(b, offset));//数量
            offset += len;

            len = 4;
            fb.setCommonFee(this.getLong(b, offset));//金额
            offset += len;

            v.add(fb);
        }
        // result.setDetailsEntry(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);

        r.setDetailReturnNon(v);

        return result;
    }

   

    

    private FileRecord00DetailResult getInfoForDetail(char[] b, int offset, FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord81Detail fb;
        Vector<FileRecord81Detail> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        len = 1;
        int count = this.getInt(b, offset);//寄存器子条目数量
        offset += len;
        totalNum = count;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord81Detail();
            this.addCommonInfo(fb, lineAdd);

            len = 1;
            fb.setDataCode(this.getInt(b, offset));//代码
            offset += len;

            len = 4;
            fb.setDataValue(this.getLong(b, offset));//值
            offset += len;

            v.add(fb);
        }
        result.setDetails(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);

        return result;
    }

    @Override
    public void handleMessage(MessageBase msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean checkData(FileRecordBase frb) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
