package com.goldsign.esmcs.application; 
 
import com.goldsign.csfrm.application.adapter.ApplicationAdapter;
import com.goldsign.csfrm.env.BaseConstant;
import com.goldsign.csfrm.exception.AuthenException;
import com.goldsign.csfrm.exception.AuthorException;
import com.goldsign.csfrm.thread.SystemClock;
import com.goldsign.csfrm.util.DateHelper;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.csfrm.vo.*;
import com.goldsign.esmcs.env.AppConstant;
import com.goldsign.esmcs.env.ConfigConstant;
import com.goldsign.esmcs.env.SynLockConstant;
import com.goldsign.esmcs.exception.*;
import com.goldsign.esmcs.ftp.FtpMonitor;
import com.goldsign.esmcs.service.*;
import com.goldsign.esmcs.service.impl.CommuService;
import com.goldsign.esmcs.service.impl.FileService;
import com.goldsign.esmcs.service.impl.KmsService;
import com.goldsign.esmcs.thread.ComFileDownThread;
import com.goldsign.esmcs.thread.EsCommuMonitorThread;
import com.goldsign.esmcs.thread.EsOrderNoticeThread;
import com.goldsign.esmcs.util.ConfigUtil;
import com.goldsign.esmcs.util.Converter;
import com.goldsign.esmcs.util.DbcpHelper;
import com.goldsign.esmcs.util.PubUtil;
import com.goldsign.esmcs.vo.CityParamVo;
import com.goldsign.esmcs.vo.EsPortParam;
import com.goldsign.esmcs.vo.KmsCfgParam;
import com.goldsign.esmcs.vo.PubFlagVo;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import javax.swing.JLabel;
import org.apache.log4j.Logger;

/**
 * ES应用类
 *
 * @author lenovo
 */
public abstract class Application extends ApplicationAdapter{

    private static final Logger logger = Logger.getLogger(Application.class.getName());
    
    private ICommuService commuService; //SOCKET通讯服务接口
    private IFileService fileService; //文件操作接口
    private IEsDeviceService esDeviceService;
    private IKmsService kmsService;
    private ComFileDownThread fileDownServer;
    private EsCommuMonitorThread commuMonitorThread;
    private IRightService rightService;//权限类
    private EsOrderNoticeThread orderNoticeThread;
    private SystemClock systemClock;
    
    public Application(){
    
    }
    
    public Application(String versionNo, String appCode, String appName){
        super(versionNo, appCode, appName);
        this.commuService = CommuService.getInstance();//正式
        this.fileService = new FileService();
        kmsService = KmsService.getInstance();
    }
    
    public ICommuService getCommuService(){
        return this.commuService;
    }
    
    public IFileService getFileService(){
        return this.fileService;
    }
    
    public void setEsDeviceService(IEsDeviceService esDeviceService){
        this.esDeviceService = esDeviceService;
    }
    
    public IEsDeviceService getEsDeviceService(){
        return this.esDeviceService;
    }
    
    public IRwDeviceService[] getRwDeviceServices(){
        return new IRwDeviceService[0];
    }
    
    /**
     * 是否手工制卡
     * 
     * @param comNo
     * @return 
     */
    public boolean isRunHandMakeModeSameCom(String comNo){
    
        return false;
    }
    
    /**
     * @return the kmsService
     */
    public IKmsService getKmsService() {
        return kmsService;
    }
    
    /**
     * @param rightService the rightService to set
     */
    public void setRightService(IRightService rightService) {
        this.rightService = rightService;
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
        
        List<SysModuleVo> modules = rightService.getSysModules(user);
        logger.debug("系统模块：" + modules); 
        authorResult.setObjs(modules);
        authorResult.setResult(true);
        
        return authorResult;
    }
    
