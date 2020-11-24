/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.report.proxy;

import java.util.Locale;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crystaldecisions.report.web.viewer.ReportExportControl;

import com.crystaldecisions.sdk.framework.CrystalEnterprise;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.crystaldecisions.sdk.framework.ISessionMgr;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;
import com.crystaldecisions.sdk.occa.infostore.IInfoStore;
import com.crystaldecisions.sdk.occa.managedreports.IReportSourceFactory;
import com.crystaldecisions.sdk.occa.report.application.ReportClientDocument;
import com.crystaldecisions.sdk.occa.report.data.Fields;
import com.crystaldecisions.sdk.occa.report.exportoptions.ExportOptions;
import com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat;
import com.crystaldecisions.sdk.occa.report.lib.ReportSDKException;
import com.crystaldecisions.sdk.occa.report.reportsource.IReportSource;
import com.crystaldecisions.sdk.occa.report.data.ConnectionInfo;
import com.crystaldecisions.sdk.occa.report.data.ConnectionInfos;
import com.crystaldecisions.sdk.occa.report.data.ParameterField;
import com.crystaldecisions.sdk.occa.report.data.ParameterFieldDiscreteValue;
import com.crystaldecisions.sdk.occa.managedreports.IReportAppFactory;
import com.crystaldecisions.sdk.occa.report.application.OpenReportOptions;

import com.crystaldecisions.sdk.occa.report.data.Values;
import com.goldsign.acc.report.proxy.thread.ReportThread;
import com.goldsign.acc.report.proxy.util.Encryption;
import com.goldsign.acc.report.proxy.vo.SynControl;
import com.crystaldecisions.sdk.occa.report.exportoptions.ExcelExportFormatOptions;
import com.crystaldecisions.sdk.occa.report.exportoptions.IExcelExportFormatOptions;

/**
 *
 * @author hejj
 */
public class ReportProxy {

    public static String REPORT_SERVER_PAGE_SERVER = "0";
    public static String REPORT_SERVER_RAS_SERVER = "1";
    public static String REPORT_SERVER_RAS_SERVER_SINGLE_SESSION = "2";
    private String EXCEL = "xls";
    private String TXT = "txt";
    private IInfoStore iStore;
    private IReportSourceFactory factoryPS;
    private ReportExportControl exportControl;
    private ISessionMgr sessionMgr = null;
    private IEnterpriseSession enterpriseSession = null;
    private IReportSource reportSource = null;
    public static String EN_KEY = "GOLDSIGN";
    private IReportAppFactory factoryApp;
    private ReportClientDocument reportClientDoc;
    private static IEnterpriseSession enterpriseSessionPub = null;
    private static IInfoStore iStorePub = null;
    private static IReportAppFactory factoryAppPub = null;
    private static SynControl SYN_CONTROL = new SynControl();
    private static int numOpenReportSource = 0;
    private static int numOpenReportSourceMax = 15;
    private static String PERIOD_TYPE_CUSTOMED="9";

    public ReportProxy() {
    }

    private static synchronized void plusNum() {
        numOpenReportSource = numOpenReportSource % numOpenReportSourceMax;
        numOpenReportSource++;
    }

    private boolean isNeedSyn() {
        if (numOpenReportSource == numOpenReportSourceMax) {
            return true;
        }
        return false;
    }

    public ReportProxy(String user, String password, String server)
            throws Exception {

        try {
            sessionMgr = CrystalEnterprise.getSessionMgr();
            enterpriseSession = sessionMgr.logon(user, password, server,
                    "secEnterprise");
            System.out.println("ReportProxy - Logon ok!");

            this.iStore = (IInfoStore) enterpriseSession
                    .getService("InfoStore");
            this.factoryPS = (IReportSourceFactory) enterpriseSession
                    .getService("PSReportFactory");
            System.out.println("ReportProxy - Factory ok!");

            this.exportControl = new ReportExportControl();
            this.exportControl.setName("ExportControl");
            this.exportControl.setExportAsAttachment(true);
            System.out.println("ReportProxy - ExportControl ok!");
            System.out.println("ReportProxy - constructor ok!");
        } catch (Exception e) {
            System.out.println("ReportProxy - constructor error - " + e);
            throw new Exception(e.getMessage());
        }

    }

