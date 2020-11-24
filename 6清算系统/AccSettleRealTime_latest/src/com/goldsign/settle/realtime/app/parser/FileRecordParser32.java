/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord31;
import com.goldsign.settle.realtime.app.vo.FileRecord1Detail;
import com.goldsign.settle.realtime.app.vo.FileRecord32;
import com.goldsign.settle.realtime.app.vo.FileRecord2Detail;
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
 * TVM审计数据
 */
public class FileRecordParser32 extends FileRecordParserBase {

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecord32 r = new FileRecord32();
        HashMap subRecords = new HashMap();
        FileRecord00DetailResult result;
        char[] b;
        int offset = 0;
        try {
            b = this.getLineCharFromFile(line);

            //获取公共信息
            result = this.getInfoForCommon(r, b, offset);
            offset = result.getOffsetTotal();
            //获取操作信息
            result = this.getInfoForOp(r, b, offset);
            offset = result.getOffsetTotal();

            //明细
            result = this.getInfoForDetail(b, offset,lineAdd);
            offset = result.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTrdType(), HandlerBase.TRAD_SUFIX[0], result.getDetails());




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

    private FileRecord00DetailResult getInfoForCommon(FileRecord32 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 2;
        r.setTrdType(this.getCharString(b, offset, len));//交易类型
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



        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForOp(FileRecord32 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();



        int len = 2;
        r.setCardPutNum(this.getShort(b, offset));//当天Token Hopper补充总单程票数量
        offset += len;

        len = 2;
        r.setCoinPutNum(this.getShort(b, offset));//当天Coin Hopper 补充总硬币数量
        offset += len;

        len = 4;
        r.setCoinPutFee(this.getShort(b, offset));//当天Coin Hopper补充 总硬币金额
        offset += len;

        len = 2;
        r.setCardClearNum(this.getShort(b, offset));//当天Token Hopper清空的单程票总数量
        offset += len;

        len = 2;
        r.setCoinClearNum(this.getShort(b, offset));//当天Coin Hopper清空的硬币总数量
        offset += len;

        len = 4;
        r.setCoinClearFee(this.getShort(b, offset));//当天Coin Hopper清空的硬币总金额
        offset += len;

        len = 2;
        r.setCoinReclaimNum(this.getShort(b, offset));//当天Coin Box回收的硬币总数量
        offset += len;

        len = 4;
        r.setCoinReclaimFee(this.getShort(b, offset));//当天Coin Box回收的硬币总金额
        offset += len;

        len = 2;
        r.setNoteReclaimNum(this.getShort(b, offset));//当天Banknote Box回收的纸币总张数
        offset += len;

        len = 4;
        r.setNoteReclaimFee(this.getShort(b, offset));//当天Banknote Box回收的纸币总金额
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForDetail(char[] b, int offset,FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord2Detail fb;
        Vector<FileRecord2Detail> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;
        int totalFee = 0;

        len = 1;
        int count = this.getInt(b, offset);
        offset += len;
        for (int i = 0; i < count; i++) {
            fb = new FileRecord2Detail();
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
