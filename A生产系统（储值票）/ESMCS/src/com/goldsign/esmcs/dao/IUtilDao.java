/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.esmcs.dao;

import com.goldsign.csfrm.dao.IBaseDao;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author mh
 */
public interface IUtilDao extends IBaseDao{
    /**
     * 按表名查询字段值
     * @param tableName 表名
     * @param columnA 列1
     * @param columnB 列2
     * @param cColumnA 条件1
     * @param cValue 条件列1值
     * @return 返回结果
     */
    public Map getTableColumn(String tableName,String columnA,String columnB,String cColumnA,String cValue);
    
    //查询Pub_flag表
    public Vector getTablePubFlag(String  tableName);
}
