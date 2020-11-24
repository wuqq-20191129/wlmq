/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord31;
import com.goldsign.settle.realtime.app.vo.FileRecord1Detail;
import com.goldsign.settle.realtime.app.vo.FileRecord81;
import com.goldsign.settle.realtime.app.vo.FileRecord81Detail;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.handler.HandlerBase;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.HashMap;
import java.util.Vector;

/**
 *13.2. AG寄存器数据
 * @author hejj
 */
public class FileRecordParser81 extends FileRecordParserBase {

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecord81 r = new FileRecord81();

        FileRecord00DetailResult result;
        HashMap subRecords = new HashMap();
        char[] b;
        int offset = 0;
        try {
            b = this.getLineCharFromFile(line);

            //获取公共信息
            result = this.getInfoForCommon(r, b, offset);
            offset = result.getOffsetTotal();
            //进站明细
            result = this.getInfoForDetailEntry(r, b, offset, lineAdd);
            offset = result.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTradeType(), HandlerBase.TRAD_SUFIX[0], r.getDetailEntry());
            //出站明细
            result = this.getInfoForDetailExit(r, b, offset, lineAdd);
            offset = result.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTradeType(), HandlerBase.TRAD_SUFIX[1], r.getDetailExit());
            //扣款明细
            result = this.getInfoForDetailConsume(r, b, offset, lineAdd);
            offset = result.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTradeType(), HandlerBase.TRAD_SUFIX[2], r.getDetailExit());

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

    private FileRecord00DetailResult getInfoForCommon(FileRecord81 r, char[] b, int offset) throws Exception {
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
        r.setEntryNum(this.getLong(b, offset));//进闸人数
        offset += len;

        len = 4;
        r.setExitNum(this.getLong(b, offset));//出闸人数
        offset += len;

        len = 4;
        r.setRefuseNum(this.getLong(b, offset));//拒绝人数
        offset += len;

        len = 4;
        r.setReclaimNum(this.getLong(b, offset));//车票回收数量
        offset += len;

        len = 4;
        r.setConsumeNum(this.getLong(b, offset));//扣款数量
        offset += len;

        len = 4;
        r.setConsumeFee(this.getLong(b, offset));//扣款金额
        offset += len;

        len = 4;
        r.setRefuseEntryNum(this.getLong(b, offset));//拒绝进闸人数
        offset += len;

        len = 4;
        r.setOvertimeCloseNum(this.getLong(b, offset));//超时关门次数
        offset += len;

        len = 4;
        r.setRefuseExitNum(this.getLong(b, offset));//拒绝出闸人数
        offset += len;

        len = 4;
        r.setAllowPassNum(this.getLong(b, offset));//允许通过人数
        offset += len;

        len = 4;
        r.setCheckedPassNum(this.getLong(b, offset));//正常验票后正常通过人数
        offset += len;

        len = 4;
        r.setIllegalPassNum(this.getLong(b, offset));//非法通过人数
        offset += len;

        len = 4;
        r.setCheckedOvertimeCloseNum(this.getLong(b, offset));//正常验票后无人通过，超时关门次数
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForDetailEntry(FileRecord81 r, char[] b, int offset, FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord81Detail fb;
        Vector<FileRecord81Detail> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        len = 4;
        int count = this.getLong(b, offset);//重复次数
        offset += len;
        totalNum = count;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord81Detail();
            this.addCommonInfo(fb, lineAdd);

            len = 1;
            fb.setCardMainId(this.getBcdString(b, offset, 1));//票卡主类型
            offset += len;

            len = 1;
            fb.setCardSubId(this.getBcdString(b, offset, 1));//票卡子类型
            offset += len;

            len = 4;
            fb.setEntryNum(this.getLong(b, offset));//进站人数
            offset += len;

            v.add(fb);
        }
        // result.setDetailsEntry(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);

        r.setDetailEntry(v);

        return result;
    }

    private FileRecord00DetailResult getInfoForDetailExit(FileRecord81 r, char[] b, int offset, FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord81Detail fb;
        Vector<FileRecord81Detail> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        len = 4;
        int count = this.getLong(b, offset);//重复次数
        offset += len;
        totalNum = count;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord81Detail();
            this.addCommonInfo(fb, lineAdd);

            len = 1;
            fb.setCardMainId(this.getBcdString(b, offset, 1));//票卡主类型
            offset += len;

            len = 1;
            fb.setCardSubId(this.getBcdString(b, offset, 1));//票卡子类型
            offset += len;

            len = 4;
            fb.setExitNum(this.getLong(b, offset));//出站人数
            offset += len;

            v.add(fb);
        }
        //result.setDetails(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);

        r.setDetailExit(v);

        return result;
    }

    private FileRecord00DetailResult getInfoForDetailConsume(FileRecord81 r, char[] b, int offset, FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord81Detail fb;
        Vector<FileRecord81Detail> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        len = 4;
        int count = this.getLong(b, offset);//重复次数
        offset += len;
        totalNum = count;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord81Detail();
            this.addCommonInfo(fb, lineAdd);

            len = 1;
            fb.setCardMainId(this.getBcdString(b, offset, 1));//票卡主类型
            offset += len;

            len = 1;
            fb.setCardSubId(this.getBcdString(b, offset, 1));//票卡子类型
            offset += len;

            len = 4;
            fb.setConsumeNum(this.getLong(b, offset));//扣款次数
            offset += len;

            len = 4;
            fb.setConsumeFee(this.getLong(b, offset));//扣款金额
            offset += len;

            v.add(fb);
        }
        // result.setDetails(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);

        r.setDetailConsume(v);

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
