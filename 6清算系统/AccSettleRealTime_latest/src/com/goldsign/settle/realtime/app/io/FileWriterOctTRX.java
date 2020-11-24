/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecordOctCW;
import com.goldsign.settle.realtime.app.vo.FileRecordOctTRX;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileWriterOctTRX extends FileWriterBase{
     @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
    }

    @Override
    public String getLine(FileRecordBase vob) {
        FileRecordOctTRX vo = (FileRecordOctTRX) vob;
        StringBuffer sb = new StringBuffer();

        sb.append(vo.getStrSamTradeSeq() + FileWriterBase.FIELD_DELIM);//1SAM卡脱机交易流水

        sb.append(vo.getCardMainId() + FileWriterBase.FIELD_DELIM);//票卡主类型
        sb.append(vo.getCardSubId() + FileWriterBase.FIELD_DELIM);//票卡子类型

        sb.append(vo.getCardLogicalId() + FileWriterBase.FIELD_DELIM);//票卡逻辑卡号
        sb.append(vo.getCardPhysicalId() + FileWriterBase.FIELD_DELIM);//票卡物理卡号
        sb.append(vo.getLastSamLogicalId() + FileWriterBase.FIELD_DELIM);//上次交易SAM卡逻辑卡号
        
        sb.append(vo.getLastDealDatetime() + FileWriterBase.FIELD_DELIM);//上次交易日期时间
        sb.append(vo.getSamLogicalId() + FileWriterBase.FIELD_DELIM);//本次交易SAM卡逻辑卡号
        sb.append(vo.getDealDatetime() + FileWriterBase.FIELD_DELIM);//本次交易日期时间
        sb.append(vo.getEntrySamLogicalId() + FileWriterBase.FIELD_DELIM);//入口SAM卡逻辑卡号
        sb.append(vo.getEntryDatetime() + FileWriterBase.FIELD_DELIM);//入口日期时间
        sb.append(vo.getPayModeId() + FileWriterBase.FIELD_DELIM);//交易类型-支付类型
        sb.append(vo.getDealFee_s()+ FileWriterBase.FIELD_DELIM);//交易金额
        
        sb.append(vo.getDealBalance_s()+ FileWriterBase.FIELD_DELIM);//本次余额
        sb.append(vo.getStrCardConsumeSeq() + FileWriterBase.FIELD_DELIM);//票卡消费交易计数
        sb.append(vo.getTac() + FileWriterBase.FIELD_DELIM);//交易认证码
        sb.append(vo.getBusCityCode() + FileWriterBase.FIELD_DELIM);//城市代码
        sb.append(vo.getBusBusinessCode() + FileWriterBase.FIELD_DELIM);//行业代码
        sb.append(vo.getBusTacDealType() + FileWriterBase.FIELD_DELIM);//TAC交易类型
        sb.append(vo.getBusTacDevId() + FileWriterBase.FIELD_DELIM);//TAC终端编号



        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    
}
