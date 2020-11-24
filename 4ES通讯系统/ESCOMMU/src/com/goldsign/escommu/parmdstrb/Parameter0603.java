package com.goldsign.escommu.parmdstrb;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.exception.ParameterException;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.vo.ParaGenDtl;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.log4j.Logger;

public class Parameter0603 extends ParameterBase {

    private static int LIMIT_NUM = 20000;
    private static final String PARMTYPE = "0603";
    private static final int[] FORMAT = {16, 3};
    private static Logger logger = Logger.getLogger(Parameter0603.class.getName());

    public boolean formParaFile() {
        boolean result = false;
        //	DateHelper.screenPrint("Parameter0603 started! ");
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
            logger.error("Parameter0603 错误! " + e);
            result = false;
        }
        DateHelper.screenPrint("Parameter0603 结束! ");
        return result;
    }

    private Vector getRecordFromTable(ParaGenDtl paraGenDtl) {
        Vector recV = new Vector();		//black list can be empty
        boolean result;
        String sqlStr = "select CARD_LOGICAL_ID,action_type "
                + "from "+AppConstant.COM_ST_P+"OP_PRM_BLACK_LIST_OCT "
                + "where to_char(gen_datetime,'yyyyMMdd')<=to_char(SYSDATE,'yyyyMMdd') "
                + "order by CARD_LOGICAL_ID asc";
        try {
            result = dbHelper.getFirstDocument(sqlStr);
            while (result) {
                String[] fields = new String[FORMAT.length];
                fields[0] = dbHelper.getItemValue("CARD_LOGICAL_ID");
                fields[1] = dbHelper.getItemValue("action_type");
                recV.add(fields);
                result = dbHelper.getNextDocument();
            }
        } catch (SQLException e) {
            logger.error("访问表 card_black_list 错误! " + e);
            return null;
        }

        //一城通黑名单限定改为从配置中读取
        int limitOct = this.getLimit();
        if (this.isLegalLimit(limitOct)) {
            recV = this.getRecordNotOverLimit(recV, limitOct);
        }
        return recV;
    }

    private boolean isLegalLimit(int limit) {
        if (limit == -1 || limit == 0) {
            return false;
        }
        return true;
    }

    private int getLimit() {

        boolean result;
        int n;
        String[] values = {"blacklist.oct.max"};
        String sqlStr = "select  config_name, config_value from "+AppConstant.COM_ST_P+"OP_CFG_SYS where config_name=? ";
        try {
            result = dbHelper.getFirstDocument(sqlStr, values);
            if (!result) {
                return -1;
            }
            n = dbHelper.getItemIntValue("config_value");
        } catch (SQLException e) {
            logger.error("获取一城通黑名单限制数错误 " + e);
            return -1;
        }
        return n;

    }

    public Vector getRecordNotOverLimit(Vector v, int limit) {
        Vector vLimit = new Vector();
        int size = v.size();
        int randomIndex;
        int randomNum;
        //集合数超过限定数
        if (size > limit) {
            randomIndex = (int) (Math.random() * size);//随机数作为集合索引
            randomNum = size - randomIndex;//随机数索引包含的数量
            System.out.println("randomIndex=" + randomIndex);
            if (randomNum >= limit) {//随机数索引包含的数量大于等于限定数
                vLimit.addAll(v.subList(randomIndex, randomIndex + limit));//取从随机数索引开始的limit条数量

            } else {//随机数索引包含的数量小于限定数
                vLimit.addAll(v.subList(0, limit - randomNum));//缺少的数从头开始取
                vLimit.addAll(v.subList(randomIndex, size));//取从随机数索引开始
            }

        } else{//集合数不超过限定数

            vLimit.addAll(v);
        }
        return vLimit;
    }

    @Override
    public String getParmType() {
        return PARMTYPE;
    }

    /*
    public static void main(String[] args) {
        Parameter0603 p = new Parameter0603();
        for (int i = 0; i < 100; i++) {
            p.test();
        }
    }

    public void test() {
        Parameter0603 p = new Parameter0603();
        Vector v = new Vector();
        for (int i = 1; i <= 100; i++) {
            v.add(new Integer(i).toString());
        }
        int limit = 99;
        Vector vl = p.getRecordNotOverLimit(v, limit);
        System.out.println("count=" + vl.size());

        for (int i = 0; i < vl.size(); i++) {
            System.out.println(vl.get(i));
        }

    }*/
}
