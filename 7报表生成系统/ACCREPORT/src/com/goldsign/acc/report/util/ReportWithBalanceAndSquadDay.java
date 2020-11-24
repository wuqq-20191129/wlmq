package com.goldsign.acc.report.util;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;
import com.goldsign.acc.report.constant.AppConstant;
import com.goldsign.acc.report.common.ReportBase;
import com.goldsign.acc.report.vo.ReportAttribute;
import com.goldsign.acc.report.vo.ReportGenerateResult;
import com.goldsign.lib.db.util.DbHelper;


import org.apache.log4j.Logger;

public class ReportWithBalanceAndSquadDay
        extends ReportBase {

    private static Logger logger = Logger.getLogger(ReportWithBalanceAndSquadDay.class.
            getName());

    //按单个运营日生成报表
    public ReportGenerateResult generate(String balanceWaterNo,
            ReportAttribute report, String squadDay, String bufferFlag) {

        ReportGenerateResult result = new ReportGenerateResult(false);
        String out = report.getOutType();


//		//文件类型使用+号连接，如PDF+XLS+TXT如果有一个生成错误所有都需重新生成
        result = this.saveReportByOutType(balanceWaterNo, squadDay, report, out, bufferFlag);

        this.threadSleep();
        return result;


    }

    private void threadSleep() {
        try {
            Thread.sleep(AppConstant.threadSleepTime);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //获取单个清算日的各运营日
    public Vector getSquaddays(String balanceWaterNo, ReportAttribute report) {
        Vector squaddays = new Vector();

        String sql = null;

        //separate table and condition
        String dataTable = report.getDataTable();
        String table = "";
        String condition = "";
        int position = dataTable.lastIndexOf(",");//dataTable.indexOf(",");
        DbHelper dbHelper = null;
        if (position > 0) {
            table = dataTable.substring(0, position);
            condition = dataTable.substring(position + 1, dataTable.length());
        } else {
            table = dataTable;
            condition = "";
        }
        //System.out.println("getSquaddays table:"+table);
        //System.out.println("getSquaddays condition:"+condition);

        //construct sql

        if (ReportBase.isDayReport(report) || ReportBase.isDayReportByCustomed(report)) {
            sql = "select distinct squad_day from " + table
                    + " where substr(balance_water_no,1,8)='" + balanceWaterNo.substring(0, 8) + "' " + condition;

            //squaddays.add(thisDate); //the squadday belongs to the balanceday must be included even if no data
        }

        if (ReportBase.isMonthReport(report)) { //月报
            sql = "select distinct substr(squad_day,1,6) squad_day from "
                    + table + " where substr(balance_water_no,1,6)='"
                    + balanceWaterNo.substring(0, 6) + "' " + condition;
            //squaddays.add(thisMonth); //the squadmonth belongs to the balancemonth must be included even if no data
        }
        if (ReportBase.isMonthReportByCustomed(report)) { //定制月报
            sql = "select distinct substr(squad_day,1,6) squad_day from "
                    + table + " where substr(balance_water_no,1,8)>='"
                    + report.getBeginDate() + "' " + " and substr(balance_water_no,1,8)<='" + report.getEndDate() + "' " + condition;
            //squaddays.add(thisMonth); //the squadmonth belongs to the balancemonth must be included even if no data
        }

        if (ReportBase.isYearReport(report)) { //year report
            sql = "select distinct substr(squad_day,1,4) squad_day from "
                    + table + " where substr(balance_water_no,1,4)='"
                    + balanceWaterNo.substring(0, 4) + "' " + condition;
            //squaddays.add(thisYear); //the squadyear belongs to the balancemonth must be included even if no data
        }


        int count = countInString(condition, "?");

        try {
            boolean result;
            dbHelper = new DbHelper("", AppConstant.REPORT_DBCPHELPER.getConnection());
            if (count > 0) {
                ArrayList values = this.getSqlValues(condition, report);
                result = dbHelper.getFirstDocument(sql, values.toArray());

            } else {
                result = dbHelper.getFirstDocument(sql);

            }
            //logger.info("从中间表获取squad_day参数:"+result);
            while (result) {
                boolean addResult = false;
                String squad = dbHelper.getItemValue("squad_day");
                squaddays.add(squad);

                result = dbHelper.getNextDocument();


            }
            if(squaddays.isEmpty())//如果中间表没有数据，生成当前清算日、月、年的空报表
                this.getDefautSquadDays(balanceWaterNo, squaddays, report);
            //  logger.info("balanceWaterNo:" + balanceWaterNo + ",  " + "squad_day:" + squaddays);
        } catch (Exception e) {
            logger.error("获取运营日 " + report.getDataTable()
                    + " 错误! " + e);
        } finally {
            try {
                if (dbHelper != null) {
                    dbHelper.closeConnection();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return squaddays;
    }

    private void getDefautSquadDays(String balanceWaterNo, Vector squadDays, ReportAttribute report) {
        if (!squadDays.isEmpty()) {
            return;
        }
        if (ReportBase.isDayReport(report) || ReportBase.isDayReportByCustomed(report)) {
            squadDays.add(ReportBase.getDateFromBalanceWaterNo(balanceWaterNo));
            return;
        }
        if (ReportBase.isMonthReport(report) || ReportBase.isMonthReportByCustomed(report)) {
            squadDays.add(ReportBase.getMonthFromBalanceWaterNo(balanceWaterNo));
            return;
        }
        if (ReportBase.isYearReport(report)) {
            squadDays.add(ReportBase.getYearFromBalanceWaterNo(balanceWaterNo));
            return;
        }


    }

    private ArrayList getSqlValues(String condition, ReportAttribute report) {
        String con = condition.replaceAll("and", "#");
        StringTokenizer st = new StringTokenizer(con, "#");
        String token = null;
        ArrayList values = new ArrayList();
        String value = "";


        while (st.hasMoreTokens()) {
            token = st.nextToken();
            //System.out.println("token="+token);
            if (!this.isNeedValue(token)) {
                continue;
            }
            value = this.getTokenValue(token, report);
            //System.out.println("value="+value);
            values.add(value);

        }
        return values;


    }

    private String getTokenValue(String token, ReportAttribute report) {
        if (this.isLine(token)) {
            return report.getLineId();
        }
        if (this.isMainType(token)) {
            return report.getCardMainCode();
        }
        if (this.isSubType(token)) {
            return report.getCardSubCode();
        }
        return "";

    }

    private boolean isNeedValue(String token) {
        if (token == null || token.length() == 0) {
            return false;
        }
        int index = token.indexOf("?");
        if (index != -1) {
            return true;
        }
        return false;
    }

    private boolean isLine(String token) {
        if (token == null || token.length() == 0) {
            return false;
        }
        if (token.indexOf("line") != -1) {
            return true;
        }
        return false;

    }

    private boolean isMainType(String token) {
        if (token == null || token.length() == 0) {
            return false;
        }
        if (token.indexOf("card_main") != -1) {
            return true;
        }
        return false;

    }

    private boolean isSubType(String token) {
        if (token == null || token.length() == 0) {
            return false;
        }
        if (token.indexOf("card_sub") != -1) {
            return true;
        }
        return false;

    }

    public static void main(String[] args) {
        String s = " and line_code=? and card_main_code=? and card_sub_code=?";
        s = s.replaceAll("and", "#");
        StringTokenizer st = new StringTokenizer(s, "#");

        String token;
        ReportWithBalanceAndSquadDay r = new ReportWithBalanceAndSquadDay();


        while (st.hasMoreTokens()) {
            token = st.nextToken();
            System.out.println("token=" + token);
            if (!r.isNeedValue(token)) {
                continue;
            }
            if (r.isLine(token)) {
                System.out.println("isline");
            }
            if (r.isMainType(token)) {
                System.out.println("isMainType");
            }
            if (r.isSubType(token)) {
                System.out.println("isSubType");
            }
        }


    }

    /**
     * count how many subString in mainStr
     */
    private int countInString(String mainStr, String subStr) {
        int count = 0;
        if (mainStr == null || subStr == null) {
            return count;
        }
        if (mainStr.length() == 0 || subStr.length() == 0) {
            return count;
        }
        if (mainStr.length() >= subStr.length()) {
            String main = mainStr;
            int ndx;
            while ((ndx = main.indexOf(subStr)) > 0) {
                count = count + 1;
                main = main.substring(ndx + subStr.length(), main.length());
            }
        }
        return count;
    }
}
