/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.controller;

import com.goldsign.acc.app.opma.entity.SysGroup;
import com.goldsign.acc.app.opma.mapper.SysGroupMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 权限组弹窗
 *
 * @author luck 20171020
 */
@Controller
public class SysGroupWindow extends PrmBaseController {

    @Autowired
    SysGroupMapper sysGrpMapper;

    @RequestMapping("/SysGroupWindow")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/opma/sys_group_window.jsp");
        OperationResult opResult = new OperationResult();
        try {
            opResult = this.getGroup(request, this.operationLogMapper);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }

    public OperationResult getGroup(HttpServletRequest request, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        request.setCharacterEncoding("utf-8");
        String d_groupName = request.getParameter("d_groupName");
        request.setAttribute("d_groupName", d_groupName);
        List<SysGroup> resultSet;
        try {
            resultSet = sysGrpMapper.getAll();
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
