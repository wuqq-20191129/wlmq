/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.csfrm.application.impl;

import com.goldsign.csfrm.application.adapter.ApplicationAdapter;
import com.goldsign.csfrm.exception.AuthenException;
import com.goldsign.csfrm.exception.AuthorException;
import com.goldsign.csfrm.vo.*;
import java.util.ArrayList;

/**
 *
 * @author lenovo
 * 
 * 应用基类
 * 目的主要为了测试框架
 * 子类也可以直接继承此类
 */
public class BaseApplication extends ApplicationAdapter{

    /**
     * 版本号
     * 
     * @param versionNo 
     */
    public BaseApplication(String versionNo) {
        super(versionNo);
    }

    public BaseApplication() {
    }

    /**
     * 实现登录前验证方法
     * 
     * @param param
     * @return
     * @throws AuthenException 
     */
    @Override
    protected AuthenResult authen(CallParam param)
            throws AuthenException{

        AuthenResult authenResult = new AuthenResult();
        
        authenResult.setObj(new SysUserVo());
        
        authenResult.setResult(true);

        return authenResult;
    }
    
    /**
     * 实现登录后授权方法
     * 
     * @param user
     * @return
     * @throws AuthorException 
     */
    @Override
    protected AuthorResult author(SysUserVo user)
            throws AuthorException{
        
        AuthorResult authorResult = new AuthorResult();
        
        authorResult.setObjs(new ArrayList<SysModuleVo>());
        
        authorResult.setResult(true);
        
        return authorResult;
    }

}
