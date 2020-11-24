/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.frame.dao;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FrameDBUtil;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.frame.util.FrameUtil;
import com.goldsign.frame.vo.PriviledgeGroupVo;
import com.goldsign.frame.vo.PriviledgeOperatorVo;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.naming.NamingException;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import java.util.ArrayList;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.login.util.Encryption;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author hejj
 */
public class PriviledgeDao {

    private static Logger logger = Logger.getLogger(PriviledgeDao.class.getName());

    public PriviledgeDao() {
    }

    public boolean isExistingForGroup(String groupID) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        Vector groups = new Vector();
        ArrayList pStmtValue = new ArrayList();
        pStmtValue.add(groupID);

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select sys_group_id from op_sys_group where sys_group_id=?";
            result = dbHelper.getFirstDocument(strSql, pStmtValue.toArray());

        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }

        return result;


    }

    public Vector getAllPriviledges() throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        Vector groups = new Vector();


        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select sys_group_id,sys_group_name from op_sys_group";
            result = dbHelper.getFirstDocument(strSql);

            while (result) {
                PriviledgeGroupVo pg = new PriviledgeGroupVo();
                pg.setGroupID(dbHelper.getItemValue("sys_group_id"));
                pg.setGroupName(dbHelper.getItemValue("sys_group_name"));
                groups.add(pg);
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }

        return groups;
    }

    public String getGroupsByOperatorID(String operatorID) throws Exception {
        String strSql = null;
        boolean result = false;
        String groupIDs = "";
        ArrayList pStmtValues = new ArrayList();
        DbHelper dbHelper = null;

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            pStmtValues.add(operatorID);
            strSql = "select sys_group_id from op_sys_group_operator where sys_operator_id=?";
            result = dbHelper.getFirstDocument(strSql, pStmtValues.toArray());

            while (result) {
                groupIDs += dbHelper.getItemValue("sys_group_id") + ",";
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }


        if (groupIDs.length() != 0) {
            groupIDs = groupIDs.substring(0, groupIDs.length() - 1);
        }
        return groupIDs;
    }

    public String getGroupsByOperatorID(DbHelper dbHelper, String operatorID) throws Exception {
        String strSql = null;
        boolean result = false;
        String groupIDs = "";
        ArrayList pStmtValues = new ArrayList();
        // DbHelper dbHelper =null;

        try {
            //    dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            pStmtValues.add(operatorID);
            strSql = "select sys_group_id from op_sys_group_operator where sys_operator_id=?";
            result = dbHelper.getFirstDocument(strSql, pStmtValues.toArray());

            while (result) {
                groupIDs += dbHelper.getItemValue("sys_group_id") + ",";
                result = dbHelper.getNextDocument();
            }
        } /*
         catch (NamingException e){
         logger.error("错误:",e);
         throw e;
         }
         */ catch (SQLException e) {
            logger.error("错误:", e);
            throw e;
        }
        /*
         finally {
         try {
         if (dbHelper != null)
         dbHelper.closeConnection();
         }
         catch (SQLException e) {
         logger.error("Fail to close connection", e);
         }
         }
         */


        if (groupIDs.length() != 0) {
            groupIDs = groupIDs.substring(0, groupIDs.length() - 1);
        }
        return groupIDs;
    }

    public int modifyGroup(String groupID, String groupName) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        Vector groups = new Vector();
        ArrayList pStmtValues = new ArrayList();
        int result = 0;

        pStmtValues.add(FrameUtil.GbkToIso(groupName));
        pStmtValues.add(groupID);


        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "update op_sys_group set sys_group_name=? where sys_group_id=?";
            result = dbHelper.executeUpdate(strSql, pStmtValues.toArray());

        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }



        return result;

    }

    public int addGroup(String groupID, String groupName, HttpServletRequest request) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        Vector groups = new Vector();
        ArrayList pStmtValues = new ArrayList();
        int result = 0;
        String grpID = (new FrameDBUtil()).getSequenceByauto("op_sys_group", "sys_group_id", 99);
        pStmtValues.add(grpID);
        // pStmtValues.add(groupID);
        pStmtValues.add(new FrameUtil().GbkToIso(groupName));
        //  pStmtValues.add(new CharUtil().IsoToGbk(groupName));
        //pStmtValues.add(groupName);

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "insert into op_sys_group(sys_group_id,sys_group_name) values(?,?)";
            result = dbHelper.executeUpdate(strSql, pStmtValues.toArray());
            new FrameUtil().setAutoKeyRequestParameter(request, "groupIDTF#" + grpID);

        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }



        return result;

    }

    public int deleteGroups(Vector groupIDs) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        ArrayList pStmtValues = new ArrayList();
        int result = 0;
        String groupID = null;

        try {

            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            dbHelper.setAutoCommit(false);
            for (int i = 0; i < groupIDs.size(); i++) {
                groupID = (String) groupIDs.get(i);
                pStmtValues.clear();
                pStmtValues.add(groupID);
                strSql = "delete from op_sys_group where sys_group_id=?";
                result += dbHelper.executeUpdate(strSql, pStmtValues.toArray());

                this.deleteOperatorInGroup(dbHelper, groupID);
                this.deleteModulesInGroup(dbHelper, groupID);
            }
            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);
        } catch (Exception e) {
            FramePubUtil.handleExceptionForTran(e, logger,dbHelper);
        } finally {
            FramePubUtil.finalProcessForTran(dbHelper);

        }

        return result;

    }
    //groupID为NULL或空时，选择所有


    public String getGroupIDsText(String groupIDs) throws Exception {
        StringTokenizer st = new StringTokenizer(groupIDs, ",");
        FrameDBUtil dbUtil = new FrameDBUtil();
        Vector groups = dbUtil.getTableFlags("op_sys_group", "sys_group_id", "sys_group_name");
        String groupID = null;
        String groupIDText = "";
        while (st.hasMoreTokens()) {
            groupID = st.nextToken();
            groupIDText += dbUtil.getTextByCode(groupID, groups) + ",";
        }
        if (groupIDText.length() != 0) {
            groupIDText = groupIDText.substring(0, groupIDText.length() - 1);
        }
        return groupIDText;

    }

    public Vector getAllOperatorsForGroup(String groupID) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        Vector operators = new Vector();

        ArrayList pStmtValues = new ArrayList();
        FrameDBUtil dbUtil = new FrameDBUtil();

        Vector pubFlags = dbUtil.getPubFlags();
        Vector statuses = dbUtil.getPubFlagsByType(2, pubFlags);
        String operatorId = "";
        String groupIDs = "";
        String groupIDText = "";


        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            if (groupID == null || groupID.trim().length() == 0) {
                strSql = "select distinct o.sys_operator_id,sys_password_hash,sys_operator_name,sys_employee_id,sys_expired_date,sys_status from op_sys_operator o,op_sys_group_operator g where o.sys_operator_id =g.sys_operator_id";
                result = dbHelper.getFirstDocument(strSql);
            } else {
                strSql = "select o.sys_operator_id,sys_password_hash,sys_operator_name,sys_employee_id,sys_expired_date,sys_status from op_sys_operator o,op_sys_group_operator g where o.sys_operator_id =g.sys_operator_id and g.sys_group_id=?";
                pStmtValues.add(groupID);
                result = dbHelper.getFirstDocument(strSql, pStmtValues.toArray());
            }


            while (result) {
                PriviledgeOperatorVo po = new PriviledgeOperatorVo();
                String operatorID = dbHelper.getItemValue("sys_operator_id");
                po.setOperatorID(operatorID);
                //   po.setPassword(dbHelper.getItemValue("sys_password_hash"));
                po.setName(dbHelper.getItemValue("sys_operator_name"));
                po.setEmployeeID(dbHelper.getItemValue("sys_employee_id"));
                po.setExpiredDate(FrameUtil.convertDateToViewFormat(dbHelper.getItemValue("sys_expired_date")));
                po.setStatus(dbHelper.getItemValue("sys_status"));
                po.setStatusText(dbUtil.getTextByCode(dbHelper.getItemValue("sys_status"), statuses));
                groupIDs = this.getGroupsByOperatorID(operatorID);
                po.setGroupID(groupIDs);

                groupIDText = this.getGroupIDsText(groupIDs);
                po.setGroupIDText(groupIDText);
                operators.add(po);
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }

        return operators;
    }

    public int addOperator(PriviledgeOperatorVo po) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;

        ArrayList pStmtValues = new ArrayList();
        FrameUtil util = new FrameUtil();
        int result = 0;
        String strDays = "60";

        //2007-07-18  yjh  新增密码编辑日期及密码有效期
        Date date = new Date();
        SimpleDateFormat curDate = new SimpleDateFormat("yyyyMMdd");
        String strDate = curDate.format(date);


        pStmtValues.add(po.getOperatorID());
        pStmtValues.add(Encryption.biEncrypt(po.getPassword()));
        pStmtValues.add(FrameUtil.GbkToIso(po.getName()));
        pStmtValues.add(FrameUtil.GbkToIso(po.getEmployeeID()));
        pStmtValues.add(FrameUtil.convertDateToDBFormat(po.getExpiredDate()));
        pStmtValues.add(po.getStatus());

        pStmtValues.add(strDate);
        pStmtValues.add(Integer.valueOf(strDays));

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "insert into op_sys_operator(sys_operator_id,sys_password_hash,sys_operator_name,sys_employee_id,sys_expired_date,sys_status,password_edit_date,edit_past_days) values(?,?,?,?,?,?,?,?)";
            dbHelper.setAutoCommit(false);
            result = dbHelper.executeUpdate(strSql, pStmtValues.toArray());
            this.addOperatorIntoGroup(dbHelper, po);
            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);

        } catch (Exception e) {
            FramePubUtil.handleExceptionForTran(e, logger,dbHelper);
        } finally {
            FramePubUtil.finalProcessForTran(dbHelper);

        }
        return result;
    }

    public int addOperatorIntoGroup(DbHelper dbHelper, PriviledgeOperatorVo po) throws Exception {
        String strSql = null;

        ArrayList pStmtValues = new ArrayList();
        int result = 0;
        Vector groupIDs = new Vector();
        String strGroupIDs = po.getGroupID();
        new FrameUtil().getIDs(strGroupIDs, groupIDs, ",");


        try {
            for (int i = 0; i < groupIDs.size(); i++) {
                strSql = "insert into op_sys_group_operator(sys_group_id,sys_operator_id) values(?,?)";
                pStmtValues.clear();
                pStmtValues.add((String) groupIDs.get(i));
                pStmtValues.add(po.getOperatorID());
                dbHelper.executeUpdate(strSql, pStmtValues.toArray());
            }


        } catch (SQLException e) {
            dbHelper.rollbackTran();
            logger.error("错误:", e);
            throw e;
        }


        return result;

    }

    public int restoreOperator(String operatorID) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        Vector groups = new Vector();

        ArrayList pStmtValues = new ArrayList();
        int result = 0;
        pStmtValues.add(operatorID);

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "update op_sys_operator set failed_num=0 where sys_operator_id=?";
            result = dbHelper.executeUpdate(strSql, pStmtValues.toArray());

        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }



        return result;

    }

    public int modifyOperator(PriviledgeOperatorVo po) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        Vector groups = new Vector();
        String strDays = "60";

        //2007-07-18  yangjihe 新增（记录密码更新时间）

        Date date = new Date();
        SimpleDateFormat curDate = new SimpleDateFormat("yyyyMMdd");
        String strDate = curDate.format(date);



        ArrayList pStmtValues = new ArrayList();
        FrameUtil util = new FrameUtil();
        int result = 0;
        String password = po.getPassword().trim();
        if (password.length() != 0) {
            pStmtValues.add(Encryption.biEncrypt(po.getPassword()));
        }
        pStmtValues.add(FrameUtil.GbkToIso(po.getName()));
        pStmtValues.add(FrameUtil.GbkToIso(po.getEmployeeID()));
        pStmtValues.add(FrameUtil.convertDateToDBFormat(po.getExpiredDate()));
        pStmtValues.add(po.getStatus());

        pStmtValues.add(strDate);
        pStmtValues.add(Integer.valueOf(strDays));

        pStmtValues.add(po.getOperatorID());


        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            if (password.length() != 0)//密码也更改

            {
                strSql = "update op_sys_operator set sys_password_hash=?,sys_operator_name=?,sys_employee_id=?,sys_expired_date=?,sys_status=?,failed_num=0,password_edit_date=?,edit_past_days=? where sys_operator_id=?";
            } else //密码不更改

            {
                strSql = "update op_sys_operator set sys_operator_name=?,sys_employee_id=?,sys_expired_date=?,sys_status=?,failed_num=0,password_edit_date=?,edit_past_days=? where sys_operator_id=?";
            }

            dbHelper.setAutoCommit(false);
            result = dbHelper.executeUpdate(strSql, pStmtValues.toArray());
            this.deleteOperatorGroups(dbHelper, po.getOperatorID());
            this.addOperatorIntoGroup(dbHelper, po);
            /*
             strSql = "update op_sys_group_operator set sys_group_id=? where sys_operator_id=?";
             pStmtValues.clear();
             pStmtValues.add(po.getGroupID());
             pStmtValues.add(po.getOperatorID());
             result =dbHelper.executeUpdate(strSql,pStmtValues.toArray());
             */
            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);

        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }



        return result;

    }

    public String getOldPassword(String operatorID) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;

        ArrayList pStmtValue = new ArrayList();
        String oldPwd = null;
        pStmtValue.add(operatorID);

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select sys_password_hash from op_sys_operator where sys_operator_id=?";
            result = dbHelper.getFirstDocument(strSql, pStmtValue.toArray());
            if (result) {
                oldPwd = dbHelper.getItemValueWithoutTrim("sys_password_hash");
            } else {
                throw new Exception("操作员不存在");
            }

        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }

        return oldPwd;

    }

    //旧密码是否正确

    public boolean isOldPassword(PriviledgeOperatorVo po) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;

        ArrayList pStmtValues = new ArrayList();
        int result = 0;
        FrameUtil util = new FrameUtil();
        String password = po.getPassword().trim();
        String operatorID = po.getOperatorID();
        String oldPassword = po.getOldPassword();
        password = Encryption.biEncrypt(password);
        oldPassword = Encryption.biEncrypt(oldPassword);


        String dbOldPassword = this.getOldPassword(operatorID);
        if (!dbOldPassword.equals(oldPassword)) {
            return false;
        }
        return true;
    }

    //修改旧密码

    public boolean iseditPassword(PriviledgeOperatorVo po) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        String strDays = "60";


        ArrayList pStmtValues = new ArrayList();
        int result = 0;
        FrameUtil util = new FrameUtil();
        String password = po.getPassword().trim();

        //System.out.println("修改后密码:"+password);

        String operatorID = po.getOperatorID();
        String oldPassword = po.getOldPassword();
        password = Encryption.biEncrypt(password);
        oldPassword = Encryption.biEncrypt(oldPassword);

        //2007-07-18  yangjihe 新增（记录密码更新时间）

        Date date = new Date();
        SimpleDateFormat curDate = new SimpleDateFormat("yyyyMMdd");
        String strDate = curDate.format(date);


        pStmtValues.add(password);
        pStmtValues.add(strDate);
        pStmtValues.add(Integer.valueOf(strDays));
        pStmtValues.add(operatorID);

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "update op_sys_operator set sys_password_hash=?,password_edit_date=?,edit_past_days=?   where sys_operator_id=?  and sys_status='0'";
            result = dbHelper.executeUpdate(strSql, pStmtValues.toArray());
            return true;
        } catch (NamingException e) {
            logger.error("错误:", e);
            return false;
        } catch (SQLException e) {
            logger.error("错误:", e);
            return false;
        } finally {
            try {
                if (dbHelper != null) {
                    dbHelper.closeConnection();
                }
            } catch (SQLException e) {
                logger.error("Fail to close connection", e);
                return false;
            }
        }
    }

    public int modifyPassword(PriviledgeOperatorVo po) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        String strDays = "60";


        ArrayList pStmtValues = new ArrayList();
        int result = 0;
        FrameUtil util = new FrameUtil();
        String password = po.getPassword().trim();
        String operatorID = po.getOperatorID();
        String oldPassword = po.getOldPassword();
        password = Encryption.biEncrypt(password);
        oldPassword = Encryption.biEncrypt(oldPassword);

        //2007-07-18  yangjihe 新增（记录密码更新时间）


        Date date = new Date();
        SimpleDateFormat curDate = new SimpleDateFormat("yyyyMMdd");
        String strDate = curDate.format(date);

        String dbOldPassword = this.getOldPassword(operatorID);
        if (!dbOldPassword.equals(oldPassword)) {
            throw new Exception("旧密码不对");
        }

        pStmtValues.add(password);
        pStmtValues.add(strDate);
        pStmtValues.add(Integer.valueOf(strDays));
        pStmtValues.add(operatorID);


        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "update op_sys_operator set sys_password_hash=?,password_edit_date=?,edit_past_days=?  where sys_operator_id=?  and sys_status='0'";
            result = dbHelper.executeUpdate(strSql, pStmtValues.toArray());
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }
        return result;

    }

    public int deleteOperators(Vector operatorIDs) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;

        ArrayList pStmtValues = new ArrayList();
        int result = 0;
        String operatorID = null;

        try {

            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            dbHelper.setAutoCommit(false);
            for (int i = 0; i < operatorIDs.size(); i++) {
                operatorID = (String) operatorIDs.get(i);
                pStmtValues.clear();
                pStmtValues.add(operatorID);
                strSql = "delete from op_sys_operator where sys_operator_id=?";
                dbHelper.executeUpdate(strSql, pStmtValues.toArray());

                strSql = "delete from op_sys_group_operator where sys_operator_id=?";
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

    public int deleteOperatorGroups(DbHelper dbHelper, String operatorID) throws Exception {
        String strSql = null;
        ArrayList pStmtValues = new ArrayList();
        int result = 0;

        try {
            pStmtValues.clear();
            pStmtValues.add(operatorID);
            strSql = "delete from op_sys_group_operator where sys_operator_id=?";
            result += dbHelper.executeUpdate(strSql, pStmtValues.toArray());

        } catch (SQLException e) {
            logger.error("错误:", e);
            throw e;
        }


        return result;

    }

    public int deleteOperatorInGroup(DbHelper dbHelper, String groupID) throws Exception {
        String strSql = null;

        ArrayList pStmtValues = new ArrayList();
        int result = 0;

        try {
            pStmtValues.clear();
            pStmtValues.add(groupID);
            strSql = "delete from op_sys_group_operator where sys_group_id=?";
            result += dbHelper.executeUpdate(strSql, pStmtValues.toArray());

        } catch (SQLException e) {
            logger.error("错误:", e);
            throw e;
        }


        return result;

    }

    public int deleteModulesInGroup(DbHelper dbHelper, String groupID) throws Exception {
        String strSql = null;

        ArrayList pStmtValues = new ArrayList();
        int result = 0;

        try {
            pStmtValues.clear();
            pStmtValues.add(groupID);
            strSql = "delete from op_sys_group_module where sys_group_id=?";
            result += dbHelper.executeUpdate(strSql, pStmtValues.toArray());

        } catch (SQLException e) {
            logger.error("错误:", e);
            throw e;
        }


        return result;

    }

    public boolean isExistingForOperator(String operatorID) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;

        ArrayList pStmtValue = new ArrayList();
        pStmtValue.add(operatorID);

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select sys_operator_id from op_sys_operator where sys_operator_id=?";
            result = dbHelper.getFirstDocument(strSql, pStmtValue.toArray());

        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }

        return result;


    }
}
