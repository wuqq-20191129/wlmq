/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.application;

import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.csfrm.vo.SBarStatusVo;
import com.goldsign.esmcs.dll.structure.BoxInf;
import com.goldsign.esmcs.env.PKAppConstant;
import com.goldsign.esmcs.env.PKConfigConstant;
import com.goldsign.esmcs.exception.EsJniException;
import com.goldsign.esmcs.exception.PkEsJniException;
import com.goldsign.esmcs.exception.RwJniException;
import com.goldsign.esmcs.service.IPkEsDeviceService;
import com.goldsign.esmcs.service.IRwDeviceService;
import com.goldsign.esmcs.service.impl.PkEsDeviceService;
import com.goldsign.esmcs.service.impl.PkRightService;
import com.goldsign.esmcs.service.impl.RwDeviceService;
import com.goldsign.esmcs.thread.PhyLogicFileDownThread;
import com.goldsign.esmcs.vo.BoxInfoVo;
import com.goldsign.esmcs.vo.EsBaseInfo;
import com.goldsign.esmcs.vo.EsPortParam;
import com.goldsign.esmcs.vo.RwPortParam;
import com.goldsign.esmcs.vo.SamCardVo;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * 储值票应用
 * 
 * @author lenovo
 */
public class PKApplication extends Application{

    private static final Logger logger = Logger.getLogger(PKApplication.class.getName());
     
    private IPkEsDeviceService pkEsDeviceService; //储值票ES设备服务
    private IRwDeviceService rwDeviceService; //读写器服务
    private PhyLogicFileDownThread phyLogicFileDownThread;
    
    public PKApplication(){

    }
            
    public PKApplication(String versionNo){
        super(versionNo, PKAppConstant.APP_CODE, PKAppConstant.APP_NAME);
        this.rwDeviceService = new RwDeviceService();
        this.pkEsDeviceService = new PkEsDeviceService();
        //注入父类的ES设备服务
        super.setEsDeviceService(pkEsDeviceService);
        super.setRightService(new PkRightService());//注入权限类
    }
    
    public IPkEsDeviceService getPkEsDeviceService(){
        
        return this.pkEsDeviceService;
    }
    
    public IRwDeviceService getRwDeviceService(){
        return this.rwDeviceService;
    }

    @Override
    public IRwDeviceService[] getRwDeviceServices(){
        return new IRwDeviceService[]{getRwDeviceService()};
    }

    /**
     * 加载事件
     * 
     * @param callParam
     * @return 
     */
    @Override
    public CallResult loadEventCallBack(CallParam callParam) {
        
        com.goldsign.csfrm.env.BaseConstant.APP_NAME_SELF_ADAPTION = "票卡生产系统"; 
        return super.loadEventCallBack(callParam);
    }
    
    /**
     * 启动物理卡号与逻辑卡号对照文件下载服务
     *
     */
    private CallResult startPhyLogicFileThread(CallParam callParam) {

        CallResult callResult = new CallResult();

        phyLogicFileDownThread = new PhyLogicFileDownThread();
        phyLogicFileDownThread.start();
        logger.info("启动物理卡号与逻辑卡号对照文件下载服务线程！");

        callResult.setResult(true);

        return callResult;
    }
    
    /**
     * 停止物理卡号与逻辑卡号对照文件下载服务
     * 
     * @param callParam 
     
    private CallResult stopPhyLogicFileThread(CallParam callParam) {
        
        CallResult callResult = new CallResult();
        
        phyLogicFileDownThread.interrupt();
        phyLogicFileDownThread = null;
        
        logger.info("物理卡号与逻辑卡号对照文件下载服务线程！");
        
        callResult.setResult(true);
        
        return callResult;
        
    }*/
   
