/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord61;
import com.goldsign.settle.realtime.frame.dao.FileErrorDao;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Vector;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
//行政处理
public class FileWriter61 extends FileWriterBase {
private static Logger logger = Logger.getLogger(FileErrorDao.class.getName());

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
        /*
        String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;
        FileRecord61 vo;
        String line;
        try {
            fos = new FileOutputStream(fileNameBcp);
            osr = new OutputStreamWriter(fos, FileWriterBase.CHARSET);
            bw = new BufferedWriter(osr);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecord61) records.get(i);
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
        FileRecord61 vo =(FileRecord61)vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getRecordVer()+ FileWriterBase.FIELD_DELIM);//记录版本20180528
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//2线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//2站点代码
        sb.append(vo.getDevTypeId() + FileWriterBase.FIELD_DELIM);//3设备类型
        sb.append(vo.getDeviceId() + FileWriterBase.FIELD_DELIM);//4设备代码


        sb.append(vo.getAdminDatetime() + FileWriterBase.FIELD_DELIM);//5时间
        sb.append(vo.getAdminWayId() + FileWriterBase.FIELD_DELIM);//6事务代码
        sb.append(vo.getCardMainId() + FileWriterBase.FIELD_DELIM);//7票卡主类型
        sb.append(vo.getCardSubId() + FileWriterBase.FIELD_DELIM);//7票卡子类型

        sb.append(this.convertFenToYuan(vo.getReturnFee()) + FileWriterBase.FIELD_DELIM);//8退给乘客的金额
        sb.append(this.convertFenToYuan(vo.getPenaltyFee()) + FileWriterBase.FIELD_DELIM);//9收取的罚金
        sb.append(vo.getAdminReasonId() + FileWriterBase.FIELD_DELIM);//10行政处理原因
       // this.getDescribe(vo.getDescribe());
        sb.append(vo.getDescribe() + FileWriterBase.FIELD_DELIM);//11行政处理描述
        sb.append(vo.getPassengerName() + FileWriterBase.FIELD_DELIM);//`12乘客姓名

        sb.append(vo.getOperatorId() + FileWriterBase.FIELD_DELIM);//13操作员代码
        sb.append(vo.getShiftId() + FileWriterBase.FIELD_DELIM);//14BOM班次序号

        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    private void getDescribe(String des){
        try {
            int len=des.getBytes("gbk").length;
            logger.error("行政处理描述长度："+len);
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex);
        }
    }
}
