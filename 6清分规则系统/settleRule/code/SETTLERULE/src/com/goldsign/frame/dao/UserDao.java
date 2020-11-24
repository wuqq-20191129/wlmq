/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.frame.dao;

import com.goldsign.frame.constant.FrameCodeConstant;
import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.frame.vo.Menu;
import com.goldsign.frame.vo.User;
import java.sql.SQLException;
import javax.naming.NamingException;

import java.util.Vector;
import java.util.Hashtable;



import com.goldsign.lib.db.util.DbHelper;
import org.apache.log4j.Logger;
import java.util.ArrayList;

/**
 *
 * @author hejj
 */
public class UserDao {

    private static Logger logger = Logger.getLogger(UserDao.class.getName());
    public static boolean initFlag = false;
    private static Hashtable accountTable = new Hashtable();
    private static Vector topMenuList = new Vector();
    private static Vector leftMenuList = new Vector();

    public synchronized static void init() throws Exception {
        if (!initFlag) {
            accountTable.clear();
            leftMenuList.clear();

            UserDao userDAO = new UserDao();
            //userList 0:用户数 1:用户对象
            Vector userList = userDAO.findAll();
            User newUser = null;
            for (int i = 1; i < userList.size(); i++) {
                newUser = (User) userList.elementAt(i);
                accountTable.put(newUser.getAccount(), newUser);
            }

            topMenuList = userDAO.findTopMenu();
            leftMenuList = userDAO.findLeftMenu();

            initFlag = false;
        }
    }

    public Vector getTopMenu(String operatorID) throws Exception {
        Vector topMenu = this.getTopMenuByOperator(operatorID);
        return topMenu;
    }

    public Vector getTopMenuByOperator(String operatorID) throws Exception {
        Vector userTopMenu = new Vector();
        //获得用户有权访问的顶菜单
        Vector topModuleIDs = this.getTopModuleIDsByOperator(operatorID);
        Menu menu = null;
        //群集时topMenuList可能为空
        if (this.topMenuList.isEmpty()) {
            this.topMenuList = this.findTopMenu();
        }
        for (int i = 0; i < this.topMenuList.size(); i++) {
            menu = (Menu) topMenuList.get(i);
            if (this.isEffectiveTopMenu(menu, topModuleIDs, operatorID)) {
                userTopMenu.add(menu);
            }

        }
        return userTopMenu;

    }

    public boolean isEffectiveTopMenu(Menu menu, Vector topModuleIDs, String operatorID) throws Exception {
        //
        String topModuleID = "";
        for (int i = 0; i < topModuleIDs.size(); i++) {
            topModuleID = (String) topModuleIDs.get(i);
            if (menu.getMenuId().equals(topModuleID)) {
                return true;
            }
        }
        return false;
    }

    public Vector getTopModuleIDsByOperator(String operatorID) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        Vector topModuleIDs = new Vector();
        String moduleID = "";

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
//             strSql = "select distinct sys_module_id from op_sys_group_module  "
//                     + "where  sys_group_id in(select sys_group_id from op_sys_group_operator where sys_operator_id='"+operatorID+"' )";
           strSql = "select distinct a.module_id sys_module_id from op_sys_group_module a, op_sys_module b"
                   + " where b.module_type='M' and a.module_id=b.module_id"
                   + " and a.sys_group_id in(select sys_group_id from op_sys_group_operator where sys_operator_id='"+operatorID+"' )";   
            result = dbHelper.getFirstDocument(strSql);

