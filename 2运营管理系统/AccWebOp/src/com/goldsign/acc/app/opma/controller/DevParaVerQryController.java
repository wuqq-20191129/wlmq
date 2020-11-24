/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.controller;

import com.goldsign.acc.app.opma.entity.DevParaVerQry;
import com.goldsign.acc.app.opma.mapper.DevParaVerQryMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.CharUtil;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.vo.User;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.transaction.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @desc:设备参数版本查询请求
 * @author:chenzx
 * @create date: 2017-6-26
 */
@Controller
public class DevParaVerQryController extends PrmBaseController {

    @Autowired
    private DevParaVerQryMapper mapper;

    @RequestMapping("/DevParaVerQry")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("/jsp/opma/dev_para_ver_qry.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.mapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.mapper, this.operationLogMapper);
                }
            }
            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.mapper, this.operationLogMapper, opResult);
                }
                if (command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                        || command.equals(CommandConstant.COMMAND_BACKEND)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] attrNames = {FIL_DEV_TYPES, LINES, STATIONS, LINE_STATIONS, STATUSES};
        this.setPageOptions(attrNames, mv, request, response);
        this.getResultSetText((List<DevParaVerQry>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);
        return mv;
    }

    private OperationResult add(HttpServletRequest request, DevParaVerQryMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        DevParaVerQry po = this.getReqAttribute(request, CommandConstant.COMMAND_ADD);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "ID为" + po.getWater_no() + "的";
        if (CharUtil.getDBLenth(po.getRemark()) > 50) {
            rmsg.addMessage("备注最大值不能超过50位(中文字符为两位)");
            return rmsg;
        } else {
            try {
                n = this.addByTrans(request, mapper, po);

            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), operationLogMapper);
        }
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    private int addByTrans(HttpServletRequest request, DevParaVerQryMapper mapper, DevParaVerQry vo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = mapper.addDevParaVerQry(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private DevParaVerQry getReqAttribute(HttpServletRequest request, String type) {
        DevParaVerQry po = new DevParaVerQry();
        po.setLine_id(FormUtil.getParameter(request, "d_lineId"));
        po.setStation_id(FormUtil.getParameter(request, "d_stationId"));
        po.setDev_type_id(FormUtil.getParameter(request, "d_devTypeId"));
        if (FormUtil.getParameter(request, "d_deviceId") != null && FormUtil.getParameter(request, "d_deviceId") != "") {
            po.setDevice_id(FormUtil.getParameter(request, "d_deviceId"));
        } else {
            po.setDevice_id("000");
        }
        po.setStatus("0");
        Date date = new Date();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
        po.setQuery_date(dateStr);
        User user = new User();
        user = (User) request.getSession().getAttribute("User");
        po.setOperator_id(user.getAccount());
        po.setRemark(FormUtil.getParameter(request, "d_remark"));
        po.setLcc_line_id(FormUtil.getParameter(request, "d_lineId"));
        return po;
    }

    public OperationResult queryForOp(HttpServletRequest request, DevParaVerQryMapper mapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        DevParaVerQry vo;
        List<DevParaVerQry> resultSet;
        try {
            vo = this.getQueryConditionForOp(request);
            resultSet = mapper.getDevParaVerQry(vo);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    private DevParaVerQry getQueryConditionForOp(HttpServletRequest request) {

        DevParaVerQry qCon = new DevParaVerQry();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            qCon.setLine_id(FormUtil.getParameter(request, "d_lineId"));
            qCon.setStation_id(FormUtil.getParameter(request, "d_stationId"));
            qCon.setDev_type_id(FormUtil.getParameter(request, "d_devTypeId"));
            if (FormUtil.getParameter(request, "d_deviceId") != null && FormUtil.getParameter(request, "d_deviceId") != "") {
                qCon.setDevice_id(FormUtil.getParameter(request, "d_deviceId"));
            } else {
                qCon.setDevice_id("000");
            }
//                    qCon.setLcc_line_id(FormUtil.getParameter(request, "d_lineId"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
        }
        return qCon;
    }

    private OperationResult query(HttpServletRequest request, DevParaVerQryMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        DevParaVerQry queryCondition;
        List<DevParaVerQry> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = mapper.getDevParaVerQry(queryCondition);
            request.getSession().setAttribute("queryCondition", queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    private DevParaVerQry getQueryCondition(HttpServletRequest request) {
        DevParaVerQry vo = new DevParaVerQry();
        String dev_type_id = request.getParameter("q_devTypeId");
        //String lcc_line_id = request.getParameter("q_lccLineId");
        String line_id = request.getParameter("q_lineId");
        String station_id = request.getParameter("q_stationId");
        String device_id = request.getParameter("q_deviceId");
        String status = request.getParameter("q_statusId");
        String beginDateTime = request.getParameter("q_beginDatetime");
        String endDateTime = request.getParameter("q_endDatetime");

        if (!"".equals(dev_type_id)) {
            vo.setDev_type_id(dev_type_id);
        }
//        if (!"".equals(lcc_line_id)) {
//            vo.setLcc_line_id(lcc_line_id);
//        }
        if (!"".equals(line_id)) {
            vo.setLine_id(line_id);
        }
        if (!"".equals(station_id)) {
            vo.setStation_id(station_id);
        }
        if (!"".equals(device_id)) {
            vo.setDevice_id(device_id);
        }
        if (!"".equals(status)) {
            vo.setStatus(status);
        }
        if (!"".equals(beginDateTime)) {
            beginDateTime = beginDateTime + " 00:00:00";
            vo.setBeginDatetime(beginDateTime);
        }
        if (!"".equals(beginDateTime)) {
            endDateTime = endDateTime + " 23:59:59";
            vo.setEndDatetime(endDateTime);
        }
        return vo;
    }

    private void getResultSetText(List<DevParaVerQry> resultSet, ModelAndView mv) throws Exception {
        List<PubFlag> filDevTypes = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.FIL_DEV_TYPES);
        List<PubFlag> lines = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.LINES);
        List<PubFlag> stations = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.STATIONS);
        List<PubFlag> statuses = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.STATUSES);

        for (DevParaVerQry vo : resultSet) {
            if (filDevTypes != null && !filDevTypes.isEmpty()) {
                if (vo.getDev_type_id().equals("00")) {
                    vo.setDevTypeText("全部");
                } else {
                    vo.setDevTypeText(DBUtil.getTextByCode(vo.getDev_type_id(), filDevTypes));
                }
            }
            if (lines != null && !lines.isEmpty()) {
                if (vo.getLine_id().equals("00")) {
                    vo.setLineText("全部");
                } else {
                    vo.setLineText(DBUtil.getTextByCode(vo.getLine_id(), lines));
                }
            }
            if (stations != null && !stations.isEmpty()) {
                if (vo.getStation_id().equals("00")) {
                    vo.setStationText("全部");
                } else {
                    vo.setStationText(DBUtil.getTextByCode(vo.getStation_id(), vo.getLine_id(), stations));
                }
            }
            if (statuses != null && !statuses.isEmpty()) {
                vo.setStatusText(DBUtil.getTextByCode(vo.getStatus(), statuses));
            }
        }
    }
    
    @RequestMapping("/DevParaVerQryExportAll")
    public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.opma.entity.DevParaVerQry");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
    
    private List<Map<String,String>> entityToMap(List results){
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            DevParaVerQry vo = (DevParaVerQry)results.get(n);
            Map<String,String> map = new HashMap<>();
            if(vo.getWater_no() != null)
                map.put("WATER_NO", vo.getWater_no().toString());
            map.put("LINE_TEXT", vo.getLineText());
            map.put("STATION_TEXT", vo.getStationText());
            map.put("DEV_TYPE_TEXT", vo.getDevTypeText());
            map.put("DEVICE_ID", vo.getDevice_id());
            map.put("STATUS_TEXT", vo.getStatusText());
            map.put("QUERY_DATE", vo.getQuery_date());
            map.put("SEND_DATE", vo.getSend_date());
            map.put("OPERATOR_ID", vo.getOperator_id());
            map.put("REMARK", vo.getRemark());
            list.add(map);
        }
        return list;
    }
}
