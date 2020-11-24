/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord76;

import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileWriter76 extends FileWriterBase{
      @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
       
    }
    
    @Override
    public String getLine(FileRecordBase vob) {
        FileRecord76 vo =(FileRecord76)vob;
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
        sb.append(vo.getCardLogicalId() + FileWriterBase.FIELD_DELIM);//8票逻辑ID
        sb.append(vo.getCardPhysicalId() + FileWriterBase.FIELD_DELIM);//9票物理ID
        sb.append(vo.getCardConsumeSeq() + FileWriterBase.FIELD_DELIM);//10票卡扣款交易计数
        sb.append(vo.getCardStatusId() + FileWriterBase.FIELD_DELIM);//11票状态

        sb.append(vo.getUpdateArea() + FileWriterBase.FIELD_DELIM);//12更新区域
        sb.append(vo.getUpdateReasonId() + FileWriterBase.FIELD_DELIM);//13更新原因
        sb.append(vo.getUpdateDatetime() + FileWriterBase.FIELD_DELIM);//14更新日期时间
        sb.append(vo.getPayType() + FileWriterBase.FIELD_DELIM);//15支付方式
        sb.append(this.convertFenToYuan(vo.getPenaltyFee()) + FileWriterBase.FIELD_DELIM);//16罚款金额
        
        sb.append(vo.getReceiptId() + FileWriterBase.FIELD_DELIM);//17凭证ID 
        sb.append(vo.getOperatorId() + FileWriterBase.FIELD_DELIM);//18操作员代码
        sb.append(vo.getEntryLineId() + FileWriterBase.FIELD_DELIM);//19入口线路代码
        sb.append(vo.getEntryStationId() + FileWriterBase.FIELD_DELIM);//19入口站点代码
        sb.append(vo.getShiftId() + FileWriterBase.FIELD_DELIM);//20BOM班次序号
        
        sb.append(vo.getCardAppFlag() + FileWriterBase.FIELD_DELIM);//21卡应用标识
        sb.append(vo.getLimitMode() + FileWriterBase.FIELD_DELIM);//22限制使用模式
        sb.append(vo.getLimitEntryStation() + FileWriterBase.FIELD_DELIM);//23限制进站代码
        sb.append(vo.getLimitExitStation() + FileWriterBase.FIELD_DELIM);//24限制出站代码
        sb.append(vo.getCardAppMode() + FileWriterBase.FIELD_DELIM);//25卡应用模式
        sb.append(vo.getTctActiveDatetime()+ FileWriterBase.FIELD_DELIM );//26乘车票激活时间
        
        sb.append(vo.getBusinessWaterNo()+ FileWriterBase.FIELD_DELIM);//业务流水
        sb.append(vo.getBusinessWaterNoRel()+ FileWriterBase.FIELD_DELIM);//关联业务流水
 
        
        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    
}
