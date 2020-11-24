/*
 * Amendment History:
 *
 * Date          By             Description
 * ----------    -----------    -------------------------------------------
 * 2011-11-28    hejj           重新整理
 */
package com.goldsign.acc.report.handler;

import org.apache.log4j.Logger;

import com.goldsign.acc.report.constant.AppConstant;
import com.goldsign.acc.report.util.ReportLogUtil;

import com.goldsign.acc.report.util.ReportWithBalanceAndSquadDay;
import com.goldsign.acc.report.util.ReportWithBalanceOrSquadDay;
import com.goldsign.acc.report.vo.InitResult;
import com.goldsign.acc.report.vo.LogReportTotalVo;

import com.goldsign.acc.report.vo.Report;
import com.goldsign.acc.report.vo.ReportAttribute;
import com.goldsign.acc.report.vo.ReportGenerateResult;
import com.goldsign.acc.report.vo.ThreadStatus;
import java.util.Vector;

public class ReportGenerator {

    private static ThreadStatus threadStatus = new ThreadStatus();
    private static Logger logger = Logger.getLogger(ReportGenerator.class.getName());
    //在独立线程中生成报表
    protected static Vector errReports = new Vector();

    public static void addErrorReports(ReportAttribute report) {
        synchronized (errReports) {
            errReports.add(report);
        }
    }

    public static Vector getErrorReports() {
        synchronized (errReports) {
            return errReports;
        }
    }

    public static void clearErrorReports() {
        synchronized (ReportGenerator.errReports) {
            ReportGenerator.errReports.clear();
        }
    }

    //在独立线程中生成报表
    public int generateReportForThread(Vector reports, InitResult iResult,
            int threadNum, boolean isRebuild, Vector rebuildReports, String bufferFlag) {
        ReportAttribute report = null;

        ReportGenerateResult result = null;
        Vector squaddayV = new Vector();
        String squadDay = null;
        ReportAttribute ra = null;

        for (int j = 0; j < reports.size(); j++) {
            report = (ReportAttribute) reports.get(j);
            report.setThreadNum(threadNum);//线程数目


            logger.info("线程 " + threadNum + " 开始处理分配的报表:  ");
            if (isRebuild) {
                logger.info("重新生成报表");
            }

            logger.info("生成报表-模板:" + report.getReportModule() + "代码:" + report.getReportCode());
            if (report.getReportType().equals(AppConstant.REPORT_TYPE_DAYTWO)) { //report with balance_day + squad_day清算日＋运营日
                ReportWithBalanceAndSquadDay rpt = new ReportWithBalanceAndSquadDay();
                squaddayV.clear();
                squaddayV.addAll(report.getSquadDayV());
                report.clearSquadDayV();
                if (squaddayV.isEmpty()) {//生成异常时，重生成不再取
                    squaddayV.addAll(rpt.getSquaddays(iResult.getBalanceWaterNo(), report));//从中间表获取同一清算流水的各运营日
                }
                for (int i = 0; i < squaddayV.size(); i++) {
                    squadDay = (String) squaddayV.get(i);
                    // result = rpt.generate(balanceWaterNo, report);
                    result = rpt.generate(iResult.getBalanceWaterNo(), report, squadDay, bufferFlag);//生成一个运营日的报表

                    if (result.getRunResult() == false && result.getRetCode() != 0) {//生成失败，可重新生成的报表
                        ra = new ReportAttribute();
                        this.copyValue(ra, report);
                        ra.setSquadDayV(squadDay);//多运营日时，仅对生成失败的运营日作处理，其他生成正常的运营日，无需作处理
                        this.addReportToRebuild(rebuildReports, ra);

                    }
                    if (result.getRunResult() == false && result.getRetCode() == 0) {//生成失败，作为生成错误的报表，如大报表生成的超时
                        ra = new ReportAttribute();
                        this.copyValue(ra, report);
                        ra.setSquadDayV(squadDay);//多运营日时，仅对生成失败的运营日作处理，其他生成正常的运营日，无需作处理
                        ReportGenerator.addErrorReports(ra);

                    }
                }
            } else { //report with balance_day or squad_day清算日或运营日
                ReportWithBalanceOrSquadDay rpt = new ReportWithBalanceOrSquadDay();
                result = rpt.generate(iResult.getBalanceWaterNo(), report, bufferFlag);

                if (result.getRunResult() == false && result.getRetCode() != 0)//生成失败，可重新生成的报表
                {
                    this.addReportToRebuild(rebuildReports, report);
                }
                if (result.getRunResult() == false && result.getRetCode() == 0)//生成失败，作为生成错误的报表，如大报表生成的超时
                {
                    ReportGenerator.addErrorReports(report);
                }
            }


        }
        return reports.size();


    }

