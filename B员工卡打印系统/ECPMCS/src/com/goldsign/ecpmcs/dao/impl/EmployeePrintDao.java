/*
 * 文件名：EmployeePrintDao
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.ecpmcs.dao.impl;

import com.goldsign.csfrm.dao.impl.BaseDao;
import com.goldsign.csfrm.util.DateHelper;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.ecpmcs.dao.IEmployeePrintDao;
import com.goldsign.ecpmcs.env.AppConstant;
import com.goldsign.ecpmcs.util.PubUtil;
import com.goldsign.ecpmcs.vo.SignCardPrintVo;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;


/*
 * 〈员工卡打印记录查询〉
 * @author     lindaquan
 * @version    V1.1
 * @createTime 2014-12-18
 */

public class EmployeePrintDao extends BaseDao implements IEmployeePrintDao{
    
    private static Logger logger = Logger.getLogger(EmployeePrintDao.class.getName());

    /**
     * 打印记录查询
     * @param signCardPrintParam
     * @return 
     */
    @Override
    public List<SignCardPrintVo> getPrintList(SignCardPrintVo signCardPrintParam) {
        
        List<SignCardPrintVo> printVoRets = new ArrayList<SignCardPrintVo>();
        boolean result = false;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            String sqlStr = "select name, gender, identity_id, identity_type, card_type, photo_name, print_oper, print_time, issue_time,"
                    + " department, position, logical_id, employee_class from "+AppConstant.DATABASE_USER_TK+"w_ic_ecp_print_list where 1=1";
            
            sqlStr += sqlWhere(signCardPrintParam);
            sqlStr += " order by print_time desc";
            logger.info("sqlStr:" + sqlStr);
            
            result = dbHelper.getFirstDocument(sqlStr);
            while (result) {
                
                SignCardPrintVo vo = new SignCardPrintVo();
                vo.setPhotoName(dbHelper.getItemTrueValue("photo_name"));
                vo.setName(dbHelper.getItemTrueValue("name"));
                vo.setLogicalId(dbHelper.getItemTrueValue("logical_id"));
                vo.setPrintTime(DateHelper.dateToStr19yyyy_MM_dd_HH_mm_ss(dbHelper.getItemTimestampValue("print_time")));
                vo.setIdentityId(dbHelper.getItemTrueValue("identity_id"));
                vo.setPrintOper(dbHelper.getItemTrueValue("print_oper"));
                vo.setIssueTime(DateHelper.dateToStr19yyyy_MM_dd_HH_mm_ss(dbHelper.getItemTimestampValue("issue_time")));
                String s1 = dbHelper.getItemTrueValue("card_type");
                String s2 = PubUtil.getMapString(AppConstant.TICKET_TYPE, dbHelper.getItemTrueValue("card_type"));
                vo.setCardType(dbHelper.getItemTrueValue("card_type"));
                vo.setCardTypeTxt(PubUtil.getMapString(AppConstant.TICKET_TYPE, dbHelper.getItemTrueValue("card_type")));//(dbHelper.getItemTrueValue("card_type"));
                vo.setGender(dbHelper.getItemTrueValue("gender"));
                vo.setGenderTxt(PubUtil.getMapString(AppConstant.CERTIFICATE_SEX, dbHelper.getItemTrueValue("gender")));//
                vo.setIdentityType(dbHelper.getItemTrueValue("identity_type"));
                vo.setIdentityTypeTxt(PubUtil.getMapString(AppConstant.CERTIFICATE_TYPE, dbHelper.getItemTrueValue("identity_type")));
                vo.setDepartment(dbHelper.getItemTrueValue("department"));
                vo.setDepartmentTxt(PubUtil.getMapString(AppConstant.EMPLOYEE_DEPARTMENT, dbHelper.getItemTrueValue("department")));//
                vo.setPosition(dbHelper.getItemTrueValue("position"));
                vo.setPositionTxt(PubUtil.getMapString(AppConstant.EMPLOYEE_POSITION, dbHelper.getItemTrueValue("position")));//();
                vo.setEmployeeClass(dbHelper.getItemTrueValue("employee_class"));
                vo.setEmployeeClassTxt(PubUtil.getMapString(AppConstant.EMPLOYEE_CLASS, dbHelper.getItemTrueValue("employee_class")));
                
                printVoRets.add(vo);
                
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);

        }

