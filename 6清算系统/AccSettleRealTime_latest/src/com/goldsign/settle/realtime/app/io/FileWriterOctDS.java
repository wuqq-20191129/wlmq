/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecordOctDS;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileWriterOctDS extends FileWriterBase{

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
    }

    @Override
    public String getLine(FileRecordBase vob) {
        FileRecordOctDS vo = (FileRecordOctDS) vob;
        StringBuffer sb = new StringBuffer();

        sb.append(vo.getFileNameAudit() + FileWriterBase.FIELD_DELIM);//oct结算的文件
        sb.append(vo.getRecordNum() + FileWriterBase.FIELD_DELIM);//文件记录数
        sb.append(vo.getRecordFee() + FileWriterBase.FIELD_DELIM);//文件记录的金额



        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    
}