    //在独立线程中生成报表
    @Deprecated
    public int generateReportForThread_delete(Vector reports, String balanceWaterNo,
            int threadNum, boolean isRebuild, Vector rebuildReports, String bufferFlag) {
        ReportAttribute report = null;

        ReportGenerateResult result = null;
        Vector squaddayV = new Vector();
        String squadDay = null;
        ReportAttribute ra = null;

        for (int j = 0; j < reports.size(); j++) {
            report = (ReportAttribute) reports.get(j);
            report.setThreadNum(threadNum);//线程数目
            boolean genReport = false;

            if (report.getPeriodType().equals(AppConstant.REPORT_DAY)) { //day report,日报
                genReport = true;
            }
            if (report.getPeriodType().equals(AppConstant.REPORT_MONTH)) { //month report月报
                if (Report.genMonthReport
                        && (balanceWaterNo.equals(Report.monthReportBalanceWater))) {
                    genReport = true;
                } else {
                    genReport = false;
                }
            }
            if (report.getPeriodType().equals(AppConstant.REPORT_YEAR)) { //day report,年报
                if (Report.genYearReport
                        && (balanceWaterNo.equals(Report.yearReportBalanceWater))) {
                    genReport = true;
                } else {
                    genReport = false;
                }
            }
            if (genReport) {

                logger.info("线程 " + threadNum + " 开始处理分配的报表:  ");
                if (isRebuild) {
                    logger.info("重新生成报表");
                }

                logger.info("生成报表-模板:" + report.getReportModule() + "代码:" + report.getReportCode());
                if (report.getReportType().equals(AppConstant.REPORT_TYPE_DAYTWO)) { //report with balance_day + squad_day清算日＋运营日
                    ReportWithBalanceAndSquadDay rpt = new ReportWithBalanceAndSquadDay();
                    squaddayV.clear();
                    squaddayV.addAll(report.getSquadDayV());
                    report.clearSquadDayV();
                    if (squaddayV.isEmpty()) {
                        squaddayV.addAll(rpt.getSquaddays(balanceWaterNo, report));//从中间表获取同一清算流水的各运营日
                    }
                    for (int i = 0; i < squaddayV.size(); i++) {
                        squadDay = (String) squaddayV.get(i);
                        // result = rpt.generate(balanceWaterNo, report);
                        result = rpt.generate(balanceWaterNo, report, squadDay, bufferFlag);//生成一个运营日的报表

                        if (result.getRunResult() == false && result.getRetCode() != 0) {//生成失败，可重新生成的报表
                            ra = new ReportAttribute();
                            this.copyValue(ra, report);
                            ra.setSquadDayV(squadDay);//多运营日时，仅对生成失败的运营日作处理，其他生成正常的运营日，无需作处理
                            this.addReportToRebuild(rebuildReports, ra);

                        }
                        if (result.getRunResult() == false && result.getRetCode() == 0) {//生成失败，作为生成错误的报表，如大报表生成的超时
                            ra = new ReportAttribute();
                            this.copyValue(ra, report);
                            ra.setSquadDayV(squadDay);//多运营日时，仅对生成失败的运营日作处理，其他生成正常的运营日，无需作处理
                            ReportGenerator.addErrorReports(ra);

                        }
                    }
                } else { //report with balance_day or squad_day清算日或运营日
                    ReportWithBalanceOrSquadDay rpt = new ReportWithBalanceOrSquadDay();
                    result = rpt.generate(balanceWaterNo, report, bufferFlag);

                    if (result.getRunResult() == false && result.getRetCode() != 0)//生成失败，可重新生成的报表
                    {
                        this.addReportToRebuild(rebuildReports, report);
                    }
                    if (result.getRunResult() == false && result.getRetCode() == 0)//生成失败，作为生成错误的报表，如大报表生成的超时
                    {
                        ReportGenerator.addErrorReports(report);
                    }
                }

            }

        }
        return reports.size();


    }

    public static boolean getOneThreadStatus() {
        synchronized (threadStatus) {
            return threadStatus.getIsFinishForThread11();

        }

    }

    public static void restoreOneThreadStatus() {
        synchronized (threadStatus) {
            threadStatus.setIsFinishForThread11(false);

        }

    }

