/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.etmcs.application;

import com.goldsign.csfrm.application.adapter.ApplicationAdapter;
import com.goldsign.csfrm.env.BaseConstant;
import com.goldsign.csfrm.exception.AuthenException;
import com.goldsign.csfrm.exception.AuthorException;
import com.goldsign.csfrm.exception.LoadException;
import com.goldsign.csfrm.service.impl.ConfigFileService;
import com.goldsign.csfrm.thread.SystemClock;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.csfrm.vo.*;
import com.goldsign.etmcs.env.AppConstant;
import com.goldsign.etmcs.env.CommuConstant;
import com.goldsign.etmcs.env.ConfigConstant;
import com.goldsign.etmcs.exception.CommuException;
import com.goldsign.etmcs.service.ICommuService;
import com.goldsign.etmcs.service.IKmsService;
import com.goldsign.etmcs.service.IPSamIssueService;
import com.goldsign.etmcs.service.IRightService;
import com.goldsign.etmcs.service.IRwDeviceService;
import com.goldsign.etmcs.service.impl.CommuService;
import com.goldsign.etmcs.service.impl.KmsService;
import com.goldsign.etmcs.service.impl.RightService;
import com.goldsign.etmcs.service.impl.RwDeviceService;
import com.goldsign.etmcs.thread.DbMonitorThread;
import com.goldsign.etmcs.thread.EsCommuMonitorThread;
import com.goldsign.etmcs.util.ConfigUtil;
import com.goldsign.etmcs.util.DbcpHelper;
import com.goldsign.etmcs.util.PubUtil;
import com.goldsign.etmcs.vo.CommonCfgParam;
import com.goldsign.etmcs.vo.KmsCfgParam;
import com.goldsign.etmcs.vo.PubFlagVo;
import com.goldsign.login.bo.ILoginBo;
import com.goldsign.login.bo.LoginBo;
import com.goldsign.login.result.AuthResult;
import com.goldsign.login.vo.AuthInParam;
import com.goldsign.login.vo.ModuleDistrVo;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import javax.swing.JLabel;
import org.apache.log4j.Logger;

/**
 *
 * @author lenovo
 */
public class Application extends ApplicationAdapter{

    
    private static final Logger logger = Logger.getLogger(Application.class.getName());
    
    private IRwDeviceService rwDeviceService;
    private IKmsService kmsService;
    private IRightService rightService;
    private ICommuService commuService;
    private IPSamIssueService pSamIssueService;

    
    public Application(){
        
        rwDeviceService = new RwDeviceService();
        kmsService = KmsService.getInstance();
        rightService = new RightService();
        this.commuService = CommuService.getInstance();//正式
    }
    
    public ICommuService getCommuService(){
        return this.commuService;
    }
    
    /**
     * 登录前，初始化
     * 
     * @param callParam
     * @return 
     */
    @Override
    public CallResult loadEventCallBack(CallParam callParam){
        
        //设置系统名称
        com.goldsign.csfrm.env.BaseConstant.APP_NAME_SELF_ADAPTION = "员工卡发行系统";

        CallResult callResult = null;
        
        //加载配置和日志文件
        callResult = loadConfigAndLogFile();
        if (!callResult.isSuccess()) {
            return callResult;
        }
        
        //保存配置文件信息
        AppConstant.configs = (Hashtable) callResult.getObj();
        
        //加载数据库连接池
        callResult = loadDataConnectionPool();
        if (!callResult.isSuccess()) {
            return callResult;
        }else{
            AppConstant.DATABASE_STATUS = true;
        }
        //保存连接池信息
        AppConstant.dbcpHelper = (DbcpHelper) callResult.getObj();
        
        //打开SOCKET通讯端口  Es通讯
        callResult = openCommu(callParam);
        if (!callResult.isSuccess()) {
            callResult.setResult(true); 
            return callResult;
        } else{
            AppConstant.COMMU_STATUS = true;
        }
        //打开加密机端口
        callResult = openKmsConnection();
        if(!callResult.isSuccess()){
            callResult.setResult(true); 
            return callResult;
        }else{
            AppConstant.KMS_STATUS = true;
        }
        
        //打开读写器串口
        callResult = openRwConnection();
        if(!callResult.isSuccess()){
            callResult.setResult(true); 
            return callResult;
        }else{
            AppConstant.READER_PORT = true;
        }
        
        return callResult;
    }
    
