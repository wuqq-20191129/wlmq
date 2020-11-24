/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord50;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.Vector;

/**
 *
 * @author hejj
 */
//单程票售票记录
public class FileWriter50 extends FileWriterBase {

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
        /*
        String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;
        FileRecord50 vo;
        String line;
        try {
            fos = new FileOutputStream(fileNameBcp);
            osr = new OutputStreamWriter(fos, FileWriterBase.CHARSET);
            bw = new BufferedWriter(osr);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecord50) records.get(i);
                line = this.getLine(vo);
                bw.write(line);
                bw.flush();
            }

        } catch (Exception ex) {
            throw new FileWriteException(ex.getMessage());

        } finally {
            this.closeFile(fos, osr, bw);
        }
        */


    }

    @Override
    public String getLine(FileRecordBase vob) {
        FileRecord50 vo =(FileRecord50)vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getRecordVer()+ FileWriterBase.FIELD_DELIM);//记录版本20180528
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//站点代码
        sb.append(vo.getDevTypeId() + FileWriterBase.FIELD_DELIM);//设备类型
        sb.append(vo.getDeviceId() + FileWriterBase.FIELD_DELIM);//设备代码
        sb.append(vo.getSamLogicalId() + FileWriterBase.FIELD_DELIM);//SAM卡逻辑卡号（单程票）
        sb.append(vo.getSamTradeSeq() + FileWriterBase.FIELD_DELIM);//SAM卡脱机交易流水号（单程票）
        sb.append(vo.getSaleTime() + FileWriterBase.FIELD_DELIM);//时间
        sb.append(vo.getPayType() + FileWriterBase.FIELD_DELIM);//支付方式
        sb.append(vo.getCardLogicIdPay() + FileWriterBase.FIELD_DELIM);//支付票卡逻辑卡号
        sb.append(vo.getCardCountUsed() + FileWriterBase.FIELD_DELIM);//单程票使用次数
        sb.append(vo.getCardLogicalId() + FileWriterBase.FIELD_DELIM);//单程票逻辑ID
        sb.append(vo.getCardPhysicalId() + FileWriterBase.FIELD_DELIM);//单程票物理ID
        sb.append(vo.getCardStatusId() + FileWriterBase.FIELD_DELIM);//单程票状态
        sb.append(this.convertFenToYuan(vo.getSaleFee()) + FileWriterBase.FIELD_DELIM);//充值金额
        sb.append(vo.getCardMainId() + FileWriterBase.FIELD_DELIM);//单程票卡主类型
        sb.append(vo.getCardSubId() + FileWriterBase.FIELD_DELIM);//单程票卡子类型
        sb.append(vo.getZoneId() + FileWriterBase.FIELD_DELIM);//区段代码
        sb.append(vo.getTac() + FileWriterBase.FIELD_DELIM);//MAC/TAC
        sb.append(vo.getDepositType() + FileWriterBase.FIELD_DELIM);//成本押金类型
        sb.append(this.convertFenToYuan(vo.getDepositFee()) + FileWriterBase.FIELD_DELIM);//成本押金金额
        sb.append(vo.getOperatorId() + FileWriterBase.FIELD_DELIM);//操作员代码
        sb.append(vo.getShiftId() + FileWriterBase.FIELD_DELIM);//BOM班次序号
        sb.append(this.convertFenToYuan(vo.getAuxiFee()) + FileWriterBase.FIELD_DELIM);//手续费
        sb.append(vo.getCardAppFlag()+ FileWriterBase.FIELD_DELIM);//卡应用标识
        sb.append(vo.getBusTacDealType()+ FileWriterBase.FIELD_DELIM);//TAC交易类型
        sb.append(vo.getBusTacDevId()+ FileWriterBase.FIELD_DELIM);//TAC终端编号
        
         sb.append(vo.getOrderNo()+ FileWriterBase.FIELD_DELIM);//订单号
        
        this.addCommonToBuff(sb, vo);
        /*
        sb.append(vo.getBalanceWaterNo()+ FileWriterBase.FIELD_DELIM);//清算流水号
        sb.append(vo.getFileName()+ FileWriterBase.FIELD_DELIM);//文件名称
        sb.append(vo.getCheckFlag());//校验标识
        */
        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
}
