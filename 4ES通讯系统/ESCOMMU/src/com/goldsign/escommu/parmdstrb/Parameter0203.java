package com.goldsign.escommu.parmdstrb;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.exception.ParameterException;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.vo.ParaGenDtl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import org.apache.log4j.Logger;

public class Parameter0203 extends ParameterBase {

    private static final String PARMTYPE = "0203";
    private static final int[] FORMAT = {16, 1, 4, 4, 2};
    private static Logger logger = Logger.getLogger(Parameter0203.class.getName());

    public boolean formParaFile() {
        boolean result = false;
        //	DateHelper.screenPrint("Parameter0203 started! ");
        try {
            //get parameter data
            Vector recV = getRecordFromTable(paraGenDtl);
            if (recV == null) {
                throw new ParameterException("从参数表取记录错误! ");
            }
            //记录从数据库取出将要写文件的记录数
            this.setDbRecordNum(recV);

            //format data
            Vector formatedRecV = formatRecord(PARMTYPE, recV, FORMAT);

            //write data to file
            result = writeDataToFile(formatedRecV);
            if (!result) {
                throw new ParameterException("写参数文件 " + paraFileName + " 错误! ");
            }
            logger.info("参数文件 " + paraFileName + " 生成成功！ ");
        } catch (Exception e) {
            logger.error("Parameter0203 错误! " + e);
            result = false;
        }
        DateHelper.screenPrint("Parameter0203 结束! ");
        return result;
    }

    private Vector getRecordFromTable(ParaGenDtl paraGenDtl) {
        Vector recV = null;
        boolean result;
        String sqlStr = "select sam_logical_id,sam_type_id,line_id,station_id,device_id,dev_type_id from "+AppConstant.COM_ST_P+"OP_PRM_SAM_LIST where record_flag=? order by sam_logical_id";
        ArrayList pStmtValues = new ArrayList();
        pStmtValues.clear();
        pStmtValues.add(paraGenDtl.getVerType());
        try {
            result = dbHelper.getFirstDocument(sqlStr, pStmtValues.toArray());

            if (result) {
                recV = new Vector();
            }
            while (result) {
                String[] fields = new String[FORMAT.length];
                fields[0] = dbHelper.getItemValue("sam_logical_id");
                fields[1] = dbHelper.getItemValue("sam_type_id");
                fields[2] = dbHelper.getItemValue("line_id") + dbHelper.getItemValue("station_id");
                fields[3] = dbHelper.getItemValue("device_id");
                fields[4] = dbHelper.getItemValue("dev_type_id");
                recV.add(fields);
                result = dbHelper.getNextDocument();
            }
        } catch (SQLException e) {
            logger.error("访问表 sam_list 错误! " + e);
            return null;
        }
        return recV;
    }

    @Override
    public String getParmType() {
        return PARMTYPE;
    }
}