    /**
     * 加载配置和日志文件
     *
     * @return
     */
    public static CallResult loadConfigAndLogFile() {

        ConfigParam configParam = new ConfigParam(ConfigConstant.CONFIG_FILE, ConfigConstant.LOG_FILE,
                new XmlTagVo[]{
                    new XmlTagVo(ConfigConstant.CommonTag), new XmlTagVo(ConfigConstant.DataConnectionPoolTag),
                    new XmlTagVo(ConfigConstant.RwDeviceTag),new XmlTagVo(ConfigConstant.UploadTag),
                    new XmlTagVo(ConfigConstant.KmsCommuTag),new XmlTagVo(ConfigConstant.EsCommuTag)
                });
        
        try {
            return new ConfigFileService().loadConfigAndLogFile(configParam);
        } catch (LoadException ex) {
            logger.error(ex);
            return new CallResult(ex.getMessage());
        }

    }
    
    /**
     * 加载数据库连接池
     *
     * @return
     */
    private CallResult loadDataConnectionPool(){
        
        CallResult callResult = new CallResult();
        
        DbcpHelper dbcpHelper = null;
        String driverName = ConfigUtil.getConfigValue(ConfigConstant.DataConnectionPoolTag, 
                ConfigConstant.DataConnectionPoolDriverTag);
        String url = ConfigUtil.getConfigValue(ConfigConstant.DataConnectionPoolTag, 
                ConfigConstant.DataConnectionPoolURLTag);;
        String userId =  ConfigUtil.getConfigValue(ConfigConstant.DataConnectionPoolTag, 
                ConfigConstant.DataConnectionPoolUserTag);;
        String password = ConfigUtil.getConfigValue(ConfigConstant.DataConnectionPoolTag, 
                ConfigConstant.DataConnectionPoolPasswordTag);
        String maxActive = ConfigUtil.getConfigValue(ConfigConstant.DataConnectionPoolTag,
                ConfigConstant.DataConnectionPoolMaxActiveTag);
        String maxIdle = ConfigUtil.getConfigValue(ConfigConstant.DataConnectionPoolTag,
                ConfigConstant.DataConnectionPoolMaxIdleTag);
        String maxWait = ConfigUtil.getConfigValue(ConfigConstant.DataConnectionPoolTag,
                ConfigConstant.DataConnectionPoolMaxWaitTag);
        try {
            dbcpHelper = new DbcpHelper(driverName, url, userId, password,
                    StringUtil.getInt(maxActive), StringUtil.getInt(maxIdle), StringUtil.getInt(maxWait));
            
            callResult.setObj(dbcpHelper);
            callResult.setResult(true);
            
        } catch (ClassNotFoundException ex) {
            logger.error(ex);
            callResult.setMsg(ex.getMessage());
        }
 
        return callResult;
    }
    
