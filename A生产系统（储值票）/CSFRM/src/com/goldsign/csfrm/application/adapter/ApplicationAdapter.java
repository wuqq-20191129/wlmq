/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.csfrm.application.adapter;

import com.goldsign.csfrm.application.IApplicationListener;
import com.goldsign.csfrm.env.BaseConstant;
import com.goldsign.csfrm.exception.AuthenException;
import com.goldsign.csfrm.exception.AuthorException;
import com.goldsign.csfrm.exception.InitException;
import com.goldsign.csfrm.exception.LoadException;
import com.goldsign.csfrm.ui.dialog.LoginDialog;
import com.goldsign.csfrm.ui.frame.LoginFrame;
import com.goldsign.csfrm.ui.frame.MainFrame;
import com.goldsign.csfrm.util.MessageShowUtil;
import com.goldsign.csfrm.util.UIUtil;
import com.goldsign.csfrm.vo.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author lenovo
 * 
 * 应用适配器
 * 类里大部分为事件回调方法，
 * 新建应用时，可以继承此类，并重载相应方法。
 */
public abstract class ApplicationAdapter implements IApplicationListener{
    
    private static final Logger logger = Logger.getLogger(ApplicationAdapter.class.getName());
    
    //应用
    public SysAppVo sysAppVo = BaseConstant.sysAppVo;
    
    public ApplicationAdapter(){
        //默认版本号
        this(BaseConstant.VERSION_DEFAULT, BaseConstant.APP_CODE_DEFAULT, BaseConstant.APP_NAME_DEFAULT);
    }
    
    /**
     * 
     * 版本号
     * 
     * @param versionNo 
     */
    public ApplicationAdapter(String versionNo){
        
        this(versionNo, BaseConstant.APP_CODE_DEFAULT, BaseConstant.APP_NAME_DEFAULT);
    }
    
    /**
     * 
     * @param versionNo
     * @param appCode
     * @param appName 
     */
    public ApplicationAdapter(String versionNo, String appCode, String appName){
        
        sysAppVo.setVersionNo(versionNo);
        sysAppVo.setAppCode(appCode);
        sysAppVo.setAppName(appName);
    }
    
    /**
     * 系统加载时，回调此方法，子类可重载此方法，实现登录前的加载工作
     * @param callParam
     * @return 
     */
    @Override
    public CallResult loadEventCallBack(CallParam callParam){
        
        CallResult callResult = new CallResult();
        callResult.setResult(true);
        
        logger.info("回调方法--loadEventCallBack");
        
        return callResult;
    }
    
    /**
     * 系统加载时，回调此方法，子类可重载此方法，实现登录后的加载工作
     * @param callParam
     * @return 
     */
    @Override
    public CallResult loadEventAfterLoginCallBack(CallParam callParam){
        
        CallResult callResult = new CallResult();
        callResult.setResult(true);
        
        logger.info("回调方法--loadEventAfterLoginCallBack");
        
        return callResult;
    }
    
    /**
     * 系统加载，回调loadEventCallBack方法
     * @param callParam
     * @return
     * @throws LoadException 
     */
    protected CallResult load(CallParam callParam) {
        
        CallResult callResult = null;
        
        //回调loadEventCallBack方法
        callResult = this.loadEventCallBack(callParam);
        
        //启动登录窗口,回调loadEventCallBack方法的错误在登录窗口给予提示
        this.showLoginDialog(callResult);
        
        //moqf 20140327 回调loadEventAfterLoginCallBack方法
        callResult = this.loadEventAfterLoginCallBack(callParam);

        //返回结果为真，可以进行下一步操作
        callResult.setResult(true);
        
        return callResult;
        
    }
    
