/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord1Detail;
import com.goldsign.settle.realtime.app.vo.FileRecord2Detail;
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
public class FileWriter32A extends FileWriterBase{

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
        
    }
    
    @Override
    public  String getLine(FileRecordBase vob) {
        FileRecord2Detail vo =(FileRecord2Detail)vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getWaterNo() + FileWriterBase.FIELD_DELIM);//流水号
        sb.append(vo.getTrdType() + FileWriterBase.FIELD_DELIM);//交易类型
        sb.append(vo.getPaymodeId() + FileWriterBase.FIELD_DELIM);//支付类型
        sb.append(vo.getCardMainId() + FileWriterBase.FIELD_DELIM);//票卡主类型
        sb.append(vo.getCardSubId() + FileWriterBase.FIELD_DELIM);//票卡子类型



        sb.append(vo.getDealNum() + FileWriterBase.FIELD_DELIM);//交易数量
        sb.append(this.convertFenToYuan(vo.getDealFee())+ FileWriterBase.FIELD_DELIM);//交易金额


        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    
}
