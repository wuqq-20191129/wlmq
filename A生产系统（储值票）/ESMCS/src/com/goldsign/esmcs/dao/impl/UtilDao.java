/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.esmcs.dao.impl;

import com.goldsign.csfrm.dao.impl.BaseDao;
import com.goldsign.csfrm.util.MessageShowUtil;
import com.goldsign.esmcs.dao.IUtilDao;
import com.goldsign.esmcs.env.AppConstant;
import com.goldsign.esmcs.util.PubUtil;
import com.goldsign.esmcs.vo.PubFlagVo;
import com.goldsign.lib.db.util.DbHelper;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author limj
 */
public class UtilDao extends BaseDao implements IUtilDao{
    
    private static Logger logger = Logger.getLogger(UtilDao.class.getName());
    @Override
    public Map getTableColumn(String tableName, String columnA, String columnB, String cColumnA, String cValue) {
        Map map = new HashMap();
        boolean result = false;
        DbHelper dbHelper = null;
        try{
            dbHelper = new DbHelper("",AppConstant.dbcpHelper.getConnection());
            String sqlStr = "SELECT "+columnA+" as code,"+columnB+" as code_text from w_acc_st."+tableName
                    +" WHERE trim("+cColumnA+")='"+cValue+"'";
            logger.info("sqlStr:"+sqlStr);
            result = dbHelper.getFirstDocument(sqlStr);
            while(result){
                map.put(dbHelper.getItemTrueValue("code"), dbHelper.getItemValue("code_text"));
                result = dbHelper.getNextDocument();
            }
        }catch(Exception e){
            PubUtil.handleExceptionNoThrow(e, logger);
        }finally{
            PubUtil.finalProcess(dbHelper);
        }
        return map;
    }

    @Override
    public Vector getTablePubFlag(String tableName) {
        Map map = new HashMap();
        boolean result = false;
        DbHelper dbHelper = null;
        Vector v = new Vector();
        try{
            dbHelper = new DbHelper("",AppConstant.dbcpHelper.getConnection());
            String sqlStr = "SELECT type,code,code_text From w_acc_tk."+tableName;
            logger.info("sqlStr:"+sqlStr);
            result = dbHelper.getFirstDocument(sqlStr);
            while(result){
                PubFlagVo vo = new PubFlagVo();
                vo.setCode(dbHelper.getItemValue("code"));
                vo.setCodeText(dbHelper.getItemValue("code_text"));
                vo.setType(dbHelper.getItemValue("type"));
                v.add(vo);
                result = dbHelper.getNextDocument();
            }
        }catch(Exception e){
            PubUtil.handleExceptionNoThrow(e,logger);
            logger.info("数据库参数加载失败");
            MessageShowUtil.alertInfoMsg("数据库参数加载失败！");
        }finally{
            PubUtil.finalProcess(dbHelper);
        }
        return v;
    }
    
}
