/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.frame.dao;

import com.goldsign.frame.constant.FrameCodeConstant;
import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.frame.vo.ModuleBottonVo;
import com.goldsign.frame.vo.ModuleDistrVo;
import java.util.Vector;

import org.apache.log4j.Logger;
import java.util.ArrayList;

import com.goldsign.lib.db.util.DbHelper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author hejj
 */
public class ModuleDistrDao {

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
    private static Logger logger = Logger.getLogger(UserDao.class.getName());

    public ModuleDistrDao() {
    }

    public Vector getModulesByOperator(String operatorID) throws Exception {
        HashMap opModules = new HashMap();
        Vector groups = this.getGroupByOperator(operatorID);
        String groupID = "";
        Vector groupModules = null;
        for (int i = 0; i < groups.size(); i++) {
            groupID = (String) groups.get(i);
            groupModules = this.getModulesByGroupID(groupID);
            opModules.put(groupID, groupModules);
        }
        return this.combineOpModules(opModules);
    }

    public Vector getThirdModulesByOperator(String operatorID) throws Exception {
        Vector modules = this.getModulesByOperator(operatorID);
        Vector eModules = new Vector();
        for (int i = 0; i < modules.size(); i++) {
            ModuleDistrVo mv = (ModuleDistrVo) modules.get(i);
            if (mv.getModuleID().length() > 2) {
                eModules.add(mv);
            }

        }
        return eModules;
    }

    private Vector combineOpModules(HashMap opModules) {
        Vector comModules = new Vector();
        Set keys = opModules.keySet();
        Iterator it = keys.iterator();
        String groupID = "";
        Vector modules = null;
        int i = 0;
        while (it.hasNext()) {
            groupID = (String) it.next();
            modules = (Vector) opModules.get(groupID);
            if (i == 0) {
                comModules.addAll(modules);
                i++;
                continue;
            }
            this.combineModules(comModules, modules);
            i++;
        }
        return comModules;


    }

    private void combineModules(Vector comModules, Vector modules) {
        ModuleDistrVo mv = null;
        for (int i = 0; i < modules.size(); i++) {
            mv = (ModuleDistrVo) modules.get(i);
            if (!this.isExistingModule(comModules, mv)) {
                comModules.add(mv);
            }
        }

    }

    private boolean isExistingModule(Vector comModues, ModuleDistrVo mo) {
        boolean result = false;
        ModuleDistrVo opMo = null;
        String moduleID = mo.getModuleID();
        for (int i = 0; i < comModues.size(); i++) {
            opMo = (ModuleDistrVo) comModues.get(i);
            if (opMo.getModuleID().equals(moduleID)) {
                this.combineModule(opMo, mo);
                return true;
            }
        }
        return result;
    }

    private ModuleDistrVo combineModule(ModuleDistrVo opModuleDistrVo, ModuleDistrVo moduleDistrVO) {

        this.combineRight(opModuleDistrVo, opModuleDistrVo.getQueryRight(), moduleDistrVO.getQueryRight(), this.OP_TYPE_QUERY);
        this.combineRight(opModuleDistrVo, opModuleDistrVo.getAddRight(), moduleDistrVO.getAddRight(), this.OP_TYPE_ADD);
        this.combineRight(opModuleDistrVo, opModuleDistrVo.getDeleteRight(), moduleDistrVO.getDeleteRight(), this.OP_TYPE_DELETE);
        this.combineRight(opModuleDistrVo, opModuleDistrVo.getModifyRight(), moduleDistrVO.getModifyRight(), this.OP_TYPE_MODIFY);

        this.combineRight(opModuleDistrVo, opModuleDistrVo.getCloneRight(), moduleDistrVO.getCloneRight(), this.OP_TYPE_CL0NE);
        this.combineRight(opModuleDistrVo, opModuleDistrVo.getSubmitRight(), moduleDistrVO.getSubmitRight(), this.OP_TYPE_SUBMIT);
        this.combineRight(opModuleDistrVo, opModuleDistrVo.getExportRight(), moduleDistrVO.getExportRight(), this.OP_TYPE_EXPORT);
        this.combineRight(opModuleDistrVo, opModuleDistrVo.getImportRight(), moduleDistrVO.getImportRight(), this.OP_TYPE_IMPORT);
        this.combineRight(opModuleDistrVo, opModuleDistrVo.getPrintRight(), moduleDistrVO.getPrintRight(), this.OP_TYPE_PRINT);

        this.combineRight(opModuleDistrVo, opModuleDistrVo.getDownloadRight(), moduleDistrVO.getDownloadRight(), this.OP_TYPE_DOWNLOAD);
        this.combineRight(opModuleDistrVo, opModuleDistrVo.getRptQueryRight(), moduleDistrVO.getRptQueryRight(), this.OP_TYPE_RPTREQUERY);
        this.combineRight(opModuleDistrVo, opModuleDistrVo.getStatisticRight(), moduleDistrVO.getStatisticRight(), this.OP_TYPE_RPTSTATISTIC);
        this.combineRight(opModuleDistrVo, opModuleDistrVo.getCheckRight(), moduleDistrVO.getCheckRight(), this.OP_TYPE_CHECK);
        this.combineRight(opModuleDistrVo, opModuleDistrVo.getDistributeRight(), moduleDistrVO.getDistributeRight(), this.OP_TYPE_DISTRIBUTE);
        this.combineRight(opModuleDistrVo, opModuleDistrVo.getRefundOkRight(), moduleDistrVO.getRefundOkRight(), this.OP_TYPE_REFUND_OK);
        this.combineRight(opModuleDistrVo, opModuleDistrVo.getRefundNoRight(), moduleDistrVO.getRefundNoRight(), this.OP_TYPE_REFUND_NO);
        this.combineRight(opModuleDistrVo, opModuleDistrVo.getRefundModifyRight(), moduleDistrVO.getRefundModifyRight(), this.OP_TYPE_REFUND_MODIFY);
        this.combineRight(opModuleDistrVo, opModuleDistrVo.getRefundCheckRight(), moduleDistrVO.getRefundCheckRight(), this.OP_TYPE_REFUND_CHECK);
        this.combineRight(opModuleDistrVo, opModuleDistrVo.getAuditRight(), moduleDistrVO.getAuditRight(), this.OP_TYPE_AUDIT);
        return opModuleDistrVo;


    }

