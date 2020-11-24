/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00;
import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord02;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.handler.HandlerBase;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.util.DateHelper;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.HashMap;

/**
 *12.3. TVM纸币箱数据
 * @author hejj
 */
public class FileRecordParser02 extends FileRecordParserBase {

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecord02 r = new FileRecord02();

        FileRecord00DetailResult details;
        HashMap subRecords = new HashMap();
        char[] b;
        int offset = 0;
        try {
            b = this.getLineCharFromFile(line);

            //获取公共信息
            details = this.getInfoForCommon(r, b, offset);
            offset = details.getOffsetTotal();

            //纸币单位金额
            //   details = this.getInfoForNoteAmountUnit(r, b, offset);
            //  offset = details.getOffsetTotal();
            //纸币单位数量
            //  details = this.getInfoForNoteNum(r, b, offset);
            //  offset = details.getOffsetTotal(); 
            details = this.getInfoForSub(r, b, offset, lineAdd);
            offset = details.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTrdType(), HandlerBase.TRAD_SUFIX[0], r.getDetail());
            
            //汇总
            details = this.getInfoForNoteAmountTotal(r, b, offset);
            offset = details.getOffsetTotal();

            //获取附加信息
            this.addCommonInfo(r, lineAdd);
            //r.setBalanceWaterNo(lineAdd.getBalanceWaterNo());
            //r.setFileName(lineAdd.getFileName());
              //获取分记录信息
            r.setSubRecords(subRecords);

        } catch (Exception ex) {
            throw new RecordParseForFileException(ex.getMessage());
        }

        return r;

    }

    @Override
    public void handleMessage(MessageBase msg) {
    }

    private FileRecord00DetailResult getInfoForCommon(FileRecord02 r, char[] b, int offset) throws Exception {
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
        //modify by hejj 20160622 long->char(16)
        len = 16;
        r.setNoteNo(this.getCharString(b, offset, len));//纸币箱编号
        offset += len;

        len = 6;
        r.setOperatorId(this.getCharString(b, offset, len));//操作员工号
        offset += len;

        len = 7;
        r.setPutTime(this.getBcdString(b, offset, len));//纸币箱放入时间
        r.setPutTime_s(DateHelper.convertStandFormatDatetime(r.getPutTime()));
        offset += len;

        len = 7;
        r.setPopTime(this.getBcdString(b, offset, len));//纸币箱取出时间
        r.setPopTime_s(DateHelper.convertStandFormatDatetime(r.getPopTime()));
        offset += len;

        len = 2;
        r.setWaterNoOp(this.getShort(b, offset));//流水号 
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForNoteAmountUnit(FileRecord02 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 2;
        r.setNoteFee1(this.getShort(b, offset));//纸币1单位金额
        offset += len;

        len = 2;
        r.setNoteFee2(this.getShort(b, offset));//纸币2单位金额
        offset += len;

        len = 2;
        r.setNoteFee3(this.getShort(b, offset));//纸币3单位金额
        offset += len;

        len = 2;
        r.setNoteFee4(this.getShort(b, offset));//纸币4单位金额
        offset += len;

        len = 2;
        r.setNoteFee5(this.getShort(b, offset));//纸币5单位金额
        offset += len;

        len = 2;
        r.setNoteFee6(this.getShort(b, offset));//纸币6单位金额
        offset += len;

        len = 2;
        r.setNoteFee7(this.getShort(b, offset));//纸币7单位金额
        offset += len;

        len = 2;
        r.setNoteFee8(this.getShort(b, offset));//纸币8单位金额
        offset += len;

        len = 2;
        r.setNoteFee9(this.getShort(b, offset));//纸币9单位金额
        offset += len;

        len = 2;
        r.setNoteFee10(this.getShort(b, offset));//纸币10单位金额
        offset += len;

        len = 2;
        r.setNoteFee11(this.getShort(b, offset));//纸币11单位金额
        offset += len;

        len = 2;
        r.setNoteFee12(this.getShort(b, offset));//纸币12单位金额
        offset += len;

        len = 2;
        r.setNoteFee13(this.getShort(b, offset));//纸币13单位金额
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForNoteNum(FileRecord02 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 2;
        r.setNoteNum1(this.getShort(b, offset));//纸币1单位数量
        offset += len;

        len = 2;
        r.setNoteNum2(this.getShort(b, offset));//纸币2单位数量
        offset += len;

        len = 2;
        r.setNoteNum3(this.getShort(b, offset));//纸币3单位数量
        offset += len;

        len = 2;
        r.setNoteNum4(this.getShort(b, offset));//纸币4单位数量
        offset += len;

        len = 2;
        r.setNoteNum5(this.getShort(b, offset));//纸币5单位数量
        offset += len;

        len = 2;
        r.setNoteNum6(this.getShort(b, offset));//纸币6单位数量
        offset += len;

        len = 2;
        r.setNoteNum7(this.getShort(b, offset));//纸币7单位数量
        offset += len;

        len = 2;
        r.setNoteNum8(this.getShort(b, offset));//纸币8单位数量
        offset += len;

        len = 2;
        r.setNoteNum9(this.getShort(b, offset));//纸币9单位数量
        offset += len;

        len = 2;
        r.setNoteNum10(this.getShort(b, offset));//纸币10单位数量
        offset += len;

        len = 2;
        r.setNoteNum11(this.getShort(b, offset));//纸币11单位数量
        offset += len;

        len = 2;
        r.setNoteNum12(this.getShort(b, offset));//纸币12单位数量
        offset += len;

        len = 2;
        r.setNoteNum13(this.getShort(b, offset));//纸币13单位数量
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForNoteAmountTotal(FileRecord02 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 4;
        r.setNoteFeetotal(this.getLong(b, offset));//钱箱金额
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    @Override
    public boolean checkData(FileRecordBase frb) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
