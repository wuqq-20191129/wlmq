/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.ecpmcs.dao.impl;

import com.goldsign.csfrm.dao.impl.BaseDao;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.ecpmcs.dao.IEmployeeCardDao;
import com.goldsign.ecpmcs.env.AppConstant;
import com.goldsign.ecpmcs.util.PhotoSearcher;
import com.goldsign.ecpmcs.util.PubUtil;
import com.goldsign.ecpmcs.vo.AnalyzeVo;
import com.goldsign.ecpmcs.vo.EmployeeCardVo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * 员工卡查询
 * @author lind
 */
public class EmployeeCardDao extends BaseDao implements IEmployeeCardDao {
    
    private static Logger logger = Logger.getLogger(EmployeeCardDao.class.getName());

    @Override
    public List<EmployeeCardVo> getEmployeeCardVos(EmployeeCardVo employeeCardVo) {
        List<EmployeeCardVo> EmployeeCardVoRets = new ArrayList<EmployeeCardVo>();
        boolean result = false;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            
            String sqlStr = "select * from "+AppConstant.DATABASE_USER_TK+"W_IC_ET_ISSUE where 1=1 ";
            
            if(!StringUtil.isEmpty(employeeCardVo.getUseState())){
                sqlStr += " and use_state='"+ employeeCardVo.getUseState() +"'";
            }
            
//            String makeTime = "make_time";
//            String makeOper = "make_oper";
//            if(!employeeCardVo.getUseState().equals(AppConstant.ET_STATE_ISSUE)){
//                makeTime = "return_time";
//                makeOper = "return_oper";
//            }
            
            sqlStr += setTimeWhere(employeeCardVo);
            
            if(!StringUtil.isEmpty(employeeCardVo.getEmployeeId())){
                sqlStr += " and employee_id like '%" + employeeCardVo.getEmployeeId() + "%'";
            }
            if(!StringUtil.isEmpty(employeeCardVo.getEmployeeName())){
                sqlStr += " and employee_name like '%" + employeeCardVo.getEmployeeName() + "%'";
            }

            logger.info("sqlStr:" + sqlStr);
            
            result = dbHelper.getFirstDocument(sqlStr);
            while (result) {
                
                //EmployeeCardVo makeCardVoRet = setRecordValue(dbHelper, makeTime, makeOper);
                EmployeeCardVo makeCardVoRet = setRecordValue(dbHelper);
                EmployeeCardVoRets.add(makeCardVoRet);
                
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return EmployeeCardVoRets;
    }
    
    
    private String setTimeWhere(EmployeeCardVo employeeCardVo) {
        
            String bTimeStr = "";
            String eTimeStr = "";
            
            if(employeeCardVo.getUseState().equals(AppConstant.ET_STATE_ISSUE)){
                if(!StringUtil.isEmpty(employeeCardVo.getBeginTime())){
                    bTimeStr=" and make_time >= to_date('" + employeeCardVo.getBeginTime() + " 00:00:00','yyyyMMdd hh24:mi:ss')";
                }
                if(!StringUtil.isEmpty(employeeCardVo.getEndTime())){
                    eTimeStr= " and make_time <= to_date('" + employeeCardVo.getEndTime() + " 23:59:59','yyyyMMdd hh24:mi:ss')";
                }
            } else if(employeeCardVo.getUseState().equals(AppConstant.ET_STATE_RETURN)){
                if(!StringUtil.isEmpty(employeeCardVo.getBeginTime())){
                    bTimeStr=" and return_time >= to_date('" + employeeCardVo.getBeginTime() + " 00:00:00','yyyyMMdd hh24:mi:ss')";
                }
                if(!StringUtil.isEmpty(employeeCardVo.getEndTime())){
                    eTimeStr= " and return_time <= to_date('" + employeeCardVo.getEndTime() + " 23:59:59','yyyyMMdd hh24:mi:ss')";
                }
            }else{
                if(!StringUtil.isEmpty(employeeCardVo.getBeginTime())){
                    bTimeStr=" and (return_time >= to_date('" + employeeCardVo.getBeginTime() + " 00:00:00','yyyyMMdd hh24:mi:ss')"
                        + " or make_time >= to_date('" + employeeCardVo.getBeginTime() + " 00:00:00','yyyyMMdd hh24:mi:ss'))";
                }
                if(!StringUtil.isEmpty(employeeCardVo.getEndTime())){
                    eTimeStr= " and (return_time <= to_date('" + employeeCardVo.getEndTime() + " 23:59:59','yyyyMMdd hh24:mi:ss')"
                        + " or make_time <= to_date('" + employeeCardVo.getEndTime() + " 23:59:59','yyyyMMdd hh24:mi:ss'))";
                }
            }
            
            return bTimeStr + eTimeStr;
    }
    
    
    /**
     * 取实例值
     * @param makeCardVoRet
     * @param dbHelper 
     */
    private EmployeeCardVo setRecordValue(DbHelper dbHelper) throws SQLException {
        
            EmployeeCardVo makeCardVoRet = new EmployeeCardVo();
            makeCardVoRet.setEmployeeId(dbHelper.getItemTrueValue("employee_id"));
            makeCardVoRet.setEmployeeName(dbHelper.getItemTrueValue("employee_name"));
            makeCardVoRet.setGender(dbHelper.getItemTrueValue("gender"));
            makeCardVoRet.setLogicId(dbHelper.getItemTrueValue("logical_id"));
            makeCardVoRet.setMakeOper(dbHelper.getItemTrueValue("make_oper"));
            makeCardVoRet.setMakeTime(dbHelper.getItemValue("make_time"));
            makeCardVoRet.setPhyId(dbHelper.getItemValue("phy_id"));
            makeCardVoRet.setPrintId(dbHelper.getItemValue("print_id"));
            makeCardVoRet.setUseState(dbHelper.getItemValue("use_state"));
            makeCardVoRet.setReturnOper(dbHelper.getItemValue("return_oper"));
            makeCardVoRet.setReturnTime(dbHelper.getItemValue("return_time"));
            makeCardVoRet.setEmployeeDepartment(dbHelper.getItemTrueValue("employee_department"));
            makeCardVoRet.setEmployeePositions(dbHelper.getItemTrueValue("employee_position"));
            makeCardVoRet.setEmployeeClass(dbHelper.getItemValue("employee_class"));
            makeCardVoRet.setEmployeeDepartmentTxt(
                PubUtil.getMapString(AppConstant.EMPLOYEE_DEPARTMENT,dbHelper.getItemTrueValue("employee_department")));
            makeCardVoRet.setEmployeePositionsTxt(
                PubUtil.getMapString(AppConstant.EMPLOYEE_POSITION,dbHelper.getItemTrueValue("employee_position")));
            makeCardVoRet.setEmployeeClassTxt(
                PubUtil.getMapString(AppConstant.EMPLOYEE_CLASS,dbHelper.getItemTrueValue("employee_class")));
            String returnDir = "";
            //搜索相片路径
            returnDir = PhotoSearcher.fileJPG(AppConstant.CERTIFICATE_TYPE_EMPLOYEE, dbHelper.getItemTrueValue("employee_id"));
            makeCardVoRet.setImgDir(returnDir);
                
            return makeCardVoRet;
    }

    @Override
    public boolean getEmployeeCardVo(AnalyzeVo analyzeVo) {
        boolean result = false;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            
            String sqlStr = "select * from "+AppConstant.DATABASE_USER_TK+"W_IC_ET_ISSUE where use_state = '" + AppConstant.ET_STATE_ISSUE + "' ";
            
            if(!StringUtil.isEmpty(analyzeVo.getCertificateCode())){
                sqlStr += " and employee_id = '" + analyzeVo.getCertificateCode().trim() + "'";
            }
            if(!StringUtil.isEmpty(analyzeVo.getcLogicalID())){
                sqlStr += " and logical_id = '" + analyzeVo.getcLogicalID().trim() + "'";
            }

            logger.info("sqlStr:" + sqlStr);
            
            result = dbHelper.getFirstDocument(sqlStr);
            if (result) {
                analyzeVo.setEmployeeDepartment(dbHelper.getItemTrueValue("employee_department"));
                analyzeVo.setEmployeePositions(dbHelper.getItemTrueValue("employee_position"));
                analyzeVo.setEmployeeClass(dbHelper.getItemTrueValue("employee_class"));
                analyzeVo.setcLogicalID(dbHelper.getItemTrueValue("logical_id"));
            }
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return result;
    }
    
}
