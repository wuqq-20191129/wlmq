/*
 * Amendment History:
 *
 * Date          By             Description
 * ----------    -----------    -------------------------------------------
 * 2005-06-24    Rong Weitao    Create the class
 */
package com.goldsign.acc.report.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

import com.goldsign.acc.report.constant.AppConstant;
import com.goldsign.acc.report.vo.LogReportDetailVo;
import com.goldsign.acc.report.vo.ReportAttribute;
import com.goldsign.acc.report.vo.ReportGenerateResult;
import com.goldsign.acc.report.vo.ThreadStatus;
import com.goldsign.acc.report.util.ReportLogUtil;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

public class ReportFileThread extends Thread {

    private File reportFile;
    private URL url;
    private String path;
    private String file;
    private String retMsg = "com.crystaldecisions.report.web.viewer.ReportExportControl";
    private ReportAttribute reportAttribute;
    private String balanceWaterNo;
    private String squadDay;
    private String bufferFlag;
    private static ThreadStatus threadStatus = new ThreadStatus();
    private static Logger logger = Logger.getLogger(ReportFileThread.class
            .getName());
    private boolean ture;

    public ReportFileThread(URL aUrl, String aPath, String aFile,
            ReportAttribute aReportAttribute, String aBalanceWaterNo, String aSquadDay,
            String bufferFlag) {

        this.url = aUrl;
        this.reportFile = new File(aPath, aFile);
        this.path = aPath;
        this.file = aFile;
        this.reportAttribute = aReportAttribute;
        this.balanceWaterNo = aBalanceWaterNo;
        this.squadDay = aSquadDay;

        this.bufferFlag = bufferFlag;


    }

    public static int getFilesGenerated() {
        synchronized (threadStatus) {
            return threadStatus.getfilesGenerated();
        }

    }

    public static void addFileGenerated() {
        synchronized (threadStatus) {
            threadStatus.addFilesGenerated();
        }

    }

    public static void resetFileGenerated() {
        synchronized (threadStatus) {
            threadStatus.setFilesGenerated(0);
        }

    }

