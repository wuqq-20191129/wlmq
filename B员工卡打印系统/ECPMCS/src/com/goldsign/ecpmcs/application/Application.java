/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.ecpmcs.application;

import com.goldsign.csfrm.application.adapter.ApplicationAdapter;
import com.goldsign.csfrm.env.BaseConstant;
import com.goldsign.csfrm.exception.AuthenException;
import com.goldsign.csfrm.exception.AuthorException;
import com.goldsign.csfrm.exception.LoadException;
import com.goldsign.csfrm.service.impl.ConfigFileService;
import com.goldsign.csfrm.thread.SystemClock;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.csfrm.vo.AuthenResult;
import com.goldsign.csfrm.vo.AuthorResult;
import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.csfrm.vo.ConfigParam;
import com.goldsign.csfrm.vo.LoginParam;
import com.goldsign.csfrm.vo.SBarStatusVo;
import com.goldsign.csfrm.vo.SysModuleVo;
import com.goldsign.csfrm.vo.SysUserVo;
import com.goldsign.csfrm.vo.XmlTagVo;
import com.goldsign.login.bo.ILoginBo;
import com.goldsign.login.bo.LoginBo;
import com.goldsign.login.result.AuthResult;
import com.goldsign.login.vo.ModuleDistrVo;
import com.goldsign.ecpmcs.env.AppConstant;
import com.goldsign.ecpmcs.env.ConfigConstant;
import com.goldsign.ecpmcs.service.IRightService;
import com.goldsign.ecpmcs.service.IRwDeviceService;
import com.goldsign.ecpmcs.service.impl.RightService;
import com.goldsign.ecpmcs.service.impl.RwDeviceService;
import com.goldsign.ecpmcs.thread.DbMonitorThread;
import com.goldsign.ecpmcs.util.ConfigUtil;
import com.goldsign.ecpmcs.util.DbcpHelper;
import com.goldsign.ecpmcs.util.PubUtil;
import com.goldsign.ecpmcs.vo.PubFlagVo;
import com.goldsign.login.vo.AuthInParam;
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
    private IRightService rightService;
    
    public Application(){
        rwDeviceService = new RwDeviceService();
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
        com.goldsign.csfrm.env.BaseConstant.APP_NAME_SELF_ADAPTION = "记名卡票面打印系统";
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
                    new XmlTagVo(ConfigConstant.PhotoTag),new XmlTagVo(ConfigConstant.PrinterTag)});
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
        
        //加载打印错误代码
        loadPrintErrCode();
        
        //加载打印模板
        loadPrintTemplate();
        
        //启动时钟线程
        startStatusBarSystemClock();
        
        //启动数据库心跳线程
        startDbMonitorThread();
        
        String[] vars = new String[]{AppConstant.STATUS_BAR_READER_PORT,AppConstant.STATUS_BAR_BATABASE_STATUS};
        boolean[] statuses = new boolean[]{AppConstant.READER_PORT, AppConstant.DATABASE_STATUS};
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
        
        return callResult;
    }

    /**
     * @return the rwDeviceService
     */
    public IRwDeviceService getRwDeviceService() {
        return rwDeviceService;
    }


    //加载 性别、证件类型和票卡主类型
    private void loadParamersValue() {
        PubUtil pubUtil = new PubUtil();
        //加载系统参数表数据
        Vector v = pubUtil.getTablePubFlag(AppConstant.TABLE_SCP_PARAMER);
        for(int i=0;i<v.size();i++){
            PubFlagVo pvo = new PubFlagVo();
            pvo = (PubFlagVo) v.get(i);
            //性别 certificate_sex
            if(pvo.getType().equals(AppConstant.STR_CERTIFICATE_SEX)){
                AppConstant.CERTIFICATE_SEX.put(pvo.getCode(), pvo.getCodeText());
            }
            // 证件类型 certificate_type
            if(pvo.getType().equals(AppConstant.STR_CERTIFICATE_TYPE)){
                AppConstant.CERTIFICATE_TYPE.put(pvo.getCode(), pvo.getCodeText());
            }
            // 员工卡发行状态
            if(pvo.getType().equals(AppConstant.STR_ET_STATE)){
                AppConstant.ET_STATE.put(pvo.getCode(), pvo.getCodeText());
            }
        }
        
        //加载员工卡发行系统参数表数据
        Vector et = pubUtil.getTablePubFlag(AppConstant.TABLE_ET_PARAMER);
        for(int i=0;i<et.size();i++){
            PubFlagVo pvo = new PubFlagVo();
            pvo = (PubFlagVo) et.get(i);
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
        AppConstant.TICKET_TYPE = pubUtil.getTableColumn(
            AppConstant.TABLE_OP_CARD_SUB_TYPE,"CARD_MAIN_ID||CARD_SUB_ID" ,"CARD_SUB_NAME" ,"record_flag" ,AppConstant.RECORD_CURRENT );
        
    }

    /**
     * 返回权限模块转菜单数列
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
     * 加载打印错误代码
     */
    private void loadPrintErrCode(){
        if(PubUtil.loadPrintErrFile()){
            logger.info("加载打印错误代码成功！");
        }else{
            logger.info("加载打印错误代码失败！");
        }
    }
    
    /**
     * 加载打印模板
     */
    private void loadPrintTemplate(){
        PubUtil.loadPrintTemplate();
        if(AppConstant.PRINT_TEMPLATE.size()>0){
            logger.info("加载打印模板成功！");
        }else{
            logger.info("加载打印模板失败！");
        }
    }
    
}
