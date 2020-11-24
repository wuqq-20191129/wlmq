/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord50;
import com.goldsign.settle.realtime.app.vo.FileRecordSTL;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileWriterSTL extends FileWriterBase {

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
    }

    @Override
    public String getLine(FileRecordBase vob) {
        FileRecordSTL vo = (FileRecordSTL) vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getRecordVer() + FileWriterBase.FIELD_DELIM);//记录版本
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//站点代码

        sb.append(vo.getBomSaleSjtNum() + FileWriterBase.FIELD_DELIM);//2BOM单程票发售总数量
        sb.append(this.convertFenToYuan(vo.getBomSaleSjtFee()) + FileWriterBase.FIELD_DELIM);//3BOM单程票发售总金额
        sb.append(vo.getTvmSaleSjtNum() + FileWriterBase.FIELD_DELIM);//4TVM单程票发售总数量
        sb.append(this.convertFenToYuan(vo.getTvmSaleSjtFee()) + FileWriterBase.FIELD_DELIM);//5TVM单程票发售总金额
        sb.append(vo.getBomSaleNum() + FileWriterBase.FIELD_DELIM);//6BOM储值类票卡发售总数量
        sb.append(this.convertFenToYuan(vo.getBomSaleDepositFee()) + FileWriterBase.FIELD_DELIM);//7BOM储值类票卡押金总金额
        sb.append(this.convertFenToYuan(vo.getBomChargeFee()) + FileWriterBase.FIELD_DELIM);//8BOM充值总金额
        sb.append(vo.getTvmChargeNum() + FileWriterBase.FIELD_DELIM);//9TVM充值总数量
        sb.append(this.convertFenToYuan(vo.getTvmChargeFee()) + FileWriterBase.FIELD_DELIM);//10TVM充值总金额
        sb.append(vo.getReturnNum() + FileWriterBase.FIELD_DELIM);//11即时退款总数量
        sb.append(this.convertFenToYuan(vo.getReturnFee()) + FileWriterBase.FIELD_DELIM);//12即时退款总金额
        sb.append(vo.getNonRetNum() + FileWriterBase.FIELD_DELIM);//13非即时退款总数量
        sb.append(this.convertFenToYuan(vo.getNonRetDepositFee()) + FileWriterBase.FIELD_DELIM);//14非即时退款总押金
        sb.append(this.convertFenToYuan(vo.getNonRetActualBala()) + FileWriterBase.FIELD_DELIM);//15非即时退款总退还余额
        sb.append(vo.getNegativeChargeNum() + FileWriterBase.FIELD_DELIM);//16冲正总数量
        sb.append(this.convertFenToYuan(vo.getNegativeChargeFee()) + FileWriterBase.FIELD_DELIM);//17冲正总金额
        sb.append(vo.getDealNum() + FileWriterBase.FIELD_DELIM);//18出闸扣费总数量
        sb.append(this.convertFenToYuan(vo.getDealFee()) + FileWriterBase.FIELD_DELIM);//19出闸扣费总金额
        sb.append(vo.getUpdateCashNum() + FileWriterBase.FIELD_DELIM);//20现金更新总数量
        sb.append(this.convertFenToYuan(vo.getUpdateCashFee()) + FileWriterBase.FIELD_DELIM);//21现金更新总金额
        sb.append(vo.getUpdateNonCashNum() + FileWriterBase.FIELD_DELIM);//22非现金更新总数量
        sb.append(this.convertFenToYuan(vo.getUpdateNonCashFee()) + FileWriterBase.FIELD_DELIM);//23非现金更新总金额
        sb.append(vo.getAdminNum() + FileWriterBase.FIELD_DELIM);//24行政处理总数量
        sb.append(this.convertFenToYuan(vo.getAdminReturnFee()) + FileWriterBase.FIELD_DELIM);//25行政处理总支出金额

        sb.append(this.convertFenToYuan(vo.getAdminPenaltyFee()) + FileWriterBase.FIELD_DELIM);//26行政处理总收取金额

        sb.append(vo.getItmSaleSjtNum() + FileWriterBase.FIELD_DELIM);//27ITM发售单程票总数量
        sb.append(this.convertFenToYuan(vo.getItmSaleSjtFee()) + FileWriterBase.FIELD_DELIM);//28ITM发售单程票总金额
        sb.append(vo.getItmChargeNum() + FileWriterBase.FIELD_DELIM);//29ITM充值总数量
        sb.append(this.convertFenToYuan(vo.getItmChargeFee()) + FileWriterBase.FIELD_DELIM);//30ITM充值总金额

        sb.append(vo.getQrDealNum() + FileWriterBase.FIELD_DELIM);//二维码钱包交易总数量
        sb.append(vo.getElectTkTctDealNum() + FileWriterBase.FIELD_DELIM);//电子票计次钱包交易总数量
        sb.append(vo.getElectTkDealNum() + FileWriterBase.FIELD_DELIM);//电子票计值钱包交易总数量
        sb.append(this.convertFenToYuan(vo.getElectTkDealFee()) + FileWriterBase.FIELD_DELIM);//电子票计值钱包交易总金额 
        //20190829 hejj
        sb.append(vo.getQrEntryNum() + FileWriterBase.FIELD_DELIM);//二维码进站总数量
        //20191110 hejj
        sb.append(vo.getUpdateNonCashTctNum() + FileWriterBase.FIELD_DELIM);//次票非现金更新数量
        //20200506 hejj
        sb.append(vo.getBomSaleTctNum() + FileWriterBase.FIELD_DELIM);//2BOM实体次票发售总数量
        sb.append(this.convertFenToYuan(vo.getBomSaleTctFee()) + FileWriterBase.FIELD_DELIM);//3BOM实体次票发售总金额

        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());

        return sb.toString();
    }

}
