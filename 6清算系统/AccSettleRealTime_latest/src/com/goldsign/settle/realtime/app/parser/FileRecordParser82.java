/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord81;
import com.goldsign.settle.realtime.app.vo.FileRecord81Detail;
import com.goldsign.settle.realtime.app.vo.FileRecord82;
import com.goldsign.settle.realtime.app.vo.FileRecord82Detail;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.handler.HandlerBase;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.HashMap;
import java.util.Vector;

/**
 *13.3. TVM寄存器数据
 * @author hejj
 */
public class FileRecordParser82 extends FileRecordParserBase {

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecord82 r = new FileRecord82();

        FileRecord00DetailResult result;
        HashMap subRecords = new HashMap();
        char[] b;
        int offset = 0;
        try {
            b = this.getLineCharFromFile(line);

            //获取公共信息
            result = this.getInfoForCommon(r, b, offset);
            offset = result.getOffsetTotal();
            //单程票发售明细
            result = this.getInfoForDetailSaleSjt(r, b, offset, lineAdd);
            offset = result.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTradeType(), HandlerBase.TRAD_SUFIX[0], r.getDetailSaleSjt());
            //硬币接收明细
            result = this.getInfoForDetailCoinReceive(r, b, offset, lineAdd);
            offset = result.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTradeType(), HandlerBase.TRAD_SUFIX[1], r.getDetailCoinReceive());
            //纸币接收明细
            result = this.getInfoForDetailNoteReceive(r, b, offset, lineAdd);
            offset = result.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTradeType(), HandlerBase.TRAD_SUFIX[2], r.getDetailNoteReceive());

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

    private FileRecord00DetailResult getInfoForCommon(FileRecord82 r, char[] b, int offset) throws Exception {
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
        r.setSaleNum(this.getLong(b, offset));//发售数量
        offset += len;

        len = 4;
        r.setSaleFee(this.getLong(b, offset));//发售金额
        offset += len;

        len = 4;
        r.setChargeNum(this.getLong(b, offset));//充值数量
        offset += len;

        len = 4;
        r.setChargeFee(this.getLong(b, offset));//充值金额
        offset += len;

        len = 4;
        r.setCoinReceiveNum(this.getLong(b, offset));//TVM累计接收硬币的总数
        offset += len;

        len = 4;
        r.setCoinReceiveFee(this.getLong(b, offset));//TVM累计接收硬币的总金额
        offset += len;

        len = 4;
        r.setNoteReceiveNum(this.getLong(b, offset));//TVM累计接收纸币的总数
        offset += len;

        len = 4;
        r.setNoteReceiveFee(this.getLong(b, offset));//TVM累计接收纸币的总金额
        offset += len;

        len = 4;
        r.setCoinChangeNum(this.getLong(b, offset));//TVM累计找零硬币的总数
        offset += len;

        len = 4;
        r.setCoinChangeFee(this.getLong(b, offset));//TVM累计找零硬币的总金额
        offset += len;
        
        len = 4;
        r.setNoteChangeNum(this.getLong(b, offset));//TVM累计找零纸币的总数
        offset += len;

        len = 4;
        r.setNoteChangeFee(this.getLong(b, offset));//TVM累计找零纸币的总金额
        offset += len;

        len = 4;
        r.setTkWasteNum(this.getLong(b, offset));//废票箱的车票数量
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForDetailSaleSjt(FileRecord82 r, char[] b, int offset, FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord82Detail fb;
        Vector<FileRecord82Detail> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        len = 4;
        int count = this.getLong(b, offset);//重复次数
        offset += len;
        totalNum = count;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord82Detail();
            this.addCommonInfo(fb, lineAdd);

            len = 4;
            fb.setCardMoney(this.getLong(b, offset));//面值
            offset += len;

            len = 4;
            fb.setSaleSjtNum(this.getLong(b, offset));//发售数量
            offset += len;

            len = 4;
            fb.setSaleSjtFee(this.getLong(b, offset));//发售金额
            offset += len;

            v.add(fb);
        }
        // result.setDetailsEntry(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);

        r.setDetailSaleSjt(v);

        return result;
    }

    private FileRecord00DetailResult getInfoForDetailCoinReceive(FileRecord82 r, char[] b, int offset, FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord82Detail fb;
        Vector<FileRecord82Detail> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        len = 4;
        int count = this.getLong(b, offset);//重复次数
        offset += len;
        totalNum = count;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord82Detail();
            this.addCommonInfo(fb, lineAdd);

            len = 4;
            fb.setCardMoney(this.getLong(b, offset));//硬币面值
            offset += len;

            len = 4;
            fb.setCoinReceiveNum(this.getLong(b, offset));//硬币接收数量
            offset += len;

            len = 4;
            fb.setCoinReceiveFee(this.getLong(b, offset));//硬币接收金额
            offset += len;

            v.add(fb);
        }
        //result.setDetails(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);

        r.setDetailCoinReceive(v);

        return result;
    }

    private FileRecord00DetailResult getInfoForDetailNoteReceive(FileRecord82 r, char[] b, int offset, FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord82Detail fb;
        Vector<FileRecord82Detail> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        len = 4;
        int count = this.getLong(b, offset);//重复次数
        offset += len;
        totalNum = count;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord82Detail();
            this.addCommonInfo(fb, lineAdd);

             len = 4;
            fb.setCardMoney(this.getLong(b, offset));//纸币面值
            offset += len;

            len = 4;
            fb.setNoteReceiveNum(this.getLong(b, offset));//纸币接收数量
            offset += len;

            len = 4;
            fb.setNoteReceiveFee(this.getLong(b, offset));//纸币接收金额
            offset += len;

            v.add(fb);
        }
        // result.setDetails(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);

        r.setDetailNoteReceive(v);

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