    public Object getReportObject(String reportName) {
        try {
            System.out
                    .println("ReportProxy - getReportObject - processing report module:"
                    + reportName);

            String query = "Select SI_ID From CI_INFOOBJECTS Where SI_NAME = '"
                    + reportName + "' and SI_INSTANCE = 0 ";
            IInfoObjects result = iStore.query(query);

            if (result.isEmpty()) {
                throw new Exception("IInfoObjects null!");
            }
            IInfoObject firstResult = (IInfoObject) result.get(0);
            if (firstResult == null) {
                throw new Exception("IInfoObject null!");
            }
            System.out
                    .println("ReportProxy - getReportObject - IInfoObject ok!");

            Object reportSource = factoryPS.openReportSource((firstResult),
                    Locale.ENGLISH);

            System.out
                    .println("ReportProxy - getReportObject - reportObject ok!");

            return reportSource;
        } catch (Exception e) {
            System.out.println("ReportProxy - getReportObject - error - " + e);
            return null;
        }

    }

    public void exportWebReport(Object reportSource,
            HttpServletRequest request, HttpServletResponse response,
            ServletContext context, String fileType) {
        try {
            if (reportSource != null) {
                /*
                ExportOptions exportOptions = new ExportOptions();
                ReportExportFormat exportFormat = null;
                if (fileType.equals(EXCEL)) {
                    //exportFormat = ReportExportFormat.recordToMSExcel;
                    exportFormat = ReportExportFormat.MSExcel;//20170302
                } else {
                    exportFormat = ReportExportFormat.PDF;
                }
                exportOptions.setExportFormatType(exportFormat);
*/
                ExportOptions exportOptions = this.getExportOptions(fileType);//modified 20170302
                exportControl.setExportOptions(exportOptions);
                exportControl.setReportSource(reportSource);
                exportControl.processHttpRequest(request, response, context,
                        null);
                // exportControl.dispose();
                // reportSource = null;
                System.out.println("ReportProxy - exportWebReport - ok!");
            }
        } catch (Exception e) {
            System.out.println("ReportProxy - exportWebReport - error - " + e);

        } finally {
            if (exportControl != null) {
                exportControl.dispose();
                exportControl = null;
            }
            if (reportSource != null) {
                reportSource = null;
            }
            if (this.enterpriseSession != null) {
                this.enterpriseSession.logoff();
            }
            // exportControl.
        }
    }

