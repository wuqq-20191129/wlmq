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
public class ExportTccStationInfoDao extends ExportTccBaseDao{
     private static Logger logger = Logger.getLogger(ExportTccStationInfoDao.class.getName());



    protected String getRecord(DbHelper dbHelper) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(dbHelper.getItemValue("water_no") + this.FIELD_DELIM);//序号

        sb.append(dbHelper.getItemValue("line_id")+ dbHelper.getItemValue("station_id")+ this.FIELD_DELIM);//线路车站编号
        sb.append(dbHelper.getItemValue("chinese_name") + this.FIELD_DELIM);//车站名称
         sb.append(dbHelper.getItemValue("english_name") + this.FIELD_DELIM);//车站英文
         
         sb.append(dbHelper.getItemValue("used_flag") + this.FIELD_DELIM);//使用状态0：在用 1：在建 2：在规划      
        sb.append(dbHelper.getItemValue("start_end_flag") + this.FIELD_DELIM);//是否售末站 0：否 1：是   
        sb.append(dbHelper.getItemValue("transfer_flag") + this.FIELD_DELIM);//是否售换乘 0：否 1：是 
        sb.append(dbHelper.getItemValue("return_flag") + this.FIELD_DELIM);//是否折返站 0：否 1：是 
        sb.append(dbHelper.getItemValue("longitude_num") + this.FIELD_DELIM);//经度

        //最后字段
        sb.append(dbHelper.getItemValue("latitude_num") );//纬度
        //添加不可见字符不能使用可见字符串联不可见字符方式，应该分开添加，即先添加可见，再添加不可见
        //sb.append(dbHelper.getItemValue("entry_num") + this.CRLF_UNIX);//进站量
       // sb.append(dbHelper.getItemValue("entry_num") );//进站量
       // sb.append( this.CRLF_UNIX);//进站量

        
        
        
        
        return sb.toString();
        
        

    }
    
}
