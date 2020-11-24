/*
 * 文件名：DistanceProportionDao
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.dao;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FrameDBUtil;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.rule.env.RuleConstant;
import com.goldsign.rule.vo.DistanceProportionVo;
import com.goldsign.rule.vo.OperResult;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.log4j.Logger;


/*
 * 线路里程权重DAO
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-11-7
 */

public class DistanceProportionDao {
    
    private static Logger logger = Logger.getLogger(DistanceProportionDao.class.getName());
    
    /**
     * 查询
     * @param vo
     * @return 
     */
    public Vector select(DistanceProportionVo vo) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        String strWhere = "";
        boolean result = false;
        Vector v = new Vector();
        FrameDBUtil util = new FrameDBUtil();
        
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            
            //添加查询条件
            strWhere += FramePubUtil.sqlWhereAnd(vo.getRecordFlag(),"record_flag");
            strWhere += FramePubUtil.sqlWhereAnd(vo.getoLineId(),"o_line_id");
            strWhere += FramePubUtil.sqlWhereAnd(vo.getoStationId(),"o_station_id");
            strWhere += FramePubUtil.sqlWhereAnd(vo.getdLineId(),"d_line_id");
            strWhere += FramePubUtil.sqlWhereAnd(vo.getdStationId(),"d_station_id");
            
            strSql = "select * from sr_proportion where 1=1 ";
            result = dbHelper.getFirstDocument(strSql+strWhere);
            
            while (result) {
                DistanceProportionVo pg = setRecordText(dbHelper, util);
                pg.setVersion(dbHelper.getItemValue("version"));
                pg.setRecordFlag(dbHelper.getItemValue("record_flag"));
                pg.setRecordFlagText(util.getTextByCode(pg.getRecordFlag(), RuleConstant.PARAMS_VERSION, FrameDBUtil.PUB_FLAGS));
                v.add(pg);
                
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        return v;
    }

    /**
     * 取结果集
     * @param dbHelper
     * @param util
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    private DistanceProportionVo setRecordText(DbHelper dbHelper, FrameDBUtil util) throws SQLException, Exception {
        DistanceProportionVo pg = new DistanceProportionVo();
        pg.setoStationId(dbHelper.getItemValue("o_station_id"));
        pg.setoLineId(dbHelper.getItemValue("o_line_id"));
        pg.setdLineId(dbHelper.getItemValue("d_line_id"));
        pg.setdStationId(dbHelper.getItemValue("d_station_id"));
        pg.setDispartLineId(dbHelper.getItemValue("dispart_line_id"));
        pg.setInPrecent(String.valueOf(dbHelper.getItemDoubleValue("in_percent")));
        
        //取代码中文义
        pg.setoLineIdText(util.getTextByCode(pg.getoLineId(), RuleConstant.LINES));
        pg.setdLineIdText(util.getTextByCode(pg.getdLineId(), RuleConstant.LINES));
        pg.setoStationIdText(util.getTextByCode(pg.getoStationId(), pg.getoLineId(), RuleConstant.STATIONS));
        pg.setdStationIdText(util.getTextByCode(pg.getdStationId(), pg.getdLineId(), RuleConstant.STATIONS));
        pg.setDispartLineIdText(util.getTextByCode(pg.getDispartLineId(), RuleConstant.LINES));
        
        return pg;
    }

    
    /**
     * 实时查询
     * @param vo
     * @return 
     */
    public OperResult queryStore(DistanceProportionVo vo) throws Exception {
        DbHelper dbHelper = null;
        String strSql = "";
        String reMsg = "未生成数据！";
        int reCode = 1;
        boolean result = false;
        OperResult map = new OperResult();
        Vector v = new Vector();
        FrameDBUtil util = new FrameDBUtil();
        
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            dbHelper.setAutoCommit(false);
            
            //执行存储过程
            strSql = "{call UP_SR_GET_OD_PROPORTION_CUR(?,?,?,?,?,?,?)}";
            
            int[] pInIndexes = {1,2,3,4};//存储过程输入参数索引列表
            Object[] pInStmtValues = {vo.getoLineId(),vo.getoStationId(),vo.getdLineId(),vo.getdStationId()};//存储过程输入参数值

            int[] pOutIndexes = {5,6,7};//存储过程输出参数索引列表
            int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_CURSOR, DbHelper.PARAM_OUT_TYPE_INTEGER, DbHelper.PARAM_OUT_TYPE_VACHAR};//存储过程输出参数值类型

            dbHelper.runStoreProcForOracle(strSql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes);//执行存储过程
            
            result = dbHelper.getFirstDocumentForOracle();//判断结果集是否为空
            reCode = dbHelper.getOutParamIntValue(6);//存储过程返回结果代码
            reMsg = dbHelper.getOutParamStringValue(7);//存储过程返回结果代码
            
            if(reCode == 0){
                while(result){
                    DistanceProportionVo pg = setRecordText(dbHelper, util);
                    v.add(pg);

                    result = dbHelper.getNextDocument();
                }
            }
            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);
            
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        
        map.setRetMsg(reMsg);
        map.setUpdateNum(reCode);
        map.setUpdateOb(v);
        
        return map;
    }

}
