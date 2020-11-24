/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord50;
import com.goldsign.settle.realtime.app.vo.FileRecord51;
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
//非单程票售票记录
public class FileWriter51 extends FileWriterBase{

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
        /*
        String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;
        FileRecord51 vo;
        String line;
        try {
            fos = new FileOutputStream(fileNameBcp);
            osr = new OutputStreamWriter(fos, FileWriterBase.CHARSET);
            bw = new BufferedWriter(osr);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecord51) records.get(i);
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
        FileRecord51 vo =(FileRecord51)vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getRecordVer()+ FileWriterBase.FIELD_DELIM);//记录版本20180528
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//2线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//2站点代码
        sb.append(vo.getDevTypeId() + FileWriterBase.FIELD_DELIM);//3设备类型
        sb.append(vo.getDeviceId() + FileWriterBase.FIELD_DELIM);//4设备代码
        sb.append(vo.getCardMainId() + FileWriterBase.FIELD_DELIM);//5票卡主类型
        sb.append(vo.getCardSubId() + FileWriterBase.FIELD_DELIM);//5票卡子类型
         sb.append(vo.getCardLogicalId() + FileWriterBase.FIELD_DELIM);//6票卡逻辑ID
        sb.append(vo.getCardPhysicalId() + FileWriterBase.FIELD_DELIM);//7票卡物理ID
        sb.append(vo.getCardStatusId() + FileWriterBase.FIELD_DELIM);//8 票卡状态
        sb.append(vo.getCardTradSeq() + FileWriterBase.FIELD_DELIM);// 9 脱机业务流水号
        
        sb.append(vo.getSamLogicalId() + FileWriterBase.FIELD_DELIM);//10 SAM卡逻辑卡号
        sb.append(vo.getSamTradeSeq() + FileWriterBase.FIELD_DELIM);///11S AM卡脱机交易流水号
        
       
        sb.append(vo.getDepositType() + FileWriterBase.FIELD_DELIM);//12	金额类型
        sb.append(this.convertFenToYuan(vo.getDepositFee()) + FileWriterBase.FIELD_DELIM);//13	金额
         sb.append(vo.getSaleTime() + FileWriterBase.FIELD_DELIM);//14	销售时间
        sb.append(vo.getReceiptId() + FileWriterBase.FIELD_DELIM);//15凭证ID
        
        sb.append(vo.getOperatorId() + FileWriterBase.FIELD_DELIM);//16 操作员代码
        sb.append(vo.getShiftId() + FileWriterBase.FIELD_DELIM);//17 BOM班次序号
         sb.append(this.convertFenToYuan(vo.getAuxiFee()) + FileWriterBase.FIELD_DELIM);//18 手续费
        sb.append(vo.getCardAppFlag()+ FileWriterBase.FIELD_DELIM);//卡应用标识
        
        sb.append(vo.getMobileNo()+FileWriterBase.FIELD_DELIM );//手机号码
        sb.append(this.convertFenToYuan(vo.getSaleFee()) + FileWriterBase.FIELD_DELIM);//次票发售金额
        this.addCommonToBuff(sb, vo);

        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    
}
