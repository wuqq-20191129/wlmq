/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;


import com.goldsign.settle.realtime.app.vo.FileRecordNetPaidOrdImp;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileWriterNetPaidORI extends FileWriterBase{
     @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
       
    }

    @Override
    public String getLine(FileRecordBase vob) {
        FileRecordNetPaidOrdImp vo =(FileRecordNetPaidOrdImp)vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getOrderNo()+ FileWriterBase.FIELD_DELIM);//订单号
        sb.append(vo.getOrderType()+ FileWriterBase.FIELD_DELIM);//订单类型
        sb.append(vo.getFinishDatetime() + FileWriterBase.FIELD_DELIM);//订单完成执行时间
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//线路
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//站点
        sb.append(vo.getDevTypeId() + FileWriterBase.FIELD_DELIM);//设备类型
        sb.append(vo.getDeviceId() + FileWriterBase.FIELD_DELIM);//设备代码
        sb.append(vo.getStatus()+ FileWriterBase.FIELD_DELIM);//订单状态
        sb.append(vo.getCardMainId() + FileWriterBase.FIELD_DELIM);//票卡主类型
        sb.append(vo.getCardSubId() + FileWriterBase.FIELD_DELIM);//票卡子类型
        sb.append(vo.getDealNum() + FileWriterBase.FIELD_DELIM);//出票数量
        sb.append(vo.getDealNumNot() + FileWriterBase.FIELD_DELIM);//未出票数量
        sb.append(this.convertFenToYuan(vo.getDealFee()) + FileWriterBase.FIELD_DELIM);//出票金额
        sb.append(this.convertFenToYuan(vo.getRefundFee()) + FileWriterBase.FIELD_DELIM);//退款金额
        sb.append(this.convertFenToYuan(vo.getAuxiFee()) + FileWriterBase.FIELD_DELIM);//退款手续费
        
        
     
        this.addCommonToBuff(sb, vo);

        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    
}
