package com.goldsign.acc.report.dao;

import java.util.GregorianCalendar;

import org.apache.log4j.Logger;


import com.goldsign.acc.report.constant.AppConstant;
import com.goldsign.acc.report.util.PubUtil;
import com.goldsign.acc.report.vo.InitResult;
import com.goldsign.acc.report.vo.Report;
import com.goldsign.acc.report.vo.ResultFromProc;
import com.goldsign.lib.db.util.DbHelper;

import java.util.Vector;

public class BalanceWaterDAO {

    //private DbHelper dbHelper;
    private static Logger logger = Logger.getLogger(BalanceWaterDAO.class.getName());

    public BalanceWaterDAO() {
        //this.dbHelper = AppConstant.REPORT_DBHELPER;
    }

//modify by hejj 重写
    public boolean readyGenerateReport() {
        ResultFromProc ret = null;
        try {
            ret = this.getBalanceWaterNo();
            if (ret.getRetValues().isEmpty()) {
                return false;
            }
            Report.BalanceWaters.clear();
            String balanceWaterNo = (String) ret.getRetValues().get(0);
            Report.BalanceWaters.add(balanceWaterNo);
            this.setMonthOrYearFlag(balanceWaterNo);
            return true;


        } catch (Exception ex) {
            return false;
        }

    }

    public InitResult readyGenerateReportForAll() {
        ResultFromProc ret = null;
        InitResult iResult = new InitResult();
        try {
            ret = this.getBalanceWaterNo();
            if (ret.getRetValues().isEmpty()) {
                iResult.setReadyGenerateReport(false);
                return iResult;
            }
            iResult.getBalanceWaters().clear();
            String balanceWaterNo = (String) ret.getRetValues().get(0);
            iResult.getBalanceWaters().add(balanceWaterNo);
            iResult.setBalanceWaterNo(balanceWaterNo);
            this.setPeriodFlag(iResult);
            return iResult;


        } catch (Exception ex) {
            return iResult;
        }

    }

