package com.goldsign.acc.report.thread;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.goldsign.acc.report.handler.ReportGenerator;
import com.goldsign.acc.report.constant.AppConstant;
import com.goldsign.acc.report.vo.InitResult;
import com.goldsign.acc.report.vo.ReportGenerateResult;
import com.goldsign.acc.report.vo.ReportThreadBuffer;

public class ReportThread extends Thread {

    private Vector reports = null;
    private String balanceWaterNo = null;
    private int threadNum = 0;
    private long startTime = 0;
    private long endTime = 0;
    private String bufferFlag;
    private Vector rebuildReports = new Vector();
    private InitResult iResult =null;
    ;
  private static Logger logger = Logger.getLogger(ReportThread.class.getName());
    private int maxcount;

    public ReportThread(Vector reports, String balanceNo, int num) {
        this.reports = reports;
        this.balanceWaterNo = balanceNo;
        this.threadNum = num;

    }

    public ReportThread(String balanceNo, int num) {
        this.balanceWaterNo = balanceNo;
        this.threadNum = num;

    }
        public ReportThread(InitResult iResult, int num) {
        this.iResult = iResult;
        this.threadNum = num;

    }

    public void setBufferFlag(String bufferFlag) {
        this.bufferFlag = bufferFlag;
    }

    public void run() {
        ReportThreadBuffer rtb = null;
        String reportModule = null;
        ReportGenerator gen = null;
        int total = 0;
        long startTime = 0;
        long endTime = 0;
        int count = 0;

        startTime = System.currentTimeMillis();
        //处理管理器分配的报表
        while (true) {
            rtb = ReportThreadManager.getReportModuleCodesForThread();
            reports = rtb.getReports();
            reportModule = rtb.getReportModule();
            if (reports.isEmpty()) {
                break;
            } else {
            }

            gen = new ReportGenerator();
            total += gen.generateReportForThread(this.reports, iResult, threadNum, false, this.rebuildReports, this.bufferFlag);

        }
        //线程生成报表时，如出现错误，且该报表可重新生成，处理时，该报表会放入重生成缓存
        //在分配给该线程的所有报表完成后，线程开始处理可重生成报表，最多处理2次
        ReportGenerateResult result = null;
        //int MaxCount=-1;
        maxcount = AppConstant.maxCount;//最大重生成次数

        this.setBufferFlag(AppConstant.BUFFER_FLAG_REGEN);//设置处理重新生成的缓存标志
        while (!this.rebuildReports.isEmpty() && count < maxcount) {
            count++;
            logger.info("线程 " + threadNum + "第" + count + "次" + " 重生成报表");
            this.reports.clear();
            this.reports.addAll(this.rebuildReports);
            this.rebuildReports.clear();
            gen.generateReportForThread(this.reports, iResult, threadNum, true,
                    this.rebuildReports, this.bufferFlag);


        }

        ReportThreadManager.setReportThreadStatus(this.threadNum, true);

        endTime = System.currentTimeMillis();
        logger.info("线程 " + threadNum + " 共处理报表:  "
                + total + "张，共用时: "
                + (endTime - startTime) + " ms");




    }

    public void delayThread(String reportModule, long delayTime) {
        System.out.println("thread " + threadNum + " delay "
                + (delayTime / 1000)
                + " seconds to process report module " + reportModule);
        try {
            this.sleep(delayTime);
            System.out.println("thread " + threadNum + " begin to process module " + reportModule);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
