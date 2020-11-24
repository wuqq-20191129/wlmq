/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord12;
import com.goldsign.settle.realtime.app.vo.FileRecord18;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.handler.HandlerBase;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.util.DateHelper;
import java.util.HashMap;

/**
 *
 * @author hejj
 *12.18. ITM自动结存数据
 */
public class FileRecordParser18 extends FileRecordParserBase {

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecord18 r = new FileRecord18();

        FileRecord00DetailResult details;
        HashMap subRecords = new HashMap();
        char[] b;
        int offset = 0;
        try {
            b = this.getLineCharFromFile(line);

            //获取公共信息
            details = this.getInfoForCommon(r, b, offset);
            offset = details.getOffsetTotal();

            details = this.getInfoForSubWithCardAndType(r, b, offset, lineAdd);
            offset = details.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTrdType(), HandlerBase.TRAD_SUFIX[0], r.getDetail());

            details = this.getInfoForCommon_1(r, b, offset);
            offset = details.getOffsetTotal();

            //获取附加信息
            this.addCommonInfo(r, lineAdd);

            //获取分记录信息
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

    private FileRecord00DetailResult getInfoForCommon(FileRecord18 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 1;
        r.setTrdType(this.getBcdString(b, offset, len));//交易类型
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
        r.setAutoBalDatetimeBegein(this.getBcdString(b, offset, len));//自动结存周期开始时间
        r.setAutoBalDatetimeBegein_s(DateHelper.convertStandFormatDatetime(r.getAutoBalDatetimeBegein()));
        offset += len;

        len = 7;
        r.setAutoBalDatetimeEnd(this.getBcdString(b, offset, len));//自动结存周期结束时间
        r.setAutoBalDatetimeEnd_s(DateHelper.convertStandFormatDatetime(r.getAutoBalDatetimeEnd_s()));
        offset += len;

        len = 4;
        r.setSjtNumSaleTotal1(this.getLong(b, offset));//单程票箱1发售数量
        offset += len;

        len = 4;
        r.setSjtNumSaleTotal2(this.getLong(b, offset));//单程票箱2发售数量
        offset += len;

        len = 4;
        r.setSjtSaleFeeTotal1(this.getLong(b, offset));//单程票箱1发售总金额
        offset += len;

        len = 4;
        r.setSjtSaleFeeTotal2(this.getLong(b, offset));//单程票箱2发售总金额
        offset += len;


        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForCommon_1(FileRecord18 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 4;
        r.setChargeFeeTotal(this.getLong(b, offset));//充值总金额
        offset += len;

        len = 4;
        r.setSjtNumPutTotal1(this.getLong(b, offset));//单程票箱1存入总数量
        offset += len;

        len = 4;
        r.setSjtNumPutTotal2(this.getLong(b, offset));//单程票箱2存入总数量
        offset += len;

        len = 4;
        r.setSjtNumClearTotal1(this.getLong(b, offset));//单程票箱1清空数量
        offset += len;

        len = 4;
        r.setSjtNumClearTotal2(this.getLong(b, offset));//单程票箱2清空数量
        offset += len;

        len = 4;
        r.setSjtNumBalTotal1(this.getLong(b, offset));//单程票箱1结存数量
        offset += len;

        len = 4;
        r.setSjtNumBalTotal2(this.getLong(b, offset));//单程票箱2结存数量
        offset += len;

        len = 4;
        r.setSjtNumWasteTotal(this.getLong(b, offset));//单程票废票数量
        offset += len;

        len = 4;
        r.setTvmBalFeeTotal(this.getLong(b, offset));//ITM结存金额
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

}
