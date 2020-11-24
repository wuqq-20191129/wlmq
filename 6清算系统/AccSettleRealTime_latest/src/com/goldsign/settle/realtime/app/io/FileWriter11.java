/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord11;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileWriter11 extends FileWriterBase{

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
    }

    @Override
    public String getLine(FileRecordBase vob) {
        FileRecord11 vo = (FileRecord11) vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//2线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//2站点代码
        sb.append(vo.getDevTypeId() + FileWriterBase.FIELD_DELIM);//2设备类型
        sb.append(vo.getDeviceId() + FileWriterBase.FIELD_DELIM);//2设备代码


        sb.append(vo.getNoteBoxId() + FileWriterBase.FIELD_DELIM);//纸币回收箱编号
        sb.append(vo.getOperatorId() + FileWriterBase.FIELD_DELIM);//操作员的员工号

        sb.append(vo.getPopDatetime() + FileWriterBase.FIELD_DELIM);//取出时间
        sb.append(vo.getWaterNoOp() + FileWriterBase.FIELD_DELIM);//流水号
       /* 
        sb.append(this.convertFenToYuan(vo.getNoteFee1()) + FileWriterBase.FIELD_DELIM);//纸币找零箱1纸币面值
        sb.append(vo.getNoteNumReclaim1() + FileWriterBase.FIELD_DELIM);//纸币找零箱1回收数量
        sb.append(this.convertFenToYuan(vo.getNoteFee2()) + FileWriterBase.FIELD_DELIM);//纸币找零箱2纸币面值
        sb.append(vo.getNoteNumReclaim2() + FileWriterBase.FIELD_DELIM);//纸币找零箱2回收数量
        sb.append(this.convertFenToYuan(vo.getNoteFee3()) + FileWriterBase.FIELD_DELIM);//纸币找零箱3纸币面值
        sb.append(vo.getNoteNumReclaim3() + FileWriterBase.FIELD_DELIM);//纸币找零箱3回收数量
        sb.append(this.convertFenToYuan(vo.getNoteFee4()) + FileWriterBase.FIELD_DELIM);//纸币找零箱4纸币面值
        sb.append(vo.getNoteNumReclaim4() + FileWriterBase.FIELD_DELIM);//纸币找零箱4回收数量
     */   




        this.addCommonToBuff(sb, vo);

        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    
}
