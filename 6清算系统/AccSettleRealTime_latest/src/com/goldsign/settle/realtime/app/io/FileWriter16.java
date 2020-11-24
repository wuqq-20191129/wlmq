/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord06;
import com.goldsign.settle.realtime.app.vo.FileRecord16;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileWriter16 extends FileWriterBase{
     @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);

    }

    @Override
    public String getLine(FileRecordBase vob) {
        FileRecord16 vo =(FileRecord16)vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//2线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//2站点代码
        sb.append(vo.getDevTypeId() + FileWriterBase.FIELD_DELIM);//2设备类型
        sb.append(vo.getDeviceId() + FileWriterBase.FIELD_DELIM);//2设备代码


        sb.append(vo.getOperatorId() + FileWriterBase.FIELD_DELIM);//3操作员的员工号
        sb.append(vo.getClearDatetime() + FileWriterBase.FIELD_DELIM);//4清空时间
        sb.append(vo.getWaterNoOp() + FileWriterBase.FIELD_DELIM);//5流水号

        sb.append(vo.getCardMainIdHopper1() + FileWriterBase.FIELD_DELIM);//票卡主类型
        sb.append(vo.getCardSubIdHopper1() + FileWriterBase.FIELD_DELIM);//票卡子类型
        sb.append(vo.getHopper1Num() + FileWriterBase.FIELD_DELIM);//数量
        sb.append(vo.getCardMainIdHopper2() + FileWriterBase.FIELD_DELIM);//票卡主类型
        sb.append(vo.getCardSubIdHopper2() + FileWriterBase.FIELD_DELIM);//票卡子类型
        sb.append(vo.getHopper2Num() + FileWriterBase.FIELD_DELIM);//数量



        this.addCommonToBuff(sb, vo);

        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    
}
