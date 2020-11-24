/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.controller;

import com.goldsign.acc.app.opma.entity.DevParaVerCur;
import com.goldsign.acc.app.opma.mapper.DevParaVerCurMapper;
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
 * @author zhouyang
 * 20170623
 * 设备参数状态查询——获取查询条件设备ID
 */
@Controller
public class DevIdListController  extends PrmBaseController{
    @Autowired
    private DevParaVerCurMapper DevParaVerCurMapper;
    
    @RequestMapping("/DevIdList")
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
        DevParaVerCur devParaVerCur;
        List<DevParaVerCur> resultSet;

        try {
            devParaVerCur = this.getQueryCondition(request);
            resultSet = DevParaVerCurMapper.getDevIdList(devParaVerCur);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }
    
    private DevParaVerCur getQueryCondition(HttpServletRequest request) {
        DevParaVerCur devParaVerCur = new DevParaVerCur();
        devParaVerCur.setDev_type_id(FormUtil.getParameter(request, "q_deviceType"));
        devParaVerCur.setLine_id(FormUtil.getParameter(request, "q_lineId"));
        devParaVerCur.setStation_id(FormUtil.getParameter(request, "q_stationId"));
        return devParaVerCur;
    }
    
}