            while (result) {
                moduleID = dbHelper.getItemValue("sys_module_id");
                if (moduleID.trim().length() == 2) {
                    topModuleIDs.add(moduleID);
                }
                result = dbHelper.getNextDocument();
            }

        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }

        return topModuleIDs;


    }

    public Vector getLeftMenu() throws Exception {
        //群集服务器切换时，leftMenuList可能为空
        if (this.leftMenuList.isEmpty()) {
            this.leftMenuList = this.findLeftMenu();
        }
        return leftMenuList;
    }

    private Vector findLeftMenu() throws Exception {
        String strSql = null;
        DbHelper dbHelper = null;
        boolean result = false;
        Vector resultList = new Vector();

        try {
            logger.debug("UserDao -findLeftMenu started(MAIN_DATASOURCE : " + FrameDBConstant.MAIN_DATASOURCE + ")");
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);

//            strSql = "select * from op_sys_menu where sys_flag='"+FrameCodeConstant.SYS_FLAG+"' and length(menu_id)>2 order by menu_id ";
            strSql = "select module_id menu_id, module_name menu_name, menu_url, menu_icon, top_menu_id, parent_id, locked, sys_flag"
                    + " from op_sys_module where module_type='M' and sys_flag='"+FrameCodeConstant.SYS_FLAG+"' and length(module_id)>2 order by module_id";
            result = dbHelper.getFirstDocument(strSql);
            while (result) {
                Menu menu = new Menu();
                menu.setMenuId(dbHelper.getItemValue("menu_id"));
                menu.setMenuName(dbHelper.getItemValue("menu_name"));
                menu.setUrl(dbHelper.getItemValue("menu_url"));
                menu.setIcon(dbHelper.getItemValue("menu_icon"));
                menu.setTopMenuId(dbHelper.getItemValue("top_menu_id"));
                menu.setParentId(dbHelper.getItemValue("parent_id"));
                menu.setLocked(dbHelper.getItemValue("locked"));

                resultList.add(menu);
                result = dbHelper.getNextDocument();
            }
            logger.debug("UserDao - findLeftMenu ended");
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }
        return resultList;
    }

    private Vector findTopMenu() throws Exception {
        String strSql = null;
        DbHelper dbHelper = null;
        boolean result = false;
        Vector resultList = new Vector();

        try {
            logger.debug("UserDao -findTopMenu started(MAIN_DATASOURCE : " + FrameDBConstant.MAIN_DATASOURCE + ")");
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);

//            strSql = "select * from op_sys_menu where sys_flag='"+FrameCodeConstant.SYS_FLAG+"' order by menu_id ";//修改查询条件为系统代码
            strSql = "select module_id menu_id, module_name menu_name, menu_url, menu_icon, top_menu_id, parent_id, locked, sys_flag"
                    + " from op_sys_module where module_type='M' and sys_flag='"+FrameCodeConstant.SYS_FLAG+"' order by module_id ";//修改查询条件为系统代码
            result = dbHelper.getFirstDocument(strSql);
            while (result) {
                Menu menu = new Menu();
                menu.setMenuId(dbHelper.getItemValue("menu_id"));
                menu.setMenuName(dbHelper.getItemValue("menu_name"));
                menu.setUrl(dbHelper.getItemValue("menu_url"));
                menu.setIcon(dbHelper.getItemValue("menu_icon"));
                menu.setTopMenuId(dbHelper.getItemValue("top_menu_id"));
                menu.setParentId(dbHelper.getItemValue("parent_id"));
                menu.setLocked(dbHelper.getItemValue("locked"));

                resultList.add(menu);
                result = dbHelper.getNextDocument();
            }
            logger.debug("UserDao - findTopMenu ended");
        }catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }
        return resultList;
    }

    private Vector findAll() throws Exception {
        String countSql = null;
        String strSql = null;
        DbHelper dbHelper = null;
        boolean result = false;
        int rowCount = 0;
        Vector resultList = new Vector();

        try {
            logger.debug("UserDao - findAll started(MAIN_DATASOURCE : " + FrameDBConstant.MAIN_DATASOURCE + ")");
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);

            //countSql="select count(*) usercount from sys_users";
            //strSql="select account,password,name,sex,department from sys_users";
            countSql = "select count(*) usercount from op_sys_operator";
            strSql = "select sys_operator_id,sys_password_hash,sys_operator_name,sys_employee_id,sys_expired_date,sys_status,login_num,failed_num,session_id,password_edit_date,edit_past_days from op_sys_operator";

            result = dbHelper.getFirstDocument(countSql);
            if (result) {
                rowCount = dbHelper.getItemIntValue("usercount");
            }
            resultList.addElement(new Integer(rowCount));
            result = false;

            result = dbHelper.getFirstDocument(strSql);
            while (result) {
                User user = new User();
                /*
                 user.setAccount(dbHelper.getItemValue("account"));
                 user.setPassword(dbHelper.getItemValue("password"));
                 user.setUsername(dbHelper.getItemValue("name"));
                 user.setSex(dbHelper.getItemValue("sex"));
                 user.setDepartment(dbHelper.getItemValue("department"));
                 */
                user.setAccount(dbHelper.getItemValue("sys_operator_id"));
                user.setPassword(dbHelper.getItemValueWithoutTrim("sys_password_hash"));
                user.setUsername(dbHelper.getItemValue("sys_operator_name"));
                user.setEmployeeID(dbHelper.getItemValue("sys_employee_id"));
                user.setExpireDate(dbHelper.getItemValue("sys_expired_date"));
                user.setUserStatus(dbHelper.getItemValue("sys_status"));
                user.setLoginNum(dbHelper.getItemIntValue("login_num"));
                user.setFailedNum(dbHelper.getItemIntValue("failed_num"));
                user.setSessionID(dbHelper.getItemValue("session_id"));

                //2007-07-18  yjh 新增
                user.setEditPassWordDate(dbHelper.getItemValue("password_edit_date"));
                user.setEditPassWordDays(dbHelper.getItemIntValue("edit_past_days"));

                resultList.add(user);
                result = dbHelper.getNextDocument();
            }

            logger.debug("UserDao - findAll ended");
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }
        return resultList;

    }

    public int modifyUser(String operatorID, String fieldName, int fieldValue) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        ArrayList pStmtValues = new ArrayList();
        int result = 0;

        pStmtValues.add(new Integer(fieldValue));
        pStmtValues.add(operatorID);

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "update op_sys_operator set " + fieldName + "=? " + "where sys_operator_id=?";
            result = dbHelper.executeUpdate(strSql, pStmtValues.toArray());
        } catch (NamingException e) {
            logger.error("错误:", e);
            throw e;
        } catch (SQLException e) {
            logger.error("错误:", e);
            throw e;
        } finally {
            try {
                if (dbHelper != null) {
                    dbHelper.closeConnection();
                }
            } catch (SQLException e) {
                logger.error("Fail to close connection", e);
            }
        }
        return result;
    }

    public int modifyUser(String operatorID, String fieldName, String fieldValue, String fieldName1, int fieldValue1) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        ArrayList pStmtValues = new ArrayList();
        int result = 0;

        pStmtValues.add(fieldValue);
        pStmtValues.add(new Integer(fieldValue1));
        pStmtValues.add(operatorID);

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "update op_sys_operator set " + fieldName + "=?," + fieldName1 + "=? " + "where sys_operator_id=?";
            result = dbHelper.executeUpdate(strSql, pStmtValues.toArray());
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }
        return result;
    }

    public int modifyUser(String operatorID, String fieldName, String fieldValue) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        ArrayList pStmtValues = new ArrayList();
        int result = 0;

        pStmtValues.add(fieldValue);
        pStmtValues.add(operatorID);

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "update op_sys_operator set " + fieldName + "=? " + "where sys_operator_id=?";
            result = dbHelper.executeUpdate(strSql, pStmtValues.toArray());
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }
        return result;
    }

    public User findByAccount(String newAccount) throws Exception {
        init();
        if (newAccount == null) {
            return null;
        } else {
            return (User) accountTable.get(newAccount);
        }
    }

    public Hashtable getUserRoles(String account) throws Exception {
        String strSql = null;
        DbHelper dbHelper = null;
        boolean result = false;
        Hashtable resultList = new Hashtable();

        try {
            logger.debug("UserDao -getUserNameRoles started(MAIN_DATASOURCE : " + FrameDBConstant.MAIN_DATASOURCE + ")");
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);

            //strSql="select A.role_id,B.description from user_roles A,sys_roles B where A.role_id=B.role_id and A.account='" + account + "' order by A.role_id ";
            strSql = "select A.sys_group_id,B.sys_group_name from op_sys_group_operator A,sys_group B where A.sys_group_id=B.sys_group_id and A.sys_operator_id='" + account + "' order by A.sys_group_id ";
            result = dbHelper.getFirstDocument(strSql);
            while (result) {
                String tmpRoleId = dbHelper.getItemValue("sys_group_id");
                String tmpDescription = dbHelper.getItemValue("sys_group_name");

                resultList.put(tmpRoleId, tmpDescription);
                result = dbHelper.getNextDocument();
            }
            logger.debug("UserDao - getUserNameRoles ended");
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }
        return resultList;

    }

    public Hashtable getUserMenu(String account) throws Exception {
        String strSql = null;
        DbHelper dbHelper = null;
        boolean result = false;
        Hashtable resultList = new Hashtable();
        ArrayList values = new ArrayList();

        try {
            logger.debug("UserDao -getUserMenu started(MAIN_DATASOURCE : " + FrameDBConstant.MAIN_DATASOURCE + ")");

            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);

//             strSql = "select distinct B.sys_module_id from op_sys_group_operator A,op_sys_group_module B,op_sys_menu C "
//                     + "where A.sys_group_id=B.sys_group_id and B.sys_module_id=C.menu_id(+) and C.sys_flag='"+FrameCodeConstant.SYS_FLAG+"' "
//                     + "and rtrim(A.sys_operator_id)=? order by B.sys_module_id ";
            strSql = "select distinct B.module_id sys_module_id from op_sys_group_operator A,op_sys_group_module B,op_sys_module C"
                    + " where A.sys_group_id=B.sys_group_id and B.module_id=C.module_id(+)"
                    + " and C.sys_flag='"+FrameCodeConstant.SYS_FLAG+"' and c.module_type='M' and rtrim(A.sys_operator_id)=? order by B.module_id";
            values.add(account);
            result = dbHelper.getFirstDocument(strSql,values.toArray());
            while (result) {
                String tmpMenuId = dbHelper.getItemValue("sys_module_id");
                //应该put菜单VO
                resultList.put(tmpMenuId, tmpMenuId);

                result = dbHelper.getNextDocument();
            }
            logger.debug("UserDao - getUserMenu ended");
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }
        return resultList;

    }

    public String getMaxVersion() {
        String strSql = null;
        DbHelper dbHelper = null;
        boolean result = false;
        String version = "";


        try {

            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);

            strSql = "select max(version_no) maxversion  from sr_sys_version ";
            result = dbHelper.getFirstDocument(strSql);
            if (result) {
                version = dbHelper.getItemValue("maxversion");
            }


        } catch (Exception e) {
            FramePubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }
        return version;
    }
}
