/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.dao.ExportTccBaseDao;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class ExportTccProfileInfoDao extends ExportTccBaseDao{
    private static Logger logger = Logger.getLogger(ExportTccProfileInfoDao.class.getName());



    protected String getRecord(DbHelper dbHelper) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(dbHelper.getItemValue("water_no") + this.FIELD_DELIM);//序号

        sb.append(dbHelper.getItemValue("profile_id") + this.FIELD_DELIM);//断面标识
        sb.append(dbHelper.getItemValue("profile_id_name") + this.FIELD_DELIM);//断面名称
         sb.append(dbHelper.getItemValue("b_line_id")+dbHelper.getItemValue("b_station_id")  + this.FIELD_DELIM);//断面起始线路站点
        sb.append(dbHelper.getItemValue("e_line_id")+dbHelper.getItemValue("e_station_id")  + this.FIELD_DELIM);//断面终止线路站点

        //最后字段
        sb.append(dbHelper.getItemValue("direction_flag") );//上行行方向
        //添加不可见字符不能使用可见字符串联不可见字符方式，应该分开添加，即先添加可见，再添加不可见
        //sb.append(dbHelper.getItemValue("entry_num") + this.CRLF_UNIX);//进站量
       // sb.append(dbHelper.getItemValue("entry_num") );//进站量
       // sb.append( this.CRLF_UNIX);//进站量

        
        
        
        
        return sb.toString();
        
        

    }
    
}
