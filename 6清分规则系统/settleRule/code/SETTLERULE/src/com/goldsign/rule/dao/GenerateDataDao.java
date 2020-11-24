/*
 * 文件名：GenerateDataDao
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.dao;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.rule.vo.OperResult;
import org.apache.log4j.Logger;

/*
 * 生成权重数据 DAO
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-11-11
 */

public class GenerateDataDao {
    
    private static Logger logger = Logger.getLogger(GenerateDataDao.class.getName());
    
    /**
     * 查询
     * @param vo
     * @return 
     */
    public OperResult generate() throws Exception {
        DbHelper dbHelper = null;
        String strSql = "";
        String reMsg = "未生成数据！";
        int result = 1;
        OperResult map = new OperResult();
        
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            dbHelper.setAutoCommit(false);
            
            //执行存储过程
            strSql = "{call UP_SR_GET_OD_PROPORTION(?,?)}";
            
            int[] pInIndexes = {};//存储过程输入参数索引列表
            Object[] pInStmtValues = {};//存储过程输入参数值

            int[] pOutIndexes = {1,2};//存储过程输出参数索引列表
            int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_INTEGER,DbHelper.PARAM_OUT_TYPE_VACHAR};//存储过程输出参数值类型

            dbHelper.runStoreProcForOracle(strSql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes);//执行存储过程
            result = dbHelper.getOutParamIntValue(1);//存储过程返回结果代码
            reMsg = dbHelper.getOutParamStringValue(2);//存储过程返回结果代码
            
            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);
            
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        
        map.setRetMsg(reMsg);
        map.setUpdateNum(result);
        
        return map;
    }
    
}
