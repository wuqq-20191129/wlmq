/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.sammcs.application;

import com.goldsign.csfrm.application.adapter.ApplicationAdapter;
import com.goldsign.csfrm.env.BaseConstant;
import com.goldsign.csfrm.exception.AuthenException;
import com.goldsign.csfrm.exception.AuthorException;
import com.goldsign.csfrm.exception.LoadException;
import com.goldsign.csfrm.service.impl.ConfigFileService;
import com.goldsign.csfrm.thread.SystemClock;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.csfrm.vo.*;
import com.goldsign.login.bo.ILoginBo;
import com.goldsign.login.bo.LoginBo;
import com.goldsign.login.result.AuthResult;
import com.goldsign.login.vo.AuthInParam;
import com.goldsign.login.vo.ModuleDistrVo;
import com.goldsign.sammcs.env.AppConstant;
import com.goldsign.sammcs.env.ConfigConstant;
import com.goldsign.sammcs.service.IPSamIssueService;
import com.goldsign.sammcs.service.IRightService;
import com.goldsign.sammcs.service.impl.PSamIssueService;
import com.goldsign.sammcs.service.impl.RightService;
import com.goldsign.sammcs.thread.DbMonitorThread;
import com.goldsign.sammcs.util.ConfigUtil;
import com.goldsign.sammcs.util.DbcpHelper;
import com.goldsign.sammcs.vo.KmsCfgParam;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JLabel;
import org.apache.log4j.Logger;

/**
 *
 * @author lenovo
 */
public class Application extends ApplicationAdapter{

    
    private static final Logger logger = Logger.getLogger(Application.class.getName());
    
    private IPSamIssueService pSamIssueService;
    
    private IRightService rightService;
    
    
     public Application(String version){
        super(version);
        pSamIssueService = new PSamIssueService();
        rightService = new RightService();
        
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
        com.goldsign.csfrm.env.BaseConstant.APP_NAME_SELF_ADAPTION = "密钥卡发行系统";

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
            AppConstant.COMMU_STATUS = true;
        }
        //保存连接池信息
        AppConstant.dbcpHelper = (DbcpHelper) callResult.getObj();
        
        //打开加密机端口
        callResult = openKmsConnection();
        if(!callResult.isSuccess()){
            callResult.setResult(true);
            return callResult;
        }else{
            AppConstant.KMS_STATUS = true;
        }
        
