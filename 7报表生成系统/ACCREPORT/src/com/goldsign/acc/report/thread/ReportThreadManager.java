package com.goldsign.acc.report.thread;

import com.goldsign.acc.report.common.ReportBase;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.Set;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.goldsign.acc.report.handler.ReportGenerator;
import com.goldsign.acc.report.constant.AppConstant;
import com.goldsign.acc.report.vo.InitResult;
import com.goldsign.acc.report.vo.ReportAttribute;
import com.goldsign.acc.report.vo.ReportThreadBuffer;
import com.goldsign.acc.report.vo.ThreadTime;

/**
 * <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ReportThreadManager {

    public static TreeSet reportModuleCodesKeys = new TreeSet();
    private static TreeMap reportThreadStatus = new TreeMap();
    private static ThreadTime threadTime = new ThreadTime();
    public static TreeMap reportModuleCodes = new TreeMap();
    public static TreeMap reportModuleCodesQueue = new TreeMap();
    public static TreeMap reportModuleCodesMax = new TreeMap();
    public static TreeMap reportModuleCodesPriority = new TreeMap();
    public static TreeMap reportModuleCodesMonth = new TreeMap();
    public static TreeMap reportModuleCodesQueueMonth = new TreeMap();
    public static TreeMap reportModuleCodesMaxMonth = new TreeMap();
    public static TreeMap reportModuleCodesPriorityMonth = new TreeMap();
    public static TreeMap reportModuleCodesYear = new TreeMap();
    public static TreeMap reportModuleCodesQueueYear = new TreeMap();
    public static TreeMap reportModuleCodesMaxYear = new TreeMap();
    public static TreeMap reportModuleCodesPriorityYear = new TreeMap();


    public ReportThreadManager() {
    }
    private static Logger logger = Logger.getLogger(ReportThreadManager.class.getName());

    public static void startReportThreads(String balanceWaterNo) {
        initReportThreadStatus();
        for (int i = 0; i < AppConstant.threadNum; i++) {
            ReportThread rt = new ReportThread(balanceWaterNo, i);
            rt.setName(new Integer(i).toString());
            rt.setBufferFlag(AppConstant.BUFFER_FLAG_NORMAL);
            rt.start();
        }
    }
     public static void startReportThreads(InitResult iResult) {
        initReportThreadStatus();
        for (int i = 0; i < AppConstant.threadNum; i++) {
            ReportThread rt = new ReportThread(iResult, i);
            rt.setName(new Integer(i).toString());
            rt.setBufferFlag(AppConstant.BUFFER_FLAG_NORMAL);
            rt.start();
        }
    }

    public static void startReportThreadForBigReportSeperate(String balanceWaterNo) {

        if (!reportModuleCodes.isEmpty()) {
            reportModuleCodes.clear();
        }
        if (!reportModuleCodesMonth.isEmpty()) {
            reportModuleCodesMonth.clear();
        }
        if (!reportModuleCodesYear.isEmpty()) {
            reportModuleCodesYear.clear();
        }
        initReportThreadBigReportStatus();

        reportModuleCodes.putAll(reportModuleCodesQueue);
        reportModuleCodesMonth.putAll(reportModuleCodesQueueMonth);
        reportModuleCodesYear.putAll(reportModuleCodesQueueYear);
        for (int i = 0; i < AppConstant.threadNumBigReport; i++) {
            ReportThread rt = new ReportThread(balanceWaterNo, i);
            rt.setName(new Integer(i).toString());
            rt.setBufferFlag(AppConstant.BUFFER_FLAG_NORMAL);
            rt.start();
        }
        logger.info("普通队列处理线程数：" + AppConstant.threadNumBigReport);

    }

    public static void startReportThreadForMaxReportSeperate(String balanceWaterNo) {

        if (!reportModuleCodes.isEmpty()) {
            reportModuleCodes.clear();
        }
        if (!reportModuleCodesMonth.isEmpty()) {
            reportModuleCodesMonth.clear();
        }
        if (!reportModuleCodesYear.isEmpty()) {
            reportModuleCodesYear.clear();
        }
        initReportThreadMaxReportStatus();

        reportModuleCodes.putAll(reportModuleCodesMax);
        reportModuleCodesMonth.putAll(reportModuleCodesMaxMonth);
        reportModuleCodesYear.putAll(reportModuleCodesMaxYear);
        for (int i = 0; i < AppConstant.threadNumMaxReport; i++) {
            ReportThread rt = new ReportThread(balanceWaterNo, i);
            rt.setBufferFlag(AppConstant.BUFFER_FLAG_NORMAL);
            rt.start();
        }
        logger.info("大报表队列处理线程数：" + AppConstant.threadNumMaxReport);

    }

    public static void initThread() {
        threadTime.setStartTime(0);
        reportModuleCodesKeys.clear();
        reportThreadStatus.clear();

        //reportModuleCodes.clear();
        reportModuleCodesQueue.clear();
        reportModuleCodesMax.clear();
        reportModuleCodesPriority.clear();

        reportModuleCodesMonth.clear();
        reportModuleCodesQueueMonth.clear();
        reportModuleCodesMaxMonth.clear();
        reportModuleCodesPriorityMonth.clear();

        reportModuleCodesYear.clear();
        reportModuleCodesQueueYear.clear();
        reportModuleCodesMaxYear.clear();
        reportModuleCodesPriorityYear.clear();

    }

    private static void setReportModuleCodesUnit(String reportModule, ReportAttribute ra, TreeMap map) {
        Vector v = null;
        synchronized (map) {
            if (map.containsKey(reportModule)) {
                v = (Vector) map.get(reportModule);
                v.add(ra);
            } else {
                v = new Vector();
                v.add(ra);
                map.put(reportModule, v);
            }
        }
    }

    public static void setReportModuleCodes(String reportModule, ReportAttribute ra,InitResult iResult) {
        if (ReportBase.isDayReport(ra)) {
            ReportThreadManager.setReportModuleCodesUnit(reportModule, ra, reportModuleCodes);
            return;
        }
        //月报
        if (iResult.isGenMonthReport()&&ReportBase.isMonthReport(ra)) {
            ReportThreadManager.setReportModuleCodesUnit(reportModule, ra, reportModuleCodesMonth);
            return;
        }
        //年报
        if (iResult.isGenYearReport()&&ReportBase.isYearReport(ra)) {
            ReportThreadManager.setReportModuleCodesUnit(reportModule, ra, reportModuleCodesYear);
            return;
        }
        //定制报表 每天生成
        if (ReportBase.isDayReportByCustomed(ra)) {
            ReportThreadManager.setReportModuleCodesUnit(reportModule, ra, reportModuleCodes);
            return;
        }
        //定制报表 定点生成.判断条件：清算日等于配置的生成日期，定点报表一般是时段报表归入月报
        if (ReportBase.isMonthReportByCustomed(ra)&&ReportBase.isTimeToCustomed(ra, iResult.getBalanceWaterNo())) {
            ReportThreadManager.setReportModuleCodesUnit(reportModule, ra, reportModuleCodesMonth);
            return;
        }

    }

    public static void setReportModuleCodesNomalSeperate(String reportModule, ReportAttribute ra) {
        Vector v = null;
        if (!ReportBase.isMonthReport(ra) && !ReportBase.isYearReport(ra)) {
            synchronized (reportModuleCodes) {
                if (reportModuleCodes.containsKey(reportModule)) {
                    v = (Vector) reportModuleCodes.get(reportModule);
                    v.add(ra);
                } else {
                    v = new Vector();
                    v.add(ra);
                    reportModuleCodes.put(reportModule, v);
                }
            }
        } else {
            if (ReportBase.isMonthReport(ra)) {
                synchronized (reportModuleCodesMonth) {
                    if (reportModuleCodesMonth.containsKey(reportModule)) {
                        v = (Vector) reportModuleCodesMonth.get(reportModule);
                        v.add(ra);
                    } else {
                        v = new Vector();
                        v.add(ra);
                        reportModuleCodesMonth.put(reportModule, v);
                    }
                }

            } else {
                if (ReportBase.isYearReport(ra)) {
                    synchronized (reportModuleCodesYear) {
                        if (reportModuleCodesYear.containsKey(reportModule)) {
                            v = (Vector) reportModuleCodesYear.get(reportModule);
                            v.add(ra);
                        } else {
                            v = new Vector();
                            v.add(ra);
                            reportModuleCodesYear.put(reportModule, v);
                        }
                    }


                }
            }
        }
    }

    

    

    public static void setReportModuleCodesQueueSeperate(String reportModule, ReportAttribute ra) {
        Vector v = null;
        if (!ReportBase.isMonthReport(ra) && !ReportBase.isYearReport(ra)) {
            synchronized (reportModuleCodesQueue) {
                if (reportModuleCodesQueue.containsKey(reportModule)) {
                    v = (Vector) reportModuleCodesQueue.get(reportModule);
                    v.add(ra);
                } else {
                    v = new Vector();
                    v.add(ra);
                    reportModuleCodesQueue.put(reportModule, v);
                }
            }
        } else {
            if (ReportBase.isMonthReport(ra)) {
                synchronized (reportModuleCodesQueueMonth) {
                    if (reportModuleCodesQueueMonth.containsKey(reportModule)) {
                        v = (Vector) reportModuleCodesQueueMonth.get(reportModule);
                        v.add(ra);
                    } else {
                        v = new Vector();
                        v.add(ra);
                        reportModuleCodesQueueMonth.put(reportModule, v);
                    }
                }

            } else {
                if (ReportBase.isYearReport(ra)) {
                    synchronized (reportModuleCodesQueueYear) {
                        if (reportModuleCodesQueueYear.containsKey(reportModule)) {
                            v = (Vector) reportModuleCodesQueueYear.get(reportModule);
                            v.add(ra);
                        } else {
                            v = new Vector();
                            v.add(ra);
                            reportModuleCodesQueueYear.put(reportModule, v);
                        }
                    }


                }
            }
        }
    }

    public static void setReportModuleCodesPrioritySeperate(String reportModule, ReportAttribute ra) {
        Vector v = null;
        if (!ReportBase.isMonthReport(ra) && !ReportBase.isYearReport(ra)) {
            synchronized (reportModuleCodesPriority) {
                if (reportModuleCodesPriority.containsKey(reportModule)) {
                    v = (Vector) reportModuleCodesPriority.get(reportModule);
                    v.add(ra);
                } else {
                    v = new Vector();
                    v.add(ra);
                    reportModuleCodesPriority.put(reportModule, v);
                }
            }
        } else {
            if (ReportBase.isMonthReport(ra)) {
                synchronized (reportModuleCodesPriorityMonth) {
                    if (reportModuleCodesPriorityMonth.containsKey(reportModule)) {
                        v = (Vector) reportModuleCodesPriorityMonth.get(reportModule);
                        v.add(ra);
                    } else {
                        v = new Vector();
                        v.add(ra);
                        reportModuleCodesPriorityMonth.put(reportModule, v);
                    }
                }
            } else if (ReportBase.isYearReport(ra)) {
                synchronized (reportModuleCodesPriorityYear) {
                    if (reportModuleCodesPriorityYear.containsKey(reportModule)) {
                        v = (Vector) reportModuleCodesPriorityYear.get(reportModule);
                        v.add(ra);
                    } else {
                        v = new Vector();
                        v.add(ra);
                        reportModuleCodesPriorityYear.put(reportModule, v);
                    }
                }
            }
        }
    }

    public static void setReportModuleCodesMaxSeperate(String reportModule, ReportAttribute ra) {
        Vector v = null;
        if (!ReportBase.isMonthReport(ra) && !ReportBase.isYearReport(ra)) {
            synchronized (reportModuleCodesMax) {
                if (reportModuleCodesMax.containsKey(reportModule)) {
                    v = (Vector) reportModuleCodesMax.get(reportModule);
                    v.add(ra);
                } else {
                    v = new Vector();
                    v.add(ra);
                    reportModuleCodesMax.put(reportModule, v);
                }
            }
        } else {
            if (ReportBase.isMonthReport(ra)) {
                synchronized (reportModuleCodesMaxMonth) {
                    if (reportModuleCodesMaxMonth.containsKey(reportModule)) {
                        v = (Vector) reportModuleCodesMaxMonth.get(reportModule);
                        v.add(ra);
                    } else {
                        v = new Vector();
                        v.add(ra);
                        reportModuleCodesMaxMonth.put(reportModule, v);
                    }
                }

            } else {
                if (ReportBase.isYearReport(ra)) {
                    synchronized (reportModuleCodesMaxYear) {
                        if (reportModuleCodesMaxYear.containsKey(reportModule)) {
                            v = (Vector) reportModuleCodesMaxYear.get(reportModule);
                            v.add(ra);
                        } else {
                            v = new Vector();
                            v.add(ra);
                            reportModuleCodesMaxYear.put(reportModule, v);
                        }
                    }

                }
            }
        }
    }

    public static boolean isTimeForQueue() {
        synchronized (reportModuleCodesQueue) {
            if (reportModuleCodesQueue.isEmpty()) {
                return false;
            }
        }


        long interval = 0;
        synchronized (threadTime) {
            if (threadTime.getStartTime() == 0) {
                threadTime.setStartTime(System.currentTimeMillis());
                return true;
            }
            threadTime.setEndTime(System.currentTimeMillis());
            interval = threadTime.getEndTime() - threadTime.getStartTime();
            if (interval >= AppConstant.threadDelayTime) {
                threadTime.setStartTime(System.currentTimeMillis());
                return true;
            } else {
                return false;
            }
        }

    }

    public static boolean isForPriority() {
        synchronized (reportModuleCodesPriority) {
            if (reportModuleCodesPriority.isEmpty()) {
                return false;
            } else {
                return true;
            }
        }
    }

    private static String getFirstKey(TreeMap reportModuleCodes) {
        synchronized (reportModuleCodesKeys) {
            if (reportModuleCodesKeys.isEmpty()) {
                reportModuleCodesKeys.addAll(reportModuleCodes.keySet());
            }
            String key = (String) reportModuleCodesKeys.first();
            reportModuleCodesKeys.remove(key);
            return key;
        }


    }

    public static ReportThreadBuffer getReportModuleCodesForThread() {
        // if(isTimeForQueue())
        //  return getReportModuleCodesQueueForThread();
        //  if(isForPriority())
        //    return getReportModuleCodesPriorityForThread();


        ReportThreadBuffer rtb = new ReportThreadBuffer();
        Vector v;//=new Vector();
        String firstReportModule = null;
        Vector reportCodes;
        synchronized (reportModuleCodes) {
            if (reportModuleCodes.isEmpty()) {
                if (!reportModuleCodesMonth.isEmpty()) {
                    reportModuleCodes.putAll(reportModuleCodesMonth);
                    reportModuleCodesMonth.clear();
                } else {
                    if (!reportModuleCodesYear.isEmpty()) {
                        reportModuleCodes.putAll(reportModuleCodesYear);
                        reportModuleCodesYear.clear();
                    } else {
                        return rtb;
                    }

                }
            }
            firstReportModule = getFirstKey(reportModuleCodes);//(String)reportModuleCodes.firstKey();
            reportCodes = (Vector) reportModuleCodes.get(firstReportModule);
            v = getFirstModuleCode(reportCodes);
            // v.addAll((Vector)reportModuleCodes.get(firstReportModule));
            //每个报表逐个放入线程生成而不是按模板放入线程
            rtb.setReportModule(firstReportModule);
            rtb.setReports(v);
            if (reportCodes.isEmpty()) {
                reportModuleCodes.remove(firstReportModule);
            }

        }
        return rtb;
    }

    private static Vector getFirstModuleCode(Vector reportCodes) {
        Vector v = new Vector();
        v.add(reportCodes.get(0));
        reportCodes.remove(0);
        return v;
    }

    public static ReportThreadBuffer getReportModuleCodesQueueForThread() {
        ReportThreadBuffer rtb = new ReportThreadBuffer();
        Vector v = new Vector();
        String firstReportModule = null;
        synchronized (reportModuleCodesQueue) {
            if (reportModuleCodesQueue.isEmpty()) {
                return rtb;
            }
            firstReportModule = (String) reportModuleCodesQueue.firstKey();
            v.addAll((Vector) reportModuleCodesQueue.get(firstReportModule));
            rtb.setReportModule(firstReportModule);
            rtb.setReports(v);
            reportModuleCodesQueue.remove(firstReportModule);

        }
        return rtb;
    }

    public static ReportThreadBuffer getReportModuleCodesPriorityForThread() {
        ReportThreadBuffer rtb = new ReportThreadBuffer();
        Vector v = new Vector();
        String firstReportModule = null;
        synchronized (reportModuleCodesPriority) {
            if (reportModuleCodesPriority.isEmpty()) {
                return rtb;
            }
            firstReportModule = (String) reportModuleCodesPriority.firstKey();
            v.addAll((Vector) reportModuleCodesPriority.get(firstReportModule));
            rtb.setReportModule(firstReportModule);
            rtb.setReports(v);
            reportModuleCodesPriority.remove(firstReportModule);

        }
        return rtb;
    }

    public static void initReportThreadStatus() {
        reportThreadStatus.clear();
        for (int i = 0; i < AppConstant.threadNum; i++) {
            reportThreadStatus.put(new Integer(i), new Boolean(false));
        }
    }

    public static void initReportThreadBigReportStatus() {
        reportThreadStatus.clear();
        for (int i = 0; i < AppConstant.threadNumBigReport; i++) {
            reportThreadStatus.put(new Integer(i), new Boolean(false));
        }
    }

    public static void initReportThreadMaxReportStatus() {
        reportThreadStatus.clear();
        for (int i = 0; i < AppConstant.threadNumMaxReport; i++) {
            reportThreadStatus.put(new Integer(i), new Boolean(false));
        }
    }

    public static void setReportThreadStatus(int i, boolean isFinish) {
        synchronized (reportThreadStatus) {
            reportThreadStatus.put(new Integer(i), new Boolean(isFinish));

        }
    }

    public static boolean isAllThreadFinish() {
        boolean isAllFinish = true;
        Set ts = null;
        Iterator it = null;
        Integer key = null;
        Boolean value = null;
        synchronized (reportThreadStatus) {
            ts = (Set) reportThreadStatus.keySet();
            it = ts.iterator();
            while (it.hasNext()) {
                key = (Integer) it.next();
                value = (Boolean) reportThreadStatus.get(key);
                if (value.booleanValue() == false) {
                    return false;
                }

            }


        }
        return isAllFinish;

    }

    public static boolean isAllThreadBigReportFinish() {
        boolean isAllFinish = true;
        Integer key = null;
        Boolean value = null;
        Integer threadnum = null;
        synchronized (reportThreadStatus) {
            for (int i = 0; i < AppConstant.threadNumBigReport; i++) {
                key = new Integer(i);
                value = (Boolean) reportThreadStatus.get(key);
                if (value.booleanValue() == false) {
                    return false;
                }

            }



        }
        return isAllFinish;

    }

    public static boolean isAllThreadMaxReportFinish() {
        boolean isAllFinish = true;
        Integer key = null;
        Boolean value = null;
        Integer threadnum = null;
        synchronized (reportThreadStatus) {
            for (int i = 0; i < AppConstant.threadNumMaxReport; i++) {
                key = new Integer(i);
                value = (Boolean) reportThreadStatus.get(key);
                if (value.booleanValue() == false) {
                    return false;
                }

            }



        }
        return isAllFinish;

    }

    public static void printTreeMapInfo(TreeMap tm) {
        if (tm.isEmpty()) {
            return;
        }
        Set ts = (Set) tm.keySet();
        Iterator it = ts.iterator();
        String key = null;
        Vector value = null;
        ReportAttribute v = null;
        String tmp = "";
        int total = 0;
        while (it.hasNext()) {
            key = (String) it.next();
            value = (Vector) tm.get(key);
            logger.info("报表模板:" + key);
            tmp = "";
            for (int i = 0; i < value.size(); i++) {
                v = (ReportAttribute) value.get(i);
                tmp += v.getReportCode() + "	";

            }
            total += value.size();
            logger.info("报表代码,共" + value.size() + "个" + ":" + tmp);
            logger.info("---------------------------");


        }

        logger.info("== 共" + ts.size() + " 个报表模板,共 " + total + " 个报表代码 ==");

    }

    public static int getTreeMapReportNum(TreeMap tm) {
        if (tm.isEmpty()) {
            return 0;
        }
        Set ts = (Set) tm.keySet();
        Iterator it = ts.iterator();
        String key = null;
        Vector value = null;
        ReportAttribute v = null;
        int n = 0;
        while (it.hasNext()) {
            key = (String) it.next();
            value = (Vector) tm.get(key);
//			System.out.println("reportModue="+key);
            n += value.size();
        }
        return n;

    }
}
