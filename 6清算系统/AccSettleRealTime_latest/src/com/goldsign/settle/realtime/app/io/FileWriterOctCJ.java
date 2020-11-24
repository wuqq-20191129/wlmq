/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecordOctCJ;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileWriterOctCJ extends FileWriterBase{

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
    }

    @Override
    public String getLine(FileRecordBase vob) {
        FileRecordOctCJ vo = (FileRecordOctCJ) vob;
        StringBuffer sb = new StringBuffer();

        sb.append(vo.getFileNameAcc() + FileWriterBase.FIELD_DELIM);//ACC上传的文件
        sb.append(vo.getFileStatus() + FileWriterBase.FIELD_DELIM);//文件接收状态     



        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    
}
