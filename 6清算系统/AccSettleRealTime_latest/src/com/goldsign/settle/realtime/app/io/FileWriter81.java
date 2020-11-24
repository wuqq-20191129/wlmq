/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord31;
import com.goldsign.settle.realtime.app.vo.FileRecord81;
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
public class FileWriter81 extends FileWriterBase {

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);

    }

    @Override
    public String getLine(FileRecordBase vob) {
        FileRecord81 vo = (FileRecord81)vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getWaterNo() + FileWriterBase.FIELD_DELIM);//流水号
        sb.append(vo.getCommuDatetime() + FileWriterBase.FIELD_DELIM);//2通讯消息时间
        
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//3线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//3站点代码
        sb.append(vo.getDevTypeId() + FileWriterBase.FIELD_DELIM);//4设备类型
        sb.append(vo.getDeviceId() + FileWriterBase.FIELD_DELIM);//5设备代码



        
        sb.append(vo.getGenDatetime() + FileWriterBase.FIELD_DELIM);//6产生日期/时间
        sb.append(vo.getTotalNum()+ FileWriterBase.FIELD_DELIM);//7合计数量

         sb.append(vo.getOverflowNum()+ FileWriterBase.FIELD_DELIM);//已溢出次数
         sb.append(vo.getEntryNum()+ FileWriterBase.FIELD_DELIM);//进闸人数
         sb.append(vo.getExitNum()+ FileWriterBase.FIELD_DELIM);//出闸人数
         sb.append(vo.getRefuseNum()+ FileWriterBase.FIELD_DELIM);//拒绝人数
         sb.append(vo.getReclaimNum()+ FileWriterBase.FIELD_DELIM);//车票回收数量
         sb.append(vo.getConsumeNum()+ FileWriterBase.FIELD_DELIM);//扣款数量
         sb.append(this.convertFenToYuan(vo.getConsumeFee())+ FileWriterBase.FIELD_DELIM);//扣款金额

        sb.append(vo.getRefuseEntryNum()+ FileWriterBase.FIELD_DELIM);//拒绝进闸人数
        sb.append(vo.getOvertimeCloseNum()+ FileWriterBase.FIELD_DELIM);//超时关门次数
        sb.append(vo.getRefuseExitNum()+ FileWriterBase.FIELD_DELIM);//拒绝出闸人数
        sb.append(vo.getAllowPassNum()+ FileWriterBase.FIELD_DELIM);//允许通过人数
        sb.append(vo.getCheckedPassNum()+ FileWriterBase.FIELD_DELIM);//正常验票后正常通过人数
        sb.append(vo.getIllegalPassNum()+ FileWriterBase.FIELD_DELIM);//非法通过人数
        sb.append(vo.getCheckedOvertimeCloseNum()+ FileWriterBase.FIELD_DELIM);//正常验票后无人通过，超时关门次数
        this.addCommonToBuff(sb, vo);



        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
}
