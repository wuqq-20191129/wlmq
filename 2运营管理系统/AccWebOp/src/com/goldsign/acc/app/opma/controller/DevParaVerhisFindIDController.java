/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.controller;

import com.goldsign.acc.app.opma.entity.DevParaVerCur;
import com.goldsign.acc.app.opma.entity.DevParaVerHis;
import com.goldsign.acc.app.opma.mapper.DevParaVerCurMapper;
import com.goldsign.acc.app.opma.mapper.DevParaVerHisMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.FormUtil;
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
 * @author liudz
 * 设备ID
 */
@Controller
public class DevParaVerhisFindIDController extends PrmBaseController{
  @Autowired
    private DevParaVerHisMapper devParaVerHisMapper;
    
    @RequestMapping("/DevIdListForVerhis")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/opma/dev_list.jsp");
        OperationResult opResult = new OperationResult();
        try {
            
            opResult = this.query(request,this.operationLogMapper);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }

    public OperationResult query(HttpServletRequest request, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        DevParaVerHis devParaVerHis;
        List<DevParaVerHis> resultSet;

        try {
            devParaVerHis = this.getQueryCondition(request);
            resultSet = devParaVerHisMapper.getDevIdList(devParaVerHis);
            or.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }
    
    private DevParaVerHis getQueryCondition(HttpServletRequest request) {
        DevParaVerHis devParaVerHis = new DevParaVerHis();
        devParaVerHis.setDev_type_id(FormUtil.getParameter(request, "q_deviceType"));
        devParaVerHis.setLine_id(FormUtil.getParameter(request, "q_lineId"));
        devParaVerHis.setStation_id(FormUtil.getParameter(request, "q_stationId"));
        return devParaVerHis;
    }
    
}