    private ReportGenerateResult getRunResult() {
        FileReader reader = null;
        BufferedReader br = null;
        char[] buffer = new char[1024];
        String content = null;
        ReportGenerateResult result = new ReportGenerateResult();
        try {
            reader = new FileReader(this.reportFile);
            br = new BufferedReader(reader);
            int count = br.read(buffer);
            if (count != -1) {
                content = new String(buffer);
                if (content.indexOf(retMsg) != -1) {
                    logger.info(this.reportFile + "作为大小小于设定值错误需重新生成");
                    result.setRunResult(false);
                    result.setRetCode(1);//
                    return result;
                } else {
                    logger.info(this.reportFile + "作为作为服务器缓存异常错误需重新生成");
                    result.setRunResult(false);
                    result.setRetCode(0);
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
            return result;
        } finally {
            this.finalProcess(reader, br, buffer);
        }

        return result;
    }

    private void finalProcess(FileReader reader, BufferedReader br, char[] buffer) {
        try {
            if (reader != null) {
                reader.close();
            }
            if (br != null) {
                br.close();
            }
            buffer = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private boolean isReportCustomed(ReportAttribute ra){
        if(ra.getPeriodType().equals(AppConstant.FLAG_PERIOD_CUSTOMED))
            return true;
        return false;
    }

    private void setReqParameter(OutputStream out, ReportAttribute ra) {
        String lineId = ra.getLineId();
        String stationId = "";
        String mainType = ra.getCardMainCode();
        String subType = ra.getCardSubCode();
        String balanceDay = this.balanceWaterNo.substring(0, 8);
        String reportDay = this.squadDay;
        String user = ra.getDsUser();
        String pass = ra.getDsPass();
        String param = "in_line_id=" + lineId + "&in_station_id=" + stationId + "&in_squad_day=" + reportDay
                + "&in_balance_day=" + balanceDay + "&in_card_main_id=" + mainType
                + "&in_card_sub_id=" + subType + "&p_user=" + user + "&p_pass=" + pass+"&period_type="+ra.getPeriodType();
        if(this.isReportCustomed(ra)){
            param +="&in_begin_day="+ra.getBeginDate()+"&in_end_day="+ra.getEndDate();
        }             
        
        try {


            out.write(param.getBytes("GBK"));
            out.flush();
            out.close();

            logger.info("传递参数:" + param);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean isErrorReportGenTime(long startTime, long endTime) {
        if ((endTime - startTime) <= AppConstant.MinReportGenTime) {
            return true;
        }
        return false;
    }

    private void setRunResult(ReportGenerateResult result) {
        if (result.getRunResult()) {
            result.setRunResult(false);
        }
        if (result.getRetCode() == -1) {
            result.setRetCode(1);
        }
    }

    public void setRequestAttribute(HttpURLConnection connection) throws Exception {
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", "Mozilla/4.7 [en] (Win98; I)");
        connection.setRequestProperty("Accept-Language", "zh-cn");
        connection.setRequestProperty("Cache-Control", "no-cache");
        connection.setRequestProperty("Connection", "Keep-Alive");
        //	connection.setRequestProperty("Content-Type",
        //	"application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Type",
                "text/html;charset=GBK");

        connection.setDoOutput(true);
        connection.setDoInput(true);
    }

    public void setRequestAttribute(URLConnection connection) throws Exception {

        connection.setRequestProperty("User-Agent", "Mozilla/4.7 [en] (Win98; I)");
        connection.setRequestProperty("Accept-Language", "zh-cn");
        connection.setRequestProperty("Cache-Control", "no-cache");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
//		connection.setRequestProperty("Content-Type",
        //	"text/html;charset=GBK");

        connection.setDoOutput(true);
        connection.setDoInput(true);
    }

    private boolean isTxtFile(String file) {
        if (file.endsWith(".txt")) {
            return true;
        }
        return false;
    }

    public ReportGenerateResult runOneReport() throws IOException {


        InputStream in = null;
        OutputStream out = null;
        OutputStream urlOut = null;
        URLConnection con = null;

        // boolean result = true;
        int total = 0;
        boolean re = true;
        ReportGenerateResult result = new ReportGenerateResult();

        try {

            //logger.info("生成报表-报表模板："+this.reportAttribute.getReportModule()+
            //		"报表代码："+this.reportAttribute.getReportCode());
            long startTime = System.currentTimeMillis();
            out = new FileOutputStream(reportFile);
            //logger.info("报表文件："+reportFile);
            int bytes_read;
            sleep(1000);
            con = (URLConnection) url.openConnection();
            con.setDoOutput(true);
            con.connect();

            urlOut = con.getOutputStream();
            logger.info("Url:" + url);
            this.setReqParameter(urlOut, this.reportAttribute);

            in = con.getInputStream();

            while ((bytes_read = in.read()) != -1) {
                out.write(bytes_read);
                out.flush();
                total++;
            }

            if (!this.isTxtFile(this.file)) {
                if (total <= 1024) {
                    logger.error("报表生成错误，文件" + this.file + " 大小<=1024b");
                    result = this.getRunResult();
                    re = false;
                }
            }
            this.addFileGenerated();

            long endTime = System.currentTimeMillis();
            if (!this.isTxtFile(this.file))//对TXT文件不做检查
            {
                if (this.isErrorReportGenTime(startTime, endTime)) {
                    this.setRunResult(result);//重新生成报表生成时间小于1s的报表
                    logger.error("报表文件-" + this.file + " 用时" + (endTime - startTime) + " 小于最小设定值，作错误处理，将重新生成");
                } else if (re) {
                    logger.info("报表文件-" + this.file + "生成完成，共用时:"
                            + (endTime - startTime) + "ms.");
                }
            }
            //记录生成日志。包括明细信息、汇总信息
            this.log(total, startTime, endTime);//并行刨除异常
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("报表文件-" + this.file + "生成错误 - " + e);
            result.setRunResult(false);
            return result;
        } finally {
            out.flush();
            this.finalProcess(in, out, urlOut);

        }
        return result;
    }

    private void log(int total, long startTime, long endTime) throws Exception {
//		记录生成日志
        LogReportDetailVo vo = ReportLogUtil.getLogReportDetailVo(
                this.reportAttribute.getReportCode(),
                this.file,
                this.reportAttribute.getReportModule(),
                total,
                startTime,
                endTime,
                this.reportAttribute.getThreadNum(),
                1,
                this.balanceWaterNo,
                this.squadDay);
        //明细
        ReportLogUtil.logForDbDetail(vo, AppConstant.LOG_LEVEL_INFO, this.bufferFlag);
        //汇总
        ReportLogUtil.getLogReportTotalVo(this.reportAttribute.getReportCode(),
                this.file,
                this.reportAttribute.getReportModule(),
                total,
                startTime,
                endTime,
                this.reportAttribute.getThreadNum(),
                1,
                this.balanceWaterNo,
                this.squadDay,
                bufferFlag);

      
    }

    private void finalProcess(InputStream in, OutputStream out, OutputStream urlOut) {
        try {
            if (in != null) {
                in.close();
            }
            if (urlOut != null) {
                urlOut.close();
            }
            if (out != null) {
                out.close();
            }



        } catch (Exception e) {
        }

    }
}
