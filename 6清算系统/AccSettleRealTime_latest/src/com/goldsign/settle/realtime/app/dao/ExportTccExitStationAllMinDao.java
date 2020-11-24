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
public class ExportTccExitStationAllMinDao extends ExportTccBaseDao{
    private static Logger logger = Logger.getLogger(ExportTccExitStationAllMinDao.class.getName());



    protected String getRecord(DbHelper dbHelper) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(dbHelper.getItemValue("water_no") + this.FIELD_DELIM);//序号
        sb.append(dbHelper.getItemValue("squad_day") + this.FIELD_DELIM);//日期
        sb.append(dbHelper.getItemValue("line_id") + this.FIELD_DELIM);//线路编号
        sb.append(dbHelper.getItemValue("line_name") + this.FIELD_DELIM);//线路名称
        sb.append(dbHelper.getItemValue("station_id") + this.FIELD_DELIM);//车站编号
        sb.append(dbHelper.getItemValue("station_name") + this.FIELD_DELIM);//车站名称
        sb.append(dbHelper.getItemValue("begin_hour_min") + this.FIELD_DELIM);//起始时刻
        sb.append(dbHelper.getItemValue("end_hour_min") + this.FIELD_DELIM);//终止时刻
        //最后字段
        sb.append(dbHelper.getItemValue("export_num") );//出站量
        //添加不可见字符不能使用可见字符串联不可见字符方式，应该分开添加，即先添加可见，再添加不可见
        //sb.append(dbHelper.getItemValue("entry_num") + this.CRLF_UNIX);//进站量
       // sb.append(dbHelper.getItemValue("entry_num") );//进站量
       // sb.append( this.CRLF_UNIX);//进站量

        
        
        
        
        return sb.toString();
        
        

    }
    
}
