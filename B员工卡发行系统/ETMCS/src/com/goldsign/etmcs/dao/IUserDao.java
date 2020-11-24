/*
 * 文件名：IUserDao
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.etmcs.dao;

import com.goldsign.csfrm.vo.SysUserVo;


/*
 * 登录用户信息DAo
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-10-29
 */

public interface IUserDao {
    
    //取用户组
    void getUserGroup(SysUserVo sysUserVo);

}
