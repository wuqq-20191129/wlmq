/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.fm.dao;

import com.goldsign.fm.common.AppConstant;
import com.goldsign.fm.common.PubUtil;
import com.goldsign.fm.vo.StationVo;
import com.goldsign.lib.db.util.DbHelper;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 * 车站
 * @author Administrator
 */
public class StationDao {
    private static Logger logger = Logger.getLogger(StationDao.class.getName());
    public HashMap getStationAllByLine() throws Exception {
        Vector stations = this.getStationAll();
        StationVo vo;
        HashMap hm = new HashMap();
        for(int i=0;i<stations.size();i++){
            vo=(StationVo)stations.get(i);
            this.divideStation(vo, hm);
        }
        return hm;

    }
    private void divideStation(StationVo vo ,HashMap hm){
        String lineId =vo.getBelongLineId();// vo.getLineId();
        Vector v;
        if(hm.containsKey(lineId))
        {
            ((Vector)hm.get(lineId)).add(vo);
        }
        else{
            v = new Vector();
            v.add(vo);
            hm.put(lineId, v);
        }
    }
    public Vector getStationAll() throws Exception {
        DbHelper dbHelper = null;
        String sql = "select line_id,belong_line_id,station_id,chinese_name,english_name " +
                "from w_acc_st.w_op_prm_station " +
                "where record_flag=? " +
                "order by sequence";
        Object[] values={"0"};
        boolean result = false;
        Vector v = new Vector();
        StationVo vo;

        try {
            dbHelper = new DbHelper("",AppConstant.dbcpHelper1.getConnection());
            result = dbHelper.getFirstDocument(sql,values);
            while (result) {
                vo = this.getResultRecord(dbHelper);
                v.add(vo);
                result = dbHelper.getNextDocument();

            }


        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return v;

    }

    private StationVo getResultRecord(DbHelper dbHelper) throws SQLException {
        StationVo vo = new StationVo();
        vo.setLineId(dbHelper.getItemValue("line_id"));
        vo.setBelongLineId(dbHelper.getItemValue("belong_line_id"));
        vo.setStationId(dbHelper.getItemValue("station_id"));
        vo.setChineseName(dbHelper.getItemValue("chinese_name"));
        vo.setEnglishName(dbHelper.getItemValue("english_name"));

        return vo;
    }

}


