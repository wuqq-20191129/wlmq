/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecordOctBLA;
import com.goldsign.settle.realtime.app.vo.FileRecordOctCW;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileWriterOctBLA extends FileWriterBase{
     @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
    }

    @Override
    public String getLine(FileRecordBase vob) {
        FileRecordOctBLA vo = (FileRecordOctBLA) vob;
        StringBuffer sb = new StringBuffer();

        sb.append(vo.getCardLogicalIdStart() + FileWriterBase.FIELD_DELIM);//开始名单逻辑卡号

        sb.append(vo.getSectFlag() + FileWriterBase.FIELD_DELIM);//段号标志
        sb.append(vo.getCardLogicalIdEnd() + FileWriterBase.FIELD_DELIM);//结束名单逻辑卡号

        sb.append(vo.getCardStatusApp() + FileWriterBase.FIELD_DELIM);//票卡状态




        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    
}