    /**
     * 显示登录窗口
     * 
     * @param callResult 
     */
    protected void showLoginDialog(CallResult callResult){
        
        LoginFrame loginFrame =  new LoginFrame();
        LoginDialog loginDialog = new LoginDialog(loginFrame, true);//模态登录窗口
        
        UIUtil.makeContainerInScreenMiddle(loginDialog);    //居中
        loginDialog.setAlwaysOnTop(true);                   //顶层
        
        //显示错误提示信息
        if(!callResult.isSuccess()){
            loginDialog.setPromptMsg(callResult.getMsg());
        }
        
        loginDialog.setAppVersionNo(sysAppVo.getVersionNo());//设置应用版本号
        loginDialog.setLoginEnable(callResult.isSuccess());//设备登录按钮能动性
        loginDialog.setVisible(true);   //显示
       
        loginFrame.dispose();           //销毁
        
    }
    
    /**
     * 系统初始化时，回调此方法，子类可重载此方法，实现登录后的初始化工作
     * @param callParam
     * @return 
     */
    @Override
    public CallResult initEventCallBack(CallParam callParam){
        
        CallResult callResult = new CallResult();
        callResult.setResult(true);
        
        logger.info("回调方法--initEventCallBack");
        
        return callResult;
    }
    
    /**
     * 系统菜单被单击时，回调此方法，子类可重载此方法，实现截取菜单事件
     * @param callParam
     * @return 
     */
    @Override
    public CallResult menuClickEventCallBack(CallParam callParam){
        
        CallResult callResult = new CallResult();
        callResult.setResult(true);
        
        logger.info("回调方法--menuChangeEventCallBack");
        
        return callResult;
    }
    
    /**
     * 系统完全加载后，回调此方法，子类可重载此方法，实现系统展现后的处理工作
     * @param callParam
     * @return 
     */
    @Override
    public CallResult finishEventCallBack(CallParam callParam){
        
        CallResult callResult = new CallResult();
        callResult.setResult(true);
        
        logger.info("回调方法--finishEventCallBack");
        
        return callResult;
    }
    
    /**
     * 系统退出时，回调此方法，子类可重载此方法，实现退出时的资源回收工作
     * @param callParam
     * @return 
     */
    @Override
    public CallResult exitEventCallBack(CallParam callParam){
        
        CallResult callResult = new CallResult();
        callResult.setResult(true);
        
        logger.info("回调方法--exitEventCallBack");
        
        return callResult;
    }
    
    /**
     * 系统登录，包括验证和授权
     * @param callParam
     * @return
     * @throws AuthenException
     * @throws AuthorException 
     */
    @Override
    public CallResult login(CallParam callParam) 
            throws AuthenException, AuthorException {
        
        //验证
        AuthenResult authenResult = this.authen(callParam);
  
        if(!authenResult.isSuccess()){
            return authenResult;
        }
        
        SysUserVo user = authenResult.getObj();
        BaseConstant.user = user;   //赋值用户
        logger.info("登录验证成功...用户："+user.getUsername());
        
        //授权
        AuthorResult authorResult = this.author(user);
        
        if(!authorResult.isSuccess()){
            return  authorResult;
        }
        
        List<SysModuleVo> moduleVos = authorResult.getObjs();
        logger.info("登录授权成功...size="+moduleVos.size());
        for(SysModuleVo moduleVo: moduleVos){
            BaseConstant.availableMenus.add(moduleVo);   //赋值权限
            logger.info("模块："+moduleVo.getModuleId()+":"+moduleVo.getName());
        }
        
        return authorResult;
    }
    
    /**
     * 登录取消，回调方法,子类重载实现相应的回收工作
     * 
     * @param callParam
     * @return 
     */
    @Override
    public CallResult logexitEventCallBack(CallParam callParam) {
        
        CallResult callResult = new CallResult();
        callResult.setResult(true);
        
        logger.info("回调方法--logexitEventCallBack");
        
        return callResult;
    }
    
    /**
     * 登录前验证，子类实现此方法,实现自定义的验证操作
     * @param param
     * @return
     * @throws AuthenException 
     */
    protected abstract AuthenResult authen(CallParam param)
            throws AuthenException;
    
    
    /**
     * 登录后授权，子类实现此方法,实现自定义的授权操作
     * @param user
     * @return
     * @throws AuthorException 
     */
    protected abstract AuthorResult author(SysUserVo user)
            throws AuthorException;

