package com.goldsign.kmsfront.struct.message;

import com.goldsign.commu.commu.message.MessageBase;
import com.goldsign.commu.commu.util.CharUtil;
import com.goldsign.commu.commu.util.DateHelper;
import com.goldsign.commu.commu.vo.RequestVo;
import com.goldsign.commu.commu.vo.ResponseVo;
import com.goldsign.kmsfront.struct.service.IMessageService;
import com.goldsign.kmsfront.struct.service.MessageService;
import com.goldsign.kmsfront.struct.vo.QueryConVo;
import com.goldsign.kmsfront.struct.vo.QueryRetVo;
import com.goldsign.kmsfront.struct.vo.Request1Vo;
import com.goldsign.kmsfront.struct.vo.Response1Vo;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

public class Message1 extends MessageBase {

    private static Logger logger = Logger.getLogger(Message1.class.getName());
   
    private IMessageService messageService;
    
    public Message1(){ 
        messageService = new MessageService();
    }
    
    @Override
    public void process() throws Exception {

        //取得查询条件
        Request1Vo request1Vo = (Request1Vo)getRequestData();
        //取Service返回结果
        String startTime = DateHelper.timeToStringSSS(new Date());
        QueryRetVo queryRetVo = messageService.queryStructDatas(request1Vo.getQueryCons());
        String endTime = DateHelper.timeToStringSSS(new Date());
        //分析得到返回数据
        Response1Vo response1Vo = (Response1Vo) getResponseData(startTime, queryRetVo, endTime);
        //发送数据
        sendResponseData(response1Vo);
    }
    
    private ResponseVo getResponseData(String startTime, QueryRetVo queryRetVo, 
            String endTime){
        
        Response1Vo response1Vo = new Response1Vo();
        int totalLen = 0;//总长度
        int startTimeLen = startTime.getBytes().length;//开始时间
        totalLen += 1 + 2 + startTimeLen;
        int endTimeLen = endTime.getBytes().length;//结束时间
        totalLen += 1 + 2 + endTimeLen;
        int queryRetValuesLen = queryRetVo.getLen();//数据
        List<String> queryRetValues = queryRetVo.getValues();
        int queryRetValueSize = queryRetValues.size();
        totalLen += (1 + 2)*queryRetValueSize + queryRetValuesLen;
        byte[] data = new byte[1 + 2 + totalLen];
        
        //应答报文头
        data[0] = '2';
        //报文长度
        System.arraycopy(CharUtil.to2Byte(totalLen), 0, data, 1, 2);
        
        final int RPT_HEAD_LEN = 3;
        
        //开始时间
        data[RPT_HEAD_LEN] = (byte)1;
        System.arraycopy(CharUtil.to2Byte(startTimeLen), 0, data, RPT_HEAD_LEN+1, 2);
        System.arraycopy(startTime.getBytes(), 0, data, RPT_HEAD_LEN+3, startTimeLen);
        
        //结束时间
        data[RPT_HEAD_LEN+3+startTimeLen] = (byte)2;
        System.arraycopy(CharUtil.to2Byte(endTimeLen), 0, data, RPT_HEAD_LEN+3+startTimeLen+1, 2);
        System.arraycopy(endTime.getBytes(), 0, data, RPT_HEAD_LEN+3+startTimeLen+3, endTimeLen);
        
        //数据
        int i=0;
        for(String value: queryRetValues){
            int valueLen = value.getBytes().length;
            data[RPT_HEAD_LEN+3+startTimeLen+3+endTimeLen+i] = (byte)0;
            System.arraycopy(CharUtil.to2Byte(valueLen), 0, data, RPT_HEAD_LEN+3+startTimeLen+3+endTimeLen+i+1, 2);
            System.arraycopy(value.getBytes(), 0, data, RPT_HEAD_LEN+3+startTimeLen+3+endTimeLen+i+3, valueLen);

            i += 3 + valueLen;
        }
        response1Vo.setData(data);
        
        return response1Vo;
    }

    @Override
    protected RequestVo getRequestData() {

        Request1Vo request1Vo = new Request1Vo();
        request1Vo.setMsgType((String) data[0]);
        byte[] dataBs = (byte[]) data[1];
        int dataLen = dataBs.length;
        int lenNum = 0;
        while (lenNum < dataLen) {
            int type = dataBs[lenNum];
            int len = CharUtil.byteToInt(new byte[]{dataBs[lenNum + 1], dataBs[lenNum + 2]});
            String value = new String(CharUtil.getByteArr(dataBs, lenNum + 3, len));
            int signIdx = value.indexOf("|");
            if(signIdx != -1){
                String sign = value.substring(0, signIdx);
                value = value.substring(signIdx+1, value.length());
                QueryConVo queryConVo = new QueryConVo(type, sign, value);
                logger.info("查询条件"+lenNum+"："+queryConVo);
                request1Vo.addQueryCon(queryConVo);
            }
            
            lenNum = lenNum + 3 + len;
        }

        return request1Vo;
    }
}
