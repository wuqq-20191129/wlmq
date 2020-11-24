/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecordMobileSTL;
import com.goldsign.settle.realtime.app.vo.FileRecordSTL;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileWriterMobileSTL extends FileWriterBase {

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

        sb.append(this.convertFenToYuan(vo.getChargeFee()) + FileWriterBase.FIELD_DELIM);//充值总金额
        sb.append(vo.getChargeNum() + FileWriterBase.FIELD_DELIM);//充值总数量
        sb.append(vo.getPaidChannelTypeCharge() + FileWriterBase.FIELD_DELIM);//充值渠道类型
        sb.append(vo.getPaidChannelCodeCharge() + FileWriterBase.FIELD_DELIM);//充值渠道代码

        sb.append(vo.getReturnNum() + FileWriterBase.FIELD_DELIM);//11即时退款总数量
        sb.append(this.convertFenToYuan(vo.getReturnFee()) + FileWriterBase.FIELD_DELIM);//12即时退款总金额
        sb.append(vo.getPaidChannelTypeReturn() + FileWriterBase.FIELD_DELIM);//退款渠道类型
        sb.append(vo.getPaidChannelCodeReturn() + FileWriterBase.FIELD_DELIM);//退款渠道代码

        sb.append(vo.getSaleNum() + FileWriterBase.FIELD_DELIM);//11发售总数量
        sb.append(this.convertFenToYuan(vo.getSaleFee()) + FileWriterBase.FIELD_DELIM);//12发售总金额

        sb.append(vo.getLockNum() + FileWriterBase.FIELD_DELIM);//11锁卡总数量
        sb.append(vo.getUnlockNum() + FileWriterBase.FIELD_DELIM);//11解锁总数量

        sb.append(vo.getSaleSjtNum() + FileWriterBase.FIELD_DELIM);//12购单程票总数量
        sb.append(this.convertFenToYuan(vo.getSaleSjtFee()) + FileWriterBase.FIELD_DELIM);//13购单程票总金额
        sb.append(vo.getPaidChannelTypeSaleSjt() + FileWriterBase.FIELD_DELIM);//二维码购票渠道类型
        sb.append(vo.getPaidChannelCodeSaleSjt() + FileWriterBase.FIELD_DELIM);//二维码购票渠道代码
        
         sb.append(vo.getPaidChannelTypeSale() + FileWriterBase.FIELD_DELIM);//发售渠道类型
        sb.append(vo.getPaidChannelCodeSale() + FileWriterBase.FIELD_DELIM);//发售渠道代码
        sb.append(vo.getChargeTctTNum() + FileWriterBase.FIELD_DELIM);//充次总次数
        sb.append(vo.getChargeTctNum() + FileWriterBase.FIELD_DELIM);//充次总数量
        sb.append(this.convertFenToYuan(vo.getChargeTctFee()) + FileWriterBase.FIELD_DELIM);//购次总金额
         sb.append(vo.getPaidChannelTypeBuy() + FileWriterBase.FIELD_DELIM);//购次渠道类型
        sb.append(vo.getPaidChannelCodeBuy() + FileWriterBase.FIELD_DELIM);//购次渠道代码

        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());

        return sb.toString();
    }
}
