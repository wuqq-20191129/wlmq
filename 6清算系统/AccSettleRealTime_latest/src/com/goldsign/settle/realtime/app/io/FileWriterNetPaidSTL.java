/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecordMobileSTL;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileWriterNetPaidSTL extends FileWriterBase{
    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
    }

    @Override
    public String getLine(FileRecordBase vob) {
        FileRecordMobileSTL vo = (FileRecordMobileSTL) vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//站点代码
        
        sb.append(vo.getSaleNum() + FileWriterBase.FIELD_DELIM);//11发售总数量
        sb.append(this.convertFenToYuan(vo.getSaleFee()) + FileWriterBase.FIELD_DELIM);//12发售总金额
        
        sb.append(vo.getChargeNum() + FileWriterBase.FIELD_DELIM);//充值总数量
        sb.append(this.convertFenToYuan(vo.getChargeFee()) + FileWriterBase.FIELD_DELIM);//充值总金额
        

        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    
}
