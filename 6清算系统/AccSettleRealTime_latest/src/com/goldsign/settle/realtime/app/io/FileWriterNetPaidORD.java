/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord50;
import com.goldsign.settle.realtime.app.vo.FileRecordNetPaidOrd;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileWriterNetPaidORD extends FileWriterBase{
     @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
       
    }

    @Override
    public String getLine(FileRecordBase vob) {
        FileRecordNetPaidOrd vo =(FileRecordNetPaidOrd)vob;
        StringBuffer sb = new StringBuffer();
        
        sb.append(vo.getOrderNo()+ FileWriterBase.FIELD_DELIM);//订单号
        sb.append(vo.getOrderType()+ FileWriterBase.FIELD_DELIM);//订单类型
        sb.append(vo.getStatus()+ FileWriterBase.FIELD_DELIM);//订单状态
        sb.append(vo.getGenerateDatetime() + FileWriterBase.FIELD_DELIM);//订单生成时间
        sb.append(vo.getPaidDatetime() + FileWriterBase.FIELD_DELIM);//订单支付时间
        sb.append(vo.getCardMainId() + FileWriterBase.FIELD_DELIM);//票卡主类型
        sb.append(vo.getCardSubId() + FileWriterBase.FIELD_DELIM);//票卡子类型
        sb.append(vo.getLineIdStart() + FileWriterBase.FIELD_DELIM);//起点站线路
        sb.append(vo.getStationIdStart() + FileWriterBase.FIELD_DELIM);//起点站站点
        sb.append(vo.getLineIdEnd() + FileWriterBase.FIELD_DELIM);//终点站线路
        sb.append(vo.getStationIdEnd() + FileWriterBase.FIELD_DELIM);//终点站站点
        sb.append(this.convertFenToYuan(vo.getDealUnitFee()) + FileWriterBase.FIELD_DELIM);//单价金额
        sb.append(vo.getDealNum() + FileWriterBase.FIELD_DELIM);//订单张数
         sb.append(this.convertFenToYuan(vo.getDealFee()) + FileWriterBase.FIELD_DELIM);//订单总金额
         sb.append(vo.getOrderTypeBuy() + FileWriterBase.FIELD_DELIM);//订单购票类型
         sb.append(vo.getPaidChannelType() + FileWriterBase.FIELD_DELIM);//支付渠道类型
         sb.append(vo.getPaidChannelCode() + FileWriterBase.FIELD_DELIM);//支付渠道代码
         sb.append(vo.getMobileNo() + FileWriterBase.FIELD_DELIM);//手机号码
     
        this.addCommonToBuff(sb, vo);

        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    
}
