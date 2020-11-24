/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord81Detail;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileWriter81B extends FileWriterBase{
    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
        
    }

    @Override
    public String getLine(FileRecordBase vob) {
        FileRecord81Detail vo =(FileRecord81Detail)vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getWaterNo() + FileWriterBase.FIELD_DELIM);//流水号
        sb.append(vo.getCardMainId()+ FileWriterBase.FIELD_DELIM);//票卡主类型
        sb.append(vo.getCardSubId()+ FileWriterBase.FIELD_DELIM);//票卡子类型
        sb.append(vo.getExitNum()+ FileWriterBase.FIELD_DELIM);//出站数量




        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    
    
}
