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
public class ExportTccLineInfoDao extends ExportTccBaseDao{
    private static Logger logger = Logger.getLogger(ExportTccLineInfoDao.class.getName());



    protected String getRecord(DbHelper dbHelper) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(dbHelper.getItemValue("water_no") + this.FIELD_DELIM);//序号

        sb.append(dbHelper.getItemValue("line_id") + this.FIELD_DELIM);//线路编号
        sb.append(dbHelper.getItemValue("line_name") + this.FIELD_DELIM);//线路名称
         sb.append(dbHelper.getItemValue("line_name_en") + this.FIELD_DELIM);//线路英文
        sb.append(dbHelper.getItemValue("circle_flag") + this.FIELD_DELIM);//是否环线
        sb.append(dbHelper.getItemValue("used_flag") + this.FIELD_DELIM);//使用状态0：在用 1：在建 2：在规划
        sb.append(dbHelper.getItemValue("end_station_up") + this.FIELD_DELIM);//上行末站
        sb.append(dbHelper.getItemValue("end_station_down") + this.FIELD_DELIM);//下行末站
        sb.append(dbHelper.getItemValue("total_line_len") + this.FIELD_DELIM);//线长度

        //最后字段
        sb.append(dbHelper.getItemValue("total_run_min") );//运行时间
        //添加不可见字符不能使用可见字符串联不可见字符方式，应该分开添加，即先添加可见，再添加不可见
        //sb.append(dbHelper.getItemValue("entry_num") + this.CRLF_UNIX);//进站量
       // sb.append(dbHelper.getItemValue("entry_num") );//进站量
       // sb.append( this.CRLF_UNIX);//进站量

        
        
        
        
        return sb.toString();
        
        

    }
    
}
