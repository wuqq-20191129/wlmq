/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord02;
import com.goldsign.settle.realtime.app.vo.FileRecord12;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileWriter12 extends FileWriterBase{

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
    }

    @Override
    public String getLine(FileRecordBase vob) {
         FileRecord12 vo =(FileRecord12)vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//2线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//2站点代码
        sb.append(vo.getDevTypeId() + FileWriterBase.FIELD_DELIM);//2设备类型
        sb.append(vo.getDeviceId() + FileWriterBase.FIELD_DELIM);//2设备代码
        
        sb.append(vo.getAutoBalDatetimeBegein() + FileWriterBase.FIELD_DELIM);//自动结存周期开始时间
        sb.append(vo.getAutoBalDatetimeEnd() + FileWriterBase.FIELD_DELIM);//自动结存周期结束时间
        
        sb.append(this.convertFenToYuan(vo.getSjtSaleFeeTotal1()) + FileWriterBase.FIELD_DELIM);//单程票箱1发售总金额
        sb.append(this.convertFenToYuan(vo.getSjtSaleFeeTotal2()) + FileWriterBase.FIELD_DELIM);//单程票箱2发售总金额
        sb.append(this.convertFenToYuan(vo.getChargeFeeTotal()) + FileWriterBase.FIELD_DELIM);//充值总金额
        sb.append(this.convertFenToYuan(vo.getNoteRecvFeeTotal()) + FileWriterBase.FIELD_DELIM);//纸币接收总金额
        sb.append(this.convertFenToYuan(vo.getCoinRecvFeeTotal()) + FileWriterBase.FIELD_DELIM);//硬币接收总金额
        sb.append(this.convertFenToYuan(vo.getNoteRecvFeeRepTotal()) + FileWriterBase.FIELD_DELIM);//更换纸币接收钱箱累计金额
        sb.append(this.convertFenToYuan(vo.getCoinRecvFeeRepTotal()) + FileWriterBase.FIELD_DELIM);//更换硬币接收钱箱累计金额
        sb.append(this.convertFenToYuan(vo.getCoinBalFeeTotal()) + FileWriterBase.FIELD_DELIM);//硬币钱箱结存金额
        sb.append(this.convertFenToYuan(vo.getNoteBalFeeTotal()) + FileWriterBase.FIELD_DELIM);//纸币接收钱箱结存金额
        sb.append(this.convertFenToYuan(vo.getNoteChgFeePutTotal1()) + FileWriterBase.FIELD_DELIM);//存入纸币找零箱1总金额
        sb.append(this.convertFenToYuan(vo.getNoteChgFeePutTotal2()) + FileWriterBase.FIELD_DELIM);//存入纸币找零箱2总金额
        sb.append(this.convertFenToYuan(vo.getCoinChgFeePutTotal1()) + FileWriterBase.FIELD_DELIM);//存入硬币找零HOPPER 1总金额
        sb.append(this.convertFenToYuan(vo.getCoinChgFeePutTotal2()) + FileWriterBase.FIELD_DELIM);//存入硬币找零HOPPER 2总金额
        sb.append(this.convertFenToYuan(vo.getCoinChgFeePopTotal1()) + FileWriterBase.FIELD_DELIM);//硬币找零HOPPER 1找零总金额
        sb.append(this.convertFenToYuan(vo.getCoinChgFeePopTotal2()) + FileWriterBase.FIELD_DELIM);//硬币找零HOPPER 2找零总金额
        sb.append(this.convertFenToYuan(vo.getNoteChgFeePopTotal1()) + FileWriterBase.FIELD_DELIM);//纸币找零箱1找零总金额
        sb.append(this.convertFenToYuan(vo.getNoteChgFeePopTotal2()) + FileWriterBase.FIELD_DELIM);//纸币找零箱2找零总金额
        sb.append(this.convertFenToYuan(vo.getCoinChgFeeBalTotal1()) + FileWriterBase.FIELD_DELIM);//硬币找零HOPPER 1结存总金额
        sb.append(this.convertFenToYuan(vo.getCoinChgFeeBalTotal2()) + FileWriterBase.FIELD_DELIM);//硬币找零HOPPER 2结存总金额
        sb.append(this.convertFenToYuan(vo.getNoteChgFeeBalTotal1()) + FileWriterBase.FIELD_DELIM);//纸币找零钱箱1结存总金额
        sb.append(this.convertFenToYuan(vo.getNoteChgFeeBalTotal2()) + FileWriterBase.FIELD_DELIM);//纸币找零钱箱2结存总金额
        sb.append(this.convertFenToYuan(vo.getCoinChgFeeClearTotal1()) + FileWriterBase.FIELD_DELIM);//硬币找零HOPPER 1清空总金额
        sb.append(this.convertFenToYuan(vo.getCoinChgFeeClearTotal2()) + FileWriterBase.FIELD_DELIM);//硬币找零HOPPER 2清空总金额
        sb.append(this.convertFenToYuan(vo.getCoinChgFeeBalSumTotal1()) + FileWriterBase.FIELD_DELIM);//清币结账后，硬币找零HOPPER1结存累计金额
        sb.append(this.convertFenToYuan(vo.getCoinChgFeeBalSumTotal2()) + FileWriterBase.FIELD_DELIM);//清币结账后，硬币找零HOPPER2结存累计金额
        sb.append(vo.getNoteReclaimChgNum() + FileWriterBase.FIELD_DELIM);//已更换纸币找零回收箱的回收数量
        sb.append(vo.getNoteReclaimBalNum() + FileWriterBase.FIELD_DELIM);//纸币找零回收箱回收结存数量
        sb.append(vo.getSjtNumPutTotal1() + FileWriterBase.FIELD_DELIM);//单程票箱1存入总数量
        sb.append(vo.getSjtNumPutTotal2() + FileWriterBase.FIELD_DELIM);//单程票箱2存入总数量
        sb.append(vo.getSjtNumSaleTotal1() + FileWriterBase.FIELD_DELIM);//单程票箱1发售数量

        sb.append(vo.getSjtNumSaleTotal2() + FileWriterBase.FIELD_DELIM);//单程票箱2发售数量
        sb.append(vo.getSjtNumClearTotal1() + FileWriterBase.FIELD_DELIM);//单程票箱1清空数量
        sb.append(vo.getSjtNumClearTotal2() + FileWriterBase.FIELD_DELIM);//单程票箱2清空数量
        sb.append(vo.getSjtNumBalTotal1() + FileWriterBase.FIELD_DELIM);//单程票箱1结存数量
        sb.append(vo.getSjtNumBalTotal2() + FileWriterBase.FIELD_DELIM);//单程票箱2结存数量
        sb.append(vo.getSjtNumWasteTotal() + FileWriterBase.FIELD_DELIM);//单程票废票数量
        sb.append(vo.getTvmBalFeeTotal() + FileWriterBase.FIELD_DELIM);//TVM结存金额

        this.addCommonToBuff(sb, vo);

        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    
}
