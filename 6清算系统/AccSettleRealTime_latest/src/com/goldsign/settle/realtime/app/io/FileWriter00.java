/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord00;

import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.util.TradeUtil;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Vector;

/**
 *
 * @author hejj
 * BOM班次数据
 */
public class FileWriter00 extends FileWriterBase{

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        pathBcp =TradeUtil.getDirForBalanceWaterNo(pathBcp, true);//added by hejj 20160622
        String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;
        FileRecord00 vo;
        String line;
        try {
            fos = new FileOutputStream(fileNameBcp);
            osr = new OutputStreamWriter(fos, FileWriterBase.CHARSET);
            bw = new BufferedWriter(osr);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecord00) records.get(i);
                line = this.getLine(vo);
                bw.write(line);
                bw.flush();
            }

        } catch (Exception ex) {
            throw new FileWriteException(ex.getMessage());

        } finally {
            this.closeFile(fos, osr, bw);
        }
    }
    
    @Override
    public String getLine(FileRecordBase vob) {
        FileRecord00 vo =(FileRecord00)vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getWaterNo() + FileWriterBase.FIELD_DELIM);//1流水号
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//2线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//2站点代码
        sb.append(vo.getDevTypeId() + FileWriterBase.FIELD_DELIM);//2设备类型
        sb.append(vo.getDeviceId() + FileWriterBase.FIELD_DELIM);//2设备代码
        
        sb.append(vo.getOperatorId() + FileWriterBase.FIELD_DELIM);//3BOM操作员工号
        sb.append(vo.getShiftId() + FileWriterBase.FIELD_DELIM);//4BOM班次
        sb.append(vo.getStartTime() + FileWriterBase.FIELD_DELIM);//5班次开始时间
        sb.append(vo.getEndTime() + FileWriterBase.FIELD_DELIM);//6班次结束时间
        
        sb.append(vo.getSaleTotalNum() + FileWriterBase.FIELD_DELIM);//发售总数量
        sb.append(this.convertFenToYuan(vo.getSaleTotalFee()) + FileWriterBase.FIELD_DELIM);//11发售总金额
         sb.append(vo.getChargeTotalNum() + FileWriterBase.FIELD_DELIM);//充值总数量
        sb.append(this.convertFenToYuan(vo.getChargeTotalFee()) + FileWriterBase.FIELD_DELIM);//16充值总金额
         sb.append(vo.getUpdateTotalNum() + FileWriterBase.FIELD_DELIM);//更新总数量
        sb.append(this.convertFenToYuan(vo.getUpdateTotalFee()) + FileWriterBase.FIELD_DELIM);//23更新总金额
        sb.append(this.convertFenToYuan(vo.getUpdateTotalFeeCash()) + FileWriterBase.FIELD_DELIM);//24更新现金总金额
         sb.append(vo.getAdminTotalNum() + FileWriterBase.FIELD_DELIM);//行政处理总数量
        sb.append(this.convertFenToYuan(vo.getAdminTotalFeeReturn()) + FileWriterBase.FIELD_DELIM);//30行政处理退款总金额
        sb.append(this.convertFenToYuan(vo.getAdminTotalFeeIncome()) + FileWriterBase.FIELD_DELIM);//31行政处理罚金总金额
        sb.append(vo.getDelayTotalNum() + FileWriterBase.FIELD_DELIM);//延期总数量
       
        sb.append(vo.getReturnTotalNum() + FileWriterBase.FIELD_DELIM);//即时退款总数量
        sb.append(this.convertFenToYuan(vo.getReturnTotalFee()) + FileWriterBase.FIELD_DELIM);//44即时退款总金额
        sb.append(vo.getReturnNonTotalNum() + FileWriterBase.FIELD_DELIM);//非即时退款总数量
        sb.append(this.convertFenToYuan(vo.getReturnNonTotalFee()) + FileWriterBase.FIELD_DELIM);//51非即时退款总金额
        sb.append(vo.getReturnNonAppTotalNum() + FileWriterBase.FIELD_DELIM);//54非即时退款申请总数量
        sb.append(vo.getChargeUpdateTotalNum() + FileWriterBase.FIELD_DELIM);//冲正总数量
        sb.append(this.convertFenToYuan(vo.getChargeUpdateTotalFee()) + FileWriterBase.FIELD_DELIM);//59冲正总金额
        sb.append(vo.getUnlockTotalNum() + FileWriterBase.FIELD_DELIM);//解锁总数量
        sb.append(this.convertFenToYuan(vo.getTotalNoCashFee()) + FileWriterBase.FIELD_DELIM);//63班次应收非现金金额总金额
        sb.append(this.convertFenToYuan(vo.getTotalFee()) + FileWriterBase.FIELD_DELIM);//64班次应收金额总金额
        
        this.addCommonToBuff(sb, vo);
        /*
        sb.append(vo.getBalanceWaterNo()+ FileWriterBase.FIELD_DELIM);//清算流水号
        sb.append(vo.getFileName()+ FileWriterBase.FIELD_DELIM);//文件
        sb.append(vo.getCheckFlag());//校验标志
         */

        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    
}
