package com.goldsign.acc.report.common;

import com.goldsign.acc.report.constant.AppConstant;
import java.io.File;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.Vector;

import com.goldsign.acc.report.thread.ReportFileThread;
import com.goldsign.acc.report.vo.ReportAttribute;
import com.goldsign.acc.report.vo.ReportGenerateResult;
import com.goldsign.lib.db.util.DbHelper;


import org.apache.log4j.Logger;

public class ReportBase {

    private final String PDF = "pdf";
    private final String XLS = "xls";
    private final String TXT = "txt";
    //protected DbHelper dbHelper =// AppConstant.REPORT_DBCPHELPER.getcon;
    private static Logger logger = Logger.getLogger(ReportBase.class.getName());

    protected boolean setReportParameter(String sql) {
        boolean result = false;
        DbHelper dbHelper = null;

        try {
            dbHelper = new DbHelper("",
                    AppConstant.REPORT_DBCPHELPER.getConnection());
            if (dbHelper.executeUpdate(sql) > 0) {
                System.out.println("setReportParameter ok! sql:" + sql);
                Thread.sleep(5000);		//ensure update effect
                result = true;
            }
        } catch (Exception e) {
            System.out.println("setReportParameter error - " + e);
            logger.error("setReportParameter error - " + e);
            logger.error("setReportParameter sql - " + sql);
            result = false;
        } finally {
            try {
                if (dbHelper != null) {
                    dbHelper.closeConnection();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /*
     protected ReportGenerateResult saveReportAsPdf(String balanceWaterNo,
     String reportDay,
     ReportAttribute report) {
		
     return saveReportAsFile(balanceWaterNo, reportDay, report, PDF);
		
     }
     */

    protected ReportGenerateResult saveReportByOutType(String balanceWaterNo,
            String reportDay,
            ReportAttribute report, String outType, String bufferFlag) {

        ReportGenerateResult retResult = new ReportGenerateResult();
        retResult.setRunResult(true);
        retResult.setRetCode(0);

        Vector outTypes = this.getOutTypeV(outType);
        String ot;
        ReportGenerateResult result;
        for (int i = 0; i < outTypes.size(); i++) {
            ot = (String) outTypes.get(i);
            result = saveReportAsFile(balanceWaterNo, reportDay, report, ot, bufferFlag);

            if (!result.getRunResult()) {
                retResult.setRunResult(false);
                retResult.setRetCode(result.getRetCode());
            }


        }


        return retResult;
        //  }
    }

    private Vector getOutTypeV(String outType) {
        StringTokenizer st = new StringTokenizer(outType, "+");
        Vector outTypes = new Vector();
        while (st.hasMoreTokens()) {
            outTypes.add(st.nextToken().toLowerCase());
        }
        return outTypes;

    }
    /*
     protected ReportGenerateResult saveReportAsTxt(String balanceWaterNo,
     String reportDay,
     ReportAttribute report) {
		
     return saveReportAsFile(balanceWaterNo, reportDay, report, TXT);
		
     }
	
     protected ReportGenerateResult saveReportAsXls(String balanceWaterNo,
     String reportDay,
     ReportAttribute report) {
     return saveReportAsFile(balanceWaterNo, reportDay, report, XLS);
		
     }
     */

    private ReportGenerateResult saveReportAsFile(String balanceWaterNo,
            String reportDay,
            ReportAttribute report,
            String fileType, String bufferFlag) {
        //boolean result = false;
        ReportGenerateResult result = new ReportGenerateResult(false);
        try {
            String reportCode = report.getReportCode();
            String reportModule = report.getReportModule();
            //乌市报表以w_开头
            URL url = new URL(AppConstant.RasUrl + "?reportName=w_rpt_"
                    + reportModule.substring(0, 2) + "_"
                    + reportModule.substring(3, 6) + "&fileType=" + fileType
                    + "&srvName=" + AppConstant.serverName
                    + "&srvUser=" + AppConstant.serverUser
                    + "&srvPass=" + AppConstant.serverPass
                    + "&hdlSrv=" + AppConstant.handleServer);
            String path = AppConstant.FilePath + reportModule;
            File p = new File(path);
            if (!p.exists()) {
                p.mkdirs();
                logger.info("报表路径 '" + path + "' 不存在, 创建!");
            }
            if (reportDay.length() <= 6 && reportDay.length() >= 6) {
                reportDay = reportDay + "00";
            }
            if (reportDay.length() <= 4 && reportDay.length() >= 4) {
                reportDay = reportDay + "0000";
            }
            String file = reportDay + "." + reportCode + "." + balanceWaterNo + "." + fileType;

            AppConstant.Reporting = file;

            ReportFileThread rft = new ReportFileThread(url, path, file, report, balanceWaterNo, reportDay, bufferFlag);

            result = rft.runOneReport();//不启动线程生成报表，因为该函数本身就在线程内

            AppConstant.Reporting = "";

            //result = true;
        } catch (Exception e) {
            System.out.println("saveReportAsFile error - " + e);
        }
        return result;
    }
    
    
    public static boolean isDayReport(ReportAttribute ra) {
        String periodType = ra.getPeriodType();
        if (periodType == null || periodType.length() == 0) {
            return false;
        }

        if (ra.getPeriodType().equals(AppConstant.FLAG_PERIOD_DAY)) {
            return true;
        }
        return false;
    }
    public static boolean isDayReportByCustomed(ReportAttribute ra) {
        String periodType = ra.getPeriodType();
        String generateDate =ra.getGenerateDate();
        if (periodType == null || periodType.length() == 0) {
            return false;
        }
         if (generateDate == null || generateDate.length() == 0) {
            return false;
        }

        if (ra.getPeriodType().equals(AppConstant.FLAG_PERIOD_CUSTOMED)&&generateDate.equals(AppConstant.FLAG_GENERATE_ALL)) {
            return true;
        }
        return false;
    }

    public static boolean isYearReport(ReportAttribute ra) {
        String periodType = ra.getPeriodType();
        if (periodType == null || periodType.length() == 0) {
            return false;
        }

        if (ra.getPeriodType().equals(AppConstant.FLAG_PERIOD_YEAR)) {
            return true;
        }
        return false;
    }
    
    public static boolean isMonthReport(ReportAttribute ra) {
        String periodType = ra.getPeriodType();
        if (periodType == null || periodType.length() == 0) {
            return false;
        }

        if (ra.getPeriodType().equals(AppConstant.FLAG_PERIOD_MONTH)) {
            return true;
        }
        return false;
    }
    public static boolean isTimeToCustomed(ReportAttribute ra,String balanceWaterNo) {
        String day =balanceWaterNo.substring(6, 8);
        String dayReport = ra.getGenerateDate();
        if(dayReport == null || dayReport.length()==0)
            return false;
        if(dayReport.equals(day))
            return true;
        return false;
        
    }
    public static boolean isMonthReportByCustomed(ReportAttribute ra) {
        String periodType = ra.getPeriodType();
        String generateDate =ra.getGenerateDate();
        if (periodType == null || periodType.length() == 0) {
            return false;
        }
        if (generateDate == null || generateDate.length() == 0) {
            return false;
        }

        if (ra.getPeriodType().equals(AppConstant.FLAG_PERIOD_CUSTOMED)&&!generateDate.equals(AppConstant.FLAG_GENERATE_ALL)) {
            return true;
        }
        return false;
    }
    public static String getDateFromBalanceWaterNo(String balanceWaterNo){
        if(balanceWaterNo ==null || balanceWaterNo.length() ==0)
            return "";
        return balanceWaterNo.substring(0,8);
    }
     public static String getMonthFromBalanceWaterNo(String balanceWaterNo){
        if(balanceWaterNo ==null || balanceWaterNo.length() ==0)
            return "";
        return balanceWaterNo.substring(0,6)+"00";
    }
     public static String getYearFromBalanceWaterNo(String balanceWaterNo){
        if(balanceWaterNo ==null || balanceWaterNo.length() ==0)
            return "";
        return balanceWaterNo.substring(0,4)+"0000";
    }
}
