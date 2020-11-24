/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.lib.jms.vo.MessageInfo;

import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class MessageQueDao {
    public static String  FLAG_PARAM_INFO_MSG_NO="0";//非参数通知
    public static int     WATER_NO_DEFAULT=0;//缺省参数通知流水号
    public static String FLAG_PROCESS_NO="0";//未处理

    private static Logger logger = Logger.getLogger(MessageQueDao.class.getName());

    public int insert( MessageInfo msgInfo) throws Exception {
        String sql = "insert into " + FrameDBConstant.DB_CM + "W_CM_QUE_MESSAGE(message_id,message_time,line_id,station_id,"
                + "ip_address,message,process_flag,is_para_inform_msg,para_inform_water_no,message_type,message_type_sub,remark) "
                + "values(W_ACC_CM.W_SEQ_W_CM_QUE_MESSAGE.nextval,sysdate,?,?,?,?,?,?,?,?,?,?) ";
        DbHelper dbHelper = null;
        int result = 0;
        Object[] values = {msgInfo.getLineId(),msgInfo.getStationId(),msgInfo.getIp(),msgInfo.getDatas(),FLAG_PROCESS_NO,
            FLAG_PARAM_INFO_MSG_NO,WATER_NO_DEFAULT,msgInfo.getMsgType(),msgInfo.getMsgTypeSub(),msgInfo.getRemark()};
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.executeUpdate(sql, values);

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return result;

    }

}
