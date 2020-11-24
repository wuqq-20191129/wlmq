/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.etmcs.dao.impl;

import com.goldsign.csfrm.dao.impl.BaseDao;
import com.goldsign.csfrm.env.BaseConstant;
import com.goldsign.csfrm.util.DateHelper;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.etmcs.dao.IMakeCardDao;
import com.goldsign.etmcs.env.AppConstant;
import com.goldsign.etmcs.env.ConfigConstant;
import com.goldsign.etmcs.util.ConfigUtil;
import com.goldsign.etmcs.util.PubUtil;
import com.goldsign.etmcs.vo.MakeCardParam;
import com.goldsign.etmcs.vo.MakeCardVo;
import com.goldsign.etmcs.vo.PubFlagVo;
import com.goldsign.lib.db.util.DbHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 * 员工制卡DAO
 * @author lenovo
 */
public class MakeCardDao extends BaseDao implements IMakeCardDao{

    private static Logger logger = Logger.getLogger(MakeCardDao.class.getName());
    
    /*
     * 查询员工发卡记录
     */
    @Override
    public List<MakeCardVo> getMakeCards(MakeCardParam makeCardParam) {
        
        List<MakeCardVo> makeCardVoRets = new ArrayList<MakeCardVo>();
        boolean result = false;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            
            String sqlStr = "select * from W_ACC_TK.W_IC_ET_ISSUE o where 1=1 ";
            
            if(!StringUtil.isEmpty(makeCardParam.getType())){
                sqlStr += " and o.use_state='"+ makeCardParam.getType() +"'";
            }
            
            String makeTime = "make_time";
            String makeOper = "make_oper";
            if(makeCardParam.getType().equals(AppConstant.ET_STATE_RETURN)){
                makeTime = "return_time";
                makeOper = "return_oper";
            }
            
            sqlStr += setTimeWhere(makeCardParam);
            
            //查询同一员工号重复发两次及以上的员工卡
            if(makeCardParam.getType().equals(AppConstant.ET_STATE_REPEAT)){
                sqlStr = "select o.* from " 
                            +  " (select count(*) as num,t.employee_id from W_ACC_TK.W_IC_ET_ISSUE t " 
                            +  " group by t.employee_id)  s," 
                            +  " W_ACC_TK.W_IC_ET_ISSUE o "
                            +  " where s.num > 1 and o.employee_id = s.employee_id ";
            }
            
            if(!StringUtil.isEmpty(makeCardParam.getEmployeeId())){
                sqlStr += " and o.employee_id like '%" + makeCardParam.getEmployeeId() + "%'";
            }
            if(!StringUtil.isEmpty(makeCardParam.getEmployeeName())){
                sqlStr += " and o.employee_name like '%" + makeCardParam.getEmployeeName() + "%'";
            }
            
            
            if(makeCardParam.getType().equals(AppConstant.ET_STATE_REPEAT)){
                sqlStr += "order by o.employee_id,o.MAKE_TIME desc";
            }else{
                sqlStr += "order by o.MAKE_TIME desc";
            }
            
            
            logger.info("sqlStr:" + sqlStr);
            
            result = dbHelper.getFirstDocument(sqlStr);
            while (result) {
                
                MakeCardVo makeCardVoRet = setRecordValue(dbHelper, makeTime, makeOper);
                makeCardVoRets.add(makeCardVoRet);
                
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return makeCardVoRets;
    }
    
     /*
     * 按工号查询员工发卡信息
     */
    @Override
    public MakeCardVo getMakeCardsByEmployeeId(MakeCardVo vo) {
        
        MakeCardVo makeCardVoRet = new MakeCardVo();
        boolean result = false;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            String useState = "";
            useState = (vo.getUseState()!=null && !vo.getUseState().isEmpty())?(" and use_state='"+ vo.getUseState()+"'"):"";
            String sqlStr = "select * from W_ACC_TK.W_IC_ET_ISSUE where 1=1 "
                    + useState 
                    +" and trim(employee_id) = '" + StringUtil.nullToString(vo.getEmployeeId()).trim() + "'"
                    +" and trim(logical_id) = '" + StringUtil.nullToString(vo.getLogicId()).trim() + "'";

            logger.info("sqlStr:" + sqlStr);
            
            result = dbHelper.getFirstDocument(sqlStr);
            while (result) {
                String makeTime = "make_time";
                String makeOper = "make_oper";
                makeCardVoRet = setRecordValue(dbHelper, makeTime, makeOper);
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
            return null;
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return makeCardVoRet;
    }
    //检验数据库是否存在员工信息"ACC_TK"."IC_ET_ISSUE"
    @Override
    public MakeCardVo getEmployeeInfo(MakeCardVo vo) {
        
        MakeCardVo reVo = null;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            
            String sqlStr = "select * from W_ACC_TK.W_IC_ET_ISSUE where use_state='"+ AppConstant.ET_STATE_ISSUE +"' ";
            if(!StringUtil.isEmpty(vo.getEmployeeId())){
                sqlStr += " and trim(employee_id) = '" + vo.getEmployeeId().trim()+"'";
            }

            logger.info("sqlStr:" + sqlStr);
            
            if(dbHelper.getFirstDocument(sqlStr)){
                reVo = new MakeCardVo();
                reVo.setEmployeeId(dbHelper.getItemValue("EMPLOYEE_ID"));
                reVo.setEmployeeName(dbHelper.getItemValue("EMPLOYEE_NAME"));
                return reVo;
            }
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
            return reVo;
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return reVo;
    }
    //检验数据库是否存在员工信息"ACC_TK"."IC_ET_ISSUE"
    @Override
    public int isExistsMakeCard(MakeCardVo vo) {
        
        int result = AppConstant.DAO_RETURN_FALSE;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            
            String sqlStr = "select * from W_ACC_TK.W_IC_ET_ISSUE where use_state='"+ AppConstant.ET_STATE_ISSUE +"' ";
            if(!StringUtil.isEmpty(vo.getEmployeeId())){
                sqlStr += " and trim(employee_id) = '" + vo.getEmployeeId().trim()+"'";
            }
            if(!StringUtil.isEmpty(vo.getLogicId())){
                sqlStr += " and trim(logical_id) = '" + vo.getLogicId().trim()+"'";
            }

            logger.info("sqlStr:" + sqlStr);
            
            if(dbHelper.getFirstDocument(sqlStr)){
                result = AppConstant.DAO_RETURN_TRUE;
            }
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
            result = AppConstant.DAO_RETURN_ERROR;
            return result;
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return result;
    }

    /*
     * 员工发卡
     */
    @Override
    public Boolean writeMakeCard(MakeCardVo vo) {
        boolean result = false;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            
            dbHelper.setAutoCommit(false);
            
            //单位，职务，级别
            setDepartment(dbHelper, vo);
            setPosition(dbHelper, vo);
            setClass(dbHelper, vo);
            
            Object[] values = {
                                    StringUtil.nullToString(vo.getEmployeeDepartment()),
                                    StringUtil.nullToString(vo.getEmployeePositions()),  
                                    StringUtil.nullToString(vo.getEmployeeName()), 
                                    StringUtil.nullToString(vo.getGender()), 
                                    StringUtil.nullToString(vo.getPhyId()), 
                                    StringUtil.nullToString(vo.getEmployeeClass()),//添加员工级别
                                    StringUtil.nullToString(vo.getPrintId()), 
                                    StringUtil.nullToString(vo.getMakeOper()), 
                                    StringUtil.nullToString(vo.getEmployeeId()), 
                                    StringUtil.nullToString(vo.getLogicId())
                                    
                               };
            
            //当数据库存在员工信息，并为退卡状态时use_state='1'，更新该条记录
            String sqlStr = "update W_ACC_TK.W_IC_ET_ISSUE set employee_department =?, employee_position =?,"
                    + " use_state='"+ AppConstant.ET_STATE_ISSUE +"',employee_name=?, gender=?, phy_id=?, employee_class=?, "
                    + " print_id=?, make_oper=?, make_time=sysdate where use_state='" 
                    + AppConstant.ET_STATE_RETURN +"' and employee_id =?  and logical_id=? ";

            logger.info("sqlStr:" + sqlStr);
            
            result = dbHelper.executeUpdate(sqlStr, values)>0;
            
            //如果更新失败，为新发卡，则插入员工信息
            if(!result){
                sqlStr = "insert INTO W_ACC_TK.W_IC_ET_ISSUE(employee_department, employee_position, employee_name,"
                        + " gender, phy_id, employee_class, print_id, make_oper,employee_id, logical_id, make_time, "
                        + "return_oper, return_time, use_state, remark) VALUES (?, ?, ?,"
                        + " ?, ?, ?, ?, ?, ?, ?, sysdate, '', '', '"+ AppConstant.ET_STATE_ISSUE +"', '')";
                
                logger.info("sqlStr:" + sqlStr);
                result = dbHelper.executeUpdate(sqlStr, values)>0;
            }
            
            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);
            
            //当手动输入单位或职务或级别时重新加载单位，职务，级别下拉数据
            if(!StringUtil.isEmpty(vo.getEmployeeDepartmentTxt()) 
                    || !StringUtil.isEmpty(vo.getEmployeePositionsTxt())
                    || !StringUtil.isEmpty(vo.getEmployeeClassTxt())){
                updateDepartPosition();
                
            }
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
            return false;
        } finally {
            PubUtil.finalProcess(dbHelper);

        }
        return result;
    }