   /**
    * 生成状态栏
    * 
    * @param param
    * @return 
    */
   @Override
    public CallResult genStatusBarEventCallBack(CallParam param){
        
       CallResult callResult = new CallResult();

       List<SBarStatusVo> sBarStatusVos = new ArrayList<SBarStatusVo>();
       sBarStatusVos.add(new SBarStatusVo(PKAppConstant.STATUS_BAR_BOX_PORT_NAME, PKAppConstant.STATUS_BAR_BOX_PORT, 0.13f));
       sBarStatusVos.add(new SBarStatusVo(PKAppConstant.STATUS_BAR_CHANNEL_PORT_NAME, PKAppConstant.STATUS_BAR_CHANNEL_PORT, 0.13f));
       sBarStatusVos.add(new SBarStatusVo(PKAppConstant.STATUS_BAR_PRINTER_PORT_NAME, PKAppConstant.STATUS_BAR_PRINTER_PORT, 0.13f));
       sBarStatusVos.add(new SBarStatusVo(PKAppConstant.STATUS_BAR_KMS_STATUS_NAME, PKAppConstant.STATUS_BAR_KMS_STATUS, 0.13f));
       sBarStatusVos.add(new SBarStatusVo(PKAppConstant.STATUS_BAR_RW_PORT_STATE.name, PKAppConstant.STATUS_BAR_RW_PORT_STATE.port, 0.13f));
       sBarStatusVos.add(new SBarStatusVo(PKAppConstant.STATUS_BAR_COMMU_STATUS_NAME, PKAppConstant.STATUS_BAR_COMMU_STATUS, 0.13f));
       sBarStatusVos.add(new SBarStatusVo(PKAppConstant.STATUS_BAR_CURRENT_TIME_NAME, PKAppConstant.STATUS_BAR_CURRENT_TIME, 0.22f));
        
       callResult.setObjs(sBarStatusVos);
       callResult.setResult(true);

        return callResult;
    }

    /**
     * 打开ES端口
     * 
     * @param callParam
     * @return
     * @throws EsJniException 
     */
    @Override
    protected CallResult openEsPort(CallParam callParam)throws EsJniException  {
        
        CallResult callResult = new CallResult();
        
        Hashtable esDevices = (Hashtable) PKAppConstant.configs.get(PKConfigConstant.EsDeviceTag);
        
        EsPortParam portParam = new EsPortParam();
        short channelPort = StringUtil.getShort(esDevices.get(PKConfigConstant.EsDevicePkChannelPortTag).toString());
        portParam.setPort(channelPort);
        CallResult channelCallResult = openEsChannelPort(portParam);
        
        short boxPort = StringUtil.getShort(esDevices.get(PKConfigConstant.EsDevicePkBoxPortTag).toString());
        portParam.setPort(boxPort);
        CallResult boxCallResult = openEsBoxPort(portParam);
        
        callResult.setResult(channelCallResult.isSuccess()&&boxCallResult.isSuccess());
        
        return callResult;
    }
    
    /**
     * 打开ES通道端口
     * 
     * @param portParam
     * @return 
     */
    private CallResult openEsChannelPort(EsPortParam portParam) throws PkEsJniException{
        
        CallResult channelCallResult = pkEsDeviceService.openChannelPort(portParam);
        if (channelCallResult.isSuccess()) {
            logger.info("打开通道端口成功！");
            PKAppConstant.CHANNEL_PORT = true;
        }

        return channelCallResult;
    }

    /**
     * 打开ES票箱端口
     * 
     * @param portParam
     * @return
     * @throws PkEsJniException 
     */
    private CallResult openEsBoxPort(EsPortParam portParam) throws PkEsJniException{
        
        CallResult boxCallResult = pkEsDeviceService.openBoxPort(portParam);
        CallParam callParam = new CallParam();
            CallResult boxStateResult  = pkEsDeviceService.getAllBoxState(portParam);//limj 检测急停状态下票箱返回异常
            BoxInfoVo[] boxInfs = (BoxInfoVo[])boxStateResult.getObj(1);//limj 检测急停状态下票箱返回异常
            int state = boxInfs[0].getBoxRunState();//limj 检测急停状态下票箱返回异常
            if(state==0){
                logger.info("打开票箱端口失败！");
                PKAppConstant.BOX_PORT = false;
            }else if (boxCallResult.isSuccess()) {
                logger.info("打开票箱端口成功！");
                PKAppConstant.BOX_PORT = true;
            }
            

         return boxCallResult;
    }
    /**
     * 打开读头端口
     * 
     * @param callParam
     * @return
     * @throws RwJniException 
     */
    @Override
    protected CallResult openRwPort(CallParam callParam) throws RwJniException {
        
        CallResult callResult = null;
        
        Hashtable rwDevices = (Hashtable) PKAppConstant.configs.get(PKConfigConstant.RwDeviceTag);
        Hashtable commons = (Hashtable) PKAppConstant.configs.get(PKConfigConstant.CommonTag);
        RwPortParam rwPortParam = new RwPortParam();
        String port = rwDevices.get(PKConfigConstant.RwDeviceRwPortTAG).toString();
        rwPortParam.setPort(port);
        String stationId = commons.get(PKConfigConstant.CommonStationIdTag).toString();
        rwPortParam.setStationId(stationId);
        String deviceType = commons.get(PKConfigConstant.CommonDeviceTypeTag).toString();
        rwPortParam.setDeviceType(deviceType);
        String deviceId = commons.get(PKConfigConstant.CommonDeviceNoTag).toString();       
        rwPortParam.setDeviceId(deviceId);
        //打开端口            
        callResult = rwDeviceService.openRwPort(rwPortParam);
        if(callResult.isSuccess()){
            logger.info("打开Rw端口成功！");
            PKAppConstant.STATUS_BAR_RW_PORT_STATE.state = true;
        }
        //取得SAM卡号
        CallResult callResultSam = rwDeviceService.getSamCard(null);
        if(callResultSam.isSuccess()){
            SamCardVo samCardVo = (SamCardVo) callResultSam.getObj();
            logger.info("取SAM卡号成功，SAM卡："+ samCardVo);
            PKAppConstant.STATUS_BAR_RW_PORT_STATE.samNo = samCardVo.getSamNo();
        }
        
        return callResult;
    }

