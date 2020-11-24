/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord02;
import com.goldsign.settle.realtime.app.vo.FileRecord50;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileWriter02 extends FileWriterBase {

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
        /*
        String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;
        FileRecord02 vo;
        String line;
        try {
            fos = new FileOutputStream(fileNameBcp);
            osr = new OutputStreamWriter(fos, FileWriterBase.CHARSET);
            bw = new BufferedWriter(osr);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecord02) records.get(i);
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
        FileRecord02 vo =(FileRecord02)vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//2线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//2站点代码
        sb.append(vo.getDevTypeId() + FileWriterBase.FIELD_DELIM);//2设备类型
        sb.append(vo.getDeviceId() + FileWriterBase.FIELD_DELIM);//2设备代码

        sb.append(vo.getNoteNo() + FileWriterBase.FIELD_DELIM);//3纸币箱编号
        sb.append(vo.getOperatorId() + FileWriterBase.FIELD_DELIM);//4操作员的员工号
        sb.append(vo.getPutTime() + FileWriterBase.FIELD_DELIM);//5纸币箱放入时间
        sb.append(vo.getPopTime() + FileWriterBase.FIELD_DELIM);//6纸币箱取出时间
        sb.append(vo.getWaterNoOp() + FileWriterBase.FIELD_DELIM);//7流水号
        /*
        sb.append(this.convertFenToYuan(vo.getNoteFee1()) + FileWriterBase.FIELD_DELIM);//纸币1单位金额
        sb.append(this.convertFenToYuan(vo.getNoteFee2()) + FileWriterBase.FIELD_DELIM);//纸币2单位金额
        sb.append(this.convertFenToYuan(vo.getNoteFee3()) + FileWriterBase.FIELD_DELIM);//纸币3单位金额
        sb.append(this.convertFenToYuan(vo.getNoteFee4()) + FileWriterBase.FIELD_DELIM);//纸币4单位金额
        sb.append(this.convertFenToYuan(vo.getNoteFee5()) + FileWriterBase.FIELD_DELIM);//纸币5单位金额
        sb.append(this.convertFenToYuan(vo.getNoteFee6()) + FileWriterBase.FIELD_DELIM);//纸币6单位金额
        sb.append(this.convertFenToYuan(vo.getNoteFee7()) + FileWriterBase.FIELD_DELIM);//纸币7单位金额
        sb.append(this.convertFenToYuan(vo.getNoteFee8()) + FileWriterBase.FIELD_DELIM);//纸币8单位金额
        sb.append(this.convertFenToYuan(vo.getNoteFee9()) + FileWriterBase.FIELD_DELIM);//纸币9单位金额
        sb.append(this.convertFenToYuan(vo.getNoteFee10()) + FileWriterBase.FIELD_DELIM);//纸币10单位金额
        sb.append(this.convertFenToYuan(vo.getNoteFee11()) + FileWriterBase.FIELD_DELIM);//纸币11单位金额
        sb.append(this.convertFenToYuan(vo.getNoteFee12()) + FileWriterBase.FIELD_DELIM);//纸币12单位金额
        sb.append(this.convertFenToYuan(vo.getNoteFee13()) + FileWriterBase.FIELD_DELIM);//纸币13单位金额
        sb.append(vo.getNoteNum1() + FileWriterBase.FIELD_DELIM);//纸币1数量
        sb.append(vo.getNoteNum2()+ FileWriterBase.FIELD_DELIM);//纸币2数量
        sb.append(vo.getNoteNum3()+ FileWriterBase.FIELD_DELIM);//纸币3数量
        sb.append(vo.getNoteNum4()+ FileWriterBase.FIELD_DELIM);//纸币4数量
        sb.append(vo.getNoteNum5()+ FileWriterBase.FIELD_DELIM);//纸币5数量
        sb.append(vo.getNoteNum6()+ FileWriterBase.FIELD_DELIM);//纸币6数量
        sb.append(vo.getNoteNum7()+ FileWriterBase.FIELD_DELIM);//纸币7数量
        sb.append(vo.getNoteNum8()+ FileWriterBase.FIELD_DELIM);//纸币8数量
        sb.append(vo.getNoteNum9()+ FileWriterBase.FIELD_DELIM);//纸币9数量
        sb.append(vo.getNoteNum10()+ FileWriterBase.FIELD_DELIM);//纸币10数量
        sb.append(vo.getNoteNum11()+ FileWriterBase.FIELD_DELIM);//纸币11数量
        sb.append(vo.getNoteNum12()+ FileWriterBase.FIELD_DELIM);//纸币12数量
        sb.append(vo.getNoteNum13()+ FileWriterBase.FIELD_DELIM);//纸币13数量
        */
        sb.append(this.convertFenToYuan(vo.getNoteFeetotal())+FileWriterBase.FIELD_DELIM);//钱箱金额
        this.addCommonToBuff(sb, vo);

        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
}
