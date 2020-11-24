/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord09;

import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileWriter09 extends FileWriterBase {

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
    }

    @Override
    public String getLine(FileRecordBase vob) {
        FileRecord09 vo = (FileRecord09) vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//2线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//2站点代码
        sb.append(vo.getDevTypeId() + FileWriterBase.FIELD_DELIM);//2设备类型
        sb.append(vo.getDeviceId() + FileWriterBase.FIELD_DELIM);//2设备代码

        sb.append(vo.getNoteBoxType() + FileWriterBase.FIELD_DELIM);//纸币箱类型
        sb.append(vo.getNoteBoxId() + FileWriterBase.FIELD_DELIM);//纸币箱编号
        sb.append(vo.getOperatorId() + FileWriterBase.FIELD_DELIM);//操作员的员工号
        sb.append(vo.getPutDatetime() + FileWriterBase.FIELD_DELIM);//存入时间
        sb.append(vo.getWaterNoOp() + FileWriterBase.FIELD_DELIM);//流水号
        sb.append(this.convertFenToYuan(vo.getNoteFee()) + FileWriterBase.FIELD_DELIM);//纸币面值
        sb.append(vo.getNoteNum() + FileWriterBase.FIELD_DELIM);//纸币数量
        sb.append(this.convertFenToYuan(vo.getNoteFeeTotal()) + FileWriterBase.FIELD_DELIM);//纸币金额




        this.addCommonToBuff(sb, vo);

        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
}