        return callResult;
    }
    
    /**
     * 登录后，初始化
     * 
     * @param callParam
     * @return 
     */
    public CallResult loadEventAfterLoginCallBack(CallParam callParam){

        CallResult callResult = null;
        
        //打开加密机端口
        callResult = openKmsConnection();
        if(!callResult.isSuccess()){
            callResult.setResult(true);
            return callResult;
        }else{
            AppConstant.KMS_STATUS = true;
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
                    new XmlTagVo(ConfigConstant.KmsCommuTag),new XmlTagVo(ConfigConstant.SamIssueTag),
                    new XmlTagVo(ConfigConstant.BackupTag)
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
                ConfigConstant.DataConnectionPoolURLTag);
        String userId =  ConfigUtil.getConfigValue(ConfigConstant.DataConnectionPoolTag, 
                ConfigConstant.DataConnectionPoolUserTag);
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
            authenResult.setMsg("机器未在加密机上注册，请注册后再登录！");
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
        sBarStatusVos.add(new SBarStatusVo(AppConstant.STATUS_BAR_SITE_NAME, AppConstant.STATUS_BAR_SITE_NO,0.25f));
        sBarStatusVos.add(new SBarStatusVo(AppConstant.STATUS_BAR_KMS_STATUS_NAME, AppConstant.STATUS_BAR_KMS_STATUS,0.25f));
        sBarStatusVos.add(new SBarStatusVo(AppConstant.STATUS_BAR_COMMU_STATUS_NAME, AppConstant.STATUS_BAR_COMMU_STATUS,0.25f));
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
        
        //启动时钟线程
        startStatusBarSystemClock();
        
        //启动数据库心跳线程
        startDbMonitorThread();
        
        BaseConstant.publicPanel.setOpLink(AppConstant.STATUS_BAR_SITE_NO,
                ConfigUtil.getConfigValue(ConfigConstant.CommonTag, ConfigConstant.CommonStationIdTag));
        
        String[] vars = new String[]{AppConstant.STATUS_BAR_KMS_STATUS, AppConstant.STATUS_BAR_COMMU_STATUS};
        boolean[] statuses = new boolean[]{AppConstant.KMS_STATUS, AppConstant.COMMU_STATUS};
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
        DbMonitorThread  dbT = new DbMonitorThread();
        dbT.start();
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
            //关闭连接池
            AppConstant.dbcpHelper.close();
        } catch (SQLException ex) {
            logger.error(ex);
        }
        
        callResult.setResult(true);
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
        //add 20140312 author、Issue都用到keyVerstion参数
        String keyVerstion = ConfigUtil.getConfigValue(ConfigConstant.SamIssueTag, 
                ConfigConstant.SamIssueKeyVerstion);
        
        KmsCfgParam kmsCfgParam = new KmsCfgParam();
        kmsCfgParam.setKmsIp(kmsIp);
        kmsCfgParam.setKmsPort(kmsPort);
        kmsCfgParam.setKmsPin(kmsPin);
        kmsCfgParam.setKmsKeyVerstion(keyVerstion);
        
        callResult = getpSamIssueService().author(kmsCfgParam);
        if(!callResult.isSuccess()){
            return callResult;
        }
        
        return callResult;
    }
    
    public boolean isOpenKmsConnection() {
        
        boolean isOpen = false;
        
        CallResult callResult = new CallResult();
        
        String kmsIp = ConfigUtil.getConfigValue(ConfigConstant.KmsCommuTag, 
                ConfigConstant.KmsCommuKmIp);
        String kmsPort = ConfigUtil.getConfigValue(ConfigConstant.KmsCommuTag, 
                ConfigConstant.KmsCommuKmPort);
        String kmsPin = ConfigUtil.getConfigValue(ConfigConstant.KmsCommuTag, 
                ConfigConstant.KmsCommuKmPin);
        //add 20140312 author、Issue都用到keyVerstion参数
        String keyVerstion = ConfigUtil.getConfigValue(ConfigConstant.SamIssueTag, 
                ConfigConstant.SamIssueKeyVerstion);
        
        KmsCfgParam kmsCfgParam = new KmsCfgParam();
        kmsCfgParam.setKmsIp(kmsIp);
        kmsCfgParam.setKmsPort(kmsPort);
        kmsCfgParam.setKmsPin(kmsPin);
        kmsCfgParam.setKmsKeyVerstion(keyVerstion);
        
        callResult = getpSamIssueService().author(kmsCfgParam);
        if(callResult.isSuccess()){
            isOpen = true;
        }
        return isOpen;
    }
        
     /**
     * 返回权限模块转菜单数列
     * @param moduleDistrList 返回权限模块
     * @return 
     */
//    private List<SysModuleVo> moduleDistrToSys(List<ModuleDistrVo> moduleDistrList) {
//        List<SysModuleVo> sysModuleVos = new ArrayList<SysModuleVo>();
//        
//        for(int i=0; i<moduleDistrList.size(); i++){
//            ModuleDistrVo dvo = moduleDistrList.get(i);
//            SysModuleVo svo = new SysModuleVo();
//            boolean flag = true;
//            svo.setModuleId(dvo.getModuleID());
//            svo.setParentId(dvo.getParentId());
//            svo.setName(dvo.getMenuName());
//            svo.setIcon(dvo.getMenuIcon());
//            svo.setHandleClassName(dvo.getMenuUrl());
//            flag = dvo.getParentId().equals(svo.getModuleId());
//            svo.setModuleLevel(flag ? "1":"2");
//            
//            sysModuleVos.add(svo);
//        }
//        
//        return sysModuleVos;
//    }
    
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


    public IPSamIssueService getpSamIssueService() {
        return pSamIssueService;
    }

    public void setpSamIssueService(IPSamIssueService pSamIssueService) {
        this.pSamIssueService = pSamIssueService;
    }
    
    

}
