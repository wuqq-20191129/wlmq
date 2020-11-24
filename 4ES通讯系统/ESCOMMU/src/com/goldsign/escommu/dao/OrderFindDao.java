/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.dao;
import com.goldsign.escommu.dbutil.DbHelper;
import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.util.PubUtil;
import com.goldsign.escommu.vo.OrderTypeReqVo;
import com.goldsign.escommu.vo.OrderTypeRspVo;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class OrderFindDao {
    private static Logger logger = Logger.getLogger(OrderFindDao.class.
			getName());
    
    public List<OrderTypeRspVo> orderFind(OrderTypeReqVo orderTypeReqVo) throws Exception {
        boolean result = false;
        DbHelper dbHelper = null;
        Object[] values = {orderTypeReqVo.getOprtType(), orderTypeReqVo.getOperCode()};
                    
        List<OrderTypeRspVo> orderTypeRspVos = new ArrayList<OrderTypeRspVo>();
        OrderTypeRspVo orderTypeRspVo = null;
        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            //********************************************
            String sqlStr = "select a.order_no, a.CARD_MAIN_CODE||a.CARD_SUB_CODE card_type,b.CARD_SUB_NAME card_sub_desc, "+
                    "to_char(a.card_per_ava,'yyyyMMdd') card_per_ava,a.card_mon,a.burse_uplimit deposit_amnt,a.b_req_no,a.e_req_no,a.b_serial_no, "+
                    "a.e_serial_no ,to_char(a.gen_time,'yyyyMMdd') gen_time,a.pro_num,a.card_type card_ver,a.line_code,a.station_code,a.cardstartava, "+
                    "a.card_ava_days,a.exit_line_code,a.exit_station_code,a.MODEL model "+ 
                    ",a.MAX_RECHARGE_VAL,a.SALE_FLAG,a.Test_Flag, a.ES_SERIAL_NO " +
                "from "+AppConstant.COM_TK_P+"IC_PDU_ORDER_FORM a,"+AppConstant.COM_ST_P+"OP_PRM_CARD_SUB b "+
                "where a.CARD_MAIN_CODE=b.CARD_MAIN_ID and a.CARD_SUB_CODE=b.CARD_SUB_ID "+           
                    "and a.es_worktype_id=? and a.es_operator_id=? "+
                    "and a.hdl_flag='0' and b.record_flag='0' order by a.order_no ";

            result = dbHelper.getFirstDocument(sqlStr, values);
            while (result) {
                orderTypeRspVo = getResultRecord(dbHelper);
                orderTypeRspVos.add(orderTypeRspVo);
                
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);

        }

        return orderTypeRspVos;
    }
    
    private OrderTypeRspVo  getResultRecord(DbHelper dbHelper) throws Exception{
        
        OrderTypeRspVo orderTypeRspVo = new OrderTypeRspVo();

        //********************************************
        String orderNo = dbHelper.getItemValue("order_no");
        String cardType = dbHelper.getItemValue("card_type");
        String cardName = dbHelper.getItemValue("card_sub_desc");
        String cardEffTime = dbHelper.getItemValue("card_per_ava");
        Long printValue = dbHelper.getItemLongValue("card_mon");
        Long depValue = dbHelper.getItemLongValue("deposit_amnt");
        String startReqNo = dbHelper.getItemValue("b_req_no");
        String endReqNo = dbHelper.getItemValue("e_req_no");
        String startSeq = dbHelper.getItemValue("b_serial_no");
        String endSeq = dbHelper.getItemValue("e_serial_no");
        String makeDate = dbHelper.getItemValue("gen_time");
        Long quantity = dbHelper.getItemLongValue("pro_num");
        String signCode = dbHelper.getItemValue("card_ver");
        String lineCode = dbHelper.getItemValue("line_code");
        String stationCode = dbHelper.getItemValue("station_code");
        String ccEffStartTime = dbHelper.getItemValue("cardstartava");
        String ccEffUseTime = dbHelper.getItemValue("card_ava_days");
        String limitOutLineCode = dbHelper.getItemValue("exit_line_code");
        String limitOutStationCode = dbHelper.getItemValue("exit_station_code");
        String limitMode = dbHelper.getItemValue("model");
        String saleFlag = dbHelper.getItemValue("sale_flag");
        String testFlag = dbHelper.getItemValue("test_flag");
        String esSerialNo = dbHelper.getItemValue("ES_SERIAL_NO");
        long maxRechargeVal = dbHelper.getItemLongValue("max_recharge_val");

        orderTypeRspVo.setOrderNo(orderNo);
        orderTypeRspVo.setCardType(cardType);
        orderTypeRspVo.setCardName(cardName);
        orderTypeRspVo.setCardEffTime(cardEffTime);
        orderTypeRspVo.setPrintValue(printValue);
        orderTypeRspVo.setDepValue(depValue);
        orderTypeRspVo.setStartReqNo(startReqNo);
        orderTypeRspVo.setEndReqNo(endReqNo);
        orderTypeRspVo.setStartSeq(startSeq);
        orderTypeRspVo.setEndSeq(endSeq);
        orderTypeRspVo.setMakeDate(makeDate);
        orderTypeRspVo.setQuantity(quantity);
        orderTypeRspVo.setSignCode(signCode);
        orderTypeRspVo.setLineCode(lineCode);
        orderTypeRspVo.setStationCode(stationCode);
        orderTypeRspVo.setCcEffStartTime(ccEffStartTime);
        orderTypeRspVo.setCcEffUseTime(ccEffUseTime);
        orderTypeRspVo.setLimitOutLineCode(limitOutLineCode);
        orderTypeRspVo.setLimitOutStationCode(limitOutStationCode);
        orderTypeRspVo.setLimitMode(limitMode);
        orderTypeRspVo.setSaleFlag(saleFlag);
        orderTypeRspVo.setTestFlag(testFlag);
        orderTypeRspVo.setMaxRecharge(maxRechargeVal);
        orderTypeRspVo.setEsSeriakNo(esSerialNo);

        return orderTypeRspVo;
    }
}