    private void combineRight(ModuleDistrVo opModuleDistrVo, String opRight, String right, int opType) {
        if (opRight.trim().equals("1") || right.trim().equals("1")) {
            switch (opType) {
                case 0:
                    opModuleDistrVo.setQueryRight("1");
                    return;
                case 1:
                    opModuleDistrVo.setAddRight("1");
                    return;
                case 2:
                    opModuleDistrVo.setDeleteRight("1");
                    return;
                case 3:
                    opModuleDistrVo.setModifyRight("1");
                    return;

                case 4:
                    opModuleDistrVo.setCloneRight("1");
                    return;
                case 5:
                    opModuleDistrVo.setSubmitRight("1");
                    return;
                case 6:
                    opModuleDistrVo.setExportRight("1");
                    return;
                case 7:
                    opModuleDistrVo.setImportRight("1");
                    return;
                case 8:
                    opModuleDistrVo.setPrintRight("1");
                    return;

                case 9:
                    opModuleDistrVo.setDownloadRight("1");
                    return;
                case 10:
                    opModuleDistrVo.setRptQueryRight("1");
                    return;
                case 11:
                    opModuleDistrVo.setStatisticRight("1");
                    return;
                case 12:
                    opModuleDistrVo.setCheckRight("1");
                    return;
                case 13:
                    opModuleDistrVo.setDistributeRight("1");
                    return;
                case 14:
                    opModuleDistrVo.setRefundOkRight("1");
                    return;
                case 15:
                    opModuleDistrVo.setRefundNoRight("1");
                    return;
                case 16:
                    opModuleDistrVo.setRefundModifyRight("1");
                    return;
                case 17:
                    opModuleDistrVo.setRefundCheckRight("1");
                    return;
                case 18:
                    opModuleDistrVo.setAuditRight("1");
                    return;

            }
        } else {
            switch (opType) {
                case 0:
                    opModuleDistrVo.setQueryRight("0");
                    return;
                case 1:
                    opModuleDistrVo.setAddRight("0");
                    return;
                case 2:
                    opModuleDistrVo.setDeleteRight("0");
                    return;
                case 3:
                    opModuleDistrVo.setModifyRight("0");
                    return;

                case 4:
                    opModuleDistrVo.setCloneRight("0");
                    return;
                case 5:
                    opModuleDistrVo.setSubmitRight("0");
                    return;
                case 6:
                    opModuleDistrVo.setExportRight("0");
                    return;
                case 7:
                    opModuleDistrVo.setImportRight("0");
                    return;
                case 8:
                    opModuleDistrVo.setPrintRight("0");
                    return;

                case 9:
                    opModuleDistrVo.setDownloadRight("0");
                    return;
                case 10:
                    opModuleDistrVo.setRptQueryRight("0");
                    return;
                case 11:
                    opModuleDistrVo.setStatisticRight("0");
                    return;
                case 12:
                    opModuleDistrVo.setCheckRight("0");
                    return;
                case 13:
                    opModuleDistrVo.setDistributeRight("0");
                    return;
                case 14:
                    opModuleDistrVo.setRefundOkRight("0");
                    return;
                case 15:
                    opModuleDistrVo.setRefundNoRight("0");
                    return;
                case 16:
                    opModuleDistrVo.setRefundModifyRight("0");
                    return;
                case 17:
                    opModuleDistrVo.setRefundCheckRight("0");
                    return;
                case 18:
                    opModuleDistrVo.setAuditRight("0");
                    return;

            }

        }

    }

