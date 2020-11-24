/*
 * 文件名：IUtilDao
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.etmcs.dao;

import com.goldsign.csfrm.dao.IBaseDao;
import java.util.Map;
import java.util.Vector;


/*
 * 公共查询方法 DAO类
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-8-30
 */

public interface IUtilDao extends IBaseDao{
    
    /**
     * 按表名查询字段值
     * @param tableName 表名
     * @param columnA 列1
     * @param colmunB 列2
     * @param cColumnA 条件1
     * @param cValue 条件列1值
     * @return 返回结果集
     */
    public Map getTableColumn(String tableName, String columnA, String colmunB, String cColumnA, String cValue);
    
    //查询Pub_flag表
    public Vector getTablePubFlag(String tableName);
    
}
