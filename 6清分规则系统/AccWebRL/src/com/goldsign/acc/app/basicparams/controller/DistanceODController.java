/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicparams.controller;

import com.goldsign.acc.app.basicparams.entity.DistanceOD;
import com.goldsign.acc.app.basicparams.mapper.DistanceODMapper;
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
public class DistanceODController extends RLBaseController {

    @Autowired
    private DistanceODMapper mapper;

    @RequestMapping(value = "/distanceODAction")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("/jsp/basicparams/distanceOD.jsp");
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
        this.getResultSetText((List<DistanceOD>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
         this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    private void getResultSetText(List<DistanceOD> resultSet, ModelAndView mv) throws Exception {
        List<PubFlag> line = (List<PubFlag>) mv.getModelMap().get(RLBaseController.LINE);
        List<PubFlag> station = (List<PubFlag>) mv.getModelMap().get(RLBaseController.STATION);
        List<PubFlag> record_flag = (List<PubFlag>) mv.getModelMap().get(RLBaseController.RECORDFLAG);

        for (DistanceOD vo : resultSet) {
            if (line != null && !line.isEmpty()) {
                vo.setoLineText(DBUtil.getTextByCode(vo.getO_line_id(), line));
            }
            if (station != null && !station.isEmpty()) {
                vo.setoStationText(DBUtil.getTextByCode(vo.getO_station_id(), vo.getO_line_id(), station));
            }
            if (line != null && !line.isEmpty()) {
                vo.seteLineText(DBUtil.getTextByCode(vo.getE_line_id(), line));
            }
            if (station != null && !station.isEmpty()) {
                vo.seteStationText(DBUtil.getTextByCode(vo.getE_station_id(), vo.getE_line_id(), station));
            }
            if (record_flag != null && !record_flag.isEmpty()) {
                vo.setVersionText(DBUtil.getTextByCode(vo.getRecord_flag(), record_flag));
            }
            vo.setChangDetailText("换乘明细");
        }
    }

    private DistanceOD getQueryCondition(HttpServletRequest request) {
        DistanceOD vo = new DistanceOD();
        String o_line_id = request.getParameter("q_beginLineId");
        String o_station_id = request.getParameter("q_beginStationId");
        String e_line_id = request.getParameter("q_endLineId");
        String e_station_id = request.getParameter("q_endStationId");
        String record_flag = request.getParameter("q_recordFlag");
        if (!"".equals(o_line_id)) {
            vo.setO_line_id(o_line_id);
        }
        if (!"".equals(o_station_id)) {
            vo.setO_station_id(o_station_id);
        }
        if (!"".equals(e_line_id)) {
            vo.setE_line_id(e_line_id);
        }
        if (!"".equals(e_station_id)) {
            vo.setE_station_id(e_station_id);
        }
        if (!"".equals(record_flag)) {
            vo.setRecord_flag(record_flag);
        }
        return vo;
    }

    public OperationResult queryPlan(HttpServletRequest request, DistanceODMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        DistanceOD queryCondition;
        List<DistanceOD> resultSet;
        List<DistanceOD> resultSet1;
        List<DistanceOD> resultSet2;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = mapper.getDistanceOD(queryCondition);
            resultSet1 = mapper.getMinDistance(queryCondition);
            resultSet2 = mapper.getDistanceThres();
            for (DistanceOD vo : resultSet) {
                //设置最短路径
                DistanceOD tmp = resultSet1.get(0);
                vo.setMin_distance(tmp.getMin_distance());

                //设置有是否有效路径
                DistanceOD t = resultSet2.get(0);
                //起始站同一线路时，换乘次数为0的路径有效 
//                System.out.println("getO_line_id===>" + vo.getO_line_id());
//                System.out.println("getO_line_id===>" + vo.getO_line_id());
//                System.out.println("getO_line_id===>" + vo.getO_line_id());
                if (vo.getO_line_id().equals(vo.getE_line_id()) && vo.getChange_times().equals("0")) {
                    vo.setIsValidText("有效");
                } else {
                    //起始站不同一线路时，（里程 - 最短里程）/最短里程 < 比例阀值 有效
                    float distance = Float.parseFloat(vo.getDistance());
                    float min = Float.parseFloat(tmp.getMin_distance());
                    float threshold = Float.parseFloat(t.getThreshold());
                    int times = Integer.parseInt(vo.getChange_times());
                    int change_thres = Integer.parseInt(t.getChange_thres());

                    if (!vo.getO_line_id().equals(vo.getE_line_id()) && ((distance - min == 0) | ((distance - min) / min <= threshold)) && times <= change_thres) {
                        vo.setIsValidText("有效");
                    } else {
                        vo.setIsValidText("无效");
                    }
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
    
    @RequestMapping("/DistanceODExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElements(request);
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }

}
