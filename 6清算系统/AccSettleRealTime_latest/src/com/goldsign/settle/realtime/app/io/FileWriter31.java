/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord02;
import com.goldsign.settle.realtime.app.vo.FileRecord31;
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
public class FileWriter31 extends FileWriterBase {

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);

    }

    @Override
    public String getLine(FileRecordBase vob) {
        FileRecord31 vo  = (FileRecord31)vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getWaterNo() + FileWriterBase.FIELD_DELIM);//流水号
        
        sb.append(vo.getOpDate() + FileWriterBase.FIELD_DELIM);//操作日期
        
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//站点代码
        sb.append(vo.getDevTypeId() + FileWriterBase.FIELD_DELIM);//设备类型
        sb.append(vo.getDeviceId() + FileWriterBase.FIELD_DELIM);//设备代码



        
        sb.append(vo.getCurDatetime() + FileWriterBase.FIELD_DELIM);//当前时间
        sb.append(vo.getBoxPopNum() + FileWriterBase.FIELD_DELIM);//SJT票箱取出次数
        sb.append(vo.getTotalNum() + FileWriterBase.FIELD_DELIM);//总交易数量
        sb.append(this.convertFenToYuan(vo.getTotalFee())+ FileWriterBase.FIELD_DELIM);//总交易金额
        this.addCommonToBuff(sb, vo);
        /*
        sb.append(vo.getBalanceWaterNo()+ FileWriterBase.FIELD_DELIM);//清算流水号
        sb.append(vo.getFileName()+ FileWriterBase.FIELD_DELIM);//文件
        sb.append(vo.getCheckFlag());//校验标志
         */

        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
}