        return printVoRets;
    }

    /**
     * 分类统计
     * @param signCardPrintParam
     * @return 
     */
    @Override
    public List<SignCardPrintVo> countPrintList(SignCardPrintVo signCardPrintParam) {
        List<SignCardPrintVo> printVoRets = new ArrayList<SignCardPrintVo>();
        boolean result = false;
        DbHelper dbHelper = null;
        int total = 0;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            String sqlStr = "select card_type,count(1) count from "+AppConstant.DATABASE_USER_TK+"w_ic_ecp_print_list where 1=1 ";

            sqlStr += sqlWhere(signCardPrintParam);
            sqlStr += " group by card_type";

            logger.info("sqlStr:" + sqlStr);
            
            result = dbHelper.getFirstDocument(sqlStr);
            while (result) {
                
                SignCardPrintVo vo = new SignCardPrintVo();
                vo.setRownum(dbHelper.getItemTrueValue("count"));
                vo.setCardTypeTxt(dbHelper.getItemTrueValue("card_type"));
                printVoRets.add(vo);
                total += dbHelper.getItemIntValue("count");
                
                result = dbHelper.getNextDocument();
            }
            
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        
        SignCardPrintVo vo = new SignCardPrintVo();
        vo.setRownum(String.valueOf(total));
        printVoRets.add(vo);

        return printVoRets;
    }
    
    private String sqlWhere(SignCardPrintVo signCardPrintParam){
        String sqlStr = "";
        if(!StringUtil.isEmpty(signCardPrintParam.getBeginTime())){
            sqlStr += " and to_char(print_time,'yyyyMMdd') >= '" + signCardPrintParam.getBeginTime().trim() + "'";
        }
        if(!StringUtil.isEmpty(signCardPrintParam.getEndTime())){
            sqlStr += " and to_char(print_time,'yyyyMMdd') <= '" + signCardPrintParam.getEndTime().trim() + "'";
        }
        if(!StringUtil.isEmpty(signCardPrintParam.getPrintOper())){
            sqlStr += " and print_oper like '%" + signCardPrintParam.getPrintOper().trim() + "%'";
        }
        if(!StringUtil.isEmpty(signCardPrintParam.getName())){
            sqlStr += " and name like '%" + signCardPrintParam.getName().trim() + "%'";
        }
        if(!StringUtil.isEmpty(signCardPrintParam.getIdentityId())){
            sqlStr += " and identity_id like '%" + signCardPrintParam.getIdentityId().trim() + "%'";
        }
        if(!StringUtil.isEmpty(signCardPrintParam.getIdentityType())){
            sqlStr += " and trim(identity_type) = '" + signCardPrintParam.getIdentityType().trim() + "'";
        }
        if(!StringUtil.isEmpty(signCardPrintParam.getCardType())){
            sqlStr += " and trim(card_type) = '" + signCardPrintParam.getCardType().trim() + "'";
        }
        if(!StringUtil.isEmpty(signCardPrintParam.getDepartment())){
            sqlStr += " and trim(department) = '" + signCardPrintParam.getDepartment().trim() + "'";
        }
        if(!StringUtil.isEmpty(signCardPrintParam.getPosition())){
            sqlStr += " and trim(position) = '" + signCardPrintParam.getPosition().trim() + "'";
        }
        if(!StringUtil.isEmpty(signCardPrintParam.getRownum())){
            sqlStr += " and rownum<= "+ signCardPrintParam.getRownum();
        }
        return sqlStr;
        
    }


    /**
     * 插入打印记录
     * @param vo
     * @return 
     */
    @Override
    public Boolean insertPrint(SignCardPrintVo vo) {
        boolean result = false;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            
            dbHelper.setAutoCommit(false);
            
            Object[] values = {
                //id
                StringUtil.nullToString(vo.getName()), //name
                StringUtil.nullToString(vo.getGender()),  //gender
                StringUtil.nullToString(vo.getIdentityId()), //identity_id
                StringUtil.nullToString(vo.getIdentityType()), //identity_type
                StringUtil.nullToString(vo.getCardType()), //card_type
                StringUtil.nullToString(vo.getLogicalId()),//logical_id  **
                StringUtil.nullToString(vo.getPhotoName()),// photo_name
                StringUtil.nullToString(vo.getPrintOper()), // print_oper
                //print_time
                StringUtil.nullToString(vo.getIssueTime()),// issue_time
                StringUtil.nullToString(vo.getRemark()),//remark
                StringUtil.nullToString(vo.getDepartment()),//department
                StringUtil.nullToString(vo.getPosition()),//position
                StringUtil.nullToString(vo.getEmployeeClass())//employee_class
            };
            Object[] valueQuery = {
                StringUtil.nullToString(vo.getIdentityId()),
                StringUtil.nullToString(vo.getLogicalId())
            };
            //1.先检查是否已经插入记录（通过证件号--IDENTITY_ID）
            String queryStr = "select count(*) as total from " + AppConstant.DATABASE_USER_TK 
                    + "w_ic_ecp_print_list o where o.IDENTITY_ID = ? and o.LOGICAL_ID = ? " ;
            logger.info("queryStr:" + queryStr);
            boolean queryFlag = dbHelper.getFirstDocument(queryStr,valueQuery);
            int total = 0;
            if(queryFlag){
                total = Integer.valueOf(dbHelper.getItemTrueValue("total"));
            }
            String sqlStr = "";
            if(total>0){//2.若已插入记录，则更新打印时间PRINT_TIME、更新操作员PRINT_OPER
                Object[] updateQuery = {
                    //print_time
                    StringUtil.nullToString(vo.getPrintOper()),
                    StringUtil.nullToString(vo.getIssueTime()),// issue_time
                    StringUtil.nullToString(vo.getRemark()),//remark
                    StringUtil.nullToString(vo.getDepartment()),//department
                    StringUtil.nullToString(vo.getPosition()),//position
                    StringUtil.nullToString(vo.getEmployeeClass()),//employee_class
                    StringUtil.nullToString(vo.getIdentityId()),
                    StringUtil.nullToString(vo.getLogicalId())
                };
                sqlStr = "update " + AppConstant.DATABASE_USER_TK + "w_ic_ecp_print_list o  "
                        + " set o.PRINT_TIME = sysdate, o.PRINT_OPER = ?, issue_time = ?, remark = ?, "
                        + " department = ?, position = ?,  employee_class = ? "
                        + " where o.IDENTITY_ID = ? and o.LOGICAL_ID = ? ";
                logger.info("queryStr:" + sqlStr);
                result = dbHelper.executeUpdate(sqlStr, updateQuery)>0;
            }else{//3.若第一次打印，则添加新的打印记录
                sqlStr = "insert into "+AppConstant.DATABASE_USER_TK+"w_ic_ecp_print_list (id, name, gender, identity_id, "
                    + "identity_type, card_type, logical_id,  photo_name, print_oper, print_time, issue_time, remark, department, position,  employee_class)"
                    + " values ("+AppConstant.DATABASE_USER_TK+"w_seq_w_ic_ecp_print_list.nextval, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, to_date(?,'yyyymmddhh24miss'), ?, ?, ?, ?)";
                logger.info("queryStr:" + sqlStr);
                result = dbHelper.executeUpdate(sqlStr, values)>0;
            }
            
            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);
            
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
            return false;
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return result;
    }
    
    /**
     * 通过逻辑卡号或该员工的等级
     * @param logicNo
     * @return 
     */
    @Override
    public String getEmployeeClass(String logicNo) {
        boolean result = false;
        String employeeClass = "";
        DbHelper dbHelper = null;
        
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            
            dbHelper.setAutoCommit(false);
            
            Object[] values = {logicNo};
            
            //
            String sqlStr = "select employee_class from "+AppConstant.DATABASE_USER_TK+"W_IC_ET_ISSUE  where logical_id = ? ";

            logger.info("sqlStr:" + sqlStr);
            result = dbHelper.executeUpdate(sqlStr, values)>0;
            while (result) {
                employeeClass = dbHelper.getItemTrueValue("employee_class");
            }
            
        } catch (Exception e) {
            PubUtil.handleDBNotConnected(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return employeeClass;
    }

}
