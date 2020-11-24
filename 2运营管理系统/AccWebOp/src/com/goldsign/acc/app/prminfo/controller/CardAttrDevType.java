/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.opma.entity.DevParaVerCur;
import com.goldsign.acc.app.opma.mapper.DevParaVerCurMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
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
 * 票卡属性设备弹窗
 * @author luck 20171020 
 */
@Controller
public class CardAttrDevType extends PrmBaseController {

    @RequestMapping("/CardAttrDevType")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        String windowType = request.getParameter("WindowType");
        ModelAndView mv = new ModelAndView("/jsp/prminfo/dev_type.jsp");
        OperationResult opResult = new OperationResult();
        try {
            if (windowType.trim().equals("1")) {
                opResult = this.getAddValDev(request, this.operationLogMapper);
            } else if (windowType.trim().equals("2")) {
                opResult = this.getUseOnEqui(request, this.operationLogMapper);
            }else{
                opResult = this.getSaleEquiType(request, this.operationLogMapper);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }

    
    /**
     * 充值设备
     * @param request
     * @param opLogMapper
     * @return
     * @throws Exception 
     */
    public OperationResult getAddValDev(HttpServletRequest request, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        String d_rechargeDeviceType = request.getParameter("d_rechargeDeviceType");
        request.setAttribute("d_rechargeDeviceType", d_rechargeDeviceType);
        LogUtil logUtil = new LogUtil();
        List<PubFlag> resultSet;
        try {
            resultSet = pubFlagMapper.getRechargeDeviceTypes();
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    /**
     * 设备可使用
     * @param request
     * @param opLogMapper
     * @return
     * @throws Exception 
     */
    public OperationResult getUseOnEqui(HttpServletRequest request, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        String d_availableDeviceType = request.getParameter("d_availableDeviceType");
        request.setAttribute("d_rechargeDeviceType", d_availableDeviceType);
        LogUtil logUtil = new LogUtil();
        List<PubFlag> resultSet;
        try {
            resultSet = pubFlagMapper.getDeviceTypes();
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    /**
     * 发售设备
     * @param request
     * @param opLogMapper
     * @return
     * @throws Exception 
     */
    public OperationResult getSaleEquiType(HttpServletRequest request, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        String d_saleEquiType = request.getParameter("d_saleEquiType");
        request.setAttribute("d_rechargeDeviceType", d_saleEquiType);
        LogUtil logUtil = new LogUtil();
        List<PubFlag> resultSet;
        try {
            resultSet = pubFlagMapper.getSaleDeviceTypes();
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
