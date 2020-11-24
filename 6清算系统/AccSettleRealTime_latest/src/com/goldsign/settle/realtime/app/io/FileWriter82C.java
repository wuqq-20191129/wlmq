/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord82Detail;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileWriter82C extends FileWriterBase{
     @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
       
    }

    @Override
    public String getLine(FileRecordBase vob) {
        FileRecord82Detail vo =(FileRecord82Detail)vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getWaterNo() + FileWriterBase.FIELD_DELIM);//流水号
        
        sb.append(this.convertFenToYuan(vo.getCardMoney())+ FileWriterBase.FIELD_DELIM);//纸币面值
        sb.append(vo.getNoteReceiveNum()+ FileWriterBase.FIELD_DELIM);//纸币接收数量
        sb.append(this.convertFenToYuan(vo.getNoteReceiveFee())+ FileWriterBase.FIELD_DELIM);//纸币接收金额




        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    
}
