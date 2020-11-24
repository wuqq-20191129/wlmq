/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord00ForReturn;
import com.goldsign.settle.realtime.app.vo.FileRecord00ForUpdate;
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
 * BOM班次数据 即时退款明细
 */
public class FileWriter00F extends FileWriterBase{

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
        /*
        String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;
        FileRecord00ForReturn vo;
        String line;
        try {
            fos = new FileOutputStream(fileNameBcp);
            osr = new OutputStreamWriter(fos, FileWriterBase.CHARSET);
            bw = new BufferedWriter(osr);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecord00ForReturn) records.get(i);
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
        FileRecord00ForReturn vo  =(FileRecord00ForReturn)vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getWaterNo() + FileWriterBase.FIELD_DELIM);//流水号
        
        sb.append(vo.getCardMainId() + FileWriterBase.FIELD_DELIM);//票卡主类型
        sb.append(vo.getCardSubId() + FileWriterBase.FIELD_DELIM);//票卡子类型
        sb.append(vo.getNum() + FileWriterBase.FIELD_DELIM);//交易数量
        sb.append(this.convertFenToYuan(vo.getReturnBalanceFee())+FileWriterBase.FIELD_DELIM);//即时退款余额
        sb.append(this.convertFenToYuan(vo.getReturnDepositFee())+FileWriterBase.FIELD_DELIM);//即时退款押金
        
        sb.append(vo.getReturnType() + FileWriterBase.FIELD_DELIM);//退款类型
        sb.append(vo.getIsBroken() + FileWriterBase.FIELD_DELIM);//是否折损

        
        sb.append(this.convertFenToYuan(vo.getPenaltyFee())+ FileWriterBase.FIELD_DELIM);//退款罚金
        sb.append(vo.getPenaltyReason()+ FileWriterBase.FIELD_DELIM );//罚款原因


        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());////modify by hejj20160625
        //sb.append(FileWriterBase.CRLF_1);


        return sb.toString();
    }
    
}