    /*
     * 员工退卡
     */
    @Override
    public Boolean writeReturnCard(MakeCardVo vo) {
        boolean result = false;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            
            dbHelper.setAutoCommit(false);
            
            Object[] values = { vo.getReturnOper(), vo.getEmployeeId(), vo.getLogicId()};
            
            //当数据库存在员工信息，并为发卡状态时use_state='0'，更新该条记录
            String sqlStr = "update W_ACC_TK.W_IC_ET_ISSUE set use_state='"+ AppConstant.ET_STATE_RETURN 
                    +"', return_oper=?, return_time=sysdate where use_state='"+ AppConstant.ET_STATE_ISSUE +"' and employee_id =?  and logical_id=? ";

            logger.info("sqlStr:" + sqlStr);
            
            result = dbHelper.executeUpdate(sqlStr, values)>0;
            
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

    /*
     * 员工发卡，退卡报表统计查询
     */
    @Override
    public List<Map<String, String>> getMakeCardsCount(MakeCardParam makeCardParam) {
         List<Map<String, String>> makeCardVoRets = new ArrayList<Map<String, String>>();
        boolean result = false;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            
            String sqlStr = "select COUNT(1) count,decode(use_state," 
                            +AppConstant.ET_STATE_RETURN+ ",'" 
                            +AppConstant.ET_STATE_RETURN_NAME+ "','" 
                            +AppConstant.ET_STATE_ISSUE+ "','" 
                            +AppConstant.ET_STATE_ISSUE_NAME+ "','')use_state_desc,use_state from W_ACC_TK.W_IC_ET_ISSUE o where 1=1 ";
            
            if(!StringUtil.isEmpty(makeCardParam.getType())){
                sqlStr += " and use_state='"+ makeCardParam.getType() +"'";
            }
            
            sqlStr += setTimeWhere(makeCardParam);

            sqlStr += "GROUP BY use_state";

            logger.info("sqlStr:" + sqlStr);
            
            result = dbHelper.getFirstDocument(sqlStr);
            while (result) {
                Map<String,String> map =new HashMap<String,String>();
                
                map.put("useState", dbHelper.getItemTrueValue("use_state"));
                map.put("useStateDesc", dbHelper.getItemTrueValue("use_state_desc"));
                map.put("count", dbHelper.getItemTrueValue("count"));
                
                makeCardVoRets.add(map);
                
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return makeCardVoRets;
    }
    
    /*
     * 将读取文件信息插入数据库
     */
    public Boolean writeMakeCardForFile(Object[] values) {
        boolean result = false;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            
            dbHelper.setAutoCommit(false);
            
            //单位，职务,级别
            MakeCardVo vo = new MakeCardVo();
            vo.setEmployeeDepartmentTxt(String.valueOf(values[0]));
            vo.setEmployeePositionsTxt(String.valueOf(values[1]));
            vo.setEmployeeClassTxt(String.valueOf(values[2]));
            
            setDepartment(dbHelper, vo);
            setPosition(dbHelper, vo);
            setClass(dbHelper, vo);
            
            values[0] = vo.getEmployeeDepartment();
            values[1] = vo.getEmployeePositions();
            values[2] = vo.getEmployeeClass();
            
            //当数据库存在员工信息，更新该条记录
            String sqlStr = "update W_ACC_TK.W_IC_ET_ISSUE set employee_department =?, employee_position =?, employee_class =?, employee_name=?, gender=?, phy_id=?, print_id=?, make_oper=?, "
                    + "make_time=to_date(?,'yyyy-MM-dd hh24:mi:ss'),return_oper=?,return_time=to_date(?,'yyyy-MM-dd hh24:mi:ss'), use_state=?,remark=? where employee_id =? and logical_id=? ";

            logger.info("将读取文件信息插入数据库sqlStr:" + sqlStr);
            
            result = dbHelper.executeUpdate(sqlStr, values)>0;
            
            //如果更新失败，为新发卡，则插入员工信息
            if(!result){
                sqlStr = "insert INTO W_ACC_TK.W_IC_ET_ISSUE(employee_department, employee_position, employee_class, employee_name, gender, phy_id, print_id, make_oper, make_time, return_oper, return_time, "
                        + "use_state, remark ,employee_id, logical_id) VALUES (?, ?, ?, ?, ?, ?, to_date(?,'yyyy-MM-dd hh24:mi:ss'), ?, to_date(?,'yyyy-MM-dd hh24:mi:ss'), ?, ?, ?)";
                
                logger.info("将读取文件信息插入数据库sqlStr:" + sqlStr);
                result = dbHelper.executeUpdate(sqlStr, values)>0;
            }
            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);
            result = true;
            
            //当手动输入单位或职务时重新加载单位，职务下拉数据
            if(!StringUtil.isEmpty(vo.getEmployeeDepartmentTxt()) 
                    || !StringUtil.isEmpty(vo.getEmployeePositionsTxt())
                    || !StringUtil.isEmpty(vo.getEmployeeClassTxt())){
                updateDepartPosition();
            }
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
            return false;
        } finally {
            PubUtil.finalProcess(dbHelper);

        }
        return result;
    }

