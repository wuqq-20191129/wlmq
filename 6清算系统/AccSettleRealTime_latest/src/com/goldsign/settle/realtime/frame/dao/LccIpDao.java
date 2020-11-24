/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.ConfigBcpVo;
import com.goldsign.settle.realtime.frame.vo.LccIpVo;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class LccIpDao {

    private static Logger logger = Logger.getLogger(LccIpDao.class.getName());

    public Vector<LccIpVo> getConfigIp() throws Exception {

        String sql = "select LCC_LINE_ID,LCC_IP  "
                + "from "+FrameDBConstant.DB_PRE+"CM_COD_LCC_LINE ";
        DbHelper dbHelper = null;
        boolean result;
        LccIpVo vo = null;
        Vector<LccIpVo> v = new Vector();
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql);
            while (result) {
                vo.setLineId(dbHelper.getItemValue("LCC_LINE_ID"));
                vo.setIp(dbHelper.getItemValue("LCC_IP"));
                v.add(vo);
                result = dbHelper.getNextDocument();;
            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return v;
    }
     public LccIpVo getConfigIp(String lineId) throws Exception {
         LccIpVo vo= this.getConfigIpFromLine(lineId);
         if(this.isValid(vo))
             return vo;
          vo= this.getConfigIpFromMobile(lineId);
          return vo;
         
         
     }
     private boolean isValid(LccIpVo vo){
         if(vo == null)
             return false;
         if(vo.getIp() ==null )
             return false;
         if(vo.getIp().length() ==0 )
             return false;
         return true;
     }

    public LccIpVo getConfigIpFromLine(String lineId) throws Exception {

        String sql = "select LCC_LINE_ID,LCC_IP  "
                + "from "+FrameDBConstant.DB_PRE+"st_cod_lcc_line where LCC_LINE_ID=?";
        DbHelper dbHelper = null;
        boolean result;
        Object[] values = {lineId};

        LccIpVo vo = new LccIpVo();
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql,values);
            if (result) {
                vo.setLineId(dbHelper.getItemValue("LCC_LINE_ID"));
                vo.setIp(dbHelper.getItemValue("LCC_IP"));


            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return vo;
    }
    public LccIpVo getConfigIpFromMobile(String lineId) throws Exception {

        String sql = "select LCC_LINE_ID,LCC_IP  "
                + "from "+FrameDBConstant.DB_PRE+"st_mb_cod_lcc_line where LCC_LINE_ID=?";
        DbHelper dbHelper = null;
        boolean result;
        Object[] values = {lineId};

        LccIpVo vo = new LccIpVo();
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql,values);
            if (result) {
                vo.setLineId(dbHelper.getItemValue("LCC_LINE_ID"));
                vo.setIp(dbHelper.getItemValue("LCC_IP"));


            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return vo;
    }
}
