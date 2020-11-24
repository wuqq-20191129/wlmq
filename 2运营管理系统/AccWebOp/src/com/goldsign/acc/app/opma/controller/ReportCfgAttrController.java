/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.controller;

import com.goldsign.acc.app.opma.entity.ReportCfgAttr;
import com.goldsign.acc.app.prminfo.mapper.PrmVersionMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;

import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;

import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;

import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.goldsign.acc.app.opma.mapper.ReportCfgAttrMapper;

/**
 * 报表属性配置
 *
 * @author luck
 */
@Controller
public class ReportCfgAttrController extends PrmBaseController {

    @Autowired
    private ReportCfgAttrMapper rgaMapper;

    @RequestMapping("/ReportGfgAttr")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/opma/report_cfg_attr.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();

                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.rgaMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.rgaMapper, this.operationLogMapper);
                }

            }
            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT) || command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                        || command.equals(CommandConstant.COMMAND_BACKEND)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.rgaMapper, this.operationLogMapper, opResult);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {CARDMAINS, CARDSUBS, LINES, STATIONS, REPORTMODULES, REPORTCODES};
        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.getResultSetText((List<ReportCfgAttr>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);
        return mv;

    }

    private void getResultSetText(List<ReportCfgAttr> resultSet, ModelAndView mv) {
        List<PubFlag> cardMains = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDMAINS);
        List<PubFlag> cardSubs = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDSUBS);
        List<PubFlag> lines = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.LINES);
        List<PubFlag> stations = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.STATIONS);
        List<PubFlag> resources = rgaMapper.getResources();

        for (ReportCfgAttr sd : resultSet) {
            if (cardMains != null && !cardMains.isEmpty()) {
                sd.setCardMainName(DBUtil.getTextByCode(sd.getCardMainId(), cardMains));
            }
            if (cardSubs != null && !cardSubs.isEmpty()) {
                sd.setCardSubName(DBUtil.getTextByCode(sd.getCardSubId(), sd.getCardMainId(), cardSubs));
            }
            if (lines != null && !lines.isEmpty()) {
                sd.setLineName(DBUtil.getTextByCode(sd.getLineId(), lines));
            }
            if (stations != null && !stations.isEmpty()) {
                sd.setStationName(DBUtil.getTextByCode(sd.getStationId(), sd.getLineId(), stations));
            }
            if (resources != null && !resources.isEmpty()) {
                sd.setDsName(DBUtil.getTextByCode(sd.getDsId(), resources));
            }
            switch (sd.getPeriodType()) {
                case "1":
                    sd.setPeriodTypeName("日报");
                    break;
                case "2":
                    sd.setPeriodTypeName("月报");
                    break;
                case "3":
                    sd.setPeriodTypeName("年报");
                    break;
            }
            switch (sd.getReportType()) {
                case "1":
                    sd.setReportTypeName("运营日等于清算日");
                    break;
                case "2":
                    sd.setReportTypeName("运营日从中间表取");
                    break;
            }
            switch (sd.getReportLock()) {
                case "1":
                    sd.setReportLockName("否");
                    break;
                case "0":
                    sd.setReportLockName("是");
                    break;
            }

        }

    }

    private ReportCfgAttr getQueryConditionForOp(HttpServletRequest request) throws UnsupportedEncodingException {

        ReportCfgAttr qCon = new ReportCfgAttr();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return qCon;
            }
            qCon.setReportModule(FormUtil.getParameter(request, "d_reportModule"));
            qCon.setLineId(FormUtil.getParameter(request, "d_lineId"));
            qCon.setCardMainId(FormUtil.getParameter(request, "d_cardMainId"));
            qCon.setCardSubId(FormUtil.getParameter(request, "d_cardSubId"));
            qCon.setOutType(FormUtil.getParameter(request, "d_outType"));
            qCon.setReportLock(FormUtil.getParameter(request, "d_reportLock"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setReportModule(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_reportModule"));
                qCon.setReportCode(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_reportCode"));
                qCon.setReportLock(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_reportLock"));
            }
        }
        return qCon;
    }

    private ReportCfgAttr getQueryCondition(HttpServletRequest request) throws UnsupportedEncodingException {
        ReportCfgAttr qCon = new ReportCfgAttr();
        qCon.setReportCode(FormUtil.getParameter(request, "q_reportCode"));
        qCon.setReportModule(FormUtil.getParameter(request, "q_reportModule"));
        qCon.setReportLock(FormUtil.getParameter(request, "q_reportLock"));
        return qCon;
    }

    public OperationResult query(HttpServletRequest request, ReportCfgAttrMapper rgaMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        ReportCfgAttr queryCondition;
        List<ReportCfgAttr> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            request.getSession().setAttribute("queryCondition", queryCondition);
            resultSet = rgaMapper.getReportGfgAttrs(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult queryForOp(HttpServletRequest request, ReportCfgAttrMapper rgaMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        ReportCfgAttr queryCondition;
        List<ReportCfgAttr> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request);
            if (queryCondition == null) {
                return null;
            }
            resultSet = rgaMapper.getReportGfgAttrs(queryCondition);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult modify(HttpServletRequest request, ReportCfgAttrMapper rgaMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        ReportCfgAttr po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            n = this.modifyByTrans(request, rgaMapper, pvMapper, po);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    public ReportCfgAttr getReqAttribute(HttpServletRequest request) throws UnsupportedEncodingException {
        ReportCfgAttr po = new ReportCfgAttr();
        po.setReportModule(FormUtil.getParameter(request, "d_reportModule"));
        po.setLineId(FormUtil.getParameter(request, "d_lineId"));
        po.setCardMainId(FormUtil.getParameter(request, "d_cardMainId"));
        po.setCardSubId(FormUtil.getParameter(request, "d_cardSubId"));
        po.setOutType(FormUtil.getParameter(request, "d_outType"));
        po.setReportLock(FormUtil.getParameter(request, "d_reportLock"));
        return po;
    }

    private int modifyByTrans(HttpServletRequest request, ReportCfgAttrMapper rgaMapper, PrmVersionMapper pvMapper, ReportCfgAttr po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = rgaMapper.modify(po);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    @RequestMapping("/ReportGfgAttrExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ReportCfgAttr queryCondition = new ReportCfgAttr();

        if (request.getSession().getAttribute("queryCondition") != null) {
            Object obj = request.getSession().getAttribute("queryCondition");
            if (obj instanceof ReportCfgAttr) {
                queryCondition = (ReportCfgAttr) request.getSession().getAttribute("queryCondition");
            }
        }
        List<Map> queryResults = rgaMapper.queryToMap(queryCondition);
        System.out.println("size====>" + queryResults.size());
        List<PubFlag> cardMains = pubFlagMapper.getCardMains();
        List<PubFlag> cardSubs = pubFlagMapper.getCardSubs();
        List<PubFlag> lines = pubFlagMapper.getLines();
        List<PubFlag> stations = pubFlagMapper.getStations();
        List<PubFlag> resources = rgaMapper.getResources();
        /* 查询结果部分内容转换中文 */
        for (Map vo : queryResults) {
            if (cardMains != null && !cardMains.isEmpty()) {
                if (vo.get("CARD_MAIN_ID") != null) {
                    vo.put("CARDMAINNAME", DBUtil.getTextByCode(vo.get("CARD_MAIN_ID").toString(), cardMains));
                }
            }
            if (cardSubs != null && !cardSubs.isEmpty()) {
                if (vo.get("CARD_SUB_ID") != null) {
                    vo.put("CARDSUBNAME", DBUtil.getTextByCode(vo.get("CARD_SUB_ID").toString(), vo.get("CARD_MAIN_ID").toString(), cardSubs));
                }
            }
            if (lines != null && !lines.isEmpty()) {
                if (vo.get("LINE_ID") != null) {
                    vo.put("LINENAME", DBUtil.getTextByCode(vo.get("LINE_ID").toString(), lines));
                }
            }
            if (stations != null && !stations.isEmpty()) {
                if (vo.get("STATION_ID") != null) {
                    vo.put("STATIONNAME", DBUtil.getTextByCode(vo.get("STATION_ID").toString(), vo.get("LINE_ID").toString(), stations));
                }
            }

            if (resources != null && !resources.isEmpty()) {
                vo.put("DSNAME", DBUtil.getTextByCode(vo.get("DS_ID").toString(), resources));
            }

            switch (vo.get("PERIOD_TYPE").toString()) {
                case "1":
                    vo.put("PERIODTYPENAME", "日报");
                    break;
                case "2":
                    vo.put("PERIODTYPENAME", "月报");
                    break;
                case "3":
                    vo.put("PERIODTYPENAME", "年报");
                    break;
            }

            switch (vo.get("REPORT_TYPE").toString()) {
                case "1":
                    vo.put("REPORTTYPENAME", "运营日等于清算日");
                    break;
                case "2":
                    vo.put("REPORTTYPENAME", "运营日从中间表取");
                    break;
            }

            switch (vo.get("REPORT_LOCK").toString()) {
                case "1":
                    vo.put("REPORTLOCKNAME", "否");
                    break;
                case "0":
                    vo.put("REPORTLOCKNAME", "是");
                    break;
            }

        }

        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

}