    /*
     * 写入本地文件
     * @param vo
     * @return file
     */
    @Override
    public File writeLocalFile(MakeCardVo vo) throws IOException {
        String dir = getMakeCardDir();//保存路径
        File parentFile = new File(dir);
        if(!parentFile.isDirectory()){
            parentFile.mkdirs();
        }
        File file = new File(dir+"/"+vo.getEmployeeId()+AppConstant.MAKE_CARD_TO_FILE_SUFFIX);
        if(!file.exists()){
            file.createNewFile();
        }
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(new FileWriter(file,true));
            String makeCardStr = fmtMakeCardToFileStr(vo, AppConstant.VER_SIGN);
            pw.println(makeCardStr);
            pw.flush();;
        }catch(Exception e) {
            logger.error(e);
        }finally {
            if(null != pw){
                pw.close();
            }
        }
        return file;
    }

    /**
     * 取服务器员工信息文件,并将每条记录插入数据库
     * 
     * @param vo
     * @return
     * @throws IOException 
     */
    @Override
    public Boolean readLocalFile(File file) throws IOException{
        
        Boolean result = false;
        if (!file.exists()) {
            return result;
        }
        
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while(line != null){
                String[] makeCard = line.split(AppConstant.SEP_VER_SIGN,-1);
                if(makeCard.length>0){
                    if(!writeMakeCardForFile(makeCard)){
                        return result;
                    }
                }
                line = br.readLine();
            }
            result = true;
        }finally{
            if(null != br){
                br.close();
            }
        }
        
        return result;
    }
    
    /**
     * 取服务器员工信息文件保存路径
     * @param employeeId
     * @return file
     */
    private String getMakeCardDir() {
        return BaseConstant.appWorkDir+ConfigUtil.getConfigValue(ConfigConstant.UploadTag, ConfigConstant.UploadMakeCardTag);
        /*
        Hashtable uploads = (Hashtable) AppConstant.configs.get(ConfigConstant.UploadTag);
        return (String) uploads.get(ConfigConstant.UploadMakeCardTag);
        */
    }

    /**
     * 格式化待写入的订单内容，以指定符号分隔
     * @param vo
     * @return String
     */
    private String fmtMakeCardToFileStr(MakeCardVo vo,String sign) {
        StringBuffer makeCardStr = new StringBuffer("");
        makeCardStr.append(vo.getEmployeeDepartmentTxt()!=null? vo.getEmployeeDepartmentTxt(): "").append(sign);
        makeCardStr.append(vo.getEmployeePositionsTxt()!=null? vo.getEmployeePositionsTxt(): "").append(sign);
        makeCardStr.append(vo.getEmployeeClassTxt()!=null? vo.getEmployeeClassTxt(): "").append(sign);
        makeCardStr.append(vo.getEmployeeName()!=null? vo.getEmployeeName(): "").append(sign);
        makeCardStr.append(vo.getGender()!=null? vo.getGender(): "").append(sign);
        makeCardStr.append(vo.getPhyId()!=null? vo.getPhyId(): "").append(sign);
        makeCardStr.append(vo.getPrintId()!=null? vo.getPrintId(): "").append(sign);
        makeCardStr.append(vo.getMakeOper()!=null? vo.getMakeOper(): "").append(sign);
        makeCardStr.append(vo.getMakeTime()!=null? vo.getMakeTime(): (DateHelper.curDateToStr19yyyy_MM_dd_HH_mm_ss())).append(sign);
        makeCardStr.append(vo.getReturnOper()!=null? vo.getReturnOper(): "").append(sign);
        makeCardStr.append(vo.getReturnTime()!=null? vo.getReturnTime(): "").append(sign);
        makeCardStr.append(vo.getUseState()!=null? vo.getUseState(): "").append(sign);
        makeCardStr.append(AppConstant.MAKE_CARD_TO_FILE_REMARK).append(sign);
        makeCardStr.append(vo.getEmployeeId()!=null? vo.getEmployeeId(): "").append(sign);
        makeCardStr.append(vo.getLogicId()!=null? vo.getLogicId(): "");
        
        return makeCardStr.toString();
    }

    /**
     * 取实例值
     * @param makeCardVoRet
     * @param dbHelper 
     */
    private MakeCardVo setRecordValue(DbHelper dbHelper, String makeTime, String makeOper) throws SQLException {
        
            MakeCardVo makeCardVoRet = new MakeCardVo();
            makeCardVoRet.setEmployeeId(dbHelper.getItemTrueValue("employee_id"));
            makeCardVoRet.setEmployeeName(dbHelper.getItemTrueValue("employee_name"));
            makeCardVoRet.setGender(dbHelper.getItemTrueValue("gender"));
            makeCardVoRet.setLogicId(dbHelper.getItemTrueValue("logical_id"));
            makeCardVoRet.setMakeOper(dbHelper.getItemTrueValue(makeOper));
            makeCardVoRet.setMakeTime(dbHelper.getItemValue(makeTime));
            makeCardVoRet.setPhyId(dbHelper.getItemValue("phy_id"));
            makeCardVoRet.setPrintId(dbHelper.getItemValue("print_id"));
            makeCardVoRet.setUseState(dbHelper.getItemValue("use_state"));
            makeCardVoRet.setReturnOper(dbHelper.getItemValue("return_oper"));
            makeCardVoRet.setReturnTime(dbHelper.getItemValue("return_time"));
            makeCardVoRet.setEmployeeDepartment(dbHelper.getItemTrueValue("employee_department"));
            makeCardVoRet.setEmployeePositions(dbHelper.getItemTrueValue("employee_position"));
            makeCardVoRet.setEmployeeClass(dbHelper.getItemTrueValue("employee_class"));
            
            makeCardVoRet.setEmployeeDepartmentTxt(
                PubUtil.getMapString(AppConstant.EMPLOYEE_DEPARTMENT,dbHelper.getItemTrueValue("employee_department")));
            makeCardVoRet.setEmployeePositionsTxt(
                PubUtil.getMapString(AppConstant.EMPLOYEE_POSITION,dbHelper.getItemTrueValue("employee_position")));
            makeCardVoRet.setEmployeeClassTxt(
                PubUtil.getMapString(AppConstant.EMPLOYEE_CLASS,dbHelper.getItemTrueValue("employee_class")));
            
            return makeCardVoRet;
    }

    private String setTimeWhere(MakeCardParam makeCardParam) {
        
            String bTimeStr = "";
            String eTimeStr = "";
            
            if(makeCardParam.getType().equals(AppConstant.ET_STATE_ISSUE)){
                if(!StringUtil.isEmpty(makeCardParam.getBeginDate())){
                    bTimeStr=" and o.make_time >= to_date('" + makeCardParam.getBeginDate() + " 00:00:00','yyyyMMdd hh24:mi:ss')";
                }
                if(!StringUtil.isEmpty(makeCardParam.getEndDate())){
                    eTimeStr= " and o.make_time <= to_date('" + makeCardParam.getEndDate() + " 23:59:59','yyyyMMdd hh24:mi:ss')";
                }
            } else if(makeCardParam.getType().equals(AppConstant.ET_STATE_RETURN)){
                if(!StringUtil.isEmpty(makeCardParam.getBeginDate())){
                    bTimeStr=" and o.return_time >= to_date('" + makeCardParam.getBeginDate() + " 00:00:00','yyyyMMdd hh24:mi:ss')";
                }
                if(!StringUtil.isEmpty(makeCardParam.getEndDate())){
                    eTimeStr= " and o.return_time <= to_date('" + makeCardParam.getEndDate() + " 23:59:59','yyyyMMdd hh24:mi:ss')";
                }
            }else{
                if(!StringUtil.isEmpty(makeCardParam.getBeginDate())){
                    bTimeStr=" and (o.return_time >= to_date('" + makeCardParam.getBeginDate() + " 00:00:00','yyyyMMdd hh24:mi:ss')"
                        + " or o.make_time >= to_date('" + makeCardParam.getBeginDate() + " 00:00:00','yyyyMMdd hh24:mi:ss'))";
                }
                if(!StringUtil.isEmpty(makeCardParam.getEndDate())){
                    eTimeStr= " and (o.return_time <= to_date('" + makeCardParam.getEndDate() + " 23:59:59','yyyyMMdd hh24:mi:ss')"
                        + " or o.make_time <= to_date('" + makeCardParam.getEndDate() + " 23:59:59','yyyyMMdd hh24:mi:ss'))";
                }
                
            }
            
            return bTimeStr + eTimeStr;
    }


    /**
     * 查验是否存在单位代码，否则在数据库添加
     * @param dbHelper
     * @param vo 
     */
    private void setDepartment(DbHelper dbHelper, MakeCardVo vo) {
        boolean result = false;
        
        if(StringUtil.isEmpty(vo.getEmployeeDepartment()) && !StringUtil.isEmpty(vo.getEmployeeDepartmentTxt())){
            try {
                String sqlStr2 = "select code max_code from W_ACC_TK.W_IC_ET_PUB_FLAG where type='" 
                        + AppConstant.STR_EMPLOYEE_DEPARTMENT+ "' and trim(code_text) = trim('"
                        + vo.getEmployeeDepartmentTxt() + "')";
                result = dbHelper.getFirstDocument(sqlStr2);
                //已经存在该单位时直接取对应单位代码
                if(result){
                    vo.setEmployeeDepartment(dbHelper.getItemValue("max_code"));
                }else{
                    //不存在该单位时取最大单位代码+1
                    String sqlStr = "select nvl(max(to_number(code)),0)+1 max_code from W_ACC_TK.W_IC_ET_PUB_FLAG where type='" +AppConstant.STR_EMPLOYEE_DEPARTMENT+ "'";
                    result = dbHelper.getFirstDocument(sqlStr);
                    //插入数据库
                    if(result){
                        vo.setEmployeeDepartment(dbHelper.getItemValue("max_code"));
                        String sqlInsert = "insert into W_ACC_TK.W_IC_ET_PUB_FLAG (type, code, code_text, description) values (?, ?, ?, ?)";
                        Object[] values = {AppConstant.STR_EMPLOYEE_DEPARTMENT,vo.getEmployeeDepartment(),
                            vo.getEmployeeDepartmentTxt(),AppConstant.DECRI_EMPLOYEE_DEPARTMENT};
                        dbHelper.executeUpdate(sqlInsert,values);
                    }
                }
                
            } catch (Exception e) {
                PubUtil.handleExceptionNoThrow(e, logger);
            }
        }
    }
    
    /**
     * 查验是否存在职务代码，否则在数据库添加
     * @param dbHelper
     * @param vo 
     */
    private void setPosition(DbHelper dbHelper, MakeCardVo vo) {
        boolean result = false;
        
        if(StringUtil.isEmpty(vo.getEmployeePositions()) && !StringUtil.isEmpty(vo.getEmployeePositionsTxt())){
            try {
                String sqlStr2 = "select code max_code from W_ACC_TK.W_IC_ET_PUB_FLAG where type='" 
                        + AppConstant.STR_EMPLOYEE_POSITION+ "' and trim(code_text) = trim('"
                        + vo.getEmployeePositionsTxt() + "')";
                result = dbHelper.getFirstDocument(sqlStr2);
                
                //已经存在该职务时直接取对应职务代码
                if(result){
                    vo.setEmployeeDepartment(dbHelper.getItemValue("max_code"));
                    
                }else{
                    String sqlStr = "select (case nvl(max(to_number(code)),-1) when -1 then 0 else nvl(max(to_number(code)),0)+1 end) as max_code "
                            + " from W_ACC_TK.W_IC_ET_PUB_FLAG where type='" +AppConstant.STR_EMPLOYEE_POSITION+ "'";
                    result = dbHelper.getFirstDocument(sqlStr);

                    if(result){
                        vo.setEmployeePositions(dbHelper.getItemValue("max_code"));
                        String sqlInsert = "insert into W_ACC_TK.W_IC_ET_PUB_FLAG (type, code, code_text, description) values (?, ?, ?, ?)";
                        Object[] values = {AppConstant.STR_EMPLOYEE_POSITION,vo.getEmployeePositions(),
                            vo.getEmployeePositionsTxt(),AppConstant.DECRI_EMPLOYEE_POSITION};
                        dbHelper.executeUpdate(sqlInsert,values);
                    }
                }
            } catch (Exception e) {
                PubUtil.handleExceptionNoThrow(e, logger);
            }
        }
    }
    
    /**
     * 查验是否存在级别代码，否则在数据库添加
     * @param dbHelper
     * @param vo 
     */
    private void setClass(DbHelper dbHelper, MakeCardVo vo) {
        boolean result = false;
        
        if(StringUtil.isEmpty(vo.getEmployeeClass()) && !StringUtil.isEmpty(vo.getEmployeeClassTxt())){
            try {
                String sqlStr2 = "select code max_code from W_ACC_TK.W_IC_ET_PUB_FLAG where type='" 
                        + AppConstant.STR_EMPLOYEE_CLASS+ "' and trim(code_text) = trim('"
                        + vo.getEmployeeClassTxt()+ "')";
                result = dbHelper.getFirstDocument(sqlStr2);
                
                //已经存在该级别时直接取对应级别代码
                if(result){
                    vo.setEmployeeClass(dbHelper.getItemValue("max_code"));
                    
                }else{
                    String sqlStr = "select (case nvl(max(to_number(code)),-1) when -1 then 0 else nvl(max(to_number(code)),0)+1 end) as max_code "
                            + "from W_ACC_TK.W_IC_ET_PUB_FLAG where type='" +AppConstant.STR_EMPLOYEE_CLASS+ "'";
                    result = dbHelper.getFirstDocument(sqlStr);

                    if(result){
                        vo.setEmployeeClass(dbHelper.getItemValue("max_code"));
                        String sqlInsert = "insert into W_ACC_TK.W_IC_ET_PUB_FLAG (type, code, code_text, description) values (?, ?, ?, ?)";
                        Object[] values = {AppConstant.STR_EMPLOYEE_CLASS,vo.getEmployeeClass(),
                            vo.getEmployeeClassTxt(),AppConstant.DECRI_EMPLOYEE_CLASS};
                        dbHelper.executeUpdate(sqlInsert,values);
                    }
                }
            } catch (Exception e) {
                PubUtil.handleExceptionNoThrow(e, logger);
            }
        }
    }
    
    /**
     * 更新单位，职务，级别下拉
     */
    private void updateDepartPosition(){
        //加载员工发卡系统参数表数据
        PubUtil pubUtil = new PubUtil();
        Vector v = pubUtil.getTablePubFlag(AppConstant.TABLE_ET_PARAMER);
        AppConstant.EMPLOYEE_DEPARTMENT.clear();
        AppConstant.EMPLOYEE_POSITION.clear();
        AppConstant.EMPLOYEE_CLASS.clear();
        for(int i=0;i<v.size();i++){
            PubFlagVo pvo = new PubFlagVo();
            pvo = (PubFlagVo) v.get(i);
            // 员工单位 employee_department
            if(pvo.getType().equals(AppConstant.STR_EMPLOYEE_DEPARTMENT)){
                AppConstant.EMPLOYEE_DEPARTMENT.put(pvo.getCode(), pvo.getCodeText());
            }
            // 员工职务 employee_position
            if(pvo.getType().equals(AppConstant.STR_EMPLOYEE_POSITION)){
                AppConstant.EMPLOYEE_POSITION.put(pvo.getCode(), pvo.getCodeText());
            }
            // 员工级别 employee_class
            if(pvo.getType().equals(AppConstant.STR_EMPLOYEE_CLASS)){
                AppConstant.EMPLOYEE_CLASS.put(pvo.getCode(), pvo.getCodeText());
            }
        }
    }
    
    
    /*
     * 修改卡信息
     */
    @Override
    public Boolean editCard(MakeCardVo vo) {
        boolean result = false;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            dbHelper.setAutoCommit(false);
            
            //单位，职务
            setDepartment(dbHelper, vo);
            setPosition(dbHelper, vo);
            setClass(dbHelper, vo);
            
            Object[] values = {
                    StringUtil.nullToString(vo.getEmployeeDepartment()),
                    StringUtil.nullToString(vo.getEmployeePositions()),
                    StringUtil.nullToString(vo.getEmployeeClass()),
                    StringUtil.nullToString(vo.getUpdateOper()), 
                    StringUtil.nullToString(vo.getEmployeeId()), 
                    StringUtil.nullToString(vo.getLogicId())
                };
            
            //当数据库存在员工信息，并为发卡状态时use_state='0'，更新该条记录
            String sqlStr = "update W_ACC_TK.W_IC_ET_ISSUE set employee_department =?, employee_position =?, employee_class =?,"
                    + " update_oper=?, update_time=sysdate where use_state='" + AppConstant.ET_STATE_ISSUE +"' and employee_id =?  and logical_id=? ";

            logger.info("sqlStr:" + sqlStr);
            
            result = dbHelper.executeUpdate(sqlStr, values)>0;
            
            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);
            
            //当手动输入单位或职务时重新加载单位，职务下拉数据
            if(!StringUtil.isEmpty(vo.getEmployeeDepartmentTxt()) 
                    || !StringUtil.isEmpty(vo.getEmployeePositionsTxt())
                    || !StringUtil.isEmpty(vo.getEmployeeClassTxt())){
                updateDepartPosition();
            }
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
            return false;
        } finally {
            PubUtil.finalProcess(dbHelper);

        }
        logger.info("测试sqlStr:" + result);
        return result;
    }
    
    /*
     * 查询是否已经退卡
     */
    @Override
    public Boolean isCardReturned(MakeCardVo vo) {
        boolean result = false;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            dbHelper.setAutoCommit(false);
            
            Object[] values = {
                    StringUtil.nullToString(vo.getLogicId())
                };
            
            //当数据库存在员工信息，并为发卡状态时use_state='0'，更新该条记录
            String sqlStr = "select USE_STATE from  W_ACC_TK.W_IC_ET_ISSUE where logical_id=?  ";

            logger.info("sqlStr:" + sqlStr);
            result = dbHelper.getFirstDocument(sqlStr, values);
            if(result){
                if("1".equals(dbHelper.getItemValue("USE_STATE"))){
                    result = true;
                }else{
                    result = false;
                }
                
            }
            
        } catch (Exception e) {
            return false;
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        logger.info("测试sqlStr:" + result);
        return result;
    }
    
    //检测设备配置是否正确
    @Override
    public Boolean isDeviceConfigureRight(String ip, String deviceType, String deviceNo) {
        boolean result = false;
        DbHelper dbHelper2 = null;
        try {
            dbHelper2 = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            dbHelper2.setAutoCommit(false);
            
            Object[] values = {
                    StringUtil.nullToString(ip),
                    StringUtil.nullToString(deviceType),
                    StringUtil.nullToString(deviceNo)
                };
            
            //当数据库存在员工信息，并为发卡状态时use_state='0'，更新该条记录
            /*
            String sqlStr = "select count(*) as total from w_acc_st.w_op_prm_dev_code_acc "
                    + " where record_flag ='0' "
                    + " and IP_ADDRESS = '" + values[0] + "' "
                    + " and DEV_TYPE_ID = '" + values[1] + "' "
                    + " and DEVICE_ID = '" + values[2] + "' ";

            logger.info("sqlStr:" + sqlStr);
            result = dbHelper2.getFirstDocument(sqlStr);
            */
            
            String sqlStr = "select count(*) as total from w_acc_st.w_op_prm_dev_code_acc "
                    + " where record_flag ='0' "
                    + " and IP_ADDRESS = ? "
                    + " and DEV_TYPE_ID =?  "
                    + " and DEVICE_ID = ? ";

            logger.info("sqlStr:" + sqlStr);
            result = dbHelper2.getFirstDocument(sqlStr, values); 
            if(result){
                String x = dbHelper2.getItemValue("total");
                if(Integer.valueOf(dbHelper2.getItemValue("total"))>0){
                    result = true;
                }else{
                    result = false;
                }
            }
            
        } catch (Exception e) {
            return false;
        } finally {
            PubUtil.finalProcess(dbHelper2);
        }
        logger.info("测试sqlStr:" + result);
        return result;
    }

}