    public void generateReportForError(Vector reports, String balanceWaterNo, int threadNum, String bufferFlag) {

        ReportAttribute report = null;
        long startTime = 0;
        long endTime = 0;
        int count = 0;
        int total = 0;
        ReportGenerateResult result = null;
        Vector squaddayV = new Vector();
        //线程数目
        String squadDay = null;
        ReportAttribute ra = null;
        startTime = System.currentTimeMillis();
        total = reports.size();
        //day report,日报
        for (int j = 0; j < reports.size(); j++) {
            report = (ReportAttribute) reports.get(j);
            report.setThreadNum(threadNum);
            boolean genReport = false;
            squaddayV.clear();
            if (this.isRebuild(j, total)) {
                logger.info("重生成报表");
            }
            logger.info("报表模板:" + report.getReportModule() + "报表代码:" + report.getReportCode());
            if (report.getPeriodType().equals(AppConstant.REPORT_DAY)) //day report,年报
            {
                genReport = true;
            }
            if (report.getPeriodType().equals(AppConstant.REPORT_MONTH)) {
                if (Report.genMonthReport && (balanceWaterNo.equals(Report.monthReportBalanceWater))) {
                    genReport = true;
                } else {
                    genReport = false;
                }
            }
            if (report.getPeriodType().equals(AppConstant.REPORT_YEAR)) {
                if (Report.genYearReport && (balanceWaterNo.equals(Report.yearReportBalanceWater))) {
                    //report with balance_day + squad_day清算日＋运营日
                    genReport = true;
                } else //report with balance_day + squad_day清算日＋运营日
                {
                    genReport = false;
                }
            }
            if (genReport) {
                if (report.getReportType().equals(AppConstant.REPORT_TYPE_DAYTWO)) {
                    ReportWithBalanceAndSquadDay rpt = new ReportWithBalanceAndSquadDay();
                    squaddayV.addAll(report.getSquadDayV());
                    report.clearSquadDayV();
                    if (squaddayV.isEmpty()) {
                        // result = rpt.generate(balanceWaterNo, report);
                        squaddayV.addAll(rpt.getSquaddays(balanceWaterNo, report));
                    }
                    for (int i = 0; i < squaddayV.size(); i++) //生成一个运营日的报表
                    {
                        squadDay = (String) squaddayV.get(i);
                        //生成失败，可重新生成的报表
                        result = rpt.generate(balanceWaterNo, report, squadDay, bufferFlag);
                        if (this.isRebuild(j, total)) //多运营日时，仅对生成失败的运营日作处理，其他生成正常的运营日，无需作处理
                        {
                            continue;
                        }
                        if (result.getRunResult() == false && result.getRetCode() != 0) {
                            ra = new ReportAttribute();
                            //生成失败，作为生成错误的报表，如大报表生成的超时
                            this.copyValue(ra, report);
                            ra.setSquadDayV(squadDay);
                            this.addReportToRebuild(reports, ra);
                        }
                        if (result.getRunResult() == false && result.getRetCode() == 0) {
                            //report with balance_day or squad_day清算日或运营日
                            ra = new ReportAttribute();
                            this.copyValue(ra, report);
                            ra.setSquadDayV(squadDay);
                            ReportGenerator.addErrorReports(ra);
                        }
                    }
                } else {
                    ReportWithBalanceOrSquadDay rpt = new ReportWithBalanceOrSquadDay();
                    //生成失败，作为生成错误的报表，如大报表生成的超时
                    result = rpt.generate(balanceWaterNo, report, bufferFlag);
                    if (this.isRebuild(j, total)) {
                        continue;
                    }
                    if (result.getRunResult() == false && result.getRetCode() != 0) //  endTime = System.currentTimeMillis();
                    {
                        this.addReportToRebuild(reports, report);
                    }
                    if (result.getRunResult() == false && result.getRetCode() == 0) {
                        this.addErrorReports(report);
                    }
                }
                //记录报表错误信息


            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("thread " + threadNum + " total report processed:  " + reports.size() + " spend time total: " + (endTime - startTime) + " ms");
    }

    private LogReportTotalVo logForError(String balanceWaterNo, long startTime, long endTime) {
//		记录汇总日志
        LogReportTotalVo vo = ReportLogUtil.getLogReportTotalVo(balanceWaterNo, startTime, endTime);
        ReportLogUtil.logForDbTotal(vo, AppConstant.LOG_LEVEL_INFO);



        return vo;

    }

    protected void copyValue(ReportAttribute ra, ReportAttribute report) {
        ra.setCardMainCode(report.getCardMainCode());
        ra.setCardSubCode(report.getCardSubCode());
        ra.setDataTable(report.getDataTable());
        ra.setLineId(report.getLineId());
        ra.setOutType(report.getOutType());
        ra.setPeriodType(report.getPeriodType());
        ra.setReportCode(report.getReportCode());
        ra.setReportModule(report.getReportModule());
        ra.setReportName(report.getReportName());
        ra.setReportType(report.getReportType());
        ra.setDsId(report.getDsId());
        ra.setDsUser(report.getDsUser());
        ra.setDsPass(report.getDsPass());
        ra.setThreadNum(report.getThreadNum());
        
        ra.setPeriodDetailType(report.getPeriodDetailType());
        ra.setBeginDate(report.getBeginDate());
        ra.setEndDate(report.getEndDate());
        ra.setGenerateDate(report.getGenerateDate());
    }

    protected void addReportToRebuild(Vector reports, ReportAttribute report) {
        reports.add(report);
    }

    protected boolean isRebuild(int j, int total) {
        if (j >= total) {
            return true;
        }
        return false;
    }
}
