/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord03;
import com.goldsign.settle.realtime.app.vo.FileRecord05;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Vector;

/**
 *
 * @author hejj TVM车票存入数据
 */
public class FileWriter05 extends FileWriterBase {

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
        /*
        String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;
        FileRecord05 vo;
        String line;
        try {
            fos = new FileOutputStream(fileNameBcp);
            osr = new OutputStreamWriter(fos, FileWriterBase.CHARSET);
            bw = new BufferedWriter(osr);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecord05) records.get(i);
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
    public  String getLine(FileRecordBase vob) {
        FileRecord05 vo =(FileRecord05)vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//2线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//2站点代码
        sb.append(vo.getDevTypeId() + FileWriterBase.FIELD_DELIM);//2设备类型
        sb.append(vo.getDeviceId() + FileWriterBase.FIELD_DELIM);//2设备代码

        sb.append(vo.getHopperId() + FileWriterBase.FIELD_DELIM);//3单程票Hopper
        sb.append(vo.getOperatorId() + FileWriterBase.FIELD_DELIM);//4操作员的员工号
        sb.append(vo.getBoxId() + FileWriterBase.FIELD_DELIM);//5票箱编号
        sb.append(vo.getPutDatetime() + FileWriterBase.FIELD_DELIM);//6存入时间
        sb.append(vo.getWaterNoOp() + FileWriterBase.FIELD_DELIM);//7流水号

        sb.append(vo.getCardMainId() + FileWriterBase.FIELD_DELIM);//8票卡主类型
        sb.append(vo.getCardSubId() + FileWriterBase.FIELD_DELIM);//8票卡子类型
        sb.append(vo.getNum() + FileWriterBase.FIELD_DELIM);//9数量



        this.addCommonToBuff(sb, vo);

        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
}
