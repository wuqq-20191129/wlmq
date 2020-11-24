/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicparams.controller;

import com.goldsign.acc.app.basicparams.entity.DistanceDetail;
import com.goldsign.acc.app.basicparams.mapper.DistanceDetailMapper;
import com.goldsign.acc.frame.constant.CommandConstant;

import com.goldsign.acc.frame.controller.RLBaseController;

import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;

import com.goldsign.acc.frame.util.DBUtil;

import com.goldsign.acc.frame.util.LogUtil;

import com.goldsign.acc.frame.vo.OperationResult;

import java.util.List;

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
public class DistanceDetailController extends RLBaseController {

    @Autowired
    private DistanceDetailMapper mapper;

    @RequestMapping(value = "/distanceDetailAction")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("/jsp/basicparams/distanceDetail.jsp");
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

        String[] attrNames = {LINE, STATION, LINE_STATIONS};
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.getResultSetText((List<DistanceDetail>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    private void getResultSetText(List<DistanceDetail> resultSet, ModelAndView mv) throws Exception {
        List<PubFlag> line = (List<PubFlag>) mv.getModelMap().get(RLBaseController.LINE);
        List<PubFlag> station = (List<PubFlag>) mv.getModelMap().get(RLBaseController.STATION);
        List<PubFlag> record_flag = (List<PubFlag>) mv.getModelMap().get(RLBaseController.RECORDFLAG);

        for (DistanceDetail vo : resultSet) {
            if (line != null && !line.isEmpty()) {
                vo.setpLineText(DBUtil.getTextByCode(vo.getPass_line_out(), line));
            }
            if (station != null && !station.isEmpty()) {
                vo.setpStationText(DBUtil.getTextByCode(vo.getPchange_station_id(), vo.getPass_line_out(), station));
            }
            if (line != null && !line.isEmpty()) {
                vo.setnLineText(DBUtil.getTextByCode(vo.getPass_line_in(), line));
            }
            if (station != null && !station.isEmpty()) {
                vo.setnStationText(DBUtil.getTextByCode(vo.getNchange_station_id(), vo.getPass_line_in(), station));
            }
        }
    }

    private DistanceDetail getQueryCondition(HttpServletRequest request) {
        DistanceDetail vo = new DistanceDetail();
        String od_id = request.getParameter("q_od_id");
        if (!"".equals(od_id)) {
            vo.setOd_id(od_id);
        }
        return vo;
    }

    public OperationResult queryPlan(HttpServletRequest request, DistanceDetailMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        DistanceDetail queryCondition;
        List<DistanceDetail> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = mapper.getDistanceDetail(queryCondition);
            
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

}