    private Vector getGroupByOperator(String operatorID) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        Vector modules = new Vector();

        String groupID = "";
        Vector groupIDs = new Vector();

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select sys_group_id from op_sys_group_operator where sys_operator_id=?";
            ArrayList pStmtValues = new ArrayList();
            pStmtValues.add(operatorID);
            result = dbHelper.getFirstDocument(strSql, pStmtValues.toArray());

            while (result) {
                groupID = dbHelper.getItemValue("sys_group_id");
                groupIDs.add(groupID);
                result = dbHelper.getNextDocument();
            }

        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }

        return groupIDs;


    }

    /**
     * 修改新版权限后弃用 备份20140108
     * @param groupID
     * @return
     * @throws Exception 
     */
    public Vector getModulesByGroupID_bak(String groupID) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        Vector modules = new Vector();


        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select sys_group_id,sys_module_id,sys_rright,sys_mright,sys_dright,sys_right,sys_clone_right,"
                    + " sys_submit_right,sys_export_right,sys_import_right,sys_print_right,sys_download_right,"
                    + "sys_rptqry_right,sys_rptstatis_right,sys_check_right,sys_distribute_right,sys_refund_ok_right,"
                    + "sys_refund_no_right,sys_refund_md_right,sys_refund_ck_right,sys_audit_right "
                    + " from op_sys_group_module where sys_group_id=? and sys_flag=?";
            ArrayList pStmtValues = new ArrayList();
            pStmtValues.add(groupID);
            pStmtValues.add(FrameCodeConstant.SYS_FLAG);
            result = dbHelper.getFirstDocument(strSql, pStmtValues.toArray());

            while (result) {
                ModuleDistrVo mo = new ModuleDistrVo();
                mo.setModuleID(dbHelper.getItemValue("sys_module_id"));
                mo.setGroupID(dbHelper.getItemValue("sys_group_id"));
                mo.setQueryRight(dbHelper.getItemValue("sys_rright"));
                mo.setAddRight(dbHelper.getItemValue("sys_right"));
                mo.setDeleteRight(dbHelper.getItemValue("sys_dright"));
                mo.setModifyRight(dbHelper.getItemValue("sys_mright"));

                //sys_clone_right, sys_submit_right,sys_export_right,sys_import_right,sys_print_right,
                //sys_download_right,sys_rptqry_right,sys_rptstatis_right,sys_check_right,sys_distribute_right
                mo.setCloneRight(dbHelper.getItemValue("sys_clone_right"));
                mo.setSubmitRight(dbHelper.getItemValue("sys_submit_right"));
                mo.setExportRight(dbHelper.getItemValue("sys_export_right"));
                mo.setImportRight(dbHelper.getItemValue("sys_import_right"));
                mo.setPrintRight(dbHelper.getItemValue("sys_print_right"));

                mo.setDownloadRight(dbHelper.getItemValue("sys_download_right"));
                mo.setRptQueryRight(dbHelper.getItemValue("sys_rptqry_right"));
                mo.setStatisticRight(dbHelper.getItemValue("sys_rptstatis_right"));
                mo.setCheckRight(dbHelper.getItemValue("sys_check_right"));
                mo.setDistributeRight(dbHelper.getItemValue("sys_distribute_right"));
                mo.setRefundOkRight(dbHelper.getItemValue("sys_refund_ok_right"));
                mo.setRefundNoRight(dbHelper.getItemValue("sys_refund_no_right"));
                mo.setRefundModifyRight(dbHelper.getItemValue("sys_refund_md_right"));
                mo.setRefundCheckRight(dbHelper.getItemValue("sys_refund_ck_right"));

                mo.setAuditRight(dbHelper.getItemValue("sys_audit_right"));

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

    public int distrModules(String groupID, Vector modules) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        ArrayList pStmtValues = new ArrayList();
        int result = 0;
        ModuleDistrVo module = null;


        try {

            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            dbHelper.setAutoCommit(false);
            strSql = "delete from op_sys_group_module where sys_group_id=?";
            pStmtValues.add(groupID);
            dbHelper.executeUpdate(strSql, pStmtValues.toArray());

            strSql = "insert into op_sys_group_module(sys_group_id,sys_module_id,sys_rright,sys_right,sys_dright,sys_mright,sys_clone_right, sys_submit_right,sys_export_right,sys_import_right,sys_print_right,sys_download_right,sys_rptqry_right,sys_rptstatis_right,sys_check_right,sys_distribute_right,sys_refund_ok_right,sys_refund_no_right,sys_refund_md_right,sys_refund_ck_right,sys_audit_right) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            for (int i = 0; i < modules.size(); i++) {
                module = (ModuleDistrVo) modules.get(i);
                pStmtValues.clear();
                pStmtValues.add(groupID);
                pStmtValues.add(module.getModuleID());
                pStmtValues.add(module.getQueryRight());
                pStmtValues.add(module.getAddRight());
                pStmtValues.add(module.getDeleteRight());
                pStmtValues.add(module.getModifyRight());

                pStmtValues.add(module.getCloneRight());
                pStmtValues.add(module.getSubmitRight());
                pStmtValues.add(module.getExportRight());
                pStmtValues.add(module.getImportRight());
                pStmtValues.add(module.getPrintRight());

                pStmtValues.add(module.getDownloadRight());
                pStmtValues.add(module.getRptQueryRight());
                pStmtValues.add(module.getStatisticRight());
                pStmtValues.add(module.getCheckRight());
                pStmtValues.add(module.getDistributeRight());
                pStmtValues.add(module.getRefundOkRight());
                pStmtValues.add(module.getRefundNoRight());
                pStmtValues.add(module.getRefundModifyRight());
                pStmtValues.add(module.getRefundCheckRight());

                pStmtValues.add(module.getAuditRight());
                //    System.out.println("RefundOkRight:"+module.getRefundOkRight()+" RefundNoRight:"+module.getRefundNoRight()
                //    		           +" RefundModifyRight"+module.getRefundModifyRight());

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
    
    /**
     * 新版用户组权限
     * @param groupID
     * @return
     * @throws Exception 
     */
    public Vector getModulesByGroupID(String groupID) throws Exception {
        Vector buttons = this.getButtonsByGroupID(groupID);//按钮权限
        Vector modules = new Vector();//组合后使用的权限
        
        boolean isAdd = false;//循环标志
        for(int i=buttons.size()-1; i>=0; i--){
            isAdd = false;
            ModuleBottonVo bo = (ModuleBottonVo) buttons.get(i);
            //模块已经存在时，更新数据
            for(int j=0; j<modules.size() && !isAdd; j++){
                ModuleDistrVo opModuleDistrVo = (ModuleDistrVo) modules.get(j);
                if(bo.getModuleId().equals(opModuleDistrVo.getModuleID())){
                    combineRight(opModuleDistrVo, "1", "1", Integer.valueOf(bo.getBtnId()));
                    modules.remove(j);
                    modules.add(opModuleDistrVo);
                    isAdd = true;
                }
            }
            //模块已经存在时，更新数据，否则插入数据
            if(!isAdd){
                ModuleDistrVo opModuleDistrVo = new ModuleDistrVo();
                opModuleDistrVo.setGroupID(groupID);
                opModuleDistrVo.setModuleID(bo.getModuleId());
                combineRight(opModuleDistrVo, "1", "1", Integer.valueOf(bo.getBtnId()));
                modules.add(opModuleDistrVo);
            }
        }
        
        return modules;
    }
    
    /**
     * 数据库查询用户组本系统按钮权限
     * @param groupID 
     * @return
     * @throws Exception 
     */
    public Vector getButtonsByGroupID(String groupID) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        Vector buttons = new Vector();

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select distinct C.parent_id,B.module_id,to_number(C.btn_id) btn_id from op_sys_group_module B, op_sys_module C"
                    + " where rtrim(B.sys_group_id) = ? and B.module_id = C.module_id(+)"
                    + " and C.module_type = 'B' and C.sys_flag = ? "
                    + " order by B.module_id";
            ArrayList pStmtValues = new ArrayList();
            pStmtValues.add(groupID);//用户组
            pStmtValues.add(FrameCodeConstant.SYS_FLAG);//系统代码
            result = dbHelper.getFirstDocument(strSql, pStmtValues.toArray());

            while (result) {
                ModuleBottonVo bo = new ModuleBottonVo();
                bo.setBtnId(dbHelper.getItemValue("btn_id"));
                bo.setModuleId(dbHelper.getItemValue("parent_id"));
                buttons.add(bo);
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }

        return buttons;
    }
    
}
