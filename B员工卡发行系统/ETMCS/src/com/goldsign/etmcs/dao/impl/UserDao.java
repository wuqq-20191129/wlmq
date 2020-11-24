/*
 * 文件名：UserDao
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.etmcs.dao.impl;

import com.goldsign.csfrm.vo.SysUserVo;
import com.goldsign.etmcs.dao.IUserDao;
import com.goldsign.etmcs.env.AppConstant;
import com.goldsign.etmcs.util.PubUtil;
import com.goldsign.lib.db.util.DbHelper;
import org.apache.log4j.Logger;


/*
 * 登录用户信息DAo实现类
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-10-29
 */

public class UserDao implements IUserDao{
    
    private static Logger logger = Logger.getLogger(UserDao.class.getName());

    @Override
    public void getUserGroup(SysUserVo sysUserVo) {
        boolean result = false;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            String sqlStr = "select * from W_ACC_ST.W_OP_SYS_GROUP_OPERATOR WHERE sys_operator_id = '" +sysUserVo.getAccount()+ "'";
            
            logger.info("sqlStr:" + sqlStr);
            
            result = dbHelper.getFirstDocument(sqlStr);
            while (result) {
                sysUserVo.setEmployeeLevel(dbHelper.getItemTrueValue("sys_group_id"));
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

    }

}
