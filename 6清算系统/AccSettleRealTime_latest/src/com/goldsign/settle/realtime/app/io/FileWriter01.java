/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord01;
import com.goldsign.settle.realtime.app.vo.FileRecord02;
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
 * TVM硬币箱数据
 */

public class FileWriter01 extends FileWriterBase{

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
        /*
        String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;
        FileRecord01 vo;
        String line;
        try {
            fos = new FileOutputStream(fileNameBcp);
            osr = new OutputStreamWriter(fos, FileWriterBase.CHARSET);
            bw = new BufferedWriter(osr);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecord01) records.get(i);
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
        FileRecord01 vo =(FileRecord01)vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getWaterNo()+ FileWriterBase.FIELD_DELIM);//流水号
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//站点代码
        sb.append(vo.getDevTypeId() + FileWriterBase.FIELD_DELIM);//设备类型
        sb.append(vo.getDeviceId() + FileWriterBase.FIELD_DELIM);//设备代码

        sb.append(vo.getCoinBoxId() + FileWriterBase.FIELD_DELIM);//硬币箱编号
        sb.append(vo.getOperatorId() + FileWriterBase.FIELD_DELIM);//操作员的员工号
        sb.append(vo.getPutDatetime() + FileWriterBase.FIELD_DELIM);//硬币箱放入时间
        sb.append(vo.getPopDatetime() + FileWriterBase.FIELD_DELIM);//硬币箱取出时间
        sb.append(vo.getWaterNoOp() + FileWriterBase.FIELD_DELIM);//流水号
        /*
        sb.append(this.convertFenToYuan(vo.getCoinFee1()) + FileWriterBase.FIELD_DELIM);//硬币1单位金额
        sb.append(this.convertFenToYuan(vo.getCoinFee2()) + FileWriterBase.FIELD_DELIM);//硬币2单位金额
        sb.append(this.convertFenToYuan(vo.getCoinFee3()) + FileWriterBase.FIELD_DELIM);//硬币3单位金额
        sb.append(this.convertFenToYuan(vo.getCoinFee4()) + FileWriterBase.FIELD_DELIM);//硬币4单位金额
        sb.append(this.convertFenToYuan(vo.getCoinFee5()) + FileWriterBase.FIELD_DELIM);//硬币5单位金额
        sb.append(this.convertFenToYuan(vo.getCoinFee6()) + FileWriterBase.FIELD_DELIM);//硬币6单位金额
        sb.append(this.convertFenToYuan(vo.getCoinFee7()) + FileWriterBase.FIELD_DELIM);//硬币7单位金额
        sb.append(this.convertFenToYuan(vo.getCoinFee8()) + FileWriterBase.FIELD_DELIM);//硬币8单位金额
       
        sb.append(vo.getCoinNum1() + FileWriterBase.FIELD_DELIM);//硬币1数量
        sb.append(vo.getCoinNum2()+ FileWriterBase.FIELD_DELIM);//硬币2数量
        sb.append(vo.getCoinNum3()+ FileWriterBase.FIELD_DELIM);//硬币3数量
        sb.append(vo.getCoinNum4()+ FileWriterBase.FIELD_DELIM);//硬币4数量
        sb.append(vo.getCoinNum5()+ FileWriterBase.FIELD_DELIM);//硬币5数量
        sb.append(vo.getCoinNum6()+ FileWriterBase.FIELD_DELIM);//硬币6数量
        sb.append(vo.getCoinNum7()+ FileWriterBase.FIELD_DELIM);//硬币7数量
        sb.append(vo.getCoinNum8()+ FileWriterBase.FIELD_DELIM);//硬币8数量
     */   
        sb.append(this.convertFenToYuan(vo.getCoinFeeTotal())+FileWriterBase.FIELD_DELIM);//钱箱金额
        this.addCommonToBuff(sb, vo);

        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    
}
