/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord31;
import com.goldsign.settle.realtime.app.vo.FileRecord32;
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
public class FileWriter32 extends FileWriterBase{

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
       
    }
    
    @Override
     public String getLine(FileRecordBase vob) {
         FileRecord32 vo =(FileRecord32)vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getWaterNo() + FileWriterBase.FIELD_DELIM);//流水号
        
        sb.append(vo.getOpDate() + FileWriterBase.FIELD_DELIM);//2操作日期
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//3线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//3站点代码
        sb.append(vo.getDevTypeId() + FileWriterBase.FIELD_DELIM);//3设备类型
        sb.append(vo.getDeviceId() + FileWriterBase.FIELD_DELIM);//3设备代码



        
        sb.append(vo.getCurDatetime() + FileWriterBase.FIELD_DELIM);//4当前时间
        sb.append(vo.getCardPutNum() + FileWriterBase.FIELD_DELIM);//5当天Token Hopper补充总单程票数量
        sb.append(vo.getCoinPutNum() + FileWriterBase.FIELD_DELIM);//6当天Coin Hopper 补充总硬币数量
        sb.append(this.convertFenToYuan(vo.getCoinPutFee())+ FileWriterBase.FIELD_DELIM);//7当天Coin Hopper补充 总硬币金额
        sb.append(vo.getCardClearNum() + FileWriterBase.FIELD_DELIM);//8当天Token Hopper清空的单程票总数量
        sb.append(vo.getCoinClearNum() + FileWriterBase.FIELD_DELIM);//9当天Coin Hopper清空的硬币总数量
        sb.append(this.convertFenToYuan(vo.getCoinClearFee())+ FileWriterBase.FIELD_DELIM);//10当天Coin Hopper清空的硬币总金额
        
        sb.append(vo.getCoinReclaimNum() + FileWriterBase.FIELD_DELIM);//11当天Coin Box回收的硬币总数量
        sb.append(this.convertFenToYuan(vo.getCoinReclaimFee())+ FileWriterBase.FIELD_DELIM);//12当天Coin Box回收的硬币总金额
        sb.append(vo.getNoteReclaimNum() + FileWriterBase.FIELD_DELIM);//13当天Banknote Box回收的纸币总张数
        sb.append(this.convertFenToYuan(vo.getNoteReclaimFee())+ FileWriterBase.FIELD_DELIM);//14当天Banknote Box回收的纸币总金额
        this.addCommonToBuff(sb, vo);


        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    
}
