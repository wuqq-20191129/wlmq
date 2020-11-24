/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord02;
import com.goldsign.settle.realtime.app.vo.FileRecord03;
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
/**
 * 
 * TVM硬币存入数据
 */
public class FileWriter03 extends FileWriterBase{

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
        /*
        String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;
        FileRecord03 vo;
        String line;
        try {
            fos = new FileOutputStream(fileNameBcp);
            osr = new OutputStreamWriter(fos, FileWriterBase.CHARSET);
            bw = new BufferedWriter(osr);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecord03) records.get(i);
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
        FileRecord03 vo =(FileRecord03)vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//站点代码
        sb.append(vo.getDevTypeId() + FileWriterBase.FIELD_DELIM);//设备类型
        sb.append(vo.getDeviceId() + FileWriterBase.FIELD_DELIM);//设备代码

        sb.append(vo.getHopperid() + FileWriterBase.FIELD_DELIM);//硬币Hopper
        sb.append(vo.getOperatorId() + FileWriterBase.FIELD_DELIM);//操作员的员工号
        sb.append(vo.getPutDatetime() + FileWriterBase.FIELD_DELIM);//存入时间
        sb.append(vo.getWaterNoOp() + FileWriterBase.FIELD_DELIM);//流水号
        
        sb.append(this.convertFenToYuan(vo.getCoinUnitFee()) + FileWriterBase.FIELD_DELIM);//硬币单位
        sb.append(vo.getCoinNum() + FileWriterBase.FIELD_DELIM);//硬币数量
        sb.append(this.convertFenToYuan(vo.getCoinFeeTotal()) + FileWriterBase.FIELD_DELIM);//硬币金额
        
        
        this.addCommonToBuff(sb, vo);

        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    
}
