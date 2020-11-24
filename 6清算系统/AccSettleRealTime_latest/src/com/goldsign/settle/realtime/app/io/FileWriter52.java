/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord50;
import com.goldsign.settle.realtime.app.vo.FileRecord52;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Vector;

/**
 *
 * @author hejj
 */
//记名卡申请
public class FileWriter52 extends FileWriterBase {

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
        /*
        String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        DataOutputStream dos =null;// new DataOutputStream(os);
        BufferedWriter bw = null;
        FileRecord52 vo;
        String line;
        char[] cs;
        try {
            fos = new FileOutputStream(fileNameBcp);
            dos=new DataOutputStream(fos);
           // osr = new OutputStreamWriter(fos, FileWriterBase.CHARSET);
          //  bw = new BufferedWriter(osr);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecord52) records.get(i);
                line = this.getLine(vo);
                cs = line.toCharArray();
                for (char c : cs) {
                    dos.write((int)c);
                }
                // bw.write(line);
                dos.flush();
            }

        } catch (Exception ex) {
            throw new FileWriteException(ex.getMessage());

        } finally {
            this.closeFile(fos,dos);
        }
        */
    }
/*
    public void writeFileByLine(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;
        FileRecordBase vo;
        String line;
        try {
            fos = new FileOutputStream(fileNameBcp);
            osr = new OutputStreamWriter(fos, FileWriterBase.CHARSET);
            bw = new BufferedWriter(osr);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecordBase) records.get(i);
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
*/
    public String getLine(FileRecordBase vob) {
        FileRecord52 vo =(FileRecord52)vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getRecordVer()+ FileWriterBase.FIELD_DELIM);//记录版本20180528
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//2线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//2站点代码
        sb.append(vo.getDevTypeId() + FileWriterBase.FIELD_DELIM);//3设备类型
        sb.append(vo.getDeviceId() + FileWriterBase.FIELD_DELIM);//4设备代码

        sb.append(vo.getApplyName() + FileWriterBase.FIELD_DELIM);//5姓名
        sb.append(vo.getApplySex() + FileWriterBase.FIELD_DELIM);//6性别
        sb.append(vo.getIdentityType() + FileWriterBase.FIELD_DELIM);//7证件类型
        sb.append(vo.getIdentityId() + FileWriterBase.FIELD_DELIM);//8证件号码
        sb.append(vo.getExpiredDate() + FileWriterBase.FIELD_DELIM);//9证件有效期
        sb.append(vo.getTelNo() + FileWriterBase.FIELD_DELIM);//10电话
        sb.append(vo.getFax() + FileWriterBase.FIELD_DELIM);//11传真
        sb.append(vo.getAddress() + FileWriterBase.FIELD_DELIM);//12通讯地址

        sb.append(vo.getOperatorId() + FileWriterBase.FIELD_DELIM);//13操作员代码
        sb.append(vo.getApplyDatetime() + FileWriterBase.FIELD_DELIM);//14申请日期
        sb.append(vo.getShiftId() + FileWriterBase.FIELD_DELIM);//15BOM班次序号
        sb.append(vo.getCardAppFlag() + FileWriterBase.FIELD_DELIM);//16卡应用标识
        sb.append(vo.getCardMainId() + FileWriterBase.FIELD_DELIM);//17卡主类
        sb.append(vo.getCardSubId() + FileWriterBase.FIELD_DELIM);//17卡子类
        sb.append(vo.getApplyBusinessType() + FileWriterBase.FIELD_DELIM);//18业务类型



        this.addCommonToBuff(sb, vo);

        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
}
