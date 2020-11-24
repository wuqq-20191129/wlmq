package com.goldsign.escommu.message;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.env.LogConstant;
import com.goldsign.escommu.env.MessageConstant;
import com.goldsign.escommu.dao.OrderUpdateDao;
import com.goldsign.escommu.exception.CommuException;
import com.goldsign.escommu.util.CharUtil;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.util.LogDbUtil;
import com.goldsign.escommu.util.MessageCommonUtil;
import com.goldsign.escommu.vo.OrderStateReqVo;
import com.goldsign.escommu.vo.OrderStateRspVo;
import com.goldsign.escommu.vo.SynchronizedControl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author lenovo
 */
public class Message36 extends MessageBase {

    private static Logger logger = Logger.getLogger(Message36.class.getName());
    public static SynchronizedControl SYNCONTROL = new SynchronizedControl();

    public void run() throws Exception {
        String result = LogConstant.RESULT_HDL_SUCESS;
        this.level = AppConstant.LOG_LEVEL_INFO;
        try {
            this.hdlStartTime = System.currentTimeMillis();

            this.process();

            this.hdlEndTime = System.currentTimeMillis();
        } catch (Exception e) {
            result = LogConstant.RESULT_HDL_FAIL;
            this.hdlEndTime = System.currentTimeMillis();
            this.level = AppConstant.LOG_LEVEL_ERROR;
            this.remark = e.getMessage();
            throw e;
        } finally {//记录处理日志
            LogDbUtil.logForDbDetail(LogConstant.MESSAGE_ID_ORDER_STATE_REQ, this.messageFrom,
                    this.hdlStartTime, this.hdlEndTime, result, this.threadNum,
                    this.level, this.remark);
        }
    }

    public void process() throws Exception {
        DateHelper.screenPrint("处理" + MessageCommonUtil.getMessageName(MessageConstant.MESSAGE_ID_ORDER_STATE_REQ) + "消息");
        synchronized (SYNCONTROL) {
            try {

                List<OrderStateReqVo> orderStateReqVos = getOrderStateReq();
                OrderUpdateDao orderUpdateDao = new OrderUpdateDao();
                List<OrderStateRspVo> orderStateRspVos = orderUpdateDao.orderUpdate(orderStateReqVos);
                
                byte[] datasRsp = new ConstructMessage37().constructMessage(orderStateRspVos);
                this.bridge.getConnection().sendData(datasRsp, Integer.parseInt(messageSequ));
                
            } catch (CommuException e) {
                DateHelper.screenPrintForEx(DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date()) + "  " + thisClassName + " error! messageSequ:" + messageSequ + ". " + e);
                logger.error(thisClassName + " error! messageSequ:" + messageSequ + ". " + e);
                throw e;
            } 
        }
    }

    private List<OrderStateReqVo> getOrderStateReq() throws CommuException{
    
        List<OrderStateReqVo> OrderStateReqVos = new ArrayList<OrderStateReqVo>();
        String oprtType = CharUtil.trimStr(this.getCharString(2, 2));
        String operCode = CharUtil.trimStr(getCharString(4, 8));
        String deviceId = CharUtil.trimStr(this.getCharString(12, 3));
        int recordNum = this.getShort(15);
        for(int i=0; i<recordNum; i++){
            OrderStateReqVo OrderStateReqVo = new OrderStateReqVo();
            String orderNo = CharUtil.trimStr(this.getCharString(15+i*14, 16));
            OrderStateReqVo.setOprtType(oprtType);
            OrderStateReqVo.setOperCode(operCode);
            OrderStateReqVo.setDeviceId(deviceId);
            OrderStateReqVo.setOrderNo(orderNo);
            
            OrderStateReqVos.add(OrderStateReqVo);
        }
     
        return OrderStateReqVos;
    }
}