    /**
     * 验证
     * 
     * @param param
     * @return
     * @throws AuthenException 
     */
    @Override
    protected AuthenResult authen(CallParam param) throws AuthenException {
        
        AuthenResult authenResult = new AuthenResult();
        
        LoginParam loginParam = (LoginParam)param;
        ILoginBo loginBo = new LoginBo();
        String account = loginParam.getUserNo();
        String password = loginParam.getPasswrod();
        String systemFlag = ConfigUtil.getConfigValue(ConfigConstant.CommonTag, ConfigConstant.CommonSystemFlagTag);
        AuthResult ar = null;
        AuthInParam ap = new AuthInParam();
        String ip = "127.0.0.1";
        try {
             ip = InetAddress.getLocalHost().getHostAddress();  
             System.out.println("IP:" + ip);
        } catch (Exception e) {
            throw new AuthenException(e);
        }
        ap.setIp(ip);
        try {
            ar = loginBo.authorization(account, password, systemFlag, ap, AppConstant.dbcpHelper.getDbHelper());
        } catch (Exception ex) {
            throw new AuthenException(ex);
        }
        if (ar.getReturnCode().equals(loginBo.SUCCESS_AUTH)) {
            
            SysUserVo sysUserVo = new SysUserVo();
            sysUserVo.setUsername(ar.getUser().getUsername());
            sysUserVo.setAccount(ar.getUser().getAccount());
            //添加权限菜单
            List<ModuleDistrVo> moduleDistrList = ar.getModules();
            List<SysModuleVo> sysModuleList = moduleDistrToSys(moduleDistrList);
            sysUserVo.setSysModuleVos(sysModuleList);
            
            authenResult.setObj(sysUserVo);
            authenResult.setResult(true);
        } else {
            authenResult.setMsg(ar.getMsg());
        }
        
        String deviceType = ConfigUtil.getConfigValue(ConfigConstant.CommonTag, ConfigConstant.CommonDeviceTypeTag);
        String deviceNo = ConfigUtil.getConfigValue(ConfigConstant.CommonTag, ConfigConstant.CommonDeviceNoTag);
        boolean deviceFlag = rightService.getDeviceConfigureCheck(ip, deviceType, deviceNo);
        if(!deviceFlag){
            authenResult.setResult(false);
            authenResult.setMsg("本机器未注册，请注册后再登录！");
        }
        return authenResult;
    }
        
    /**
     * 授权
     * 
     * @param user
     * @return
     * @throws AuthorException 
     */
    @Override
    protected AuthorResult author(SysUserVo user) throws AuthorException {
        
        AuthorResult authorResult = new AuthorResult();
        List<SysModuleVo> modules = new ArrayList<SysModuleVo>();
        //取权限功能
        modules = rightService.getSysModules(user);
        
        authorResult.setObjs(modules);
        authorResult.setResult(true);
        
        return authorResult;
    }
        
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
        sBarStatusVos.add(new SBarStatusVo(AppConstant.STATUS_BAR_SITE_NAME, AppConstant.STATUS_BAR_SITE_NO,0.16f));
        sBarStatusVos.add(new SBarStatusVo(AppConstant.STATUS_BAR_COMMU_STATUS_NAME, AppConstant.STATUS_BAR_COMMU_STATUS,0.16f));
        sBarStatusVos.add(new SBarStatusVo(AppConstant.STATUS_BAR_KMS_STATUS_NAME, AppConstant.STATUS_BAR_KMS_STATUS,0.16f));
        sBarStatusVos.add(new SBarStatusVo(AppConstant.STATUS_BAR_READER_PORT_NAME, AppConstant.STATUS_BAR_READER_PORT,0.16f));
        sBarStatusVos.add(new SBarStatusVo(AppConstant.STATUS_BAR_BATABASE_STATUS_NAME, AppConstant.STATUS_BAR_BATABASE_STATUS,0.16f));
        sBarStatusVos.add(new SBarStatusVo(AppConstant.STATUS_BAR_CURRENT_TIME_NAME, AppConstant.STATUS_BAR_CURRENT_TIME,0.25f));
        
        callResult.setObjs(sBarStatusVos);
        callResult.setResult(true);
        
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
        
        CallResult callResult = new CallResult();
        
         //加载参数数值
        loadParamersValue();
        
        //启动时钟线程
        startStatusBarSystemClock();
        
        //启动数据库心跳线程
        startDbMonitorThread();
        
        //启动通讯心跳线程
        startCommuMonitorThread();
        
