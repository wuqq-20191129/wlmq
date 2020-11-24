/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.util.PubUtil;

import com.goldsign.settle.realtime.frame.vo.FileTccConfigVo;
import java.sql.SQLException;

import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class FileTccConfigDao {

    private static Logger logger = Logger.getLogger(FileTccConfigDao.class.getName());
    private static String ONCE_ONE_DAY = "1";
    private static String MANU_ONE_DAY = "9";
    private static String MANU_VALID_YES = "1";
     private static String MANU_VALID_NO = "0";
    public Vector<FileTccConfigVo> getConfigs() throws Exception {
        Vector<FileTccConfigVo> v = this.getConfigsData();
        this.updateConfigsOnlyOnce();//更新仅1次配置
        return v;
    }
    public Vector<FileTccConfigVo> getConfigsData() throws Exception {
        Vector<FileTccConfigVo> v = new Vector<FileTccConfigVo>();
        String sql = "select water_no,id,period,manu_valid_flag,file_name_base,remark "
                + "from " + FrameDBConstant.DB_ST + "w_st_sys_cfg_tcc where period=? or ( period=? and  manu_valid_flag=? ) order by water_no ";
        DbHelper dbHelper = null;
        boolean result;
        FileTccConfigVo vo;
        Object[] values ={ONCE_ONE_DAY,MANU_ONE_DAY,MANU_VALID_YES};
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql,values);
            while (result) {
                vo = this.getVo(dbHelper);
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

    private FileTccConfigVo getVo(DbHelper dbHelper) throws SQLException {
        FileTccConfigVo vo = new FileTccConfigVo();

        vo.setWaterNo(dbHelper.getItemValue("water_no"));
        vo.setId(dbHelper.getItemValue("id"));
        vo.setPeriod(dbHelper.getItemValue("period"));
        vo.setManuValidFlag(dbHelper.getItemValue("manu_valid_flag"));
        vo.setFileNameBase(dbHelper.getItemValue("file_name_base"));
        vo.setRemark(dbHelper.getItemValue("remark"));
        return vo;
    }
    
    public int updateConfigsOnlyOnce() throws Exception {

        String sql = "update "+FrameDBConstant.DB_ST +"w_st_sys_cfg_tcc set manu_valid_flag=?  "
                + "where period=? and  manu_valid_flag=? ";
        DbHelper dbHelper = null;

        Object[] values ={MANU_VALID_NO,MANU_ONE_DAY,MANU_VALID_YES};
        int n=0;
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            n =dbHelper.executeUpdate(sql,values);
            
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return n;
    }

}