    //update is_rpt after generate reports
    public void updateReportFlag(String no) {
        DbHelper dbHelper = null;
        try {
            dbHelper = new DbHelper("", AppConstant.REPORT_DBCPHELPER.getConnection());

            String sqlStr1 =
                    "update " + AppConstant.ST_USER + "w_st_sys_flow_report set finish_flag='1',end_datetime=sysdate " + "where balance_water_no='" + no
                    + "'";
            dbHelper.executeUpdate(sqlStr1);
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

    }

    private boolean checkLastDay(String dayStr) {
        try {
            int y = Integer.parseInt(dayStr.substring(0, 4));
            int m = Integer.parseInt(dayStr.substring(4, 6));
            int d = Integer.parseInt(dayStr.substring(6, 8));

            GregorianCalendar gc = new GregorianCalendar(y, m - 1, d);
            gc.add(GregorianCalendar.DATE, 1);
            if (gc.get(GregorianCalendar.DATE) == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("checkLastDay(" + dayStr + ") error! " + e);
            return false;
        }
    }

    public boolean checkyearLastDay(String dayStr) {
        try {
            int y = Integer.parseInt(dayStr.substring(0, 4));
            int m = Integer.parseInt(dayStr.substring(4, 6));
            int d = Integer.parseInt(dayStr.substring(6, 8));

           // GregorianCalendar gc = new GregorianCalendar(y - 1, m, d);
            GregorianCalendar gc = new GregorianCalendar(y , m-1, d);
            gc.add(GregorianCalendar.DATE, 1);
            if ((gc.get(GregorianCalendar.DATE) == 1) && (gc.get(GregorianCalendar.MONTH) == 0)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("checkyearLastDay(" + dayStr + ") error! " + e);
            return false;
        }
    }
    /*
    public static void main(String[] args){
        BalanceWaterDAO dao = new BalanceWaterDAO();
        String balanceWaterNo="2017123001";
        boolean isLastDayForYear =dao.checkyearLastDay(balanceWaterNo);
        System.out.println("isLastDayForYear="+isLastDayForYear);
        
    }
*/
    public ResultFromProc getBalanceWaterNo() throws Exception {
        String procName = AppConstant.ST_USER + "w_up_rp_get_bal_water_no";
        String sql = "{call " + procName + "(?,?,?)}"; //存储过程调用语句
        DbHelper dbHelper = null;
        ResultFromProc ret = null;
        boolean result;
        Vector<String> balanceWaterNos = new Vector();
        String balanceWaterNo;
        //String balWaterNo = "";
        int retCode; //存储过程参数返回的执行结果代码
        String retMsg; //存储过程参数返回的执行结果消息
        int[] pInIndexes = {}; //存储过程输入参数索引列表
        Object[] pInStmtValues = {}; //存储过程输入参数值
        int[] pOutIndexes = {1, 2, 3}; //存储过程输出参数索引列表
        int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_INTEGER, DbHelper.PARAM_OUT_TYPE_VACHAR,
            DbHelper.PARAM_OUT_TYPE_CURSOR}; //存储过程输出参数值类型
        try {
            dbHelper = new com.goldsign.lib.db.util.DbHelper("", AppConstant.REPORT_DBCPHELPER.getConnection());
            dbHelper.runStoreProcForOracle(sql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes); //执行存储过程
            /*
             * 取输出参数值
             */
            retCode = dbHelper.getOutParamIntValue(1); //获取返回代码输出参数值
            retMsg = dbHelper.getOutParamStringValue(2); //获取返回消息输出参数值

            logger.info("返回代码：" + retCode + " 返回消息：" + retMsg);
            result = dbHelper.getFirstDocumentForOracle();//判断结果集是否为空
            if (result) {
                balanceWaterNo = dbHelper.getItemValue("BALANCE_WATER_NO");//
                if (balanceWaterNo != null && balanceWaterNo.length() !=0){
                    balanceWaterNos.add(balanceWaterNo);
                }
                //balanceWaterNo ="20131231";
                    
                /*
                boolean lastDay = false;
                boolean yearlastDay = false;
                if (balanceWaterNo != null && balanceWaterNo != "") {
                    if (!lastDay) {
                        lastDay = checkLastDay(balanceWaterNo.substring(0, 8));
                        if (lastDay) {
                            Report.monthReportBalanceWater = balanceWaterNo;
                            Report.genMonthReport = lastDay;
                            if (!yearlastDay) {
                                yearlastDay = checkyearLastDay(balanceWaterNo.substring(0, 8));
                                if (yearlastDay) {
                                    Report.yearReportBalanceWater = balanceWaterNo;
                                }
                                Report.genYearReport = yearlastDay;
                            }
                        }
                    }
                }
                */
            }

            ret = new ResultFromProc(retCode, retMsg, balanceWaterNos);
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return ret;

    }

    private void setMonthOrYearFlag(String balanceWaterNo) {
        boolean lastDay = false;
        boolean yearlastDay = false;
        if (!lastDay) {
            lastDay = checkLastDay(balanceWaterNo.substring(0, 8));
            if (lastDay) {
                Report.monthReportBalanceWater = balanceWaterNo;
                Report.genMonthReport = lastDay;
                if (!yearlastDay) {
                    yearlastDay = checkyearLastDay(balanceWaterNo.substring(0, 8));
                    if (yearlastDay) {
                        Report.yearReportBalanceWater = balanceWaterNo;
                    }
                    Report.genYearReport = yearlastDay;
                }
            }
        }
    }
    private void setPeriodFlag(InitResult iResult) {
        String balanceWaterNo = iResult.getBalanceWaterNo();
        boolean lastDayMonth = this.checkLastDay(balanceWaterNo);
        boolean lastDayYear = this.checkyearLastDay(balanceWaterNo);
        iResult.setReadyGenerateReport(true);
        iResult.setGenMonthReport(lastDayMonth);
        iResult.setGenYearReport(lastDayYear);
        iResult.setBalanceWaterNoMonth(balanceWaterNo);
        iResult.setBalanceWaterNoYear(balanceWaterNo);

    }
}
