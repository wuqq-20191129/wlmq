/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.login.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.login.constant.LoginConstant;
import com.goldsign.login.vo.Menu;
import com.goldsign.login.vo.ModuleDistrVo;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oywl
 */
public class LoginModulesDao {

    private static int OP_TYPE_QUERY = 0;
    private static int OP_TYPE_ADD = 1;
    private static int OP_TYPE_DELETE = 2;
    private static int OP_TYPE_MODIFY = 3;
    private static int OP_TYPE_CL0NE = 4;
    private static int OP_TYPE_SUBMIT = 5;
    private static int OP_TYPE_EXPORT = 6;
    private static int OP_TYPE_IMPORT = 7;
    private static int OP_TYPE_PRINT = 8;
    private static int OP_TYPE_DOWNLOAD = 9;
    private static int OP_TYPE_RPTREQUERY = 10;
    private static int OP_TYPE_RPTSTATISTIC = 11;
    private static int OP_TYPE_CHECK = 12;
    private static int OP_TYPE_DISTRIBUTE = 13;
    private static int OP_TYPE_REFUND_OK = 14;
    private static int OP_TYPE_REFUND_NO = 15;
    private static int OP_TYPE_REFUND_MODIFY = 16;
    private static int OP_TYPE_REFUND_CHECK = 17;
    private static int OP_TYPE_AUDIT = 18;
    private static Logger logger = Logger.getLogger(LoginDao.class.getName());
    
    
    public Vector getThirdModulesByOperator(String operatorID, String sysFlag, DbHelper dbHelper) throws Exception {
        List<ModuleDistrVo> modules = this.getModulesByOperatorID(operatorID, sysFlag, dbHelper);
        Vector eModules = new Vector();
        for (int i = 0; i < modules.size(); i++) {
            ModuleDistrVo mv = (ModuleDistrVo) modules.get(i);
//            if (mv.getModuleID().length() == 6) {
            eModules.add(mv);
//            }
        }
        return eModules;
    }

    public List<ModuleDistrVo> getModulesByOperatorID(String operatorID, String sysFlag, DbHelper dbHelper) throws Exception {
        String strSql = null;
        boolean result = false;

        List<ModuleDistrVo> modules = new ArrayList<ModuleDistrVo>();
        try {
//           1 查询菜单权限
            strSql = " select distinct  sgm.module_id ,sm.module_name,sm.menu_url , "
                    + " sm.menu_icon ,sm.top_menu_id ,sm.parent_id ,sm.locked ,sm.sys_flag "
                    + " from "+LoginConstant.DB_USER+LoginConstant.TABLE_SYS_PRE+"op_sys_group_module sgm , "
                    +LoginConstant.DB_USER+LoginConstant.TABLE_SYS_PRE+"OP_SYS_module sm "
                    + " where sgm.module_id=sm.module_id and sm.module_type='M' "
                    + " and sgm.sys_group_id in "
                    + " (select sgo.sys_group_id from "+LoginConstant.DB_USER+LoginConstant.TABLE_SYS_PRE+"op_sys_group_operator sgo "
                    + " where sgo.sys_operator_id = '"
                    + operatorID + "' ) ";
            if (sysFlag != null) {
                strSql += " and sm.sys_flag='" + sysFlag + "'";
            }
            strSql += " order by sgm.module_id asc ";

            result = dbHelper.getFirstDocument(strSql);

            while (result) {
                ModuleDistrVo mo = new ModuleDistrVo();
                mo.setModuleID(dbHelper.getItemValue("module_id"));
//                mo.setGroupID(dbHelper.getItemValue("sys_group_id"));

                mo.setMenuName(dbHelper.getItemValue("module_name"));
                mo.setMenuUrl(dbHelper.getItemValue("menu_url"));
                mo.setMenuIcon(dbHelper.getItemValue("menu_icon"));
                mo.setTopMenuId(dbHelper.getItemValue("top_menu_id"));
                mo.setParentId(dbHelper.getItemValue("parent_id"));
                mo.setLocked(dbHelper.getItemValue("locked"));
                mo.setSysFlag(dbHelper.getItemValue("sys_flag"));

                modules.add(mo);
                result = dbHelper.getNextDocument();
            }

//          2  查询按钮权限
            List<Menu> btnModules = new ArrayList<Menu>();
            result = false;
            strSql = " select distinct  sgm.module_id , "
                    + " sm.parent_id ,sm.btn_id "
                    + " from "+LoginConstant.DB_USER+LoginConstant.TABLE_SYS_PRE+"op_sys_group_module sgm , "
                    +LoginConstant.DB_USER+LoginConstant.TABLE_SYS_PRE+"OP_SYS_module sm "
                    + " where sgm.module_id=sm.module_id and sm.module_type='B' "
                    + " and sgm.sys_group_id in "
                    + " (select sgo.sys_group_id from "+LoginConstant.DB_USER+LoginConstant.TABLE_SYS_PRE+"op_sys_group_operator sgo "
                    + " where sgo.sys_operator_id = '"
                    + operatorID + "' ) ";
            if (sysFlag != null) {
                strSql += " and sm.sys_flag='" + sysFlag + "'";
            }
            strSql += " order by sgm.module_id asc ";
            result = dbHelper.getFirstDocument(strSql);
            while (result) {
                Menu mo = new Menu();
                mo.setMenuId(dbHelper.getItemValue("module_id"));
                mo.setParentId(dbHelper.getItemValue("parent_id"));
                mo.setBtnId(dbHelper.getItemValue("btn_id"));
                btnModules.add(mo);
                result = dbHelper.getNextDocument();
            }

//          3 把按钮权限添加到菜单里
            for (ModuleDistrVo moduleDistrVo : modules) {
//               判断属于菜单的按钮组 添加到菜单中             
                moduleDistrVo.setBtnModules(
                        findBtnModules(moduleDistrVo, btnModules));
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "错误:", e);
            throw e;
        } finally {
//            PubUtil.finalProcess(dbHelper, logger);
        }
        return modules;
    }

    private List<Menu> findBtnModules(ModuleDistrVo moduleDistrVo, List<Menu> btnModules) {
        List<Menu> menuBtnModules = new ArrayList<Menu>();
        if (moduleDistrVo != null && btnModules != null) {
            for (Menu menu : btnModules) {
                if (menu.getParentId().equals(moduleDistrVo.getModuleID())) {
                    menuBtnModules.add(menu);
                }
            }
        }
        return menuBtnModules;
    }
}
