package com.goldsign.escommu.parmdstrb;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.exception.ParameterException;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.vo.ParaGenDtl;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.log4j.Logger;

public class Parameter0604 extends ParameterBase {

    private static final String PARMTYPE = "0604";
    private static final int[] FORMAT = {16, 16, 3};
    private static Logger logger = Logger.getLogger(Parameter0604.class.getName());

    public boolean formParaFile() {
        boolean result = false;
        //	DateHelper.screenPrint("Parameter0604 started! ");
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
            logger.error("Parameter0604 错误! " + e);
            result = false;
        }
        DateHelper.screenPrint("Parameter0604 结束! ");
        return result;
    }

    private Vector getRecordFromTable(ParaGenDtl paraGenDtl) {
        Vector recV = new Vector();		//black list can be empty
        boolean result;
        String sqlStr = "select begin_logical_id,end_logical_id,action_type "
                + "from "+AppConstant.COM_ST_P+"OP_PRM_BLACK_LIST_OCT_SEC "
                + "where to_char(gen_datetime,'yyyyMMdd')<=to_char(SYSDATE,'yyyyMMdd') "
                + "order by begin_logical_id,end_logical_id";
        try {
            result = dbHelper.getFirstDocument(sqlStr);
            while (result) {
                String[] fields = new String[FORMAT.length];
                fields[0] = dbHelper.getItemValue("begin_logical_id");
                fields[1] = dbHelper.getItemValue("end_logical_id");
                fields[2] = dbHelper.getItemValue("action_type");
                recV.add(fields);
                result = dbHelper.getNextDocument();
            }
        } catch (SQLException e) {
            logger.error("访问表 card_black_list 错误! " + e);
            return null;
        }
        return recV;
    }

    @Override
    public String getParmType() {
        return PARMTYPE;
    }
}
