package com.goldsign.systemmonitor.dao;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FrameCharUtil;
import com.goldsign.frame.util.FrameDBUtil;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.frame.vo.Menu;
import com.goldsign.lib.db.util.DbHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import org.apache.log4j.Logger;

public class MenuDao {

    static Logger logger = Logger.getLogger(MenuDao.class);

    public MenuDao() {
        super();
    }

    public Vector getTopMenu() throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        Vector topModuleIDs = new Vector();
        Menu menu;
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select distinct menu_id,menu_name,menu_url,menu_icon,top_menu_id,parent_id,locked,password_flag from mtr_menu where  length(ltrim(rtrim(menu_id)))=2";

            result = dbHelper.getFirstDocument(strSql);

            while (result) {
                menu = new Menu();
                menu.setMenuId(dbHelper.getItemValue("menu_id"));
                menu.setMenuName(dbHelper.getItemValueIso("menu_name"));
                menu.setUrl(dbHelper.getItemValue("menu_url"));
                menu.setIcon(dbHelper.getItemValue("menu_icon"));
                menu.setTopMenuId(dbHelper.getItemValue("top_menu_id"));
                menu.setParentId(dbHelper.getItemValue("parent_id"));
                menu.setLocked(dbHelper.getItemValue("locked"));
                menu.setPasswordFlag(dbHelper.getItemValue("password_flag"));
                topModuleIDs.add(menu);
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
        String strSql = null;
        DbHelper dbHelper = null;
        boolean result = false;
        Vector resultList = new Vector();

        try {

            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);

            strSql = "select * from mtr_menu where top_menu_id<>'0' order by menu_id ";
            result = dbHelper.getFirstDocument(strSql);
            while (result) {
                Menu menu = new Menu();
                menu.setMenuId(dbHelper.getItemValue("menu_id"));
                menu.setMenuName(dbHelper.getItemValueIso("menu_name"));
                menu.setUrl(dbHelper.getItemValue("menu_url"));
                menu.setIcon(dbHelper.getItemValue("menu_icon"));
                menu.setTopMenuId(dbHelper.getItemValue("top_menu_id"));
                menu.setParentId(dbHelper.getItemValue("parent_id"));
                menu.setLocked(dbHelper.getItemValue("locked"));

                resultList.add(menu);
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        return resultList;
    }

    public Vector getLeftMenu(String topMenuId) throws Exception {
        String strSql = null;
        DbHelper dbHelper = null;
        boolean result = false;
        Vector resultList = new Vector();
        Object[] values = {topMenuId};
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select menu_id,menu_name,menu_url,menu_icon,top_menu_id,parent_id,"
                    + "locked,post_parameter,status,new_window_flag "
                    + " from mtr_menu where top_menu_id<>'0' and top_menu_id=? order by menu_id ";
            result = dbHelper.getFirstDocument(strSql, values);
            while (result) {
                Menu menu = new Menu();
                menu.setMenuId(dbHelper.getItemValue("menu_id"));
                menu.setMenuName(dbHelper.getItemValueIso("menu_name"));
                menu.setUrl(dbHelper.getItemValue("menu_url"));
                menu.setIcon(dbHelper.getItemValue("menu_icon"));
                menu.setTopMenuId(dbHelper.getItemValue("top_menu_id"));
                menu.setParentId(dbHelper.getItemValue("parent_id"));
                menu.setLocked(dbHelper.getItemValue("locked"));
                menu.setStatus(dbHelper.getItemValue("status"));
                menu.setNewWindowFlag(dbHelper.getItemValue("new_window_flag"));

                resultList.add(menu);
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        return resultList;
    }

    public String getMenuPostParameter(String moduleId) throws Exception {
        String strSql = null;
        DbHelper dbHelper = null;
        boolean result = false;
        Object[] values = {moduleId};
        String postParameter = "";

        try {

            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);

            strSql = "select post_parameter from mtr_menu where menu_id=?  ";
            result = dbHelper.getFirstDocument(strSql, values);
            if (result) {
                postParameter = dbHelper.getItemValue("post_parameter");
            }
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        return postParameter;
    }

    public Vector getModulesByCondition(Menu po) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        Vector modules = new Vector();

        FrameDBUtil dbUtil = new FrameDBUtil();
        Vector pubFlags = dbUtil.getPubFlags();
        Vector typePubFlags = dbUtil.getPubFlagsByType(1, pubFlags);
        Vector typePubFlagsNewWindow = dbUtil.getPubFlagsByType(FrameDBConstant.Flag_type_new_window_flag, pubFlags);
        FrameDBUtil util = new FrameDBUtil();

        ArrayList pStmtValues = new ArrayList();

        String whereParams = "";
        HashMap params = new HashMap();

        params.put("menu_id", po.getMenuId());
        params.put("menu_name", po.getMenuName());
        params.put("top_menu_id", po.getTopMenuId());
        params.put("parent_id", po.getParentId());
        whereParams = util.getWhereParam(params, pStmtValues);



        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select menu_id,menu_name,top_menu_id,parent_id,menu_url,menu_icon,locked,new_window_flag from mtr_menu " + whereParams + " order by menu_id ";
            result = dbHelper.getFirstDocument(strSql, pStmtValues.toArray());

            while (result) {
                Menu mo = new Menu();
                mo.setMenuId(dbHelper.getItemValue("menu_id"));
                mo.setMenuName(dbHelper.getItemValueIso("menu_name"));
                mo.setTopMenuId(dbHelper.getItemValue("top_menu_id"));
                mo.setParentId(dbHelper.getItemValue("parent_id"));
                mo.setUrl(dbHelper.getItemValue("menu_url"));
                mo.setIcon(dbHelper.getItemValue("menu_icon"));
                mo.setLocked(dbHelper.getItemValue("locked"));
                mo.setNewWindowFlag(dbHelper.getItemValue("new_window_flag"));
                mo.setLockedText(dbUtil.getTextByCode(dbHelper.getItemValue("locked"), typePubFlags));
                mo.setNewWindowFlagText(dbUtil.getTextByCode(dbHelper.getItemValue("new_window_flag"), typePubFlagsNewWindow));
                modules.add(mo);
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        return modules;
    }

    public int addMoule(Menu mo) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;


        ArrayList pStmtValues = new ArrayList();
        int result = 0;
        pStmtValues.add(mo.getMenuId());
        pStmtValues.add(FrameCharUtil.IsoToGbk(mo.getMenuName()));
        pStmtValues.add(mo.getTopMenuId());
        pStmtValues.add(mo.getParentId());
        pStmtValues.add(mo.getUrl());
        pStmtValues.add(mo.getIcon());
        pStmtValues.add(mo.getLocked());
        pStmtValues.add(mo.getPostParameter());
        pStmtValues.add(mo.getNewWindowFlag());

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            if (!this.isExists(dbHelper, mo.getMenuId())) {
                strSql = "insert into mtr_menu(menu_id,menu_name,top_menu_id,parent_id,menu_url,menu_icon,locked,post_parameter,new_window_flag) values(?,?,?,?,?,?,?,?,?)";
                result = dbHelper.executeUpdate(strSql, pStmtValues.toArray());
            }
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        return result;
    }

    public boolean isExists(DbHelper dbHelper, String menuId) throws Exception {
        String strSql = null;
        boolean result = false;
        
        dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
        strSql = "select count(*) num from mtr_menu where menu_id='" + menuId + "'";
        result = dbHelper.getFirstDocument(strSql);
        if(dbHelper.getItemValue("num").equals("0")){
            result=false;
        }
        
        return result;
    }

    private boolean isValidPostParameter(String postParameter) {
        if (postParameter == null || postParameter.length() == 0) {
            return false;
        }
        return true;
    }

    public int modifyMoule(Menu mo) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;


        ArrayList pStmtValues = new ArrayList();
        int result = 0;

        pStmtValues.add(FrameCharUtil.IsoToGbk(mo.getMenuName()));
        pStmtValues.add(mo.getTopMenuId());
        pStmtValues.add(mo.getParentId());
        pStmtValues.add(mo.getUrl());
        pStmtValues.add(mo.getIcon());
        pStmtValues.add(mo.getLocked());
        pStmtValues.add(mo.getNewWindowFlag());
        if (this.isValidPostParameter(mo.getPostParameter())) {
            pStmtValues.add(mo.getPostParameter());
        }
        pStmtValues.add(mo.getMenuId());

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "update mtr_menu set menu_name=?,top_menu_id=?,parent_id=?,menu_url=?,menu_icon=?,locked=?,new_window_flag=? where menu_id=?";
            if (this.isValidPostParameter(mo.getPostParameter())) {
                strSql = "update mtr_menu set menu_name=?,top_menu_id=?,parent_id=?,menu_url=?,menu_icon=?,locked=?,post_parameter=?  ,new_window_flag=? where menu_id=?";
            }
            result = dbHelper.executeUpdate(strSql, pStmtValues.toArray());
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        return result;
    }

    public int deleteModules(Vector moduleIDs) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        ArrayList pStmtValues = new ArrayList();
        int result = 0;
        String moduleID = null;
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            dbHelper.setAutoCommit(false);
            for (int i = 0; i < moduleIDs.size(); i++) {
                moduleID = (String) moduleIDs.get(i);
                pStmtValues.clear();
                pStmtValues.add(moduleID);
                strSql = "delete from mtr_menu where menu_id=?";
                result += dbHelper.executeUpdate(strSql, pStmtValues.toArray());
            }
            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);
        } catch (Exception e) {
            FramePubUtil.handleExceptionForTran(e, logger, dbHelper);
        } finally {
            FramePubUtil.finalProcessForTran(dbHelper);
        }
        return result;

    }
}
