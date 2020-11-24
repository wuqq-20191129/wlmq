/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecordOctCW;
import com.goldsign.settle.realtime.app.vo.FileRecordSTL;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileWriterOctCW extends FileWriterBase {

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
    }

    @Override
    public String getLine(FileRecordBase vob) {
        FileRecordOctCW vo = (FileRecordOctCW) vob;
        StringBuffer sb = new StringBuffer();

       // sb.append(vo.getFileName() + FileWriterBase.FIELD_DELIM);//文件名
        sb.append(vo.getSerialNo() + FileWriterBase.FIELD_DELIM);//序列号
        sb.append(vo.getSamLogicalId() + FileWriterBase.FIELD_DELIM);//终端编号
        sb.append(vo.getTerminalFlag() + FileWriterBase.FIELD_DELIM);//终端标志
        sb.append(vo.getDealDatetime() + FileWriterBase.FIELD_DELIM);//交易时间

        sb.append(vo.getSamTradeSeq() + FileWriterBase.FIELD_DELIM);//终端交易流水号
        sb.append(vo.getCardLogicalId() + FileWriterBase.FIELD_DELIM);//票卡逻辑卡号
        sb.append(vo.getCardPhysicalId() + FileWriterBase.FIELD_DELIM);//票卡物理卡号
        sb.append(vo.getCardMainId() + FileWriterBase.FIELD_DELIM);//票卡主类型
        sb.append(vo.getCardSubId() + FileWriterBase.FIELD_DELIM);//票卡子类型

        
        
        sb.append(vo.getLastSamLogicalId() + FileWriterBase.FIELD_DELIM);//上次交易设备编号
        sb.append(vo.getLastDealDatetime() + FileWriterBase.FIELD_DELIM);//上次交易日期时间
        sb.append(vo.getDealFee_s() + FileWriterBase.FIELD_DELIM);//交易金额
        sb.append(vo.getDealBalance_s() + FileWriterBase.FIELD_DELIM);//本次余额
        sb.append(vo.getDealNoDiscountFee_s() + FileWriterBase.FIELD_DELIM);//原票价


        sb.append(vo.getPayModeId()+ FileWriterBase.FIELD_DELIM);//交易类型
        sb.append(vo.getEntrySamLogicalId()+ FileWriterBase.FIELD_DELIM);//本次入口设备编号
        sb.append(vo.getEntryDatetime()+ FileWriterBase.FIELD_DELIM);//本次入口日期时间
        sb.append(vo.getCardChargeSeq_s()+ FileWriterBase.FIELD_DELIM);//票卡联机交易计数
        sb.append(vo.getCardConsumeSeq()+ FileWriterBase.FIELD_DELIM);//票卡脱机交易计数
        

         sb.append(vo.getTac()+ FileWriterBase.FIELD_DELIM);//交易认证码
        sb.append(vo.getBusCityCode()+ FileWriterBase.FIELD_DELIM);//城市编码
        sb.append(vo.getBusBusinessCode()+ FileWriterBase.FIELD_DELIM);//行业代码
        sb.append(vo.getKeyVersion()+ FileWriterBase.FIELD_DELIM);//密钥标示
        sb.append(vo.getReserveField()+ FileWriterBase.FIELD_DELIM);//预留字段
        
        
        sb.append(vo.getLastChargeDatetime()+ FileWriterBase.FIELD_DELIM);//最后充值时间
        sb.append(vo.getErrorCode()+ FileWriterBase.FIELD_DELIM);//错误类型
        
        
        

        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());

        return sb.toString();
    }
}
