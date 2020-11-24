/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord54;
import com.goldsign.settle.realtime.app.vo.FileRecord59;
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
//加锁/解锁 
public class FileWriter59 extends FileWriterBase {

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
        /*
        String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;
        FileRecord59 vo;
        String line;
        try {
            fos = new FileOutputStream(fileNameBcp);
            osr = new OutputStreamWriter(fos, FileWriterBase.CHARSET);
            bw = new BufferedWriter(osr);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecord59) records.get(i);
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

    public  String getLine(FileRecordBase vob) {
        FileRecord59 vo =(FileRecord59)vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getRecordVer()+ FileWriterBase.FIELD_DELIM);//记录版本20180528
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//2线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//2站点代码
        sb.append(vo.getDevTypeId() + FileWriterBase.FIELD_DELIM);//3设备类型
        sb.append(vo.getDeviceId() + FileWriterBase.FIELD_DELIM);//4设备代码
        sb.append(vo.getSamLogicalId() + FileWriterBase.FIELD_DELIM);//5SAM卡逻辑卡号
        sb.append(vo.getSamTradeSeq() + FileWriterBase.FIELD_DELIM);//6SAM卡脱机交易流水号

        
        sb.append(vo.getCardMainId() + FileWriterBase.FIELD_DELIM);//7票卡主类型
        sb.append(vo.getCardSubId() + FileWriterBase.FIELD_DELIM);//7票卡子类型
        sb.append(vo.getCardLogicalId() + FileWriterBase.FIELD_DELIM);//8票逻辑ID
        sb.append(vo.getCardPhysicalId() + FileWriterBase.FIELD_DELIM);//9票物理ID
        sb.append(vo.getCardStatusId() + FileWriterBase.FIELD_DELIM);//10票状态

        sb.append(vo.getLockFlag() + FileWriterBase.FIELD_DELIM);//11加解锁标志
        sb.append(vo.getLockDatetime() + FileWriterBase.FIELD_DELIM);//12时间
        
        sb.append(vo.getOperatorId() + FileWriterBase.FIELD_DELIM);//13操作员代码
        sb.append(vo.getShiftId() + FileWriterBase.FIELD_DELIM);//14BOM班次序号
        sb.append(vo.getCardAppFlag() + FileWriterBase.FIELD_DELIM);//15卡应用标识
        
        sb.append(vo.getCardAppMode() + FileWriterBase.FIELD_DELIM);//16卡应用模式
        
        
        
        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
}
