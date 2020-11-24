/*
 * 文件名：LineStationDao
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.dao;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FrameDBUtil;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.frame.util.FrameUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.rule.env.RuleConstant;
import com.goldsign.rule.vo.LineStationVo;
import java.util.Vector;
import org.apache.log4j.Logger;


/*
 * 清分规则系统 参数设置DAO
 * @author     wangkejia
 * @version    V1.0
 */

public class LineStationDao {
    
      static Logger logger = Logger.getLogger(
            LineStationDao.class.getName());

    
    public LineStationDao() {
        super();
    }

    /**
     * 查询
     * @lineStation vo
     * @return 
     */
   public Vector select(LineStationVo vo) throws Exception {
        DbHelper dbHelper = null;
        String strSql = " ";
        String strWhere = " ";
        Vector result = new Vector();
        boolean result1=false;
        FrameDBUtil util = new FrameDBUtil();
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            dbHelper.setAutoCommit(false);

          if(!result1){
         strSql="select distinct*from (select a.line_name,b.chinese_name,b.english_name,b.record_flag "
                 + "from op_prm_line a,op_prm_station b where a.line_id=b.line_id";
            strWhere +=FramePubUtil.sqlWhereAnd(vo.getLine(),"a.line_id");
            strWhere +=FramePubUtil.sqlWhereAnd(vo.getStationId(),"b.station_id");
            strWhere += FramePubUtil.sqlWhereAnd(vo.getRecordFlag(),"a.record_flag");
            strWhere += FramePubUtil.sqlWhereAnd(vo.getRecordFlag(),"b.record_flag");
            strWhere +=")";
           result1 = dbHelper.getFirstDocument(strSql+strWhere);
      }
                      //如果站点是交叉站点，则另作判断
      if(vo.getStationId()!=null&&vo.getStationId().length()!=0){
          DbHelper dbHelper1 = null;
          dbHelper1 = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
          String strSql2=" select 1 from op_prm_transfer_station where line_id='"+vo.getLine()+"' and station_id='"+vo.getStationId()+"'";
          boolean result2=dbHelper1.getFirstDocument(strSql2);
          if(result2){
           strWhere=" ";
            strSql="select distinct*from (select distinct d.line_id,d.station_id,d.chinese_name,d.english_name,d.record_flag,e.line_name "
                    + "from op_prm_transfer_station c, op_prm_station d, op_prm_line e "
                    + "where ((c.transfer_line_id = e.line_id and c.transfer_station_id = d.station_id and c.record_flag = '0') or (1=1 ";
            strWhere +=FramePubUtil.sqlWhereAnd(vo.getLine(),"d.line_id");
            strWhere +=FramePubUtil.sqlWhereAnd(vo.getStationId(),"d.station_id");
            strWhere +=")) and d.line_id = e.line_id ";
            strWhere +=FramePubUtil.sqlWhereAnd(vo.getLine(),"c.line_id");
            strWhere +=FramePubUtil.sqlWhereAnd(vo.getStationId(),"c.station_id");
            strWhere += FramePubUtil.sqlWhereAnd(vo.getRecordFlag(),"d.record_flag");
            strWhere +=")";
 
            result1 = dbHelper.getFirstDocument(strSql+strWhere);
           }
          }
            while (result1) {
                LineStationVo pg = new LineStationVo();
                pg.setLine(FrameUtil.GbkToIso(dbHelper.getItemValue("line_name")));
                pg.setChineseStation(FrameUtil.GbkToIso(dbHelper.getItemValue("chinese_name")));
                pg.setEnglishStation(dbHelper.getItemValue("english_name"));
                pg.setRecordFlag(dbHelper.getItemValue("record_flag"));
                pg.setRecordFlagText(util.getTextByCode(pg.getRecordFlag(), RuleConstant.PARAMS_VERSION, FrameDBUtil.PUB_FLAGS));
                result.add(pg);
                
                result1 = dbHelper.getNextDocument();
            }
            
            dbHelper.setAutoCommit(true);

        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        return result;
    }

 
}