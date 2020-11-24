/*
 * 文件名：UtilDao
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.ecpmcs.dao.impl;

import com.goldsign.csfrm.dao.impl.BaseDao;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.ecpmcs.dao.IUtilDao;
import com.goldsign.ecpmcs.env.AppConstant;
import com.goldsign.ecpmcs.util.PubUtil;
import com.goldsign.ecpmcs.vo.PubFlagVo;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import org.apache.log4j.Logger;

/*
 * 公共查询Dao
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-8-30
 */

public class UtilDao extends BaseDao implements IUtilDao{
    
    private static Logger logger = Logger.getLogger(UtilDao.class.getName());

    //按表名，条件查询列
    @Override
    public Map getTableColumn(String tableName, String columnA, String colmunB, String cColumnA, String cValue) {
        Map map = new HashMap();
        boolean result = false;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            
            String sqlStr = "SELECT "+columnA+" code,"+colmunB+" code_text FROM "+AppConstant.DATABASE_USER_ST+""+tableName
                    + " WHERE trim("+cColumnA+")='"+cValue+"'";

            logger.info("sqlStr:" + sqlStr);
            
            result = dbHelper.getFirstDocument(sqlStr);
            while (result) {
                map.put(dbHelper.getItemTrueValue("code"), dbHelper.getItemValue("code_text"));
                
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return map;
    }
    
    //按表名，条件查询列 查询Pub_flag表
    @Override
    public Vector getTablePubFlag(String tableName) {
        boolean result = false;
        DbHelper dbHelper = null;
        Vector v = new Vector();
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            
            String sqlStr = "SELECT type,code,code_text FROM "+AppConstant.DATABASE_USER_TK+""+tableName;
 
            logger.info("sqlStr:" + sqlStr);
            
            result = dbHelper.getFirstDocument(sqlStr);
            while (result) {
                PubFlagVo vo = new PubFlagVo();
                vo.setCode(dbHelper.getItemValue("code"));
                vo.setCodeText(dbHelper.getItemValue("code_text"));
                vo.setType(dbHelper.getItemValue("type"));
                v.add(vo);
                
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return v;
    }

}
