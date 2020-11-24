/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord09;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.util.DateHelper;

/**
 *12.10. TVM纸币找零存入数据
 * @author hejj
 */
public class FileRecordParser09 extends FileRecordParserBase {

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecord09 r = new FileRecord09();

        FileRecord00DetailResult details;
        char[] b;
        int offset = 0;
        try {
            b = this.getLineCharFromFile(line);

            //获取公共信息
            details = this.getInfoForCommon(r, b, offset);
            offset = details.getOffsetTotal();


            //获取附加信息
            this.addCommonInfo(r, lineAdd);


        } catch (Exception ex) {
            throw new RecordParseForFileException(ex.getMessage());
        }

        return r;
    }

    private FileRecord00DetailResult getInfoForCommon(FileRecord09 r, char[] b, int offset) throws Exception {
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

        len = 1;
        r.setNoteBoxType(this.getInt(b, offset));//纸币箱类型
        offset += len;

        len = 16;
        r.setNoteBoxId(this.getCharString(b, offset, len));//纸币箱编号
        offset += len;

        len = 6;
        r.setOperatorId(this.getCharString(b, offset, len));//操作员的员工号
        offset += len;


        len = 7;
        r.setPutDatetime(this.getBcdString(b, offset, len));//存入时间
        r.setPutDatetime_s(DateHelper.convertStandFormatDatetime(r.getPutDatetime()));
        offset += len;

        len = 2;
        r.setWaterNoOp(this.getShort(b, offset));//流水号
        offset += len;

        len = 2;
        r.setNoteFee(this.getShort(b, offset));//纸币面值
        offset += len;

        len = 2;
        r.setNoteNum(this.getShort(b, offset));//纸币数量
        offset += len;

        len = 4;
        r.setNoteFeeTotal(this.getLong(b, offset));//纸币金额
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    @Override
    public void handleMessage(MessageBase msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
