/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;


import com.goldsign.settle.realtime.app.vo.FileRecord53;
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
//进站记录
public class FileWriter53 extends FileWriterBase{

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
        /*
       String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;
        FileRecord53 vo;
        String line;
        try {
            fos = new FileOutputStream(fileNameBcp);
            osr = new OutputStreamWriter(fos, FileWriterBase.CHARSET);
            bw = new BufferedWriter(osr);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecord53) records.get(i);
                line = this.getLine(vo);
                bw.write(line);
                bw.flush();
            }

        } catch (Exception ex) {
            throw new FileWriteException(ex.getMessage());

        } finally {
            this.closeFile(fos, osr, bw);
        }
        */
    }
    
    @Override
    public String getLine(FileRecordBase vob) {
        FileRecord53 vo =(FileRecord53)vob;
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
        
         sb.append(vo.getAccumulateConsumeNum()+ FileWriterBase.FIELD_DELIM);//一卡通优惠累计消费次数20191110
        
        
        
        
        this.addCommonToBuff(sb, vo);
        

        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    
}
