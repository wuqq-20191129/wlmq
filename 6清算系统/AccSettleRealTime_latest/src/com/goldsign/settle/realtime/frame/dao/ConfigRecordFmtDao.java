/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.ConfigRecordFmtVo;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;
import org.apache.log4j.Logger;


/**
 *
 * @author hejj
 */
public class ConfigRecordFmtDao {
    private static Logger logger =Logger.getLogger(ConfigRecordFmtDao.class.getName());
    public HashMap getConfigRecordFmt() throws Exception{
        HashMap hm = new HashMap();
        String sql ="select file_type,record_type,field_seq,field_name,field_type,field_len,remark "
                + "from "+FrameDBConstant.DB_PRE+"st_cfg_file_record_fmt order by file_type,record_type,field_seq";
        DbHelper dbHelper =null;
        boolean result;
        ConfigRecordFmtVo vo;
        try {
            dbHelper =new DbHelper("",FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql);
            while(result){
                vo =this.getConfigRecordFmtVo(dbHelper);
                this.putMap(hm, vo);
                result =dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        }
        finally{
            PubUtil.finalProcess(dbHelper);
        }
        
        return hm;
    }
    private void putMap(HashMap hm,ConfigRecordFmtVo vo){
        String key =vo.getRecordType();
        if(!hm.containsKey(key))
            hm.put(vo.getRecordType(), new Vector());
        Vector v = (Vector)hm.get(key);
        v.add(vo);
    }
    private ConfigRecordFmtVo getConfigRecordFmtVo(DbHelper dbHelper) throws SQLException{
        ConfigRecordFmtVo vo =new ConfigRecordFmtVo();
        //file_type,record_type,field_seq,field_name,field_type,field_len,remark
        vo.setFileType(dbHelper.getItemValue("file_type"));
        vo.setRecordType(dbHelper.getItemValue("record_type"));
        vo.setFieldSeq(dbHelper.getItemIntValue("field_seq"));
        vo.setFieldType(dbHelper.getItemValue("field_type"));
        vo.setFieldLen(dbHelper.getItemIntValue("field_len"));
        vo.setFieldName(dbHelper.getItemValue("field_name"));      
        vo.setRemark(dbHelper.getItemValue("remark"));
        return vo;
    }
    
}