    /**
     * 加载配置文件
     * 
     * @param callParam
     * @return 
     */
    private CallResult loadConfigAndLogFile(CallParam callParam){
        
        CallResult callResult = null;
        try {
            //加载配置和日志文件
            callResult = fileService.loadConfigAndLogFile(callParam);
        } catch (FileException ex) {
            System.out.println("配置文件加载异常："+ex);
            return new CallResult("配置文件加载异常，请联系管理员!");
        }
        if (!callResult.isSuccess()) {
            System.out.println("配置文件加载失败："+callResult.getMsg());
            callResult.resetMsg("配置文件加载失败："+callResult.getMsg());
            return callResult;
        }
        //保存配置文件信息
        AppConstant.configs = (Hashtable) callResult.getObj();
        DateHelper.screenPrintForEx(AppConstant.configs);//打印
        
        return callResult;
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
    
    /**
     * 测试本地和远程FTP是否正常
     * 
     * @param callParam
     * @return 
     */
    private CallResult testFtpLogin(CallParam callParam){
    
        CallResult callResult = new CallResult();
        
        if(!testLocalFtpLogin()){
            callResult.setMsg("本地FTP连接异常，请联系统管理员！");
            return callResult;
        }
        if(!testRemoteEsFtpLogin()){
            callResult.setMsg("ES通讯FTP连接异常，请联系统管理员！");
            return callResult;
        }
    
        callResult.setResult(true);
        return callResult;
    }
    
    /**
     * 测试本地FTP连接
     * 
     * @return 
     */
    private boolean testLocalFtpLogin(){
    
        Hashtable uploads = (Hashtable) AppConstant.configs.get(ConfigConstant.UploadTag);
        String server = (String) uploads.get(ConfigConstant.LocalFtpServerIpTag);
        String userName = (String) uploads.get(ConfigConstant.LocalFtpUserNameTag);
        String password = (String) uploads.get(ConfigConstant.LocalFtpUserPasswordTag);
        int port = Integer.valueOf((String)uploads.get(ConfigConstant.LocalFtpServerPortTag));
        String path = (String) uploads.get(ConfigConstant.UploadOrderPathTag);
        path = path.replace(AppConstant.SLASH_WIN_SIGN, AppConstant.SLASH_LIX_SIGN);
        String[] parthArr = path.split(AppConstant.SLASH_LIX_SIGN + AppConstant.ADD_SIGN);
        path = parthArr[parthArr.length-1];
        
        return FtpMonitor.testFtpPathLogin(server,port, userName, password, path);
    }
    
    /**
     * 测试ES通讯FTP连接
     * 
     * @return 
     */
    private boolean testRemoteEsFtpLogin(){
    
        Hashtable downloads = (Hashtable) AppConstant.configs.get(ConfigConstant.DownloadTag);
        String server = (String) downloads.get(ConfigConstant.FtpServerIpTag);
        String userName = (String) downloads.get(ConfigConstant.FtpUserNameTag);
        String password = (String) downloads.get(ConfigConstant.FtpUserPasswordTag);
                
        return FtpMonitor.testFtpUserLogin(server, userName, password);
    }
    
    /**
     * 下载审计和错误文件
     * 
     * @param callParam
     * @return 
     */
    private CallResult downAuditAndErrorFile(CallParam callParam){
        
        CallResult callResult = null;
        try {
            //下载审计和错误文件
            callResult = commuService.downAuditAndErrorFile(callParam);
            if(callResult.isSuccess()){
                AppConstant.IS_CHECK = true;
            }
        } catch (CommuException ex) {
            logger.error("下载审计和错误文件异常:"+ex);
            return new CallResult("下载审计和错误文件异常，请联系管理员!");
        }catch(Exception ex){
            logger.error("记录审计和错误文件异常:"+ex);
            return new CallResult("记录审计和错误文件异常，请联系管理员!");
        }
        if (!callResult.isSuccess()) {
            logger.error("下载或记录审计和错误文件失败:"+callResult.getMsg());
            callResult.resetMsg("下载或记录审计和错误文件失败:"+callResult.getMsg());
            return callResult;
        } else {
            AppConstant.auditAndErrorFileVos = callResult.getObjs();
            logger.info("下载或记录审计和错误文件成功!");
            DateHelper.screenPrintForEx(AppConstant.auditAndErrorFileVos);//打印
            
        }
        
        return callResult;
    }
    
    /**
     * 查询黑名单文件
     * 
     * @param callParam
     * @return 
     */
    private CallResult queryBlacklist(CallParam callParam){
        
        CallResult callResult = null;
        try {
            callResult = commuService.queryBlacklist(callParam);
        } catch (CommuException ex) {
            logger.error("下载黑名单文件异常:"+ex);
            return new CallResult("下载黑名单文件异常，请联系管理员!");
        }
        if (!callResult.isSuccess()) {
            logger.error("下载黑名单文件失败："+callResult.getMsg());
            callResult.resetMsg("下载黑名单文件失败："+callResult.getMsg());
            return callResult;
        } else {
            logger.info("下载黑名单文件成功.");
            AppConstant.blacklistFileVos = callResult.getObjs();
            DateHelper.screenPrintForEx(AppConstant.blacklistFileVos);//打印
        }
        return callResult;
    }
    
    /**
     * 查询SAM卡文件
     * 
     * @param callParam
     * @return 
     */
    private CallResult querySamlist(CallParam callParam){
        
        CallResult callResult = null;
        try {
            //下载SAM卡列表
            callResult = commuService.querySamlist(callParam);
        } catch (CommuException ex) {
            logger.error("下载SAM卡文件异常:"+ex);
            return new CallResult("下载SAM卡文件异常，请联系管理员!");
        }
        if (!callResult.isSuccess()) {
            logger.error("下载SAM卡文件失败："+callResult.getMsg());
            callResult.resetMsg("下载SAM卡文件失败："+callResult.getMsg());
            return callResult;
        } else {
            logger.info("下载SAM卡文件成功!");
            AppConstant.samCardFileVos = callResult.getObjs();
            DateHelper.screenPrintForEx(AppConstant.samCardFileVos);//打印
        }
        return callResult;
    }
    
    /**
     * 查询城市参数
     * 
     * @param callParam
     * @return 
     */
    private CallResult queryParams(CallParam callParam) {
        
        CallResult callResult = null;
        try {
            //下载城市参数
            callResult = commuService.queryParams(callParam);
        } catch (CommuException ex) {
            logger.error("查询城市参数异常:"+ex);
            return new CallResult("查询城市参数异常，请联系管理员!");
        }
        if (!callResult.isSuccess()) {
            logger.error("查询城市参数失败:"+callResult.getMsg());
            callResult.resetMsg("查询城市参数失败:"+callResult.getMsg());
            return callResult;
        } else {
            logger.info("查询城市参数成功!");
            AppConstant.cityParamVo = (CityParamVo) callResult.getObj();
            DateHelper.screenPrintForEx(AppConstant.cityParamVo);//打印
        }
        return callResult;
    }
    
    /**
     * 下载票种参数
     * 
     * @param callParam
     * @return 
     */
    private CallResult queryCardTypes(CallParam callParam){
        
        CallResult callResult = null;
        try {
            //下载票种参数
            callResult = commuService.queryCardTypes(callParam);
        } catch (CommuException ex) {
            logger.error("查询票种参数异常:"+ex);
            return new CallResult("查询票种参数异常，请联系管理员!");
        }
        if (!callResult.isSuccess()) {
            logger.error("查询票种参数失败："+callResult.getMsg());
            callResult.resetMsg("查询票种参数失败："+callResult.getMsg());
            return callResult;
        } else {
            logger.info("查询票种参数成功!");
            AppConstant.ticketTypeVos = callResult.getObjs();
            DateHelper.screenPrintForEx(AppConstant.ticketTypeVos);//打印
        }
        return callResult;
    }
    
    /**
     * 
     * @param callParam
     * @return 
     */
    private CallResult queryTicketPrice(CallParam callParam) {
        CallResult callResult = null;
        try {
            //下载票价参数
            callResult = commuService.queryTicketPrice(callParam);
        } catch (CommuException ex) {
            logger.error("查询票价参数异常:"+ex);
            return new CallResult("查询票价参数异常，请联系管理员!");
        }
        if (!callResult.isSuccess()) {
            logger.error("查询票价参数失败："+callResult.getMsg());
            callResult.resetMsg("查询票价参数失败："+callResult.getMsg());
            return callResult;
        } else {
            logger.info("查询票价参数成功!");
            AppConstant.ticketPriceVos = callResult.getObjs();
            DateHelper.screenPrintForEx(AppConstant.ticketPriceVos);//打印
        }

        return callResult;
    }
    
    /**
     * 登录前，初始化
     * 
     * @param callParam
     * @return 
     */
    @Override
    public CallResult loadEventCallBack(CallParam callParam){
        
        CallResult callResult = null;
       
        //加载配置和日志文件
        callResult = loadConfigAndLogFile(callParam);
        if (!callResult.isSuccess()) {
            return callResult;
        }
         
        //测试FTP连接是否正常
        callResult = testFtpLogin(callParam);
        if (!callResult.isSuccess()) {
           return callResult;
        }
        
        //启动文件下载线程
        callResult = startFileDownServer(callParam);
        if(!callResult.isSuccess()){
            return callResult;
        }

        //打开SOCKET通讯端口
        callResult = openCommu(callParam);
        if (!callResult.isSuccess()) {
            return callResult;
        }
          
        //下载审计和错误文件
        callResult = downAuditAndErrorFile(callParam);
         if (!callResult.isSuccess()) {
            return callResult;
        }
            
        //下载黑名单
        callResult = queryBlacklist(callParam);
        if (!callResult.isSuccess()) {
            return callResult;
        }
           
        //下载SAM卡列表
        callResult = querySamlist(callParam);
        if (!callResult.isSuccess()) {
            return callResult;
        }
            
        //下载城市参数
        callResult = queryParams(callParam);
        if (!callResult.isSuccess()) {
            return callResult;
        }
            
        //下载票种参数
        callResult = queryCardTypes(callParam);
        if (!callResult.isSuccess()) {
            return callResult;
        }
            
        //下载票价参数
        callResult = queryTicketPrice(callParam);
        if (!callResult.isSuccess()) {
            return callResult;
        }
            
        //启动订单通知线程,发送滞留文件消息
        callResult = startOrderNoticeMonitor(callParam);
        if (!callResult.isSuccess()) {
            return callResult;
        }
        
        //加载数据库连接池   limj
        callResult = loadDataConnectionPool();
        if(!callResult.isSuccess()){
            return callResult;
        }
        //保存数据库连接池
        AppConstant.dbcpHelper = (DbcpHelper)callResult.getObj();
        
        callResult.setResult(true);
        
        return callResult;
    }
    
    /**
     * 启动订单通知线程,发送滞留文件消息
     * 
     * @param callParam
     * @return 
     */
    private CallResult startOrderNoticeMonitor(CallParam callParam){
        
        CallResult callResult = new CallResult();
        orderNoticeThread = new EsOrderNoticeThread(callParam);
        orderNoticeThread.start();
        
        logger.info("完成启动订单通知线程！");
        
        callResult.setResult(true);
        
        return callResult;
    }
    
    /**
     * 停止订单通知线程
     * 
     * @param callParam
     * @return 
     */
    private CallResult stopOrderNoticeMonitor(CallParam callParam) {
        
        CallResult callResult = new CallResult();
        
        synchronized(SynLockConstant.SYN_NOTICE_MSG_FILE_LOCK){
            //orderNoticeThread.interrupt();
            orderNoticeThread.stop();
            orderNoticeThread = null;
        }
        
        logger.info("完成停止订单通知线程！");
        
        callResult.setResult(true);
        
        return callResult;
    }
    
    /**
     * 启动文件下载服务
     * 
     */
    private CallResult startFileDownServer(CallParam callParam){
        
        CallResult callResult = new CallResult();
        
        fileDownServer = new ComFileDownThread();
        fileDownServer.start();
        logger.info("完成启动文件下载服务线程！");
        
        callResult.setResult(true);
        
        return callResult;
    }
    
    /**
     * 停止文件下载服务
     * 
     * @param callParam 
     */
    private CallResult stopFileDownServer(CallParam callParam) {
        
        CallResult callResult = new CallResult();
        
        //fileDownServer.interrupt();
        fileDownServer.stop();
        fileDownServer = null;
        
        logger.info("完成停止文件下载服务线程！");
        
        callResult.setResult(true);
        
        return callResult;
        
    }

    /**
     * 登录后，初始化
     * 
     * @param callParam
     * @return 
     */
    @Override
    public CallResult initEventCallBack(CallParam callParam){

        CallResult callResult = new CallResult();

        CallResult callResultRw = null;
        CallResult callResultEs = null;
        CallResult callResultKm = null;
       try{
            //加载数据库参数limj
            loadParamersValue();
        }catch(Exception ex){
            logger.error("数据库参数加载异常:"+ex);
            return new CallResult("数据库参数加载异常，请联系管理员！");
        }
       try {
            //打开读头端口
          
            callResultRw = openRwPort(callParam);
            if(!callResultRw.isSuccess()){
                throw new RwJniException();
            }
        } catch (RwJniException ex) {
            logger.error("打开读写器端口异常:"+ex);
            return new CallResult("打开读写器端口异常，请联系管理员！");
        }
       try {
            //打开ES端口
            callResultEs = openEsDevicePort(callParam);
//            if(!callResultEs.isSuccess()){
//                throw new EsJniException();
//            }
        } catch (EsJniException ex) {
            logger.error("打开ES设备异常:"+ex);
            return new CallResult("打开ES设备异常，请联系管理员！");
        } 
      
        try {
            //打开加密机端口
            callResultKm = openKmDevicePort(callParam);
            if(!callResultRw.isSuccess()){
                throw new KmJniException();
            }else {
                AppConstant.KMS_STATUS = true;
            }
        } catch (KmJniException ex) {
            logger.error("加密钥授权异常:"+ex);
            return new CallResult("加密钥授权异常，请联系管理员！");
        }
        
    
        callResult.setResult(true);
        
        return callResult;
    }

    /**
     * 初始化后，完成
     * @param callParam
     * @return 
     */
    @Override
    public CallResult finishEventCallBack(CallParam callParam) {
        
        CallResult callResult = new CallResult();
       
        
        //启动时钟线程
        startStatusBarSystemClock();
        
        //设置状态栏信息
        String[] vars = new String[]{AppConstant.STATUS_BAR_KMS_STATUS, AppConstant.STATUS_BAR_COMMU_STATUS, 
            AppConstant.STATUS_BAR_PRINTER_PORT};
        boolean[] statuses = new boolean[]{AppConstant.KMS_STATUS, AppConstant.COMMU_STATUS, AppConstant.PRINTER_PORT};
        setSBarStatus(vars, statuses);
              
        //启动ES通讯检测线程
        startEsCommuMonitor(null);
        
        callResult.setResult(true);
        
        return callResult;
    }
    
    /**
     * 启动系统时钟
     * 
     * @param callParam
     * @return 
     */
    private void startStatusBarSystemClock(){
    
        //启动系统时间线程
        JLabel currentTime = BaseConstant.publicPanel.getStatusOpLinkComp(AppConstant.STATUS_BAR_CURRENT_TIME);
        systemClock = new SystemClock(currentTime);
        systemClock.start();
        
        logger.info("完成启动系统时钟线程.");
    }
    
    /**
     * 关闭系统时钟
     * 
     */
    private void stopStatusBarSystemClock(){
        
        systemClock.stop();
        systemClock = null;
        
        logger.info("完成停止系统时钟线程.");
    }
    
    /**
     * 启动ES通讯检测线程
     * 
     * @return 
     */
    private CallResult startEsCommuMonitor(CallParam callParam){
    
        CallResult callResult = new CallResult();
        
        commuMonitorThread = new EsCommuMonitorThread(commuService);
        commuMonitorThread.start();

        logger.info("完成启动通讯监控线程.");
        
        callResult.setResult(true);
        
        return callResult;
    }
    
    /**
     * 停止ES通讯检测线程
     * 
     */
    private CallResult stopEsCommuMonitor(CallParam callParam){
        
        CallResult callResult = new CallResult();
        
        //commuMonitorThread.interrupt();
        commuMonitorThread.stop();
        commuMonitorThread = null;
        
        logger.info("完成停止系统时钟线程.");
        
        callResult.setResult(true);
        
        return callResult;
    }
    
    /**
     * 打开ES设备端口
     * 
     * @param callParam
     * @return
     * @throws EsJniException 
     */
    protected CallResult openEsDevicePort(CallParam callParam)throws EsJniException{
        
        CallResult callResult = new CallResult();
        
        CallResult callResultEs = null;
        CallResult callResultPt = null;
        
        callResultEs = openEsPort(callParam);
        
        callResultPt = openPtPort(callParam);
       
        callResult.setMsg(callResultEs.getMsg());
        callResult.setMsg(callResultPt.getMsg());
        
        callResult.setResult(callResultEs.isSuccess()&&callResultPt.isSuccess());
        
        return callResult;
    }
    
    private CallResult openPtPort(CallParam callParam) throws EsJniException{
        
        Hashtable esDevices = (Hashtable) AppConstant.configs.get(ConfigConstant.EsDeviceTag);
        EsPortParam portParam = new EsPortParam();
        short port = StringUtil.getShort(esDevices.get(ConfigConstant.EsDevicePtPortTag).toString());
        portParam.setPort(port);
        short comRate = StringUtil.getShort(esDevices.get(ConfigConstant.EsDevicePtComRateTag).toString());
        portParam.setComRate(comRate);
        //打开打印机端口
        CallResult callResultPt = esDeviceService.openPtPort(portParam);
        if(callResultPt.isSuccess()){
            logger.info("打开ES打印机端口成功.");
            callResultPt.setMsg("打开ES打印机端口成功.");
            AppConstant.PRINTER_PORT = true;
            CallResult callResultInit = esDeviceService.printerInit();//初始化端口
            if(!callResultInit.isSuccess()){
                logger.info("初始化ES打印机失败:"+callResultInit.getMsg());
                callResultPt.setMsg("初始化ES打印机失败:"+callResultInit.getMsg());
            }
        }else{
            logger.error("打开ES打印机端口失败："+callResultPt.getMsg());
            callResultPt.resetMsg("打开ES打印机端口失败："+callResultPt.getMsg());
        }
        
        return callResultPt;
    }
    
    /**
     * 子类实现此方，打开ES端口
     * 
     * @param callParam
     * @return
     * @throws EsJniException 
     */
    protected abstract CallResult openEsPort(CallParam callParam)throws EsJniException;
    
    /**
     * 子类实现此方，打开RW端口
     * 
     * @param callParam
     * @return
     * @throws EsJniException 
     */
    protected abstract CallResult openRwPort(CallParam callParam)throws RwJniException;
    
    
    /**
     * 打开加密机设备端口
     * 
     * @param callParam
     * @return
     * @throws EsJniException 
     */
    protected CallResult openKmDevicePort(CallParam callParam)throws KmJniException{
        
        CallResult callResult = new CallResult();
        
        String kmsIp = ConfigUtil.getConfigValue(ConfigConstant.KmsCommuTag, 
                ConfigConstant.KmsCommuKmIp);
        String kmsPort = ConfigUtil.getConfigValue(ConfigConstant.KmsCommuTag, 
                ConfigConstant.KmsCommuKmPort);
        String kmsPin = ConfigUtil.getConfigValue(ConfigConstant.KmsCommuTag, 
                ConfigConstant.KmsCommuKmPin);
        String keyVer = AppConstant.cityParamVo.getKeyVersion();
        
        KmsCfgParam kmsCfgParam = new KmsCfgParam();
        kmsCfgParam.setKmsIp(kmsIp);
        kmsCfgParam.setKmsPort(kmsPort);
        kmsCfgParam.setKmsPin(kmsPin);
        kmsCfgParam.setKeyVer(keyVer);
        callResult = getKmsService().author(kmsCfgParam);
        
        if(!callResult.isSuccess()){
            logger.error("加密钥授权失败："+callResult.getMsg());
            return callResult;
        }
        logger.info("加密钥授权成功!");
        callResult.setMsg("加密钥授权成功!");
        
        return callResult;
    }
    /**
     * 登录认证
     * 
     * @param param
     * @return
     * @throws AuthenException 
     */
    @Override
    protected AuthenResult authen(CallParam param) throws AuthenException {

        AuthenResult authenResult = null;
        LoginParam loginParam = (LoginParam)param;
        try {
            authenResult = (AuthenResult) commuService.loginOperator(loginParam);

        } catch (CommuException ex) {
            logger.error("登录认证异常:"+ex);
            return new AuthenResult("登录认证异常，请联系管理员！");
        }   
        //登录成功，更改设备状态
        if(authenResult.isSuccess()){
            logger.info("登录认证成功!");
            authenResult.setMsg("登录认证成功!");
            
            updateDeviceStatusLogin(loginParam);
        }

        return authenResult;
    }
    
    /**
     * 更新设备状态-成功登录
     * 
     * @param param 
     */
    private void updateDeviceStatusLogin(LoginParam param){
        
        CallResult callResult = null;
        try {
            param.resetParam(param.getUserNo());
            param.setParam(AppConstant.ES_DEVICE_STATUS_LOGIN);
            param.setParam(Converter.getEsDeviceStatusDes(AppConstant.ES_DEVICE_STATUS_LOGIN));
            callResult = commuService.updateDeviceStatus(param);
            if (callResult.isSuccess()) {
                logger.info("更改设备状态（登录）成功!");
            }
        } catch (CommuException ex) {
            logger.error("更改设备状态（登录）异常："+ex);
        }
    }
    
    /**
     * 更新设备状态-成功退出
     *
     * @param param
     */
    private CallResult updateDeviceStatusLogout(CallParam param)throws CommuException{
        
        CallResult callResult = null;
        
        param.resetParam(AppConstant.user.getAccount());
        param.setParam(AppConstant.ES_DEVICE_STATUS_LOGOUT);
        param.setParam(Converter.getEsDeviceStatusDes(AppConstant.ES_DEVICE_STATUS_LOGOUT));
        callResult = commuService.updateDeviceStatus(param);
        if (callResult.isSuccess()) {
            logger.info("更改设备状态（退出）成功!");
        }
        
        return callResult;
    }

    /**
     * 退出回调
     * 
     * @param callParam
     * @return 
     */
    @Override
    public CallResult exitEventCallBack(CallParam callParam) {
        
        CallResult callResult = null;
        
        try {
            
            callResult = closeRwPort(callParam);
            if(callResult.isSuccess()){
                logger.info("关闭读头端口成功！");
            }
        } catch (Exception ex) {
            logger.error("关闭读头端口异常："+ex);
        }
        try {
            callResult = closeEsDevicePort(callParam);
            if(callResult.isSuccess()){
                logger.info("关闭ES端口成功！");
            }
        } catch (Exception ex) {
            logger.error("关闭ES端口异常："+ex);
        }
        try {
            callResult = updateDeviceStatusLogout(callParam);
            if(callResult.isSuccess()){
                logger.info("更改设备状态（退出）成功！");
            }
        } catch (Exception ex) {
            logger.error("更改设备状态（退出）异常："+ex);
        }
        try {
            synchronized(SynLockConstant.SYN_WIN_EXIT_LOCK){
                callResult = commuService.closeCommu(callParam);
                this.sysAppVo.setAppStatus(AppConstant.SYS_APP_CUR_STATUS_EXIT);
            }
            if(callResult.isSuccess()){
                logger.info("关闭通讯成功！");
            }
        } catch (Exception ex) {
            logger.error("关闭通讯异常："+ex);
        }
        //关闭文件下载服务
        try{
            stopFileDownServer(null);
        }catch(Exception ex){
            logger.error("关闭文件下载线程异常："+ex);
        }
        
        //关闭订单通知线程
        try{
            stopOrderNoticeMonitor(callParam);
        }catch(Exception ex){
            logger.error("关闭订单通知线程异常："+ex);
        }
        //关闭ES通讯线程
        try{
            stopEsCommuMonitor(callParam);
        }catch(Exception ex){
            logger.error("关闭ES通讯监控线程异常："+ex);
        }
        //关闭系统时钟线程
        try{
            stopStatusBarSystemClock();
        }catch(Exception ex){
            logger.error("关闭系统时钟线程异常："+ex);
        }
        
        callResult.setResult(true);
        return callResult;
    }
    
     /**
     * 关闭ES设备端口
     * 
     * @param callParam
     * @return
     * @throws EsJniException 
     */
    protected CallResult closeEsDevicePort(CallParam callParam)throws EsJniException{
        
        CallResult callResult = null;
        
        callResult = closeEsPort(callParam);
        
        callResult = esDeviceService.closePtPort(callParam);
        if(callResult.isSuccess()){
            logger.info("关闭Es打印机端口成功！");
            callResult.setMsg("关闭Es打印机端口成功！");
        }
        
        return callResult;
    } 
    
    /**
     * 子类实现此方，关闭ES端口
     * 
     * @param callParam
     * @return
     * @throws EsJniException 
     */
    protected abstract CallResult closeEsPort(CallParam callParam)throws EsJniException;
    
    /**
     * 子类实现此方，关闭RW端口
     * 
     * @param callParam
     * @return
     * @throws EsJniException 
     */
    protected abstract CallResult closeRwPort(CallParam callParam)throws RwJniException;
    
    /**
     加载运营管理系统连接池   limj
     *
     */
    protected CallResult loadDataConnectionPool(){
        CallResult callResult = new CallResult();
        DbcpHelper dbcpHelper = null;
        String driverName = ConfigUtil.getConfigValue(ConfigConstant.DataConncetionPoolTag,
                ConfigConstant.DriverTag);
        String url = ConfigUtil.getConfigValue(ConfigConstant.DataConncetionPoolTag, 
                ConfigConstant.URLTag);
        String userName = ConfigUtil.getConfigValue(ConfigConstant.DataConncetionPoolTag, 
                ConfigConstant.UserNameTag);
        String password = ConfigUtil.getConfigValue(ConfigConstant.DataConncetionPoolTag,
                ConfigConstant.PassWordTag);
        String maxActive = ConfigUtil.getConfigValue(ConfigConstant.DataConncetionPoolTag,
                ConfigConstant.MaxActiveTag);
        String maxIdle = ConfigUtil.getConfigValue(ConfigConstant.DataConncetionPoolTag,
                ConfigConstant.MaxIdleTag);
        String maxWait = ConfigUtil.getConfigValue(ConfigConstant.DataConncetionPoolTag,
                ConfigConstant.MaxWaitTag);
        try{
            dbcpHelper = new DbcpHelper(driverName,url,userName,password,
                    StringUtil.getInt(maxActive),StringUtil.getInt(maxIdle),StringUtil.getInt(maxWait));
            callResult.setObj(dbcpHelper);
            callResult.setResult(true);
            logger.info("加载数据库连接池成功!");
        }catch(ClassNotFoundException ex){
            logger.error(ex);
            callResult.setMsg(ex.getMessage());
        }        
        return callResult;
    }
    
    //加载站点，线路，限制模式和票卡主类型
    private void loadParamersValue(){
        //加载参数表数据
        PubUtil pubUtil = new PubUtil();
        Vector v = pubUtil.getTablePubFlag(AppConstant.TABLE_ET_PARAMER);
        for(int i=0;i<v.size();i++){
            PubFlagVo pvo = new PubFlagVo();
            pvo = (PubFlagVo)v.get(i);
            //发行状态
            if(pvo.getType().equals(AppConstant.STR_ISSUE_STATUS)){
                AppConstant.ISSUE_STATUS.put(pvo.getCode(), pvo.getCodeText());
            }
            //票卡状态
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
            // 是否地铁通卡 certificate_ismetro
            if(pvo.getType().equals(AppConstant.STR_CERTIFICATE_ISMETRO)){
                AppConstant.CERTIFICATE_ISMETRO.put(pvo.getCode(), pvo.getCodeText());
            }
           
        }
        //票卡类型 cTicketType[4];	（取主类型，前两位）
        AppConstant.TICKET_TYPE = PubUtil.getTableColumn(AppConstant.TABLE_OP_CARD_MAIN_TYPE,"CARD_MAIN_ID||CARD_SUB_ID" ,"CARD_SUB_NAME" ,"record_flag" ,"0" );
        //线路 cLine[2]
        AppConstant.C_LINE = PubUtil.getTableColumn(AppConstant.TABLE_C_LINE,"LINE_ID" ,"LINE_NAME" ,"record_flag" ,"3" );
        //站点 cStationNo[2]
        AppConstant.C_STATION_NO= PubUtil.getTableColumn(AppConstant.TABLE_C_STATION,"STATION_ID" ,"CHINESE_NAME" ,"record_flag" ,"3" );
        //限制模式 cLimitMode[3]
        AppConstant.C_LIMIT_MODE = PubUtil.getTableColumn(AppConstant.TABLE_OP_PUB_FLAG,"CODE" ,"CODE_TEXT" ,"TYPE" ,"26");
        logger.info("加载参数表数据完成!");
        }
    

}
