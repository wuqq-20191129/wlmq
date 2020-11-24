/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.util;

import com.crystaldecisions.report.web.viewer.ReportExportControl;
import com.crystaldecisions.sdk.framework.CrystalEnterprise;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.crystaldecisions.sdk.framework.ISessionMgr;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;
import com.crystaldecisions.sdk.occa.infostore.IInfoStore;
import com.crystaldecisions.sdk.occa.managedreports.IReportSourceFactory;
import com.crystaldecisions.sdk.occa.report.data.ConnectionInfo;
import com.crystaldecisions.sdk.occa.report.data.ConnectionInfos;
import com.crystaldecisions.sdk.occa.report.data.Fields;
import com.crystaldecisions.sdk.occa.report.data.ParameterField;
import com.crystaldecisions.sdk.occa.report.data.ParameterFieldDiscreteValue;
import com.crystaldecisions.sdk.occa.report.data.Values;
import com.crystaldecisions.sdk.occa.report.exportoptions.ExcelExportFormatOptions;
import com.crystaldecisions.sdk.occa.report.exportoptions.ExportOptions;
import com.crystaldecisions.sdk.occa.report.exportoptions.IExcelExportFormatOptions;
import com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat;
import com.crystaldecisions.sdk.occa.report.reportsource.IReportSource;
import com.goldsign.acc.frame.constant.DBConstant;
import com.goldsign.acc.frame.thread.ReportThreadForRT;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class ReportUtil {

    private static Logger logger = Logger.getLogger(ReportUtil.class);

    public final static String EXCEL = "xls";
    public final static String PDF = "pdf";
    private final static String NAME_SESSION = "secEnterprise";// 会话名称
    private final static String NAME_STORE = "InfoStore";// 模板库名称

    private final static String NAME_SOURCE_FACTORY = "PSReportFactory";// 报表源厂名称
    private final static String NAME_EXPORT_CONTROL = "ExportControl";// 导出控件名称
    private final static String URL_REPORT = "jsp/report/reportGenerator.jsp";
    // excel文件后缀
    public static String REPORT_XSL_SUFFIX = ".xls";
    // pdf文件后缀
    public static String REPORT_PDF_SUFFIX = ".pdf";
//	模板参数名称
    public static String REPORT_TEMPLATE_NAME = "template";
//	需去掉'-'分隔符的日期控件
//	public static String[] DATE_CONTROLS = { "@p_begin_day", "@p_end_day" };
    public static String[] DATE_CONTROLS = {"p_begin_day", "p_end_day"};
    //单据基本路径
    public static String BASE_PATH_BILL = "/report/bills/";

    public String RAGetReportUrlContext(HttpServletRequest request)
            throws Exception {
        String reportUrl = "";
        String reqUrl = request.getRequestURL().toString();
        String contextPath = request.getContextPath();

        int index = reqUrl.indexOf(contextPath);
        index = reqUrl.indexOf("/", index + 1);
        reportUrl = reqUrl.substring(0, index + 1);
        return reportUrl;
    }

    public String RPGetDestinationFileName(HttpServletRequest request,
            String templateName) throws Exception {
        String desFileName = "";
        desFileName = this.RPGetDestinationPath(request) + templateName
                + this.RPGetReportParamtersText(request);
        return desFileName;
    }

    private String RPGetReportParamtersText(HttpServletRequest request) {
        String parameterNames = request.getParameter("parameterNames");

        if (parameterNames == null || parameterNames.length() == 0) {
            return "";
        }
        StringTokenizer st = new StringTokenizer(parameterNames, "#");
        String name = "";
        String value = "";
        String text = "";
        while (st.hasMoreTokens()) {
            name = st.nextToken();
            value = request.getParameter(name);
            if (value == null || value.trim().length() == 0) {
                value = "";
                continue;
            }
            if (this.isDateControl(name)) {
                value = this.trimDate(value);// value.replaceAll("-","");
            }
            text += "." + value;
        }

        //User user = (User) request.getSession().getAttribute("User");
        String operator = PageControlUtil.getOperatorFromSession(request);
        text = "." + operator + text;
        // end
        return text;
    }

    private String trimDate(String date) {

        if (date == null || date.length() == 0) {
            return "";
        }
        return date.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");

    }

    private boolean isDateControl(String controlName) {
        String cn;
        for (int i = 0; i < DATE_CONTROLS.length; i++) {
            cn = DATE_CONTROLS[i];
            if (cn.equals(controlName)) {
                return true;
            }
        }
        return false;

    }

    private String RPGetDestinationPath(HttpServletRequest request)
            throws Exception {
        String destPath = this.getContextRealPath(request);// request.getSession().getServletContext().getRealPath(request.getContextPath());
        // logger.info("contextPath:"+request.getContextPath());
        destPath += ReportUtil.BASE_PATH_BILL;
        this.createDir(destPath);
        return destPath;

    }

    private void createDir(String destPath) {
        File path = new File(destPath);
        if (!path.exists()) {
            path.mkdirs();
        }

    }

    private String getContextRealPath(HttpServletRequest request) {
        String contextPath = request.getSession().getServletContext()
                .getRealPath(request.getContextPath());
        String pathDelim = System.getProperty("file.separator");
        int index = contextPath.lastIndexOf(pathDelim);
        contextPath = contextPath.substring(0, index);
        // contextPath =contextPath.replaceAll(pathDelim,"/");
        return contextPath;

    }

    public void genReportFileByThread(String user, String password, String server,
            String reportName, HttpServletRequest request,
            HttpServletResponse response, ServletContext context,
            String fileType, String paramName, long maxWaitTime,String dbUser,String dbPass) throws Exception {

       // TicketStorageReportUser userReport = this.getDbLoginInfo();
        ReportThreadForRT t = new ReportThreadForRT(user, password, server, reportName, request, response, context, fileType, paramName, dbUser,dbPass);
        t.start();
        logger.info("报表线程启动");
        try {
            t.join(maxWaitTime);
            if (!t.getStatus().getIsFinished()) {
                t.stop();
                response.getWriter().print("生成导出数据超过" + maxWaitTime / 1000 + "秒");
                //t.destroy();
                logger.info("报表线程中断");

            }
            logger.info("报表线程完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPass(String pass) {
//        Encryption en = new Encryption();
//        System.out.println(en.biDecrypt(DBConstant.EN_KEY, pass) + "===============");
//        return en.biDecrypt(DBConstant.EN_KEY, pass);
        //20181217 moqf 改为使用com.goldsign.login.util.Encryption
        return com.goldsign.login.util.Encryption.biDecrypt(pass);

    }

    public void genReportFile(String reportFile, String templateName,
            String fileType, String reportContext, HashMap params,String dbUser,String dbPass)
            throws Exception {
        InputStream in = null;
        OutputStream out = null;
        OutputStream urlOut = null;
        URLConnection con = null;
        URL url = null;
        long total = 0;

        try {
            out = new FileOutputStream(reportFile);
            int bytes_read;
            url = new URL(reportContext + URL_REPORT);
            con = (URLConnection) url.openConnection();

            con.setDoOutput(true);
            //	con.connect();

            urlOut = con.getOutputStream();

            this.setReqParameters(urlOut, templateName, fileType, params,dbUser,dbPass);

            in = con.getInputStream();

            while ((bytes_read = in.read()) != -1) {
                out.write(bytes_read);
                out.flush();
                total++;
            }

        } catch (Exception e) {
            this.handleException(e);
            throw e;

        } finally {
            this.finalProcess(in, out, urlOut);
        }

    }

    private void finalProcess(InputStream in, OutputStream out,
            OutputStream urlOut) {
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

    private void handleException(Exception e) {
        e.printStackTrace();
        logger.error(e);

    }

    public void exportReport(String user, String password, String server,
            String reportName, HttpServletRequest request,
            HttpServletResponse response, ServletContext context,
            String fileType, String paramNames, String dbUser,String dbPass) throws Exception {
        // this.getReportParameter(request,paramNames);

        // 会话相关对象
        ISessionMgr sessionMgr = null;// 会话管理器

        IEnterpriseSession enterpriseSession = null;// 会话
        // 模板库

        IInfoStore iStore;
        // 报表数据源相关对象。

        IReportSourceFactory factoryPS;// 报表源厂
        IReportSource reportSource = null;// 报表源

        ReportExportControl exportControl = new ReportExportControl();// 导出控件

        try {
            sessionMgr = CrystalEnterprise.getSessionMgr();
            enterpriseSession = sessionMgr.logon(user, password, server,
                    NAME_SESSION);// 创建会话

            iStore = (IInfoStore) enterpriseSession.getService(NAME_STORE);// 获取模板库

            factoryPS = (IReportSourceFactory) enterpriseSession// 获取报表源厂
                    .getService(NAME_SOURCE_FACTORY);

            reportSource = this.getReportSource(iStore, factoryPS, reportName);// 获取报表源

            this.setExportAttribute(exportControl, request,
                    reportSource, enterpriseSession, fileType, paramNames, dbUser,dbPass);// 报表导出控件

            exportControl.processHttpRequest(request, response, context, null);// 获取生成报表的流

            // response.getWriter().
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print(e);
            throw e;

        } finally {
            this.freeResource(exportControl, reportSource, enterpriseSession);
        }

    }

    public void freeResource(ReportExportControl exportControl,
            IReportSource reportSource, IEnterpriseSession enterpriseSession) {
        if (exportControl != null) {
            exportControl.dispose();
            exportControl = null;
        }
        if (reportSource != null) {
            reportSource.dispose();
            reportSource = null;
        }

        if (enterpriseSession != null) {

            enterpriseSession.logoff();

            enterpriseSession = null;

        }

    }

    private IReportSource getReportSource(IInfoStore iStore,
            IReportSourceFactory factoryPS, String reportName) throws Exception {
        try {

            String query = "Select SI_ID From CI_INFOOBJECTS Where SI_NAME = '"
                    + reportName + "' and SI_INSTANCE = 0 ";
            IInfoObjects result = iStore.query(query);
            if (result.isEmpty()) {
                throw new Exception("模板库中不存在报表模板" + reportName);
            }
            IInfoObject firstResult = (IInfoObject) result.get(0);
            if (firstResult == null) {
                throw new Exception("模板库中不存在报表模板" + reportName);
            }

            IReportSource reportSource = factoryPS.openReportSource(
                    (firstResult), Locale.ENGLISH);

            return reportSource;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
    private void setExportAttribute(ReportExportControl exportControl,
            HttpServletRequest request,
            IReportSource reportSource, IEnterpriseSession enterpriseSession,
            String fileType, String paramNames, String dbUser,String dbPass) throws Exception {
        ReportExportFormat exportFormat = null;
        ExportOptions exportOptions = new ExportOptions();



        if (fileType.equals(EXCEL)) {
            exportFormat = ReportExportFormat.MSExcel;

            //exportFormat = ReportExportFormat.recordToMSExcel;
        } else {
            exportFormat = ReportExportFormat.PDF;

        }

        exportOptions.setExportFormatType(exportFormat);// 导出格式    
        if (fileType.equals(EXCEL)) {
            IExcelExportFormatOptions efo = new ExcelExportFormatOptions();
            efo.setShowGridlines(true);
            exportOptions.setFormatOptions(efo);

        }

        Fields fields = this.getReportParameter(request, paramNames);// 参数值



        //测试参数输入用户名、密码


        this.setDbInfo(exportControl, dbUser,dbPass);
        exportControl.setReportSource(reportSource);// 设置导出报表源


        //		测试参数输入用户名、密码


        //this.getServerDbInfo(exportControl);
        exportControl.setEnterpriseLogon(enterpriseSession);// 设置导出的会话

        exportControl.setExportOptions(exportOptions);// 设置导出选项

        exportControl.setParameterFields(fields);// 设置导出模板需要的参数值

        exportControl.setName(NAME_EXPORT_CONTROL);

        exportControl.setExportAsAttachment(true);// 导出作为附件


    }
    private void setDbInfo(ReportExportControl exportControl, String dbUser,String dbPass) throws Exception {

        //TicketStorageReportUser user = this.getDbLoginInfo();
        ConnectionInfo connectionInfo = new ConnectionInfo();
        connectionInfo.setUserName(dbUser);
        connectionInfo.setPassword(dbPass);
        //如果需设置子报表的信息
        //PropertyBag attr = new PropertyBag();

        //attr.put(PropertyBagHelper.CONNINFO_SERVER_NAME,serverName);
        //attr.put(PropertyBagHelper.CONNINFO_DATABASE_NAME,dbName);

        ConnectionInfos connectionInfos = new ConnectionInfos();
        connectionInfos.add(connectionInfo);
        exportControl.setDatabaseLogonInfos(connectionInfos);

        exportControl.setEnableLogonPrompt(false);

    }
    private Fields getReportParameter(HttpServletRequest request,
            String paramNames) {

        Fields fields = new Fields();

        //System.out.println("paramNames:" + paramNames);
        StringTokenizer st = new StringTokenizer(paramNames, "#");
        String name;
        String value;
        while (st.hasMoreTokens()) {
            name = st.nextToken();
            value = request.getParameter(name);
            if (value == null) {
                value = "";
            }
            logger.info(name + "=" + value);
            this.setParameterFieldDiscreteValue(fields, name, value);
        }

        return fields;

    }
    private void setParameterFieldDiscreteValue(Fields fields,
            String paramName, String value) {
        Values values = new Values();
        ParameterFieldDiscreteValue pValue = new ParameterFieldDiscreteValue();
        pValue.setValue(value);
        values.add(pValue);

        ParameterField pField = new ParameterField();
        pField.setName(paramName);
        pField.setCurrentValues(values);
        fields.add(pField);



    }

    private void setReqParameters(OutputStream out, String reportName,
            String fileType, HashMap params,String dbUser,String dbPass) throws Exception {
        if (params.isEmpty()) {
            return;
        }
        Set keys = params.keySet();
        Iterator it = keys.iterator();
        String key;
        String value;
        String param = "reportName=" + reportName + "&fileType=" + fileType+
                "&dbUser="+dbUser+"&dbPass="+dbPass
                + "&";
        String paramNames = "";
        while (it.hasNext()) {
            key = (String) it.next();
            value = (String) params.get(key);
            param += key + "=" + value + "&";
            paramNames += key + "#";

        }
        param += "paramNames=" + paramNames;
        logger.info("param=" + param);

        try {

            out.write(param.getBytes("GBK"));
            out.flush();
            out.close();
        } catch (UnsupportedEncodingException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }

    }

    public String RPGetDestinationFileNameOnly(HttpServletRequest request,
            String templateName) throws Exception {
        String desFileName = "";
        desFileName = templateName
                + this.RPGetReportParamtersText(request);
        return desFileName;
    }

}
