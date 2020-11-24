/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicparams.controller;

import com.goldsign.acc.app.basicparams.entity.LineStation;
import com.goldsign.acc.app.basicparams.mapper.LineStationMapper;
import com.goldsign.acc.frame.constant.CommandConstant;

import com.goldsign.acc.frame.controller.RLBaseController;

import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;

import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;

import com.goldsign.acc.frame.util.LogUtil;

import com.goldsign.acc.frame.vo.OperationResult;

import java.util.List;
import java.util.Map;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * 线路站点查询
 *
 * @author chenzx
 */
@Controller
public class LineStationController extends RLBaseController {

    @Autowired
    private LineStationMapper mapper;

    @RequestMapping(value = "/lineStationAction")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("/jsp/basicparams/lineStation.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();

                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult = this.queryPlan(request, this.mapper, this.operationLogMapper);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = {LINE, STATION, LINE_STATIONS, RECORDFLAG};
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.getResultSetText((List<LineStation>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    private void getResultSetText(List<LineStation> resultSet, ModelAndView mv) throws Exception {
        List<PubFlag> line = (List<PubFlag>) mv.getModelMap().get(RLBaseController.LINE);
        List<PubFlag> station = (List<PubFlag>) mv.getModelMap().get(RLBaseController.STATION);
        List<PubFlag> record_flag = (List<PubFlag>) mv.getModelMap().get(RLBaseController.RECORDFLAG);
        int id_code = 0;

        for (LineStation vo : resultSet) {
            if (line != null && !line.isEmpty()) {
                vo.setLineText(DBUtil.getTextByCode(vo.getLine_id(), line));
            }
            if (station != null && !station.isEmpty()) {
                vo.setStationText(DBUtil.getTextByCode(vo.getStation_id(), vo.getLine_id(), station));
            }
            if (record_flag != null && !record_flag.isEmpty()) {
                vo.setVersionText(DBUtil.getTextByCode(vo.getRecord_flag(), record_flag));
            }
            vo.setId_code(id_code);
            id_code++;
        }
    }

    private LineStation getQueryCondition(HttpServletRequest request) {
        LineStation vo = new LineStation();
        String line_id = request.getParameter("q_lineId");
        String station_id = request.getParameter("q_stationId");
        String record_flag = request.getParameter("q_recordFlag");
        if (!"".equals(line_id)) {
            vo.setLine_id(line_id);
        }
        if (!"".equals(station_id)) {
            vo.setStation_id(station_id);
        }
        if (!"".equals(record_flag)) {
            vo.setRecord_flag(record_flag);
        }
        return vo;
    }

    public OperationResult queryPlan(HttpServletRequest request, LineStationMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        LineStation queryCondition;
        List<LineStation> resultSet;
        List<LineStation> resultSet1;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = mapper.getLineStation(queryCondition);
            if (queryCondition.getLine_id()!=null && queryCondition.getStation_id()!=null) {
                if (this.existRecord(queryCondition, mapper)) {
                    resultSet1 = mapper.getTransferLineStation(queryCondition);
                    resultSet.addAll(resultSet1);
                }
            }
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    private boolean existRecord(LineStation vo, LineStationMapper mapper) {
        List<LineStation> list = mapper.getTransferStation(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }
    
    @RequestMapping("/LineStationExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElements(request);
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }
}
