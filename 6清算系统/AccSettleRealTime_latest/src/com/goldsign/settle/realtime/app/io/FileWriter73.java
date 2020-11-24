/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord73;
import com.goldsign.settle.realtime.app.vo.FileRecordQrCode53;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;

/**
 *
 * @author hejj
 */
public class FileWriter73 extends FileWriterQrCode53{
     @Override
    public String getLine(FileRecordBase vob) {
        FileRecord73 vo =(FileRecord73)vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getRecordVer()+ FileWriterBase.FIELD_DELIM);//记录版本20180528
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//2线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//2站点代码
        sb.append(vo.getDevTypeId() + FileWriterBase.FIELD_DELIM);//3设备类型
        sb.append(vo.getDeviceId() + FileWriterBase.FIELD_DELIM);//4设备代码
        sb.append(vo.getSamLogicalId() + FileWriterBase.FIELD_DELIM);//5SAM卡逻辑卡号
        sb.append(vo.getSamTradeSeq() + FileWriterBase.FIELD_DELIM);//6SAM卡脱机交易流水号
         sb.append(vo.getCardMainId() + FileWriterBase.FIELD_DELIM);//7票卡主类型
        sb.append(vo.getCardSubId() + FileWriterBase.FIELD_DELIM);//7票卡子类型
        sb.append(vo.getCardLogicalId() + FileWriterBase.FIELD_DELIM);//8逻辑ID
        sb.append(vo.getCardPhysicalId() + FileWriterBase.FIELD_DELIM);//9物理ID
        
        sb.append(vo.getEntryDatetime() + FileWriterBase.FIELD_DELIM);//10时间
        sb.append(vo.getCardStatusId() + FileWriterBase.FIELD_DELIM);//11票卡状态
        sb.append(this.convertFenToYuan(vo.getBalanceFee()) + FileWriterBase.FIELD_DELIM);//12余额
        sb.append(vo.getCardAppFlag()+ FileWriterBase.FIELD_DELIM);//13卡应用标识
        sb.append(vo.getLimitMode() + FileWriterBase.FIELD_DELIM);//14限制使用模式
        sb.append(vo.getLimitEntryStation() + FileWriterBase.FIELD_DELIM);//15限制进站代码
        sb.append(vo.getLimitExitStation()+ FileWriterBase.FIELD_DELIM );//16限制出站代码
        
        sb.append(vo.getWorkMode()+ FileWriterBase.FIELD_DELIM );//17进站工作模式
        sb.append(vo.getCardConsumeSeq()+ FileWriterBase.FIELD_DELIM );//18票卡扣款交易计数
        sb.append(vo.getCardAppMode()+ FileWriterBase.FIELD_DELIM );//19卡应用模式
        sb.append(vo.getTctActiveDatetime()+ FileWriterBase.FIELD_DELIM );//20乘车票激活时间
        
        sb.append(vo.getDiscountYearMonth()+ FileWriterBase.FIELD_DELIM);//优惠月
        sb.append(this.convertFenToYuan(vo.getAccumulateConsumeFee()) + FileWriterBase.FIELD_DELIM);//优惠月累计消费金额
        sb.append(vo.getIntervalBetweenBusMetro()+ FileWriterBase.FIELD_DELIM);//联乘时间间隔
        sb.append(vo.getLastBusDealDatetime()+ FileWriterBase.FIELD_DELIM);//上次公交交易时间
        
        sb.append(vo.getBusinessWaterNo()+ FileWriterBase.FIELD_DELIM);//业务流水
        sb.append(vo.getMobileNo()+ FileWriterBase.FIELD_DELIM);//手机号码
        
        
        
        
        
        
        this.addCommonToBuff(sb, vo);
        

        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    
}
