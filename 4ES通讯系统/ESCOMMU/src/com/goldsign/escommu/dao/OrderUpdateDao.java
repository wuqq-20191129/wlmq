/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.dao;
import com.goldsign.escommu.dbutil.DbHelper;
import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.env.FileConstant;
import com.goldsign.escommu.util.PubUtil;
import com.goldsign.escommu.vo.OrderStateReqVo;
import com.goldsign.escommu.vo.OrderStateRspVo;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class OrderUpdateDao {
    private static Logger logger = Logger.getLogger(OrderUpdateDao.class.
			getName());
    
    private static final Object LOCK = new Object();
    
    public List<OrderStateRspVo> orderUpdate(List<OrderStateReqVo> orderStateReqVos) throws Exception {
        boolean result = false;
        DbHelper dbHelper = null;
        
        List<OrderStateRspVo> orderStateRspVos = new ArrayList<OrderStateRspVo>();
        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            synchronized(LOCK){
            dbHelper.setAutoCommit(false);
            for(OrderStateReqVo orderStateReqVo: orderStateReqVos){
                Object[] values = {orderStateReqVo.getOrderNo()};
                Object[] values2 = {orderStateReqVo.getDeviceId(), orderStateReqVo.getOrderNo()};

                OrderStateRspVo orderStateRspVo = new OrderStateRspVo();
                   
                //********************************************
                String sqlStr = "select * from "+AppConstant.COM_TK_P+"IC_PDU_ORDER_FORM where order_no = ?";
                String sqlStr2 = "update "+AppConstant.COM_TK_P+"IC_PDU_ORDER_FORM set hdl_flag = '1',fini_pronum = 0,trashy_num = 0,es_serial_no = ? "
                        + "where order_no = ?";
                //String sqlStr3 = "select COUNT(1) as sign_num from "+AppConstant.COM_ST_P+"st_list_sign_card a where a.hdl_flag='0' and a.card_main_id=? and a.card_sub_id=? and ROWNUM<=? order by a.REQ_NO ASC ";
                //hwj modify 20160225 记名卡区分1：正常申请 2：补卡申请
                String sqlStr3 = "select COUNT(1) as sign_num from "+AppConstant.COM_ST_P+"st_list_sign_card a where a.hdl_flag='0' and a.card_main_id=? and a.card_sub_id=? and a.app_business_type=? and ROWNUM<=? order by a.REQ_NO ASC ";
                String sqlStr4 = "update "+AppConstant.COM_ST_P+"st_list_sign_card c "
                        + " set c.hdl_flag='1',c.hdl_time=sysdate,c.order_no=? "
                        + " where "
                        + " c.req_no in(select b.req_no from (select a.REQ_NO from "+AppConstant.COM_ST_P+"st_list_sign_card a where a.hdl_flag='0' and a.card_main_id=? and a.card_sub_id=? and a.app_business_type=? order by a.REQ_NO ASC) b where rownum<=?) ";
                //hwj modify 20160225 记名卡区分1：正常申请 2：补卡申请
                //        + " c.req_no in(select b.req_no from (select a.REQ_NO from "+AppConstant.COM_ST_P+"st_list_sign_card a where a.hdl_flag='0' and a.card_main_id=? and a.card_sub_id=? order by a.REQ_NO ASC) b where rownum<=?) ";
                
                logger.info("sqlStr1:"+sqlStr);
                result = dbHelper.getFirstDocument(sqlStr, values);
                
                orderStateRspVo.setOrderNo(orderStateReqVo.getOrderNo());
                
                if (!result) {
                    orderStateRspVo.setResult("12");
                    orderStateRspVos.add(orderStateRspVo);
                    continue;
                }
                String cardMainCode = dbHelper.getItemValue("CARD_MAIN_CODE");
                String cardSubCode = dbHelper.getItemValue("CARD_SUB_CODE");
                
                String hdlFlag = dbHelper.getItemValue("hdl_flag");
                if(!hdlFlag.equals("0")){
                    orderStateRspVo.setResult("11");
                    orderStateRspVos.add(orderStateRspVo);
                    continue;
                }
                
                String cardType = dbHelper.getItemValue("card_type");
                String order_type = dbHelper.getItemValue("order_type");//hwj modify 20160225 记名卡区分1：正常申请 2：补卡申请
                if(cardType.equals("1") && 
                        (FileConstant.WORKTYPE_INITIAL.equals(orderStateReqVo.getOprtType())
                        ||FileConstant.WORKTYPE_AGAIN.equals(orderStateReqVo.getOprtType()))){//记名卡订单
                    int proNum = dbHelper.getItemIntValue("pro_num");
                    logger.info("sqlStr3:"+sqlStr3);
                    //hwj modify 20160225 记名卡区分1：正常申请 2：补卡申请
                    //result = dbHelper.getFirstDocument(sqlStr3, new Object[]{cardMainCode,cardSubCode,proNum});
                    result = dbHelper.getFirstDocument(sqlStr3, new Object[]{cardMainCode,cardSubCode,order_type,proNum});
                    if(!result){//记名卡不存在
                        orderStateRspVo.setResult("21");
                        orderStateRspVos.add(orderStateRspVo);
                        continue;
                    }
                    int signNum = dbHelper.getItemIntValue("sign_num");
                    if(signNum != proNum){//记名卡与生产订单数量
                        orderStateRspVo.setResult("22");
                        orderStateRspVos.add(orderStateRspVo);
                        continue;
                    }
                    logger.info("sqlStr4:"+sqlStr4);
                    //hwj modify 20160225 记名卡区分1：正常申请 2：补卡申请
                    //dbHelper.executeUpdate(sqlStr4, new Object[]{orderStateReqVo.getOrderNo(), cardMainCode,cardSubCode,proNum});
                    dbHelper.executeUpdate(sqlStr4, new Object[]{orderStateReqVo.getOrderNo(), cardMainCode,cardSubCode,order_type,proNum});
                }
                logger.info("sqlStr2:"+sqlStr2);
                dbHelper.executeUpdate(sqlStr2, values2);
               
                orderStateRspVo.setResult("0");
                orderStateRspVos.add(orderStateRspVo);
                
            }
            dbHelper.commitTran();
            }
        } catch (Exception e) {
            //PubUtil.handleExceptionForTranNoThrow(e,logger,dbHelper);
            PubUtil.handleExceptionForTran(e, logger, dbHelper);
        } finally {
            //PubUtil.finalProcessForTran(dbHelper);
            PubUtil.finalProcessForTran(dbHelper);
        }

        return orderStateRspVos;
    }
}
