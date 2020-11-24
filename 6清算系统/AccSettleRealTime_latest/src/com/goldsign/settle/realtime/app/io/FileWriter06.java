/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord05;
import com.goldsign.settle.realtime.app.vo.FileRecord06;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Vector;

/**
 *
 * @author hejj TVM车票清空数据
 */
public class FileWriter06 extends FileWriterBase {

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
        /*
        String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;
        FileRecord06 vo;
        String line;
        try {
            fos = new FileOutputStream(fileNameBcp);
            osr = new OutputStreamWriter(fos, FileWriterBase.CHARSET);
            bw = new BufferedWriter(osr);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecord06) records.get(i);
                line = this.getLine(vo);
                bw.write(line);
                bw.flush();
            }

        } catch (Exception ex) {
            throw new FileWriteException(ex.getMessage());

        } finally {
            this.closeFile(fos, osr, bw);
        }
        */
    }

    @Override
    public String getLine(FileRecordBase vob) {
        FileRecord06 vo =(FileRecord06)vob;
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
