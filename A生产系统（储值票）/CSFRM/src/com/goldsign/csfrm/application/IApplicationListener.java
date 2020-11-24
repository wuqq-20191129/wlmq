/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.csfrm.application;

import com.goldsign.csfrm.exception.AuthenException;
import com.goldsign.csfrm.exception.AuthorException;
import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;

/**
 *
 * @author lenovo
 * 
 * 应用标准接口类
 * 新建应用时，都应实现此类
 * 接口中，都是事件回调方法
 *
 */
public interface IApplicationListener {
    
    /**
     * 系统加载时，回调方法
     * 
     * @param callParam
     * @return 
     */
    CallResult loadEventCallBack(CallParam callParam);
    
    /**
     * 系统加载时，回调方法
     * 
     * @param callParam
     * @return 
     */
    CallResult loadEventAfterLoginCallBack(CallParam callParam);
    
    /**
     * 系统初始化时，回调方法
     * 
     * @param callParam
     * @return 
     */
    CallResult initEventCallBack(CallParam callParam);
    
    /**
     * 系统登录
     * 
     * @param callParam
     * @return
     * @throws AuthenException
     * @throws AuthorException 
     */
    CallResult login(CallParam callParam) 
            throws AuthenException, AuthorException;
    
    /**
     * 登录取消，回调方法,子类重载实现相应的回收工作
     * 
     * @param callParam
     * @return 
     */
    CallResult logexitEventCallBack(CallParam callParam);
    
    /**
     * 系统完成后，回调方法
     * 
     * @param callParam
     * @return 
     */
    CallResult finishEventCallBack(CallParam callParam);
    
    /**
     * 菜单点击后，回调方法
     * 
     * @param callParam
     * @return 
     */
    CallResult menuClickEventCallBack(CallParam callParam);
    
    /**
     * 系统退出时，回调方法
     * 
     * @param callParam
     * @return 
     */
    CallResult exitEventCallBack(CallParam callParam);
    
    /**
     * 生成状态栏时，回调方法
     * 
     * @param callParam
     * @return 
     */
    CallResult genStatusBarEventCallBack(CallParam callParam);
}
