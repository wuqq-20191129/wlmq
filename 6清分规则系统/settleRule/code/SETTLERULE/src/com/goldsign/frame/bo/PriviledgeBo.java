/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.frame.bo;

import com.goldsign.frame.dao.PriviledgeDao;
import com.goldsign.frame.dao.UserDao;
import com.goldsign.frame.vo.EditResult;
import com.goldsign.frame.vo.PriviledgeOperatorVo;


import java.util.Vector;



import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class PriviledgeBo {

    private static Logger logger = Logger.getLogger(PriviledgeBo.class.getName());

    public PriviledgeBo() {
    }

    public Vector getAllPriviledges() throws Exception {
        PriviledgeDao pmda = new PriviledgeDao();
        return pmda.getAllPriviledges();

    }

    public int modifyGroup(String groupID, String groupName) throws Exception {
        PriviledgeDao pmda = new PriviledgeDao();
        return pmda.modifyGroup(groupID, groupName);

    }

    public boolean isExistingForGroup(String groupID) throws Exception {
        PriviledgeDao pmda = new PriviledgeDao();
        return pmda.isExistingForGroup(groupID);
    }

    public int addGroup(String groupID, String groupName, HttpServletRequest request) throws Exception {
        PriviledgeDao pmda = new PriviledgeDao();
        return pmda.addGroup(groupID, groupName, request);
    }

    public int deleteGroups(Vector groupIDs) throws Exception {
        PriviledgeDao pmda = new PriviledgeDao();
        return pmda.deleteGroups(groupIDs);
    }

    public Vector getAllOperatorsForGroup(String groupID) throws Exception {
        PriviledgeDao pmda = new PriviledgeDao();
        return pmda.getAllOperatorsForGroup(groupID);

    }

    public boolean isExistingForOperator(String operatorID) throws Exception {
        PriviledgeDao pmda = new PriviledgeDao();
        return pmda.isExistingForOperator(operatorID);
    }

    public int addOperator(PriviledgeOperatorVo po) throws Exception {
        PriviledgeDao pmda = new PriviledgeDao();
        return pmda.addOperator(po);
    }

    public int modifyOperator(PriviledgeOperatorVo po) throws Exception {
        PriviledgeDao pmda = new PriviledgeDao();
        return pmda.modifyOperator(po);
    }

    public int restoreOperator(String operatorID) throws Exception {
        PriviledgeDao pmda = new PriviledgeDao();
        return pmda.restoreOperator(operatorID);
    }

    public int deleteOperator(Vector operatorIDs) throws Exception {
        PriviledgeDao pmda = new PriviledgeDao();
        return pmda.deleteOperators(operatorIDs);
    }

    public int modifyPassword(PriviledgeOperatorVo po) throws Exception {
        PriviledgeDao pmda = new PriviledgeDao();
        return pmda.modifyPassword(po);
    }

    //修改密码 
    public EditResult EditUserPwd(HttpServletRequest request, PriviledgeOperatorVo po) throws Exception {
        EditResult EditResult = new EditResult();

        //判断旧密码是否正确	  
        if (!this.isOldPwd(po)) {
            EditResult.setResult(false);
            EditResult.setMsg("旧密码不正确");
            return EditResult;
        }

        if (!this.isEditPwd(request, po)) {
            EditResult.setResult(false);
            EditResult.setMsg("修改失败");
            return EditResult;
        }
        EditResult.setResult(true);
        return EditResult;
    }

    //旧密码是否正确

    public boolean isOldPwd(PriviledgeOperatorVo po) {
        PriviledgeDao pmda = new PriviledgeDao();
        try {
            if (!pmda.isOldPassword(po)) {
                return false;
            }
        } catch (Exception e) {
            logger.error("错误:", e);
            return false;
        }
        return true;
    }

    //更新旧密码

    public boolean isEditPwd(HttpServletRequest request, PriviledgeOperatorVo po) {
        PriviledgeDao pmda = new PriviledgeDao();

        try {
            if (!pmda.iseditPassword(po)) {
                return false;
            }

            UserDao.initFlag = false;
            //应用群集时，发送群集应用操作员缓存应更改信息 
            //modify by hejj 2013-04-08 屏蔽集群
            //if(util.isCluster(request))
            // dbUtil.sendApplicationMessage(request,DBUtil.MESSAGE_TYPE_OPERATOR,-1,"");	  

        } catch (Exception e) {
            logger.error("错误:", e);
            return false;
        }
        return true;
    }
}
