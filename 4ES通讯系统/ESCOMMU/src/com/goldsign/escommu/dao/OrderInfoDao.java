/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.dao;

import com.goldsign.escommu.dbutil.DbHelper;
import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.util.CharUtil;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.util.FileUtil;
import com.goldsign.escommu.util.PubUtil;
import com.goldsign.escommu.vo.FileListVo;
import com.goldsign.escommu.vo.FileStatVo;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class OrderInfoDao {

    private static Logger logger = Logger.getLogger(OrderInfoDao.class.getName());

    public int deleteRecordsFromBcp(FileStatVo vo, DbHelper dbHelper) throws Exception {
        int result = 0;
        FileUtil util = new FileUtil();
        String tableName = util.getTableBcp(vo.getFileName());
        String sqlStr = "delete from " + tableName + " where order_no=? ";
        Object[] values = {vo.getOrderNo()};
        result = dbHelper.executeUpdate(sqlStr, values);

        return result;
    }

    public int insertOrderNumChange(FileStatVo vo, DbHelper dbHelper) throws Exception {
        int result = 0;
        String sqlStr = "insert into "+AppConstant.COM_TK_P+"IC_ES_ORDER_NUM_CHANGE(water_no,file_name,order_no,fini_pronum_reset,fini_pronum_before,reset_time,remark) "
                + "values("+AppConstant.COM_TK_P+"SEQ_"+AppConstant.TABLE_PREFIX+"IC_ES_ORDER_NUM_CHANGE.nextval,?,?,?,?,?,?) ";
        Object[] values = {vo.getFileName(), vo.getOrderNo(),
            new Integer(vo.getResetNum()), new Integer(vo.getResetNumBefore()), DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date()), ""};
        result = dbHelper.executeUpdate(sqlStr, values);
        return result;
    }

    public int updateOrderRemark(FileStatVo vo, DbHelper dbHelper) throws Exception {
        int result = 0;
        String sqlStr = " update "+AppConstant.COM_TK_P+"IC_PDU_ORDER_FORM set order_memo=?  where order_no=? ";
        //'重号数量:'+convert(varchar(10),@repeatCountTotal)
        String remark = "重号数量:" + vo.getReapeatRecords().size();
        Object[] values = {remark, vo.getOrderNo()};
        result = dbHelper.executeUpdate(sqlStr, values);
        return result;
    }

    public int insertReapeatRecords(FileStatVo vo, DbHelper dbHelper) throws Exception {
        Vector v = vo.getReapeatRecords();
        int n = 0;
        FileListVo listvo;
        for (int i = 0; i < v.size(); i++) {
            listvo = (FileListVo) v.get(i);
            n += this.insertReapeatRecord(listvo, dbHelper);
        }
        return n;
    }

    public int insertReapeatRecord(FileListVo vo, DbHelper dbHelper) throws Exception {
        int result = 0;
        FileUtil util = new FileUtil();
        String sqlStr = "insert into "+AppConstant.COM_TK_P+"IC_ES_PDU_REPEAT_LOGIC(order_no,logic_id,phy_id,"
                + "print_id,card_main_type,card_sub_type,card_type,"
                + "card_money,peri_avadate,manu_time,hdl_time,kdc_version,status_flag,req_no,"
                + "card_ava_days,exit_line_code,exit_station_code,modal)"
                + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        /*
         * values(@order_no,@logi_id,@phy_id,@prin_id,substring(@card_type,1,2),substring(@card_type,3,4),@card_ver,
         * convert(numeric,convert(char,@card_mon)),@pri_date,@manutime,@hdl_time,null,@flag,@req_no,
         * @card_ava_days,@exit_line_id,@exit_station_id,@mode) ";" +
         */

        Object[] values = {vo.getOrderNo(), vo.getLogiId(), vo.getPhyId(), vo.getPrintId(), vo.getCardMainCode(), vo.getCardSubCode(), vo.getCardType(),
            new Integer(vo.getCardMon()), 
            DateHelper.utilDateToSqlTimestamp(DateHelper.getDatetimeFromYYYY_MM_DD_HH_MM_SS(CharUtil.trimStr(vo.getPeriAvadate()+" 00:00:00"))), 
            DateHelper.utilDateToSqlTimestamp(DateHelper.getDatetimeFromYYYY_MM_DD_HH_MM_SS(CharUtil.trimStr(vo.getManuTime()))),
            DateHelper.utilDateToSqlTimestamp(DateHelper.getDatetimeFromYYYY_MM_DD_HH_MM_SS(CharUtil.trimStr(vo.getHdlTime()))),
            CharUtil.trimStr(vo.getKdcVersion()), CharUtil.trimStr(vo.getStatusFlag()), CharUtil.trimStr(vo.getReqNo()),
            CharUtil.trimStr(vo.getCardAvaDays()), CharUtil.trimStr(vo.getExitLineCode()), CharUtil.trimStr(vo.getExitStationCode()), CharUtil.trimStr(vo.getMode())
        };
        result = dbHelper.executeUpdate(sqlStr, values);
        return result;
    }
    
    public void genProduceBill(FileStatVo vo) throws Exception {
        Integer result = null;
        Connection connection = null;
        CallableStatement cst = null;
        String retMsg;
        try {
            connection = AppConstant.DATA_DBCPHELPER.getConnection();
            String sqlStr = "{call "+AppConstant.COM_TK_P+"UP_IC_ES_GEN_PRODUCE_BILL(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            cst = connection.prepareCall(sqlStr);
            cst.setString(1, vo.getOrderNo());
            cst.setInt(2, new Integer(vo.getFiniPronum()));
            cst.setInt(3, new Integer(vo.getSurplusNum()));
            cst.setInt(4, new Integer(vo.getTrashyNum()));
            cst.setString(5, CharUtil.trimStr(vo.getEsSamNo()));
            cst.setString(6, vo.getHdlFlag());
            cst.setTimestamp(7, new Timestamp(DateHelper.getDatetimeFromYYYYMMDDHHMMSS(vo.getAchieveTime()).getTime()));
            cst.setString(8, CharUtil.trimStr(vo.getOrderMemo()));
            cst.setString(9, vo.getEsWorktypeId());
            cst.setInt(10, vo.getDrawNum());//领票数量
            cst.setString(11, vo.getmBPduType());//生产类型
            cst.registerOutParameter(12, Types.INTEGER);
            cst.registerOutParameter(13, Types.VARCHAR);
            
            cst.execute();
            
            result = (Integer) cst.getObject(12);
            retMsg = (String) cst.getObject(13);
            logger.info(vo.getOrderNo() + retMsg);
            if(result != 0){
                throw new Exception(retMsg);
            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);

        } finally {
            if(cst != null){
                cst.close();
            }
            if(connection != null){
                connection.close();
            }
        }
    }
}
