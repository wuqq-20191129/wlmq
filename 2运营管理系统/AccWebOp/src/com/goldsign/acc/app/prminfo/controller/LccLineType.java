/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author liudezeng
 * 下发线路弹窗
 */
@Controller
public class LccLineType extends PrmBaseController{
     @RequestMapping("/LccLineType")
 public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/prminfo/lcc_line_type.jsp");
        OperationResult opResult = new OperationResult();
        try {
         
                opResult = this.getLccLineDev(request, this.operationLogMapper);
        
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }

    private OperationResult getLccLineDev(HttpServletRequest request, OperationLogMapper operationLogMapper)throws Exception {
        OperationResult or = new OperationResult();
//        String parmTypeId = request.getParameter("parmTypeId");
        LogUtil logUtil = new LogUtil();
        List<PubFlag> resultSet;
        try {
            resultSet = pubFlagMapper.getLccLines();
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询"+resultSet.size()+"条记录");
        } catch (Exception e) {
            e.printStackTrace();
             return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
         logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
        
    }
}