    public void exportCristalReport(String user, String password,
            String server, String reportName, HttpServletRequest request,
            HttpServletResponse response, ServletContext context,
            String fileType, long maxWaitTime) {
        ReportProxy helper = new ReportProxy();
        ReportThread t = new ReportThread(user, password, server, reportName,
                request, response, context, fileType, helper,
                ReportProxy.REPORT_SERVER_PAGE_SERVER);
        t.start();
        try {
            t.join(maxWaitTime);
            if (!t.getReportGenerateStatus().getIsFinish()) {
                t.interrupt();
                // helper.freeResource();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportCristalReportByRas(String user, String password,
            String server, String reportName, HttpServletRequest request,
            HttpServletResponse response, ServletContext context,
            String fileType, long maxWaitTime) {
        ReportProxy helper = new ReportProxy();
        ReportThread t = new ReportThread(user, password, server, reportName,
                request, response, context, fileType, helper,
                ReportProxy.REPORT_SERVER_RAS_SERVER);
        t.start();
        try {
            t.join(maxWaitTime);
            if (!t.getReportGenerateStatus().getIsFinish()) {
                System.out.println("报表" + reportName + "生成时间将超过水晶报表服务器最大处理时间10分钟，提前将该报表线程杀死");
                System.out.println("杀死前释放未完成报表" + reportName + "资源");
                helper.freeResourceByRas();

                t.stop();
                System.out.println("杀死报表" + reportName + "线程");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportCristalReportByRasUsingSingleSession(String user,
            String password, String server, String reportName,
            HttpServletRequest request, HttpServletResponse response,
            ServletContext context, String fileType, long maxWaitTime) {
        ReportProxy helper = new ReportProxy();
        ReportThread t = new ReportThread(user, password, server, reportName,
                request, response, context, fileType, helper,
                ReportProxy.REPORT_SERVER_RAS_SERVER_SINGLE_SESSION);
        t.start();
        try {
            t.join(maxWaitTime);
            if (!t.getReportGenerateStatus().getIsFinish()) {
                t.interrupt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPass(String enPass) {
        Encryption en = new Encryption();
        return en.biDecrypt(EN_KEY, enPass);
    }

    private void setDbInfo(ReportExportControl exportControl,
            HttpServletRequest req) {
        String dbUserName = req.getParameter("p_user");// "acc_st";//req.getParameter("p_user");

        String dbPassword = this.getPass(req.getParameter("p_pass"));//"acc_st";// this.getPass(req.getParameter("p_pass"));

        ConnectionInfo connectionInfo = new ConnectionInfo();
        connectionInfo.setUserName(dbUserName);
        connectionInfo.setPassword(dbPassword);

        // 如果需设置子报表的信息
        // PropertyBag attr = new PropertyBag();

        // attr.put(PropertyBagHelper.CONNINFO_SERVER_NAME,serverName);
        // attr.put(PropertyBagHelper.CONNINFO_DATABASE_NAME,dbName);

        // connectionInfo.setAttributes(attr);

        ConnectionInfos connectionInfos = new ConnectionInfos();
        connectionInfos.add(connectionInfo);
        exportControl.setDatabaseLogonInfos(connectionInfos);

        exportControl.setEnableLogonPrompt(false);
        System.out.println("用户：" + dbUserName + "密码:" + dbPassword);

        // logger.info("设置登陆信息：服务器名："+serverName+"数据库名："+dbName+"用户："+dbUserName+"密码:"+dbPassword);

    }

    public void exportReport(String user, String password, String server,
            String reportName, HttpServletRequest request,
            HttpServletResponse response, ServletContext context,
            String fileType) {

        Fields fields;
        long startTime = 0;
        long endTime = 0;
        try {
            this.init(user, password, server);

            System.out.println("sucess log on report server");

            reportSource = this.getReportSource(reportName);

            System.out.println("sucess get report source");
            if (reportSource == null) {
                response.getWriter().println(
                        "reportName=" + reportName + " fileType=" + fileType);
                response.getWriter().println("report source not found");
                response.getWriter().flush();
                response.getWriter().close();

            }

            if (reportSource != null) {
                /*
                ExportOptions exportOptions = new ExportOptions();
                ReportExportFormat exportFormat = null;
                if (fileType.equals(EXCEL)) {
                    exportFormat = ReportExportFormat.recordToMSExcel;

                } else {
                    if (fileType.equals(TXT)) {
                        exportFormat = ReportExportFormat.text;
                    } else {
                        exportFormat = ReportExportFormat.PDF;
                    }
                }
                exportOptions.setExportFormatType(exportFormat);
                */
                ExportOptions exportOptions = this.getExportOptions(fileType);//modified 20170302
                System.out.println("ReportProxy - getReportParameter - ok!");
                fields = this.getReportParameter(request);
                // 设置报表的账户密码
                this.setDbInfo(exportControl, request);
                exportControl.setExportOptions(exportOptions);
                exportControl.setReportSource(reportSource);

                exportControl.setEnterpriseLogon(this.enterpriseSession);

                System.out.println("ReportProxy - setParameterFields - ok!");
                exportControl.setParameterFields(fields);

                startTime = System.currentTimeMillis();

                // response.setHeader("Content-Type","text/html;charset=GBK");
                exportControl.processHttpRequest(request, response, context,
                        null);

                endTime = System.currentTimeMillis();
                System.out.println("process http request total time "
                        + (endTime - startTime) + " ms");

                System.out.println("ReportProxy - exportReport - ok!");
            }
        } catch (Exception e) {
            System.out.println("ReportProxy - exportWebReport - error - " + e);
            try {
                response.getWriter().println(
                        "reportName=" + reportName + " fileType=" + fileType);
                response.getWriter().println(e.getMessage());
                response.getWriter().flush();
                response.getWriter().close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } finally {
            this.freeResource();
        }

    }

    private void handleExeption(HttpServletResponse response,
            String reportName, String fileType, String msg) throws IOException {
        response.getWriter().println(
                "reportName=" + reportName + " fileType=" + fileType);
        response.getWriter().println(msg);
        response.getWriter().flush();
        response.getWriter().close();
    }

    private void handleExeptionWithoutThrow(HttpServletResponse response,
            String reportName, String fileType, String msg) {
        try {
            response.getWriter().println(
                    "reportName=" + reportName + " fileType=" + fileType);
            response.getWriter().println(msg);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ExportOptions getExportOptions(String fileType) {
        ExportOptions exportOptions = new ExportOptions();
        ReportExportFormat exportFormat = this
                .getReportExportFormat(fileType);
        exportOptions.setExportFormatType(exportFormat);
        if (fileType.equals(EXCEL)) {
            IExcelExportFormatOptions efo = new ExcelExportFormatOptions();
            efo.setShowGridlines(true);
            exportOptions.setFormatOptions(efo);

        }
        return exportOptions;

    }

    private ReportExportFormat getReportExportFormat(String fileType) {
        ReportExportFormat exportFormat = null;
        if (fileType.equals(EXCEL)) {
            exportFormat = ReportExportFormat.MSExcel;//ReportExportFormat.recordToMSExcel;//20170302

        } else {
            if (fileType.equals(TXT)) {
                exportFormat = ReportExportFormat.text;
            } else {
                exportFormat = ReportExportFormat.PDF;
            }
        }
        return exportFormat;

    }
    //private void setExport

    public void exportReportByRas(String user, String password, String server,
            String reportName, HttpServletRequest request,
            HttpServletResponse response, ServletContext context,
            String fileType) {

        Fields fields;
        long startTime = 0;
        long endTime = 0;
        try {
            this.initByRas(user, password, server);

            reportSource = this.getReportSourceByRas(reportName);

            if (reportSource == null) {
                this.handleExeption(response, reportName, fileType, "报表源为NULL");
            }

            if (reportSource != null) {
                /*
                 ExportOptions exportOptions = new ExportOptions();
                 ReportExportFormat exportFormat = this
                 .getReportExportFormat(fileType);

                 exportOptions.setExportFormatType(exportFormat);
                 */
                ExportOptions exportOptions = this.getExportOptions(fileType);//modified 20170302

                System.out.println("成功设置报表导出格式");
                fields = this.getReportParameter(request);
                // 设置报表的账户密码
                this.setDbInfo(exportControl, request);
                System.out.println("成功设置报表数据源密码");
                exportControl.setExportOptions(exportOptions);
                exportControl.setReportSource(reportSource);

                exportControl.setEnterpriseLogon(this.enterpriseSession);
                exportControl.setParameterFields(fields);

                System.out.println("报表导出控件成功设置导出选项、报表参数、报表源、会话");

                startTime = System.currentTimeMillis();

                exportControl.processHttpRequest(request, response, context,
                        null);

                endTime = System.currentTimeMillis();
                System.out.println("完成报表" + reportName + "导出耗时："
                        + (endTime - startTime) + " ms");

            }
        } catch (Exception e) {
            this.handleExeptionWithoutThrow(response, reportName, fileType, e
                    .getMessage());

        } finally {
            this.freeResourceByRas();
        }

    }

    public void exportReportByRasUsingSingleSession(String user,
            String password, String server, String reportName,
            HttpServletRequest request, HttpServletResponse response,
            ServletContext context, String fileType) {

        Fields fields;
        long startTime = 0;
        long endTime = 0;
        try {
            this.initByRasUsingSingleSession(user, password, server);

            reportSource = this
                    .getReportSourceByRasUsingSingleSession(reportName);

            if (reportSource == null) {
                this.handleExeption(response, reportName, fileType, "报表源为NULL");
            }

            if (reportSource != null) {
                /*
                 ExportOptions exportOptions = new ExportOptions();
                 ReportExportFormat exportFormat = this
                 .getReportExportFormat(fileType);
                 exportOptions.setExportFormatType(exportFormat);
                 */
                ExportOptions exportOptions = this.getExportOptions(fileType);//modified 20170302
                System.out.println("成功设置报表导出格式");
                fields = this.getReportParameter(request);
                // 设置报表的账户密码
                this.setDbInfo(exportControl, request);
                System.out.println("成功设置报表数据源密码");
                exportControl.setExportOptions(exportOptions);
                exportControl.setReportSource(reportSource);

                exportControl
                        .setEnterpriseLogon(ReportProxy.enterpriseSessionPub);
                exportControl.setParameterFields(fields);

                System.out.println("报表导出控件成功设置导出选项、报表参数、报表源、会话");

                startTime = System.currentTimeMillis();

                exportControl.processHttpRequest(request, response, context,
                        null);

                endTime = System.currentTimeMillis();
                System.out.println("完成报表" + reportName + "导出耗时："
                        + (endTime - startTime) + " ms");

            }
        } catch (Exception e) {
            this.handleExeptionWithoutThrow(response, reportName, fileType, e
                    .getMessage());

        } finally {
            this.freeResourceByRasUsingSingleSession();
        }

    }

    public void freeResource() {
        if (this.exportControl != null) {
            this.exportControl.dispose();
            this.exportControl = null;
        }
        if (this.reportSource != null) {
            this.reportSource.dispose();
            this.reportSource = null;
        }

        if (this.enterpriseSession != null) {

            this.enterpriseSession.logoff();

            this.enterpriseSession = null;

            // exportControl.
        }

    }

    private void closeReportSource(IReportSource rptSource) {
        if (rptSource != null) {
            rptSource.dispose();
            rptSource = null;
        }
    }

    private void closeSession(IEnterpriseSession enpSession) {
        if (enpSession != null) {
            enpSession.logoff();
            enpSession = null;
        }
    }

    private void closeReportExportControl(ReportExportControl expControl) {
        if (expControl != null) {
            expControl.dispose();
            expControl = null;
        }
    }

    private void closeForReportClientDoc(ReportClientDocument rptClientDoc) {

        try {
            if (rptClientDoc != null) {
                rptClientDoc.close();
                rptClientDoc = null;
            }
        } catch (ReportSDKException e) {

            e.printStackTrace();
        }
    }

    public void freeResourceByRas() {

        this.closeReportSource(this.reportSource);
        this.closeForReportClientDoc(this.reportClientDoc);
        this.closeReportExportControl(this.exportControl);
        this.closeSession(this.enterpriseSession);

    }

    public void freeResourceByRasUsingSingleSession() {

        this.closeReportSource(this.reportSource);
        this.closeForReportClientDoc(this.reportClientDoc);
        this.closeReportExportControl(this.exportControl);
        // this.closeSession(this.enterpriseSession);

    }
    private boolean isReportCustomed(String periodType){
        if(periodType ==null || periodType.length()==0)
            return false;
        if(periodType.equals(PERIOD_TYPE_CUSTOMED))
            return true;
        return false;
    }

    private Fields getReportParameter(HttpServletRequest request) {
        Fields fields = new Fields();
        String lineId = this.getFieldValue(request.getParameter("in_line_id"));
        String stationId = this.getFieldValue(request.getParameter("in_station_id"));
        String mainType = this.getFieldValue(request.getParameter("in_card_main_id"));
        String subType = this.getFieldValue(request.getParameter("in_card_sub_id"));
        String squadDay = this.getFieldValue(request.getParameter("in_squad_day"));
        String balanceDay = this.getFieldValue(request.getParameter("in_balance_day"));
        String periodType =this.getFieldValue(request.getParameter("period_type"));
        String beginDay =this.getFieldValue(request.getParameter("in_begin_day"));
        String endDay =this.getFieldValue(request.getParameter("in_end_day"));

        this.setParameterFieldDiscreteValue(fields, "in_line_id", lineId);
        this.setParameterFieldDiscreteValue(fields, "in_station_id", stationId);
        this.setParameterFieldDiscreteValue(fields, "in_card_main_id", mainType);
        this.setParameterFieldDiscreteValue(fields, "in_card_sub_id", subType);
        this.setParameterFieldDiscreteValue(fields, "in_squad_day", squadDay);
        this.setParameterFieldDiscreteValue(fields, "in_balance_day", balanceDay);
        if(this.isReportCustomed(periodType)){
            this.setParameterFieldDiscreteValue(fields, "in_begin_day", beginDay);
            this.setParameterFieldDiscreteValue(fields, "in_end_day", endDay);
        }


        System.out.println("lineId=" + lineId + " stationId=" + stationId
                + " mainType=" + mainType + " subType=" + subType
                + " squadDay=" + squadDay + " balanceDay=" + balanceDay+" beginDay="+beginDay+" endDay="+endDay);

        return fields;

    }

    private Fields delete_getReportParameter(HttpServletRequest request) {
        Fields fields = new Fields();
        String lineId = this.getFieldValue(request.getParameter("p_line_id"));
        String stationId = this.getFieldValue(request
                .getParameter("p_station_id"));
        String mainType = this.getFieldValue(request
                .getParameter("p_main_type"));
        String subType = this.getFieldValue(request.getParameter("p_sub_type"));
        String squadDay = this.getFieldValue(request
                .getParameter("p_squad_day"));
        String balanceDay = this.getFieldValue(request
                .getParameter("p_balance_day"));

        this.setParameterFieldDiscreteValue(fields, "@p_line_id", lineId);
        this.setParameterFieldDiscreteValue(fields, "@p_station_id", stationId);
        this.setParameterFieldDiscreteValue(fields, "@p_card_main_type",
                mainType);
        this
                .setParameterFieldDiscreteValue(fields, "@p_card_sub_type",
                subType);
        this.setParameterFieldDiscreteValue(fields, "@p_squad_day", squadDay);
        this.setParameterFieldDiscreteValue(fields, "@p_balance_day",
                balanceDay);

        System.out.println("lineId=" + lineId + " stationId=" + stationId
                + " mainType=" + mainType + " subType=" + subType
                + " squadDay=" + squadDay + " balanceDay=" + balanceDay);

        return fields;

    }

    private String getFieldValue(String value) {
        if (value == null || value.length() == 0) {
            return "-1";
        }
        return value;
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

    private void init(String user, String password, String server)
            throws Exception {

        try {
            sessionMgr = CrystalEnterprise.getSessionMgr();
            enterpriseSession = sessionMgr.logon(user, password, server,
                    "secEnterprise");
            System.out.println("ReportProxy - Logon ok!");

            this.iStore = (IInfoStore) enterpriseSession
                    .getService("InfoStore");
            this.factoryPS = (IReportSourceFactory) enterpriseSession
                    .getService("PSReportFactory");
            System.out.println("ReportProxy - Factory ok!");

            this.exportControl = new ReportExportControl();
            this.exportControl.setName("ExportControl");
            this.exportControl.setExportAsAttachment(true);

            System.out.println("ReportProxy - ExportControl ok!");
            System.out.println("ReportProxy - constructor ok!");
        } catch (Exception e) {
            System.out.println("ReportProxy - constructor error - " + e);
            throw new Exception(e.getMessage());
        }

    }

    /*
     * 初始化报表服务器相关对象：报表库、RAS报表管理厂、报表导出控件
     */
    private void initByRas(String user, String password, String server)
            throws Exception {

        try {
            sessionMgr = CrystalEnterprise.getSessionMgr();
            enterpriseSession = sessionMgr.logon(user, password, server,
                    "secEnterprise");
            System.out.println("成功与报表服务器建立会话!");

            this.iStore = (IInfoStore) enterpriseSession
                    .getService("InfoStore");
            this.factoryApp = (IReportAppFactory) enterpriseSession
                    .getService("RASReportFactory");
            System.out.println("成功创建报表库、RAS报表管理厂!");

            this.exportControl = new ReportExportControl();
            this.exportControl.setName("ExportControl");
            this.exportControl.setExportAsAttachment(true);

            System.out.println("成功创建报表导出控件");

        } catch (Exception e) {
            System.out.println("初始化报表服务错误。 " + e);
            throw new Exception(e.getMessage());
        }

    }

    /*
     * 初始化报表服务器相关对象：报表库、RAS报表管理厂、报表导出控件
     */
    private void initByRasUsingSingleSession(String user, String password,
            String server) throws Exception {

        try {
            sessionMgr = CrystalEnterprise.getSessionMgr();
            if (ReportProxy.enterpriseSessionPub == null) {
                ReportProxy.enterpriseSessionPub = sessionMgr.logon(user,
                        password, server, "secEnterprise");
                System.out.println("成功与报表服务器建立会话!");
            }
            if (ReportProxy.iStorePub == null) {
                ReportProxy.iStorePub = (IInfoStore) ReportProxy.enterpriseSessionPub
                        .getService("InfoStore");
            }
            if (ReportProxy.factoryAppPub == null) {
                ReportProxy.factoryAppPub = (IReportAppFactory) ReportProxy.enterpriseSessionPub
                        .getService("RASReportFactory");
                System.out.println("成功创建报表库、RAS报表管理厂!");
            }

            this.exportControl = new ReportExportControl();
            this.exportControl.setName("ExportControl");
            this.exportControl.setExportAsAttachment(true);

            System.out.println("成功创建报表导出控件");

        } catch (Exception e) {
            System.out.println("初始化报表服务错误。 " + e);
            throw new Exception(e.getMessage());
        }

    }

    private IReportSource getReportSource(String reportName) throws Exception {
        try {
            System.out
                    .println("ReportProxy - getReportObject - processing report module:"
                    + reportName);

            String query = "Select SI_ID From CI_INFOOBJECTS Where SI_NAME = '"
                    + reportName + "' and SI_INSTANCE = 0 ";
            IInfoObjects result = iStore.query(query);

            System.out.println("query result set size " + result.size());

            if (result.isEmpty()) {
                throw new Exception("IInfoObjects null!");
            }
            IInfoObject firstResult = (IInfoObject) result.get(0);
            if (firstResult == null) {
                throw new Exception("IInfoObject null!");
            }
            System.out
                    .println("ReportProxy - getReportObject - IInfoObject ok!");

            // IReportSource reportSource = factoryPS.openReportSource(
            // (firstResult),
            // Locale.ENGLISH);

            reportSource = factoryPS.openReportSource((firstResult),
                    Locale.SIMPLIFIED_CHINESE);

            System.out
                    .println("ReportProxy - getReportObject - reportObject ok!");

            return reportSource;
        } catch (Exception e) {
            System.out.println("ReportProxy - getReportObject - error - " + e);
            throw e;
            // return null;
        }

    }

    private IReportSource getReportSourceByRas(String reportName)
            throws Exception {

        try {
            System.out.println("处理报表：" + reportName);

            String query = "Select SI_ID From CI_INFOOBJECTS Where SI_NAME = '"
                    + reportName + "' and SI_INSTANCE = 0 ";
            IInfoObjects result = iStore.query(query);

            System.out.println("同名报表对象数量为： " + result.size());

            if (result.isEmpty()) {
                throw new Exception("报表不存在!");
            }
            IInfoObject firstResult = (IInfoObject) result.get(0);
            if (firstResult == null) {
                throw new Exception("报表不存在!");
            }
            //this.openReportSourceBySyn(firstResult,reportName);
            this.openReportSourceByNoSyn(firstResult, reportName);

            return reportSource;
        } catch (Exception e) {
            System.out.println("打开报：" + reportName + "失败 " + e);
            throw e;

        }

    }

    private void openReportSourceBySyn(IInfoObject firstResult,
            String reportName) throws Exception {
        long startTime;
        long endTime;
        System.out.println("使用同步获取报表数据源");
        synchronized (ReportProxy.SYN_CONTROL) {
            startTime = System.currentTimeMillis();
            this.reportClientDoc = this.factoryApp.openDocument(firstResult, 0,
                    Locale.SIMPLIFIED_CHINESE);
            endTime = System.currentTimeMillis();
            System.out.println("成功打开报表" + reportName + "耗时："
                    + (endTime - startTime) / 1000 + "秒");
            startTime = System.currentTimeMillis();
            this.reportSource = this.reportClientDoc.getReportSource();
            endTime = System.currentTimeMillis();
            System.out.println("获取打开报表的内容" + reportName + "耗时："
                    + (endTime - startTime) / 1000 + "秒");
        }
        // System.out.println("成功打开报表" + reportName);
    }

    private void openReportSourceByNoSyn(IInfoObject firstResult,
            String reportName) throws Exception {
        long startTime;
        long endTime;
        //synchronized (ReportProxy.SYN_CONTROL) {
        System.out.println("不使用同步获取报表数据源");
        startTime = System.currentTimeMillis();

        this.reportClientDoc = this.factoryApp.openDocument(firstResult, 0,
                Locale.SIMPLIFIED_CHINESE);


        endTime = System.currentTimeMillis();
        System.out.println("成功打开报表" + reportName + "耗时："
                + (endTime - startTime) / 1000 + "秒");
        startTime = System.currentTimeMillis();
        this.reportSource = this.reportClientDoc.getReportSource();
        endTime = System.currentTimeMillis();
        System.out.println("获取打开报表的内容" + reportName + "耗时："
                + (endTime - startTime) / 1000 + "秒");
        //}
        // System.out.println("成功打开报表" + reportName);
    }

    private IReportSource getReportSourceByRasUsingSingleSession(
            String reportName) throws Exception {
        try {
            System.out.println("处理报表：" + reportName);

            String query = "Select SI_ID From CI_INFOOBJECTS Where SI_NAME = '"
                    + reportName + "' and SI_INSTANCE = 0 ";
            IInfoObjects result = ReportProxy.iStorePub.query(query);

            System.out.println("同名报表对象数量为： " + result.size());

            if (result.isEmpty()) {
                throw new Exception("报表不存在!");
            }
            IInfoObject firstResult = (IInfoObject) result.get(0);
            if (firstResult == null) {
                throw new Exception("报表不存在!");
            }
            System.out.println("成功获取报表对象" + reportName + "!");

            this.reportClientDoc = ReportProxy.factoryAppPub.openDocument(
                    firstResult, 0, Locale.SIMPLIFIED_CHINESE);

            this.reportSource = this.reportClientDoc.getReportSource();

            System.out.println("成功打开报表");

            return reportSource;
        } catch (Exception e) {
            System.out.println("打开报：" + reportName + "失败 " + e);
            throw e;

        }

    }
}
