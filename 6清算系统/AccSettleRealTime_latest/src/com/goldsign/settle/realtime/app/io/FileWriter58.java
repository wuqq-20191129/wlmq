/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord58;
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
 * 非即时退款申请
 */
//非即时退款申请 
public class FileWriter58 extends FileWriterBase {

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
        /*
        String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;
        FileRecord58 vo;
        String line;
        try {
            fos = new FileOutputStream(fileNameBcp);
            osr = new OutputStreamWriter(fos, FileWriterBase.CHARSET);
            bw = new BufferedWriter(osr);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecord58) records.get(i);
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
        FileRecord58 vo =(FileRecord58)vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getRecordVer()+ FileWriterBase.FIELD_DELIM);//记录版本20180528
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//2线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//2站点代码
        sb.append(vo.getDevTypeId() + FileWriterBase.FIELD_DELIM);//3设备类型
        sb.append(vo.getDeviceId() + FileWriterBase.FIELD_DELIM);//4设备代码



        sb.append(vo.getCardMainId() + FileWriterBase.FIELD_DELIM);//5票卡主类型
        sb.append(vo.getCardSubId() + FileWriterBase.FIELD_DELIM);//5票卡子类型
        sb.append(vo.getCardLogicalId() + FileWriterBase.FIELD_DELIM);//6票逻辑ID
        sb.append(vo.getCardPhysicalId() + FileWriterBase.FIELD_DELIM);//7票物理ID
        sb.append(vo.getCardPrintId() + FileWriterBase.FIELD_DELIM);//8票卡印刻号

        sb.append(vo.getApplyDatetime() + FileWriterBase.FIELD_DELIM);//9日期时间
        sb.append(vo.getReceiptId() + FileWriterBase.FIELD_DELIM);//10凭证ID
        sb.append(vo.getOperatorId() + FileWriterBase.FIELD_DELIM);//11操作员代码



        sb.append(vo.getApplyName() + FileWriterBase.FIELD_DELIM);//12乘客姓名
        sb.append(vo.getTelNo() + FileWriterBase.FIELD_DELIM);//13乘客电话
        sb.append(vo.getIdentityType() + FileWriterBase.FIELD_DELIM);//14证件类型

        sb.append(vo.getIdentityId() + FileWriterBase.FIELD_DELIM);//15证件号码

        sb.append(vo.getIsBroken() + FileWriterBase.FIELD_DELIM);//16票卡是否折损
        sb.append(vo.getShiftId() + FileWriterBase.FIELD_DELIM);//17BOM班次序号
        sb.append(vo.getCardAppFlag() + FileWriterBase.FIELD_DELIM);//18卡应用标识


        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
}