        BaseConstant.publicPanel.setOpLink(AppConstant.STATUS_BAR_SITE_NO,
                ConfigUtil.getConfigValue(ConfigConstant.CommonTag, ConfigConstant.CommonStationIdTag));
        
        String[] vars = new String[]{AppConstant.STATUS_BAR_COMMU_STATUS,AppConstant.STATUS_BAR_KMS_STATUS, 
            AppConstant.STATUS_BAR_READER_PORT,AppConstant.STATUS_BAR_BATABASE_STATUS};
        boolean[] statuses = new boolean[]{AppConstant.COMMU_STATUS, AppConstant.KMS_STATUS,
            AppConstant.READER_PORT, AppConstant.DATABASE_STATUS};
        setSBarStatus(vars, statuses);
        
        callResult.setResult(true);
        
        return callResult;
    }
    
    /**
     * 启动系统时钟
     *
     * @param callParam
     * @return
     */
    private void startStatusBarSystemClock() {

        //启动系统时间线程
        JLabel currentTime = BaseConstant.publicPanel.getStatusOpLinkComp(AppConstant.STATUS_BAR_CURRENT_TIME);
        SystemClock sc = new SystemClock(currentTime);
        sc.start();

    }
    
    /**
     * 启动数据库心跳线程
     */
    private void startDbMonitorThread() {
        DbMonitorThread dbT = new DbMonitorThread();
        dbT.start();
    }
    
    /**
     * 启动数据库心跳线程
     */
    private void startCommuMonitorThread() {
        EsCommuMonitorThread commuT = new EsCommuMonitorThread(commuService);
        commuT.start();
    }

    /**
     * 取消登录
     * 
     * @param callParam
     * @return 
     */
    @Override
    public CallResult logexitEventCallBack(CallParam callParam) {
        return exitEventCallBack(callParam);
    }
    
   /**
     * 退出回调
     * 
     * @param callParam
     * @return 
     */
    @Override
    public CallResult exitEventCallBack(CallParam callParam) {
        
        CallResult callResult = new CallResult();
        try {
            synchronized(CommuConstant.SYN_WIN_EXIT_LOCK){
                callResult = commuService.closeCommu(callParam);
                this.sysAppVo.setAppStatus(AppConstant.SYS_APP_CUR_STATUS_EXIT);
            }
            if(callResult.isSuccess()){
                logger.info("关闭通讯成功！");
            }
        } catch (Exception ex) {
            logger.error("关闭通讯异常："+ex);
        }
        
        try {
            //关闭连接池
            AppConstant.dbcpHelper.close();
        } catch (SQLException ex) {
            logger.error(ex);
        }
        
        //关闭读头串口
        if(AppConstant.READER_PORT){
            callResult = getRwDeviceService().closeConnection();
        }else{
            callResult.setResult(true);
            callResult.resetMsg("读写器串口未打开！");
        }
        return callResult;
    }

    private CallResult openRwConnection() {
        
        CallResult callResult = new CallResult();
        
        String portNo = ConfigUtil.getConfigValue(ConfigConstant.RwDeviceTag, 
                ConfigConstant.RwDeviceRwPortTag);
        callResult = getRwDeviceService().openConnection(portNo);//打开端口
        if(!callResult.isSuccess()){
            return callResult;
        }
        
        //站点：0103，设备：030003
        String stationId = ConfigUtil.getConfigValue(ConfigConstant.CommonTag, 
                ConfigConstant.CommonStationIdTag);
        String deviceType = ConfigUtil.getConfigValue(ConfigConstant.CommonTag, 
                ConfigConstant.CommonDeviceTypeTag);
        String deviceId = ConfigUtil.getConfigValue(ConfigConstant.CommonTag,
                ConfigConstant.CommonDeviceNoTag);
        CommonCfgParam commonCfgParam = new CommonCfgParam();
        commonCfgParam.setStationId(stationId);
        commonCfgParam.setDeviceType(deviceType);
        commonCfgParam.setDeviceNo(deviceId);
        callResult = getRwDeviceService().initDevice(commonCfgParam);//初始化设备
        
        return callResult;
    }

    private CallResult openKmsConnection() {
        
        CallResult callResult = new CallResult();
        
        String kmsIp = ConfigUtil.getConfigValue(ConfigConstant.KmsCommuTag, 
                ConfigConstant.KmsCommuKmIp);
        String kmsPort = ConfigUtil.getConfigValue(ConfigConstant.KmsCommuTag, 
                ConfigConstant.KmsCommuKmPort);
        String kmsPin = ConfigUtil.getConfigValue(ConfigConstant.KmsCommuTag, 
                ConfigConstant.KmsCommuKmPin);
        String kmsVersion = ConfigUtil.getConfigValue(ConfigConstant.KmsCommuTag, 
                ConfigConstant.KmsCommuKmVersion);
        
        KmsCfgParam kmsCfgParam = new KmsCfgParam();
        kmsCfgParam.setKmsIp(kmsIp);
        kmsCfgParam.setKmsPort(kmsPort);
        kmsCfgParam.setKmsPin(kmsPin);
        kmsCfgParam.setKmsVersion(kmsVersion);
        callResult = getKmsService().author(kmsCfgParam);
        
        if(!callResult.isSuccess()){
            return callResult;
        }
        
        return callResult;
    }

    /**
     * @return the rwDeviceService
     */
    public IRwDeviceService getRwDeviceService() {
        return rwDeviceService;
    }

    /**
     * @return the kmsService
     */
    public IKmsService getKmsService() {
        return kmsService;
    }

    //加载 站点，线路，限制模式和票卡主类型
    private void loadParamersValue() {
        PubUtil pubUtil = new PubUtil();
        String paramer = AppConstant.TABLE_ET_PARAMER;
        //加载员工发卡系统参数表数据
        Vector v = pubUtil.getTablePubFlag(paramer);
        
        for(int i=0;i<v.size();i++){
            PubFlagVo pvo = new PubFlagVo();
            pvo = (PubFlagVo) v.get(i);
            //发行状态
            if(pvo.getType().equals(AppConstant.STR_ISSUE_STATUS)){
                AppConstant.ISSUE_STATUS.put(pvo.getCode(), pvo.getCodeText());
            }
            //票卡状态 以附录5“车票状态”为准 bStatus
            if(pvo.getType().equals(AppConstant.STR_TICKET_STATUS)){
                AppConstant.TICKET_STATUS.put(pvo.getCode(), pvo.getCodeText());
            }
            //票卡物理类型 bCharacter 1：UL；2：CPU；
            if(pvo.getType().equals(AppConstant.STR_PHY_CHARACTER)){
                AppConstant.PHY_CHARACTER.put(pvo.getCode(), pvo.getCodeText());
            }
            //性别 certificate_sex
            if(pvo.getType().equals(AppConstant.STR_CERTIFICATE_SEX)){
                AppConstant.CERTIFICATE_SEX.put(pvo.getCode(), pvo.getCodeText());
            }
            //持卡类型 certificate_iscompany
            if(pvo.getType().equals(AppConstant.STR_CERTIFICATE_ISCOMPANY)){
                AppConstant.CERTIFICATE_ISCOMPANY.put(pvo.getCode(), pvo.getCodeText());
            }
            // 证件类型 certificate_type
            if(pvo.getType().equals(AppConstant.STR_CERTIFICATE_TYPE)){
                AppConstant.CERTIFICATE_TYPE.put(pvo.getCode(), pvo.getCodeText());
            }
            // 本单位职工 (地铁通卡) certificate_ismetro
            if(pvo.getType().equals(AppConstant.STR_CERTIFICATE_ISMETRO)){
                AppConstant.CERTIFICATE_ISMETRO.put(pvo.getCode(), pvo.getCodeText());
            }
            // 员工单位 employee_department
            if(pvo.getType().equals(AppConstant.STR_EMPLOYEE_DEPARTMENT)){
                AppConstant.EMPLOYEE_DEPARTMENT.put(pvo.getCode(), pvo.getCodeText());
            }
            // 员工职务 employee_position
            if(pvo.getType().equals(AppConstant.STR_EMPLOYEE_POSITION)){
                AppConstant.EMPLOYEE_POSITION.put(pvo.getCode(), pvo.getCodeText());
            }
            // 员工级别 employee_class
            if(pvo.getType().equals(AppConstant.STR_EMPLOYEE_CLASS)){
                AppConstant.EMPLOYEE_CLASS.put(pvo.getCode(), pvo.getCodeText());
            }
        }
        //票卡类型 cTicketType[4];	（取主类型，前两位）
        AppConstant.TICKET_TYPE = pubUtil.getTableColumn(AppConstant.TABLE_OP_CARD_MAIN_TYPE,"CARD_MAIN_ID" ,"CARD_MAIN_NAME" ,"record_flag" ,"3" );
        //线路 cLine[2]
        AppConstant.C_LINE = pubUtil.getTableColumn(AppConstant.TABLE_C_LINE,"LINE_ID" ,"LINE_NAME" ,"record_flag" ,"3" );
        //站点 cStationNo[2]
        AppConstant.C_STATION_NO= pubUtil.getTableColumn(AppConstant.TABLE_C_STATION,"STATION_ID" ,"CHINESE_NAME" ,"record_flag" ,"3" );
        //限制模式 cLimitMode[3]
        AppConstant.C_LIMIT_MODE = pubUtil.getTableColumn(AppConstant.TABLE_OP_PUB_FLAG,"CODE" ,"CODE_TEXT" ,"TYPE" ,"26");
        
    }

    /**
     * 返回权限模块转菜单数列 2014-01-19 修改新菜单权限 
     * @param moduleDistrList 返回权限模块
     * @return 
     */
    private List<SysModuleVo> moduleDistrToSys(List<ModuleDistrVo> moduleDistrList) {
        List<SysModuleVo> sysModuleVos = new ArrayList<SysModuleVo>();
        
        for(int i=0; i<moduleDistrList.size(); i++){
            ModuleDistrVo dvo = moduleDistrList.get(i);
            SysModuleVo svo = new SysModuleVo();
            svo.setModuleId(dvo.getModuleID());
            svo.setParentId(dvo.getParentId());
            svo.setName(dvo.getMenuName());
            svo.setIcon(dvo.getMenuIcon());
            svo.setHandleClassName(dvo.getMenuUrl());
            svo.setModuleLevel(String.valueOf(svo.getModuleId().length()/2));
            
            sysModuleVos.add(svo);
        }
        
        return sysModuleVos;
    }
    
    /**
     * 打开SOCKET通讯端口
     * 
     * @param callParam
     * @return 
     */
    private CallResult openCommu(CallParam callParam){
        
        CallResult callResult = null;
        
        try {
            //打开SOCKET通讯端口
            callResult = commuService.openCommu(callParam);
        } catch (CommuException ex) {
            logger.error("打开SOCKET通讯异常:"+ex);
            return new CallResult("打开SOCKET通讯异常，请联系管理员!");
        }
        if (!callResult.isSuccess()) {
            callResult.resetMsg("打开SOCKET通讯异常，请联系管理员!");
            logger.error("打开SOCKET通讯失败:"+callResult.getMsg());
            return callResult;
        } else {
            //保存通讯状态
            AppConstant.COMMU_STATUS = true;
            logger.info("打开SOCKET通讯成功！");
        }
        return callResult;
    }
    
    public IPSamIssueService getpSamIssueService() {
        return pSamIssueService;
    }

    public void setpSamIssueService(IPSamIssueService pSamIssueService) {
        this.pSamIssueService = pSamIssueService;
    }
    
}
