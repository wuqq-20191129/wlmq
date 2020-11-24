/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord18;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileWriter19 extends FileWriterBase{
     @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
    }

    @Override
    public String getLine(FileRecordBase vob) {
         FileRecord18 vo =(FileRecord18)vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//2线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//2站点代码
        sb.append(vo.getDevTypeId() + FileWriterBase.FIELD_DELIM);//2设备类型
        sb.append(vo.getDeviceId() + FileWriterBase.FIELD_DELIM);//2设备代码
        
        sb.append(vo.getAutoBalDatetimeBegein() + FileWriterBase.FIELD_DELIM);//自动结存周期开始时间
        sb.append(vo.getAutoBalDatetimeEnd() + FileWriterBase.FIELD_DELIM);//自动结存周期结束时间
        
        sb.append(vo.getSjtNumSaleTotal1() + FileWriterBase.FIELD_DELIM);//单程票箱1发售数量
        sb.append(vo.getSjtNumSaleTotal2() + FileWriterBase.FIELD_DELIM);//单程票箱2发售数量
        sb.append(this.convertFenToYuan(vo.getSjtSaleFeeTotal1()) + FileWriterBase.FIELD_DELIM);//单程票箱1发售总金额
        sb.append(this.convertFenToYuan(vo.getSjtSaleFeeTotal2()) + FileWriterBase.FIELD_DELIM);//单程票箱2发售总金额
        sb.append(this.convertFenToYuan(vo.getChargeFeeTotal()) + FileWriterBase.FIELD_DELIM);//充值总金额

        sb.append(vo.getSjtNumPutTotal1() + FileWriterBase.FIELD_DELIM);//单程票箱1存入总数量
        sb.append(vo.getSjtNumPutTotal2() + FileWriterBase.FIELD_DELIM);//单程票箱2存入总数量

        sb.append(vo.getSjtNumClearTotal1() + FileWriterBase.FIELD_DELIM);//单程票箱1清空数量
        sb.append(vo.getSjtNumClearTotal2() + FileWriterBase.FIELD_DELIM);//单程票箱2清空数量
        sb.append(vo.getSjtNumBalTotal1() + FileWriterBase.FIELD_DELIM);//单程票箱1结存数量
        sb.append(vo.getSjtNumBalTotal2() + FileWriterBase.FIELD_DELIM);//单程票箱2结存数量
        sb.append(vo.getSjtNumWasteTotal() + FileWriterBase.FIELD_DELIM);//单程票废票数量
        sb.append(this.convertFenToYuan(vo.getTvmBalFeeTotal()) + FileWriterBase.FIELD_DELIM);//TVM结存金额

        this.addCommonToBuff(sb, vo);

        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    
}
