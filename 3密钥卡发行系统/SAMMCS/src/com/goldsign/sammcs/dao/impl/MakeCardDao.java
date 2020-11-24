/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.sammcs.dao.impl;

import com.goldsign.csfrm.dao.impl.BaseDao;
import com.goldsign.csfrm.env.BaseConstant;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.sammcs.dao.IMakeCardDao;
import com.goldsign.sammcs.env.AppConstant;
import com.goldsign.sammcs.env.ConfigConstant;
import com.goldsign.sammcs.util.ConfigUtil;
import com.goldsign.sammcs.util.PubUtil;
import com.goldsign.sammcs.vo.MakeCardQueryVo;
import com.goldsign.sammcs.vo.MakeCardVo;
import com.goldsign.sammcs.vo.OperateLogVo;
import com.goldsign.sammcs.vo.SamIssueDetailVo;
import com.goldsign.sammcs.vo.SamOrderPlanVo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Override;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class MakeCardDao extends BaseDao implements IMakeCardDao{
    
    private static Logger logger = Logger.getLogger(MakeCardDao.class.getName());
    
    public List<SamOrderPlanVo> getMakeCards(MakeCardQueryVo queryVo){
        List<SamOrderPlanVo> samOrderPlanList = new ArrayList<SamOrderPlanVo>();
        boolean result = false;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            String sqlStr = "select a.ORDER_NO,a.SAM_TYPE,b.SAM_TYPE_DESC,a.ORDER_NUM,a.START_LOGIC_NO,a.AUDIT_ORDER_OPER,a.AUDIT_ORDER_TIME,a.FINISH_NUM,a.MAKE_CARD_OPER"+
                                ",a.MAKE_CARD_TIME,a.FINISH_FLAG,a.REMARK from W_ACC_TK.W_IC_SAM_ORDER_PLAN a, W_ACC_TK.W_IC_SAM_TYPE b where a.SAM_TYPE=b.SAM_TYPE_CODE(+) and a.AUDIT_ORDER_TIME is not null "+ 
                                " and exists (select 1 from W_ACC_TK.W_IC_SAM_OUT_ISSUE where pdu_order_no=a.ORDER_NO and order_state=? )";
            
            Object[] values = { AppConstant.RECORD_FLAG_AUDITED};
                    
            StringBuffer sqlBuf = new StringBuffer(sqlStr);
            if(!StringUtil.isEmpty(queryVo.getOrderNo())){
                sqlBuf.append(" and a.ORDER_NO like'%"+ queryVo.getOrderNo().toUpperCase() +"%'");
            }
            //20190918 limingjin
            if(!StringUtil.isEmpty(queryVo.getWorkType())){
                sqlBuf.append(" and a.ORDER_NO like'"+ queryVo.getWorkType()+"%'");
            }
            if(!StringUtil.isEmpty(queryVo.getSamType())){
                sqlBuf.append(" and a.SAM_TYPE='"+ queryVo.getSamType() +"'");
            }
            if(!StringUtil.isEmpty(queryVo.getFinishFlag())){
                sqlBuf.append(" and a.FINISH_FLAG='"+ queryVo.getFinishFlag() +"'");
            }
            String orderSql = " order by a.ORDER_NO";
            sqlBuf.append(orderSql);

            logger.info("sqlStr:" + sqlBuf.toString());
            
            result = dbHelper.getFirstDocument(sqlBuf.toString(),values);
            while (result) {
                SamOrderPlanVo samOrderPlanVo = new SamOrderPlanVo();
                samOrderPlanVo.setOrderNo(dbHelper.getItemTrueValue("ORDER_NO"));
                //20190918 limingjin
                if(samOrderPlanVo.getOrderNo().startsWith("CZDD")){
                  samOrderPlanVo.setWorkType("重置");
                }else{
                  samOrderPlanVo.setWorkType("发行");
                }
                samOrderPlanVo.setSamType(dbHelper.getItemTrueValue("SAM_TYPE"));
                samOrderPlanVo.setSamTypeDesc(dbHelper.getItemTrueValue("SAM_TYPE_DESC"));
                samOrderPlanVo.setStartLogicNo(dbHelper.getItemTrueValue("START_LOGIC_NO"));
                samOrderPlanVo.setOrderNum(dbHelper.getItemTrueValue("ORDER_NUM"));
                samOrderPlanVo.setRemark(dbHelper.getItemTrueValue("REMARK"));        
                samOrderPlanVo.setAuditOrderOper(dbHelper.getItemTrueValue("AUDIT_ORDER_OPER"));
                samOrderPlanVo.setAuditOrderTime(dbHelper.getItemTrueValue("AUDIT_ORDER_TIME"));
                samOrderPlanVo.setFinishNum(dbHelper.getItemTrueValue("FINISH_NUM"));
                samOrderPlanVo.setFinishFlag(dbHelper.getItemTrueValue("FINISH_FLAG"));
                samOrderPlanVo.setMakeCardOper(dbHelper.getItemTrueValue("MAKE_CARD_OPER"));
                samOrderPlanVo.setMakeCardTime(dbHelper.getItemTrueValue("MAKE_CARD_TIME"));
                        
                samOrderPlanList.add(samOrderPlanVo);
                
                        
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return samOrderPlanList;
    }
    
    
    //20160126 modify by mqf 增加卡商代码cardProducerCode
    public Boolean writeMakeCard(MakeCardVo vo, String phyNo, String cardProducerCode, String userId) throws SQLException {
        boolean result = false;
        DbHelper dbHelper = null;
        String paraStr = "";
        String isBadCardStr ="";
        if (!StringUtil.isEmpty(phyNo)) {
//            paraStr = ", IS_BAD='"+AppConstant.SAM_CARD_STATE_OK+"', PHY_NO='"+phyNo+"' ";
            paraStr = ", IS_BAD='"+AppConstant.SAM_CARD_STATE_OK+"', PHY_NO='"+phyNo+"', CARD_PRODUCER_CODE='"+cardProducerCode+"' ";
            isBadCardStr = AppConstant.SAM_CARD_MAKE_RESULT_SUCCESS;
        } else {
            paraStr = ", IS_BAD='"+AppConstant.SAM_CARD_STATE_BAD+"' ";
            isBadCardStr = AppConstant.SAM_CARD_MAKE_RESULT_FAIL;
        }
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            
            dbHelper.setAutoCommit(false);
                    
            //更新库存信息
            Object[] values = { vo.getCurLogicNo()};
            String sqlStr = "update W_ACC_TK.W_IC_SAM_STOCK set PRODUCE_TYPE='"+AppConstant.PRODUCE_TYPE_PRODUCT+ "', STOCK_STATE='"+AppConstant.STOCK_STATE_MAKE_CARD+"'"+paraStr
                             + " where LOGIC_NO=?";
            logger.info("sqlStr:" + sqlStr);
            result = dbHelper.executeUpdate(sqlStr, values)>0;
            if (!result){
                dbHelper.rollbackTran();
                dbHelper.setAutoCommit(true);
                return false;                      
            }
            String detailSqlStr = null;
            
            //新增密钥卡发行明细表
            //20160126 增加card_producer_code
              detailSqlStr = "insert INTO W_ACC_TK.W_IC_SAM_ISSUE_DETAIL(order_no, sam_type, logic_no, make_card_oper, make_card_time, make_result,remark,card_producer_code"
                    + ") VALUES (?, ?, ?, ?, sysdate, ?, ?, ?)";
            
             
            

            Object[]  detailValues ={vo.getOrderNo(),vo.getSamType(),vo.getCurLogicNo(),userId,isBadCardStr,"",cardProducerCode};

            logger.info("sqlStr:" + detailSqlStr);
            result = dbHelper.executeUpdate(detailSqlStr, detailValues)>0;
            if (!result){
                dbHelper.rollbackTran();
                dbHelper.setAutoCommit(true);
                return false;                      
            }
            //20190917 limingjin
            if(vo.getOrderNo().startsWith("CZDD")&&!StringUtil.isEmpty(phyNo)){
                detailSqlStr = "insert INTO W_ACC_TK.W_IC_SAM_RESET_DETAIL(order_no, sam_type, logic_no, make_card_oper, make_card_time, make_result,remark,card_producer_code"
                    + ") VALUES (?, ?, ?, ?, sysdate, ?, ?, ?)";
                 Object[]  resetDetailValues ={vo.getOrderNo(),vo.getSamType(),vo.getCurLogicNo(),userId,isBadCardStr,"",cardProducerCode};

                logger.info("sqlStr:" + detailSqlStr);
                result = dbHelper.executeUpdate(detailSqlStr, resetDetailValues)>0;
                if (!result){
                    dbHelper.rollbackTran();
                    dbHelper.setAutoCommit(true);
                    return false;                      
                }
            }
            
            //更新生产订单
            String orderPlanSql = "update W_ACC_TK.W_IC_SAM_ORDER_PLAN set FINISH_NUM= NVL(FINISH_NUM,0)+1,"
                            + "FINISH_FLAG= (case when ORDER_NUM> NVL(FINISH_NUM,0)+1 then '"+AppConstant.FINISH_FLAG_PART_COMPLETE+"'"
                            + " when ORDER_NUM= NVL(FINISH_NUM,0)+1 then '"+AppConstant.FINISH_FLAG_ALL_COMPLETE+"' end),"
                            + " MAKE_CARD_OPER=?, MAKE_CARD_TIME=sysdate"
                            + " where ORDER_NO=? ";
            Object[]  orderPlanValues ={userId,vo.getOrderNo()};
            
            result = dbHelper.executeUpdate(orderPlanSql,orderPlanValues)>0;
            if (!result){
                dbHelper.rollbackTran();
                dbHelper.setAutoCommit(true);
                return false;                      
            }
            
                    
            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);
            
        } catch (Exception e) {
            dbHelper.rollbackTran();
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
    //20160126 add by mqf 增加卡商代码cardProducerCode
    @Override
    public File writeLocalFile(MakeCardVo vo, String phyNo, String cardProducerCode) throws IOException {
        String dir = getMakeCardDir();//保存路径
        File parentFile = new File(dir);
        if(!parentFile.isDirectory()){
            parentFile.mkdirs();
        }
        File file = new File(dir+"/"+vo.getCurLogicNo()+AppConstant.MAKE_CARD_TO_FILE_SUFFIX);
        if(!file.exists()){
            file.createNewFile();
        }
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(new FileWriter(file,true));
            String makeCardStr = fmtMakeCardToFileStr(vo, phyNo, cardProducerCode, AppConstant.VER_SIGN);
            pw.println(makeCardStr);
            pw.flush();
        }catch(Exception e) {
            logger.error(e);
        }finally {
            if(null != pw){
                pw.close();
            }
        }
        return file;
    }
    
    
    
    public boolean checkOrderPlan(String orderNo) {
        boolean result = false;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            String sqlStr = "select FINISH_FLAG from W_ACC_TK.W_IC_SAM_ORDER_PLAN where AUDIT_ORDER_TIME is not null and ORDER_NO='"+orderNo+"'";
            
            logger.info("sqlStr:" + sqlStr);
            
            result = dbHelper.getFirstDocument(sqlStr);
            if (result) {
                String finishFlag = dbHelper.getItemTrueValue("FINISH_FLAG");
                if (!AppConstant.FINISH_FLAG_ALL_COMPLETE.equals(finishFlag)) {
                    return true;
                } else {
                    return false;
                }      
                
            }
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        
        return result;
    }
    
    public Object[] getOrderPlanData(String orderNo) {
        boolean result = false;
        Object[] results = null;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            String sqlStr = "select FINISH_FLAG,NVL(FINISH_NUM,0) FINISH_NUM, SAM_TYPE, MAKE_CARD_OPER,MAKE_CARD_TIME from W_ACC_TK.W_IC_SAM_ORDER_PLAN where AUDIT_ORDER_TIME is not null and ORDER_NO='"+orderNo+"'";
            
            logger.info("sqlStr:" + sqlStr);
            
            result = dbHelper.getFirstDocument(sqlStr);
            if (result) {
                results = new Object[5];
                results[0] = dbHelper.getItemTrueValue("FINISH_FLAG");
                results[1] = dbHelper.getItemTrueValue("FINISH_NUM");
                results[2] = dbHelper.getItemTrueValue("SAM_TYPE");
                results[3] = dbHelper.getItemTrueValue("MAKE_CARD_OPER");
                results[4] = dbHelper.getItemTrueValue("MAKE_CARD_TIME");
                
            }
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        
        return results;
    }
    
    /**
     * 判断是否存在记录
     * @param logicNo
     * @return 
     */
    public Boolean issueDetailIsExist(String logicNo) {
        boolean result = false;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            String sqlStr = "select 1 from W_ACC_TK.W_IC_SAM_ISSUE_DETAIL "+ " where LOGIC_NO='"+logicNo+"'";
            
            logger.info("sqlStr:" + sqlStr);
            
            result = dbHelper.getFirstDocument(sqlStr);
            if (result) {
                return true;
            } else {
                return false;
            }      
                
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return result;
        
    }
    
    public String getCurLogicNo(String orderNo) {
        
        boolean result = false;
        DbHelper dbHelper = null;
        
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            String sqlStr = "select NVL(FINISH_NUM,0) FINISH_NUM from W_ACC_TK.W_IC_SAM_ORDER_PLAN where ORDER_NO='"+orderNo+"'";
            
            logger.info("sqlStr:" + sqlStr);
            int finishNum = 0;
            result = dbHelper.getFirstDocument(sqlStr);
            if (result) {
                finishNum = Integer.parseInt(dbHelper.getItemTrueValue("FINISH_NUM"));
            }
            
            sqlStr = "select START_LOGIC_NO,END_LOGIC_NO,to_number(substr(END_LOGIC_NO,12,5))-to_number(substr(START_LOGIC_NO,12,5))+1 as num from W_ACC_TK.W_IC_SAM_LOGIC_NOS "
                            + "where ORDER_NO='"+orderNo+"' order by START_LOGIC_NO";
            
            logger.info("sqlStr:" + sqlStr);
            int levelNum = finishNum+1;
            result = dbHelper.getFirstDocument(sqlStr);
            while (result) {
                String startLogicNo = dbHelper.getItemTrueValue("START_LOGIC_NO");
                int num = 0;
                if (dbHelper.getItemTrueValue("num") != null) {
                    num = Integer.parseInt(dbHelper.getItemTrueValue("num"));
                }
                if (levelNum>num) {
                    levelNum = levelNum-num;
                } else {
                    if (levelNum==1) {
                        return startLogicNo;
                    } else {
                        String leftPart = startLogicNo.substring(0, 11);
                        int startNo = Integer.parseInt(startLogicNo.substring(11, 16));
                        String curNoStr = String.valueOf(startNo + levelNum-1);
                        String zeroStr ="00000";
                        String curStartNo = leftPart+zeroStr.substring(0, zeroStr.length()-curNoStr.length())+curNoStr;
                        return curStartNo;
                        
                    }
                }
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return null;
    }
    
    /**
     * 取服务器员工信息文件保存路径
     * @return file
     */
    private String getMakeCardDir() {
        return BaseConstant.appWorkDir+ConfigUtil.getConfigValue(ConfigConstant.BackupTag, ConfigConstant.BackupMakeCardTag);
    }
    
    
    private String fmtMakeCardToFileStr(MakeCardVo vo, String phyNo, String cardProducerCode, String sign) {
        StringBuffer makeCardStr = new StringBuffer("");
        makeCardStr.append(vo.getOrderNo()).append(sign);
        makeCardStr.append(vo.getSamType()).append(sign);
        makeCardStr.append(vo.getCurLogicNo()).append(sign);
        makeCardStr.append(phyNo).append(sign);
        makeCardStr.append(AppConstant.MAKE_CARD_TO_FILE_REMARK).append(sign);
        //20160126 add by mqf 增加卡商代码cardProducerCode
        makeCardStr.append(cardProducerCode).append(sign);
        
        return makeCardStr.toString();
    }
    
    /**
     * 取服务器员工信息文件,并将每条记录插入数据库
     * 
     * @param vo
     * @return
     * @throws IOException 
     */
    public Boolean readLocalFile(File file, String userId) throws IOException,SQLException {
        
        Boolean result = false;
        if (!file.exists()) {
            return result;
        }
        
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            //每个备份文件正常只有一条记录
            if (line != null){
                String[] makeCard = line.split(AppConstant.SEP_VER_SIGN);
                if(makeCard.length>0){
                    if(!writeMakeCardForFile(makeCard,userId)){
                        return result;
                    }
                }
//                line = br.readLine();
            }
            result = true;
        }finally{
            if(null != br){
                br.close();
            }
        }
        
        return result;
    }
    
    /*
     * 将读取文件信息插入数据库
     */
    public Boolean writeMakeCardForFile(Object[] makeCardValues, String userId) throws SQLException {
        
        String orderNo = (String)makeCardValues[0];
        String samType = (String)makeCardValues[1];
        String curLogicNo = (String)makeCardValues[2];
        String phyNo = (String)makeCardValues[3];
        String remark = (String)makeCardValues[4];
        //20160126 add by mqf 增加卡商代码cardProducerCode
        String cardProducerCode = (String)makeCardValues[5];
        
        boolean result = false;
        DbHelper dbHelper = null;
        String paraStr = "";
        String isBadCardStr ="";
        if (!StringUtil.isEmpty(phyNo)) {
            paraStr = ", IS_BAD='"+AppConstant.SAM_CARD_STATE_OK+"', PHY_NO='"+phyNo+"', CARD_PRODUCER_CODE='"+cardProducerCode+"' ";
            isBadCardStr = AppConstant.SAM_CARD_STATE_OK;
        } else {
            paraStr = ", IS_BAD='"+AppConstant.SAM_CARD_STATE_BAD+"' ";
            isBadCardStr = AppConstant.SAM_CARD_STATE_BAD;
        }
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            
            dbHelper.setAutoCommit(false);
            
            //判断是否存在记录
            String selectDetailSqlStr = "select 1 from W_ACC_TK.W_IC_SAM_ISSUE_DETAIL "+ " where LOGIC_NO='"+curLogicNo+"'";
            result = dbHelper.getFirstDocument(selectDetailSqlStr);
            if (result) {
                return false;
            }
                    
            //更新库存信息
            Object[] values = { curLogicNo};
            String sqlStr = "update W_ACC_TK.W_IC_SAM_STOCK set PRODUCE_TYPE='"+AppConstant.PRODUCE_TYPE_PRODUCT+ "', STOCK_STATE='"+AppConstant.STOCK_STATE_MAKE_CARD+"'"+paraStr
                             + " where LOGIC_NO=?";
            logger.info("sqlStr:" + sqlStr);
            result = dbHelper.executeUpdate(sqlStr, values)>0;
            if (!result){
                dbHelper.rollbackTran();
                dbHelper.setAutoCommit(true);
                return false;                      
            }
            
            //20160126 add by mqf 增加卡商代码cardProducerCode
            //新增密钥卡发行明细表
            String detailSqlStr = "insert INTO W_ACC_TK.W_IC_SAM_ISSUE_DETAIL(order_no, sam_type, logic_no, make_card_oper, make_card_time, make_result,remark,card_producer_code"
                    + ") VALUES (?, ?, ?, ?, sysdate, ?, ?, ?)";

            Object[]  detailValues ={orderNo,samType,curLogicNo,userId,isBadCardStr,remark,cardProducerCode};

            logger.info("sqlStr:" + detailSqlStr);
            result = dbHelper.executeUpdate(detailSqlStr, detailValues)>0;
            if (!result){
                dbHelper.rollbackTran();
                dbHelper.setAutoCommit(true);
                return false;                      
            }
            //20190917 limingjin
            if(orderNo.startsWith("CZDD")&&!StringUtil.isEmpty(phyNo)){
                String resetSqlStr = "insert INTO W_ACC_TK.W_IC_SAM_RESET_DETAIL(order_no, sam_type, logic_no, make_card_oper, make_card_time, make_result,remark,card_producer_code"
                        + ") VALUES (?, ?, ?, ?, sysdate, ?, ?, ?)";

                Object[]  resetDetailValues ={orderNo,samType,curLogicNo,userId,isBadCardStr,remark,cardProducerCode};

                logger.info("sqlStr:" + detailSqlStr);
                result = dbHelper.executeUpdate(resetSqlStr, resetDetailValues)>0;
                if (!result){
                    dbHelper.rollbackTran();
                    dbHelper.setAutoCommit(true);
                    return false;                      
                }
            }
            //更新生产订单
            String orderPlanSql = "update W_ACC_TK.W_IC_SAM_ORDER_PLAN set FINISH_NUM= NVL(FINISH_NUM,0)+1,"
                            + "FINISH_FLAG= (case when ORDER_NUM> NVL(FINISH_NUM,0)+1 then '"+AppConstant.FINISH_FLAG_PART_COMPLETE+"'"
                            + " when ORDER_NUM= NVL(FINISH_NUM,0)+1 then '"+AppConstant.FINISH_FLAG_ALL_COMPLETE+"' end),"
                            + " MAKE_CARD_OPER=?, MAKE_CARD_TIME=sysdate"
                            + " where ORDER_NO=? ";
            Object[]  orderPlanValues ={userId,orderNo};
            result = dbHelper.executeUpdate(orderPlanSql,orderPlanValues)>0;
            if (!result){
                dbHelper.rollbackTran();
                dbHelper.setAutoCommit(true);
                return false;                      
            }
            
                    
            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);
            
        } catch (Exception e) {
            dbHelper.rollbackTran();
            PubUtil.handleExceptionNoThrow(e, logger);
            return false;
        } finally {
            PubUtil.finalProcess(dbHelper);

        }
        return result;
    }
    
    
    public List<SamIssueDetailVo> queryIssueDetails(MakeCardQueryVo queryVo){
        List<SamIssueDetailVo> samOrderPlanList = new ArrayList<SamIssueDetailVo>();
        boolean result = false;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            String sqlStr = "select a.ORDER_NO,a.SAM_TYPE,b.SAM_TYPE_DESC,a.LOGIC_NO,a.MAKE_CARD_OPER,a.MAKE_CARD_TIME,a.MAKE_RESULT,a.REMARK "+
                                " from W_ACC_TK.W_IC_SAM_ISSUE_DETAIL a, W_ACC_TK.W_IC_SAM_TYPE b where a.SAM_TYPE=b.SAM_TYPE_CODE(+)  ";
            StringBuffer sqlBuf = new StringBuffer(sqlStr);
            if(!StringUtil.isEmpty(queryVo.getMakeCardTimeBegin())){
                sqlBuf.append(" and a.MAKE_CARD_TIME >=to_date('" + queryVo.getMakeCardTimeBegin() + " 00:00:00','yyyyMMdd hh24:mi:ss')");
                
            }
            if(!StringUtil.isEmpty(queryVo.getMakeCardTimeEnd())){
                sqlBuf.append(" and a.MAKE_CARD_TIME <=to_date('" + queryVo.getMakeCardTimeEnd() + " 23:59:59','yyyyMMdd hh24:mi:ss')");
            }
            String orderSql = " order by a.ORDER_NO,a.LOGIC_NO";
            sqlBuf.append(orderSql);

            logger.info("sqlStr:" + sqlBuf.toString());
            
            result = dbHelper.getFirstDocument(sqlBuf.toString());
            while (result) {
                SamIssueDetailVo vo = new SamIssueDetailVo();
                vo.setOrderNo(dbHelper.getItemTrueValue("ORDER_NO"));
                vo.setSamType(dbHelper.getItemTrueValue("SAM_TYPE"));
                vo.setSamTypeDesc(dbHelper.getItemTrueValue("SAM_TYPE_DESC"));
                vo.setLogicNo(dbHelper.getItemTrueValue("LOGIC_NO"));
                vo.setMakeCardOper(dbHelper.getItemTrueValue("MAKE_CARD_OPER"));
                vo.setMakeCardTime(dbHelper.getItemTrueValue("MAKE_CARD_TIME"));        
                vo.setMakeResult(dbHelper.getItemTrueValue("MAKE_RESULT"));
                vo.setRemark(dbHelper.getItemTrueValue("REMARK"));
                        
                samOrderPlanList.add(vo);
                        
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return samOrderPlanList;
    }
    /**
     * 结束订单，更新库存状态，订单状态
     * @param orderNo
     * @return
     * @throws SQLException 
     */
    public Boolean cancelOrder(String orderNo, String userId) throws SQLException {
        boolean result = false;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            
            dbHelper.setAutoCommit(false);
            
            List<String[]> logicNosList = new ArrayList<String[]>();
            String logicNosSqlStr = "select START_LOGIC_NO,END_LOGIC_NO from W_ACC_TK.W_IC_SAM_LOGIC_NOS "
                            + "where ORDER_NO='"+orderNo+"' order by START_LOGIC_NO";
            result = dbHelper.getFirstDocument(logicNosSqlStr);
            while (result) {
                String[] obj = new String[2];
                obj[0] = dbHelper.getItemTrueValue("START_LOGIC_NO");
                obj[1] = dbHelper.getItemTrueValue("END_LOGIC_NO");
                logicNosList.add(obj);
                        
                result = dbHelper.getNextDocument();
            }
            
            if (!logicNosList.isEmpty()) {
                for(String[] obj : logicNosList) {
                    String startLogicNo = obj[0];
                    String endLogicNo = obj[1];
                    
                    if (StringUtil.isEmpty(startLogicNo) || StringUtil.isEmpty(endLogicNo)) {
                        dbHelper.rollbackTran();
                        dbHelper.setAutoCommit(true);
                        return false;
                    }
                    
                    //更新库存信息
                    Object[] values = {startLogicNo,endLogicNo};
                    String stockSqlStr = "update W_ACC_TK.W_IC_SAM_STOCK set STOCK_STATE='"+AppConstant.STOCK_STATE_MAKE_CARD+ "'"
                                     + " where LOGIC_NO>=? and LOGIC_NO<=?";
                    logger.info("sqlStr:" + stockSqlStr);//更新库存信息
                    
                    result = dbHelper.executeUpdate(stockSqlStr, values)>0;
                    if (!result){
                        dbHelper.rollbackTran();
                        dbHelper.setAutoCommit(true);
                        return false;                      
                    }
                    
                }
            }
            
            
            //更新生产订单 为完成标志，即是未制卡，结束订单后也更制卡人、制卡时间、制卡数量为0
            String orderPlanSql = "update W_ACC_TK.W_IC_SAM_ORDER_PLAN set FINISH_FLAG= '"+AppConstant.FINISH_FLAG_ALL_COMPLETE
                    + "', MAKE_CARD_OPER=?, MAKE_CARD_TIME=sysdate"
                    +", FINISH_NUM= (case when NVL(FINISH_NUM,0)=0 then 0 else FINISH_NUM end)"
                    + "  where ORDER_NO=? ";
            Object[]  orderPlanValues ={userId,orderNo};
            result = dbHelper.executeUpdate(orderPlanSql,orderPlanValues)>0;
            if (!result){
                dbHelper.rollbackTran();
                dbHelper.setAutoCommit(true);
                return false;                      
            }
            
                    
            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);
            
        } catch (Exception e) {
            dbHelper.rollbackTran();
            PubUtil.handleExceptionNoThrow(e, logger);
            return false;
        } finally {
            PubUtil.finalProcess(dbHelper);

        }
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
            
            //查询当前设备是否已经在运营系统中注册
            String sqlStr = "select count(*) as total from w_acc_st.w_op_prm_dev_code_acc "
                    + " where record_flag ='0' "
                    + " and IP_ADDRESS = ? "
                    + " and DEV_TYPE_ID =?  "
                    + " and DEVICE_ID = ? ";

            logger.info("sqlStr:" + sqlStr);
            result = dbHelper2.getFirstDocument(sqlStr, values);
            if(result){
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
