/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord81;
import com.goldsign.settle.realtime.app.vo.FileRecord82;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileWriter82 extends FileWriterBase{
     @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);

    }

    @Override
    public String getLine(FileRecordBase vob) {
        FileRecord82 vo = (FileRecord82)vob;
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
         sb.append(vo.getSaleNum()+ FileWriterBase.FIELD_DELIM);//发售数量
         sb.append(this.convertFenToYuan(vo.getSaleFee())+ FileWriterBase.FIELD_DELIM);//发售金额
         sb.append(vo.getChargeNum()+ FileWriterBase.FIELD_DELIM);//充值数量
         sb.append(this.convertFenToYuan(vo.getChargeFee())+ FileWriterBase.FIELD_DELIM);//充值金额
         sb.append(vo.getCoinReceiveNum()+ FileWriterBase.FIELD_DELIM);//TVM累计接收硬币的总数
         sb.append(this.convertFenToYuan(vo.getCoinReceiveFee())+ FileWriterBase.FIELD_DELIM);//TVM累计接收硬币的总金额

        sb.append(vo.getNoteReceiveNum()+ FileWriterBase.FIELD_DELIM);//TVM累计接收纸币的总数
        sb.append(this.convertFenToYuan(vo.getNoteReceiveFee())+ FileWriterBase.FIELD_DELIM);//TVM累计接收纸币的总金额
        sb.append(vo.getCoinChangeNum()+ FileWriterBase.FIELD_DELIM);//TVM累计找零硬币的总数
        sb.append(this.convertFenToYuan(vo.getCoinChangeFee())+ FileWriterBase.FIELD_DELIM);//TVM累计找零硬币的总金额
        sb.append(vo.getNoteChangeNum()+ FileWriterBase.FIELD_DELIM);//TVM累计找零纸币的总数
        sb.append(this.convertFenToYuan(vo.getNoteChangeFee())+ FileWriterBase.FIELD_DELIM);//TVM累计找零纸币的总金额
        sb.append(vo.getTkWasteNum()+ FileWriterBase.FIELD_DELIM);//废票箱的车票数量
        this.addCommonToBuff(sb, vo);



        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    
}
