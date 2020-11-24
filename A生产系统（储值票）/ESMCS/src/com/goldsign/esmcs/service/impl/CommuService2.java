/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.service.impl;

import com.goldsign.csfrm.service.impl.BaseService;
import com.goldsign.csfrm.vo.AuthenResult;
import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.csfrm.vo.SysUserVo;
import com.goldsign.esmcs.env.ConfigConstant;
import com.goldsign.esmcs.exception.CommuException;
import com.goldsign.esmcs.service.ICommuService;
import com.goldsign.esmcs.util.ConfigUtil;
import com.goldsign.esmcs.vo.CityParamVo;
import com.goldsign.esmcs.vo.FtpFileParamVo;
import com.goldsign.esmcs.vo.TicketPriceVo;
import com.goldsign.esmcs.vo.TicketTypeVo;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author lenovo
 */
public class CommuService2 extends BaseService implements ICommuService{

    private static final Logger logger = Logger.getLogger(CommuService2.class.getName());
    
    /**
     * 操作员登录
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult loginOperator(CallParam callParam)throws CommuException{
        
        AuthenResult authenResult = new AuthenResult();
 
        SysUserVo user = new SysUserVo();
        
        user.setUsername("制卡员");
        user.setAccount("000001");
        authenResult.setObj(user);
        authenResult.setResult(true);
        
        logger.info("验证操作员成功！");
        
        return authenResult;
    }
    
    /**
     * ES制卡和明细数据
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult uploadEsOrderFile(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
        
        callResult.setResult(true);
         
        return callResult;
    }

    /**
     * 打开SOCKET通讯端口
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult openCommu(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
        
        callResult.setResult(true);
        
        logger.info("SOCKET通讯端口成功！");
        return callResult;
    }

    /**
     * 接收审计和错误文件
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult downAuditAndErrorFile(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
       
        List<FtpFileParamVo> files = new ArrayList<FtpFileParamVo>();
        
        FtpFileParamVo fileVo = new FtpFileParamVo(ConfigUtil.getFtpLoginParam());
        fileVo.setFile("PRM.0601.20130701.01");
        fileVo.setLocalPath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditLocalPathTag));
        fileVo.setRemotePath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditRemotePathTag));         
        files.add(fileVo);
        
        FtpFileParamVo fileVo2 = new FtpFileParamVo(ConfigUtil.getFtpLoginParam());
        fileVo2.setFile("PRM.0602.20130701.01");
        fileVo2.setLocalPath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditLocalPathTag));
        fileVo2.setRemotePath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditRemotePathTag));         
        files.add(fileVo2);
        
        FtpFileParamVo fileVo3 = new FtpFileParamVo(ConfigUtil.getFtpLoginParam());
        fileVo3.setFile("PRM.0603.20130701.01");
        fileVo3.setLocalPath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditLocalPathTag));
        fileVo3.setRemotePath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditRemotePathTag));         
        files.add(fileVo3);
        
        FtpFileParamVo fileVo4 = new FtpFileParamVo(ConfigUtil.getFtpLoginParam());
        fileVo4.setFile("PRM.0604.20130701.01");
        fileVo4.setLocalPath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditLocalPathTag));
        fileVo4.setRemotePath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditRemotePathTag));         
        files.add(fileVo4);
        
        callResult.setObjs(files);
        callResult.setResult(true);
        
        logger.info("下载审计和错误文件成功！");
        
        return callResult;
    }

    /**
     * 下载城市代码
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult queryParams(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
     
        CityParamVo cityParamVo = new CityParamVo();
        cityParamVo.setCityCode("123456");
        cityParamVo.setIndustryCode("000000");
        cityParamVo.setKeyVersion("001");
        
        callResult.setObj(cityParamVo);
        
        callResult.setResult(true);
        
        logger.info("下载城市代码成功！");
        return callResult;
    }

    /**
     * 下载票价
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult queryTicketPrice(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();

        List<TicketPriceVo> ticketPriceVos = new ArrayList<TicketPriceVo>();
        TicketPriceVo ticketPriceVo = new TicketPriceVo();
        ticketPriceVo.setCardPrice("100");
        ticketPriceVos.add(ticketPriceVo);
        
        TicketPriceVo ticketPriceVo2 = new TicketPriceVo();
        ticketPriceVo2.setCardPrice("200");
        ticketPriceVos.add(ticketPriceVo2);
        
        TicketPriceVo ticketPriceVo3 = new TicketPriceVo();
        ticketPriceVo3.setCardPrice("300");
        ticketPriceVos.add(ticketPriceVo3);
        
        TicketPriceVo ticketPriceVo4 = new TicketPriceVo();
        ticketPriceVo4.setCardPrice("400");
        ticketPriceVos.add(ticketPriceVo4);
        
        callResult.setObjs(ticketPriceVos);
        callResult.setResult(true);
        
        logger.info("下载票价成功！");
        return callResult;
    }

    /**
     * 下载票种
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult queryCardTypes(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();

        List<TicketTypeVo> ticketTypeVos = new ArrayList<TicketTypeVo>();
        TicketTypeVo ticketTypeVo = new TicketTypeVo();
        ticketTypeVo.setCardCode("0001");
        ticketTypeVo.setCardDesc("单程票");
        ticketTypeVos.add(ticketTypeVo);
        
        TicketTypeVo ticketTypeVo2 = new TicketTypeVo();
        ticketTypeVo2.setCardCode("0002");
        ticketTypeVo2.setCardDesc("单程票2");
        ticketTypeVos.add(ticketTypeVo2);
        
        TicketTypeVo ticketTypeVo3 = new TicketTypeVo();
        ticketTypeVo3.setCardCode("0101");
        ticketTypeVo3.setCardDesc("储值票");
        ticketTypeVos.add(ticketTypeVo3);
        
        TicketTypeVo ticketTypeVo4 = new TicketTypeVo();
        ticketTypeVo4.setCardCode("0102");
        ticketTypeVo4.setCardDesc("储值票2");
        ticketTypeVos.add(ticketTypeVo4);
        
        callResult.setObjs(ticketTypeVos);
        callResult.setResult(true);
        
        logger.info("下载票种成功！");
        return callResult;
    }

    /**
     * 下载黑名单
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult queryBlacklist(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
  
        List<FtpFileParamVo> files = new ArrayList<FtpFileParamVo>();
        
        FtpFileParamVo fileVo = new FtpFileParamVo(ConfigUtil.getFtpLoginParam());
        fileVo.setFile("PRM.0601.20130701.01");
        fileVo.setLocalPath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditLocalPathTag));
        fileVo.setRemotePath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditRemotePathTag));         
        files.add(fileVo);
        
        FtpFileParamVo fileVo2 = new FtpFileParamVo(ConfigUtil.getFtpLoginParam());
        fileVo2.setFile("PRM.0602.20130701.01");
        fileVo2.setLocalPath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditLocalPathTag));
        fileVo2.setRemotePath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditRemotePathTag));         
        files.add(fileVo2);
        
        FtpFileParamVo fileVo3 = new FtpFileParamVo(ConfigUtil.getFtpLoginParam());
        fileVo3.setFile("PRM.0603.20130701.01");
        fileVo3.setLocalPath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditLocalPathTag));
        fileVo3.setRemotePath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditRemotePathTag));         
        files.add(fileVo3);
        
        FtpFileParamVo fileVo4 = new FtpFileParamVo(ConfigUtil.getFtpLoginParam());
        fileVo4.setFile("PRM.0604.20130701.01");
        fileVo4.setLocalPath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditLocalPathTag));
        fileVo4.setRemotePath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditRemotePathTag));         
        files.add(fileVo4);
        
        callResult.setObjs(files);
        callResult.setResult(true);
        
        logger.info("下载黑名单成功！");
        return callResult;
    }

    /**
     * 下载SAM列表
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult querySamlist(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
    
        List<FtpFileParamVo> files = new ArrayList<FtpFileParamVo>();
        
        FtpFileParamVo fileVo = new FtpFileParamVo(ConfigUtil.getFtpLoginParam());
        fileVo.setFile("PRM.0601.20130701.01");
        fileVo.setLocalPath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditLocalPathTag));
        fileVo.setRemotePath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditRemotePathTag));         
        files.add(fileVo);
        
        FtpFileParamVo fileVo2 = new FtpFileParamVo(ConfigUtil.getFtpLoginParam());
        fileVo2.setFile("PRM.0602.20130701.01");
        fileVo2.setLocalPath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditLocalPathTag));
        fileVo2.setRemotePath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditRemotePathTag));         
        files.add(fileVo2);
        
        FtpFileParamVo fileVo3 = new FtpFileParamVo(ConfigUtil.getFtpLoginParam());
        fileVo3.setFile("PRM.0603.20130701.01");
        fileVo3.setLocalPath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditLocalPathTag));
        fileVo3.setRemotePath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditRemotePathTag));         
        files.add(fileVo3);
        
        FtpFileParamVo fileVo4 = new FtpFileParamVo(ConfigUtil.getFtpLoginParam());
        fileVo4.setFile("PRM.0604.20130701.01");
        fileVo4.setLocalPath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditLocalPathTag));
        fileVo4.setRemotePath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpAuditRemotePathTag));         
        files.add(fileVo4);
        
        callResult.setObjs(files);
        callResult.setResult(true);
        
        logger.info("下载SAM列表成功！");
        return callResult;
    }

    /**
     * 关闭通讯
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult closeCommu(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
        
      
        callResult.setResult(true);
        
        return callResult;
    }

    /**
     * 查询生产订单
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult queryProduceOrders(CallParam callParam) throws CommuException {
        
        CallResult callResult =  new CallResult();
        
        List<Object[]> orders = new ArrayList<Object[]>();
        
        Object[] order = new Object[24];
        order[0] = "未开始";
        order[1] = "12345678901234";
        order[2] = "0102";
        order[3] = "学生储值票";
        order[4] = "20130416";
        order[5] = "1000";
        order[6] = "300";
        order[7] = "0000000001";
        order[8] = "0000000100";
        order[9] = "00000001";
        order[10] = "00001000";
        order[11] = "20130415";
        order[12] = 1000;
        order[13] = 0;
        order[14] = 0;
        order[15] = 0;
        order[16] = "001";
        order[17] = "05";
        order[18] = "13";
        order[19] = "00000000";
        order[20] = "000";
        order[21] = "00";
        order[22] = "00";
        order[23] = "000";

        orders.add(order);
        
        Object[] order2 = new Object[24];
        order2[0] = "未开始";
        order2[1] = "00005678900000";
        order2[2] = "0101";
        order2[3] = "成人储值票";
        order2[4] = "20130416";
        order2[5] = "2000";
        order2[6] = "500";
        order2[7] = "0000001001";
        order2[8] = "0000002000";
        order2[9] = "00001001";
        order2[10] = "00002000";
        order2[11] = "20130415";
        order2[12] = 1000;
        order2[13] = 0;
        order2[14] = 0;
        order2[15] = 0;
        order2[16] = "002";
        order2[17] = "06";
        order2[18] = "17";
        order2[19] = "00000000";
        order2[20] = "000";
        order2[21] = "00";
        order2[22] = "00";
        order2[23] = "000";

        orders.add(order2);
       
        callResult.setObjs(orders);
        callResult.setResult(true);
        
        logger.info("下载生产订单成功！");
        
        return callResult;
    }

    @Override
    public CallResult querySignCards(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
        callResult.setResult(true);
        
        logger.info("下载记名卡成功！");
        return callResult;
    }

    @Override
    public CallResult updateOrderStatus(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
        callResult.setResult(true);
        
        logger.info("更改订单状态成功！");
        return callResult;
    }
    
    @Override
    public CallResult updateDeviceStatus(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
        callResult.setResult(true);
        
        logger.info("更改设备状态成功！");
        return callResult;
    }

    @Override
    public boolean isCommuOk() throws CommuException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CallResult setKmsLock(CallParam callParam) throws CommuException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
