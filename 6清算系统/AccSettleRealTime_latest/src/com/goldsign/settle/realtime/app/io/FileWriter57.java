/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord54;
import com.goldsign.settle.realtime.app.vo.FileRecord57;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Vector;

/**
 *
 * @author hejj
 */
//退卡记录（即时、非即时的退款结果）
public class FileWriter57 extends FileWriterBase {

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
        /*
        String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;
        FileRecord57 vo;
        String line;
        try {
            fos = new FileOutputStream(fileNameBcp);
            osr = new OutputStreamWriter(fos, FileWriterBase.CHARSET);
            bw = new BufferedWriter(osr);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecord57) records.get(i);
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
        FileRecord57 vo = (FileRecord57) vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getRecordVer()+ FileWriterBase.FIELD_DELIM);//记录版本20180528
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//2线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//2站点代码
        sb.append(vo.getDevTypeId() + FileWriterBase.FIELD_DELIM);//3设备类型
        sb.append(vo.getDeviceId() + FileWriterBase.FIELD_DELIM);//4设备代码
        sb.append(vo.getSamLogicalId() + FileWriterBase.FIELD_DELIM);//5SAM卡逻辑卡号
        sb.append(vo.getSamTradeSeq() + FileWriterBase.FIELD_DELIM);//6SAM卡脱机交易流水号

        sb.append(vo.getCardMainId() + FileWriterBase.FIELD_DELIM);//7票卡主类型
        sb.append(vo.getCardSubId() + FileWriterBase.FIELD_DELIM);//7票卡子类型
        sb.append(vo.getCardLogicalId() + FileWriterBase.FIELD_DELIM);//8票逻辑ID
        sb.append(vo.getCardPhysicalId() + FileWriterBase.FIELD_DELIM);//9票物理ID
        sb.append(vo.getCardStatusId() + FileWriterBase.FIELD_DELIM);//10票状态

        sb.append(this.convertFenToYuan(vo.getReturnBalanceFee()) + FileWriterBase.FIELD_DELIM);//11退卡内金额
        sb.append(this.convertFenToYuan(vo.getReturnDepositFee()) + FileWriterBase.FIELD_DELIM);//12退押金
        sb.append(this.convertFenToYuan(vo.getPenaltyFee()) + FileWriterBase.FIELD_DELIM);//13罚款
        sb.append(vo.getPenaltyReasonId() + FileWriterBase.FIELD_DELIM);//14罚款原因
        sb.append(vo.getCardConsumeSeq() + FileWriterBase.FIELD_DELIM);//15票卡扣款交易计数

        sb.append(vo.getReturnType() + FileWriterBase.FIELD_DELIM);//16退款类型
        sb.append(vo.getReturnDatetime() + FileWriterBase.FIELD_DELIM);//17日期时间
        sb.append(vo.getReceiptId() + FileWriterBase.FIELD_DELIM);//18凭证ID
        sb.append(vo.getApplyDatetime() + FileWriterBase.FIELD_DELIM);//19申请日期时间
        sb.append(vo.getTac() + FileWriterBase.FIELD_DELIM);//20MAC/TAC//交易认证码

        sb.append(vo.getOperatorId() + FileWriterBase.FIELD_DELIM);//21操作员代码
        sb.append(vo.getShiftId() + FileWriterBase.FIELD_DELIM);//22BOM班次序号
        sb.append(this.convertFenToYuan(vo.getAuxiFee()) + FileWriterBase.FIELD_DELIM);//23手续费
        sb.append(vo.getCardAppFlag() + FileWriterBase.FIELD_DELIM);//24卡应用标识

        sb.append(vo.getBusTacDealType() + FileWriterBase.FIELD_DELIM);//25TAC交易类型
        sb.append(vo.getBusTacDevId() + FileWriterBase.FIELD_DELIM);//26TAC终端编号

        sb.append(vo.getMobileNo() + FileWriterBase.FIELD_DELIM);//手机号码
        sb.append(vo.getPaidChannelType() + FileWriterBase.FIELD_DELIM);//支付渠道类型
        sb.append(vo.getPaidChannelCode() + FileWriterBase.FIELD_DELIM);//支付渠道代码

        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());

        return sb.toString();
    }

}
