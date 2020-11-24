/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecordOctQS;
import com.goldsign.settle.realtime.app.vo.FileRecordOctDS;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileWriterOctQS extends FileWriterBase {

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
    }

    @Override
    public String getLine(FileRecordBase vob) {
        FileRecordOctQS vo = (FileRecordOctQS) vob;
        StringBuffer sb = new StringBuffer();

        sb.append(vo.getSettleDataType()+ FileWriterBase.FIELD_DELIM);//清算数据类型代码
        sb.append(vo.getSamLogicalId() + FileWriterBase.FIELD_DELIM);//SAM卡逻辑卡号
        sb.append(vo.getTrdType() + FileWriterBase.FIELD_DELIM);//交易类型
        sb.append(vo.getTotaDealNum() + FileWriterBase.FIELD_DELIM);//交易记录数
        sb.append(vo.getTotaDealFee() + FileWriterBase.FIELD_DELIM);//交易金额



        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
}
