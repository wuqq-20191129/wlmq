/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.ConfigBcpVo;
import com.goldsign.settle.realtime.test.vo.SysVersionVo;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class SysVersionDao {
    private static String DEFAULT_VERSION_NO="0.00";
    private static Logger logger = Logger.getLogger(SysVersionDao.class.getName());
    public SysVersionVo getSysVersion() throws Exception {
        Vector<String> stations = new Vector();
        String sql = "select max(version_no) version_no from "+FrameDBConstant.DB_PRE+"st_sys_version ";
                
        DbHelper dbHelper = null;
        boolean result;
        SysVersionVo vo = new SysVersionVo();
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql);
            if(result){
                vo.setVersionNo(dbHelper.getItemValue("version_no"));
            }
            else
                vo.setVersionNo(DEFAULT_VERSION_NO);
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return vo;
    }
    
}