    /**
     * 完成事件，设置状态栏状态
     * 
     * @param callParam
     * @return 
     */
    @Override
    public CallResult finishEventCallBack(CallParam callParam) {
        
        CallResult callResult = super.finishEventCallBack(callParam);
        
        String[] vars = new String[]{PKAppConstant.STATUS_BAR_RW_PORT_STATE.port, PKAppConstant.STATUS_BAR_BOX_PORT, 
            PKAppConstant.STATUS_BAR_CHANNEL_PORT};
        boolean[] statuses = new boolean[]{PKAppConstant.STATUS_BAR_RW_PORT_STATE.state, PKAppConstant.BOX_PORT, PKAppConstant.CHANNEL_PORT};
        
        setSBarStatus(vars, statuses);
        
        //下载物理卡号与逻辑卡号对照文件消息
        callResult = startPhyLogicFileThread(callParam);
        
        return callResult;
    }

    /**
     * 关闭ES端口
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    @Override
    protected CallResult closeEsPort(CallParam callParam) throws PkEsJniException {
        
        CallResult callResult = new CallResult();
        
        CallResult channelCallResult = closeEsChannelPort(callParam);
        
        CallResult boxCallResult = closeEsBoxPort(callParam);
            
        callResult.setResult(channelCallResult.isSuccess()&&boxCallResult.isSuccess());
        
        return callResult;
    }
    
    /**
     * 关闭ES通道端口
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    private CallResult closeEsChannelPort(CallParam callParam) throws PkEsJniException{
        
        CallResult channelCallResult = pkEsDeviceService.closeChannelPort(callParam);
        if (channelCallResult.isSuccess()) {
            PKAppConstant.CHANNEL_PORT = false;
            logger.info("关闭ES通道端口成功！");
        }
        
        return channelCallResult;
    }

    /**
     * 关闭票箱端口
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    private CallResult closeEsBoxPort(CallParam callParam) throws PkEsJniException{
        
        CallResult boxCallResult = pkEsDeviceService.closeBoxPort(callParam);
        if (boxCallResult.isSuccess()) {
            PKAppConstant.BOX_PORT = false;
            logger.info("关闭ES票箱端口成功！");
        }
        
        return boxCallResult;
    }
    
    
    /**
     * 关闭RW端口
     * 
     * @param callParam
     * @return
     * @throws RwJniException 
     */
    @Override
    protected CallResult closeRwPort(CallParam callParam) throws RwJniException {

        CallResult callResult = null;
        
        callResult = rwDeviceService.closeRwPort(callParam);
        if(callResult.isSuccess()){
            PKAppConstant.STATUS_BAR_RW_PORT_STATE.state = false;
            logger.info("关闭读头端口成功！");
        }
        
        return callResult;
    }

    /**
     * 退出事件
     * 
     * @param callParam
     * @return 
     */
    @Override
    public CallResult exitEventCallBack(CallParam callParam) {
        
        CallResult callResult = super.exitEventCallBack(callParam);
        
        //stopPhyLogicFileThread(null);
        
        return callResult;
    }
    
}