    /**
     * 启动，系统的入口
     * @throws LoadException
     * @throws InitException
     * @throws AuthenException
     * @throws AuthorException 
     */
    public void run() throws LoadException, InitException, AuthenException, AuthorException, 
            ClassNotFoundException, InstantiationException, IllegalAccessException, 
            UnsupportedLookAndFeelException{
        
        UIManager.setLookAndFeel(BaseConstant.FRM_WINDOW_STYLE);    //系统皮肤
        
        BaseConstant.application = this;    //当前应用
        
        CallParam callParam = new CallParam();
        callParam.setParam(this);
        
        CallResult callResult = this.load(callParam);
        
        if(!callResult.isSuccess()){
            logger.log(Level.SEVERE, "加载失败："+callResult.getMsg());
            return;
        }
        
        callResult = this.init(callParam);
        
        if(!callResult.isSuccess()){
            logger.log(Level.SEVERE, "初始化失败："+callResult.getMsg());
            return;
        }
    }

    /**
     * 系统初始化
     * @param param
     * @return
     * @throws InitException 
     */
    protected CallResult init(CallParam param) {
        
        CallResult callResult = this.initEventCallBack(param);

        //显示主页面
        this.showMainFrame(callResult);
        
        if(callResult.isSuccess()){
            callResult = this.finishEventCallBack(param);//完成回调方法
            if(!callResult.isSuccess()){
                MessageShowUtil.warnOpMsg(callResult.getMsg());
                return callResult;
            }
            
            BaseConstant.publicPanel.setOpResult("欢迎光临本系统！");
        }

        return callResult;
    }
    
    /**
     * 展示系统首页面
     * @throws Exception 
     */
    protected void showMainFrame(CallResult callResult){
        
        MainFrame mainFrame = new MainFrame();
        mainFrame.initFrame();
        if(!callResult.isSuccess()){
            MessageShowUtil.warnOpMsg(callResult.getMsg());
        }

    }

    /**
     * 描绘状态栏时，回调此方法，子类可重载此方法，实现自定义的状态栏
     * @param param
     * @return 
     */
    @Override
    public CallResult genStatusBarEventCallBack(CallParam param){
        
        CallResult callResult = new CallResult();
        
        logger.info("回调方法--genStatusBarEventCallBack");
        
        List<SBarStatusVo> sBarStatusVos = new ArrayList<SBarStatusVo>();
        //sBarStatusVos.add(new SBarStatusVo("  通讯状态:", "statusInfo",1f));
        /*sBarStatusVos.add(new SBarStatusVo("  读卡器2", "reader2",0.2f));
        sBarStatusVos.add(new SBarStatusVo("  读卡器3", "reader3",0.2f));
        sBarStatusVos.add(new SBarStatusVo("  读卡器4", "reader4",0.2f));
        sBarStatusVos.add(new SBarStatusVo("  读卡器5", "reader5",0.1f));
        sBarStatusVos.add(new SBarStatusVo("  读卡器5", "reader5",0.1f));*/
        
        callResult.setObjs(sBarStatusVos);
        callResult.setResult(true);
        
        return callResult;
    }

    /**
     * 设置状态栏状态
     * 正常、警告
     * @param vars
     * @param statuses 
     */
    protected void setSBarStatus(String[] vars, boolean[] statuses) {
        int len = vars.length;
        for (int i = 0; i < len; i++) {
            if (statuses[i]) {
                BaseConstant.publicPanel.setOpLink(vars[i], BaseConstant.STATUS_BAR_STATUS_COLOR_ON);
            } else {
                BaseConstant.publicPanel.setOpLinkError(vars[i], BaseConstant.STATUS_BAR_STATUS_COLOR_OFF);
            }
        }
    }
}
